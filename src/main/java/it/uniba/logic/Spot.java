package it.uniba.logic;

/**
 * <body>
 * <h2>DESCRIZIONE</h2>
 * Rappresenta una casella della scacchiera. <br>
 * Essa e' composta da una coordinata X e una Y. <br>
 * Tali coordinate corrispondono ai numeri da 1 a 8 (per la X) <br>
 * e lettere da 'a' ad 'h' (per la Y). <br>
 *
 * <h2>RESPONSABILITA' DI CLASSE</h2>
 * Si occupa di verificare se su di una casella e' presente o meno un pezzo <br>
 * e controlla se la casa corrente e' sotto attacco da pezzi avversari <br>
 *
 * <h2>CLASSIFICAZIONE ECB</h2>
 * <strong>Entity</strong><br>
 * E' un componente formante della tavola da gioco <br>
 * e dunque deriva dal concetto concreto di "Scacchiera" <br>
 * </body>
 *
 * @author wilkinson
 */
public class Spot {
	private Piece piece; // pezzo che la occupa
	private int x; // coordinata riga
	private int y; // coordinata colonna

	/**
	 * costruttore di Spot (che rappresenta una casella della scacchiera)
	 *
	 * @param abscissa coordinata della riga (corrispondente ad un numero)
	 * @param ordinate coordinata della colonna (corrispondente ad una lettera)
	 * @param chessman pezzo nello spot
	 */
	public Spot(final int abscissa, final int ordinate, final Piece chessman) {
		this.x = abscissa;
		this.y = ordinate;
		this.piece = chessman;
	}

	/**
	 * costruttore di uno spot generico (per confronti tra spot)
	 *
	 * @param abscissa
	 * @param ordinate
	 */
	public Spot(final int abscissa, final int ordinate) {
		this.x = abscissa;
		this.y = ordinate;
	}

	static final int BOARDDIM = 8;
	static final int CASE_0 = 0;
	static final int CASE_1 = 1;
	static final int CASE_2 = 2;
	static final int CASE_3 = 3;
	static final int CASE_4 = 4;
	static final int CASE_5 = 5;
	static final int CASE_6 = 6;
	static final int CASE_7 = 7;

	/**
	 * controlla se lo spot corrente vuoto
	 *
	 * @return
	 */
	public boolean isEmpty() {
		if (getPiece() == null) {
			return true;
		}
		return false;
	}

	/**
	 * controllo se la casa examined e' sotto attacco da pezzi avversari
	 *
	 * @param board    scacchiera attuale
	 * @param color    colore del pezzo amico
	 * @return true se lo Spot corrente e' sotto attacco, false altrimenti
	 */
	public boolean isUnderAttack(final Board board, final boolean color) {
		Move examinedMove = new Move(null, this);
		for (int i = 0; i < BOARDDIM; i++) {
			for (int j = 0; j < BOARDDIM; j++) {
				Spot currentSpot = board.getSpot(i, j);
				if (!currentSpot.isEmpty() && currentSpot.getPiece().isWhite() != color) {
					for (Move currentMove : currentSpot.getPiece().getLegalMoves()) {
						if (currentSpot.getPiece() instanceof Pawn) {
							// caso speciale:
							// un pedone di fronte che catturerebbe in diagonale
							if (board.isFrontDiagonal(currentSpot, examinedMove.getEnd())) {
								return true;
							}
							// pezzo avversario che minaccia la casa
						} else if (currentMove.sameEnd(examinedMove)) {
							return true;
						}
					}
				}
			}
		}
		return false;
	}

	// Getters & Setters

	/**
	 * @return piece pezzo corrente
	 */
	public Piece getPiece() {
		return this.piece;
	}

	/**
	 * @return x ascissa di Spot
	 */
	public int getX() {
		return x;
	}

	/**
	 * @return y ordinata di Spot
	 */
	public int getY() {
		return y;
	}

	/**
	 * @param chessman modifica il pezzo corrente
	 */
	public void setPiece(final Piece chessman) {
		this.piece = chessman;
	}
}
