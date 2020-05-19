package it.uniba.example;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.StringTokenizer;

import org.junit.jupiter.api.Test;

import it.uniba.main.Game;
import it.uniba.main.King;
import it.uniba.main.Knight;
import it.uniba.main.Pawn;
import it.uniba.main.Rook;

class GameTest {
	private static final int ROW_1= 7;
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
	private static Game game;

	/**
	 * Test delle diverse situazioni d'arrocco
	 */
	@Test
	void testEP() {
		//Cattura en passant da parte del pedone bianco in a5
		Pawn examinedPawn = null;
		game=new Game();
		StringTokenizer move= new StringTokenizer("a3 h5 a4 Cf6 Cf3 d6 d3 d5 Ad2 d4 a5 b5");
		while(move.hasMoreTokens()) {
			game.currentGame(move.nextToken());
			examinedPawn= (Pawn) (game.getBoard().getSpot(ROW_5, COL_B).getPiece());
		}

		//controllo che il pezzo  mosso di due e' catturabile en passant
		assertTrue(examinedPawn.isPossibleEnPassantCapture()); 
		//controllo che altri pedoni non catturabili en passant abbiano il booleano settato a a false
		examinedPawn= (Pawn) (game.getBoard().getSpot(ROW_2, COL_B).getPiece());
		assertFalse(examinedPawn.isPossibleEnPassantCapture());
		examinedPawn= (Pawn) (game.getBoard().getSpot(ROW_2, COL_C).getPiece());
		assertFalse(examinedPawn.isPossibleEnPassantCapture());
		examinedPawn= (Pawn) (game.getBoard().getSpot(ROW_2, COL_F).getPiece());
		assertFalse(examinedPawn.isPossibleEnPassantCapture());
		examinedPawn= (Pawn) (game.getBoard().getSpot(ROW_7, COL_C).getPiece());
		assertFalse(examinedPawn.isPossibleEnPassantCapture());
		examinedPawn= (Pawn) (game.getBoard().getSpot(ROW_7, COL_A).getPiece());
		assertFalse(examinedPawn.isPossibleEnPassantCapture());
		examinedPawn= (Pawn) (game.getBoard().getSpot(ROW_7, COL_F).getPiece());
		assertFalse(examinedPawn.isPossibleEnPassantCapture());
		//controllo che finito il turno il booleano ritorna false
		game.currentGame("h4");
		game.currentGame("h6");
		examinedPawn= (Pawn) (game.getBoard().getSpot(ROW_5, COL_B).getPiece());
		assertFalse(examinedPawn.isPossibleEnPassantCapture()); 

		//Tentativo di cattura en passant nel caso in cui il pezzo avversario si muova di uno spot
		game=new Game();
		move= new StringTokenizer("a3 h5 a4 Cf6 Cf3 d6 d3 d5 Ad2 d4 a5 b6"); //serie di mosse
		while(move.hasMoreTokens()) {
			game.currentGame(move.nextToken());
			examinedPawn= (Pawn) (game.getBoard().getSpot(ROW_6, COL_B ).getPiece());
		}
		assertFalse (examinedPawn.isPossibleEnPassantCapture());

		/**
		 * Cattura en passant da parte del pedone nero in a7
		 */
		game=new Game();
		move= new StringTokenizer("g3 e6 e3 Re7 Re2 a5 f3 a4 b4");//serie di mosse
		while(move.hasMoreTokens()) {
			game.currentGame(move.nextToken());
			examinedPawn= (Pawn) (game.getBoard().getSpot(ROW_4, COL_B).getPiece());
		}
		//controllo che il pezzo  mosso di due e' catturabile en passant
		assertTrue(examinedPawn.isPossibleEnPassantCapture()); 
		//controllo che altri pedoni non catturabili en passant abbiano il booleano settato a a false
		examinedPawn= (Pawn) (game.getBoard().getSpot(ROW_2, COL_A).getPiece());
		assertFalse(examinedPawn.isPossibleEnPassantCapture());
		examinedPawn= (Pawn) (game.getBoard().getSpot(ROW_2, COL_C).getPiece());
		assertFalse(examinedPawn.isPossibleEnPassantCapture());
		examinedPawn= (Pawn) (game.getBoard().getSpot(ROW_2, COL_H).getPiece());
		assertFalse(examinedPawn.isPossibleEnPassantCapture());
		examinedPawn= (Pawn) (game.getBoard().getSpot(ROW_7, COL_H).getPiece());
		assertFalse(examinedPawn.isPossibleEnPassantCapture());
		examinedPawn= (Pawn) (game.getBoard().getSpot(ROW_7, COL_B).getPiece());
		assertFalse(examinedPawn.isPossibleEnPassantCapture());
		examinedPawn= (Pawn) (game.getBoard().getSpot(ROW_7, COL_F).getPiece());
		assertFalse(examinedPawn.isPossibleEnPassantCapture());
		//controllo che finito il turno il booleano ritorna false
		game.currentGame("h6");
		game.currentGame("h4");
		examinedPawn= (Pawn) (game.getBoard().getSpot(ROW_4, COL_B).getPiece());
		assertFalse(examinedPawn.isPossibleEnPassantCapture()); 

		//Tentativo di cattura en passant nel caso in cui il pezzo avversario si muova di uno spot
		game=new Game();
		move= new StringTokenizer("g3 e6 e3 Re7 Re2 a5 f3 a4 b3");//serie di mosse
		while(move.hasMoreTokens()) {
			game.currentGame(move.nextToken());
			examinedPawn= (Pawn) (game.getBoard().getSpot(ROW_3, COL_B).getPiece());
		}
		assertFalse (examinedPawn.isPossibleEnPassantCapture());
	}

