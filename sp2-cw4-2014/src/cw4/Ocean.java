/**
 * Software and Programming II
 * Coursework: sp2-cw4-2014
 * 
 * Pete Whelpton 
 * Due Date: 29/03/2015
 * Lecturer: Keith Mannock
 */

package cw4;

import java.awt.Point;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;
import java.util.Stack;
import java.util.StringJoiner;

/**
 * The game board
 * <p>
 * A 10 x 10 array of ships, representing the ocean
 * 
 * @author pete
 *
 */
public class Ocean {

	/* Properties */
	private Ship ships[][] = new Ship[10][10];
	private int shotsFired = 0;
	private int hitCount = 0;
	private int shipsSunk = 0;
	final private int fleetSize = 10;
	
	/* Methods */
	/* Constructors */
	/**
	 * Instantiates a new Ocean object
	 */
	public Ocean() {
		
		// Populate each tile with EmptySea
		for (int row = 0; row < this.ships.length; row++) {  
		    for (int tile = 0; tile < this.ships[row].length; tile++) {  
		        this.ships[row][tile] = new EmptySea();
		    }
		}
	}
	
	/* Getters and Setters */
	/**
	 * Return number of shots fired
	 * 
	 * @return Number of shots fired
	 */
	public int getShotsFired() {
		return this.shotsFired;
	}
	
	/**
	 * Return number of hits
	 * 
	 * @return Number of hits
	 */
	public int getHitCount() {
		return this.hitCount;
	}
	
	/**
	 * Return number of ships sunk
	 * 
	 * @return Number of ships sunk
	 */
	public int getShipsSunk() {
		return this.shipsSunk;
	}
	
	/**
	 * Return the gameboard (ships array)
	 * 
	 * @return Two dimensional array of ships
	 */
	public Ship[][] getShipArray() {
		return this.ships;
	}
	
	/**
	 * Place a ship on the board
	 * 
	 * @param row Row number location for bow of ship
	 * @param column Column number location for bow of ship
	 * @param ship Ship type to place
	 */
	public void setShip(int row, int column, Ship ship) {
		this.ships[row][column] = ship;
	}
	
	
	/* Business Logic */
	/**
	 * Places all ships randomly on the game board
	 * <p>
	 * Creates a fleet 'stack' and passes this to the recurse placeShip method
	 */
	public void placeAllShipsRandomly() {
		
		/* Build stack of ships */
		Stack<Ship> fleet = new Stack<Ship>();
		fleet.push(new Submarine());
		fleet.push(new Submarine());
		fleet.push(new Submarine());
		fleet.push(new Submarine());
		fleet.push(new Destroyer());
		fleet.push(new Destroyer());
		fleet.push(new Destroyer());
		fleet.push(new Cruiser());
		fleet.push(new Cruiser());
		fleet.push(new Battleship());

		/* Place these ships */
		this.placeShip(fleet);
		
	}
	
	/**
	 * Place ships until no ships left to place
	 * <p>
	 * Recursive method that uses backtracking to ensure all ships are placed
	 * on the game board in valid locations
	 * 
	 * @param fleet Stack of ships to be place
	 */
	private void placeShip(Stack<Ship> fleet) {
		
		// No ships left to place - return 
		if(fleet.empty()) {
			return;
		}
		
		// Get ship to place
		Ship shipToPlace = fleet.pop();
		
		// Get list of empty spaces and shuffle to randomise attempts at placing
		// ships
		List<Point> emptyTiles = this.getEmptyTiles();
		Collections.shuffle(emptyTiles);
		
		// Iterate through every empty tile, trying to place the ship
		for(Point tile : emptyTiles) {
			
			// Get a random value for horizontal
			Random random = new Random();
			boolean horizontal = random.nextBoolean();
			
			// Get co-ordinates for attempt at placing ship
			int row = (int) tile.getY();
			int column = (int) tile.getX();
			
			// Attempt to place ship
			if(shipToPlace.okToPlaceShipAt(row, column, horizontal, this)) {
				shipToPlace.placeShipAt(row, column, horizontal, this);
				this.placeShip(fleet);
				break;
			}
			// Attempt failed, so flip ship orientation and try again!
			else if(shipToPlace.okToPlaceShipAt(row, column, !horizontal, this)) {
				shipToPlace.placeShipAt(row, column, !horizontal, this);
				this.placeShip(fleet);
				break;
			}
			
		}
		
	}
	
