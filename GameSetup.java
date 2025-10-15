import java.awt.*;
import javax.swing.*;

/** Javadoc. */
public class GameSetup {
    JFrame gameSetup; 

    JLabel instructions;
    
    /** GameSetup page constructor. */
    public GameSetup() {
        gameSetup = new JFrame("Setup Game"); 
        gameSetup.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        gameSetup.setSize(600, 600); 
        gameSetup.setVisible(true); 
        gameSetup.setLocationRelativeTo(null);

        String instructionText = "To start your game," 
            + "please decide how much money you want to play with, "
            + "as well as what should the small and big blinds be.";
        instructions = new JLabel("<html><span style='font-size:15px'>" 
            + instructionText + "</span></html>"); 
        instructions.setLocation(20, 20); 
        instructions.setSize(550, 100);

        gameSetup.add(instructions); 
        gameSetup.setLayout(new BorderLayout());



    }
}