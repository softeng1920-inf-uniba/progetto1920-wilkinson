package it.uniba.example;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.*;

import it.uniba.main.Game;
import it.uniba.main.Pawn;
import it.uniba.main.Piece;
import it.uniba.main.Queen;

class GameTest {
//	private static final int ROW_1 = 7;
//	private static final int ROW_2 = 6;
	private static final int ROW_3 = 5;
	private static final int ROW_4 = 4;
	private static final int ROW_5 = 3;
//	private static final int ROW_6 = 2;
//	private static final int ROW_7 = 1;
//	private static final int ROW_8 = 0;
	
//	private static final int COL_H = 7;
//	private static final int COL_G = 6;
//	private static final int COL_F = 5;
	private static final int COL_E = 4;
	private static final int COL_D = 3;
//	private static final int COL_C = 2;
//	private static final int COL_B = 1;
	private static final int COL_A = 0;
	
	@Test
	void testCurrentGame() {
		Game game = new Game();
		
		//test isMoved pedone
		String command = "d4";
		game.currentGame(command);
		assertTrue(game.getBoard().getSpot(ROW_4, COL_D).getPiece().isMoved());
		
		//test cattura pedone
		command = "e5";
		game.currentGame(command);
		command = "dxe5";
		game.currentGame(command);
		assertTrue(game.getBoard().getSpot(ROW_5, COL_E).getPiece() instanceof Pawn);
		
		//test isMoved Regina
		command = "d5";
		game.currentGame(command);
		command = "Dxd5";
		game.currentGame(command);
		assertTrue(game.getBoard().getSpot(ROW_5, COL_D).getPiece().isMoved());

		//test cattura Regina
		assertTrue(game.getBoard().getSpot(ROW_5, COL_E).getPiece() instanceof Queen);	
	}
}