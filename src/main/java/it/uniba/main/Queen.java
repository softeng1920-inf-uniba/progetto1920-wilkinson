package it.uniba.main;

/**
 * rappresenta una regina sulla scacchiera
 * 
 * @author wilkinson
 *
 */
public class Queen extends Piece {

	public Queen(boolean white) {
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
	boolean canMove(Board board, Spot start, Spot end, boolean isWhiteTurn) {
		Queen startPiece = (Queen) start.getPiece();
		Piece endPiece = end.getPiece();
		
		if (endPiece == null) {
			if (board.isColumn(start, end) || board.isRow(start, end) || board.isDiagonal(start, end)) {
				return true;
			}
		} else if (startPiece.isWhite() != endPiece.isWhite()) {
			if (board.isColumn(start, end) || board.isRow(start, end) || board.isDiagonal(start, end)) {
				return true;
			}
			
		}
		return false;
	}
	
	
}