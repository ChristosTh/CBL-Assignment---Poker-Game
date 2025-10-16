import java.util.ArrayList;
import java.util.Arrays;

/** Class to determine the power of a player's hand. */
public class HandEvaluation {

    ArrayList<Card> cardsToCheck;

    private int[] playerCardsRank;
    private int highestIndex;

    private int[] playersCardsSuit;

    /** Stores the ranks of the cards in order. */
    private static final char[] RANK_ORDER = 
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
        int[] values = new int[cardsToCheck.size() + 1];
        for (int i = 0; i < cardsToCheck.size(); i++) {
            values[i] = getRankValue(cardsToCheck.get(i).getRank());

            if (getRankValue(cardsToCheck.get(i).getRank()) == 0) {
                values[values.length - 1] = 13;
            }
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

    /** Method to calculate which player has the better hand. */
    public int cardsCalculation() {
        if (isRoyalFlush()) {
            return 9;
        } else if (isStraightFlush()) {
            return 8;
        } else if (isFourKind()) {
            return 7;
        } else if (isFullHouse()) {
            return 6;
        } else if (isFlush()) {
            return 5;
        } else if (isStraight()) {
            return 4;
        } else if (isThreeKind()) {
            return 3;
        } else if (isTwoPair()) {
            return 2;
        } else if (isPair()) {
            return 1;
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
        if (isFlush() && isStraight() && !isRoyalFlush()) {
            return true;
        } else {
            return false;
        }
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
        if (isThreeKind() && isTwoPair()) {
            return true;
        } else {
            return false;
        }
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
        } else {
            return false;
        }
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
            if (rankCount[i] >= 2 && i != 0) {
                pairs++;
            }
        }
        if (pairs == 2) {
            return true;
        } else {
            return false;
        }
    }

    /** Function to check if we just have a pair. */
    public boolean isPair() {
        int[] rankCount = getRankCounts();
        int pairs = 0;
        for (int i = 0; i < rankCount.length; i++) {
            if (rankCount[i] == 2 && i != rankCount.length - 1) {
                pairs++;
            }
        }
        if (pairs == 1) {
            return true;
        } else {
            return false;
        }
    }

    /** Determines the winner between two players. */
    public Player winner(Player player1, Player player2) {
        ArrayList<Card> fullHand1 = new ArrayList<>(cardsToCheck);
        fullHand1.add(player1.getFirstCard());
        fullHand1.add(player1.getSecondCard());
        int player1Score = new HandEvaluation(fullHand1).cardsCalculation();

        ArrayList<Card> fullHand2 = new ArrayList<>(cardsToCheck);
        fullHand2.add(player2.getFirstCard());
        fullHand2.add(player2.getSecondCard());
        int player2Score = new HandEvaluation(fullHand2).cardsCalculation();

        if (player1Score > player2Score) {
            return player1;
        } else if (player2Score > player1Score) {
            return player2;
        } else {
            return breakTie(fullHand1, fullHand2, player1, player2, player1Score);
        }
    }

    /** Break tie based on hand strength. */
    private Player breakTie(ArrayList<Card> hand1, ArrayList<Card> hand2, Player p1, Player p2, int handType) {
        int[] counts1 = getRankCountsFromHand(hand1);
        int[] counts2 = getRankCountsFromHand(hand2);

        if (handType == 1 || handType == 2) {
            return compareMultiples(counts1, counts2, p1, p2);
        } else if (handType == 3 || handType == 6 || handType == 7) {
            return compareSet(counts1, counts2, p1, p2, handType);
        } else if (handType == 4 || handType == 8) {
            return compareStraightHighCard(hand1, hand2, p1, p2);
        } else {
            return compareHighCards(hand1, hand2, p1, p2);
        }
    }

    private int[] getRankCountsFromHand(ArrayList<Card> hand) {
        int[] counts = new int[RANK_ORDER.length];
        for (Card c : hand) {
            int idx = getRankValue(c.getRank());
            counts[idx]++;
        }
        return counts;
    }

    private Player compareMultiples(int[] c1, int[] c2, Player p1, Player p2) {
        for (int i = c1.length - 1; i >= 0; i--) {
            if (c1[i] >= 2 && c2[i] < 2) {
                return p1;
            }
            if (c2[i] >= 2 && c1[i] < 2) {
                return p2;
            }
            if (c1[i] >= 2 && c2[i] >= 2) {
                if (i > 0) {
                    return p1;
                } else {
                    return p2;
                }
            }
        }
        return compareHighCardsFromCounts(c1, c2, p1, p2);
    }

    private Player compareSet(int[] c1, int[] c2, Player p1, Player p2, int handType) {
        int target;
        if (handType == 7) {
            target = 4;
        } else {
            target = 3;
        }

        for (int i = c1.length - 1; i >= 0; i--) {
            if (c1[i] == target && c2[i] != target) {
                return p1;
            }
            if (c2[i] == target && c1[i] != target) {
                return p2;
            }
            if (c1[i] == target && c2[i] == target) {
                if (i > 0) {
                    return p1;
                } else {
                    return p2;
                }
            }
        }
        return compareHighCardsFromCounts(c1, c2, p1, p2);
    }

    private Player compareStraightHighCard(ArrayList<Card> h1, ArrayList<Card> h2, Player p1, Player p2) {
        int max1 = getSortedRanksFromHand(h1)[h1.size() - 1];
        int max2 = getSortedRanksFromHand(h2)[h2.size() - 1];

        if (max1 > max2) {
            return p1;
        } else if (max2 > max1) {
            return p2;
        } else {
            return null;
        }
    }

    private Player compareHighCards(ArrayList<Card> h1, ArrayList<Card> h2, Player p1, Player p2) {
        int[] r1 = getSortedRanksFromHand(h1);
        int[] r2 = getSortedRanksFromHand(h2);
        for (int i = r1.length - 1; i >= 0; i--) {
            if (r1[i] > r2[i]) {
                return p1;
            } else if (r2[i] > r1[i]) {
                return p2;
            }
        }
        return null;
    }

    private Player compareHighCardsFromCounts(int[] c1, int[] c2, Player p1, Player p2) {
        for (int i = c1.length - 1; i >= 0; i--) {
            if (c1[i] > 0 && c2[i] == 0) {
                return p1;
            }
            if (c2[i] > 0 && c1[i] == 0) {
                return p2;
            }
        }
        return null;
    }

    private int[] getSortedRanksFromHand(ArrayList<Card> hand) {
        int[] values = new int[hand.size()];
        for (int i = 0; i < hand.size(); i++) {
            int val = getRankValue(hand.get(i).getRank());
            if (val == 0) {
                values[i] = 13;
            } else {
                values[i] = val;
            }
        }
        Arrays.sort(values);
        return values;
    }
}
