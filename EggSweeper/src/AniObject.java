import java.awt.image.BufferedImage;
import java.io.Serializable;
import java.util.List;

/**
 * This class provides a convenient object which can hold images, and data corresponding
 * to the size and position of these images.
 * @author Will Ransom
 *
 */
public class AniObject implements Serializable {
	
	// used to identify the object
	private String name;
	// the positions of the object when it was created
	private int origX;
	private int origY;
	// the current position of the object
	private int X;
	private int Y;
	// the size of the object's image when it was created
	private int origXSize;
	private int origYSize;
	// the current size of the object's image
	private int xSize;
	private int ySize;
	// for resizing the bird as score changes
	private int scoreSize = 0;
	// if the object's images should be painted or not
	private boolean visible;
	// only used to prevent the dead bird from flipping over more than once
	boolean repeat = true;
	// the frame at which the object's images were first painted only used if repeat is set to false
	private int firstFrame = 0;
	
	// the list of images to be painted successively with each frame simulating motion
	private transient List<BufferedImage> images;
	
	
	/**
	 * Constructor to create a new AniObject object
	 * 
	 * @param objName the name used to identify the AniObject
	 * @param xLoc the x location of the AniObject
	 * @param yLoc the y location of the AniObject
	 * @param sizeX the horizontal size at which the AniObject's images should be painted
	 * @param sizeY the vertical size at which the AniObject's images should be painted
	 * @param objImage the image list for the AniObject
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
	 * @param repeatorNot true for repeat
	 */
	public void setRepeat(boolean repeatorNot) {
		this.repeat = repeatorNot;
	}
	
	/**
	 * Used to change the image in a single image AniObject
	 * @param image the new image to be painted
	 */
	public void setImage(BufferedImage image) {
		this.images.remove(images.get(0));
		images.add(image);
	}
	
	/**
	 * Used to identify AniObjects
	 */
	public String toString() {
		return name;
	}
	
	/**
	 * Set name to newName
	 * 
	 * @param newName the new name of the AniObject
	 */
	public void setName(String newName) {
		this.name = newName;
	}
	
	/**
	 * Set AniObject's visibility
	 * 
	 * @param isVisible whether the AniObject's images should be painted, true for painting
	 */
	public void setVisible(boolean isVisible) {
		visible = isVisible;
	}
	
	/**
	 * Return the AniObject visibility
	 * 
	 * @return visible whether the AniObject's images should be painted, true for painting
	 */
	public boolean getVisible() {
		return visible;
	}
	
	/**
	 * Return the size of the AniObject's images in the x direction
	 * 
	 * @return xSize the AniObject's horizontal image size
	 */
	public int getXSize() {
		return xSize;
	}
	
	/**
	 * Return the size of the AniOject's images in the y direction
	 * 
	 * @return ySize the AniObject's vertical size
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
	 * Return the original size of the AniObject's images in the x direction
	 * 
	 * @return origXSize the AniObject's horizontal image size when it was created
	 */
	public int getOrigXSize() {
		return origXSize;
	}
	
	/**
	 * Return the original size of the AniObject's images in the y direction
	 * 
	 * @return origYSize the AniObject's vertical image size when it was created
	 */
	public int getOrigYSize() {
		return origYSize;
	}
	
	/**
	 * Return the position of the AniObject in the x direction
	 * 
	 * @return X the x position
	 */
	public int getX() {
		return X;
	}
	
	/**
	 * Set the x position of the AniObject 
	 * 
	 * @param x the new x position
	 */
	public void setX(int x) {
		X = x;
	}
	
	/**
	 * Return the position of the AniObject in the y direction
	 * 
	 * @return Y the y position
	 */
	public int getY() {
		return Y;
	}
	
	/**
	 * Set the y position of the AniObject
	 * 
	 * @param y the new y position
	 */
	public void setY(int y) {
		Y = y;
	}
	
	/**
	 * Return the original x position of the AniObject when it was created
	 * 
	 * @return origX the original x position
	 */
	public int getOrigX() {
		return origX;
	}
	
	/**
	 * Return the original y position of the AniObject when it was created
	 * 
	 * @return origY the original y position
	 */
	public int getOrigY() {
		return origY;
	}
	
	/**
	 * Increase the scoreSize by the specified rate
	 * 
	 * @param rate how much to increment the score size
	 */
	public void incScoreSize(int rate) {
		scoreSize +=rate;
	}
	
	/**
	 * Decrease the scoreSize by 10
	 */
	public void decScoreSize() {
		scoreSize = scoreSize - 10;
	}
	
	/**
	 * Set the scoreSize to newSize
	 * 
	 * @param newSize the new scoreSize
	 */
	public void setScoreSize(int newSize) {
		scoreSize = newSize;
	}
	
	/**
	 * Returns the frame at which the AniObject was first painted, used in death animation to stop
	 *  bird from repeatedly flipping
	 * 
	 * @return firstFrame the frame value when the AniObject was first painted
	 */
	public int getFirstFrame() {
		return firstFrame;
	}
	
	/**
	 * This method checks if the images repeat or not. If so it returns  an image from the images list
	 * based the modulus of the frame value by the size of the list. If not then return 
	 * images based on the number of frames since the firstFrame value.
	 * 
	 * @param frame the current frame
	 * @return image the image to be painted
	 */
	public BufferedImage getImage(int frame) {
		if (this.repeat) {
			// mod the frame # by the number of images in the AniObject's images list
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
