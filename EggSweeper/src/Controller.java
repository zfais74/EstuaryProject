import java.awt.CardLayout;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.MouseInfo;
import java.awt.Point;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.io.FileNotFoundException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.Timer;

import TimeManagement.GameBoardTimer;
import TimeManagement.PowerUpTimer;
import enums.Difficulty;
import enums.Direction;
import enums.Item;
import enums.PowerUps;

// The Controller

/**
 * This class is the engine of the game, it controls all animations, and managers all model data.
 * @author Will Ransom, Zeke Faison, Elton Mwale, Chima Akparanta, Yat Chan
 *
 */
public class Controller implements Serializable, ActionListener {
	
	// The Model
	Player player;
	Board gameBoard;
	// timers
	PowerUpTimer powerUpTimer;
	GameBoardTimer gameBoardTimer;
	// extra timer which checks if the other timers are out of time
	Timer checkTimersTimer;
	// dummy board used for tutorial
	Board dummyBoard;
	
	// The View
	JFrame frame;
	Animation animation;
	// CarLayout manages which panel is showing
	CardLayout screens;
	// cardPanel holds the list of panels
	JPanel cardPanel;
	
	// constant for tick method
	private int tickStage = 0;
	
	// some global variables
	JLabel powerUpLabel = new JLabel("");
	JLabel aniLabel = new JLabel("");
	JLabel timerLabel = new JLabel("");
	public boolean foundAllEggs = false;

	JPanel blank;
	JButton skipRestart;
	
	// tutorial stage variables
	boolean tutorialFoundTrash = false;
	boolean tutorialFoundEgg = false;
	boolean finishedTutorial = false;
	
	// points to win the game at all difficulty levels
	private int pointsToWin;
	
	/**
	 * Sets default GridBagLayout constraints
	 * @return default constraints
	 */
	private GridBagConstraints constraintFactory() {
		GridBagConstraints constraints = new GridBagConstraints();
		
		// the constraints are defined every time you add an element
		// grid x and grid y are the positions the component starts at
		constraints.gridx = 0;
		constraints.gridy = 0;
		// grid width and grid height define how many spaces the component takes up
		constraints.gridwidth = 1;
		constraints.gridheight = 1;
		// how the components can fit into place
		constraints.weightx = 1.0;
		constraints.weighty = 1.0;
		// layout settings
		constraints.anchor = GridBagConstraints.CENTER;
		constraints.fill = GridBagConstraints.NONE;
		return constraints;
	}

	/**
	 * Displays the start and instructions buttons, and the background image
	 */
	public void startScreen() {
		checkTimersTimer = new Timer(1000, this);
		checkTimersTimer.start();
		// declare a new JPanel
		JPanel startPanel = new JPanel();
		// set its layout manager to GridBag
		startPanel.setLayout(new GridBagLayout());
		
		// the constraints describe each new component's location
		GridBagConstraints constraints = constraintFactory();
		constraints.gridx = 1;
		constraints.gridy = 0;
		
		// empty labels force the layout manager to place buttons at center of screen
		JLabel holder1 = new JLabel(" ");
		startPanel.add(holder1, constraints, 0);
		
		constraints.gridx = 1;
		constraints.gridy = 1;
		
		JLabel holder2 = new JLabel(" ");
		startPanel.add(holder2, constraints, 0);
		
		constraints.gridy = 2;
		
		JLabel title = new JLabel("EGG SWEEPER");
		title.setFont(new Font("Arial", Font.PLAIN, 80));
		
		startPanel.add(title, constraints, 0);
		
		// set gridheight to 7 so image will span whole screen
		constraints.gridy = 0;
		constraints.gridheight = 7;
		
		JLabel backgroundImage = new JLabel(animation.getBackgroundImage());
		startPanel.add(backgroundImage, constraints, 1);
		
		constraints.gridheight = 1;
		
		JButton startButton = new JButton("Start Game");
		JButton instButton = new JButton("Instructions");
		startButton.setFont(new Font("Arial", Font.PLAIN, 30));
		instButton.setFont(new Font("Arial", Font.PLAIN, 30));
		startButton.setVisible(true);instButton.setVisible(true);
		
		constraints.gridy = 3;
		startPanel.add(startButton,constraints, 1);
		constraints.gridy = 4;
		startPanel.add(instButton,constraints, 1);
		
		constraints.gridy = 5;
		JLabel holder3 = new JLabel(" ");
		startPanel.add(holder3, constraints);
		
		constraints.gridy = 6;
		JLabel holder4 = new JLabel(" ");
		startPanel.add(holder4, constraints);
		
		startButton.addActionListener((ActionEvent e)->{

	        		// when clicked calls method to generate difficulty selection screen
	        		pickDifficulty();
	                
	        }
	    );

		instButton.addActionListener((ActionEvent e)-> {
				frame.getContentPane().remove(startPanel);
				frame.validate();
				frame.getContentPane().repaint();
				//
				DisplayInstructions();

			}
		);
		
		//Adds the start screen to the deck
		cardPanel.add(startPanel, "Start");
		// show the start panel
		screens.show(cardPanel, "Start");
		
		//adds a blank screen to the deck
		JPanel blankScreen = new JPanel();
		blankScreen.setLayout(new GridLayout(10,0));
		
		// aniLabel displays messages during animations
		aniLabel.setText("A Red Knot migrating from South America to the Arctic stops in the Delaware Bay to refuel on Horseshoe Crab eggs.");
		aniLabel.setFont(new Font("Arial", Font.PLAIN, 30));
		aniLabel.setVerticalAlignment(JLabel.CENTER);
		aniLabel.setHorizontalAlignment(JLabel.CENTER);
		
		blankScreen.add(aniLabel);
		
		// holders to put the restart and skip buttons at the bottom with GridLayout
		for (int i = 0; i < 7; i++) {
			JLabel holderLabel = new JLabel(" ");
			blankScreen.add(holderLabel);
		}
		
		JButton skip = new JButton("Skip Animation");
		skip.setFont(new Font("Arial", Font.PLAIN, 30));
		
		skip.addActionListener(new ActionListener(){
	        public void actionPerformed(ActionEvent e){
	        		// build the board
	                tickStage = 1;
	        }
	    });
		
		skipRestart = skip;
		blankScreen.add(skipRestart);
		
		//too be referenced later in endScreen
		blank = blankScreen;
		
    	cardPanel.add(blankScreen, "Blank");
    	
    	// add the panel to the frame
		frame.add(cardPanel);
		frame.validate();
		frame.repaint();

	}
	
	/**
	 * Queues the instructions image and displays a start button
	 */
	public void DisplayInstructions(){
		JPanel instructionPanel = new JPanel();
		instructionPanel.setLayout(new GridBagLayout());
		GridBagConstraints constraints = constraintFactory();
		
		JButton startButton = new JButton("Start Game");
		startButton.setFont(new Font("Arial", Font.PLAIN, 30));
		startButton.setVisible(true);
		startButton.addActionListener((ActionEvent e)->{
	        		
	        		frame.getContentPane().remove(instructionPanel);
	        		frame.validate();
	        		frame.getContentPane().repaint();
	        		// when clicked calls method to generate difficulty selection screen
	        		pickDifficulty();
	                
	        }
	    );
		
		JLabel instructions = new JLabel(animation.getInstructionsIcon());
		instructions.setSize(new Dimension(100,100));
		
		constraints.gridx = 0;
		constraints.gridy = 0;
		instructionPanel.add(instructions, constraints);
		
		constraints.gridx = 0;
		constraints.gridy = 1;
		instructionPanel.add(startButton, constraints);
		
		cardPanel.add(instructionPanel, "Instructions");
		screens.show(cardPanel, "Instructions");
		frame.validate();
		frame.repaint();

	}
	
