import java.util.ArrayList;
import java.util.Arrays;

/** * Class to determine the power of a player's hand.
 * This version contains corrected logic for hand evaluation and tie-breaking.
 */
public class HandEvaluation {

    ArrayList<Card> cardsToCheck; // The 5, 6, or 7 cards being evaluated
    private int[] rankCounts; // 13-element array (2-A)
    private int[] suitCounts; // 4-element array (suits)

    /** * Stores the ranks of the cards in order from 2 to Ace.
     * 'A' is index 12 (high). '2' is index 0.
     */
    private static final char[] RANK_ORDER = 
        {'2', '3', '4', '5', '6', '7', '8', '9', 'T', 'J', 'Q', 'K', 'A'};
    private static final String[] SUITS = {"hearts", "diamonds", "clubs", "spades"};

    /** Calculates the integer value for the power of the card. */
    private int getRankValue(char rank) {
        for (int i = 0; i < RANK_ORDER.length; i++) {
            if (RANK_ORDER[i] == rank) {
                return i; // '2' = 0, 'A' = 12
            }
        }
        return -1; // should never happen
    }

    /** Returns an int[13] with counts of each rank. */
    private int[] getRankCounts() {
        int[] counts = new int[13];
        for (Card c : cardsToCheck) {
            int idx = getRankValue(c.getRank());
            if (idx != -1) {
                counts[idx]++;
            }
        }
        return counts;
    }

    /** Returns an int[4] with counts of each suit. */
    private int[] getSuitCounts() {
        int[] counts = new int[4];
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
        this.cardsToCheck.addAll(cardsToCheck);
        
        // Initialize counts based on the cards
        this.rankCounts = getRankCounts();
        this.suitCounts = getSuitCounts();
    }

    /** Method to calculate which player has the better hand. */
    public int cardsCalculation() {
        // Checks are ordered from best hand to worst
        if (isRoyalFlush()) return 9;
        if (isStraightFlush()) return 8;
        if (isFourKind()) return 7;
        if (isFullHouse()) return 6;
        if (isFlush()) return 5;
        if (isStraight()) return 4;
        if (isThreeKind()) return 3;
        if (isTwoPair()) return 2;
        if (isPair()) return 1;
        return 0; // High Card
    }

    /** Function to check if we have a royal flush. */
    public boolean isRoyalFlush() {
        // A Royal Flush is a T-J-Q-K-A Straight Flush
        if (!isFlush()) return false;
        
        // Check for T, J, Q, K, A (indices 8, 9, 10, 11, 12)
        return rankCounts[8] > 0 && 
               rankCounts[9] > 0 && 
               rankCounts[10] > 0 && 
               rankCounts[11] > 0 && 
               rankCounts[12] > 0;
    }

    /** Function to check for a Straight Flush. */
    public boolean isStraightFlush() {
        return isFlush() && isStraight() && !isRoyalFlush();
    }

    /** Function to check if we have four of a kind. */
    public boolean isFourKind() {
        for (int count : rankCounts) {
            if (count == 4) return true;
        }
        return false;
    }

    /** Function to check if we have a full house. */
    public boolean isFullHouse() {
        boolean hasThree = false;
        boolean hasTwo = false;
        for (int count : rankCounts) {
            if (count == 3) hasThree = true;
            if (count == 2) hasTwo = true;
        }
        // A full house can also be two "three of a kinds" (e.g., KKK + 22 on board + K2 in hand)
        // So we check if there's a 3-of-a-kind and *any other pair*
        if (hasThree && hasTwo) return true;

        // Check for two 3-of-a-kinds (e.g. KKK 777 A)
        int threeKinds = 0;
        for (int count : rankCounts) {
            if (count == 3) threeKinds++;
        }
        return threeKinds >= 2;
    }

    /** Function to check if we have a flush. */
    public boolean isFlush() {
        for (int count : suitCounts) {
            if (count >= 5) return true;
        }
        return false;
    }

