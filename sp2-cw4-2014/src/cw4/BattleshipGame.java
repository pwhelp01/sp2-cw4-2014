package cw4;

import java.util.Scanner;

public class BattleshipGame {

	final private int MIN_ROW = 0;
	final private int MAX_ROW = 9;
	final private int MIN_COLUMN = 0;
	final private int MAX_COLUMN = 9;
	
	public BattleshipGame() {
		
	}

	public static void main(String[] args) {
		
		// Instantiate a new Battleships game and start in running
		BattleshipGame battleships = new BattleshipGame();
		battleships.run();
		
	}
	
	public void run() {
		
		boolean keepPlaying = false;
		
		this.printGreeting();
		keepPlaying = this.readPlayAgain();
		while(keepPlaying) {
			this.gameLoop();
			keepPlaying = this.readPlayAgain();
		}
		this.printGoodbye();
		
	}
	
	private void gameLoop() {
		
		Ocean ocean = new Ocean();
		ocean.placeAllShipsRandomly();
		
		do{
			ocean.print2();
			int column = readInt("Fire at column: ", this.MIN_COLUMN, this.MAX_COLUMN);
			int row = readInt("Fire at row: ", this.MIN_ROW, this.MAX_ROW);
			System.out.println();
			System.out.println("You fired at (" + column + "," + row + ")");
			
			if(ocean.shootAt(row, column)) {
				System.out.println("Hit!");
			}
			else{
				System.out.println("Miss!");
			}
			
			// Sleep for a second so user can read result of shot
			try {
				Thread.sleep(1000);
			}
			catch(InterruptedException e) {
				
			}
			
		} while(!ocean.isGameOver());
		
		System.out.println();
		System.out.println("Congratulations!  You sunk all ships in " 
				+ ocean.getShotsFired() + " shots.");
		
	}
	
	private int readInt(final String PROMPT, final int MIN, final int MAX) {
		
		int rv = 0;
		boolean sentinel = false;
		
		do {
			try {
				Scanner in = new Scanner(System.in);
				System.out.print(PROMPT);
				rv = in.nextInt();
				
				if(rv < MIN || rv > MAX) {
					throw new Exception();
				}
				
				sentinel = true;
				
			}
			catch (Exception e) {
				System.out.println("Invalid input!  Please enter a number between "
						+ MIN + " and " + MAX);
			}
		} while(!sentinel);
		
		return rv;
		
	}
	
	private void printGreeting() {
		
		System.out.println();
		System.out.println("Welcome to Battleships!");
		System.out.println("=======================");
		
		System.out.println();
		System.out.println("Software and Programming II Coursework");
		System.out.println("Submitted by: Pete Whelpton (pwhelp01)");
		System.out.println();
		
	}
	
	private void printGoodbye() {
		
		System.out.println();
		System.out.println("Thank you for playing!");
		
	}
	
	private boolean readPlayAgain() {
		
		boolean rv = false;
		boolean sentinel = false;
		
		
		do {
			try {
				Scanner in = new Scanner(System.in);
				System.out.println();
				System.out.print("New game? (y/n): ");
				String input = in.next();
				
				if(input.equalsIgnoreCase("y")) {
					rv = true;
					sentinel = true;
				}
				else if(input.equalsIgnoreCase("n")) {
					rv = false;
					sentinel = true;
				}
				else {
					throw new Exception();
				}
			}
			catch(Exception e) {
				System.out.println("Invalid input.  Please try again.");
			}
			
		} while(!sentinel);
		
		return rv;
		
	}

}
