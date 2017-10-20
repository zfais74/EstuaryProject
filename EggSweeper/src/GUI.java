import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JLayeredPane;
import javax.swing.JPanel;

// The View

public class GUI extends JPanel{
	
	private JFrame frame;
	private JLayeredPane contentPane;
	
	// constants for placing buttons
	public int buffer = 50;
	public int gridButtonSize = 45;
	public int generalButtonSize = 200;
	public int contentPaneSize = 1000;
	
	public JFrame getFrame() {
		return frame;
	}
	
	public JLayeredPane getContentPane() {
		return contentPane;
	}
	
	// constructor, takes a Game object to link GUI to controller
	GUI(){
		// create and display the window
		frame = new JFrame("Egg Sweeper");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        //Create and set up the content pane.
        contentPane = new JLayeredPane();
        contentPane.setPreferredSize(new Dimension(contentPaneSize, contentPaneSize)); 
        
        frame.setContentPane(contentPane);
  
        frame.pack();
        frame.setVisible(true);
	}
	
	public void loadBeach(int xLoc, int yLoc, int size) {
		ImageIcon beach = new ImageIcon(GUI.class.getResource("images/beach.png"));
        JLabel beachLabel = new JLabel(beach);
        beachLabel.setBounds(xLoc, yLoc, size, size);
        contentPane.add(beachLabel, 1);
	}
	
}