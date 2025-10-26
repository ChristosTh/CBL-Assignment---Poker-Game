import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;
import javax.swing.*;

/** Class for the user interface. */
public class StartMenu {
    GameSetup setup;
    
    JFrame startMenu; 

    JButton goToSetup;
    JButton rules; // Do this after, when every necessary thing works. 

    /** Public Constructor for the UI. */
    public StartMenu() {
        // Start menu (the page the user is shown after opening the application): 
        startMenu = new JFrame("Start Menu");
        startMenu.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        startMenu.setSize(600, 600); 
        startMenu.setLayout(new BoxLayout(startMenu.getContentPane(), BoxLayout.Y_AXIS)); 

        goToSetup = new JButton("Setup Game");
        goToSetup.setSize(200, 75);  
        goToSetup.setLocation(185, 110); 

        rules = new JButton("Texas Holdem Rules"); 
        rules.setSize(200, 75); 
        rules.setLocation(185, 325); 
        rules.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                //File textFile = new File("Rules.txt"); 
                File pdfFile = new File("Poker Game Rules.pdf");
                try {
                    //Desktop.getDesktop().open(textFile); 
                    Desktop desktop = Desktop.getDesktop();
                    desktop.open(pdfFile);
                } catch (IOException ex) {
                    // Hopefully this never gets executed.
                }       
            }
        }); 

        startMenu.add(goToSetup); 
        startMenu.add(rules); 
        startMenu.setLayout(new BorderLayout());
        startMenu.setLocationRelativeTo(null);
        startMenu.setVisible(true); 

        ActionListener actions = new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                startMenu.setVisible(false); 
                startMenu = null; 
                setup = new GameSetup(); 
            }
            
        }; 
        goToSetup.addActionListener(actions); 


    }
}
