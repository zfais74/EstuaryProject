import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.MouseInfo;
import java.awt.Point;
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
import javax.swing.JPanel;

// The View

public class Animation extends JPanel implements Serializable {

	private transient List<AniObject> images;
	private AniObject boardImage;

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
	Animation() {
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
			AniObject boardImg = new AniObject("board", 100, 254, 1000, 646, boardList);
			images.add(boardImg);
			boardImage = boardImg;
		} catch (IOException e) {
			System.out.println("Failed to load board, trying bin folder");
		}

		try {
			BufferedImage board = ImageIO.read(new File("board.png"));
			List<BufferedImage> boardList = new ArrayList<BufferedImage>();
			boardList.add(board);
			AniObject boardImg = new AniObject("board", 100, 254, 1000, 646, boardList);
			images.add(boardImg);
			boardImage = boardImg;
		} catch (IOException e) {
			System.out.println("Failed to load board");
		}

		try {
			BufferedImage grass = ImageIO.read(new File("images/grass.png"));
			List<BufferedImage> grassList = new ArrayList<BufferedImage>();
			grassList.add(grass);
			int size0 = 250;
			images.add(new AniObject("grass1", 180, 150, (int) (size0 * Controller.getSizeRatio(150, boardImage)), (int) (size0 * Controller.getSizeRatio(150, boardImage)), grassList));
			images.add(new AniObject("grass2", 0, 150, (int) (size0 * Controller.getSizeRatio(150, boardImage)), (int) (size0 * Controller.getSizeRatio(150, boardImage)), grassList));
			images.add(new AniObject("grass3", 130, 220, (int) (size0 * Controller.getSizeRatio(220, boardImage)), (int) (size0 * Controller.getSizeRatio(220, boardImage)), grassList));
			images.add(new AniObject("grass4", -25, 250, (int) (size0 * Controller.getSizeRatio(250, boardImage)), (int) (size0 * Controller.getSizeRatio(250, boardImage)), grassList));
			images.add(new AniObject("grass5", 70, 300, (int) (size0 * Controller.getSizeRatio(300, boardImage)), (int) (size0 * Controller.getSizeRatio(300, boardImage)), grassList));
			images.add(new AniObject("grass6", -15, 380, (int) (size0 * Controller.getSizeRatio(380, boardImage)), (int) (size0 * Controller.getSizeRatio(380, boardImage)), grassList));
			images.add(new AniObject("grass7", 70, 400, (int) (size0 * Controller.getSizeRatio(400, boardImage)), (int) (size0 * Controller.getSizeRatio(400, boardImage)), grassList));
			images.add(new AniObject("grass8", 45, 480, (int) (size0 * Controller.getSizeRatio(480, boardImage)), (int) (size0 * Controller.getSizeRatio(480, boardImage)), grassList));
			images.add(new AniObject("grass9", -40, 520, (int) (size0 * Controller.getSizeRatio(520, boardImage)), (int) (size0 * Controller.getSizeRatio(520, boardImage)), grassList));
			images.add(new AniObject("grass10", 0, 600, (int) (size0 * Controller.getSizeRatio(600, boardImage)), (int) (size0 * Controller.getSizeRatio(600, boardImage)), grassList));
		} catch (IOException e) {
			System.out.println("Failed to load grass, trying bin folder");
		}

