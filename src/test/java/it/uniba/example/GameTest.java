package it.uniba.example;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import it.uniba.main.Bishop;
import it.uniba.main.Board;
import it.uniba.main.Game;
import it.uniba.main.King;
import it.uniba.main.Knight;
import it.uniba.main.Pawn;
import it.uniba.main.Piece;
import it.uniba.main.Queen;
import it.uniba.main.Rook;
import it.uniba.main.Spot;

public class GameTest {
	private static final int DIM_BOARD = 8;
	private static Game game;
	private static Board newBoard;
	private static final boolean WHITE = true;
	private static final boolean BLACK = false;
	//  RIGHE
	private static final int ROW_1 = 7;
	private static final int ROW_2 = 6;
	private static final int ROW_3 = 5;
	private static final int ROW_4 = 4;
	private static final int ROW_5 = 3;
	private static final int ROW_6 = 2;
	private static final int ROW_7 = 1;
	private static final int ROW_8 = 0;
	//  COLONNE
	private static final int COL_H = 7;
	private static final int COL_G = 6;
	private static final int COL_F = 5;
	private static final int COL_E = 4;
	private static final int COL_D = 3;
	private static final int COL_C = 2;
	private static final int COL_B = 1;
	private static final int COL_A = 0;

	@BeforeEach
	void setUp() {
		game = new Game();
		newBoard = new Board(true);
		newBoard.getSpot(ROW_1, COL_E).setPiece(new King (WHITE));
		newBoard.getSpot(ROW_8, COL_E).setPiece(new King (BLACK));
	}

	// Test situazioni en passant pedone bianco 
	@Test
	void testEnPassantWhite() {
		// Cattura en passant da parte del pedone bianco
		Piece piece;
		// inizializzo pedoni 
		for (int j = COL_A; j < DIM_BOARD; j++) {
			newBoard.getSpot(ROW_7, j).setPiece(new Pawn(BLACK));
			newBoard.getSpot(ROW_2, j).setPiece(new Pawn(WHITE));
		}
		newBoard.getSpot(ROW_4, COL_C).setPiece(new Pawn(WHITE));
		newBoard.recalLegalMoves();
		game.setBoard(newBoard);
		game.currentGame("c5");
		game.currentGame("b5");
		piece= game.getBoard().getSpot(ROW_5, COL_B).getPiece();
		// controllo che il pezzo nero mosso di due e' catturabile en passant
		assertTrue(((Pawn) piece).isPossibleEnPassantCapture());
		// controllo che i pedoni non mossi abbiano
		// il booleano dell'en passant settato a false
		checkEnPassantBoolean();
		// controllo che finito il turno il pedone non sia piu' catturabile en passant
		game.currentGame("a3"); 
		assertFalse(((Pawn) piece).isPossibleEnPassantCapture());
		// controllo che il nuovo pedone mosso (d5) sia catturabile en passant
		// e che quello in (b5) non lo sia piu'
		game.currentGame("d5");
		assertFalse(((Pawn) piece).isPossibleEnPassantCapture());
		piece = (game.getBoard().getSpot(ROW_5, COL_D).getPiece());
		assertTrue(((Pawn) piece).isPossibleEnPassantCapture());
		// provo a catturare il pedone in (b5)
		assertFalse(game.currentGame("cxb6"));
		// catturo il pedone in (d5)
		assertTrue(game.currentGame("cxd6"));
	}

