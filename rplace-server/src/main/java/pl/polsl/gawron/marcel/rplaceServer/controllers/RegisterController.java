package pl.polsl.gawron.marcel.rplaceServer.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import pl.polsl.gawron.marcel.rplaceServer.models.RegistrationFormModel;
import pl.polsl.gawron.marcel.rplaceServer.repositories.UserRepository;

/**
 * Controller responsible for registering users
 *
 * @author Marcel Gawron
 * @version 1.0
 */
@Controller
public class RegisterController {

    private UserRepository userRepository;

    public RegisterController(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @GetMapping("/register")
    public String sendRegisterPage(Model model) {
        model.addAttribute("formModel", new RegistrationFormModel());

        return "register";
    }

    @RequestMapping("/register")
    public String userRegister(@ModelAttribute RegistrationFormModel formModel, Model model)
    {
        model.addAttribute("shouldRedirectToRegister", true); // Show register page button

        if (!formModel.getName().matches("(?!.*[.\\-_]{2,})^[a-zA-Z0-9.\\-_]{3,24}$")) {
            model.addAttribute("message",
                    "Username should be 3-24 characters long, can contain letters, numbers and special characters ._-(which cannot be consecutive or combined)");
            return "authMessage";
        }
        if (!formModel.getPasswordFirst().matches("^(?=.*\\d)(?=.*[a-z])(?=.*[A-Z])(?=.*[a-zA-Z]).{8,20}$")){
            model.addAttribute("message", "Password should be 8-20 characters long and have at least 1 uppercase letter, 1 lower case letter, and 1 number");
            return "authMessage";
        }
        if(!formModel.getPasswordFirst().equals(formModel.getPasswordSecond())){
            model.addAttribute("message", "Passwords dont match");
            return "authMessage";
        }
        userRepository.addUser(formModel.getName(), formModel.getPasswordFirst());
        model.addAttribute("message", "Now please login!");
        model.addAttribute("shouldRedirectToRegister", false); // Show login page button
        return "authMessage";
    }
}
