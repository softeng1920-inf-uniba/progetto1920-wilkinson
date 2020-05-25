package it.uniba.test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;
import static org.junit.jupiter.api.Assertions.assertAll;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import it.uniba.logic.Bishop;
import it.uniba.logic.Board;
import it.uniba.logic.King;
import it.uniba.logic.Knight;
import it.uniba.logic.Move;
import it.uniba.logic.Pawn;
import it.uniba.logic.Piece;
import it.uniba.logic.Queen;
import it.uniba.logic.Rook;
import it.uniba.logic.Spot;

class MoveTest {
	private Move moveInterpreted;
	private Move moveExpected;
	private Board board;
	private Piece examinedPiece;
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

	/**
	 * Pre-condizioni:
	 * 		- inizializzo una scacchiera con i soli re
	 */
	@BeforeEach
	void setup() {
		board = new Board(true);
		board.getSpot(ROW_1, COL_E).setPiece(new King(WHITE));
		board.getSpot(ROW_8, COL_E).setPiece(new King(BLACK));
	}

	/*
	 * Ricalcoli pre-testing e interpretazione della mossa
	 */
	void interpret(String command, boolean color) {
		board.recalLegalMoves();
		board.refineLegalMoves();
		moveInterpreted = new Move(command, board, color);
	}

	/**********************************************************************************/

	/*
	 * TEST DI INTERPRETAZIONE DEI COMANDI VALIDI
	 */

	@Test
	/**
	 * Testa un comando per muovere un pedone bianco
	 */
	void testCommandPawnWhite() {
		board.getSpot(ROW_2, COL_A).setPiece(new Pawn(WHITE));
		examinedPiece = board.getSpot(ROW_2, COL_A).getPiece();

		interpret("a3", WHITE);

		moveExpected = new Move(new Spot(ROW_2, COL_A), new Spot(ROW_3, COL_A));
		assertAll(
			() -> assertEquals(moveInterpreted, moveExpected),
			() -> assertTrue(moveInterpreted.getPieceMoved() instanceof Pawn),
			() -> assertEquals(moveInterpreted.getPieceMoved().isWhite(), WHITE)
			);
	}

	@Test
	/**
	 * Testa un comando per muovere un pedone nero
	 */
	void testCommandPawnBlack() {
		board.getSpot(ROW_7, COL_A).setPiece(new Pawn(BLACK));
		examinedPiece = board.getSpot(ROW_7, COL_A).getPiece();

		interpret("a6", BLACK);

		moveExpected = new Move(new Spot(ROW_7, COL_A), new Spot(ROW_6, COL_A));
		assertAll(
				() -> assertEquals(moveInterpreted, moveExpected),
				() -> assertTrue(moveInterpreted.getPieceMoved() instanceof Pawn),
				() -> assertEquals(moveInterpreted.getPieceMoved().isWhite(), BLACK)
				);
	}

	@Test
	/**
	 * Testa un comando per muovere un pedone bianco con cattura e.p.
	 */
	void testCommandPawnWhiteEP() {
		board.getSpot(ROW_5, COL_C).setPiece(new Pawn(WHITE));
		examinedPiece = board.getSpot(ROW_5, COL_C).getPiece();

		board.getSpot(ROW_5, COL_D).setPiece(new Pawn(BLACK));
		((Pawn) board.getSpot(ROW_5, COL_D).getPiece()).setPossibleEnPassantCapture(true);

		interpret("cxd6", WHITE);

		moveExpected = new Move(new Spot(ROW_5, COL_C), new Spot(ROW_6, COL_D));
		assertAll(
				() -> assertEquals(moveInterpreted, moveExpected),
				() -> assertTrue(moveInterpreted.getPieceMoved() instanceof Pawn),
				() -> assertEquals(moveInterpreted.getPieceMoved().isWhite(), WHITE),
				() -> assertTrue(((Pawn) examinedPiece).isCapturingEnPassant())
				);
	}

