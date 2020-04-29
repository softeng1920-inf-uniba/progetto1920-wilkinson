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
		// TODO Auto-generated constructor stub
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
	boolean canMove(Board board, Spot start, Spot end, boolean isWhiteTurn) {
		if (board.isFreePath(start, end)) {
			return true;
		}
		return false;
	}
}
