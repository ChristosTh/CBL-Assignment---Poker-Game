import javax.smartcardio.Card;

public class Player {
    
    public class pairOfCards {

        private Card firstCard;
        private Card secondCard;

        public pairOfCards() {
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
