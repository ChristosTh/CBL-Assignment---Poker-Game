/** Wallet class for user. */
public class Wallet {
    private int wallet = 0; 

    public Wallet(int x) {
        wallet = x; 
    }

    /** in main method check if raise is possible and then run Pot.raised */
    boolean raise(int x) {
        if (x < wallet) {
            wallet -= x; 
            return true; 
        }
        return false;
    }

    /** Method to call on a raise. */
    boolean call(int currentRaise) {
        if (currentRaise < wallet) {
            wallet -= currentRaise; 
            return true; 
        }
        return false; 
    }

    boolean allIn() {
        return false;
    }
}