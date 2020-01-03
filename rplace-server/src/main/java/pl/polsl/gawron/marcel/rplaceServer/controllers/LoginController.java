package pl.polsl.gawron.marcel.rplaceServer.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import pl.polsl.gawron.marcel.rplaceData.models.User;
import pl.polsl.gawron.marcel.rplaceServer.models.LoginFormModel;
import pl.polsl.gawron.marcel.rplaceServer.repositories.UserRepository;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;

/**
 * @author Marcel Gawron
 * @version 1.0
 */
@Controller
public class LoginController {

    private UserRepository userRepository;

    /**
     * Constructor for login controller
     * @param userRepository repository handling user data/database
     */
    public LoginController(UserRepository userRepository){
        this.userRepository = userRepository;
    }

    /**
     * Responds to HTTP GET requests with login page
     * @param model object containing attributes for rendering views
     * @return name of the template to render
     */
    @RequestMapping(path = "/login", method = RequestMethod.GET)
    public String sendLoginPage(Model model) {
        model.addAttribute("formModel", new LoginFormModel());
        return "login";
    }

    /**
     * Responds to HTTP POST requests from register page
     * @param formModel data model of a form in a register page
     * @param model model object containing attributes for rendering views
     * @return name of the template to render
     */
    @RequestMapping(path = "/login", method = RequestMethod.POST)
    public String loginUser(@ModelAttribute LoginFormModel formModel, Model model, HttpServletResponse response){
        int nameLength = formModel.getName().length();
        model.addAttribute("shouldRedirectToRegister", false);
        if(nameLength < 3 || nameLength > 25)
        {
            model.addAttribute("message", "Username is not valid length");
            return "authMessage";
        }
        User user = userRepository.findUser(formModel.getName());
        if(user == null){
            model.addAttribute("message", "User does not exist");
            return "authMessage";
        }
        if(!user.getPassword().equals(formModel.getPassword())){
            model.addAttribute("message", "Password does not match");
            return "authMessage";
        }
        Cookie usernameCookie = new Cookie("username", user.getName());
        user.generateUserTokenAndSetExpiryTime();
        Cookie tokenCookie = new Cookie("token", user.getToken());
        int expiryDate = 7 * 24 * 60 * 60;   // days * hours * minutes * seconds
        usernameCookie.setMaxAge(expiryDate);
        tokenCookie.setMaxAge(expiryDate);
//        usernameCookie.setSecure(true); // No HTTPS :(
//        tokenCookie.setSecure(true);
        usernameCookie.setHttpOnly(true);
        tokenCookie.setHttpOnly(true);

        response.addCookie(usernameCookie);
        response.addCookie(tokenCookie);
        return "loggedInPage";
    }

}