	/**
	 * Displays the Easy, Medium, and Hard buttons, and displays the background image and initiates the intro animation
	 */
	public void pickDifficulty() {
		JPanel difficultyPanel = new JPanel();
		difficultyPanel.setLayout(new GridBagLayout());
		
		GridBagConstraints constraints = constraintFactory();
		constraints.gridx = 1;
		constraints.weightx = 50;
		constraints.weighty = 50;
		
		// again empty labels force layout manager to place buttons at center of screen
		JLabel holder1 = new JLabel(" ");
		JLabel holder2 = new JLabel(" ");
		JLabel holder3 = new JLabel(" ");
		
		
		JLabel pickDiff = new JLabel("Pick a Difficulty");
		pickDiff.setFont(new Font("Arial", Font.PLAIN, 50));
		
		JButton easyButton = new JButton("Easy");
		easyButton.setFont(new Font("Arial", Font.PLAIN, 30));
		easyButton.setVisible(true);
		
		JButton mediumButton = new JButton("Medium");
		mediumButton.setFont(new Font("Arial", Font.PLAIN, 30));
		mediumButton.setVisible(true);
		
		JButton hardButton = new JButton("Hard");
		hardButton.setFont(new Font("Arial", Font.PLAIN, 30));
		hardButton.setVisible(true);
		
		JLabel holder4 = new JLabel(" ");
		JLabel holder5 = new JLabel(" ");
		JLabel holder6 = new JLabel(" ");
		
		constraints.gridy = 0;
		difficultyPanel.add(holder1, constraints, 0);
		constraints.gridy = 1;
		difficultyPanel.add(holder2, constraints, 0);
		constraints.gridy = 2;
		difficultyPanel.add(holder3, constraints, 0);
		constraints.gridy = 3;
		difficultyPanel.add(pickDiff,constraints, 0);
		
		constraints.gridy = 0;
		constraints.gridheight = 10;
		 
		JLabel backgroundImage = new JLabel(animation.getBackgroundImage());
		difficultyPanel.add(backgroundImage, constraints, 1);
		
		constraints.gridheight = 1;
		
		constraints.gridy = 4;
		//After a button is added add the width
		difficultyPanel.add(easyButton,constraints, 1);
		constraints.gridy = 5;
		difficultyPanel.add(mediumButton,constraints, 1);
		constraints.gridy = 6;
		difficultyPanel.add(hardButton,constraints, 1);
		constraints.gridy = 7;
		difficultyPanel.add(holder4, constraints, 1);
		constraints.gridy = 8;
		difficultyPanel.add(holder5, constraints, 0);
		constraints.gridy = 9;
		difficultyPanel.add(holder6, constraints, 0);
		
		
		
		cardPanel.add(difficultyPanel, "Difficulty");
		screens.show(cardPanel, "Difficulty");
		frame.validate();
		frame.repaint();
		
		// each button will start the game
		easyButton.addActionListener((ActionEvent e)->{

	        	// switches to the blank screen in the deck for an animation
	        	screens.show(cardPanel, "Blank");
	        	frame.getContentPane().revalidate();
	        	frame.getContentPane().repaint();
        		// when clicked picks character and difficulty
	        	// queues up the tutorial board and game board for after the animation
        		gameBoard = new Board(Difficulty.EASY);
        		dummyBoard = new Board(Difficulty.EASY);
        		player = new Player();
        		pointsToWin = 10;
        		// starts the first migration animation
        		animation.migrationAnimation();
	        }
	    );
		
		mediumButton.addActionListener((ActionEvent e)->{
	        	screens.show(cardPanel, "Blank");
	        	frame.getContentPane().revalidate();
	        	frame.getContentPane().repaint();
	        	gameBoard = new Board(Difficulty.MEDIUM);
	        	dummyBoard = new Board(Difficulty.MEDIUM);
	        	player = new Player(); 
	        	pointsToWin = 20;
	        	animation.migrationAnimation();
	        }
	    );
		
		hardButton.addActionListener((ActionEvent e)->{
	        		
	        	screens.show(cardPanel, "Blank");
	        	frame.getContentPane().revalidate();
	        	frame.getContentPane().repaint();
	        	gameBoard = new Board(Difficulty.HARD);
	        	dummyBoard = new Board(Difficulty.HARD);
	        	player = new Player();
	        	pointsToWin = 30;
	        	animation.migrationAnimation();
	        }
	    );
	}
	
