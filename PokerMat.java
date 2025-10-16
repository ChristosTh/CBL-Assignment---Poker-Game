import java.awt.*; 
import javax.swing.*;


/** Javadoc. */
public class PokerMat {


    JFrame pokerMat; 

    JLabel walletDisplay; 
    JLabel bBlindDisplay; 
    JLabel sBlindDisplay; 

    ImageIcon faceDownCard = new ImageIcon("CardsPNG\\faceDownCard.png"); 
    Image scalingImage = faceDownCard.getImage().getScaledInstance(160, 160, Image.SCALE_DEFAULT);
    ImageIcon scaledImage = new ImageIcon(scalingImage); 
    JLabel imageContainer = new JLabel(scaledImage);  

    static ImageIcon playerCard1; 
    static ImageIcon playerCard2; 

    /** Method to show your cards in UI. */
    static void setCards(String cardPath1, String cardPath2) {
        playerCard1 = new ImageIcon(cardPath1);
        playerCard2 = new ImageIcon(cardPath2);  
    }

    Image scalingCard1 = playerCard1.getImage().getScaledInstance(125, 160, Image.SCALE_DEFAULT); 
    ImageIcon scaledCard1 = new ImageIcon(scalingCard1); 
    JLabel card1Container = new JLabel(scaledCard1); 
        
    Image scalingCard2 = playerCard2.getImage().getScaledInstance(180, 180, Image.SCALE_DEFAULT); 
    ImageIcon scaledCard2 = new ImageIcon(scalingCard2); 
    JLabel card2Container = new JLabel(scaledCard2); 

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

        sBlindDisplay = new JLabel(Double.toString(Poker.poker.pot.getSmallBlind())); 
        sBlindDisplay.setSize(200, 200); 
        sBlindDisplay.setLocation(400, 400); 

        imageContainer.setSize(180, 180); 
        imageContainer.setLocation(100, 250); 

        card1Container.setSize(125, 160); 
        card1Container.setLocation(500, 250); 

        pokerMat.add(sBlindDisplay); 
        pokerMat.add(imageContainer);
        pokerMat.add(card1Container); 
        pokerMat.add(card2Container); 
        pokerMat.setLayout(new BorderLayout()); 

    }
}
