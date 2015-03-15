/**
 * Software and Programming II
 * Coursework: sp2-cw4-2014
 * 
 * Pete Whelpton 
 * Due Date: 29/03/2015
 * Lecturer: Keith Mannock
 */

package cw4;

/**
 * One-tile empty sea. 
 * <p>
 * Behaves slightly differently to other ships.
 * 
 * @author pete
 *
 */
public class EmptySea extends Ship {

	/* Methods */
	/* Constructors */
	/**
	 * Instantiates a new EmptySea object
	 */
	public EmptySea() {
		
		// Set length
		this.length = 1;
		
		// Create and populate hit array with false values, as ship not hit yet!
		this.hit = new boolean[this.length];
		for(boolean section : hit) {
			section = false;
		}
		
	}
	
	/**
	 * Shooting at empty sea always returns false (a miss).
	 * <p>
	 * The hit array is still set to true, so when the board is printed the 
	 * user knows that they fired at this location.
	 * 
	 * @param row Row being shot at 
	 * @param column Column being shot at
	 * @return Always false (can't hit empty sea!)
	 */
	@Override
	public boolean shootAt(int row, int column) {
		
		// Set hit array to true (hard coded to only possible location) and
		// return false
		this.hit[this.length - 1] = true;
		return false;
		
	}
	
	/**
	 * Returns a single-character String, based on the 
	 * object's state, to use in the Ocean print() method
	 * 
	 * @return Hit = "-", Not shot at = "."
	 */
	@Override
	public String toString() {
		
		// Declare variable to hold return value
		String rv = "";
		
		// Check if section at the print pointer has been hit, and return result
		// "-" if it is hit, "." if not
		if(this.hit[this.printPointer]) {
			rv = "-";
		}
		else{
			rv = ".";
		}
		
		// Increment the print pointer, so the next time method is called,
		// it will return the value for the next section
		this.printPointer++;
		
		// If the state of all sections has been returned, reset the print
		// pointer ready for the next turn
		if(printPointer == this.length) {
			this.printPointer = 0;
		}
		
		return rv;
		
	}
	
	/**
	 * Returns the floating/sunk state of the ship
	 * <p>
	 * Empty sea cannot be sunk, so always returns false
	 * 
	 * @return Always returns false (not sunk)
	 */
	@Override
	public boolean isSunk() {
		return false;
	}

}
