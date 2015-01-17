package cw4;

public class EmptySea extends Ship {

	/* Methods */
	/* Constructors */
	public EmptySea() {
		
		// Set length
		this.length = 1;
		
		// Create and populate hit array with false values, as ship not hit yet!
		this.hit = new boolean[this.length];
		for(boolean section : hit) {
			section = false;
		}
		
		
	}
	
	@Override
	public String toString() {
		return "~";
	}
	
	@Override
	public boolean isSunk() {
		return false;
	}

}
