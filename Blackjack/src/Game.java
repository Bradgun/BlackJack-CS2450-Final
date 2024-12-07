import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Random;
import java.util.concurrent.atomic.AtomicInteger;

public class Game /*implements ActionListener*/ {
    int dealerTotalPrime = 0;
    int playerTotalPrime = 0;
    JLabel dealerTotalText = new JLabel();
    JLabel playerTotalText = new JLabel();

    ArrayList<Card> deck = new ArrayList<>();
    JButton hitButton = new JButton("Hit");
    JButton standButton = new JButton("Stand");
//    JButton splitButton = new JButton("Split");
    JButton doubleDownButton = new JButton("Double Down");

    ArrayList<Chip> chips = new ArrayList<>();

//    Color casinoGreen = new Color(0, 210, 0);

    Image chipSprite1 = ImageIO.read(new File("Chip Images/chip1.png"));
    Image chipSprite10 = ImageIO.read(new File("Chip Images/chip10.png"));
    Image chipSprite100 = ImageIO.read(new File("Chip Images/chip100.png"));
    Image chipSprite500 = ImageIO.read(new File("Chip Images/chip500.png"));
    Image chipSprite1000 = ImageIO.read(new File("Chip Images/chip1000.png"));
    Image chipSprite5000 = ImageIO.read(new File("Chip Images/chip5000.png"));

    Chip chip1 = new Chip(1, chipSprite1);    // Set sprite size to file default
    Chip chip10 = new Chip(10, chipSprite10);
    Chip chip100 = new Chip(100, chipSprite100);
    Chip chip500 = new Chip(500, chipSprite500);
    Chip chip1000 = new Chip(1000, chipSprite1000);
    Chip chip5000 = new Chip(5000, chipSprite5000);

    int walletAmount = 2500;
    int betAmount = 0;
    JLabel walletText = new JLabel("Wallet: $" + walletAmount);
    JLabel walletTextDealPanel = new JLabel("Wallet: $" + walletAmount);
    JLabel betText = new JLabel("Bet: $" + betAmount);
    JLabel betAmountText;
    Random random = new Random();

    JFrame jfrm = new JFrame("Mack's Sidetrack Blackjack");
    CardLayout card = new CardLayout();
    JPanel mainPanel = new JPanel(card);
    JPanel betPanel;
    JPanel dealPanel;

    Game(ArrayList<Card> givenDeck) throws IOException, InterruptedException {
        deck.addAll(givenDeck);
//        Collections.shuffle(deck);    //Shuffle unnecessary as we're already drawing at random

        chips.add(chip5000);
        chips.add(chip1000);
        chips.add(chip500);
        chips.add(chip100);
        chips.add(chip10);
        chips.add(chip1);

        jfrm.setSize(960, 720);
        jfrm.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        jfrm.setLocationRelativeTo(null);

        betPanel = betPanel();
//        betPanel.setOpaque(false);
        dealPanel = dealPanel();
//        dealPanel.setOpaque(false);

        Image backgroundImage = ImageIO.read(new File ("Misc Images/corgi.png"));
        ImageIcon backgroundIcon = new ImageIcon(backgroundImage);
        JLabel mainPanelBackground = new JLabel(backgroundIcon);
        //TODO: If possible, find a way to set a background image!

//        mainPanel.setOpaque(false);

        mainPanel.add(betPanel, "Bet Panel");
        mainPanel.add(dealPanel, "Deal Panel");
        card.show(mainPanel, "Bet Panel");

        jfrm.add(mainPanel);
        jfrm.setVisible(true);
    }

    Game(ArrayList<Card> givenDeck, int wallet) throws IOException, InterruptedException {
        walletAmount = wallet;
        deck.addAll(givenDeck);
//        Collections.shuffle(deck);    //Shuffle unnecessary as we're already drawing at random

        chips.add(chip5000);
        chips.add(chip1000);
        chips.add(chip500);
        chips.add(chip100);
        chips.add(chip10);
        chips.add(chip1);

        jfrm.setSize(960, 720);
        jfrm.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        jfrm.setLocationRelativeTo(null);

        betPanel = betPanel();
        dealPanel = dealPanel();

        mainPanel.add(betPanel, "Bet Panel");
        mainPanel.add(dealPanel, "Deal Panel");
        card.show(mainPanel, "Bet Panel");

        jfrm.add(mainPanel);
        jfrm.setVisible(true);
    }

