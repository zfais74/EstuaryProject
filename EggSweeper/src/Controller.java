import java.awt.Dimension;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Iterator;
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
		JLabel title = new JLabel("EGG SWEEPER");
		title.setFont(new Font("Arial", Font.PLAIN, 80));
		// bounds must be set for label to display
		title.setBounds(175, 125, 650, 100);
		frame.getContentPane().add(title);
		
		JButton startButton = new JButton("Start Game");
		startButton.setFont(new Font("Arial", Font.PLAIN, 30));
		startButton.setLocation((animation.contentPaneSize/2) - (animation.generalButtonSize/2), (animation.contentPaneSize/2) - (animation.generalButtonSize/4));
		startButton.setSize(animation.generalButtonSize, animation.generalButtonSize/2);
		startButton.setVisible(true);
		frame.getContentPane().add(startButton);
		startButton.addActionListener(new ActionListener(){
	        public void actionPerformed(ActionEvent e){
	        		
	        		frame.getContentPane().remove(startButton);
	        		frame.getContentPane().remove(title);
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
		easyButton.setLocation((animation.contentPaneSize/2) - (animation.generalButtonSize/2), (animation.contentPaneSize/2) - (animation.generalButtonSize/4) - animation.generalButtonSize);
		easyButton.setSize(animation.generalButtonSize, animation.generalButtonSize/2);
		easyButton.setVisible(true);
		
		JButton mediumButton = new JButton("Medium");
		mediumButton.setFont(new Font("Arial", Font.PLAIN, 30));
		mediumButton.setLocation((animation.contentPaneSize/2) - (animation.generalButtonSize/2), (animation.contentPaneSize/2) - (animation.generalButtonSize/4));
		mediumButton.setSize(animation.generalButtonSize, animation.generalButtonSize/2);
		mediumButton.setVisible(true);
		
		JButton hardButton = new JButton("Hard");
		hardButton.setFont(new Font("Arial", Font.PLAIN, 30));
		hardButton.setLocation((animation.contentPaneSize/2) - (animation.generalButtonSize/2), (animation.contentPaneSize/2) - (animation.generalButtonSize/4) + animation.generalButtonSize);
		hardButton.setSize(animation.generalButtonSize, animation.generalButtonSize/2);
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
		JLabel clicks = new JLabel("Clicks remaining: " + Integer.toString(gameBoard.getClicks()));
		clicks.setFont(new Font("Arial", Font.PLAIN, 40));
		// bounds must be set for label to display
		clicks.setBounds(animation.contentPaneSize/10, 0, 500, animation.buffer);
		frame.getContentPane().add(clicks);
		
		JLabel score = new JLabel("Score: " + Integer.toString(player.getScore()));
		score.setFont(new Font("Arial", Font.PLAIN, 40));
		// bounds must be set for label to display
		score.setBounds(animation.contentPaneSize/2, 0, 500, animation.buffer);
		frame.getContentPane().add(score);
		
		for (int i = 0; i < Board.boardSize; i++) {
			for (int j = 0; j < Board.boardSize; j++) {
				addButton(i, j);
			}
		}
		
		animation.getImages().get(0).setVisible(true);
	}

	// adds a button at corresponding to an index not a location on the board
	public void addButton(int xIndex, int yIndex) {
		
		int xLocation = animation.buffer + xIndex*(animation.gridButtonSize);
		int yLocation = animation.buffer + yIndex*(animation.gridButtonSize);
		// create the button
		JButton gridButton = new JButton();
		gridButton.setLocation(xLocation, yLocation);
		gridButton.setSize(animation.gridButtonSize, animation.gridButtonSize);
		//gridButton.setOpaque(true);
		gridButton.setContentAreaFilled(false);
		gridButton.setBorderPainted(true);
		gridButton.setVisible(true);
		// adding the click listener
		gridButton.addActionListener(new ActionListener(){
	        public void actionPerformed(ActionEvent e){
	        		
	        		// clicking a button will call the checkSpace method for that GridSpace
	                GridSpace.Item item = player.checkSpace(xIndex, yIndex, gameBoard);
	                animation.addChest(xIndex, yIndex);
	                
	                JLabel newClicks = new JLabel("Clicks remaining: " + Integer.toString(gameBoard.getClicks()));
	        		newClicks.setFont(new Font("Arial", Font.PLAIN, 40));
	        		// bounds must be set for label to display
	        		newClicks.setBounds(animation.contentPaneSize/10, 0, 500, animation.buffer);
	        		newClicks.setOpaque(true);
	        		frame.getContentPane().add(newClicks, 0);
	                //clickLabel.setText("Clicks remaining: " + Integer.toString(gameBoard.getClicks()));
	        		
	        		JLabel newScore = new JLabel("Score: " + Integer.toString(player.getScore()));
	        		newScore.setFont(new Font("Arial", Font.PLAIN, 40));
	        		// bounds must be set for label to display
	        		newScore.setBounds(animation.contentPaneSize/2, 0, 500, animation.buffer);
	        		newScore.setOpaque(true);
	        		frame.getContentPane().add(newScore, 0);
	        		
	        		if (item == GridSpace.Item.TRASH) {
		        		JLabel ateSome = new JLabel("Ate Some Trash :(");
		        		ateSome.setFont(new Font("Arial", Font.PLAIN, 40));
		        		// bounds must be set for label to display
		        		ateSome.setBounds(animation.contentPaneSize/10, animation.contentPaneSize - animation.buffer, 500, animation.buffer);
		        		ateSome.setOpaque(true);
		        		frame.getContentPane().add(ateSome, 0);
	        		}
	        		else if (item == GridSpace.Item.EGG) {
		        		JLabel ateSome = new JLabel("You Found and egg!!!");
		        		ateSome.setFont(new Font("Arial", Font.PLAIN, 40));
		        		// bounds must be set for label to display
		        		ateSome.setBounds(animation.contentPaneSize/10, animation.contentPaneSize - animation.buffer, 500, animation.buffer);
		        		ateSome.setOpaque(true);
		        		frame.getContentPane().add(ateSome, 0);
	        		}
	        		else if (item == GridSpace.Item.EMPTY) {
		        		JLabel ateSome = new JLabel("Nothing there...");
		        		ateSome.setFont(new Font("Arial", Font.PLAIN, 40));
		        		// bounds must be set for label to display
		        		ateSome.setBounds(animation.contentPaneSize/10, animation.contentPaneSize - animation.buffer, 500, animation.buffer);
		        		ateSome.setOpaque(true);
		        		frame.getContentPane().add(ateSome, 0);
	        		}
	        		else if (item == GridSpace.Item.ALREADYCHECKED) {
		        		JLabel ateSome = new JLabel("Already checked there.");
		        		ateSome.setFont(new Font("Arial", Font.PLAIN, 40));
		        		// bounds must be set for label to display
		        		ateSome.setBounds(animation.contentPaneSize/10, animation.contentPaneSize - animation.buffer, 500, animation.buffer);
		        		ateSome.setOpaque(true);
		        		frame.getContentPane().add(ateSome, 0);
	        		}
	        		
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
		eggLabel.setBounds((animation.contentPaneSize/2) - 400, (animation.contentPaneSize/4) - 80, 800, 70);
		
		JLabel trashLabel = new JLabel("and you ate " + Integer.toString(player.getTrash()) + " pieces of trash,");
		trashLabel.setFont(new Font("Arial", Font.PLAIN, 60));
		trashLabel.setBounds((animation.contentPaneSize/2) - 400, (animation.contentPaneSize/4), 850, 70);
		
		JLabel scoreLabel = new JLabel("so your score is " + Integer.toString(player.getScore()) + "!!!");
		scoreLabel.setFont(new Font("Arial", Font.PLAIN, 60));
		scoreLabel.setBounds((animation.contentPaneSize/2) - 400, (animation.contentPaneSize/4) + 80, 800, 70);
		
		JButton quitButton = new JButton("Quit");
		quitButton.setFont(new Font("Arial", Font.PLAIN, 30));
		quitButton.setLocation((animation.contentPaneSize/2) - (animation.generalButtonSize/2), 8*(animation.contentPaneSize/10));
		quitButton.setSize(animation.generalButtonSize, animation.generalButtonSize/2);
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
		Iterator<AniObject> itrMigration = animation.getImages().iterator();
		boolean buildBoard = false;
		while (itrMigration.hasNext()) {
			AniObject aniObject = itrMigration.next();
			if (aniObject.toString() == "bird") {
				aniObject.setY(aniObject.getY() - 10);
				if (aniObject.getY() == animation.contentPaneSize/5) {
					buildBoard = true;
					break;
				}
			}
		}
		if (buildBoard == true) {
			buildBoard = false;
			Iterator<AniObject> itrRemove = animation.getImages().iterator();
			while (itrRemove.hasNext()) {
				AniObject aniObjectRemove = itrRemove.next();
				if ((aniObjectRemove.toString() == "US") || (aniObjectRemove.toString() == "bird")) {
					itrRemove.remove();
				}
			}
			controller.buildBoard();
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