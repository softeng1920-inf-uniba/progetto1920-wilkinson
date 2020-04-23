package it.uniba.main;

/**rappresenta un re sulla scacchiera
 * 
 * @author wilkinson
 *
 */
public class King extends Piece {

	public King(boolean white) {
		super(white);
		// TODO Auto-generated constructor stub
	}

	@Override
	public String draw() {
		if (isWhite()) {
			return "\u2654";
		} else {
			return "\u265a";
		}
	}

	@Override
	boolean canMove(Board board, Spot start, Spot end, boolean isWhiteTurn) {
		return false;
	}

	@Override
	void findLegalMoves(Board board, Spot currentSpot) {
		// TODO Auto-generated method stub
		
	}
}