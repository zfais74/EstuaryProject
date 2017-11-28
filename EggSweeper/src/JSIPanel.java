import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import static javax.imageio.ImageIO.*;

public class JSIPanel extends JPanel{
    @Override
    protected void paintComponent(Graphics g) {

        super.paintComponent(g);
        try {
            g.drawImage(ImageIO.read(new File("beach-Yat.png")), 0, 0, null);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
