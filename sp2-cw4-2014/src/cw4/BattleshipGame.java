/**
 * Software and Programming II
 * Coursework: sp2-cw4-2014
 * 
 * Pete Whelpton 
 * Due Date: 29/03/2015
 * Lecturer: Keith Mannock
 */

package cw4;

import java.util.Scanner;

/**
 * Main class for the Battleships Game
 * 
 * @author pete
 *
 */
public class BattleshipGame {

	/* Properties */
	// Constants containing min/max values for user input,
	// ideally these would have come from a configuration file...
	final private int MIN_ROW = 0;
	final private int MAX_ROW = 9;
	final private int MIN_COLUMN = 0;
	final private int MAX_COLUMN = 9;

	/* Methods */
	/* Constructors */
	/**
	 * Instantiates a new BattleshipGame object
	 */
	public BattleshipGame() {

	}

	/**
	 * Entry point of the program.
	 * <p>
	 * Instantiates a new BattleshipGame and starts playing
	 * 
	 * @param args Command line parameters - not used
	 */
	public static void main(String[] args) {

		// Instantiate a new Battleships game and start it running
		BattleshipGame battleships = new BattleshipGame();
		battleships.startPlaying();

	}

	/**
	 * Outer game loop.
	 * <p>
	 * Welcome user.  Play games until user doesn't want to play anymore.
	 * Print goodbye message.
	 */
	public void startPlaying() {

		// Variable to check
		boolean keepPlaying = false;

		// Print greet message and ask user if the want to play a game
		this.printGreeting();
		
		// 'Read ahead' while loop.  Keep playing games until user answers 'n' or 'N'
		keepPlaying = this.readPlayAgain();
		while (keepPlaying) {
			this.gameLoop();
			keepPlaying = this.readPlayAgain();
		}
		
		// Print goodbye message
		this.printGoodbye();

	}

	/**
	 * Inner game loop.
	 * <p>
	 * The 'main' game loop for an individual game.  Display board, get user
	 * move, calculate and display results.  Repeat until 'Game Over'
	 */
	private void gameLoop() {

		// Instantiate a new game board with randomly placed ships.
		Ocean ocean = new Ocean();
		ocean.placeAllShipsRandomly();

		// Game loop - keep looping until game over
		do {
			
			// Print current state of game board
			ocean.print();
			
			// Prompt user for which move (column, row) they want to make
			int column = readInt("Fire at column: ", this.MIN_COLUMN,
					this.MAX_COLUMN);
			int row = readInt("Fire at row: ", this.MIN_ROW, this.MAX_ROW);
			
			// Inform user where they have fired
			System.out.println();
			System.out.println("You fired at (" + column + "," + row + ")");

			// Check if a ship is hit and inform the user
			if (ocean.shootAt(row, column)) {
				System.out.println("Hit!");
			} else {
				System.out.println("Miss!");
			}

			// Sleep for a second so user has time to read result of shot
			try {
				Thread.sleep(1000);
			} catch (InterruptedException e) {
				// No other threads running that could interrupt- so catch is
				// superfluous
			}

		} while (!ocean.isGameOver()); // Check if all ships sunk

		// Game over, so print congratulations message and how many ships sunk
		System.out.println();
		System.out.println("Congratulations!  You sank all ships in "
				+ ocean.getShotsFired() + " shots.");

	}

	/**
	 * Prompt the user to input an integer value and display the result
	 * 
	 * @param PROMPT Message to display to user
	 * @param MIN Minimum acceptable input value
	 * @param MAX Maximum acceptable input value
	 * @return User input
	 */
	private int readInt(final String PROMPT, final int MIN, final int MAX) {

		// Declare local variables (return value and loop sentinel)
		int rv = 0;
		boolean sentinel = false;

		// Loop until user inputs an acceptable value
		do {
			try {
				// Create new scanner object (try-with-resources)
				Scanner in = new Scanner(System.in);
				
				// Prompt for and read user input
				System.out.print(PROMPT);
				rv = in.nextInt();
				
				// Throw an exception if user input is invalid
				if (rv < MIN || rv > MAX) {
					throw new Exception();
				}

				// Acceptable input, so we can abort loop
				sentinel = true;

			} 
			// Handle invalid input
			catch (Exception e) {
				
				// Print error message to user
				System.out
						.println("Invalid input!  Please enter a number between "
								+ MIN + " and " + MAX);
			}
		} while (!sentinel);

		return rv;

	}

	/**
	 * Print greeting message to user
	 */
	private void printGreeting() {

		System.out.println();
		System.out.println("Welcome to Battleships!");
		System.out.println("=======================");

		System.out.println();
		System.out.println("Software and Programming II Coursework");
		System.out.println("Submitted by: Pete Whelpton (pwhelp01)");
		System.out.println();

	}

	/**
	 * Print goodbye message to user
	 */
	private void printGoodbye() {

		System.out.println();
		System.out.println("Thank you for playing!");

	}

	/**
	 * Find out if user wants to play another game
	 * 
	 * @return True if the user wants to play again, otherwise false
	 */
	private boolean readPlayAgain() {

		// Declare local variables (return value and loop sentinel)
		boolean rv = false;
		boolean sentinel = false;

		// Loop until user inputs an acceptable value
		do {
			try {
				// Create new scanner object (try-with-resources)
				Scanner in = new Scanner(System.in);
				
				// Prompt for and read user input
				System.out.println();
				System.out.print("New game? (y/n): ");
				String input = in.next();

				// Throw an exception if user input is invalid
				if (input.equalsIgnoreCase("y")) {
					rv = true;
				} else if (input.equalsIgnoreCase("n")) {
					rv = false;
				} else {
					throw new Exception();
				}
				
				// Acceptable input, so we can abort loop
				sentinel = true;
				
			} 
			// Handle invalid input
			catch (Exception e) {
				// Print error message to user
				System.out.println("Invalid input.  Please try again.");
			}

		} while (!sentinel);

		return rv;

	}

}
