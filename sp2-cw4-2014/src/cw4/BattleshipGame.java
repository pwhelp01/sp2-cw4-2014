package cw4;

import java.util.Scanner;

public class BattleshipGame {

	public BattleshipGame() {
		
	}

	public static void main(String[] args) {
		
		// Instantiate a new Battleships game and start in running
		BattleshipGame battleships = new BattleshipGame();
		battleships.run();
		
	}
	
	public void run() {
		
		this.printGreeting();
		System.out.println(this.getPlayAgain());
		this.printGoodbye();
		
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
	
	private boolean getPlayAgain() {
		
		boolean rv = false;
		boolean sentinel = false;
		Scanner in = new Scanner(System.in);
		
		do {
			try {
				
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
				in.nextLine();
			}
			
		} while(!sentinel);
		
		in.close();
		in = null;
		
		return rv;
		
	}

	
}
