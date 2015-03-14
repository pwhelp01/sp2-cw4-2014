package cw4;

import java.awt.Point;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;
import java.util.Stack;
import java.util.StringJoiner;

public class Ocean {

	/* Properties */
	private Ship ships[][] = new Ship[10][10];
	private int shotsFired = 0;
	private int hitCount = 0;
	private int shipsSunk = 0;
	final private int fleetSize = 10;
	
	/* Methods */
	/* Constructors */
	public Ocean() {
		
		// Populate each tile with EmptySea
		for (int row = 0; row < this.ships.length; row++) {  
		    for (int tile = 0; tile < this.ships[row].length; tile++) {  
		        this.ships[row][tile] = new EmptySea();
		    }
		}
	}
	
	/* Getters and Setters */
	public int getShotsFired() {
		return this.shotsFired;
	}
	
	public int getHitCount() {
		return this.hitCount;
	}
	
	public int getShipsSunk() {
		return this.shipsSunk;
	}
	
	public Ship[][] getShipArray() {
		return this.ships;
	}
	
	public void setShip(int row, int column, Ship ship) {
		this.ships[row][column] = ship;
	}
	
	
	/* Business Logic */
	public void placeAllShipsRandomly() {
		
		/* Build array of ships */
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

		
		this.placeShip(fleet);
		
	}
	
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
	
	public List<Point> getEmptyTiles() {
		
		List<Point> emptyTiles = new ArrayList<Point>();
		
		// Iterate every tile looking for empty ones
		for (int row = 0; row < this.ships.length; row++) {  
		    for (int tile = 0; tile < this.ships[row].length; tile++) {  
		        if(!isOccupied(row, tile)) {
		        	emptyTiles.add(new Point(tile, row));
		        }
		    }  
		}
		
		return emptyTiles;
		
	}
	
	
	public boolean isOccupied(int row, int column) {
		
		// Check is on board?
		
		// Get the correct value of an empty tile
		final String EMPTY = new EmptySea().toString();
		
		// Check if the tile in question is occupied, return false if it is
		if(this.ships[row][column].toString().equals(EMPTY)) {
			return false;
		}
		
		// Otherwise, tile is empty so return true
		return true;
	}
	
	public boolean isOnBoard(int row, int column) {
		
		// Check if row is out of bounds
		if(row < 0 || row >= this.ships.length) {
			return false;
		}
		
		// Check if column is out of bounds
		if(column <0 || column >= this.ships[0].length) {
			return false;
		}
		
		// Row and Column both ok, so return true
		return true;
	}
	
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
				System.out.println("You sunk a " + target.getShipType() + "!");
				this.shipsSunk++;
			}
		}
		this.shotsFired++;
		
		// Return result
		return hit;

	}
	
	public boolean isGameOver() {
		
		if(this.shipsSunk == this.fleetSize) {
			return true;
		}
		
		return false;
		
	}
	
	public void print() {
		
		// Print column headers 
		
		// Print array data
		for (int row = 0; row < this.ships.length; row++) {  
		    for (int tile = 0; tile < this.ships[row].length; tile++) {  
		        System.out.print(this.ships[row][tile]);
		    }  
		    
		    System.out.println();
		}
	}
	
	public void print2() {
		
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
		    
		    System.out.println();
		}
		
		System.out.println();
		
	}
	

}
