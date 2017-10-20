import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.util.Random;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;

// The Controller

public class Controller{
	
	// The Model
	Player player;
	Board gameBoard;
	
	// The View
	GUI gameGUI;
	
	Controller(){
		gameGUI = new GUI();
	}
	
	// displays start button
	public void startScreen() {
		JButton startButton = new JButton("Start Game");
		startButton.setFont(new Font("Arial", Font.PLAIN, 30));
		startButton.setLocation((gameGUI.contentPaneSize/2) - (gameGUI.generalButtonSize/2), (gameGUI.contentPaneSize/2) - (gameGUI.generalButtonSize/4));
		startButton.setSize(gameGUI.generalButtonSize, gameGUI.generalButtonSize/2);
		startButton.addActionListener(new ActionListener(){
	        public void actionPerformed(ActionEvent e){
	        		
	        		gameGUI.getFrame().getContentPane().remove(startButton);
	        		// when clicked calls method to generate difficulty selection screen
	        		pickDifficulty();
	                
	        }
	    });
		gameGUI.getFrame().getContentPane().add(startButton);
	}
	
	// displays easy, medium and hard button
	public void pickDifficulty() {
		JButton easyButton = new JButton("Easy");
		easyButton.setFont(new Font("Arial", Font.PLAIN, 30));
		easyButton.setLocation((gameGUI.contentPaneSize/2) - (gameGUI.generalButtonSize/2), (gameGUI.contentPaneSize/2) - (gameGUI.generalButtonSize/4) - gameGUI.generalButtonSize);
		easyButton.setSize(gameGUI.generalButtonSize, gameGUI.generalButtonSize/2);
		
		JButton mediumButton = new JButton("Medium");
		mediumButton.setFont(new Font("Arial", Font.PLAIN, 30));
		mediumButton.setLocation((gameGUI.contentPaneSize/2) - (gameGUI.generalButtonSize/2), (gameGUI.contentPaneSize/2) - (gameGUI.generalButtonSize/4));
		mediumButton.setSize(gameGUI.generalButtonSize, gameGUI.generalButtonSize/2);
		
		JButton hardButton = new JButton("Hard");
		hardButton.setFont(new Font("Arial", Font.PLAIN, 30));
		hardButton.setLocation((gameGUI.contentPaneSize/2) - (gameGUI.generalButtonSize/2), (gameGUI.contentPaneSize/2) - (gameGUI.generalButtonSize/4) + gameGUI.generalButtonSize);
		hardButton.setSize(gameGUI.generalButtonSize, gameGUI.generalButtonSize/2);
		
		gameGUI.getFrame().getContentPane().add(easyButton);
		gameGUI.getFrame().getContentPane().add(mediumButton);
		gameGUI.getFrame().getContentPane().add(hardButton);
		
		easyButton.addActionListener(new ActionListener(){
	        public void actionPerformed(ActionEvent e){
	        		
	        	gameGUI.getFrame().getContentPane().remove(easyButton);
	        	gameGUI.getFrame().getContentPane().remove(mediumButton);
	        	gameGUI.getFrame().getContentPane().remove(hardButton);
	        	gameGUI.getFrame().getContentPane().repaint();
        		// when clicked picks character and difficulty
        		gameBoard = new Board(Board.Difficulty.EASY);
        		player = new Player(Player.Bird.DUNLIN);
        		// then displays the board buttons
        		buildBoard();       
	        }
	    });
		
		mediumButton.addActionListener(new ActionListener(){
	        public void actionPerformed(ActionEvent e){
	        		
	        	gameGUI.getFrame().getContentPane().remove(easyButton);
	        	gameGUI.getFrame().getContentPane().remove(mediumButton);
	        	gameGUI.getFrame().getContentPane().remove(hardButton);
	        	gameGUI.getFrame().getContentPane().repaint();
	        	gameBoard = new Board(Board.Difficulty.MEDIUM);
	        	player = new Player(Player.Bird.SANDPIPER);
        		// then displays the board buttons
        		buildBoard(); 
	        }
	    });
		
		hardButton.addActionListener(new ActionListener(){
	        public void actionPerformed(ActionEvent e){
	        		
	        	gameGUI.getFrame().getContentPane().remove(easyButton);
	        	gameGUI.getFrame().getContentPane().remove(mediumButton);
	        	gameGUI.getFrame().getContentPane().remove(hardButton);
	        	gameGUI.getFrame().getContentPane().repaint();
	        	gameBoard = new Board(Board.Difficulty.HARD);
	        	player = new Player(Player.Bird.REDKNOT);
        		// then displays the board buttons
        		buildBoard(); 
	        }
	    });
	}
	
