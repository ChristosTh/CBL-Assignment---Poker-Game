
/** TO-DO: Complete the small blind and big blind functions.  */



/** Pot class to sum and keep track of all the bets. */
public class Pot {
    private int potTotal = 0; 
    private int smallBlind = 0; 
    private int bigBlind = 0; 
    private int currentRaise = 0; 

    /** Pot constructor. */
    public Pot(int smallBlind, int bigBlind) {
        currentRaise = 0; 
        // give values later to sBlind & bBlind.
        this.smallBlind = smallBlind; 
        this.bigBlind = bigBlind; 
    }

    /** Raise method for pot. */ 
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

    /** All in method for pot.  */
    int potAllIn(int x) {
        if (x > currentRaise) {
            currentRaise = x; 
        } 
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