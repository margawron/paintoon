package pl.polsl.gawron.marcel.paintoonServer.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import pl.polsl.gawron.marcel.paintoonData.models.User;
import pl.polsl.gawron.marcel.paintoonServer.models.LoginForm;
import pl.polsl.gawron.marcel.paintoonServer.repositories.HistoryEntryRepository;
import pl.polsl.gawron.marcel.paintoonServer.repositories.UserRepository;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Controller responsible for user login capabilities
 *
 * @author Marcel Gawron
 * @version 1.0
 */
@Controller
public class LoginController {

    private UserRepository userRepository;
    @Autowired
    private HistoryEntryRepository historyEntryRepository;

    /**
     * Constructor for login controller
     *
     * @param userRepository repository handling user data/database
     */
    public LoginController(UserRepository userRepository) {
        this.userRepository = userRepository;
    }


    /**
     * Reponds to HTTP GET requests with stats page
     *
     * @param model    in memory bitmap model
     * @param username cookie with name of logged in user
     * @param token    cookie with token of logged in user
     * @return name of the template to render
     */
    @RequestMapping(path = "/profilePage", method = RequestMethod.GET)
    public String profilePage(Model model,
                              @CookieValue(name = "username", required = false) String username,
                              @CookieValue(name = "token", required = false) String token) {
        // Check for lost souls
        if (username == null || token == null) {
            return "redirect:/login";
        }
        User user = userRepository.findUserByName(username);
        if (user == null) {
            return "redirect:/login";
        }
        if (!user.isTokenValid(token)) {
            return "redirect:/login";
        }

        model.addAttribute("numberOfPixels", historyEntryRepository.getCountOfUserPixelChanges(user.getId()));
        model.addAttribute("username", username);
        model.addAttribute("isTokenValid", user.isTokenValid(token));

        return "profilePage";
    }

    /**
     * Responds to HTTP GET requests with login page
     *
     * @param model   object containing attributes for rendering views
     * @param request request sent by client
     * @return name of the template to render
     */
    @RequestMapping(path = "/login", method = RequestMethod.GET)
    public String sendLoginPage(Model model,
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
                User user = userRepository.findUserByName(username);
                if (user != null && user.isTokenValid(token)) {
                    return "redirect:/profilePage";
                }
            }
        }
        model.addAttribute("formModel", new LoginForm());
        return "login";
    }

    /**
     * Responds to HTTP POST requests from register page
     *
     * @param formModel data model of a form in a register page
     * @param model     model object containing attributes for rendering views
     * @param response  servlet response created by Spring context management
     * @return name of the template to render
     */
    @RequestMapping(path = "/login", method = RequestMethod.POST)
    public String loginUser(@ModelAttribute LoginForm formModel, Model model, HttpServletResponse response) {
        int nameLength = formModel.getName().length();
        model.addAttribute("shouldRedirectToRegister", false);
        if (nameLength < 3 || nameLength > 25) {
            model.addAttribute("message", "Username is not valid length");
            return "authMessage";
        }
        User user = userRepository.findUserByName(formModel.getName());
        if (user == null) {
            model.addAttribute("message", "User does not exist");
            return "authMessage";
        }
        if (!user.getPassword().equals(formModel.getPassword())) {
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
        userRepository.save(user);
        response.addCookie(usernameCookie);
        response.addCookie(tokenCookie);
        return "redirect:/profilePage";
    }

}
