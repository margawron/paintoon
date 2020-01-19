package pl.polsl.gawron.marcel.rplaceServer.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import pl.polsl.gawron.marcel.rplaceServer.repositories.HistoryEntryRepository;
import pl.polsl.gawron.marcel.rplaceServer.repositories.UserRepository;

/**
 * Controller for displaying data stored in database
 *
 * @author Marcel Gawron
 * @version 1.0
 */
@Controller
public class DataPrinterController {

    private UserRepository userRepository;
    private HistoryEntryRepository historyEntryRepository;

    /**
     * Initializes controller class
     *
     * @param userRepository         class managing database user table
     * @param historyEntryRepository class managing database history entry table
     */
    public DataPrinterController(UserRepository userRepository, HistoryEntryRepository historyEntryRepository) {
        this.historyEntryRepository = historyEntryRepository;
        this.userRepository = userRepository;
    }

    /**
     * Function responsible for responding with page of users
     *
     * @param model model object containing attributes for rendering views
     * @return name of the template to render
     */
    @RequestMapping({"/db_users"})
    public String usersPage(Model model) {

        model.addAttribute("users", userRepository.findAll());

        return "usersPage";
    }

    /**
     * Function reponsible for responding with page of history entries
     *
     * @param model model object containing attributes for rendering views
     * @return name of the template to render
     */
    @RequestMapping({"/db_history"})
    public String historyEntryPage(Model model) {

        model.addAttribute("historyEntries", historyEntryRepository.findAll());
        model.addAttribute("foreachLatest", historyEntryRepository.getLatestPixelChangeForEachPixel());
        return "historyPage";
    }
}