	void checkEnPassantBoolean() {
		for (int i = 0; i < DIM_BOARD; i++) {
			for (int j = 0; j < DIM_BOARD; j++) {
				Spot currentSpot = game.getBoard().getSpot(i, j);

				if (!currentSpot.isEmpty()) {
					if (currentSpot.getPiece() instanceof Pawn
							&& !currentSpot.getPiece().isMoved()) {
						// controllo i booleani di en passant dei pedoni non mossi
						assertFalse(((Pawn) game.getBoard().getSpot(i, j).getPiece()).isPossibleEnPassantCapture());
					}
				}
			}
		}
	}
	//controllo che lo spostamento di una casa di un pezzo nero 
	//alla prima mossa, non setti il booleano per la possibile
	//cattura en passant
	@Test
	void testEnPassantOneSpotBlack() {
		Piece piece;
		// inizializzo pedoni 
		for (int j = COL_A; j < DIM_BOARD; j++) {
			newBoard.getSpot(ROW_7, j).setPiece(new Pawn(BLACK));
			newBoard.getSpot(ROW_2, j).setPiece(new Pawn(WHITE));
		}
		newBoard.getSpot(ROW_4, COL_C).setPiece(new Pawn(WHITE));
		newBoard.recalLegalMoves();
		game.setBoard(newBoard);
		game.currentGame("c5");
		game.currentGame("b6");
		piece= game.getBoard().getSpot(ROW_6, COL_B).getPiece();
		// controllo che il booleano non sia true
		assertFalse(((Pawn) piece).isPossibleEnPassantCapture());
	}

	@Test
	void testEnPassantBlack() {
		Piece piece;
		// Cattura en passant da parte del pedone nero
		// inizializzo pedoni 
		for (int j = COL_A; j < DIM_BOARD; j++) {
			newBoard.getSpot(ROW_7, j).setPiece(new Pawn(BLACK));
			newBoard.getSpot(ROW_2, j).setPiece(new Pawn(WHITE));
		}
		newBoard.getSpot(ROW_4, COL_B).setPiece(new Pawn(BLACK));
		newBoard.recalLegalMoves();
		game.setBoard(newBoard);
		game.currentGame("a4");
		piece=game.getBoard().getSpot(ROW_4, COL_A).getPiece();
		// controllo che il pezzo nero mosso di due e' catturabile en passant
		assertTrue(((Pawn) piece).isPossibleEnPassantCapture());
		// controllo che i pedoni non mossi abbiano
		// il booleano dell'en passant settato a false
		checkEnPassantBoolean();
		// controllo che finito il turno il pedone non sia piu' catturabile en passant
		game.currentGame("h6");
		assertFalse(((Pawn) piece).isPossibleEnPassantCapture());
		// controllo che il nuovo pedone mosso (c4) sia catturabile en passant
		// e che quello in (a4) non lo sia piu'
		game.currentGame("c4");
		assertFalse(((Pawn) piece).isPossibleEnPassantCapture());
		piece = (game.getBoard().getSpot(ROW_4, COL_C).getPiece());
		assertTrue(((Pawn) piece).isPossibleEnPassantCapture());
		// provo a catturare il pedone in (a4)
		assertFalse(game.currentGame("bxa3"));
		// catturo il pedone in (f6)
		assertTrue(game.currentGame("bxc3"));
	}

	//controllo che lo spostamento di una casa di un pezzo bianco
	//alla prima mossa, non setti il booleano per la possibile
	//cattura en passant
	@Test
	void testEnPassantOneSpotWhite() {
		Piece piece;
		// inizializzo pedoni 
		for (int j = COL_A; j < DIM_BOARD; j++) {
			newBoard.getSpot(ROW_7, j).setPiece(new Pawn(BLACK));
			newBoard.getSpot(ROW_2, j).setPiece(new Pawn(WHITE));
		}
		newBoard.getSpot(ROW_4, COL_A).setPiece(new Pawn(BLACK));
		newBoard.recalLegalMoves();
		game.setBoard(newBoard);
		game.currentGame("b3"); //Movimento di una sola casa 
		piece= game.getBoard().getSpot(ROW_3, COL_B).getPiece();
		// controllo che il booleano non sia stato settato a true
		assertFalse(((Pawn) piece).isPossibleEnPassantCapture());
	}

	//testo situazioni in cui il re bianco non ha possibilità di movimento
	@Test
	void testWhiteKingNoLegalMoves() {
		//inserisco i pezzi nella scacchiera per bloccare il movimento del re bianco
		newBoard.getSpot(ROW_1, COL_D).setPiece(new Rook(WHITE)); 
		newBoard.getSpot(ROW_2, COL_D).setPiece(new Pawn(WHITE));
		newBoard.getSpot(ROW_2, COL_F).setPiece(new Rook(BLACK)); 
		newBoard.getSpot(ROW_2, COL_H).setPiece(new Rook(BLACK));
		newBoard.recalLegalMoves();
		newBoard.refineLegalMoves();
		game.setBoard(newBoard);
		assertFalse(game.currentGame("Rxf2"));//la mossa non potra' essere effettuata
		assertFalse(game.currentGame("Re2"));//la mossa non potra' essere effettuata
		assertFalse(game.currentGame("Rf1"));//la mossa non potra' essere effettuata
		//controllo che il re non si sia mosso
		assertTrue(game.getBoard().getSpot(ROW_1, COL_E).getPiece() instanceof King);
	}

