package pl.polsl.gawron.marcel.rplace.protocol.packets.requests;

import com.google.gson.Gson;
import pl.polsl.gawron.marcel.rplace.models.Color;
import pl.polsl.gawron.marcel.rplace.protocol.Serializable;

/**
 * Representation of set pixel request packet
 *
 * @author Marcel Gawron
 * @version 1.0
 */
public class RequestSetPixel implements Serializable {
    private int xPos;
    private int yPos;
    private Color color;
    private String userHash;

    /**
     * Getter for horizontal position
     *
     * @return horizontal position
     */
    public int getxPos() {
        return xPos;
    }

    /**
     * Setter for horizontal position
     *
     * @param xPos horizontal position
     */
    public void setxPos(int xPos) {
        this.xPos = xPos;
    }

    /**
     * Getter for vertical position
     *
     * @return vertical position
     */
    public int getyPos() {
        return yPos;
    }

    /**
     * Setter for vertical position
     *
     * @param yPos vertical position
     */
    public void setyPos(int yPos) {
        this.yPos = yPos;
    }

    /**
     * Getter for color of a pixel
     *
     * @return pixel color
     */
    public Color getColor() {
        return color;
    }

    /**
     * Setter for color of a pixel
     *
     * @param color new pixel color
     */
    public void setColor(Color color) {
        this.color = color;
    }

    /**
     * Getter for user hash
     *
     * @return user hash code
     */
    public String getUserHash() {
        return userHash;
    }

    /**
     * Setter for user hash
     *
     * @param userHash new user hash code
     */
    public void setUserHash(String userHash) {
        this.userHash = userHash;
    }

    /**
     * Serializes class object to JSON
     *
     * @return JSON class representation
     */
    @Override
    public String serialize() {
        Gson gson = new Gson();
        return gson.toJson(this, RequestSetPixel.class);
    }

    /**
     * Deserializes class object from JSON
     *
     * @param json JSON class representation
     * @return class instance
     */
    public static RequestSetPixel deserialize(String json) throws Exception {
        Gson gson = new Gson();
        return gson.fromJson(json, RequestSetPixel.class);
    }
}
