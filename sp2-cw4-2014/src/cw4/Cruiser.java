package cw4;

public class Cruiser extends Ship {

	/* Methods */
	/* Constructors */
	public Cruiser() {
		
		// Set length
		this.length = 3;
		
		// Create and populate hit array with false values, as ship not hit yet!
		this.hit = new boolean[this.length];
		for(boolean section : hit) {
			section = false;
		}
		
		
	}
	
	/*
	@Override
	public String toString() {
		return "C";
	}
	*/
	
}