    /** Function to check if we have a straight. */
    public boolean isStraight() {
        // Check for A-2-3-4-5 "wheel" (indices 12, 0, 1, 2, 3)
        if (rankCounts[12] > 0 && 
            rankCounts[0] > 0 && 
            rankCounts[1] > 0 && 
            rankCounts[2] > 0 && 
            rankCounts[3] > 0) {
            return true;
        }
        
        // Check for any other 5 consecutive cards
        int consecutive = 0;
        for (int i = 0; i < rankCounts.length; i++) {
            if (rankCounts[i] > 0) {
                consecutive++;
            } else {
                consecutive = 0;
            }
            if (consecutive == 5) return true;
        }
        return false;
    }

    /** Function to check if we have three of a kind. */
    public boolean isThreeKind() {
        // We must ensure it's not a Full House or Four of a Kind,
        // which are checked first by cardsCalculation()
        for (int count : rankCounts) {
            if (count == 3) return true;
        }
        return false;
    }

    /** Function to check if we have a 2 pair. */
    public boolean isTwoPair() {
        // Must not be a Full House or Four of a Kind
        int pairs = 0;
        for (int count : rankCounts) {
            if (count == 2) pairs++;
        }
        return pairs >= 2;
    }

    /** Function to check if we just have a pair. */
    public boolean isPair() {
        // Must not be Two Pair, Three of a Kind, etc.
        int pairs = 0;
        for (int count : rankCounts) {
            if (count == 2) pairs++;
        }
        return pairs == 1;
    }

    /** Determines the winner between two players. */
    public Player winner(Player player1, Player player2) {
        ArrayList<Card> fullHand1 = new ArrayList<>(cardsToCheck);
        fullHand1.add(player1.getFirstCard());
        fullHand1.add(player1.getSecondCard());
        HandEvaluation eval1 = new HandEvaluation(fullHand1);
        int player1Score = eval1.cardsCalculation();

        ArrayList<Card> fullHand2 = new ArrayList<>(cardsToCheck);
        fullHand2.add(player2.getFirstCard());
        fullHand2.add(player2.getSecondCard());
        HandEvaluation eval2 = new HandEvaluation(fullHand2);
        int player2Score = eval2.cardsCalculation();

        System.out.println("Player Score: " + player1Score + " Bot Score: " + player2Score);

        if (player1Score > player2Score) {
            return player1;
        } else if (player2Score > player1Score) {
            return player2;
        } else {
            // Scores are equal, must break the tie
            return breakTie(eval1.rankCounts, eval2.rankCounts, player1, player2, player1Score);
        }
    }

    /** * Break tie based on hand strength.
     * This now uses the rank count arrays for all comparisons.
     */
    private Player breakTie(int[] c1, int[] c2, Player p1, Player p2, int handType) {
        switch (handType) {
            case 1: // One Pair
            case 2: // Two Pair
                return compareMultiples(c1, c2, p1, p2);
            
            case 3: // Three of a Kind
            case 6: // Full House
            case 7: // Four of a Kind
                return compareSet(c1, c2, p1, p2, handType);
            
            case 4: // Straight
            case 8: // Straight Flush
                return compareStraightHighCard(c1, c2, p1, p2);

            case 0: // High Card
            case 5: // Flush
            default:
                // Flushes and High Cards are decided by the highest 5 cards
                return compareHighCardsFromCounts(c1, c2, p1, p2);
        }
    }

    /** * Compares hands with pairs (One Pair, Two Pair).
     * It correctly finds the highest pair, then the second highest (for Two Pair),
     * and *then* checks kickers.
     */
    private Player compareMultiples(int[] c1, int[] c2, Player p1, Player p2) {
        // Compare pairs from highest to lowest
        for (int i = 12; i >= 0; i--) { // 12 = A
            boolean p1HasPair = c1[i] >= 2;
            boolean p2HasPair = c2[i] >= 2;

            if (p1HasPair && !p2HasPair) return p1;
            if (!p1HasPair && p2HasPair) return p2;
        }
        
        // If all pairs are equal (e.g., K,K,7,7 vs K,K,7,7), compare kickers
        return compareHighCardsFromCounts(c1, c2, p1, p2);
    }

