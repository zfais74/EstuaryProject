package enums;
/**
 * Items are used for searching gridspaces
 * @author zeke
 *
 */
public enum Item {
	/**
	 * Empty indicates a grid space is empty
	 */
	EMPTY, 
	/**
	 * Egg indicates a grid space is holding an egg
	 */
	EGG, 
	/**
	 * Trash indicates a grid space is holding trash
	 */
	//TRASH,
	TWIG,
	BOTTLE,
	PESTICIDE,
	/**
	 * Already checked indicates the player has already checked the grid space
	 */
	ALREADYCHECKED;
}
