/**
 * Software and Programming II
 * Coursework: sp2-cw4-2014
 * 
 * Pete Whelpton 
 * Due Date: 29/03/2015
 * Lecturer: Keith Mannock
 */

package cw4;

import static org.junit.Assert.*;

import java.awt.Point;
import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.util.Collection;
import java.util.List;

import org.junit.After;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;

/**
 * J-Unit tests for the Ocean class
 * 
 * @author pete
 *
 */
public class OceanTest {
	
	Ocean ocean;
	
	/**
	 * Create an ocean to use in each test
	 */
	@Before
	public void setUp() {
		ocean = new Ocean();
	}
	
	/**
	 * Destroy the ocean so a fresh one is created for next test
	 */
	@After
	public void tearDown() {
		ocean = null;
	}
	
	@Ignore @Test 
	public void testPlaceShip() {
		
		Ship battleship = new Battleship();
		
		if(battleship.okToPlaceShipAt(0, 0, true, ocean)) {
			battleship.placeShipAt(0,0, true, ocean);
		}
		//battleship.placeShipAt(0,0, true, ocean);
		
		ocean.print();		
	}
	
	/**
	 * Test occupied / non-occupied tiles are correctly returned
	 */
	@Test
	public void testIsOccupied() {
		
		// Create a ship
		Ship k19 = new Submarine();
		
		// Place the ship
		ocean.setShip(0, 0, k19);
		
		// Test the occupied tile is correctly returned as occupied, and 
		// neighbouring tiles correctly returned as not occupied
		assertTrue(ocean.isOccupied(0, 0));
		assertFalse(ocean.isOccupied(1, 0));
		assertFalse(ocean.isOccupied(0, 1));
		assertFalse(ocean.isOccupied(1, 1));
	
	}
	
	/**
	 * Test all empty tiles on the board are returned
	 */
	@Test
	public void testGetEmptyTiles() {
		
		// Create a new ocean and place the fleet
		ocean.placeAllShipsRandomly();
		
		// Get list of empty tiles
		List<Point> emptyTiles = ocean.getEmptyTiles();
		
		// There are 100 tiles on the board, less 20 tiles occupied by ships
		// so there should be 80 empty tiles remaining
		assertTrue(emptyTiles.size() == 80);
		
		// Check every tile returned is empty
		for(Point tile : emptyTiles) {
			assertFalse(ocean.isOccupied(tile.y, tile.x));
		}
	}
	
	/**
	 * Test gameboard tiles are correctly returned as true, and invalid
	 * co-ordinates returned as false
	 */
	@Test
	public void testIsOnBoard() {
		
		// Create new ocean and get all tiles
		Ship[][] tiles = ocean.getShipArray();
		
		// Check every tile is correctly returned as being on the board
		for (int row = 0; row < tiles.length; row++) {  
		    for (int tile = 0; tile < tiles[row].length; tile++) {  
		        assertTrue(ocean.isOnBoard(row, tile));    
		    }
		}
		
		// Check some random invalid tiles
		assertFalse(ocean.isOnBoard(-1, 0));
		assertFalse(ocean.isOnBoard(10, 0));
		assertFalse(ocean.isOnBoard(0, -1));
		assertFalse(ocean.isOnBoard(0, 10));
		
	}
	
	/**
	 * Test game over state correctly calculated / returned
	 */
	@Test
	public void testIsGameOver() {
		
		// Check starting state is false
		assertFalse(ocean.isGameOver());
		
		// Place 10 ships and sink them all
		for(int i = 0; i < 10; i++) {
			ocean.setShip(0, 0, new Submarine());
			ocean.shootAt(0, 0);
		}
		
		// Check game is now gameover
		assertTrue(ocean.isGameOver());
	}
	
	/**
	 * Check the correct response is returned when shooting an a floating ship,
	 * sunk ship, empty sea tile
	 */
	@Test
	public void testShootAt() {
		
		// Create and place a battleship
		Ship essess = new Battleship();
		ocean.setShip(0, 0, essess);
		ocean.setShip(1, 0, essess);
		ocean.setShip(2, 0, essess);
		ocean.setShip(3, 0, essess);
		
		// Check all sections of battleship return hit
		assertTrue(ocean.shootAt(0, 0));
		assertTrue(ocean.shootAt(1, 0));
		assertTrue(ocean.shootAt(2, 0));
		assertTrue(ocean.shootAt(3, 0));
		
		// Ship should be sunk, so check all sections return false
		assertFalse(ocean.shootAt(0, 0));
		assertFalse(ocean.shootAt(1, 0));
		assertFalse(ocean.shootAt(2, 0));
		assertFalse(ocean.shootAt(3, 0));
		
		// Get list of all empty tiles on board
		List<Point> emptyTiles = ocean.getEmptyTiles();
				
		// Check every tile returns false when shot at
		for(Point tile : emptyTiles) {
			assertFalse(ocean.shootAt(tile.y, tile.x));
		}
	}
	
