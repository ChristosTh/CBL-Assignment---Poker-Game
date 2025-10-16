import java.util.ArrayList;
import java.util.Scanner; 

/** Main class for poker. */
public class Poker {
    static Player player = new Player(100, 1, 2);
    static Deck deck = new Deck();
    static Bot bot = new Bot(100, 1, 2);
    static ArrayList<Card> communityCards = new ArrayList<Card>();

    /** Method to distribute 2 cards to the user. */
    public void giveCardsToUsers() {
        player.receiveFirstCard(deck.giveCard());
        player.receiveSecondCard(deck.giveCard());
        bot.receiveFirstCard(deck.giveCard());
        bot.receiveSecondCard(deck.giveCard());
    }

    /** Method to output the cards on the table. */
    public void outputCommunityCards() {
        System.out.print("Community Cards:");
        for (int i = 0; i < communityCards.size(); i++) {
            System.out.print(" " + communityCards.get(i).getRank() + " "
                + communityCards.get(i).getSuit());
        }
        System.out.println();
    }

    public static void main(String[] args) {
        Poker poker = new Poker();

        deck.shuffleCards();
        Card card = deck.giveCard();
        // card.displayCard();
        new StartMenu(); 
        
        System.out.println("Please enter three integers separated by a single space: "
                + "How much money you'd like to play with, the small blind, and the big blind.");
        Scanner input = new Scanner(System.in);
        int moneyAmount = input.nextInt(); 
        int small = input.nextInt();
        int big = input.nextInt(); 
        player = new Player(moneyAmount, small, big);
        input.close(); 

        poker.giveCardsToUsers();

        System.out.println("Card 1: " 
            + player.getFirstCard().getRank() + " " + player.getFirstCard().getSuit());
        System.out.println("Card 2: " 
            + player.getSecondCard().getRank() + " " + player.getSecondCard().getSuit());

        System.out.println("Bot Card 1: " 
            + bot.getFirstCard().getRank() + " " + bot.getFirstCard().getSuit());
        System.out.println("Bot Card 2: " 
            + bot.getSecondCard().getRank() + " " + bot.getSecondCard().getSuit());


        for (int i = 0; i < 3; i++) {
            communityCards.add(deck.giveCard());
        }

        poker.outputCommunityCards();
        

    }
}
