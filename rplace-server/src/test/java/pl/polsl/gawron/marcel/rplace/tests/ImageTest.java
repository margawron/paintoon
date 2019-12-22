package pl.polsl.gawron.marcel.rplace.tests;

import org.junit.jupiter.api.Test;
import pl.polsl.gawron.marcel.rplace.models.Color;
import pl.polsl.gawron.marcel.rplace.models.Image;

import java.util.Random;

import static org.junit.jupiter.api.Assertions.*;

public class ImageTest {

    @Test
    public void testInvalidImageSize() {
        // Given
        int negativeSize = -100;
        // When + Then

        assertThrows(NegativeArraySizeException.class, () -> {
            Image image = new Image(negativeSize);
        }, "Should throw NegativeArraySizeException");
    }

    @Test
    public void checkBitmapByteArraySize() {
        // Given
        int maxImageSize = 5000;
        Random rand = new Random();
        int size = Math.abs(rand.nextInt(maxImageSize));
        // When
        Image image = new Image(size);
        // Then
        assertEquals(size * size * 3, image.getBitmap().length, "Bitmap byte array should be the same");

    }

    @Test
    public void checkSetPixelFunction() {
        // Given
        Image image = new Image(100);
        Random rand = new Random();
        int howMuchPixelsShouldBeChecked = 100;
        for (int repeats = 0; repeats < howMuchPixelsShouldBeChecked; ++repeats) {
            // When
            int xPos = Math.abs(rand.nextInt(100));
            int yPos = Math.abs(rand.nextInt(100));
            byte[] rgb = new byte[3];
            rand.nextBytes(rgb);
            Color color = new Color(rgb[0], rgb[1], rgb[2]);
            image.setPixel(xPos, yPos, color);
            Color received = image.getPixelColor(xPos, yPos);
            // Then
            assertEquals(color.getBlue(), received.getBlue(), "Blue colors should be the same");
            assertEquals(color.getGreen(), received.getGreen(), "Green colors should be the same");
            assertEquals(color.getRed(), received.getRed(), "Red colors should be the same");
        }
    }
}