	@Test
	void testWhiteKingOneLegalMoves() {
		//inserisco i pezzi nella scacchiera per dare un solo movimento del re bianco
		Piece piece;
		newBoard.getSpot(ROW_1, COL_D).setPiece(new Rook(WHITE)); 
		newBoard.getSpot(ROW_2, COL_D).setPiece(new Pawn(WHITE));
		newBoard.getSpot(ROW_2, COL_F).setPiece(new Rook(BLACK)); 
		newBoard.recalLegalMoves();
		game.setBoard(newBoard);
		piece= game.getBoard().getSpot(ROW_1, COL_E).getPiece();
		assertTrue(game.currentGame("Rxf2")); //eseguo l'unica mossa possibile
		assertTrue(game.getBoard().getSpot(ROW_2, COL_F).getPiece() instanceof King);
	}

	@Test
	void testBlackKingNoLegalMoves() {
		//inserisco i pezzi nella scacchiera per bloccare il movimento del re nero
		newBoard.getSpot(ROW_7, COL_D).setPiece(new Pawn(BLACK)); 
		newBoard.getSpot(ROW_8, COL_D).setPiece(new Rook(BLACK));
		newBoard.getSpot(ROW_7, COL_F).setPiece(new Rook(WHITE)); 
		newBoard.getSpot(ROW_6, COL_G).setPiece(new Pawn(WHITE));
		newBoard.recalLegalMoves();
		newBoard.refineLegalMoves();
		game.setBoard(newBoard);
		game.currentGame("Re2");
		assertFalse(game.currentGame("Rxf7")); //provo ad eseguire la mossa illegale
		assertFalse(game.currentGame("Re7")); //provo ad eseguire la mossa illegale
		assertFalse(game.currentGame("Rf8")); //provo ad eseguire la mossa illegale
		assertTrue(newBoard.getSpot(ROW_8, COL_E).getPiece() instanceof King);
	}
	//testo situazione in cui il re nero ha solo una mossa possibile
	@Test
	void testBlackKingOneLegalMoves() {
		//inserisco i pezzi nella scacchiera per permettere un solo movimento al re nero
		newBoard.getSpot(ROW_7, COL_D).setPiece(new Pawn(BLACK)); 
		newBoard.getSpot(ROW_8, COL_D).setPiece(new Rook(BLACK));
		newBoard.getSpot(ROW_7, COL_F).setPiece(new Rook(WHITE)); 
		newBoard.recalLegalMoves();
		newBoard.refineLegalMoves();
		game.setBoard(newBoard);
		game.currentGame("Re2");
		assertTrue(game.currentGame("Rxf7")); //provo ad eseguire l'unica mossa
		assertTrue(newBoard.getSpot(ROW_7, COL_F).getPiece() instanceof King);
	}

	//test di eventuali situazioni in cui il movimento di un pezzo espongono il re bianco
	@Test
	void testWhiteKingUnderAttack() {
		// serie di mosse per controllare che non sia possibile scoprire il re bianco
		newBoard.getSpot(ROW_2, COL_E).setPiece(new Rook(WHITE)); 
		newBoard.getSpot(ROW_7, COL_E).setPiece(new Rook(BLACK)); 
		newBoard.recalLegalMoves();
		newBoard.refineLegalMoves();
		game.setBoard(newBoard);
		assertFalse(game.currentGame("Ta2")); //provo ad eseguire la mossa che esporrebbe il re bianco
	}

