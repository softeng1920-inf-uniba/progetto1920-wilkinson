package it.uniba.test;

import static org.junit.Assert.assertEquals;
import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import it.uniba.logic.Bishop;
import it.uniba.logic.Board;
import it.uniba.logic.Game;
import it.uniba.logic.King;
import it.uniba.logic.Pawn;
import it.uniba.logic.Piece;
import it.uniba.logic.Rook;
import it.uniba.logic.Spot;

public class GameTest {
	private static final int DIM_BOARD = 8;
	private Game game;
	private Board newBoard;
	private Spot start;
	private Spot end;
	private Piece examinedPiece;
	private boolean legalMove;

	private static final boolean WHITE = true;
	private static final boolean BLACK = false;
	// RIGHE
	private static final int ROW_1 = 7;
	private static final int ROW_2 = 6;
	private static final int ROW_3 = 5;
	private static final int ROW_4 = 4;
	private static final int ROW_5 = 3;
	private static final int ROW_6 = 2;
	private static final int ROW_7 = 1;
	private static final int ROW_8 = 0;
	// COLONNE
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
	 * 		- i due re devono essere sulla scacchiera
	 */
	@BeforeEach
	void setUp() {
		game = new Game();
		newBoard = new Board(true);
		newBoard.getSpot(ROW_1, COL_E).setPiece(new King(WHITE));
		newBoard.getSpot(ROW_8, COL_E).setPiece(new King(BLACK));
	}

	/**
	 * Post-condizioni:
	 * 		- non devono esserci pezzi killed sulla scacchiera
	 * 		- i booleani di en passant devono essere resettati correttamente
	 * 		- il pezzo considerato deve essere settato come mosso
	 * 		- in end deve esserci lo stesso tipo di pezzo mosso da start (e dello stesso colore)
	 */
	@AfterEach
	void tearDown() {
		for (int i = 0; i < DIM_BOARD; i++) {
			for (int j = 0; j < DIM_BOARD; j++) {
				Spot currentSpot = game.getBoard().getSpot(i, j);

				if (!currentSpot.isEmpty()) {
					// controllo che tutti i pezzi sulla scacchiera non siano contrassegnati come
					// killed
					assertFalse(currentSpot.getPiece().isKilled());
					if (currentSpot.getPiece() instanceof Pawn
							&& currentSpot.getPiece().isWhite() == game.isWhiteTurn()) {
						// controllo i booleani di en passant della fazione del prossimo turno
						assertFalse(((Pawn) game.getBoard().getSpot(i, j).getPiece()).isPossibleEnPassantCapture());
					}
				}
			}
		}

		if (legalMove) {
			assertAll(() -> assertTrue(examinedPiece.isMoved()),
					() -> assertEquals(end.getPiece().getClass().getName(), examinedPiece.getClass().getName()),
					() -> assertEquals(end.getPiece().isWhite(), examinedPiece.isWhite()),
					() -> assertTrue(start.isEmpty()));
		}
	}

	/**
	 * ricalcola mosse legali dei pezzi sulla scacchiera e la assegna alla partita
	 * corrente settando il turno al bianco o al nero
	 */
	void recalMoves(boolean turn) {
		newBoard.recalLegalMoves();
		newBoard.refineLegalMoves();
		game.setBoard(newBoard);
		game.setWhiteTurn(turn);
	}

	@Test
	void testWhiteEnPassantBoolean() {
		legalMove = true;
		newBoard.getSpot(ROW_2, COL_D).setPiece(new Pawn(WHITE));

		recalMoves(WHITE);

		// case di inizio e fine movimento
		start = game.getBoard().getSpot(ROW_2, COL_D);
		end = game.getBoard().getSpot(ROW_4, COL_D);

		// pedone esaminato
		examinedPiece = start.getPiece();

		// controllo il booleano del pedone da muovere
		assertFalse(((Pawn) start.getPiece()).isPossibleEnPassantCapture());

		assertTrue(game.currentGame("d4")); // effettuo la mossa

		// controllo il booleano del pedone mosso
		assertTrue(((Pawn) end.getPiece()).isPossibleEnPassantCapture());
	}

