import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.io.File;
import java.io.IOException;

public class Chip {
    int value;
    Image picture;
    JLabel pictureAsset;
    JButton add;
    JButton remove;

    Chip(int value, Image picture) {
        this.value = value;
        this.picture = picture;

        Image scaled = picture.getScaledInstance(100, 140, Image.SCALE_DEFAULT);
        ImageIcon icon = new ImageIcon(scaled);
        pictureAsset = new JLabel(icon);

        add = new JButton("+$" + value);
        remove = new JButton("-$" + value);
    }
    public int getValue() {
        return value;
    }
    public Image getPicture() {
        return picture;
    }
    public JLabel getPictureAsset() {
        return pictureAsset;
    }
    public JLabel getPictureAssetScaled(int width, int height) {
        Image scaled = picture.getScaledInstance(width, height, Image.SCALE_DEFAULT);
        ImageIcon scaledIcon = new ImageIcon(scaled);
        return new JLabel(scaledIcon);
    }
    public JButton getAddButton() {
        return add;
    }
    public JButton getRemoveButton() {
        return remove;
    }
}
