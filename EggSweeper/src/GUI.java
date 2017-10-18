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
	// constants for placing buttons
	int buffer = 50;
	int buttonSize = 18;
	int contentPaneSize = 2*buffer + GameBoard.size*buttonSize;
	// the game being played on the GUI
	Game game;
	
	// constructor, takes a Game object so the player can manipulate the model through the GUI
	GUI(Game newGame){
		game = newGame;
		// create and display the window
		gui = buildWindow();
        gui.pack();
        gui.setVisible(true);
        
        // add the buttons representing each GridSpace
        for (int i = 0; i < GameBoard.size; i++) {
        	for (int j = 0; j < GameBoard.size; j++) {
        		addButton(i, j);
        	}
        }
	}
	
	// adds a button at corresponding to an index not a location on the board
	public void addButton(int xIndex, int yIndex) {
		int xLocation = buffer + xIndex*(buttonSize);
		int yLocation = buffer + yIndex*(buttonSize);
		// create the button
		JButton gridButton = new JButton();
		gridButton.setLocation(xLocation, yLocation);
		gridButton.setSize(buttonSize, buttonSize);
		gridButton.setVisible(true);
		// adding the click listener
		gridButton.addActionListener(new ActionListener(){
	        public void actionPerformed(ActionEvent e){
	        		
	        		// clicking a button will call the checkSpace method for that GridSpace
	                System.out.println("Clicked (" + Integer.toString(xIndex) + "," + Integer.toString(yIndex) + ")");
	                game.checkSpace(xIndex, yIndex);
	                
	        }
	    });
		// add the button to the contentPane
		gui.getContentPane().add(gridButton);
	}
	
	public JFrame buildWindow(){
		//Create and set up the window.
        JFrame frame = new JFrame("Egg Sweeper");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        //Create and set up the content pane.
        JComponent contentPane = new JLayeredPane();
        contentPane.setPreferredSize(new Dimension(contentPaneSize, contentPaneSize)); 
        // adding the pane to the window
        frame.setContentPane(contentPane);
        
        return frame;
	}
	
	
}