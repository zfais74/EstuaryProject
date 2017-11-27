import java.awt.CardLayout;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
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

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.Timer;

import TimeManagement.GameBoardTimer;
import TimeManagement.PowerUpTimer;
import enums.Bird;
import enums.Direction;
import enums.Item;
import enums.PowerUps;
import powerUpModels.Cleaner;
import powerUpModels.Helper;

// The Controller

public class Controller implements Serializable, ActionListener {
	
	// The Model
	Player player;
	Board gameBoard;
	PowerUpTimer powerUpTimer;
	GameBoardTimer gameBoardTimer;
	Timer checkTimersTimer;
	
	// The View
	JFrame frame;
	Animation animation;
	CardLayout screens;
	JPanel cardPanel;
	Controller thisController;
	
	// Constant for tick method
	private int boardBuilt = 0;
	JLabel answerLabel = new JLabel(" ");
	List<Helper> helpers;
	List<Cleaner> cleaners;
	
	
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
		JPanel startPanel = new JPanel();
		//Set its layout manager to GridBag
		startPanel.setLayout(new GridBagLayout());
		
		//The constraints describe each new component's location
		GridBagConstraints constraints = constraintFactory();
		//The component will render in the 3rd column of the first row
		constraints.gridx = 3;
		
		JLabel title = new JLabel("EGG SWEEPER");
		title.setFont(new Font("Arial", Font.PLAIN, 80));
		
		//Add components to the start panel instead of the frame's contentPane directly
		startPanel.add(title, constraints);
		
		JButton startButton = new JButton("Start Game");JButton instButton = new JButton("Instructions");JButton loadButton = new JButton("Load");
		startButton.setFont(new Font("Arial", Font.PLAIN, 30));instButton.setFont(new Font("Arial", Font.PLAIN, 30));loadButton.setFont(new Font("Arial", Font.PLAIN, 30));
		startButton.setVisible(true);instButton.setVisible(true);
		
		//This component will be in the same column, just 3 rows below
		constraints.gridy = 3;
		
		startPanel.add(startButton,constraints);
		constraints.gridy = 4;
		startPanel.add(instButton,constraints);
		constraints.gridy = 5;
		startPanel.add(loadButton,constraints);
		
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

		loadButton.addActionListener((ActionEvent e)-> {
				Load.LoadGame();
				//find correct method so that it keeps ticking

			}
		);
		//Adds the start screen to the deck
		cardPanel.add(startPanel, "Start");
		screens.show(cardPanel, "Start");
		//adds a blank screen to the deck
		JPanel blankScreen = new JPanel();
    	cardPanel.add(blankScreen, "Blank");

