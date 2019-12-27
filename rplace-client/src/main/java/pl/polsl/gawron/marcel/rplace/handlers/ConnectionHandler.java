package pl.polsl.gawron.marcel.rplace.handlers;

import pl.polsl.gawron.marcel.rplace.protocol.PacketType;
import pl.polsl.gawron.marcel.rplace.protocol.packets.requests.RequestRegister;
import pl.polsl.gawron.marcel.rplace.protocol.packets.responses.ResponseInvalidRequest;
import pl.polsl.gawron.marcel.rplace.protocol.packets.responses.ResponseRegister;

import java.io.*;
import java.net.Socket;

/**
 * Handles connection to the server
 *
 * @author Marcel Gawron
 * @version 1.0
 */
public class ConnectionHandler {

    private String host;
    private int port;
    private Socket clientSocket;
    private BufferedReader input;
    private PrintWriter output;

    /**
     * Default constructor
     * @param host address of a host to connect to
     * @param port port of a host to connect to
     */
    public ConnectionHandler(String host, int port){
        this.port = port;
        this.host = host;
    }

    /**
     * Attempts to create a connection to a server
     * will exit if try fails
     */
    public void connect(){
        try {
            clientSocket = new Socket(host, port);
            input = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
            output = new PrintWriter(new BufferedWriter(
                    new OutputStreamWriter(clientSocket.getOutputStream())), true);
        } catch (IOException e){
            System.out.println(e.getMessage());
            System.exit(-1);
        }
    }


    /**
     * Makes request to the server for registration
     * @param name username to be registered
     * @param password password for the user
     * @return returns response from the server
     * @throws Exception could be thrown from {@link BufferedReader#readLine()}
     */
    public ResponseRegister requestRegister(String name, String password) throws Exception {
        StringBuilder stringBuilder = new StringBuilder();

        RequestRegister request = new RequestRegister();
        request.setName(name);
        request.setPassword(password);

        stringBuilder.append(PacketType.REQUEST_REGISTER.getValue());
        stringBuilder.append(" ");
        stringBuilder.append(request.serialize());
        output.println(stringBuilder.toString());

        String[] responseStrings = splitInput(input.readLine());
        int packetCode = Integer.parseInt(responseStrings[0]);
        if(packetCode == 200){
            ResponseInvalidRequest invalidRequest = ResponseInvalidRequest.deserialize(responseStrings[1]);
            System.out.println(invalidRequest.getMessage());
            return null;
        }
        return ResponseRegister.deserialize(responseStrings[1]);
    }

    /**
     * Splits message into packet code and the message itself
     * @param string input string
     * @return Array of two strings 0 - packet code, 1 - packet
     */
    private String[] splitInput(String string){
        return string.split(" ");
    }
}
