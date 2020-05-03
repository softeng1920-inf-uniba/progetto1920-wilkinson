package it.uniba.main;

/**
 * rappresenta un alfiere sulla scacchiera
 * 
 * @author wilkinson
 *
 */
public class Bishop extends Piece {

	public Bishop(boolean white) {
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
	boolean canMove(Board board, Spot start, Spot end) {
		if (board.isFreePath(start, end)) {
			return true;
		}
		return false;
	}
}
