package it.uniba.main;

/**
 * rappresenta una casa della scacchiera ha una coordinata X e una Y
 * corrispondenti a numeri (da 1 a 8) e lettere (da 'a' ad 'h')
 * 
 * @author pierpaolo
 *
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
	public Spot(final int x, final int y, final Piece piece) {
		this.x = x;
		this.y = y;
		this.piece = piece;
	}

	/**
	 * costruttore di uno spot generico (per confronti tra spot)
	 * 
	 * @param x
	 * @param y
	 */
	public Spot(final int x, final int y) {
		this.x = x;
		this.y = y;
	}

	/**
	 * controlla se lo spot corrente è vuoto
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
		for (int i = 0; i < 8; i++) {
			for (int j = 0; j < 8; j++) {
				Spot currentSpot = board.getSpot(i, j);
				if (!currentSpot.isEmpty() && currentSpot.getPiece().isWhite() != color) {
					for (Move currentMove : currentSpot.getPiece().getLegalMoves()) {
						if (currentSpot.getPiece() instanceof Pawn) {
							// caso in cui ci sia un pedone di fronte che catturerebbe in diagonale
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
		case 0:
			return "a";
		case 1:
			return "b";
		case 2:
			return "c";
		case 3:
			return "d";
		case 4:
			return "e";
		case 5:
			return "f";
		case 6:
			return "g";
		case 7:
			return "h";
		default:
			return null;
		}
	}

	// Getters & Setters
	public Piece getPiece() {
		return this.piece;
	}

	public int getX() {
		return x;
	}

	public void setX(int x) {
		this.x = x;
	}

	public int getY() {
		return y;
	}

	public void setY(int y) {
		this.y = y;
	}

	public void setPiece(Piece piece) {
		this.piece = piece;
	}

	public String toString() {
		String output = "";
		return output += convertCoordinate(y) + (8 - x);
	}
}
