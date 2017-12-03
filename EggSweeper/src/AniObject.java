import java.awt.image.BufferedImage;
import java.io.Serializable;
import java.util.List;

public class AniObject implements Serializable {
	
	private String name;
	private int X;
	private int Y;
	private int origXSize;
	private int origYSize;
	private int xSize;
	private int ySize;
	private int scoreSize = 0;
	private transient List<BufferedImage> images;
	private boolean visible;
	
	AniObject(String objName, int xLoc, int yLoc, int sizeX, int sizeY, List<BufferedImage> objImage){
		name = objName;
		X = xLoc;
		Y = yLoc;
		origXSize = sizeX;
		origYSize = sizeY;
		xSize = sizeX;
		ySize = sizeY;
		images = objImage;
		visible = false;
	}
	
	public String toString() {
		return name;
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
	
	public void incScoreSize() {
		scoreSize +=10;
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
	
	public int getY() {
		return Y;
	}
	
	public void setY(int y) {
		Y = y;
	}
	
	public BufferedImage getImage(int frame) {
		int imageNum = frame % images.size();
		return images.get(imageNum);
	}
}
