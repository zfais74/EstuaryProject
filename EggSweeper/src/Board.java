import java.io.File;
import java.io.FileNotFoundException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.Scanner;

import enums.Item;

// The Model

/**
 * 
 * @author Will Ransom
 *
 */


public class Board implements Serializable {
	
	// the GameBoard is an empty 2D array of GridSpace pointers
	private GridSpace[][] board = new GridSpace[boardSize][boardSize];
	
	// the Difficulty affects distribution of EGGs and TRASH
	public static enum Difficulty {EASY, MEDIUM, HARD};
	
	// Board data
	private int timer;
	private int clicks;
	private Difficulty difficulty;
	
	// size of the board
	public static int boardSize = 10;
	
	// randConst is used in setting Items in GridSpaces
	private static final int randConst = 100;
	// ratio for determining distribution of EMPTY spaces in all Difficulties
	private double emptyRatio = randConst * (3./10.);
	// ratios for determining distribution of EGGs and TRASH based on Difficulty
	private double easyEggRatio = randConst * (9./10.);
	private double mediumEggRatio = randConst * (8.5/10.);
	private double hardEggRatio = randConst * (8./10.);
	
	private double easyTwigRatio = (randConst - easyEggRatio) * (5./10.);
	private double easyBottleRatio = (randConst - easyEggRatio) * (3./10.);
	private double easyPesticideRatio = (randConst - easyEggRatio) * (2./10.);
	private double mediumTwigRatio = (randConst - easyEggRatio) * (4./10.);
	private double mediumBottleRatio = (randConst - easyEggRatio) * (4./10.);
	private double mediumPesticideRatio = (randConst - easyEggRatio) * (2./10.);
	private double hardTwigRatio = (randConst - easyEggRatio) * (2./10.);
	private double hardBottleRatio = (randConst - easyEggRatio) * (3./10.);
	private double hardPesticideRatio = (randConst - easyEggRatio) * (5./10.);
	
	private List<String> questionsAsked = new ArrayList<String>();
	private String answer;
	
	
	/**
	 * Constructor
	 * @param newDifficulty difficulty to play the game at
	 * @return a new Board object
	 * 
	 */
	// sets EMPTY, TRASH and EGG spaces in the board
	Board(Difficulty newDifficulty) {
		System.out.println(" ");
		System.out.println("Difficulty selected: " + newDifficulty);
		System.out.println("Score: 0");
		difficulty = newDifficulty;
		//timer is currently unused
		timer = 100;
		// max number of clicks
		clicks = 10;
		
		Random rand = new Random();
		// cycle through each GridSpace pointer
		for (int i = 0; i < boardSize; i++) {
			for (int j = 0; j < boardSize; j++) {
				// generate random integer to use for determining contents of GridSpace
				int randomInt = rand.nextInt(randConst);
				Item spaceItem = Item.EMPTY;
				switch (difficulty) {
					// on EASY Difficulty, 30% EMPTY, 60% EGGs, 10% TRASH
					case EASY:
						if (randomInt < (easyPesticideRatio)) {
							spaceItem = Item.PESTICIDE;
						}
						else if (randomInt < (easyBottleRatio)) {
							spaceItem = Item.BOTTLE;
						}
						else if (randomInt < (easyTwigRatio)) {
							spaceItem = Item.TWIG;
						}
						else if (randomInt < (emptyRatio)) {
							spaceItem = Item.EMPTY;
						}
						else if (randomInt < (easyEggRatio)) {
							spaceItem = Item.EGG;
						}/*
						else {
							spaceItem = Item.TWIG;
						}*/
						break;
					case MEDIUM:
						if (randomInt < (mediumPesticideRatio)) {
							spaceItem = Item.PESTICIDE;
						}
						else if (randomInt < (mediumBottleRatio)) {
							spaceItem = Item.BOTTLE;
						}
						else if (randomInt < (mediumTwigRatio)) {
							spaceItem = Item.TWIG;
						}
						else if (randomInt < (emptyRatio)) {
							spaceItem = Item.EMPTY;
						}
						else if (randomInt < (mediumEggRatio)) {
							spaceItem = Item.EGG;
						}
//						else {
//							spaceItem = Item.TWIG;
//						}
						break;
					case HARD:
						if (randomInt < (hardTwigRatio)) {
							spaceItem = Item.TWIG;
						}
						else if (randomInt < (hardBottleRatio)) {
							spaceItem = Item.BOTTLE;
						}
						else if (randomInt < (hardPesticideRatio)) {
							spaceItem = Item.PESTICIDE;
						}
						else if (randomInt < (emptyRatio)) {
							spaceItem = Item.EMPTY;
						}
						else if (randomInt < (hardEggRatio)) {
							spaceItem = Item.EGG;
						}
//						else {
//							spaceItem = Item.TWIG;
//						}
						break;
				}
				board[i][j] = new GridSpace(spaceItem);
			}
		}
	}
	
