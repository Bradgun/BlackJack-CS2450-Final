import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Random;

public class Game {
    ArrayList<Card> deck = new ArrayList<>();
    JButton hitButton = new JButton("Hit");
    JButton standButton = new JButton("Stand");
    JButton splitButton = new JButton("Split");
    JButton doubleDownButton = new JButton("Double Down");

    ArrayList<Chip> chips = new ArrayList<>();
    ArrayList<JLabel> chipsVisual = new ArrayList<>();

//    Color casinoGreen = new Color(0, 210, 0);

    Image chipSprite1 = ImageIO.read(new File("Chip Images/chip1.png"));
    Image chipSprite10 = ImageIO.read(new File("Chip Images/chip10.png"));
    Image chipSprite100 = ImageIO.read(new File("Chip Images/chip100.png"));
    Image chipSprite500 = ImageIO.read(new File("Chip Images/chip500.png"));
    Image chipSprite1000 = ImageIO.read(new File("Chip Images/chip1000.png"));
    Image chipSprite5000 = ImageIO.read(new File("Chip Images/chip5000.png"));

    Image scaled1 = chipSprite1.getScaledInstance(100, 100, Image.SCALE_SMOOTH);
    Image scaled10 = chipSprite10.getScaledInstance(100, 100, Image.SCALE_SMOOTH);
    Image scaled100 = chipSprite100.getScaledInstance(100, 100, Image.SCALE_SMOOTH);
    Image scaled500 = chipSprite500.getScaledInstance(100, 100, Image.SCALE_SMOOTH);
    Image scaled1000 = chipSprite1000.getScaledInstance(100, 100, Image.SCALE_SMOOTH);
    Image scaled5000 = chipSprite5000.getScaledInstance(100, 100, Image.SCALE_SMOOTH);

//    Chip chip1 = new Chip(1, scaled1);  // Set sprite size to rescaled variant
//    Chip chip10 = new Chip(10, scaled10);
//    Chip chip100 = new Chip(100, scaled100);
//    Chip chip500 = new Chip(500, scaled500);
//    Chip chip1000 = new Chip(1000, scaled1000);
//    Chip chip5000 = new Chip(5000, scaled5000);

    Chip chip1 = new Chip(1, chipSprite1);    // Set sprite size to file default
    Chip chip10 = new Chip(10, chipSprite10);
    Chip chip100 = new Chip(100, chipSprite100);
    Chip chip500 = new Chip(500, chipSprite500);
    Chip chip1000 = new Chip(1000, chipSprite1000);
    Chip chip5000 = new Chip(5000, chipSprite5000);

//    ArrayList<Card> dealerHand = new ArrayList<>();
//    ArrayList<Card> playerHand = new ArrayList<>();

    ArrayList<JLabel> dealerHandVisual = new ArrayList<>();
    ArrayList<JLabel> playerHandVisual = new ArrayList<>();

    int walletAmount = 2500;
    int betAmount = 0;

    JFrame jfrm = new JFrame("Mack's Sidetrack Blackjack");
    CardLayout card = new CardLayout();
    JPanel mainPanel = new JPanel(card);

    Game(ArrayList<Card> givenDeck) throws IOException {
        deck.addAll(givenDeck);
        chips.add(chip1);
        chips.add(chip10);
        chips.add(chip100);
        chips.add(chip500);
        chips.add(chip1000);
        chips.add(chip5000);

        jfrm.setSize(960, 720);
        jfrm.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        jfrm.setLocationRelativeTo(null);

        mainPanel.add(betPanel(), "Bet Panel");
        mainPanel.add(dealPanel(), "Deal Panel");

        card.show(mainPanel, "Bet Panel");

        jfrm.add(mainPanel);
        jfrm.setVisible(true);
    }

