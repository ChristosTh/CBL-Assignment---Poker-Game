import java.util.ArrayList;
import java.util.Arrays;

/** Class to determing te power of player's hand. */
public class HandEvaluation {

    ArrayList<Card> cardsToCheck;

    private int[] playerCardsRank;
    private int highestIndex;

    private int[] playersCardsSuit;

    /** Stores the ranks of the cards in order. */
    private static final char[] RANK_ORDER = 
        // T is 10 - just to use char
        {'A', '2', '3', '4', '5', '6', '7', '8', '9', 'T', 'J', 'Q', 'K', 'A'};
    private static final String[] SUITS = {"hearts", "diamonds", "clubs", "spades"};

    /** Calculates the integer value for the power of the card. */
    private int getRankValue(char rank) {
        for (int i = 0; i < RANK_ORDER.length; i++) {
            if (RANK_ORDER[i] == rank) {
                return i;
            }
        }
        return -1; // should never happen if cards are valid
    }

    /** Returns an int[13] with counts of each rank. */
    private int[] getRankCounts() {
        int[] counts = new int[RANK_ORDER.length];
        for (Card c : cardsToCheck) {
            int idx = getRankValue(c.getRank());
            counts[idx]++;
        }
        return counts;
    }

    /** Returns sorted numeric rank indices. */
    private int[] getSortedRanks() {
        int[] values = new int[cardsToCheck.size()];
        for (int i = 0; i < cardsToCheck.size(); i++) {
            values[i] = getRankValue(cardsToCheck.get(i).getRank());
        }
        Arrays.sort(values);
        return values;
    }

    /** Returns an int[4] with counts of each suit. */
    private int[] getSuitCounts() {
        int[] counts = new int[SUITS.length];
        for (Card c : cardsToCheck) {
            for (int i = 0; i < SUITS.length; i++) {
                if (c.getSuit().equals(SUITS[i])) {
                    counts[i]++;
                }
            }
        }
        return counts;
    }

    
    /** Constructor to know what cards we need to check. */
    public HandEvaluation(ArrayList<Card> cardsToCheck) {
        this.cardsToCheck = new ArrayList<Card>();
        this.cardsToCheck.clear();
        this.cardsToCheck.addAll(cardsToCheck);
        playerCardsRank = getSortedRanks();
        highestIndex = playerCardsRank[playerCardsRank.length - 1];
        playersCardsSuit = getSuitCounts();
    }

    /** Method to calculate which player has the better comp. */
    public int cardsCalculation() {
        if (isPair()) {
            return 1;
        } else if (isTwoPair()) {
            return 2;
        } else if (isThreeKind()) {
            return 3;
        } else if (isStraight()) {
            return 4;
        } else if (isFlush()) {
            return 5;
        } else if (isFullHouse()) {
            return 6;
        } else if (isFourKind()) {
            return 7;
        } else if (isStraightFlush()) {
            return 8;
        } else if (isRoyalFlush()) {
            return 9;
        } else {
            return 0;
        }

    }

    /** Function to check if we have a royal flush. */
    public boolean isRoyalFlush() {
        
        if (cardsToCheck.size() == 5) {
            return isFlush() && isStraight() && playerCardsRank[0] == 'T';
        } else if (cardsToCheck.size() == 6) {
            return isFlush() && isStraight() && playerCardsRank[1] == 'T';
        } else {
            return isFlush() && isStraight() && playerCardsRank[2] == 'T';
        }

    }

    public boolean isStraightFlush() {
        return isFlush() && isStraight() && !isRoyalFlush();
    }   

    /** Function to check if we have four of a kind. */
    public boolean isFourKind() {

        int[] rankCount = getRankCounts();

        for (int i = 0; i < rankCount.length; i++) {
            if (rankCount[i] == 4) {
                return true;
            }
        }

        return false;
    }

    /** Function to check if we have a full house. */
    public boolean isFullHouse() {
        return isThreeKind() && isTwoPair();
    }

    /** Function to check if we have a flush. */
    public boolean isFlush() {
        
        for (int count : playersCardsSuit) {
            if (count == 5) {
                return true;
            }
        }

        return false;

    }

    /** Function to check if we have a straight. */
    public boolean isStraight() {

        int consecutiveCounter = 0;

        for (int i = 0; i < playerCardsRank.length - 1; i++) {
            if (playerCardsRank[i + 1] - playerCardsRank[i] == 1) {
                consecutiveCounter++;
            } else if (consecutiveCounter != 5) {
                consecutiveCounter = 0;
            }
        }

        if (consecutiveCounter == 5) {
            return true;
        }
        return false;
    }

    /** Function to check if we have three of a kind. */
    public boolean isThreeKind() {
        
        int[] rankCount = getRankCounts();

        for (int i = 0; i < rankCount.length; i++) {
            if (rankCount[i] == 3) {
                return true;
            }
        }

        return false;

    }

    /** Function to check if we have a 2 pair. */
    public boolean isTwoPair() {

        int[] rankCount = getRankCounts();
        int pairs = 0;

        for (int i = 0; i < rankCount.length; i++) {
            if (rankCount[i] == 2 && i != 0) {
                pairs++;
            }
        }

        return pairs == 2;
    }

    /** Function to check if we just have a pair. */
    public boolean isPair() {

        int[] rankCount = getRankCounts();
        int pairs = 0;

        for (int i = 0; i < rankCount.length; i++) {
            if (rankCount[i] == 2 && i != 0) {
                pairs++;
            }
        }

        return pairs == 1;

    }

    /** Function to check if just have a high card. */
    public Player hasHigherCard(Player player1, Player player2) {

        int player1HighestCardInt = 0;
        int player2HighestCardInt = 0;

        for (int i = 1; i < RANK_ORDER.length; i++) {
            if (RANK_ORDER[i] == player1.getFirstCard().getRank()) {
                player1HighestCardInt = i;
            }
        }

        for (int i = 1; i < RANK_ORDER.length; i++) {
            if (RANK_ORDER[i] == player1.getSecondCard().getRank()) {
                if (i > player1HighestCardInt) {
                    player1HighestCardInt = i;
                }
            }
        }

        for (int i = 1; i < RANK_ORDER.length; i++) {
            if (RANK_ORDER[i] == player2.getFirstCard().getRank()) {
                player1HighestCardInt = i;
            }
        }

        for (int i = 1; i < RANK_ORDER.length; i++) {
            if (RANK_ORDER[i] == player2.getSecondCard().getRank()) {
                if (i > player1HighestCardInt) {
                    player1HighestCardInt = i;
                }
            }
        }


        if (player1HighestCardInt < player2HighestCardInt) {
            return player1;
        } else {
            return player2;
        }

    }

    /** Function to determine the winner. */
    public Player winner(Player player1, Player player2) {

        cardsToCheck.add(player1.getFirstCard());
        cardsToCheck.add(player1.getSecondCard());

        int player1Calc = cardsCalculation();

        cardsToCheck.removeLast();
        cardsToCheck.removeLast();

        cardsToCheck.add(player2.getFirstCard());
        cardsToCheck.add(player2.getSecondCard());

        int player2Calc = cardsCalculation();

        cardsToCheck.removeLast();
        cardsToCheck.removeLast();

        if (player1Calc > player2Calc) {
            return player1;
        } else if (player2Calc > player1Calc) {
            return player2;
        } else {
            return hasHigherCard(player1, player2);
        }

    }

}