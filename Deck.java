import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Deck {

    ArrayDeque<Card> cards;
    
    public Deck(ArrayDeque<Card> cards) {
        this.cards = new ArrayDeque<Card>(cards);
    }

    public void shuffleCards() {
        List<Card> list = new ArrayList<Card>(cards);
        Collections.shuffle(list);
        cards = new ArrayDeque<Card>(list);
    }

    public Card giveCard() {
        Card cardToReturn;
        cardToReturn = cards.getFirst();
        cards.removeFirst();

        return cardToReturn;
    }

    public void getCard(Card cardGot) {
        cards.addLast(cardGot);
    }

}
