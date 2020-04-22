package it.uniba.main;

public class Pawn extends Piece {
	private boolean possibleEnPassantCapture;
	private boolean isCapturingEnPassant;

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
	boolean canMove(Board board, Spot start, Spot end, boolean isWhiteTurn) {
		Pawn startPiece = (Pawn) start.getPiece();
		Piece endPiece = end.getPiece();
		int diffX = start.getX() - end.getX(); // differenza fra le coordinate X dei due spot
		int diffY = start.getY() - end.getY(); // differenza fra le coordinate Y dei due spot

		// turno del bianco
		if (isWhiteTurn) {
			// pezzo in start bianco
			if (startPiece.isWhite()) {
				// nessun pezzo in end
				if (endPiece == null) {
					// movimento in avanti di una casella (standard)
					if (diffY == 0 && diffX == 1) {
						return true;
						// movimento in avanti di due caselle (se prima mossa)
					} else if (diffY == 0 && diffX == 2 && !startPiece.isMoved()) {
						return true;
					}
					// stabilisce se il movimento è una cattura en passant
					if (start.getX() == 3) {
						if (Math.abs(diffY) == 1 && diffX == 1) {
							if (board.getSpot(start.getX(), end.getY()).getPiece() != null
									&& board.getSpot(start.getX(), end.getY()).getPiece() instanceof Pawn) {
								Pawn possibleCapture = (Pawn) board.getSpot(start.getX(), end.getY()).getPiece();
								if (!possibleCapture.isWhite() && possibleCapture.isPossibleEnPassantCapture()) {
									return true;
								}
							}
						}
					}
				} else {
					// pezzo in end bianco (stesso colore)
					if (endPiece.isWhite()) {
						return false;
					} else {
						// cattura del pezzo se in diagonale
						if (Math.abs(diffY) == 1 && diffX == 1) {
							return true;
						} else {
							return false;
						}
					}
				}
				// turno del bianco ma pezzo nero da muovere
			} else {
				return false;
			}

			// turno del nero
		} else {
			// pezzo in start nero
			if (!startPiece.isWhite()) {
				// nessun pezzo in end
				if (endPiece == null) {
					// movimento in avanti di una casella (standard)
					if (diffY == 0 && diffX == -1) {
						return true;
						// movimento in avanti di due caselle (se prima mossa)
					} else if (diffY == 0 && diffX == -2 && !startPiece.isMoved()) {
						return true;
					}
					// stabilisce se il movimento è una cattura en passant
					if (start.getX() == 4) {
						if (Math.abs(diffY) == 1 && diffX == -1) {
							if (board.getSpot(start.getX(), end.getY()).getPiece() != null
									&& board.getSpot(start.getX(), end.getY()).getPiece() instanceof Pawn) {
								Pawn possibleCapture = (Pawn) board.getSpot(start.getX(), end.getY()).getPiece();
								if (possibleCapture.isWhite() && possibleCapture.isPossibleEnPassantCapture()) {
									return true;
								}
							}
						}
					}
					// pezzo in end nero (stesso colore)
				} else {
					if (!endPiece.isWhite()) {
						return false;
					} else {
						// cattura del pezzo se in diagonale
						if (Math.abs(diffY) == 1 && diffX == -1) {
							return true;
						} else {
							return false;
						}
					}
				}
				// turno del nero ma pezzo bianco da muovere
			} else {
				return false;
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
