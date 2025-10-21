import java.awt.*; 
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import javax.swing.*;


/** Javadoc. */
public class PokerMat {
    JFrame pokerMat; 

    static ImageIcon playerCard1; 
    static ImageIcon playerCard2; 

    /** Method to show your cards in UI. */
    static void setCards(String cardPath1, String cardPath2) {
        playerCard1 = new ImageIcon(cardPath1);
        playerCard2 = new ImageIcon(cardPath2);  
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

    ImageIcon chipStacks = new ImageIcon("CardsPNG\\Chip_Stacks.png"); 
    Image scalingChips = chipStacks.getImage().getScaledInstance(46, 46, Image.SCALE_DEFAULT); 
    ImageIcon scaledChips = new ImageIcon(scalingChips); 
    JLabel chipContainer = new JLabel(scaledChips); 

    JLabel moneyDisplay; 
    ImageIcon moneyBox = new ImageIcon("CardsPNG\\Money_Box.png"); 
    Image scalingBox = moneyBox.getImage().getScaledInstance(130, 60, Image.SCALE_DEFAULT);
    ImageIcon scaledBox = new ImageIcon(scalingBox); 
    JLabel boxContainer = new JLabel(scaledBox);

    JButton raiseButton; 
    JButton callButton; 
    JButton foldButton; 
    JButton allInButton; 
    JButton checkButton; 
    //#endregion

    /** PokerMat page constructor. */
    public PokerMat(double moneyAmount, double smallBlind, double bigBlind) {
        Poker.poker.setBlinds(smallBlind, bigBlind); 
        Poker.player.setWallet(moneyAmount); 

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

        chipContainer.setSize(46, 46); 
        chipContainer.setLocation(903, 25); 

        boxContainer.setSize(130, 60); 
        boxContainer.setLocation(947, 20); 
        
        String initialWallet = Double.toString(Poker.player.getWallet()); 
        if (initialWallet.substring(initialWallet.length() - 2).equals(".0")) {
            initialWallet = initialWallet.substring(0, initialWallet.length() - 2);
        }
        initialWallet = "<html><span style='font-size:10.3px'>" 
            + initialWallet + "</span></html>"; 
        moneyDisplay = new JLabel(initialWallet);
        moneyDisplay.setForeground(new Color(230, 165, 90));
        moneyDisplay.setLocation(995, 33);  
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
                Poker.player.call(Poker.pot.getCurrentRaise()); 
                updateWalletDisplay(moneyDisplay);

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
            public void actionPerformed(ActionEvent e){
                if (Poker.pot.getCurrentRaise() == 0) {
                    Poker.player.call(0); 
                } else {
                    System.out.println("You are not able to Check. Please either bet or fold.");
                }
            }
        }); 
        //#endregion

        // pokerMat.add(sBlindDisplay); 
        pokerMat.add(imageContainer);
        pokerMat.add(card1Container); 
        pokerMat.add(card2Container); 
        pokerMat.add(raiseButton);
        pokerMat.add(callButton); 
        pokerMat.add(foldButton); 
        pokerMat.add(allInButton); 
        pokerMat.add(checkButton); 
        pokerMat.add(chipContainer); 
        pokerMat.add(moneyDisplay); 
        pokerMat.add(boxContainer); 
        pokerMat.setLayout(new BorderLayout());
    }
}
