import java.util.ArrayList;

/** Bot class for bot that the user will play against. */
public class Bot extends Player {
    
    /** Bot constructor inheriting the super class. */
    public Bot(int money, int small, int big) {
        super(money, small, big);
    }

    /** Method for bot to bluff. */
    public void bluff() {

    }

    /** Metho to calculate what cards the bot is holding. */
    public double handCalculation(ArrayList<Card> cardsToCheck) {
        HandEvaluation handEvaluation = new HandEvaluation(cardsToCheck);

        if (handEvaluation.isPair()){
            return 0.25;
        } else if (handEvaluation.isTwoPair()) {
            return 0.35;
        } else if (handEvaluation.isThreeKind()) {
            return 0.45;
        } else if (handEvaluation.isStraight()) {
            return 0.55;
        } else if (handEvaluation.isFlush()) {
            return 0.65;
        } else if (handEvaluation.isFullHouse()) {
            return 0.75;
        } else if (handEvaluation.isFourKind()) {
            return 0.9;
        } else if (handEvaluation.isStraightFlush()) {
            return 0.95;
        } else if (handEvaluation.isRoyalFlush()) {
            return 1;
        } else {
            return 0;
        }

    }

    /** Method for bot to calculate the power of the cards its holding. */
    public int handPower(ArrayList<Card> communityCards) {

        ArrayList<Card> cardsToCheck = new ArrayList<Card>();
        cardsToCheck.clear();

        cardsToCheck.add(getFirstCard());
        cardsToCheck.add(getSecondCard());
        
        for (int i = 0; i < communityCards.size(); i++) {
            cardsToCheck.add(communityCards.get(i));
        }

        return handCalculation(cardsToCheck);

    }

    public void decision(ArrayList<Card> communityCards) {

        if (communityCards.size() == 3) {
            if (handPower(communityCards) >= 2) {
                raise();
            }
        }

    }

    /*
     * One Pair: 2
     * Two Pairs: 3
     * Three of a kind: 4
     * Straight: 5
     * Flush: 6
     * Full house: 7
     * Four of a kind: 8
     * Straight Flush: 9
     * Royal Flush: 10
     */

}
