package cw4;

import static org.junit.Assert.*;

import org.junit.Ignore;
import org.junit.Test;

public class OceanTest {

	@Ignore @Test
	public void testokToPlaceShipAt() {
		
		Ocean ocean = new Ocean();
		
		Battleship battleship = new Battleship();
		Submarine redOctober = new Submarine();
		
		System.out.println(battleship.okToPlaceShipAt(0, 9, true, ocean));
		System.out.println(battleship.okToPlaceShipAt(0, 0, true, ocean));
		System.out.println(battleship.okToPlaceShipAt(0, 9, false, ocean));
		System.out.println(redOctober.okToPlaceShipAt(0, 9, true, ocean));
		
		System.out.println(battleship.okToPlaceShipAt(9, 0, true, ocean));
		System.out.println(battleship.okToPlaceShipAt(9, 0, false, ocean));
		System.out.println(redOctober.okToPlaceShipAt(9, 0, true, ocean));

		
	}
	
	@Test 
	public void testPlaceShip() {
		
		Ocean ocean = new Ocean();
		Ship battleship = new Battleship();
		
		if(battleship.okToPlaceShipAt(0, 0, true, ocean)) {
			battleship.placeShipAt(0,0, true, ocean);
		}
		//battleship.placeShipAt(0,0, true, ocean);
		
		ocean.print();
		System.out.println(battleship.getBowRow());
		System.out.println(battleship.getBowColumn());
		
	}

}