	/**
	 * Testo situazuoni particolari per il re
	 */
	@Test
	void testKingMovement(){
		game=new Game();
		//serie di mosse per non dare possibilita' di movimento al re bianco
		King examinedKing=null;
		StringTokenizer move= new StringTokenizer("e4 h5 g4 hxg4 Re2 a5 b4 axb4 Re3 Ta5 c3 Thh5 a3 Tad5 f3 Th3 c4 Td4 e5 Tg3 e6 Tg2 c5 Tf2 a4 b3 a5 b6 a6 bxc5 a7 g3");//serie di mosse
		while(move.hasMoreTokens()) {
			game.currentGame(move.nextToken());
		}
		examinedKing= (King) (game.getBoard().getSpot(ROW_3, COL_E).getPiece());
		assertTrue(examinedKing.getLegalMoves().isEmpty());
		//serie di mosse che permette una sola mossa legale
		game=new Game();
		examinedKing=null;
		move= new StringTokenizer("e4 h5 g4 hxg4 Re2 a5 b4 axb4 Re3 Ta5 c3 Thh5 a3 Tad5 f3 Th3 c4 Td4 e5 Tg3 e6 Tg2 c5 Tf2 a4 b3 a5 b2 a6 b6 a7 bxc5");//serie di mosse
		while(move.hasMoreTokens()) {
			game.currentGame(move.nextToken());
		}
		examinedKing= (King) (game.getBoard().getSpot(ROW_3, COL_E).getPiece());
		assertTrue(game.getBoard().getSpot(ROW_2, COL_F).getPiece() instanceof Rook);
		game.currentGame("Rxf2");
		assertTrue(game.getBoard().getSpot(ROW_2, COL_F).getPiece() instanceof King);

		game=new Game();
		//serie di mosse per non dare possibilita' di movimento al re nero
		move= new StringTokenizer("a4 b5 axb5 e5 Txa7 Re7 h4 g5 hxg5 Re6 Th5 h6 gxh6 Cc6 Txc7 Cb4 Txd7 Cf6 Tf5 Ta7 g4 Ca6 Cc3 Cb4 Ca2 Ca6 c4 Ta8 c5 Ta7 c6"); //serie di mosse
		while(move.hasMoreTokens()) {
			game.currentGame(move.nextToken());
		}
		examinedKing= (King) (game.getBoard().getSpot(ROW_6, COL_E).getPiece());
		assertTrue(examinedKing.getLegalMoves().isEmpty());
		//serie di mosse che permette una sola mossa legale
		game=new Game();
		move= new StringTokenizer("a4 b5 h4 g5 hxg5 e5 axb5 Re7 Ta6 e4 f3 exf3 b6 Re6 b7+ Re5 Td6 a6 Th4 De7 Tf4 a5 g3"); //serie di mosse
		while(move.hasMoreTokens()) {
			game.currentGame(move.nextToken());
		}
		examinedKing= (King) (game.getBoard().getSpot(ROW_5, COL_E).getPiece());
		assertTrue(game.getBoard().getSpot(ROW_6, COL_D).getPiece() instanceof Rook);
		game.currentGame("Rxd6");
		assertTrue(game.getBoard().getSpot(ROW_6, COL_D).getPiece() instanceof King);
	}


