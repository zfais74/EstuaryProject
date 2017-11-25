
public class NumberManipulation {
	public static int generateNum(int range) {
		return (int)(Math.random() * ((range - 1) + 1)) + 1;
	}
}
