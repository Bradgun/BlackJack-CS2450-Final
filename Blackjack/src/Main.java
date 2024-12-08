import javax.swing.*;
import java.awt.*;
import java.awt.event.WindowEvent;
import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;

public class Main {
    public static ArrayList<Card> deck = new ArrayList<>();
    static JFrame jfrm = new JFrame("Blackjack");
    static JPanel tutorialPanel;

    public static void main(String[] args) throws InterruptedException, IOException {
//        printSleepMessage("Initializing deck...", 1500);

    // Standard deck
//        printSleepMessage("Initializing deck...", 1000);
        initializeCards("Spades", "Card Images/Spades");
        initializeCards("Hearts", "Card Images/Hearts");
        initializeCards("Clubs", "Card Images/Clubs");
//        initializeCards("Diamonds", "Card Images/Diamonds");
        deck.add(new Card("Back", "None", 0, "Card Images/Misc/cardBack.png")); // Add back of card sprite
//        printSleepMessage("Deck created.", 500);

//        printSleepMessage("Initializing chips...", 1000);
//        printSleepMessage("Chips created.", 500);

//        funnyThread(); //Comment this out to run the thing faster :)

//        printSleepMessage("Running simulation...", 1500);

        jfrm.setSize(640, 480);
        jfrm.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        jfrm.setLocationRelativeTo(null);
        jfrm.setLayout(new CardLayout());

        JPanel startPanel = new JPanel();
        startPanel.setLayout(new BoxLayout(startPanel, BoxLayout.Y_AXIS));

        JLabel header = new JLabel("CS 2450.01 Final Project");
        JLabel subheader = new JLabel("Group 9 -- High Rollers");
        JLabel title = new JLabel("Mack's Sidetrack Blackjack");
        JButton newGame = new JButton("New Game");
        JButton tutorial = new JButton("Manual");

        JPanel subPanel = new JPanel();
        subPanel.setLayout(new BoxLayout(subPanel, BoxLayout.X_AXIS));
        subPanel.add(newGame);
        subPanel.add(Box.createRigidArea(new Dimension(20, 0)));
        subPanel.add(tutorial);

        header.setAlignmentX(Component.CENTER_ALIGNMENT);
        subheader.setAlignmentX(Component.CENTER_ALIGNMENT);
        title.setAlignmentX(Component.CENTER_ALIGNMENT);
        newGame.setAlignmentX(Component.CENTER_ALIGNMENT);
        tutorial.setAlignmentX(Component.CENTER_ALIGNMENT);

        header.setFont(new Font("Header", Font.PLAIN, 24));
        subheader.setFont(new Font("Subheader", Font.PLAIN, 18));
        title.setFont(new Font("Title", Font.BOLD, 36));

        startPanel.add(header);
        startPanel.add(Box.createRigidArea(new Dimension(0, 20)));
        startPanel.add(subheader);
        startPanel.add(Box.createRigidArea(new Dimension(0, 100)));
        startPanel.add(title);
        startPanel.add(Box.createRigidArea(new Dimension(0, 100)));
        startPanel.add(subPanel);

        newGame.addActionListener(ae -> {
            jfrm.dispatchEvent(new WindowEvent(jfrm, WindowEvent.WINDOW_CLOSING));
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
            try {
                Game game = new Game(deck);
            } catch (IOException | InterruptedException e) {
                throw new RuntimeException(e);
            }
        });
        tutorial.addActionListener(e -> {
            try {
                openManual();
            } catch (IOException ex) {
                throw new RuntimeException(ex);
            }
        });

        jfrm.add(startPanel);
        jfrm.setVisible(true);
    }

    public static void printSleepMessage(String message, int sleepDuration) throws InterruptedException {
        System.out.println("> " + message);
        Thread.sleep(sleepDuration);
    }

    public static void initializeCards(String suit, String relativeDirectory) throws IOException {
        // General declaration syntax: Card newCard = new Card("Rank", "Suit", value, "imagePath");
        // General addition syntax: deck.add(newCard);

        Card king = new Card("King", suit, 10, (relativeDirectory + "/kingOf" + suit + ".png"));
        Card queen = new Card("Queen", suit, 10, (relativeDirectory + "/queenOf" + suit + ".png"));
        Card jack = new Card("Jack", suit, 10, (relativeDirectory + "/jackOf" + suit + ".png"));
        Card of10 = new Card("10", suit, 10, (relativeDirectory + "/10Of" + suit + ".png"));
        Card of9 = new Card("9", suit, 9, (relativeDirectory + "/9Of" + suit + ".png"));
        Card of8 = new Card("8", suit, 8, (relativeDirectory + "/8Of" + suit + ".png"));
        Card of7 = new Card("7", suit, 7, (relativeDirectory + "/7Of" + suit + ".png"));
        Card of6 = new Card("6", suit, 6, (relativeDirectory + "/6Of" + suit + ".png"));
        Card of5 = new Card("5", suit, 5, (relativeDirectory + "/5Of" + suit + ".png"));
        Card of4 = new Card("4", suit, 4, (relativeDirectory + "/4Of" + suit + ".png"));
        Card of3 = new Card("3", suit, 3, (relativeDirectory + "/3Of" + suit + ".png"));
        Card of2 = new Card("2", suit, 2, (relativeDirectory + "/2Of" + suit + ".png"));
        Card ace = new Card("Ace", suit, 1, (relativeDirectory + "/aceOf" + suit + ".png"));

        deck.add(king);
        deck.add(queen);
        deck.add(jack);
        deck.add(of10);
        deck.add(of9);
        deck.add(of8);
        deck.add(of7);
        deck.add(of6);
        deck.add(of5);
        deck.add(of4);
        deck.add(of3);
        deck.add(of2);
        deck.add(ace);
    }

    public static void funnyThread() throws InterruptedException {
        System.out.println();
        printSleepMessage("Fetching credentials...", 2000);
        printSleepMessage("Identity validated.", 1000);
        System.out.println();
        printSleepMessage("Performing background check...", 2000);
        printSleepMessage("No criminal history detected. Access granted.", 1000);
    }

    public static void openManual() throws IOException {
        JFrame manualFrame = new JFrame("Manual");
        manualFrame.setLocationRelativeTo(null);
        manualFrame.setSize(960, 720);
        manualFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        JEditorPane manualPane = new JEditorPane();
//        manualPane.setContentType("text/html");
        manualPane.setEditable(false);

        File manualFile = new File("HTML Files/Danjels' Blackjack Manual (2024 Edition) (HTML Markup)/DanjelsBlackjackManual_2024Edition_HTMLMarku.html");
        manualPane.setPage(manualFile.toURI().toURL());

        JScrollPane scrollPane = new JScrollPane(manualPane);
        manualFrame.add(scrollPane);
        manualFrame.setVisible(true);
    }
}