	@Test
	void testBlackEnPassantBoolean() {
		legalMove = true;
		newBoard.getSpot(ROW_7, COL_D).setPiece(new Pawn(BLACK));

		recalMoves(BLACK);

		// case di inizio e fine movimento
		start = game.getBoard().getSpot(ROW_7, COL_D);
		end = game.getBoard().getSpot(ROW_5, COL_D);

		// pedone esaminato
		examinedPiece = start.getPiece();

		// controllo il booleano del pedone da muovere
		assertFalse(((Pawn) start.getPiece()).isPossibleEnPassantCapture());

		assertTrue(game.currentGame("d5")); // effettuo la mossa
		// controllo il booleano del pedone mosso
		assertTrue(((Pawn) end.getPiece()).isPossibleEnPassantCapture());
	}

	@Test
	void testWhiteOneSpotEnPassantBoolean() {
		legalMove = true;
		newBoard.getSpot(ROW_2, COL_D).setPiece(new Pawn(WHITE));

		recalMoves(WHITE);

		// case di inizio e fine movimento
		start = game.getBoard().getSpot(ROW_2, COL_D);
		end = game.getBoard().getSpot(ROW_3, COL_D);

		// pedone esaminato
		examinedPiece = start.getPiece();

		// controllo il booleano del pedone da muovere
		assertFalse(((Pawn) start.getPiece()).isPossibleEnPassantCapture());

		assertTrue(game.currentGame("d3")); // effettuo la mossa

		// controllo il booleano del pedone mosso
		assertFalse(((Pawn) end.getPiece()).isPossibleEnPassantCapture());
	}

	@Test
	void testBlackOneSpotEnPassantBoolean() {
		legalMove = true;
		newBoard.getSpot(ROW_7, COL_D).setPiece(new Pawn(BLACK));

		recalMoves(BLACK);

		// case di inizio e fine movimento
		start = game.getBoard().getSpot(ROW_7, COL_D);
		end = game.getBoard().getSpot(ROW_6, COL_D);

		// pedone esaminato
		examinedPiece = start.getPiece();

		// controllo il booleano del pedone da muovere
		assertFalse(((Pawn) start.getPiece()).isPossibleEnPassantCapture());

		assertTrue(game.currentGame("d6")); // effettuo la mossa
		// controllo il booleano del pedone mosso
		assertFalse(((Pawn) end.getPiece()).isPossibleEnPassantCapture());
	}

	@Test
	void testWhiteEnPassantCapture() {
		legalMove = true;
		newBoard.getSpot(ROW_5, COL_D).setPiece(new Pawn(WHITE));
		// pedone da catturare
		newBoard.getSpot(ROW_5, COL_C).setPiece(new Pawn(BLACK));
		((Pawn) newBoard.getSpot(ROW_5, COL_C).getPiece()).setPossibleEnPassantCapture(true);

		recalMoves(WHITE);

		// case di inizio e fine movimento
		start = game.getBoard().getSpot(ROW_5, COL_D);
		end = game.getBoard().getSpot(ROW_6, COL_C);

		// pedone esaminato
		examinedPiece = start.getPiece();

		assertTrue(game.currentGame("dxc6")); // effettuo la mossa

		// controllo che il pedone in c5 sia stato catturato
		assertTrue(game.getBoard().getSpot(ROW_5, COL_C).isEmpty());
	}

