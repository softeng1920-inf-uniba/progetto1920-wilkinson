package it.uniba.main;

public class Move {
	private AlgebraicNotation interpreter; 		// interprete della mossa scritta in notazione algebrica abbreviata
	private Spot start; 						// casa di partenza
	private Spot end; 							// casa di arrivo
	private Piece pieceMoved; 					// pezzo che deve eseguire il movimento
	private boolean isAmbiguity = false;		//caso in cui ci sia ambiguita' di movimento
	
	/**
	 * costruttore dell'oggetto Move
	 * 
	 * @param command comando da interpretare in mossa
	 */
	public Move(final String command, Game game) {				             
		this.interpreter = new AlgebraicNotation(command); 					// Istanzio l'oggetto interpreter

		if(this.getInterpreter().isGoodMove()) {	

			this.end = extractCoordinates(interpreter.getEndSquareId());				//estrae le coordinate di arrivo

			findStartSpotOnBoard(game, this.end, interpreter.getPieceLetter(), interpreter.getEndSquareId()); //estrae le coordinate di partenza

			if(this.start != null) {
				this.pieceMoved = start.getPiece();						// prende il pezzo che si muove dallo Spot di partenza
			}
		}
	}

	/**estrae le coordinate della casa di arrivo
	 * 
	 * @param algebraicFinalSpot stringa di coordinate
	 * @return spot di arrivo instanziato come elemento di classe Spot
	 */
	Spot extractCoordinates(String algebraicFinalSpot) {
		Spot endSpot;
		if(algebraicFinalSpot.length() == 2) {	//caso in cui la stringa sia lunga 2 
			endSpot = new Spot(convertCoordinate(algebraicFinalSpot.substring(1, 2)), 
					convertCoordinate(algebraicFinalSpot.substring(0, 1)), null);
			return endSpot;
		} else if(algebraicFinalSpot.length() == 3) { //caso in cui la stringa sia lunga 3 (ambiguita')
			endSpot = new Spot(convertCoordinate(algebraicFinalSpot.substring(2, 3)), 
					convertCoordinate(algebraicFinalSpot.substring(1, 2)), null);
			setAmbiguity(true);
			return endSpot;
		}
		return null;
	}


	/**converte la coordinata in notazione matriciale
	 * 
	 * @param coordinate stringa di coordinate
	 * @return indice di riga-colonna 
	 */
	int convertCoordinate(String coordinate) {
		switch(coordinate) {
		case "a": case "8":
			return 0;
		case "b": case "7":
			return 1;
		case "c": case "6":
			return 2;
		case "d": case "5":
			return 3;
		case "e": case "4":
			return 4;
		case "f": case "3":
			return 5;
		case "g": case "2":
			return 6;
		case "h": case "1":
			return 7;
		default:
			return 8;
		}

	}


	/**ricerca lo spot di partenza nella scacchiera a partire da:
	 * 
	 * @param game.getBoard() scacchiera
	 * @param endSpot punto di arrivo
	 * @param piece lettera del pezzo da muovere
	 * @return spot di partenza
	 */
	void findStartSpotOnBoard(Game game, Spot endSpot, String piece, String algebraicFinalSpot) {
		Piece currentPiece = classPieceMoved(piece);	//tipo di pezzo da muovere (instanziato come elemento della classe

		if(isAmbiguity) {
			findCandidates(game, currentPiece, endSpot, algebraicFinalSpot);
		}
		else{
			findCandidates(game, currentPiece, endSpot, null);	//Spot candidati ad essere spot di partenza
		}
	}

	/** Intepreta dalla lettera inserita dall'utente il pezzo che vuole muovere
	 * 
	 * @param algebraicPiece
	 * @return ritorna il pezzo che si deve muovere
	 */
	Piece classPieceMoved (String algebraicPiece){
		Piece currentPiece;
		switch (algebraicPiece){          
		case "T":
			currentPiece = new Rook(true);
			break;
		case "C":
			currentPiece = new Knight(true);
			break;
		case "A":
			currentPiece = new Bishop(true);
			break;
		case "D":
			currentPiece = new Queen(true);
			break;
		case "R":
			currentPiece = new King(true);
			break;
		default :
			currentPiece = new Pawn(true);
			break;
		}
		return currentPiece;
	}

	/**Trova i candidati possibili come punto di partenza di una mossa
	 * 
	 * @param game	la partita attuale
	 * @param piece	il pezzo che deve muoversi
	 * @param end	il punto d'arrivo
	 * @param algebraicFinalSpot	casella d'arrivo in notazione algebrica
	 */
	void findCandidates(Game game, Piece piece, Spot end, String algebraicFinalSpot) {
		if(isAmbiguity){
			for(int i=0; i<8; i++){
				Spot start = game.getBoard().getSpot(i, convertCoordinate(algebraicFinalSpot.substring(0, 1) ));
				Spot end2 = game.getBoard().getSpot(end.getX(), end.getY());
				if(start.getPiece() != null && start.getPiece().canMove(game.getBoard(), start, end2) && start.getPiece().isWhite() == game.whiteTurn){
					this.start = start;
				}
			}
		}
		else{
			for(int i=0; i<8; i++) {
				for(int j=0; j<8; j++) {
					Spot start = game.getBoard().getSpot(i, j);
					Spot end2 = game.getBoard().getSpot(end.getX(), end.getY());
					if(start.getPiece() != null && start.getPiece().canMove(game.getBoard(), start, end2) && start.getPiece().isWhite() == game.whiteTurn) {
						this.start = start;
					}

				}
			}
		}

	}


	/**
	 * enumerazione dello stato di gioco (per verificare se la partita � ancora in
	 * corso)
	 * 
	 * @author wilkinson
	 *
	 */
	enum GameStatus {
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

	public void setStart(Spot start) {
		this.start = start;
	}

	public void setEnd(Spot end) {
		this.end = end;
	}

	public void setPieceMoved(Piece pieceMoved) {
		this.pieceMoved = pieceMoved;
	}
}