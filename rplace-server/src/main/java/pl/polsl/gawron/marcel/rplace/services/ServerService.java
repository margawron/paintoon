package pl.polsl.gawron.marcel.rplace.services;

import pl.polsl.gawron.marcel.rplace.controllers.ProtocolController;

import java.io.*;
import java.net.Socket;

public class ServerService implements Closeable {

    private Socket socket;
    private BufferedReader input;
    private PrintWriter output;

    public ServerService(Socket socket) throws IOException {
        this.socket = socket;
        output = new PrintWriter(new BufferedWriter(
                new OutputStreamWriter(socket.getOutputStream())), true);
        input = new BufferedReader(new InputStreamReader(socket.getInputStream()));

    }

    public void respond(ProtocolController controller) {
        try {
            while (true) {
                controller.processRequest(input, output);
            }
        } catch (IOException e) {
            System.err.println(e.getMessage());
        } finally {
            try {
                socket.close();
            } catch (IOException e) {
                System.err.println(e.getMessage());
            }
        }
    }

    @Override
    public void close() throws IOException {
        if (socket != null) {
            socket.close();
        }
    }
}
