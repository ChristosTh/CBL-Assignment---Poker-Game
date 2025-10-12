import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/** Class for the deck of cards with all the methods 
 * that are related to it and need to be applied. */
public class Deck {

    ArrayDeque<Card> cards = new ArrayDeque<Card>();
    
    /** Deck constructor creating a new full deck of cards. */
    public Deck() {
        String[] suits = {"hearts", "diamonds", "clubs", "spades"};
        char[] ranks = {'A', '2', '3', '4', '5', '6', '7', '8', '9', 'T', 'J', 'Q', 'K'}; // T is 10

        for (String suit : suits) {
            for (char rank : ranks) {
                cards.add(new Card(rank, suit));
            }
        }

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
    public void takeCard(Card cardGot) {
        cards.addLast(cardGot);
    }

}
