package it.uniba.main;

/**
 * rappresenta una torre sulla scacchiera
 * 
 * @author wilkinson
 *
 */
public class Rook extends Piece {

	public Rook(final boolean white) {
		super(white);
	}

	@Override
	public String draw() {
		if (isWhite()) {
			return "\u2656";
		} else {
			return "\u265c";
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
