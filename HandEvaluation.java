import java.util.ArrayList;

/** Class to determing te power of player's hand. */
public class HandEvaluation {

    ArrayList<Card> cardsToCheck;
    
    /** Constructor to know what cards we need to check. */
    public HandEvaluation(ArrayList<Card> cardsToCheck) {
        this.cardsToCheck.addAll(cardsToCheck);
    }

    public boolean isStraightFlash() {
        return false;
    }   

    public boolean isFourKind() {
        return false;
    }

    public boolean isFullHouse() {
        return false;
    }

    public boolean isFlush() {
        return false;
    }

    public boolean isStraight() {
        return false;
    }

    public boolean isThreeKind() {
        return false;
    }

    public boolean isTwoPair() {
        return false;
    }

    public boolean isPair() {
        return false;
    }

    public boolean isHighCard() {
        return false;
    }

}