	@Test
	void testBlackEnPassantCapture() {
		legalMove = true;
		newBoard.getSpot(ROW_4, COL_D).setPiece(new Pawn(BLACK));
		// pedone da catturare
		newBoard.getSpot(ROW_4, COL_C).setPiece(new Pawn(WHITE));
		((Pawn) newBoard.getSpot(ROW_4, COL_C).getPiece()).setPossibleEnPassantCapture(true);

		recalMoves(BLACK);

		// case di inizio e fine movimento
		start = game.getBoard().getSpot(ROW_4, COL_D);
		end = game.getBoard().getSpot(ROW_3, COL_C);

		// pedone esaminato
		examinedPiece = start.getPiece();

		assertTrue(game.currentGame("dxc3")); // effettuo la mossa

		// controllo che il pedone in c4 sia stato catturato
		assertTrue(game.getBoard().getSpot(ROW_4, COL_C).isEmpty());
	}

	@Test
	void testWhiteNotEnPassantCapture() {
		legalMove = false;
		newBoard.getSpot(ROW_5, COL_D).setPiece(new Pawn(WHITE));
		// pedone da catturare
		newBoard.getSpot(ROW_6, COL_C).setPiece(new Pawn(BLACK));

		recalMoves(WHITE);

		assertFalse(game.currentGame("dxc6 ep")); // mossa non scritta correttamente
	}

	@Test
	void testBlackNotEnPassantCapture() {
		legalMove = false;
		newBoard.getSpot(ROW_4, COL_D).setPiece(new Pawn(BLACK));
		// pedone da catturare
		newBoard.getSpot(ROW_3, COL_C).setPiece(new Pawn(WHITE));

		recalMoves(BLACK);

		assertFalse(game.currentGame("dxc3 ep")); // effettuo la mossa
	}

	@Test
	void testEnPassantBooleanReset() {
		legalMove = false;
		newBoard.getSpot(ROW_2, COL_B).setPiece(new Pawn(WHITE));
		newBoard.getSpot(ROW_7, COL_E).setPiece(new Pawn(BLACK));

		recalMoves(WHITE);

		// controllo il booleano del pedone da muovere
		assertFalse(((Pawn) game.getBoard().getSpot(ROW_2, COL_B).getPiece()).isPossibleEnPassantCapture());
		assertTrue(game.currentGame("b4")); // spingo di due case il pedone bianco
		// ricontrollo il booleano del pedone mosso
		assertTrue(((Pawn) game.getBoard().getSpot(ROW_4, COL_B).getPiece()).isPossibleEnPassantCapture());

		// controllo il booleano del pedone da muovere
		assertFalse(((Pawn) game.getBoard().getSpot(ROW_7, COL_E).getPiece()).isPossibleEnPassantCapture());
		assertTrue(game.currentGame("e5")); // spingo di due case il pedone nero
		// ricontrollo il booleano del pedone mosso
		assertTrue(((Pawn) game.getBoard().getSpot(ROW_5, COL_E).getPiece()).isPossibleEnPassantCapture());
		// ricontrollo il booleano del pedone iniziale
		assertFalse(((Pawn) game.getBoard().getSpot(ROW_4, COL_B).getPiece()).isPossibleEnPassantCapture());
	}

	@Test
	void testCaptureWithNoXcommand() {
		legalMove = false;
		newBoard.getSpot(ROW_5, COL_D).setPiece(new Rook(WHITE));
		// pezzo da catturare
		newBoard.getSpot(ROW_5, COL_A).setPiece(new Pawn(BLACK));

		recalMoves(WHITE);

		assertFalse(game.currentGame("Ta5")); // mossa non scritta correttamente
	}

	@Test
	void testMovementWithXcommand() {
		legalMove = false;
		newBoard.getSpot(ROW_5, COL_D).setPiece(new Pawn(WHITE));

		recalMoves(WHITE);

		assertFalse(game.currentGame("xd6")); // mossa non scritta correttamente
	}

