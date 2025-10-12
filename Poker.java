import java.util.Scanner; 

/** Main class for poker. */
public class Poker {
    static Player player = new Player(100, 1, 2);
    static Deck deck = new Deck();
    static Bot bot = new Bot(100, 1, 2);

    /** Method to distribute 2 cards to the user. */
    public void giveCardsToUser() {
        player.receiveFirstCard(deck.giveCard());
        player.receiveSecondCard(deck.giveCard());
    }

    public static void main(String[] args) {
        // console asks how much money

        deck.shuffleCards();
        //Card card = deck.giveCard();
        //card.displayCard();

        
        /*System.out.println("Please enter three integers separated by a single space: "
                + "How much money you'd like to play with, the small blind, and the big blind.");
        Scanner input = new Scanner(System.in);
        int moneyAmount = input.nextInt(); 
        int small = input.nextInt();
        int big = input.nextInt(); 
        player = new Player(moneyAmount, small, big);
        input.close(); */

        player.receiveFirstCard(deck.giveCard());
        player.receiveSecondCard(deck.giveCard());

        System.out.println("Card 1: " + player.getFirstCard().getRank() + " " + player.getFirstCard().getSuit());
        System.out.println("Card 2: " + player.getSecondCard().getRank() + " " + player.getSecondCard().getSuit());

        bot.receiveFirstCard(deck.giveCard());
        bot.receiveSecondCard(deck.giveCard());

        System.out.println("Bot Card 1: " + bot.getFirstCard().getRank() + " " + bot.getFirstCard().getSuit());
        System.out.println("Bot Card 2: " + bot.getSecondCard().getRank() + " " + bot.getSecondCard().getSuit());

    }
}
