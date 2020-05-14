package it.uniba.example;


import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

import org.junit.jupiter.api.Test;

import it.uniba.main.Pawn;
import it.uniba.main.Piece;
import it.uniba.main.Spot;


public class TestSpot {
	private static Spot spot;
	private static Spot spot2;
	private static Spot spot3;
	private static Spot spot4;
	private static Spot spot5;
	private static Spot spot6;
	private static Spot spot7;
	private static Spot spot8;
	private static Spot spot9;

	private static Piece piece;


	@Test
	void testIsEmpty() {
		spot = new Spot(1, 1);
		assertTrue(spot.isEmpty());
	}

	@Test
	void testIsNotEmpty() {
		piece = new Pawn(true);
		spot = new Spot(1, 1, piece);
		assertFalse(spot.isEmpty());
	}

	@Test
	void testToString() {
		spot = new Spot(1,1);
		spot2 = new Spot(1,0);
		spot3 = new Spot(1,2);
		spot4 = new Spot(1,3);
		spot5 = new Spot(1,4);
		spot6 = new Spot(1,5);
		spot7 = new Spot(1,6);
		spot8 = new Spot(1,7);
		spot9 = new Spot(10,10);
		assertEquals(spot.toString(), "b7");
		assertEquals(spot2.toString(), "a7");
		assertEquals(spot3.toString(), "c7");
		assertEquals(spot4.toString(), "d7");
		assertEquals(spot5.toString(), "e7");
		assertEquals(spot6.toString(), "f7");
		assertEquals(spot7.toString(), "g7");
		assertEquals(spot8.toString(), "h7");
		assertNotEquals(spot9.toString(), "");

	}

	@Test
	void testGetX() {
		spot = new Spot(2,2);
		assertEquals(spot.getX(), 2);
		assertNotEquals(spot.getX(), 3);
		spot.setX(5);
		assertEquals(spot.getX(), 5);
		assertNotEquals(spot.getX(),3);

	}

	@Test
	void testGetY() {
		spot = new Spot(2,2);
		assertEquals(spot.getY(), 2);
		assertNotEquals(spot.getY(), 3);
		spot.setY(5);
		assertEquals(spot.getY(), 5);
		assertNotEquals(spot.getY(),3);
	}

}
