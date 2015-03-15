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

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class ShipTest {
	
	Ocean ocean;
	Ship nautilus;
	Ship solent;
	Ship reliant;
	Ship brandenburg;
	Ship deepBlue;
	
	/**
	 * Create an ocean and ships to use in each test
	 */
	@Before
	public void setUp() {
		ocean = new Ocean();
		nautilus = new Submarine();
		solent = new Destroyer();
		reliant = new Cruiser();
		brandenburg = new Battleship();
		deepBlue = new EmptySea();
		
	}
	
	/**
	 * Destroy the ocean and ships so fresh ones are created for next test
	 */
	@After
	public void tearDown() {
		ocean = null;
		nautilus = null;
		solent = null;
		reliant = null;
		brandenburg = null;
	}
	
	/**
	 * Test all ship types are returning the correct length
	 */
	@Test
	public void testGetLength() {
		
		assertEquals(nautilus.getLength(), 1);
		assertEquals(solent.getLength(), 2);
		assertEquals(reliant.getLength(), 3);
		assertEquals(brandenburg.getLength(), 4);
		assertEquals(deepBlue.getLength(), 1);
	}
	
	/**
	 * Test all ship types are returning the correct class
	 */
	@Test
	public void testGetShipType() {
		
		assertEquals(nautilus.getShipType(), "Submarine");
		assertEquals(solent.getShipType(), "Destroyer");
		assertEquals(reliant.getShipType(), "Cruiser");
		assertEquals(brandenburg.getShipType(), "Battleship");
		assertEquals(deepBlue.getShipType(), "EmptySea");
	}
	
	/**
	 * Test that all ship types are placed correctly
	 */
	@Test
	public void testPlaceShipAt() {
		
		// Place and test submarine
		nautilus.placeShipAt(0, 0, true, ocean);
		assertEquals(nautilus.getBowColumn(), 0);
		assertEquals(nautilus.getBowRow(), 0);
		assertEquals(nautilus.isHorizontal(), true);
		
		// Place and test destroyer
		solent.placeShipAt(1, 2, false, ocean);
		assertEquals(solent.getBowColumn(), 2);
		assertEquals(solent.getBowRow(), 1);
		assertEquals(solent.isHorizontal(), false);
		
		// Place and test cruiser
		reliant.placeShipAt(4, 4, true, ocean);
		assertEquals(reliant.getBowColumn(), 4);
		assertEquals(reliant.getBowRow(), 4);
		assertEquals(reliant.isHorizontal(), true);
			
		// Place and test battleship
		brandenburg.placeShipAt(0, 9, false, ocean);
		assertEquals(brandenburg.getBowColumn(), 9);
		assertEquals(brandenburg.getBowRow(), 0);
		assertEquals(brandenburg.isHorizontal(), false);
				
		// Check ships are where they should be 
		Ship[][] ships = ocean.getShipArray();
		assertEquals(ships[0][0], nautilus);
		assertEquals(ships[1][2], solent);
		assertEquals(ships[2][2], solent);
		assertEquals(ships[4][4], reliant);
		assertEquals(ships[4][5], reliant);
		assertEquals(ships[4][6], reliant);		
		assertEquals(ships[0][9], brandenburg);
		assertEquals(ships[1][9], brandenburg);
		assertEquals(ships[2][9], brandenburg);	
		assertEquals(ships[3][9], brandenburg);	
		
	}
	
	
	/**
	 * Test that invalid ship positions are not returned as valid
	 */
	@Test
	public void testokToPlaceShipAt() {
		
		Battleship bismark = new Battleship();
		Submarine redOctober = new Submarine();
		
		// Place ships around edges of gameboard and check correct results
		// returned
		assertFalse(bismark.okToPlaceShipAt(0, 9, true, ocean));
		assertTrue(bismark.okToPlaceShipAt(0, 0, true, ocean));
		assertTrue(bismark.okToPlaceShipAt(0, 9, false, ocean));
		assertTrue(redOctober.okToPlaceShipAt(0, 9, true, ocean));
		assertTrue(bismark.okToPlaceShipAt(9, 0, true, ocean));
		assertFalse(bismark.okToPlaceShipAt(9, 0, false, ocean));
		assertTrue(redOctober.okToPlaceShipAt(9, 0, true, ocean));
		
		// Check ships cannot be placed next to each other
		ocean.setShip(1, 1, bismark);
		ocean.setShip(2, 1, bismark);
		ocean.setShip(3, 1, bismark);
		ocean.setShip(4, 1, bismark);
		
		assertFalse(redOctober.okToPlaceShipAt(0, 0, true, ocean));
		assertFalse(redOctober.okToPlaceShipAt(1, 0, true, ocean));
		assertFalse(redOctober.okToPlaceShipAt(2, 0, true, ocean));
		assertFalse(redOctober.okToPlaceShipAt(3, 0, true, ocean));
		assertFalse(redOctober.okToPlaceShipAt(4, 0, true, ocean));
		assertFalse(redOctober.okToPlaceShipAt(5, 0, true, ocean));
		assertFalse(redOctober.okToPlaceShipAt(0, 1, true, ocean));
		assertFalse(redOctober.okToPlaceShipAt(5, 1, true, ocean));
		assertFalse(redOctober.okToPlaceShipAt(0, 0, true, ocean));
		assertFalse(redOctober.okToPlaceShipAt(1, 2, true, ocean));
		assertFalse(redOctober.okToPlaceShipAt(2, 2, true, ocean));
		assertFalse(redOctober.okToPlaceShipAt(3, 2, true, ocean));
		assertFalse(redOctober.okToPlaceShipAt(4, 2, true, ocean));
		assertFalse(redOctober.okToPlaceShipAt(5, 2, true, ocean));
		
		// Check that ships cannot be placed off the board
		assertFalse(bismark.okToPlaceShipAt(10, 10, true, ocean));
		
	}
	
	/**
	 * Test shooting at sections marks them as hit, sinks the ship, then returns
	 * false when ship is sunk
	 */
	@Test
	public void testShootAt() {
		
		// Test shooting at empty sea (shoot return false both times)
		assertFalse(deepBlue.shootAt(0, 0));
		assertFalse(deepBlue.shootAt(0, 0));
		
		// Place a battleship and test each section 
		brandenburg.placeShipAt(0, 0, true, ocean);
		assertTrue(brandenburg.shootAt(0, 0));
		assertTrue(brandenburg.shootAt(0, 1));
		assertTrue(brandenburg.shootAt(0, 2));
		assertTrue(brandenburg.shootAt(0, 3));
		
		// Ship should now be sunk, so all sections should return false
		assertFalse(brandenburg.shootAt(0, 0));
		assertFalse(brandenburg.shootAt(0, 1));
		assertFalse(brandenburg.shootAt(0, 2));
		assertFalse(brandenburg.shootAt(0, 3));
		
	}
	
	/**
	 * Test ship is sunk once all its sections are hit
	 */
	@Test
	public void testIsSunk() {
		
		// Place a battleship and check it is afloat
		brandenburg.placeShipAt(0, 0, true, ocean);
		assertFalse(brandenburg.isSunk());
		
		// Shoot each section and check ship is sunk only once all sections hit
		assertTrue(brandenburg.shootAt(0, 0));
		assertFalse(brandenburg.isSunk());
		assertTrue(brandenburg.shootAt(0, 1));
		assertFalse(brandenburg.isSunk());
		assertTrue(brandenburg.shootAt(0, 2));
		assertFalse(brandenburg.isSunk());
		assertTrue(brandenburg.shootAt(0, 3));
		assertTrue(brandenburg.isSunk());
	}

	/**
	 * Test toString() is returning the correct characters ("x", "S" and ".")
	 */
	@Test
	public void testToString() {
		
		// Add destoryer to the gameboard
		solent.placeShipAt(0, 0, true, ocean);
		
		// Check that unhit sections returns "."
		assertEquals(solent.toString(), ".");
		assertEquals(solent.toString(), ".");
		
		// Shoot one section - check that returns "S" and the unhit section
		// still returns "."
		solent.shootAt(0, 0);
		assertEquals(solent.toString(), "S");
		assertEquals(solent.toString(), ".");
		
		// Sink the ship and check that both sections return "x"
		solent.shootAt(0, 1);
		assertEquals(solent.toString(), "x");
		assertEquals(solent.toString(), "x");
		
	}
	
	/**
	 * Test the generic getters and setters
	 * <p>
	 * Seems superfluous, but the spec said test ALL public methods, so
	 * here goes....
	 */
	@Test
	public void testGettersAndSetters() {
		
		// Test getBowRow() and setBowRow()
		nautilus.setBowRow(1);
		assertEquals(nautilus.getBowRow(), 1);
		
		// Test getBowColumn and setBowColumn()
		nautilus.setBowColumn(1);
		assertEquals(nautilus.getBowColumn(), 1);
		
		// Test isHorizontal() and setHorizontal()
		assertFalse(nautilus.isHorizontal());
		nautilus.setHorizontal(true);
		assertTrue(nautilus.isHorizontal());
	}
}
