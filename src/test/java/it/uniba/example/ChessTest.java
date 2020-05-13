package it.uniba.example;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

import it.uniba.main.Bishop;
import it.uniba.main.Board;
import it.uniba.main.King;
import it.uniba.main.Knight;
import it.uniba.main.Pawn;
import it.uniba.main.Piece;
import it.uniba.main.Rook;

public class ChessTest {
	private static Board board;
	private static Piece genericPiece;
//	private static Game gameLogic;
//	private static String command;
//	private static final int standardBoardDimension = 8;
	private static final boolean BLACK = false;
	private static final boolean WHITE = true;
	private static final int RAW_1 = 7;
	private static final int RAW_2 = 6;
	private static final int RAW_3 = 5;
	private static final int RAW_4 = 4;
	private static final int RAW_5 = 3;
	private static final int RAW_6 = 2;
	private static final int RAW_7 = 1;
	private static final int RAW_8 = 0;
	private static final int COL_H = 7;
	private static final int COL_G = 6;
	private static final int COL_F = 5;
	private static final int COL_E = 4;
	private static final int COL_D = 3;
	private static final int COL_C = 2;
	private static final int COL_B = 1;
	private static final int COL_A = 0;

	/**
	 * Test to make sure pawns move properly.
	 *
	 * Note: White pawns move up, Black pawns move down.
	 */
	@Test
	public void testPawnMovements(){
		board = new Board(true);

		//One step
		board.getSpot(RAW_7, COL_B).setPiece(new Pawn(BLACK));
		genericPiece = board.getSpot(RAW_7, COL_B).getPiece();
		assertTrue(genericPiece.canMove(board, board.getSpot(RAW_7, COL_B), board.getSpot(RAW_6, COL_B)));
		//Two step first move
		assertTrue(genericPiece.canMove(board, board.getSpot(RAW_7, COL_B), board.getSpot(RAW_5, COL_B)));
		//Three step
		assertFalse(genericPiece.canMove(board, board.getSpot(RAW_7, COL_B), board.getSpot(RAW_4, COL_B)));
		//Back step
		assertFalse(genericPiece.canMove(board, board.getSpot(RAW_7, COL_B), board.getSpot(RAW_8, COL_B)));

		// Diagonal without enemies
		assertFalse(genericPiece.canMove(board, board.getSpot(RAW_7, COL_B), board.getSpot(RAW_6, COL_A)));

		// Diagonal with enemies
		board.getSpot(RAW_6, COL_A).setPiece(new Pawn(WHITE));
		assertTrue(genericPiece.canMove(board, board.getSpot(RAW_7, COL_B), board.getSpot(RAW_6, COL_A)));

		// One step, white
		board.getSpot(RAW_2, COL_G).setPiece(new Pawn(WHITE));
		genericPiece = board.getSpot(RAW_2, COL_G).getPiece();
		assertTrue(genericPiece.canMove(board, board.getSpot(RAW_2, COL_G), board.getSpot(RAW_3, COL_G)));
		// back step, black
		assertFalse(genericPiece.canMove(board, board.getSpot(RAW_2, COL_G), board.getSpot(RAW_1, COL_G)));

		// Invalid move, partner in front
		board.getSpot(RAW_3, COL_G).setPiece(new Pawn(WHITE));
		assertFalse(genericPiece.canMove(board, board.getSpot(RAW_2, COL_G), board.getSpot(RAW_3, COL_G)));

		// Two step, already moved
		board.getSpot(RAW_3, COL_G).getPiece().setAsMoved();
		assertFalse(genericPiece.canMove(board, board.getSpot(RAW_3, COL_G), board.getSpot(RAW_5, COL_G)));
	}

	/**
	 * Test to make sure knight moves properly.
	 */
	@Test
	public void testKnightMovements(){
		board = new Board(true);

		// All 8 valid movements
		board.getSpot(RAW_4, COL_D).setPiece(new Knight(WHITE));
		genericPiece = board.getSpot(RAW_4, COL_D).getPiece();
		assertTrue(genericPiece.canMove(board, board.getSpot(RAW_4, COL_D), board.getSpot(RAW_5, COL_B)));
		assertTrue(genericPiece.canMove(board, board.getSpot(RAW_4, COL_D), board.getSpot(RAW_5, COL_F)));
		assertTrue(genericPiece.canMove(board, board.getSpot(RAW_4, COL_D), board.getSpot(RAW_6, COL_C)));
		assertTrue(genericPiece.canMove(board, board.getSpot(RAW_4, COL_D), board.getSpot(RAW_6, COL_E)));
		assertTrue(genericPiece.canMove(board, board.getSpot(RAW_4, COL_D), board.getSpot(RAW_3, COL_B)));
		assertTrue(genericPiece.canMove(board, board.getSpot(RAW_4, COL_D), board.getSpot(RAW_3, COL_F)));
		assertTrue(genericPiece.canMove(board, board.getSpot(RAW_4, COL_D), board.getSpot(RAW_2, COL_C)));
		assertTrue(genericPiece.canMove(board, board.getSpot(RAW_4, COL_D), board.getSpot(RAW_2, COL_E)));

		// same spot
		assertFalse(genericPiece.canMove(board, board.getSpot(RAW_4, COL_D), board.getSpot(RAW_4, COL_D)));

		// empty spot, but invalid movement
		assertFalse(genericPiece.canMove(board, board.getSpot(RAW_4, COL_D), board.getSpot(RAW_4, COL_C)));

		// ally spot
		board.getSpot(RAW_2, COL_E).setPiece(new Knight(WHITE));
		assertFalse(genericPiece.canMove(board, board.getSpot(RAW_4, COL_D), board.getSpot(RAW_2, COL_E)));

		// enemy spot
		board.getSpot(RAW_2, COL_C).setPiece(new Knight(BLACK));
		assertTrue(genericPiece.canMove(board, board.getSpot(RAW_4, COL_D), board.getSpot(RAW_2, COL_C)));
	}