		try {
			BufferedImage grass = ImageIO.read(new File("grass.png"));
			List<BufferedImage> grassList = new ArrayList<BufferedImage>();
			grassList.add(grass);
			int size0 = 250;
			images.add(new AniObject("grass1", 180, 150, (int) (size0 * Controller.getSizeRatio(150, boardImage)), (int) (size0 * Controller.getSizeRatio(150, boardImage)), grassList));
			images.add(new AniObject("grass2", 0, 150, (int) (size0 * Controller.getSizeRatio(150, boardImage)), (int) (size0 * Controller.getSizeRatio(150, boardImage)), grassList));
			images.add(new AniObject("grass3", 130, 220, (int) (size0 * Controller.getSizeRatio(220, boardImage)), (int) (size0 * Controller.getSizeRatio(220, boardImage)), grassList));
			images.add(new AniObject("grass4", -25, 250, (int) (size0 * Controller.getSizeRatio(250, boardImage)), (int) (size0 * Controller.getSizeRatio(250, boardImage)), grassList));
			images.add(new AniObject("grass5", 70, 300, (int) (size0 * Controller.getSizeRatio(300, boardImage)), (int) (size0 * Controller.getSizeRatio(300, boardImage)), grassList));
			images.add(new AniObject("grass6", -15, 380, (int) (size0 * Controller.getSizeRatio(380, boardImage)), (int) (size0 * Controller.getSizeRatio(380, boardImage)), grassList));
			images.add(new AniObject("grass7", 70, 400, (int) (size0 * Controller.getSizeRatio(400, boardImage)), (int) (size0 * Controller.getSizeRatio(400, boardImage)), grassList));
			images.add(new AniObject("grass8", 45, 480, (int) (size0 * Controller.getSizeRatio(480, boardImage)), (int) (size0 * Controller.getSizeRatio(480, boardImage)), grassList));
			images.add(new AniObject("grass9", -40, 520, (int) (size0 * Controller.getSizeRatio(520, boardImage)), (int) (size0 * Controller.getSizeRatio(520, boardImage)), grassList));
			images.add(new AniObject("grass10", 0, 600, (int) (size0 * Controller.getSizeRatio(600, boardImage)), (int) (size0 * Controller.getSizeRatio(600, boardImage)), grassList));
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
			int i = images.size() - 1;
			int j = 0;
			Iterator<AniObject> holeBirdItr = images.iterator();
			while (holeBirdItr.hasNext()) {
				AniObject next = holeBirdItr.next();
				if (next.toString().compareToIgnoreCase("bird") == 0) {
					break;
				}
				j++;
			}
			Collections.swap(images, i, j);
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
			int i = images.size() - 1;
			int j = 0;
			Iterator<AniObject> holeBirdItr = images.iterator();
			while (holeBirdItr.hasNext()) {
				AniObject next = holeBirdItr.next();
				if (next.toString().compareToIgnoreCase("bird") == 0) {
					break;
				}
				j++;
			}
			Collections.swap(images, i, j);
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
			BufferedImage US = ImageIO.read(new File("images/map.png"));
			List<BufferedImage> USList = new ArrayList<BufferedImage>();
			USList.add(US);
			AniObject USObject = new AniObject("US", 350, 100, 450, 600, USList);
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
			BufferedImage US = ImageIO.read(new File("map.png"));
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

	void addMaggie(int xLoc, int yLoc, int sizeX, int sizeY) {
		try {
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
			int i = images.size() - 1;
			int j = 0;
			Iterator<AniObject> maggieBirdItr = images.iterator();
			while (maggieBirdItr.hasNext()) {
				AniObject next = maggieBirdItr.next();
				if (next.toString().compareToIgnoreCase("bird") == 0) {
					break;
				}
				j++;
			}
			Collections.swap(images, i, j);
			return;
		} catch (IOException e1) {
			System.out.println("failed to load maggie, trying bin folder");
		}


	}

	void addQuestionmark(int xLoc, int yLoc, int sizeX, int sizeY) {
		try {
			BufferedImage questionmark = ImageIO.read(new File("images/questionMark2.png"));
			List<BufferedImage> qmList = new ArrayList<BufferedImage>();
			qmList.add(questionmark);
			AniObject qmObject = new AniObject("qm", xLoc, yLoc, sizeX, sizeY, qmList);
			qmObject.setVisible(true);
			images.add(qmObject);
			int i = images.size() - 1;
			int j = 0;
			Iterator<AniObject> qmBirdItr = images.iterator();
			while (qmBirdItr.hasNext()) {
				AniObject next = qmBirdItr.next();
				if (next.toString().compareToIgnoreCase("bird") == 0) {
					break;
				}
				j++;
			}
			Collections.swap(images, i, j);
			return;
		} catch (IOException e1) {
			System.out.println("failed to load questionmark, trying bin folder");
		}


	}

	public ImageIcon getChestIcon() {
		ImageIcon chest = new ImageIcon("images/chest.png");
		Image chestImage = chest.getImage();
		Image resizedChest = chestImage.getScaledInstance(200, 200, java.awt.Image.SCALE_SMOOTH);
		return new ImageIcon(resizedChest);
	}


	public void addHomeBackground(){
		try {
			BufferedImage HB = ImageIO.read(new File("images/homeBackground.png"));
			List<BufferedImage> HBList = new ArrayList<BufferedImage>();
			HBList.add(HB);
			AniObject HBObject = new AniObject("HB", 100, 100, 450, 600, HBList);
			HBObject.setVisible(true);
			images.add(HBObject);
			return;
		} catch (IOException e1) {
			System.out.println("failed to load HomeBackground, trying bin folder");
		}
		try {
			BufferedImage HB = ImageIO.read(new File("homeBackground.png"));
			List<BufferedImage> HBList = new ArrayList<BufferedImage>();
			HBList.add(HB);
			AniObject HBObject = new AniObject("HB", 100, 100, 450, 600, HBList);
			HBObject.setVisible(true);
			images.add(HBObject);
		} catch (IOException e1) {
			System.out.println("failed to load HomeBackground");
		}

	}
}