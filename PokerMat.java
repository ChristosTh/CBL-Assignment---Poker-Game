import java.awt.*; 
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.*;


/** Javadoc. */
public class PokerMat {
    JFrame pokerMat; 

    JLabel walletDisplay; 
    JLabel bBlindDisplay; 
    JLabel sBlindDisplay; 

    ImageIcon faceDownCard = new ImageIcon("CardsPNG\\faceDownCard.png"); 
    Image scalingImage = faceDownCard.getImage().getScaledInstance(157, 160, Image.SCALE_DEFAULT);
    ImageIcon scaledImage = new ImageIcon(scalingImage); 
    JLabel imageContainer = new JLabel(scaledImage);  

    static ImageIcon playerCard1; 
    static ImageIcon playerCard2; 

    /** Method to show your cards in UI. */
    static void setCards(String cardPath1, String cardPath2) {
        playerCard1 = new ImageIcon(cardPath1);
        playerCard2 = new ImageIcon(cardPath2);  
    }

    /** Closing JFrame. */
    void closeFrame(JFrame frame) {
        frame.setVisible(false); 
        frame = null; 
    }

    Image scalingCard1 = playerCard1.getImage().getScaledInstance(115, 160, Image.SCALE_DEFAULT); 
    ImageIcon scaledCard1 = new ImageIcon(scalingCard1); 
    JLabel card1Container = new JLabel(scaledCard1); 
        
    Image scalingCard2 = playerCard2.getImage().getScaledInstance(115, 160, Image.SCALE_DEFAULT); 
    ImageIcon scaledCard2 = new ImageIcon(scalingCard2); 
    JLabel card2Container = new JLabel(scaledCard2); 

    JButton raiseButton; 
    JButton callButton; 
    JButton foldButton; 
    JButton allInButton; 

    /** PokerMat page constructor. */
    public PokerMat(double moneyAmount, double smallBlind, double bigBlind) {
        Poker.poker.setBlinds(smallBlind, bigBlind); 
        Poker.player.setWallet(moneyAmount); 

        pokerMat = new JFrame("Poker"); 
        pokerMat.setSize(1100, 750); 
        pokerMat.setVisible(true); 
        pokerMat.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        pokerMat.getContentPane().setBackground(new Color(6, 117, 8));
        pokerMat.setLocationRelativeTo(null);

        /* display small blind:
        sBlindDisplay = new JLabel(Double.toString(Poker.poker.pot.getSmallBlind())); 
        sBlindDisplay.setSize(200, 200); 
        sBlindDisplay.setLocation(400, 400); */

        //#region Displaying cards.
        imageContainer.setSize(180, 180); 
        imageContainer.setLocation(100, 250); 

        card1Container.setSize(115, 160); 
        card1Container.setLocation(420, 500); 

        card2Container.setSize(115, 160); 
        card2Container.setLocation(550, 500);
        //#endregion

        //#region Raise functionality.
        raiseButton = new JButton("Raise");
        raiseButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                JFrame raiseFrame = new JFrame("Raising"); 
                JButton closeRaiseFrame = new JButton("Raise"); 
                JTextField raiseAmount = new JTextField("Type raise amount here"); 

                raiseAmount.setForeground(Color.GRAY); 
                raiseAmount.addMouseListener(new MouseAdapter() {
                    @Override
                    public void mouseClicked(MouseEvent e) {
                        raiseAmount.setForeground(Color.BLACK);
                        raiseAmount.setText(""); 
                    }
                });
                raiseAmount.setSize(100, 20); 
                raiseAmount.setLocation(80, 100);

                closeRaiseFrame.setSize(100, 20); 
                closeRaiseFrame.setLocation(170, 225); 

                closeRaiseFrame.addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        Poker.player.raise(Double.parseDouble(raiseAmount.getText())); 
                        System.out.println("wtf" + Poker.pot.getCurrentRaise()); 
                        closeFrame(raiseFrame); 
                    }
                });
                
                
                raiseFrame.setFocusable(true); 
                raiseFrame.setSize(300, 300); 
                raiseFrame.setLocationRelativeTo(null); 
                raiseFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
                raiseFrame.setVisible(true); 


                raiseFrame.add(closeRaiseFrame); 
                raiseFrame.add(raiseAmount); 
                raiseFrame.setLayout(new BorderLayout()); 
            } 
        });
        raiseButton.setSize(100, 30); 
        raiseButton.setLocation(300, 510);
        //#endregion

        //#region Call functionality
        callButton = new JButton("Call"); 
        callButton.setSize(100, 30); 
        callButton.setLocation(300, 550); 
        callButton.addMouseListener(new MouseAdapter() {
            @Override 
            public void mouseClicked(MouseEvent e) {
                Poker.player.call(Poker.pot.getCurrentRaise()); 
            }
        });
        //#endregion
        
        // pokerMat.add(sBlindDisplay); 
        pokerMat.add(imageContainer);
        pokerMat.add(card1Container); 
        pokerMat.add(card2Container); 
        pokerMat.add(raiseButton);
        pokerMat.add(callButton); 
        pokerMat.setLayout(new BorderLayout()); 


        

    }
}