	/**
	 * Test to make sure rook moves properly.
	 */
	@Test
	public void testRookMovements(){
		board = new Board(true);

		// same raw movement
		board.getSpot(RAW_4, COL_D).setPiece(new Rook(WHITE));
		genericPiece = board.getSpot(RAW_4, COL_D).getPiece();
		assertTrue(genericPiece.canMove(board, board.getSpot(RAW_4, COL_D), board.getSpot(RAW_4, COL_G)));
		assertTrue(genericPiece.canMove(board, board.getSpot(RAW_4, COL_D), board.getSpot(RAW_4, COL_A)));

		// same col movement
		assertTrue(genericPiece.canMove(board, board.getSpot(RAW_4, COL_D), board.getSpot(RAW_2, COL_D)));
		assertTrue(genericPiece.canMove(board, board.getSpot(RAW_4, COL_D), board.getSpot(RAW_6, COL_D)));

		// illegal movement
		assertFalse(genericPiece.canMove(board, board.getSpot(RAW_4, COL_D), board.getSpot(RAW_1, COL_H)));
		assertFalse(genericPiece.canMove(board, board.getSpot(RAW_4, COL_D), board.getSpot(RAW_8, COL_A)));

		// units in the way (ally and enemy)
		board.getSpot(RAW_4, COL_F).setPiece(new Rook(WHITE));
		assertFalse(genericPiece.canMove(board, board.getSpot(RAW_4, COL_D), board.getSpot(RAW_4, COL_G)));
		board.getSpot(RAW_6, COL_D).setPiece(new Rook(BLACK));
		assertFalse(genericPiece.canMove(board, board.getSpot(RAW_4, COL_D), board.getSpot(RAW_8, COL_D)));

		// capture
		assertTrue(genericPiece.canMove(board, board.getSpot(RAW_4, COL_D), board.getSpot(RAW_6, COL_D)));
	}

	/**
	 * Test to make sure bishop moves properly.
	 */
	@Test
	public void testBishopMovements(){
		board = new Board(true);

		// first diagonal movement (NW, SE)
		board.getSpot(RAW_4, COL_D).setPiece(new Bishop(WHITE));
		genericPiece = board.getSpot(RAW_4, COL_D).getPiece();
		assertTrue(genericPiece.canMove(board, board.getSpot(RAW_4, COL_D), board.getSpot(RAW_6, COL_B)));
		assertTrue(genericPiece.canMove(board, board.getSpot(RAW_4, COL_D), board.getSpot(RAW_2, COL_F)));

		// second diagonal movement (NE, SW)
		assertTrue(genericPiece.canMove(board, board.getSpot(RAW_4, COL_D), board.getSpot(RAW_2, COL_B)));
		assertTrue(genericPiece.canMove(board, board.getSpot(RAW_4, COL_D), board.getSpot(RAW_6, COL_F)));

		// illegal movement
		assertFalse(genericPiece.canMove(board, board.getSpot(RAW_4, COL_D), board.getSpot(RAW_1, COL_H)));
		assertFalse(genericPiece.canMove(board, board.getSpot(RAW_4, COL_D), board.getSpot(RAW_8, COL_A)));

		// units in the way (ally and enemy)
		board.getSpot(RAW_6, COL_B).setPiece(new Bishop(WHITE));
		assertFalse(genericPiece.canMove(board, board.getSpot(RAW_4, COL_D), board.getSpot(RAW_7, COL_A)));
		board.getSpot(RAW_7, COL_G).setPiece(new Rook(BLACK));
		assertFalse(genericPiece.canMove(board, board.getSpot(RAW_4, COL_D), board.getSpot(RAW_8, COL_H)));

		// capture
		assertTrue(genericPiece.canMove(board, board.getSpot(RAW_4, COL_D), board.getSpot(RAW_7, COL_G)));
	}

	/**
	 * Test to verify the Queen moves properly
	 */
	@Test
	public void testQueenMovements(){

	}

	/**
	 * Test to verify the King moves properly
	 */
	@Test
	public void testKingMovements(){
		board = new Board(true);

		// All 8 valid movements
		board.getSpot(RAW_4, COL_D).setPiece(new King(WHITE));
		genericPiece = board.getSpot(RAW_4, COL_D).getPiece();
		assertTrue(genericPiece.canMove(board, board.getSpot(RAW_4, COL_D), board.getSpot(RAW_5, COL_C)));
		assertTrue(genericPiece.canMove(board, board.getSpot(RAW_4, COL_D), board.getSpot(RAW_4, COL_C)));
		assertTrue(genericPiece.canMove(board, board.getSpot(RAW_4, COL_D), board.getSpot(RAW_3, COL_C)));
		assertTrue(genericPiece.canMove(board, board.getSpot(RAW_4, COL_D), board.getSpot(RAW_5, COL_D)));
		assertTrue(genericPiece.canMove(board, board.getSpot(RAW_4, COL_D), board.getSpot(RAW_3, COL_D)));
		assertTrue(genericPiece.canMove(board, board.getSpot(RAW_4, COL_D), board.getSpot(RAW_5, COL_E)));
		assertTrue(genericPiece.canMove(board, board.getSpot(RAW_4, COL_D), board.getSpot(RAW_4, COL_E)));
		assertTrue(genericPiece.canMove(board, board.getSpot(RAW_4, COL_D), board.getSpot(RAW_3, COL_E)));


	}
}
