package pl.polsl.gawron.marcel.rplaceServer.controllers;

import org.springframework.http.MediaType;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import pl.polsl.gawron.marcel.rplaceData.models.Color;
import pl.polsl.gawron.marcel.rplaceData.models.Image;
import pl.polsl.gawron.marcel.rplaceData.models.User;
import pl.polsl.gawron.marcel.rplaceServer.models.Message;
import pl.polsl.gawron.marcel.rplaceServer.models.SetPixel;
import pl.polsl.gawron.marcel.rplaceServer.repositories.UserRepository;

import javax.imageio.ImageIO;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
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
    private UserRepository userRepository;
    // NEEDED
    // SentTo annotation is not enough to trigger
    // WebSocket refresh
    private SimpMessagingTemplate template;
    /**
     * Default controller
     *
     * @param image image model to be sent to client
     */
    public ImageController(Image image, UserRepository userRepository, SimpMessagingTemplate simpMessagingTemplate) {
        this.image = image;
        bufferedImage = new BufferedImage(image.getSize(), image.getSize(), BufferedImage.TYPE_3BYTE_BGR);
        this.userRepository = userRepository;
        this.template = simpMessagingTemplate;
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

    /**
     * Handles JS set pixel requests
     * @param payload JS sent JSON payload
     * @param request request sent from browser - user for cookies
     * @param response response to be sent to the client
     * @return model to be sent to the client (not used)
     * @throws Exception {@link HttpServletResponse#sendError} could throw
     */
    @RequestMapping(value = "/image/set_pixel", method = RequestMethod.POST)
    public String changePixelRequest(@RequestBody SetPixel payload,
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
        Message broadcastMessage = new Message();
        broadcastMessage.setUsername(user.getName());
        broadcastMessage.setX(payload.getX());
        broadcastMessage.setY(payload.getY());
        broadcastMessage.setRed(payload.getRed());
        broadcastMessage.setGreen(payload.getGreen());
        broadcastMessage.setBlue(payload.getBlue());
        fireWebSocketBroadcastEvent(broadcastMessage);
        return null;
    }

    /**
     * Send message by WebSocket that the pixel has changed
     *
     * @SendTo("/topic/broadcast") Not enough to send message automatically
     * (Not enough for Spring to know that it needs to send a message)
     *
     * @param message message to send
     */
    public void fireWebSocketBroadcastEvent(Message message){
       this.template.convertAndSend("/topic/broadcast", message);
    }
}
