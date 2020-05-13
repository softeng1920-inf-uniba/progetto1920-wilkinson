package it.uniba.main;

/**
 * DESCRIZIONE
 * rappresenta un pezzo torre
 *
 * RESPONSABILITA' DI CLASSE
 * calcola le mosse legali di una torre seguendo le
 * regole ufficiale degli scacchi
 *
 * CLASSIFICAZIONE ECB
 * <<Entity>> poiche' eredita dalla classe Piece.java
 *
 * @author wilkinson
 */
public class Rook extends Piece {

	public Rook(final boolean white) {
		super(white);
	}

	/**
	 * Metodo per ottenere l'unicode della torre in base al suo colore (bianco o
	 * nero)
	 */
	@Override
	public String draw() {
		if (isWhite()) {
			return "\u2656";
		} else {
			return "\u265c";
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
	boolean canMove(final Board board, final Spot start, final Spot end) {
		if (board.isFreePath(start, end)) {
			return true;
		}
		return false;
	}
}
