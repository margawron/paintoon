package pl.polsl.gawron.marcel.paintoonClient.handlers;

import pl.polsl.gawron.marcel.paintoonData.protocol.packets.responses.ResponseImageByteArray;
import pl.polsl.gawron.marcel.paintoonData.protocol.packets.responses.ResponseLogin;
import pl.polsl.gawron.marcel.paintoonData.protocol.packets.responses.ResponseRegister;
import pl.polsl.gawron.marcel.paintoonData.protocol.packets.responses.ResponseSetPixel;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.awt.image.DataBufferByte;
import java.awt.image.Raster;
import java.io.File;
import java.io.IOException;
import java.util.Scanner;

/**
 * Controls interaction with the user
 *
 * @author Marcel Gawron
 * @version 1.0
 */
public class UserInteractionHandler {

    private String[] args;
    private boolean isRegistered;
    private boolean isLoggedIn;
    private String username;
    private String password;
    private String token;

    /**
     * Default constructor
     *
     * @param args arguments will be forwarded to ArgumentParser
     */
    public UserInteractionHandler(String[] args) {
        this.args = args;

    }

    /**
     * Runs main program loop
     */
    public void run() {
        ConnectionHandler connectionHandler = new ConnectionHandler();
        connectionHandler.connect();

        registration(connectionHandler);
        login(connectionHandler);
        boolean shouldRun = true;
        while (shouldRun) {
            System.out.println("Tell me what you want to do:");
            System.out.println("0 - exit, 1 - get current server image, 2 - send pixel change request");
            Scanner scanner = new Scanner(System.in);
            int whatToDo = scanner.nextInt();
            switch (whatToDo) {
                case 0: {
                    shouldRun = false;
                    break;
                }
                case 1: {
                    currentServerImage(connectionHandler);
                    break;
                }
                case 2: {
                    sendPixelChangeToServer(connectionHandler);
                    break;
                }
                default: { /* DO NOTHING */}
            }
        }

        printDebugInfo();
    }

    /**
     * Handles registration part of connection to the server
     *
     * @param connectionHandler server connection handler
     */
    private void registration(ConnectionHandler connectionHandler) {
        if (askIfUserIsRegistered()) {
            getUserCredentials();
        } else {
            if (!this.isRegistered) {
                System.out.println("Please provide login and password for registration purposes");
                System.out.println("Then you will be automatically logged in");
            }
            getUserCredentials();
            while (!this.isRegistered) {
                ResponseRegister response = null;
                try {
                    response = connectionHandler.requestRegister(username, password);
                } catch (Exception e) {
                    System.out.println(e.getMessage());
                }
                if (response == null) {
                    return;
                }
                isRegistered = response.isRegistrationSuccessful();
                if (!isRegistered) {
                    System.out.println("Something went wrong, please enter your credentials again");
                    getUserCredentials();
                }
            }
        }
    }

    /**
     * Handles login part of connection to the server
     *
     * @param connectionHandler server connection handler
     */
    private void login(ConnectionHandler connectionHandler) {
        boolean isLoggedIn = false;
        while (!isLoggedIn) {
            ResponseLogin response = null;
            try {
                response = connectionHandler.requestLogin(username, password);
            } catch (Exception e) {
                System.out.println(e.getMessage());
            }
            if (response == null) {
                return;
            }
            this.token = response.getToken();
            isLoggedIn = true;
        }
    }

