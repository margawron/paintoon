package pl.polsl.gawron.marcel.rplaceServer.controllers;

import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import pl.polsl.gawron.marcel.rplaceData.models.Image;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.awt.image.DataBufferByte;
import java.awt.image.Raster;
import java.io.ByteArrayOutputStream;
import java.io.IOException;

/**
 * Controller responsible for image response
 *
 * @author Marcel Gawron
 * @version 1.0
 */
@Controller
public class ImageController {

    private Image image;
    private BufferedImage bufferedImage;

    /**
     * Default controller
     *
     * @param image image model to be sent to client
     */
    public ImageController(Image image) {
        this.image = image;
        bufferedImage = new BufferedImage(image.getSize(), image.getSize(), BufferedImage.TYPE_3BYTE_BGR);
    }

    /**
     * Function responsible for responding with image file
     *
     * @return byte array with image
     * @throws IOException thrown when ImageIO cant write to ByteArrayOutputStream
     */
    @RequestMapping(value = "/image", method = RequestMethod.GET, produces = MediaType.IMAGE_PNG_VALUE)
    public @ResponseBody
    byte[] getImage() throws IOException {
        image.getBitmap();
        bufferedImage.setData(Raster.createRaster(bufferedImage.getSampleModel(), new DataBufferByte(image.getBitmap(), 3 * image.getSize() * image.getSize()), new Point()));
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        ImageIO.write(bufferedImage, "png", byteArrayOutputStream);
        return byteArrayOutputStream.toByteArray();
    }

}
