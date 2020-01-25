package pl.polsl.gawron.marcel.paintoonClient.handlers;

import pl.polsl.gawron.marcel.paintoonData.models.Color;
import pl.polsl.gawron.marcel.paintoonData.protocol.PacketType;
import pl.polsl.gawron.marcel.paintoonData.protocol.packets.requests.RequestImageByteArray;
import pl.polsl.gawron.marcel.paintoonData.protocol.packets.requests.RequestLogin;
import pl.polsl.gawron.marcel.paintoonData.protocol.packets.requests.RequestRegister;
import pl.polsl.gawron.marcel.paintoonData.protocol.packets.requests.RequestSetPixel;
import pl.polsl.gawron.marcel.paintoonData.protocol.packets.responses.*;

import java.io.*;
import java.net.Socket;
import java.util.Properties;

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
     * loads port and address value from properties file
     */
    public ConnectionHandler() {
        Properties properties = new Properties();

        String path = Thread.currentThread().getContextClassLoader().getResource("").getPath();
        try (FileInputStream inputStream = new FileInputStream(path + File.separator + "config.properties")) {
            properties.load(inputStream);
            this.host = properties.getProperty("connection.address");
            this.port = Integer.parseInt(properties.getProperty("connection.port"));
        } catch (IOException e) {
            System.err.println(e.getMessage());
            System.exit(-1);
        }
    }

    /**
     * Attempts to create a connection to a server
     * will exit if try fails
     */
    public void connect() {
        try {
            clientSocket = new Socket(host, port);
            input = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
            output = new PrintWriter(new BufferedWriter(
                    new OutputStreamWriter(clientSocket.getOutputStream())), true);
        } catch (IOException e) {
            System.out.println(e.getMessage());
            System.exit(-1);
        }
    }

    /**
     * Makes request to the server for registration
     *
     * @param name     username to be registered
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
        if (packetCode == 200) {
            ResponseInvalidRequest invalidRequest = ResponseInvalidRequest.deserialize(responseStrings[1]);
            System.out.println(invalidRequest.getMessage());
            return null;
        }
        return ResponseRegister.deserialize(responseStrings[1]);
    }

    /**
     * Makes request to the server for login
     *
     * @param name     username to be registered
     * @param password password for the user
     * @return returns response from the server
     * @throws Exception could be thrown from {@link BufferedReader#readLine()}
     */
    public ResponseLogin requestLogin(String name, String password) throws Exception {
        StringBuilder stringBuilder = new StringBuilder();

        RequestLogin request = new RequestLogin();
        request.setName(name);
        request.setPassword(password);

        stringBuilder.append(PacketType.REQUEST_LOGIN.getValue());
        stringBuilder.append(" ");
        stringBuilder.append(request.serialize());
        output.println(stringBuilder.toString());

        String[] responseStrings = splitInput(input.readLine());
        int packetCode = Integer.parseInt(responseStrings[0]);
        if (packetCode == 200) {
            ResponseInvalidRequest invalidRequest = ResponseInvalidRequest.deserialize(responseStrings[1]);
            System.out.println(invalidRequest.getMessage());
            return null;
        }
        return ResponseLogin.deserialize(responseStrings[1]);
    }

    /**
     * Makes request to the server for getting image
     *
     * @return response from the server
     * @throws Exception when cannot read or write
     */
    public ResponseImageByteArray requestImageByteArray() throws Exception {
        StringBuilder stringBuilder = new StringBuilder();

        RequestImageByteArray request = new RequestImageByteArray();

        stringBuilder.append(PacketType.REQUEST_IMAGE_BYTE_ARRAY.getValue());
        stringBuilder.append(" ");
        stringBuilder.append(request.serialize());
        output.println(stringBuilder.toString());

        String[] responseStrings = splitInput(input.readLine());
        int packetCode = Integer.parseInt(responseStrings[0]);
        if (packetCode == 200) {
            ResponseInvalidRequest invalidRequest = ResponseInvalidRequest.deserialize(responseStrings[1]);
            System.out.println(invalidRequest.getMessage());
            return null;
        }
        return ResponseImageByteArray.deserialize(responseStrings[1]);
    }

    /**
     * Makes request for settting the pixel
     *
     * @param username user name setting the pixel
     * @param token    authentication token
     * @param x        horizontal position of a pixel
     * @param y        vertical position of a pixel
     * @param r        red component of a pixel
     * @param g        green component of a pixel
     * @param b        blue component of a pixel
     * @return response from server
     * @throws Exception when cannot write or read
     */
    public ResponseSetPixel requestSetPixel(String username, String token, int x, int y, byte r, byte g, byte b) throws Exception {
        StringBuilder stringBuilder = new StringBuilder();

        RequestSetPixel request = new RequestSetPixel();
        request.setUserName(username);
        request.setUserToken(token);
        request.setxPos(x);
        request.setyPos(y);
        Color color = new Color();
        color.setRed(r);
        color.setGreen(g);
        color.setBlue(b);
        request.setColor(color);

        stringBuilder.append(PacketType.REQUEST_SET_PIXEL.getValue());
        stringBuilder.append(" ");
        stringBuilder.append(request.serialize());
        output.println(stringBuilder.toString());

        String[] responseStrings = splitInput(input.readLine());
        int packetCode = Integer.parseInt(responseStrings[0]);
        if (packetCode == 200) {
            ResponseInvalidRequest invalidRequest = ResponseInvalidRequest.deserialize(responseStrings[1]);
            System.out.println(invalidRequest.getMessage());
            return null;
        }
        return ResponseSetPixel.deserialize(responseStrings[1]);
    }

    /**
     * Splits message into packet code and the message itself
     *
     * @param string input string
     * @return Array of two strings 0 - packet code, 1 - packet
     */
    private String[] splitInput(String string) {
        return string.split(" ", 2);
    }
}
