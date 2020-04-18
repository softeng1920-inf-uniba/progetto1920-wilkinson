package it.uniba.main;

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
	boolean canMove(Board board, Spot start, Spot end) {
		return false;
	}
}