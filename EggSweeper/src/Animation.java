import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JLayeredPane;
import javax.swing.JPanel;

// The View

public class Animation extends JPanel{
	
	private List<AniObject> images;
	
	// constants for placing buttons
	public static int buffer = 50;
	public static int gridButtonSize = 45;
	public static int generalButtonSize = 200;
	public static int contentPaneSize = 1000;
	
	public List<AniObject> getImages() {
		return images;
	}
	
	// constructor, takes a Game object to link GUI to controller
	Animation(){
		images = new ArrayList<AniObject>();
		this.setPreferredSize(new Dimension(contentPaneSize, contentPaneSize));
		try {
			BufferedImage beach = ImageIO.read(new File("src/images/beach.png"));
			images.add(new AniObject("beach", buffer, buffer, contentPaneSize - (2*buffer), contentPaneSize - (2*buffer), beach));
		} catch (IOException e) {
			System.out.println("Failed to load beach");
		}
		
	}
	
	public void paint(Graphics g) {
		for(int imageNum = 0; imageNum < images.size(); imageNum++) {
			if (images.get(imageNum).getVisible() == true) {
				g.drawImage(images.get(imageNum).getImage(), images.get(imageNum).getX(), images.get(imageNum).getY(), images.get(imageNum).getXSize(), images.get(imageNum).getYSize(), this);
			}
		}
    	
	}

	public void addHole(int xIndex, int yIndex) {
		AniObject hole;
		try {
			hole = new AniObject("hole", buffer + (xIndex*gridButtonSize), buffer + (yIndex*gridButtonSize), gridButtonSize, gridButtonSize, ImageIO.read(new File("src/images/hole.png")));
			hole.setVisible(true);
            images.add(hole);
		} catch (IOException e1) {
			System.out.println("failed to load hole");
		}
		
	}

	public void migrationAnimation() {
		AniObject bird;
		AniObject US;
		try {
			US = new AniObject("US", 100, 100, 600, 800, ImageIO.read(new File("src/images/US.png")));
            US.setVisible(true);
            images.add(US);
			bird = new AniObject("bird", (int) Math.round(contentPaneSize*(3./5.)), (int) Math.round(contentPaneSize*(4./5.)), 100, 150, ImageIO.read(new File("src/images/bird.png")));
			bird.setVisible(true);
            images.add(bird);
		} catch (IOException e1) {
			System.out.println("failed to load hole");
		}
		
	}
	
}