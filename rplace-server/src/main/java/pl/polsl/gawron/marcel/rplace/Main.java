package pl.polsl.gawron.marcel.rplace;

import pl.polsl.gawron.marcel.rplace.controllers.ImageController;
import pl.polsl.gawron.marcel.rplace.models.Color;
import pl.polsl.gawron.marcel.rplace.models.Image;
import pl.polsl.gawron.marcel.rplace.utils.ArgumentParser;

/**
 * Main class of a program
 *
 * @author Marcel Gawron
 * @version 1.0
 */
public class Main {
    public static void main(String[] args) {
        // Argument parser for program
        ArgumentParser argparse = new ArgumentParser(args);
        argparse.parse();
        argparse.printConfig();

        // Creates an Image
        ImageController bmpCtrl = new ImageController(new Image(100));
        for (int i = 0; i < 100; ++i) {
            bmpCtrl.setPixel(i, i, (byte) -128, (byte) -128, (byte) 0);
        }
        for (int i = 0; i < 100; ++i) {
            bmpCtrl.setPixel(99 - i, i, new Color(0, 0, 255));
        }
        bmpCtrl.saveAsFile("test");


    }
}
