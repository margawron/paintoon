package pl.polsl.gawron.marcel.rplace.handlers;

import org.apache.commons.lang3.RandomStringUtils;
import pl.polsl.gawron.marcel.rplace.controllers.ProtocolController;
import pl.polsl.gawron.marcel.rplace.models.HistoryEntry;
import pl.polsl.gawron.marcel.rplace.models.User;
import pl.polsl.gawron.marcel.rplace.protocol.PacketType;
import pl.polsl.gawron.marcel.rplace.protocol.packets.requests.*;
import pl.polsl.gawron.marcel.rplace.protocol.packets.responses.*;
import pl.polsl.gawron.marcel.rplace.repositories.HistoryEntryRepository;
import pl.polsl.gawron.marcel.rplace.repositories.UserRepository;
import pl.polsl.gawron.marcel.rplace.utils.Constants;

import java.time.Instant;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

/**
 * Processes packet type and returns corresponding output
 *
 * @author Marcel Gawron
 * @version 1.0
 */
public class PacketHandler {
    /**
     * Default constructor
     */
    private UserRepository userRepository;
    private HistoryEntryRepository historyEntryRepository;
    private ProtocolController protocolController;
    public PacketHandler() {
        this.userRepository = new UserRepository();
    }

    public PacketHandler(ProtocolController controller){
        this.userRepository = new UserRepository();
        this.historyEntryRepository = new HistoryEntryRepository();
        this.protocolController = controller;
    }

    /**
     * Checks if packet is valid, and passes it to correct function
     * depending on packet type
     *
     * @param splittedInput packet split into packet code at index 0 and packet body at index 1
     * @return serialized packet to send to client
     */
    public String dispatchPacket(String[] splittedInput) {
        StringBuilder responseStringBuilder = new StringBuilder();
        if (!isInputLengthValid(responseStringBuilder, splittedInput.length)) {
            return responseStringBuilder.toString();
        }
        Integer packetCode = getPacketId(responseStringBuilder, splittedInput);
        if(packetCode == null){
            return responseStringBuilder.toString();
        }
        PacketType receivedPacketType = PacketType.fromInteger(packetCode);
        String packetBody = getPacketBody(splittedInput);
        if(packetBody == null){
            return handleInvalidRequest("No body");
        }
        switch (receivedPacketType){
            case REQUEST_REGISTER: return handleRegisterRequest(packetBody);
            case REQUEST_LOGIN: return handleLoginRequest( packetBody);
            case REQUEST_IMAGE_BYTE_ARRAY: return handleImageByteArrayRequest( packetBody);
            case REQUEST_SET_PIXEL: return handleSetPixelRequest( packetBody);
            case REQUEST_PIXEL_HISTORY: return handlePixelHistoryRequest(packetBody);
            default: return handleInvalidRequest("Invalid request");
        }
    }

    /**
     * Checks if packet is empty, if so outputs {@link PacketType#INVALID_REQUEST}
     * @param responseStringBuilder output StringBuilder
     * @param length length of input array
     * @return true if input array is not empty
     */
    private boolean isInputLengthValid(StringBuilder responseStringBuilder, int length) {
        if (length != 2) {
            responseStringBuilder.append(PacketType.INVALID_REQUEST.getValue());
            ResponseInvalidRequest invalidRequest = new ResponseInvalidRequest();
            invalidRequest.setMessage("Invalid packet");
            responseStringBuilder.append(" ");
            responseStringBuilder.append(invalidRequest.serialize());
            return false;
        }
        return true;
    }

    /**
     * Parses packet type code or outputs {@link PacketType#INVALID_REQUEST}
     * @param responseStringBuilder output StringBuilder
     * @param strings input array
     * @return packet code or null if packet code is invalid
     */
    private Integer getPacketId(StringBuilder responseStringBuilder, String[] strings){
        Integer packetCode = null;
        try{
            packetCode = Integer.parseInt(strings[0]);
        } catch (NumberFormatException e){
            responseStringBuilder.append(PacketType.INVALID_REQUEST.getValue());
            ResponseInvalidRequest invalidRequest = new ResponseInvalidRequest();
            invalidRequest.setMessage("Invalid packet code");
            responseStringBuilder.append(" ");
            responseStringBuilder.append(invalidRequest.serialize());
        }
        return packetCode;
    }

