package it.uniba.example;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

import it.uniba.main.Bishop;
import it.uniba.main.Board;
import it.uniba.main.King;
import it.uniba.main.Knight;
import it.uniba.main.Pawn;
import it.uniba.main.Queen;
import it.uniba.main.Rook;

class PieceTest {
	private static Board board;
	private static final boolean WHITE = true;
	private static final boolean BLACK = false;
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
		board.getSpot(ROW_2, COL_A).setPiece(new Pawn(WHITE));
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

		// movimento in avanti con pedone di fronte
		board.getSpot(ROW_3, COL_A).setPiece(new Pawn(WHITE));
		assertFalse(board.getSpot(ROW_2, COL_A).getPiece().canMove(board, board.getSpot(ROW_2, COL_A), board.getSpot(ROW_3, COL_A)));
		// movimento in avanti di due caselle di un pedone gia' mosso
		board.getSpot(ROW_3, COL_A).getPiece().setAsMoved();
		assertFalse(board.getSpot(ROW_3, COL_A).getPiece().canMove(board, board.getSpot(ROW_3, COL_A), board.getSpot(ROW_5, COL_A)));

		// cattura di un pezzo nemico
		board.getSpot(ROW_3, COL_B).setPiece(new Pawn(BLACK)); // pezzo nemico
		assertTrue(board.getSpot(ROW_2, COL_A).getPiece().canMove(board, board.getSpot(ROW_2, COL_A), board.getSpot(ROW_3, COL_B)));
		// cattura di un pezzo amico
		board.getSpot(ROW_3, COL_B).setPiece(new Pawn(WHITE)); // pezzo amico
		assertFalse(board.getSpot(ROW_2, COL_A).getPiece().canMove(board, board.getSpot(ROW_2, COL_A), board.getSpot(ROW_3, COL_B)));
	}
}