	/**
	 * Sets up the actual game play panel and all necessary action listeners
	 */
	public void buildBoard() {
		
		// starts the timer
		gameBoardTimer = new GameBoardTimer();
		gameBoardTimer.getTimer().start();
		timerLabel.setText("Time: " + Integer.toString(gameBoardTimer.getTimeRemaining()/1000) + " ");
		
		// the sets up the board panel's initial state
		JPanel boardPanel = new JPanel();
		boardPanel.setLayout(new GridBagLayout());
		GridBagConstraints constraints = constraintFactory();
		
		constraints.gridx = 1;
		constraints.gridy = 1;
		constraints.anchor = GridBagConstraints.EAST;
		
		powerUpLabel.setText("<html>Help the hungry bird&nbsp<br/>find some Eggs!</html>");
		powerUpLabel.setFont(new Font("Arial", Font.PLAIN, 40));
		boardPanel.add(powerUpLabel, constraints, 0);
		
		constraints.gridx = 1;
		constraints.gridy = 2;
		constraints.anchor = GridBagConstraints.EAST;
		
		JLabel score = new JLabel("Score: " + Integer.toString(player.getScore()) + " ");
		score.setFont(new Font("Arial", Font.PLAIN, 40));
		boardPanel.add(score, constraints);
		
		constraints.gridx = 1;
		constraints.gridy = 3;
		constraints.anchor = GridBagConstraints.EAST;
		
		timerLabel.setFont(new Font("Arial", Font.PLAIN, 40));
		boardPanel.add(timerLabel, constraints);
		
//		JButton save = new JButton("Save");
//		save.setFont(new Font("Arial", Font.PLAIN, 30));
//		save.setFocusable(false);
//		save.setVisible(true);
//		boardPanel.add(save,constraints);
//		save.addActionListener((ActionEvent a)->{
//			Load.SaveGame(this);
//		});
		
		// bird and board AniObject must be referenced by anonymous classes and therefore
		// must be final, final references are made for this purpose
		AniObject bird = null;
		AniObject board = null;
		Iterator<AniObject> boardItr = animation.getImages().iterator();
		while (boardItr.hasNext()) {
			AniObject next = boardItr.next();
			if (next.toString().equalsIgnoreCase("board")  || next.toString().equalsIgnoreCase("beach") || 
					next.toString().equalsIgnoreCase("grass")) { 
				next.setVisible(true);
			}
			if (next.toString().equalsIgnoreCase("bird")) {
				bird = next;
			}
			if (next.toString().equalsIgnoreCase("board")) {
				board = next;
			}
		}
		final AniObject birdMouse = bird;
		final AniObject boardMouse = board;
		
		// the size and position of the bird are reset to the location of the mouse after the animation
		double birdRatio = animation.screenRatio*getSizeRatio(bird.getY(), board);
		bird.setSize(birdRatio);
		Point mouseLoc = MouseInfo.getPointerInfo().getLocation();
		bird.setX((int) Math.round(mouseLoc.getX() + 10 - bird.getYSize()/4.5));
		bird.setY((int) Math.round(mouseLoc.getY() - bird.getYSize()/1.8));
		frame.repaint();
		
		constraints.gridx = 1;
		constraints.gridy = 4;
		constraints.anchor = GridBagConstraints.EAST;
    		
		JLabel ateSomeHolder = new JLabel(" ");
		ateSomeHolder.setFont(new Font("Arial", Font.PLAIN, 40));
		boardPanel.add(ateSomeHolder, constraints);
		
		constraints.gridx = 1;
		constraints.gridy = 5;
		constraints.anchor = GridBagConstraints.EAST;
		JButton chestButton = new JButton();
		chestButton.setPreferredSize(new Dimension(200, 200));
		// the chest button is visible (for the layout manager) but transparent and disabled.
		chestButton.setContentAreaFilled(false);
		chestButton.setBorderPainted(false);
		chestButton.setVisible(true);
		chestButton.setEnabled(false);
		// for the mouse hover to continue when the mouse enters the boundaries of a button
		// it is necessary to define a motion listen just for that button
		chestButton.addMouseMotionListener(new MouseMotionListener() {

			@Override
			public void mouseDragged(MouseEvent arg0) {
				
			}

			@Override
			public void mouseMoved(MouseEvent e) {
				birdMouse.setX( (int) Math.round(e.getX() + 5 - birdMouse.getYSize()/6.) + chestButton.getX());
				birdMouse.setY((int) Math.round(e.getY() - birdMouse.getYSize()/1.8) + chestButton.getY());
				double newBirdRatio = animation.screenRatio*getSizeRatio(birdMouse.getY(), boardMouse);
				birdMouse.setSize(newBirdRatio);
				frame.repaint();
				
			}
			
		});
		boardPanel.add(chestButton, constraints, 0);
		
		// show the board panel in its initial state
		cardPanel.add(boardPanel, "Board");
		screens.show(cardPanel, "Board");
		frame.validate();
		frame.repaint();
		
		// a mouse listener tracks any clicks on the board panel
		frame.getContentPane().addMouseListener(new MouseListener() {
		    @Override
		    public void mouseClicked(MouseEvent e) {
		    	int xLoc = e.getX();
		        int yLoc = e.getY();
		        // it first decides which if any grid space exists at the click location
		        int[] gridIndex = windowToGrid(xLoc, yLoc);
		        int xIndex = gridIndex[0];
		        int yIndex = gridIndex[1];
		        if ((xIndex == -1) || (yIndex == -1)){
		        	// click was not on the actual game board
		        	return;
		        }
		        // click was on the game board at GridSpace[xIndex][yIndex]
		        else {
		        	// checks the contents of the GridSpace
		        	Item item = player.checkSpace(xIndex, yIndex, gameBoard);
		        	// adds a hole image of the proper shape and size at the proper location, see windowToGrid()
		        	animation.addHole(gridIndex[2], gridIndex[3], gridIndex[4], gridIndex[5]);
		        	
		        	// removes all previous components
		        	boardPanel.removeAll();
		        	frame.validate();
		        	frame.repaint();
	                
		        	// and resets the components in their new state
		        	constraints.gridx = 1;
		    		constraints.gridy = 1;
		    		constraints.anchor = GridBagConstraints.EAST;
		    		
		    		boardPanel.add(powerUpLabel, constraints);
	                
	        		constraints.gridx = 1;
	        		constraints.gridy = 2;
	        		constraints.anchor = GridBagConstraints.EAST;
	        		
	        		JLabel newScore = new JLabel("Score: " + Integer.toString(player.getScore()) + " ");
	        		newScore.setFont(new Font("Arial", Font.PLAIN, 40));
	        		boardPanel.add(newScore, constraints);
	        		
	        		constraints.gridx = 1;
	        		constraints.gridy = 3;
	        		constraints.anchor = GridBagConstraints.EAST;

	        		timerLabel.setFont(new Font("Arial", Font.PLAIN, 40));
	        		boardPanel.add(timerLabel, constraints);
	        		
//	        		JButton save = new JButton("Save");
//	        		save.setFocusable(false);
//	        		save.setFont(new Font("Arial", Font.PLAIN, 30));
//	        		save.setVisible(true);
//	        		boardPanel.add(save,constraints);
//	        		save.addActionListener((ActionEvent a)->{
//	        			Load.SaveGame(thisController);
//	        		});
	        		
	        		JLabel ateSome = new JLabel(" ");
	        		Font ateFont = new Font("Arial", Font.PLAIN, 40);
	        		final JLabel ateSomeRef = ateSome;
	        		
	        		constraints.gridx = 1;
	        		constraints.gridy = 5;
	        		constraints.anchor = GridBagConstraints.EAST;
	        		
	        		JButton chestButton = new JButton();
	        		chestButton.setPreferredSize(new Dimension(200, 200));
	        		chestButton.setContentAreaFilled(false);
	        		chestButton.setBorderPainted(false);
	        		chestButton.setVisible(true);
	        		// the chest button now may be activated and so gets an action listener
	        		chestButton.addActionListener((ActionEvent a)->{
	        	        		
	        	        	String question;
	        	        	try {		
								question = gameBoard.getPowerupQuestion();
								List<String> possibleAns = gameBoard.getPossibleAnswers();
								Collections.shuffle(possibleAns);
								// disables itself and clears the ateSome label
								chestButton.setEnabled(false);
								ateSomeRef.setText(" ");
								// calls the power up question screen
								questionScreen(question, possibleAns);
								
							} catch (FileNotFoundException e1) {
								e1.printStackTrace();
								
							}
	        	        	
	        	        }
	        	    );
	        		// again a separate mouse hover listener if required for the chest button
	        		chestButton.addMouseMotionListener(new MouseMotionListener() {

	        			@Override
	        			public void mouseDragged(MouseEvent arg0) {
	        				
	        			}

	        			@Override
	        			public void mouseMoved(MouseEvent e) {
	        				birdMouse.setX( (int) Math.round(e.getX() + 5 - birdMouse.getYSize()/6.) + chestButton.getX());
	        				birdMouse.setY((int) Math.round(e.getY() - birdMouse.getYSize()/1.8) + chestButton.getY());
	        				double newBirdRatio = getSizeRatio(birdMouse.getY(), boardMouse);
	        				birdMouse.setSize(newBirdRatio);
	        				frame.repaint();
	        				
	        			}
	        			
	        		});
	        		
	        		boardPanel.add(chestButton, constraints);
	        		
	        		// handles the contents of the GridSpace clicked
	        		if (item == Item.EGG) {
	        			// checks for double eggs
	        			int mult = player.getEggMultiplier();
	        			// sets the proper score image using the same size, shape and location as the hole image
	        			animation.scoreImage(gridIndex[2], gridIndex[3], gridIndex[4], gridIndex[5], "plus", mult);
	        			// changes the birds size
	        			if (mult == 1) {
	        				birdMouse.incScoreSize(10);
	        			}
	        			else if (mult == 2){
	        				birdMouse.incScoreSize(20);
	        			}
		        		ateSome.setText("You Found an egg!! ");
		        		ateSome.setFont(ateFont);
		        		frame.getContentPane().add(ateSome, 0);
		        		constraints.gridx = 1;
		        		constraints.gridy = 4;
		        		constraints.anchor = GridBagConstraints.EAST;
		        		boardPanel.add(ateSome, constraints);
		        		// activates the chest button 50% of the time
		        		if (NumberManipulation.generateNum(10) < 6 && player.hasPowerUp() == false) {
		        			// sets the image of the chest, the rest of the button is still invisible
		        			chestButton.setIcon(animation.getChestIcon());
		                	chestButton.setEnabled(true);
		        		}
		        		// checks to see if the player has found all the eggs on the board and if so ends the game.
		        		if (player.getEggsFound() == gameBoard.getTotEggs()) {
		        			if (player.getScore() > pointsToWin) {
		        				aniLabel.setText("You found all the eggs! Your Red Knot will definetly make it to the Arctic!!!");
		        				aniLabel.setFont(new Font("Arial", Font.PLAIN, 50));
		        				foundAllEggs = true;
		        			}
		        			else {
		        				aniLabel.setText("You found all the eggs, but your Red Knot still won't make it to the Arctic :(");
		        				aniLabel.setFont(new Font("Arial", Font.PLAIN, 50));
		        				foundAllEggs = true;
		        			}
		        			endScreen();
		        		}
	        		}
	        		
	        		else if (item == Item.ALREADYCHECKED) {
		        		ateSome = new JLabel("Already looked there ");
		        		ateSome.setFont(ateFont);
		        		constraints.gridx = 1;
		        		constraints.gridy = 4;
		        		constraints.anchor = GridBagConstraints.EAST;
		        		boardPanel.add(ateSome, constraints);
	        		}
	        		else {
	        			if (item == Item.TRASH) {
	        				animation.scoreImage(gridIndex[2], gridIndex[3], gridIndex[4], gridIndex[5], "minus", 1);
	        				birdMouse.decScoreSize();
			        		ateSome.setText("Ate Some Trash :( ");
			        		ateSome.setFont(ateFont);
			        		constraints.gridx = 1;
			        		constraints.gridy = 4;
			        		constraints.anchor = GridBagConstraints.EAST;
			        		boardPanel.add(ateSome, constraints);
		        		}
		        		
	        			else if (item == Item.EMPTY) {
			        		ateSome.setText("Nothing there ");
			        		ateSome.setFont(ateFont);
			        		constraints.gridx = 1;
			        		constraints.gridy = 4;
			        		constraints.anchor = GridBagConstraints.EAST;
			        		boardPanel.add(ateSome, constraints);
		        		}
	        			
	        			// if item was TRASH or NOTHING, triggers the hint system, finds all directions to
	        			// items surrounding the space just checked
	        			List<Direction> dirList = gameBoard.getAdjacentItemGridDirections(xIndex, yIndex);
	        			Collections.shuffle(dirList);
	        			// picks three random directions and places a question mark image there
	        			for(int count = 0; count < dirList.size() && count < 3; count++) {
	        				Direction d = dirList.get(count);
	        				// instead of adding the image based on mouse location like the holes, question marks
	        				// are added based on the index of the GridSpace where they should be placed
	        				int[] dims = indexSizePos(xIndex + gameBoard.convertXDim(d),yIndex + gameBoard.convertYDim(d));
	        				animation.addQuestionmark(dims[0], dims[1], dims[2], dims[3]);
	        			}
	        			
	        		}      		

	        		frame.validate();
		        }
		        
		    }

			@Override
			public void mouseEntered(MouseEvent arg0) {
				// TODO Auto-generated method stub
				
			}

			@Override
			public void mouseExited(MouseEvent arg0) {
				// TODO Auto-generated method stub
				
			}

			@Override
			public void mousePressed(MouseEvent arg0) {
				// TODO Auto-generated method stub
				
			}

			@Override
			public void mouseReleased(MouseEvent arg0) {
				// TODO Auto-generated method stub
				
			}
		});
		
		// finally after the massifve mouseListener the mouseMotionListener is added
		// this tracks the bird hover image
		frame.getContentPane().addMouseMotionListener(new MouseMotionListener() {

			@Override
			public void mouseDragged(MouseEvent arg0) {
				// TODO Auto-generated method stub
				
			}

			@Override
			public void mouseMoved(MouseEvent e) {
				birdMouse.setX( (int) Math.round(e.getX() + 5 - birdMouse.getYSize()/6.));
				birdMouse.setY((int) Math.round(e.getY() - birdMouse.getYSize()/1.8));
				double newBirdRatio = getSizeRatio(birdMouse.getY(), boardMouse);
				// birds size will scale with the depth of the board as it is moved vertically on the screen
				birdMouse.setSize(newBirdRatio);
				frame.repaint();
				
			}
			
		});
		
	}
	//-----------------------------------------------------------------------------------------------------------------------------------
	/**
	 * A dummy build board method to serve as the game tutorial
	 */
	void tutorial() {
		JPanel tutorialPanel = new JPanel();
		tutorialPanel.setLayout(new GridBagLayout());
		GridBagConstraints constraints = constraintFactory();
		
		constraints.gridx = 1;
		constraints.gridy = 1;
		constraints.anchor = GridBagConstraints.EAST;
		
		JButton skipButton = new JButton("Skip Tutorial");
		skipButton.setFont(new Font("Arial", Font.PLAIN, 40));
		skipButton.addActionListener((ActionEvent a)->{
			Iterator<AniObject> itrRemove = animation.getImages().iterator();
			while (itrRemove.hasNext()) {
				AniObject aniObjectRemove = itrRemove.next();
				if (aniObjectRemove.toString().equalsIgnoreCase("hole") || aniObjectRemove.toString().equalsIgnoreCase("qm")) {
					itrRemove.remove();
				}
				// skipping will set the beach image to the game play version
				if (aniObjectRemove.toString().equalsIgnoreCase("beach")) {
					aniObjectRemove.setImage(animation.getTutorialImage(5));
				}
			}
			frame.getContentPane().removeMouseListener(frame.getContentPane().getMouseListeners()[0]);
			frame.getContentPane().removeMouseMotionListener(frame.getContentPane().getMouseMotionListeners()[0]);
			// and start the game
			buildBoard();
		});
		tutorialPanel.add(skipButton, constraints, 0);
		
		constraints.gridx = 1;
		constraints.gridy = 2;
		constraints.anchor = GridBagConstraints.EAST;
		
		JLabel score = new JLabel(" ");
		score.setFont(new Font("Arial", Font.PLAIN, 40));
		tutorialPanel.add(score, constraints);
		
		constraints.gridx = 1;
		constraints.gridy = 3;
		constraints.anchor = GridBagConstraints.EAST;
		
		timerLabel.setFont(new Font("Arial", Font.PLAIN, 40));
		timerLabel.setText("EggSweeper");
		tutorialPanel.add(timerLabel, constraints);
		
//		JButton save = new JButton("Save");
//		save.setFont(new Font("Arial", Font.PLAIN, 30));
//		save.setFocusable(false);
//		save.setVisible(true);
//		boardPanel.add(save,constraints);
//		save.addActionListener((ActionEvent a)->{
//			Load.SaveGame(this);
//		});
		
		AniObject bird = null;
		AniObject board = null;
		Iterator<AniObject> boardItr = animation.getImages().iterator();
		while (boardItr.hasNext()) {
			AniObject next = boardItr.next();
			if (next.toString().equalsIgnoreCase("board")  || next.toString().equalsIgnoreCase("beach") || 
					next.toString().equalsIgnoreCase("grass")) { 
				next.setVisible(true);
			if (next.toString().equalsIgnoreCase("beach")) {
				next.setImage(animation.getTutorialImage(1));
			}
			}
			if (next.toString().equalsIgnoreCase("bird")) {
				bird = next;
			}
			if (next.toString().equalsIgnoreCase("board")) {
				board = next;
			}
		}
		
		double birdRatio = animation.screenRatio*getSizeRatio(bird.getY(), board);
		bird.setSize(birdRatio);
		Point mouseLoc = MouseInfo.getPointerInfo().getLocation();
		bird.setX((int) Math.round(mouseLoc.getX() + 10 - bird.getYSize()/4.5));
		bird.setY((int) Math.round(mouseLoc.getY() - bird.getYSize()/1.8));
		frame.repaint();
		
		final AniObject birdMouse = bird;
		final AniObject boardMouse = board;
		
		constraints.gridx = 1;
		constraints.gridy = 4;
		constraints.anchor = GridBagConstraints.EAST;
    		
		JLabel ateSomeHolder = new JLabel(" ");
		ateSomeHolder.setFont(new Font("Arial", Font.PLAIN, 40));
		tutorialPanel.add(ateSomeHolder, constraints);
		
		constraints.gridx = 1;
		constraints.gridy = 5;
		constraints.anchor = GridBagConstraints.EAST;
		JButton chestButton = new JButton();
		chestButton.setPreferredSize(new Dimension(200, 200));
		chestButton.setContentAreaFilled(false);
		chestButton.setVisible(true);
		chestButton.setBorderPainted(false);
		chestButton.addMouseMotionListener(new MouseMotionListener() {

			@Override
			public void mouseDragged(MouseEvent arg0) {
				// TODO Auto-generated method stub
				
			}

			@Override
			public void mouseMoved(MouseEvent e) {
				birdMouse.setX( (int) Math.round(e.getX() + 5 - birdMouse.getYSize()/6.) + chestButton.getX());
				birdMouse.setY((int) Math.round(e.getY() - birdMouse.getYSize()/1.8) + chestButton.getY());
				double newBirdRatio = animation.screenRatio*getSizeRatio(birdMouse.getY(), boardMouse);
				birdMouse.setSize(newBirdRatio);
				frame.repaint();
				
			}
			
		});
		chestButton.setEnabled(false);
		tutorialPanel.add(chestButton, constraints, 0);
		
		cardPanel.add(tutorialPanel, "Tutorial");
		screens.show(cardPanel, "Tutorial");
		frame.validate();
		frame.repaint();
		
		
		frame.getContentPane().addMouseListener(new MouseListener() {
		    @Override
		    public void mouseClicked(MouseEvent e) {
		    		int xLoc = e.getX();
		        int yLoc = e.getY();
		        int[] gridIndex = windowToGrid(xLoc, yLoc);
		        int xIndex = gridIndex[0];
		        int yIndex = gridIndex[1];
		        if ((xIndex == -1) || (yIndex == -1)){
		        	return;
		        }
		        else {
		        	animation.addHole(gridIndex[2], gridIndex[3], gridIndex[4], gridIndex[5]);
		        	
		        	tutorialPanel.removeAll();
		        	frame.validate();
		        	frame.repaint();
	                
		        	constraints.gridx = 1;
		    		constraints.gridy = 1;
		    		constraints.anchor = GridBagConstraints.EAST;
		    		
		    		constraints.gridx = 1;
		    		constraints.gridy = 1;
		    		constraints.anchor = GridBagConstraints.EAST;
		    		
		    		JButton skipButton = new JButton("Skip Tutorial");
		    		skipButton.setFont(new Font("Arial", Font.PLAIN, 40));
		    		skipButton.addActionListener((ActionEvent a)->{
		    			Iterator<AniObject> itrRemove = animation.getImages().iterator();
		    			while (itrRemove.hasNext()) {
		    				AniObject aniObjectRemove = itrRemove.next();
		    				if (aniObjectRemove.toString().equalsIgnoreCase("hole") || aniObjectRemove.toString().equalsIgnoreCase("qm")) {
		    					itrRemove.remove();
		    				}
		    				if (aniObjectRemove.toString().equalsIgnoreCase("beach")) {
		    					aniObjectRemove.setImage(animation.getTutorialImage(5));
		    				}
		    			}
		    			frame.getContentPane().removeMouseListener(frame.getContentPane().getMouseListeners()[0]);
		    			frame.getContentPane().removeMouseMotionListener(frame.getContentPane().getMouseMotionListeners()[0]);
		    			buildBoard();
		    		});
		    		tutorialPanel.add(skipButton, constraints, 0);
	                
	        		constraints.gridx = 1;
	        		constraints.gridy = 2;
	        		constraints.anchor = GridBagConstraints.EAST;
	        		
	        		JLabel newScore = new JLabel(" ");
	        		newScore.setFont(new Font("Arial", Font.PLAIN, 40));
	        		tutorialPanel.add(newScore, constraints);
	        		
	        		constraints.gridx = 1;
	        		constraints.gridy = 3;
	        		constraints.anchor = GridBagConstraints.EAST;

	        		timerLabel.setFont(new Font("Arial", Font.PLAIN, 40));
	        		tutorialPanel.add(timerLabel, constraints);
	        		
//	        		JButton save = new JButton("Save");
//	        		save.setFocusable(false);
//	        		save.setFont(new Font("Arial", Font.PLAIN, 30));
//	        		save.setVisible(true);
//	        		boardPanel.add(save,constraints);
//	        		save.addActionListener((ActionEvent a)->{
//	        			Load.SaveGame(thisController);
//	        		});
	        		
	        		JLabel ateSome = new JLabel(" ");
	        		Font ateFont = new Font("Arial", Font.PLAIN, 40);
	        		final JLabel ateSomeRef = ateSome;
	        		
	        		constraints.gridx = 1;
	        		constraints.gridy = 5;
	        		constraints.anchor = GridBagConstraints.EAST;
	        		
	        		JButton chestButton = new JButton();
	        		chestButton.setPreferredSize(new Dimension(200, 200));
	        		chestButton.setContentAreaFilled(false);
	        		chestButton.setBorderPainted(false);
	        		chestButton.setVisible(true);
                	chestButton.setEnabled(false);
	        		chestButton.addActionListener((ActionEvent a) -> {
	        			// questions screen is called with instructions rather than an actual question
	        			String explanation = "<html>Power up chests randomly pop up when you find eggs. This is the question screen<br/>which gives you the chance to get power ups if you answer the question correctly</html>";
	        			List<String> skips = new ArrayList<>();
	        			skips.add("Press to start game");
	        			skips.add("Press to start game");
	        			skips.add("Press to start game");
	        			skips.add("Press to start game");
	        			questionScreenTutorial(explanation, skips);
	        		});
        		
	        		chestButton.addMouseMotionListener(new MouseMotionListener() {

	        			@Override
	        			public void mouseDragged(MouseEvent arg0) {
	        				// TODO Auto-generated method stub
	        				
	        			}

	        			@Override
	        			public void mouseMoved(MouseEvent e) {
	        				birdMouse.setX( (int) Math.round(e.getX() + 5 - birdMouse.getYSize()/6.) + chestButton.getX());
	        				birdMouse.setY((int) Math.round(e.getY() - birdMouse.getYSize()/1.8) + chestButton.getY());
	        				double newBirdRatio = getSizeRatio(birdMouse.getY(), boardMouse);
	        				birdMouse.setSize(newBirdRatio);
	        				frame.repaint();
	        				
	        			}
	        			
	        		});
	        		
	        		tutorialPanel.add(chestButton, constraints);
	        		
	        		// in the first stage of the tutorial the beach image will already be set to the first stage version
	        		Item item = null;
	        		// checks to make sure the player hasn't already clicked this space
	        		if (gameBoard.getSpace(xIndex, yIndex).getItem() == Item.ALREADYCHECKED) {
	        			item = Item.ALREADYCHECKED;
	        		}
	        		// if it hasn't already been checked, tutorial proceeds
	        		else {
	        			gameBoard.setSpace(xIndex, yIndex, new GridSpace(Item.ALREADYCHECKED));
	        			// stage 1: the first GridSpace clicked in tutorial mode will always contain trash
	        			if(!tutorialFoundTrash) {
	        				Iterator<AniObject> boardItr = animation.getImages().iterator();
	        				while (boardItr.hasNext()) {
	        					AniObject next = boardItr.next();
	        					// updates the beach image to stage 2
	        					if (next.toString().equalsIgnoreCase("beach")) {
	        						next.setImage(animation.getTutorialImage(2));
	        					}
	        				}
		        			item = Item.TRASH;
		        			tutorialFoundTrash = true;
		        		} 
	        			// stage2: the second GridSpace clicked will be an egg
		        		else if(tutorialFoundTrash && !tutorialFoundEgg) {
		        			Iterator<AniObject> boardItr = animation.getImages().iterator();
	        				while (boardItr.hasNext()) {
	        					AniObject next = boardItr.next();
	        					if (next.toString().equalsIgnoreCase("beach")) {
	        						// updates beach image to stage 3
	        						next.setImage(animation.getTutorialImage(3));
	        					}
	        				}
		        			item = Item.EGG;
		        			tutorialFoundEgg = true;
		        		}
	        			// stage3: the third GridSpace clicked will also be an egg
		        		else if(tutorialFoundTrash == true && tutorialFoundEgg == true) {
		        			item = Item.EGG;
		        			Iterator<AniObject> boardItr = animation.getImages().iterator();
		        			while (boardItr.hasNext()) {
	        					AniObject next = boardItr.next();
	        					if (next.toString().equalsIgnoreCase("beach")) {
	        						// updates the beach image to stage 3
	        						next.setImage(animation.getTutorialImage(4));
	        					}
	        				}
		        			// this time the chest button is enabled, and will remain enabled until the play clicks it
		        			chestButton.setIcon(animation.getChestIcon());
		                	chestButton.setEnabled(true);
		        		}
	        		}
	        		// then handles the manually set item as normal
	        		if (item == Item.EGG) {
	        			int mult = player.getEggMultiplier();
	        			animation.scoreImage(gridIndex[2], gridIndex[3], gridIndex[4], gridIndex[5], "plus", mult);
	        			if (mult == 1) {
	        				birdMouse.incScoreSize(10);
	        			}
	        			else if (mult == 2){
	        				birdMouse.incScoreSize(20);
	        			}
		        		ateSome.setText("You Found an egg!! ");
		        		ateSome.setFont(ateFont);
		        		constraints.gridx = 1;
		        		constraints.gridy = 4;
		        		constraints.anchor = GridBagConstraints.EAST;
		        		tutorialPanel.add(ateSome, constraints);
	        		}
	        		else if (item == Item.ALREADYCHECKED) {
		        		ateSome = new JLabel("Already looked there ");
		        		ateSome.setFont(ateFont);
		        		constraints.gridx = 1;
		        		constraints.gridy = 4;
		        		constraints.anchor = GridBagConstraints.EAST;
		        		tutorialPanel.add(ateSome, constraints);
	        		}
	        		else {
	        			if (item == Item.TRASH) {
	        				animation.scoreImage(gridIndex[2], gridIndex[3], gridIndex[4], gridIndex[5], "minus", 1);
	        				birdMouse.decScoreSize();
			        		ateSome.setText("Ate Some Trash :( ");
			        		ateSome.setFont(ateFont);
			        		constraints.gridx = 1;
			        		constraints.gridy = 4;
			        		constraints.anchor = GridBagConstraints.EAST;
			        		tutorialPanel.add(ateSome, constraints);
		        		}
		        		
	        			else if (item == Item.EMPTY) {
	        				newScore.setText(" ");
			        		ateSome.setText("Nothing there ");
			        		ateSome.setFont(ateFont);
			        		constraints.gridx = 1;
			        		constraints.gridy = 4;
			        		constraints.anchor = GridBagConstraints.EAST;
			        		tutorialPanel.add(ateSome, constraints);
		        		}
	        			
	        			List<Direction> dirList = gameBoard.getAdjacentItemGridDirections(xIndex, yIndex);
	        			Collections.shuffle(dirList);
	        			for(int count = 0; count < dirList.size() && count < 3; count++) {
	        				Direction d = dirList.get(count);
	        				int[] dims = indexSizePos(xIndex + gameBoard.convertXDim(d),yIndex + gameBoard.convertYDim(d));
	        				animation.addQuestionmark(dims[0], dims[1], dims[2], dims[3]);
	        			}
	        			
	        		}      		

	        		frame.validate();
		        }
		        
		    }

			@Override
			public void mouseEntered(MouseEvent arg0) {
				// TODO Auto-generated method stub
				
			}

			@Override
			public void mouseExited(MouseEvent arg0) {
				// TODO Auto-generated method stub
				
			}

			@Override
			public void mousePressed(MouseEvent arg0) {
				// TODO Auto-generated method stub
				
			}

			@Override
			public void mouseReleased(MouseEvent arg0) {
				// TODO Auto-generated method stub
				
			}
		});
		
		frame.getContentPane().addMouseMotionListener(new MouseMotionListener() {

			@Override
			public void mouseDragged(MouseEvent arg0) {
				// TODO Auto-generated method stub
				
			}

			@Override
			public void mouseMoved(MouseEvent e) {
				birdMouse.setX( (int) Math.round(e.getX() + 5 - birdMouse.getYSize()/6.));
				birdMouse.setY((int) Math.round(e.getY() - birdMouse.getYSize()/1.8));
				double newBirdRatio = getSizeRatio(birdMouse.getY(), boardMouse);
				birdMouse.setSize(newBirdRatio);
				frame.repaint();
				
			}
			
		});
	}
	
