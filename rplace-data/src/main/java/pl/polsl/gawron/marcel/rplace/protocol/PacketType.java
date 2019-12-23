package pl.polsl.gawron.marcel.rplace.protocol;

/**
 * Message type enum
 */
public enum PacketType {
    /**
     * Client-side protocol requests
     **/
    REQUEST_REGISTER,
    REQUEST_LOGIN,
    REQUEST_IMAGE_BYTE_ARRAY,
    REQUEST_SET_PIXEL,
    REQUEST_PIXEL_HISTORY,
    /**
     * Server-side protocol responses
     **/
    RESPONSE_REGISTER,
    RESPONSE_LOGIN,
    RESPONSE_IMAGE_BYTE_ARRAY,
    RESPONSE_SET_PIXEL,
    RESPONSE_PIXEL_HISTORY,
    /**
     * Unhandled message type
     **/
    INVALID_REQUEST
}
