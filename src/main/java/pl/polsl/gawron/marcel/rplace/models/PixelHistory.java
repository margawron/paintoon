package pl.polsl.gawron.marcel.rplace.models;

import pl.polsl.gawron.marcel.rplace.utils.Constants;

import java.util.Calendar;
import java.util.TimeZone;

/**
 * Class representing a single pixel history
 *
 * @author Marcel Gawron
 * @version 1.0
 */
public class PixelHistory {
    private int x;
    private int y;
    private Color color;
    private User currentlyModifiedBy;
    private Calendar timeOfLastModification;

    /**
     * Default constructor
     *
     */
    public PixelHistory() {}


}
