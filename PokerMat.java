import java.awt.*; 
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import javax.swing.*;


/** Javadoc. */
public class PokerMat {
    JFrame pokerMat; 

    static ImageIcon playerCard1; 
    static ImageIcon playerCard2; 
    static ImageIcon botCard1; 
    static ImageIcon botCard2; 
    
    static ImageIcon[] baseCC = new ImageIcon[5];
    static Image[] scalingCC = new Image[5]; 
    static ImageIcon[] scaledCC = new ImageIcon[5]; 
    static ArrayList<JLabel> ccContainer = new ArrayList<>();

    static JLabel potTotalUI = new JLabel(Double.toString(Round.pot.getPotTotal())); 

    JButton newRoundButton = new JButton("New Round"); 

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

    JButton showFlop = new JButton("Show flop");
    JButton showTurn = new JButton("Show turn"); 

    /** Method to show your cards in UI. */
    static void setCards(String cardPath1, String cardPath2, String cardPath3, String cardPath4) {
        playerCard1 = new ImageIcon(cardPath1);
        playerCard2 = new ImageIcon(cardPath2);  
        botCard1 = new ImageIcon(cardPath3); 
        botCard2 = new ImageIcon(cardPath4); 
    }

    /** Method to set flop. */
    static void setFlop(String[] cardPaths) {
        for (int i = 0; i < 3; i++) {
            baseCC[i] = new ImageIcon(cardPaths[i]); 
            scalingCC[i] = baseCC[i].getImage().getScaledInstance(115, 160, Image.SCALE_DEFAULT); 
            scaledCC[i] = new ImageIcon(scalingCC[i]); 
            JLabel holder = new JLabel(scaledCC[i]); 
            ccContainer.add(holder); 
            ccContainer.get(i).setSize(115, 160); 
            ccContainer.get(i).setLocation(285 + 145 * i, 260); 
            ccContainer.get(i).setVisible(false); 
        }
    }

    /** Method to set turn card. */
    static void setTurn(String cardPath) {
        baseCC[3] = new ImageIcon(cardPath); 
        scalingCC[3] = baseCC[3].getImage().getScaledInstance(115, 160, Image.SCALE_DEFAULT); 
        scaledCC[3] = new ImageIcon(scalingCC[3]); 
        JLabel holder = new JLabel(scaledCC[3]); 
        ccContainer.add(holder); 
        ccContainer.get(3).setSize(115, 160); 
        ccContainer.get(3).setLocation(285 + 145 * 3, 260); 
        ccContainer.get(3).setVisible(false); 
    }


    /** Closing JFrame. */
    void closeFrame(JFrame frame) {
        frame.setVisible(false); 
        frame = null; 
    }

    /** Updating the wallet display. */
    void updateWalletDisplay(JLabel label) {
        String newWallet = Double.toString(Poker.player.getWallet()); 
        if (newWallet.substring(newWallet.length() - 2).equals(".0")) {
            newWallet = newWallet.substring(0, newWallet.length() - 2);
        }
        newWallet = "<html><span style='font-size:10.3px'>" 
            + newWallet + "</span></html>";
        label.setText(newWallet); 
    }

    //#region UI components
    ImageIcon faceDownCard = new ImageIcon("CardsPNG\\faceDownCard.png"); 
    Image scalingImage = faceDownCard.getImage().getScaledInstance(157, 160, Image.SCALE_DEFAULT);
    ImageIcon scaledImage = new ImageIcon(scalingImage); 
    JLabel imageContainer = new JLabel(scaledImage);  

    Image scalingCard1 = playerCard1.getImage().getScaledInstance(115, 160, Image.SCALE_DEFAULT); 
    ImageIcon scaledCard1 = new ImageIcon(scalingCard1); 
    JLabel card1Container = new JLabel(scaledCard1); 
        
    Image scalingCard2 = playerCard2.getImage().getScaledInstance(115, 160, Image.SCALE_DEFAULT); 
    ImageIcon scaledCard2 = new ImageIcon(scalingCard2); 
    JLabel card2Container = new JLabel(scaledCard2); 

    Image scalingBotCard1 = botCard1.getImage().getScaledInstance(115, 160, Image.SCALE_DEFAULT); 
    ImageIcon scaledBotCard1 = new ImageIcon(scalingBotCard1); 
    JLabel botCard1Container = new JLabel(scaledBotCard1); 

    Image scalingBotCard2 = botCard2.getImage().getScaledInstance(115, 160, Image.SCALE_DEFAULT); 
    ImageIcon scaledBotCard2 = new ImageIcon(scalingBotCard2); 
    JLabel botCard2Container = new JLabel(scaledBotCard2); 

    ImageIcon chipStack = new ImageIcon("CardsPNG\\Chip_Stacks.png"); 
    Image scalingChips = chipStack.getImage().getScaledInstance(46, 46, Image.SCALE_DEFAULT); 
    ImageIcon scaledChips = new ImageIcon(scalingChips); 
    JLabel chipContainer = new JLabel(scaledChips); 

