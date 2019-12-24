package pl.polsl.gawron.marcel.rplace;

import pl.polsl.gawron.marcel.rplace.controllers.ProtocolController;
import pl.polsl.gawron.marcel.rplace.services.ServerService;

import java.io.Closeable;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

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
    final private int PORT = 9876;
    private ServerSocket serverSocket;
    private ProtocolController protocolController;

    public Main() {
        protocolController = new ProtocolController();
    }

    public static void main(String[] args) {
        try {
            Main server = new Main();
            server.startServer();
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
    }

    public void startServer() throws IOException {
        serverSocket = new ServerSocket(PORT);
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

    @Override
    public void close() throws IOException {
        if (serverSocket != null) {
            serverSocket.close();
        }
    }
}

/**
 * // Argument parser for program
 * ArgumentParser argparse = new ArgumentParser(args);
 * argparse.parse();
 * argparse.printConfig();
 * <p>
 * // Creates an Image
 * ImageController bmpCtrl = new ImageController(new Image(100));
 * for (int i = 0; i < 100; ++i) {
 * bmpCtrl.setPixel(i, i, (byte) -128, (byte) -128, (byte) 0);
 * }
 * for (int i = 0; i < 100; ++i) {
 * bmpCtrl.setPixel(99 - i, i, new Color(0, 0, 255));
 * }
 * bmpCtrl.saveAsFile("test");
 */