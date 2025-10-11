import java.util.Scanner; 

/** Main class for poker. */
public class Poker {
    static Player player;
    Deck deck = new Deck();

    /** Method to distribute 2 cards to the user. */
    public void giveCardsToUser() {
        player.receiveFirstCard(deck.giveCard());
        player.receiveSecondCard(deck.giveCard());
    }

    public static void main(String[] args) {
        // console asks how much money

        Card card = new Card('2', "spades");

        // Display the card's image in a window
        card.displayCard();
        
        System.out.println("Please enter three integers separated by a single space: "
                + "How much money you'd like to play with, the small blind, and the big blind."); 
        Scanner input = new Scanner(System.in); 
        int moneyAmount = input.nextInt(); 
        int small = input.nextInt(); 
        int big = input.nextInt(); 
        player = new Player(moneyAmount, small, big);
        input.close(); 


    }
}
