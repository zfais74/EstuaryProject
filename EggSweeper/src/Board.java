import java.io.File;
import java.io.FileNotFoundException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.Scanner;

import enums.Item;
import enums.Direction;
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
		private Difficulty difficulty;
		private List<String>possibleAnswers = new ArrayList<>();
		
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
		private List<Integer> questionNumsAsked = new ArrayList<Integer>();
		private String correctAnswer;
		private int totalQuestions = 10;
		
		
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
		 * gets the number of items surrounding a space
		 * @param xIndex x index of the grid space to be set
		 * @param yIndex y index of the grid space to be set
		 * @return items surrounding
		 */
		public int countAdjacentItems(int xIndex, int yIndex) {
			int count = 0;
			int radius = 1;
			if(xIndex < 0 || yIndex < 0 || xIndex >= boardSize || yIndex >= boardSize) {
				System.out.println(String.format("Called countAdjacentItems with incorrect arguments (%d,%d)", xIndex, yIndex));
			}
			else {
				for(int i = -radius; i <= radius; i++) {
					for(int j = -radius; j <= radius; j++) {
						if(i == 0 && j == 0)
							continue;
						if(xIndex + i < 0 || xIndex + i >= boardSize)
							continue;
						if(yIndex + j < 0 || yIndex + j >= boardSize)
							continue;
						GridSpace space = getSpace(xIndex + i, yIndex + j);
						Item thisItem = space.getItem();
						if( thisItem != Item.EMPTY && thisItem != Item.ALREADYCHECKED)
							count++;
					}
				}
			}
			return count;
		}
		
		private Direction calculateDirection(int xIndex, int yIndex, int targetX, int targetY) {
			Direction retDirection = Direction.UNDEFINED;
			int xDelta = targetX - xIndex;
			int yDelta = targetY - yIndex;
			//If the given arguments are greater than 1 gridspace away return 
			if(Math.abs(xDelta) > 1 || Math.abs(yDelta) > 1) {
				System.out.println(String.format("Target Arguments out of range current Loc: (%d,%d) to end Loc (%d, %d)",  xIndex,  yIndex,  targetX,  targetY));
			}
			//Otherwise calculate the direction
			else {
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
						System.out.println(String.format("Target Arguments are identical current Loc: (%d,%d) to end Loc (%d, %d)",  xIndex,  yIndex,  targetX,  targetY));
					}
				}
			}
			return retDirection;
		}
		
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
		
		public List<Direction> getAdjacentItemGridDirections(int xIndex, int yIndex) {
			List<Direction> adjDirections = new ArrayList<Direction>();
			int radius = 1;
			if(xIndex < 0 || yIndex < 0 || xIndex >= boardSize || yIndex >= boardSize) {
				System.out.println(String.format("Called countAdjacentItems with incorrect arguments (%d,%d)", xIndex, yIndex));
			}
			else {
				for(int i = -radius; i <= radius; i++) {
					for(int j = -radius; j <= radius; j++) {
						if(i == 0 && j == 0)
							continue;
						int targetX = xIndex + i;
						int targetY = yIndex + j;
						if( targetX < 0 || targetX >= boardSize)
							continue;
						if(targetY < 0 ||targetY  >= boardSize)
							continue;
						GridSpace space = getSpace(targetX, targetY);
						Item thisItem = space.getItem();
						if( thisItem != Item.EMPTY && thisItem != Item.ALREADYCHECKED && space.getIsCovered())
							adjDirections.add(calculateDirection(xIndex, yIndex, targetX, targetY));
					}
				}
			}
			return adjDirections;
		}
		
		
		public List<Direction> getAdjacentItemGridDirections(int xIndex, int yIndex, Item targetItem) {
			List<Direction> adjDirections = new ArrayList<Direction>();
			int radius = 1;
			if(xIndex < 0 || yIndex < 0 || xIndex >= boardSize || yIndex >= boardSize) {
				System.out.println(String.format("Called countAdjacentItems with incorrect arguments (%d,%d)", xIndex, yIndex));
			}
			else {
				for(int i = -radius; i <= radius; i++) {
					for(int j = -radius; j <= radius; j++) {
						if(i == 0 && j == 0)
							continue;
						int targetX = xIndex + i;
						int targetY = yIndex + j;
						if( targetX < 0 || targetX >= boardSize)
							continue;
						if(targetY < 0 ||targetY  >= boardSize)
							continue;
						GridSpace space = getSpace(targetX, targetY);
						Item thisItem = space.getItem();
						if(thisItem == targetItem && space.getIsCovered())
							adjDirections.add(calculateDirection(xIndex, yIndex, targetX, targetY));
					}
				}
			}
			return adjDirections;
		}
		
		/**
		 * read estuary question from text file
		 * @return string form of question
		 * @throws FileNotFoundException
		 */
		public String getPowerupQuestion() throws FileNotFoundException {
			possibleAnswers.clear();
			String question = "";
			StringBuilder qNum = new StringBuilder(); // string builder for the question number
			qNum.append("Q"); //marker for questions
			File questionsFile = new File("questions/powerQuestions.txt");
			int questionNum = generateQuestionNum(totalQuestions);
			while (questionNumsAsked.contains(questionNum)) {
				questionNum = generateQuestionNum(totalQuestions);
			}
			this.questionNumsAsked.add(questionNum);
			qNum.append(questionNum);
			qNum.append(":"); // another marker for questions
			try {
				Scanner fn = new Scanner (questionsFile);
				while(fn.hasNextLine()) {
					String line = fn.nextLine();
					if(line.contains(qNum.toString())){ // if the line contains the Q(number):, then add it to the question string builder 
						int colonPosition = line.indexOf(":") + 1;
						line = line.substring(colonPosition);
						question = "<html>" + line;
						String nextLine = fn.nextLine();
						if (nextLine.length() != 0) {
							question = question + "<br/>" + nextLine + "</html>";
						}
						System.out.println("Selected Question:" + question.toString());
						break;
					}
				}
				fn.close();
			} catch (FileNotFoundException e){
				System.out.println(e.getMessage());
				System.exit(0);
			}
			setAnswers(questionNum);
			//question = removeColon(question);
			return question;
		}
		
		/**
		 * Generates random number to select which question to ask user during power up opportunity
		 * @param range a upper limit for the random number
		 * @return random number to pick which question to ask
		 */
		private int generateQuestionNum(int range) {
			int rand = (int)(Math.random() * ((range - 1) + 1)) + 1;
			System.out.println("rand = " + Integer.toString(rand));
			return rand;
		}
		
		/**
		 * read question answer from text file
		 * @param questionNum
		 * @return string form of answer
		 * @throws FileNotFoundException
		 */
		private void setAnswers(int questionNum) throws FileNotFoundException{
			StringBuilder answer = new StringBuilder(); 
			StringBuilder aNum = new StringBuilder(); 
			StringBuilder caNum = new StringBuilder();
			aNum.append("A"); //marker for questions
			aNum.append(questionNum);
			aNum.append(":");
			caNum.append("CA"); //marker for questions
			caNum.append(questionNum);
			caNum.append(":");
			File questionsFile = new File("questions/answers.txt");
			try {
				Scanner fn = new Scanner (questionsFile);
				while(fn.hasNextLine()) {
					String line = fn.nextLine();
					if(line.contains(caNum.toString())){ 
						answer.append(line);
						answer = removeColon(answer);
						this.possibleAnswers.add(answer.toString());
						this.correctAnswer = answer.toString();
						answer.setLength(0);
						break;
					}
				}
				fn.close();
				Scanner fn2 = new Scanner (questionsFile);
				while(fn2.hasNextLine()) {
					String line = fn2.nextLine();
					if(line.contains(aNum.toString()) && !(line.contains(caNum.toString()))){ 
						answer.append(line);
						answer = removeColon(answer);
						this.possibleAnswers.add(answer.toString());
						answer.setLength(0);
						continue;
					}
				}
				fn2.close();
			} catch (FileNotFoundException e){
				System.out.println(e.getMessage());
				System.exit(0);
			}
		}
		
		public List<Integer> getQuestionsAsked(){
			return this.questionNumsAsked;
		}
		
		public List<String> getPossibleAnswers() {
			return this.possibleAnswers;
		}
		
		public GridSpace[][] getBoard() {
			return this.board;
		}
		
		/**
		 * Removes question and answer identifiers ex: A1: & Q1: from the string
		 * @param possibleAnswer
		 * @return
		 */
		private StringBuilder removeColon(StringBuilder possibleAnswer) {
			StringBuilder filteredAnswer = new StringBuilder();
			int colonPosition = possibleAnswer.indexOf(":") + 1;
			String questionWithoutColon = possibleAnswer.substring(colonPosition);
			filteredAnswer.append(questionWithoutColon);
			return filteredAnswer;
		}
		
		boolean checkAnswer(String playerAnswer) {
			return playerAnswer.equalsIgnoreCase(correctAnswer);
		}
		
		public String getAnswer() {
			return this.correctAnswer;
		}
}
	