    private JPanel dealPanel() throws IOException {
        AtomicInteger aceCount = new AtomicInteger();

        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(new BorderLayout());

        JPanel dealScreen = new JPanel();
        dealScreen.setLayout(new BoxLayout(dealScreen, BoxLayout.Y_AXIS));

        JPanel header = new JPanel();   //Orients header content to top-left
        header.setLayout(new BorderLayout());
        JPanel headerMini = new JPanel();
        headerMini.setLayout(new BoxLayout(headerMini, BoxLayout.Y_AXIS));

        walletTextDealPanel.setFont(new Font("Wallet", Font.BOLD, 18));
        betText.setFont(new Font("Bet", Font.BOLD, 18));
        headerMini.add(walletTextDealPanel);
        headerMini.add(betText);
        header.add(headerMini, BorderLayout.WEST);

        dealerTotalText.setFont(new Font("Dealer Total", Font.PLAIN, 24));
        playerTotalText.setFont(new Font("Player Total", Font.PLAIN, 24));
        dealerTotalText.setAlignmentX(Component.CENTER_ALIGNMENT);
        playerTotalText.setAlignmentX(Component.CENTER_ALIGNMENT);

        JPanel dealerHand = new JPanel();
        dealerHand.setLayout(new BoxLayout(dealerHand, BoxLayout.X_AXIS));
        ArrayList<Card> dealerCards = new ArrayList<>();
        ArrayList<JLabel> dealerCardsVisual = new ArrayList<>();
        int randomIndex = random.nextInt(deck.size() - 1);  // Cannot draw last card in deck, which is the back of a card
        dealerCards.add(deck.get(randomIndex));
        dealerCardsVisual.add(deck.get(randomIndex).getPictureAssetScaled(150, 210));
        randomIndex = random.nextInt(deck.size() - 1);
        dealerCards.add(deck.get(randomIndex));    // Second card dealer draws is drawn face-down
        dealerCardsVisual.add(deck.getLast().getPictureAssetScaled(150, 210));
        for (int i = 0 ; i < dealerCardsVisual.size(); i++) {
            dealerHand.add(dealerCardsVisual.get(i));
            if (i != dealerCardsVisual.size() - 1) {
                dealerHand.add(Box.createRigidArea(new Dimension(20 ,0)));
            }
        }

        JPanel buttonRow = new JPanel();
        buttonRow.setLayout(new BoxLayout(buttonRow, BoxLayout.X_AXIS));

        hitButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        standButton.setAlignmentX(Component.CENTER_ALIGNMENT);
//        splitButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        doubleDownButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        buttonRow.add(doubleDownButton);
        buttonRow.add(Box.createRigidArea(new Dimension(20, 0)));
        buttonRow.add(hitButton);
        buttonRow.add(Box.createRigidArea(new Dimension(20, 0)));
        buttonRow.add(standButton);

//        buttonRow.add(splitButton);   //FIXME: Uncomment when fully implemented

        JPanel playerHand = new JPanel();
        ArrayList<Card> playerCards = new ArrayList<>();
        ArrayList<JLabel> playerCardsVisual = new ArrayList<>();
        playerHand.setLayout(new BoxLayout(playerHand, BoxLayout.X_AXIS));
        randomIndex = random.nextInt(deck.size() - 1);  // Cannot draw last card in deck, which is the back of a card
        playerCards.add(deck.get(randomIndex));
        playerCardsVisual.add(deck.get(randomIndex).getPictureAssetScaled(150, 210));
        randomIndex = random.nextInt(deck.size() - 1);
        playerCards.add(deck.get(randomIndex));
        playerCardsVisual.add(deck.get(randomIndex).getPictureAssetScaled(150, 210));

        for (int i = 0 ; i < playerCardsVisual.size(); i++) {
            playerHand.add(playerCardsVisual.get(i));
            if (i != playerCardsVisual.size() - 1) {
                playerHand.add(Box.createRigidArea(new Dimension(20 ,0)));
            }
        }

        for (int i = 0; i < dealerCards.size(); i++) {
            dealerTotalPrime = dealerTotalPrime + dealerCards.get(i).getValue();
        }
        for (int i = 0; i < playerCards.size(); i++) {
            playerTotalPrime = playerTotalPrime + playerCards.get(i).getValue();
        }
        updateTotalsFirst(dealerCards.getFirst().getValue());

        mainPanel.add(header, BorderLayout.NORTH);
        dealScreen.add(dealerTotalText);
        dealScreen.add(Box.createRigidArea(new Dimension(0, 20)));
        dealScreen.add(dealerHand);
        dealScreen.add(Box.createRigidArea(new Dimension(0, 20)));
        dealScreen.add(buttonRow);
        dealScreen.add(Box.createRigidArea(new Dimension(0, 20)));
        dealScreen.add(playerHand);
        dealScreen.add(Box.createRigidArea(new Dimension(0, 20)));
        dealScreen.add(playerTotalText);
        mainPanel.add(dealScreen, BorderLayout.CENTER);

        hitButton.addActionListener(e -> {
            doubleDownButton.setVisible(false); //Can only be done for your first card drawn

            int randomNum = random.nextInt(deck.size() - 1);
            playerCards.add(deck.get(randomNum));
            playerCardsVisual.add(deck.get(randomNum).getPictureAssetScaled(150, 210));
            playerHand.add(Box.createRigidArea(new Dimension(20 ,0)));
            playerHand.add(playerCardsVisual.getLast());
            dealPanel.revalidate();

            if (deck.get(randomNum).getRank().equals("Ace")) {
                aceCount.getAndIncrement();
            }

            int playerTotal = 0;
            for (int i = 0; i < playerCards.size(); i++) {
                playerTotal = playerTotal + playerCards.get(i).getValue();
            }

            if (playerTotal > 21) {
                int aceCountInt = aceCount.get();
                for (int i = 0; i < playerCards.size(); i++) {
                    if (playerCards.get(i).getRank().equals("Ace")) {
                        if (!(playerCards.get(i).aceAltered)) { //If this ace has already been checked
                            playerCards.get(i).setValue(1);
                            aceCount.getAndDecrement();
                            break;
                        }
                    }
                }

                if (aceCountInt == 0) { //Only if all the aces have been handled
                    doubleDownButton.setVisible(true);  //Upon loss, reset the state of the double down button
                    JDialog L = lossDialog();
                    L.setLocationRelativeTo(jfrm);
                    L.setVisible(true);
                }
            }
            playerTotalPrime = playerTotal;
            updateTotalsFirst(dealerCards.getFirst().getValue());
        });
        standButton.addActionListener(e -> {
            hitButton.setEnabled(false);
            standButton.setEnabled(false);
            dealerHand.remove(2);   //Remove and replace face-down card with the actual card, simulating a "flipping over" of the card;
            dealerHand.add(dealerCards.getLast().getPictureAssetScaled(150, 210));
            dealPanel.revalidate();

            int dealerTotal = 0;
            for (int i = 0; i < dealerCards.size(); i++) {
                dealerTotal = dealerTotal + dealerCards.get(i).getValue();
            }

            int randomNum = random.nextInt(deck.size() - 1);
            while (dealerTotal < 17) {
                dealerCards.add(deck.get(randomNum));
                dealerHand.add(Box.createRigidArea(new Dimension(20, 0)));
                dealerHand.add(deck.get(randomNum).getPictureAssetScaled(150, 210));
                dealerTotal = dealerTotal + dealerCards.getLast().getValue();
                randomNum = random.nextInt(deck.size() - 1);
                dealPanel.revalidate();
            }
            dealerTotalPrime = dealerTotal;
            updateTotals();
        });
        doubleDownButton.addActionListener(e -> {
            walletAmount = walletAmount - betAmount;
            betAmount = betAmount * 2;
            walletTextDealPanel.setText("Wallet: $" + walletAmount);
            betText.setText("Bet: $" + betAmount);

            hitButton.setEnabled(false);
            standButton.setEnabled(false);
            doubleDownButton.setEnabled(false);

            int randomNum = random.nextInt(deck.size() - 1);
            playerCards.add(deck.get(randomNum));
            playerHand.add(Box.createRigidArea(new Dimension(20 , 0)));
            playerHand.add(deck.get(randomNum).getPictureAssetScaled(150, 210));

            if (deck.get(randomNum).getRank().equals("Ace")) {
                aceCount.getAndIncrement();
            }

            int playerTotal = 0;
            for (int i = 0; i < playerCards.size(); i++) {
                playerTotal = playerTotal + playerCards.get(i).getValue();
            }

            if (playerTotal > 21) {
                int aceCountInt = aceCount.get();
                for (int i = 0; i < playerCards.size(); i++) {
                    if (playerCards.get(i).getRank().equals("Ace")) {
                        if (!(playerCards.get(i).aceAltered)) { //If this ace has already been checked
                            playerCards.get(i).setValue(1);
                            aceCount.getAndDecrement();
                            break;
                        }
                    }
                }

                if (aceCountInt == 0) { //Only if all the aces have been handled
                    doubleDownButton.setVisible(true);  //Upon loss, reset the state of the double down button
                    JDialog L = lossDialog();
                    L.setLocationRelativeTo(jfrm);
                    L.setVisible(true);
                }
            }

            dealerHand.remove(2);   //Remove and replace face-down card with the actual card, simulating a "flipping over" of the card;
            dealerHand.add(dealerCards.getLast().getPictureAssetScaled(150, 210));
            dealPanel.revalidate();

            int dealerTotal = 0;
            for (int i = 0; i < dealerCards.size(); i++) {
                dealerTotal = dealerTotal + dealerCards.get(i).getValue();
            }

            randomNum = random.nextInt(deck.size() - 1);
            while (dealerTotal < 17) {
                dealerCards.add(deck.get(randomNum));
                dealerHand.add(Box.createRigidArea(new Dimension(20, 0)));
                dealerHand.add(deck.get(randomNum).getPictureAssetScaled(150, 210));
                dealerTotal = dealerTotal + dealerCards.getLast().getValue();
                randomNum = random.nextInt(deck.size() - 1);
                dealPanel.revalidate();
            }
            playerTotalPrime = playerTotal;
            updateTotals();
        });

        hitButton.setToolTipText("Press this button to draw another card.");
        standButton.setToolTipText("Press this button to stop drawing.");
        doubleDownButton.setToolTipText("Double your bet, but only draw one more card. Goes away when you choose to hit instead.");

        hitButton.setMnemonic('h');
        standButton.setMnemonic('s');
        doubleDownButton.setMnemonic('d');

        //TODO: Thinking about ditching this mechanic for now, will be implemented in the extended version post-CS2450
//        splitButton.setVisible(false);
//        splitButton.addActionListener(e -> {
//
//        });

        return mainPanel;
    }

