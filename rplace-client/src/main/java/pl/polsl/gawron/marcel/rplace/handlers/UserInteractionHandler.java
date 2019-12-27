package pl.polsl.gawron.marcel.rplace.handlers;

import pl.polsl.gawron.marcel.rplace.protocol.packets.requests.RequestRegister;
import pl.polsl.gawron.marcel.rplace.protocol.packets.responses.ResponseRegister;
import pl.polsl.gawron.marcel.rplace.utils.ArgumentParser;

import java.io.IOException;
import java.util.Map;
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
    private String username;
    private String password;
    private String token;

    /**
     * Default constructor
     * @param args arguments will be forwarded to ArgumentParser
     */
    public UserInteractionHandler(String[] args) {
        this.args = args;

    }

    /**
     * Runs main program loop
     */
    public void run() {
        int port = getServerPort();
        ConnectionHandler connectionHandler = new ConnectionHandler("localhost", port);
        connectionHandler.connect();
        askUserIfRegistered();
        if(!this.isRegistered){
            System.out.println("Please provide login and password for registration purposes");
            System.out.println("Then you will be automatically logged in");
        }
        getUserCredentials();
        while(!this.isRegistered){
            ResponseRegister response = null;
            try {
                response =  connectionHandler.requestRegister(username, password);
            } catch (Exception e){
                System.out.println(e.getMessage());
            }
            if(response == null) {
                return;
            }
            isRegistered = response.isRegistrationSuccessful();
            if(isRegistered = true){
                System.out.println("You are registered, now logging in ...");
            } else {
                System.out.println("Something went wrong, please enter your credentials again");
                getUserCredentials();
            }
        }
        printDebugInfo();
    }

    /**
     * Setups base for socket connection
     * @return port number to be used for socket to connect
     */
    private int getServerPort() {
        ArgumentParser argumentParser = new ArgumentParser(args);
        argumentParser.parse();
        argumentParser.printConfig();
        Map<String,String> config = argumentParser.getConfig();
        Integer port = null;
        try {
            port = Integer.parseInt(config.get("port"));
        } catch (NumberFormatException e){
            System.out.println(e.getMessage());
            System.exit(-1);
        }
        return port;
    }

    /**
     * Asks users if they are registered
     * sets {@link UserInteractionHandler#isRegistered} accordingly
     */
    private void askUserIfRegistered(){
        boolean shouldRepeat = true;
        while (shouldRepeat) {
            System.out.println("Are you registered [Y/n]");
            Scanner sc = new Scanner(System.in);
            String userInput = sc.nextLine();
            if (userInput.isEmpty() || userInput.equals("Y") || userInput.equals("y")) {
                this.isRegistered = true;
                shouldRepeat = false;
            } else if (userInput.equals("N") || userInput.equals("n")) {
                this.isRegistered = false;
                shouldRepeat = false;
            } else {
                shouldRepeat = true;
                System.out.println("Not valid input");
            }
        }
    }

    /**
     * Compounded method for getting user login and password
     */
    private void getUserCredentials(){
        getUserName();
        getPassword();
    }

    /**
     * Gets user login from stdin
     * sets {@link UserInteractionHandler#username} accordingly
     */
    private void getUserName(){
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
            if(this.username.contains(" ")){
                shouldRepeat = true;
                System.out.println("Login should not contain a spaces");
            }
        }
    }

    /**
     * Gets user password from stdin
     * sets {@link UserInteractionHandler#password} accordingly
     */
    private void getPassword(){
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
            if(this.password.contains(" ")){
                shouldRepeat = true;
                System.out.println("Password should not contain a spaces");
            }
        }
    }

    private void tryToRegister(){

    }



    private void printDebugInfo(){
        try {
            System.out.println("Is registered:" + this.isRegistered);
            System.out.println("Login: " + this.username);
            System.out.println("Password:" + this.password);
            System.out.println("Token: " + this.token);
        } catch (NullPointerException ignored){

        }
    }
}
