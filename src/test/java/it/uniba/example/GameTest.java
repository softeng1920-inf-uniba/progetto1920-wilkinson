package it.uniba.example;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.StringTokenizer;

import org.junit.jupiter.api.Test;

import it.uniba.main.Game;
import it.uniba.main.Pawn;


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
	}
}
