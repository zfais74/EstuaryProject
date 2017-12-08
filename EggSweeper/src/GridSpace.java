import enums.Item;

import java.io.Serializable;

// The Model

/**
 * This class provides a convenient object which can fill the game board.
 * It contains an Item and a few methods to maniuplate them.
 * @author Will Ransom
 *
 */
public class GridSpace implements Serializable {
	
	// the contents of a GridSpace
	private Item contents;
	
	// constructor
	/**
	 * A GridSpace constructor to create a new GridSpace object
	 * 
	 * @param item the Item in the GridSpace
	 */
	GridSpace(Item item) {
		contents = item;
	}
	
	/**
	 * Returns an Item object that contain the content of the given square(GridSpace).
	 * @return the content at a specified GridSpace
	 */
	public Item getItem() {
		return this.contents;
	}
	
	/**
	 * Set the contents of the GridSpace to a given item
	 * @param item the Item to be placed in the GridSpace
	 */
	public void setItem(Item item) {
		this.contents = item;
	}
	
}