	/**
	 * getter for a GridSpace in the the board at index (x, y)
	 * @param xIndex x index of the grid space to be checked
	 * @param yIndex y index of the grid space to be checked
	 * @return GridSpace at board[x][y]
	 */
	public GridSpace getSpace(int xIndex, int yIndex) {
		return board[xIndex][yIndex];
	}
	
	/**
	 * Set a GridSpace at a location (x, y) in the board
	 * @param xIndex x index of the grid space to be set
	 * @param yIndex y index of the grid space to be set
	 * @param space a GridSpace object to be set at location (x, y)
	 */
	public void setSpace(int xIndex, int yIndex, GridSpace space) {
		board[xIndex][yIndex] = space;
	}
	
	/**
	 * gets the number of items surrounding a space
	 * @param xIndex x index of the grid space to be set
	 * @param yIndex y index of the grid space to be set
	 * @return items surrounding
	 */
	public int countAdjacentItems(int xIndex, int yIndex) {
		int count = 0;
		int radius = 1;
		for(int i = -radius; i <= radius; i++) {
			for(int j = -radius; j <= radius; j++) {
				if(i == 0 && j == 0)
					continue;
				if(xIndex + i < 0 || xIndex + i >= boardSize)
					continue;
				if(yIndex + j < 0 || yIndex + j >= boardSize)
					continue;
				GridSpace space = getSpace(xIndex + i, yIndex + j);
				if(space.getItem() != Item.EMPTY)
					count++;
			}
		}
		return count;
	}
	
	/**
	 * getter for number of clicks left
	 * @return clicks left
	 */
	public int getClicks() {
		return clicks;
	}
	
	/**
	 * setter for number of clicks left
	 * @param newClicks number of clicks to be set
	 */
	public void setClicks(int newClicks) {
		clicks = newClicks;
	}
	
	/**
	 * decrement clicks by one
	 */
	public void decClicks() {
		clicks--;
	}
	
	/**
	 * getter for time left in game
	 * @return time left
	 */
	public int getTime() {
		return timer;
	}
	
	/**
	 * setter for time left in game
	 * @param newTime new amount of time to be set
	 */
	public void setTime(int newTime) {
		timer = newTime;
	}
	
	/**
	 * read estuary question from text file
	 * @return string form of question
	 * @throws FileNotFoundException
	 */
	public String getPowerupQuestion() throws FileNotFoundException {
		StringBuilder question = new StringBuilder(); // question string builder
		StringBuilder qNum = new StringBuilder(); // string builder for the question number
		qNum.append("Q"); //marker for questions
		File questionsFile = new File("questions/powerQuestions.txt");
		int questionNum = generateQuestionNum(5);
		qNum.append(questionNum);
		qNum.append(":"); // another marker for questions
		try {
			Scanner fn = new Scanner (questionsFile);
			while(fn.hasNextLine()) {
				String line = fn.nextLine();
				if(line.contains(qNum.toString())){ // if the line contains the Q(number):, then add it to the question string builder 
					question.append(line);
					System.out.println("Selected Question:" + question.toString());
					break;
				}
			}
			fn.close();
			this.answer = this.getPowerupAnswer(questionNum);
			
		} catch (FileNotFoundException e){
			System.out.println(e.getMessage());
			System.exit(0);
		}
		
		return question.toString();
	}
	
	/**
	 * Generates random number to select which question to ask user during power up opportunity
	 * @param range a upper limit for the random number
	 * @return random number to pick which question to ask
	 */
	private int generateQuestionNum(int range) {
		return (int)(Math.random() * ((range - 1) + 1)) + 1;
	}
	
	/**
	 * read question answer from text file
	 * @param questionNum
	 * @return string form of answer
	 * @throws FileNotFoundException
	 */
	private String getPowerupAnswer(int questionNum) throws FileNotFoundException{
		StringBuilder answer = new StringBuilder(); 
		StringBuilder aNum = new StringBuilder(); 
		aNum.append("A"); //marker for questions
		File questionsFile = new File("questions/answers.txt");
		aNum.append(questionNum);
		aNum.append(":");
		try {
			Scanner fn = new Scanner (questionsFile);
			while(fn.hasNextLine()) {
				String line = fn.nextLine();
				if(line.contains(aNum.toString())){ 
					answer.append(line);
					break;
				}
			}
			fn.close();
		} catch (FileNotFoundException e){
			System.out.println(e.getMessage());
			System.exit(0);
		}
		
		return answer.toString();
	}
}
	