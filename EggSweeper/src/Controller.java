import java.awt.CardLayout;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.MouseInfo;
import java.awt.Point;
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
import enums.Bird;
import enums.Difficulty;
import enums.Direction;
import enums.Item;
import enums.PowerUps;
import powerUpModels.Helper;

// The Controller

public class Controller implements Serializable, ActionListener {
	
	// The Model
	Player player;
	Board gameBoard;
	PowerUpTimer powerUpTimer;
	GameBoardTimer gameBoardTimer;
	Timer checkTimersTimer;
	Board dummyBoard;
	
	// The View
	JFrame frame;
	Animation animation;
	CardLayout screens;
	JPanel cardPanel;
	Controller thisController;
	
	// Constant for tick method
	private int tickStage = 0;
	JLabel powerUpLabel = new JLabel("<html>Help the hungry bird&nbsp<br/>find some Eggs!</html>");
	JLabel aniLabel = new JLabel("A Red Knot migrating from South America to the Arctic stops in the Delaware Bay to refuel on Horseshoe Crab eggs. ");
	JLabel timerLabel = new JLabel("");
	List<Helper> helpers;
	boolean tutorialFoundTrash = false;
	boolean tutorialFoundEgg = false;
	boolean finishedTutorial = false;
	
	
	private GridBagConstraints constraintFactory() {
		GridBagConstraints constraints = new GridBagConstraints();
		
		//The constraints are defined every time you add an element
		//Grid x and grid y are the positions the component starts at
		constraints.gridx = 0;
		constraints.gridy = 0;
		//Grid width and grid height define how many spaces the component takes up
		constraints.gridwidth = 1;
		constraints.gridheight = 1;
		//Something about a hint on how the components can fit into place
		constraints.weightx = 1.0;
		constraints.weighty = 1.0;
		//More settings
		constraints.anchor = GridBagConstraints.CENTER;
		constraints.fill = GridBagConstraints.NONE;
		return constraints;
	}
	// displays start button
	public void startScreen() {
		checkTimersTimer = new Timer(1000, this);
		checkTimersTimer.start();
		//Declare a new JPanel
		JPanel startPanel = new JSIPanel();
		//Set its layout manager to GridBag
		startPanel.setLayout(new GridBagLayout());
		
		
		
		
//		//The constraints describe each new component's location
//		GridBagConstraints constraints = constraintFactory();
//		//The component will render in the 3rd column of the first row
//		constraints.gridx = 1;
//		constraints.gridy = 1;
//		
//		JLabel holder1 = new JLabel(" ");
//		startPanel.add(holder1, constraints, 0);
//		
//		constraints.gridy = 2;
//		
//		JLabel title = new JLabel("EGG SWEEPER");
//		title.setFont(new Font("Arial", Font.PLAIN, 80));
//		
//		//Add components to the start panel instead of the frame's contentPane directly
//		startPanel.add(title, constraints, 0);
//		
//		constraints.gridy = 1;
//		constraints.gridheight = 5;
//		JLabel backgroundImage = new JLabel(new ImageIcon("images/homeBackground169.png"));
//		startPanel.add(backgroundImage, constraints, 1);
//		
//		constraints.gridheight = 1;
//		
//		JButton startButton = new JButton("Start Game");
//		JButton instButton = new JButton("Instructions");
//		startButton.setFont(new Font("Arial", Font.PLAIN, 30));
//		instButton.setFont(new Font("Arial", Font.PLAIN, 30));
//		startButton.setVisible(true);instButton.setVisible(true);
//		
//		//This component will be in the same column, just 3 rows below
//		constraints.gridy = 3;
//		startPanel.add(startButton,constraints, 1);
//		constraints.gridy = 4;
//		startPanel.add(instButton,constraints, 1);
//		
//		constraints.gridy = 5;
//		JLabel holder2 = new JLabel(" ");
//		startPanel.add(holder2, constraints);
		
		
		
		//The constraints describe each new component's location
		GridBagConstraints constraints = constraintFactory();
		//The component will render in the 3rd column of the first row
		constraints.gridx = 1;
		constraints.gridy = 0;
		
		JLabel holder1 = new JLabel(" ");
		startPanel.add(holder1, constraints, 0);
		
		constraints.gridx = 1;
		constraints.gridy = 1;
		
		JLabel holder2 = new JLabel(" ");
		startPanel.add(holder2, constraints, 0);
		
		constraints.gridy = 2;
		
		JLabel title = new JLabel("EGG SWEEPER");
		title.setFont(new Font("Arial", Font.PLAIN, 80));
		
		//Add components to the start panel instead of the frame's contentPane directly
		startPanel.add(title, constraints, 0);
		
		constraints.gridy = 0;
		constraints.gridheight = 7;
		
		ImageIcon background = new ImageIcon("images/homeBackground.png");
		Image backImage = background.getImage();
		Image resizedBack = backImage.getScaledInstance( frame.getWidth(), frame.getHeight(),  java.awt.Image.SCALE_SMOOTH ) ; 
		JLabel backgroundImage = new JLabel(new ImageIcon(resizedBack));
		startPanel.add(backgroundImage, constraints, 1);
		
		constraints.gridheight = 1;
		
		JButton startButton = new JButton("Start Game");
		JButton instButton = new JButton("Instructions");
		startButton.setFont(new Font("Arial", Font.PLAIN, 30));
		instButton.setFont(new Font("Arial", Font.PLAIN, 30));
		startButton.setVisible(true);instButton.setVisible(true);
		
		//This component will be in the same column, just 3 rows below
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

//		constraints.gridy = 5;
//		JButton loadButton = new JButton("Load");
//		loadButton.setFont(new Font("Arial", Font.PLAIN, 30));
//		startPanel.add(loadButton,constraints);
//		loadButton.addActionListener((ActionEvent e)-> {
//				Load.LoadGame();
//				//find correct method so that it keeps ticking
//
//			}
//		);
		
		//Adds the start screen to the deck
		cardPanel.add(startPanel, "Start");
		screens.show(cardPanel, "Start");
		//adds a blank screen to the deck
		JPanel blankScreen = new JPanel();
		blankScreen.setLayout(new GridLayout(10,0));
		aniLabel.setFont(new Font("Arial", Font.PLAIN, 30));
		aniLabel.setVerticalAlignment(JLabel.CENTER);
		aniLabel.setHorizontalAlignment(JLabel.CENTER);
		
		blankScreen.add(aniLabel);
    	cardPanel.add(blankScreen, "Blank");

		frame.add(cardPanel);
		frame.validate();
		frame.repaint();

	}
	
