package it.uniba.main;

/**rappresenta un cavallo sulla scacchiera
 * 
 * @author wilkinson
 *
 */
public class Knight extends Piece {

	public Knight(boolean white) {
		super(white);
		// TODO Auto-generated constructor stub
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
	boolean canMove(Board board, Spot start, Spot end, boolean isWhiteTurn) {
		return false;
	}
}
