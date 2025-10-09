/** Class for a player of poker and everything that a player needs to play. */
public class Player {

    private Wallet wallet;

    /** Player constructor to initalize its wallet and pair of cards to null. */
    public Player(Integer money) {
        wallet = new Wallet(money);
        PairOfCards pairOfCards = new PairOfCards();
    }
    
    /** Class for the pair of cards the player is holding. */
    public class PairOfCards {

        private Card firstCard;
        private Card secondCard;

        /** Constructor for the pair of cards class. Just initializing the cards. */
        public PairOfCards() {
            firstCard = null;
            secondCard = null;
        }

        public void receiveFirstCard(Card card) {
            firstCard = card;
        }

        public void receiveSecondCard(Card card) {
            secondCard = card;
        }

    }

}
