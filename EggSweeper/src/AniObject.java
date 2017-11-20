import java.awt.image.BufferedImage;
import java.util.List;

public class AniObject {
	
	private String name;
	private int X;
	private int Y;
	private int xSize;
	private int ySize;
	private List<BufferedImage> images;
	private boolean visible;
	
	AniObject(String objName, int xLoc, int yLoc, int sizeX, int sizeY, List<BufferedImage> objImage){
		name = objName;
		X = xLoc;
		Y = yLoc;
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
	
	public BufferedImage getImage(int frame) {
		int imageNum = frame % images.size();
		return images.get(imageNum);
	}
}
