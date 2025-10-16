import java.awt.*; 
import javax.swing.*;


/** Javadoc. */
public class PokerMat {


    JFrame pokerMat; 

    JLabel walletDisplay; 
    JLabel bBlindDisplay; 
    JLabel sBlindDisplay; 

    ImageIcon faceDownCard = new ImageIcon("CardsPNG\faceDownCard.png"); 
    Image scalingImage = faceDownCard.getImage().getScaledInstance(180, 180, Image.SCALE_DEFAULT);
    ImageIcon scaledImage = new ImageIcon(scalingImage); 
    JLabel imageContainer = new JLabel(scaledImage); 

    /** PokerMat page constructor. */
    public PokerMat(double moneyAmount, double smallBlind, double bigBlind) {
        Poker.poker.setBlinds(smallBlind, bigBlind); 
        Poker.player.setWallet(moneyAmount); 

        pokerMat = new JFrame("Poker"); 
        pokerMat.setSize(1100, 750); 
        pokerMat.setVisible(true); 
        pokerMat.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        pokerMat.getContentPane().setBackground(new Color(9, 156, 12));
        pokerMat.setLocationRelativeTo(null);

        sBlindDisplay = new JLabel(Double.toString(Poker.poker.pot.getSmallBlind())); 
        sBlindDisplay.setSize(200, 200); 
        sBlindDisplay.setLocation(400, 400); 

        imageContainer.setSize(180, 180); 
        imageContainer.setLocation(100, 250); 

        pokerMat.add(sBlindDisplay); 
        pokerMat.add(imageContainer);
        pokerMat.setLayout(new BorderLayout()); 

    }
}
