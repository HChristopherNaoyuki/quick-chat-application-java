package solution;

import javax.swing.SwingUtilities;

/**
 * Entry point for the QuickChat application
 */
public class Solution 
{
    /**
     * Main method to launch the application
     * @param args Command line arguments (not used)
     */
    public static void main(String[] args) 
    {
        // Create and show the GUI on the Event Dispatch Thread
        SwingUtilities.invokeLater(() -> 
        {
            ChatApplication app = new ChatApplication();
            app.show();
        });
    }
}