	// add the buttons representing each GridSpace
	public void buildBoard() {
		for (int i = 0; i < Board.boardSize; i++) {
			for (int j = 0; j < Board.boardSize; j++) {
				addButton(i, j);
			}
		}
		gameGUI.loadBeach(gameGUI.buffer, gameGUI.buffer, gameGUI.gridButtonSize*Board.boardSize);
	}

	// adds a button at corresponding to an index not a location on the board
	public void addButton(int xIndex, int yIndex) {
		int xLocation = gameGUI.buffer + xIndex*(gameGUI.gridButtonSize);
		int yLocation = gameGUI.buffer + yIndex*(gameGUI.gridButtonSize);
		// create the button
		JButton gridButton = new JButton();
		gridButton.setLocation(xLocation, yLocation);
		gridButton.setSize(gameGUI.gridButtonSize, gameGUI.gridButtonSize);
		//gridButton.setOpaque(true);
		gridButton.setContentAreaFilled(false);
		gridButton.setBorderPainted(false);
		gridButton.setVisible(true);
		// adding the click listener
		gridButton.addActionListener(new ActionListener(){
	        public void actionPerformed(ActionEvent e){
	        		
	        		// clicking a button will call the checkSpace method for that GridSpace
	                System.out.println("Clicked (" + Integer.toString(xIndex) + "," + Integer.toString(yIndex) + ")");
	                player.checkSpace(xIndex, yIndex, gameBoard);
	                if (gameBoard.getClicks() == 0){
	                	endScreen();
	                }
	                
	        }
	    });
		// add the button to the contentPane
		gameGUI.getFrame().getContentPane().add(gridButton, 0);
	}
	
	// displays score, and a quit button
	public void endScreen() {
		gameGUI.getFrame().getContentPane().removeAll();
		// need to repaint the contentPane to get rid of buttons
		gameGUI.getFrame().getContentPane().repaint();
		
		// create a label
		JLabel eggLabel = new JLabel("You found " + Integer.toString(player.getEggs()) + " eggs,");
		eggLabel.setFont(new Font("Arial", Font.PLAIN, 60));
		// bounds must be set for label to display
		eggLabel.setBounds((gameGUI.contentPaneSize/2) - 400, (gameGUI.contentPaneSize/4) - 80, 800, 70);
		
		JLabel trashLabel = new JLabel("and you ate " + Integer.toString(player.getTrash()) + " pieces of trash,");
		trashLabel.setFont(new Font("Arial", Font.PLAIN, 60));
		trashLabel.setBounds((gameGUI.contentPaneSize/2) - 400, (gameGUI.contentPaneSize/4), 850, 70);
		
		JLabel scoreLabel = new JLabel("so your score is " + Integer.toString(player.getScore()) + "!!!");
		scoreLabel.setFont(new Font("Arial", Font.PLAIN, 60));
		scoreLabel.setBounds((gameGUI.contentPaneSize/2) - 400, (gameGUI.contentPaneSize/4) + 80, 800, 70);
		
		JButton quitButton = new JButton("Quit");
		quitButton.setFont(new Font("Arial", Font.PLAIN, 30));
		quitButton.setLocation((gameGUI.contentPaneSize/2) - (gameGUI.generalButtonSize/2), 8*(gameGUI.contentPaneSize/10));
		quitButton.setSize(gameGUI.generalButtonSize, gameGUI.generalButtonSize/2);
		quitButton.addActionListener(new ActionListener(){
	        public void actionPerformed(ActionEvent e){
	        	
	        	// when clicked, exits
		        System.exit(0);
	                
	        }
	    });
		
		gameGUI.getFrame().getContentPane().add(eggLabel);
		gameGUI.getFrame().getContentPane().add(trashLabel);
		gameGUI.getFrame().getContentPane().add(scoreLabel);
		gameGUI.getFrame().getContentPane().add(quitButton);
		
	}
	
	// general test
	public static void main(String[] args) {
		// the main thread with an anonymous implementation of the Runnable class
		javax.swing.SwingUtilities.invokeLater(new Runnable() {
            public void run() {
            	Controller game = new Controller();
            	game.startScreen();
            }
        });
	}
	
}