	@Test
	/**
	 * Testa un comando per muovere un pedone nero con cattura e.p.
	 */
	void testCommandPawnBlackEP() {
		board.getSpot(ROW_4, COL_C).setPiece(new Pawn(BLACK));
		examinedPiece = board.getSpot(ROW_4, COL_C).getPiece();

		board.getSpot(ROW_4, COL_D).setPiece(new Pawn(WHITE));
		((Pawn) board.getSpot(ROW_4, COL_D).getPiece()).setPossibleEnPassantCapture(true);

		interpret("cxd3", BLACK);

		moveExpected = new Move(new Spot(ROW_4, COL_C), new Spot(ROW_3, COL_D));
		assertAll(
				() -> assertEquals(moveInterpreted, moveExpected),
				() -> assertTrue(moveInterpreted.getPieceMoved() instanceof Pawn),
				() -> assertEquals(moveInterpreted.getPieceMoved().isWhite(), BLACK),
				() -> assertTrue(((Pawn) examinedPiece).isCapturingEnPassant())
				);
	}

	@Test
	/**
	 * Testa un comando per muovere una torre bianca
	 */
	void testCommandRookWhite() {
		board.getSpot(ROW_1, COL_A).setPiece(new Rook(WHITE));
		examinedPiece = board.getSpot(ROW_1, COL_A).getPiece();

		interpret("Ta4", WHITE);

		moveExpected = new Move(new Spot(ROW_1, COL_A), new Spot(ROW_4, COL_A));
		assertAll(
				() -> assertEquals(moveInterpreted, moveExpected),
				() -> assertTrue(moveInterpreted.getPieceMoved() instanceof Rook),
				() -> assertEquals(moveInterpreted.getPieceMoved().isWhite(), WHITE)
				);
	}

	@Test
	/**
	 * Testa un comando per muovere una torre nera
	 */
	void testCommandRookBlack() {
		board.getSpot(ROW_8, COL_A).setPiece(new Rook(BLACK));
		examinedPiece = board.getSpot(ROW_8, COL_A).getPiece();

		interpret("Ta4", BLACK);

		moveExpected = new Move(new Spot(ROW_8, COL_A), new Spot(ROW_4, COL_A));
		assertAll(
				() -> assertEquals(moveInterpreted, moveExpected),
				() -> assertTrue(moveInterpreted.getPieceMoved() instanceof Rook),
				() -> assertEquals(moveInterpreted.getPieceMoved().isWhite(), BLACK)
				);
	}

	@Test
	/**
	 * Testa un comando per muovere un cavallo bianco
	 */
	void testCommandKnightWhite() {
		board.getSpot(ROW_1, COL_B).setPiece(new Knight(WHITE));
		examinedPiece = board.getSpot(ROW_1, COL_A).getPiece();

		interpret("Cc3", WHITE);

		moveExpected = new Move(new Spot(ROW_1, COL_B), new Spot(ROW_3, COL_C));
		assertAll(
				() -> assertEquals(moveInterpreted, moveExpected),
				() -> assertTrue(moveInterpreted.getPieceMoved() instanceof Knight),
				() -> assertEquals(moveInterpreted.getPieceMoved().isWhite(), WHITE)
				);
	}

	@Test
	/**
	 * Testa un comando per muovere un cavallo nero
	 */
	void testCommandKnightBlack() {
		board.getSpot(ROW_8, COL_B).setPiece(new Knight(BLACK));
		examinedPiece = board.getSpot(ROW_8, COL_B).getPiece();

		interpret("Cc6", BLACK);

		moveExpected = new Move(new Spot(ROW_8, COL_B), new Spot(ROW_6, COL_C));
		assertAll(
				() -> assertEquals(moveInterpreted, moveExpected),
				() -> assertTrue(moveInterpreted.getPieceMoved() instanceof Knight),
				() -> assertEquals(moveInterpreted.getPieceMoved().isWhite(), BLACK)
				);
	}

	@Test
	/**
	 * Testa un comando per muovere un alfiere bianco
	 */
	void testCommandBishopWhite() {
		board.getSpot(ROW_1, COL_C).setPiece(new Bishop(WHITE));
		examinedPiece = board.getSpot(ROW_1, COL_C).getPiece();

		interpret("Ah6", WHITE);

		moveExpected = new Move(new Spot(ROW_1, COL_C), new Spot(ROW_6, COL_H));
		assertAll(
				() -> assertEquals(moveInterpreted, moveExpected),
				() -> assertTrue(moveInterpreted.getPieceMoved() instanceof Bishop),
				() -> assertEquals(moveInterpreted.getPieceMoved().isWhite(), WHITE)
				);
	}

