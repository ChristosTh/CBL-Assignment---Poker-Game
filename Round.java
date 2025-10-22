/** Class for round. */
public class Round {

    public Round() {
        Poker.deck = new Deck(); 
        Poker.deck.shuffleCards(); 
        Poker.giveCardsToUsers(); 
        Poker.giveFlop(); 

    }

    /** Method to start the round, paying the blinds. */
    void blinds() {
        if (Poker.blindsTrack.get(0) == 1) {
            Poker.player.smallBlind(); 
            Poker.bot.bigBlind(); 
            while (Poker.player.getLastBet() < Poker.bot.getLastBet()) { 
                System.out.println("not fulfilled."); 
            }
            flop(); 
        } else {
            Poker.player.bigBlind(); 
            Poker.bot.bigBlind(); 
            flop(); 
        }
    }

    /** Method to start the flop part of the round (first 3 community cards). */
    void flop() {

    }

    /** Method to start the turn part of the round (4th community card). */
    void turn() {

    }

    /** Method to start the river part of the round (5th community card). */
    void river() {

    }

}
