import static org.junit.Assert.*;

import org.junit.Test;

import enums.Difficulty;
import enums.Item;
import enums.PowerUps;

public class PlayerTest {
	
	private Player player = new Player();

	@Test
	public void testIncScore() {
		int scoreBeforeInc = player.getScore();
		player.incScore();
		int scoreAfterInc = player.getScore();
		assertTrue("the older score should be less than the newer score", scoreBeforeInc < scoreAfterInc);
	}

	@Test
	public void testDecScore() {
		int scoreBeforeDec = player.getScore();
		player.decScore();
		int scoreAfterDec = player.getScore();
		assertTrue("the older score should be greater than the newer score", scoreBeforeDec > scoreAfterDec);
	}
	
	@Test
	public void testPlayerHasPowerUp(){
		player.setPowerupStatus(true);
		boolean result = player.hasPowerUp();
		assertTrue("result should be true", result);
	}
	
	@Test
	public void testPlayerDoesNotHavePowerUp (){
		boolean result = player.hasPowerUp();
		assertFalse("result should be false", result);
	}
	
	
	@Test
	public void testPlayerHasNullPowerUp(){
		PowerUps powerUp = player.getCurrentPowerUp();
		assertEquals("The powerUp variable should be null",powerUp, null);
	}
	
	@Test
	public void testPlayerTotalCorrectAnswers(){
		int result = player.gettotalCorrectAnswers();
		assertEquals("result should be 0", result, 0);
	}
	
	@Test
	public void testIncreaseTotalCorrectAnswers(){
		player.incTotalCorrectAnswers();
		int result = player.gettotalCorrectAnswers();
		assertEquals("result should be 1", result, 1);
	}
	
	@Test
	public void playerCheckSpaceEmpty(){
		GridSpace space1 = new GridSpace(Item.EMPTY);
		Board tempBoard = new Board(Difficulty.EASY);
		tempBoard.setSpace(0, 0, space1);
		Item result = player.checkSpace(0, 0, tempBoard);
		assertEquals("result should be EMPTY", result, Item.EMPTY);
	}
	
	@Test
	public void playerCheckSpaceEgg(){
		GridSpace space1 = new GridSpace(Item.EGG);
		Board tempBoard = new Board(Difficulty.EASY);
		tempBoard.setSpace(0, 0, space1);
		Item result = player.checkSpace(0, 0, tempBoard);
		assertEquals("result should be EGG", result, Item.EGG);
	}
	
	@Test
	public void playerCheckSpaceTrash(){
		GridSpace space1 = new GridSpace(Item.TRASH);
		Board tempBoard = new Board(Difficulty.EASY);
		tempBoard.setSpace(0, 0, space1);
		Item result = player.checkSpace(0, 0, tempBoard);
		assertEquals("result should be TRASH", result, Item.TRASH);
	}
	
	@Test
	public void playerCheckSpaceAlreadyChecked(){
		GridSpace space1 = new GridSpace(Item.ALREADYCHECKED);
		Board tempBoard = new Board(Difficulty.EASY);
		tempBoard.setSpace(0, 0, space1);
		Item result = player.checkSpace(0, 0, tempBoard);
		assertEquals("result should be ALREADYCHECKED", result, Item.ALREADYCHECKED);
	}
	
	@Test
	public void playerLosesBonusPowerUp(){
		player.setCurrentPowerUp(PowerUps.BONUS);
		player.setPowerupStatus(false);
	}
	

}
