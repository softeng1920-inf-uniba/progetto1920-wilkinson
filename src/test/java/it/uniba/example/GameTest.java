package it.uniba.example;

import static org.junit.jupiter.api.Assertions.*;

import java.util.StringTokenizer;

import org.junit.jupiter.api.*;

import it.uniba.main.Bishop;
import it.uniba.main.Game;
import it.uniba.main.King;
import it.uniba.main.Knight;
import it.uniba.main.Pawn;

import it.uniba.main.Queen;
import it.uniba.main.Rook;

class GameTest {

//  RIGHE
	private static final int ROW_1 = 7;
	private static final int ROW_2 = 6;
	private static final int ROW_3 = 5;
	private static final int ROW_4 = 4;
	private static final int ROW_5 = 3;
//	private static final int ROW_6 = 2;
//	private static final int ROW_7 = 1;
//	private static final int ROW_8 = 0;

	//  COLONNE
	private static final int COL_H = 7;
	private static final int COL_G = 6;
	private static final int COL_F = 5;
	private static final int COL_E = 4;
	private static final int COL_D = 3;
	private static final int COL_C = 2;
	private static final int COL_B = 1;
	private static final int COL_A = 0;

	@Test
	void testCurrentGame() {
		Game game = new Game();

		//test isMoved pedone
		game.currentGame("d4");
		assertTrue(game.getBoard().getSpot(ROW_4, COL_D).getPiece().isMoved());
		assertTrue(game.getBoard().getSpot(ROW_2, COL_D).isEmpty());

		//test cattura pedone
		game.currentGame("e5");
		game.currentGame("dxe5");
		assertTrue(game.getBoard().getSpot(ROW_5, COL_E).getPiece() instanceof Pawn);
		assertTrue(game.getBoard().getSpot(ROW_4, COL_D).isEmpty());

		//test isMoved Regina
		game.currentGame("d5");
		game.currentGame("Dxd5");
		assertTrue(game.getBoard().getSpot(ROW_5, COL_D).getPiece().isMoved());
		assertTrue(game.getBoard().getSpot(ROW_1, COL_D).isEmpty());

		//test cattura Regina
		assertTrue(game.getBoard().getSpot(ROW_5, COL_D).getPiece() instanceof Queen);

		//test  isMoved Cavallo
		game.currentGame("h5");
		game.currentGame("Ca3");
		assertTrue(game.getBoard().getSpot(ROW_3, COL_A).getPiece().isMoved());
		assertTrue(game.getBoard().getSpot(ROW_1, COL_B).isEmpty());

		//test cattura Cavallo
		game.currentGame("b5");
		game.currentGame("Cxb5");
		assertTrue(game.getBoard().getSpot(ROW_5, COL_B).getPiece() instanceof Knight);
		assertTrue(game.getBoard().getSpot(ROW_3, COL_A).isEmpty());

		//test isMoved Alfiere
		game.currentGame("g5");
		game.currentGame("Axg5");
		assertTrue(game.getBoard().getSpot(ROW_5, COL_G).getPiece().isMoved());
		assertTrue(game.getBoard().getSpot(ROW_1, COL_C).isEmpty());

		//test cattura Alfiere
		assertTrue(game.getBoard().getSpot(ROW_5, COL_G).getPiece() instanceof Bishop);

		//test isMoved Torre
		game.currentGame("Ch6");
		game.currentGame("h4");
		game.currentGame("Ag7");
		game.currentGame("Th3");
		assertTrue(game.getBoard().getSpot(ROW_3, COL_H).getPiece().isMoved());
		assertTrue(game.getBoard().getSpot(ROW_1, COL_H).isEmpty());

		//test cattura Torre
		game.currentGame("f5");
		game.currentGame("a4");
		game.currentGame("f4");
		game.currentGame("a5");
		game.currentGame("f3");
		game.currentGame("Txf3");
		assertTrue(game.getBoard().getSpot(ROW_3, COL_F).getPiece() instanceof Rook);
		assertTrue(game.getBoard().getSpot(ROW_3, COL_H).isEmpty());
		
		//test isMoved Re
		game.currentGame("c5");
		game.currentGame("Rd2");
		assertTrue(game.getBoard().getSpot(ROW_2, COL_D).getPiece().isMoved());
		assertTrue(game.getBoard().getSpot(ROW_1, COL_E).isEmpty());

		//test cattura Re
		game.currentGame("c4");
		game.currentGame("Rc3");
		game.currentGame("Th7");
		game.currentGame("Rxc4");
		assertTrue(game.getBoard().getSpot(ROW_4, COL_C).getPiece() instanceof King);
		assertTrue(game.getBoard().getSpot(ROW_3, COL_C).isEmpty());
	}
	
	@Test
	void testIsAmbiguity() {
		Game game = new Game();

		//test ambiguita' movimento torre
		String command = ("a4 a5 Ta3 b5 axb5 Ta6 h4 h5 Tah3");
		StringTokenizer move= new StringTokenizer(command);
		while(move.hasMoreTokens()) {
			game.currentGame(move.nextToken());
		}
		assertTrue(game.getBoard().getSpot(ROW_3, COL_H).getPiece() instanceof Rook);
		assertTrue(game.getBoard().getSpot(ROW_3, COL_A).isEmpty());
		assertTrue(game.getBoard().getSpot(ROW_1, COL_H).getPiece() instanceof Rook);

		
	}
}