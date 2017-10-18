import java.awt.Dimension;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JLayeredPane;

// The View

public class GUI{
	
	JFrame gui;
	// constants for placing buttons
	int buffer = 50;
	int gridButtonSize = 18;
	int generalButtonSize = 200;
	int contentPaneSize = 2*buffer + GameBoard.size*gridButtonSize;
	// the game being played on the GUI
	Game game;
	
	// constructor, takes a Game object so the player can manipulate the model through the GUI
	GUI(Game newGame){
		game = newGame;
		// create and display the window
		gui = buildWindow();
        gui.pack();
        gui.setVisible(true);
        startScreen();
	}
	
	// displays start button
	public void startScreen() {
		JButton startButton = new JButton("Start Game");
		startButton.setFont(new Font("Arial", Font.PLAIN, 30));
		startButton.setLocation((contentPaneSize/2) - (generalButtonSize/2), (contentPaneSize/2) - (generalButtonSize/4));
		startButton.setSize(generalButtonSize, generalButtonSize/2);
		startButton.addActionListener(new ActionListener(){
	        public void actionPerformed(ActionEvent e){
	        		
	        		gui.getContentPane().remove(startButton);
	        		// when clicked calls method to generate difficulty selection screen
	        		pickDifficulty();
	                
	        }
	    });
		gui.getContentPane().add(startButton);
	}
	
	// displays easy, medium and hard button
	public void pickDifficulty() {
		JButton easyButton = new JButton("Easy");
		easyButton.setFont(new Font("Arial", Font.PLAIN, 30));
		easyButton.setLocation((contentPaneSize/2) - (generalButtonSize/2), (contentPaneSize/2) - (generalButtonSize/4) - generalButtonSize);
		easyButton.setSize(generalButtonSize, generalButtonSize/2);
		
		JButton mediumButton = new JButton("Medium");
		mediumButton.setFont(new Font("Arial", Font.PLAIN, 30));
		mediumButton.setLocation((contentPaneSize/2) - (generalButtonSize/2), (contentPaneSize/2) - (generalButtonSize/4));
		mediumButton.setSize(generalButtonSize, generalButtonSize/2);
		
		JButton hardButton = new JButton("Hard");
		hardButton.setFont(new Font("Arial", Font.PLAIN, 30));
		hardButton.setLocation((contentPaneSize/2) - (generalButtonSize/2), (contentPaneSize/2) - (generalButtonSize/4) + generalButtonSize);
		hardButton.setSize(generalButtonSize, generalButtonSize/2);
		
		gui.getContentPane().add(easyButton);
		gui.getContentPane().add(mediumButton);
		gui.getContentPane().add(hardButton);
		
		easyButton.addActionListener(new ActionListener(){
	        public void actionPerformed(ActionEvent e){
	        		
	        		gui.getContentPane().remove(easyButton);
	        		gui.getContentPane().remove(mediumButton);
	        		gui.getContentPane().remove(hardButton);
	        		// when clicked picks character and difficulty
	        		game.board = new GameBoard(GameBoard.Difficulty.EASY);
	        		game.player = new Player(Player.Bird.DUNLIN);
	        		// then displays the board buttons
	        		buildBoard();
	                
	        }
	    });
		
		mediumButton.addActionListener(new ActionListener(){
	        public void actionPerformed(ActionEvent e){
	        		
	        		gui.getContentPane().remove(easyButton);
	        		gui.getContentPane().remove(mediumButton);
	        		gui.getContentPane().remove(hardButton);
	        		game.board = new GameBoard(GameBoard.Difficulty.MEDIUM);
	        		game.player = new Player(Player.Bird.SANDPIPER);
	        		buildBoard();
	        }
	    });
		
		hardButton.addActionListener(new ActionListener(){
	        public void actionPerformed(ActionEvent e){
	        		
	        		gui.getContentPane().remove(easyButton);
	        		gui.getContentPane().remove(mediumButton);
	        		gui.getContentPane().remove(hardButton);
	        		game.board = new GameBoard(GameBoard.Difficulty.HARD);
	        		game.player = new Player(Player.Bird.REDKNOT);
	        		buildBoard();
	        }
	    });
	}
	
	// add the buttons representing each GridSpace
	public void buildBoard() {
		for (int i = 0; i < GameBoard.size; i++) {
			for (int j = 0; j < GameBoard.size; j++) {
				addButton(i, j);
			}
		}
	}
	
	// displays score, and a quit button
	public void endScreen(int playerScore) {
		gui.getContentPane().removeAll();
		
		JLabel eggLabel = new JLabel("You found " + Integer.toString(game.player.getEggs()) + " eggs!");
		eggLabel.setFont(new Font("Arial", Font.PLAIN, 60));
		eggLabel.setBounds((contentPaneSize/2) - 400, (contentPaneSize/4) - 80, 800, 70);
		eggLabel.setVisible(true);
		
		JLabel trashLabel = new JLabel("But you ate " + Integer.toString(game.player.getTrash()) + " pieces of trash,");
		trashLabel.setFont(new Font("Arial", Font.PLAIN, 60));
		trashLabel.setBounds((contentPaneSize/2) - 400, (contentPaneSize/4), 850, 70);
		trashLabel.setVisible(true);
		
		JLabel scoreLabel = new JLabel("So your score is " + Integer.toString(game.player.getScore()));
		scoreLabel.setFont(new Font("Arial", Font.PLAIN, 60));
		scoreLabel.setBounds((contentPaneSize/2) - 400, (contentPaneSize/4) + 80, 800, 70);
		scoreLabel.setVisible(true);
		
		JButton quitButton = new JButton("Quit");
		quitButton.setFont(new Font("Arial", Font.PLAIN, 30));
		quitButton.setLocation((contentPaneSize/2) - (generalButtonSize/2), 8*(contentPaneSize/10));
		quitButton.setSize(generalButtonSize, generalButtonSize/2);
		quitButton.addActionListener(new ActionListener(){
	        public void actionPerformed(ActionEvent e){
	        	
	        	// when clicked, exits
		        System.exit(0);
	                
	        }
	    });
		
		gui.getContentPane().add(eggLabel);
		gui.getContentPane().add(trashLabel);
		gui.getContentPane().add(scoreLabel);
		gui.getContentPane().add(quitButton);
		
		// for some reason need to repaint the contentPane to get rid of buttons generated in another mehtod
		gui.setVisible(true);
		gui.getContentPane().repaint();
		
	}
	
	// adds a button at corresponding to an index not a location on the board
	public void addButton(int xIndex, int yIndex) {
		int xLocation = buffer + xIndex*(gridButtonSize);
		int yLocation = buffer + yIndex*(gridButtonSize);
		// create the button
		JButton gridButton = new JButton();
		gridButton.setLocation(xLocation, yLocation);
		gridButton.setSize(gridButtonSize, gridButtonSize);
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