	/**
	 * Returns a ratio corresponding to the proper size of an image with respect to the visual
	 * depth of the board at the yLoc on the screen
	 * @param yLoc the height on the screen
	 * @param boardImage the board AniObject
	 * @return a ratio corresponding to the proper size of an image with respect to the visual depth of the board at the yLoc on the screen
	 */
	static double getSizeRatio(int yLoc, AniObject boardImage) {
		double ratio;
		// these are the slopes of the vertical lines on the board grid
		double[] LineSlopes = {-0.3115, -0.2492, -0.1869, -0.1246, -0.0623, 0, 0.0623, 0.1246, 0.1869, 0.2492, 0.3115};
		// and the horizontal distance of the top of those lines from the left edge of a reference grid image which is 1000 pixels wide
		int[] LineTopX = {201, 261, 320, 380, 440, 500, 560, 620, 680, 739, 799};
		
		int imageHeight = boardImage.getY();
		
		// the smallest ratio returned will be the size of an object at the back of the board, so if yLoc
		// is above the board as it appears on the screen, yLoc is reassigned to the top of the board
		if (yLoc <= imageHeight) {
			yLoc = imageHeight;
		}
		
		// after this the distance from the top of the board to yLoc is found
		yLoc = yLoc - imageHeight;
		// and multiplied by a ratio of the reference grid image width to the actual grid image width
		yLoc = (int) Math.round(yLoc*(1000./boardImage.getXSize()));
		
		// ySize is the height of the reference image found by multiplying the height of the actual image
		// by the ratio of the reference image's width to the actual images width
		int ySize = (int) Math.round(boardImage.getYSize()*(1000./boardImage.getXSize()));
		
		// bottomXWidth is the spacing between two vertical grid lines at the very bottom of the board
		double bottomXWidth = (LineTopX[1] + (LineSlopes[1] * ySize)) - (LineTopX[0] + (LineSlopes[0] * ySize));
		// locXWidth is the spacing between 2 vertical grid lines at the yLoc
		double locXWidth = (LineTopX[1] + (LineSlopes[1] * yLoc)) - (LineTopX[0] + (LineSlopes[0] * yLoc));
		// ratio is the ratio of these widths which basically says how small the object should be compared
		// to an object at the very front of the board
		ratio = locXWidth/bottomXWidth;
		
		return ratio;
	}
	
