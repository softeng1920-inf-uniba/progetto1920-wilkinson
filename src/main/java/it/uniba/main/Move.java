package it.uniba.main;

public class Move {
	AlgebraicNotation interpreter;	//interprete della mossa scritta in notazione algebrica abbreviata
	Spot start;	//casa di partenza
	Spot end;	//casa di arrivo
	Piece pieceMoved;	//pezzo che deve eseguire il movimento
	Piece pieceKilled;	//eventuale pezzo catturato col movimento

	/**costruttore dell'oggetto Move
	 * 
	 * @param start casa di partenza
	 * @param end casa di arrivo
	 */
	public Move(Spot start, Spot end) {
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
