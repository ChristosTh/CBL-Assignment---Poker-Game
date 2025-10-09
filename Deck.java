import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/** Class for the deck of cards with all the methods 
 * that are related to it and need to be applied. */
public class Deck {

    ArrayDeque<Card> cards;
    
    public Deck(ArrayDeque<Card> cards) {
        this.cards = new ArrayDeque<Card>(cards);
    }

    /** Procedure to shuffle the cards in the deck. */
    public void shuffleCards() {
        List<Card> list = new ArrayList<Card>(cards);
        Collections.shuffle(list);
        cards = new ArrayDeque<Card>(list);
    }

    /** Function to give a card to the players or the table. */
    public Card giveCard() {
        Card cardToReturn;
        cardToReturn = cards.getFirst();
        cards.removeFirst();

        return cardToReturn;
    }
    
    /** Procedure to get a card from the table and add it to the deck. */
    public void getCard(Card cardGot) {
        cards.addLast(cardGot);
    }

}
