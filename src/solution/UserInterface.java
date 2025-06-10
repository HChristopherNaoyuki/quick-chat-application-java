package solution;

import javax.swing.JFrame;

/**
 * Interface for GUI components to implement
 */
public interface UserInterface 
{
    void initializeComponents();
    void setupLayout();
    void setupListeners();
    void show();
    void hide();
    JFrame getFrame();
}