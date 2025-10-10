import java.util.ArrayList;
import java.util.Arrays;

/** Class to determing te power of player's hand. */
public class HandEvaluation {

    ArrayList<Card> cardsToCheck;

    private int[] playerCardsRank = getSortedRanks();
    private int highestIndex = playerCardsRank[playerCardsRank.length - 1];

    private int[] playersCardsSuit = getSuitCounts();

    /** Stores the ranks of the cards in order. */
    private static final char[] RANK_ORDER = 
        // T is 10 - just to use char
        {'A', '2', '3', '4', '5', '6', '7', '8', '9', 'T', 'J', 'Q', 'K', 'A'};
    private static final String[] SUITS = {"Hearts", "Diamonds", "Clubs", "Spades"};

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
        this.cardsToCheck.addAll(cardsToCheck);
    }

    public boolean isRoyalFlush() {
        return isFlush() && isStraight() && playerCardsRank[0] == 'T';
    }

    public boolean isStraightFlush() {
        return isFlush() && isStraight() && !isRoyalFlush();
    }   

    /** Function to check if we have four of a kind. */
    public boolean isFourKind() {

        int[] rankCount = getRankCounts();
        
        for (int count : rankCount) {
            if (count == 3) {
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

        boolean consecutive = true;

        for (int i = 0; i < playerCardsRank.length - 1; i++) {
            if (playerCardsRank[i + 1] - playerCardsRank[i] != 1) {
                consecutive = false;
                break;
            }
        }

        return consecutive;
    }

    /** Function to check if we have three of a kind. */
    public boolean isThreeKind() {
        
        int[] rankCount = getRankCounts();
        
        for (int count : rankCount) {
            if (count == 3) {
                return true;
            }
        }

        return false;

    }

    /** Function to check if we have a 2 pair. */
    public boolean isTwoPair() {

        int[] rankCount = getRankCounts();
        int pairs = 0;

        for (int count : rankCount) {
            if (count == 2) {
                pairs++;
            }
        }

        return pairs == 2;
    }

    /** Function to check if we just have a pair. */
    public boolean isPair() {

        int[] rankCount = getRankCounts();
        int pairs = 0;

        for (int count : rankCount) {
            if (count == 2) {
                pairs++;
            }
        }

        return pairs == 1;

    }

    /** Function to check if just have a high card. */
    public boolean isHighCard() {

        if (isPair() || isTwoPair() || isThreeKind() || isStraight() ||
            isFlush() || isFullHouse() || isFourKind() || isStraightFlush() || isRoyalFlush()) {
            return false;
        }

        //char highCard = RANK_ORDER[highestIndex];
        //System.out.println("High card: " + highCard);
        return false; // need to identify what a high card is

    }

}
