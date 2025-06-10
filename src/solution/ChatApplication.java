package solution;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.FileWriter;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.List;
import java.util.ArrayList;

/**
 * Main application controller implementing the UserInterface
 * Manages user authentication, messaging, and UI components
 * Follows Allman style formatting with detailed comments
 */
public final class ChatApplication implements UserInterface 
{
    // Data storage collections
    private final Map<String, Login> users;
    private final Map<String, List<Message>> messages;
    private Login currentUser;
    
    // Main UI components
    private JFrame mainFrame;
    private JPanel mainPanel;
    private JPanel loginPanel;
    private JPanel chatPanel;
    
    // Login panel components
    private JTextField usernameField;
    private JPasswordField passwordField;
    private JButton loginButton;
    private JButton registerButton;
    private JLabel statusLabel;
    
    // Chat panel components
    private JTextArea chatArea;
    private JTextField messageField;
    private JButton sendButton;
    private JComboBox<String> recipientComboBox;

    /**
     * Constructor initializes data structures and UI
     * Sets up the application environment
     */
    public ChatApplication() 
    {
        // Initialize data storage
        this.users = new HashMap<>();
        this.messages = new HashMap<>();
        this.currentUser = null;
        
        // Setup demo user for testing
        initializeDemoUser();
        
        // Initialize UI components
        initializeComponents();
        setupLayout();
        setupListeners();
    }

    /**
     * Initializes all UI components required for the application
     * Creates and configures each visual element
     */
    @Override
    public void initializeComponents() 
    {
        // Main window frame
        mainFrame = new JFrame("QuickChat Application");
        
        // Main panel with CardLayout for view switching
        mainPanel = new JPanel(new CardLayout());
        
        // Initialize login panel components
        initializeLoginPanel();
        
        // Initialize chat panel components
        initializeChatPanel();
        
        // Add both panels to main panel
        mainPanel.add(loginPanel, "login");
        mainPanel.add(chatPanel, "chat");
    }

    /**
     * Initializes and configures the login panel components
     * Uses GridBagLayout for precise component placement
     */
    private void initializeLoginPanel() 
    {
        loginPanel = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);
        gbc.anchor = GridBagConstraints.WEST;
        
        // Username components
        gbc.gridx = 0;
        gbc.gridy = 0;
        loginPanel.add(new JLabel("Username:"), gbc);
        
        gbc.gridx = 1;
        usernameField = new JTextField(20);
        loginPanel.add(usernameField, gbc);
        
        // Password components
        gbc.gridx = 0;
        gbc.gridy = 1;
        loginPanel.add(new JLabel("Password:"), gbc);
        
        gbc.gridx = 1;
        passwordField = new JPasswordField(20);
        loginPanel.add(passwordField, gbc);
        
