/**
 * A utility class used to manipulate numbers
 * @author Zeke Faison
 *
 */
public class NumberManipulation {
	/**
	 * Generates a random integer ranging from 1 to a specific number
	 * @param endRange The end of the range the developer chooses to use
	 * @return A random integer
	 */
	public static int generateNum(int endRange) {
		return (int)(Math.random() * ((endRange - 1) + 1)) + 1;
	}
}
