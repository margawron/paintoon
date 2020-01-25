package pl.polsl.gawron.marcel.paintoonServer;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * Main class of a program
 *
 * @author Marcel Gawron
 * @version 1.0
 */
@SpringBootApplication
public class Main {

    /**
     * Main function of a program
     * Loads src/main/java/resources/config.properties, sets port of the server
     * and starts the server
     * then starts Spring application
     *
     * @param args arguments passed from command line during start of a program
     */
    public static void main(String[] args) {
        SpringApplication.run(Main.class, args);
    }

}