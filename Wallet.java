/** Wallet class for user and bots. */
public class Wallet extends Pot {
    private int wallet = 0; 
    private int lastBet = 0; 

    /** When asking user how much money they want to play with, 
     *  also ask what should the small blind and the big blind be. */
    public Wallet(int x, int smallBlind, int bigBlind) {
        super(smallBlind, bigBlind); 
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

    /** Method to go all in. */
    boolean allIn() {
        if (wallet > 0) {
            lastBet += wallet; 
            potAllIn(wallet); 
            wallet = 0; 
            return true; 
        }
        return false; 
    }
}