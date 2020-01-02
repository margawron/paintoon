package pl.polsl.gawron.marcel.rplaceServer.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;


/**
 * TODO:
 * https://dzone.com/articles/how-to-use-cookies-in-spring-boot
 */

/**
 * Main web application controller
 * @author Marcel Gawron
 * @version 1.0
 */
@Controller
public class HomePageController {

    /**
     * Default constructor
     */
    public HomePageController() {

    }

    /**
     * Function responsible for responding with homepage
     * @return name of the home page template
     */
    @RequestMapping({"", "/", "index"})
    public String homepage() {

        return "homepage";
    }


}