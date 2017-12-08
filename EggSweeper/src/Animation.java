import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.MouseInfo;
import java.awt.Point;
import java.awt.Toolkit;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;

// The View
/**
 * This class controls all image loading, painting, and creation of AniObjects.
 * @author Will Ransom
 *
 */
public class Animation extends JPanel implements Serializable{
	
	// List of images to be painted
	private transient List<AniObject> images;
	// board image object must be stored for placing grass of the correct isometric size
	private AniObject boardImage;

	// ratio of the screens native resolution to the value used for placing images
	public double screenRatio;
	// the paint frame
	private int frame = 0;
	private int screenWidth;
	private int screenHeight;
	
	/**
	 * Returns the List of AniObjects which are being used in the game
	 * @return
	 */
	public List<AniObject> getImages() {
		return images;
	}
	
	/**
	 * Constructor, creates beach board and grass AniObjects and sets screen width and height
	 * to the native resolution
	 */
	Animation(){
		Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
       	screenWidth = (int) screenSize.getWidth();
       	screenHeight = (int) screenSize.getHeight();
		images = new ArrayList<AniObject>();
		try {
			BufferedImage beach = ImageIO.read(new File("images/beach5.png"));
			List<BufferedImage> beachList = new ArrayList<BufferedImage>();
			beachList.add(beach);
			images.add(new AniObject("beach", 0, 0, (int) ((3./4.) * screenWidth), screenHeight, beachList));
		} catch (IOException e) {
			System.out.println("Failed to load beach, trying bin folder");
		}

		try {
			BufferedImage board = ImageIO.read(new File("images/board.png"));
			List<BufferedImage> boardList = new ArrayList<BufferedImage>();
			boardList.add(board);
			// screen ratio is used for determining the size and position of various images
			this.screenRatio =  (3./4.) * screenWidth / 1200.;
			AniObject boardImg = new AniObject("board", (int) Math.round(screenRatio*100), (int) Math.round(screenRatio*254), (int) Math.round(screenRatio*1000), (int) Math.round(screenRatio*646), boardList);
			images.add(boardImg);
			boardImage = boardImg;
		} catch (IOException e) {
			System.out.println("Failed to load board, trying bin folder");
		}
		
		try {
			BufferedImage grass = ImageIO.read(new File("images/grass.png"));
			List<BufferedImage> grassList = new ArrayList<BufferedImage>();
			grassList.add(grass);
			// each grass image is a separate object with its own position and size
			int size0 = 250;
			images.add(new AniObject("grass", (int) (screenRatio*190), (int) (screenRatio*170), (int) (size0*Controller.getSizeRatio(150, boardImage)), (int) (size0*Controller.getSizeRatio(150, boardImage)), grassList));
			images.add(new AniObject("grass", (int) (screenRatio*10), (int) (screenRatio*170), (int) (size0*Controller.getSizeRatio(150, boardImage)), (int) (size0*Controller.getSizeRatio(150, boardImage)), grassList));
			images.add(new AniObject("grass", (int) (screenRatio*140), (int) (screenRatio*240), (int) (size0*Controller.getSizeRatio(220, boardImage)), (int) (size0*Controller.getSizeRatio(220, boardImage)), grassList));
			images.add(new AniObject("grass", (int) (screenRatio*-15), (int) (screenRatio*270), (int) (size0*Controller.getSizeRatio(250, boardImage)), (int) (size0*Controller.getSizeRatio(250, boardImage)), grassList));
			images.add(new AniObject("grass", (int) (screenRatio*80), (int) (screenRatio*320), (int) (size0*Controller.getSizeRatio(300, boardImage)), (int) (size0*Controller.getSizeRatio(300, boardImage)), grassList));
			images.add(new AniObject("grass", (int) (screenRatio*-5), (int) (screenRatio*400), (int) (size0*Controller.getSizeRatio(380, boardImage)), (int) (size0*Controller.getSizeRatio(380, boardImage)), grassList));
			images.add(new AniObject("grass", (int) (screenRatio*80), (int) (screenRatio*420), (int) (size0*Controller.getSizeRatio(400, boardImage)), (int) (size0*Controller.getSizeRatio(400, boardImage)), grassList));
			images.add(new AniObject("grass", (int) (screenRatio*55), (int) (screenRatio*500), (int) (size0*Controller.getSizeRatio(480, boardImage)), (int) (size0*Controller.getSizeRatio(480, boardImage)), grassList));
			images.add(new AniObject("grass", (int) (screenRatio*-30), (int) (screenRatio*540), (int) (size0*Controller.getSizeRatio(520, boardImage)), (int) (size0*Controller.getSizeRatio(520, boardImage)), grassList));
			images.add(new AniObject("grass", (int) (screenRatio*10), (int) (screenRatio*620), (int) (size0*Controller.getSizeRatio(600, boardImage)), (int) (size0*Controller.getSizeRatio(600, boardImage)), grassList));
		} catch (IOException e) {
			System.out.println("Failed to load grass, trying bin folder");
		}
		
		return;
		
	}
	
