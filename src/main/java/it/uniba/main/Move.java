package it.uniba.main;

public class Move {
	AlgebraicNotation interpreter;	//interprete della mossa scritta in notazione algebrica abbreviata
	Spot start;	//casa di partenza
	Spot end;	//casa di arrivo
	Piece pieceMoved;	//pezzo che deve eseguire il movimento
	Piece pieceKilled;	//eventuale pezzo catturato col movimento

	/**costruttore dell'oggetto Move
	 * 
	 * @param command comando da interpretare in mossa
	 */
	public Move(String command) {
		//TODO
	}
	
	/**enumerazione dello stato di gioco (per verificare se la partita è ancora in corso)
	 * 
	 * @author wilkinson
	 *
	 */
	enum GameStatus{
		ACTIVE,
		BLACK_WIN,
		WHITE_WIN,
		DRAW,
		FORCED_END
	}
}
