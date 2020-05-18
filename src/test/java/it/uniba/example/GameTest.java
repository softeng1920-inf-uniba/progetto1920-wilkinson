package it.uniba.example;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.StringTokenizer;

import org.junit.jupiter.api.Test;

import it.uniba.main.Game;
import it.uniba.main.King;
import it.uniba.main.Pawn;
import it.uniba.main.Rook;




class GameTest {

	private static Game game;

	/**
	 * Test delle diverse situazioni d'arrocco
	 */
	@Test
	void testInitialize() {
		//Cattura en passant da parte del pedone bianco in a5
		Pawn examinedPawn = null;
		game=new Game();
		String command=("a3 h5 a4 Cf6 Cf3 d6 d3 d5 Ad2 d4 a5 b5 "); //serie di mosse
		StringTokenizer move= new StringTokenizer(command);
		while(move.hasMoreTokens()) {
			game.currentGame(move.nextToken());
			examinedPawn= (Pawn) (game.getBoard().getSpot(3, 1).getPiece());
			game.getBoard().showBoard();
		}

		//controllo che il pezzo  mosso di due e' catturabile en passant
		assertTrue(examinedPawn.isPossibleEnPassantCapture()); 
		//controllo che altri pedoni non catturabili en passant abbiano il booleano settato a a false
		examinedPawn= (Pawn) (game.getBoard().getSpot(6, 1).getPiece());
		assertFalse(examinedPawn.isPossibleEnPassantCapture());
		examinedPawn= (Pawn) (game.getBoard().getSpot(6, 2).getPiece());
		assertFalse(examinedPawn.isPossibleEnPassantCapture());
		examinedPawn= (Pawn) (game.getBoard().getSpot(6, 5).getPiece());
		assertFalse(examinedPawn.isPossibleEnPassantCapture());
		examinedPawn= (Pawn) (game.getBoard().getSpot(1, 2).getPiece());
		assertFalse(examinedPawn.isPossibleEnPassantCapture());
		examinedPawn= (Pawn) (game.getBoard().getSpot(1, 0).getPiece());
		assertFalse(examinedPawn.isPossibleEnPassantCapture());
		examinedPawn= (Pawn) (game.getBoard().getSpot(1, 6).getPiece());
		assertFalse(examinedPawn.isPossibleEnPassantCapture());
		//controllo che finito il turno il booleano ritorna false
		command="h4";
		game.currentGame(command);
		command="h6";
		game.currentGame(command);
		examinedPawn= (Pawn) (game.getBoard().getSpot(3, 1).getPiece());
		assertFalse(examinedPawn.isPossibleEnPassantCapture()); 

		//Tentativo di cattura en passant nel caso in cui il pezzo avversario si muova di uno spot
		game=new Game();
		command=("a3 h5 a4 Cf6 Cf3 d6 d3 d5 Ad2 d4 a5 b6");
		move= new StringTokenizer(command);
		while(move.hasMoreTokens()) {
			game.currentGame(move.nextToken());
			examinedPawn= (Pawn) (game.getBoard().getSpot(2, 1).getPiece());
		}
		assertFalse (examinedPawn.isPossibleEnPassantCapture());


		/**
		 * Cattura en passant da parte del pedone nero in a7
		 * 
		 */
		game=new Game();
		command=("g3 e6 e3 Re7 Re2 a5 f3 a4 b4"); //serie di mosse
		move= new StringTokenizer(command);
		while(move.hasMoreTokens()) {
			game.currentGame(move.nextToken());
			examinedPawn= (Pawn) (game.getBoard().getSpot(4, 1).getPiece());
		}
		//controllo che il pezzo  mosso di due e' catturabile en passant
		assertTrue(examinedPawn.isPossibleEnPassantCapture()); 
		//controllo che altri pedoni non catturabili en passant abbiano il booleano settato a a false
		examinedPawn= (Pawn) (game.getBoard().getSpot(6, 0).getPiece());
		assertFalse(examinedPawn.isPossibleEnPassantCapture());
		examinedPawn= (Pawn) (game.getBoard().getSpot(6, 2).getPiece());
		assertFalse(examinedPawn.isPossibleEnPassantCapture());
		examinedPawn= (Pawn) (game.getBoard().getSpot(6, 7).getPiece());
		assertFalse(examinedPawn.isPossibleEnPassantCapture());
		examinedPawn= (Pawn) (game.getBoard().getSpot(1, 7).getPiece());
		assertFalse(examinedPawn.isPossibleEnPassantCapture());
		examinedPawn= (Pawn) (game.getBoard().getSpot(1, 1).getPiece());
		assertFalse(examinedPawn.isPossibleEnPassantCapture());
		examinedPawn= (Pawn) (game.getBoard().getSpot(1, 6).getPiece());
		assertFalse(examinedPawn.isPossibleEnPassantCapture());
		//controllo che finito il turno il booleano ritorna false
		command="h6";
		game.currentGame(command);
		command="h4";
		game.currentGame(command);
		examinedPawn= (Pawn) (game.getBoard().getSpot(4, 1).getPiece());
		assertFalse(examinedPawn.isPossibleEnPassantCapture()); 

		//Tentativo di cattura en passant nel caso in cui il pezzo avversario si muova di uno spot
		game=new Game();
		command=("g3 e6 e3 Re7 Re2 a5 f3 a4 b3");
		move= new StringTokenizer(command);
		while(move.hasMoreTokens()) {
			game.currentGame(move.nextToken());
			examinedPawn= (Pawn) (game.getBoard().getSpot(5, 1).getPiece());
		}
		assertFalse (examinedPawn.isPossibleEnPassantCapture());
		/**
		 * Testo situazuoni particolari per il re
		 */
		game=new Game();
		//serie di mosse per non dare possibilita' di movimento al re bianco
		King examinedKing=null;
		command=("e4 h5 g4 hxg4 Re2 a5 b4 axb4 Re3 Ta5 c3 Thh5 a3 Tad5 f3 Th3 c4 Td4 e5 Tg3 e6 Tg2 c5 Tf2 a4 b3 a5 b6 a6 bxc5 a7 g3"); //serie di mosse
		move= new StringTokenizer(command);
		while(move.hasMoreTokens()) {
			game.currentGame(move.nextToken());
		}
		examinedKing= (King) (game.getBoard().getSpot(5, 4).getPiece());
		assertTrue(examinedKing.getLegalMoves().isEmpty());
		//serie di mosse che permette una sola mossa legale
		game=new Game();
		examinedKing=null;
		command=("e4 h5 g4 hxg4 Re2 a5 b4 axb4 Re3 Ta5 c3 Thh5 a3 Tad5 f3 Th3 c4 Td4 e5 Tg3 e6 Tg2 c5 Tf2 a4 b3 a5 b2 a6 b6 a7 bxc5"); //serie di mosse
		move= new StringTokenizer(command);
		while(move.hasMoreTokens()) {
			game.currentGame(move.nextToken());
		}
		examinedKing= (King) (game.getBoard().getSpot(5, 4).getPiece());
		assertTrue(game.getBoard().getSpot(6, 5).getPiece() instanceof Rook);
		command=("Rxf2");
		game.currentGame(command);
		assertTrue(game.getBoard().getSpot(6, 5).getPiece() instanceof King);

		game=new Game();
		//serie di mosse per non dare possibilita' di movimento al re nero
		command=("a4 b5 axb5 e5 Txa7 Re7 h4 g5 hxg5 Re6 Th5 h6 gxh6 Cc6 Txc7 Cb4 Txd7 Cf6 Tf5 Ta7 g4 Ca6 Cc3 Cb4 Ca2 Ca6 c4 Ta8 c5 Ta7 c6"); //serie di mosse
		move= new StringTokenizer(command);
		while(move.hasMoreTokens()) {
			game.currentGame(move.nextToken());
		}
		examinedKing= (King) (game.getBoard().getSpot(2, 4).getPiece());
		assertTrue(examinedKing.getLegalMoves().isEmpty());
		//serie di mosse che permette una sola mossa legale
		game=new Game();
		command=("a4 b5 h4 g5 hxg5 e5 axb5 Re7 Ta6 e4 f3 exf3 b6 Re6 b7+ Re5 Td6 a6 Th4 De7 Tf4 a5 g3"); //serie di mosse
		move= new StringTokenizer(command);
		while(move.hasMoreTokens()) {
			game.currentGame(move.nextToken());
		}
		examinedKing= (King) (game.getBoard().getSpot(3, 4).getPiece());
		assertTrue(game.getBoard().getSpot(2, 3).getPiece() instanceof Rook);
		game.currentGame("Rxd6");
		assertTrue(game.getBoard().getSpot(2, 3).getPiece() instanceof King);

		/**
		 * Controllo eventuali situazioni in cui il movimento di un pezzo
		 * espongono il re alla cattura
		 */
		game=new Game();
		//serie di mosse per controllare che non sia possibile scoprire il re bianco
		command=("e4 a5 b4 axb4 Re2 Ta5 a3 Te5 a4 f5"); //serie di mosse
		move= new StringTokenizer(command);
		while(move.hasMoreTokens()) {
			game.currentGame(move.nextToken());
		}
		examinedKing= (King) (game.getBoard().getSpot(6, 4).getPiece());
		//provo ad eseguire la mossa che esporrebbe il re allo scacco
		command=("exf5");
		game.currentGame(command);
		// verifico che il pezzo che proteggeva il re non si sia spostato
		assertTrue(game.getBoard().getSpot(4, 4).getPiece() instanceof Pawn);

		//serie di mosse per controllare che non sia possibile scoprire il re nero
		game=new Game();
		command=("a4 b5 axb5 e5 Ta4 Re7 Te4 Re6 f4"); //serie di mosse
		move= new StringTokenizer(command);
		while(move.hasMoreTokens()) {
			game.currentGame(move.nextToken());
		}
		examinedKing= (King) (game.getBoard().getSpot(2, 4).getPiece());
		//provo ad eseguire la mossa che esporrebbe il re allo scacco
		command=("exf4");
		game.currentGame(command);
		// verifico che il pezzo che proteggeva il re non si sia spostato
		assertTrue(game.getBoard().getSpot(3, 4).getPiece() instanceof Pawn);
	}
}