    ImageIcon botChip = new ImageIcon("CardsPNG\\Chip_Stacks.png"); 
    Image scalingBotChip = botChip.getImage().getScaledInstance(46, 46, Image.SCALE_DEFAULT); 
    ImageIcon scaledBotChip = new ImageIcon(scalingBotChip); 
    JLabel botChipContainer = new JLabel(scaledBotChip); 

    JLabel botMoneyDisplay; 
    ImageIcon botMoneyBox = new ImageIcon("CardsPNG\\Money_Box.png"); 
    Image scalingBotBox = botMoneyBox.getImage().getScaledInstance(130, 60, Image.SCALE_DEFAULT);
    ImageIcon scaledBotBox = new ImageIcon(scalingBotBox); 
    JLabel botBoxContainer = new JLabel(scaledBotBox); 

    JLabel moneyDisplay; 
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
    Image scalingSBIcon = smallBlindIcon.getImage().getScaledInstance(60, 60, Image.SCALE_DEFAULT); 
    ImageIcon scaledSBIcon = new ImageIcon(scalingSBIcon); 
    JLabel sbContainer = new JLabel(scaledSBIcon); 

    ImageIcon bigBlindIcon = new ImageIcon("CardsPNG\\bigBlindIcon.png"); 
    Image scalingBBIcon = bigBlindIcon.getImage().getScaledInstance(60, 60, Image.SCALE_DEFAULT); 
    ImageIcon scaledBBIcon = new ImageIcon(scalingBBIcon); 
    JLabel bbContainer = new JLabel(scaledBBIcon); 

    ImageIcon potTotalIcon = new ImageIcon("CardsPNG\\potIcon.png"); 
    Image scalingPotIcon = potTotalIcon.getImage()
        .getScaledInstance(50, 50, Image.SCALE_DEFAULT); 
    ImageIcon scaledPotIcon = new ImageIcon(scalingPotIcon); 
    JLabel potContainer = new JLabel(scaledPotIcon); 

    JLabel sbText = new JLabel(Double.toString(Round.pot.getSmallBlind()));
    JLabel bbText = new JLabel(Double.toString(Round.pot.getBigBlind())); 

    JButton raiseButton; 
    JButton callButton; 
    JButton foldButton; 
    JButton allInButton; 
    JButton checkButton; 
    //#endregion

