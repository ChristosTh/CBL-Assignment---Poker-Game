import java.util.ArrayList;

/**
 * Class for a round of poker. Manages the gameflow, betting rounds,
 * and state progression from Preflop to Showdown.
 */
public class Round {

    /** Enum to track which phase the round is in. */
    public enum GameState {
        PREFLOP, FLOP, TURN, RIVER, SHOWDOWN, HAND_OVER
    }

    static Deck deck;
    static ArrayList<Card> communityCards;
    static Pot pot = new Pot();
    
    static boolean playerFirst;
    static String whoPlays; // the player or the bot

    static boolean flopShowed;
    static boolean turnShowed;
    static boolean riverShowed;

    private Player player;
    private Bot bot;
    private GameState currentState;

    /** Round constructor. Sets up the hand, deals cards, and starts the
     *  first betting round. */
    public Round(Player player, Bot bot, double playerMoneyAmount, 
            double roundSmallBlind, double roundBigBlind, boolean wasPlayerFirst) {
        
        this.player = player;
        this.bot = bot;

        flopShowed = false;
        turnShowed = false;
        riverShowed = false;

        pot = new Pot();
        player.setWallet(playerMoneyAmount);
        pot.setBlinds(roundSmallBlind, roundBigBlind);
        
        deck = new Deck();
        communityCards = new ArrayList<Card>();
        deck.shuffleCards();

        playerFirst = true;
        
        giveCardsToUsers(player, bot);
        payBlinds(player, bot);

        currentState = GameState.PREFLOP;

        // Create the UI
        GameSetup.mat = new PokerMat(player.getWallet(), pot.getSmallBlind(), pot.getBigBlind());
        GameSetup.mat.newRoundButton.setEnabled(false); // will be set to visible after round ends

        whoPlays = playerFirst ? "player" : "bot";
        
        if (whoPlays.equals("bot")) {
            runBotTurn();
        }
    }

    /** Method to distribute 2 cards to the users. */
    public static void giveCardsToUsers(Player player, Bot bot) {
        player.receiveFirstCard(deck.giveCard());
        player.receiveSecondCard(deck.giveCard());
        bot.receiveFirstCard(deck.giveCard());
        bot.receiveSecondCard(deck.giveCard());

        PokerMat.setCards(player.getCard1Path(), player.getCard2Path(),
            bot.getCard1Path(), bot.getCard2Path());
    }

    /** Players pay their required blinds. */
    void payBlinds(Player player, Bot bot) {
        if (playerFirst) {
            System.out.println("Player is Small Blind");
            player.paySmallBlind(); 
            bot.payBigBlind();
        } else {
            System.out.println("Bot is Small Blind");
            bot.paySmallBlind();
            player.payBigBlind();
            pot.addBigBlind();
        }
    }

    /** Central method called by the UI when the player clicks an action button.
     * @param action The action taken ("fold", "check", "call", "raise").
     * @param amount The amount for a raise (this is the NEW TOTAL bet).
     */
    public void playerActed(String action, double amount) {
        if (!whoPlays.equals("player") || currentState == GameState.HAND_OVER) {
            return; // Not player's turn or hand is over
        }

        boolean roundOver = false;

        switch (action) {
            case "fold":
                player.fold();
                endHand(bot); // Bot wins
                return;

            case "check":
                // Can only check if no bet is pending
                if (pot.getCurrentRaise() > player.getLastBet()) {
                    System.out.println("You cannot check, there is a bet to you.");
                    return; 
                }
                player.check();
                if (player.getLastBet() == bot.getLastBet()) {
                    roundOver = false;
                }
                break;

            case "call":
                double amountToCall = pot.getCurrentRaise() - player.getLastBet();
                if (amountToCall <= 0) {
                    return; // Invalid action
                }
                player.call(pot.getCurrentRaise()); // Updates wallet & lastBet
                roundOver = false; // Calling always ends the betting round

                if (bot.getLastBet() == player.getLastBet()) {
                    System.out.println("Bot raised bef!!!!!!!!");
                    roundOver = true;
                }

                break;

            case "raise":
                player.raise(amount);          
                roundOver = false;             
                break;

            default: 
                break;
        }

        if (roundOver) {
            progressToNextStage();
            System.out.println("Playing: " + whoPlays);
        } else {
            whoPlays = "bot";
            runBotTurn();
        }
    }

    /** Runs the bot's logic. */
    private void runBotTurn() {
        if (!whoPlays.equals("bot") || currentState == GameState.HAND_OVER) {
            return;
        }

        System.out.println("Bot is thinking...");
        
        /* Bot's action logic is inside decideAction. */
        int bd = bot.decideAction(pot.getPotTotal(), pot.getCurrentRaise(), communityCards);

        if (bd == 1) {
            endHand(player);
        }

        if (bot.hasFolded()) {
            endHand(player); // Player wins
            return;
        }

        // Check if bot raised
        if (bot.getLastBet() > player.getLastBet()) {
            whoPlays = "player";
            System.out.println("Bot raised to " + bot.getLastBet() + ". Your turn.");
            
        } else {
            // Bot called (bot.lastBet == pot.currentRaise) or checked (both 0)
            if (player.getLastBet() == 0 && bot.getLastBet() == 0) {
                //GameSetup.mat.botAction("check", 0, false); 
            } else {
                //GameSetup.mat.botAction("call", 1, false); 
            }
            System.out.println("Bot called or checked.");
            progressToNextStage();
        }
    }

