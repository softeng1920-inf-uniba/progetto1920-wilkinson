package it.uniba.main;

/**
 * rappresenta una regina sulla scacchiera
 * 
 * @author wilkinson
 *
 */
public class Queen extends Piece {

	public Queen(final boolean white) {
		super(white);
	}

	@Override
	public String draw() {
		if (isWhite()) {
			return "\u2655";
		} else {
			return "\u265b";
		}
	}

	@Override
	boolean canMove(final Board board, final Spot start, final Spot end) {
		if (board.isFreePath(start, end)) {
			return true;
		}
		return false;
	}
}