	/**
	 * Returns an array which contains the x, y indexes of any GridSpace which is clicked, as wells as the
	 * proper x and y size and location of any image to be painted in that GridSpace
	 * @param xLoc the x location of a point of interest, usually a mouse location on a click
	 * @param yLoc the y location of a point of interest, usually a mouse location on a click
	 * @return an array containing: [x index of space, y index of space, x location of image, y location of image, x size of image, y size of image] 
	 */
	private int[] windowToGrid(int xLoc, int yLoc) {
		// as a default the indexes are set to -1, if they are -1 at the end of the method xLoc and yLoc were not
		// within the bounds of the board grid
		int xIndex = -1;
		int yIndex = -1;
		
		// first iterate to find the board object
		Iterator<AniObject> boardItr = animation.getImages().iterator();
		AniObject boardImage = null;
		while (boardItr.hasNext()) {
			boardImage = boardItr.next();
			if (boardImage.toString().equalsIgnoreCase("board")) {
				break;
			}	
		}
		
		// set a few constants based on the board grid image size
		int gridImageWidth = boardImage.getXSize();
		int imageXloc = boardImage.getX();
		int imageYloc = boardImage.getY();
		
		// these are the slopes of the vertical lines on the board grid
		double[] LineSlopes = {-0.3115, -0.2492, -0.1869, -0.1246, -0.0623, 0, 0.0623, 0.1246, 0.1869, 0.2492, 0.3115};
		// the horizontal distance of the top of those lines from the left edge of an reference grid image which is 1000 pixels wide
		int[] LineTopX = {201, 261, 320, 380, 440, 500, 560, 620, 680, 739, 799};
		// and the vertical distance of each of the horizontal lines from the top edge of the reference image
		int[] LineHeights = {0, 51, 104, 160, 219, 281, 346, 415, 488, 564, 644};
		
		// the distances from xLoc to the left edge and yLoc to the top edge of the image are found
		xLoc = xLoc - imageXloc;
		yLoc = yLoc - imageYloc;
		
		// the line heights which apply to the reference image are then scaled to the size of the actual image 
		int itrIndex = 0;
		for (int i: LineHeights) {
			i = (int) Math.round(i * gridImageWidth/1000.);
			LineHeights[itrIndex] = i;
			itrIndex++;
		}
		
		// the same for the top x's
		itrIndex = 0;
		for (int j: LineTopX) {
			j = (int) Math.round(j * gridImageWidth/1000.);
			LineTopX[itrIndex] = j;
			itrIndex++;
		}
		
		// if yLoc is above the board grid image on the screen leave the yIndex as -1
		if (yLoc < LineHeights[0]){
			// do nothing
		}
		// otherwise, figure out which row it falls within
		else {
			for(int i = 1; i < 11; i++) {
				if (yLoc < LineHeights[i]) {
					yIndex = i - 1;
					break;
				}
			}
		}
		// if a GridSpace is not found, then yLoc is below the board grid image, so leave the yIndex as -1
		
		// if xLoc is left of the board grid image on the screen leave the xIndex as -1
		if (xLoc < LineTopX[0] + LineSlopes[0] * yLoc){
			// do nothing
		}
		// otherwise, figure out which column it falls within
		else {
			for(int j = 1; j < 11; j++) {
				// slopes are needed since the columns are slanted
				// this finds the x location of each vertical line at the distance of yLoc down the image just x(y) = x(top) + slope*y
				if (xLoc < LineTopX[j] + LineSlopes[j] * yLoc) {
					xIndex = j - 1;
					break;
				}
			}
		}
		// if a GridSpace is not found, then xLoc is right of the board grid image, so leave the xIndex as -1
		
		int holeX = 1;
		int holeY = 1;
		int holeXSize = 1;
		int holeYSize = 1;
		
		// if xIndex or yIndex is -1, then the xLoc or yLoc was outside the board grid image, and no GridSpace fits it's location, so just return, otherwise:
		if ((xIndex != -1) && (yIndex != -1)){
			// this is the ratio of sizes of GridSpaces in row 8 to those in row 9
			double boxRatio = ((double) LineHeights[9] - (double) LineHeights[8])/((double) LineHeights[10] - (double) LineHeights[9]);
			// this is the scaling ratio between objects at the back of the board, and those at the yIndex on the board basically 1/(boxRatio^yIndex)
			double gridSizeRatio = 1/Math.pow(boxRatio, yIndex);
			
			// the proper y size of an image at the y index
			holeYSize = (int) Math.round(gridSizeRatio * (LineHeights[1] - LineHeights[0]));
			// the proper y location of an image over a GridSpace at this y index with respect to the original frame not the board grid image
			holeY = (int) Math.round(imageYloc + LineHeights[yIndex] + (LineHeights[yIndex+1] - LineHeights[yIndex])/2. - (holeYSize/2.));
			
			// the proper x size of an image at the x index
			holeX = (int) Math.round(imageXloc + (LineTopX[xIndex] + LineSlopes[xIndex] * (LineHeights[yIndex] + LineHeights[yIndex+1])/2.));
			// the proper x location of an image over a GridSpace at this x index with respect to the original frame not the board grid image
			holeXSize = (int) (((LineTopX[xIndex + 1]  + LineSlopes[xIndex + 1] * (LineHeights[yIndex] + LineHeights[yIndex+1])/2.) - (LineTopX[xIndex]  + (LineSlopes[xIndex] * (LineHeights[yIndex] + LineHeights[yIndex+1])/2.))));
		}
		
		int[] gridIndex = {xIndex, yIndex, holeX, holeY, holeXSize, holeYSize};
		
		return gridIndex;
	}
	
