package it.uniba.example;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import org.junit.jupiter.api.BeforeEach;
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

public class PieceTest {
	private static Board board;
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
	}

	@Test
	void testPawnMovement() {
		// caso di test per il movimento del pedone
		board.getSpot(ROW_2, COL_A).setPiece(new Pawn(WHITE));
		// movimento in avanti di una casella
		assertTrue(board.getSpot(ROW_2, COL_A).getPiece().canMove(board, board.getSpot(ROW_2, COL_A),
				board.getSpot(ROW_3, COL_A)));
		// movimento in avanti di due caselle
		assertTrue(board.getSpot(ROW_2, COL_A).getPiece().canMove(board, board.getSpot(ROW_2, COL_A),
				board.getSpot(ROW_4, COL_A)));
		// movimento in avanti di tre caselle
		assertFalse(board.getSpot(ROW_2, COL_A).getPiece().canMove(board, board.getSpot(ROW_2, COL_A),
				board.getSpot(ROW_5, COL_A)));
		// movimento indietro
		assertFalse(board.getSpot(ROW_2, COL_A).getPiece().canMove(board, board.getSpot(ROW_2, COL_A),
				board.getSpot(ROW_1, COL_A)));

		// movimento in diagonale (senza pezzi nemici)
		assertFalse(board.getSpot(ROW_2, COL_A).getPiece().canMove(board, board.getSpot(ROW_2, COL_A),
				board.getSpot(ROW_3, COL_B)));
		// movimento in diagonale (con pezzo nemico)
		board.getSpot(ROW_3, COL_B).setPiece(new Pawn(BLACK)); // pezzo nemico
		assertTrue(board.getSpot(ROW_2, COL_A).getPiece().canMove(board, board.getSpot(ROW_2, COL_A),
				board.getSpot(ROW_3, COL_B)));
		// movimento in diagonale (con pezzo amico)
		board.getSpot(ROW_3, COL_B).setPiece(new Pawn(WHITE)); // pezzo amico
		assertFalse(board.getSpot(ROW_2, COL_A).getPiece().canMove(board, board.getSpot(ROW_2, COL_A),
				board.getSpot(ROW_3, COL_B)));

		// movimento in avanti con pedone di fronte
		board.getSpot(ROW_3, COL_A).setPiece(new Pawn(WHITE));
		assertFalse(board.getSpot(ROW_2, COL_A).getPiece().canMove(board, board.getSpot(ROW_2, COL_A),
				board.getSpot(ROW_3, COL_A)));
		// movimento in avanti di due caselle di un pedone gia' mosso
		board.getSpot(ROW_3, COL_A).getPiece().setAsMoved();
		assertFalse(board.getSpot(ROW_3, COL_A).getPiece().canMove(board, board.getSpot(ROW_3, COL_A),
				board.getSpot(ROW_5, COL_A)));
	}

	@Test
	void testRookMovement() {
		// caso di test per il movimento della torre
		board.getSpot(ROW_5, COL_D).setPiece(new Rook(BLACK));
		// movimento in verticale (Nord e Sud)
		assertTrue(board.getSpot(ROW_5, COL_D).getPiece().canMove(board, board.getSpot(ROW_5, COL_D),
				board.getSpot(ROW_1, COL_D))); // N
		assertTrue(board.getSpot(ROW_5, COL_D).getPiece().canMove(board, board.getSpot(ROW_5, COL_D),
				board.getSpot(ROW_8, COL_D))); // S
		// movimento in orizzontale (Est e Ovest)
		assertTrue(board.getSpot(ROW_5, COL_D).getPiece().canMove(board, board.getSpot(ROW_5, COL_D),
				board.getSpot(ROW_5, COL_H))); // E
		assertTrue(board.getSpot(ROW_5, COL_D).getPiece().canMove(board, board.getSpot(ROW_5, COL_D),
				board.getSpot(ROW_5, COL_A))); // W
		// movimento in diagonale
		assertFalse(board.getSpot(ROW_5, COL_D).getPiece().canMove(board, board.getSpot(ROW_5, COL_D),
				board.getSpot(ROW_3, COL_F))); // NE
		assertFalse(board.getSpot(ROW_5, COL_D).getPiece().canMove(board, board.getSpot(ROW_5, COL_D),
				board.getSpot(ROW_7, COL_B))); // SW

		// movimento con pezzi lungo il percorso
		board.getSpot(ROW_5, COL_E).setPiece(new Rook(BLACK)); // pezzo amico
		assertFalse(board.getSpot(ROW_5, COL_D).getPiece().canMove(board, board.getSpot(ROW_5, COL_D),
				board.getSpot(ROW_5, COL_F)));
		board.getSpot(ROW_4, COL_D).setPiece(new Bishop(WHITE)); // pezzo nemico
		assertFalse(board.getSpot(ROW_5, COL_D).getPiece().canMove(board, board.getSpot(ROW_5, COL_D),
				board.getSpot(ROW_3, COL_D)));

		// cattura di un pezzo nemico
		assertTrue(board.getSpot(ROW_5, COL_D).getPiece().canMove(board, board.getSpot(ROW_5, COL_D),
				board.getSpot(ROW_4, COL_D)));
		// cattura di un pezzo amico
		board.getSpot(ROW_5, COL_E).setPiece(new Pawn(BLACK)); // pezzo amico
		assertFalse(board.getSpot(ROW_5, COL_D).getPiece().canMove(board, board.getSpot(ROW_5, COL_D),
				board.getSpot(ROW_5, COL_E)));
	}

	@Test
	void testKnightMovement() {
		// caso di test per il movimento del cavallo (tutte le 8 mosse possibili)
		board.getSpot(ROW_4, COL_D).setPiece(new Knight(BLACK));
		assertTrue(board.getSpot(ROW_4, COL_D).getPiece().canMove(board, board.getSpot(ROW_4, COL_D),
				board.getSpot(ROW_2, COL_E)));
		assertTrue(board.getSpot(ROW_4, COL_D).getPiece().canMove(board, board.getSpot(ROW_4, COL_D),
				board.getSpot(ROW_3, COL_F)));
		assertTrue(board.getSpot(ROW_4, COL_D).getPiece().canMove(board, board.getSpot(ROW_4, COL_D),
				board.getSpot(ROW_5, COL_F)));
		assertTrue(board.getSpot(ROW_4, COL_D).getPiece().canMove(board, board.getSpot(ROW_4, COL_D),
				board.getSpot(ROW_6, COL_E)));
		assertTrue(board.getSpot(ROW_4, COL_D).getPiece().canMove(board, board.getSpot(ROW_4, COL_D),
				board.getSpot(ROW_6, COL_C)));
		assertTrue(board.getSpot(ROW_4, COL_D).getPiece().canMove(board, board.getSpot(ROW_4, COL_D),
				board.getSpot(ROW_5, COL_B)));
		assertTrue(board.getSpot(ROW_4, COL_D).getPiece().canMove(board, board.getSpot(ROW_4, COL_D),
				board.getSpot(ROW_3, COL_B)));
		assertTrue(board.getSpot(ROW_4, COL_D).getPiece().canMove(board, board.getSpot(ROW_4, COL_D),
				board.getSpot(ROW_2, COL_C)));

		// movimento illegale in una casella vuota
		assertFalse(board.getSpot(ROW_4, COL_D).getPiece().canMove(board, board.getSpot(ROW_4, COL_D),
				board.getSpot(ROW_4, COL_C)));

		// cattura di un pezzo amico
		board.getSpot(ROW_3, COL_B).setPiece(new Pawn(BLACK)); // pezzo amico
		assertFalse(board.getSpot(ROW_4, COL_D).getPiece().canMove(board, board.getSpot(ROW_4, COL_D),
				board.getSpot(ROW_3, COL_B)));
		// cattura di un pezzo nemico
		board.getSpot(ROW_5, COL_F).setPiece(new Pawn(WHITE));
		assertTrue(board.getSpot(ROW_4, COL_D).getPiece().canMove(board, board.getSpot(ROW_4, COL_D),
				board.getSpot(ROW_5, COL_F)));
	}

	@Test
	void testBishopMovement() {
		// caso di test per il movimento dell'alfiere
		board.getSpot(ROW_5, COL_E).setPiece(new Bishop(BLACK));
		assertTrue(board.getSpot(ROW_5, COL_E).getPiece().canMove(board, board.getSpot(ROW_5, COL_E),
				board.getSpot(ROW_8, COL_H))); // SE
		assertTrue(board.getSpot(ROW_5, COL_E).getPiece().canMove(board, board.getSpot(ROW_5, COL_E),
				board.getSpot(ROW_8, COL_B))); // SW
		assertTrue(board.getSpot(ROW_5, COL_E).getPiece().canMove(board, board.getSpot(ROW_5, COL_E),
				board.getSpot(ROW_2, COL_B))); // NW
		assertTrue(board.getSpot(ROW_5, COL_E).getPiece().canMove(board, board.getSpot(ROW_5, COL_E),
				board.getSpot(ROW_2, COL_H))); // NE

		// movimento in orizzontale
		assertFalse(board.getSpot(ROW_5, COL_E).getPiece().canMove(board, board.getSpot(ROW_5, COL_E),
				board.getSpot(ROW_5, COL_B)));
		// movimento in verticale
		assertFalse(board.getSpot(ROW_5, COL_E).getPiece().canMove(board, board.getSpot(ROW_5, COL_E),
				board.getSpot(ROW_2, COL_E)));

		// movimento con pezzi lungo il percorso
		board.getSpot(ROW_4, COL_D).setPiece(new Rook(BLACK)); // pezzo amico
		assertFalse(board.getSpot(ROW_5, COL_E).getPiece().canMove(board, board.getSpot(ROW_5, COL_E),
				board.getSpot(ROW_2, COL_B)));
		board.getSpot(ROW_6, COL_F).setPiece(new Pawn(WHITE)); // pezzo nemico
		assertFalse(board.getSpot(ROW_5, COL_E).getPiece().canMove(board, board.getSpot(ROW_5, COL_E),
				board.getSpot(ROW_8, COL_H)));

		// cattura di un pezzo amico
		board.getSpot(ROW_2, COL_B).setPiece(new Queen(BLACK)); // pezzo amico
		assertFalse(board.getSpot(ROW_5, COL_E).getPiece().canMove(board, board.getSpot(ROW_5, COL_E),
				board.getSpot(ROW_2, COL_B)));
		// cattura di un pezzo nemico
		board.getSpot(ROW_8, COL_B).setPiece(new Rook(WHITE)); // pezzo nemico
		assertTrue(board.getSpot(ROW_5, COL_E).getPiece().canMove(board, board.getSpot(ROW_5, COL_E),
				board.getSpot(ROW_8, COL_B)));
	}

	@Test
	void testQueenMovement() {
		// caso di test per il movimento della regina (tutte le 8 direzioni possibili)
		board.getSpot(ROW_3, COL_D).setPiece(new Queen(BLACK));
		assertTrue(board.getSpot(ROW_3, COL_D).getPiece().canMove(board, board.getSpot(ROW_3, COL_D),
				board.getSpot(ROW_1, COL_F))); // NE
		assertTrue(board.getSpot(ROW_3, COL_D).getPiece().canMove(board, board.getSpot(ROW_3, COL_D),
				board.getSpot(ROW_3, COL_G))); // E
		assertTrue(board.getSpot(ROW_3, COL_D).getPiece().canMove(board, board.getSpot(ROW_3, COL_D),
				board.getSpot(ROW_4, COL_E))); // SE
		assertTrue(board.getSpot(ROW_3, COL_D).getPiece().canMove(board, board.getSpot(ROW_3, COL_D),
				board.getSpot(ROW_8, COL_D))); // S
		assertTrue(board.getSpot(ROW_3, COL_D).getPiece().canMove(board, board.getSpot(ROW_3, COL_D),
				board.getSpot(ROW_6, COL_A))); // SW
		assertTrue(board.getSpot(ROW_3, COL_D).getPiece().canMove(board, board.getSpot(ROW_3, COL_D),
				board.getSpot(ROW_3, COL_B))); // W
		assertTrue(board.getSpot(ROW_3, COL_D).getPiece().canMove(board, board.getSpot(ROW_3, COL_D),
				board.getSpot(ROW_1, COL_B))); // NW
		assertTrue(board.getSpot(ROW_3, COL_D).getPiece().canMove(board, board.getSpot(ROW_3, COL_D),
				board.getSpot(ROW_2, COL_D))); // N

		// movimento con pezzi lungo il percorso
		board.getSpot(ROW_3, COL_E).setPiece(new Pawn(BLACK)); // pezzo amico
		assertFalse(board.getSpot(ROW_3, COL_D).getPiece().canMove(board, board.getSpot(ROW_3, COL_D),
				board.getSpot(ROW_3, COL_G)));
		board.getSpot(ROW_5, COL_D).setPiece(new Pawn(WHITE)); // pezzo nemico
		assertFalse(board.getSpot(ROW_3, COL_D).getPiece().canMove(board, board.getSpot(ROW_3, COL_D),
				board.getSpot(ROW_8, COL_D)));

		// cattura di un pezzo amico
		board.getSpot(ROW_6, COL_A).setPiece(new Pawn(BLACK)); // pezzo amico
		assertFalse(board.getSpot(ROW_3, COL_D).getPiece().canMove(board, board.getSpot(ROW_3, COL_D),
				board.getSpot(ROW_6, COL_A)));
		// cattura di un pezzo nemico (tutte le 8 catture possibili)
		board.getSpot(ROW_2, COL_E).setPiece(new Pawn(WHITE)); // pezzo nemico
		assertTrue(board.getSpot(ROW_3, COL_D).getPiece().canMove(board, board.getSpot(ROW_3, COL_D),
				board.getSpot(ROW_2, COL_E))); // NE
		board.getSpot(ROW_3, COL_E).setPiece(new Pawn(WHITE)); // pezzo nemico
		assertTrue(board.getSpot(ROW_3, COL_D).getPiece().canMove(board, board.getSpot(ROW_3, COL_D),
				board.getSpot(ROW_3, COL_E))); // E
		board.getSpot(ROW_4, COL_E).setPiece(new Pawn(WHITE)); // pezzo nemico
		assertTrue(board.getSpot(ROW_3, COL_D).getPiece().canMove(board, board.getSpot(ROW_3, COL_D),
				board.getSpot(ROW_4, COL_E))); // SE !
		board.getSpot(ROW_4, COL_D).setPiece(new Pawn(WHITE)); // pezzo nemico
		assertTrue(board.getSpot(ROW_3, COL_D).getPiece().canMove(board, board.getSpot(ROW_3, COL_D),
				board.getSpot(ROW_4, COL_D))); // S
		board.getSpot(ROW_4, COL_C).setPiece(new Pawn(WHITE)); // pezzo nemico
		assertTrue(board.getSpot(ROW_3, COL_D).getPiece().canMove(board, board.getSpot(ROW_3, COL_D),
				board.getSpot(ROW_4, COL_C))); // SW
		board.getSpot(ROW_3, COL_C).setPiece(new Pawn(WHITE)); // pezzo nemico
		assertTrue(board.getSpot(ROW_3, COL_D).getPiece().canMove(board, board.getSpot(ROW_3, COL_D),
				board.getSpot(ROW_3, COL_C))); // W
		board.getSpot(ROW_2, COL_C).setPiece(new Pawn(WHITE)); // pezzo nemico
		assertTrue(board.getSpot(ROW_3, COL_D).getPiece().canMove(board, board.getSpot(ROW_3, COL_D),
				board.getSpot(ROW_2, COL_C))); // NW
		board.getSpot(ROW_2, COL_D).setPiece(new Pawn(WHITE)); // pezzo nemico
		assertTrue(board.getSpot(ROW_3, COL_D).getPiece().canMove(board, board.getSpot(ROW_3, COL_D),
				board.getSpot(ROW_2, COL_D))); // N
	}

	@Test
	void testKingMovement() {
		// caso di test per il movimento del re (tutte le 8 mosse possibili)
		board.getSpot(ROW_4, COL_D).setPiece(new King(BLACK));
		assertTrue(board.getSpot(ROW_4, COL_D).getPiece().canMove(board, board.getSpot(ROW_4, COL_D),
				board.getSpot(ROW_3, COL_D))); // N
		assertTrue(board.getSpot(ROW_4, COL_D).getPiece().canMove(board, board.getSpot(ROW_4, COL_D),
				board.getSpot(ROW_3, COL_E))); // NE
		assertTrue(board.getSpot(ROW_4, COL_D).getPiece().canMove(board, board.getSpot(ROW_4, COL_D),
				board.getSpot(ROW_4, COL_E))); // E
		assertTrue(board.getSpot(ROW_4, COL_D).getPiece().canMove(board, board.getSpot(ROW_4, COL_D),
				board.getSpot(ROW_5, COL_E))); // SE
		assertTrue(board.getSpot(ROW_4, COL_D).getPiece().canMove(board, board.getSpot(ROW_4, COL_D),
				board.getSpot(ROW_5, COL_D))); // S
		assertTrue(board.getSpot(ROW_4, COL_D).getPiece().canMove(board, board.getSpot(ROW_4, COL_D),
				board.getSpot(ROW_5, COL_C))); // SW
		assertTrue(board.getSpot(ROW_4, COL_D).getPiece().canMove(board, board.getSpot(ROW_4, COL_D),
				board.getSpot(ROW_4, COL_C))); // W
		assertTrue(board.getSpot(ROW_4, COL_D).getPiece().canMove(board, board.getSpot(ROW_4, COL_D),
				board.getSpot(ROW_3, COL_C))); // NW

		// movimento in avanti di due caselle
		assertFalse(board.getSpot(ROW_4, COL_D).getPiece().canMove(board, board.getSpot(ROW_4, COL_D),
				board.getSpot(ROW_2, COL_D)));

		// cattura di un pezzo amico
		board.getSpot(ROW_5, COL_C).setPiece(new Pawn(BLACK)); // pezzo amico
		assertFalse(board.getSpot(ROW_4, COL_D).getPiece().canMove(board, board.getSpot(ROW_4, COL_D),
				board.getSpot(ROW_5, COL_C)));
		// cattura di un pezzo nemico (tutte le 8 catture possibili)
		board.getSpot(ROW_3, COL_D).setPiece(new Pawn(WHITE)); // pezzo nemico
		assertTrue(board.getSpot(ROW_4, COL_D).getPiece().canMove(board, board.getSpot(ROW_4, COL_D),
				board.getSpot(ROW_3, COL_D))); // N
		board.getSpot(ROW_3, COL_E).setPiece(new Pawn(WHITE)); // pezzo nemico
		assertTrue(board.getSpot(ROW_4, COL_D).getPiece().canMove(board, board.getSpot(ROW_4, COL_D),
				board.getSpot(ROW_3, COL_E))); // NE
		board.getSpot(ROW_4, COL_E).setPiece(new Pawn(WHITE)); // pezzo nemico
		assertTrue(board.getSpot(ROW_4, COL_D).getPiece().canMove(board, board.getSpot(ROW_4, COL_D),
				board.getSpot(ROW_4, COL_E))); // E
		board.getSpot(ROW_5, COL_E).setPiece(new Pawn(WHITE)); // pezzo nemico
		assertTrue(board.getSpot(ROW_4, COL_D).getPiece().canMove(board, board.getSpot(ROW_4, COL_D),
				board.getSpot(ROW_5, COL_E))); // SE
		board.getSpot(ROW_5, COL_D).setPiece(new Pawn(WHITE)); // pezzo nemico
		assertTrue(board.getSpot(ROW_4, COL_D).getPiece().canMove(board, board.getSpot(ROW_4, COL_D),
				board.getSpot(ROW_5, COL_D))); // S
		board.getSpot(ROW_5, COL_C).setPiece(new Pawn(WHITE)); // pezzo nemico
		assertTrue(board.getSpot(ROW_4, COL_D).getPiece().canMove(board, board.getSpot(ROW_4, COL_D),
				board.getSpot(ROW_5, COL_C))); // SW
		board.getSpot(ROW_4, COL_C).setPiece(new Pawn(WHITE)); // pezzo nemico
		assertTrue(board.getSpot(ROW_4, COL_D).getPiece().canMove(board, board.getSpot(ROW_4, COL_D),
				board.getSpot(ROW_4, COL_C))); // W
		board.getSpot(ROW_3, COL_C).setPiece(new Pawn(WHITE)); // pezzo nemico
		assertTrue(board.getSpot(ROW_4, COL_D).getPiece().canMove(board, board.getSpot(ROW_4, COL_D),
				board.getSpot(ROW_3, COL_C))); // NW
	}

	
	
	/**Verifichiamo che il pedone posto
	 * a difesa del Re, non può effettuare alcun movimento
	 * in quanto lascerebbe il re indifeso ad un attacco
	 * nel turno immediatamente successivo 
	 */
	@Test
	public void testPawnRecalculateMovements() {
		Spot kingSpot = board.getSpot(ROW_1, COL_E);
		kingSpot.setPiece(new King(WHITE));
		Piece kingPiece = kingSpot.getPiece();
		kingSpot.setPiece(new King(WHITE));
		kingPiece.findLegalMoves(board, kingSpot);

		Spot pawnSpot = board.getSpot(ROW_2, COL_D);
		pawnSpot.setPiece(new Pawn(WHITE));
		Piece pawnPiece = pawnSpot.getPiece();
		pawnSpot.setPiece(new Pawn(WHITE));
		pawnPiece.findLegalMoves(board, pawnSpot);

		Spot bishopSpot = board.getSpot(ROW_4, COL_B);
		bishopSpot.setPiece(new Bishop(BLACK));
		Piece bishopPiece = bishopSpot.getPiece();
		bishopSpot.setPiece(new Bishop(BLACK));
		bishopPiece.findLegalMoves(board, bishopSpot);

		pawnPiece.recalculateMoves(board);

		// test sulla lunghezza dell'arraylist di mosse del pedone (in questo caso 0)
		assertEquals(pawnPiece.getLegalMoves().size(), 0);
	}

	/**
	 * Verifichiamo che il cavallo può solamente 
	 * catturare l'alfiere che minaccia il Re.
	 */
	@Test
	public void testKnightRecalculateMovements() {
		Spot kingSpot = board.getSpot(ROW_1, COL_E);
		kingSpot.setPiece(new King(WHITE));
		Piece kingPiece = kingSpot.getPiece();
		kingSpot.setPiece(new King(WHITE));
		kingPiece.findLegalMoves(board, kingSpot);

		Spot knightSpot = board.getSpot(ROW_2, COL_C);
		knightSpot.setPiece(new Knight(WHITE));
		Piece knightPiece = knightSpot.getPiece();
		knightSpot.setPiece(new Knight(WHITE));
		knightPiece.findLegalMoves(board, knightSpot);

		Spot bishopSpot = board.getSpot(ROW_4, COL_B);
		bishopSpot.setPiece(new Bishop(BLACK));
		Piece bishopPiece = bishopSpot.getPiece();
		bishopSpot.setPiece(new Bishop(BLACK));
		bishopPiece.findLegalMoves(board, bishopSpot);

		knightPiece.recalculateMoves(board);
		// test sulla lunghezza dell'arraylist di mosse del pedone (1 mossa: catturare l'alfiere)
		assertEquals(knightPiece.getLegalMoves().size(), 1);
		
		assertTrue(knightPiece.getLegalMoves().contains(new Move(knightSpot, new Spot(ROW_4, COL_B))));

	}
	
	/**Verifichiamo che la torre posta
	 * a difesa del Re, non può effettuare alcun movimento
	 * in quanto lascerebbe il re indifeso ad un attacco
	 */
	@Test
	public void testRookRecalculateMovements() {
		Spot kingSpot = board.getSpot(ROW_1, COL_E);
		kingSpot.setPiece(new King(WHITE));
		Piece kingPiece = kingSpot.getPiece();
		kingSpot.setPiece(new King(WHITE));
		kingPiece.findLegalMoves(board, kingSpot);

		Spot rookSpot = board.getSpot(ROW_2, COL_D);
		rookSpot.setPiece(new Rook(WHITE));
		Piece rookPiece = rookSpot.getPiece();
		rookSpot.setPiece(new Rook(WHITE));
		rookPiece.findLegalMoves(board, rookSpot);

		Spot bishopSpot = board.getSpot(ROW_4, COL_B);
		bishopSpot.setPiece(new Bishop(BLACK));
		Piece bishopPiece = bishopSpot.getPiece();
		bishopSpot.setPiece(new Bishop(BLACK));
		bishopPiece.findLegalMoves(board, bishopSpot);

		rookPiece.recalculateMoves(board);

		// test sulla lunghezza dell'arraylist di mosse della Torre (in questo caso 0)
		assertEquals(rookPiece.getLegalMoves().size(), 0);
	}

	@Test
	public void testBishopRecalculateMovements() {
		Spot kingSpot = board.getSpot(ROW_1, COL_E);
		kingSpot.setPiece(new King(WHITE));
		Piece kingPiece = kingSpot.getPiece();
		kingSpot.setPiece(new King(WHITE));
		kingPiece.findLegalMoves(board, kingSpot);

		Spot bishopSpotW = board.getSpot(ROW_2, COL_E);
		bishopSpotW.setPiece(new Bishop(WHITE));
		Piece bishopPieceW = bishopSpotW.getPiece();
		bishopSpotW.setPiece(new Bishop(WHITE));
		bishopPieceW.findLegalMoves(board, bishopSpotW);

		Spot rookSpot = board.getSpot(ROW_3, COL_E);
		rookSpot.setPiece(new Rook(BLACK));
		Piece rookPiece = rookSpot.getPiece();
		rookSpot.setPiece(new Rook(BLACK));
		rookPiece.findLegalMoves(board, rookSpot);

		bishopPieceW.recalculateMoves(board);

		// test sulla lunghezza dell'arraylist di mosse della Torre (in questo caso 0)
		assertEquals(bishopPieceW.getLegalMoves().size(), 0);

	}

	@Test
	public void testQueenRecalculateMovements() {
		Spot kingSpot = board.getSpot(ROW_1, COL_E);
		kingSpot.setPiece(new King(WHITE));
		Piece kingPiece = kingSpot.getPiece();
		kingSpot.setPiece(new King(WHITE));
		kingPiece.findLegalMoves(board, kingSpot);

		Spot queenSpot = board.getSpot(ROW_2, COL_D);
		queenSpot.setPiece(new Queen(WHITE));
		Piece queenPiece = queenSpot.getPiece();
		queenSpot.setPiece(new Queen(WHITE));
		queenPiece.findLegalMoves(board, queenSpot);

		Spot bishopSpot = board.getSpot(ROW_4, COL_B);
		bishopSpot.setPiece(new Bishop(BLACK));
		Piece bishopPiece = bishopSpot.getPiece();
		bishopSpot.setPiece(new Bishop(BLACK));
		bishopPiece.findLegalMoves(board, bishopSpot);

		queenPiece.recalculateMoves(board);

		// test sulla lunghezza dell'arraylist di mosse della Torre (c3, c4)
		assertEquals(queenPiece.getLegalMoves().size(), 2);

		// test sulle mosse presenti all'interno dell'arrayList di mosse
		assertTrue(queenPiece.getLegalMoves().contains(new Move(queenSpot, new Spot(ROW_4, COL_B))));
		assertTrue(queenPiece.getLegalMoves().contains(new Move(queenSpot, new Spot(ROW_3, COL_C))));
		
		//riduco le possibilita' di movimento per la donna
		Spot queenSpot2 = board.getSpot(ROW_3, COL_C);
		queenSpot2.setPiece(new Queen(BLACK));
		Piece queenPieceB = queenSpot2.getPiece();
		queenSpot2.setPiece(new Queen(BLACK));
		queenPieceB.findLegalMoves(board, queenSpot2);

		queenPiece.recalculateMoves(board);

		// test sulla lunghezza dell'arraylist di mosse della Torre (in questo caso 1))
		assertEquals(queenPiece.getLegalMoves().size(), 1);

		// test sulle mosse presenti all'interno dell'arrayList di mosse
		assertTrue(queenPiece.getLegalMoves().contains(new Move(queenSpot, new Spot(ROW_3, COL_C))));

	}

	
	/**
	 * Test sui movimenti legali del Re e 
	 * loro ricalcolo in base al fatto che
	 * il re puo' finire sotto attacco.
	 */
	@Test
	public void testKingRecalculateMovements() {
		Spot kingSpot = board.getSpot(ROW_7, COL_B);
		kingSpot.setPiece(new King(WHITE));
		Piece kingPiece = kingSpot.getPiece();
		kingSpot.setPiece(new King(WHITE));
		kingPiece.findLegalMoves(board, kingSpot);

		// test sulla lunghezza dell'arraylist di mosse (in questo caso 8)
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

		// test sulla lunghezza dell'arraylist di mosse (in questo caso 5)
		// board.showBoard();
		kingPiece.recalculateMoves(board);
		assertEquals(kingPiece.getLegalMoves().size(), 5);

		// movimenti validi rimasti
		assertTrue(kingPiece.getLegalMoves().contains(new Move(kingSpot, new Spot(ROW_6, COL_B))));
		assertTrue(kingPiece.getLegalMoves().contains(new Move(kingSpot, new Spot(ROW_8, COL_B))));
		assertTrue(kingPiece.getLegalMoves().contains(new Move(kingSpot, new Spot(ROW_6, COL_A))));
		assertTrue(kingPiece.getLegalMoves().contains(new Move(kingSpot, new Spot(ROW_8, COL_A))));
		assertTrue(kingPiece.getLegalMoves().contains(new Move(kingSpot, new Spot(ROW_7, COL_C))));

		// movimenti non piu' validi
		assertFalse(kingPiece.getLegalMoves().contains(new Move(kingSpot, new Spot(ROW_7, COL_A))));
		assertFalse(kingPiece.getLegalMoves().contains(new Move(kingSpot, new Spot(ROW_6, COL_C))));
		assertFalse(kingPiece.getLegalMoves().contains(new Move(kingSpot, new Spot(ROW_8, COL_C))));

	
		Spot queenSpot = board.getSpot(ROW_8, COL_C);
		queenSpot.setPiece(new Queen(BLACK));
		Piece queenPiece = queenSpot.getPiece();
		queenSpot.setPiece(new Queen(BLACK));
		queenPiece.findLegalMoves(board, queenSpot);
		kingPiece.recalculateMoves(board);

		// unica mossa possibile per il re in b6
		assertEquals(kingPiece.getLegalMoves().size(), 1);

		// movimenti validi rimasti
		assertTrue(kingPiece.getLegalMoves().contains(new Move(kingSpot, new Spot(ROW_6, COL_B))));

		// movimenti non piu' validi
		assertFalse(kingPiece.getLegalMoves().contains(new Move(kingSpot, new Spot(ROW_7, COL_A))));
		assertFalse(kingPiece.getLegalMoves().contains(new Move(kingSpot, new Spot(ROW_6, COL_C))));
		assertFalse(kingPiece.getLegalMoves().contains(new Move(kingSpot, new Spot(ROW_8, COL_C))));
		assertFalse(kingPiece.getLegalMoves().contains(new Move(kingSpot, new Spot(ROW_8, COL_B))));
		assertFalse(kingPiece.getLegalMoves().contains(new Move(kingSpot, new Spot(ROW_6, COL_A))));
		assertFalse(kingPiece.getLegalMoves().contains(new Move(kingSpot, new Spot(ROW_8, COL_A))));
		assertFalse(kingPiece.getLegalMoves().contains(new Move(kingSpot, new Spot(ROW_7, COL_C))));

		Spot rookSpot2 = board.getSpot(ROW_6, COL_C);
		rookSpot2.setPiece(new Rook(BLACK));
		Piece rookPiece2 = rookSpot2.getPiece();
		rookSpot2.setPiece(new Rook(BLACK));
		rookPiece2.findLegalMoves(board, rookSpot);

		kingPiece.recalculateMoves(board);

		// nessuna mossa possibile per il re
		assertEquals(kingPiece.getLegalMoves().size(), 0);

		// movimenti non piu' validi
		assertFalse(kingPiece.getLegalMoves().contains(new Move(kingSpot, new Spot(ROW_7, COL_A))));
		assertFalse(kingPiece.getLegalMoves().contains(new Move(kingSpot, new Spot(ROW_6, COL_C))));
		assertFalse(kingPiece.getLegalMoves().contains(new Move(kingSpot, new Spot(ROW_8, COL_C))));
		assertFalse(kingPiece.getLegalMoves().contains(new Move(kingSpot, new Spot(ROW_8, COL_B))));
		assertFalse(kingPiece.getLegalMoves().contains(new Move(kingSpot, new Spot(ROW_6, COL_A))));
		assertFalse(kingPiece.getLegalMoves().contains(new Move(kingSpot, new Spot(ROW_8, COL_A))));
		assertFalse(kingPiece.getLegalMoves().contains(new Move(kingSpot, new Spot(ROW_7, COL_C))));
		assertFalse(kingPiece.getLegalMoves().contains(new Move(kingSpot, new Spot(ROW_6, COL_B))));
			
	}


	/**
	 * Test sui movimenti legali del Re bianco e
	 * suoi alleati, con loro ricalcolo in base al fatto che
	 * il re puo' finire sotto attacco.
	 */
	@Test
	public void testPinnedKingMovements() {
		Spot kingSpot = board.getSpot(ROW_1, COL_F);
		kingSpot.setPiece(new King(WHITE));
		Piece kingPiece = kingSpot.getPiece();
		kingPiece.findLegalMoves(board, kingSpot);
	
		Spot rookSpotW = board.getSpot(ROW_1, COL_E);
		rookSpotW.setPiece(new Rook(WHITE));
		Piece rookPieceW = rookSpotW.getPiece();
		rookPieceW.findLegalMoves(board, rookSpotW);

		
		Spot rookSpotW2 = board.getSpot(ROW_2, COL_E);
		rookSpotW2.setPiece(new Rook(WHITE));
		Piece rookPieceW2 = rookSpotW2.getPiece();
		rookPieceW2.findLegalMoves(board, rookSpotW2);
		
		Spot rookSpotB = board.getSpot(ROW_1, COL_A);
		rookSpotB.setPiece(new Rook(BLACK));
		Piece rookPieceB = rookSpotB.getPiece();
		rookPieceB.findLegalMoves(board, rookSpotB);
		
		Spot rookSpotB2 = board.getSpot(ROW_2, COL_G);
		rookSpotB2.setPiece(new Rook(BLACK));
		Piece rookPieceB2 = rookSpotB2.getPiece();
		rookPieceB2.findLegalMoves(board, rookSpotB2);
		
		Spot bishopSpot = board.getSpot(ROW_6, COL_C);
		bishopSpot.setPiece(new Bishop(BLACK));
		Piece bishopPiece = bishopSpot.getPiece();
		bishopPiece.findLegalMoves(board, bishopSpot);
		
		Spot queenSpot = board.getSpot(ROW_6, COL_A);
		queenSpot.setPiece(new Queen(BLACK));
		Piece queenPiece = queenSpot.getPiece();
		queenPiece.findLegalMoves(board, queenSpot);

		kingPiece.recalculateMoves(board);
		rookPieceW2.recalculateMoves(board);
		rookPieceW.recalculateMoves(board);

		
		// nessuna mossa possibile per il re
		assertEquals(kingPiece.getLegalMoves().size(), 0);
		
		//la torre in e2 non si può muovere
		assertEquals(rookPieceW2.getLegalMoves().size(), 0);

		//la torre in e1 si puo' muovere su tutta la prima riga a sinistra
		assertEquals(rookPieceW.getLegalMoves().size(), 4);
		
		System.out.println(queenPiece.getLegalMoves().size());
	
					
	}
}
