/** Wallet class for user and bots. */
public class Wallet extends Pot {
    private double wallet = 0; 
    private double lastBet = 0; 

    /** When asking user how much money they want to play with, 
     *  also ask what should the small blind and the big blind be. */
    public Wallet() { 
        lastBet = 0; 
    }

    void setWallet(double cash) {
        wallet = cash; 
    }

    double getWallet() {
        return wallet; 
    }

    /** in main method check if raise is possible and then run Pot.raised */
    boolean actionRaise(double x) {
        if (x < wallet) {
            wallet -= x; 
            lastBet = x; 
            Poker.pot.potRaise(x); 
            return true; 
        }
        return false;
    }

    /** Method to call on a raise. */
    boolean actionCall(double newRaise) {
        double betDifference = newRaise - lastBet; 
        if (betDifference < wallet) {
            wallet -= betDifference; 
            lastBet += betDifference; 
            Poker.pot.potCall(betDifference); 
            return true; 
        }
        return false; 
    }

    /** Method to go all in. */
    boolean allIn() {
        if (wallet > 0) {
            lastBet += wallet; 
            Poker.pot.potAllIn(wallet); 
            wallet = 0; 
            return true; 
        }
        return false; 
    }

    /** Method for the small blind, if the player can't afford the small blind, 
     *  they are forced to go all-in.  */
    boolean smallBlind() {
        if (wallet >= getSmallBlind()) {
            wallet -= getSmallBlind(); 
            Poker.pot.potSmallBlind(); 
            return true; 
        }
        allIn(); 
        return false;
    }

    /** Method for the big blind, if a player's stack is bigger 
     * than small blind but smaller than the big blind, they go all-in 
     * as long as they aren't the current small blind. 
     */
    boolean bigBlind() {
        if (wallet >= getBigBlind()) {
            wallet -= getBigBlind(); 
            Poker.pot.potBigBlind(); 
            return true; 
        }
        allIn(); 
        return false; 
    }

}