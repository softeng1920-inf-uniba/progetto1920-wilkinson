package it.uniba.main;

import java.util.ArrayList;

/**rappresenta un re sulla scacchiera
 * 
 * @author wilkinson
 *
 */
public class King extends Piece {

	public King(boolean white) {
		super(white);
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
		if (board.isSpotAround(start, end)) {
			if (end.isEmpty()) {
				return true;
			} else if (this.isWhite() != end.getPiece().isWhite()) {
				return true;
			}
		}
		return false;
	}
	
	void recalculateMoves(Board board) {
		ArrayList<Move> movesToRemove = new ArrayList <Move>();
		for (int i = 0; i < 8; i++) {
			for (int j = 0; j < 8; j++) {
				Spot currentSpot = board.getSpot(i, j);
				if (!currentSpot.isEmpty()) {
					if (currentSpot.getPiece().isWhite() != this.isWhite()) {
						for (Move pieceMove: currentSpot.getPiece().getLegalMoves()) {
							for (Move kingMove: this.getLegalMoves()) {
								if (pieceMove.sameEnd(kingMove)) {
									movesToRemove.add(kingMove);
								}
							}
						}
					}
				}
			}
			this.getLegalMoves().removeAll(movesToRemove);
		}
	}
}