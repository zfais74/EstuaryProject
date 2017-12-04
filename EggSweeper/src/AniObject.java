import java.awt.image.BufferedImage;
import java.io.Serializable;
import java.util.List;

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
	
	public void setRepeat(boolean repeatorNot) {
		this.repeat = repeatorNot;
	}
	
	public String toString() {
		return name;
	}
	
	public void setName(String newName) {
		this.name = newName;
	}
	
	public void setVisible(boolean isVisible) {
		visible = isVisible;
	}
	
	public boolean getVisible() {
		return visible;
	}
	
	public int getXSize() {
		return xSize;
	}
	
	public int getYSize() {
		return ySize;
	}
	
	public void setSize(double newRatio) {
		xSize = (int) ((scoreSize + origXSize) * newRatio);
		ySize = (int) ((scoreSize + origYSize) * newRatio);
	}
	
	public int getOrigXSize() {
		return origXSize;
	}
	
	public int getOrigYSize() {
		return origYSize;
	}
	
	public int getX() {
		return X;
	}
	
	public void setX(int x) {
		X = x;
	}
	
	public int getY() {
		return Y;
	}
	
	public void setY(int y) {
		Y = y;
	}
	
	public int getOrigX() {
		return origX;
	}
	
	public int getOrigY() {
		return origY;
	}
	
	public void incScoreSize(int rate) {
		scoreSize +=rate;
	}
	
	public void decScoreSize() {
		scoreSize-=10;
	}
	
	public void setScoreSize(int newSize) {
		scoreSize = newSize;
	}
	
	public int getFirstFrame() {
		return this.firstFrame;
	}
	
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
