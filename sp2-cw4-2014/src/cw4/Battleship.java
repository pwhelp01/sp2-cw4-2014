package cw4;

public class Battleship extends Ship {

	/* Methods */
	/* Constructors */
	public Battleship() {
		
		// Set length
		this.length = 4;
		
		// Create and populate hit array with false values, as ship not hit yet!
		this.hit = new boolean[this.length];
		for(boolean section : hit) {
			section = false;
		}
		
		
	}
	
	@Override
	public String toString() {
		return "B";
	}

	
}
