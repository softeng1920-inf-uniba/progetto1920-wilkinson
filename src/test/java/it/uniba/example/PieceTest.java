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

		// cattura en passant
		board.getSpot(ROW_4, COL_E).setPiece(new Pawn(BLACK)); // pezzo nemico
		board.getSpot(ROW_4, COL_D).setPiece(new Pawn(WHITE)); // pezzo amico
		board.getSpot(ROW_4, COL_D).getPiece().setAsMoved();
		((Pawn)board.getSpot(ROW_4, COL_D).getPiece()).setPossibleEnPassantCapture(true);
		assertTrue(board.getSpot(ROW_4, COL_E).getPiece().canMove(board, board.getSpot(ROW_4, COL_E), board.getSpot(ROW_3, COL_D)));
		((Pawn)board.getSpot(ROW_4, COL_D).getPiece()).setPossibleEnPassantCapture(false);
		assertFalse(board.getSpot(ROW_4, COL_E).getPiece().canMove(board, board.getSpot(ROW_4, COL_E), board.getSpot(ROW_3, COL_D)));

		// cattura en passant con pezzo amico
		board.getSpot(ROW_4, COL_E).setPiece(new Pawn(WHITE)); // pezzo da catturare
		board.getSpot(ROW_4, COL_D).setPiece(new Pawn(WHITE)); // pezzo che cattura
		((Pawn)board.getSpot(ROW_4, COL_D).getPiece()).setPossibleEnPassantCapture(true);
		assertFalse(board.getSpot(ROW_4, COL_E).getPiece().canMove(board, board.getSpot(ROW_4, COL_E), board.getSpot(ROW_3, COL_D)));
	}

	@Test
	void testRookMovement() {
		board = new Board(true);
		// caso di test per il movimento della torre
		board.getSpot(ROW_5, COL_D).setPiece(new Rook(WHITE));
		// movimento in verticale (Nord e Sud)
		assertTrue(board.getSpot(ROW_5, COL_D).getPiece().canMove(board, board.getSpot(ROW_5, COL_D), board.getSpot(ROW_1, COL_D))); // N
		assertTrue(board.getSpot(ROW_5, COL_D).getPiece().canMove(board, board.getSpot(ROW_5, COL_D), board.getSpot(ROW_8, COL_D))); // S
		// movimento in orizzontale (Est e Ovest)
		assertTrue(board.getSpot(ROW_5, COL_D).getPiece().canMove(board, board.getSpot(ROW_5, COL_D), board.getSpot(ROW_5, COL_H))); // E
		assertTrue(board.getSpot(ROW_5, COL_D).getPiece().canMove(board, board.getSpot(ROW_5, COL_D), board.getSpot(ROW_5, COL_A))); // W
		// movimento in diagonale
		assertFalse(board.getSpot(ROW_5, COL_D).getPiece().canMove(board, board.getSpot(ROW_5, COL_D), board.getSpot(ROW_3, COL_F))); // NE
		assertFalse(board.getSpot(ROW_5, COL_D).getPiece().canMove(board, board.getSpot(ROW_5, COL_D), board.getSpot(ROW_7, COL_B))); // SW

		// movimento con pezzi lungo il percorso
		board.getSpot(ROW_5, COL_E).setPiece(new Pawn(WHITE)); // pezzo amico
		assertFalse(board.getSpot(ROW_5, COL_D).getPiece().canMove(board, board.getSpot(ROW_5, COL_D), board.getSpot(ROW_5, COL_F)));
		board.getSpot(ROW_4, COL_D).setPiece(new Pawn(BLACK)); // pezzo nemico
		assertFalse(board.getSpot(ROW_5, COL_D).getPiece().canMove(board, board.getSpot(ROW_5, COL_D), board.getSpot(ROW_3, COL_D)));

		// cattura di un pezzo amico
		board.getSpot(ROW_5, COL_E).setPiece(new Pawn(WHITE)); // pezzo amico
		assertFalse(board.getSpot(ROW_5, COL_D).getPiece().canMove(board, board.getSpot(ROW_5, COL_D), board.getSpot(ROW_5, COL_E)));
		// cattura di un pezzo nemico
		assertTrue(board.getSpot(ROW_5, COL_D).getPiece().canMove(board, board.getSpot(ROW_5, COL_D), board.getSpot(ROW_4, COL_D))); // verticale
		board.getSpot(ROW_5, COL_E).setPiece(new Pawn(BLACK)); // pezzo nemico
		assertTrue(board.getSpot(ROW_5, COL_D).getPiece().canMove(board, board.getSpot(ROW_5, COL_D), board.getSpot(ROW_5, COL_E))); // orizzontale
	}
	@Test
	void testKnightMovement() {
		board = new Board(true);
		// caso di test per il movimento del cavallo (tutte le 8 mosse possibili)
		board.getSpot(ROW_4, COL_D).setPiece(new Knight(WHITE));
		assertTrue(board.getSpot(ROW_4, COL_D).getPiece().canMove(board, board.getSpot(ROW_4, COL_D), board.getSpot(ROW_2, COL_E)));
		assertTrue(board.getSpot(ROW_4, COL_D).getPiece().canMove(board, board.getSpot(ROW_4, COL_D), board.getSpot(ROW_3, COL_F)));
		assertTrue(board.getSpot(ROW_4, COL_D).getPiece().canMove(board, board.getSpot(ROW_4, COL_D), board.getSpot(ROW_5, COL_F)));
		assertTrue(board.getSpot(ROW_4, COL_D).getPiece().canMove(board, board.getSpot(ROW_4, COL_D), board.getSpot(ROW_6, COL_E)));
		assertTrue(board.getSpot(ROW_4, COL_D).getPiece().canMove(board, board.getSpot(ROW_4, COL_D), board.getSpot(ROW_6, COL_C)));
		assertTrue(board.getSpot(ROW_4, COL_D).getPiece().canMove(board, board.getSpot(ROW_4, COL_D), board.getSpot(ROW_5, COL_B)));
		assertTrue(board.getSpot(ROW_4, COL_D).getPiece().canMove(board, board.getSpot(ROW_4, COL_D), board.getSpot(ROW_3, COL_B)));
		assertTrue(board.getSpot(ROW_4, COL_D).getPiece().canMove(board, board.getSpot(ROW_4, COL_D), board.getSpot(ROW_2, COL_C)));

		// movimento illegale in una casella vuota
		assertFalse(board.getSpot(ROW_4, COL_D).getPiece().canMove(board, board.getSpot(ROW_4, COL_D), board.getSpot(ROW_4, COL_C)));

		// cattura di un pezzo amico
		board.getSpot(ROW_3, COL_B).setPiece(new Pawn(WHITE)); // pezzo amico
		assertFalse(board.getSpot(ROW_4, COL_D).getPiece().canMove(board, board.getSpot(ROW_4, COL_D), board.getSpot(ROW_3, COL_B)));
		// cattura di un pezzo nemico (tutte le 8 catture possibili)
		board.getSpot(ROW_2, COL_E).setPiece(new Pawn(BLACK)); // pezzo nemico
		assertTrue(board.getSpot(ROW_4, COL_D).getPiece().canMove(board, board.getSpot(ROW_4, COL_D), board.getSpot(ROW_2, COL_E)));
		board.getSpot(ROW_3, COL_F).setPiece(new Pawn(BLACK)); // pezzo nemico
		assertTrue(board.getSpot(ROW_4, COL_D).getPiece().canMove(board, board.getSpot(ROW_4, COL_D), board.getSpot(ROW_3, COL_F)));
		board.getSpot(ROW_5, COL_F).setPiece(new Pawn(BLACK)); // pezzo nemico
		assertTrue(board.getSpot(ROW_4, COL_D).getPiece().canMove(board, board.getSpot(ROW_4, COL_D), board.getSpot(ROW_5, COL_F)));
		board.getSpot(ROW_6, COL_E).setPiece(new Pawn(BLACK)); // pezzo nemico
		assertTrue(board.getSpot(ROW_4, COL_D).getPiece().canMove(board, board.getSpot(ROW_4, COL_D), board.getSpot(ROW_6, COL_E)));
		board.getSpot(ROW_6, COL_C).setPiece(new Pawn(BLACK)); // pezzo nemico
		assertTrue(board.getSpot(ROW_4, COL_D).getPiece().canMove(board, board.getSpot(ROW_4, COL_D), board.getSpot(ROW_6, COL_C)));
		board.getSpot(ROW_5, COL_B).setPiece(new Pawn(BLACK)); // pezzo nemico
		assertTrue(board.getSpot(ROW_4, COL_D).getPiece().canMove(board, board.getSpot(ROW_4, COL_D), board.getSpot(ROW_5, COL_B)));
		board.getSpot(ROW_3, COL_B).setPiece(new Pawn(BLACK)); // pezzo nemico
		assertTrue(board.getSpot(ROW_4, COL_D).getPiece().canMove(board, board.getSpot(ROW_4, COL_D), board.getSpot(ROW_3, COL_B)));
		board.getSpot(ROW_2, COL_C).setPiece(new Pawn(BLACK)); // pezzo nemico
		assertTrue(board.getSpot(ROW_4, COL_D).getPiece().canMove(board, board.getSpot(ROW_4, COL_D), board.getSpot(ROW_2, COL_C)));
	}

	@Test
	void testBishopMovement() {
		board = new Board(true);
		//caso di test per il movimento dell'alfiere
		board.getSpot(ROW_5, COL_E).setPiece(new Bishop(WHITE));
		assertTrue(board.getSpot(ROW_5, COL_E).getPiece().canMove(board, board.getSpot(ROW_5, COL_E), board.getSpot(ROW_8, COL_H))); // SE
		assertTrue(board.getSpot(ROW_5, COL_E).getPiece().canMove(board, board.getSpot(ROW_5, COL_E), board.getSpot(ROW_8, COL_B))); // SW
		assertTrue(board.getSpot(ROW_5, COL_E).getPiece().canMove(board, board.getSpot(ROW_5, COL_E), board.getSpot(ROW_2, COL_B))); // NW
		assertTrue(board.getSpot(ROW_5, COL_E).getPiece().canMove(board, board.getSpot(ROW_5, COL_E), board.getSpot(ROW_2, COL_H))); // NE

		// movimento in orizzontale
		assertFalse(board.getSpot(ROW_5, COL_E).getPiece().canMove(board, board.getSpot(ROW_5, COL_E), board.getSpot(ROW_5, COL_B)));
		// movimento in verticale
		assertFalse(board.getSpot(ROW_5, COL_E).getPiece().canMove(board, board.getSpot(ROW_5, COL_E), board.getSpot(ROW_2, COL_E)));

		// movimento con pezzi lungo il percorso
		board.getSpot(ROW_4, COL_D).setPiece(new Pawn(WHITE)); // pezzo amico
		assertFalse(board.getSpot(ROW_5, COL_E).getPiece().canMove(board, board.getSpot(ROW_5, COL_E), board.getSpot(ROW_2, COL_B)));
		board.getSpot(ROW_6, COL_F).setPiece(new Pawn(BLACK)); // pezzo nemico
		assertFalse(board.getSpot(ROW_5, COL_E).getPiece().canMove(board, board.getSpot(ROW_5, COL_E), board.getSpot(ROW_8, COL_H)));

		// cattura di un pezzo amico
		board.getSpot(ROW_2, COL_B).setPiece(new Pawn(WHITE)); // pezzo amico
		assertFalse(board.getSpot(ROW_5, COL_E).getPiece().canMove(board, board.getSpot(ROW_5, COL_E), board.getSpot(ROW_2, COL_B)));
		// cattura di un pezzo nemico (tutte e 4 le catture possibili)
		board.getSpot(ROW_6, COL_F).setPiece(new Pawn(BLACK)); // pezzo nemico
		assertTrue(board.getSpot(ROW_5, COL_E).getPiece().canMove(board, board.getSpot(ROW_5, COL_E), board.getSpot(ROW_6, COL_F))); // SE
		board.getSpot(ROW_6, COL_D).setPiece(new Pawn(BLACK)); // pezzo nemico
		assertTrue(board.getSpot(ROW_5, COL_E).getPiece().canMove(board, board.getSpot(ROW_5, COL_E), board.getSpot(ROW_6, COL_D))); // SW
		board.getSpot(ROW_4, COL_D).setPiece(new Pawn(BLACK)); // pezzo nemico
		assertTrue(board.getSpot(ROW_5, COL_E).getPiece().canMove(board, board.getSpot(ROW_5, COL_E), board.getSpot(ROW_4, COL_D))); // NW
		board.getSpot(ROW_4, COL_F).setPiece(new Pawn(BLACK)); // pezzo nemico
		assertTrue(board.getSpot(ROW_5, COL_E).getPiece().canMove(board, board.getSpot(ROW_5, COL_E), board.getSpot(ROW_4, COL_F))); // NE
	}

	@Test
	void testKingMovement() {
		board = new Board(true);
		// caso di test per il movimento del re (tutte le 8 mosse possibili)
		board.getSpot(ROW_4, COL_D).setPiece(new King(WHITE));
		assertTrue(board.getSpot(ROW_4, COL_D).getPiece().canMove(board, board.getSpot(ROW_4, COL_D), board.getSpot(ROW_3, COL_D))); // N
		assertTrue(board.getSpot(ROW_4, COL_D).getPiece().canMove(board, board.getSpot(ROW_4, COL_D), board.getSpot(ROW_3, COL_E))); // NE
		assertTrue(board.getSpot(ROW_4, COL_D).getPiece().canMove(board, board.getSpot(ROW_4, COL_D), board.getSpot(ROW_4, COL_E))); // E
		assertTrue(board.getSpot(ROW_4, COL_D).getPiece().canMove(board, board.getSpot(ROW_4, COL_D), board.getSpot(ROW_5, COL_E))); // SE
		assertTrue(board.getSpot(ROW_4, COL_D).getPiece().canMove(board, board.getSpot(ROW_4, COL_D), board.getSpot(ROW_5, COL_D))); // S
		assertTrue(board.getSpot(ROW_4, COL_D).getPiece().canMove(board, board.getSpot(ROW_4, COL_D), board.getSpot(ROW_5, COL_C))); // SW
		assertTrue(board.getSpot(ROW_4, COL_D).getPiece().canMove(board, board.getSpot(ROW_4, COL_D), board.getSpot(ROW_4, COL_C))); // W
		assertTrue(board.getSpot(ROW_4, COL_D).getPiece().canMove(board, board.getSpot(ROW_4, COL_D), board.getSpot(ROW_3, COL_C))); // NW

		// movimento in avanti di due caselle
		assertFalse(board.getSpot(ROW_4, COL_D).getPiece().canMove(board, board.getSpot(ROW_4, COL_D), board.getSpot(ROW_2, COL_D)));

		// cattura di un pezzo amico
		board.getSpot(ROW_5, COL_C).setPiece(new Pawn(WHITE)); // pezzo amico
		assertFalse(board.getSpot(ROW_4, COL_D).getPiece().canMove(board, board.getSpot(ROW_4, COL_D), board.getSpot(ROW_5, COL_C)));
		// cattura di un pezzo nemico (tutte le 8 catture possibili)
		board.getSpot(ROW_3, COL_D).setPiece(new Pawn(BLACK)); // pezzo nemico
		assertTrue(board.getSpot(ROW_4, COL_D).getPiece().canMove(board, board.getSpot(ROW_4, COL_D), board.getSpot(ROW_3, COL_D))); // N
		board.getSpot(ROW_3, COL_E).setPiece(new Pawn(BLACK)); // pezzo nemico
		assertTrue(board.getSpot(ROW_4, COL_D).getPiece().canMove(board, board.getSpot(ROW_4, COL_D), board.getSpot(ROW_3, COL_E))); // NE
		board.getSpot(ROW_4, COL_E).setPiece(new Pawn(BLACK)); // pezzo nemico
		assertTrue(board.getSpot(ROW_4, COL_D).getPiece().canMove(board, board.getSpot(ROW_4, COL_D), board.getSpot(ROW_4, COL_E))); // E
		board.getSpot(ROW_5, COL_E).setPiece(new Pawn(BLACK)); // pezzo nemico
		assertTrue(board.getSpot(ROW_4, COL_D).getPiece().canMove(board, board.getSpot(ROW_4, COL_D), board.getSpot(ROW_5, COL_E))); // SE
		board.getSpot(ROW_5, COL_D).setPiece(new Pawn(BLACK)); // pezzo nemico
		assertTrue(board.getSpot(ROW_4, COL_D).getPiece().canMove(board, board.getSpot(ROW_4, COL_D), board.getSpot(ROW_5, COL_D))); // S
		board.getSpot(ROW_5, COL_C).setPiece(new Pawn(BLACK)); // pezzo nemico
		assertTrue(board.getSpot(ROW_4, COL_D).getPiece().canMove(board, board.getSpot(ROW_4, COL_D), board.getSpot(ROW_5, COL_C))); // SW
		board.getSpot(ROW_4, COL_C).setPiece(new Pawn(BLACK)); // pezzo nemico
		assertTrue(board.getSpot(ROW_4, COL_D).getPiece().canMove(board, board.getSpot(ROW_4, COL_D), board.getSpot(ROW_4, COL_C))); // W
		board.getSpot(ROW_3, COL_C).setPiece(new Pawn(BLACK)); // pezzo nemico
		assertTrue(board.getSpot(ROW_4, COL_D).getPiece().canMove(board, board.getSpot(ROW_4, COL_D), board.getSpot(ROW_3, COL_C))); // NW
	}

	@Test
	void testQueenMovement() {
		board = new Board(true);
		// caso di test per il movimento della regina (tutte le 8 direzioni possibili)
		board.getSpot(ROW_3, COL_D).setPiece(new Queen(WHITE));
		assertTrue(board.getSpot(ROW_3, COL_D).getPiece().canMove(board, board.getSpot(ROW_3, COL_D), board.getSpot(ROW_1, COL_F))); // NE
		assertTrue(board.getSpot(ROW_3, COL_D).getPiece().canMove(board, board.getSpot(ROW_3, COL_D), board.getSpot(ROW_3, COL_G))); // E
		assertTrue(board.getSpot(ROW_3, COL_D).getPiece().canMove(board, board.getSpot(ROW_3, COL_D), board.getSpot(ROW_4, COL_E))); // SE
		assertTrue(board.getSpot(ROW_3, COL_D).getPiece().canMove(board, board.getSpot(ROW_3, COL_D), board.getSpot(ROW_8, COL_D))); // S
		assertTrue(board.getSpot(ROW_3, COL_D).getPiece().canMove(board, board.getSpot(ROW_3, COL_D), board.getSpot(ROW_6, COL_A))); // SW
		assertTrue(board.getSpot(ROW_3, COL_D).getPiece().canMove(board, board.getSpot(ROW_3, COL_D), board.getSpot(ROW_3, COL_B))); // W
		assertTrue(board.getSpot(ROW_3, COL_D).getPiece().canMove(board, board.getSpot(ROW_3, COL_D), board.getSpot(ROW_1, COL_B))); // NW
		assertTrue(board.getSpot(ROW_3, COL_D).getPiece().canMove(board, board.getSpot(ROW_3, COL_D), board.getSpot(ROW_2, COL_D))); // N

		// movimento con pezzi lungo il percorso
		board.getSpot(ROW_3, COL_E).setPiece(new Pawn(WHITE)); // pezzo amico
		assertFalse(board.getSpot(ROW_3, COL_D).getPiece().canMove(board, board.getSpot(ROW_3, COL_D), board.getSpot(ROW_3, COL_G)));
		board.getSpot(ROW_5, COL_D).setPiece(new Pawn(BLACK)); // pezzo nemico
		assertFalse(board.getSpot(ROW_3, COL_D).getPiece().canMove(board, board.getSpot(ROW_3, COL_D), board.getSpot(ROW_8, COL_D)));

		// cattura di un pezzo amico
		board.getSpot(ROW_6, COL_A).setPiece(new Pawn(WHITE)); // pezzo amico
		assertFalse(board.getSpot(ROW_3, COL_D).getPiece().canMove(board, board.getSpot(ROW_3, COL_D), board.getSpot(ROW_6, COL_A)));
		// cattura di un pezzo nemico (tutte le 8 catture possibili)
		board.getSpot(ROW_2, COL_E).setPiece(new Pawn(BLACK)); // pezzo nemico
		assertTrue(board.getSpot(ROW_3, COL_D).getPiece().canMove(board, board.getSpot(ROW_3, COL_D), board.getSpot(ROW_2, COL_E))); // NE
		board.getSpot(ROW_3, COL_E).setPiece(new Pawn(BLACK)); // pezzo nemico
		assertTrue(board.getSpot(ROW_3, COL_D).getPiece().canMove(board, board.getSpot(ROW_3, COL_D), board.getSpot(ROW_3, COL_E))); // E
		board.getSpot(ROW_4, COL_E).setPiece(new Pawn(BLACK)); // pezzo nemico
		assertTrue(board.getSpot(ROW_3, COL_D).getPiece().canMove(board, board.getSpot(ROW_3, COL_D), board.getSpot(ROW_4, COL_E))); // SE !
		board.getSpot(ROW_4, COL_D).setPiece(new Pawn(BLACK)); // pezzo nemico
		assertTrue(board.getSpot(ROW_3, COL_D).getPiece().canMove(board, board.getSpot(ROW_3, COL_D), board.getSpot(ROW_4, COL_D))); // S
		board.getSpot(ROW_4, COL_C).setPiece(new Pawn(BLACK)); // pezzo nemico
		assertTrue(board.getSpot(ROW_3, COL_D).getPiece().canMove(board, board.getSpot(ROW_3, COL_D), board.getSpot(ROW_4, COL_C))); // SW
		board.getSpot(ROW_3, COL_C).setPiece(new Pawn(BLACK)); // pezzo nemico
		assertTrue(board.getSpot(ROW_3, COL_D).getPiece().canMove(board, board.getSpot(ROW_3, COL_D), board.getSpot(ROW_3, COL_C))); // W
		board.getSpot(ROW_2, COL_C).setPiece(new Pawn(BLACK)); // pezzo nemico
		assertTrue(board.getSpot(ROW_3, COL_D).getPiece().canMove(board, board.getSpot(ROW_3, COL_D), board.getSpot(ROW_2, COL_C))); // NW
		board.getSpot(ROW_2, COL_D).setPiece(new Pawn(BLACK)); // pezzo nemico
		assertTrue(board.getSpot(ROW_3, COL_D).getPiece().canMove(board, board.getSpot(ROW_3, COL_D), board.getSpot(ROW_2, COL_D))); // N
	}
}
