import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JLayeredPane;

// The View

public class GUI{
	
	JFrame gui;
	int buffer = 50;
	int buttonSize = 18;
	int contentPaneSize = 2*buffer + GameBoard.size*buttonSize;
	Game game;
	
	
	GUI(Game newGame){
		game = newGame;
		gui = buildGUI();
		//Display the window.
        gui.pack();
        gui.setVisible(true);
        
        for (int i = 0; i < GameBoard.size; i++) {
        	for (int j = 0; j < GameBoard.size; j++) {
        		addButton(i, j);
        	}
        }
	}
	
	public void addButton(int xIndex, int yIndex) {
		int xLocation = buffer + xIndex*(buttonSize);
		int yLocation = buffer + yIndex*(buttonSize);
		JButton gridButton = new JButton();
		gridButton.setVisible(true);
		gridButton.setLocation(xLocation, yLocation);
		gridButton.setSize(buttonSize, buttonSize);
		gridButton.addActionListener(new ActionListener(){
	        public void actionPerformed(ActionEvent e){
	        		
	                System.out.println("Clicked (" + Integer.toString(xIndex) + "," + Integer.toString(yIndex) + ")");
	                game.checkSpace(xIndex, yIndex);
	                
	        }
	    });
		gui.getContentPane().add(gridButton);
	}
	
	public JFrame buildGUI(){
		//Create and set up the window.
        JFrame frame = new JFrame("Egg Sweeper");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        //Create and set up the content pane.
        JComponent contentPane = new JLayeredPane();
        contentPane.setPreferredSize(new Dimension(contentPaneSize, contentPaneSize));
        //pane.setOpaque(true); 
        // adding the pane to the window
        frame.setContentPane(contentPane);
        
        return frame;
	}
	
	
}