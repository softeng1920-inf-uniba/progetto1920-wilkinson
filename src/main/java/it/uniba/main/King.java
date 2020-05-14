package it.uniba.main;

/**
 * DESCRIZIONE
 * rappresenta un pezzo re
 *
 * RESPONSABILITA' DI CLASSE
 * calcola le mosse legali di un re seguendo le regole
 * ufficiale degli scacchi e le ricalcola limitando il movimento del re tenendo
 * conto delle minacce avversarie
 *
 * CLASSIFICAZIONE ECB
 * <<Entity>> poiche' eredita dalla classe Piece.java
 *
 * @author wilkinson
 */
public class King extends Piece {

	public King(final boolean white) {
		super(white);
	}

	/**
	 * Metodo per ottenere l'unicode del re in base al suo colore (bianco o nero)
	 */
	@Override
	public String draw() {
		if (isWhite()) {
			return "\u2654";
		} else {
			return "\u265a";
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
	public boolean canMove(final Board board, final Spot start, final Spot end) {
		if (board.isSpotAround(start, end)) {
			if (end.isEmpty()) {
				return true;
			} else if (this.isWhite() != end.getPiece().isWhite()) {
				return true;
			}
		}
		return false;
	}
}
