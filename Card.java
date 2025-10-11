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

    
    // Method to display the card as an image in a Swing window
    public void displayCard() {
        // Create a new JFrame to display the card's image
        JFrame frame = new JFrame("Card Display");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        // Create a JLabel and set the card's image as the icon
        JLabel label = new JLabel(cardImage);

        // Add the JLabel to the frame and pack the frame to fit the image
        frame.getContentPane().add(label);
        frame.pack();
        frame.setLocationRelativeTo(null); // Center the window
        frame.setVisible(true);
    }

}