	/**
	 * Test gameboard is printing correctly
	 * <p>
	 * Only tested with default board.  Idea about diverting print output
	 * to ByteArrayStream pinched from here:
	 * http://stackoverflow.com/questions/1119385/junit-test-for-system-out-println
	 */
	@Test
	public void testPrint() {
		
		// Create stream for catching print output and divert printing there
		final ByteArrayOutputStream outContent = new ByteArrayOutputStream();
		System.setOut(new PrintStream(outContent));
		
		// Get platform dependent line ending
		String lineEnd = System.getProperty("line.separator");
		
		// Create string containing expected default gameboard
		String expectedOutput = "" + lineEnd 
				+ "  0 1 2 3 4 5 6 7 8 9" + lineEnd
				+ "0 . . . . . . . . . . " + lineEnd
				+ "1 . . . . . . . . . . " + lineEnd
				+ "2 . . . . . . . . . . " + lineEnd
				+ "3 . . . . . . . . . . " + lineEnd
				+ "4 . . . . . . . . . . " + lineEnd
				+ "5 . . . . . . . . . . " + lineEnd
				+ "6 . . . . . . . . . . " + lineEnd
				+ "7 . . . . . . . . . . " + lineEnd
				+ "8 . . . . . . . . . . " + lineEnd
				+ "9 . . . . . . . . . . " + lineEnd
				+ lineEnd;
	
		// Print ocean (to ByteArrayStream) and compare to handmade gameboard
		ocean.print();	
		assertEquals(expectedOutput, outContent.toString());
		
	}
	
	/**
	 * Test all ships are being placed on board, and that placement is suitably
	 * random
	 */
	@Test
	public void testPlaceAllShipsRandomly() {
		
		// Place ships on board
		ocean.placeAllShipsRandomly();
		
		// Get ships array
		Ship[][] tiles = ocean.getShipArray();
		
		// Add counter to count number of occupied tiles
		int occupiedTiles = 0;
		final int EXPECTED_RESULT = 20;
		
		// Loop through every tile and add to counter if occupied
		for (int row = 0; row < tiles.length; row++) {  
		    for (int tile = 0; tile < tiles[row].length; tile++) {  
		        if(ocean.isOccupied(row, tile)) {
		        	occupiedTiles++;
		        }
		    }
		}
		
		// Check that there are 20 occupied tiles
		assertEquals(occupiedTiles, EXPECTED_RESULT);
	
	
	// Create another ocean and check output is not the same (chance of duplicate
	// ocean is very, very, low), i.e. check ship placement is suitably random
	Ocean ocean2 = new Ocean();
	ocean2.placeAllShipsRandomly();
	
	assertFalse(ocean.toString().equals(ocean2.toString()));
	
	}
	
	/**
	 * Test the generic getters and setters
	 * <p>
	 * Seems superfluous, but the spec said test ALL public methods, so
	 * here goes....
	 */
	@Test
	public void testGettersAndSetters() {
		
		Ship u571 = new Submarine();
		u571.placeShipAt(2, 2, true, ocean);
		
		// Test shots fired on new board / after one shot fired
		assertTrue(ocean.getShotsFired() == 0);
		ocean.shootAt(0, 0);
		assertTrue(ocean.getShotsFired() == 1);
		
		// Test hit count and ships sunk
		assertTrue(ocean.getHitCount() == 0);
		assertTrue(ocean.getShipsSunk() == 0);
		assertTrue(ocean.shootAt(2, 2));
		assertTrue(ocean.getShipsSunk() == 1);
		
		// Check the ship is set
		Ship crimsonTide = new Submarine();
		ocean.setShip(3, 3, crimsonTide);
		assertTrue(ocean.isOccupied(3,3));
		
	}
	
}
