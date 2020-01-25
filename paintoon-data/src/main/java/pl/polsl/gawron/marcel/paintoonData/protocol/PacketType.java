package pl.polsl.gawron.marcel.paintoonData.protocol;

/**
 * Message type enum
 *
 * @author Marcel Gawron
 * @version 1.0
 */
public enum PacketType {
    /**
     * Client requests
     **/
    REQUEST_REGISTER(0),
    REQUEST_LOGIN(1),
    REQUEST_IMAGE_BYTE_ARRAY(2),
    /**
     * No body required
     **/
    REQUEST_SET_PIXEL(3),
    REQUEST_PIXEL_HISTORY(4),
    /**
     * Server responses
     **/
    RESPONSE_REGISTER(100),
    RESPONSE_LOGIN(101),
    RESPONSE_IMAGE_BYTE_ARRAY(102),
    RESPONSE_SET_PIXEL(103),
    RESPONSE_PIXEL_HISTORY(104),
    /**
     * Unhandled message type
     * Sent from server
     **/
    INVALID_REQUEST(200);

    private int value;

    PacketType(int value) {
        this.value = value;
    }

    public static PacketType fromInteger(int value) {
        switch (value) {
            /** Client-side requests **/
            case 0:
                return REQUEST_REGISTER;
            case 1:
                return REQUEST_LOGIN;
            case 2:
                return REQUEST_IMAGE_BYTE_ARRAY;
            case 3:
                return REQUEST_SET_PIXEL;
            case 4:
                return REQUEST_PIXEL_HISTORY;
            /** Server-side responses **/
            case 100:
                return RESPONSE_REGISTER;
            case 101:
                return RESPONSE_LOGIN;
            case 102:
                return RESPONSE_IMAGE_BYTE_ARRAY;
            case 103:
                return RESPONSE_SET_PIXEL;
            case 104:
                return RESPONSE_PIXEL_HISTORY;
            /** Unhandled message type **/
            default:
                return INVALID_REQUEST;
        }
    }

    public int getValue() {
        return value;
    }
}
