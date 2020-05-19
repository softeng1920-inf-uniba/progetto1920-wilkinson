package it.uniba.example;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.StringTokenizer;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import it.uniba.main.Board;
import it.uniba.main.Game;
import it.uniba.main.King;
import it.uniba.main.Knight;
import it.uniba.main.Pawn;
import it.uniba.main.Piece;
import it.uniba.main.Rook;
import it.uniba.main.Spot;


class GameTest {
	private static final int DIM_BOARD = 8;
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
	private static final boolean WHITE = true;
	private static final boolean BLACK = false;
	private static Game game;
	StringTokenizer move;
	Piece piece;

	@BeforeEach
	void setUp() {
		game = new Game();
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

	// Test situazioni en passant pedone bianco 
	@Test
	void testEnPassantWhite() {
		// Cattura en passant da parte del pedone bianco
		Piece piece;
		Board newBoard =new Board(true);
		// inizializzo pedoni 
		for (int j = COL_A; j < DIM_BOARD; j++) {
			newBoard.getSpot(ROW_7, j).setPiece(new Pawn(BLACK));
			newBoard.getSpot(ROW_2, j).setPiece(new Pawn(WHITE));
		}
		newBoard.getSpot(ROW_4, COL_C).setPiece(new Pawn(WHITE));
		newBoard.getSpot(ROW_1, COL_E).setPiece(new King(WHITE));
		newBoard.getSpot(ROW_8, COL_C).setPiece(new King(BLACK));
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
		Board newBoard =new Board(true);
		// inizializzo pedoni 
		for (int j = COL_A; j < DIM_BOARD; j++) {
			newBoard.getSpot(ROW_7, j).setPiece(new Pawn(BLACK));
			newBoard.getSpot(ROW_2, j).setPiece(new Pawn(WHITE));
		}
		newBoard.getSpot(ROW_4, COL_C).setPiece(new Pawn(WHITE));
		newBoard.getSpot(ROW_1, COL_E).setPiece(new King(WHITE));
		newBoard.getSpot(ROW_8, COL_C).setPiece(new King(BLACK));
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
		Board newBoard =new Board(true);
		// inizializzo pedoni 
		for (int j = COL_A; j < DIM_BOARD; j++) {
			newBoard.getSpot(ROW_7, j).setPiece(new Pawn(BLACK));
			newBoard.getSpot(ROW_2, j).setPiece(new Pawn(WHITE));
		}
		newBoard.getSpot(ROW_4, COL_B).setPiece(new Pawn(BLACK));
		newBoard.getSpot(ROW_1, COL_E).setPiece(new King(WHITE));
		newBoard.getSpot(ROW_8, COL_C).setPiece(new King(BLACK));
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
			Board newBoard =new Board(true);
			// inizializzo pedoni 
			for (int j = COL_A; j < DIM_BOARD; j++) {
				newBoard.getSpot(ROW_7, j).setPiece(new Pawn(BLACK));
				newBoard.getSpot(ROW_2, j).setPiece(new Pawn(WHITE));
			}
			newBoard.getSpot(ROW_4, COL_A).setPiece(new Pawn(BLACK));
			newBoard.getSpot(ROW_1, COL_E).setPiece(new King(WHITE));
			newBoard.getSpot(ROW_8, COL_C).setPiece(new King(BLACK));
			newBoard.recalLegalMoves();
			game.setBoard(newBoard);
			game.currentGame("b3"); //Movimento di una sola casa 
			piece= game.getBoard().getSpot(ROW_3, COL_B).getPiece();
			// controllo che il booleano non sia stato settato a true
			assertFalse(((Pawn) piece).isPossibleEnPassantCapture());
		}

	//testo situazioni in cui il re non ha possibilità di movimento
	@Test
	void testWhiteKingNoLegalMoves() {
		// serie di mosse per non dare possibilita' di movimento al re bianco
		move = new StringTokenizer(
				"e4 h5 g4 hxg4 Re2 a5 b4 axb4 Re3 Ta5 c3 Thh5 a3 Tad5 f3 Th3 c4 Td4 e5 Tg3 e6 Tg2 c5 Tf2 a4 b3 a5 b6 a6 bxc5 a7 g3");// serie
		// di
		// mosse
		while (move.hasMoreTokens()) {
			game.currentGame(move.nextToken());
		}
		piece = (game.getBoard().getSpot(ROW_3, COL_E).getPiece());
		assertTrue(piece.getLegalMoves().isEmpty());
	}

	@Test
	void testWhiteKingOneLegalMoves() {

		// serie di mosse che permette una sola mossa legale
		move = new StringTokenizer(
				"e4 h5 g4 hxg4 Re2 a5 b4 axb4 Re3 Ta5 c3 Thh5 a3 Tad5 f3 Th3 c4 Td4 e5 Tg3 e6 Tg2 c5 Tf2 a4 b3 a5 b2 a6 b6 a7 bxc5");// serie
		// di
		// mosse
		while (move.hasMoreTokens()) {
			game.currentGame(move.nextToken());
		}
		piece = (game.getBoard().getSpot(ROW_3, COL_E).getPiece());
		assertTrue(game.getBoard().getSpot(ROW_2, COL_F).getPiece() instanceof Rook);
		game.currentGame("Rxf2");
		assertTrue(game.getBoard().getSpot(ROW_2, COL_F).getPiece() instanceof King);
	}

	@Test
	void testBlackKingNoLegalMoves() {
		// serie di mosse per non dare possibilita' di movimento al re nero
		move = new StringTokenizer(
				"a4 b5 axb5 e5 Txa7 Re7 h4 g5 hxg5 Re6 Th5 h6 gxh6 Cc6 Txc7 Cb4 Txd7 Cf6 Tf5 Ta7 g4 Ca6 Cc3 Cb4 Ca2 Ca6 c4 Ta8 c5 Ta7 c6"); // serie
		// di
		// mosse
		while (move.hasMoreTokens()) {
			game.currentGame(move.nextToken());
		}
		piece = (game.getBoard().getSpot(ROW_6, COL_E).getPiece());
		assertTrue(piece.getLegalMoves().isEmpty());
	}
	//testo situazione in cui il re nero ha solo una mossa possibile
	@Test
	void testBlackKingOneLegalMoves() {
		// serie di mosse che permette una sola mossa legale
		move = new StringTokenizer(
				"a4 b5 h4 g5 hxg5 e5 axb5 Re7 Ta6 e4 f3 exf3 b6 Re6 b7+ Re5 Td6 a6 Th4 De7 Tf4 a5 g3"); // serie di
		// mosse
		while (move.hasMoreTokens()) {
			game.currentGame(move.nextToken());
		}
		piece = (game.getBoard().getSpot(ROW_5, COL_E).getPiece());
		assertTrue(game.getBoard().getSpot(ROW_6, COL_D).getPiece() instanceof Rook);
		game.currentGame("Rxd6");
		assertTrue(game.getBoard().getSpot(ROW_6, COL_D).getPiece() instanceof King);
	}

	//test di eventuali situazioni in cui il movimento di un pezzo espongono il re bianco
	@Test
	void testWhiteKingUnderAttack() {
		// serie di mosse per controllare che non sia possibile scoprire il re bianco
		move = new StringTokenizer("e4 a5 b4 axb4 Re2 Ta5 a3 Te5 a4 f5"); // serie di mosse
		while (move.hasMoreTokens()) {
			game.currentGame(move.nextToken());
		}
		piece = (game.getBoard().getSpot(ROW_2, COL_E).getPiece());
		// provo ad eseguire la mossa che esporrebbe il re allo scacco
		game.currentGame("exf5");
		// verifico che il pezzo che proteggeva il re non si sia spostato
		assertTrue(game.getBoard().getSpot(ROW_4, COL_E).getPiece() instanceof Pawn);
	}
	//test di eventuali situazioni in cui il movimento di un pezzo espongono il re nero
	@Test
	void testBlackKingUnderAttack() {
		move = new StringTokenizer("a4 b5 axb5 e5 Ta4 Re7 Te4 Re6 f4"); // serie di mosse
		while (move.hasMoreTokens()) {
			game.currentGame(move.nextToken());
		}
		piece = (game.getBoard().getSpot(ROW_6, COL_E).getPiece());
		// provo ad eseguire la mossa che esporrebbe il re allo scacco
		game.currentGame("exf4");
		// verifico che il pezzo che proteggeva il re non si sia spostato
		assertTrue(game.getBoard().getSpot(ROW_5, COL_E).getPiece() instanceof Pawn);
	}

	// test di situazioni di ambiguita: quando due cavalli bianchi possono spostarsi nella stessa casa d'arrivo
	@Test
	void testIsAmbiguityWhiteKnight() {
		// test ambiguita' movimento cavallo bianco
		move = new StringTokenizer("Cc3 d6 Cf3 c6 Cg5 b6"); // serie di mosse
		while (move.hasMoreTokens()) {
			game.currentGame(move.nextToken());
		}
		// provo ad eseguire la mossa ambigua
		game.currentGame("Cce4");
		// verifico che il pezzo che spostato sia quello giusto
		assertTrue(game.getBoard().getSpot(ROW_4, COL_E).getPiece() instanceof Knight);
		assertTrue(game.getBoard().getSpot(ROW_5, COL_C).isEmpty());
		assertTrue(game.getBoard().getSpot(ROW_5, COL_G).getPiece() instanceof Knight);
	}
	// test ambiguita' cattura da parte del cavallo bianco

	@Test
	void testIsAmbiguityWhiteKnightCapture() {
		move = new StringTokenizer("Cc3 d5 Cf3 a6 e4 dxe4 Cg5 a5");// serie di mosse
		while (move.hasMoreTokens()) {
			game.currentGame(move.nextToken());
		}
		// provo ad eseguire la mossa ambigua
		game.currentGame("Ccxe4");
		// verifico che il pezzo spostato sia quello giusto
		assertTrue(game.getBoard().getSpot(ROW_4, COL_E).getPiece() instanceof Knight);
		assertTrue(game.getBoard().getSpot(ROW_5, COL_C).isEmpty());
		assertTrue(game.getBoard().getSpot(ROW_5, COL_G).getPiece() instanceof Knight);
	}
	// test ambiguita' movimento cavallo nero
	@Test
	void testIsAmbiguityBlackKnight() {
		move = new StringTokenizer("d4 Cc6 e4 Cf6 c3 Cg4 c4"); // serie di mosse
		while (move.hasMoreTokens()) {
			game.currentGame(move.nextToken());
		}
		// provo ad eseguire la mossa ambigua
		game.currentGame("Cce5");
		// verifico che il pezzo che spostato sia quello giusto
		assertTrue(game.getBoard().getSpot(ROW_5, COL_E).getPiece() instanceof Knight);
		assertTrue(game.getBoard().getSpot(ROW_6, COL_C).isEmpty());
		assertTrue(game.getBoard().getSpot(ROW_4, COL_G).getPiece() instanceof Knight);
	}
	// test ambiguita' movimento cavallo nero
	@Test
	void testIsAmbiguityBlackKnightCapture() {
		move = new StringTokenizer("e4 Cc6 e5 Cf6 b3 Cg4 b4"); // serie di mosse
		while (move.hasMoreTokens()) {
			game.currentGame(move.nextToken());
		}
		// provo ad eseguire la mossa ambigua
		game.currentGame("Ccxe5");
		// verifico che il pezzo che spostato sia quello giusto
		assertTrue(game.getBoard().getSpot(ROW_5, COL_E).getPiece() instanceof Knight);
		assertTrue(game.getBoard().getSpot(ROW_6, COL_C).isEmpty());
		assertTrue(game.getBoard().getSpot(ROW_4, COL_G).getPiece() instanceof Knight);
	}
	// test ambiguita' movimento pedone bianco, due pedoni nella stessa colonna
	@Test
	void testIsAmbiguityWhitePawn() {
		move = new StringTokenizer("e4 f5 f3 d6 exf5 d5"); // serie di mosse
		while (move.hasMoreTokens()) {
			game.currentGame(move.nextToken());
		}
		// provo ad eseguire la mossa ambigua
		game.currentGame("f6");
		// verifico che il pedone bianco spostato sia quello giusto
		assertTrue(game.getBoard().getSpot(ROW_6, COL_F).getPiece() instanceof Pawn);
		assertTrue(game.getBoard().getSpot(ROW_5, COL_F).isEmpty());
		assertTrue(game.getBoard().getSpot(ROW_3, COL_F).getPiece() instanceof Pawn);
	}
	// test ambiguita' cattura da parte del pedone bianco
	@Test
	void testIsAmbiguityWhitePawnCapture() {
		move = new StringTokenizer("d4 e5 f4 a6"); // serie di mosse
		while (move.hasMoreTokens()) {
			game.currentGame(move.nextToken());
		}
		// provo ad eseguire la mossa ambigua
		game.currentGame("dxe5");
		// verifico che il pedone bianco spostato sia quello giusto
		assertTrue(game.getBoard().getSpot(ROW_5, COL_E).getPiece() instanceof Pawn);
		assertTrue(game.getBoard().getSpot(ROW_4, COL_D).isEmpty());
		assertTrue(game.getBoard().getSpot(ROW_4, COL_F).getPiece() instanceof Pawn);
	}
	// test ambiguita' movimento pedone nero, due pedoni nella stessa colonna
	@Test
	void testIsAmbiguityBlackPawn() {
		move = new StringTokenizer("d4 e5 f3 d6 f4 exd4 f5"); // serie di mosse
		while (move.hasMoreTokens()) {
			game.currentGame(move.nextToken());
		}
		// provo ad eseguire la mossa ambigua
		game.currentGame("d3");
		// verifico che il pedone nero spostato sia quello giusto
		assertTrue(game.getBoard().getSpot(ROW_3, COL_D).getPiece() instanceof Pawn);
		assertTrue(game.getBoard().getSpot(ROW_4, COL_D).isEmpty());
		assertTrue(game.getBoard().getSpot(ROW_6, COL_D).getPiece() instanceof Pawn);
	}
	// test ambiguita' cattura da parte del pedone nero
	@Test
	void testIsAmbiguityBlackPawnCapture() {
		move = new StringTokenizer("d4 c5 a3 e5 a4"); // serie di mosse
		while (move.hasMoreTokens()) {
			game.currentGame(move.nextToken());
		}
		// provo ad eseguire la mossa ambigua
		game.currentGame("cxd4");
		// verifico che il pedone nero spostato sia quello giusto
		assertTrue(game.getBoard().getSpot(ROW_4, COL_D).getPiece() instanceof Pawn);
		assertTrue(game.getBoard().getSpot(ROW_5, COL_C).isEmpty());
		assertTrue(game.getBoard().getSpot(ROW_5, COL_E).getPiece() instanceof Pawn);
	}
}