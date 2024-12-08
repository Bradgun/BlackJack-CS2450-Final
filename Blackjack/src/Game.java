import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Random;
import java.util.concurrent.atomic.AtomicInteger;

public class Game /*implements ActionListener*/ {
    JLabel dealerTotalText = new JLabel();
    JLabel playerTotalText = new JLabel();

    ArrayList<Card> deck = new ArrayList<>();
    JButton hitButton = new JButton("Hit");
    JButton standButton = new JButton("Stand");
    JButton doubleDownButton = new JButton("Double Down");

    int dealerTotal = 0;
    int playerTotal = 0;

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
    JPanel gameOverPanel;

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
        dealPanel = dealPanel();
        gameOverPanel = badEnding();

        mainPanel.add(betPanel, "Bet Panel");
        mainPanel.add(dealPanel, "Deal Panel");
        mainPanel.add(gameOverPanel, "Game Over Panel");
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
        gameOverPanel = badEnding();

        mainPanel.add(betPanel, "Bet Panel");
        mainPanel.add(dealPanel, "Deal Panel");
        mainPanel.add(gameOverPanel, "Game Over Panel");
        card.show(mainPanel, "Bet Panel");

        jfrm.add(mainPanel);
        jfrm.setVisible(true);
    }

    private JPanel dealPanel() throws IOException {
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
        doubleDownButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        buttonRow.add(doubleDownButton);
        buttonRow.add(Box.createRigidArea(new Dimension(20, 0)));
        buttonRow.add(hitButton);
        buttonRow.add(Box.createRigidArea(new Dimension(20, 0)));
        buttonRow.add(standButton);

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

            playerTotal = calculateTotal(playerCards);

            if (playerTotal > 21) {
                doubleDownButton.setVisible(true);  //Upon loss, reset the state of the double down button
                JDialog L = lossDialog();
                L.setLocationRelativeTo(jfrm);
                L.setVisible(true);
            }
            else if (playerTotal == 21) {
                JDialog W = winDialog();
                W.setLocationRelativeTo(jfrm);
                W.setVisible(true);
            }
            updatePlayerTotal();
        });
        standButton.addActionListener(e -> {
            hitButton.setEnabled(false);
            standButton.setEnabled(false);
            dealerHand.remove(2);   //Remove and replace face-down card with the actual card, simulating a "flipping over" of the card;
            dealerHand.add(dealerCards.getLast().getPictureAssetScaled(150, 210));
            dealPanel.revalidate();

            dealerTotal = calculateTotal(dealerCards);

            int randomNum = random.nextInt(deck.size() - 1);
            while (dealerTotal < 17) {
                dealerCards.add(deck.get(randomNum));
                dealerHand.add(Box.createRigidArea(new Dimension(20, 0)));
                dealerHand.add(deck.get(randomNum).getPictureAssetScaled(150, 210));
                dealerTotal = calculateTotal(dealerCards);
                randomNum = random.nextInt(deck.size() - 1);
                dealPanel.revalidate();
                updateTotals();
            }

            if (dealerTotal > 21 || playerTotal > dealerTotal) {
                JDialog W = winDialog();
                W.setLocationRelativeTo(jfrm);
                W.setVisible(true);
            }
            else if (playerTotal < dealerTotal) {
                JDialog L = lossDialog();
                L.setLocationRelativeTo(jfrm);
                L.setVisible(true);
            }
            else {
                JDialog P = pushDialog();
                P.setLocationRelativeTo(jfrm);
                P.setVisible(true);
            }
        });
        doubleDownButton.addActionListener(e -> {
            if (walletAmount - betAmount > 0) {
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

                playerTotal = calculateTotal(playerCards);

                if (playerTotal > 21) { //If player busts
                    doubleDownButton.setVisible(true);  //Upon loss, reset the state of the double down button
                    JDialog L = lossDialog();
                    L.setLocationRelativeTo(jfrm);
                    L.setVisible(true);
                }
                else if (playerTotal == 21) {   //If player wins
                    JDialog W = winDialog();
                    W.setLocationRelativeTo(jfrm);
                    W.setVisible(true);
                }
                else {  //If player doesn't bust, but doesn't get a blackjack
                    dealerHand.remove(2);   //Remove and replace face-down card with the actual card, simulating a "flipping over" of the card;
                    dealerHand.add(dealerCards.getLast().getPictureAssetScaled(150, 210));
                    dealPanel.revalidate();

                    dealerTotal = calculateTotal(dealerCards);

                    randomNum = random.nextInt(deck.size() - 1);
                    while (dealerTotal < 17) {
                        dealerCards.add(deck.get(randomNum));
                        dealerHand.add(Box.createRigidArea(new Dimension(20, 0)));
                        dealerHand.add(deck.get(randomNum).getPictureAssetScaled(150, 210));
                        dealerTotal = calculateTotal(dealerCards);
                        randomNum = random.nextInt(deck.size() - 1);
                        updateTotals();
                        dealPanel.revalidate();
                    }

                    if (dealerTotal > 21 || playerTotal > dealerTotal) {
                        JDialog W = winDialog();
                        W.setLocationRelativeTo(jfrm);
                        W.setVisible(true);
                    }
                    else if (playerTotal < dealerTotal) {
                        JDialog L = lossDialog();
                        L.setLocationRelativeTo(jfrm);
                        L.setVisible(true);
                    }
                    else {
                        JDialog P = pushDialog();
                        P.setLocationRelativeTo(jfrm);
                        P.setVisible(true);
                    }
                }
            }
        });

        hitButton.setToolTipText("Press this button to draw another card.");
        standButton.setToolTipText("Press this button to stop drawing.");
        doubleDownButton.setToolTipText("Double your bet, but only draw one more card. Goes away when you choose to hit instead. Cannot be done if you don't have enough money.");

        hitButton.setMnemonic('h');
        standButton.setMnemonic('s');
        doubleDownButton.setMnemonic('d');

        playerTotal = calculateTotal(playerCards);
        updatePlayerTotal();

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

        JPanel cashOutPanel = new JPanel();
        cashOutPanel.setLayout(new BorderLayout());
        JButton cashOutButton = new JButton("Cash out!");
        cashOutPanel.add(cashOutButton, BorderLayout.EAST);

        betScreen.add(Box.createRigidArea(new Dimension(0, 20)));
        JLabel errorNoBetText = new JLabel("You've gotta place some kind of wager, partner!");
        errorNoBetText.setVisible(false);
        errorNoBetText.setAlignmentX(Component.CENTER_ALIGNMENT);
        betScreen.add(errorNoBetText);
        betScreen.add(Box.createRigidArea(new Dimension(0, 30)));
        betScreen.add(cashOutPanel);

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
        cashOutButton.addActionListener(e -> {
            try {
                JPanel goodEnding = goodEnding();
                mainPanel.add(goodEnding, "Good Ending");
                card.show(mainPanel, "Good Ending");
            } catch (IOException ex) {
                throw new RuntimeException(ex);
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
            walletAmount = walletAmount + (2 * betAmount);
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

        winText.setAlignmentX(Component.CENTER_ALIGNMENT);
        winAmount.setAlignmentX(Component.CENTER_ALIGNMENT);
        confirmButton.setAlignmentX(Component.CENTER_ALIGNMENT);

        mainPanel.add(winText);
        mainPanel.add(Box.createRigidArea(new Dimension(0, 20)));
        mainPanel.add(winAmount);
        mainPanel.add(Box.createRigidArea(new Dimension(0, 20)));
        mainPanel.add(confirmButton);

        mainDialog.add(mainPanel);

        setWalletAmount(true);
        clearBet();

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
            if (walletAmount <= 0) {
                card.show(mainPanel, "Game Over Panel");
                mainPanel.revalidate();
            }
            else {
                jfrm.dispose();
                try {
                    Game newGame = new Game(deck, walletAmount);
                } catch (IOException ex) {
                    throw new RuntimeException(ex);
                } catch (InterruptedException ex) {
                    throw new RuntimeException(ex);
                }
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

    public JDialog pushDialog() {
        JDialog mainDialog = new JDialog(jfrm, true);
        mainDialog.setTitle("Push!");
        mainDialog.setLocationRelativeTo(null);

        mainDialog.setSize(400, 300);
        JPanel mainDialogPanel = new JPanel();
        mainDialogPanel.setLayout(new BoxLayout(mainDialogPanel, BoxLayout.Y_AXIS));

        JLabel pushText = new JLabel("Push!");
        JLabel pushAmount = new JLabel("Returned $" + betAmount);
        JButton confirmButton = new JButton("Okay.");
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

        pushText.setAlignmentX(Component.CENTER_ALIGNMENT);
        pushAmount.setAlignmentX(Component.CENTER_ALIGNMENT);
        confirmButton.setAlignmentX(Component.CENTER_ALIGNMENT);

        pushText.setFont(new Font("PushText", Font.BOLD, 24));
        pushAmount.setFont(new Font("PushAmount", Font.BOLD, 24));
        pushAmount.setForeground(Color.GRAY);

        mainDialogPanel.add(pushText);
        mainDialogPanel.add(Box.createRigidArea(new Dimension(0, 20)));
        mainDialogPanel.add(pushAmount);
        mainDialogPanel.add(Box.createRigidArea(new Dimension(0, 20)));
        mainDialogPanel.add(confirmButton);

        mainDialog.add(mainDialogPanel);

        walletAmount = walletAmount + betAmount;
        clearBet();

        return mainDialog;
    }

    public JPanel goodEnding() throws IOException {
        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.Y_AXIS));

        JLabel victoryText = new JLabel();
        Image victoryImage;
        ImageIcon victoryIcon = new ImageIcon();
        JLabel victoryImageLabel = new JLabel();
        if (walletAmount >= 1000000) {
            victoryText.setText("On top of the world! Nice going, big shot!");
            victoryImage = ImageIO.read(new File("Ending Images/scrooge.jpg"));
        }
        else if (walletAmount >= 100000) {
            victoryText.setText("Nice job, kid! You've earned yourself high-roller status!");
            victoryImage = ImageIO.read(new File("Ending Images/mrKrabs.jpg"));
        }
        else if (walletAmount >= 10000) {
            victoryText.setText("Now we're getting somewhere. Nice one, kid. Keep going.");
            victoryImage = ImageIO.read(new File("Ending Images/oceansEleven.jpg"));
        }
        else if (walletAmount > 2500) {
            victoryText.setText("A net positive for sure, but can we get any higher?");
            victoryImage = ImageIO.read(new File("Ending Images/theOnePieceIsReal.jpg"));
        }
        else if (walletAmount == 2500) {
            victoryText.setText("Well, at least 'ya didn't lose anything. Try again, kid.");
            victoryImage = ImageIO.read(new File("Ending Images/letsGoGambling.jpg"));
        }
        else {
            victoryText.setText("...shoulda kept going, kid.");
            victoryImage = ImageIO.read(new File("Ending Images/facepalm.jpg"));
        }
        victoryIcon.setImage(victoryImage);
        victoryImageLabel.setIcon(victoryIcon);

        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new BoxLayout(buttonPanel, BoxLayout.X_AXIS));
        JButton tryAgain = new JButton("Try again?");
        JButton quit = new JButton("Quit");
        tryAgain.addActionListener(e -> {
            jfrm.dispose();
            try {
                Game trueNewGame = new Game(deck);
            } catch (IOException ex) {
                throw new RuntimeException(ex);
            } catch (InterruptedException ex) {
                throw new RuntimeException(ex);
            }
        });
        quit.addActionListener(e -> {
            jfrm.dispose();
        });
        buttonPanel.add(tryAgain);
        buttonPanel.add(Box.createRigidArea(new Dimension(10, 0)));
        buttonPanel.add(quit);

        victoryText.setFont(new Font("Victory", Font.BOLD, 24));

        victoryText.setAlignmentX(Component.CENTER_ALIGNMENT);
        victoryImageLabel.setAlignmentX(Component.CENTER_ALIGNMENT);

        mainPanel.add(Box.createRigidArea(new Dimension(0, 10)));
        mainPanel.add(victoryText);
        mainPanel.add(Box.createRigidArea(new Dimension(0, 50)));
        mainPanel.add(victoryImageLabel);
        mainPanel.add(Box.createRigidArea(new Dimension(0, 50)));
        mainPanel.add(buttonPanel);

        return mainPanel;
    }

    public JPanel badEnding() throws IOException {
        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.Y_AXIS));

        JLabel line1 = new JLabel("The House always wins...");
        JLabel line2 = new JLabel("...but you could've won so much more.");

        Image cashMoneyImage = ImageIO.read(new File("Ending Images/spongebobCash.png"));
        Image loserImage = ImageIO.read(new File("Ending Images/neverquit.jpg"));
        ImageIcon cashMoneyIcon = new ImageIcon(cashMoneyImage);
        ImageIcon loserIcon = new ImageIcon(loserImage);
        JLabel imageLabel = new JLabel();

        JPanel subPanel = new JPanel();
        subPanel.setLayout(new BoxLayout(subPanel, BoxLayout.X_AXIS));
        JButton tryAgain = new JButton("Try again?");
        JButton quit = new JButton("Quit");

        line1.setFont(new Font("Line 1", Font.BOLD, 36));
        line2.setFont(new Font("Line 2", Font.BOLD, 24));

        line1.setAlignmentX(Component.CENTER_ALIGNMENT);
        line2.setAlignmentX(Component.CENTER_ALIGNMENT);
        tryAgain.setAlignmentX(Component.CENTER_ALIGNMENT);
        quit.setAlignmentX(Component.CENTER_ALIGNMENT);
        imageLabel.setAlignmentX(Component.CENTER_ALIGNMENT);

        tryAgain.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                imageLabel.setIcon(cashMoneyIcon);
            }

            @Override
            public void mouseExited(MouseEvent e) {
                imageLabel.setIcon(null);
            }
        });
        quit.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                imageLabel.setIcon(loserIcon);
            }

            @Override
            public void mouseExited(MouseEvent e) {
                imageLabel.setIcon(null);
            }
        });
        tryAgain.addActionListener(e -> {
            jfrm.dispose();
            try {
                Game trueNewGame = new Game(deck);
            } catch (IOException ex) {
                throw new RuntimeException(ex);
            } catch (InterruptedException ex) {
                throw new RuntimeException(ex);
            }
        });
        quit.addActionListener(e -> {
            jfrm.dispose();
        });

        subPanel.add(tryAgain);
        subPanel.add(Box.createRigidArea(new Dimension(10, 0)));
        subPanel.add(quit);

        mainPanel.add(line1);
        mainPanel.add(Box.createRigidArea(new Dimension(0, 100)));
        mainPanel.add(line2);
        mainPanel.add(Box.createRigidArea(new Dimension(0, 100)));
        mainPanel.add(subPanel);
        mainPanel.add(Box.createRigidArea(new Dimension(0, 20)));
        mainPanel.add(imageLabel);

        return mainPanel;
    }

    public void updateTotals() {
        dealerTotalText.setText("Dealer's total: " + dealerTotal);
        playerTotalText.setText("Your total: " + playerTotal);
    }
    public void updatePlayerTotal() {
        dealerTotalText.setText("Dealer's total: ?");
        playerTotalText.setText("Your total: " + playerTotal);
    }

    public int calculateTotal(ArrayList<Card> hand) {
        //create placeholder for total score and ace count
        int total = 0;
        int aceCount = 0;

        //iterate through the cards in the player or dealer's hand
        for (int i  = 0; i < hand.size(); i++) {
            Card card = hand.get(i);
            total += card.getValue();

            //find if there's any aces in the hand and add it to aceCount
            if (card.getRank().equals("Ace")) {
                aceCount++;
            }
        }
        //Ace can be 1 or 11 points, let ace equal to 11 if score allows it without busting
        //Converting ace from 1 to 11 points -> 10 points added
        //if score is above 11, then converting an ace will result in a bust, so exit loop without worrying about aceCount
        while (total <= 11 && aceCount > 0) {
            total += 10;
            aceCount--;
        }
        return total;
    }
}
