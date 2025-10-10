import java.util.ArrayList;

/** Pot class to sum and keep track of all the bets. */
public class Pot {
    private int potTotal = 0; 
    private int sBlind = 0; 
    private int bBlind = 0; 
    private int currentRaise = 0; 
    ArrayList<Wallet> bets = new ArrayList<>(); // ArrayList to keep track of the user's last bet. 

    /** Pot constructor. */
    public Pot() {
        currentRaise = 0; 
        // give values later to sBlind & bBlind.
        sBlind = 0; 
        bBlind = 0; 
        potTotal = sBlind + bBlind; 
    }

    /** in main method check if raise is possible and then run Pot.raised */ 
    int potRaise(int x) {
        potTotal += x; 
        currentRaise = x; 
        return potTotal; 
    }

    /** Call method for pot. */
    int potCall(int x) {
        potTotal += x; 
        return potTotal; 
    }

    /** Method to add small blind to the pot. */
    int smallBlind(int x) {
        potTotal += x; 
        return potTotal;
    }

    /** Method to add big blind to the pot. */
    int bigBlind(int x) {
        potTotal += x; 
        return potTotal; 
    }
}