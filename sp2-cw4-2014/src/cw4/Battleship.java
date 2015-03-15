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
 * Four-tile battleship
 * 
 * @author pete
 *
 */
public class Battleship extends Ship {

	/* Methods */
	/* Constructors */
	/**
	 * Instantiates a new Battleship object
	 */
	public Battleship() {

		// Set length
		this.length = 4;

		// Create and populate hit array with false values, as ship not hit yet!
		this.hit = new boolean[this.length];
		for (boolean section : hit) {
			section = false;
		}

	}

}
