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

}
