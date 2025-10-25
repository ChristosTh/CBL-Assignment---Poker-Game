import java.awt.*; 
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import javax.swing.*;


/** * Javadoc.
 * This class represents the UI of the poker game (the "View").
 * It is a "passive" view, meaning it only displays the game state
 * and reports user actions to the Round controller. It contains no game logic.
 */
public class PokerMat {
    JFrame pokerMat; 

    // Static references to card images, set by Round
    static ImageIcon playerCard1; 
    static ImageIcon playerCard2; 
    static ImageIcon botCard1; 
    static ImageIcon botCard2; 
    
    // Community Card images
    static ImageIcon[] baseCC = new ImageIcon[5];
    static Image[] scalingCC = new Image[5]; 
    static ImageIcon[] scaledCC = new ImageIcon[5]; 
    static ArrayList<JLabel> ccContainer = new ArrayList<>();

    // UI Components
    static JLabel potTotalUI = new JLabel(Double.toString(Round.pot.getPotTotal())); 

    JButton newRoundButton = new JButton("New Round"); 

    // Fields for card JLabels and images
    JLabel card1Container; 
    JLabel card2Container; 
    JLabel botCard1Container; 
    JLabel botCard2Container; 

    // Scaled images for the bot's *actual* cards (kept hidden)
    ImageIcon scaledBotCard1;
    ImageIcon scaledBotCard2;
    ImageIcon scaledImage; // The face-down card

    JLabel botMoneyDisplay; 
    JLabel moneyDisplay; 

    JButton raiseButton; 
    JButton callButton; 
    JButton foldButton; 
    JButton allInButton; 
    JButton checkButton; 

    /** updating pot total in UI. */
    public static void updatePotTotalUI() {
        String newText = Double.toString(Round.pot.getPotTotal()); 
        if (newText.substring(newText.length() - 2).equals(".0")) {
            newText = newText.substring(0, newText.length() - 2);
        }
        newText = "<html><span style='font-size:10.3px'>" 
            + newText + "</span></html>"; 
        potTotalUI.setText(newText); 
    }

    /** Method to show your cards in UI. Called by Round. */
    static void setCards(String cardPath1, String cardPath2, String cardPath3, String cardPath4) {
        playerCard1 = new ImageIcon(cardPath1);
        playerCard2 = new ImageIcon(cardPath2);  
        botCard1 = new ImageIcon(cardPath3); 
        botCard2 = new ImageIcon(cardPath4); 
    }

    /** Method to set flop. Called by Round. */
    static void setFlop(String[] cardPaths) {
        for (int i = 0; i < 3; i++) {
            baseCC[i] = new ImageIcon(cardPaths[i]); 
            scalingCC[i] = baseCC[i].getImage().getScaledInstance(115, 160, Image.SCALE_DEFAULT); 
            scaledCC[i] = new ImageIcon(scalingCC[i]); 
            JLabel holder = new JLabel(scaledCC[i]); 
            ccContainer.add(holder); 
            ccContainer.get(i).setSize(115, 160); 
            ccContainer.get(i).setVisible(false); // Start invisible
        }
    }

    /** Method to set turn card. Called by Round. */
    static void setTurn(String cardPath) {
        baseCC[3] = new ImageIcon(cardPath); 
        scalingCC[3] = baseCC[3].getImage().getScaledInstance(115, 160, Image.SCALE_DEFAULT); 
        scaledCC[3] = new ImageIcon(scalingCC[3]); 
        JLabel holder = new JLabel(scaledCC[3]); 
        ccContainer.add(holder); 
        ccContainer.get(3).setSize(115, 160); 
        ccContainer.get(3).setVisible(false); 
    }

    /** Method to set river card. Called by Round. */
    static void setRiver(String cardPath) {
        baseCC[4] = new ImageIcon(cardPath); 
        scalingCC[4] = baseCC[4].getImage().getScaledInstance(115, 160, Image.SCALE_DEFAULT); 
        scaledCC[4] = new ImageIcon(scalingCC[4]); 
        JLabel holder = new JLabel(scaledCC[4]); 
        ccContainer.add(holder); 
        ccContainer.get(4).setSize(115, 160); 
        ccContainer.get(4).setVisible(false); 
    }

