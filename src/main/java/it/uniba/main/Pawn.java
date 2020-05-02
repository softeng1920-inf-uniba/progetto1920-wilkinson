package it.uniba.main;

/**
 * rappresenta un pedone sulla scacchiera
 * 
 * @author wilkinson
 *
 */
public class Pawn extends Piece {
	private boolean possibleEnPassantCapture;
	private boolean isCapturingEnPassant;
	private static final int ENPASSANT_WHITE_X = 3;
	private static final int ENPASSANT_BLACK_X = 4;

	public Pawn(boolean white) {
		super(white); // chiamo il costruttore della classe astratta Piece
		possibleEnPassantCapture = false; // settato a true quando il pezzo potra' essere catturato en-passant
		isCapturingEnPassant = false; // settato a true quando il pezzo stara' catturando en-passant
	}

	@Override
	/**
	 * Metodo per ottenere l'unicode del pedone nero e del pedone bianco
	 */
	public String draw() {
		if (isWhite()) {
			return "\u2659"; // unicode del bianco
		} else {
			return "\u265f"; // unicode del nero
		}
	}

	/**
	 * metodo che verifica il possibile movimento di una o due posizioni del pedone
	 * e che verifica la possibile cattura di un pezzo avversario attraverso una
	 * cattura classica o attraverso la cattura en-passant
	 */
	@Override
	boolean canMove(Board board, Spot start, Spot end) {
		Pawn startPiece = (Pawn) start.getPiece();
		Piece endPiece = end.getPiece();

		// nessun pezzo in end
		if (endPiece == null) {
			// movimento in avanti di una casella (standard)
			if (board.isFrontSpot(start, end)) {
				return true;
				// movimento in avanti di due caselle (se prima mossa)
			} else if (board.isTwoSpotsAhead(start, end) && !startPiece.isMoved()) {
				return true;
			}
			// stabilisce se il movimento è una cattura en passant
			if (startPiece.isWhite()) {
				if (start.getX() == ENPASSANT_WHITE_X) { // per il bianco
					if (board.isFrontDiagonal(start, end)) {
						if (isCapturingEnPassant(board, start, end)) {
							return true;
						}
					}
				}
			} else {
				if (start.getX() == ENPASSANT_BLACK_X) { // per il nero
					if (board.isFrontDiagonal(start, end)) {
						if (isCapturingEnPassant(board, start, end)) {
							return true;
						}
					}
				}
			}
		} else {
			// pezzo in end dello stesso colore
			if (startPiece.isWhite() == endPiece.isWhite()) {
				return false;
			} else {
				// cattura del pezzo se in diagonale
				if (board.isFrontDiagonal(start, end)) {
					return true;
				} else {
					return false;
				}
			}
		}

		return false;
	}

	/**
	 * stabilisce se il pedone in start può catturare en passant finendo in end
	 * 
	 * @param board
	 * @param start
	 * @param end
	 * @return
	 */
	boolean isCapturingEnPassant(Board board, Spot start, Spot end) {

		if (!board.getSpot(start.getX(), end.getY()).isEmpty()
				&& board.getSpot(start.getX(), end.getY()).getPiece() instanceof Pawn) {
			Pawn possibleCapture = (Pawn) board.getSpot(start.getX(), end.getY()).getPiece();
			if (possibleCapture.isWhite() != board.getSpot(start.getX(), start.getY()).getPiece().isWhite()
					&& possibleCapture.isPossibleEnPassantCapture()) {
				return true;
			}
		}
		return false;
	}

	// Getters & Setters
	public boolean isPossibleEnPassantCapture() {
		return possibleEnPassantCapture;
	}

	public void setPossibleEnPassantCapture(boolean possibleEnPassantCapture) {
		this.possibleEnPassantCapture = possibleEnPassantCapture;
	}

	public boolean isCapturingEnPassant() {
		return isCapturingEnPassant;
	}

	public void setCapturingEnPassant(boolean isCapturingEnPassant) {
		this.isCapturingEnPassant = isCapturingEnPassant;
	}
}
