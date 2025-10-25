import java.util.ArrayList;

/** Bot class for bot that the user will play against. */
public class Bot extends Player {

    private double aggressionLevel;
    //private Wallet wallet;
    /*private Position position;

    // Enum to check when is the bot playing.
    public enum Position {
        EARLY, MIDDLE, LATE
    }*/
    
    /** Bot constructor. */
    public Bot(double money, double aggressionLevel) {
        this.aggressionLevel = aggressionLevel;
        //wallet = new Wallet();
        //this.position = position;
    }

    /** Method for bot to bluff. */
    public void bluff(double potOdds, double callAmount) {
        if (potOdds < 0.1) {
            int raiseAmount = (int) (getWallet() * 0.1); 
            boolean reraising = false; 
            if (Poker.player.getLastBet() == Round.pot.getCurrentRaise() && Poker.player.getLastBet() != 0 && Round.pot.getCurrentRaise() != 0) {
                reraising = true; 
            } 
            raise(raiseAmount);
            GameSetup.mat.botAction("raise", raiseAmount, reraising); 
        } else {
            call(callAmount);
            GameSetup.mat.botAction("call", callAmount, false); 
        }
    }

    /** Metho to calculate what cards the bot is holding. */
    public double handCalculation(ArrayList<Card> cardsToCheck) {
        HandEvaluation handEvaluation = new HandEvaluation(cardsToCheck);

        if (handEvaluation.isPair()) {
            return 0.25;
        } else if (handEvaluation.isTwoPair()) {
            return 0.35;
        } else if (handEvaluation.isThreeKind()) {
            return 0.45;
        } else if (handEvaluation.isStraight()) {
            return 0.55;
        } else if (handEvaluation.isFlush()) {
            return 0.65;
        } else if (handEvaluation.isFullHouse()) {
            return 0.75;
        } else if (handEvaluation.isFourKind()) {
            return 0.9;
        } else if (handEvaluation.isStraightFlush()) {
            return 0.95;
        } else if (handEvaluation.isRoyalFlush()) {
            return 1;
        } else {
            return 0;
        }

    }

    /** Method for bot to calculate the power of the cards its holding. */
    public double handPower(ArrayList<Card> communityCards) {

        ArrayList<Card> cardsToCheck = new ArrayList<Card>();
        cardsToCheck.clear();

        cardsToCheck.add(getFirstCard());
        cardsToCheck.add(getSecondCard());
        
        for (int i = 0; i < communityCards.size(); i++) {
            cardsToCheck.add(communityCards.get(i));
        }

        return handCalculation(cardsToCheck);

    }