    private JPanel dealPanel() throws IOException {
        JPanel dealScreen = new JPanel();
        dealScreen.setLayout(new BoxLayout(dealScreen, BoxLayout.Y_AXIS));

        JPanel header = new JPanel();
        header.setLayout(new BoxLayout(header, BoxLayout.Y_AXIS));
        JLabel walletText = new JLabel("Wallet: $" + walletAmount);
        JLabel betText = new JLabel("Bet: $" + betAmount);
        header.add(walletText);
        header.add(betText);

        JPanel dealerHand = new JPanel();
        ArrayList<Card> dealerCards = new ArrayList<>();
        ArrayList<JLabel> dealerCardsVisual = new ArrayList<>();

        JPanel buttonRow = new JPanel();
        hitButton.addActionListener(e -> {

        });
        standButton.addActionListener(e -> {

        });

        splitButton.setVisible(false);
        splitButton.addActionListener(e -> {

        });
        doubleDownButton.setVisible(false);
        doubleDownButton.addActionListener(e -> {

        });
        buttonRow.add(hitButton);
        buttonRow.add(standButton);
        buttonRow.add(splitButton);
        buttonRow.add(doubleDownButton);

        JPanel playerHand = new JPanel();
        ArrayList<Card> playerCards = new ArrayList<>();
        ArrayList<JLabel> playerCardsVisual = new ArrayList<>();

        dealScreen.add(header);
        dealScreen.add(dealerHand);
        dealScreen.add(buttonRow);
        dealScreen.add(playerHand);

        return dealScreen;
    }