	/**
	 * Returns an array which contains the proper x and y size and location of any image to be painted in GridSpace[xIndex, yIndex]
	 * @param xIndex the x index of the GridSpace for an image to be painted in
	 * @param yIndex the y index of the GridSpace for an image to be painted in
	 * @return an array containing: [x location of image, y location of image, x size of image, y size of image] 
	 */
	public int[] indexSizePos(int xIndex, int yIndex) {
		// method works identically to windowToGrid accept that it just takes a x, y, index instead of an x, y, location on the screen
		// and only returns the proper x, y, location and size of the image to be placed at this x, y index
		double[] LineSlopes = {-0.3115, -0.2492, -0.1869, -0.1246, -0.0623, 0, 0.0623, 0.1246, 0.1869, 0.2492, 0.3115};
		int[] LineTopX = {201, 261, 320, 380, 440, 500, 560, 620, 680, 739, 799};
		int[] LineHeights = {0, 51, 104, 160, 219, 281, 346, 415, 488, 564, 644};
		
		Iterator<AniObject> boardItr = animation.getImages().iterator();
		AniObject boardImage = null;
		while (boardItr.hasNext()) {
			boardImage = boardItr.next();
			if (boardImage.toString().equalsIgnoreCase("board")) {
				break;
			}	
		}
		
		int gridImageWidth = boardImage.getXSize();
		int imageXloc = boardImage.getX();
		int imageYloc = boardImage.getY();
		
		int itrIndex = 0;
		for (int i: LineHeights) {
			i = (int) Math.round(i * gridImageWidth/1000.);
			LineHeights[itrIndex] = i;
			itrIndex++;
		}
		
		itrIndex = 0;
		for (int j: LineTopX) {
			j = (int) Math.round(j * gridImageWidth/1000.);
			LineTopX[itrIndex] = j;
			itrIndex++;
		}
		
		int newX = 1;
		int newY = 1;
		int newXSize = 1;
		int newYSize = 1;
		
		if ((xIndex != -1) && (yIndex != -1)){
			double boxRatio = ((double) LineHeights[9] - (double) LineHeights[8])/((double) LineHeights[10] - (double) LineHeights[9]);
			double gridSizeRatio = 1/Math.pow(boxRatio, yIndex);
			newYSize = (int) Math.round(gridSizeRatio * (LineHeights[1] - LineHeights[0]));
			newY = (int) Math.round(imageYloc + LineHeights[yIndex] + (LineHeights[yIndex+1] - LineHeights[yIndex])/2. - (newYSize/2.));
			
			newX = (int) Math.round(imageXloc + (LineTopX[xIndex] + LineSlopes[xIndex] * (LineHeights[yIndex] + LineHeights[yIndex+1])/2.));
			newXSize = (int) (((LineTopX[xIndex + 1]  + LineSlopes[xIndex + 1] * (LineHeights[yIndex] + LineHeights[yIndex+1])/2.) - (LineTopX[xIndex]  + (LineSlopes[xIndex] * (LineHeights[yIndex] + LineHeights[yIndex+1])/2.))));
		}
		
		int[] gridIndex = {newX, newY, newXSize, newYSize};
		
		return gridIndex;
		
	}
	
