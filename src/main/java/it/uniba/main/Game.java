package it.uniba.main;

import java.util.ArrayList;

import it.uniba.main.Move.GameStatus;

public class Game {
	Board board;	//oggetto scacchiera per la partita in corso
	boolean whiteTurn = true;	//true se turno del bianco, false se turno del nero
	GameStatus status;	//stato della partita (se ACTIVE si continua a giocare, altrimenti si quitta)
	ArrayList<Move> whiteMoves;	//lista con le mosse effettuate dal bianco
	ArrayList<Move> blackMoves;	//lista con le mosse effettuate dal nero
	ArrayList<Piece> whiteCaptures;	//lista con i pezzi catturati dal bianco (quindi pezzi neri)
	ArrayList<Piece> blackCaptures;	//lista con i pezzi catturati dal nero (quindi pezzi bianchi)
	
	/**metodo che pone inizio alla partita (inizializzando la board, settando ad ACTIVE lo status, ecc
	 * 
	 */
	void initialize() {
		//TODO
	}
	
	/**controlla se la partita è ancora in corso
	 * 
	 * @return true se status è diverso da ACTIVE
	 */
	public boolean isEnd() {
		//TODO
		return false;
	}
	
	/**metodo che effettua la mossa
	 * tiene conto di:
	 * 		- che pezzo si deve muovere?
	 * 		- la mossa è valida?
	 * 		- c'è una cattura?
	 * 		- la mossa è un arrocco?
	 * 		- la mossa se valida va memorizzata
	 * 		- muove il pezzo da start ad end
	 * 
	 * @param move mossa da effettuare
	 * @return true se la mossa è stata effettuata, false altrimenti
	 */
	public boolean makeMove(Move move) {
		//TODO
		return false;
	}
}
