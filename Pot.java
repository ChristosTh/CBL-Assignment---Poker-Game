/** Pot class to sum and keep track of all the bets. */
public class Pot {
    private int potTotal = 0; 
    private int sBlind = 0; 
    private int bBlind = 0; 

    /** Pot constructor. */
    public Pot() {
        potTotal = 0; 

        // give values later to sBlind & bBlind.
        sBlind = 0; 
        bBlind = 0; 
    }

    /** in main method check if raise is possible and then run Pot.raised */ 
    int raised(int x) {
        potTotal += x; 
        return potTotal; 
    }

}