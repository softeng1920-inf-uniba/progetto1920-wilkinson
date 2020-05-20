package it.uniba.example;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.After;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import it.uniba.main.Move;
import it.uniba.main.Board;
import it.uniba.main.Pawn;
import it.uniba.main.Rook;
import it.uniba.main.Knight;
import it.uniba.main.Bishop;
import it.uniba.main.King;
import it.uniba.main.Queen;

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
		
		@Test
		/**
		 * Testa un comando per il movimento e cattura e.p. di un pedone bianco
		 */
		void testCommandPawnWhiteEP() {
			board.getSpot(ROW_5, COL_C).setPiece(new Pawn(WHITE));
			board.getSpot(ROW_5, COL_D).setPiece(new Pawn(BLACK));
			((Pawn) board.getSpot(ROW_5, COL_D).getPiece()).setPossibleEnPassantCapture(true);
			((Pawn) board.getSpot(ROW_5, COL_D).getPiece()).setCapturingEnPassant(true);
			board.recalLegalMoves();
			move = new Move("cxd6 e.p.", board, WHITE);
			assertTrue(move.isEnPassantMove(board));
		}
		
		@Test
		/**
		 * Testa un comando per il movimento e cattura e.p. di un pedone nero
		 */
		void testCommandPawnBlackEP() {
			board.getSpot(ROW_4, COL_B).setPiece(new Pawn(WHITE));
			board.getSpot(ROW_4, COL_C).setPiece(new Pawn(BLACK));
			((Pawn) board.getSpot(ROW_4, COL_B).getPiece()).setPossibleEnPassantCapture(true);
			((Pawn) board.getSpot(ROW_4, COL_B).getPiece()).setCapturingEnPassant(true);
			board.recalLegalMoves();
			move = new Move("cxb3 e.p.", board, BLACK);
			assertTrue(move.isEnPassantMove(board));
		}
		
		
		@Test
		/**
		 * Testa un comando per muovere una torre bianca
		 */
		void testCommandRookWhite() {
			board.getSpot(ROW_1, COL_A).setPiece(new Rook(WHITE));
			board.recalLegalMoves();
			move = new Move("Ta4", board, WHITE);
			assertNotNull(move.getStart());
		}
		
		@Test
		/**
		 * Testa un comando per muovere una torre nera
		 */
		void testCommandRookBlack() {
			board.getSpot(ROW_8, COL_A).setPiece(new Rook(BLACK));
			board.recalLegalMoves();
			move = new Move("Ta4", board, BLACK);
			assertNotNull(move.getStart());
		}
		
		@Test
		/**
		 * Testa un comando per muovere un cavallo bianco
		 */
		void testCommandKnightWhite() {
			board.getSpot(ROW_1, COL_B).setPiece(new Knight(WHITE));
			board.recalLegalMoves();
			move = new Move("Cc3", board, WHITE);
			assertNotNull(move.getStart());
		}
		
		@Test
		/**
		 * Testa un comando per muovere un cavallo nero
		 */
		void testCommandKnightBlack() {
			board.getSpot(ROW_8, COL_B).setPiece(new Knight(BLACK));
			board.recalLegalMoves();
			move = new Move("Cc6", board, BLACK);
			assertNotNull(move.getStart());
		}
		
		@Test
		/**
		 * Testa un comando per muovere un alfiere bianco
		 */
		void testCommandBishopWhite() {
			board.getSpot(ROW_1, COL_C).setPiece(new Bishop(WHITE));
			board.recalLegalMoves();
			move = new Move("Ah6", board, WHITE);
			assertNotNull(move.getStart());
		}
		
		@Test
		/**
		 * Testa un comando per muovere un alfiere nero
		 */
		void testCommandBishopBlack() {
			board.getSpot(ROW_8, COL_C).setPiece(new Bishop(BLACK));
			board.recalLegalMoves();
			move = new Move("Ah3", board, BLACK);
			assertNotNull(move.getStart());
		}
		
		@Test
		/**
		 * Testa un comando per muovere la regina bianca
		 */
		void testCommandQueenWhite() {
			board.getSpot(ROW_1, COL_D).setPiece(new Queen(WHITE));
			board.recalLegalMoves();
			move = new Move("Dd5", board, WHITE);
			assertNotNull(move.getStart());
		}
		
		@Test
		/**
		 * Testa un comando per muovere la regina nera
		 */
		void testCommandQueenBlack() {
			board.getSpot(ROW_8, COL_D).setPiece(new Queen(BLACK));
			board.recalLegalMoves();
			move = new Move("Dd5", board, BLACK);
			assertNotNull(move.getStart());
		}
		
		@Test
		/**
		 * Testa un comando per muovere il re bianco
		 */
		void testCommandKingWhite() {
			board.getSpot(ROW_1, COL_E).setPiece(new King(WHITE));
			board.recalLegalMoves();
			move = new Move("Rd2", board, WHITE);
			assertNotNull(move.getStart());
		}
		
		@Test
		/**
		 * Testa un comando per muovere il re nero
		 */
		void testCommandKingBlack() {
			board.getSpot(ROW_8, COL_E).setPiece(new King(BLACK));
			board.recalLegalMoves();
			move = new Move("Rd7", board, BLACK);
			assertNotNull(move.getStart());
		}
		
		
		
		
		@After
		void tearDown() {

		}
		
		
		
	}


