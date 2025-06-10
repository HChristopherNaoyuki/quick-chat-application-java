package solution;

/**
 * Enumeration representing different statuses a message can have
 */
public enum MessageStatus 
{
    PENDING("Pending"),
    SENT("Sent"),
    RECEIVED("Received"),
    READ("Read"),
    FAILED("Failed");

    private final String displayName;

    MessageStatus(String displayName) 
    {
        this.displayName = displayName;
    }

    @Override
    public String toString() 
    {
        return displayName;
    }
}