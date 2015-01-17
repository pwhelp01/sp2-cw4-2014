package cw4;

public class Submarine extends Ship {

	/* Methods */
	/* Constructors */
	public Submarine() {
		
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
		return "S";
	}

}
