import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
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
	
	private int frame = 0;
	
	public List<AniObject> getImages() {
		return images;
	}
	
	// constructor, takes a Game object to link GUI to controller
	Animation(){
		images = new ArrayList<AniObject>();
		this.setPreferredSize(new Dimension(ratioW, ratioH));
		try {
			BufferedImage beach = ImageIO.read(new File("images/beach.png"));
			List<BufferedImage> beachList = new ArrayList<BufferedImage>();
			beachList.add(beach);
			images.add(new AniObject("beach", 0, 0, 1200, 900, beachList));
		} catch (IOException e) {
			System.out.println("Failed to load beach, trying bin folder");
		}
		try {
			BufferedImage beach = ImageIO.read(new File("beach.png"));
			List<BufferedImage> beachList = new ArrayList<BufferedImage>();
			beachList.add(beach);
			images.add(new AniObject("beach", 0, 0, 1200, 900, beachList));
		} catch (IOException e) {
			System.out.println("Failed to load beach");
		}

		try {
			BufferedImage board = ImageIO.read(new File("images/board.png"));
			List<BufferedImage> boardList = new ArrayList<BufferedImage>();
			boardList.add(board);
			images.add(new AniObject("board", 100,254, 1000, 646, boardList));
		} catch (IOException e) {
			System.out.println("Failed to load board, trying bin folder");
		}
		
		try {
			BufferedImage board = ImageIO.read(new File("board.png"));
			List<BufferedImage> boardList = new ArrayList<BufferedImage>();
			boardList.add(board);
			images.add(new AniObject("board", 100,254, 1000, 646, boardList));
		} catch (IOException e) {
			System.out.println("Failed to load board");
		}
		
		try {
			BufferedImage grass = ImageIO.read(new File("images/grass.png"));
			List<BufferedImage> grassList = new ArrayList<BufferedImage>();
			grassList.add(grass);
			images.add(new AniObject("grass1", 180,150, 100, 160, grassList));
			images.add(new AniObject("grass2", 0,150, 100, 160, grassList));
			images.add(new AniObject("grass3", 130,200, 110, 175, grassList));
			images.add(new AniObject("grass4", - 25,250, 115, 180, grassList));
			images.add(new AniObject("grass5", 70,300, 120, 185, grassList));
			images.add(new AniObject("grass6", -15,380, 125, 190, grassList));
			images.add(new AniObject("grass7", 100,400, 125, 190, grassList));
			images.add(new AniObject("grass8", 75,480, 135, 200, grassList));
			images.add(new AniObject("grass9", -20,520, 145, 210, grassList));
			images.add(new AniObject("grass10", 30,600, 150, 240, grassList));
		} catch (IOException e) {
			System.out.println("Failed to load grass, trying bin folder");
		}
		
		try {
			BufferedImage grass = ImageIO.read(new File("grass.png"));
			List<BufferedImage> grassList = new ArrayList<BufferedImage>();
			grassList.add(grass);
			images.add(new AniObject("grass1", 180,150, 100, 160, grassList));
			images.add(new AniObject("grass2", 0,150, 100, 160, grassList));
			images.add(new AniObject("grass3", 130,200, 110, 175, grassList));
			images.add(new AniObject("grass4", - 25,250, 115, 180, grassList));
			images.add(new AniObject("grass5", 70,300, 120, 185, grassList));
			images.add(new AniObject("grass6", -15,380, 125, 190, grassList));
			images.add(new AniObject("grass7", 100,400, 125, 190, grassList));
			images.add(new AniObject("grass8", 75,480, 135, 200, grassList));
			images.add(new AniObject("grass9", -20,520, 145, 210, grassList));
			images.add(new AniObject("grass10", 30,600, 150, 240, grassList));
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
				g.drawImage(image.getImage(frame), image.getX(), image.getY(), image.getXSize(), image.getYSize(), this);
			}
		}
		frame++;
    	
	}

	public void addHole(int xLoc, int yLoc, int sizeX, int sizeY) {
		try {
            BufferedImage hole = ImageIO.read(new File("images/hole2.png"));
			List<BufferedImage> holeList = new ArrayList<BufferedImage>();
			holeList.add(hole);
			AniObject holeObject = new AniObject("hole", xLoc, yLoc, sizeX, sizeY, holeList);
			holeObject.setVisible(true);
			images.add(holeObject);
            return;
		} catch (IOException e1) {
			System.out.println("failed to load hole, trying bin folder");
		}
		
		try {
			BufferedImage hole = ImageIO.read(new File("hole2.png"));
			List<BufferedImage> holeList = new ArrayList<BufferedImage>();
			holeList.add(hole);
			AniObject holeObject = new AniObject("hole", xLoc, yLoc, sizeX, sizeY, holeList);
			holeObject.setVisible(true);
			images.add(holeObject);
		} catch (IOException e1) {
			System.out.println("failed to load hole");
		}
		
		
	}


	public void addChest() {
		try {
			BufferedImage chest = ImageIO.read(new File("images/chest.png"));
			List<BufferedImage> chestList = new ArrayList<BufferedImage>();
			chestList.add(chest);
			AniObject chestObject = new AniObject("chest", 1410, 600, 200, 200, chestList);
			chestObject.setVisible(false);
			images.add(chestObject);
            return;
		} catch (IOException e1) {
			System.out.println("failed to load chest, trying bin folder");
		}
		
		try {
			BufferedImage chest = ImageIO.read(new File("chest.png"));
			List<BufferedImage> chestList = new ArrayList<BufferedImage>();
			chestList.add(chest);
			AniObject chestObject = new AniObject("chest", 1410, 600, 200, 200, chestList);
			chestObject.setVisible(false);
			images.add(chestObject);
            
		} catch (IOException e1) {
			System.out.println("failed to load chest");
		}
		
		return;
	}

	public void migrationAnimation() {
		try {			
			BufferedImage US = ImageIO.read(new File("images/US.png"));
			List<BufferedImage> USList = new ArrayList<BufferedImage>();
			USList.add(US);
			AniObject USObject = new AniObject("US", 100, 100, 450, 600, USList);
			USObject.setVisible(true);
			images.add(USObject);
			
			BufferedImage bird1 = ImageIO.read(new File("images/bird1.png"));
			BufferedImage bird2 = ImageIO.read(new File("images/bird2.png"));
			BufferedImage bird3 = ImageIO.read(new File("images/bird3.png"));
			List<BufferedImage> birdList = new ArrayList<BufferedImage>();
			birdList.add(bird1);
			birdList.add(bird2);
			birdList.add(bird3);
			AniObject birdObject = new AniObject("bird", 600, 600, 100, 150, birdList);
			birdObject.setVisible(true);
			images.add(birdObject);
            return;
		} catch (IOException e1) {
			System.out.println("failed to load US or Bird, trying bin folder");
		}
		try {
			BufferedImage US = ImageIO.read(new File("US.png"));
			List<BufferedImage> USList = new ArrayList<BufferedImage>();
			USList.add(US);
			AniObject USObject = new AniObject("US", 100, 100, 450, 600, USList);
			USObject.setVisible(true);
			images.add(USObject);
			
			BufferedImage bird1 = ImageIO.read(new File("bird1.png"));
			BufferedImage bird2 = ImageIO.read(new File("bird2.png"));
			BufferedImage bird3 = ImageIO.read(new File("bird3.png"));
			List<BufferedImage> birdList = new ArrayList<BufferedImage>();
			birdList.add(bird1);
			birdList.add(bird2);
			birdList.add(bird3);
			AniObject birdObject = new AniObject("bird", 600, 600, 100, 150, birdList);
			birdObject.setVisible(true);
			images.add(birdObject);
		} catch (IOException e1) {
			System.out.println("failed to load US or Bird");
		}
		return;
	}
	
	public ImageIcon getChestIcon(){
		ImageIcon chest = new ImageIcon("images/chest.png");
		Image chestImage = chest.getImage();
		Image resizedChest = chestImage.getScaledInstance( 200, 200,  java.awt.Image.SCALE_SMOOTH ) ;  
		return new ImageIcon(resizedChest);
	}
	
}