/** Card class that describes what a card is so that we can use them. */
public class Card {

    private char rank;
    private String suit;

    /** Card constructor that gives the properties of the card. */
    public Card(char rank, String suit) {
        this.rank = rank;
        this.suit = suit;
    }

    public char getRank() {
        return rank;
    }

    public String getSuit() {
        return suit;
    }

}
