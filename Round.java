import java.util.ArrayList;

/** Class for round. */
public class Round {

    static Deck deck;
    static ArrayList<Card> communityCards;
    static Pot pot = new Pot(); 
    
    static boolean playerFirst;

    static String whoPlays;

    static boolean flopShowed;
    static boolean turnShowed;
    static boolean riverShowed;

    /** Method to distribute 2 cards to the user. */
    public static void giveCardsToUsers(Player player, Bot bot) {
        player.receiveFirstCard(deck.giveCard());
        player.receiveSecondCard(deck.giveCard());
        //player.receiveFirstCard(new Card('A', "diamonds"));
        //player.receiveSecondCard(new Card('8', "spades"));
        //bot.receiveFirstCard(new Card('9', "diamonds"));
        //bot.receiveSecondCard(new Card('K', "spades"));
        bot.receiveFirstCard(deck.giveCard());
        bot.receiveSecondCard(deck.giveCard());

        PokerMat.setCards(player.getCard1Path(), player.getCard2Path(),
            bot.getCard1Path(), bot.getCard2Path()); 
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

    /* public void setBlinds(double smallBlind, double bigBlind) {
        pot.setBlinds(smallBlind, bigBlind); 
    } */

    /** Set flop cards. */
    public static void giveFlop() {
        String[] cardPaths = new String[3]; 
        for (int i = 0; i < 3; i++) {
            communityCards.add(deck.giveCard());
            cardPaths[i] = communityCards.get(i).getCardPath(); 
        }
        PokerMat.setFlop(cardPaths);
    }

    public static void giveTurn() {
<<<<<<< HEAD
        String cardPath = new String(); 
        communityCards.add(deck.giveCard()); 
        cardPath = communityCards.get(3).getCardPath(); 
        PokerMat.setTurn(cardPath); 
=======
        communityCards.add(deck.giveCard());
        String cardPath = communityCards.getLast().getCardPath();
    }

    public static void giveRiver() {
        communityCards.add(deck.giveCard());
        String cardPath = communityCards.getLast().getCardPath();
>>>>>>> 0c85418819009f92fafbe24d58928dfb72ff2d23
    }

    public Round(Player player, Bot bot, double playerMoneyAmount, double roundSmallBlind, double roundBigBlind, boolean wasPlayerFirst) {
        /*Poker.deck = new Deck(); 
        Poker.deck.shuffleCards(); 
        Poker.giveCardsToUsers(); 
        Poker.giveFlop(); */

        flopShowed = false;
        turnShowed = false;
        riverShowed = false;

        pot = new Pot();

        Poker.player.setWallet(playerMoneyAmount);
        Poker.bot.setWallet(playerMoneyAmount);
        pot.setBlinds(roundSmallBlind, roundBigBlind); 
        deck = new Deck();
        communityCards = new ArrayList<Card>();
        //communityCards.clear();

        deck.shuffleCards();

        if (!wasPlayerFirst) {
            playerFirst = true;
            System.out.println("Player is first");
            whoPlays = "player";
        } else {
            playerFirst = false;
            System.out.println("Bot is first");
            whoPlays = "bot";
        }

        giveCardsToUsers(player, bot);
        payBlinds(player, bot);

        System.out.println("Bot wallet: " + bot.getWallet());

        GameSetup.mat = new PokerMat(Poker.player.getWallet(), pot.getSmallBlind(), pot.getBigBlind());


        /*if (whoPlays.equals("bot")) {
            bot.decideAction(pot.getPotTotal(), pot.getCurrentRaise(), communityCards);
            whoPlays = "player";
        }*/

        System.out.println("Bot Card 1: " 
            + bot.getFirstCard().getRank() + " " + bot.getFirstCard().getSuit());
        System.out.println("Bot Card 2: " 
            + bot.getSecondCard().getRank() + " " + bot.getSecondCard().getSuit());

        /*communityCards.add(new Card('8', "spades"));
        communityCards.add(new Card('K', "diamonds"));
        communityCards.add(new Card('4', "hearts"));*/

        outputCommunityCards();

        HandEvaluation determineWinner = new HandEvaluation(communityCards);
        Player winner = determineWinner.winner(player, bot);
        
        if (winner == player) {
            System.out.println("Winner is the player!");
        } else {
            System.out.println("Winner is the bot!");
        }

    }

    /** Method to start the round, paying the blinds. */
    void payBlinds(Player player, Bot bot) {
        if (playerFirst) {
            player.paySmallBlind();
            bot.payBigBlind(); 
            //while (player.getLastBet() < bot.getLastBet()) { }
            flop(bot); 
        } else {
            player.payBigBlind(); 
            bot.payBigBlind(); 
            flop(bot); 
        }
    }

    /** Method to start the flop part of the round (first 3 community cards). */
    void flop(Bot bot) {

        if (whoPlays.equals("bot")) {
            bot.decideAction(pot.getPotTotal(), pot.getCurrentRaise(), communityCards);
            whoPlays = "player";
            System.out.println("Bot decided!");
        }

        giveFlop();
        flopShowed = true;
        /*if (playerFirst) {
            
            giveFlop();
        } else {
            giveFlop();
        }*/

    }

    // Method to start the turn part of the round (4th community card). 
    static void turn(Bot bot) {

        if (whoPlays.equals("bot")) {
            bot.decideAction(pot.getPotTotal(), pot.getCurrentRaise(), communityCards);
            whoPlays = "player";
            System.out.println("Bot decided!");
            giveTurn();
        }

    }

    // Method to start the river part of the round (5th community card). 
    void river(Bot bot) {

        if (whoPlays.equals("bot")) {
            bot.decideAction(pot.getPotTotal(), pot.getCurrentRaise(), communityCards);
            whoPlays = "player";
            System.out.println("Bot decided!");
            giveRiver();
        }
    }

    static boolean getPlayerFirst() {
        return playerFirst;
    }

}
