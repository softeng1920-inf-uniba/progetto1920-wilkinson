package it.uniba.main;

/**
 * DESCRIZIONE
 * Rappresenta una casella della scacchiera.
 * Essa e' composta da una coordinata X e una Y.
 * Tali coordinate corrispondono ai numeri da 1 a 8 (per la X)
 * e lettere da 'a' ad 'h' (per la Y).
 *
 * RESPONSABILITA' DI CLASSE
 * Si occupa di verificare se su di una casella e' presente o meno un pezzo
 * e controlla se la casa corrente e' sotto attacco da pezzi avversari
 *
 * CLASSIFICAZIONE ECB
 * <<Entity>>
 * E' un componente formante della tavola da gioco
 * e dunque deriva dal concetto concreto di "Scacchiera"
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
	 * @param x coordinata della riga (corrispondente ad un numero)
	 * @param y coordinata della colonna (corrispondente ad una lettera)
	 */
	public Spot(final int abscissa, final int ordinate, final Piece chessman) {
		this.x = abscissa;
		this.y = ordinate;
		this.piece = chessman;
	}

	/**
	 * costruttore di uno spot generico (per confronti tra spot)
	 *
	 * @param x
	 * @param y
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
	 * controlla se lo spot corrente � vuoto
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
	 * @param examined mossa che ha come end lo spot corrente
	 * @param color    colore del pezzo amico
	 * @return true se lo Spot corrente e' sotto attacco, false altrimenti
	 */
	boolean isUnderAttack(final Board board, final boolean color) {
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

	/**
	 * converte la coordinata Y di uno spot in lettera
	 *
	 * @param coordinate
	 * @return
	 */
	private String convertCoordinate(final int coordinate) {
		switch (coordinate) {
		case CASE_0:
			return "a";
		case CASE_1:
			return "b";
		case CASE_2:
			return "c";
		case CASE_3:
			return "d";
		case CASE_4:
			return "e";
		case CASE_5:
			return "f";
		case CASE_6:
			return "g";
		case CASE_7:
			return "h";
		default:
			return null;
		}
	}
	
	@Override
	public boolean equals(Object examined) {
		if (!(examined instanceof Spot)) {
			return false;
		}
		Spot s = (Spot) examined;
		if (this.getX() == s.getX() && this.getY() == s.getY()) {
			return true;
		}
		return false;
	}
	
	private static final int HASH = 42;

	/**
	 * default hashcode (di nessuna utilita', poiche' non c'e' un Set implementato)
	 */
	@Override
	public int hashCode() {
	    assert false : "hashCode not designed";
	    return HASH; // any arbitrary constant will do
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
	 * @param abscissa ascissa di Spot da settare
	 */
	public void setX(final int abscissa) {
		this.x = abscissa;
	}

	/**
	 * @return y ordinata di Spot
	 */
	public int getY() {
		return y;
	}

	/**
	 * @param ordinate ordinata di Spot da settare
	 */
	public void setY(final int ordinate) {
		this.y = ordinate;
	}

	/**
	 * @param chessman modifica il pezzo corrente
	 */
	public void setPiece(final Piece chessman) {
		this.piece = chessman;
	}

	/**
	 * rappresenta la stringa "Riga + Colonna"
	 */
	@Override
	public String toString() {
		String output = "";
		output += convertCoordinate(y) + (BOARDDIM - x);
		return output;
	}
}
