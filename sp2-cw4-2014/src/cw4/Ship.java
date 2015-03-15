/**
 * Software and Programming II
 * Coursework: sp2-cw4-2014
 * 
 * Pete Whelpton 
 * Due Date: 29/03/2015
 * Lecturer: Keith Mannock
 */

package cw4;

import org.junit.Test;

/**
 * Abstract class all concrete ship types inherit from
 * 
 * @author pete
 *
 */
public abstract class Ship {

	/* Properties */
	protected int bowRow;
	protected int bowColumn;
	protected int length;
	protected boolean horizontal;
	protected boolean[] hit;
	protected int printPointer;
	
	/* Methods */
	/* Constructors */
	/**
	 * Constructor for abstract ship
	 */
	public Ship() {
		// Set print pointer used in printing the ocean gameboard
		this.printPointer = 0;
	}
	
	/* Getters and Setters */
	/**
	 * Returns the row number where the ship's bow is located
	 * 
	 * @return Row number of the ship's bow
	 */
	public int getBowRow() {
		return this.bowRow;
	}
	
	/**
	 * Set the row number where the ship's bow is located
	 * 
	 * @param row Row number to locate the ship's bow
	 */
	public void setBowRow(int row) {
		this.bowRow = row;
	}
	
	/**
	 * Returns the column number where the ship's bow is located
	 * 
	 * @return Column number of the ship's bow
	 */
	public int getBowColumn() {
		return this.bowColumn;
	}
	
	/**
	 * Set the column number where the ship's bow is located
	 * 
	 * @param column Column number to locate the ship's bow
	 */
	public void setBowColumn(int column) {
		this.bowColumn = column;
	}
	
	/**
	 * Return whether or not the ship is aligned horizontally
	 * 
	 * @return True if ship is horizontal, false if it is vertical
	 */
	public boolean isHorizontal() {
		return this.horizontal;
	}
	
	/**
	 * Set horizontal alignment of the ship
	 * 
	 * @param horizontal Pass true if ship should be horizontal, false if 
	 * ship should be vertical
	 */
	public void setHorizontal(boolean horizontal) {
		this.horizontal = horizontal;
	}
	
	/**
	 * Return length of the ship in game tiles
	 * 
	 * @return Length of ship
	 */
	public int getLength() {
		return this.length;
	}
	
	/**
	 * Return the name of the class of the ship
	 * 
	 * @return Ship classname
	 */
	public String getShipType() {
		return this.getClass().getSimpleName();
	}
	
	/* Business Logic */
	/**
	 * Check if it is possible to place at ship at the given location
	 * 
	 * @param row Row number for ship's bow
	 * @param column Column number for ship's bow
	 * @param horizontal True if ship is horizontal, false if vertical
	 * @param ocean Ocean gameboard to place ship on
	 * @return True if given location is valid for ship type, otherwise false
	 */
	public boolean okToPlaceShipAt(int row, int column, 
									boolean horizontal, Ocean ocean) {
		
		/* Horizontal Ship */
		if(horizontal) {
			
			// Check if out of bounds
			for(int x = column; x < column + this.getLength(); x++) {
				if(!ocean.isOnBoard(row, x)) {
					return false;
				}
			}
			
		}
		/* Vertical Ship */
		else {
			for(int y = row; y < row + this.getLength(); y++) {
				if(!ocean.isOnBoard(y, column)) {
					return false;
				}
			}
		}
		
		// Variables used in 'walking' around the ship's edge
		int startRow = row - 1;
		int endRow = row + 1;
		int startCol = column -1;
		int endCol = column + 1;
		
		if(horizontal) {
			endCol = column + this.length;
		}
		else {
			endRow = row + this.length;
		}
		
		// Walk around the ship and its neighbouring tiles and check they are
		// not occupied
		for(int x = startRow; x <= endRow; x++) {
			for(int y = startCol; y <= endCol; y++) {
				if(ocean.isOnBoard(x, y)) {
					 if(ocean.isOccupied(x, y)) {
						return false;
					}
				}
			}
		}
		
		// Ship is wholly on the board and not overlapping another ship.  Hooray!
		return true;
	}
	
	/**
	 * Place the ship at the given location on the gameboard
	 * 
	 * @param row Row number to place bow of ship
	 * @param column Column number to place bow of ship
	 * @param horizontal True if ship is horizontal, false if ship is vertical
	 * @param ocean Ocean gameboard to place ship on
	 */
	public void placeShipAt(int row, int column, boolean horizontal, 
							Ocean ocean) {
		
		// Set tiles on gameboard
		if(horizontal) {
			
			// Place horizontal ship
			for(int x = column; x < column + this.length; x++) {
				ocean.setShip(row, x, this);
			}
			
		}
		// Place vertical ship
		else {
			for(int y = row; y < row + this.length; y++) {
				ocean.setShip(y, column, this);
			}
		}
		
		// Set ship properties
		this.bowRow = row;
		this.bowColumn = column;
		this.horizontal = horizontal;
		
	}
	
	/**
	 * Shoot at the given section of a ship
	 * 
	 * @param row Row number shot is aimed at
	 * @param column Column number shot is aimed at
	 * @return True if section of the ship is hit, false if not 
	 * (i.e. ship already sunk)
	 */
	public boolean shootAt(int row, int column) {
		
		// Section of ship being shot at
		int section = 0;
		
		// If ship is sunk, always return false
		if(this.isSunk()) {
			return false;
		}
		
		// Calculate which section has been hit, based on orientation of ship
		if(this.horizontal) {
			section = column - this.bowColumn;
		}
		else{
			section = row - this.bowRow;
		}
		
		// Mark section as hit and return true
		this.hit[section] = true;
		return true;
	}
	
	/**
	 * Return state of ship (sunk / afloat)
	 * 
	 * @return True if ship is sunk, false if ship is still afloat
	 */
	public boolean isSunk() {

		// Check every section, if at least one is intact ship is not sunk
		for(boolean section : hit) {
			if(section == false) {
				return false;
			}
		}
		
		// Otherwise, all sections are destroyed and ship is sunk
		return true;
	}
	
	/**
	 * Returns a single-character String, based on the 
	 * object's state, to use in the Ocean print() method
	 * 
	 * @return Ship sunk = "x", Section hit = "S", Not shot at = "."
	 */
	@Override
	public String toString() {
		
		// Declare variable to hold return value
		String rv = "";
		
		// If ship is sunk, always return "x"
		if(this.isSunk()) {
			rv = "x";
		}
		// Otherwise, check if section at printpointer is hit and return "S"
		// if it has
		else if(this.hit[this.printPointer]) {
			rv = "S";
		}
		// If the ship is neither sunk or the section hit, return the unknown marker
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
	
}