	/**
	 * Return the co-ordinates empty tiles (EmptySea objects) on the board
	 * <p>
	 * Helper method used in placing the ships
	 * 
	 * @return List of empty sea co-ordinates
	 */
	public List<Point> getEmptyTiles() {
		
		// Create new list to hold co-ordinates
		List<Point> emptyTiles = new ArrayList<Point>();
		
		// Iterate every tile looking for empty ones
		for (int row = 0; row < this.ships.length; row++) {  
		    for (int tile = 0; tile < this.ships[row].length; tile++) {  
		        if(!isOccupied(row, tile)) {
		        	// Location does not a have a ship, add to list
		        	emptyTiles.add(new Point(tile, row));
		        }
		    }  
		}
		
		return emptyTiles;
		
	}
	
	/**
	 * Check if a co-ordinate has a ship in that location
	 * 
	 * @param row Row number to check 
	 * @param column Column number to check
	 * @return True if a ship exists at that location, false if it is empty sea
	 */
	public boolean isOccupied(int row, int column) {
		
		// Get the correct value of an empty tile
		final String EMPTY = new EmptySea().getShipType();
		
		// Check if the tile in question is occupied, return false if it is
		if(this.ships[row][column].getShipType().equals(EMPTY)) {
			return false;
		}
		
		// Otherwise, tile is empty so return true
		return true;
	}
	
	/**
	 * Check if co-ordinate is a valid game tile (on the gameboard)
	 * 
	 * @param row Row number to check
	 * @param column Column number to check
	 * @return True if co-ordinate is a tile on the gameboard, otherwise false
	 */
	public boolean isOnBoard(int row, int column) {
		
		// Constant to hold the min row/column value (assumes we will never 
		// have negative numbers on the board!
		final int MIN_VALUE = 0;
		
		// Check if row is out of bounds
		if(row < MIN_VALUE || row >= this.ships.length) {
			return false;
		}
		
		// Check if column is out of bounds
		if(column < MIN_VALUE || column >= this.ships[0].length) {
			return false;
		}
		
		// Row and Column both ok, so return true
		return true;
	}
	
	/**
	 * Shoot at the ship at the give co-ordinates
	 * 
	 * @param row Row number to shoot at
	 * @param column Column number to shoot at 
	 * @return True if shot is a hit, false if it is a miss
	 */
	public boolean shootAt (int row, int column) {
		
		// Check shot is on the board, and throw error if not
		if(!this.isOnBoard(row, column)) {
			throw new IllegalArgumentException("Tile " + column + ", " + row
					+ " is not on the board");
		}
		
		// Shoot at the ship tile and increment shots fired and ships sunk
		Ship target = this.ships[row][column];
		boolean hit = target.shootAt(row, column);
		if(hit) {
			if(target.isSunk()) {
				System.out.println("You sank a " + target.getShipType() + "!");
				this.shipsSunk++;
			}
		}
		this.shotsFired++;
		
		// Return result
		return hit;

	}
	
	/**
	 * Check if game is over
	 * <p>
	 * Compares number of ships to the fleet size
	 * 
	 * @return True if all ships sunk, false if there are still ships afloat
	 */
	public boolean isGameOver() {
		
		// Check if all ships sunk and return true if they have been
		if(this.shipsSunk == this.fleetSize) {
			return true;
		}
		
		//Otherwise, return false
		return false;
		
	}

	/**
	 * Print the game board to the console
	 */
	public void print() {
		
		// Print line space
		System.out.println();
		
		// Print column headers
		StringJoiner headers = new StringJoiner(" ");
		for(int i = 0; i < this.ships[0].length; i++) {
			headers.add(Integer.toString(i));
		}
		System.out.print("  ");
		System.out.println(headers);
		
		// Print board tiles
		for (int row = 0; row < this.ships.length; row++) {  
		    System.out.print(row + " ");
			for (int tile = 0; tile < this.ships[row].length; tile++) {  
		    	System.out.print(this.ships[row][tile]);
		    	System.out.print(" ");
		    }   
			
			// Line space
		    System.out.println();
		}
		
		// Line space
		System.out.println();
		
	}

}
