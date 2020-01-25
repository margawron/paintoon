package pl.polsl.gawron.marcel.paintoonServer.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;


/**
 * Main web application controller
 *
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
     *
     * @return name of the home page template
     */
    @RequestMapping({"", "/", "index"})
    public String homepage() {

        return "homepage";
    }


}
