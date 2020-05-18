package it.uniba.example;

import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

import org.junit.After;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import it.uniba.main.Board;
import it.uniba.main.King;
import it.uniba.main.Move;
import it.uniba.main.Rook;

class MoveTest {
	private Move move;
	private Board board;
	private static final boolean WHITE = true;
	private static final boolean BLACK = false;
	private static final int ROW_1 = 7;
	private static final int ROW_4 = 4;
	private static final int ROW_8 = 0;
	private static final int COL_H = 7;
	private static final int COL_G = 6;
	private static final int COL_F = 5;
	private static final int COL_E = 4;
	private static final int COL_A = 0;

	@BeforeEach
	void setup() {
		board = new Board(true);
		board.getSpot(ROW_1, COL_E).setPiece(new King(WHITE));
		board.getSpot(ROW_8, COL_E).setPiece(new King(BLACK));
	}

	@Test
	void testCastleShort() {
		move = new Move("0-0", board, WHITE);
		board.getSpot(ROW_1, COL_H).setPiece(new Rook(WHITE));
		assertTrue(move.makeCastling(board, WHITE));
		assertNull(board.getSpot(ROW_1, COL_E).getPiece());
		assertNull(board.getSpot(ROW_1, COL_H).getPiece());
	}

	@After
	void tearDown() {


	}

}