    /** UI to show player won. */
    void playerWon() {
        String text = "You have won this round."
            + " Proceed to the next round by pressing \"New Round\"";
        text = String.format("<html><div WIDTH=%d>%s</div></html>", 200, text);

        JLabel playerWonUI = new JLabel(text); 

        pokerMat.add(playerWonUI);
        pokerMat.setComponentZOrder(playerWonUI, 0);
        pokerMat.repaint(); 
        playerWonUI.setSize(200, 95); 
        playerWonUI.setLocation(500, 300); 
        playerWonUI.setBackground(Color.GRAY);
        playerWonUI.setOpaque(true); 
    }

    /** UI to show bot won. */
    void botWon() {
        String text = "You have lost this round." 
            + " Proceed to the next round by pressing \"New Round\""; 
        text = String.format("<html><div WIDTH=%d>%s</div></html>", 200, text);
        
        JLabel playerWonUI = new JLabel(text); 

        pokerMat.add(playerWonUI);
        pokerMat.setComponentZOrder(playerWonUI, 0);
        pokerMat.repaint(); 
        playerWonUI.setSize(200, 95); 
        playerWonUI.setLocation(500, 300); 
        playerWonUI.setBackground(Color.GRAY);
        playerWonUI.setOpaque(true);
    }

    /** Closing JFrame. */
    void closeFrame(JFrame frame) {
        frame.setVisible(false); 
        frame.dispose(); // Use dispose to release resources
        frame = null; 
    }

    /** Updating the wallet display. */
    void updateWalletDisplay(JLabel label) {
        // Determine if this is the player or bot label
        double walletValue = (label == moneyDisplay) ? Poker.player.getWallet() : Poker.bot.getWallet();
        
        String newWallet = Double.toString(walletValue); 
        if (newWallet.substring(newWallet.length() - 2).equals(".0")) {
            newWallet = newWallet.substring(0, newWallet.length() - 2);
        }
        newWallet = "<html><span style='font-size:10.3px'>" 
            + newWallet + "</span></html>";
        label.setText(newWallet);   
    }

    /** Method to show what the bot did. */
    void botAction(String action) {
        String text = "The bot has " + action + "ed"; 
        text = String.format("<html><div WIDTH=%d>%s</div></html>", 100, text);
        
        JLabel botActionUI = new JLabel(text); 

        pokerMat.add(botActionUI);
        pokerMat.setComponentZOrder(botActionUI, 0);
        pokerMat.repaint(); 
        botActionUI.setLocation(500, 240); 
        botActionUI.setSize(100, 30); 
        botActionUI.setLocation(500, 240); 
        botActionUI.setBackground(Color.GRAY);
        botActionUI.setOpaque(true);
    }

    JLabel raiseErrorText = new JLabel("You cannot raise that amount of money. Please bet less"); 

    /** Method to show raise error. */
    void raiseError() {
        pokerMat.add(raiseErrorText); 
        pokerMat.setComponentZOrder(raiseErrorText, 0); 
        pokerMat.repaint(); 
        raiseErrorText.setSize(100, 30); 
        raiseErrorText.setLocation(300, 300); 
        raiseErrorText.setOpaque(true); 
        raiseErrorText.setBackground(Color.RED); 
    }

