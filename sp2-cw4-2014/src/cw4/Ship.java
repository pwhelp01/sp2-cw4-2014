package cw4;

public abstract class Ship {

	/* Properties */
	protected int bowRow;
	protected int bowColumn;
	protected int length;
	protected boolean horizontal;
	protected boolean[] hit;
	protected int printPointer = 0;
	
	/* Methods */
	/* Constructors */
	public Ship() {
		// TODO Auto-generated constructor stub
	}
	
	/* Getters and Setters */
	public int getBowRow() {
		return this.bowRow;
	}
	
	public void setBowRow(int row) {
		this.bowRow = row;
	}
	
	public int getBowColumn() {
		return this.bowColumn;
	}
	
	public void setBowColund(int column) {
		this.bowColumn = column;
	}
	
	public boolean isHorizontal() {
		return this.horizontal;
	}
	
	public void setHorizontal(boolean horizontal) {
		this.horizontal = horizontal;
	}
	
	public int getLength() {
		return this.length;
	}
	
	public String getShipType() {
		return this.getClass().getSimpleName();
	}
	
	/* Business Logic */
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
	
	public void placeShipAt(int row, int column, boolean horizontal, 
							Ocean ocean) {
		
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
		
		this.bowRow = row;
		this.bowColumn = column;
		this.horizontal = horizontal;
		
	}
	
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
	
	@Override
	public String toString() {
		String rv = "";
		
		if(this.isSunk()) {
			rv = "x";
		}
		if(this.hit[this.printPointer]) {
			rv = "S";
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
	
	
	
}
