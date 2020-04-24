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
		// TODO Auto-generated constructor stub
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

		// turno del bianco
		if (isWhiteTurn) {
			// pezzo in start bianco
			if (startPiece.isWhite()) {
				// nessun pezzo in end
				if (endPiece == null) { //TODO implementare i diversi movimenti in questo if
					
					/*ESEMPIO DEI DUE MOVIMENTI STANDARD DEL PEDONE
					 * if (board.isFrontSpot(start, end)) {
						return true;
						// movimento in avanti di due caselle (se prima mossa)
					} else if (board.isTwoSpotsAhead(start, end) && !startPiece.isMoved()) {
						return true;
					}*/
					
					if(board.isColumn(start, end)) {
						return true;
					}
					if(board.isRow(start, end)) {
						return true;
					}

					// turno del bianco ma pezzo nero da muovere
				} else {
					return false;
				}

				// turno del nero
			} else {
				// pezzo in start nero
				if (!startPiece.isWhite()) {
					// nessun pezzo in end
					if (endPiece == null) {
					//TODO speculare a cio' che implementero' nel ramo del bianco
						if(board.isColumn(start, end)) {
							return true;
						}
						if(board.isRow(start, end)) {
							return true;
						}

						// turno del nero ma pezzo bianco da muovere
					} else {
						return false;
					}
				}
				return false;
			}
		}
		return false;
	}

}