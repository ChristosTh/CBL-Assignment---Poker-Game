/** Class for a player of poker and everything that a player needs to play. */
public class Player {

    private Wallet wallet;
    private Card firstCard;
    private Card secondCard;
    private boolean hasFolded = false;

    /** Player constructor to initalize its wallet and pair of cards to null. */
    public Player() {
        wallet = new Wallet(); 
        firstCard = null;
        secondCard = null;
    }
    
    public double getLastBet() {
        return wallet.getLastBet();
    }

    public double getWallet() {
        return wallet.getWallet(); 
    }

    public void setLastBet(double lastBet) {
        wallet.setLastBet(lastBet);
    }

    public void setWallet(double cash) {
        wallet.setWallet(cash); 
    }

    /** method to get one of the two cards. */
    public void receiveFirstCard(Card card) {
        firstCard = card;
    }

    /** method to get the other of the two cards. */
    public void receiveSecondCard(Card card) {
        secondCard = card;
    }

    /** method to clear the cards. */
    public void clearCards() {
        firstCard = null;
        secondCard = null;
    }

    /** method to return the first of the player's/bot's two cards. */
    public Card getFirstCard() {
        return firstCard;
    }

    /* method to get the filePath of the card to be able to display it. */
    public String getCard1Path() {
        return firstCard.getCardPath(); 
    }

    /** Function to get the other of the two cards. */
    public Card getSecondCard() {
        return secondCard;
    }
    
    /** Same purpose as getCard1Path but for the second card. */
    public String getCard2Path() {
        return secondCard.getCardPath(); 
    }

    /** Method for player to raise. */
    public void raise(double x) {
        wallet.actionRaise(x);
    }

    /** Method for player to call. */
    public void call(double currentRaise) {
        wallet.actionCall(currentRaise); 
        System.out.println(wallet.getWallet()); 
    }

    /** Calls wallet.actionSmallBlind() which correctly pays the small blind. */
    public void paySmallBlind() {
        wallet.actionSmallBlind(); 
    }

    /** same as paySmallBlind() but for the big blind. */
    public void payBigBlind() {
        wallet.actionBigBlind();
    }

    public boolean hasFolded() {
        return hasFolded;
    }

}