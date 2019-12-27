package pl.polsl.gawron.marcel.rplace;

import pl.polsl.gawron.marcel.rplace.controllers.ProtocolController;
import pl.polsl.gawron.marcel.rplace.services.ServerService;
import pl.polsl.gawron.marcel.rplace.utils.ArgumentParser;

import java.io.Closeable;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Map;

/**
 * Main class of a program
 *
 * @author Marcel Gawron
 * @version 1.0
 */
public class Main implements Closeable {
    /**
     * Main class of server application
     *
     * @param args
     */
    // Network
    private int port = 9876;
    private ServerSocket serverSocket;
    private ProtocolController protocolController;

    /**
     * Default constructor
     */
    public Main() {
        protocolController = new ProtocolController();
    }

    /**
     * Main function of a program
     * @param args arguments passed from command line during start of a program
     */
    public static void main(String[] args) {
        try {
            Main server = new Main();
            ArgumentParser argumentParser = new ArgumentParser(args);
            Map<String, String> config = argumentParser.parse();
            int port = Integer.parseInt(config.get("port"));
            server.setPort(port);
            argumentParser.printConfig();
            server.startServer();
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
    }

    /**
     * Starts listening on a socket
     * @throws IOException thrown when socket could not be opened
     */
    public void startServer() throws IOException {
        serverSocket = new ServerSocket(port);
        System.out.println("Server started");
        while (true) {
            Socket socket = serverSocket.accept();
            try (ServerService serverService = new ServerService(socket)) {
                serverService.respond(protocolController);
            } catch (IOException e) {
                System.err.println(e.getMessage());
            }
        }
    }

    /**
     * Invoked when server is shut down
     * @throws IOException could throw when socket is in use
     */
    @Override
    public void close() throws IOException {
        if (serverSocket != null) {
            serverSocket.close();
        }
    }

    /**
     * Getter for a port
     * @return number of a port that server should listen on
     */
    public int getPort() {
        return port;
    }

    /**
     * Setter for a prot
     * @param port number of a port that sever should listen on
     */
    public void setPort(int port) {
        this.port = port;
    }
}