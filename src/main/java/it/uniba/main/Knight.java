package it.uniba.main;

/**
 * rappresenta un cavallo sulla scacchiera
 * 
 * @author wilkinson
 *
 */
public class Knight extends Piece {
	/**
	 * Costruttore
	 * 
	 * @param white
	 */
	public Knight(final boolean white) {
		super(white);

	}

	@Override
	public String draw() {
		if (isWhite()) {
			return "\u2658";
		} else {
			return "\u265e";
		}
	}

	@Override
	boolean canMove(final Board board, final Spot start, final Spot end) {
		Knight startPiece = (Knight) start.getPiece();
		Piece endPiece = end.getPiece();

		if (endPiece == null) {
			if (board.isLMove(start, end)) {
				return true;
			}
		} else if (startPiece.isWhite() != endPiece.isWhite()) {
			if (board.isLMove(start, end)) {
				return true;
			}
		}
		return false;
	}
}