	/**
	 * Returns the image associated with that stage of the tutorial
	 * @param num a positive integer from 1 to 5
	 * @return
	 */
	public BufferedImage getTutorialImage(int num) {
		if (num == 1) {
			BufferedImage beach1 = null;
			try {
				beach1 = ImageIO.read(new File("images/beach1.png"));
			} catch (IOException e) {
				System.out.println("Failed to load beach, trying bin folder");
			}
			return beach1;
		}
		else if (num == 2) {
			BufferedImage beach2 = null;
			try {
				beach2 = ImageIO.read(new File("images/beach2.png"));
			} catch (IOException e) {
				System.out.println("Failed to load beach, trying bin folder");
			}
			return beach2;
		}
		else if (num == 3) {
			BufferedImage beach3 = null;
			try {
				beach3 = ImageIO.read(new File("images/beach3.png"));
			} catch (IOException e) {
				System.out.println("Failed to load beach, trying bin folder");
			}
			return beach3;
		}
		else if (num == 4){
			BufferedImage beach4 = null;
			try {
				beach4 = ImageIO.read(new File("images/beach4.png"));
			} catch (IOException e) {
				System.out.println("Failed to load beach, trying bin folder");
			}
			return beach4;
		}
		else {
			BufferedImage beach5 = null;
			try {
				beach5 = ImageIO.read(new File("images/beach5.png"));
			} catch (IOException e) {
				System.out.println("Failed to load beach, trying bin folder");
			}
			return beach5;
		}
	}
	
	/**
	 * Paint method responsible for drawing all the AniObject's images in the List images
	 * also increments the frame value each time it is called so that moving images will animate
	 */
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

	/**
	 * Adds a new hole AniOjbect of the specified size at the specified location,
	 * and then swaps the positions of the bird and hole in the images List
	 * so that the bird is painted on top 
	 * @param xLoc the x location of the hole
	 * @param yLoc the y location of the hole
	 * @param sizeX the horizontal size of the hole
	 * @param sizeY the vertical size off the hole
	 */
	public void addHole(int xLoc, int yLoc, int sizeX, int sizeY) {
		try {
            BufferedImage hole = ImageIO.read(new File("images/hole2.png"));
			List<BufferedImage> holeList = new ArrayList<BufferedImage>();
			holeList.add(hole);
			AniObject holeObject = new AniObject("hole", xLoc, yLoc, sizeX, sizeY, holeList);
			holeObject.setVisible(true);
			images.add(holeObject);
			// the hole will be at the end of the List
			int i = images.size() - 1;
			// iterate through to find the bird
			int j = 0;
			Iterator<AniObject> holeBirdItr = images.iterator();
			while (holeBirdItr.hasNext()) {
				AniObject next = holeBirdItr.next();
				if (next.toString().equalsIgnoreCase("bird")) {
					break;
				}
				j++;
			}
			// swap their positions
			Collections.swap(images, i, j);
            return;
		} catch (IOException e1) {
			System.out.println("failed to load hole, trying bin folder");
		}
		
	}
	
	/**
	 * Adds the proper scoreImage AniOjbect of the specified size at the specified location,
	 * @param xLoc the x position of the image
	 * @param yLoc the y position of the image
	 * @param sizeX the horizontal size of the image
	 * @param sizeY the vertical size of the image
	 * @param plusOrMinus "plus" means a horseshoe crab egg image will be used, "minus" means a bottle image will be used
	 * @param scoreMult either 1 or 2, if 2 a double horseshoe crab egg image is used for double eggs power up
	 */
	public void scoreImage(int xLoc, int yLoc, int sizeX, int sizeY, String plusOrMinus, int scoreMult) {
		try {
			if (plusOrMinus.equalsIgnoreCase("plus")) {
				if (scoreMult == 1) {
					BufferedImage plusOne = ImageIO.read(new File("images/horseshoeEgg2.png"));
					List<BufferedImage> scoreList = new ArrayList<BufferedImage>();
					scoreList.add(plusOne);
					AniObject scoreObject = new AniObject("plusOne", xLoc, yLoc, sizeX, sizeY, scoreList);
					scoreObject.setVisible(true);
					images.add(scoreObject);
				}
				else if (scoreMult == 2) {
					BufferedImage plusTwo = ImageIO.read(new File("images/doubleEgg2.png"));
					List<BufferedImage> scoreList = new ArrayList<BufferedImage>();
					scoreList.add(plusTwo);
					AniObject scoreObject = new AniObject("plusTwo", xLoc, yLoc, sizeX, sizeY, scoreList);
					scoreObject.setVisible(true);
					images.add(scoreObject);
				}
				
			}
			else if(plusOrMinus.equalsIgnoreCase("minus")) {
				BufferedImage minusOne = ImageIO.read(new File("images/bottle.png"));
				List<BufferedImage> scoreList = new ArrayList<BufferedImage>();
				scoreList.add(minusOne);
				AniObject scoreObject = new AniObject("minusOne", xLoc, yLoc, sizeX, sizeY, scoreList);
				scoreObject.setVisible(true);
				images.add(scoreObject);
			}
            return;
		} catch (IOException e1) {
			System.out.println("failed to load plusImage, trying bin folder");
		}
	}