    /** Method for the bot to decide what to do. */
    public int decideAction(double potSize, double callAmount, ArrayList<Card> communityCards) {
        double potOdds = callAmount / (potSize + callAmount);
        double handStrength = handPower(communityCards);
        double adjustedAggression = (0.5 + aggressionLevel * 0.5);
        // to calculate the probability of action
        double ev = expectedValue(handStrength, potSize, callAmount);

        /*switch (position) {
            case EARLY -> handStrength = handStrength * 0.9;
            case MIDDLE -> handStrength = handStrength * 1.0;
            case LATE -> handStrength = handStrength * 1.1;
        }*/

        System.out.println("EV: " + ev);
        System.out.println("Hand Strenght: " + handStrength);
        System.out.println("Pot Odds: " + potOdds);

        if (ev > 0) {

            if (Math.random() < adjustedAggression) {
                int raiseAmount = (int) ((0.1 * getWallet() * Math.random() + 0.2) 
                    + Poker.player.getLastBet());

                /*while (raiseAmount == 0) {
                    raiseAmount = (int) ((0.1 * getWallet() * Math.random() + 0.2) 
                        + Poker.player.getLastBet());   
                }*/
                boolean reraising = false; 
                if (Poker.player.getLastBet() == Round.pot.getCurrentRaise() && Poker.player.getLastBet() != 0 && Round.pot.getCurrentRaise() != 0) {
                    reraising = true; 
                } 
                if (raiseAmount == 0) {
                    call(callAmount);
                    GameSetup.mat.botAction("call", callAmount, false);
                } else {
                    raise(raiseAmount); 
                    GameSetup.mat.botAction("raise", raiseAmount, reraising); 
                }
                //call(callAmount);
                GameSetup.mat.updateWalletDisplay(GameSetup.mat.botMoneyDisplay);
                System.out.println("Bot raised!");
                System.out.println("BOT RAISEEDDDDDD!");
                return 0;
            } else {
                call(callAmount);
                GameSetup.mat.botAction("call", callAmount, false); 
                GameSetup.mat.updateWalletDisplay(GameSetup.mat.botMoneyDisplay);
                System.out.println("Bot called / checked!");
                return 0;
            }

        } else if (handStrength >= 0.65) {

            if (potOdds < 0.6) {
                if (Math.random() < adjustedAggression) {
                    int raiseAmount = (int) ((0.1 * getWallet() * Math.random() + 0.2) 
                        + Poker.player.getLastBet());
                    boolean reraising = false; 
                    if (Poker.player.getLastBet() == Round.pot.getCurrentRaise() && Poker.player.getLastBet() != 0 && Round.pot.getCurrentRaise() != 0) {
                        reraising = true; 
                    } 
                    if (raiseAmount == 0) {
                        call(callAmount);
                        GameSetup.mat.botAction("call", callAmount, false);
                    } else {
                        raise(raiseAmount); 
                        GameSetup.mat.botAction("raise", raiseAmount, reraising); 
                    }
                    System.out.println("Bot raised!");
                    //call(callAmount);
                    GameSetup.mat.updateWalletDisplay(GameSetup.mat.botMoneyDisplay);
                    System.out.println("BOT RAISEEDDDDDD!");
                    return 0;
                } else {
                    call(callAmount);
                    GameSetup.mat.botAction("call", callAmount, false); 
                    GameSetup.mat.updateWalletDisplay(GameSetup.mat.botMoneyDisplay);
                    System.out.println("Bot called / checked!");
                    return 0;
                }
            } else {
                call(callAmount);
                GameSetup.mat.botAction("call", callAmount, false); 
                GameSetup.mat.updateWalletDisplay(GameSetup.mat.botMoneyDisplay);
                System.out.println("Bot called / checked!");
                return 0;
            }

        } else if (handStrength >= 0.45) {
            if (potOdds < 0.5) {
                call(callAmount);
                GameSetup.mat.botAction("call", callAmount, false); 
                GameSetup.mat.updateWalletDisplay(GameSetup.mat.botMoneyDisplay);
                System.out.println("Bot called / checked!");
                return 0;
            } else if (Math.random() < 0.25 * adjustedAggression) {
                bluff(potOdds, callAmount);
                System.out.println("BOT RAISEEDDDDDD!");
                System.out.println("Bot bluffs!");
                return 0;
            } else {
                fold();
                GameSetup.mat.botAction("fold", -1, false); 
                System.out.println("Bot folds!");
                return 1;
            }
        } else {
            if (potOdds < 0.4) {
                call(callAmount);
                GameSetup.mat.botAction("call", callAmount, false); 
                GameSetup.mat.updateWalletDisplay(GameSetup.mat.botMoneyDisplay);
                System.out.println("Bot callss!");
                return 0;
            } else {
                fold();
                GameSetup.mat.botAction("fold", -1, false); 
                System.out.println("Bot folds!");
                return 1;
            }
        }

        
        // --- Step 5: Weak hand logic — fold or bluff occasionally
        //double bluffChance = 0.05; // baseline bluff chance
        /*if (position == Position.LATE) {
            bluffChance += 0.10; // more likely to bluff late
        }*/
        //bluffChance += aggressionLevel * 0.05; // more aggressive bot bluffs more

        /*if (Math.random() < bluffChance) {
            bluff(potOdds, callAmount);
            System.out.println("Bot bluffs!");
        } else {
            fold();
            System.out.println("Bot folds!");
        }*/
        

    }

    public double expectedValue(double winProbability, double potSize, double callAmount) {
        return (winProbability * (potSize + callAmount)) - ((1 - winProbability) * callAmount);
    }

    /*
     * One Pair: 2
     * Two Pairs: 3
     * Three of a kind: 4
     * Straight: 5
     * Flush: 6
     * Full house: 7
     * Four of a kind: 8
     * Straight Flush: 9
     * Royal Flush: 10
     */

}