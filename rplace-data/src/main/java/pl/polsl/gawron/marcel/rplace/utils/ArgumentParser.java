package pl.polsl.gawron.marcel.rplace.utils;

import pl.polsl.gawron.marcel.rplace.exceptions.ArgumentParsingException;

import java.util.*;

/**
 * Class that parses configuration passed by a command line during startup
 *
 * @author Marcel Gawron
 * @version 1.0
 */
public class ArgumentParser {

    private String[] arguments;
    private Map<String, String> config;

    /**
     * Creates an argument parser
     *
     * @param args arguments forwarded from command line
     */
    public ArgumentParser(String[] args) {
        arguments = args;
    }

    /**
     * Parse arguments
     *
     * @return member map configuration object
     */
    public Map<String, String> parse() {
        config = new HashMap<>();
        try {
            extractPortNumberFromArguments();
        } catch (ArgumentParsingException e) {
            extractPortNumberFromCmd();
        }
        return config;
    }

    /**
     * Extracts port number from arguments passed to program
     *
     * @throws ArgumentParsingException thrown when port number failed to be parsed by Short.parseShort();
     */
    private void extractPortNumberFromArguments() throws ArgumentParsingException {
        Short port = null;
        for (int i = 0; i < arguments.length; i++) {
            if (arguments[i].equals(Constants.Strings.PORT_NUMBER_COMMAND_FLAG) && i + 1 < arguments.length) {
                try {
                    port = Short.parseShort(arguments[i + 1]);
                    config.put("port", port.toString());
                } catch (NumberFormatException e) {
                    throw new ArgumentParsingException("Port number cannot be parsed!");
                }
            }
        }
        if (port == null) {
            extractPortNumberFromCmd();
        }
    }

    /**
     * Asks user via Command line prompt to enter port number
     */
    private void extractPortNumberFromCmd() {
        Scanner sc = new Scanner(System.in);
        Short port = null;
        while (port == null) {
            try {
                System.out.println("Please, enter port number on which application should work:");
                port = sc.nextShort();
            } catch (InputMismatchException e) {
                System.out.println("Port number is not valid, please try again");
                port = null;
                sc.nextLine();
            }
        }
        config.put("port", port.toString());
    }

    /**
     * Getter for config map
     *
     * @return unmodifiable config map generated by parser
     */
    public final Map<String, String> getConfig() {
        return Collections.unmodifiableMap(config);
    }

    /**
     * Prints content of config map inside argument parser
     */
    public void printConfig() {
        System.out.println("Key -> Value");
        for (Map.Entry<String, String> entry : config.entrySet()) {
            System.out.println(entry.getKey() + " -> " + entry.getValue());
        }
    }

}
