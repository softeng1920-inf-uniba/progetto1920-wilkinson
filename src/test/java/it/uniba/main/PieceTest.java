package it.uniba.main;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

class PieceTest {
	private static Board board;
	private static final boolean WHITE = false;
	private static final boolean BLACK = true;
	private static final int ROW_1 = 7;
	private static final int ROW_2 = 6;
	private static final int ROW_3 = 5;
	private static final int ROW_4 = 4;
	private static final int ROW_5 = 3;
	private static final int ROW_6 = 2;
	private static final int ROW_7 = 1;
	private static final int ROW_8 = 0;
	private static final int COL_H = 7;
	private static final int COL_G = 6;
	private static final int COL_F = 5;
	private static final int COL_E = 4;
	private static final int COL_D = 3;
	private static final int COL_C = 2;
	private static final int COL_B = 1;
	private static final int COL_A = 0;

	@Test
	void testPawnMovement() {
		board = new Board(true);
		// caso di test per il movimento del pedone
		board.getSpot(ROW_2, COL_A).setPiece(new Pawn(BLACK));
		// movimento in avanti di una casella
		assertTrue(board.getSpot(ROW_2, COL_A).getPiece().canMove(board, board.getSpot(ROW_2, COL_A), board.getSpot(ROW_3, COL_A)));
		// movimento in avanti di due caselle
		assertTrue(board.getSpot(ROW_2, COL_A).getPiece().canMove(board, board.getSpot(ROW_2, COL_A), board.getSpot(ROW_4, COL_A)));
		// movimento in avanti di tre caselle
		assertFalse(board.getSpot(ROW_2, COL_A).getPiece().canMove(board, board.getSpot(ROW_2, COL_A), board.getSpot(ROW_5, COL_A)));
		// movimento indietro
		assertFalse(board.getSpot(ROW_2, COL_A).getPiece().canMove(board, board.getSpot(ROW_2, COL_A), board.getSpot(ROW_1, COL_A)));

		// movimento in diagonale (senza pezzi nemici)
		assertFalse(board.getSpot(ROW_2, COL_A).getPiece().canMove(board, board.getSpot(ROW_2, COL_A), board.getSpot(ROW_3, COL_B)));
		// movimento in diagonale (con pezzo nemico)
		board.getSpot(ROW_3, COL_B).setPiece(new Pawn(WHITE)); // pezzo nemico
		assertTrue(board.getSpot(ROW_2, COL_A).getPiece().canMove(board, board.getSpot(ROW_2, COL_A), board.getSpot(ROW_3, COL_B)));
		// movimento in diagonale (con pezzo amico)
		board.getSpot(ROW_3, COL_B).setPiece(new Pawn(BLACK)); // pezzo amico
		assertFalse(board.getSpot(ROW_2, COL_A).getPiece().canMove(board, board.getSpot(ROW_2, COL_A), board.getSpot(ROW_3, COL_B)));

		// movimento in avanti con pedone di fronte
		board.getSpot(ROW_3, COL_A).setPiece(new Pawn(WHITE));
		assertFalse(board.getSpot(ROW_2, COL_A).getPiece().canMove(board, board.getSpot(ROW_2, COL_A), board.getSpot(ROW_3, COL_A)));
		// movimento in avanti di due caselle di un pedone gia' mosso
		board.getSpot(ROW_3, COL_A).getPiece().setAsMoved();
		assertFalse(board.getSpot(ROW_3, COL_A).getPiece().canMove(board, board.getSpot(ROW_3, COL_A), board.getSpot(ROW_5, COL_A)));
	}
}
