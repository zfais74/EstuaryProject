import java.awt.Dimension;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.Random;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;

// The Controller

public class Controller{
	
	// The Model
	Player player;
	Board gameBoard;
	
	// The View
	JFrame frame;
	Animation animation;
	
	// displays start button
	public void startScreen() {
		JButton startButton = new JButton("Start Game");
		startButton.setFont(new Font("Arial", Font.PLAIN, 30));
		startButton.setLocation((Animation.contentPaneSize/2) - (Animation.generalButtonSize/2), (Animation.contentPaneSize/2) - (Animation.generalButtonSize/4));
		startButton.setSize(Animation.generalButtonSize, Animation.generalButtonSize/2);
		startButton.setVisible(true);
		frame.getContentPane().add(startButton);
		startButton.addActionListener(new ActionListener(){
	        public void actionPerformed(ActionEvent e){
	        		
	        		frame.getContentPane().remove(startButton);
	        		frame.getContentPane().repaint();
	        		// when clicked calls method to generate difficulty selection screen
	        		pickDifficulty();
	                
	        }
	    });
	}
	
	// displays easy, medium and hard button
	public void pickDifficulty() {
		JButton easyButton = new JButton("Easy");
		easyButton.setFont(new Font("Arial", Font.PLAIN, 30));
		easyButton.setLocation((Animation.contentPaneSize/2) - (Animation.generalButtonSize/2), (Animation.contentPaneSize/2) - (Animation.generalButtonSize/4) - Animation.generalButtonSize);
		easyButton.setSize(Animation.generalButtonSize, Animation.generalButtonSize/2);
		easyButton.setVisible(true);
		
		JButton mediumButton = new JButton("Medium");
		mediumButton.setFont(new Font("Arial", Font.PLAIN, 30));
		mediumButton.setLocation((Animation.contentPaneSize/2) - (Animation.generalButtonSize/2), (Animation.contentPaneSize/2) - (Animation.generalButtonSize/4));
		mediumButton.setSize(Animation.generalButtonSize, Animation.generalButtonSize/2);
		mediumButton.setVisible(true);
		
		JButton hardButton = new JButton("Hard");
		hardButton.setFont(new Font("Arial", Font.PLAIN, 30));
		hardButton.setLocation((Animation.contentPaneSize/2) - (Animation.generalButtonSize/2), (Animation.contentPaneSize/2) - (Animation.generalButtonSize/4) + Animation.generalButtonSize);
		hardButton.setSize(Animation.generalButtonSize, Animation.generalButtonSize/2);
		hardButton.setVisible(true);
		
		frame.getContentPane().add(easyButton);
		frame.getContentPane().add(mediumButton);
		frame.getContentPane().add(hardButton);
		
		easyButton.addActionListener(new ActionListener(){
	        public void actionPerformed(ActionEvent e){
	        		
	        	frame.getContentPane().remove(easyButton);
	        	frame.getContentPane().remove(mediumButton);
	        	frame.getContentPane().remove(hardButton);
	        	frame.getContentPane().repaint();
        		// when clicked picks character and difficulty
        		gameBoard = new Board(Board.Difficulty.EASY);
        		player = new Player(Player.Bird.DUNLIN);
        		animation.migrationAnimation();
	        }
	    });
		
		mediumButton.addActionListener(new ActionListener(){
	        public void actionPerformed(ActionEvent e){
	        		
	        	frame.getContentPane().remove(easyButton);
	        	frame.getContentPane().remove(mediumButton);
	        	frame.getContentPane().remove(hardButton);
	        	frame.getContentPane().repaint();
	        	gameBoard = new Board(Board.Difficulty.MEDIUM);
	        	player = new Player(Player.Bird.SANDPIPER); 
	        	animation.migrationAnimation();
	        }
	    });
		
		hardButton.addActionListener(new ActionListener(){
	        public void actionPerformed(ActionEvent e){
	        		
	        	frame.getContentPane().remove(easyButton);
	        	frame.getContentPane().remove(mediumButton);
	        	frame.getContentPane().remove(hardButton);
	        	frame.getContentPane().repaint();
	        	gameBoard = new Board(Board.Difficulty.HARD);
	        	player = new Player(Player.Bird.REDKNOT);
	        	animation.migrationAnimation();
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
		animation.getImages().get(0).setVisible(true);
	}

	// adds a button at corresponding to an index not a location on the board
	public void addButton(int xIndex, int yIndex) {
		int xLocation = Animation.buffer + xIndex*(Animation.gridButtonSize);
		int yLocation = Animation.buffer + yIndex*(Animation.gridButtonSize);
		// create the button
		JButton gridButton = new JButton();
		gridButton.setLocation(xLocation, yLocation);
		gridButton.setSize(Animation.gridButtonSize, Animation.gridButtonSize);
		//gridButton.setOpaque(true);
		gridButton.setContentAreaFilled(false);
		gridButton.setBorderPainted(true);
		gridButton.setVisible(true);
		// adding the click listener
		gridButton.addActionListener(new ActionListener(){
	        public void actionPerformed(ActionEvent e){
	        		
	        		// clicking a button will call the checkSpace method for that GridSpace
	                System.out.println("Clicked (" + Integer.toString(xIndex) + "," + Integer.toString(yIndex) + ")");
	                player.checkSpace(xIndex, yIndex, gameBoard);
	                animation.addHole(xIndex, yIndex);
	                if (gameBoard.getClicks() == 0){
	                	endScreen();
	                }
	                
	        }
	    });
		// add the button to the contentPane
		frame.getContentPane().add(gridButton, 0);
	}
	
	// displays score, and a quit button
	public void endScreen() {
		frame.getContentPane().removeAll();
		// need to repaint the contentPane to get rid of buttons
		frame.getContentPane().repaint();
		
		for(AniObject object: animation.getImages()) {
			object.setVisible(false);
		}
		
		// create a label
		JLabel eggLabel = new JLabel("You found " + Integer.toString(player.getEggs()) + " eggs,");
		eggLabel.setFont(new Font("Arial", Font.PLAIN, 60));
		// bounds must be set for label to display
		eggLabel.setBounds((Animation.contentPaneSize/2) - 400, (Animation.contentPaneSize/4) - 80, 800, 70);
		
		JLabel trashLabel = new JLabel("and you ate " + Integer.toString(player.getTrash()) + " pieces of trash,");
		trashLabel.setFont(new Font("Arial", Font.PLAIN, 60));
		trashLabel.setBounds((Animation.contentPaneSize/2) - 400, (Animation.contentPaneSize/4), 850, 70);
		
		JLabel scoreLabel = new JLabel("so your score is " + Integer.toString(player.getScore()) + "!!!");
		scoreLabel.setFont(new Font("Arial", Font.PLAIN, 60));
		scoreLabel.setBounds((Animation.contentPaneSize/2) - 400, (Animation.contentPaneSize/4) + 80, 800, 70);
		
		JButton quitButton = new JButton("Quit");
		quitButton.setFont(new Font("Arial", Font.PLAIN, 30));
		quitButton.setLocation((Animation.contentPaneSize/2) - (Animation.generalButtonSize/2), 8*(Animation.contentPaneSize/10));
		quitButton.setSize(Animation.generalButtonSize, Animation.generalButtonSize/2);
		quitButton.addActionListener(new ActionListener(){
	        public void actionPerformed(ActionEvent e){
	        	
	        	// when clicked, exits
		        System.exit(0);
	                
	        }
	    });
		
		frame.getContentPane().add(eggLabel);
		frame.getContentPane().add(trashLabel);
		frame.getContentPane().add(scoreLabel);
		frame.getContentPane().add(quitButton);
		
	}
	
	public static void tick(Animation animation, Controller controller) {
		List<AniObject> imgObjects = animation.getImages();
		for(int imageNum = 0; imageNum < imgObjects.size(); imageNum++) {
			if (imgObjects.get(imageNum).getName() == "bird") {
				imgObjects.get(imageNum).setY(imgObjects.get(imageNum).getY() - 10);
				if (imgObjects.get(imageNum).getY() == animation.contentPaneSize/5) {
					// remove the bird
					animation.getImages().remove(imageNum);
					//remove US
					animation.getImages().remove(imageNum - 1);
	        		// then displays the board buttons
	        		controller.buildBoard(); 
				}
			}
		}
	}
	
	// general test
	public static void main(String[] args) {
		Controller cont = new Controller();
       	cont.frame = new JFrame();
    	cont.frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	  	cont.animation = new Animation();
	  	cont.animation.setVisible(true);
	  	cont.frame.getContentPane().add(cont.animation);
	  	cont.frame.pack();
	  	cont.frame.setVisible(true);
	  	cont.startScreen();
	  	while (true) {
    		cont.frame.repaint();
    		tick(cont.animation, cont);
   		try {
    			Thread.sleep(40);
    		} catch (InterruptedException e) {
    			e.printStackTrace();
    		}
    	}
	}
	
}