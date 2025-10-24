import java.util.ArrayList;

/**
 * Class for a round of poker. Manages the gameflow, betting rounds,
 * and state progression from Preflop to Showdown.
 */
public class Round {

    // Enum to track the current stage of the game
    public enum GameState {
        PREFLOP, FLOP, TURN, RIVER, SHOWDOWN, HAND_OVER
    }

    static Deck deck;
    static ArrayList<Card> communityCards;
    static Pot pot = new Pot();

    static boolean playerIsSmallBlind; // true if player posts small blind
    static String whoPlays; // "player" or "bot"

    static boolean flopShowed;
    static boolean turnShowed;
    static boolean riverShowed;

    private Player player;
    private Bot bot;
    private GameState currentState;

    /**
     * Round constructor. Sets up the hand, deals cards, and starts the
     * first betting round.
     */
    public Round(Player player, Bot bot, double playerMoneyAmount,
                 double roundSmallBlind, double roundBigBlind, boolean wasPlayerSmallBlind) {

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

        // Alternate blinds each hand
        playerIsSmallBlind = !wasPlayerSmallBlind;

        giveCardsToUsers(player, bot);
        payBlinds(player, bot);

        currentState = GameState.PREFLOP;

        // Create the UI
        GameSetup.mat = new PokerMat(player.getWallet(), pot.getSmallBlind(), pot.getBigBlind());
        GameSetup.mat.newRoundButton.setEnabled(false);

        // Preflop: Small Blind acts first
        whoPlays = playerIsSmallBlind ? "player" : "bot";

        if (whoPlays.equals("bot")) {
            runBotTurn();
        }
    }

    /** Deals 2 cards to each player. */
    public static void giveCardsToUsers(Player player, Bot bot) {
        player.receiveFirstCard(deck.giveCard());
        player.receiveSecondCard(deck.giveCard());
        bot.receiveFirstCard(deck.giveCard());
        bot.receiveSecondCard(deck.giveCard());

        PokerMat.setCards(player.getCard1Path(), player.getCard2Path(),
                bot.getCard1Path(), bot.getCard2Path());
    }

    /** Posts blinds for both players and initializes pot raise. */
    void payBlinds(Player player, Bot bot) {
        if (playerIsSmallBlind) {
            System.out.println("Player is Small Blind");
            player.paySmallBlind();
            bot.payBigBlind();
        } else {
            System.out.println("Bot is Small Blind");
            bot.paySmallBlind();
            player.payBigBlind();
        }

        // Ensure pot.currentRaise reflects the Big Blind amount
        pot.potRaise(pot.getBigBlind());
    }

    /** Handles player's actions from UI. */
    public void playerActed(String action, double amount) {
        if (!whoPlays.equals("player") || currentState == GameState.HAND_OVER) return;

        boolean roundOver = false;

        switch (action) {
            case "fold":
                player.fold();
                endHand(bot);
                return;

            case "check":
                if (pot.getCurrentRaise() > player.getLastBet()) {
                    System.out.println("You cannot check; there is a bet to you.");
                    return;
                }
                player.check();
                if (player.getLastBet() == bot.getLastBet()) roundOver = true;
                break;

            case "call":
                double amountToCall = pot.getCurrentRaise() - player.getLastBet();
                if (amountToCall <= 0) {
                    System.out.println("Nothing to call.");
                    return;
                }
                player.call(pot.getCurrentRaise());
                roundOver = true;
                break;

            case "raise":
                if (amount < pot.getCurrentRaise() * 2) {
                    System.out.println("Raise must be at least 2x current bet.");
                    return;
                }
                player.raise(amount);
                pot.potRaise(amount);
                roundOver = false;
                break;
        }

        if (roundOver) {
            progressToNextStage();
        } else {
            whoPlays = "bot";
            runBotTurn();
        }
    }