	//test di eventuali situazioni in cui il movimento di un pezzo espongono il re nero
	@Test
	void testBlackKingUnderAttack() {
		newBoard.getSpot(ROW_2, COL_E).setPiece(new Rook(WHITE)); 
		newBoard.getSpot(ROW_7, COL_E).setPiece(new Rook(BLACK)); 
		newBoard.recalLegalMoves();
		newBoard.refineLegalMoves();
		game.setBoard(newBoard);
		game.currentGame("Te3");
		assertFalse(game.currentGame("Ta7")); //provo ad eseguire la mossa che esporrebbe il re nero
	}

	// test di situazioni di ambiguita: quando due cavalli bianchi possono spostarsi nella stessa casa d'arrivo
	@Test
	void testIsAmbiguityWhiteKnight() {
		// test ambiguita' movimento cavallo bianco
		newBoard.getSpot(ROW_3, COL_A).setPiece(new Knight(WHITE)); 
		newBoard.getSpot(ROW_3, COL_E).setPiece(new Knight(WHITE)); 
		newBoard.recalLegalMoves();
		newBoard.refineLegalMoves();
		game.setBoard(newBoard);
		assertFalse(game.currentGame("Cc2"));//provo ad effettuare la mossa ambigua
		assertTrue(game.getBoard().getSpot(ROW_2, COL_C).isEmpty());
		assertTrue(game.currentGame("Cac2"));//effettuo la mossa non ambigua
		assertTrue(game.getBoard().getSpot(ROW_2, COL_C).getPiece() instanceof Knight);
	}

	// test ambiguita' cattura da parte del cavallo bianco
	@Test
	void testIsAmbiguityWhiteKnightCapture() {
		newBoard.getSpot(ROW_3, COL_A).setPiece(new Knight(WHITE)); 
		newBoard.getSpot(ROW_3, COL_E).setPiece(new Knight(WHITE)); 
		newBoard.getSpot(ROW_2, COL_C).setPiece(new Pawn(BLACK)); 
		newBoard.recalLegalMoves();
		newBoard.refineLegalMoves();
		game.setBoard(newBoard);
		assertFalse(game.currentGame("Cxc2"));//provo ad effettuare la mossa ambigua
		assertTrue(game.currentGame("Caxc2"));//effettuo la mossa non ambigua
		assertTrue(game.getBoard().getSpot(ROW_2, COL_C).getPiece() instanceof Knight);
	}

	// test ambiguita' movimento cavallo nero
	@Test
	void testIsAmbiguityBlackKnight() {
		newBoard.getSpot(ROW_6, COL_A).setPiece(new Knight(BLACK)); 
		newBoard.getSpot(ROW_6, COL_E).setPiece(new Knight(BLACK)); 
		newBoard.recalLegalMoves();
		newBoard.refineLegalMoves();
		game.setBoard(newBoard);
		game.currentGame("Re2");
		assertFalse(game.currentGame("Cc7"));//provo ad effettuare la mossa ambigua
		assertTrue(game.currentGame("Cac7"));//effettuo la mossa non ambigua
		assertTrue(game.getBoard().getSpot(ROW_7, COL_C).getPiece() instanceof Knight);
	}

	// test ambiguita' cattura da parte del cavallo nero
	@Test
	void testIsAmbiguityBlackKnightCapture() {
		newBoard.getSpot(ROW_6, COL_A).setPiece(new Knight(BLACK)); 
		newBoard.getSpot(ROW_6, COL_E).setPiece(new Knight(BLACK)); 
		newBoard.getSpot(ROW_7, COL_C).setPiece(new Pawn(WHITE)); 
		newBoard.recalLegalMoves();
		newBoard.refineLegalMoves();
		game.setBoard(newBoard); 
		game.currentGame("Re2");
		assertFalse(game.currentGame("Cxc7"));//provo ad effettuare la mossa ambigua
		assertTrue(game.currentGame("Caxc7"));//effettuo la mossa non ambigua
		assertTrue(game.getBoard().getSpot(ROW_7, COL_C).getPiece() instanceof Knight);
	}

