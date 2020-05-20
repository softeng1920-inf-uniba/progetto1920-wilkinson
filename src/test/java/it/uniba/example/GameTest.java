package it.uniba.example;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.*;

import it.uniba.main.Bishop;
import it.uniba.main.Board;
import it.uniba.main.Game;
import it.uniba.main.King;
import it.uniba.main.Knight;
import it.uniba.main.Pawn;
import it.uniba.main.Queen;
import it.uniba.main.Rook;

public class GameTest {
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
		game.currentGame("Rb8");
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
		newBoard.getSpot(ROW_3, COL_C).setPiece(new Pawn(BLACK));
		newBoard.recalLegalMoves();
		game.setBoard(newBoard);
		
		//test isMoved Re
		game.currentGame("Rb1");
		assertTrue(game.getBoard().getSpot(ROW_1, COL_B).getPiece().isMoved());
		assertTrue(game.getBoard().getSpot(ROW_1, COL_A).isEmpty());

		//test cattura Re
		game.currentGame("c2");
		game.currentGame("Rxc2");
		assertTrue(game.getBoard().getSpot(ROW_2, COL_C).getPiece() instanceof King);
		assertTrue(game.getBoard().getSpot(ROW_2, COL_C).getPiece().isWhite());
		assertTrue(game.getBoard().getSpot(ROW_1, COL_B).isEmpty());
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
}