    /**
     * Selects string from array which contains packet body
     * @param strings input array of strings
     * @return packet body string
     */
    private String getPacketBody(String[] strings) {
        return strings[1];
    }

    /**
     * Handles register requests packet
     * @param packet string with packet body to parse
     * @return serialized packet to send to client
     */
    private String handleRegisterRequest(String packet){
        StringBuilder stringBuilder = new StringBuilder();

        System.out.println("Received register request");

        RequestRegister request = null;
        try {
            request = RequestRegister.deserialize(packet);
        } catch (Exception e){
            System.out.println("Failed to parse register request packet");
        }
        if(request == null){
            return handleInvalidRequest("Invalid packet body");
        }
        if(request.getName() == null || request.getPassword() == null){
            return handleInvalidRequest("Invalid packet body");
        }
        boolean wasAdded = userRepository.addUser(request.getName(), request.getPassword());

        ResponseRegister response = new ResponseRegister();
        response.setRegistrationSuccessful(wasAdded);

        stringBuilder.append(PacketType.RESPONSE_REGISTER);
        stringBuilder.append(" ");
        stringBuilder.append(response.serialize());

        return stringBuilder.toString();
    }

    private String handleLoginRequest(String packet){
        StringBuilder stringBuilder = new StringBuilder();

        System.out.println("Received login request");

        RequestLogin request = null;
        try {
            request = RequestLogin.deserialize(packet);
        } catch (Exception e){
            System.out.println("Failed to parse login request packet");
        }
        if(request == null){
            return handleInvalidRequest("Invalid packet body");
        }
        if(request.getName() == null || request.getPassword() == null){
            return handleInvalidRequest("Invalid packet body");
        }
        User user = userRepository.findUser(request.getName());
        if(user == null){
            return handleInvalidRequest("User with that name does not exist");
        }

        ResponseLogin response = new ResponseLogin();
        if(!user.getPassword().equals(request.getPassword())){
            return handleInvalidRequest("Invalid password");
        }
        String hash = RandomStringUtils.randomAlphanumeric(20);
        user.setHash(hash);
        response.setHash(hash);

        Instant now = Instant.now();
        LocalDateTime expiryDate = LocalDateTime.from(now);
        expiryDate.plusWeeks(1);
        user.setHashExpiryServerTime(expiryDate);
        response.setHashExpiryServerTime(expiryDate);

        stringBuilder.append(PacketType.RESPONSE_LOGIN.getValue());
        stringBuilder.append(" ");
        stringBuilder.append(response.serialize());
        return stringBuilder.toString();
    }

    private String handleImageByteArrayRequest( String packet){
        StringBuilder stringBuilder = new StringBuilder();

        System.out.println("Received image byte array request");

        RequestImageByteArray request = null;
        try{
            request = RequestImageByteArray.deserialize(packet);
        } catch (Exception e){
            System.out.println("Failed to parse image byte array request");
        }
        if(request == null){
            return handleInvalidRequest("Invalid packet body");
        }

        ResponseImageByteArray response = new ResponseImageByteArray();
        response.setBitmapByteArray(protocolController.getImage().getBitmap());
        response.setSize(protocolController.getImage().getSize());

        stringBuilder.append(PacketType.RESPONSE_IMAGE_BYTE_ARRAY.getValue());
        stringBuilder.append(" ");
        stringBuilder.append(response.serialize());
        return stringBuilder.toString();
    }

