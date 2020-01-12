package pl.polsl.gawron.marcel.rplaceServer.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import pl.polsl.gawron.marcel.rplaceData.models.User;
import pl.polsl.gawron.marcel.rplaceServer.models.RegistrationForm;
import pl.polsl.gawron.marcel.rplaceServer.repositories.UserRepository;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;

/**
 * Controller responsible for registering users
 *
 * @author Marcel Gawron
 * @version 1.0
 */
@Controller
public class RegisterController {

    private UserRepository userRepository;

    /**
     * Constructor for register controller
     *
     * @param userRepository repository handling user data/database
     */
    public RegisterController(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    /**
     * Responds to HTTP GET requests with register page
     *
     * @param model   model object containing attributes for rendering views
     * @param request request sent by user
     * @return name of the template to render
     */
    @RequestMapping(path = "/register", method = RequestMethod.GET)
    public String sendRegisterPage(Model model,
                                   HttpServletRequest request) {
        // Get necessary cookies
        Cookie[] cookies = request.getCookies();
        String username = null;
        String token = null;
        if (cookies != null) {
            for (var cookie : cookies) {
                if (cookie.getName().equals("username")) {
                    username = cookie.getValue();
                }
                if (cookie.getName().equals("token")) {
                    token = cookie.getValue();
                }
            }
            // Check if user is already logged and if he is, redirect him to login page
            if (username != null && token != null) {
                User user = userRepository.findUser(username);
                if (user != null && user.isTokenValid(token)) {
                    return "redirect:/loggedIn";
                }
            }
        }

        model.addAttribute("formModel", new RegistrationForm());
        return "register";
    }

    /**
     * Responds to HTTP POST requests from register page
     *
     * @param formModel data model of a form in a register page
     * @param model     model object containing attributes for rendering views
     * @return name of the template to render
     */
    @RequestMapping(path = "/register", method = RequestMethod.POST)
    public String userRegister(@ModelAttribute RegistrationForm formModel, Model model) {
        model.addAttribute("shouldRedirectToRegister", true); // Show register page button

        if (!formModel.getName().matches("(?!.*[.\\-_]{2,})^[a-zA-Z0-9.\\-_]{3,24}$")) {
            model.addAttribute("message",
                    "Username should be 3-24 characters long, can contain letters, numbers and special characters ._-(which cannot be consecutive or combined)");
            return "authMessage";
        }
        if (!formModel.getPasswordFirst().matches("^(?=.*\\d)(?=.*[a-z])(?=.*[A-Z])(?=.*[a-zA-Z]).{8,20}$")) {
            model.addAttribute("message", "Password should be 8-20 characters long and have at least 1 uppercase letter, 1 lower case letter, and 1 number");
            return "authMessage";
        }
        if (!formModel.getPasswordFirst().equals(formModel.getPasswordSecond())) {
            model.addAttribute("message", "Passwords dont match");
            return "authMessage";
        }
        userRepository.addUser(formModel.getName(), formModel.getPasswordFirst());
        model.addAttribute("message", "Now please login!");
        model.addAttribute("shouldRedirectToRegister", false); // Show login page button
        return "authMessage";
    }
}
