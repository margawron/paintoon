package pl.polsl.gawron.marcel.rplaceServer.controllers;

import org.springframework.stereotype.Repository;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * Main web application controller
 *
 * @author Marcel Gawron
 * @version 1.0
 */
@Repository
public class HomePageController {

    public HomePageController(){

    }

    @RequestMapping({"", "/", "index"})
    public String homepage(Model model){

        return "homepage";
    }


}
