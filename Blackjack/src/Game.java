import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;
import java.util.Collections;

public class Game {
    ArrayList<Card> deck = new ArrayList<>();

    JFrame jfrm = new JFrame("Mack's Sidetrack Blackjack");

    Game(ArrayList<Card> givenDeck) {
        deck.addAll(givenDeck);


    }

    private JLabel makeCardAsset(Card card) {
        Image scaledCard = card.getPicture().getScaledInstance(100, 140, Image.SCALE_DEFAULT);
        ImageIcon cardIcon = new ImageIcon(scaledCard);
        return new JLabel(cardIcon);
    }
}