	@Test
	void testWhiteKingNoLegalMoves() {
		legalMove = false;
		newBoard.getSpot(ROW_2, COL_D).setPiece(new Rook(BLACK));
		newBoard.getSpot(ROW_2, COL_F).setPiece(new Rook(BLACK));

		recalMoves(WHITE);

		assertAll(
				() -> assertFalse(game.currentGame("Rd1")),
				() -> assertFalse(game.currentGame("Rd2")),
				() -> assertFalse(game.currentGame("Re2")),
				() -> assertFalse(game.currentGame("Rf1")),
				() -> assertFalse(game.currentGame("Rf2")),
				() -> assertFalse(game.currentGame("Rxd2")),
				() -> assertFalse(game.currentGame("Rxf2"))
				);
	}

	@Test
	void testBlackKingNoLegalMoves() {
		legalMove = false;
		newBoard.getSpot(ROW_7, COL_D).setPiece(new Rook(WHITE));
		newBoard.getSpot(ROW_7, COL_F).setPiece(new Rook(WHITE));

		recalMoves(BLACK);

		assertAll(
				() -> assertFalse(game.currentGame("Rd8")),
				() -> assertFalse(game.currentGame("Rd7")),
				() -> assertFalse(game.currentGame("Re7")),
				() -> assertFalse(game.currentGame("Rf8")),
				() -> assertFalse(game.currentGame("Rf7")),
				() -> assertFalse(game.currentGame("Rxd7")),
				() -> assertFalse(game.currentGame("Rxf7"))
				);
	}

	@Test
	void testWhitePin() {
		legalMove = false;
		newBoard.getSpot(ROW_2, COL_D).setPiece(new Pawn(WHITE));
		newBoard.getSpot(ROW_4, COL_B).setPiece(new Bishop(BLACK));

		recalMoves(WHITE);

		assertAll(
				() -> assertFalse(game.currentGame("d3")),
				() -> assertFalse(game.currentGame("d4"))
				);
	}

	@Test
	void testBlackPin() {
		legalMove = false;
		newBoard.getSpot(ROW_7, COL_D).setPiece(new Pawn(BLACK));
		newBoard.getSpot(ROW_5, COL_B).setPiece(new Bishop(WHITE));

		recalMoves(BLACK);

		assertAll(
				() -> assertFalse(game.currentGame("d6")),
				() -> assertFalse(game.currentGame("d5"))
				);
	}

	@Test
	void testWhiteKingCaptured() {
		legalMove = false;
		newBoard.getSpot(ROW_4, COL_B).setPiece(new Bishop(BLACK));

		recalMoves(BLACK);

		assertFalse(game.currentGame("Axe1"));
	}

	@Test
	void testBlackKingCaptured() {
		legalMove = false;
		newBoard.getSpot(ROW_5, COL_B).setPiece(new Bishop(WHITE));

		recalMoves(WHITE);

		assertFalse(game.currentGame("Axe8"));
	}

	@Test
	void testWhiteKingShortCastle() {
		legalMove = true;
		newBoard.getSpot(ROW_1, COL_H).setPiece(new Rook(WHITE));

		recalMoves(WHITE);

		// case di inizio e fine movimento
		start = game.getBoard().getSpot(ROW_1, COL_E);
		end = game.getBoard().getSpot(ROW_1, COL_G);

		// re esaminato
		examinedPiece = start.getPiece();

		assertFalse(game.currentGame("O-O-O"));
		assertTrue(game.currentGame("O-O"));
	}

	@Test
	void testBlackKingShortCastle() {
		legalMove = true;
		newBoard.getSpot(ROW_8, COL_H).setPiece(new Rook(BLACK));

		recalMoves(BLACK);

		// case di inizio e fine movimento
		start = game.getBoard().getSpot(ROW_8, COL_E);
		end = game.getBoard().getSpot(ROW_8, COL_G);

		// re esaminato
		examinedPiece = start.getPiece();

		assertFalse(game.currentGame("O-O-O"));
		assertTrue(game.currentGame("O-O"));
	}