	/**
	 * Decides whether to show the winning or losing animations and initiates these animations
	 */
	public void endScreen() {
		// suppresses the gameplay images, and shuts down the listeners and timers
		System.out.println(frame.getContentPane());
		frame.getContentPane().removeMouseListener(frame.getContentPane().getMouseListeners()[0]);
		frame.getContentPane().removeMouseMotionListener(frame.getContentPane().getMouseMotionListeners()[0]);
		hideImages();
		if (this.gameBoardTimer != null) {
			this.gameBoardTimer.getTimer().stop();
		}
		this.checkTimersTimer.stop();
		
		skipRestart.setText("Resart");
		skipRestart.removeActionListener(skipRestart.getActionListeners()[0]);
		skipRestart.addActionListener((ActionEvent a)->{
			Iterator<AniObject> itr = animation.getImages().iterator();
			while (itr.hasNext()) {
				// remove all images that weren't present at the start of the game
				AniObject next = itr.next();
				if (next.toString() != "grass" && next.toString() != "board" && next.toString() != "beach") {
					itr.remove();
				}
			}
			// restart the game
			startScreen();
			tickStage = 0;
		});
		
		// checks if player won
		if (player.getScore() > pointsToWin) {
			Iterator<AniObject> itr = animation.getImages().iterator();
			// resets the size and location of the bird for the animation
			while (itr.hasNext()) {
				AniObject aniObject = itr.next();
				if (aniObject.toString().equalsIgnoreCase("bird")) {
					aniObject.setScoreSize(0);
					aniObject.setSize(1);
					aniObject.setY(300);
					aniObject.setX(600);
					aniObject.setVisible(true);
				}
			}
			animation.migrationAnimation2();
			// if player found all the eggs aniLabel has already been set.
			if (foundAllEggs == false) {
				aniLabel.setText("Your score was " + Integer.toString(player.getScore()) + ". Your Red Knot will make it to the Arctic!!!");
				aniLabel.setFont(new Font("Arial", Font.PLAIN, 50));
			}
			screens.show(cardPanel, "Blank");
			tickStage = 3;
		}
		// if the player lost
		else {
			Iterator<AniObject> itr = animation.getImages().iterator();
			while (itr.hasNext()) {
				AniObject aniObject = itr.next();
				if (aniObject.toString().equalsIgnoreCase("bird")) {
					aniObject.setScoreSize(0);
					aniObject.setSize(1);
					aniObject.setY(300);
					aniObject.setX(600);
					aniObject.setVisible(true);
				}
			}
			animation.migrationAnimation2();
			if (foundAllEggs == false) {
				aniLabel.setText("Your score was " + Integer.toString(player.getScore()) + ". Your Red Knot will not make it to the Arctic :(");
				aniLabel.setFont(new Font("Arial", Font.PLAIN, 50));
			}
			screens.show(cardPanel, "Blank");
			tickStage = 5;
		}

		//Add and repaint
		frame.validate();
		frame.repaint();
	}
	
	/**
	 * Updates image locations for animations
	 * @param animation
	 * @param controller
	 */
	public static void tick(Animation animation, Controller controller) {
		// the first migration animation
		if (controller.tickStage == 0) {
			Iterator<AniObject> itrMigration = animation.getImages().iterator();
			while (itrMigration.hasNext()) {
				AniObject aniObject = itrMigration.next();
				if (aniObject.toString().equalsIgnoreCase("bird")) {
					aniObject.setY(aniObject.getY() - 2);
					if (aniObject.getY() <= 300) {
						controller.tickStage = 1;
						break;
					}
				}
			}
		}
		// removes the Americas from the images list
		else if (controller.tickStage == 1) {
			controller.tickStage = 2;
			Iterator<AniObject> itrRemove = animation.getImages().iterator();
			while (itrRemove.hasNext()) {
				AniObject aniObjectRemove = itrRemove.next();
				if (aniObjectRemove.toString().equalsIgnoreCase("US")) {
					itrRemove.remove();
				}
			}
			controller.tutorial();
		}
		// bottle and horseshoe crab animation
		else if (controller.tickStage == 2) {
			Iterator<AniObject> scoreImageItr = animation.getImages().iterator();
			while (scoreImageItr.hasNext()) {
				AniObject scoreImage = scoreImageItr.next();
				if (scoreImage.toString().equalsIgnoreCase("plusOne") || scoreImage.toString().equalsIgnoreCase("minusOne") || scoreImage.toString().equalsIgnoreCase("plusTwo")) {
					if (scoreImage.getOrigY() - scoreImage.getY() < 30) {
						scoreImage.setY(scoreImage.getY() - 2);
					}
					else {
						scoreImage.setVisible(false);
						scoreImage.setName("done");
					}
				}
			}
		}
		// second migration animation winning
		else if (controller.tickStage == 3) {
			Iterator<AniObject> itrMigration2 = animation.getImages().iterator();
			while (itrMigration2.hasNext()) {
				AniObject aniObject = itrMigration2.next();
				if (aniObject.toString().equalsIgnoreCase("bird")) {
					aniObject.setY(aniObject.getY() - 3);
					if (aniObject.getY() <= 65) {
						Iterator<AniObject> itrRemove = animation.getImages().iterator();
						while (itrRemove.hasNext()) {
							AniObject aniObjectRemove = itrRemove.next();
							if (aniObjectRemove.toString().equalsIgnoreCase("US")) {
								itrRemove.remove();
							}
							if (aniObjectRemove.toString().equalsIgnoreCase("bird")) {
								aniObjectRemove.setSize(2);
								aniObjectRemove.setX(525);
								aniObjectRemove.setY(100);
							}
						}
						animation.layEgg();
						controller.tickStage = 4;
						break;
					}
				}
			}
		}
		// winning animation
		else if (controller.tickStage == 4) {
			
			Iterator<AniObject> itrEgg = animation.getImages().iterator();
			while (itrEgg.hasNext()) {
				AniObject egg = itrEgg.next();
				if (egg.toString().equalsIgnoreCase("egg")) {
					if (egg.getY() < 450) {
						egg.setY(egg.getY() + 3);
					}
				}
			}
		}
		// second migration animation losing
		else if (controller.tickStage == 5) {
			Iterator<AniObject> itrMigration2 = animation.getImages().iterator();
			while (itrMigration2.hasNext()) {
				AniObject aniObject = itrMigration2.next();
				if (aniObject.toString().equalsIgnoreCase("bird")) {
					aniObject.setY(aniObject.getY() - 1);
					if (aniObject.getY() <= 250) {
						Iterator<AniObject> itrRemove = animation.getImages().iterator();
						while (itrRemove.hasNext()) {
							AniObject aniObjectRemove = itrRemove.next();
							if (aniObjectRemove.toString().equalsIgnoreCase("US")) {
								itrRemove.remove();
							}
							if (aniObjectRemove.toString().equalsIgnoreCase("bird")) {
								itrRemove.remove();
							}
						}
						animation.deadBird();
						controller.tickStage = 6;
						break;
					}
				}
			}
		}
		// losing animation
		else if (controller.tickStage == 6) {
			boolean tombStoneAni = false;
			Iterator<AniObject> itrDeadBird = animation.getImages().iterator();
			while (itrDeadBird.hasNext()) {
				AniObject deadBird = itrDeadBird.next();
				if (deadBird.toString().equalsIgnoreCase("deadBird")) {
					if (animation.getFrame() - deadBird.getFirstFrame() < 100 && animation.getFrame() - deadBird.getFirstFrame() > 15) {
						tombStoneAni = true;
					}
				}
			}
			//stop
			if (tombStoneAni == true) {
				animation.tombStone();
				controller.tickStage = 7;
			}
		}
		else {
			return;
		}
	}
	
	/**
	 * Removes power ups
	 */
	private void removePowerUp() {
		powerUpLabel.setText(" ");
		PowerUps currentPowerUp = player.getCurrentPowerUp();
		player.setPowerupStatus(false);
		// undo double eggs
		player.setEggMultiplier(1);
		// clear the helper list and remove their AniObjects
		if(currentPowerUp == PowerUps.HELPER) {
			Iterator<AniObject> aniIter = animation.getImages().iterator();
			AniObject next;
			while (aniIter.hasNext()) {
				next = aniIter.next();
				if (next.toString().equalsIgnoreCase("maggie")) {
					aniIter.remove();
				}
			}
		}
		// restart the timer if it was stopped
		if(currentPowerUp == PowerUps.FREEZE) {
			gameBoardTimer.getTimer().start();
		}
		
	}
	
	/**
	 * Implements the various power ups
	 */
	private void implementPowerUp() {
		
		PowerUps currentPowerUp = player.getCurrentPowerUp();
		if(currentPowerUp == PowerUps.HELPER) {
			revealEggs();
		}
		if (currentPowerUp == PowerUps.CLEANER) {
			clearTrash();
		}
		if (currentPowerUp == PowerUps.FREEZE) {
			gameBoardTimer.getTimer().stop();
		}
		if (currentPowerUp == PowerUps.BONUS) {
			player.setEggMultiplier(2);
		}
	}
	
	/**
	 * Searches board for eggs and creates an maggie AniObject at the grid location of each egg 
	 */
	private void revealEggs() {
		int boardX = 0;
		// search the board for eggs
		for(GridSpace[] row: gameBoard.getBoard()) {
			int boardY = 0;
			for(GridSpace gridSpace: row) {
				if(gridSpace.getItem() == Item.EGG) {
					// get the proper size and shape of an image at that location
					int[] gridIndex = indexSizePos(boardX, boardY);
					// and create a maggie AniObject with these properties
					animation.addMaggie(gridIndex[0], gridIndex[1], gridIndex[2], gridIndex[3]);
				}
				boardY++;
			}
			boardX++;
		}
	}
	
	/**
	 * Searches the board for trash and clears up to 10 random pieces
	 */
	private void clearTrash() {
		int count = 0;
		int totTrash = 0;
		// first finds the total pieces of trash on the board
		for (int i = 0; i < 5; i++) {
			for (int j = 0; j < 10; j++) {
				GridSpace trySpace = gameBoard.getSpace(i,  j);
				if (trySpace.getItem() == Item.TRASH) {
					totTrash++;
				}
			}
		}
		// then removes up to 10 pieces
		while (count < 10 && count < totTrash) {
			int X = NumberManipulation.generateNum(10);
			int Y = NumberManipulation.generateNum(10);
			X = X - 1;
			Y = Y - 1;	
			GridSpace trySpace = gameBoard.getSpace(X,  Y);
			if (trySpace.getItem() == Item.TRASH) {
				trySpace.setItem(Item.ALREADYCHECKED);
				int[] gridIndex = indexSizePos(X, Y);
				animation.addHole(gridIndex[0], gridIndex[1], gridIndex[2], gridIndex[3]);
				count++;
			}
		}
	}
	