	/**
	 * Creates the Americas and bird AniObjects to be used in the first animation
	 */
	public void migrationAnimation() {
		try {
			BufferedImage US = ImageIO.read(new File("images/map.png"));
			List<BufferedImage> USList = new ArrayList<BufferedImage>();
			USList.add(US);
			AniObject USObject = new AniObject("US", 350, 100, 450, 600, USList);
			USObject.setVisible(true);
			images.add(USObject);
			
			// multiple  images are added to the birdObject's image List.  These
			// will be painted one after another based on the frame to simulate flapping
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
		return;
	}
	
	/**
	 * Recreates the Americas AniObject which was deleted and then swaps the positions of 
	 * the bird and the Americas in the images List to make sure the bird is painted on top
	 */
	public void migrationAnimation2() {
		try {			
			BufferedImage US = ImageIO.read(new File("images/map.png"));
			List<BufferedImage> USList = new ArrayList<BufferedImage>();
			USList.add(US);
			AniObject USObject = new AniObject("US", 350, 100, 450, 600, USList);
			USObject.setVisible(true);
			images.add(USObject);
			// The americas will be at the end of the List
			int i = images.size() - 1;
			// iterate trhough to find the bird
			int j = 0;
			Iterator<AniObject> holeBirdItr = images.iterator();
			while (holeBirdItr.hasNext()) {
				AniObject next = holeBirdItr.next();
				if (next.toString().equalsIgnoreCase("bird")) {
					break;
				}
				j++;
			}
			// swap their positions
			Collections.swap(images, i, j);
			
            return;
		} catch (IOException e1) {
			System.out.println("failed to load US or Bird, trying bin folder");
		}
	}
	
	/**
	 * Creates the egg and nest AniObjects which will be used in the winning animation
	 * then swaps the positions bird and the last image inserted in the images List
	 * to make sure its painted on top
	 */
	public void layEgg() {
		BufferedImage egg;
		BufferedImage nest;
		try {
			nest = ImageIO.read(new File("images/nest2.png"));
			List<BufferedImage> nestList = new ArrayList<BufferedImage>();
			nestList.add(nest);
			AniObject nestObject = new AniObject("nest", 475, 450, 300, 200, nestList);
			nestObject.setVisible(true);
			images.add(nestObject);
			
			egg = ImageIO.read(new File("images/egg2.png"));
			List<BufferedImage> eggList = new ArrayList<BufferedImage>();
			eggList.add(egg);
			AniObject eggObject = new AniObject("egg", 575, 280, 100, 100, eggList);
			eggObject.setVisible(true);
			images.add(eggObject);
			
			// the egg will be at the end of the List
			int i = images.size() - 1;
			// iterate through to find the bird
			int j = 0;
			Iterator<AniObject> eggBirdItr = images.iterator();
			while (eggBirdItr.hasNext()) {
				AniObject next = eggBirdItr.next();
				if (next.toString().equalsIgnoreCase("bird")) {
					break;
				}
				j++;
			}
			// swap their positions
			Collections.swap(images, i, j);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * Creates the deadBird AniObject for the losing animation
	 */
	public void deadBird() {
		// 15 images are used because the tick frequency does not change and the
		// flipping animation should last for many frames
		BufferedImage deadBirdImage1;
		BufferedImage deadBirdImage2;
		BufferedImage deadBirdImage3;
		BufferedImage deadBirdImage4;
		BufferedImage deadBirdImage5;
		BufferedImage deadBirdImage6;
		BufferedImage deadBirdImage7;
		BufferedImage deadBirdImage8;
		BufferedImage deadBirdImage9;
		BufferedImage deadBirdImage10;
		BufferedImage deadBirdImage11;
		BufferedImage deadBirdImage12;
		BufferedImage deadBirdImage13;
		BufferedImage deadBirdImage14;
		BufferedImage deadBirdImage15;
		try {
			// only three images are actually used, each inserted 5 times
			// so that it is painted for 5 frames
			deadBirdImage1 = ImageIO.read(new File("images/deadbird1.png"));
			deadBirdImage2 = ImageIO.read(new File("images/deadbird1.png"));
			deadBirdImage3 = ImageIO.read(new File("images/deadbird1.png"));
			deadBirdImage4 = ImageIO.read(new File("images/deadbird1.png"));
			deadBirdImage5 = ImageIO.read(new File("images/deadbird1.png"));
			deadBirdImage6 = ImageIO.read(new File("images/deadbird2.png"));
			deadBirdImage7 = ImageIO.read(new File("images/deadbird2.png"));
			deadBirdImage8 = ImageIO.read(new File("images/deadbird2.png"));
			deadBirdImage9 = ImageIO.read(new File("images/deadbird2.png"));
			deadBirdImage10 = ImageIO.read(new File("images/deadbird2.png"));
			deadBirdImage11 = ImageIO.read(new File("images/deadbird3.png"));
			deadBirdImage12 = ImageIO.read(new File("images/deadbird3.png"));
			deadBirdImage13 = ImageIO.read(new File("images/deadbird3.png"));
			deadBirdImage14 = ImageIO.read(new File("images/deadbird3.png"));
			deadBirdImage15 = ImageIO.read(new File("images/deadbird3.png"));
			List<BufferedImage> deadBirdList = new ArrayList<BufferedImage>();
			deadBirdList.add(deadBirdImage1);
			deadBirdList.add(deadBirdImage2);
			deadBirdList.add(deadBirdImage3);
			deadBirdList.add(deadBirdImage4);
			deadBirdList.add(deadBirdImage5);
			deadBirdList.add(deadBirdImage6);
			deadBirdList.add(deadBirdImage7);
			deadBirdList.add(deadBirdImage8);
			deadBirdList.add(deadBirdImage9);
			deadBirdList.add(deadBirdImage10);
			deadBirdList.add(deadBirdImage11);
			deadBirdList.add(deadBirdImage12);
			deadBirdList.add(deadBirdImage13);
			deadBirdList.add(deadBirdImage14);
			deadBirdList.add(deadBirdImage15);
			AniObject deadBirdObject = new AniObject("deadBird", 525, 450, 100, 100, deadBirdList);
			deadBirdObject.setVisible(true);
			images.add(deadBirdObject);
			deadBirdObject.setSize(2);
			deadBirdObject.setRepeat(false);
		}
		catch(IOException e) {
			e.printStackTrace();
		}
	}
	
	/*
	 * Creates the tombStone AniObject for the losing animation
	 */
	public void tombStone() {
		BufferedImage tomb;
		try {			
			tomb = ImageIO.read(new File("images/tomb.png"));
			List<BufferedImage> tombList = new ArrayList<BufferedImage>();
			tombList.add(tomb);
			AniObject tombObject = new AniObject("tomb", 525, 200, 200, 250, tombList);
			tombObject.setVisible(true);
			images.add(tombObject);
		}
		catch(IOException e){
			e.printStackTrace();
		}
	}
	
	/**
	 * Creates a new maggie AniObject of the specified size at the specified location
	 * to be used in the help finding eggs power up then swaps the position of the
	 * maggie and the bird to make sure the bird is painted on top
	 * @param xLoc the x position of the maggie
	 * @param yLoc the y position of the magggie
	 * @param sizeX the horizontal size of the maggie
	 * @param sizeY the vertical size of the maggie
	 */
	void addMaggie(int xLoc, int yLoc, int sizeX, int sizeY) {
		try {
			// multiple images added to the maggieObject's image List 
			// so that each one will be painted one after another for each frame
			// simulating hand waving
			BufferedImage maggie1 = ImageIO.read(new File("images/maggie1.png"));
			BufferedImage maggie2 = ImageIO.read(new File("images/maggie2.png"));
			BufferedImage maggie3 = ImageIO.read(new File("images/maggie3.png"));
			BufferedImage maggie4 = ImageIO.read(new File("images/maggie4.png"));
			BufferedImage maggie5 = ImageIO.read(new File("images/maggie5.png"));
			BufferedImage maggie6 = ImageIO.read(new File("images/maggie6.png"));
			List<BufferedImage> maggieList = new ArrayList<BufferedImage>();
			maggieList.add(maggie1);
			maggieList.add(maggie1);
			maggieList.add(maggie2);
			maggieList.add(maggie2);
			maggieList.add(maggie3);
			maggieList.add(maggie3);
			maggieList.add(maggie4);
			maggieList.add(maggie4);
			maggieList.add(maggie5);
			maggieList.add(maggie5);
			maggieList.add(maggie6);
			maggieList.add(maggie6);
			AniObject maggieObject = new AniObject("maggie", xLoc, yLoc, sizeX, sizeY, maggieList);
			maggieObject.setVisible(true);
			images.add(maggieObject);
			// maggie will be at the end of the List
			int i = images.size() - 1;
			// iterate through to find the bird
			int j = 0;
			Iterator<AniObject> maggieBirdItr = images.iterator();
			while (maggieBirdItr.hasNext()) {
				AniObject next = maggieBirdItr.next();
				if (next.toString().equalsIgnoreCase("bird")) {
					break;
				}
				j++;
			}
			// swap their positions
			Collections.swap(images, i, j);
            return;
		} catch (IOException e1) {
			System.out.println("failed to load maggie, trying bin folder");
		}
		
		
	}
	
	/**
	 * adds a question mark AniObject of the specified size at the specified location to
	 * be used in the hint system, then swaps the positions of the question mark and the
	 * bird in the images List to make sure the bird is painted on top.
	 * @param xLoc the x position of the question mark
	 * @param yLoc the y position of the question mark
	 * @param sizeX the horizontal size of the question mark
	 * @param sizeY the vertical size of the question mark
	 */
	void addQuestionmark(int xLoc, int yLoc, int sizeX, int sizeY) {
		try {
			BufferedImage questionmark = ImageIO.read(new File("images/questionMark2.png"));
			List<BufferedImage> qmList = new ArrayList<BufferedImage>();
			qmList.add(questionmark);
			AniObject qmObject = new AniObject("qm", xLoc, yLoc, sizeX, sizeY, qmList);
			qmObject.setVisible(true);
			images.add(qmObject);
			// the question mark will be at the end of the List
			int i = images.size() - 1;
			// iterate through to find the bird
			int j = 0;
			Iterator<AniObject> qmBirdItr = images.iterator();
			while (qmBirdItr.hasNext()) {
				AniObject next = qmBirdItr.next();
				if (next.toString().equalsIgnoreCase("bird")) {
					break;
				}
				j++;
			}
			// swap their positions
			Collections.swap(images, i, j);
            return;
		} catch (IOException e1) {
			System.out.println("failed to load questionmark, trying bin folder");
		}
		
		
	}
	
	/**
	 * Generates an image icon of the chest for the power up button
	 * @return an ImageIcon of the chest
	 */
	public ImageIcon getChestIcon(){
		ImageIcon chest = new ImageIcon("images/chest.png");
		Image chestImage = chest.getImage();
		Image resizedChest = chestImage.getScaledInstance( 200, 200,  java.awt.Image.SCALE_SMOOTH ) ;  
		return new ImageIcon(resizedChest);
	}
	
	/**
	 * Generates a transparent image to force the layout manager to paint
	 * the chest button the correct size even when it is invisible and deactivated
	 * @return an ImageIcon of a transparent square
	 */
	public ImageIcon getTransChestIcon(){
		ImageIcon chest = new ImageIcon("images/chestTrans.png");
		Image chestImage = chest.getImage();
		Image resizedChest = chestImage.getScaledInstance( 200, 200,  java.awt.Image.SCALE_SMOOTH ) ;  
		return new ImageIcon(resizedChest);
	}
	
	/**
	 * Generates ImageIcon for the start screen and difficulty screen backgrounds
	 * @return the background image
	 */
	public ImageIcon getBackgroundImage() {
		ImageIcon back = new ImageIcon("images/homeBackground.png");
		Image backImage = back.getImage();
		Image resizedBack = backImage.getScaledInstance( screenWidth, screenHeight,  java.awt.Image.SCALE_SMOOTH ) ; 
		ImageIcon newIcon = new ImageIcon(resizedBack);
		return newIcon;
	}
	
	/**
	 * Generates the ImageIcon for the instructions
	 * @return the instructions image
	 */
	public ImageIcon getInstructionsIcon() {
		ImageIcon i = new ImageIcon("images/instructions.png");
		Image image = i.getImage();
		Image newimg = image.getScaledInstance(screenWidth/2, screenHeight/2, Image.SCALE_SMOOTH);
		return new ImageIcon(newimg);
	}
	
	/**
	 *Returns the current painting frame
	 * @return and int corresponding to the current paint frame
	 */
	public int getFrame() {
		return this.frame;
	}

}