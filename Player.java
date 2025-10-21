/** Class for a player of poker and everything that a player needs to play. */
public class Player {

    private Wallet wallet;
    private Card firstCard;
    private Card secondCard;

    /** Player constructor to initalize its wallet and pair of cards to null. */
    public Player() {
        wallet = new Wallet(); 
        // the 0's above are placeholders for when we will actually ask user smallBlind & bigBlind.
        firstCard = null;
        secondCard = null;
    }
    
    public double getLastBet() {
        return wallet.getLastBet();
    }

    public double getWallet() {
        return wallet.getWallet(); 
    }

    public void setWallet(double cash) {
        wallet.setWallet(cash); 
    }

    /** Procedure to get one of the two cards. */
    public void receiveFirstCard(Card card) {
        firstCard = card;
    }

    /** Procedure to get the other of the two cards. */
    public void receiveSecondCard(Card card) {
        secondCard = card;
    }

    /** Procedure to clear the cards. */
    public void clearCards() {
        firstCard = null;
        secondCard = null;
    }

    /** Function to get on the two cards. */
    public Card getFirstCard() {
        return firstCard;
    }

    public String getCard1Path() {
        return firstCard.getCardPath(); 
    }

    /** Function to get the other of the two cards. */
    public Card getSecondCard() {
        return secondCard;
    }
    
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

    /** Method for player to go all in. */
    public void allIn() {
        if (wallet.getWallet() > 0) {
            wallet.actionAllIn(); 
        }
    }

    public void smallBlind() {
        wallet.actionSmallBlind(); 
    }

    public void bigBlind() {
        wallet.actionBigBlind();
    }

    public void fold() {
        
    }

    public void check() {

    }

}