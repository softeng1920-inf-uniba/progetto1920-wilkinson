
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
	private String ambiguity; // primo carattere di un'ambiguità

	/**
	 * costruttore dell'oggetto Move
	 * 
	 * @param command comando da interpretare in mossa
	 * @param game    partita in corso
	 */
	public Move(final String command, final Game game) {
		this.interpreter = new AlgebraicNotation(command); // Istanzio l'oggetto interpreter

		if (this.isCastle()) { // se è un arrocco dò le coordinate di partenza del re
			if (game.isWhiteTurn()) {
				this.start = new Spot(7, 4);
			} else {
				this.start = new Spot(0, 4);
			}
		}

		if (this.getInterpreter().isGoodMove() && !this.isCastle()) {
			// estrae le coordinate di arrivo del pezzo
			this.end = extractCoordinates(interpreter.getEndSquareId());

			// individua che tipo di pezzo muovere e cerca lo spot di partenza giusto
			Piece classPiece = classPieceMoved(interpreter.getPieceLetter());
			findStartSpot(game.getBoard(), classPiece, game.isWhiteTurn());

			// se trova uno start
			if (this.start != null) {
				// avvalora il pezzo da muovere prendendolo da start
				this.pieceMoved = start.getPiece();
				// capisce se è una cattura en passant
				if (isEnPassantMove(pieceMoved, start, end, game.getBoard())) {
					((Pawn) start.getPiece()).setCapturingEnPassant(true);
				}
			}
		}
	}

	/**
	 * costruttore secondario di un oggetto Move semplice (solo spot di
	 * partenza/arrivo)
	 * 
	 * @param start
	 * @param end
	 */
	public Move(Spot start, Spot end) {
		this.start = start;
		this.end = end;
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
			ambiguity = algebraicFinalSpot.substring(0, 1);

			setAmbiguity(true);
			return endSpot;
		}
		return null;
	}

	/**
	 * trova lo spot di partenza cercando il pezzo con: 1) casa di arrivo
	 * corrispodente a quella inserita 2) controlla solo i pezzi della classe
	 * inserita dall'utente 3) controlla eventuali ambiguità
	 * 
	 * @param board
	 * @param piece
	 * @return
	 */
	boolean findStartSpot(Board board, Piece piece, boolean turn) {
		for (int i = 0; i < 8; i++) {
			for (int j = 0; j < 8; j++) {
				Spot currentSpot = board.getSpot(i, j);
				if (currentSpot.getPiece() != null
						&& currentSpot.getPiece().getClass().getName() == piece.getClass().getName()
						&& currentSpot.getPiece().isWhite() == turn) {
					for (Move currentMove : currentSpot.getPiece().getLegalMoves()) {
						if (isAmbiguity) {
							if (sameEnd(currentMove) && samePartialStart(currentMove, ambiguity)) {
								this.setStart(currentMove.getStart());
								break;
							}
						} else {
							if (sameEnd(currentMove)) {
								this.setStart(currentMove.getStart());
								break;
							}
						}
					}
				}
			}
		}
		return false;
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
	 * enumerazione dello stato di gioco (per verificare se la partita � ancora in
	 * corso)
	 * 
	 * @author wilkinson
	 *
	 */
	enum GameStatus {
		ACTIVE, BLACK_WIN, WHITE_WIN, DRAW, FORCED_END
	}

	/**
	 * capisce se il pezzo che sta muovendo sta catturando en passant
	 * 
	 * @param piece pezzo mosso
	 * @param start casa di partenza
	 * @param end   casa di arrivo
	 * @param board scacchiera
	 * @return true se cattura en passant, false altrimenti
	 */
	boolean isEnPassantMove(Piece piece, Spot start, Spot end, Board board) {
		if (piece instanceof Pawn && board.isFrontDiagonal(start, end) && end.getPiece() == null
				&& board.getSpot(start.getX(), end.getY()).getPiece() instanceof Pawn
				&& ((Pawn) board.getSpot(start.getX(), end.getY()).getPiece()).isPossibleEnPassantCapture()) {
			return true;
		}
		return false;
	}

	/**
	 * controlla se l'input è un arrocco
	 * 
	 * @return
	 */
	boolean isCastle() {
		if (this.getInterpreter().isCastleShort() || this.getInterpreter().isCastleLong()) {
			return true;
		}
		return false;
	}

	/**
	 * controlla se è possibile arroccare ed effettua l'arrocco
	 * 
	 * @param game
	 * @return
	 */
	boolean makeCastling(Game game) {
		if (game.isWhiteTurn()) {
			Spot whiteKingSpot = game.getBoard().getSpot(7, 4);
			if (this.getInterpreter().isCastleShort()) {
				// TODO arrocco corto (bianchi)
			} else if (this.getInterpreter().isCastleLong()) {
				// TODO arrocco lungo (bianchi)
			}
		} else {
			Spot blackKingSpot = game.getBoard().getSpot(0, 4);
			if (this.getInterpreter().isCastleShort()) {
				// TODO arrocco corto (neri)
			} else if (this.getInterpreter().isCastleLong()) {
				// TODO arrocco lungo (neri)
			}
		}
		return false;
	}

	/**
	 * controlla se le case fra re e torre sono sotto attacco
	 * 
	 * @param board
	 * @param kingSpot
	 * @param rookSpot
	 * @param king
	 * @return
	 */
	private boolean isPathCastleNotAttacked(Board board, Spot kingSpot, Spot rookSpot, Spot king) {
		if (king.isUnderAttack(board, king.getPiece().isWhite())
				|| kingSpot.isUnderAttack(board, king.getPiece().isWhite())
				|| rookSpot.isUnderAttack(board, king.getPiece().isWhite())) {
			return false;
		}
		return true;
	}

	/**
	 * controlla se le case fra re e torre sono libere da pezzi
	 * 
	 * @param board
	 * @param kingSpot
	 * @param rookSpot
	 * @return
	 */
	private boolean isPathCastleFree(Board board, Spot kingSpot, Spot rookSpot, Spot knightPos) {
		if (this.getInterpreter().isCastleShort()) {
			if (kingSpot.isEmpty() && rookSpot.isEmpty()) {
				return true;
			}
		} else if (this.getInterpreter().isCastleLong()) {
			if (kingSpot.isEmpty() && rookSpot.isEmpty() && knightPos.isEmpty()) {
				return true;
			}
		}
		return false;
	}

	/**
	 * confronta due oggetti di tipo Move e controlla se hanno le stesse coordinate
	 * 
	 * @param move
	 * @return true se hanno le stesse coordinate, false altrimenti
	 */
	public boolean equals(Move move) {
		if (this.getEnd().getX() == move.getEnd().getX() && this.getEnd().getY() == move.getEnd().getY()
				&& this.getStart().getX() == move.getStart().getX()
				&& this.getStart().getY() == move.getStart().getY()) {
			return true;
		}
		return false;
	}

	/**
	 * controlla se due oggetti di tipo move hanno la stessa casa di arrivo
	 * 
	 * @param move
	 * @return
	 */
	public boolean sameEnd(Move move) {
		if (this.getEnd().getX() == move.getEnd().getX() && this.getEnd().getY() == move.getEnd().getY()) {
			return true;
		} else {
			return false;
		}
	}

	/**
	 * controlla se la casa di start della mossa corrisponde parzialmente
	 * all'ambiguità
	 * 
	 * @param move
	 * @param coord
	 * @return
	 */
	public boolean samePartialStart(Move move, String coord) {
		if (Character.isDigit(coord.charAt(0))) {
			if (move.getStart().getX() == convertCoordinate(coord)) {
				return true;
			} else {
				return false;
			}
		} else {
			if (move.getStart().getY() == convertCoordinate(coord)) {
				return true;
			} else {
				return false;
			}
		}
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

	public String toString() {
		String output = "";
		return output += "[start: " + start + " end: " + end + "]";
	}

}
