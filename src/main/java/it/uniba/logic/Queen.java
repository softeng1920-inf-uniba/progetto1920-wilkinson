package it.uniba.logic;

/**
 * <body>
 * <h2>DESCRIZIONE</h2>
 * rappresenta un pezzo regina <br>
 *
 * <h2>RESPONSABILITA' DI CLASSE</h2>
 * calcola le mosse legali di una regina seguendo le <br>
 * regole ufficiale degli scacchi <br>
 *
 * <h2>CLASSIFICAZIONE ECB</h2>
 * <strong>Entity</strong><br>
 * poiche' eredita dalla classe Piece.java
 * </body>
 *
 * @author wilkinson
 */
public class Queen extends Piece {

	public Queen(final boolean white) {
		super(white);
	}

	/**
	 * Metodo per ottenere l'unicode della regina in base al suo colore (bianco o
	 * nero)
	 */
	@Override
	public String draw() {
		if (isWhite()) {
			return "\u2655";
		} else {
			return "\u265b";
		}
	}

	/**
	 * metodo che stabilisce se il pezzo puo' muoversi (la mossa e' legale)
	 *
	 * @param board scacchiera attuale
	 * @param start casa di partenza
	 * @param end   casa di arrivo
	 * @return true se movimento possibile, false se mossa illegale
	 */
	@Override
	protected boolean canMove(final Board board, final Spot start, final Spot end) {

		if (board.isFreePath(start, end)) {
			return true;
		}
		return false;
	}
}
