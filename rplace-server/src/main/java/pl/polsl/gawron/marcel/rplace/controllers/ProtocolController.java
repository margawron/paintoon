package pl.polsl.gawron.marcel.rplace.controllers;

import pl.polsl.gawron.marcel.rplace.models.Image;
import pl.polsl.gawron.marcel.rplace.models.User;
import pl.polsl.gawron.marcel.rplace.views.ImageView;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;

/**
 * Server-side networking controller
 *
 * @author Marcel Gawron
 * @version 1.0
 */
public class ProtocolController {

    // Data
    private ImageView imageView;
    private Image image;
    private List<User> users;


    public ProtocolController() {
        this.image = new Image(100);
        this.imageView = new ImageView();
        imageView.fromByteArrayToBufferedImage(image);
        users = new ArrayList<>(20);
    }

    public void processRequest(BufferedReader input, PrintWriter output) throws IOException {
        String inputString = input.readLine();
        parseInput(inputString);
    }

    private void parseInput(String input) {
        String[] split = input.split(" ", 2);

    }

}
