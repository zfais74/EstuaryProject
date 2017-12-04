package TimeManagement;

import static org.junit.Assert.*;

import javax.swing.Timer;

import org.junit.Before;
import org.junit.Test;

public class PowerUpTimerTest {
	private PowerUpTimer timer;
	
	@Before
	public void setup() {
		timer = new PowerUpTimer();
	}
	
	@Test
	public void testGetTimeRemaining() {
		int expectedTimeRemaining = 5;
		int result = timer.getTimeRemaining();
		assertEquals("result should be equal to expected", expectedTimeRemaining, result);
	}

	@Test
	public void testGetTimer() {
		int expectedTimerDelay = 1000;
		Timer retunedTimer = timer.getTimer();
		int timerDelay = retunedTimer.getDelay();
		assertEquals("timerDelay should be equal to the expectedTimerDelay", expectedTimerDelay, timerDelay);
	}

	@Test
	public void testIsTimesUpStillHaveTime() {
		boolean result = timer.isTimesUp();
		assertFalse("the result should be false since the time remaining property is not zero", result);
	}
	
	@Test
	public void testIsTimesUp() {
		timer = new PowerUpTimer(1);
		timer.actionPerformed(null);
		boolean result = timer.isTimesUp();
		assertTrue("the result should be true since the time remaining property is zero", result);
	}

}
