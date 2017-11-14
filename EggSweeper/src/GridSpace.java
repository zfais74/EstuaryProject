import enums.Item;

// The Model

/**
 * @author Will Ransom
 *
 */
public class GridSpace{
	
	// GridSpace data
	private Item contents;
	
	// whether the GridSpace has been checked yet, true if it has not been checked
	private boolean isCovered = true;
	
	// constructor
	GridSpace(Item item) {
		contents = item;
	}
	
	/**
	 * Returns an Item object that contain the content of the given square(gridspace).
	 * @return the content at a specified gridspace
	 */
	public Item getItem() {
		return this.contents;
	}
	
	/**
	 * Return the boolean status of the gridspace. For if the gridspace is covered or not
	 * @return the status of a specified gridspace
	 */
	public boolean getIsCovered() {
		return isCovered;
	}
	
	/**
	 * Set the boolean value isCovered to bool.
	 * @param bool a boolean for the specified gridspace
	 */
	public void setIsCovered(boolean bool) {
		isCovered = bool;
	}
	
}