	@Test
	/**
	 * Testa un comando per muovere un alfiere nero
	 */
	void testCommandBishopBlack() {
		board.getSpot(ROW_8, COL_C).setPiece(new Bishop(BLACK));
		examinedPiece = board.getSpot(ROW_8, COL_C).getPiece();

		interpret("Ah3", BLACK);

		moveExpected = new Move(new Spot(ROW_8, COL_C), new Spot(ROW_3, COL_H));
		assertAll(
				() -> assertEquals(moveInterpreted, moveExpected),
				() -> assertTrue(moveInterpreted.getPieceMoved() instanceof Bishop),
				() -> assertEquals(moveInterpreted.getPieceMoved().isWhite(), BLACK)
				);
	}

	@Test
	/**
	 * Testa un comando per muovere la regina bianca
	 */
	void testCommandQueenWhite() {
		board.getSpot(ROW_1, COL_D).setPiece(new Queen(WHITE));
		examinedPiece = board.getSpot(ROW_1, COL_D).getPiece();

		interpret("Dd6", WHITE);

		moveExpected = new Move(new Spot(ROW_1, COL_D), new Spot(ROW_6, COL_D));
		assertAll(
				() -> assertEquals(moveInterpreted, moveExpected),
				() -> assertTrue(moveInterpreted.getPieceMoved() instanceof Queen),
				() -> assertEquals(moveInterpreted.getPieceMoved().isWhite(), WHITE)
				);
	}

	@Test
	/**
	 * Testa un comando per muovere la regina nera
	 */
	void testCommandQueenBlack() {
		board.getSpot(ROW_8, COL_D).setPiece(new Queen(BLACK));
		examinedPiece = board.getSpot(ROW_8, COL_D).getPiece();

		interpret("Dd3", BLACK);

		moveExpected = new Move(new Spot(ROW_8, COL_D), new Spot(ROW_3, COL_D));
		assertAll(
				() -> assertEquals(moveInterpreted, moveExpected),
				() -> assertTrue(moveInterpreted.getPieceMoved() instanceof Queen),
				() -> assertEquals(moveInterpreted.getPieceMoved().isWhite(), BLACK)
				);
	}

	@Test
	/**
	 * Testa un comando per muovere il re bianco
	 */
	void testCommandKingWhite() {
		examinedPiece = board.getSpot(ROW_1, COL_E).getPiece();

		interpret("Rd2", WHITE);

		moveExpected = new Move(new Spot(ROW_1, COL_E), new Spot(ROW_2, COL_D));
		assertAll(
				() -> assertEquals(moveInterpreted, moveExpected),
				() -> assertTrue(moveInterpreted.getPieceMoved() instanceof King),
				() -> assertEquals(moveInterpreted.getPieceMoved().isWhite(), WHITE)
				);
	}

	@Test
	/**
	 * Testa un comando per muovere il re nero
	 */
	void testCommandKingBlack() {
		examinedPiece = board.getSpot(ROW_8, COL_E).getPiece();

		interpret("Rf7", BLACK);

		moveExpected = new Move(new Spot(ROW_8, COL_E), new Spot(ROW_7, COL_F));
		assertAll(
				() -> assertEquals(moveInterpreted, moveExpected),
				() -> assertTrue(moveInterpreted.getPieceMoved() instanceof King),
				() -> assertEquals(moveInterpreted.getPieceMoved().isWhite(), BLACK)
				);
	}

	/**********************************************************************************/

	/*
	 * TEST DI ARROCCO
	 */

	//test arrocco corto di pezzi bianchi
	@Test
	void testWhiteCastleShort() {
		board.getSpot(ROW_1, COL_H).setPiece(new Rook(WHITE));

		interpret("0-0", WHITE);

		assertTrue(moveInterpreted.makeCastling(board, WHITE));
	}

	//test arrocco lungo di pezzi bianchi
	@Test
	void testWhiteCastleLong() {
		board.getSpot(ROW_1, COL_A).setPiece(new Rook(WHITE));

		interpret("0-0-0", WHITE);

		assertTrue(moveInterpreted.makeCastling(board, WHITE));
	}

	//test arrocco corto di pezzi neri
	@Test
	void testBlackCastleShort() {
		board.getSpot(ROW_8, COL_H).setPiece(new Rook(BLACK));

		interpret("0-0", BLACK);

		assertTrue(moveInterpreted.makeCastling(board, BLACK));
	}

