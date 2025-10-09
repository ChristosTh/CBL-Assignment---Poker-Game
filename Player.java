/** Class for a player of poker and everything that a player needs to play. */
public class Player {

    private Wallet wallet;
    private Card firstCard;
    private Card secondCard;

    /** Player constructor to initalize its wallet and pair of cards to null. */
    public Player(Integer money) {
        wallet = new Wallet(money);
        firstCard = null;
        secondCard = null;
    }
    
    public void receiveFirstCard(Card card) {
        firstCard = card;
    }

    public void receiveSecondCard(Card card) {
        secondCard = card;
    }

    public void clearCards() {
        firstCard = null;
        secondCard = null;
    }

    public Card getFirstCard() {
        return firstCard;
    }

    public Card getSecondCard() {
        return secondCard;
    }

}
