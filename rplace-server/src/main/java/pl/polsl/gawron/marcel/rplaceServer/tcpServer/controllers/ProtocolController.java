package pl.polsl.gawron.marcel.rplaceServer.tcpServer.controllers;

import org.springframework.stereotype.Component;
import pl.polsl.gawron.marcel.rplaceData.models.Image;
import pl.polsl.gawron.marcel.rplaceServer.tcpServer.handlers.PacketHandler;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;

/**
 * Server-side networking controller
 *
 * @author Marcel Gawron
 * @version 1.0
 */
@Component
public class ProtocolController {

    // Data
    private Image image;
    private PacketHandler dispatcher;


    /**
     * Constructor for handling request of custom TCP protocol
     *
     * @param handler manages responding to the clients
     * @param image   in memory bitmap model
     */
    public ProtocolController(PacketHandler handler, Image image) {
        this.image = image;
        this.dispatcher = handler;
    }

    /**
     * Main request processing function
     *
     * @param input  input reader
     * @param output output printer
     * @throws IOException can be thrown by {@link BufferedReader#readLine()}
     */
    public void processRequest(BufferedReader input, PrintWriter output) throws IOException {
        String inputString = input.readLine();
        String outputString = parseAndDispatchInput(inputString);
        output.println(outputString);
    }

    /**
     * Divides input via space between the packet type code and packet body
     *
     * @param input full input from client
     * @return server response
     * @throws IOException when user disconnects
     */
    private String parseAndDispatchInput(String input) throws IOException {
        if (input == null) {
            throw new IOException("Client disconnected");
        }
        if (input.contains("HELP")) {
            return "Program makes use of custom protocol but if you want to get server-side image just type \"2 {}\"";
        }
        String[] split = input.split(" ", 2);
        return dispatcher.dispatchPacket(split);
    }

    /**
     * Getter for image property
     *
     * @return image property
     */
    public Image getImage() {
        return image;
    }

    /**
     * Setter for image property
     *
     * @param image new image property
     */
    public void setImage(Image image) {
        this.image = image;
    }

    /**
     * Getter for dispatcher property
     *
     * @return dispatcher property
     */
    public PacketHandler getDispatcher() {
        return dispatcher;
    }

    /**
     * Setter for dispatcher property
     *
     * @param dispatcher new dispatcher property
     */
    public void setDispatcher(PacketHandler dispatcher) {
        this.dispatcher = dispatcher;
    }
}
