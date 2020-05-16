package it.uniba.example;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

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

	@Test
	public void testPawnLegalMovements() {
		/*
		 * io farei in questo modo: - scacchiera vuota - genero un pezzo - calcolo le
		 * sue mosse legali - confronto la size dell'arraylist (deve essere pari al
		 * numero di mosse legali) - confronto le mosse dell'arraylist con quelle legali
		 * che so a priori
		 */

		Board board = new Board(true);
		
		Spot blackPawnSpot = board.getSpot(ROW_7, COL_B);
		blackPawnSpot.setPiece(new Pawn(BLACK));
		Piece blackPawn = blackPawnSpot.getPiece();
		blackPawnSpot.setPiece(new Pawn(BLACK));
		blackPawn.findLegalMoves(board, blackPawnSpot);
		// test sulla lunghezza dell'arraylist di mosse (in questo caso 2 -> b6, b5)
		assertEquals(blackPawn.getLegalMoves().size(), 2);
		// test sulle mosse presenti all'interno dell'arrayList di mosse
		assertTrue(blackPawn.getLegalMoves().contains(new Move(blackPawnSpot, new Spot(ROW_6, COL_B))));
		assertTrue(blackPawn.getLegalMoves().contains(new Move(blackPawnSpot, new Spot(ROW_5, COL_B))));
		
		Spot whitePawnSpot = board.getSpot(ROW_2, COL_C);
		whitePawnSpot.setPiece(new Pawn(WHITE));
		Piece whitePawn = whitePawnSpot.getPiece();
		whitePawnSpot.setPiece(new Pawn(WHITE));
		whitePawn.findLegalMoves(board, whitePawnSpot);
		
		board.showBoard();

		// test sulla lunghezza dell'arraylist di mosse (in questo caso 2 -> c3, c4)
		assertEquals(whitePawn.getLegalMoves().size(), 2);
		// test sulle mosse presenti all'interno dell'arrayList di mosse
		assertTrue(whitePawn.getLegalMoves().contains(new Move(whitePawnSpot, new Spot(ROW_3, COL_C))));
		assertTrue(whitePawn.getLegalMoves().contains(new Move(whitePawnSpot, new Spot(ROW_4, COL_C))));
		//board.showBoard();
	}
	
	

	@Test
	public void testKingLegalMovements() {
		// MOVIMENTI DEL RE
		Board board = new Board(true);
		Spot currentSpot = board.getSpot(ROW_7, COL_B);
		currentSpot.setPiece(new King(WHITE));
		Piece currentPiece = currentSpot.getPiece();
		currentSpot.setPiece(new King(WHITE));
		currentPiece.findLegalMoves(board, currentSpot);
		// test sulla lunghezza dell'arraylist di mosse (in questo caso 8 -> b6, b5)
		//board.showBoard();
		assertEquals(currentPiece.getLegalMoves().size(), 8);
		// test sulle mosse presenti all'interno dell'arrayList di mosse
		assertTrue(currentPiece.getLegalMoves().contains(new Move(currentSpot, new Spot(ROW_6, COL_B))));
		assertTrue(currentPiece.getLegalMoves().contains(new Move(currentSpot, new Spot(ROW_8, COL_B))));
		assertTrue(currentPiece.getLegalMoves().contains(new Move(currentSpot, new Spot(ROW_6, COL_A))));
		assertTrue(currentPiece.getLegalMoves().contains(new Move(currentSpot, new Spot(ROW_7, COL_A))));
		assertTrue(currentPiece.getLegalMoves().contains(new Move(currentSpot, new Spot(ROW_8, COL_A))));
		assertTrue(currentPiece.getLegalMoves().contains(new Move(currentSpot, new Spot(ROW_6, COL_C))));
		assertTrue(currentPiece.getLegalMoves().contains(new Move(currentSpot, new Spot(ROW_7, COL_C))));
		assertTrue(currentPiece.getLegalMoves().contains(new Move(currentSpot, new Spot(ROW_8, COL_C))));

	}

	@Test
	public void testKingRecalculateMovements() {
		Board board = new Board(true);
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

		// test sulla lunghezza dell'arraylist di mosse (in questo caso 8 -> b6, b5)
		//board.showBoard();
		kingPiece.recalculateMoves(board);
		assertEquals(kingPiece.getLegalMoves().size(), 5);
	}

}
