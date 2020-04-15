package it.uniba.main;

public class Move {
	AlgebraicNotation interpreter;	//interprete della mossa scritta in notazione algebrica abbreviata
	Spot start;	//casa di partenza
	Spot end;	//casa di arrivo
	Piece pieceMoved;	//pezzo che deve eseguire il movimento
	Piece pieceKilled;	//eventuale pezzo catturato col movimento
	boolean isAmbiguity = false;

	/**costruttore dell'oggetto Move
	 * 
	 * @param command comando da interpretare in mossa
	 */
	public Move(final String command, Game game) {	
		//TODO
	}
	
	Spot extractCoordinates(String algebraicFinalSpot) {
		return null;
	}
	
	int convertCoordinate(String coordinate) {
		return 0;
	}
	
	void findStartSpotOnBoard(Game game, Spot endSpot, String piece) {
		
	}
	
	Piece classPieceMoved (String algebraicPiece){
		return null;
	}
	
	void findCandidates(Game game, Piece piece, Spot end) {
		
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
	
	
	
	/*Getters & Setters*/
	public AlgebraicNotation getInterpreter() {
		return interpreter;
	}

	public void setInterpreter(AlgebraicNotation interpreter) {
		this.interpreter = interpreter;
	}

	public Spot getStart() {
		return start;
	}

	public Spot getEnd() {
		return end;
	}

	public Piece getPieceMoved() {
		return pieceMoved;
	}

	public boolean isAmbiguity() {
		return isAmbiguity;
	}

	public void setAmbiguity(boolean isAmbiguity) {
		this.isAmbiguity = isAmbiguity;
	}
}
