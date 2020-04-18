
package it.uniba.main;

/**
 * Rappresenta tutti i movimenti dei pezzi
 *
 * @author wilkinson
 */
public final class Move {
	private AlgebraicNotation interpreter; // interprete della mossa scritta in notazione algebrica abbreviata
	private Spot start; // casa di partenza
	private Spot end; // casa di arrivo
	private Piece pieceMoved; // pezzo che deve eseguire il movimento
	private boolean isAmbiguity = false; // caso in cui ci sia ambiguita' di movimento

	/**
	 * costruttore dell'oggetto Move
	 * 
	 * @param command comando da interpretare in mossa
	 * @param game    partita in corso
	 */
	public Move(final String command, final Game game) {
		this.interpreter = new AlgebraicNotation(command); // Istanzio l'oggetto interpreter

		if (this.getInterpreter().isGoodMove()) {

			this.end = extractCoordinates(interpreter.getEndSquareId()); // estrae le coordinate di arrivo

			findStartSpotOnBoard(game, this.end, interpreter.getPieceLetter(), /* estrae le coordinate di partenza */
					interpreter.getEndSquareId());

			if (this.start != null) {
				this.pieceMoved = start.getPiece(); // prende il pezzo che si muove dallo Spot di partenza
			}
		}
	}

	static final int EXPECTED_COMMAND_LENGTH = 2;
	static final int EXPECTED_AMBIGUOUS_COMMAND_LENGTH = 3;
	static final int BOARD_LENGTH = 8;
	static final int BOARD_HEIGHT = 8;

	/**
	 * estrae le coordinate della casa di arrivo
	 * 
	 * @param algebraicFinalSpot stringa di coordinate
	 * @return spot di arrivo instanziato come elemento di classe Spot
	 */
	private Spot extractCoordinates(final String algebraicFinalSpot) {
		Spot endSpot;
		if (algebraicFinalSpot.length() == EXPECTED_COMMAND_LENGTH) { // caso in cui la stringa sia lunga 2
			endSpot = new Spot(convertCoordinate(algebraicFinalSpot.substring(1, 2)),
					convertCoordinate(algebraicFinalSpot.substring(0, 1)), null);
			return endSpot;
		} else if (algebraicFinalSpot.length() == EXPECTED_AMBIGUOUS_COMMAND_LENGTH) { // caso in cui la stringa sia
																						// lunga 3 (ambiguita')
			endSpot = new Spot(convertCoordinate(algebraicFinalSpot.substring(2, 3)),
					convertCoordinate(algebraicFinalSpot.substring(1, 2)), null);
			setAmbiguity(true);
			return endSpot;
		}
		return null;
	}

	/**
	 * converte la coordinata in notazione matriciale
	 * 
	 * @param coordinate stringa di coordinate
	 * @return indice di riga-colonna
	 */
	private int convertCoordinate(final String coordinate) {
		switch (coordinate) {
		case "a":
		case "8":
			return 0;
		case "b":
		case "7":
			return 1;
		case "c":
		case "6":
			return 2;
		case "d":
		case "5":
			return 3;
		case "e":
		case "4":
			return 4;
		case "f":
		case "3":
			return 5;
		case "g":
		case "2":
			return 6;
		case "h":
		case "1":
			return 7;
		default:
			return 8;
		}

	}

	/**
	 * ricerca lo spot di partenza nella scacchiera a partire da:
	 * 
	 * @param game.getBoard() scacchiera
	 * @param endSpot         punto di arrivo
	 * @param piece           lettera del pezzo da muovere
	 * @return spot di partenza
	 */
	void findStartSpotOnBoard(final Game game, final Spot endSpot, final String piece,
			final String algebraicFinalSpot) {
		Piece currentPiece = classPieceMoved(piece); // tipo di pezzo da muovere (instanziato come elemento della classe

		if (isAmbiguity) {
			findCandidates(game, currentPiece, endSpot, algebraicFinalSpot);
		} else {
			findCandidates(game, currentPiece, endSpot, null); // Spot candidati ad essere spot di partenza
		}
	}

	/**
	 * Intepreta la lettera inserita dall'utente e la converte nel pezzo mosso
	 * 
	 * @param algebraicPiece pezzo mosso in notazione algebrica
	 * @return ritorna il pezzo che si deve muovere
	 */
	private Piece classPieceMoved(final String algebraicPiece) {
		Piece currentPiece;
		switch (algebraicPiece) {
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
		default:
			currentPiece = new Pawn(true);
			break;
		}
		return currentPiece;
	}

	/**
	 * Trova i candidati possibili come punto di partenza di una mossa
	 * 
	 * @param game               la partita attuale
	 * @param piece              il pezzo che deve muoversi
	 * @param end                il punto d'arrivo
	 * @param algebraicFinalSpot casella d'arrivo in notazione algebrica
	 */
	private void findCandidates(final Game game, final Piece piece, final Spot end, final String algebraicFinalSpot) {
		if (isAmbiguity) { // se c'e' un'ambiguita' di notazione
			for (int i = 0; i < BOARD_HEIGHT; i++) {
				Spot start = game.getBoard().getSpot(i, convertCoordinate(algebraicFinalSpot.substring(0, 1)));
				Spot end2 = game.getBoard().getSpot(end.getX(), end.getY());
				if (start.getPiece() != null && start.getPiece().canMove(game.getBoard(), start, end2, game.isWhiteTurn())) {
					this.start = start;
				}
			}
		} else {
			for (int i = 0; i < BOARD_HEIGHT; i++) {
				for (int j = 0; j < BOARD_LENGTH; j++) {
					Spot start = game.getBoard().getSpot(i, j);
					Spot end2 = game.getBoard().getSpot(end.getX(), end.getY());
					if (start.getPiece() != null && start.getPiece().canMove(game.getBoard(), start, end2, game.isWhiteTurn())) {
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
		ACTIVE, BLACK_WIN, WHITE_WIN, DRAW, FORCED_END
	}

	// Getters & Setters
	public AlgebraicNotation getInterpreter() {
		return interpreter;
	}

	public void setInterpreter(final AlgebraicNotation interpreter) {
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

	public void setAmbiguity(final boolean isAmbiguity) {
		this.isAmbiguity = isAmbiguity;
	}

	public void setStart(final Spot start) {
		this.start = start;
	}

	public void setEnd(final Spot end) {
		this.end = end;
	}

	public void setPieceMoved(final Piece pieceMoved) {
		this.pieceMoved = pieceMoved;
	}
}
