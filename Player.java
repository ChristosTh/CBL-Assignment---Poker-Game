/** Class for a player of poker and everything that a player needs to play. */
public class Player {

    private Wallet wallet;
    private Card firstCard;
    private Card secondCard;

    /** Player constructor to initalize its wallet and pair of cards to null. */
    public Player(int money, int small, int big) {
        wallet = new Wallet(money, small, big); 
        // the 0's above are placeholders for when we will actually ask user smallBlind & bigBlind.
        firstCard = null;
        secondCard = null;
    }
    
    /** Procedure to get one of the two cards. */
    public void receiveFirstCard(Card card) {
        firstCard = card;
    }

    /** Procedure to get the other of the two cards. */
    public void receiveSecondCard(Card card) {
        secondCard = card;
    }

    /** Procedure to clear the cards. */
    public void clearCards() {
        firstCard = null;
        secondCard = null;
    }

    /** Function to get on the two cards. */
    public Card getFirstCard() {
        return firstCard;
    }

    /** Function to get the other of the two cards. */
    public Card getSecondCard() {
        return secondCard;
    }

}