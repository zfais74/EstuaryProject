package TimeManagement;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class GameTimeActionListener implements ActionListener {
	private int time;
	
	public GameTimeActionListener(int time) {
		this.time = time;
	}
	
	public int getTime() {
		return this.time;
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		this.time -- ;
		System.out.println("Called");
	}

}