	/**
	 * Controllo eventuali situazioni in cui il movimento di un pezzo
	 * espongono il re alla cattura
	 */
	@Test
	void testKingUnderAttack(){
		King examinedKing;
		game=new Game();
		//serie di mosse per controllare che non sia possibile scoprire il re bianco
		StringTokenizer move= new StringTokenizer("e4 a5 b4 axb4 Re2 Ta5 a3 Te5 a4 f5"); //serie di mosse
		while(move.hasMoreTokens()) {
			game.currentGame(move.nextToken());
		}
		examinedKing= (King) (game.getBoard().getSpot(ROW_2, COL_E).getPiece());
		//provo ad eseguire la mossa che esporrebbe il re allo scacco
		game.currentGame("exf5");
		// verifico che il pezzo che proteggeva il re non si sia spostato
		assertTrue(game.getBoard().getSpot(ROW_4, COL_E).getPiece() instanceof Pawn);

		//serie di mosse per controllare che non sia possibile scoprire il re nero
		game=new Game();
		move= new StringTokenizer("a4 b5 axb5 e5 Ta4 Re7 Te4 Re6 f4"); //serie di mosse
		while(move.hasMoreTokens()) {
			game.currentGame(move.nextToken());
		}
		examinedKing= (King) (game.getBoard().getSpot(ROW_6, COL_E).getPiece());
		//provo ad eseguire la mossa che esporrebbe il re allo scacco
		game.currentGame("exf4");
		// verifico che il pezzo che proteggeva il re non si sia spostato
		assertTrue(game.getBoard().getSpot(ROW_5, COL_E).getPiece() instanceof Pawn);
	}

