import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.io.File;
import java.io.IOException;

public class Chip {
//    String writtenValue;
    int value;
    Image picture;
    JLabel pictureAsset;

    Chip(int value, Image picture) {
        this.value = value;
//        writtenValue = "$" + value;
        this.picture = picture;

        Image scaled = picture.getScaledInstance(100, 140, Image.SCALE_DEFAULT);
        ImageIcon icon = new ImageIcon(scaled);
        pictureAsset = new JLabel(icon);
    }

//    public String getWrittenValue() {
//        return writtenValue;
//    }
    public int getValue() {
        return value;
    }
    public Image getPicture() {
        return picture;
    }
    public JLabel getPictureAsset() {
        return pictureAsset;
    }
}
