import java.awt.*; 
import javax.swing.*;

/** Javadoc. */
public class PokerMat {
    JFrame pokerMat; 

    /** PokerMat page constructor. */
    public PokerMat(double moneyAmount, double smallBlind, double bigBlind) {
        pokerMat = new JFrame("Poker"); 
        pokerMat.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }
}
