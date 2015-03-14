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
	public boolean shootAt(int row, int column) {
		this.hit[this.length - 1] = true;
		return false;
	}
	
	@Override
	public String toString() {
		
		String rv = "";
		
		if(this.hit[this.printPointer]) {
			rv = "-";
		}
		else{
			rv = ".";
		}
		
		this.printPointer++;
		if(printPointer == this.length) {
			this.printPointer = 0;
		}
		
		return rv;
		
	}
	
	@Override
	public boolean isSunk() {
		return false;
	}

}
