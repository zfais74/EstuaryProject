import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.File;
import java.io.Serializable;
import java.util.Iterator;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

import enums.Bird;
import enums.Item;

// The Controller

public class Controller implements Serializable {
	
	// The Model
	Player player;
	Board gameBoard;
	
	// The View
	JFrame frame;
	Animation animation;
	
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
		
		startButton.addActionListener(new ActionListener(){
	        public void actionPerformed(ActionEvent e){
	        		
	        		frame.getContentPane().remove(startPanel);
	        		frame.validate();
	        		frame.getContentPane().repaint();
	        		// when clicked calls method to generate difficulty selection screen
	        		pickDifficulty();
	                
	        }
	    });

		instButton.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent e) {
				frame.getContentPane().remove(startPanel);
				frame.validate();
				frame.getContentPane().repaint();
				//
				DisplayInstructions();

			}
		});
		//When built add the component to the frame
		frame.add(startPanel);
		frame.validate();
	}
	
	public void DisplayInstructions(){
		JPanel instructionPanel = new JPanel();
		instructionPanel.setLayout(new GridBagLayout());
		GridBagConstraints constraints = constraintFactory();
		
		JButton startButton = new JButton("Start Game");
		startButton.setFont(new Font("Arial", Font.PLAIN, 30));
		startButton.setVisible(true);
		startButton.addActionListener(new ActionListener(){
	        public void actionPerformed(ActionEvent e){
	        		
	        		frame.getContentPane().remove(instructionPanel);
	        		frame.validate();
	        		frame.getContentPane().repaint();
	        		// when clicked calls method to generate difficulty selection screen
	        		pickDifficulty();
	                
	        }
	    });
		
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
		
		frame.add(difficultyPanel);
		frame.validate();
		frame.repaint();
		easyButton.addActionListener(new ActionListener(){
	        public void actionPerformed(ActionEvent e){
	        		
	        	frame.getContentPane().remove(difficultyPanel);
	        	frame.getContentPane().revalidate();
	        	frame.getContentPane().repaint();
        		// when clicked picks character and difficulty
        		gameBoard = new Board(Board.Difficulty.EASY);
        		player = new Player(Bird.DUNLIN);
        		animation.migrationAnimation();
	        }
	    });
		
		mediumButton.addActionListener(new ActionListener(){
	        public void actionPerformed(ActionEvent e){
	        		
	        	frame.getContentPane().remove(difficultyPanel);
	        	frame.getContentPane().revalidate();
	        	frame.getContentPane().repaint();
	        	gameBoard = new Board(Board.Difficulty.MEDIUM);
	        	player = new Player(Bird.SANDPIPER); 
	        	animation.migrationAnimation();
	        }
	    });
		
		hardButton.addActionListener(new ActionListener(){
	        public void actionPerformed(ActionEvent e){
	        		
	        	frame.getContentPane().remove(difficultyPanel);
	        	frame.getContentPane().revalidate();
	        	frame.getContentPane().repaint();
	        	gameBoard = new Board(Board.Difficulty.HARD);
	        	player = new Player(Bird.REDKNOT);
	        	animation.migrationAnimation();
	        }
	    });
	}
	
	public void buildBoard() {
		
		JPanel boardPanel = new JPanel();
		boardPanel.setLayout(new GridBagLayout());
		GridBagConstraints constraints = constraintFactory();
		
		constraints.gridx = 1;
		constraints.gridy = 0;
		constraints.anchor = GridBagConstraints.EAST;
		
		JLabel clicks = new JLabel("Clicks remaining: " + Integer.toString(gameBoard.getClicks()) + " ");
		clicks.setFont(new Font("Arial", Font.PLAIN, 40));
		boardPanel.add(clicks, constraints);
		
		constraints.gridx = 1;
		constraints.gridy = 1;
		constraints.anchor = GridBagConstraints.EAST;
		
		JLabel score = new JLabel("Score: " + Integer.toString(player.getScore()) + " ");
		score.setFont(new Font("Arial", Font.PLAIN, 40));
		boardPanel.add(score, constraints);
		
		constraints.gridx = 1;
		constraints.gridy = 2;
		constraints.anchor = GridBagConstraints.EAST;
		
		JButton chestButton = new JButton();
		chestButton.setPreferredSize(new Dimension(200, 200));
		chestButton.setVisible(true);
		chestButton.setEnabled(false);
		boardPanel.add(chestButton, constraints);
		
		chestButton.addActionListener(new ActionListener(){
	        public void actionPerformed(ActionEvent e){
	        		
	        	//Zeke, powerUpPanel call can go here I guess
	        	//make sure to add it in the second chestButton declaration below as well
	        	
	        	System.out.println("Chest Button Clicked");
	        	
	        }
	    });
		
		frame.add(boardPanel);
		frame.validate();
		
		Iterator<AniObject> boardItr = animation.getImages().iterator();
		while (boardItr.hasNext()) {
			AniObject next = boardItr.next();
			if (next.toString() == "board"  || next.toString() == "beach" || next.toString() == "grass1"
					|| next.toString() == "grass2" || next.toString() == "grass3" || next.toString() == "grass4" || next.toString() == "grass5"
					 || next.toString() == "grass6" || next.toString() == "grass7" || next.toString() == "grass8" || next.toString() == "grass9" 
					 || next.toString() == "grass10") { 
				next.setVisible(true);
			}	
		}
		
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
		        	constraints.gridy = 0;
		        	constraints.anchor = GridBagConstraints.EAST;
		        	
	                JLabel newClicks = new JLabel("Clicks remaining: " + Integer.toString(gameBoard.getClicks()) + " ");
	        		newClicks.setFont(new Font("Arial", Font.PLAIN, 40));
	        		newClicks.setOpaque(false);
	        		boardPanel.add(newClicks, constraints);
	                
	        		constraints.gridx = 1;
	        		constraints.gridy = 1;
	        		constraints.anchor = GridBagConstraints.EAST;
	        		
	        		JLabel newScore = new JLabel("Score: " + Integer.toString(player.getScore()) + " ");
	        		newScore.setFont(new Font("Arial", Font.PLAIN, 40));
	        		newScore.setOpaque(false);
	        		boardPanel.add(newScore, constraints);
	        		
	        		constraints.gridx = 1;
	        		constraints.gridy = 2;
	        		constraints.anchor = GridBagConstraints.EAST;
	        		
	        		JButton chestButton = new JButton();
	        		chestButton.setPreferredSize(new Dimension(200, 200));
	        		chestButton.setVisible(true);
	        		
	        		chestButton.addActionListener(new ActionListener(){
	        	        public void actionPerformed(ActionEvent e){
	        	        		
	        	        	//Zeke, powerUpPanel call can go here I guess
	        	        	//make sure to add it in the second chestButton declaration below as well
	        	        	
	        	        	System.out.println("Chest Button Clicked");
	        	        	
	        	        }
	        	    });
	        		
	        		boardPanel.add(chestButton, constraints);
	        		
	        		
	        		constraints.gridx = 0;
		        	constraints.gridy = 0;
		        	constraints.anchor = GridBagConstraints.EAST;
		        	
	        		if (item == Item.TRASH) {
		        		JLabel ateSome = new JLabel("Ate Some Trash :(");
		        		ateSome.setFont(new Font("Arial", Font.PLAIN, 40));
		        		ateSome.setOpaque(false);
		        		boardPanel.add(ateSome, constraints);
	        		}
	        		else if (item == Item.EGG) {
		        		JLabel ateSome = new JLabel("You Found and egg!!!");
		        		ateSome.setFont(new Font("Arial", Font.PLAIN, 40));
		        		ateSome.setOpaque(true);
		        		frame.getContentPane().add(ateSome, 0);
		        		boardPanel.add(ateSome, constraints);
	        		}
	        		else if (item == Item.EMPTY) {
		        		JLabel ateSome = new JLabel("Nothing there...");
		        		ateSome.setFont(new Font("Arial", Font.PLAIN, 40));
		        		ateSome.setOpaque(false);
		        		boardPanel.add(ateSome, constraints);
	        		}
	        		else if (item == Item.ALREADYCHECKED) {
		        		JLabel ateSome = new JLabel("Already checked there.");
		        		ateSome.setFont(new Font("Arial", Font.PLAIN, 40));
		        		ateSome.setOpaque(false);
		        		boardPanel.add(ateSome, constraints);
	        		}
	        		
	        		frame.add(boardPanel);
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
		
	}
	
	private int[] windowToGrid(int xLoc, int yLoc) {
		int xIndex;
		int yIndex;
		
		Iterator<AniObject> boardItr = animation.getImages().iterator();
		AniObject boardImage = null;
		while (boardItr.hasNext()) {
			boardImage = boardItr.next();
			if (boardImage.toString() == "board") {
				break;
			}	
		}
		
		int gridImageHeight = boardImage.getYSize();
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
	
	// displays score, and a quit button
	public void endScreen() {
		frame.getContentPane().removeAll();
		// need to repaint the contentPane to get rid of buttons
		frame.getContentPane().repaint();
		
		//Declare a new JPanel
		JPanel endPanel = new JPanel();
		//Set its layout manager to GridBag
		endPanel.setLayout(new GridBagLayout());
		
		GridBagConstraints constraints = constraintFactory();
		
		constraints.gridx = 3;
		constraints.weightx = 1.0;
		constraints.weighty = 1.0;
		int width = 2;
		for(AniObject object: animation.getImages()) {
			object.setVisible(false);
		}
		
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
		
		//Add and repaint
		frame.getContentPane().add(endPanel);
		frame.validate();
		frame.repaint();
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