	public void DisplayInstructions(){
		JPanel instructionPanel = new JSIPanel();
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
		
		ImageIcon i = new ImageIcon("images/instructions.png");
		Image image = i.getImage();
		Image newimg = image.getScaledInstance(frame.getWidth()/2, frame.getHeight()/2, Image.SCALE_SMOOTH);
		i = new ImageIcon(newimg);
		JLabel instructions = new JLabel();
		instructions.setIcon(i);
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
	// displays easy, medium and hard button
	public void pickDifficulty() {
		Iterator<AniObject> itrRemove = animation.getImages().iterator();
		while (itrRemove.hasNext()) {
			AniObject aniObjectRemove = itrRemove.next();
			if (aniObjectRemove.toString().compareToIgnoreCase("HB") == 0) {
				itrRemove.remove();
			}
		}
		JPanel difficultyPanel = new JSIPanel();
		difficultyPanel.setLayout(new GridBagLayout());
		
		
		
//		GridBagConstraints constraints = constraintFactory();
//		constraints.gridx = 1;
//		constraints.weightx = 50;
//		constraints.weighty = 50;
//		
//		JLabel holder1 = new JLabel(" ");
//		
//		JLabel pickDiff = new JLabel("Pick a Difficulty");
//		pickDiff.setFont(new Font("Arial", Font.PLAIN, 50));
//		
//		JButton easyButton = new JButton("Easy");
//		easyButton.setFont(new Font("Arial", Font.PLAIN, 30));
//		easyButton.setVisible(true);
//		
//		JButton mediumButton = new JButton("Medium");
//		mediumButton.setFont(new Font("Arial", Font.PLAIN, 30));
//		mediumButton.setVisible(true);
//		
//		JButton hardButton = new JButton("Hard");
//		hardButton.setFont(new Font("Arial", Font.PLAIN, 30));
//		hardButton.setVisible(true);
//		
//		JLabel holder2 = new JLabel(" ");
//		
//		constraints.gridy = 0;
//		
//		difficultyPanel.add(holder1, constraints, 0);
//		constraints.gridy = 1;
//		difficultyPanel.add(pickDiff,constraints, 0);
//		
//		constraints.gridy = 0;
//		constraints.gridheight = 6;
//		JLabel backgroundImage = new JLabel(new ImageIcon("images/homeBackground169.png"));
//		difficultyPanel.add(backgroundImage, constraints, 1);
//		
//		constraints.gridheight = 1;
//		
//		constraints.gridy = 2;
//		//After a button is added add the width
//		difficultyPanel.add(easyButton,constraints, 1);
//		constraints.gridy = 3;
//		difficultyPanel.add(mediumButton,constraints, 1);
//		constraints.gridy = 4;
//		difficultyPanel.add(hardButton,constraints, 1);
//		constraints.gridy = 5;
//		difficultyPanel.add(holder2, constraints, 1);
		
		
		
		GridBagConstraints constraints = constraintFactory();
		constraints.gridx = 1;
		constraints.weightx = 50;
		constraints.weighty = 50;
		
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
		ImageIcon background = new ImageIcon("images/homeBackground.png");
		Image backImage = background.getImage();
		Image resizedBack = backImage.getScaledInstance(frame.getWidth(), frame.getHeight(),  java.awt.Image.SCALE_SMOOTH ) ; 
		JLabel backgroundImage = new JLabel(new ImageIcon(resizedBack));
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
		easyButton.addActionListener((ActionEvent e)->{

	        	//Switches to the blank screen in the deck
	        	screens.show(cardPanel, "Blank");
	        	frame.getContentPane().revalidate();
	        	frame.getContentPane().repaint();
        		// when clicked picks character and difficulty
        		gameBoard = new Board(Difficulty.EASY);
        		dummyBoard = new Board(Difficulty.EASY);
        		player = new Player(Bird.DUNLIN);
        		animation.migrationAnimation();
	        }
	    );
		
		mediumButton.addActionListener((ActionEvent e)->{
	        	screens.show(cardPanel, "Blank");
	        	frame.getContentPane().revalidate();
	        	frame.getContentPane().repaint();
	        	gameBoard = new Board(Difficulty.MEDIUM);
	        	dummyBoard = new Board(Difficulty.MEDIUM);
	        	player = new Player(Bird.SANDPIPER); 
	        	animation.migrationAnimation();
	        }
	    );
		
		hardButton.addActionListener((ActionEvent e)->{
	        		
	        	screens.show(cardPanel, "Blank");
	        	frame.getContentPane().revalidate();
	        	frame.getContentPane().repaint();
	        	gameBoard = new Board(Difficulty.HARD);
	        	dummyBoard = new Board(Difficulty.HARD);
	        	player = new Player(Bird.REDKNOT);
	        	animation.migrationAnimation();
	        }
	    );
	}
	
	public void buildBoard() {
		
		gameBoardTimer = new GameBoardTimer();
		gameBoardTimer.getTimer().start();
		timerLabel.setText("Time: " + Integer.toString(gameBoardTimer.getTimeRemaining()/1000) + " ");
		JPanel boardPanel = new JPanel();
		boardPanel.setLayout(new GridBagLayout());
		GridBagConstraints constraints = constraintFactory();
		
		constraints.gridx = 1;
		constraints.gridy = 1;
		constraints.anchor = GridBagConstraints.EAST;
		
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
		
		AniObject bird = null;
		AniObject board = null;
		Iterator<AniObject> boardItr = animation.getImages().iterator();
		while (boardItr.hasNext()) {
			AniObject next = boardItr.next();
			if (next.toString().compareToIgnoreCase("board") == 0  || next.toString().compareToIgnoreCase("beach") == 0 || 
					next.toString().compareToIgnoreCase("grass1") == 0 || next.toString().compareToIgnoreCase("grass2") == 0 ||
					next.toString().compareToIgnoreCase("grass3") == 0 || next.toString().compareToIgnoreCase("grass4") == 0 || 
					next.toString().compareToIgnoreCase("grass5") == 0 || next.toString().compareToIgnoreCase("grass6") == 0 || 
					next.toString().compareToIgnoreCase("grass7") == 0 || next.toString().compareToIgnoreCase("grass8") == 0 || 
					next.toString().compareToIgnoreCase("grass9") == 0 || next.toString().compareToIgnoreCase("grass10") == 0) { 
				next.setVisible(true);
			}
			if (next.toString().compareToIgnoreCase("bird") == 0) {
				bird = next;
			}
			if (next.toString().compareToIgnoreCase("board") == 0) {
				board = next;
			}
		}
		
		double birdRatio = getSizeRatio(bird.getY(), board);
		bird.setSize(birdRatio);
		Point mouseLoc = MouseInfo.getPointerInfo().getLocation();
		bird.setX((int) Math.round(mouseLoc.getX() + 3 - bird.getYSize()/4.5));
		bird.setY((int) Math.round(mouseLoc.getY() - 31 - bird.getYSize()/1.8));
		
		final AniObject birdMouse = bird;
		final AniObject boardMouse = board;
		
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
				double newBirdRatio = getSizeRatio(birdMouse.getY(), boardMouse);
				birdMouse.setSize(newBirdRatio);
				frame.repaint();
				
			}
			
		});
		chestButton.setEnabled(false);
		boardPanel.add(chestButton, constraints, 0);
		
		cardPanel.add(boardPanel, "Board");
		screens.show(cardPanel, "Board");
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
		        	Item item = player.checkSpace(xIndex, yIndex, gameBoard);
		        	animation.addHole(gridIndex[2], gridIndex[3], gridIndex[4], gridIndex[5]);
		        	
		        	boardPanel.removeAll();
		        	frame.validate();
		        	frame.repaint();
	                
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
	        		
	        		chestButton.addActionListener((ActionEvent a)->{
	        	        		
	        	        	String question;
	        	        	try {		
								question = gameBoard.getPowerupQuestion();
								List<String> possibleAns = gameBoard.getPossibleAnswers();
								System.out.println("---------------------------------");
								Collections.shuffle(possibleAns);
								System.out.println(possibleAns);
								chestButton.setEnabled(false);
								ateSomeRef.setText(" ");
								questionScreen(question, possibleAns);
								
							} catch (FileNotFoundException e1) {
								// TODO Auto-generated catch block
								e1.printStackTrace();
								
							}
	        	        	
	        	        }
	        	    );
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
	        		
	        		boardPanel.add(chestButton, constraints);
	        		
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
		        		frame.getContentPane().add(ateSome, 0);
		        		constraints.gridx = 1;
		        		constraints.gridy = 4;
		        		constraints.anchor = GridBagConstraints.EAST;
		        		boardPanel.add(ateSome, constraints);
		        		if (NumberManipulation.generateNum(10) < 6 && player.hasPowerUp() == false) {
		        			chestButton.setIcon(animation.getChestIcon());
		                	chestButton.setEnabled(true);
		        		}
		        		System.out.println(player.getEggsFound());
		        		System.out.println(gameBoard.getTotEggs());
		        		if (player.getEggsFound() == gameBoard.getTotEggs()) {
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
	        			
	        			List<Direction> dirList = gameBoard.getAdjacentItemGridDirections(xIndex, yIndex);
	        			Collections.shuffle(dirList);
	        			for(int count = 0; count < dirList.size() && count < 3; count++) {
	        				Direction d = dirList.get(count);
	        				System.out.println(String.format("Item at Direction:%s", d.name()));
	        				String loc_disp = String.format("This Location: (%d,%d) That Location: (%d,%d)", xIndex, yIndex, xIndex + gameBoard.convertXDim(d), yIndex + gameBoard.convertYDim(d));
	        				System.out.println(loc_disp);
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
	//-----------------------------------------------------------------------------------------------------------------------------------
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
				if (aniObjectRemove.toString().compareToIgnoreCase("hole") == 0 || aniObjectRemove.toString().compareToIgnoreCase("qm") == 0) {
					itrRemove.remove();
				}
				if (aniObjectRemove.toString().compareToIgnoreCase("beach") == 0) {
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
			if (next.toString().compareToIgnoreCase("board") == 0  || next.toString().compareToIgnoreCase("beach") == 0 || 
					next.toString().compareToIgnoreCase("grass1") == 0 || next.toString().compareToIgnoreCase("grass2") == 0 ||
					next.toString().compareToIgnoreCase("grass3") == 0 || next.toString().compareToIgnoreCase("grass4") == 0 || 
					next.toString().compareToIgnoreCase("grass5") == 0 || next.toString().compareToIgnoreCase("grass6") == 0 || 
					next.toString().compareToIgnoreCase("grass7") == 0 || next.toString().compareToIgnoreCase("grass8") == 0 || 
					next.toString().compareToIgnoreCase("grass9") == 0 || next.toString().compareToIgnoreCase("grass10") == 0) { 
				next.setVisible(true);
			if (next.toString().compareToIgnoreCase("beach") == 0) {
				next.setImage(animation.getTutorialImage(1));
			}
			}
			if (next.toString().compareToIgnoreCase("bird") == 0) {
				bird = next;
			}
			if (next.toString().compareToIgnoreCase("board") == 0) {
				board = next;
			}
		}
		
		double birdRatio = getSizeRatio(bird.getY(), board);
		bird.setSize(birdRatio);
		Point mouseLoc = MouseInfo.getPointerInfo().getLocation();
		bird.setX((int) Math.round(mouseLoc.getX() + 3 - bird.getYSize()/4.5));
		bird.setY((int) Math.round(mouseLoc.getY() - 31 - bird.getYSize()/1.8));
		
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
				double newBirdRatio = getSizeRatio(birdMouse.getY(), boardMouse);
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
		    				if (aniObjectRemove.toString().compareToIgnoreCase("hole") == 0 || aniObjectRemove.toString().compareToIgnoreCase("qm") == 0) {
		    					itrRemove.remove();
		    				}
		    				if (aniObjectRemove.toString().compareToIgnoreCase("beach") == 0) {
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
	        		//chestButton.setIcon(animation.getTransChestIcon());
                	chestButton.setEnabled(false);
	        		chestButton.addActionListener((ActionEvent a) -> {
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
	        		
	        		Item item = null;
	        		if (gameBoard.getSpace(xIndex, yIndex).getItem() == Item.ALREADYCHECKED) {
	        			item = Item.ALREADYCHECKED;
	        		}
	        		else {
	        			gameBoard.setSpace(xIndex, yIndex, new GridSpace(Item.ALREADYCHECKED));
	        			if(!tutorialFoundTrash) {
	        				Iterator<AniObject> boardItr = animation.getImages().iterator();
	        				while (boardItr.hasNext()) {
	        					AniObject next = boardItr.next();
	        					if (next.toString().compareToIgnoreCase("beach") == 0) {
	        						next.setImage(animation.getTutorialImage(2));
	        					}
	        				}
		        			item = Item.TRASH;
		        			tutorialFoundTrash = true;
		        		} 
		        		else if(tutorialFoundTrash == true && tutorialFoundEgg == true) {
		        			item = Item.EGG;
		        			Iterator<AniObject> boardItr = animation.getImages().iterator();
		        			while (boardItr.hasNext()) {
	        					AniObject next = boardItr.next();
	        					if (next.toString().compareToIgnoreCase("beach") == 0) {
	        						next.setImage(animation.getTutorialImage(4));
	        					}
	        				}
		        			chestButton.setIcon(animation.getChestIcon());
		                	chestButton.setEnabled(true);
		        		}
		        		else if(tutorialFoundTrash && !tutorialFoundEgg) {
		        			Iterator<AniObject> boardItr = animation.getImages().iterator();
	        				while (boardItr.hasNext()) {
	        					AniObject next = boardItr.next();
	        					if (next.toString().compareToIgnoreCase("beach") == 0) {
	        						next.setImage(animation.getTutorialImage(3));
	        					}
	        				}
		        			item = Item.EGG;
		        			tutorialFoundEgg = true;
		        		}
	        		}
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
	        				System.out.println(String.format("Item at Direction:%s", d.name()));
	        				String loc_disp = String.format("This Location: (%d,%d) That Location: (%d,%d)", xIndex, yIndex, xIndex + gameBoard.convertXDim(d), yIndex + gameBoard.convertYDim(d));
	        				System.out.println(loc_disp);
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
	
	static double getSizeRatio(int yLoc, AniObject boardImage) {
		double ratio;
		double[] LineSlopes = {-0.3115, -0.2492, -0.1869, -0.1246, -0.0623, 0, 0.0623, 0.1246, 0.1869, 0.2492, 0.3115};
		int[] LineTopX = {201, 261, 320, 380, 440, 500, 560, 620, 680, 739, 799};
		
		if (yLoc <= boardImage.getY()) {
			yLoc = boardImage.getY();
		}
		
		yLoc = yLoc - boardImage.getY();
		yLoc = (int) Math.round(yLoc*(1000./boardImage.getXSize()));
		
		double bottomXWidth = (LineTopX[1] + (LineSlopes[1] * boardImage.getYSize())) - (LineTopX[0] + (LineSlopes[0] * boardImage.getYSize()));
		double locXWidth = (LineTopX[1] + (LineSlopes[1] * yLoc)) - (LineTopX[0] + (LineSlopes[0] * yLoc));
		
		ratio = locXWidth/bottomXWidth;
		
		return ratio;
	}
	
	private int[] windowToGrid(int xLoc, int yLoc) {
		int xIndex;
		int yIndex;
		
		Iterator<AniObject> boardItr = animation.getImages().iterator();
		AniObject boardImage = null;
		while (boardItr.hasNext()) {
			boardImage = boardItr.next();
			if (boardImage.toString().compareToIgnoreCase("board") == 0) {
				break;
			}	
		}
		
		int gridImageWidth = boardImage.getXSize();
		int imageXloc = boardImage.getX();
		int imageYloc = boardImage.getY();
		
		double[] LineSlopes = {-0.3115, -0.2492, -0.1869, -0.1246, -0.0623, 0, 0.0623, 0.1246, 0.1869, 0.2492, 0.3115};
		int[] LineTopX = {201, 261, 320, 380, 440, 500, 560, 620, 680, 739, 799};
		int[] LineHeights = {0, 51, 104, 160, 219, 281, 346, 415, 488, 564, 644};
		
		xLoc = xLoc - imageXloc;
		yLoc = yLoc - imageYloc;
		
		xLoc = (int) Math.round(xLoc*(1000./gridImageWidth));
		yLoc = (int) Math.round(yLoc*(1000./gridImageWidth));
		
		if (yLoc < LineHeights[0]){
			yIndex = -1;
		}
		else if (yLoc < LineHeights[1]){
			yIndex = 0;
		}
		else if (yLoc < LineHeights[2]){
			yIndex = 1;
		}
		else if (yLoc < LineHeights[3]){
			yIndex = 2;
		}
		else if (yLoc < LineHeights[4]){
			yIndex = 3;
		}
		else if (yLoc < LineHeights[5]){
			yIndex = 4;
		}
		else if (yLoc < LineHeights[6]){
			yIndex = 5;
		}
		else if (yLoc < LineHeights[7]){
			yIndex = 6;
		}
		else if (yLoc < LineHeights[8]){
			yIndex = 7;
		}
		else if (yLoc < LineHeights[9]){
			yIndex = 8;
		}
		else if (yLoc < LineHeights[10]){
			yIndex = 9;
		}
		else {
			yIndex = -1;
		}
		
		if (xLoc < LineTopX[0] + LineSlopes[0] * yLoc){
			xIndex = -1;
		}
		else if (xLoc < LineTopX[1] + LineSlopes[1] * yLoc){
			xIndex = 0;
		}
		else if (xLoc < LineTopX[2] + LineSlopes[2] * yLoc){
			xIndex = 1;
		}
		else if (xLoc < LineTopX[3] + LineSlopes[3] * yLoc){
			xIndex = 2;
		}
		else if (xLoc < LineTopX[4] + LineSlopes[4] * yLoc){
			xIndex = 3;
		}
		else if (xLoc < LineTopX[5] + LineSlopes[5] * yLoc){
			xIndex = 4;
		}
		else if (xLoc < LineTopX[6] + LineSlopes[6] * yLoc){
			xIndex = 5;
		}
		else if (xLoc < LineTopX[7] + LineSlopes[7] * yLoc){
			xIndex = 6;
		}
		else if (xLoc < LineTopX[8] + LineSlopes[8] * yLoc){
			xIndex = 7;
		}
		else if (xLoc < LineTopX[9] + LineSlopes[9] * yLoc){
			xIndex = 8;
		}
		else if (xLoc < LineTopX[10] + LineSlopes[10] * yLoc){
			xIndex = 9;
		}
		else {
			xIndex = -1;
		}
		
		int holeX = 1;
		int holeY = 1;
		int holeXSize = 1;
		int holeYSize = 1;
		
		if ((xIndex != -1) && (yIndex != -1)){
			double boxRatio = ((double) LineHeights[9] - (double) LineHeights[8])/((double) LineHeights[10] - (double) LineHeights[9]);
			double gridSizeRatio = 1/Math.pow(boxRatio, yIndex);
			holeYSize = (int) Math.round(gridSizeRatio * (gridImageWidth/1000)*(LineHeights[1] - LineHeights[0]));
			holeY = (int) Math.round(imageYloc + LineHeights[yIndex] + ((gridImageWidth/1000.)*(LineHeights[yIndex+1] - LineHeights[yIndex])/2.) - (holeYSize/2.) );
			
			holeX = (int) Math.round(imageXloc + (gridImageWidth/1000)*(LineTopX[xIndex] + LineSlopes[xIndex] * (LineHeights[yIndex] + LineHeights[yIndex+1])/2.));
			holeXSize = (int) ((gridImageWidth/1000)*((LineTopX[xIndex + 1]  + (LineSlopes[xIndex + 1] * (LineHeights[yIndex] + LineHeights[yIndex+1])/2.)) - (LineTopX[xIndex]  + (LineSlopes[xIndex] * (LineHeights[yIndex] + LineHeights[yIndex+1])/2.))));
		}
		
		int[] gridIndex = {xIndex, yIndex, holeX, holeY, holeXSize, holeYSize};
		
		return gridIndex;
	}
	
	public int[] indexSizePos(int xIndex, int yIndex) {
		double[] LineSlopes = {-0.3115, -0.2492, -0.1869, -0.1246, -0.0623, 0, 0.0623, 0.1246, 0.1869, 0.2492, 0.3115};
		int[] LineTopX = {201, 261, 320, 380, 440, 500, 560, 620, 680, 739, 799};
		int[] LineHeights = {0, 51, 104, 160, 219, 281, 346, 415, 488, 564, 644};
		
		Iterator<AniObject> boardItr = animation.getImages().iterator();
		AniObject boardImage = null;
		while (boardItr.hasNext()) {
			boardImage = boardItr.next();
			if (boardImage.toString().compareToIgnoreCase("board") == 0) {
				break;
			}	
		}
		
		int gridImageWidth = boardImage.getXSize();
		int imageXloc = boardImage.getX();
		int imageYloc = boardImage.getY();
		
		int newX = 1;
		int newY = 1;
		int newXSize = 1;
		int newYSize = 1;
		
		if ((xIndex != -1) && (yIndex != -1)){
			double boxRatio = ((double) LineHeights[9] - (double) LineHeights[8])/((double) LineHeights[10] - (double) LineHeights[9]);
			double gridSizeRatio = 1/Math.pow(boxRatio, yIndex);
			newYSize = (int) Math.round(gridSizeRatio * (gridImageWidth/1000)*(LineHeights[1] - LineHeights[0]));
			newY = (int) Math.round(imageYloc + LineHeights[yIndex] + ((gridImageWidth/1000.)*(LineHeights[yIndex+1] - LineHeights[yIndex])/2.) - (newYSize/2.) );
			
			newX = (int) Math.round(imageXloc + (gridImageWidth/1000)*(LineTopX[xIndex] + LineSlopes[xIndex] * (LineHeights[yIndex] + LineHeights[yIndex+1])/2.));
			newXSize = (int) ((gridImageWidth/1000)*((LineTopX[xIndex + 1]  + (LineSlopes[xIndex + 1] * (LineHeights[yIndex] + LineHeights[yIndex+1])/2.)) - (LineTopX[xIndex]  + (LineSlopes[xIndex] * (LineHeights[yIndex] + LineHeights[yIndex+1])/2.))));
		}
		
		int[] gridIndex = {newX, newY, newXSize, newYSize};
		
		return gridIndex;
		
	}
	
	// displays score, and a quit button
	public void endScreen() {
		
		frame.getContentPane().removeMouseListener(frame.getContentPane().getMouseListeners()[0]);
		frame.getContentPane().removeMouseMotionListener(frame.getContentPane().getMouseMotionListeners()[0]);
		
		hideImages();
		if (this.gameBoardTimer != null) {
			this.gameBoardTimer.getTimer().stop();
		}
		this.checkTimersTimer.stop();
		
		JButton quitButton = new JButton("Quit");
		quitButton.setFont(new Font("Arial", Font.PLAIN, 30));

		quitButton.addActionListener(new ActionListener(){
	        public void actionPerformed(ActionEvent e){
	        	
	        	// when clicked, exits
		        System.exit(0);
	                
	        }
	    });

		if (player.getScore() > 10) {
			Iterator<AniObject> itr = animation.getImages().iterator();
			while (itr.hasNext()) {
				AniObject aniObject = itr.next();
				if (aniObject.toString().compareToIgnoreCase("bird") == 0) {
					aniObject.setScoreSize(0);
					aniObject.setSize(1);
					aniObject.setY((int) Math.round(animation.contentPaneSize/3));
					aniObject.setX(600);
					aniObject.setVisible(true);
				}
			}
			animation.migrationAnimation2();
			aniLabel.setText("Your score was " + Integer.toString(player.getScore()) + ". Your Red Knot will make it to the Arctic!!!");
			aniLabel.setFont(new Font("Arial", Font.PLAIN, 50));
			screens.show(cardPanel, "Blank");
			tickStage = 3;
		}
		else {
			Iterator<AniObject> itr = animation.getImages().iterator();
			while (itr.hasNext()) {
				AniObject aniObject = itr.next();
				if (aniObject.toString().compareToIgnoreCase("bird") == 0) {
					aniObject.setScoreSize(0);
					aniObject.setSize(1);
					aniObject.setY((int) Math.round(animation.contentPaneSize/3));
					aniObject.setX(600);
					aniObject.setVisible(true);
				}
			}
			animation.migrationAnimation2();
			aniLabel.setText("Your score was " + Integer.toString(player.getScore()) + ". Your Red Knot will not make it to the Arctic :(");
			aniLabel.setFont(new Font("Arial", Font.PLAIN, 50));
			screens.show(cardPanel, "Blank");
			tickStage = 5;
		}

		//Add and repaint
		frame.validate();
		frame.repaint();
	}
	
	private void questionScreen(String question, List<String> possibleAns) {
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
				this.gameBoardTimer.getTimer().stop();
				System.out.println("Level over");
				this.endScreen();
			}
		}
		
	}
	
	private void removePowerUp() {
		System.out.println("Power up is gone");
		powerUpLabel.setText(" ");
		PowerUps currentPowerUp = player.getCurrentPowerUp();
		player.setPowerupStatus(false);
		player.setEggMultiplier(1);
		if(currentPowerUp == PowerUps.HELPER) {
			helpers.clear();
			Iterator<AniObject> aniIter = animation.getImages().iterator();
			AniObject next;
			while (aniIter.hasNext()) {
				next = aniIter.next();
				if (next.toString().compareToIgnoreCase("maggie") == 0) {
					aniIter.remove();
				}
			}
		}
		if(currentPowerUp == PowerUps.FREEZE) {
			gameBoardTimer.getTimer().start();
		}
		
	}
	
	private void implementPowerUp() {
		
		PowerUps currentPowerUp = player.getCurrentPowerUp();
		System.out.println("current power up: " + currentPowerUp);
		if(currentPowerUp == PowerUps.HELPER) {
			System.out.println("Maggie went out to find some eggs");
			addHelpers();
			
		}
		
		if (currentPowerUp == PowerUps.CLEANER) {
			System.out.println("The DNRE sent out some cleaners");
			addCleaners();
		}
		
		if (currentPowerUp == PowerUps.FREEZE) {
			System.out.println("Looks like you have some more time");
			pauseGameBoardTimer();
		}
		if (currentPowerUp == PowerUps.BONUS) {
			System.out.println("Eggs are worth double points!");
			player.setEggMultiplier(2);
		}
	}
	
	private void addHelpers() {
		int boardX = 0;
		helpers = new ArrayList<>();
		for(GridSpace[] row: gameBoard.getBoard()) {
			int boardY = 0;
			for(GridSpace gridSpace: row) {
				if(gridSpace.getItem() == Item.EGG && gridSpace.getIsCovered() == true) {
					Helper helper = new Helper(boardX, boardY);
					helpers.add(helper);
				}
				boardY++;
			}
			boardX++;
		}
		for (Helper helper: helpers) {
			int[] gridIndex = indexSizePos(helper.getXPos(), helper.getYPos());
			animation.addMaggie(gridIndex[0], gridIndex[1], gridIndex[2], gridIndex[3]);
		}
	}
	
	private void addCleaners() {
		int count = 0;
		int totTrash = 0;
		for (int i = 0; i < 5; i++) {
			for (int j = 0; j < 10; j++) {
				GridSpace trySpace = gameBoard.getSpace(i,  j);
				if (trySpace.getIsCovered() == true && trySpace.getItem() == Item.TRASH) {
					totTrash++;
				}
			}
		}
		while (count < 10 && count < totTrash) {
			int X = NumberManipulation.generateNum(10);
			int Y = NumberManipulation.generateNum(10);
			X = X - 1;
			Y = Y - 1;	
			GridSpace trySpace = gameBoard.getSpace(X,  Y);
			if (trySpace.getIsCovered() == true && trySpace.getItem() == Item.TRASH) {
				trySpace.setIsCovered(false);
				trySpace.setItem(Item.ALREADYCHECKED);
				int[] gridIndex = indexSizePos(X, Y);
				animation.addHole(gridIndex[0], gridIndex[1], gridIndex[2], gridIndex[3]);
				count++;
			}
		}
	}
	
	private void pauseGameBoardTimer() {
		gameBoardTimer.getTimer().setDelay(2000);
	}

	public static void tick(Animation animation, Controller controller) {
		if (controller.tickStage == 0) {
			Iterator<AniObject> itrMigration = animation.getImages().iterator();
			while (itrMigration.hasNext()) {
				AniObject aniObject = itrMigration.next();
				if (aniObject.toString().compareToIgnoreCase("bird") == 0) {
					aniObject.setY(aniObject.getY() - 2);
					if (aniObject.getY() <= (int) Math.round(animation.contentPaneSize/3)) {
						controller.tickStage = 1;
						break;
					}
				}
			}
		}
		//removes us from board
		else if (controller.tickStage == 1) {
			controller.tickStage = 2;
			Iterator<AniObject> itrRemove = animation.getImages().iterator();
			while (itrRemove.hasNext()) {
				AniObject aniObjectRemove = itrRemove.next();
				if (aniObjectRemove.toString().compareToIgnoreCase("US") == 0) {
					itrRemove.remove();
				}
			}
			controller.tutorial();
		}
		//bottle and horseshoe crab animation
		else if (controller.tickStage == 2) {
			Iterator<AniObject> scoreImageItr = animation.getImages().iterator();
			while (scoreImageItr.hasNext()) {
				AniObject scoreImage = scoreImageItr.next();
				if (scoreImage.toString().compareToIgnoreCase("plusOne") == 0 || scoreImage.toString().compareToIgnoreCase("minusOne") == 0 || scoreImage.toString().compareToIgnoreCase("plusTwo") == 0) {
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
		//second migration animation stuff
		else if (controller.tickStage == 3) {
			Iterator<AniObject> itrMigration2 = animation.getImages().iterator();
			while (itrMigration2.hasNext()) {
				AniObject aniObject = itrMigration2.next();
				if (aniObject.toString().compareToIgnoreCase("bird") == 0) {
					aniObject.setY(aniObject.getY() - 3);
					if (aniObject.getY() <= 65) {
						Iterator<AniObject> itrRemove = animation.getImages().iterator();
						while (itrRemove.hasNext()) {
							AniObject aniObjectRemove = itrRemove.next();
							if (aniObjectRemove.toString().compareToIgnoreCase("US") == 0) {
								itrRemove.remove();
							}
							if (aniObjectRemove.toString().compareToIgnoreCase("bird") == 0) {
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
		//won
		else if (controller.tickStage == 4) {
			
			Iterator<AniObject> itrEgg = animation.getImages().iterator();
			while (itrEgg.hasNext()) {
				AniObject egg = itrEgg.next();
				if (egg.toString().compareToIgnoreCase("egg") == 0) {
					if (egg.getY() < 450) {
						egg.setY(egg.getY() + 3);
					}
				}
			}
			controller.tickStage=7;
		}
		//lost
		else if (controller.tickStage == 5) {
			Iterator<AniObject> itrMigration2 = animation.getImages().iterator();
			while (itrMigration2.hasNext()) {
				AniObject aniObject = itrMigration2.next();
				if (aniObject.toString().compareToIgnoreCase("bird") == 0) {
					aniObject.setY(aniObject.getY() - 1);
					if (aniObject.getY() <= 250) {
						Iterator<AniObject> itrRemove = animation.getImages().iterator();
						while (itrRemove.hasNext()) {
							AniObject aniObjectRemove = itrRemove.next();
							if (aniObjectRemove.toString().compareToIgnoreCase("US") == 0) {
								itrRemove.remove();
							}
							if (aniObjectRemove.toString().compareToIgnoreCase("bird") == 0) {
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
		//lose
		else if (controller.tickStage == 6) {
			boolean tombStoneAni = false;
			Iterator<AniObject> itrDeadBird = animation.getImages().iterator();
			while (itrDeadBird.hasNext()) {
				AniObject deadBird = itrDeadBird.next();
				if (deadBird.toString().compareToIgnoreCase("deadBird") == 0) {
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
		else if(controller.tickStage==7){
			TopTen.getTopTenList();
			TopTen.checkTopTen(controller.player);
			TopTen.SaveTopTenList();
			controller.tickStage=8;
		}

		else {

		}
	}
	
	private void showImages() {
		for(AniObject object: animation.getImages()) {
			if (object.toString().compareToIgnoreCase("done") != 0) {
				object.setVisible(true);
			}
		}
	}
	
	private void hideImages() {
		for(AniObject object: animation.getImages()) {
			object.setVisible(false);
		}
	}
	
	private void showBird() {
		for (AniObject object: animation.getImages()) {
			if(object.toString().equalsIgnoreCase("bird")) {
				object.setVisible(true);
			}
		}
	}
	
	private JButton createAnswerButton(String answer) {
		JButton possibleAnswer = new JButton(answer);
		possibleAnswer.addActionListener((ActionEvent a)->{
			JButton selectedButton = (JButton)a.getSource();
			String userAnswer = selectedButton.getText();
			boolean playerWasCorrect = gameBoard.checkAnswer(userAnswer);
			player.setPowerupStatus(playerWasCorrect);
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
				screens.show(cardPanel, "Board");
				showImages();
				frame.validate();
				frame.repaint();	
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
	
	private JButton createAnswerButtonTutorial(String answer) {
		JButton possibleAnswer = new JButton(answer);
		possibleAnswer.addActionListener((ActionEvent a)->{
			Iterator<AniObject> boardItr = animation.getImages().iterator();
			while (boardItr.hasNext()) {
				AniObject next = boardItr.next();
				if (next.toString().compareToIgnoreCase("beach") == 0) {
					next.setImage(animation.getTutorialImage(5));
				}
			}
			frame.getContentPane().removeMouseListener(frame.getContentPane().getMouseListeners()[0]);
			frame.getContentPane().removeMouseMotionListener(frame.getContentPane().getMouseMotionListeners()[0]);
			showBird();
			player = new Player(Bird.REDKNOT);
			gameBoard = new Board(gameBoard.getDifficulty());
			buildBoard();
			
		});
		return possibleAnswer;
	}
	
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
	
	// Game with GUI
	public static void main(String[] args) {
		Controller cont = new Controller();
       	cont.frame = new JFrame();
       	//cont.frame.setPreferredSize(new Dimension(1000,1000));
       	cont.frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
       	//cont.frame.setResizable(true);
       	cont.frame.setExtendedState(JFrame.MAXIMIZED_BOTH); 
		cont.frame.setUndecorated(true);
	  	cont.animation = new Animation();
	  	cont.animation.setVisible(true);
	  	cont.frame.add(cont.animation);
	  	cont.frame.pack();
	  	cont.frame.setVisible(true);
	  	cont.cardPanel = new JPanel();
	  	cont.cardPanel.setVisible(true);
	  	cont.screens = new CardLayout();
	  	cont.cardPanel.setLayout(cont.screens);
	  	cont.thisController = cont;
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
	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		if(gameBoardTimer != null) {
			timerLabel.setText("Time: " + Integer.toString(gameBoardTimer.getTimeRemaining()/1000) + " ");
		}
		this.checkTimers();
	}
	
}