	// test ambiguita' movimento pedone bianco, due pedoni nella stessa colonna
	@Test
	void testIsAmbiguityWhitePawn() {
		newBoard.getSpot(ROW_2, COL_A).setPiece(new Pawn(WHITE)); 
		newBoard.getSpot(ROW_3, COL_A).setPiece(new Pawn(WHITE)); 
		newBoard.recalLegalMoves();
		newBoard.refineLegalMoves();
		game.setBoard(newBoard); 
		game.currentGame("a4");
		//controllo che si e' spostato il pedone piu' avanazato
		assertTrue(game.getBoard().getSpot(ROW_4, COL_A).getPiece() instanceof Pawn);
		assertTrue(game.getBoard().getSpot(ROW_3, COL_A).isEmpty());
		assertFalse(game.getBoard().getSpot(ROW_2, COL_A).getPiece().isMoved());
	}
	// test ambiguita' cattura da parte del pedone bianco
	@Test
	void testIsAmbiguityWhitePawnCapture() {
		newBoard.getSpot(ROW_2, COL_A).setPiece(new Pawn(WHITE)); 
		newBoard.getSpot(ROW_2, COL_C).setPiece(new Pawn(WHITE)); 
		newBoard.getSpot(ROW_3, COL_B).setPiece(new Knight(BLACK)); 
		newBoard.recalLegalMoves();
		newBoard.refineLegalMoves();
		game.setBoard(newBoard); 
		//provo a spostarmi senza cattura
		assertFalse(game.currentGame("b3"));
		game.currentGame("axb3");
		//controllo che c'e' stata la cattura
		assertTrue(game.getBoard().getSpot(ROW_3, COL_B).getPiece() instanceof Pawn);
		//controllo che si e' spostato il pezzo giusto
		assertTrue(game.getBoard().getSpot(ROW_2, COL_A).isEmpty());
		assertFalse(game.getBoard().getSpot(ROW_2, COL_C).getPiece().isMoved());
	}

	// test ambiguita' movimento pedone nero, due pedoni nella stessa colonna
	@Test
	void testIsAmbiguityBlackPawn() {
		newBoard.getSpot(ROW_7, COL_A).setPiece(new Pawn(BLACK)); 
		newBoard.getSpot(ROW_6, COL_A).setPiece(new Pawn(BLACK)); 
		newBoard.recalLegalMoves();
		newBoard.refineLegalMoves();
		game.setBoard(newBoard); 
		game.currentGame("Re2");
		game.currentGame("a5");
		//controllo che si e' spostato il pedone piu' avanazato
		assertTrue(game.getBoard().getSpot(ROW_5, COL_A).getPiece() instanceof Pawn);
		assertTrue(game.getBoard().getSpot(ROW_6, COL_A).isEmpty());
		assertFalse(game.getBoard().getSpot(ROW_7, COL_A).getPiece().isMoved());
	}
	// test ambiguita' cattura da parte del pedone nero
	@Test
	void testIsAmbiguityBlackPawnCapture() {
		newBoard.getSpot(ROW_7, COL_A).setPiece(new Pawn(BLACK)); 
		newBoard.getSpot(ROW_7, COL_C).setPiece(new Pawn(BLACK)); 
		newBoard.getSpot(ROW_6, COL_B).setPiece(new Knight(WHITE)); 
		newBoard.recalLegalMoves();
		newBoard.refineLegalMoves();
		game.setBoard(newBoard); 
		game.currentGame("Re2");
		//provo a spostarmi senza cattura
		assertFalse(game.currentGame("b6"));
		game.currentGame("axb6");

		//controllo che c'e' stata la cattura
		assertTrue(game.getBoard().getSpot(ROW_6, COL_B).getPiece() instanceof Pawn);
		//controllo che si e' spostato il pezzo giusto
		assertTrue(game.getBoard().getSpot(ROW_7, COL_A).isEmpty());
		assertFalse(game.getBoard().getSpot(ROW_7, COL_C).getPiece().isMoved());
		newBoard = new Board(true);
		newBoard.getSpot(ROW_1, COL_A).setPiece(new King (WHITE));
		newBoard.getSpot(ROW_8, COL_A).setPiece(new King (BLACK));
	}