	/**
	 * Displays the question, and possible answers
	 * @param question the power up question
	 * @param possibleAns the possible answers including the correct answer
	 */
	private void questionScreen(String question, List<String> possibleAns) {
		// timer stops for questions
		gameBoardTimer.getTimer().stop();
		JPanel questionPanel = new JPanel();
		questionPanel.setLayout(new GridLayout(6,0));
		
		JLabel questionLabel = new JLabel(question);
		questionLabel.setFont(new Font("Arial", Font.PLAIN, 40));
		questionLabel.setHorizontalAlignment(JLabel.CENTER);
		
		questionPanel.add(questionLabel);
		for(String answer: possibleAns) {
			JButton possibleAnswerButton = createAnswerButton(answer);
			possibleAnswerButton.setFont(new Font("Arial", Font.PLAIN, 30));
			possibleAnswerButton.setFocusable(false);
			questionPanel.add(possibleAnswerButton);
		}
		cardPanel.add(questionPanel, "PowerUp");
		screens.show(cardPanel, "PowerUp");
		hideImages();
		frame.validate();
		frame.repaint();

	}
	
	/**
	 * Dummy question screen method for tutorial
	 * @param question the instructions for how to use the power up panel
	 * @param possibleAns "start game" message list
	 */
	private void questionScreenTutorial(String question, List<String> possibleAns) {
		JPanel questionPanel = new JPanel();
		questionPanel.setLayout(new GridLayout(6,0));
		
		JLabel questionLabel = new JLabel(question);
		questionLabel.setFont(new Font("Arial", Font.PLAIN, 40));
		questionLabel.setHorizontalAlignment(JLabel.CENTER);
		
		questionPanel.add(questionLabel);
		for(String answer: possibleAns) {
			JButton possibleAnswerButton = createAnswerButtonTutorial(answer);
			possibleAnswerButton.setFont(new Font("Arial", Font.PLAIN, 30));
			possibleAnswerButton.setFocusable(false);
			questionPanel.add(possibleAnswerButton);
		}
		cardPanel.add(questionPanel, "PowerUpTutorial");
		screens.show(cardPanel, "PowerUpTutorial");
		hideImages();
		frame.validate();
		frame.repaint();

	}
	
	/**
	 * Creates answer buttons for the power up question panels
	 * @param answer the answer message
	 * @return a JButton properly set up with action listener and the answer message
	 */
	private JButton createAnswerButton(String answer) {
		JButton possibleAnswer = new JButton(answer);
		possibleAnswer.addActionListener((ActionEvent a)->{
			JButton selectedButton = (JButton)a.getSource();
			String userAnswer = selectedButton.getText();
			// check if this button contains the right answer
			boolean playerWasCorrect = gameBoard.checkAnswer(userAnswer);
			player.setPowerupStatus(playerWasCorrect);
			// if correct add power up
			if (playerWasCorrect) {
				powerUpTimer = new PowerUpTimer();
				powerUpTimer.getTimer().start();
				implementPowerUp();
				PowerUps currentPowerUp = player.getCurrentPowerUp();
				if (currentPowerUp == PowerUps.BONUS) {
					powerUpLabel.setText("Double Eggs! ");
				}
				else if (currentPowerUp == PowerUps.CLEANER) {
					powerUpLabel.setText("Cleared Some Trash! ");
				}
				else if (currentPowerUp == PowerUps.HELPER) {
					powerUpLabel.setText("Help Finding Eggs! ");
				}
				else if (currentPowerUp == PowerUps.FREEZE) {
					powerUpLabel.setText("Timer Frozen! ");
				}
				if (currentPowerUp != PowerUps.FREEZE) {
					gameBoardTimer.getTimer().start();
				}
				// return to the board
				screens.show(cardPanel, "Board");
				showImages();
				frame.validate();
				frame.repaint();
			// if wrong create and display correct answer panel
			} else {
				JPanel correctAnswerPanel = setUpCorrectAnswerPanel();
				cardPanel.add(correctAnswerPanel, "correctAnswer");
				screens.show(cardPanel, "correctAnswer");
				frame.revalidate();
				frame.repaint();
			}
			
		});
		return possibleAnswer;
	}
	
	/**
	 * Creates dummy "start game" answer buttons for tutorial which just clear the tutorial images, and start the real game
	 * @param answer
	 * @return
	 */
	private JButton createAnswerButtonTutorial(String answer) {
		JButton possibleAnswer = new JButton(answer);
		possibleAnswer.addActionListener((ActionEvent a)->{
			// clear the tutorial holes, question marks, and listeners
			Iterator<AniObject> boardItr = animation.getImages().iterator();
			while (boardItr.hasNext()) {
				AniObject next = boardItr.next();
				if (next.toString().equalsIgnoreCase("beach")) {
					next.setImage(animation.getTutorialImage(5));
				}
				if (next.toString().equalsIgnoreCase("hole") || next.toString().equalsIgnoreCase("qm")) {
					boardItr.remove();
				}
			}
			frame.getContentPane().removeMouseListener(frame.getContentPane().getMouseListeners()[0]);
			frame.getContentPane().removeMouseMotionListener(frame.getContentPane().getMouseMotionListeners()[0]);
			// start the actual game
			showImages();
			player = new Player();
			gameBoard = new Board(gameBoard.getDifficulty());
			buildBoard();
			
		});
		return possibleAnswer;
	}
	
	/**
	 * Displays the correct answer and an ok button to continue with the game
	 * @return
	 */
	private JPanel setUpCorrectAnswerPanel() {
		JPanel correctAnswerPanel = new JPanel();
		correctAnswerPanel.setLayout(new GridLayout(3,1));
		JLabel title = new JLabel("The Correct Answer Was");
		title.setFont(new Font("Arial", Font.PLAIN, 60));
		title.setHorizontalAlignment(JLabel.CENTER);
		correctAnswerPanel.add(title);
		JLabel explanation = new JLabel(gameBoard.getAnswer());
		explanation.setFont(new Font("Arial", Font.PLAIN, 40));
		explanation.setHorizontalAlignment(JLabel.CENTER);
		correctAnswerPanel.add(explanation);
		JButton okButton = new JButton("Ok");
		okButton.setFont(new Font("Arial", Font.PLAIN, 60));
		okButton.addActionListener((ActionEvent e)->{
			screens.show(cardPanel, "Board");
			showImages();
			gameBoardTimer.getTimer().start();
			frame.validate();
			frame.repaint();		
		});
		correctAnswerPanel.add(okButton);
		return correctAnswerPanel;
	}
	
	/**
	 * Shows all images accept those whose names have been set to "done"
	 */
	private void showImages() {
		for(AniObject object: animation.getImages()) {
			if (!object.toString().equalsIgnoreCase("done")) {
				object.setVisible(true);
			}
		}
	}
	
	/**
	 * Hides all images by setting visibility to false
	 */
	private void hideImages() {
		for(AniObject object: animation.getImages()) {
			object.setVisible(false);
		}
	}
	
	/**
	 * Checks if either timer has run out of time
	 */
	private void checkTimers() {
		if(powerUpTimer != null) {
			boolean timeElapsed = powerUpTimer.isTimesUp();
			if(timeElapsed) {
				this.powerUpTimer.getTimer().stop();
				this.powerUpTimer = null;
				removePowerUp();
			}
		}
		if(gameBoardTimer != null) {
			boolean timeElapsed = gameBoardTimer.isTimesUp();
			if(timeElapsed) {
				this.gameBoardTimer = null;
				this.checkTimersTimer.stop();
				this.endScreen();
			}
		}
		
	}
	
	// The main method
	public static void main(String[] args) {
		
		// make the controller
		Controller cont = new Controller();
		
		// make the frame
       	cont.frame = new JFrame();
       	cont.frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
       	cont.frame.setExtendedState(JFrame.MAXIMIZED_BOTH); 
		cont.frame.setUndecorated(true);
		
		// make the animiation
	  	cont.animation = new Animation();
	  	cont.animation.setVisible(true);
	  	
	  	// set the animation in the frame
	  	cont.frame.add(cont.animation);
	  	cont.frame.pack();
	  	cont.frame.setVisible(true);
	  	
	  	// make the cardPanel and set to CardLayout
	  	cont.cardPanel = new JPanel();
	  	cont.cardPanel.setVisible(true);
	  	cont.screens = new CardLayout();
	  	cont.cardPanel.setLayout(cont.screens);
	  	
	  	
	  	
	  	// start the game
	  	cont.startScreen();
	  	
	  	// run the animations
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
	
	/**
	 * Call the checkTimers method each second to see if timers have run out
	 */
	@Override
	public void actionPerformed(ActionEvent e) {
		if(gameBoardTimer != null) {
			timerLabel.setText("Time: " + Integer.toString(gameBoardTimer.getTimeRemaining()/1000) + " ");
		}
		this.checkTimers();
	}
	
}