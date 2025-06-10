package solution;

import java.time.LocalDateTime;
import java.util.Random;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 * Represents a chat message with validation, status tracking,
 * and serialization capabilities
 */
public class Message 
{
    private final String messageId;
    private static int messageCounter = 0;
    private static int totalMessagesSent = 0;
    
    private final String recipientPhoneNumber;
    private final String senderPhoneNumber;
    private final String messagePayload;
    private final LocalDateTime timestamp;
    
    private MessageStatus status;
    
    /**
     * Constructs a new message with automatic timestamp and ID generation
     * @param messagePayload Text content of message (≤250 chars)
     * @param recipientPhoneNumber Receiver's number (+27XXXXXXXXX)
     * @param senderPhoneNumber Sender's number (+27XXXXXXXXX)
     */
    public Message(String messagePayload, String recipientPhoneNumber, 
                  String senderPhoneNumber) 
    {
        this.messagePayload = messagePayload;
        this.recipientPhoneNumber = recipientPhoneNumber;
        this.senderPhoneNumber = senderPhoneNumber;
        this.timestamp = LocalDateTime.now();
        this.messageId = generateMessageId();
        this.status = MessageStatus.PENDING;
        messageCounter++;
    }
    
    /**
     * Generates unique 10-digit message ID
     * @return Randomly generated ID string
     */
    private String generateMessageId() 
    {
        Random random = new Random();
        return String.format("%010d", random.nextInt(1_000_000_000));
    }
    
    /**
     * Validates message length constraint
     * @return true if message ≤250 characters
     */
    public boolean checkMessageLength() 
    {
        return messagePayload != null && messagePayload.length() <= 250;
    }
    
    /**
     * Validates international phone number format
     * @return true if number starts with + and has 10-15 digits
     */
    public boolean checkRecipientCell() 
    {
        return recipientPhoneNumber != null && 
               recipientPhoneNumber.matches("^\\+\\d{10,15}$");
    }
    
    /**
     * Creates unique message hash for identification
     * @return Combination of ID, counter, and message words
     */
    public String createMessageHash() 
    {
        String[] words = messagePayload.split("\\s+");
        String firstWord = words.length > 0 ? words[0] : "";
        String lastWord = words.length > 1 ? words[words.length-1] : firstWord;
        
        return String.format("%s:%d:%s%s", 
                           messageId.substring(0, 2),
                           messageCounter,
                           firstWord.toUpperCase(),
                           lastWord.toUpperCase());
    }
    
    /**
     * Processes message based on user action
     * @param action 1=Send, 2=Disregard, 3=Store
     * @return Status message describing result
     */
    public String processMessage(int action) 
    {
        switch (action) 
        {
            case 1:
                this.status = MessageStatus.SENT;
                totalMessagesSent++;
                return "Message sent successfully";
                
            case 2:
                return "Message disregarded";
                
            case 3:
                this.status = MessageStatus.PENDING;
                return "Message stored for later";
                
            default:
                return "Invalid action specified";
        }
    }
    
    /**
     * Generates detailed message information string
     * @return Formatted multi-line message details
     */
    public String getMessageDetails() 
    {
        return String.format(
            "Message ID: %s\nHash: %s\nFrom: %s\nTo: %s\nContent: %s\nStatus: %s\nTime: %s",
            messageId, 
            createMessageHash(), 
            senderPhoneNumber,
            recipientPhoneNumber,
            messagePayload,
            status,
            timestamp.toString()
        );
    }
    
    /**
     * Converts message to Map for serialization
     * @return Map containing all message properties
     */
    public Map<String, Object> toMap() 
    {
        Map<String, Object> map = new LinkedHashMap<>();
        map.put("messageId", messageId);
        map.put("messageHash", createMessageHash());
        map.put("recipient", recipientPhoneNumber);
        map.put("sender", senderPhoneNumber);
        map.put("message", messagePayload);
        map.put("timestamp", timestamp.toString());
        map.put("status", status.toString());
        return map;
    }
    
    /**
     * Serializes message to JSON format string
     * @return Valid JSON representation
     */
    public String toJsonString() 
    {
        StringBuilder json = new StringBuilder("{");
        boolean first = true;
        
        for (Map.Entry<String, Object> entry : toMap().entrySet()) 
        {
            if (!first) 
            {
                json.append(",");
            }
            
            json.append("\"").append(entry.getKey()).append("\":");
            Object value = entry.getValue();
            
            if (value instanceof String) 
            {
                json.append("\"").append(escapeJson((String) value)).append("\"");
            } 
            else 
            {
                json.append(value);
            }
            
            first = false;
        }
        
        json.append("}");
        return json.toString();
    }
    
    /**
     * Escapes special JSON characters in strings
     * @param input Original string
     * @return JSON-safe escaped string
     */
    private String escapeJson(String input) 
    {
        return input.replace("\\", "\\\\")
                   .replace("\"", "\\\"")
                   .replace("\b", "\\b")
                   .replace("\f", "\\f")
                   .replace("\n", "\\n")
                   .replace("\r", "\\r")
                   .replace("\t", "\\t");
    }
    
    // Static counter accessor
    public static int getTotalMessagesSent() 
    { 
        return totalMessagesSent; 
    }
    
    // Standard getters
    public String getMessageId() 
    { 
        return messageId; 
    }
    
    public String getRecipientPhoneNumber() 
    { 
        return recipientPhoneNumber; 
    }
    
    public String getSenderPhoneNumber() 
    { 
        return senderPhoneNumber; 
    }
    
    public String getMessagePayload() 
    { 
        return messagePayload; 
    }
    
    public LocalDateTime getTimestamp() 
    { 
        return timestamp; 
    }
    
    public MessageStatus getStatus() 
    { 
        return status; 
    }
}