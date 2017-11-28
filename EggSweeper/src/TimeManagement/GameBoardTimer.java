package TimeManagement;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.Serializable;

import javax.swing.Timer;

public class GameBoardTimer implements ActionListener, Serializable {

	private int timeRemaining;
	private Timer timer;
	private boolean timesUp = false;
	public boolean timerStarted;
	
	public GameBoardTimer() {
		this.timeRemaining = 30000;
		this.setTimer();
	}

	public GameBoardTimer(int time) {
		this.timeRemaining = time;
		this.setTimer();
	}
	
	public void setTimer() {
		this.timer = new Timer(1000, this);
	}
	
	private void decTime() {
		timeRemaining-=1000;
	}
	
	public int getTimeRemaining () {
		return this.timeRemaining;
	}
	
	public Timer getTimer() {
		return this.timer;
	}
	
	private void setTimesUp(boolean isTimeUp) {
		this.timesUp = isTimeUp;
	}
	
	public boolean isTimesUp() {
		return this.timesUp;
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		this.decTime();
		boolean timeElapsed = this.timeRemaining <= 0;
		if(timeElapsed) {
			setTimesUp(true);
		} else {
		int currentTime = getTimeRemaining();
		currentTime /= 1000;
		StringBuilder sb = new StringBuilder();
		sb.append("Time remaining: 0:").append(currentTime);
		System.out.println(currentTime);
		}
	}

}
