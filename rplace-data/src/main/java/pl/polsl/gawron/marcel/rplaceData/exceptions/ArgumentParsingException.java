package pl.polsl.gawron.marcel.rplaceData.exceptions;

/**
 * Exceptions thrown when arguments do not conform to program specification
 *
 * @author Marcel Gawron
 * @version 1.0
 */
public class ArgumentParsingException extends Exception {

    /**
     * Default constructor
     */
    public ArgumentParsingException() {
        super("Error during parsing program arguments");
    }

    /**
     * Exception with custom message
     *
     * @param message custom message
     */
    public ArgumentParsingException(String message) {
        super(message);
    }
}