    /**
     * Gets server-side image state and saves it to a file
     *
     * @param connectionHandler server connection handler
     */
    private void currentServerImage(ConnectionHandler connectionHandler) {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Image will be saved as a file");
        System.out.println("Please enter file name");

        String filename = scanner.next();

        ResponseImageByteArray response = null;
        try {
            response = connectionHandler.requestImageByteArray();
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        if (response == null) {
            System.out.println("Request failed");
        }
        byte[] imageByteArray = response.getBitmapByteArray();
        BufferedImage bufferedImage = new BufferedImage(response.getSize(), response.getSize(), BufferedImage.TYPE_3BYTE_BGR);
        bufferedImage.setData(Raster.createRaster(bufferedImage.getSampleModel(), new DataBufferByte(imageByteArray, 3 * response.getSize() * response.getSize()), new Point()));

        File file = new File(filename + ".png");
        try {
            ImageIO.write(bufferedImage, "png", file);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Tries to set server side pixel to user entered value
     *
     * @param connectionHandler server connection handler
     */
    private void sendPixelChangeToServer(ConnectionHandler connectionHandler) {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Select horizontal pixel position to be changed:");
        int x = scanner.nextInt();
        System.out.println("Select vertical pixel position to be changed:");
        int y = scanner.nextInt();
        System.out.println("Select red component");
        int r = getColorComponent();
        System.out.println("Select green component:");
        int g = getColorComponent();
        System.out.println("Select blue component:");
        int b = getColorComponent();

        ResponseSetPixel response = null;
        try {
            response = connectionHandler.requestSetPixel(username, token, x, y, (byte) r, (byte) g, (byte) b);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        if (response == null) {
            return;
        }
        if (response.isRequestAccepted()) {
            System.out.println("Request was accepted");
        } else {
            System.out.println("Request failed");
        }
        System.out.println(response.getErrorMessage());
    }

    /**
     * Function responsible for getting proper values of color components
     *
     * @return value of a color component
     */
    public int getColorComponent() {
        Scanner sc = new Scanner(System.in);
        int component = sc.nextInt();
        while (component < 0 || component > 256) {
            System.out.println("Component value should be in range [0,255]");
            component = sc.nextInt();
        }
        return component;
    }


    /**
     * Asks users if they are registered
     * sets {@link UserInteractionHandler#isRegistered} accordingly
     *
     * @return true when user inputs that he is registered, false otherwise
     */
    private boolean askIfUserIsRegistered() {
        while (true) {
            System.out.println("Are you registered [Y/n]");
            Scanner sc = new Scanner(System.in);
            String userInput = sc.nextLine();
            if (userInput.isEmpty() || userInput.equals("Y") || userInput.equals("y")) {
                return true;
            } else if (userInput.equals("N") || userInput.equals("n")) {
                return false;
            } else {
                System.out.println("Not valid input");
            }
        }
    }

    /**
     * Compounded method for getting user login and password
     */
    private void getUserCredentials() {
        getUserName();
        getPassword();
    }

    /**
     * Gets user login from stdin
     * sets {@link UserInteractionHandler#username} accordingly
     */
    private void getUserName() {
        boolean shouldRepeat = true;
        while (shouldRepeat) {
            System.out.println("Please enter your login:");
            Scanner sc = new Scanner(System.in);
            this.username = sc.next();
            shouldRepeat = false;
            if (this.username.length() < 4) {
                shouldRepeat = true;
                System.out.println("Login is too short!");
            }
            if (this.username.contains(" ")) {
                shouldRepeat = true;
                System.out.println("Login should not contain a spaces");
            }
        }
    }

    /**
     * Gets user password from stdin
     * sets {@link UserInteractionHandler#password} accordingly
     */
    private void getPassword() {
        boolean shouldRepeat = true;
        while (shouldRepeat) {
            System.out.println("Please enter your password:");
            Scanner sc = new Scanner(System.in);
            // System.console().readPassword() doesnt work in IDE because
            // IDE's run code via javaw instead java so System.console() is always == null
            // So password will not be obscured
            this.password = sc.next();
            shouldRepeat = false;
            if (this.password.length() < 4) {
                shouldRepeat = true;
                System.out.println("Password is too short!");
            }
            if (this.password.contains(" ")) {
                shouldRepeat = true;
                System.out.println("Password should not contain a spaces");
            }
        }
    }


    /**
     * Print debug information
     */
    private void printDebugInfo() {
        try {
            System.out.println("Is registered:" + this.isRegistered);
            System.out.println("Login: " + this.username);
            System.out.println("Password:" + this.password);
            System.out.println("Token: " + this.token);
        } catch (NullPointerException ignored) {

        }
    }
}
