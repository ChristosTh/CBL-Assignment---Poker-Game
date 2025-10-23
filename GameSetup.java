import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import javax.swing.*;

/** Javadoc. */
public class GameSetup {
    static PokerMat mat; 

    JFrame gameSetup; 

    JLabel instructions;
    JLabel moneyInstructions; 
    JLabel sBlindInstructions; 
    JLabel bBlindInstructions; 

    JTextField moneyAmount;
    JTextField sBlindAmount;
    JTextField bBlindAmount; 

    JButton finishSetup; 

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
        instructions.setFocusable(true); 

        String moneyInstructionText = "Amount of money you want to play with: ";
        moneyInstructions = new JLabel("<html><span style='font-size:9.5px'>" 
            + moneyInstructionText + "</span></html>"); 
        moneyInstructions.setLocation(60, 130); 
        moneyInstructions.setSize(150, 30); 

        moneyAmount = new JTextField("€€€");
        moneyAmount.setForeground(Color.gray);
        moneyAmount.setLocation(100, 165); 
        moneyAmount.setSize(40, 20); 
        moneyAmount.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if (moneyAmount.getText().equals("€€€")) {
                    moneyAmount.setForeground(Color.BLACK);
                    moneyAmount.setText(""); 
                }
            }
        });

        String sBlindText = "Small blind amount: "; 
        sBlindInstructions = new JLabel("<html><span style='font-size:9.5px'>" 
            + sBlindText + "</span></html>"); 
        sBlindInstructions.setLocation(60, 265); 
        sBlindInstructions.setSize(150, 30); 

        sBlindAmount = new JTextField("Small blind");
        sBlindAmount.setForeground(Color.gray); 
        sBlindAmount.setLocation(100, 295); 
        sBlindAmount.setSize(70, 20); 
        sBlindAmount.addMouseListener(new MouseAdapter() {
            public void mouseClicked(MouseEvent e) {
                if (sBlindAmount.getText().equals("Small blind")) {
                    sBlindAmount.setText("");
                    sBlindAmount.setForeground(Color.BLACK); 
                }
            }
        });

        String bBlindText = "Big blind amount: "; 
        bBlindInstructions = new JLabel("<html><span style='font-size:9.5px'>" 
            + bBlindText + "</span></html>"); 
        bBlindInstructions.setLocation(60, 395); 
        bBlindInstructions.setSize(150, 30); 

        bBlindAmount = new JTextField("Big blind");
        bBlindAmount.setForeground(Color.gray); 
        bBlindAmount.setLocation(100, 425); 
        bBlindAmount.setSize(70, 20); 
        bBlindAmount.addMouseListener(new MouseAdapter() {
            public void mouseClicked(MouseEvent e) {
                if (bBlindAmount.getText().equals("Big blind")) {
                    bBlindAmount.setText("");
                    bBlindAmount.setForeground(Color.BLACK); 
                }
            }
        });

        finishSetup = new JButton("Finish setup"); 
        finishSetup.setLocation(345, 235); 
        finishSetup.setSize(150, 90); 
        finishSetup.setVisible(true); 
        finishSetup.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                gameSetup.setVisible(false); 
                gameSetup = null; 
                Poker.round = new Round(Poker.player, Poker.bot, Double.parseDouble(moneyAmount.getText()), 
                    Double.parseDouble(sBlindAmount.getText()), 
                    Double.parseDouble(bBlindAmount.getText()));
            } 
        });

        gameSetup.add(instructions); 
        gameSetup.add(moneyInstructions); 
        gameSetup.add(moneyAmount); 
        gameSetup.add(sBlindInstructions); 
        gameSetup.add(sBlindAmount);
        gameSetup.add(bBlindInstructions); 
        gameSetup.add(bBlindAmount); 
        gameSetup.add(finishSetup); 
        gameSetup.setLayout(new BorderLayout());

    }
}