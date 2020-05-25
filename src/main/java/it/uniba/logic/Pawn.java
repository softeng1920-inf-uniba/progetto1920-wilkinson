package it.uniba.logic;

/**
 * <body>
 * <h2>DESCRIZIONE</h2>
 * rappresenta un pezzo pedone ha associati degli stati per <br>
 * permettere la cattura en passant <br>
 *
 * <h2>RESPONSABILITA' DI CLASSE</h2>
 * calcola le mosse legali di un pedone seguendo le <br>
 * regole ufficiale degli scacchi <br>
 *
 * <h2>CLASSIFICAZIONE ECB</h2>
 * <strong>Entity</strong><br>
 * poiche' eredita dalla classe Piece.java
 * </body>
 *
 * @author wilkinson
 */
public class Pawn extends Piece {
	private boolean possibleEnPassantCapture;
	private boolean isCapturingEnPassant;
	private static final int ENPASSANT_WHITE_X = 3;
	private static final int ENPASSANT_BLACK_X = 4;

	public Pawn(final boolean white) {
		super(white); // chiamo il costruttore della classe astratta Piece
		possibleEnPassantCapture = false; // settato a true quando il pezzo potra' essere catturato en-passant
		isCapturingEnPassant = false; // settato a true quando il pezzo stara' catturando en-passant
	}

	/**
	 * Metodo per ottenere l'unicode del pedone in base al suo colore (bianco o
	 * nero)
	 */
	@Override
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
	protected boolean canMove(final Board board, final Spot start, final Spot end) {
		Pawn startPiece = (Pawn) start.getPiece();
		Piece endPiece = end.getPiece();

		// nessun pezzo in end
		if (endPiece == null) {
			// movimento in avanti di una casella (standard)
			if (board.isFrontSpot(start, end)) {
				return true;
				// movimento in avanti di due caselle (se prima mossa)
			} else if (board.isTwoSpotsAhead(start, end) && !startPiece.isMoved()
					&& board.frontSpot(start).isEmpty()) {
				return true;
			}
			// stabilisce se il movimento e' una cattura en passant
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
				}
			}
		}
		return false;
	}

	/**
	 * stabilisce se il pedone in start puo' catturare en passant finendo in end
	 *
	 * @param board
	 * @param start
	 * @param end
	 * @return
	 */
	public boolean isCapturingEnPassant(final Board board, final Spot start, final Spot end) {

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
	/**
	 * Metodo che restituisce un booleano che segnala se e' possibile la cattura En
	 * Passant.
	 *
	 * @return possibleEnPassantCapture
	 */
	public boolean isPossibleEnPassantCapture() {
		return possibleEnPassantCapture;
	}

	/**
	 * Metodo che imposta sull'istanza di pedone corrente la possibilita' di
	 * catturare en passant.
	 *
	 * @param possibleEnPassantCaptureIo
	 */
	public void setPossibleEnPassantCapture(final boolean possibleEnPassantCaptureIo) {

		this.possibleEnPassantCapture = possibleEnPassantCaptureIo;
	}

	/**
	 * Metodo che restituisce un booleano se il pedone sta catturando en passant.
	 *
	 * @return isCapturingEnPassant
	 */
	public boolean isCapturingEnPassant() {
		return isCapturingEnPassant;
	}

	/**
	 * Metodo che imposta il booleano isCapturingEnPassant se il pedone sta
	 * catturando en passant.
	 *
	 * @param isCapturingEnPassantIo
	 */
	public void setCapturingEnPassant(final boolean isCapturingEnPassantIo) {
		this.isCapturingEnPassant = isCapturingEnPassantIo;
	}
}