    private String handleSetPixelRequest(String packet){
        StringBuilder stringBuilder = new StringBuilder();

        System.out.println("Received set pixel request");

        RequestSetPixel request = null;
        try {
            request = RequestSetPixel.deserialize(packet);
        } catch (Exception e){
            System.out.println("Failed to parse set pixel request");
        }
        if(request == null){
            return handleInvalidRequest("Invalid packet body");
        }
        if(request.getxPos() == null || request.getyPos() == null || request.getColor() == null ||
                request.getUserHash() == null || request.getUserName() == null){
            return handleInvalidRequest("Invalid packet body");
        }
        User user = userRepository.findUser(request.getUserName());
        if(user == null){
            return handleInvalidRequest("User not found");
        }
        if(!user.getHash().equals(request.getUserHash())){
            return handleInvalidRequest("User not logged in");
        }
        Instant now = Instant.now();
        LocalDateTime currentDate = LocalDateTime.from(now);
        if(currentDate.isAfter(user.getHashExpiryServerTime())){
            return handleInvalidRequest("Hash has expired");
        }
        LocalDateTime timeOfLastModification = null;
        var userHistory = user.getUserPixelModificationHistory();

        ResponseSetPixel response = new ResponseSetPixel();

        if(!userHistory.isEmpty()){
            timeOfLastModification = userHistory.get(userHistory.size()-1).getTimeOfModification();
            if(!timeOfLastModification.plusMinutes(Constants.MINUTES_TO_WAIT_AFTER_SET_PIXEL).isAfter(currentDate)){
                response.setErrorMessage("You need to wait "+ Constants.MINUTES_TO_WAIT_AFTER_SET_PIXEL +" minutes after pixel change.");
                response.setRequestAccepted(false);
                return response.toString();
            }
        }
        protocolController.getImage().setPixel(request.getxPos(),request.getyPos(),request.getColor());
        HistoryEntry historyEntry = new HistoryEntry();
        historyEntry.setX(request.getxPos());
        historyEntry.setY(request.getyPos());
        historyEntry.setColor(request.getColor());
        historyEntry.setUserWhoModifiedPixel(user);
        historyEntry.setTimeOfModification(currentDate);
        user.getUserPixelModificationHistory().add(historyEntry);
        historyEntryRepository.addHistoryEntry(historyEntry);

        response.setErrorMessage("Pixel set");
        response.setRequestAccepted(true);

        stringBuilder.append(PacketType.RESPONSE_SET_PIXEL.getValue());
        stringBuilder.append(" ");
        stringBuilder.append(response.serialize());

        return stringBuilder.toString();
    }

    private String handlePixelHistoryRequest(String packet){
        StringBuilder stringBuilder = new StringBuilder();

        System.out.println("Received pixel history request");

        RequestPixelHistory request = null;
        try {
            request = RequestPixelHistory.deserialize(packet);
        } catch (Exception e){
            System.out.println("Failed to parse pixel history request");
        }
        if(request == null){
            return handleInvalidRequest("Invalid packet body");
        }
        if(request.getToYoungestPixelNumber() == null || request.getFromOldestPixelNumber() == null){
            return handleInvalidRequest("Invalid packet body");
        }
        if(request.getToYoungestPixelNumber() < 0 || request.getFromOldestPixelNumber() < 0){
            return handleInvalidRequest("Ranges cannot be negative");
        }
        if(request.getToYoungestPixelNumber()<=request.getFromOldestPixelNumber()){
            return handleInvalidRequest("You messed up the order of values. Oldest are 1,2,3 ... youngest are  ...n-2,n-1,n");
        }
        if(request.getFromOldestPixelNumber() - request.getToYoungestPixelNumber() > Constants.MAX_PIXEL_HISTORY_LENGTH){
            return handleInvalidRequest("To large range of pixel history");
        }
        ResponsePixelHistory response = new ResponsePixelHistory();
        List<HistoryEntry> historyList = new ArrayList<>();
        for(int i = request.getFromOldestPixelNumber(); i < request.getToYoungestPixelNumber(); i++){
            HistoryEntry historyEntry = historyEntryRepository.getHistoryEntry(i);
            if(historyEntry != null){
                historyList.add(historyEntry);
            }
        }
        response.setHistoryEntryList(historyList);

        stringBuilder.append(PacketType.RESPONSE_PIXEL_HISTORY.getValue());
        stringBuilder.append(" ");
        stringBuilder.append(response.serialize());

        return stringBuilder.toString();
    }

    private String handleInvalidRequest(String message){
        StringBuilder responseStringBuilder = new StringBuilder();

        System.out.println("Received invalid request");

        responseStringBuilder.append(PacketType.INVALID_REQUEST.getValue());
        responseStringBuilder.append(" ");

        ResponseInvalidRequest invalidRequest = new ResponseInvalidRequest();
        invalidRequest.setMessage(message);

        responseStringBuilder.append(invalidRequest.serialize());
        return responseStringBuilder.toString();
    }
}