	/**
	 * test di situazioni di ambiguita: quando due pezzi possono
	 * raggiungere la stessa casa d'arrivo
	 */
	@Test
	void testIsAmbiguity(){
		game=new Game();
		//test ambiguita' movimento cavallo bianco
		StringTokenizer move= new StringTokenizer("Cc3 d6 Cf3 c6 Cg5 b6"); //serie di mosse
		while(move.hasMoreTokens()) {
			game.currentGame(move.nextToken());
		}
		//provo ad eseguire la mossa ambigua 
		game.currentGame("Cce4");
		// verifico che il pezzo che spostato sia quello giusto
		assertTrue(game.getBoard().getSpot(ROW_4, COL_E).getPiece() instanceof Knight); 
		assertTrue(game.getBoard().getSpot(ROW_5, COL_C).isEmpty());
		assertTrue(game.getBoard().getSpot(ROW_5, COL_G).getPiece() instanceof Knight);
		//test ambiguita' cattura da parte del cavallo bianco
		game=new Game();
		move= new StringTokenizer("Cc3 d5 Cf3 a6 e4 dxe4 Cg5 a5");//serie di mosse
		while(move.hasMoreTokens()) {
			game.currentGame(move.nextToken());
		}
		//provo ad eseguire la mossa ambigua 
		game.currentGame("Ccxe4");
		// verifico che il pezzo che spostato sia quello giusto
		assertTrue(game.getBoard().getSpot(ROW_4, COL_E).getPiece() instanceof Knight); 
		assertTrue(game.getBoard().getSpot(ROW_5, COL_C).isEmpty());
		assertTrue(game.getBoard().getSpot(ROW_5, COL_G).getPiece() instanceof Knight);
		//test ambiguita' movimento cavallo nero
		game=new Game();
		move= new StringTokenizer("d4 Cc6 e4 Cf6 c3 Cg4 c4"); //serie di mosse
		while(move.hasMoreTokens()) {
			game.currentGame(move.nextToken());
		}
		//provo ad eseguire la mossa ambigua 
		game.currentGame("Cce5");
		// verifico che il pezzo che spostato sia quello giusto
		assertTrue(game.getBoard().getSpot(ROW_5, COL_E).getPiece() instanceof Knight); 
		assertTrue(game.getBoard().getSpot(ROW_6, COL_C).isEmpty());
		assertTrue(game.getBoard().getSpot(ROW_4, COL_G).getPiece() instanceof Knight);
		//test ambiguita' cattura da parte del cavallo nero
		game=new Game();
		move= new StringTokenizer("e4 Cc6 e5 Cf6 b3 Cg4 b4"); //serie di mosse
		while(move.hasMoreTokens()) {
			game.currentGame(move.nextToken());
		}
		//provo ad eseguire la mossa ambigua 
		game.currentGame("Ccxe5");
		// verifico che il pezzo che spostato sia quello giusto
		assertTrue(game.getBoard().getSpot(ROW_5, COL_E).getPiece() instanceof Knight); 
		assertTrue(game.getBoard().getSpot(ROW_6, COL_C).isEmpty());
		assertTrue(game.getBoard().getSpot(ROW_4, COL_G).getPiece() instanceof Knight); 
		//test ambiguita' movimento pedone bianco, due pedoni nella stessa colonna
		game=new Game();
		move= new StringTokenizer("e4 f5 f3 d6 exf5 d5"); //serie di mosse
		while(move.hasMoreTokens()) {
			game.currentGame(move.nextToken());
		}
		//provo ad eseguire la mossa ambigua 
		game.currentGame("f6");
		// verifico che il pedone bianco spostato sia quello giusto
		assertTrue(game.getBoard().getSpot(ROW_6, COL_F).getPiece() instanceof Pawn); 
		assertTrue(game.getBoard().getSpot(ROW_5, COL_F).isEmpty());
		assertTrue(game.getBoard().getSpot(ROW_3, COL_F).getPiece() instanceof Pawn);
		//test ambiguita' cattura da parte del pedone bianco
		game=new Game();
		move= new StringTokenizer("d4 e5 f4 a6"); //serie di mosse
		while(move.hasMoreTokens()) {
			game.currentGame(move.nextToken());
		}
		//provo ad eseguire la mossa ambigua 
		game.currentGame("dxe5");
		// verifico che il pedone bianco spostato sia quello giusto
		assertTrue(game.getBoard().getSpot(ROW_5, COL_E).getPiece() instanceof Pawn); 
		assertTrue(game.getBoard().getSpot(ROW_4, COL_D).isEmpty());
		assertTrue(game.getBoard().getSpot(ROW_4, COL_F).getPiece() instanceof Pawn);
		//test ambiguita' movimento pedone nero, due pedoni nella stessa colonna
		game=new Game();
		move= new StringTokenizer("d4 e5 f3 d6 f4 exd4 f5"); //serie di mosse
		while(move.hasMoreTokens()) {
			game.currentGame(move.nextToken());
		}
		//provo ad eseguire la mossa ambigua 
		game.currentGame("d3");
		// verifico che il pedone nero spostato sia quello giusto
		assertTrue(game.getBoard().getSpot(ROW_3, COL_D).getPiece() instanceof Pawn); 
		assertTrue(game.getBoard().getSpot(ROW_4, COL_D).isEmpty());
		assertTrue(game.getBoard().getSpot(ROW_6, COL_D).getPiece() instanceof Pawn);
		//test ambiguita' cattura da parte del pedone nero
		game=new Game();
		move= new StringTokenizer("d4 c5 a3 e5 a4"); //serie di mosse
		while(move.hasMoreTokens()) {
			game.currentGame(move.nextToken());
		}
		//provo ad eseguire la mossa ambigua 
		game.currentGame("cxd4");
		// verifico che il pedone nero spostato sia quello giusto
		assertTrue(game.getBoard().getSpot(ROW_4, COL_D).getPiece() instanceof Pawn); 
		assertTrue(game.getBoard().getSpot(ROW_5, COL_C).isEmpty());
		assertTrue(game.getBoard().getSpot(ROW_5, COL_E).getPiece() instanceof Pawn);
	}
}