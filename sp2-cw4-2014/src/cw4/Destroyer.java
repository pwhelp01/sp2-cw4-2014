package cw4;

public class Destroyer extends Ship {

	/* Methods */
	/* Constructors */
	public Destroyer() {
		
		// Set length
		this.length = 2;
		
		// Create and populate hit array with false values, as ship not hit yet!
		this.hit = new boolean[this.length];
		for(boolean section : hit) {
			section = false;
		}
		
		
	}
	
	@Override
	public String toString() {
		return "D";
	}

}
