package pl.polsl.gawron.marcel.rplaceServer.controllers;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import pl.polsl.gawron.marcel.rplaceServer.models.HistoryEntryResponseModel;
import pl.polsl.gawron.marcel.rplaceServer.models.UserResponseModel;
import pl.polsl.gawron.marcel.rplaceServer.repositories.HistoryEntryRepository;
import pl.polsl.gawron.marcel.rplaceServer.repositories.UserRepository;

/**
 * @author Marcel Gawron
 * @version 1.0
 */
@RestController
public class WebServiceController {

    private UserRepository userRepository;
    private HistoryEntryRepository historyEntryRepository;

    public WebServiceController(UserRepository userRepository, HistoryEntryRepository historyEntryRepository) {
        this.userRepository = userRepository;
        this.historyEntryRepository = historyEntryRepository;
    }

    @GetMapping("/service/users")
    public UserResponseModel getUser(@RequestParam(name = "id") Long id) {
        var user = userRepository.findById(id);
        if (!user.isPresent()) {
            return null;
        }
        UserResponseModel urm = new UserResponseModel(user.get());
        return urm;
    }

    @GetMapping("/service/history")
    public HistoryEntryResponseModel getHistoryEntry(@RequestParam(name = "id") Long id) {
        var historEntry = historyEntryRepository.findById(id);
        if (!historEntry.isPresent()) {
            return null;
        }
        HistoryEntryResponseModel herm = new HistoryEntryResponseModel(historEntry.get());
        return herm;
    }
}
