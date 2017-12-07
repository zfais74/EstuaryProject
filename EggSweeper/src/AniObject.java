import java.awt.image.BufferedImage;
import java.io.Serializable;
import java.util.List;

/**
 * @author Will Ransom
 *
 */
public class AniObject implements Serializable {
	
	private String name;
	private int origX;
	private int origY;
	private int X;
	private int Y;
	private int origXSize;
	private int origYSize;
	private int xSize;
	private int ySize;
	private int scoreSize = 0;
	private transient List<BufferedImage> images;
	private boolean visible;
	boolean repeat = true;
	private int firstFrame;
	
	/**
	 * AniObject constructor to create a new AniObject object
	 * 
	 * @param objName
	 * @param xLoc
	 * @param yLoc
	 * @param sizeX
	 * @param sizeY
	 * @param objImage
	 */
	AniObject(String objName, int xLoc, int yLoc, int sizeX, int sizeY, List<BufferedImage> objImage){
		name = objName;
		origX = xLoc;
		origY = yLoc;
		X = xLoc;
		Y = yLoc;
		origXSize = sizeX;
		origYSize = sizeY;
		xSize = sizeX;
		ySize = sizeY;
		images = objImage;
		visible = false;
	}
	
	/**
	 * Set repeat to repeatorNot
	 * 
	 * @param repeatorNot
	 */
	public void setRepeat(boolean repeatorNot) {
		this.repeat = repeatorNot;
	}
	
	public void setImage(BufferedImage image) {
		this.images.remove(images.get(0));
		images.add(image);
	}
	
	public String toString() {
		return name;
	}
	
	/**
	 * Set name to newName
	 * 
	 * @param newName
	 */
	public void setName(String newName) {
		this.name = newName;
	}
	
	/**
	 * Set visible to isVisible
	 * 
	 * @param isVisible
	 */
	public void setVisible(boolean isVisible) {
		visible = isVisible;
	}
	
	/**
	 * Return the AniObject visibility
	 * 
	 * @return visible
	 */
	public boolean getVisible() {
		return visible;
	}
	
	/**
	 * Return the size of the Aniobject in the x direction
	 * 
	 * @return xSize
	 */
	public int getXSize() {
		return xSize;
	}
	
	/**
	 * Return the size of the AniOject in the y direction
	 * 
	 * @return ySize
	 */
	public int getYSize() {
		return ySize;
	}
	
	/**
	 * Set the size of the AniOject in the x and y direction by adding the scoreSize and origXSize or origYsize then multiplying by newRatio
	 * 
	 * @param newRatio
	 */
	public void setSize(double newRatio) {
		xSize = (int) ((scoreSize + origXSize) * newRatio);
		ySize = (int) ((scoreSize + origYSize) * newRatio);
	}
	
	/**
	 * Return the original size of the AniObject in the x direction
	 * 
	 * @return origXSize
	 */
	public int getOrigXSize() {
		return origXSize;
	}
	
	/**
	 * Return the original size of the AniObject in the y direction
	 * 
	 * @return origYSize
	 */
	public int getOrigYSize() {
		return origYSize;
	}
	
	/**
	 * Return the position of the AniObject in the x direction
	 * 
	 * @return X
	 */
	public int getX() {
		return X;
	}
	
	/**
	 * Set the x position of the AniObject 
	 * 
	 * @param x
	 */
	public void setX(int x) {
		X = x;
	}
	
	/**
	 * Return the original size of the AniObject in the y direction
	 * 
	 * @return Y
	 */
	public int getY() {
		return Y;
	}
	
	/**
	 * Set the x position of the AniObject
	 * 
	 * @param y
	 */
	public void setY(int y) {
		Y = y;
	}
	
	/**
	 * Return the original x position of the AniObject
	 * 
	 * @return origX
	 */
	public int getOrigX() {
		return origX;
	}
	
	/**
	 * Return the original y position of the AniObject
	 * 
	 * @return origY
	 */
	public int getOrigY() {
		return origY;
	}
	
	/**
	 * Increase the scoreSize by the rate
	 * 
	 * @param rate
	 */
	public void incScoreSize(int rate) {
		scoreSize +=rate;
	}
	
	/**
	 * Decrease the scoreSixe by 10
	 */
	public void decScoreSize() {
		scoreSize-=10;
	}
	
	/**
	 * Set the scoreSize to newSize
	 * 
	 * @param newSize
	 */
	public void setScoreSize(int newSize) {
		scoreSize = newSize;
	}
	
	/**
	 * Return firstFrame
	 * 
	 * @return this.firstFrame
	 */
	public int getFirstFrame() {
		return this.firstFrame;
	}
	
	/**
	 * This method check if the images repeat or not. If so return images with the respected image number. If not then return images with a different frame or different size
	 * 
	 * @param frame
	 * @return image
	 */
	public BufferedImage getImage(int frame) {
		if (this.repeat) {
			int imageNum = frame % images.size();
			return images.get(imageNum);
		}
		else {
			if (this.firstFrame == 0) {
				this.firstFrame = frame;
			}
			if (frame - firstFrame < images.size()) {
				return images.get(frame - firstFrame);
			}
			else {
				return images.get(images.size() - 1);
			}
		}
	}
}
