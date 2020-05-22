package it.uniba.example;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import it.uniba.main.Board;
import it.uniba.main.King;
import it.uniba.main.Move;
import it.uniba.main.Queen;
import it.uniba.main.Rook;

class MoveTest {
	private Move move;
	private Board board;
	private static final boolean WHITE = true;
	private static final boolean BLACK = false;
	private static final int ROW_1 = 7;
	private static final int ROW_4 = 4;
	private static final int ROW_5 = 3;
	private static final int ROW_8 = 0;
	private static final int COL_B = 1;
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

	//test arrocco corto di pezzi bianchi
	@Test
	void testCastleShort() {
		move = new Move("0-0", board, WHITE);
		board.getSpot(ROW_1, COL_H).setPiece(new Rook(WHITE));
		assertTrue(move.makeCastling(board, WHITE));
		assertNull(board.getSpot(ROW_1, COL_E).getPiece());
		assertNull(board.getSpot(ROW_1, COL_H).getPiece());
	}

	//test arrocco lungo di pezzi bianchi
	@Test
	void testCastleLong() {
		move = new Move("0-0-0", board, WHITE);
		board.getSpot(ROW_1, COL_A).setPiece(new Rook(WHITE));
		assertTrue(move.makeCastling(board, WHITE));
		assertNull(board.getSpot(ROW_1, COL_E).getPiece());
		assertNull(board.getSpot(ROW_1, COL_A).getPiece());
	}

	//test arrocco corto di pezzi neri
	@Test
	void testCastleShortBlack() {
		move = new Move("0-0", board, BLACK);
		board.getSpot(ROW_8, COL_H).setPiece(new Rook (BLACK));
		assertTrue(move.makeCastling(board, BLACK));
		assertNull(board.getSpot(ROW_8, COL_E).getPiece());
		assertNull(board.getSpot(ROW_8, COL_H).getPiece());
	}

	//test arrocco lungo di pezzi neri
	@Test
	void testCastleLongBlack() {
		move = new Move("0-0-0", board, BLACK);
		board.getSpot(ROW_8, COL_A).setPiece(new Rook (BLACK));
		assertTrue(move.makeCastling(board, BLACK));
		assertNull(board.getSpot(ROW_8, COL_E).getPiece());
		assertNull(board.getSpot(ROW_8, COL_A).getPiece());
	}

	//test arrocco con un comando errato
	@Test
	void testIncorrectCastleCmd() {
		move = new Move("o-o", board, WHITE);
		board.getSpot(ROW_1, COL_H).setPiece(new Rook (WHITE));
		assertFalse(move.makeCastling(board, WHITE));
	}

	//test arrocco con il re gia' mosso in precedenza
	@Test
	void testCastleKingMoved() {
		move = new Move("0-0", board, WHITE);
		move = new Move("0-0", board, WHITE);
		board.getSpot(ROW_1, COL_H).setPiece(new Rook (WHITE));
		board.getSpot(ROW_1, COL_E).getPiece().setAsMoved();
		assertFalse(move.makeCastling(board, WHITE));
	}

	//test arrocco con torre gia' mossa in precedenza
	@Test
	void testCastleRookMoved() {
		move = new Move("0-0", board, WHITE);
		board.getSpot(ROW_1, COL_H).setPiece(new Rook (WHITE));
		board.getSpot(ROW_1, COL_H).getPiece().setAsMoved();
		assertFalse(move.makeCastling(board, WHITE));
	}

	//test arrocco con re minacciato
	@Test
	void testCastleKingUnderAttack() {
		move = new Move("0-0", board, WHITE);
		board.getSpot(ROW_1, COL_H).setPiece(new Rook (WHITE));
		board.getSpot(ROW_4, COL_E).setPiece(new Queen(BLACK));
		board.recalLegalMoves();
		assertTrue(board.getSpot(ROW_1, COL_E).isUnderAttack(board, WHITE));
		assertFalse(move.makeCastling(board, WHITE));
	}

	//test arrocco con torre minacciata
	@Test
	void testCastleRookUnderAttack() {
		move = new Move("0-0", board, WHITE);
		board.getSpot(ROW_1, COL_H).setPiece(new Rook (WHITE));
		board.getSpot(ROW_4, COL_H).setPiece(new Queen(BLACK));
		board.recalLegalMoves();
		assertTrue(board.getSpot(ROW_1, COL_H).isUnderAttack(board, WHITE));
		assertFalse(move.makeCastling(board, WHITE));
	}

	//test arrocco con posizione di arrivo del re minacciata
	@Test
	void testCastleKingEndUnderAttack() {
		move = new Move("0-0", board, WHITE);
		board.getSpot(ROW_1, COL_H).setPiece(new Rook (WHITE));
		board.getSpot(ROW_4, COL_G).setPiece(new Queen(BLACK));
		board.recalLegalMoves();
		assertTrue(board.getSpot(ROW_1, COL_G).isUnderAttack(board, WHITE));
		assertFalse(move.makeCastling(board, WHITE));

	}

	//test arrocco con posizione di arrivo della torre minacciata
	@Test
	void testCastleRookEndUnderAttack() {
		move = new Move("0-0", board, WHITE);
		board.getSpot(ROW_1, COL_H).setPiece(new Rook (WHITE));
		board.getSpot(ROW_4, COL_F).setPiece(new Queen(BLACK));
		board.recalLegalMoves();
		assertTrue(board.getSpot(ROW_1, COL_F).isUnderAttack(board, WHITE));
		assertFalse(move.makeCastling(board, WHITE));
	}

	//test arrocco lungo con casella b1 minacciata
	@Test
	void testCastleLongSpotUnderAttack() {
		move = new Move("0-0", board, WHITE);
		board.getSpot(ROW_1, COL_H).setPiece(new Rook (WHITE));
		board.getSpot(ROW_5, COL_B).setPiece(new Queen(BLACK));
		board.recalLegalMoves();
		assertTrue(board.getSpot(ROW_1, COL_B).isUnderAttack(board, WHITE));
		assertFalse(move.makeCastling(board, WHITE));
	}

	//test arrocco con pezzo nemico frapposto tra torre e re
	@Test
	void testCastleEnemyPieceBetween() {
		move = new Move("0-0", board, WHITE);
		board.getSpot(ROW_1, COL_H).setPiece(new Rook (WHITE));
		board.getSpot(ROW_1, COL_F).setPiece(new Queen(BLACK));
		assertFalse(move.makeCastling(board, WHITE));
	}

	//test arrocco con pezzo amico frapposto tra torre e re
	@Test
	void testCastleFriendPieceBetween() {
		move = new Move("0-0", board, WHITE);
		board.getSpot(ROW_1, COL_H).setPiece(new Rook (WHITE));
		board.getSpot(ROW_1, COL_F).setPiece(new Queen(WHITE));
		assertFalse(move.makeCastling(board, WHITE));
	}

	//test arrocco con pezzo diverso da torre o re
	@Test
	void testCastleIncorrectPiece() {
		move = new Move("0-0", board, WHITE);
		board.getSpot(ROW_1, COL_H).setPiece(new Queen(WHITE));
		assertFalse(move.makeCastling(board, WHITE));
	}
}