    private JPanel betPanel() throws IOException {
        JPanel betScreen = new JPanel();
        betScreen.setLayout(new BoxLayout(betScreen, BoxLayout.Y_AXIS)); //Rows of panels
//        betScreen.setBackground(casinoGreen); //Temporary, change later

        JPanel header = new JPanel();
//        header.setBackground(casinoGreen);
        header.setLayout(new FlowLayout());
        walletText.setText("Wallet: $" + walletAmount);
        walletText.setFont(new Font("Wallet", Font.BOLD, 36));
        header.add(walletText);

        JPanel betValueLine = new JPanel();
//        betValueLine.setBackground(casinoGreen);
        betValueLine.setLayout(new BoxLayout(betValueLine, BoxLayout.Y_AXIS));
        JLabel totalBetText = new JLabel("Total Bet:");
        betAmountText = new JLabel("$" + betAmount);
        totalBetText.setFont(new Font("Total Bet", Font.PLAIN, 36));
        betAmountText.setFont(new Font("Bet Amount", Font.BOLD, 36));
        totalBetText.setAlignmentX(Component.CENTER_ALIGNMENT);
        betAmountText.setAlignmentX(Component.CENTER_ALIGNMENT);
        betValueLine.add(totalBetText);
        betValueLine.add(betAmountText);

        JPanel chipsAndButtonsAvailable = new JPanel();
//        chipsAndButtonsAvailable.setBackground(casinoGreen);
        chipsAndButtonsAvailable.setLayout(new BoxLayout(chipsAndButtonsAvailable, BoxLayout.X_AXIS));

        for (int i = 0; i < chips.size(); i++) {
            JPanel chipAndButtons = new JPanel();
            chipAndButtons.setLayout(new BoxLayout(chipAndButtons, BoxLayout.Y_AXIS));

            JButton add = chips.get(i).getAddButton();
            JButton remove = chips.get(i).getRemoveButton();
            JLabel chipSprite = chips.get(i).getPictureAsset();

            add.setAlignmentX(Component.CENTER_ALIGNMENT);
            remove.setAlignmentX(Component.CENTER_ALIGNMENT);
            chipSprite.setAlignmentX(Component.CENTER_ALIGNMENT);

            chipAndButtons.add(remove);
            chipAndButtons.add(Box.createRigidArea(new Dimension(0, 10)));
            chipAndButtons.add(chipSprite);
            chipAndButtons.add(Box.createRigidArea(new Dimension(0, 10)));
            chipAndButtons.add(add);

            int finalI = i;
            add.addActionListener(e -> {
                if ((walletAmount - chips.get(finalI).getValue()) >= 0) {
                    walletAmount = walletAmount - (chips.get(finalI).getValue());
                    betAmount = betAmount + (chips.get(finalI).getValue());
                    walletText.setText("Wallet: $" + walletAmount);
                    betAmountText.setText("$" + betAmount);
                }
            });
            remove.addActionListener(e -> {
                if ((betAmount - chips.get(finalI).getValue()) >= 0) {
                    walletAmount = walletAmount + (chips.get(finalI).getValue());
                    betAmount = betAmount - (chips.get(finalI).getValue());
                    walletText.setText("Wallet: $" + walletAmount);
                    betAmountText.setText("$" + betAmount);
                }
            });

            chipsAndButtonsAvailable.add(chipAndButtons);
            if (i != chips.size() - 1) {
                chipsAndButtonsAvailable.add(Box.createRigidArea(new Dimension(30 ,0)));
            }
        }

        JLabel removeBetPromptLine = new JLabel("Remove from bet:");
        removeBetPromptLine.setAlignmentX(Component.CENTER_ALIGNMENT);

        JLabel addBetPromptLine = new JLabel("Add to bet:");
        addBetPromptLine.setAlignmentX(Component.CENTER_ALIGNMENT);

        JPanel miscBets = new JPanel();
//        miscBets.setBackground(casinoGreen);
        miscBets.setLayout(new BoxLayout(miscBets, BoxLayout.X_AXIS));
        JButton allIn = new JButton("All In!");
        JButton clearBet = new JButton("Clear Bet");
//        allIn.setBorder(BorderFactory.createLineBorder(Color.BLACK));
//        clearBet.setBorder(BorderFactory.createLineBorder(Color.BLACK));
        allIn.setPreferredSize(new Dimension(60, 30));
        clearBet.setPreferredSize(new Dimension(60, 30));
        allIn.setAlignmentX(Component.CENTER_ALIGNMENT);
        clearBet.setAlignmentX(Component.CENTER_ALIGNMENT);
        allIn.addActionListener(e -> {
            betAmount = betAmount + walletAmount;
            walletAmount = 0;
            walletText.setText("Wallet: $" + walletAmount);
            betAmountText.setText("$" + betAmount);
        });
        clearBet.addActionListener(e -> {
            walletAmount = walletAmount + betAmount;
            betAmount = 0;
            walletText.setText("Wallet: $" + walletAmount);
            betAmountText.setText("$" + betAmount);
        });
        miscBets.add(allIn);
        miscBets.add(Box.createRigidArea(new Dimension(40, 0)));
        miscBets.add(clearBet);

        betScreen.add(header);
        betScreen.add(Box.createRigidArea(new Dimension(0, 10)));
        betScreen.add(betValueLine);
        betScreen.add(Box.createRigidArea(new Dimension(0, 10)));
        betScreen.add(removeBetPromptLine);
        betScreen.add(Box.createRigidArea(new Dimension(0, 10)));
        betScreen.add(chipsAndButtonsAvailable);
        betScreen.add(Box.createRigidArea(new Dimension(0, 10)));
        betScreen.add(addBetPromptLine);
        betScreen.add(Box.createRigidArea(new Dimension(0, 30)));
        betScreen.add(miscBets);
        betScreen.add(Box.createRigidArea(new Dimension(0, 50)));

        JButton playButton = new JButton("Deal Me!");
        playButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        playButton.setPreferredSize(new Dimension(80, 40));
//        playButton.addActionListener(this);
        betScreen.add(playButton);

        betScreen.add(Box.createRigidArea(new Dimension(0, 20)));
        JLabel errorNoBetText = new JLabel("You've gotta place some kind of wager, partner!");
        errorNoBetText.setVisible(false);
        errorNoBetText.setAlignmentX(Component.CENTER_ALIGNMENT);
        betScreen.add(errorNoBetText);
        betScreen.add(Box.createRigidArea(new Dimension(0, 30)));

        playButton.addActionListener(e -> {
            if (betAmount == 0) {
                errorNoBetText.setVisible(true);
            }
            else {
                walletTextDealPanel.setText("Wallet: $" + walletAmount);
                betText.setText("Bet: $" + betAmount);
                card.show(mainPanel, "Deal Panel");
            }
        });

        return betScreen;
    }

