package pl.polsl.gawron.marcel.rplace;

import pl.polsl.gawron.marcel.rplace.handlers.ConnectionHandler;
import pl.polsl.gawron.marcel.rplace.handlers.UserInteractionHandler;
import pl.polsl.gawron.marcel.rplace.utils.ArgumentParser;

import java.net.Socket;
import java.util.Map;

/**
 * Client side main function
 *
 * @author Marcel Gawron
 * @version 1.0
 */
public class Main {
    /**
     * Main function of a client side application
     *
     * @param args arguments passed to client program
     */

    public static void main(String[] args) {
        UserInteractionHandler interactionHandler = new UserInteractionHandler(args);
        interactionHandler.run();
    }
}
