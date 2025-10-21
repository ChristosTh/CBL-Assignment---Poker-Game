import java.util.ArrayList;
import java.util.Scanner; 

/** Main class for poker. */
public class Poker {
    static Player player = new Player();
    static Deck deck = new Deck();
    static Bot bot = new Bot(100, 1, 2, 0.5);
    static ArrayList<Card> communityCards = new ArrayList<Card>();

    static Poker poker = new Poker();
    static Pot pot = new Pot(); 
    static StartMenu menu; 

    /** Method to distribute 2 cards to the user. */
    public void giveCardsToUsers() {
        player.receiveFirstCard(deck.giveCard());
        player.receiveSecondCard(deck.giveCard());
        //player.receiveFirstCard(new Card('A', "diamonds"));
        //player.receiveSecondCard(new Card('8', "spades"));
        //bot.receiveFirstCard(new Card('9', "diamonds"));
        //bot.receiveSecondCard(new Card('K', "spades"));
        bot.receiveFirstCard(deck.giveCard());
        bot.receiveSecondCard(deck.giveCard());

        PokerMat.setCards(player.getCard1Path(), player.getCard2Path()); 
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

    public void setBlinds(double smallBlind, double bigBlind) {
        pot.setBlinds(smallBlind, bigBlind); 
    }

    /** Set flop cards. */
    public void giveFlop() {
        for (int i = 0; i < 3; i++) {
            communityCards.add(deck.giveCard());
        }
    }


    public static void main(String[] args) {
        player = new Player(); 
        Bot bot = new Bot(player.getWallet(), 0.5); 
        menu = new StartMenu(); 
        deck.shuffleCards();
        //Card card = deck.giveCard();
        // card.displayCard();

        poker.giveCardsToUsers();

        System.out.println("Card 1: " 
            + player.getFirstCard().getRank() + " " + player.getFirstCard().getSuit());
        System.out.println("Card 2: " 
            + player.getSecondCard().getRank() + " " + player.getSecondCard().getSuit());

        System.out.println("Bot Card 1: " 
            + bot.getFirstCard().getRank() + " " + bot.getFirstCard().getSuit());
        System.out.println("Bot Card 2: " 
            + bot.getSecondCard().getRank() + " " + bot.getSecondCard().getSuit());

        /*communityCards.add(new Card('8', "spades"));
        communityCards.add(new Card('K', "diamonds"));
        communityCards.add(new Card('4', "hearts"));*/

        poker.outputCommunityCards();

        HandEvaluation determineWinner = new HandEvaluation(communityCards);
        Player winner = determineWinner.winner(player, bot);
        
        if (winner == player) {
            System.out.println("Winner is the player!");
        } else {
            System.out.println("Winner is the bot!");
        }

    }
}
