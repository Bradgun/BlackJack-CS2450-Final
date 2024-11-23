import javax.imageio.ImageIO;
import java.awt.*;
import java.io.File;
import java.io.IOException;

public class Card {
    String rank;
    String suit;
    int value;
    Image picture;

    Card(String rank, String suit, int value, String imagePath) throws IOException {
        this.rank = rank;
        this.suit = suit;
        this.value = value;

        picture = ImageIO.read(new File(imagePath));
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

}
