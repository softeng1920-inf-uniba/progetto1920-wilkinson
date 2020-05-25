package it.uniba.logic;

/**
 * <body>
 * <h2>DESCRIZIONE</h2>
 * rappresenta un pezzo re <br>
 *
 * <h2>RESPONSABILITA' DI CLASSE</h2>
 * calcola le mosse legali di un re seguendo le <br>
 * regole ufficiale degli scacchi, impedendone il movimento <br>
 * in case minacciate da pezzi avversari <br>
 *
 * <h2>CLASSIFICAZIONE ECB</h2>
 * <strong>Entity</strong><br>
 * poiche' eredita dalla classe Piece.java
 * </body>
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
	protected boolean canMove(final Board board, final Spot start, final Spot end) {
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
