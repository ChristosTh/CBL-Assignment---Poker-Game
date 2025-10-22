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

    public double getLastBet() {
        return lastBet;
    }

    /** in main method check if raise is possible and then run Pot.raised */
    boolean actionRaise(double x) {
        if (x < wallet) {
            wallet -= x; 
            lastBet = x; 
            Round.pot.potRaise(x); 
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
            Round.pot.potCall(betDifference); 
            return true; 
        }
        return false; 
    }

    /** Method to go all in. */
    boolean actionAllIn() {
        if (wallet > 0) {
            lastBet += wallet; 
            Round.pot.potAllIn(wallet); 
            wallet = 0; 
            return true; 
        }
        return false; 
    }

    /** Method for the small blind, if the player can't afford the small blind, 
     *  they are forced to go all-in.  */
    boolean actionSmallBlind() {
        if (wallet >= getSmallBlind()) {
            wallet -= getSmallBlind(); 
            Round.pot.potSmallBlind(); 
            return true; 
        }
        actionAllIn(); 
        return false;
    }

    /** Method for the big blind, if a player's stack is bigger 
     * than small blind but smaller than the big blind, they go all-in 
     * as long as they aren't the current small blind. 
     */
    boolean actionBigBlind() {
        if (wallet >= getBigBlind()) {
            wallet -= getBigBlind(); 
            Round.pot.potBigBlind(); 
            return true; 
        }
        actionAllIn(); 
        return false; 
    }

}