    /** PokerMat page constructor. */
    public PokerMat(double moneyAmount, double smallBlind, double bigBlind) {
        potTotalUI.setSize(50, 50); 
        potTotalUI.setLocation(207, 200);
        potTotalUI.setForeground(new Color(230, 165, 90));

        Poker.player.setWallet(moneyAmount); 

        //#region New round button.
        newRoundButton.setSize(100, 30);
        newRoundButton.setLocation(20, 200); 
        newRoundButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                GameSetup.mat.pokerMat.setVisible(false); 
                GameSetup.mat.pokerMat = null; 
                ccContainer.clear(); 
                Poker.round = new Round(Poker.player, Poker.bot, Poker.player.getWallet(), 
                    Round.pot.getSmallBlind(), Round.pot.getBigBlind(), Round.getPlayerFirst()); 
            }
        });
        //#endregion

        //#region Poker mat Frame creation/customisation
        pokerMat = new JFrame("Poker"); 
        pokerMat.setSize(1100, 750); 
        pokerMat.setVisible(true); 
        pokerMat.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        pokerMat.getContentPane().setBackground(new Color(6, 117, 8));
        pokerMat.setLocationRelativeTo(null);
        //#endregion

        //#region Displaying cards and chips/wallet.
        imageContainer.setSize(180, 180); 
        imageContainer.setLocation(100, 250); 

        card1Container.setSize(115, 160); 
        card1Container.setLocation(420, 500); 

        card2Container.setSize(115, 160); 
        card2Container.setLocation(550, 500);

        botCard1Container.setSize(115, 160); 
        botCard1Container.setLocation(420, 40); 

        botCard2Container.setSize(115, 160); 
        botCard2Container.setLocation(550, 40);

        chipContainer.setSize(46, 46); 
        chipContainer.setLocation(830, 515); 

        boxContainer.setSize(130, 60); 
        boxContainer.setLocation(872, 510); 

        botBoxContainer.setSize(130, 60); 
        botBoxContainer.setLocation(872, 20); 

        botChipContainer.setSize(46, 46); 
        botChipContainer.setLocation(830, 25); 
        
        botMoneyDisplay = new JLabel(Double.toString(Poker.bot.getWallet())); 
        botMoneyDisplay.setLocation(920, 29); 
        botMoneyDisplay.setSize(100, 30); 
        botMoneyDisplay.setForeground(new Color(230, 165, 90));

        sbContainer.setSize(50, 50); 
        bbContainer.setSize(50, 50); 
        if (Round.getPlayerFirst()) {
            sbContainer.setLocation(685, 595); 
            bbContainer.setLocation(685, 50); 
        } else {
            sbContainer.setLocation(685, 50); 
            bbContainer.setLocation(685, 595); 
        }

        sbText.setSize(50, 50); 
        sbText.setLocation(200, 20); 

        potContainer.setSize(100, 100); 
        potContainer.setLocation(85, 175); 
        
        potTextContainer.setSize(130, 60); 
        potTextContainer.setLocation(153, 196);
        
        String initialWallet = Double.toString(Poker.player.getWallet()); 
        if (initialWallet.substring(initialWallet.length() - 2).equals(".0")) {
            initialWallet = initialWallet.substring(0, initialWallet.length() - 2);
        }
        initialWallet = "<html><span style='font-size:10.3px'>" 
            + initialWallet + "</span></html>"; 
        moneyDisplay = new JLabel(initialWallet);
        moneyDisplay.setForeground(new Color(230, 165, 90));
        moneyDisplay.setLocation(920, 519);  
        moneyDisplay.setSize(100, 30); 
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
                        Poker.player.raise(Double.parseDouble(raiseAmount.getText())); 
                        updateWalletDisplay(moneyDisplay);
                        Round.whoPlays = "bot";
                        closeFrame(raiseFrame); 
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
                if (Round.whoPlays.equals("player")) {
                    Poker.player.call(Round.pot.getCurrentRaise()); 
                    updateWalletDisplay(moneyDisplay);
                    Round.whoPlays = "bot";

                    if (Round.flopShowed && !Round.turnShowed && !Round.riverShowed) {
                        for (int i = 0; i < ccContainer.size(); i++) {
                            ccContainer.get(i).setVisible(true); 
                        }
                    }
                    Round.turn(Poker.bot);
                }
                
            }
        });
        //#endregion
         
        //#region AllIn functionality
        allInButton = new JButton("All in"); 
        allInButton.setSize(100, 30); 
        allInButton.setLocation(685, 510); 
        allInButton.addActionListener(new ActionListener() {
            @Override 
            public void actionPerformed(ActionEvent e) {
                Poker.player.allIn(); 
                updateWalletDisplay(moneyDisplay);
                Round.whoPlays = "bot";
            }
        }); 
        //#endregion

        //#region Fold functionality (not finished); 
        foldButton = new JButton("Fold"); 
        foldButton.setSize(100, 30); 
        foldButton.setLocation(685, 550);
        foldButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Make player fold, end the hand. 
            }
        });
        //#endregion

        //#region Check functionality (edit once UI is complete for System.println...)
        checkButton = new JButton("Check"); 
        checkButton.setSize(100, 30); 
        checkButton.setLocation(300, 590);  
        checkButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (Round.pot.getCurrentRaise() == 0) {
                    Poker.player.call(0); 
                    Round.whoPlays = "bot";

                    if (Round.flopShowed && !Round.turnShowed && !Round.riverShowed) {
                        for (int i = 0; i < ccContainer.size(); i++) {
                            ccContainer.get(i).setVisible(true); 
                    }
                }

                } else {
                    System.out.println("You are not able to Check. Please either bet or fold.");
                }
            }
        }); 
        //#endregion

        //#region Showing flop
        showFlop.setSize(100, 30); 
        showFlop.setLocation(50, 50); 
        showFlop.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                for (int i = 0; i < ccContainer.size(); i++) {
                    ccContainer.get(i).setVisible(true); 
                }
            }
        });
        //#endregion

        //#region
        showTurn.setSize(100, 30); 
        showTurn.setLocation(50, 75); 
        showTurn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                ccContainer.get(3).setVisible(true); 
            }
        });
        //#endregion

        pokerMat.add(imageContainer);
        pokerMat.add(card1Container); 
        pokerMat.add(card2Container); 
        pokerMat.add(botCard1Container);
        pokerMat.add(botCard2Container); 
        pokerMat.add(raiseButton);
        pokerMat.add(callButton); 
        pokerMat.add(foldButton); 
        pokerMat.add(allInButton); 
        pokerMat.add(checkButton); 
        pokerMat.add(chipContainer); 
        pokerMat.add(moneyDisplay); 
        pokerMat.add(boxContainer);
        pokerMat.add(showFlop);
        pokerMat.add(newRoundButton); 
        pokerMat.add(sbContainer); 
        pokerMat.add(bbContainer); 
        pokerMat.add(sbText);
        pokerMat.add(potContainer); 
        pokerMat.add(potTotalUI); 
        pokerMat.add(botMoneyDisplay); 
        pokerMat.add(botChipContainer); 
        pokerMat.add(botBoxContainer); 
        for (int i = 0; i < ccContainer.size(); i++) {
            pokerMat.add(ccContainer.get(i)); 
        } 
        pokerMat.add(potTextContainer); 
        pokerMat.add(showTurn); 
        pokerMat.setLayout(new BorderLayout());
    }
}
