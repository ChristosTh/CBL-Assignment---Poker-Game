import javax.swing.*;

/** Card class that describes what a card is so that we can use them. */
public class Card {

    private char rank;
    private String suit;
    private ImageIcon cardImage;
    private String imagePath;

    /** Card constructor that gives the properties of the card. */
    public Card(char rank, String suit) {
        this.rank = rank;
        this.suit = suit;
        imagePath = "CardsPNG\\" + rank + "_of_" + suit + ".png"; 
        // chane for cross platform compatibility
        cardImage = new ImageIcon(imagePath);
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
