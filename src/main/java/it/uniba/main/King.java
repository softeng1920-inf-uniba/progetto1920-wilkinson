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
	public boolean canMove(Board board, Spot start, Spot end, boolean isWhiteTurn) {
		King startPiece = (King) start.getPiece();
		Piece endPiece = end.getPiece();
		if(endPiece==null) {
			if (board.isFrontSpot(start, end) || board.isFrontDiagonal(start,  end)) {
				return true;
			}
		} else if(startPiece.isWhite() != endPiece.isWhite()) {
			if (board.isFrontSpot(start, end) || board.isFrontDiagonal(start,  end)) {
				return true;
			}
		}
		return false;
	}
}