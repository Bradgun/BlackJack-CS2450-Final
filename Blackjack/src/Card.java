import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.io.File;
import java.io.IOException;

public class Card {
    String rank;
    String suit;
    int value;
    Image picture;
    JLabel pictureAsset;

    Card(String rank, String suit, int value, String imagePath) throws IOException {
        this.rank = rank;
        this.suit = suit;
        this.value = value;

        picture = ImageIO.read(new File(imagePath));
        Image scaled = picture.getScaledInstance(100, 140, Image.SCALE_DEFAULT);
        ImageIcon icon = new ImageIcon(scaled);
        pictureAsset = new JLabel(icon);
    }

    public void setValue(int val) {
        value = val;
    }
    public String getRank() {
        return rank;
    }
    public String getSuit() {
        return suit;
    }
    public int getValue() {
        return value;
    }
    public Image getPicture() {
        return picture;
    }
    public JLabel getPictureAsset() { return pictureAsset; }
    public JLabel getPictureAssetScaled(int width, int height) {
        Image scaled = picture.getScaledInstance(width, height, Image.SCALE_DEFAULT);
        ImageIcon scaledIcon = new ImageIcon(scaled);
        return new JLabel(scaledIcon);
    }

}
