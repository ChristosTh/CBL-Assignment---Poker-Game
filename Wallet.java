/** Wallet class for user and bots. */
public class Wallet extends Pot {
    private int wallet = 0; 
    private int lastBet = 0; 

    public Wallet(int x) {
        wallet = x; 
        lastBet = 0; 
    }

    /** in main method check if raise is possible and then run Pot.raised */
    boolean actionRaise(int x) {
        if (x < wallet) {
            wallet -= x; 
            lastBet = x; 
            potRaise(x); 
            return true; 
        }
        return false;
    }

    /** Method to call on a raise. */
    boolean actionCall(int currentRaise) {
        int betDifference = currentRaise - lastBet; 
        if (betDifference < wallet) {
            wallet -= betDifference; 
            lastBet += betDifference; 
            potCall(betDifference); 
            return true; 
        }
        return false; 
    }

    boolean allIn() {
        return false;
    }
}