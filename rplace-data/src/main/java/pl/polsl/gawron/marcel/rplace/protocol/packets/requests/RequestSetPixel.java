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
    private Integer xPos;
    private Integer yPos;
    private Color color;
    private String userName;
    private String userToken;

    /**
     * Deserializes class object from JSON
     *
     * @param json JSON class representation
     * @return class instance
     */
    public static RequestSetPixel deserialize(String json) {
        Gson gson = new Gson();
        return gson.fromJson(json, RequestSetPixel.class);
    }

    /**
     * Getter for horizontal position
     *
     * @return horizontal position
     */
    public Integer getxPos() {
        return xPos;
    }

    /**
     * Setter for horizontal position
     *
     * @param xPos horizontal position
     */
    public void setxPos(Integer xPos) {
        this.xPos = xPos;
    }

    /**
     * Getter for vertical position
     *
     * @return vertical position
     */
    public Integer getyPos() {
        return yPos;
    }

    /**
     * Setter for vertical position
     *
     * @param yPos vertical position
     */
    public void setyPos(Integer yPos) {
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
     * Getter for user name
     *
     * @return user name
     */
    public String getUserName() {
        return userName;
    }

    /**
     * Setter for user name
     *
     * @param userName new user name
     */
    public void setUserName(String userName) {
        this.userName = userName;
    }

    /**
     * Getter for user hash
     *
     * @return user hash code
     */
    public String getUserToken() {
        return userToken;
    }

    /**
     * Setter for user hash
     *
     * @param userToken new user hash code
     */
    public void setUserToken(String userToken) {
        this.userToken = userToken;
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
}
