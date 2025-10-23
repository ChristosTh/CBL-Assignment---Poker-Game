/** Pot class to sum and keep track of all the bets. */
public class Pot {
    private double potTotal = 0; 
    private double smallBlind = 0; 
    private double bigBlind = 0; 
    private double currentRaise = 0;

    /** Pot constructor. */
    public Pot() {
        currentRaise = 0; 
    }

    double getCurrentRaise() {
        return currentRaise; 
    }

    /** Method to set blinds. */
    void setBlinds(double s, double b) {
        smallBlind = s; 
        bigBlind = b; 
    }

    double getSmallBlind() {
        return smallBlind;
    }
    
    double getBigBlind() {
        return bigBlind;
    }


    /** Raise method for pot. */ 
    double potRaise(double x) {
        potTotal += x; 
        currentRaise = x; 
        System.out.println("1: " + currentRaise); 
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
    void addSmallBlind() {
        potTotal += smallBlind;
    }

    /** Method to add big blind to the pot. */
    void addBigBlind() {
        potTotal += bigBlind; 
    }

    double getPotTotal() {
        return potTotal;
    }
}