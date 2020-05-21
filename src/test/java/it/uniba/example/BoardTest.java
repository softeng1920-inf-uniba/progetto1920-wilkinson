package it.uniba.example;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import it.uniba.main.Bishop;
import it.uniba.main.Board;
import it.uniba.main.King;
import it.uniba.main.Knight;
import it.uniba.main.Move;
import it.uniba.main.Pawn;
import it.uniba.main.Piece;
import it.uniba.main.Queen;
import it.uniba.main.Rook;
import it.uniba.main.Spot;

public class BoardTest {
	private static Board board;
	private static Piece examinedPiece;
	private static boolean isRefine;
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
		// inizializzazione scacchiera vuota
		board = new Board(true);
	}

	@AfterEach
	void tearDown() {
		// numero di mosse calcolate
		int moveNumber = examinedPiece.getLegalMoves().size();
		// ricalcolo mosse
		board.recalLegalMoves();
		// refine mosse relativo alla posizione del re (solo se test di refine)
		if (isRefine) {
			board.refineLegalMoves();
		}
		// controllo se il numero di mosse non e' cambiato dopo il ricalcolo
		assertEquals(examinedPiece.getLegalMoves().size(), moveNumber);
	}

	@Test
	void testWhitePawnLegalMoves() {
		board.getSpot(ROW_2, COL_D).setPiece(new Pawn(WHITE)); // pedone esaminato (d2)
		examinedPiece = board.getSpot(ROW_2, COL_D).getPiece();
		board.recalLegalMoves();
		assertAll(
				// mosse del pedone bianco possibili: [2]
				() -> assertEquals(examinedPiece.getLegalMoves().size(), 2),
				() -> assertTrue(examinedPiece.getLegalMoves().contains(
						new Move(new Spot(ROW_2, COL_D), new Spot(ROW_3, COL_D)))), // 1.(d2->d3)
				() -> assertTrue(examinedPiece.getLegalMoves().contains(
						new Move(new Spot(ROW_2, COL_D), new Spot(ROW_4, COL_D))))  // 2.(d2->d4)
				);
		isRefine = false;
	}

}