    /** Bot AI turn handler. */
    private void runBotTurn() {
        if (!whoPlays.equals("bot") || currentState == GameState.HAND_OVER) return;

        System.out.println("Bot is thinking...");
        
        // Calculate the amount bot needs to call
        double amountToCall = player.getLastBet() - bot.getLastBet();
        
        // Bot's action logic is inside decideAction.
        // We assume bot's call/raise methods will update its wallet/lastBet
        // and ALSO update the pot (via a Wallet class or similar).
        // This is a crucial assumption based on your `Pot.java` auto-updating the UI.
        bot.decideAction(pot.getPotTotal(), amountToCall, communityCards);

        if (bot.hasFolded()) {
<<<<<<< HEAD
=======
            endHand(player); // Player wins
            return;
        }

        // Check if bot raised
        if (bot.getLastBet() > pot.getCurrentRaise()) {
            // Bot raised! Pot.currentRaise was updated by bot.raise()
            whoPlays = "player";
            System.out.println("Bot raised to " + bot.getLastBet() + ". Your turn.");
        
        } else if (bot.getLastBet() < pot.getCurrentRaise()) {
            // This should not happen (bot must at least call or fold)
            // But if it does, it's a fold.
            bot.fold();
>>>>>>> 3140feab2f6c04e127bf7d415f2c86f4b0259d3a
            endHand(player);
            return;
        }

        if (bot.getLastBet() > pot.getCurrentRaise()) {
            whoPlays = "player";
            System.out.println("Bot raised to " + bot.getLastBet() + ". Your turn.");
        } else if (bot.getLastBet() == pot.getCurrentRaise()) {
            System.out.println("Bot called or checked.");
            progressToNextStage();
        } else {
            bot.fold();
            endHand(player);
        }

        GameSetup.mat.updateWalletDisplay(GameSetup.mat.botMoneyDisplay); 
    }

    /** Progresses through game stages: Flop, Turn, River, Showdown. */
    private void progressToNextStage() {
        if (currentState == GameState.RIVER) {
            doShowdown();
            return;
        }

        pot.potRaise(0);
        player.setLastBet(0);
        bot.setLastBet(0);

        // Postflop: Small Blind acts first
        whoPlays = playerIsSmallBlind ? "player" : "bot";

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

    /** Deals the flop (3 community cards). */
    public static void giveFlop() {
        String[] cardPaths = new String[3];
        for (int i = 0; i < 3; i++) {
            Card c = deck.giveCard();
            communityCards.add(c);
            cardPaths[i] = c.getCardPath();
        }
        PokerMat.setFlop(cardPaths);
    }

    /** Deals the turn card. */
    public static void giveTurn() {
        Card c = deck.giveCard();
        communityCards.add(c);
        PokerMat.setTurn(c.getCardPath());
    }

    /** Deals the river card. */
    public static void giveRiver() {
        Card c = deck.giveCard();
        communityCards.add(c);
        PokerMat.setRiver(c.getCardPath());
    }

    /** Handles showdown and determines winner. */
    private void doShowdown() {
        currentState = GameState.SHOWDOWN;
        System.out.println("--- SHOWDOWN ---");
        GameSetup.mat.showBotCards();

        System.out.println("Bot has: " + bot.getFirstCard().getRank() + " " + bot.getFirstCard().getSuit() + ", "
                + bot.getSecondCard().getRank() + " " + bot.getSecondCard().getSuit());

        HandEvaluation eval = new HandEvaluation(communityCards);
        Player winner = eval.winner(player, bot);

        endHand(winner);
    }

    /** Ends hand, awards pot, updates UI. */
    private void endHand(Player winner) {
        currentState = GameState.HAND_OVER;
        double totalPot = pot.getPotTotal();

        if (winner == player) {
            System.out.println("Player wins the pot of " + totalPot);
            player.setWallet(player.getWallet() + totalPot);
        } else if (winner == bot) {
            System.out.println("Bot wins the pot of " + totalPot);
            bot.setWallet(bot.getWallet() + totalPot);
        } else {
            System.out.println("It's a tie! Splitting the pot of " + totalPot);
            player.setWallet(player.getWallet() + totalPot / 2);
            bot.setWallet(bot.getWallet() + totalPot / 2);
        }

        GameSetup.mat.updateWalletDisplay(GameSetup.mat.moneyDisplay);
        GameSetup.mat.updateWalletDisplay(GameSetup.mat.botMoneyDisplay);
        GameSetup.mat.newRoundButton.setEnabled(true);
    }

    /** Outputs community cards to console. */
    public void outputCommunityCards() {
        System.out.print("Community Cards:");
        for (Card c : communityCards) {
            System.out.print(" [" + c.getRank() + " " + c.getSuit() + "]");
        }
        System.out.println();
    }

    static boolean getPlayerFirst() {
        return playerIsSmallBlind;
    }
}