	//test arrocco lungo di pezzi neri
	@Test
	void testCastleLongBlack() {
		board.getSpot(ROW_8, COL_A).setPiece(new Rook(BLACK));

		interpret("0-0-0", BLACK);

		assertTrue(moveInterpreted.makeCastling(board, BLACK));
	}

	//test arrocco con un comando errato
	@Test
	void testIncorrectCastleCmd() {
		board.getSpot(ROW_1, COL_H).setPiece(new Rook(WHITE));

		interpret("o-o", WHITE);

		assertFalse(moveInterpreted.makeCastling(board, WHITE));
	}

	//test arrocco con il re gia' mosso in precedenza
	@Test
	void testCastleKingMoved() {
		board.getSpot(ROW_1, COL_E).getPiece().setAsMoved();
		board.getSpot(ROW_1, COL_H).setPiece(new Rook(WHITE));

		interpret("0-0", WHITE);

		assertFalse(moveInterpreted.makeCastling(board, WHITE));
	}

	//test arrocco con torre gia' mossa in precedenza
	@Test
	void testCastleRookMoved() {
		board.getSpot(ROW_1, COL_H).setPiece(new Rook(WHITE));
		board.getSpot(ROW_1, COL_H).getPiece().setAsMoved();

		interpret("0-0", WHITE);

		assertFalse(moveInterpreted.makeCastling(board, WHITE));
	}

	//test arrocco con re minacciato
	@Test
	void testCastleKingUnderAttack() {
		board.getSpot(ROW_1, COL_H).setPiece(new Rook(WHITE));
		board.getSpot(ROW_4, COL_E).setPiece(new Queen(BLACK));

		interpret("0-0", WHITE);

		assertFalse(moveInterpreted.makeCastling(board, WHITE));
	}

	//test arrocco con torre minacciata
	@Test
	void testCastleRookUnderAttack() {
		board.getSpot(ROW_1, COL_H).setPiece(new Rook(WHITE));
		board.getSpot(ROW_4, COL_E).setPiece(new Bishop(BLACK));

		interpret("0-0", WHITE);

		assertTrue(moveInterpreted.makeCastling(board, WHITE));
	}

	//test arrocco con posizione di arrivo del re minacciata
	@Test
	void testCastleKingEndUnderAttack() {
		board.getSpot(ROW_1, COL_H).setPiece(new Rook (WHITE));
		board.getSpot(ROW_4, COL_G).setPiece(new Queen(BLACK));

		interpret("0-0", WHITE);

		assertFalse(moveInterpreted.makeCastling(board, WHITE));
	}

	//test arrocco con posizione di arrivo della torre minacciata
	@Test
	void testCastleRookEndUnderAttack() {
		board.getSpot(ROW_1, COL_H).setPiece(new Rook (WHITE));
		board.getSpot(ROW_4, COL_F).setPiece(new Queen(BLACK));

		interpret("0-0", WHITE);

		assertFalse(moveInterpreted.makeCastling(board, WHITE));
	}

	//test arrocco lungo con casella b1 minacciata
	@Test
	void testCastleLongSpotUnderAttack() {
		board.getSpot(ROW_1, COL_A).setPiece(new Rook (WHITE));
		board.getSpot(ROW_5, COL_B).setPiece(new Queen(BLACK));

		interpret("0-0-0", WHITE);

		assertTrue(moveInterpreted.makeCastling(board, WHITE));
	}

	//test arrocco con pezzo nemico frapposto tra torre e re
	@Test
	void testCastleEnemyPieceBetween() {
		board.getSpot(ROW_1, COL_H).setPiece(new Rook (WHITE));
		board.getSpot(ROW_1, COL_F).setPiece(new Bishop(BLACK));

		interpret("0-0", WHITE);

		assertFalse(moveInterpreted.makeCastling(board, WHITE));
	}

	//test arrocco con pezzo amico frapposto tra torre e re
	@Test
	void testCastleFriendPieceBetween() {
		board.getSpot(ROW_1, COL_H).setPiece(new Rook (WHITE));
		board.getSpot(ROW_1, COL_F).setPiece(new Queen(WHITE));

		interpret("0-0", WHITE);

		assertFalse(moveInterpreted.makeCastling(board, WHITE));
	}

