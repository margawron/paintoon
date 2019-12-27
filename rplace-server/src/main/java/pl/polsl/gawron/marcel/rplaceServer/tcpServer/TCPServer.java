package pl.polsl.gawron.marcel.rplaceServer.tcpServer;

import org.springframework.stereotype.Component;
import pl.polsl.gawron.marcel.rplaceServer.tcpServer.controllers.ProtocolController;
import pl.polsl.gawron.marcel.rplaceServer.tcpServer.services.ServerService;

import java.io.Closeable;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Properties;

/**
 * TCP server class
 *
 * @author Marcel Gawron
 * @version 1.0
 */
@Component
public class TCPServer extends Thread implements Closeable {
    // Network
    private Thread thread;
    private int port;
    private ServerSocket serverSocket;
    private ProtocolController protocolController;

    /**
     * Default constructor
     */
    public TCPServer() {
        protocolController = new ProtocolController();
        this.thread = new Thread(this);
        this.thread.start();
    }

    /**
     * Run TCP server
     */
    @Override
    public void run(){
        System.out.println("TCP Server starting ...");
        try {
            Properties properties = new Properties();
            String path = Thread.currentThread().getContextClassLoader().getResource("").getPath();
            try(FileInputStream inputStream = new FileInputStream(path + File.separator + "config.properties")) {
                properties.load(inputStream);
                this.port = Integer.parseInt(properties.getProperty("serv.port"));
            }
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
