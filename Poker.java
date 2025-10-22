import java.util.ArrayList;
import java.util.Scanner; 

/** Main class for poker. */
public class Poker {
    static Player player = new Player();
    static Deck deck = new Deck();
    static Bot bot;
    static ArrayList<Card> communityCards = new ArrayList<Card>();
    static Round round; 

    static Poker poker = new Poker();
    static Pot pot = new Pot(); 
    static StartMenu menu; 

    static ArrayList<Integer> blindsTrack = new ArrayList<>(); 

    /** Method to distribute 2 cards to the user. */
    public static void giveCardsToUsers() {
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
    public static void giveFlop() {
        String[] cardPaths = new String[3]; 
        for (int i = 0; i < 3; i++) {
            communityCards.add(deck.giveCard());
            cardPaths[i] = communityCards.get(i).getCardPath(); 
        }
        PokerMat.setFlop(cardPaths[0], cardPaths[1], cardPaths[2]);
    }


    public static void main(String[] args) {
        blindsTrack.add(1); 
        blindsTrack.add(0); 

        player = new Player(); 
        bot = new Bot(player.getWallet(), 0.5); 
        round = new Round(); 

        menu = new StartMenu(); 

        Round round = new Round(); 
        round.flop(); 

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
