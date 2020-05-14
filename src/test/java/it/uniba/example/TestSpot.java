package it.uniba.example;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.jupiter.api.Test;

import it.uniba.main.Pawn;
import it.uniba.main.Piece;
import it.uniba.main.Spot;

public class TestSpot {
	private static Spot spot;
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

}
