/** Main class for poker. */
public class Poker {
    Pot pot = new Pot(); 
    Player player;
    Deck deck = new Deck();

    public void giveCardsToUser() {
        player.receiveFirstCard(deck.giveCard());
        player.receiveSecondCard(deck.giveCard());
    }

    public static void main(String[] args) {
        // console asks how much money


    }
}
