package pl.polsl.gawron.marcel.rplace.services;

import pl.polsl.gawron.marcel.rplace.controllers.ProtocolController;

import java.io.*;
import java.net.Socket;

/**
 * Implements a server service
 *
 * @author Marcel Gawron
 * @version 1.0
 */
public class ServerService implements Closeable {

    private Socket socket;
    private BufferedReader input;
    private PrintWriter output;

    /**
     * Default constructor
     * Assigns a socket
     * makes PrintWriter output and BufferedReader input
     *
     * @param socket socket made by ServerSocket
     * @throws IOException thrown when output and input cannot be created
     */
    public ServerService(Socket socket) throws IOException {
        this.socket = socket;
        output = new PrintWriter(new BufferedWriter(
                new OutputStreamWriter(socket.getOutputStream())), true);
        input = new BufferedReader(new InputStreamReader(socket.getInputStream()));

    }

    /**
     * Invokes controller to process a request
     *
     * @param controller controller to be invoked
     */
    public void respond(ProtocolController controller) {
        try {
            while (true) {
                controller.processRequest(input, output);
            }
        } catch (IOException e) {
            try {
                socket.close();
            } catch (IOException err) {
                System.err.println(err.getMessage());
            }
        }
    }

    /**
     * Needs to be implemented for Closeable interface
     *
     * @throws IOException throws when socket is in use
     */
    @Override
    public void close() throws IOException {
        if (socket != null) {
            socket.close();
        }
    }
}
