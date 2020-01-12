package pl.polsl.gawron.marcel.rplaceServer.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestBody;
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

    public DataPrinterController(UserRepository userRepository, HistoryEntryRepository historyEntryRepository){
        this.historyEntryRepository = historyEntryRepository;
        this.userRepository = userRepository;
    }

    @RequestMapping({"/db_users"})
    public String usersPage(Model model){

        model.addAttribute("users", userRepository.getAllUsers());

        return "usersPage";
    }

    @RequestMapping({"/db_history"})
    public String historyEntryPage(Model model){

        model.addAttribute("historyEntries", historyEntryRepository.getHistoryEntries());

        return "historyPage";
    }
}
