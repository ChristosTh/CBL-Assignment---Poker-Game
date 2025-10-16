/** Pot class to sum and keep track of all the bets. */
public class Pot {
    private double potTotal = 0; 
    private double smallBlind = 0; 
    private double bigBlind = 0; 
    private double currentRaise = 0; 

    /** Method to set blinds. */
    void setBlinds(double s, double b) {
        smallBlind = s; 
        bigBlind = b; 
    }

    /** Pot constructor. */
    public Pot() {
        currentRaise = 0; 
    }

    /** Raise method for pot. */ 
    double potRaise(double x) {
        potTotal += x; 
        currentRaise = x; 
        return potTotal; 
    }

    /** Call method for pot. */
    double potCall(double x) {
        potTotal += x; 
        return potTotal; 
    }

    /** All in method for pot.  */
    double potAllIn(double x) {
        if (x > currentRaise) {
            currentRaise = x; 
        } 
        potTotal += x; 
        return potTotal; 

    }

    /** Method to add small blind to the pot. */
    double smallBlind(double x) {
        potTotal += x; 
        return potTotal;
    }

    /** Method to add big blind to the pot. */
    double bigBlind(double x) {
        potTotal += x; 
        return potTotal; 
    }
}