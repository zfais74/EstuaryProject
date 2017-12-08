package TimeManagement;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.Serializable;

import javax.swing.Timer;

/**
 * This class is a swing timer used to time the game for 40 seconds
 * @author Zeke Faison
 *
 */
public class GameBoardTimer implements ActionListener, Serializable {

	private int timeRemaining;
	private Timer timer;
	private boolean timesUp = false;
	public boolean timerStarted;
	
	/**
	 * Constructor sets the timer for 40 seconds
	 */
	public GameBoardTimer() {
		this.timeRemaining = 10000;
		this.setTimer();
	}

	/**
	 * Constructor used for testing without full 40 second delay
	 * @param time the test time
	 */
	public GameBoardTimer(int time) {
		this.timeRemaining = time;
		this.setTimer();
	}
	
	/**
	 * Set the timer tick
	 */
	public void setTimer() {
		this.timer = new Timer(1000, this);
	}
	
	/**
	 * Reduce the time left
	 */
	private void decTime() {
		timeRemaining-=1000;
	}
	
	/**
	 * Get the time remaining
	 * @return time remaining
	 */
	public int getTimeRemaining () {
		return this.timeRemaining;
	}
	
	/**
	 * Get the Timer object, used to start and stop the timer
	 * @return Timer object
	 */
	public Timer getTimer() {
		return this.timer;
	}
	
	/**
	 * Set whether or not time is up
	 * @param isTimeUp whether or not time is up
	 */
	private void setTimesUp(boolean isTimeUp) {
		this.timesUp = isTimeUp;
	}
	
	/**
	 * Get whether or not time is up
	 * @return whether or not time is up
	 */
	public boolean isTimesUp() {
		return this.timesUp;
	}
	
	/**
	 * The tick of the swing timer, reduces time each time the swing timer produces an ActionEvent
	 */
	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		this.decTime();
		if(this.timeRemaining <= 0) {
			setTimesUp(true);
		}
	}

}
