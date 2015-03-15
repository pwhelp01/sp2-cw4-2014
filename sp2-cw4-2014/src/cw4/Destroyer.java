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
 * Two-tile destroyer
 * 
 * @author pete
 *
 */
public class Destroyer extends Ship {

	/* Methods */
	/* Constructors */
	/**
	 * Instantiates a new Destroyer object
	 */
	public Destroyer() {

		// Set length
		this.length = 2;

		// Create and populate hit array with false values, as ship not hit yet!
		this.hit = new boolean[this.length];
		for (boolean section : hit) {
			section = false;
		}

	}

}
