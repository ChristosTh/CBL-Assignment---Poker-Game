/** Class for round. */
public class Round {

    /** Method to start the round, paying the blinds. */
    void blinds() {
        if (Poker.blindsTrack.get(0) == 1) {
            Poker.player.wallet.smallBlind(); 
        }
    }

    /** Method to start the flop part of the round. */
    void flop() {
        
    }
}
