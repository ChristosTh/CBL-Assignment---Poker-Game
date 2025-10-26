import java.io.File; 

/** Card class that describes what a card is so that we can use them. */
public class Card {

    private char rank;
    private String suit;
    private String imagePath;

    /** Card constructor that gives the properties of the card. */
    public Card(char rank, String suit) {
        this.rank = rank;
        this.suit = suit;
        imagePath = "VisualAssets" + File.separator + rank + "_of_" + suit + ".png"; 
        // chane for cross platform compatibility
    }

    public char getRank() {
        return rank;
    }

    public String getSuit() {
        return suit;
    }

    String getCardPath() {
        return imagePath; 
    }
    


}
