package it.uniba.logic;

/**
 * <body>
 * <h2>DESCRIZIONE</h2>
 * rappresenta un pezzo cavallo <br>
 *
 * <h2>RESPONSABILITA' DI CLASSE</h2>
 * calcola le mosse legali di un cavallo seguendo le <br>
 * regole ufficiale degli scacchi <br>
 *
 * <h2>CLASSIFICAZIONE ECB</h2>
 * <strong>Entity</strong><br>
 * poiche' eredita dalla classe Piece.java
 * </body>
 *
 * @author wilkinson
 */
public class Knight extends Piece {

	/**
	 * Costruttore
	 *
	 * @param white
	 */
	public Knight(final boolean white) {
		super(white);
	}

	/**
	 * Metodo per ottenere l'unicode del cavallo in base al suo colore (bianco o
	 * nero)
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
	 *
	 * @return True se e' una mossa legale altrimenti False
	 */
	@Override
	protected boolean canMove(final Board board, final Spot start, final Spot end) {
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
