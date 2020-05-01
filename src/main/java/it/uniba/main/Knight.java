package it.uniba.main;

/**
 * rappresenta un cavallo sulla scacchiera
 * 
 * @author wilkinson
 *
 */
public class Knight extends Piece {

	/**
	 * Costruttore
	 * 
	 * @param white
	 */
	public Knight(boolean white) {
		super(white);

	}

	/**
	 * Metodo per ottenere l'unicode del cavallo nero e di quello bianco
	 * 
	 */
	@Override
	public String draw() {
		if (isWhite()) {
			return "\u2658";
		} else {
			return "\u265e";
		}
	}

	/**
	 * Metodo che verifica che il movimento del cavallo, indicato dall'utente, sia
	 * possibile
	 *
	 * @param board       scacchiera attuale
	 * @param start       posizione di partenza del pezzo
	 * @param end         posizione di arrivo del pezzo
	 * @param isWhiteTurn turno del giocatore
	 *
	 * @return True se e' una mossa legale altrimenti False
	 */
	@Override
	boolean canMove(Board board, Spot start, Spot end, boolean isWhiteTurn) {
		Knight startPiece = (Knight) start.getPiece();
		Piece endPiece = end.getPiece();

		if (endPiece == null) {
			if (board.isLMove(start, end)) {
				return true;
			}
		} else if (startPiece.isWhite() != endPiece.isWhite()) {
			if (board.isLMove(start, end)) {
				return true;
			}
		}
		return false;
	}
}
