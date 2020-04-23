package it.uniba.main;

/**rappresenta una torre sulla scacchiera
 * 
 * @author wilkinson
 *
 */
public class Rook extends Piece {

	public Rook(boolean white) {
		super(white);
		// TODO Auto-generated constructor stub
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
	boolean canMove(Board board, Spot start, Spot end, boolean isWhiteTurn) {
		return false;
	}

	@Override
	void findLegalMoves(Board board, Spot currentSpot) {
		// TODO Auto-generated method stub
		
	}
}