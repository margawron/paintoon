package pl.polsl.gawron.marcel.rplaceServer.controllers;

import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import pl.polsl.gawron.marcel.rplaceData.models.Color;
import pl.polsl.gawron.marcel.rplaceData.models.Image;
import pl.polsl.gawron.marcel.rplaceData.models.User;
import pl.polsl.gawron.marcel.rplaceServer.models.SetPixelModel;
import pl.polsl.gawron.marcel.rplaceServer.repositories.UserRepository;

import javax.imageio.ImageIO;
import javax.servlet.ServletRegistration;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.awt.image.DataBufferByte;
import java.awt.image.Raster;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.Set;

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
    private UserRepository userRepository;

    /**
     * Default controller
     *
     * @param image image model to be sent to client
     */
    public ImageController(Image image, UserRepository userRepository) {
        this.image = image;
        bufferedImage = new BufferedImage(image.getSize(), image.getSize(), BufferedImage.TYPE_3BYTE_BGR);
        this.userRepository = userRepository;
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

    @RequestMapping(value = "/image/set_pixel", method = RequestMethod.POST)
    public String changePixelRequest(@RequestBody SetPixelModel payload,
                                     HttpServletRequest request,
                                     HttpServletResponse response) throws Exception {
        // Get necessary cookies
        Cookie[] cookies = request.getCookies();
        String username = null;
        String token = null;
        for (var cookie : cookies) {
            if (cookie.getName().equals("username")) {
                username = cookie.getValue();
            }
            if (cookie.getName().equals("token")) {
                token = cookie.getValue();
            }
        }
        if (username == null || token == null) {
           response.sendError(403);
           return null;
        }
        User user = userRepository.findUser(username);
        if(user == null || !user.isTokenValid(token)){
            response.sendError(403);
            return null;
        }
        // TODO check if user placed a pixel in the last X minutes
        if(payload.getX() > image.getSize() && payload.getY() > image.getSize())
        {
            response.sendError(403);
            return null;
        }
        image.setPixel(payload.getX(), payload.getY(), new Color(payload.getRed(),payload.getGreen(),payload.getBlue()));
        response.setStatus(200);
        return null;

    }
}
