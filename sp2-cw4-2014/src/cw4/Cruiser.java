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
 * Three-tile cruiser
 * 
 * @author pete
 *
 */
public class Cruiser extends Ship {

	/* Methods */
	/* Constructors */
	/**
	 * Instantiates a new Cruiser object
	 */
	public Cruiser() {

		// Set length
		this.length = 3;

		// Create and populate hit array with false values, as ship not hit yet!
		this.hit = new boolean[this.length];
		for (boolean section : hit) {
			section = false;
		}

	}

}