    /** PokerMat page constructor. */
    public PokerMat(double moneyAmount, double smallBlind, double bigBlind) {
        
        pokerMat = new JFrame("Poker"); 

        potTotalUI.setSize(50, 50); 
        potTotalUI.setLocation(207, 200);
        potTotalUI.setForeground(new Color(230, 165, 90));

        // Note: Wallet is now set in Round.java, not here.

        //#region New round button.
        newRoundButton.setSize(150, 45);
        newRoundButton.setLocation(30, 30); 
        newRoundButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                GameSetup.mat.pokerMat.setVisible(false); 
                GameSetup.mat.pokerMat.dispose(); // Release resources
                GameSetup.mat.pokerMat = null; 
                ccContainer.clear(); 
                // Start a new round, swapping who was first
                Poker.round = new Round(Poker.player, Poker.bot, Poker.player.getWallet(), 
                    Round.pot.getSmallBlind(), Round.pot.getBigBlind(), Round.getPlayerFirst()); 
            }
        });
        //#endregion

        //#region Poker mat Frame creation/customisation
        pokerMat.setSize(1100, 750); 
        pokerMat.setVisible(true); 
        pokerMat.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        pokerMat.getContentPane().setBackground(new Color(6, 117, 8));
        pokerMat.setLocationRelativeTo(null);
        //#endregion

        //#region Image Loading and Scaling
        ImageIcon faceDownCard = new ImageIcon("CardsPNG\\faceDownCard.png"); 
        Image scalingImage = faceDownCard.getImage()
            .getScaledInstance(160, 160, Image.SCALE_DEFAULT); 
        scaledImage = new ImageIcon(scalingImage); 

        Image scalingCard1 = playerCard1.getImage()
            .getScaledInstance(115, 160, Image.SCALE_DEFAULT); 
        ImageIcon scaledCard1 = new ImageIcon(scalingCard1); 
        card1Container = new JLabel(scaledCard1); 
            
        Image scalingCard2 = playerCard2.getImage()
            .getScaledInstance(115, 160, Image.SCALE_DEFAULT); 
        ImageIcon scaledCard2 = new ImageIcon(scalingCard2); 
        card2Container = new JLabel(scaledCard2); 

        // Load bot's REAL cards but store them in fields
        Image scalingBotCard1Img = botCard1.getImage()
            .getScaledInstance(115, 160, Image.SCALE_DEFAULT); 
        scaledBotCard1 = new ImageIcon(scalingBotCard1Img); 
        
        Image scalingBotCard2Img = botCard2.getImage() 
            .getScaledInstance(115, 160, Image.SCALE_DEFAULT); 
        scaledBotCard2 = new ImageIcon(scalingBotCard2Img); 

        // Create bot's card containers using the FACE-DOWN image
        JLabel deckRepresentation = new JLabel(scaledImage);
        botCard1Container = new JLabel(scaledImage); 
        botCard2Container = new JLabel(scaledImage); 

        ImageIcon chipStack = new ImageIcon("CardsPNG\\Chip_Stacks.png"); 
        Image scalingChips = chipStack.getImage().getScaledInstance(46, 46, Image.SCALE_DEFAULT); 
        ImageIcon scaledChips = new ImageIcon(scalingChips); 
        JLabel chipContainer = new JLabel(scaledChips); 

        ImageIcon botChip = new ImageIcon("CardsPNG\\Chip_Stacks.png"); 
        Image scalingBotChip = botChip.getImage().getScaledInstance(46, 46, Image.SCALE_DEFAULT); 
        ImageIcon scaledBotChip = new ImageIcon(scalingBotChip); 
        JLabel botChipContainer = new JLabel(scaledBotChip); 

        ImageIcon botMoneyBox = new ImageIcon("CardsPNG\\Money_Box.png"); 
        Image scalingBotBox = botMoneyBox.getImage()
            .getScaledInstance(130, 60, Image.SCALE_DEFAULT);
        ImageIcon scaledBotBox = new ImageIcon(scalingBotBox); 
        JLabel botBoxContainer = new JLabel(scaledBotBox); 

        ImageIcon moneyBox = new ImageIcon("CardsPNG\\Money_Box.png"); 
        Image scalingBox = moneyBox.getImage().getScaledInstance(130, 60, Image.SCALE_DEFAULT);
        ImageIcon scaledBox = new ImageIcon(scalingBox); 
        JLabel boxContainer = new JLabel(scaledBox);

        ImageIcon potText = new ImageIcon("CardsPNG\\Money_Box.png"); 
        Image scalingPotText = potText.getImage()
            .getScaledInstance(130, 60, Image.SCALE_DEFAULT);
        ImageIcon scaledPotText = new ImageIcon(scalingPotText);
        JLabel potTextContainer = new JLabel(scaledPotText);

        ImageIcon smallBlindIcon = new ImageIcon("CardsPNG\\smallBlindIcon.png"); 
        Image scalingSBIcon = smallBlindIcon.getImage()
            .getScaledInstance(60, 60, Image.SCALE_DEFAULT); 
        ImageIcon scaledSBIcon = new ImageIcon(scalingSBIcon); 
        JLabel sbContainer = new JLabel(scaledSBIcon); 

        ImageIcon bigBlindIcon = new ImageIcon("CardsPNG\\bigBlindIcon.png"); 
        Image scalingBBIcon = bigBlindIcon.getImage()
            .getScaledInstance(60, 60, Image.SCALE_DEFAULT); 
        ImageIcon scaledBBIcon = new ImageIcon(scalingBBIcon); 
        JLabel bbContainer = new JLabel(scaledBBIcon); 

        ImageIcon potTotalIcon = new ImageIcon("CardsPNG\\potIcon.png"); 
        Image scalingPotIcon = potTotalIcon.getImage()
            .getScaledInstance(50, 50, Image.SCALE_DEFAULT); 
        ImageIcon scaledPotIcon = new ImageIcon(scalingPotIcon); 
        JLabel potContainer = new JLabel(scaledPotIcon); 
        //#endregion

        //#region Displaying cards and chips/wallet.
        deckRepresentation.setSize(160, 160); 
        deckRepresentation.setLocation(110, 258);

        card1Container.setSize(115, 160); 
        card1Container.setLocation(420, 500); 

        card2Container.setSize(115, 160); 
        card2Container.setLocation(550, 500);

        botCard1Container.setSize(160, 160); 
        botCard1Container.setLocation(400, 40); 

        botCard2Container.setSize(160, 160); 
        botCard2Container.setLocation(530, 40);

        chipContainer.setSize(46, 46); 
        chipContainer.setLocation(830, 515); 

        boxContainer.setSize(130, 60); 
        boxContainer.setLocation(872, 510); 

        botBoxContainer.setSize(130, 60); 
        botBoxContainer.setLocation(872, 20); 

        botChipContainer.setSize(46, 46); 
        botChipContainer.setLocation(830, 25); 
        
        botMoneyDisplay = new JLabel(); // Initialized empty, updateWalletDisplay will fill it
        botMoneyDisplay.setLocation(920, 29); 
        botMoneyDisplay.setSize(100, 30); 
        botMoneyDisplay.setForeground(new Color(230, 165, 90));
        updateWalletDisplay(botMoneyDisplay); // Set initial bot wallet
        
        sbContainer.setSize(50, 50); 
        bbContainer.setSize(50, 50); 
        if (Round.getPlayerFirst()) {
            sbContainer.setLocation(685, 595); 
            bbContainer.setLocation(685, 50); 
        } else {
            sbContainer.setLocation(685, 50); 
            bbContainer.setLocation(685, 595); 
        }

        JLabel sbText = new JLabel(Double.toString(Round.pot.getSmallBlind())); // This is fine
        sbText.setSize(50, 50); 
        sbText.setLocation(200, 20); 

        potContainer.setSize(100, 100); 
        potContainer.setLocation(85, 175); 
        
        potTextContainer.setSize(130, 60); 
        potTextContainer.setLocation(153, 196);
        
        moneyDisplay = new JLabel(); // Initialized empty
        moneyDisplay.setForeground(new Color(230, 165, 90));
        moneyDisplay.setLocation(920, 519);  
        moneyDisplay.setSize(100, 30); 
        updateWalletDisplay(moneyDisplay); // Set initial player wallet
        //#endregion

        //#region Raise functionality.
        raiseButton = new JButton("Raise");
        raiseButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                JFrame raiseFrame = new JFrame("Raising"); 
                JButton closeRaiseFrame = new JButton("Raise"); 
                JTextField raiseAmount = new JTextField("Type raise amount here"); 

                raiseAmount.setForeground(Color.GRAY); 
                raiseAmount.addMouseListener(new MouseAdapter() {
                    @Override
                    public void mouseClicked(MouseEvent e) {
                        raiseAmount.setForeground(Color.BLACK);
                        raiseAmount.setText(""); 
                    }
                });
                raiseAmount.setSize(100, 20); 
                raiseAmount.setLocation(80, 100);

                closeRaiseFrame.setSize(100, 20); 
                closeRaiseFrame.setLocation(170, 225); 

                closeRaiseFrame.addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        try {
                            double raiseValue = Double.parseDouble(raiseAmount.getText());
                            // Report action to controller

                            //double moneyLeftPlayerWallet = Poker.player.getWallet() - raiseValue;
                            //double moneyLeftBotWallet = Poker.bot.getWallet() - raiseValue;
                            
                            if (raiseValue <= Poker.player.getWallet()) {
                                Poker.round.playerActed("raise", raiseValue); 
                                updateWalletDisplay(moneyDisplay); // Update UI
                                closeFrame(raiseFrame);  
                            } else {
                                closeFrame(raiseFrame); 
                                raiseError();
                                System.out.println("You cannot raise that amount of money!");
                            }

                        } catch (NumberFormatException nfe) {
                            raiseAmount.setText("Invalid number");
                            raiseAmount.setForeground(Color.RED);
                        }
                    }
                });
                
                raiseFrame.setFocusable(true); 
                raiseFrame.setSize(300, 300); 
                raiseFrame.setLocationRelativeTo(null); 
                raiseFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
                raiseFrame.setVisible(true); 

                raiseFrame.add(closeRaiseFrame); 
                raiseFrame.add(raiseAmount); 
                raiseFrame.setLayout(new BorderLayout()); 
            } 
        });
        raiseButton.setSize(100, 30); 
        raiseButton.setLocation(300, 510);
        //#endregion

        //#region Call functionality
        callButton = new JButton("Call"); 
        callButton.setSize(100, 30); 
        callButton.setLocation(300, 550); 
        callButton.addMouseListener(new MouseAdapter() {
            @Override 
            public void mouseClicked(MouseEvent e) {
                // Report action to controller
                Poker.round.playerActed("call", 0); 
                updateWalletDisplay(moneyDisplay);
            }
        });
        //#endregion

        //#region Fold functionality
        foldButton = new JButton("Fold"); 
        foldButton.setSize(100, 30); 
        foldButton.setLocation(685, 550);
        foldButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Report action to controller
                Poker.round.playerActed("fold", 0);
            }
        });
        //#endregion

        //#region Check functionality
        checkButton = new JButton("Check"); 
        checkButton.setSize(100, 30); 
        checkButton.setLocation(685, 510);  
        checkButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Report action to controller
                Poker.round.playerActed("check", 0);
            }
        }); 
        //#endregion

        // Add all components to frame
        pokerMat.add(card1Container); 
        pokerMat.add(card2Container); 
        pokerMat.add(botCard1Container);
        pokerMat.add(botCard2Container); 
        pokerMat.add(raiseButton);
        pokerMat.add(callButton); 
        pokerMat.add(foldButton); 
        pokerMat.add(checkButton); 
        pokerMat.add(chipContainer); 
        pokerMat.add(moneyDisplay); 
        pokerMat.add(boxContainer);
        pokerMat.add(newRoundButton); 
        pokerMat.add(sbContainer); 
        pokerMat.add(bbContainer); 
        pokerMat.add(potContainer); 
        pokerMat.add(potTotalUI); 
        pokerMat.add(botMoneyDisplay); 
        pokerMat.add(botChipContainer); 
        pokerMat.add(botBoxContainer); 
        pokerMat.add(deckRepresentation); 
        // Note: Community cards are added by the show... methods
        
        pokerMat.add(potTextContainer); 
        pokerMat.setLayout(null);
        pokerMat.repaint(); // Ensure frame is drawn
    }

    // --- New methods called by Round.java ---

    /** Makes the flop cards visible. */
    public void showFlopCards() {
        // Assumes setFlop already populated ccContainer with 3 cards
        for (int i = 0; i < 3; i++) {
            pokerMat.add(ccContainer.get(i)); // Add to frame
            ccContainer.get(i).setLocation(285 + 145 * i, 260); // Set location
            ccContainer.get(i).setVisible(true); // Make visible
        }
        pokerMat.repaint();
    }

    /** Makes the turn card visible. */
    public void showTurnCard() {
        // Assumes setTurn added card at index 3
        pokerMat.add(ccContainer.get(3));
        ccContainer.get(3).setLocation(285 + 145 * 3, 260);
        ccContainer.get(3).setVisible(true);
        pokerMat.repaint();
    }

    /** Makes the river card visible. */
    public void showRiverCard() {
        // Assumes setRiver added card at index 4
        pokerMat.add(ccContainer.get(4));
        ccContainer.get(4).setLocation(285 + 145 * 4, 260); // Position for 5th card
        ccContainer.get(4).setVisible(true);
        pokerMat.repaint();
    }

    /** Reveals the bot's face-down cards. */
    public void showBotCards() {
        // Set the icons from face-down to the bot's actual cards
        botCard1Container.setIcon(scaledBotCard1);
        botCard2Container.setIcon(scaledBotCard2);
        pokerMat.repaint();
    }
}