	@Test
	void testWhiteKingLongCastle() {
		legalMove = true;
		newBoard.getSpot(ROW_1, COL_A).setPiece(new Rook(WHITE));

		recalMoves(WHITE);

		// case di inizio e fine movimento
		start = game.getBoard().getSpot(ROW_1, COL_E);
		end = game.getBoard().getSpot(ROW_1, COL_C);

		// re esaminato
		examinedPiece = start.getPiece();

		assertFalse(game.currentGame("O-O"));
		assertTrue(game.currentGame("O-O-O"));
	}

	@Test
	void testBlackKingLongCastle() {
		legalMove = true;
		newBoard.getSpot(ROW_8, COL_A).setPiece(new Rook(BLACK));

		recalMoves(BLACK);

		// case di inizio e fine movimento
		start = game.getBoard().getSpot(ROW_8, COL_E);
		end = game.getBoard().getSpot(ROW_8, COL_C);

		// re esaminato
		examinedPiece = start.getPiece();

		assertFalse(game.currentGame("O-O"));
		assertTrue(game.currentGame("O-O-O"));
	}

	@Test
	void testSameRowAmbiguityMove() {
		legalMove = true;
		newBoard.getSpot(ROW_5, COL_B).setPiece(new Rook(WHITE));
		newBoard.getSpot(ROW_5, COL_F).setPiece(new Rook(WHITE));

		recalMoves(WHITE);

		// case di inizio e fine movimento
		start = game.getBoard().getSpot(ROW_5, COL_B);
		end = game.getBoard().getSpot(ROW_5, COL_D);

		// pezzo esaminato
		examinedPiece = start.getPiece();

		assertFalse(game.currentGame("Td5"));
		assertTrue(game.currentGame("Tbd5"));
	}

	@Test
	void testSameRowAmbiguityCapture() {
		legalMove = true;
		newBoard.getSpot(ROW_5, COL_B).setPiece(new Rook(WHITE));
		newBoard.getSpot(ROW_5, COL_F).setPiece(new Rook(WHITE));
		// pezzo da catturare
		newBoard.getSpot(ROW_5, COL_D).setPiece(new Rook(BLACK));

		recalMoves(WHITE);

		// case di inizio e fine movimento
		start = game.getBoard().getSpot(ROW_5, COL_B);
		end = game.getBoard().getSpot(ROW_5, COL_D);

		// pezzo esaminato
		examinedPiece = start.getPiece();

		assertFalse(game.currentGame("Txd5"));
		assertTrue(game.currentGame("Tbxd5"));
	}

	@Test
	void testSameColAmbiguityMove() {
		legalMove = true;
		newBoard.getSpot(ROW_6, COL_C).setPiece(new Rook(WHITE));
		newBoard.getSpot(ROW_2, COL_C).setPiece(new Rook(WHITE));

		recalMoves(WHITE);

		// case di inizio e fine movimento
		start = game.getBoard().getSpot(ROW_6, COL_C);
		end = game.getBoard().getSpot(ROW_4, COL_C);

		// pezzo esaminato
		examinedPiece = start.getPiece();

		assertFalse(game.currentGame("Tc4"));
		assertTrue(game.currentGame("T6c4"));
	}

	@Test
	void testSameColAmbiguityCapture() {
		legalMove = true;
		newBoard.getSpot(ROW_6, COL_C).setPiece(new Rook(BLACK));
		newBoard.getSpot(ROW_2, COL_C).setPiece(new Rook(BLACK));
		// pezzo da catturare
		newBoard.getSpot(ROW_4, COL_C).setPiece(new Rook(WHITE));

		recalMoves(BLACK);

		// case di inizio e fine movimento
		start = game.getBoard().getSpot(ROW_6, COL_C);
		end = game.getBoard().getSpot(ROW_4, COL_C);

		// pezzo esaminato
		examinedPiece = start.getPiece();

		assertFalse(game.currentGame("Txc4"));
		assertTrue(game.currentGame("T6xc4"));
	}
}
