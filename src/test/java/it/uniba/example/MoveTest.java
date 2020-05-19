package it.uniba.example;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.After;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import it.uniba.main.Move;
import it.uniba.main.Board;
import it.uniba.main.Pawn;
import it.uniba.main.Rook;
import it.uniba.main.Spot;
import it.uniba.main.Knight;

class MoveTest {
	    private Move move;
		private Board board;
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

		@BeforeEach
		void setup(){
			board = new Board(true);
		}
		
		@Test
		/**
		 * Testa un comando senza arrocco per muovere un pezzo bianco
		 */
		void testCommandNoCastlingWhite() {
			move = new Move("a3", board, WHITE);
			assertFalse(move.isCastle());
		}
		
		@Test
		/**
		 * Testa un comando senza arrocco per muovere un pezzo nero
		 */
		void testCommandNoCastlingBlack() {
			move = new Move("a6", board, BLACK);
			assertFalse(move.isCastle());
		}
		
		@Test
		/**
		 * Testa un comando con arrocco corto per muovere un pezzo bianco
		 */
		void testCommandShortCastlingWhite() {
			move = new Move("0-0", board, WHITE);
			assertTrue(move.isCastle());
			
		}
		
		@Test
		/**
		 * Testa un comando con arrocco corto per muovere un pezzo nero
		 */
		void testCommandShortCastlingBlack() {
			move = new Move("0-0", board, BLACK);
			assertTrue(move.isCastle());
		}
		
		@Test
		/**
		 * Testa un comando con arrocco lungo per muovere un pezzo bianco
		 */
		void testCommandLongCastlingWhite() {
			move = new Move("0-0-0", board, WHITE);
			assertTrue(move.isCastle());
		}
		
		@Test
		/**
		 * Testa un comando con arrocco lungo per muovere un pezzo nero
		 */
		void testCommandLongCastlingBlack() {
			move = new Move("0-0-0", board, BLACK);
			assertTrue(move.isCastle());
		}
		
		@Test
		/**
		 * Testa un comando errato per muovere un pezzo bianco
		 */
		void testNotGoodMoveWhite() {
			move = new Move(" ", board, WHITE);
			assertFalse(move.getInterpreter().isGoodMove());
			assertFalse(move.isCastle());
		}
		
		@Test
		/**
		 * Testa un comando errato per muovere un pezzo nero
		 */
		void testNotGoodMoveBlack() {
			move = new Move(" ", board, BLACK);
			assertFalse(move.getInterpreter().isGoodMove());
			assertFalse(move.isCastle());
		}
		
		@Test
		/**
		 * Testa un comando per muovere un pedone bianco
		 */
		void testCommandPawnWhite() {
			board.getSpot(ROW_2, COL_A).setPiece(new Pawn(WHITE));
			board.recalLegalMoves();
			move = new Move("a3", board, WHITE);
			assertNotNull(move.getStart());
		}
		
		@Test
		/**
		 * Testa un comando per muovere un pedone nero
		 */
		void testCommandPawnBlack() {
			board.getSpot(ROW_7, COL_A).setPiece(new Pawn(BLACK));
			board.recalLegalMoves();
			move = new Move("a6", board, BLACK);
			assertNotNull(move.getStart());
		}
		
		@After
		void tearDown() {

		}
		
		
		
	}


