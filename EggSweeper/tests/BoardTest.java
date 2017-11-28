import static org.junit.Assert.*;

import java.io.FileNotFoundException;
import java.util.List;

import org.junit.Before;
import org.junit.Test;

import enums.Difficulty;
import enums.Direction;
import enums.Item;

public class BoardTest {
	private Board board;
	
	@Before
	public void setup() {
		board = new Board(Difficulty.EASY);
	}

	@Test
	public void boardMedConstructor() {
		board = new Board(Difficulty.MEDIUM);
	}
	
	@Test
	public void boardHardConstructor() {
		board = new Board(Difficulty.HARD);
	}

	@Test
	public void testGetSpace() {
		GridSpace space = new GridSpace(Item.EMPTY);
		board.setSpace(0, 0, space);
		GridSpace result = board.getSpace(0, 0);
		assertEquals(result, space);
	}

	@Test
	public void testCountAdjacentItems() {
		fail("Not yet implemented");
	}

	@Test
	public void testConvertYDimNorth() {
		int result = board.convertYDim(Direction.NORTH);
		System.out.println("y" + result);
		assertEquals(result, -1);
	}
	
	@Test
	public void testConvertYDimSouth() {
		int result = board.convertYDim(Direction.SOUTHEAST);
		assertEquals(result, 1);
	}
	
	@Test
	public void testConvertYDimNeither() {
		int result = board.convertYDim(Direction.EAST);
		assertEquals(result, 0);
	}

	@Test
	public void testConvertXDimEast() {
		int result = board.convertXDim(Direction.NORTHEAST);
		System.out.println("y" + result);
		assertEquals(result, 1);
	}
	
	@Test
	public void testConvertXDimWest() {
		int result = board.convertXDim(Direction.SOUTHWEST);
		assertEquals(result, -1);
	}
	
	@Test
	public void testConvertXDimNeither() {
		int result = board.convertXDim(Direction.NORTH);
		assertEquals(result, 0);
	}

	@Test
	public void testGetAdjacentItemGridDirectionsIntInt() {
		fail("Not yet implemented");
	}

	@Test
	public void testGetAdjacentItemGridDirectionsIntIntItem() {
		fail("Not yet implemented");
	}

	@Test
	public void testCheckAnswerCorrect() {
		String answer = "Harvey";
		board.setCorrectAnswer(answer);
		boolean result = board.checkAnswer(answer);
		assertTrue(result);
	}
	
	@Test
	public void testCheckAnswerIncorrect() {
		String answer = "Harvey";
		String wrong = "Student";
		board.setCorrectAnswer(answer);
		boolean result = board.checkAnswer(wrong);
		assertFalse(result);
	}

	@Test
	public void testGetAnswer() {
		String answer = "Bird";
		board.setCorrectAnswer(answer);
		String result = board.getAnswer();
		assertEquals(answer, result);
	}
	
	@Test
	public void getPowerUpQuestionTest() throws FileNotFoundException {
		board.getPowerupQuestion();
		List<String> ans = board.getPossibleAnswers();
		assertTrue(ans.size() > 0);
		
	}

}
