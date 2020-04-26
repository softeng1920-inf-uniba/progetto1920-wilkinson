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
		
		
		// turno del bianco
				if (isWhiteTurn) {
					// pezzo in start bianco
					if (startPiece.isWhite()) {
						// nessun pezzo in end
						if (endPiece == null) {
							// movimento in colonna
							if (board.isFreeColumn(start, end)) {
								return true;
								// movimento in riga
								} 
							if (board.isFreeRow(start, end)) {
								return true;
							}
							
							if(board.isFreeDiagonal(start, end)) {
								return true;
							}
					
						} else {
							// pezzo in end bianco (stesso colore)
							if (endPiece.isWhite()) {
								return false;
							}  else{
							//Caso della cattura di un pezzo nero
								if (board.isFreeColumn(start, end)) {
									return true;
									// movimento in riga
									} 
								if (board.isFreeRow(start, end)) {
									return true;
								}
								
								if(board.isFreeDiagonal(start, end)) {
									return true;
								}
							}
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
							// movimento in colonna
							if (board.isFreeColumn(start, end)) {
								return true;
								// movimento in riga
								} 
							if (board.isFreeRow(start, end)) {
								return true;
							}
							
							if(board.isFreeDiagonal(start, end)) {
								return true;
							}
					
						} else {
							// pezzo in end nero (stesso colore)
							if (!endPiece.isWhite()) {
								return false;
							} else{
							//CASO DELLA CATTURA di un pezzo bianco
								if (board.isFreeColumn(start, end)) {
									return true;
									// movimento in riga
									} 
								if (board.isFreeRow(start, end)) {
									return true;
								}
								
								if(board.isFreeDiagonal(start, end)) {
									return true;
								}
							} 
						}
					}else {
						return false;
					}
				}
				return false;
		
		/*if (endPiece == null) {
			if (board.isFreeColumn(start, end) || board.isFreeRow(start, end) || board.isFreeDiagonal(start, end)) {
				return true;
			}
		} else if (startPiece.isWhite() != endPiece.isWhite()) {
			if (board.isFreeColumn(start, end) || board.isFreeRow(start, end) || board.isFreeDiagonal(start, end)) {
				return true;
			}
			
		}
		return false;*/
	}

	
	

}