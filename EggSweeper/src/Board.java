import java.io.File;
import java.io.FileNotFoundException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.Scanner;

import enums.Item;
import enums.Difficulty;
import enums.Direction;
// The Model

/**
 * This class represents the actual game board, holding an array of GridSpace objects.
 * It also controls power up question loading and the hint system.
 * @author Will Ransom, Zeke Faison, Elton Mwale
 * 
 *
 */


public class Board implements Serializable {
	
	// the GameBoard is a 2D array of GridSpace pointers
		private GridSpace[][] board = new GridSpace[boardSize][boardSize];
		
		// difficulty affects the distribution of eggs and trash
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
		
		// list of questions already asked to prevent repeats
		private List<Integer> questionNumsAsked = new ArrayList<Integer>();
		// the list of answers for the power up questions
		private List<String>possibleAnswers = new ArrayList<>();
		// the correct answer
		private String correctAnswer;
		// total questions in file
		private int totalQuestions = 10;
		// keeping track of how many questions were asked out of the total
		private int totalQuestionAsked = 0;
		
		// the number of eggs on the board, used to end the game if the player finds them all
		private int totNumEggs = 0;
		
		
		/**
		 * Constructor, randomly sets Items in all grid spaces based on difficulty
		 * @param newDifficulty difficulty to play the game at
		 * @return a new Board object
		 * 
		 */
		// sets EMPTY, TRASH and EGG spaces in the board
		Board(Difficulty newDifficulty) {
			difficulty = newDifficulty;
			
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
							if (randomInt < (emptyRatio)) {
								spaceItem = Item.EMPTY;
							}
							else if (randomInt < (easyEggRatio)) {
								spaceItem = Item.EGG;
								totNumEggs++;
							}
							else {
								spaceItem = Item.TRASH;
							}
							break;
						case MEDIUM:
							if (randomInt < (emptyRatio)) {
								spaceItem = Item.EMPTY;
							}
							else if (randomInt < (mediumEggRatio)) {
								spaceItem = Item.EGG;
								totNumEggs++;
							}
							else {
								spaceItem = Item.TRASH;
							}
							break;
						case HARD:
							if (randomInt < (emptyRatio)) {
								spaceItem = Item.EMPTY;
							}
							else if (randomInt < (hardEggRatio)) {
								spaceItem = Item.EGG;
								totNumEggs++;
							}
							else {
								spaceItem = Item.TRASH;
							}
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
		 * Get the total number of eggs on the board
		 * 
		 * @return totNumEggs total # of eggs on the board
		 */
		public int getTotEggs() {
			return totNumEggs;
		}
		
		/**
		 *Calculates the Direction of a target GirdSpace from the base GridSpace
		 * 
		 * @param xIndex the x index of the base GridSpace
		 * @param yIndex the y index of the base GridSpace
		 * @param targetX the x index of the target GridSpace
		 * @param targetY the y index of the target GridSpace
		 * @return retDirection the Direction from base to target GridSpace
		 */
		private Direction calculateDirection(int xIndex, int yIndex, int targetX, int targetY) {
			Direction retDirection = Direction.UNDEFINED;
			// find difference in their indexes
			int xDelta = targetX - xIndex;
			int yDelta = targetY - yIndex;
			//Northern
			if(yDelta == -1) {
				if(xDelta == 1) {
					retDirection = Direction.NORTHEAST;
				}
				else if(xDelta == -1) {
					retDirection = Direction.NORTHWEST;
				}
				else if(xDelta == 0){
					retDirection = Direction.NORTH;
				}
			}
			//Southern
			else if(yDelta == 1) {
				if(xDelta == 1) {
					retDirection = Direction.SOUTHEAST;
				}
				else if(xDelta == -1) {
					retDirection = Direction.SOUTHWEST;
				}
				else if(xDelta == 0){
					retDirection = Direction.SOUTH;
				}
			}
			//East or West
			else if(yDelta == 0){
				if(xDelta == 1) {
					retDirection = Direction.EAST;
				}
				else if(xDelta == -1 ) {
					retDirection = Direction.WEST;
				}
				else if(xDelta == 0) {
					retDirection = Direction.UNDEFINED;
				}
			}
			return retDirection;
		}
		
		/**
		 *Return the integer interpretation of a direction in the y direction, ie -1 for south
		 * 
		 * @param dir the Direction to be converted
		 * @return -1,1,0 an integer corresponding to the integer interpretation of the Direction
		 */
		public int convertYDim(Direction dir) {
			if (dir.name().contains("NORTH")) {
				return -1;
			}
			else if(dir.name().contains("SOUTH")) {
				return 1;
			}
			else {
				return 0;
			}
		}
		
		/**
		 *Return the integer interpretation of a direction in the x direction, ie -1 for west
		 * 
		 * @param dir the Direction to be converted
		 * @return -1,1,0 an integer corresponding to the integer interpretation of the Direction
		 */
		public int convertXDim(Direction dir) {
			if (dir.name().contains("WEST")) {
				return -1;
			}
			else if(dir.name().contains("EAST")) {
				return 1;
			}
			else {
				return 0;
			}
		}

		/**
		 * Gets Directions of adjacent GridSpaces containing not EMPTY Items
		 * 
		 * @param xIndex the x index of the GridSpace to be searched around
		 * @param yIndex the y index of the GridSpace to be searched around
		 * @return adjDirections the List of Directions
		 */
		public List<Direction> getAdjacentItemGridDirections(int xIndex, int yIndex) {
			List<Direction> adjDirections = new ArrayList<Direction>();
			// search the 3x3 grid around the space[xIndex][yIndex]
			int radius = 1;
			for(int i = -radius; i <= radius; i++) {
				for(int j = -radius; j <= radius; j++) {
					// skip the space itself
					if(i == 0 && j == 0)
						continue;
					// the target spaces coordinates
					int targetX = xIndex + i;
					int targetY = yIndex + j;
					// boundary conditions
					if( targetX < 0 || targetX >= boardSize)
						continue;
					if(targetY < 0 ||targetY  >= boardSize)
						continue;
					// check the space
					GridSpace space = getSpace(targetX, targetY);
					Item thisItem = space.getItem();
					if( thisItem != Item.EMPTY && thisItem != Item.ALREADYCHECKED)
						adjDirections.add(calculateDirection(xIndex, yIndex, targetX, targetY));
				}
			}
			return adjDirections;
		}
	
		
		/**
		 * Reads estuary question from text file
		 * @return string form of question
		 * @throws FileNotFoundException
		 */
		public String getPowerupQuestion() throws FileNotFoundException {
			String question = "";
			StringBuilder qNum = new StringBuilder();
			// Questions are marked with Q
			qNum.append("Q");
			File questionsFile = new File("questions/powerQuestions.txt");
			// randomly pick a question number 
			int questionNum = generateQuestionNum(totalQuestions);
			// empty the list of already asked questions if all questions have been asked
			if (totalQuestionAsked  == totalQuestions) {
				questionNumsAsked.clear();
				totalQuestionAsked = 0;
			}
			// check to make sure question is not a repeat
			while (questionNumsAsked.contains(questionNum)) {
				questionNum = generateQuestionNum(totalQuestions);
			}
			// keep track of questions asked
			questionNumsAsked.add(questionNum);
			qNum.append(questionNum);
			// another marker for questions
			qNum.append(":");
			// scan the file for that question
			try {
				Scanner fn = new Scanner (questionsFile);
				while(fn.hasNextLine()) {
					String line = fn.nextLine();
					// if the line contains the Q(number):, then add it to the question string builder 
					if(line.contains(qNum.toString())){ 
						int colonPosition = line.indexOf(":") + 1;
						line = line.substring(colonPosition);
						// html is used for multi-line questions.  questions cannot be more than 2 lines long
						question = "<html>" + line;
						String nextLine = fn.nextLine();
						if (nextLine.length() != 0) {
							question = question + "<br/>" + nextLine + "</html>";
						}
						break;
					}
				}
				fn.close();
			} catch (FileNotFoundException e){
				System.out.println(e.getMessage());
			}
			setAnswers(questionNum);
			// keep track of total number of questions asked
			totalQuestionAsked++;
			return question;
		}
		
		/**
		 * read question answers from text file
		 * @param questionNum the question number
		 * @return string form of answer
		 * @throws FileNotFoundException
		 */
		private void setAnswers(int questionNum) throws FileNotFoundException{
			possibleAnswers.clear();
			StringBuilder answer = new StringBuilder(); 
			StringBuilder aNum = new StringBuilder(); 
			StringBuilder caNum = new StringBuilder();
			// wrong answers are marked with an A
			aNum.append("A");
			aNum.append(questionNum);
			aNum.append(":");
			// correct answers are marked with a CA
			caNum.append("CA");
			caNum.append(questionNum);
			caNum.append(":");
			File questionsFile = new File("questions/answers.txt");
			try {
				//scan the file for possible answers
				Scanner fn = new Scanner (questionsFile);
				while(fn.hasNextLine()) {
					String line = fn.nextLine();
					// if the line contains the Answer number add it to the possible answers
					if(line.contains(caNum.toString())){ 
						answer.append(line);
						answer = removeColon(answer);
						possibleAnswers.add(answer.toString());
						setCorrectAnswer(answer.toString());
						answer.setLength(0);
						break;
					}
				}
				fn.close();
				//scan the file for the correct answer
				Scanner fn2 = new Scanner (questionsFile);
				while(fn2.hasNextLine()) {
					String line = fn2.nextLine();
					if(line.contains(aNum.toString()) && !(line.contains(caNum.toString()))){ 
						answer.append(line);
						answer = removeColon(answer);
						possibleAnswers.add(answer.toString());
						answer.setLength(0);
						continue;
					}
				}
				fn2.close();
			} catch (FileNotFoundException e){
				System.out.println(e.getMessage());
			}
		}
		
		/**
		 * Generates random number to select which question to ask user during power up opportunity
		 * @param range a upper limit for the random number
		 * @return random number to pick which question to ask
		 */
		private int generateQuestionNum(int range) {
			int rand = (int)(Math.random() * ((range - 1) + 1)) + 1;
			return rand;
		}
		
		/**
		 * Get the possible answers to the question asked
		 * 
		 * @return possible answers to the question asked
		 */
		public List<String> getPossibleAnswers() {
			return possibleAnswers;
		}
		
		/**
		 * Get the board
		 * 
		 * @return the board
		 */
		public GridSpace[][] getBoard() {
			return board;
		}
		
		/**
		 * Removes answer identifiers ex: A1: from the string
		 * @param possibleAnswer the answer to be editted
		 * @return the answer filtered of its identifier
		 */
		private StringBuilder removeColon(StringBuilder possibleAnswer) {
			StringBuilder filteredAnswer = new StringBuilder();
			int colonPosition = possibleAnswer.indexOf(":") + 1;
			String questionWithoutColon = possibleAnswer.substring(colonPosition);
			filteredAnswer.append(questionWithoutColon);
			return filteredAnswer;
		}
		
		/**
		 * Checked if the answer was correct
		 * 
		 * @param playerAnswer the answer
		 * @return whether or not the player's answer was correct
		 */
		boolean checkAnswer(String playerAnswer) {
			return playerAnswer.equalsIgnoreCase(correctAnswer);
		}
		
		/**
		 *Get the answer correct answer
		 * 
		 * @return the correct answer
		 */
		public String getAnswer() {
			return correctAnswer;
		}
		
		/**
		 *Set the correct answer
		 * 
		 * @param answer the correct answer
		 */
		public void setCorrectAnswer(String answer) {
			correctAnswer = answer;
		}
		
		/**
		 *Get the Difficulty
		 * 
		 * @return the game Difficulty
		 */
		public Difficulty getDifficulty() {
			return difficulty;
		}
}
	