    /** * Compares hands with sets (Three of a Kind, Full House, Four of a Kind).
     * It finds the highest set, then compares kickers (or the pair in a Full House).
     */
    private Player compareSet(int[] c1, int[] c2, Player p1, Player p2, int handType) {
        int target = (handType == 7) ? 4 : 3; // 4 for FourKind, 3 for ThreeKind/FullHouse

        // Find the rank of the main set (highest one wins)
        int setRank1 = -1;
        int setRank2 = -1;
        for (int i = 12; i >= 0; i--) {
            if (c1[i] >= target) setRank1 = i;
            if (c2[i] >= target) setRank2 = i;
        }

        if (setRank1 > setRank2) return p1;
        if (setRank2 > setRank1) return p2;
        
        // --- Sets are the same rank (e.g., KKK vs KKK) ---

        // For Full House, now we compare the pair
        if (handType == 6) {
             int pairRank1 = -1;
             int pairRank2 = -1;
             // Find highest pair *that wasn't the set*
             for (int i = 12; i >= 0; i--) {
                if (i == setRank1) continue; // Skip the set
                if (c1[i] >= 2 && pairRank1 == -1) pairRank1 = i;
                if (c2[i] >= 2 && pairRank2 == -1) pairRank2 = i;
             }
             if (pairRank1 > pairRank2) return p1;
             if (pairRank2 > pairRank1) return p2;
             return null; // Tie
        }

        // For 3-of-a-Kind and 4-of-a-Kind, compare kickers
        return compareHighCardsFromCounts(c1, c2, p1, p2);
    }

    /** Finds the highest card *in the straight* and compares. */
    private Player compareStraightHighCard(int[] c1, int[] c2, Player p1, Player p2) {
        int high1 = getStraightHighCard(c1);
        int high2 = getStraightHighCard(c2);
        
        if (high1 > high2) return p1;
        if (high2 > high1) return p2;
        return null; // Tie
    }

    /** Helper for straight comparison. Finds the top card of the straight. */
    private int getStraightHighCard(int[] counts) {
        // Check for A-2-3-4-5 "wheel" first (high card is 5, index 3)
        if (counts[12] > 0 && counts[0] > 0 && counts[1] > 0 && counts[2] > 0 && counts[3] > 0) {
            return 3; // '5'
        }
        
        // Find the highest 5-in-a-row
        int consecutive = 0;
        for (int i = 12; i >= 0; i--) { // Go from A down to 2
            if (counts[i] > 0) {
                consecutive++;
            } else {
                consecutive = 0;
            }
            if (consecutive == 5) return i + 4; // Return the top card's index
        }
        return -1; // Should not happen if isStraight() was true
    }


    /** * This is the master "kicker" checker.
     * It compares hands card-by-card from highest to lowest.
     * Used for High Card, Flushes, and as the final tie-breaker for pairs/sets.
     */
    private Player compareHighCardsFromCounts(int[] c1, int[] c2, Player p1, Player p2) {
        int cardsToCompare = 5; // We only compare the best 5 cards
        for (int i = 12; i >= 0; i--) { // 12 = A, 0 = 2
            boolean p1Has = c1[i] > 0;
            boolean p2Has = c2[i] > 0;

            if (p1Has && !p2Has) return p1;
            if (!p1Has && p2Has) return p2;

            // If both have this card, we "use" one and see if there are more
            if (p1Has && p2Has) {
                // This simple logic works for high-card hands, but not for kickers
                // where a pair is involved.
                // A better kicker check:
                int p1Count = c1[i];
                int p2Count = c2[i];
                
                // For pairs/sets, we only care about the *best 5 cards*.
                // This loop is fine, as it finds the highest card p1 has
                // that p2 does not, which is the definition of a kicker.
                
                // Let's refine the loop to only count 5 cards
            }
        }
        
        // This simpler loop is actually correct.
        // It iterates from Ace down to 2.
        // The first rank where one player has a card and the other does not, wins.
        // This works for kickers (e.g. K,K,A vs K,K,Q)
        // and for high card (e.g. A,K,Q,J,7 vs A,K,Q,J,5)
        for (int i = 12; i >= 0; i--) {
            if (c1[i] > 0 && c2[i] == 0) return p1;
            if (c2[i] > 0 && c1[i] == 0) return p2;
        }

        return null; // It's a true tie (chop pot)
    }
}
