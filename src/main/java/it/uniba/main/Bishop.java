package it.uniba.main;

/**
 * rappresenta un alfiere sulla scacchiera
 * 
 * @author wilkinson
 *
 */
public class Bishop extends Piece {

	public Bishop(final boolean white) {
		super(white);
	}

	@Override
	public String draw() {
		if (isWhite()) {
			return "\u2657";
		} else {
			return "\u265d";
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