        // Button panel
        gbc.gridx = 0;
        gbc.gridy = 2;
        gbc.gridwidth = 2;
        gbc.fill = GridBagConstraints.CENTER;
        
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 5));
        loginButton = new JButton("Login");
        registerButton = new JButton("Register");
        buttonPanel.add(loginButton);
        buttonPanel.add(registerButton);
        loginPanel.add(buttonPanel, gbc);
        
        // Status label
        gbc.gridy = 3;
        statusLabel = new JLabel(" ");
        statusLabel.setForeground(Color.RED);
        loginPanel.add(statusLabel, gbc);
    }

    /**
     * Initializes and configures the chat panel components
     * Uses BorderLayout for main chat area layout
     */
    private void initializeChatPanel() 
    {
        chatPanel = new JPanel(new BorderLayout(5, 5));
        
        // Chat display area
        chatArea = new JTextArea(15, 40);
        chatArea.setEditable(false);
        chatArea.setLineWrap(true);
        chatArea.setWrapStyleWord(true);
        JScrollPane scrollPane = new JScrollPane(chatArea);
        
        // Recipient selection
        JPanel recipientPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        recipientPanel.add(new JLabel("To: "));
        recipientComboBox = new JComboBox<>();
        recipientPanel.add(recipientComboBox);
        
        // Message input panel
        JPanel messagePanel = new JPanel(new BorderLayout(5, 5));
        messageField = new JTextField();
        sendButton = new JButton("Send");
        
        messagePanel.add(messageField, BorderLayout.CENTER);
        messagePanel.add(sendButton, BorderLayout.EAST);
        
        // Add components to chat panel
        chatPanel.add(recipientPanel, BorderLayout.NORTH);
        chatPanel.add(scrollPane, BorderLayout.CENTER);
        chatPanel.add(messagePanel, BorderLayout.SOUTH);
    }

    /**
     * Sets up the main application layout and window properties
     */
    @Override
    public void setupLayout() 
    {
        mainFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        mainFrame.setContentPane(mainPanel);
        mainFrame.pack();
        mainFrame.setLocationRelativeTo(null); // Center on screen
        mainFrame.setMinimumSize(new Dimension(500, 400));
    }

    /**
     * Configures all event listeners for UI components
     */
    @Override
    public void setupListeners() 
    {
        // Login button action
        loginButton.addActionListener((ActionEvent e) -> 
        {
            String username = usernameField.getText().trim();
            String password = new String(passwordField.getPassword()).trim();
            
            if (username.isEmpty() || password.isEmpty()) 
            {
                statusLabel.setText("Please enter both username and password");
                return;
            }
            
            if (login(username, password)) 
            {
                showChatPanel();
                updateChatArea();
                updateRecipientList();
            }
        });
        
        // Register button action
        registerButton.addActionListener((ActionEvent e) -> 
        {
            String username = usernameField.getText().trim();
            String password = new String(passwordField.getPassword()).trim();
            
            if (username.isEmpty() || password.isEmpty()) 
            {
                statusLabel.setText("Please enter both username and password");
                return;
            }
            
            // Using default values for demo
            if (registerUser(username, password, "New", "User", "+27820000000")) 
            {
                statusLabel.setText("Registration successful! Please login.");
                usernameField.setText("");
                passwordField.setText("");
            }
        });
        
        // Send button action
        sendButton.addActionListener((ActionEvent e) -> 
        {
            String message = messageField.getText().trim();
            String recipient = (String) recipientComboBox.getSelectedItem();
            
            if (message.isEmpty()) 
            {
                showErrorDialog("Message cannot be empty");
                return;
            }
            
            if (recipient == null) 
            {
                showErrorDialog("Please select a recipient");
                return;
            }
            
            sendMessage(recipient, message);
            messageField.setText("");
            updateChatArea();
        });
        
        // Enter key in message field
        messageField.addActionListener((ActionEvent e) -> 
        {
            sendButton.doClick();
        });
    }

    /**
     * Displays the main application window
     */
    @Override
    public void show() 
    {
        mainFrame.setVisible(true);
    }

    /**
     * Hides the main application window
     */
    @Override
    public void hide() 
    {
        mainFrame.setVisible(false);
    }

    /**
     * Gets the main application frame
     * @return The main JFrame instance
     */
    @Override
    public JFrame getFrame() 
    {
        return mainFrame;
    }
    
    /**
     * Switches to the chat panel view
     */
    private void showChatPanel() 
    {
        CardLayout cl = (CardLayout)(mainPanel.getLayout());
        cl.show(mainPanel, "chat");
        mainFrame.setTitle("QuickChat - " + currentUser.getUsername());
    }
    
    /**
     * Updates the chat area with recent messages
     */
    private void updateChatArea() 
    {
        chatArea.setText("");
        List<Message> recentMessages = getRecentMessages();
        
        if (recentMessages.isEmpty()) 
        {
            chatArea.append("No messages yet. Start chatting!\n");
            return;
        }
        
        for (Message msg : recentMessages) 
        {
            String sender = users.get(msg.getSenderPhoneNumber()).getUsername();
            chatArea.append(sender + ": " + msg.getMessagePayload() + "\n");
        }
    }
    
    /**
     * Updates the recipient dropdown list with registered users
     */
    private void updateRecipientList() 
    {
        recipientComboBox.removeAllItems();
        
        for (Map.Entry<String, Login> entry : users.entrySet()) 
        {
            if (!entry.getKey().equals(currentUser.getCellPhoneNumber())) 
            {
                recipientComboBox.addItem(entry.getKey());
            }
        }
    }
    
    /**
     * Initializes a demo user for testing purposes
     */
    private void initializeDemoUser() 
    {
        Login demoUser = new Login("admin", "Pass123!", "Demo", "User", "+27821234567");
        users.put(demoUser.getCellPhoneNumber(), demoUser);
        messages.put(demoUser.getCellPhoneNumber(), new ArrayList<>());
    }
    
    /**
     * Displays informational dialog
     * @param message Content to display
     */
    private void showInfoDialog(String message) 
    {
        JOptionPane.showMessageDialog(mainFrame, message, "Information", 
                                    JOptionPane.INFORMATION_MESSAGE);
    }
    
    /**
     * Displays error dialog
     * @param message Error content
     */
    private void showErrorDialog(String message) 
    {
        JOptionPane.showMessageDialog(mainFrame, message, "Error", 
                                    JOptionPane.ERROR_MESSAGE);
    }
    
    /**
     * Registers new user after validation
     * @param username Unique user identifier
     * @param password Secret phrase
     * @param firstName Given name
     * @param lastName Family name
     * @param cellPhoneNumber Contact number
     * @return true if registration successful
     */
    public boolean registerUser(String username, String password, 
                              String firstName, String lastName, 
                              String cellPhoneNumber) 
    {
        Login newUser = new Login(username, password, firstName, lastName, cellPhoneNumber);
        String status = newUser.registerUser();
        
        if (!status.equals("Registration successful!")) 
        {
            showErrorDialog(status);
            return false;
        }
        
        users.put(cellPhoneNumber, newUser);
        messages.put(cellPhoneNumber, new ArrayList<>());
        return true;
    }
    
    /**
     * Authenticates user credentials
     * @param username Attempted username
     * @param password Attempted password
     * @return true if authentication successful
     */
    public boolean login(String username, String password) 
    {
        for (Login user : users.values()) 
        {
            if (user.getUsername().equals(username)) 
            {
                boolean success = user.loginUser(username, password);
                if (success) 
                {
                    currentUser = user;
                    return true;
                }
            }
        }
        
        statusLabel.setText("Invalid username or password");
        return false;
    }
    
    /**
     * Sends message with full validation
     * @param recipientNumber Receiver's phone number
     * @param messageText Content to send
     */
    public void sendMessage(String recipientNumber, String messageText) 
    {
        Message message = new Message(messageText, recipientNumber, 
                                   currentUser.getCellPhoneNumber());
        
        if (!message.checkMessageLength()) 
        {
            showErrorDialog("Message exceeds 250 character limit");
            return;
        }
        
        if (!message.checkRecipientCell()) 
        {
            showErrorDialog("Invalid recipient number format");
            return;
        }
        
        if (!users.containsKey(recipientNumber)) 
        {
            showErrorDialog("Recipient not registered");
            return;
        }
        
        String result = message.processMessage(1); // Send action
        showInfoDialog(result);
        
        // Store message for both parties
        storeMessage(currentUser.getCellPhoneNumber(), message);
        storeMessage(recipientNumber, message);
        
        // Persist to file
        persistMessage(message);
    }
    
    /**
     * Stores message in recipient's inbox
     * @param phoneNumber Recipient's identifier
     * @param message Message to store
     */
    private void storeMessage(String phoneNumber, Message message) 
    {
        messages.computeIfAbsent(phoneNumber, k -> new ArrayList<>())
               .add(message);
    }
    
    /**
     * Writes message to persistent storage
     * @param message Message to persist
     */
    private void persistMessage(Message message) 
    {
        try (FileWriter writer = new FileWriter("messages.json", true)) 
        {
            writer.write(message.toJsonString() + "\n");
        } 
        catch (IOException e) 
        {
            showErrorDialog("Failed to save message: " + e.getMessage());
        }
    }
    
    /**
     * Retrieves recent messages for current user
     * @return List of messages (empty if none)
     */
    public List<Message> getRecentMessages() 
    {
        if (currentUser == null) 
        {
            return new ArrayList<>();
        }
        return messages.getOrDefault(currentUser.getCellPhoneNumber(), new ArrayList<>());
    }
    
    /**
     * Gets the current logged in user
     * @return Login object of current user
     */
    public Login getCurrentUser() 
    {
        return currentUser;
    }
    
    /**
     * Logs out the current user
     */
    public void logout() 
    {
        currentUser = null;
        CardLayout cl = (CardLayout)(mainPanel.getLayout());
        cl.show(mainPanel, "login");
        usernameField.setText("");
        passwordField.setText("");
        statusLabel.setText("Logged out successfully");
    }
}