	@Test
	void testIsMovedAndCapturePawn() {

		//(inizializzazione pezzi per test)
		newBoard.getSpot(ROW_3, COL_B).setPiece(new Pawn(WHITE));
		newBoard.getSpot(ROW_6, COL_C).setPiece(new Pawn(BLACK));
		newBoard.recalLegalMoves();
		game.setBoard(newBoard);

		//test isMoved Pedone
		game.currentGame("b4");
		assertTrue(game.getBoard().getSpot(ROW_4, COL_B).getPiece().isMoved());
		assertTrue(game.getBoard().getSpot(ROW_3, COL_B).isEmpty());

		//test cattura Pedone
		game.currentGame("c5");
		game.currentGame("bxc5");
		assertTrue(game.getBoard().getSpot(ROW_5, COL_C).getPiece() instanceof Pawn);
		assertTrue(game.getBoard().getSpot(ROW_5, COL_C).getPiece().isWhite());
		assertTrue(game.getBoard().getSpot(ROW_4, COL_B).isEmpty());
	}

	@Test
	void testMoveAndCaptureQueen() {

		//(inizializzazione pezzi per test)
		newBoard.getSpot(ROW_3, COL_D).setPiece(new Queen(WHITE));
		newBoard.getSpot(ROW_6, COL_C).setPiece(new Pawn(BLACK));
		newBoard.recalLegalMoves();
		game.setBoard(newBoard);

		//test isMoved Regina
		game.currentGame("Dd4");
		assertTrue(game.getBoard().getSpot(ROW_4, COL_D).getPiece().isMoved());
		assertTrue(game.getBoard().getSpot(ROW_3, COL_D).isEmpty());

		//test cattura Regina
		game.currentGame("c5");
		game.currentGame("Dxc5");
		assertTrue(game.getBoard().getSpot(ROW_5, COL_C).getPiece() instanceof Queen);
		assertTrue(game.getBoard().getSpot(ROW_5, COL_C).getPiece().isWhite());
		assertTrue(game.getBoard().getSpot(ROW_4, COL_D).isEmpty());
	}

	@Test
	void testMoveAndCaptureKnight() {

		//(inizializzazione pezzi per test)
		newBoard.getSpot(ROW_2, COL_E).setPiece(new Knight(WHITE));
		newBoard.getSpot(ROW_7, COL_C).setPiece(new Pawn(BLACK));
		newBoard.recalLegalMoves();
		game.setBoard(newBoard);

		//test isMoved Cavallo
		game.currentGame("Cd4");
		assertTrue(game.getBoard().getSpot(ROW_4, COL_D).getPiece().isMoved());
		assertTrue(game.getBoard().getSpot(ROW_2, COL_E).isEmpty());

		//test cattura Cavallo
		game.currentGame("c6");
		game.currentGame("Cxc6");
		assertTrue(game.getBoard().getSpot(ROW_6, COL_C).getPiece() instanceof Knight);
		assertTrue(game.getBoard().getSpot(ROW_6, COL_C).getPiece().isWhite());
		assertTrue(game.getBoard().getSpot(ROW_4, COL_D).isEmpty());
	}

	@Test
	void testMoveAndCaptureBishop() {

		//(inizializzazione pezzi per test)
		newBoard.getSpot(ROW_2, COL_G).setPiece(new Bishop(WHITE));
		newBoard.getSpot(ROW_6, COL_C).setPiece(new Pawn(BLACK));
		newBoard.recalLegalMoves();
		game.setBoard(newBoard);

		//test isMoved Alfiere
		game.currentGame("Ae4");
		assertTrue(game.getBoard().getSpot(ROW_4, COL_E).getPiece().isMoved());
		assertTrue(game.getBoard().getSpot(ROW_2, COL_G).isEmpty());

		//test cattura Alfiere
		game.currentGame("Re7");
		game.currentGame("Axc6");
		assertTrue(game.getBoard().getSpot(ROW_6, COL_C).getPiece() instanceof Bishop);
		assertTrue(game.getBoard().getSpot(ROW_6, COL_C).getPiece().isWhite());
	}