    private JPanel betPanel() throws IOException {
        JPanel betScreen = new JPanel();
        betScreen.setLayout(new BoxLayout(betScreen, BoxLayout.Y_AXIS)); //Rows of panels
//        betScreen.setBackground(casinoGreen); //Temporary, change later

        JPanel header = new JPanel();
//        header.setBackground(casinoGreen);
//        header.setLayout(new BoxLayout(header, BoxLayout.X_AXIS));
        JLabel walletText = new JLabel("Wallet: $" + walletAmount);
        walletText.setFont(new Font("Wallet", Font.BOLD, 36));
        walletText.setAlignmentX(Component.LEFT_ALIGNMENT);
        header.add(walletText);

        JPanel betValueLine = new JPanel();
//        betValueLine.setBackground(casinoGreen);
        betValueLine.setLayout(new BoxLayout(betValueLine, BoxLayout.Y_AXIS));
        JLabel totalBetText = new JLabel("Total Bet:");
        JLabel betAmountText = new JLabel("$" + betAmount);
        totalBetText.setFont(new Font("Total Bet", Font.PLAIN, 36));
        betAmountText.setFont(new Font("Bet Amount", Font.BOLD, 36));
        totalBetText.setAlignmentX(Component.CENTER_ALIGNMENT);
        betAmountText.setAlignmentX(Component.CENTER_ALIGNMENT);
        betValueLine.add(totalBetText);
        betValueLine.add(betAmountText);

        JPanel removeChipsButtonRow = new JPanel();
//        removeChipsButtonRow.setBackground(casinoGreen);
        removeChipsButtonRow.setLayout(new BoxLayout(removeChipsButtonRow, BoxLayout.X_AXIS));
        for (int i = chips.size() - 1; i >= 0; i--) {
            JButton removeChipButton = new JButton("-$" + chips.get(i).getValue());
//            removeChipButton.setBorder(BorderFactory.createLineBorder(Color.BLACK));
            removeChipButton.setAlignmentX(Component.CENTER_ALIGNMENT);
            removeChipButton.setPreferredSize(new Dimension(40, 20));
            int finalI = i;
            removeChipButton.addActionListener(e -> {
                if (!(betAmount - chips.get(finalI).getValue() < 0)) {
                    betAmount = betAmount - chips.get(finalI).getValue();
                    walletAmount = walletAmount + chips.get(finalI).getValue();
                    walletText.setText("Wallet: $" + walletAmount);
                    betAmountText.setText("$" + betAmount);
                }
            });
            removeChipsButtonRow.add(removeChipButton);
            if (i > 0) {
                removeChipsButtonRow.add(Box.createRigidArea(new Dimension(45, 0)));
            }
        }
        removeChipsButtonRow.revalidate();

        JPanel chipsAvailable = new JPanel();
//        chipsAvailable.setBackground(casinoGreen);
        chipsAvailable.setLayout(new BoxLayout(chipsAvailable, BoxLayout.X_AXIS));
        ArrayList<JLabel> chipsAvailableVisual = new ArrayList<>();
        chipsAvailableVisual.add(chip5000.getPictureAsset());
        chipsAvailableVisual.add(chip1000.getPictureAsset());
        chipsAvailableVisual.add(chip500.getPictureAsset());
        chipsAvailableVisual.add(chip100.getPictureAsset());
        chipsAvailableVisual.add(chip10.getPictureAsset());
        chipsAvailableVisual.add(chip1.getPictureAsset());

        for (int i = 0; i < chipsAvailableVisual.size(); i++) {
            chipsAvailableVisual.get(i).setAlignmentX(Component.CENTER_ALIGNMENT);
            chipsAvailable.add(chipsAvailableVisual.get(i));
            chipsAvailable.add(Box.createRigidArea(new Dimension(15, 0)));
        }

        JLabel removeBetPromptLine = new JLabel("Remove from bet:");
        removeBetPromptLine.setAlignmentX(Component.CENTER_ALIGNMENT);

        JLabel addBetPromptLine = new JLabel("Add to bet:");
        addBetPromptLine.setAlignmentX(Component.CENTER_ALIGNMENT);

        JPanel addChipsButtonRow = new JPanel();
//        addChipsButtonRow.setBackground(casinoGreen);
        addChipsButtonRow.setLayout(new BoxLayout(addChipsButtonRow, BoxLayout.X_AXIS));
        for (int i = chips.size() - 1; i >= 0; i--) {
            JButton addChipButton = new JButton("+$" + chips.get(i).getValue());
//            addChipButton.setBorder(BorderFactory.createLineBorder(Color.BLACK));
            addChipButton.setAlignmentX(Component.CENTER_ALIGNMENT);
            addChipButton.setPreferredSize(new Dimension(40, 20));
            int finalI = i;
            addChipButton.addActionListener(e -> {
                    //Bet goes up as wallet goes down
                if (!(walletAmount - chips.get(finalI).getValue() < 0)) {
                    betAmount = betAmount + chips.get(finalI).getValue();
                    walletAmount = walletAmount - chips.get(finalI).getValue();
                    walletText.setText("Wallet: $" + walletAmount);
                    betAmountText.setText("$" + betAmount);
                }
            });
            addChipsButtonRow.add(addChipButton);
            if (i > 0) {
                addChipsButtonRow.add(Box.createRigidArea(new Dimension(45, 0)));
            }
        }

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
        betScreen.add(removeChipsButtonRow);
        betScreen.add(Box.createRigidArea(new Dimension(0, 20)));
        betScreen.add(chipsAvailable);
        betScreen.add(Box.createRigidArea(new Dimension(0, 10)));
        betScreen.add(addBetPromptLine);
        betScreen.add(Box.createRigidArea(new Dimension(0, 10)));
        betScreen.add(addChipsButtonRow);
        betScreen.add(Box.createRigidArea(new Dimension(0, 30)));
        betScreen.add(miscBets);
        betScreen.add(Box.createRigidArea(new Dimension(0, 50)));

        JButton playButton = new JButton("Deal Me!");
        playButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        playButton.setPreferredSize(new Dimension(80, 40));
        playButton.addActionListener(e -> {
            card.show(mainPanel, "Deal Panel");
        });
        betScreen.add(playButton);

        betScreen.add(Box.createRigidArea(new Dimension(0, 50)));

        return betScreen;
    }
}