	//test arrocco con pezzo diverso da torre o re
	@Test
	void testCastleIncorrectPiece() {
		board.getSpot(ROW_1, COL_H).setPiece(new Queen(WHITE));

		interpret("0-0", WHITE);

		assertFalse(moveInterpreted.makeCastling(board, WHITE));
	}

	/**********************************************************************************/

	/*
	 * TEST DI INTERPRETAZIONE DELLE AMBIGUITA'
	 */

	@Test
	void testLetterAmbiguity() {
		board.getSpot(ROW_3, COL_C).setPiece(new Knight(WHITE));
		board.getSpot(ROW_4, COL_F).setPiece(new Knight(WHITE));

		interpret("Ccd5", WHITE);

		moveExpected = new Move(new Spot(ROW_3, COL_C), new Spot(ROW_5, COL_D));
		assertAll(
				() -> assertEquals(moveInterpreted, moveExpected),
				() -> assertTrue(moveInterpreted.getPieceMoved() instanceof Knight),
				() -> assertEquals(moveInterpreted.getPieceMoved().isWhite(), WHITE)
				);
	}

	@Test
	void testNumberAmbiguity() {
		board.getSpot(ROW_3, COL_C).setPiece(new Knight(WHITE));
		board.getSpot(ROW_7, COL_C).setPiece(new Knight(WHITE));

		interpret("C3d5", WHITE);

		moveExpected = new Move(new Spot(ROW_3, COL_C), new Spot(ROW_5, COL_D));
		assertAll(
				() -> assertEquals(moveInterpreted, moveExpected),
				() -> assertTrue(moveInterpreted.getPieceMoved() instanceof Knight),
				() -> assertEquals(moveInterpreted.getPieceMoved().isWhite(), WHITE)
				);
	}

	@Test
	void testAmbiguityCapture() {
		board.getSpot(ROW_3, COL_C).setPiece(new Pawn(WHITE));
		board.getSpot(ROW_3, COL_E).setPiece(new Pawn(WHITE));
		board.getSpot(ROW_4, COL_D).setPiece(new Pawn(BLACK));

		interpret("cxd4", WHITE);

		moveExpected = new Move(new Spot(ROW_3, COL_C), new Spot(ROW_4, COL_D));
		assertAll(
				() -> assertEquals(moveInterpreted, moveExpected),
				() -> assertTrue(moveInterpreted.getPieceMoved() instanceof Pawn),
				() -> assertEquals(moveInterpreted.getPieceMoved().isWhite(), WHITE)
				);
	}

	@Test
	void testNotSetAmbiguityCapture() {
		board.getSpot(ROW_3, COL_C).setPiece(new Knight(WHITE));
		board.getSpot(ROW_4, COL_F).setPiece(new Knight(WHITE));
		board.getSpot(ROW_5, COL_D).setPiece(new Knight(BLACK));

		interpret("Cxd5", WHITE);

		assertNull(moveInterpreted.getStart());
	}

	@Test
	void testNotSetAmbiguity() {
		board.getSpot(ROW_3, COL_C).setPiece(new Knight(WHITE));
		board.getSpot(ROW_7, COL_C).setPiece(new Knight(WHITE));

		interpret("Cd5", WHITE);

		assertNull(moveInterpreted.getStart());
	}

	/**********************************************************************************/

	/*
	 * TEST DI INTERPRETAZIONE DEI COMANDI NON VALIDI
	 */

	// mossa non valida (pezzo da muovere diverso)
	@Test
	void testCommandNotValidPiece() {
		interpret("d7", BLACK);

		assertNull(moveInterpreted.getStart());
	}

	// mossa non valida (casa di arrivo non legale)

	@Test
	void testCommandNotValidEnd() {
		interpret("Rd6", BLACK);

		assertNull(moveInterpreted.getStart());
	}

	// mossa non valida (spot inesistente)
	@Test
	void testCommandNotValidSpot() {
		interpret("Rd9", BLACK);

		assertNull(moveInterpreted.getStart());
	}

	// mossa non valida (cattura pezzo amico)
	@Test
	void testCommandNotValidCapture() {
		board.getSpot(ROW_7, COL_D).setPiece(new Knight(BLACK));

		interpret("Rxd7", BLACK);

		assertNull(moveInterpreted.getStart());
	}
}
