package it.uniba.main;

import java.util.ArrayList;

/**
 * rappresenta un re sulla scacchiera
 * 
 * @author wilkinson
 *
 */
public class King extends Piece {

	public King(final boolean white) {
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
	boolean canMove(final Board board, final Spot start, final Spot end) {
		if (board.isSpotAround(start, end)) {
			if (end.isEmpty()) {
				return true;
			} else if (this.isWhite() != end.getPiece().isWhite()) {
				return true;
			}
		}
		return false;
	}

	/**
	 * ricalcola le mosse del re tenendo conto delle minacce future
	 * 
	 * @param board
	 */
	void recalculateMoves(final Board board) {
		ArrayList<Move> movesCopy = new ArrayList<Move>();
		ArrayList<Move> movesToRemove = new ArrayList<Move>();
		if (!this.getLegalMoves().isEmpty()) {
			// faccio una copia dell'array di mosse lagali del re
			for (Move currentMove : this.getLegalMoves()) {
				movesCopy.add(currentMove);
			}
			// controllo se ogni casa di destinazione non sia sotto attacco
			for (Move currentMove : movesCopy) {
				if (board.kingUnderAttackNext(currentMove.getStart(), currentMove.getEnd())) {
					movesToRemove.add(currentMove);
				}
			}
			// rimuovo le mosse non valide
			this.getLegalMoves().removeAll(movesToRemove);
		}
	}
}