    public void clearBet() {
        betAmount = 0;
        walletText.setText("Wallet: $" + walletAmount);
        betAmountText.setText("Bet: $" + betAmount);
        betPanel.revalidate();
    }
    public void setWalletAmount(boolean win) {
        if (win) {
            walletAmount = walletAmount + betAmount;
        }
    }
    public JDialog winDialog() {
        JDialog mainDialog = new JDialog();
        mainDialog.setTitle("Winner!");
        mainDialog.setSize(400, 300);
        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.Y_AXIS));

        JLabel winText = new JLabel("Winner Winner Chicken Dinner!");
        JLabel winAmount = new JLabel("+$" + betAmount);
        JButton confirmButton = new JButton("WOOOOOOOOO!!!");
        confirmButton.addActionListener(e -> {
            mainDialog.dispose();
            betPanel.removeAll();
            dealPanel.removeAll();
            jfrm.dispose();
            try {
                Game newGame = new Game(deck, walletAmount);
            } catch (IOException ex) {
                throw new RuntimeException(ex);
            } catch (InterruptedException ex) {
                throw new RuntimeException(ex);
            }
        });

        winText.setFont(new Font("WinText", Font.BOLD, 24));
        winAmount.setFont(new Font("WinAmount", Font.BOLD, 24));
        winAmount.setForeground(Color.GREEN);

        mainPanel.add(winText);
        mainPanel.add(Box.createRigidArea(new Dimension(0, 20)));
        mainPanel.add(winAmount);

        mainDialog.add(mainPanel);

        clearBet();
        setWalletAmount(true);

        return mainDialog;
    }
    public JDialog lossDialog() {
        JDialog mainDialog = new JDialog(jfrm, true);
        mainDialog.setTitle("Loser!");
        mainDialog.setLocationRelativeTo(null);

        mainDialog.setSize(400, 300);
        JPanel mainDialogPanel = new JPanel();
        mainDialogPanel.setLayout(new BoxLayout(mainDialogPanel, BoxLayout.Y_AXIS));

        JLabel lossText = new JLabel("L Bozo!");
        JLabel lossAmount = new JLabel("-$" + betAmount);
        JButton confirmButton = new JButton("Great...");
        confirmButton.addActionListener(e -> {
            mainDialog.dispose();
            betPanel.removeAll();
            dealPanel.removeAll();
            jfrm.dispose();
            try {
                Game newGame = new Game(deck, walletAmount);
            } catch (IOException ex) {
                throw new RuntimeException(ex);
            } catch (InterruptedException ex) {
                throw new RuntimeException(ex);
            }
        });

        lossText.setAlignmentX(Component.CENTER_ALIGNMENT);
        lossAmount.setAlignmentX(Component.CENTER_ALIGNMENT);
        confirmButton.setAlignmentX(Component.CENTER_ALIGNMENT);

        lossText.setFont(new Font("LossText", Font.BOLD, 24));
        lossAmount.setFont(new Font("LossAmount", Font.BOLD, 24));
        lossAmount.setForeground(Color.RED);

        mainDialogPanel.add(lossText);
        mainDialogPanel.add(Box.createRigidArea(new Dimension(0, 20)));
        mainDialogPanel.add(lossAmount);
        mainDialogPanel.add(Box.createRigidArea(new Dimension(0, 20)));
        mainDialogPanel.add(confirmButton);

        mainDialog.add(mainDialogPanel);

        setWalletAmount(false);
        clearBet();

        return mainDialog;
    }

    public void updateTotals() {
        dealerTotalText.setText("Dealer's total: " + dealerTotalPrime);
        playerTotalText.setText("Your total: " + playerTotalPrime);
    }
    public void updateTotalsFirst(int firstCardValue) {
        dealerTotalText.setText("Dealer's total: " + firstCardValue + " + ?");
        playerTotalText.setText("Your total: " + playerTotalPrime);
    }
}
