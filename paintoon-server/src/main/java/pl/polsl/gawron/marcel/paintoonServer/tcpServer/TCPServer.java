package pl.polsl.gawron.marcel.paintoonServer.tcpServer;

import pl.polsl.gawron.marcel.paintoonData.models.Image;
import pl.polsl.gawron.marcel.paintoonServer.tcpServer.controllers.ProtocolController;
import pl.polsl.gawron.marcel.paintoonServer.tcpServer.services.ServerService;

import java.io.Closeable;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * TCP server class
 *
 * @author Marcel Gawron
 * @version 1.0
 */
public class TCPServer extends Thread implements Closeable {
    private int port;
    private ServerSocket serverSocket;
    private ProtocolController protocolController;

    /**
     * Server constructor
     *
     * @param controller controller responsible for custom TCP protocol
     * @param image      Image model
     * @param port       port on which server should listen on
     */
    public TCPServer(ProtocolController controller, Image image, int port) {
        protocolController = controller;
        this.port = port;
        // Network
        Thread thread = new Thread(this);
        thread.start();
    }

    /**
     * Run TCP server
     */
    @Override
    public void run() {
        System.out.println("TCP Server listening on port: " + this.port);

        try {
            startServer();
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
    }

    /**
     * Starts listening on a socket
     *
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
     *
     * @throws IOException could throw when socket is in use
     */
    @Override
    public void close() throws IOException {
        if (serverSocket != null) {
            serverSocket.close();
        }
    }
}
