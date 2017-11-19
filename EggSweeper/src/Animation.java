import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import javax.imageio.ImageIO;
import javax.swing.JPanel;

// The View

public class Animation extends JPanel{
	
	private List<AniObject> images;
	
	// constants for placing buttons
	public int buffer = 50;
	public int gridButtonSize = 45;
	public int generalButtonSize = 200;
	public int contentPaneSize = 900;
	public int ratioW = 1600;
	public int ratioH = 900;
	
	public List<AniObject> getImages() {
		return images;
	}
	
	// constructor, takes a Game object to link GUI to controller
	Animation(){
		images = new ArrayList<AniObject>();
		this.setPreferredSize(new Dimension(ratioW, ratioH));
		try {
			BufferedImage beach = ImageIO.read(new File("images/beach.png"));
			images.add(new AniObject("beach", 0, 0, 1200, 900, beach));
		} catch (IOException e) {
			System.out.println("Failed to load beach, trying bin folder");
		}
		try {
			BufferedImage beach = ImageIO.read(new File("beach.png"));
			images.add(new AniObject("beach", 0, 0, 1200, 900, beach));
		} catch (IOException e) {
			System.out.println("Failed to load beach");
		}

		try {
			BufferedImage board = ImageIO.read(new File("images/board.png"));
			images.add(new AniObject("board", 100,254, 1000, 646, board));
		} catch (IOException e) {
			System.out.println("Failed to load board, trying bin folder");
		}
		
		try {
			BufferedImage board = ImageIO.read(new File("board.png"));
			images.add(new AniObject("board", 100, 254, 1000, 646, board));
		} catch (IOException e) {
			System.out.println("Failed to load board");
		}
		
		try {
			BufferedImage grass = ImageIO.read(new File("images/grass.png"));
			images.add(new AniObject("grass1", 180,150, 100, 160, grass));
			images.add(new AniObject("grass2", 0,150, 100, 160, grass));
			images.add(new AniObject("grass3", 130,200, 110, 175, grass));
			images.add(new AniObject("grass4", - 25,250, 115, 180, grass));
			images.add(new AniObject("grass5", 70,300, 120, 185, grass));
			images.add(new AniObject("grass6", -15,380, 125, 190, grass));
			images.add(new AniObject("grass7", 100,400, 125, 190, grass));
			images.add(new AniObject("grass8", 75,480, 135, 200, grass));
			images.add(new AniObject("grass9", -20,520, 145, 210, grass));
			images.add(new AniObject("grass10", 30,600, 150, 240, grass));
		} catch (IOException e) {
			System.out.println("Failed to load grass, trying bin folder");
		}
		
		try {
			BufferedImage grass = ImageIO.read(new File("grass.png"));
			images.add(new AniObject("grass1", 180,150, 100, 160, grass));
			images.add(new AniObject("grass2", 0,150, 100, 160, grass));
			images.add(new AniObject("grass3", 130,200, 110, 175, grass));
			images.add(new AniObject("grass4", - 25,250, 115, 180, grass));
			images.add(new AniObject("grass5", 70,300, 120, 185, grass));
			images.add(new AniObject("grass6", -15,380, 125, 190, grass));
			images.add(new AniObject("grass7", 100,400, 125, 190, grass));
			images.add(new AniObject("grass8", 75,480, 135, 200, grass));
			images.add(new AniObject("grass9", -20,520, 145, 210, grass));
			images.add(new AniObject("grass10", 30,600, 150, 240, grass));
		} catch (IOException e) {
			System.out.println("Failed to load grass");
		}
		
		return;
		
	}
	
	public void paint(Graphics g) {
		Iterator<AniObject> imageIterator = images.iterator();
		while (imageIterator.hasNext()) {
			AniObject image = imageIterator.next();
			if (image.getVisible() == true) {
				g.drawImage(image.getImage(), image.getX(), image.getY(), image.getXSize(), image.getYSize(), this);
			}
		}
    	
	}

	public void addHole(int xLoc, int yLoc, int sizeX, int sizeY) {
		AniObject hole;
		try {
			hole = new AniObject("hole", xLoc, yLoc, sizeX, sizeY, ImageIO.read(new File("images/hole2.png")));
			hole.setVisible(true);
            images.add(hole);
            return;
		} catch (IOException e1) {
			System.out.println("failed to load hole, trying bin folder");
		}
		
		try {
			hole = new AniObject("hole", xLoc, yLoc, sizeX, sizeY, ImageIO.read(new File("hole2.png")));
			hole.setVisible(true);
            images.add(hole);
		} catch (IOException e1) {
			System.out.println("failed to load hole");
		}
		
		
	}


	public void addChest() {
		AniObject chest;
		try {
			chest = new AniObject("chest", 1410, 600, 200, 200, ImageIO.read(new File("images/chest.png")));
			chest.setVisible(false);
            images.add(chest);
            return;
		} catch (IOException e1) {
			System.out.println("failed to load chest, trying bin folder");
		}
		
		try {
			chest = new AniObject("chest", 1410, 600, 200, 200, ImageIO.read(new File("chest.png")));
			chest.setVisible(true);
            images.add(chest);
            
		} catch (IOException e1) {
			System.out.println("failed to load chest");
		}
		
		return;
	}

	public void migrationAnimation() {
		AniObject bird;
		AniObject US;
		try {
			US = new AniObject("US", 100, 100, 450, 600, ImageIO.read(new File("images/US.png")));
            US.setVisible(true);
            images.add(US);
			bird = new AniObject("bird", (int) Math.round(contentPaneSize*(2./5.)), (int) Math.round(contentPaneSize*(3./5.)), 100, 150, ImageIO.read(new File("images/bird.png")));
			bird.setVisible(true);
            images.add(bird);
            return;
		} catch (IOException e1) {
			System.out.println("failed to load US or Bird, trying bin folder");
		}
		try {
			US = new AniObject("US", 100, 100, 450, 600, ImageIO.read(new File("US.png")));
            US.setVisible(true);
            images.add(US);
			bird = new AniObject("bird", (int) Math.round(contentPaneSize*(2./5.)), (int) Math.round(contentPaneSize*(3./5.)), 100, 150, ImageIO.read(new File("bird.png")));
			bird.setVisible(true);
            images.add(bird);
		} catch (IOException e1) {
			System.out.println("failed to load US or Bird");
		}
		return;
		
	}
	
}