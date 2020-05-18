package it.uniba.example;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import org.junit.After;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;

import org.junit.jupiter.api.Test;

import it.uniba.main.Bishop;
import it.uniba.main.Board;
import it.uniba.main.King;
import it.uniba.main.Queen;
import it.uniba.main.Knight;
import it.uniba.main.Move;
import it.uniba.main.Pawn;
import it.uniba.main.Piece;
import it.uniba.main.Rook;
import it.uniba.main.Spot;

public class TestPiece {
	private static Board board;
	private static Piece genericPiece;
	private static final boolean BLACK = false;
	private static final boolean WHITE = true;
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

	
	
	@BeforeEach
	void setup() {
		board = new Board(true);
		//board.getSpot(ROW_1, COL_E).setPiece(new King(WHITE));
	}

	
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
		// movimento in diagonale (con pezzo nemico)
		board.getSpot(ROW_3, COL_B).setPiece(new Pawn(BLACK)); // pezzo nemico
		assertTrue(board.getSpot(ROW_2, COL_A).getPiece().canMove(board, board.getSpot(ROW_2, COL_A), board.getSpot(ROW_3, COL_B)));
		// movimento in diagonale (con pezzo amico)
		board.getSpot(ROW_3, COL_B).setPiece(new Pawn(WHITE)); // pezzo amico
		assertFalse(board.getSpot(ROW_2, COL_A).getPiece().canMove(board, board.getSpot(ROW_2, COL_A), board.getSpot(ROW_3, COL_B)));

		// movimento in avanti con pedone di fronte
		board.getSpot(ROW_3, COL_A).setPiece(new Pawn(WHITE));
		assertFalse(board.getSpot(ROW_2, COL_A).getPiece().canMove(board, board.getSpot(ROW_2, COL_A), board.getSpot(ROW_3, COL_A)));
		// movimento in avanti di due caselle di un pedone gia' mosso
		board.getSpot(ROW_3, COL_A).getPiece().setAsMoved();
		assertFalse(board.getSpot(ROW_3, COL_A).getPiece().canMove(board, board.getSpot(ROW_3, COL_A), board.getSpot(ROW_5, COL_A)));
	}

	@Test
	void testRookMovement() {
		board = new Board(true);
		// caso di test per il movimento della torre
		board.getSpot(ROW_5, COL_D).setPiece(new Rook(BLACK));
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
		board.getSpot(ROW_5, COL_E).setPiece(new Rook(BLACK)); // pezzo amico
		assertFalse(board.getSpot(ROW_5, COL_D).getPiece().canMove(board, board.getSpot(ROW_5, COL_D), board.getSpot(ROW_5, COL_F)));
		board.getSpot(ROW_4, COL_D).setPiece(new Bishop(WHITE)); // pezzo nemico
		assertFalse(board.getSpot(ROW_5, COL_D).getPiece().canMove(board, board.getSpot(ROW_5, COL_D), board.getSpot(ROW_3, COL_D)));

		// cattura di un pezzo nemico
		assertTrue(board.getSpot(ROW_5, COL_D).getPiece().canMove(board, board.getSpot(ROW_5, COL_D), board.getSpot(ROW_4, COL_D)));
		// cattura di un pezzo amico
		board.getSpot(ROW_5, COL_E).setPiece(new Pawn(BLACK)); // pezzo amico
		assertFalse(board.getSpot(ROW_5, COL_D).getPiece().canMove(board, board.getSpot(ROW_5, COL_D), board.getSpot(ROW_5, COL_E)));
	}

	@Test
	void testKnightMovement() {
		board = new Board(true);
		// caso di test per il movimento del cavallo (tutte le 8 mosse possibili)
		board.getSpot(ROW_4, COL_D).setPiece(new Knight(BLACK));
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
		board.getSpot(ROW_3, COL_B).setPiece(new Pawn(BLACK)); // pezzo amico
		assertFalse(board.getSpot(ROW_4, COL_D).getPiece().canMove(board, board.getSpot(ROW_4, COL_D), board.getSpot(ROW_3, COL_B)));
		// cattura di un pezzo nemico
		board.getSpot(ROW_5, COL_F).setPiece(new Pawn(WHITE));
		assertTrue(board.getSpot(ROW_4, COL_D).getPiece().canMove(board, board.getSpot(ROW_4, COL_D), board.getSpot(ROW_5, COL_F)));
	}

	


	
	@Test
	public void testKingRecalculateMovements() {
		Spot kingSpot = board.getSpot(ROW_7, COL_B);
		kingSpot.setPiece(new King(WHITE));
		Piece kingPiece = kingSpot.getPiece();
		kingSpot.setPiece(new King(WHITE));
		kingPiece.findLegalMoves(board, kingSpot);

		// test sulla lunghezza dell'arraylist di mosse (in questo caso 8 -> b6, b5)
		// board2.showBoard();
		assertEquals(kingPiece.getLegalMoves().size(), 8);
		// test sulle mosse presenti all'interno dell'arrayList di mosse
		assertTrue(kingPiece.getLegalMoves().contains(new Move(kingSpot, new Spot(ROW_6, COL_B))));
		assertTrue(kingPiece.getLegalMoves().contains(new Move(kingSpot, new Spot(ROW_8, COL_B))));
		assertTrue(kingPiece.getLegalMoves().contains(new Move(kingSpot, new Spot(ROW_6, COL_A))));
		assertTrue(kingPiece.getLegalMoves().contains(new Move(kingSpot, new Spot(ROW_7, COL_A))));
		assertTrue(kingPiece.getLegalMoves().contains(new Move(kingSpot, new Spot(ROW_8, COL_A))));
		assertTrue(kingPiece.getLegalMoves().contains(new Move(kingSpot, new Spot(ROW_6, COL_C))));
		assertTrue(kingPiece.getLegalMoves().contains(new Move(kingSpot, new Spot(ROW_7, COL_C))));
		assertTrue(kingPiece.getLegalMoves().contains(new Move(kingSpot, new Spot(ROW_8, COL_C))));

		Spot rookSpot = board.getSpot(ROW_7, COL_C);
		rookSpot.setPiece(new Rook(BLACK));
		Piece rookPiece = rookSpot.getPiece();
		rookSpot.setPiece(new Rook(BLACK));
		rookPiece.findLegalMoves(board, rookSpot);

		// test sulla lunghezza dell'arraylist di mosse (in questo caso 5 -> b6, b5)
		//board.showBoard();
		kingPiece.recalculateMoves(board);
		assertEquals(kingPiece.getLegalMoves().size(), 5);
	}

}