		frame.add(cardPanel);
		frame.validate();
		frame.repaint();

	}
	
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
		
		JLabel instructions = new JLabel("Instructions will \n go \n right here");
		instructions.setFont(new Font("Arial", Font.PLAIN, 40));
		
		constraints.gridx = 0;
		constraints.gridy = 0;
		instructionPanel.add(instructions, constraints);
		
		constraints.gridx = 0;
		constraints.gridy = 1;
		instructionPanel.add(startButton, constraints);
		
		frame.add(instructionPanel);
		frame.validate();

	}
	// displays easy, medium and hard button
	public void pickDifficulty() {
		JPanel difficultyPanel = new JPanel();
		difficultyPanel.setLayout(new GridBagLayout());
		
		GridBagConstraints constraints = constraintFactory();
		constraints.gridx = 3;
		constraints.weightx = 50;
		constraints.weighty = 50;
		
		//The width of space between each button
		int width = 3;
		
		JButton easyButton = new JButton("Easy");
		easyButton.setFont(new Font("Arial", Font.PLAIN, 30));
		easyButton.setVisible(true);
		
		JButton mediumButton = new JButton("Medium");
		mediumButton.setFont(new Font("Arial", Font.PLAIN, 30));
		mediumButton.setVisible(true);
		
		JButton hardButton = new JButton("Hard");
		hardButton.setFont(new Font("Arial", Font.PLAIN, 30));
		hardButton.setVisible(true);
		
		//After a button is added add the width
		difficultyPanel.add(easyButton,constraints);
		constraints.gridy = width;
		difficultyPanel.add(mediumButton,constraints);
		constraints.gridy = 2*width;
		difficultyPanel.add(hardButton,constraints);
		
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
        		gameBoard = new Board(Board.Difficulty.EASY);
        		player = new Player(Bird.DUNLIN);
        		animation.migrationAnimation();
	        }
	    );
		
		mediumButton.addActionListener((ActionEvent e)->{
	        	screens.show(cardPanel, "Blank");
	        	frame.getContentPane().revalidate();
	        	frame.getContentPane().repaint();
	        	gameBoard = new Board(Board.Difficulty.MEDIUM);
	        	player = new Player(Bird.SANDPIPER); 
	        	animation.migrationAnimation();
	        }
	    );
		
		hardButton.addActionListener((ActionEvent e)->{
	        		
	        	screens.show(cardPanel, "Blank");
	        	frame.getContentPane().revalidate();
	        	frame.getContentPane().repaint();
	        	gameBoard = new Board(Board.Difficulty.HARD);
	        	player = new Player(Bird.REDKNOT);
	        	animation.migrationAnimation();
	        }
	    );
	}
	
	public void buildBoard() {
		
		gameBoardTimer = new GameBoardTimer();
		gameBoardTimer.getTimer().start();
		JPanel boardPanel = new JPanel();
		boardPanel.setLayout(new GridBagLayout());
		GridBagConstraints constraints = constraintFactory();
		
		constraints.gridx = 1;
		constraints.gridy = 0;
		constraints.anchor = GridBagConstraints.EAST;
		
		answerLabel.setFont(new Font("Arial", Font.PLAIN, 40));
		boardPanel.add(answerLabel, constraints, 0);
		
		constraints.gridx = 1;
		constraints.gridy = 1;
		constraints.anchor = GridBagConstraints.EAST;
		
		JLabel clicks = new JLabel("Clicks remaining: " + Integer.toString(gameBoard.getClicks()) + " ");
		clicks.setFont(new Font("Arial", Font.PLAIN, 40));
		boardPanel.add(clicks, constraints);
		
		constraints.gridx = 1;
		constraints.gridy = 2;
		constraints.anchor = GridBagConstraints.EAST;
		
		JLabel score = new JLabel("Score: " + Integer.toString(player.getScore()) + " ");
		score.setFont(new Font("Arial", Font.PLAIN, 40));
		boardPanel.add(score, constraints);
		
		constraints.gridx = 1;
		constraints.gridy = 3;
		constraints.anchor = GridBagConstraints.EAST;

		JButton save = new JButton("Save");
		save.setFont(new Font("Arial", Font.PLAIN, 30));
		save.setFocusable(false);
		save.setVisible(true);
		boardPanel.add(save,constraints);
		save.addActionListener((ActionEvent a)->{
			Load.SaveGame(this);
		});
		
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
		bird.setX((int) Math.round(mouseLoc.getX() + 3 - bird.getYSize()/5.));
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
				birdMouse.setX( (int) Math.round(e.getX() - birdMouse.getYSize()/8.) + chestButton.getX());
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
		        	answerLabel.setText(" ");
		        	frame.validate();
		        	frame.repaint();
	                
		        	constraints.gridx = 1;
		    		constraints.gridy = 0;
		    		constraints.anchor = GridBagConstraints.EAST;
		    		
		    		boardPanel.add(answerLabel, constraints);
		        	
		        	constraints.gridx = 1;
		        	constraints.gridy = 1;
		        	constraints.anchor = GridBagConstraints.EAST;
		        	
	                JLabel newClicks = new JLabel("Clicks remaining: " + Integer.toString(gameBoard.getClicks()) + " ");
	        		newClicks.setFont(new Font("Arial", Font.PLAIN, 40));
	        		boardPanel.add(newClicks, constraints);
	                
	        		constraints.gridx = 1;
	        		constraints.gridy = 2;
	        		constraints.anchor = GridBagConstraints.EAST;
	        		
	        		JLabel newScore = new JLabel("Score: " + Integer.toString(player.getScore()) + " ");
	        		newScore.setFont(new Font("Arial", Font.PLAIN, 40));
	        		boardPanel.add(newScore, constraints);
	        		
	        		constraints.gridx = 1;
	        		constraints.gridy = 3;
	        		constraints.anchor = GridBagConstraints.EAST;

	        		JButton save = new JButton("Save");
	        		save.setFocusable(false);
	        		save.setFont(new Font("Arial", Font.PLAIN, 30));
	        		save.setVisible(true);
	        		boardPanel.add(save,constraints);
	        		save.addActionListener((ActionEvent a)->{
	        			Load.SaveGame(thisController);
	        		});
	        		
	        		constraints.gridx = 1;
	        		constraints.gridy = 4;
	        		constraints.anchor = GridBagConstraints.EAST;
	        		
	        		JLabel ateSome = null;
	        		Font ateFont = new Font("Arial", Font.PLAIN, 40);
	        		
	        		if (item == Item.EGG) {
		        		ateSome = new JLabel("You Found and egg!!!");
		        		ateSome.setFont(ateFont);
		        		frame.getContentPane().add(ateSome, 0);
		        		boardPanel.add(ateSome, constraints);
	        		}
	        		else if (item == Item.ALREADYCHECKED) {
		        		ateSome = new JLabel("Already checked there.");
		        		ateSome.setFont(ateFont);
		        		boardPanel.add(ateSome, constraints);
	        		}
	        		else {
	        			if (item == Item.TRASH) {
			        		ateSome = new JLabel("Ate Some Trash :(");
			        		ateSome.setFont(ateFont);
			        		boardPanel.add(ateSome, constraints);
		        		}
		        		
	        			else if (item == Item.EMPTY) {
			        		ateSome = new JLabel("Nothing there...");
			        		ateSome.setFont(ateFont);
			        		boardPanel.add(ateSome, constraints);
		        		}
	        			
	        			List<Direction> dirList = gameBoard.getAdjacentItemGridDirections(xIndex, yIndex);
	        			Collections.shuffle(dirList);
	        			for(int count = 0; count < dirList.size() && count < 3; count++) {
	        				Direction d = dirList.get(count);
	        				System.out.println(String.format("Item at Direction:%s", d.name()));
	        				String loc_disp = String.format("This Location: (%d,%d) That Location: (%d,%d)", xIndex, yIndex, xIndex + gameBoard.convertXDim(d), yIndex + gameBoard.convertYDim(d));
	        				System.out.println(loc_disp);
	        				int[] dims = maggieSizePos(xIndex + gameBoard.convertXDim(d),yIndex + gameBoard.convertYDim(d));
	        				animation.addMaggie(dims[0], dims[1], dims[2], dims[3]);
	        			}
	        			
	        		}
	        		
	        		
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
	        	        	
	        	        	System.out.println("Chest Button Clicked");
	        	        	
	        	        }
	        	    );
	        		chestButton.addMouseMotionListener(new MouseMotionListener() {

	        			@Override
	        			public void mouseDragged(MouseEvent arg0) {
	        				// TODO Auto-generated method stub
	        				
	        			}

	        			@Override
	        			public void mouseMoved(MouseEvent e) {
	        				birdMouse.setX( (int) Math.round(e.getX() - birdMouse.getYSize()/8.) + chestButton.getX());
	        				birdMouse.setY((int) Math.round(e.getY() - birdMouse.getYSize()/1.8) + chestButton.getY());
	        				double newBirdRatio = getSizeRatio(birdMouse.getY(), boardMouse);
	        				birdMouse.setSize(newBirdRatio);
	        				frame.repaint();
	        				
	        			}
	        			
	        		});
	        		
	        		boardPanel.add(chestButton, constraints);
	        		

	        		frame.validate();
	        		
	                if (gameBoard.getClicks() == 0){
	                	endScreen();
	                }
	                //Established getClicks() != 0
	                else if (gameBoard.getClicks() % 3 == 0) {
	                	chestButton.setIcon(animation.getChestIcon());
	                	chestButton.setEnabled(true);
	                	
	                }
	                //Established getClicks() % 3 != 0
	                else {
	                	chestButton.setIcon(null);
	                	chestButton.setEnabled(false);
	                }
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
				birdMouse.setX( (int) Math.round(e.getX() - birdMouse.getYSize()/8.));
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
	
	public int[] maggieSizePos(int xIndex, int yIndex) {
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
		
		int maggieX = 1;
		int maggieY = 1;
		int maggieXSize = 1;
		int maggieYSize = 1;
		
		if ((xIndex != -1) && (yIndex != -1)){
			double boxRatio = ((double) LineHeights[9] - (double) LineHeights[8])/((double) LineHeights[10] - (double) LineHeights[9]);
			double gridSizeRatio = 1/Math.pow(boxRatio, yIndex);
			maggieYSize = (int) Math.round(gridSizeRatio * (gridImageWidth/1000)*(LineHeights[1] - LineHeights[0]));
			maggieY = (int) Math.round(imageYloc + LineHeights[yIndex] + ((gridImageWidth/1000.)*(LineHeights[yIndex+1] - LineHeights[yIndex])/2.) - (maggieYSize/2.) );
			
			maggieX = (int) Math.round(imageXloc + (gridImageWidth/1000)*(LineTopX[xIndex] + LineSlopes[xIndex] * (LineHeights[yIndex] + LineHeights[yIndex+1])/2.));
			maggieXSize = (int) ((gridImageWidth/1000)*((LineTopX[xIndex + 1]  + (LineSlopes[xIndex + 1] * (LineHeights[yIndex] + LineHeights[yIndex+1])/2.)) - (LineTopX[xIndex]  + (LineSlopes[xIndex] * (LineHeights[yIndex] + LineHeights[yIndex+1])/2.))));
		}
		
		int[] gridIndex = {maggieX, maggieY, maggieXSize, maggieYSize};
		
		return gridIndex;
		
	}
	
	// displays score, and a quit button
	public void endScreen() {
		
		this.checkTimersTimer.stop();
		//Declare a new JPanel
		JPanel endPanel = new JPanel();
		//Set its layout manager to GridBag
		endPanel.setLayout(new GridBagLayout());
		
		GridBagConstraints constraints = constraintFactory();
		
		constraints.gridx = 3;
		constraints.weightx = 1.0;
		constraints.weighty = 1.0;
		int width = 2;
		hideImages();
		
		// create a label
		JLabel eggLabel = new JLabel("You found " + Integer.toString(player.getEggs()) + " eggs,");
		eggLabel.setFont(new Font("Arial", Font.PLAIN, 60));
		// bounds must be set for label to display
		
		JLabel trashLabel = new JLabel("and you ate " + Integer.toString(player.getTrash()) + " pieces of trash,");
		trashLabel.setFont(new Font("Arial", Font.PLAIN, 60));
		
		JLabel scoreLabel = new JLabel("so your score is " + Integer.toString(player.getScore()) + "!!!");
		scoreLabel.setFont(new Font("Arial", Font.PLAIN, 60));
		
		JButton quitButton = new JButton("Quit");
		quitButton.setFont(new Font("Arial", Font.PLAIN, 30));

		quitButton.addActionListener(new ActionListener(){
	        public void actionPerformed(ActionEvent e){
	        	
	        	// when clicked, exits
		        System.exit(0);
	                
	        }
	    });
		
		endPanel.add(eggLabel, constraints);
		constraints.gridy = width;
		endPanel.add(trashLabel, constraints);
		constraints.gridy = 2*width;
		endPanel.add(scoreLabel, constraints);
		constraints.gridy = 3*width;
		endPanel.add(quitButton, constraints);
		cardPanel.add(endPanel, "End");
		screens.show(cardPanel, "End");
		
		//Add and repaint
		frame.validate();
		frame.repaint();
	}
	
	private void questionScreen(String question, List<String> possibleAns) {
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
		PowerUps currentPowerUp = player.getCurrentPowerUp();
		player.setPowerupStatus(false);
		player.setPointsPerEgg(1);
		if(currentPowerUp == PowerUps.HELPER) {
			helpers.clear();
			for (AniObject ani: animation.getImages()) {
				if (ani.toString().compareToIgnoreCase("maggie") == 0){
					ani.setVisible(false);
				}
			}
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
			player.setPointsPerEgg(2);
		}
	}
	
	private void addHelpers() {
		int boardX = 0;
		helpers = new ArrayList<>();
		for(GridSpace[] row: gameBoard.getBoard()) {
			int boardY = 0;
			for(GridSpace gridSpace: row) {
				if(gridSpace.getItem() == Item.EGG) {
					Helper helper = new Helper(boardX, boardY);
					helpers.add(helper);
				}
				boardY++;
			}
			boardX++;
		}
		for (Helper helper: helpers) {
			int[] gridIndex = maggieSizePos(helper.getXPos(), helper.getYPos());
			animation.addMaggie(gridIndex[0], gridIndex[1], gridIndex[2], gridIndex[3]);
		}
	}
	
	private void addCleaners() {
		int count = 0;
		int totTrash = 0;
		for (int i = 0; i < 10; i++) {
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
				int[] gridIndex = maggieSizePos(X, Y);
				animation.addHole(gridIndex[0], gridIndex[1], gridIndex[2], gridIndex[3]);
				count++;
			}
		}
	}
	
	private void pauseGameBoardTimer() {
		gameBoardTimer.getTimer().setDelay(2000);
	}

	public static void tick(Animation animation, Controller controller) {
		if (controller.boardBuilt == 0) {
			Iterator<AniObject> itrMigration = animation.getImages().iterator();
			while (itrMigration.hasNext()) {
				AniObject aniObject = itrMigration.next();
				if (aniObject.toString().compareToIgnoreCase("bird") == 0) {
					aniObject.setY(aniObject.getY() - 10);
					if (aniObject.getY() == animation.contentPaneSize/5) {
						controller.boardBuilt = 1;
						break;
					}
				}
			}
		}
		else if (controller.boardBuilt == 1) {
			controller.boardBuilt = 2;
			Iterator<AniObject> itrRemove = animation.getImages().iterator();
			while (itrRemove.hasNext()) {
				AniObject aniObjectRemove = itrRemove.next();
				if (aniObjectRemove.toString().compareToIgnoreCase("US") == 0) {
					itrRemove.remove();
				}
			}
			controller.buildBoard();
		}
		else {
			return;
		}
	}
	
	private void showImages() {
		for(AniObject object: animation.getImages()) {
			object.setVisible(true);
		}
	}
	
	private void hideImages() {
		for(AniObject object: animation.getImages()) {
			object.setVisible(false);
		}
	}
	
	private JButton createAnswerButton(String answer) {
		JButton possibleAnswer = new JButton(answer);
		possibleAnswer.addActionListener((ActionEvent a)->{
			JButton selectedButton = (JButton)a.getSource();
			String userAnswer = selectedButton.getText();
			boolean playerWasCorrect = gameBoard.checkAnswer(userAnswer);
			if (playerWasCorrect){
				answerLabel.setText("Correct!!!");
				powerUpTimer = new PowerUpTimer();
				powerUpTimer.getTimer().start();
			}
			else {
				answerLabel.setText("Incorrect.");
			}
			player.setPowerupStatus(playerWasCorrect);
			if (playerWasCorrect) {
				implementPowerUp();
			}
			screens.show(cardPanel, "Board");
			showImages();
			frame.validate();
			frame.repaint();	
		});
		return possibleAnswer;
	}
	
	// Game with GUI
	public static void main(String[] args) {
		Controller cont = new Controller();
       	cont.frame = new JFrame();
       	//cont.frame.setPreferredSize(new Dimension(1000,1000));
       	cont.frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	  	cont.animation = new Animation();
	  	cont.animation.setVisible(true);
	  	cont.frame.getContentPane().add(cont.animation);
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
		this.checkTimers();
	}
	
}