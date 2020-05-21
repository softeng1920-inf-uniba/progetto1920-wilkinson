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
				() -> assertTrue(examinedPiece.getLegalMoves()
						.contains(new Move(new Spot(ROW_2, COL_D), new Spot(ROW_3, COL_D)))), // 1.(d2->d3)
				() -> assertTrue(examinedPiece.getLegalMoves()
						.contains(new Move(new Spot(ROW_2, COL_D), new Spot(ROW_4, COL_D)))) // 2.(d2->d4)
		);
		isRefine = false;
	}

	@Test
	void testWhitePawnAlreadyMovedLegalMoves() {
		board.getSpot(ROW_3, COL_D).setPiece(new Pawn(WHITE)); // pedone esaminato (d3)
		examinedPiece = board.getSpot(ROW_3, COL_D).getPiece();
		board.getSpot(ROW_3, COL_D).getPiece().setAsMoved(); // pedone in (d3) gia' mosso
		board.recalLegalMoves();
		assertAll(
				// mosse del pedone bianco possibili: [1]
				() -> assertEquals(examinedPiece.getLegalMoves().size(), 1), () -> assertTrue(examinedPiece
						.getLegalMoves().contains(new Move(new Spot(ROW_3, COL_D), new Spot(ROW_4, COL_D)))) // 1.(d3->d4)
		);
		isRefine = false;
	}

	@Test
	void testWhitePawnCaptureLegalMoves() {
		board.getSpot(ROW_2, COL_D).setPiece(new Pawn(WHITE)); // pedone esaminato (d2)
		examinedPiece = board.getSpot(ROW_2, COL_D).getPiece();
		board.getSpot(ROW_3, COL_E).setPiece(new Pawn(BLACK)); // pedone nemico (e3)
		board.getSpot(ROW_3, COL_E).getPiece().setAsMoved(); // pedone in (e3) gia' mosso
		board.recalLegalMoves();
		assertAll(
				// mosse del pedone bianco possibili: [3]
				() -> assertEquals(examinedPiece.getLegalMoves().size(), 3),
				() -> assertTrue(examinedPiece.getLegalMoves()
						.contains(new Move(new Spot(ROW_2, COL_D), new Spot(ROW_3, COL_D)))), // 1.(d2->d3)
				() -> assertTrue(examinedPiece.getLegalMoves()
						.contains(new Move(new Spot(ROW_2, COL_D), new Spot(ROW_4, COL_D)))), // 2.(d2->d4)
				// cattura, movimento possibile
				() -> assertTrue(examinedPiece.getLegalMoves()
						.contains(new Move(new Spot(ROW_2, COL_D), new Spot(ROW_3, COL_E)))) // 3.(d2->e3)
		);
		isRefine = false;
	}

	@Test
	void testWhitePawnEnPassantLegalMoves() {
		board.getSpot(ROW_5, COL_D).setPiece(new Pawn(WHITE)); // pedone esaminato (d5)
		examinedPiece = board.getSpot(ROW_5, COL_D).getPiece();
		board.getSpot(ROW_5, COL_E).setPiece(new Pawn(BLACK)); // pedone nemico (e5)
		board.getSpot(ROW_5, COL_D).getPiece().setAsMoved(); // pedone in (d5) gia' mosso
		board.getSpot(ROW_5, COL_E).getPiece().setAsMoved(); // pedone in (e5) gia' mosso
		// pedone in (e5) catturabile en passant
		((Pawn) (board.getSpot(ROW_5, COL_E).getPiece())).setPossibleEnPassantCapture(true);
		board.recalLegalMoves();
		assertAll(
				// mosse del pedone bianco possibili: [2]
				() -> assertEquals(examinedPiece.getLegalMoves().size(), 2),
				() -> assertTrue(examinedPiece.getLegalMoves()
						.contains(new Move(new Spot(ROW_5, COL_D), new Spot(ROW_6, COL_D)))), // 1.(d5->d6)
				// cattura en passant, movimento possibile
				() -> assertTrue(examinedPiece.getLegalMoves()
						.contains(new Move(new Spot(ROW_5, COL_D), new Spot(ROW_6, COL_E)))) // 2.(d5->e6)
		);
		isRefine = false;
	}

	@Test
	void testWhitePawnEnPassantNotPossibleLegalMoves() {
		board.getSpot(ROW_5, COL_D).setPiece(new Pawn(WHITE)); // pedone esaminato (d5)
		examinedPiece = board.getSpot(ROW_5, COL_D).getPiece();
		board.getSpot(ROW_5, COL_E).setPiece(new Pawn(BLACK)); // pedone nemico (e5)
		board.getSpot(ROW_5, COL_D).getPiece().setAsMoved(); // pedone in (d5) gia' mosso
		board.getSpot(ROW_5, COL_E).getPiece().setAsMoved(); // pedone in (e5) gia' mosso
		// pedone in (e5) NON PIU' catturabile en passant (es. mosso turno precedente)
		((Pawn) (board.getSpot(ROW_5, COL_E).getPiece())).setPossibleEnPassantCapture(false);
		board.recalLegalMoves();
		assertAll(
				// mosse del pedone bianco possibili: [1]
				() -> assertEquals(examinedPiece.getLegalMoves().size(), 1),
				() -> assertTrue(examinedPiece.getLegalMoves()
						.contains(new Move(new Spot(ROW_5, COL_D), new Spot(ROW_6, COL_D)))), // 1.(d5->d6)
				// cattura en passant, movimento non possibile
				() -> assertFalse(examinedPiece.getLegalMoves()
						.contains(new Move(new Spot(ROW_5, COL_D), new Spot(ROW_6, COL_E)))) // (d5->e6) NO!
		);
		isRefine = false;
	}

	@Test
	void testWhitePawnEndBoardLegalMoves() {
		board.getSpot(ROW_8, COL_D).setPiece(new Pawn(WHITE)); // pedone esaminato (d8) -> BORDO SCACCHIERA
		examinedPiece = board.getSpot(ROW_8, COL_D).getPiece();
		board.recalLegalMoves();
		// mosse del pedone bianco possibili: [0]
		assertTrue(examinedPiece.getLegalMoves().isEmpty());
		isRefine = false;
	}

	@Test
	void testWhitePawnFrontPieceLegalMoves() {
		board.getSpot(ROW_2, COL_D).setPiece(new Pawn(WHITE)); // pedone esaminato (d2)
		examinedPiece = board.getSpot(ROW_2, COL_D).getPiece();
		board.getSpot(ROW_3, COL_D).setPiece(new Pawn(BLACK)); // pedone nemico (d3) -> DI FRONTE
		board.recalLegalMoves();
		// mosse del pedone bianco possibili: [0]
		assertTrue(examinedPiece.getLegalMoves().isEmpty());
		isRefine = false;
	}

	@Test
	void testBlackPawnLegalMoves() {
		board.getSpot(ROW_7, COL_D).setPiece(new Pawn(BLACK)); // pedone esaminato (d6)
		examinedPiece = board.getSpot(ROW_7, COL_D).getPiece();
		board.recalLegalMoves();
		assertAll(
				// mosse del pedone nero possibili: [2]
				() -> assertEquals(examinedPiece.getLegalMoves().size(), 2),
				() -> assertTrue(examinedPiece.getLegalMoves()
						.contains(new Move(new Spot(ROW_7, COL_D), new Spot(ROW_6, COL_D)))), // 1.(d7->d6)
				() -> assertTrue(examinedPiece.getLegalMoves()
						.contains(new Move(new Spot(ROW_7, COL_D), new Spot(ROW_5, COL_D)))) // 2.(d7->d5)
		);
		isRefine = false;
	}

	@Test
	void testBlackPawnAlreadyMovedLegalMoves() {
		board.getSpot(ROW_6, COL_D).setPiece(new Pawn(BLACK)); // pedone esaminato (d6)
		examinedPiece = board.getSpot(ROW_6, COL_D).getPiece();
		board.getSpot(ROW_6, COL_D).getPiece().setAsMoved(); // pedone in (d6) gia' mosso
		board.recalLegalMoves();
		assertAll(
				// mosse del pedone nero possibili: [1]
				() -> assertEquals(examinedPiece.getLegalMoves().size(), 1), () -> assertTrue(examinedPiece
						.getLegalMoves().contains(new Move(new Spot(ROW_6, COL_D), new Spot(ROW_5, COL_D)))) // 1.(d6->d5)
		);
		isRefine = false;
	}

	@Test
	void testBlackPawnCaptureLegalMoves() {
		board.getSpot(ROW_7, COL_D).setPiece(new Pawn(BLACK)); // pedone esaminato (d7)
		examinedPiece = board.getSpot(ROW_7, COL_D).getPiece();
		board.getSpot(ROW_6, COL_E).setPiece(new Pawn(WHITE)); // pedone nemico (e6)
		board.getSpot(ROW_6, COL_E).getPiece().setAsMoved(); // pedone in (e6) gia' mosso
		board.recalLegalMoves();
		assertAll(
				// mosse del pedone nero possibili: [3]
				() -> assertEquals(examinedPiece.getLegalMoves().size(), 3),
				() -> assertTrue(examinedPiece.getLegalMoves()
						.contains(new Move(new Spot(ROW_7, COL_D), new Spot(ROW_6, COL_D)))), // 1.(d7->d6)
				() -> assertTrue(examinedPiece.getLegalMoves()
						.contains(new Move(new Spot(ROW_7, COL_D), new Spot(ROW_5, COL_D)))), // 2.(d7->d5)
				// cattura, movimento possibile
				() -> assertTrue(examinedPiece.getLegalMoves()
						.contains(new Move(new Spot(ROW_7, COL_D), new Spot(ROW_6, COL_E)))) // 3.(d7->e6)
		);
		isRefine = false;
	}
	
	@Test
	void testBlackPawnEnPassantLegalMoves() {
		board.getSpot(ROW_4, COL_D).setPiece(new Pawn(BLACK)); // pedone esaminato (d4)
		examinedPiece = board.getSpot(ROW_4, COL_D).getPiece();
		board.getSpot(ROW_4, COL_E).setPiece(new Pawn(WHITE)); // pedone nemico    (e4)
		board.getSpot(ROW_4, COL_D).getPiece().setAsMoved(); // pedone in (d4) gia' mosso
		board.getSpot(ROW_4, COL_E).getPiece().setAsMoved(); // pedone in (e4) gia' mosso
		// pedone in (e4) catturabile en passant
		((Pawn) (board.getSpot(ROW_4, COL_E).getPiece())).setPossibleEnPassantCapture(true);
		board.recalLegalMoves();
		assertAll(
				// mosse del pedone nero possibili: [2]
				() -> assertEquals(examinedPiece.getLegalMoves().size(), 2),
				() -> assertTrue(examinedPiece.getLegalMoves().contains(
						new Move(new Spot(ROW_4, COL_D), new Spot(ROW_3, COL_D)))), // 1.(d4->d3)
				// cattura en passant, movimento possibile
				() -> assertTrue(examinedPiece.getLegalMoves().contains(
						new Move(new Spot(ROW_4, COL_D), new Spot(ROW_3, COL_E))))  // 2.(d4->e3)
				);
		isRefine = false;
	}
	
	@Test
	void testBlackPawnEnPassantNotPossibleLegalMoves() {
		board.getSpot(ROW_4, COL_D).setPiece(new Pawn(BLACK)); // pedone esaminato (d4)
		board.getSpot(ROW_4, COL_E).setPiece(new Pawn(WHITE)); // pedone nemico    (e4)
		board.getSpot(ROW_4, COL_D).getPiece().setAsMoved(); // pedone in (d4) gia' mosso
		board.getSpot(ROW_4, COL_E).getPiece().setAsMoved(); // pedone in (e4) gia' mosso
		// pedone in (e4) NON PIU' catturabile en passant (es. mosso turno precedente)
		((Pawn) (board.getSpot(ROW_4, COL_E).getPiece())).setPossibleEnPassantCapture(false);
		board.recalLegalMoves();
		assertAll(
				// mosse del pedone nero possibili: [1]
				() -> assertEquals(board.getSpot(ROW_4, COL_D).getPiece().getLegalMoves().size(), 1),
				() -> assertTrue(board.getSpot(ROW_4, COL_D).getPiece().getLegalMoves().contains(
						new Move(new Spot(ROW_4, COL_D), new Spot(ROW_3, COL_D)))), // 1.(d4->d3)
				// cattura en passant, movimento non possibile
				() -> assertFalse(board.getSpot(ROW_4, COL_D).getPiece().getLegalMoves().contains(
						new Move(new Spot(ROW_4, COL_D), new Spot(ROW_3, COL_E))))  // (d4->e3) NO!
				);
		isRefine = false;
	}
	
	@Test
	void testBlackPawnEndBoardLegalMoves() {
		board.getSpot(ROW_1, COL_D).setPiece(new Pawn(BLACK)); // pedone esaminato (d1) -> BORDO SCACCHIERA
		examinedPiece = board.getSpot(ROW_1, COL_D).getPiece();
		board.recalLegalMoves();
		// mosse del pedone nero possibili: [0]
		assertTrue(examinedPiece.getLegalMoves().isEmpty());
		isRefine = false;
	}
	
	@Test
	void testBlackPawnFrontPieceLegalMoves() {
		board.getSpot(ROW_7, COL_D).setPiece(new Pawn(BLACK)); // pedone esaminato (d7)
		examinedPiece = board.getSpot(ROW_7, COL_D).getPiece();
		board.getSpot(ROW_6, COL_D).setPiece(new Pawn(WHITE)); // pedone nemico    (d6) -> DI FRONTE
		board.recalLegalMoves();
		// mosse del pedone nero possibili: [0]
		assertTrue(examinedPiece.getLegalMoves().isEmpty());
		isRefine = false;
	}

}
