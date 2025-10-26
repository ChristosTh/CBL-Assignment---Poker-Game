import java.util.ArrayList;

/** Bot class for bot that the user will play against. */
public class Bot extends Player {

    private double aggressionLevel;
    
    /** Bot constructor. */
    public Bot(double money, double aggressionLevel) {
        this.aggressionLevel = aggressionLevel;
    }

    /** Method for bot to bluff. */
    public void bluff(double potOdds, double callAmount) {
        if (potOdds < 0.1) {
            int raiseAmount = (int) (getWallet() * 0.1); 
            boolean reraising = false; 
            if (Poker.player.getLastBet() == Round.pot.getCurrentRaise() 
                && Poker.player.getLastBet() != 0 && Round.pot.getCurrentRaise() != 0) {
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

        System.out.println("EV: " + ev);
        System.out.println("Hand Strenght: " + handStrength);
        System.out.println("Pot Odds: " + potOdds);

        if (ev > 0) {

            if (Math.random() < adjustedAggression) {
                int raiseAmount = (int) ((0.1 * getWallet() * Math.random() + 0.2) 
                    + Poker.player.getLastBet());
                boolean reraising = false; 
                if (Poker.player.getLastBet() == Round.pot.getCurrentRaise() 
                    && Poker.player.getLastBet() != 0 && Round.pot.getCurrentRaise() != 0) {
                    reraising = true; 
                } 
                if (raiseAmount == 0) {
                    call(callAmount);
                    GameSetup.mat.botAction("call", callAmount, false);
                } else {
                    raise(raiseAmount); 
                    GameSetup.mat.botAction("raise", raiseAmount, reraising); 
                }
                GameSetup.mat.updateWalletDisplay(GameSetup.mat.botMoneyDisplay);
                return 0;
            } else {
                call(callAmount);
                GameSetup.mat.botAction("call", callAmount, false); 
                GameSetup.mat.updateWalletDisplay(GameSetup.mat.botMoneyDisplay);
                return 0;
            }

        } else if (handStrength >= 0.65) {

            if (potOdds < 0.6) {
                if (Math.random() < adjustedAggression) {
                    int raiseAmount = (int) ((0.1 * getWallet() * Math.random() + 0.2) 
                        + Poker.player.getLastBet());
                    boolean reraising = false; 
                    if (Poker.player.getLastBet() == Round.pot.getCurrentRaise() 
                        && Poker.player.getLastBet() != 0 && Round.pot.getCurrentRaise() != 0) {
                        reraising = true; 
                    } 
                    if (raiseAmount == 0) {
                        call(callAmount);
                        GameSetup.mat.botAction("call", callAmount, false);
                    } else {
                        raise(raiseAmount); 
                        GameSetup.mat.botAction("raise", raiseAmount, reraising); 
                    }
                    GameSetup.mat.updateWalletDisplay(GameSetup.mat.botMoneyDisplay);
                    return 0;
                } else {
                    call(callAmount);
                    GameSetup.mat.botAction("call", callAmount, false); 
                    GameSetup.mat.updateWalletDisplay(GameSetup.mat.botMoneyDisplay);
                    return 0;
                }
            } else {
                call(callAmount);
                GameSetup.mat.botAction("call", callAmount, false); 
                GameSetup.mat.updateWalletDisplay(GameSetup.mat.botMoneyDisplay);
                return 0;
            }

        } else if (handStrength >= 0.45) {
            if (potOdds < 0.5) {
                call(callAmount);
                GameSetup.mat.botAction("call", callAmount, false); 
                GameSetup.mat.updateWalletDisplay(GameSetup.mat.botMoneyDisplay);
                return 0;
            } else if (Math.random() < 0.25 * adjustedAggression) {
                bluff(potOdds, callAmount);
                return 0;
            } else {
                GameSetup.mat.botAction("fold", -1, false); 
                return 1;
            }
        } else {
            if (potOdds < 0.4) {
                call(callAmount);
                GameSetup.mat.botAction("call", callAmount, false); 
                GameSetup.mat.updateWalletDisplay(GameSetup.mat.botMoneyDisplay);
                return 0;
            } else {
                GameSetup.mat.botAction("fold", -1, false); 
                return 1;
            }
        }
    }

    public double expectedValue(double winProbability, double potSize, double callAmount) {
        return (winProbability * (potSize + callAmount)) - ((1 - winProbability) * callAmount);
    }
}