    /** Advances the game to the next stage (Flop, Turn, River, Showdown). */
    private void progressToNextStage() {

        if (currentState == GameState.RIVER) {
            doShowdown();
            return;
        }

        // Reset for next betting round
        pot.potRaise(0);
        player.setLastBet(0);
        bot.setLastBet(0);
        
        // Post-flop, the Small Blind (playerFirst) always acts first
        whoPlays = playerFirst ? "player" : "bot";

        switch (currentState) {
            case PREFLOP:
                currentState = GameState.FLOP;
                giveFlop();
                flopShowed = true;
                GameSetup.mat.showFlopCards(); 
                System.out.println("--- FLOP ---");
                outputCommunityCards();
                break;
            case FLOP:
                currentState = GameState.TURN;
                giveTurn();
                turnShowed = true;
                GameSetup.mat.showTurnCard(); 
                System.out.println("--- TURN ---");
                outputCommunityCards();
                break;
            case TURN:
                currentState = GameState.RIVER;
                giveRiver();
                riverShowed = true;
                GameSetup.mat.showRiverCard();
                System.out.println("--- RIVER ---");
                outputCommunityCards();
                break;
            default:
                break;
        }

        // After dealing new cards, if it's the bot's turn to act first, run its turn
        if (whoPlays.equals("bot")) {
            runBotTurn();
        } else {
            System.out.println("Your turn to act.");
        }
    }

    /** Deals the flop (3 cards). */
    public static void giveFlop() {
        String[] cardPaths = new String[3];
        for (int i = 0; i < 3; i++) {
            Card c = deck.giveCard();
            communityCards.add(c);
            cardPaths[i] = c.getCardPath();
        }
        PokerMat.setFlop(cardPaths);
    }

    /** Deals the turn (1 card). */
    public static void giveTurn() {
        Card c = deck.giveCard();
        communityCards.add(c);
        PokerMat.setTurn(c.getCardPath());
    }

    /** Deals the river (1 card). */
    public static void giveRiver() {
        Card c = deck.giveCard();
        communityCards.add(c);
        PokerMat.setRiver(c.getCardPath());
    }

    /** Determining the winner. */
    private void doShowdown() {
        currentState = GameState.SHOWDOWN;
        GameSetup.mat.showBotCards(); 

        System.out.println("Bot has: "
            + bot.getFirstCard().getRank() + " " + bot.getFirstCard().getSuit() + ", "
            + bot.getSecondCard().getRank() + " " + bot.getSecondCard().getSuit());

        HandEvaluation determineWinner = new HandEvaluation(communityCards);
        Player winner = determineWinner.winner(player, bot);
        
        endHand(winner);
    }

    /** Cleans up the hand, awards the pot, and enables the "New Round" button.
     *  @param winner The player who won the hand (can be null for a tie). */
    private void endHand(Player winner) {
        currentState = GameState.HAND_OVER;
        double totalPot = pot.getPotTotal();

        if (winner == player) {
            System.out.println("Player wins the pot of " + totalPot);
            player.setWallet(player.getWallet() + totalPot);
            GameSetup.mat.playerWonRound();
        } else if (winner == bot) {
            System.out.println("Bot wins the pot of " + totalPot);
            bot.setWallet(bot.getWallet() + totalPot);
            GameSetup.mat.botWonRound();
        } else {
            System.out.println("It's a tie! Splitting the pot of " + totalPot);
            player.setWallet(player.getWallet() + totalPot / 2);
            bot.setWallet(bot.getWallet() + totalPot / 2);
            GameSetup.mat.roundTie(); 
        }

        
        GameSetup.mat.updateWalletDisplay(GameSetup.mat.moneyDisplay);
        GameSetup.mat.updateWalletDisplay(GameSetup.mat.botMoneyDisplay);
        GameSetup.mat.newRoundButton.setEnabled(true);

        if (player.getWallet() < pot.getBigBlind() || bot.getWallet() < pot.getBigBlind()) {
            GameSetup.mat.gameOver(); 
        }
    }


    /** Method to output the cards on the table. */
    public void outputCommunityCards() {
        System.out.print("Community Cards:");
        for (Card c : communityCards) {
            System.out.print(" [" + c.getRank() + " " + c.getSuit() + "]");
        }
        System.out.println();
    }

    static boolean getPlayerFirst() {
        return playerFirst;
    }
}