	@Test
	void testMoveAndCaptureRook() {

		//(inizializzazione pezzi per test)
		newBoard.getSpot(ROW_3, COL_H).setPiece(new Rook(WHITE));
		newBoard.getSpot(ROW_5, COL_C).setPiece(new Pawn(BLACK));
		newBoard.recalLegalMoves();
		game.setBoard(newBoard);

		//test isMoved Torre
		game.currentGame("Th4");
		assertTrue(game.getBoard().getSpot(ROW_4, COL_H).getPiece().isMoved());
		assertTrue(game.getBoard().getSpot(ROW_3, COL_H).isEmpty());

		//test cattura Torre
		game.currentGame("c4");
		game.currentGame("Txc4");
		assertTrue(game.getBoard().getSpot(ROW_4, COL_C).getPiece() instanceof Rook);
		assertTrue(game.getBoard().getSpot(ROW_4, COL_C).getPiece().isWhite());
		assertTrue(game.getBoard().getSpot(ROW_4, COL_H).isEmpty());
	}

	@Test
	void testMoveAndCaptureKing() {

		//(inizializzazione pezzi per test)
		newBoard.getSpot(ROW_3, COL_E).setPiece(new Pawn(BLACK));
		newBoard.recalLegalMoves();
		game.setBoard(newBoard);

		//test isMoved Re
		game.currentGame("Rf1");
		assertTrue(game.getBoard().getSpot(ROW_1, COL_F).getPiece().isMoved());
		assertTrue(game.getBoard().getSpot(ROW_1, COL_E).isEmpty());

		//test cattura Re
		game.currentGame("e2");
		game.currentGame("Rxe2");
		assertTrue(game.getBoard().getSpot(ROW_2, COL_E).getPiece() instanceof King);
		assertTrue(game.getBoard().getSpot(ROW_2, COL_E).getPiece().isWhite());
		assertTrue(game.getBoard().getSpot(ROW_1, COL_F).isEmpty());
	}

	@Test
	void testAmbiguityRook() {

		//(inizializzazione pezzi per test)
		newBoard.getSpot(ROW_1, COL_D).setPiece(new Rook(WHITE));
		newBoard.getSpot(ROW_5, COL_H).setPiece(new Rook(WHITE));
		newBoard.getSpot(ROW_6, COL_F).setPiece(new Pawn(BLACK));
		newBoard.recalLegalMoves();
		game.setBoard(newBoard);

		//test ambiguita' movimento torre
		game.currentGame("Tdd5");
		assertTrue(game.getBoard().getSpot(ROW_5, COL_D).getPiece() instanceof Rook);
		assertTrue(game.getBoard().getSpot(ROW_1, COL_D).isEmpty());
		assertTrue(game.getBoard().getSpot(ROW_5, COL_H).getPiece() instanceof Rook);

		//test ambiguita' cattura torre
		game.currentGame("f5");
		game.currentGame("Tdxf5");
		assertTrue(game.getBoard().getSpot(ROW_5, COL_F).getPiece() instanceof Rook);
		assertTrue(game.getBoard().getSpot(ROW_5, COL_F).getPiece().isWhite());
		assertTrue(game.getBoard().getSpot(ROW_5, COL_D).isEmpty());
		assertTrue(game.getBoard().getSpot(ROW_5, COL_H).getPiece() instanceof Rook);
	}

	/**Post-condizioni:
	 * 	- non devono esserci pezzi killed sulla scacchiera
	 * 	- i booleani di en passant devono essere resettati correttamente
	 *  - il pezzo considerato deve essere settato come mosso
	 *  -
	 */
	@AfterEach
	void tearDown() {
		for (int i = 0; i < DIM_BOARD; i++) {
			for (int j = 0; j < DIM_BOARD; j++) {
				Spot currentSpot = game.getBoard().getSpot(i, j);

				if (!currentSpot.isEmpty()) {
					// controllo che tutti i pezzi sulla scacchiera non siano contrassegnati come killed
					assertFalse(currentSpot.getPiece().isKilled());
					if (currentSpot.getPiece() instanceof Pawn
							&& currentSpot.getPiece().isWhite() == game.isWhiteTurn()) {
						// controllo i booleani di en passant della fazione del prossimo turno
						assertFalse(((Pawn) game.getBoard().getSpot(i, j).getPiece()).isPossibleEnPassantCapture());
					}
				}
			}
		}
	}
}