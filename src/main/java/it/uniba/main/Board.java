package it.uniba.main;

/**
 * rappresenta una scacchiera (matrice 8x8) ogni casa � un elemento di classe
 * Spot
 * 
 * @author wilkinson
 *
 */
public class Board {
	private Spot[][] boxes; // matrice scacchiera formata da caselle (elementi di classe Spot)
	private static final int BOARDDIM = 8; // dimensioni della scacchiera
	private static final int INITEMPTYRAW = 2; // indice di riga di partenza scacchiera iniziale vuota
	private static final int ENDEMPTYRAW = 6; // indice di riga di fine scacchiera iniziale vuota
	public static final String ANSI_RESET = "\u001B[0m";
	public static final String ANSI_BLACK = "\u001B[30m";
	public static final String ANSI_WHITE = "\u001B[37m";
	public static final String ANSI_BACKGROUND = "\u001B[46m";
	public static final String ANSI_WHITE_BACKGROUND = "\u001B[47m";

	/**
	 * costruttore di Board, inizializza la scacchiera con la configurazione
	 * iniziale usando il metodo resetBoard()
	 */
	public Board() {
		boxes = new Spot[BOARDDIM][BOARDDIM];
		this.resetBoard();
	}

	/**
	 * metodo che ritorna una casa della scacchiera identificato con (riga, colonna)
	 * 
	 * @param x riga
	 * @param y colonna
	 * @return elemento di classe Spot
	 */
	public Spot getSpot(int x, int y) {
		return boxes[x][y];
	}

	/**
	 * metodo che inizializza la scacchiera con pezzi nelle posizioni iniziali
	 */
	private void resetBoard() {

		boxes[1][0] = new Spot(1, 0, new Pawn(false));
		boxes[1][1] = new Spot(1, 1, new Pawn(false));
		boxes[1][2] = new Spot(1, 2, new Pawn(false));
		boxes[1][3] = new Spot(1, 3, new Pawn(false));
		boxes[1][4] = new Spot(1, 4, new Pawn(false));
		boxes[1][5] = new Spot(1, 5, new Pawn(false));
		boxes[1][6] = new Spot(1, 6, new Pawn(false));
		boxes[1][7] = new Spot(1, 7, new Pawn(false));
		boxes[6][0] = new Spot(6, 0, new Pawn(true));
		boxes[6][1] = new Spot(6, 1, new Pawn(true));
		boxes[6][2] = new Spot(6, 2, new Pawn(true));
		boxes[6][3] = new Spot(6, 3, new Pawn(true));
		boxes[6][4] = new Spot(6, 4, new Pawn(true));
		boxes[6][5] = new Spot(6, 5, new Pawn(true));
		boxes[6][6] = new Spot(6, 6, new Pawn(true));
		boxes[6][7] = new Spot(6, 7, new Pawn(true));
		boxes[0][0] = new Spot(0, 0, new Rook(false));
		boxes[0][7] = new Spot(0, 7, new Rook(false));
		boxes[7][0] = new Spot(7, 0, new Rook(true));
		boxes[7][7] = new Spot(7, 7, new Rook(true));
		boxes[0][1] = new Spot(0, 1, new Knight(false));
		boxes[0][6] = new Spot(0, 6, new Knight(false));
		boxes[7][1] = new Spot(7, 1, new Knight(true));
		boxes[7][6] = new Spot(7, 6, new Knight(true));
		boxes[0][2] = new Spot(0, 2, new Bishop(false));
		boxes[0][5] = new Spot(0, 5, new Bishop(false));
		boxes[7][2] = new Spot(7, 2, new Bishop(true));
		boxes[7][5] = new Spot(7, 5, new Bishop(true));
		boxes[0][3] = new Spot(0, 3, new Queen(false));
		boxes[7][3] = new Spot(7, 3, new Queen(true));
		boxes[0][4] = new Spot(0, 4, new King(false));
		boxes[7][4] = new Spot(7, 4, new King(true));

		for (int i = INITEMPTYRAW; i < ENDEMPTYRAW; i++) {
			for (int j = 0; j < BOARDDIM; j++) {
				boxes[i][j] = new Spot(i, j, null);
			}
		}

		for (int i = 0; i < BOARDDIM; i++) {
			for (int j = 0; j < BOARDDIM; j++) {
				Spot currentSpot = getSpot(i, j);
				if (!currentSpot.isEmpty()) {
					currentSpot.getPiece().findLegalMoves(this, currentSpot);
				}
			}
		}
	}

	/**
	 * stabilisce se i due spot in input sono diagonali rispetto alla direzione del
	 * pezzo [E][ ][E]... direzione giusta per i bianchi [ ][S][ ]... [E][ ][E]...
	 * direzione giusta per i neri
	 * 
	 * @param start
	 * @param end
	 * @return
	 */
	boolean isFrontDiagonal(Spot start, Spot end) {
		int diffX = (start.getX() - end.getX());
		int diffY = (start.getY() - end.getY());
		if (!start.isEmpty()) {
			if (start.getPiece().isWhite()) {
				if (diffX == 1 && Math.abs(diffY) == 1) {
					return true;
				} else {
					return false;
				}
			} else {
				if (diffX == -1 && Math.abs(diffY) == 1) {
					return true;
				} else {
					return false;
				}
			}
		}
		return false;
	}

	/**
	 * stabilisce se lo spot di arrivo � una casella avanti allo spot di partenza [
	 * ][E][ ]... direzione giusta per i bianchi [ ][S][ ]... [ ][E][ ]... direzione
	 * giusta per i neri
	 * 
	 * @param start
	 * @param end
	 * @return
	 */
	boolean isFrontSpot(Spot start, Spot end) {
		int diffX = (start.getX() - end.getX());
		int diffY = (start.getY() - end.getY());
		if (!start.isEmpty()) {
			if (start.getPiece().isWhite()) {
				if (diffX == 1 && diffY == 0) {
					return true;
				} else {
					return false;
				}
			} else {
				if (diffX == -1 && diffY == 0) {
					return true;
				} else {
					return false;
				}
			}
		}
		return false;
	}

	/**
	 * stabilisce se lo spot di arrivo � due caselle avanti allo spot di partenza [
	 * ][E][ ]... direzione giusta per i bianchi [ ][ ][ ]... [ ][S][ ]... [ ][ ][
	 * ]... [ ][E][ ]... direzione giusta per i neri
	 * 
	 * @param start
	 * @param end
	 * @return
	 */
	boolean isTwoSpotsAhead(Spot start, Spot end) {
		int diffX = (start.getX() - end.getX());
		int diffY = (start.getY() - end.getY());
		if (!start.isEmpty()) {
			if (start.getPiece().isWhite()) {
				if (diffX == 2 && diffY == 0) {
					return true;
				} else {
					return false;
				}
			} else {
				if (diffX == -2 && diffY == 0) {
					return true;
				} else {
					return false;
				}
			}
		}
		return false;
	}

	public boolean isSpotAround(Spot start, Spot end) {
		int diffX = Math.abs(start.getX() - end.getX());
		int diffY = Math.abs(start.getY() - end.getY());
		if (!start.isEmpty()) {
			if ((diffX == 0 || diffX == 1) && (diffY == 0 || diffY == 1)) {
				return true;
			}
		}
		return false;
	}

	/**
	 * stabilisce se lo spot di arrivo � in diagonale rispetto allo spot di partenza
	 * [ ][ ][ ][E]... [E][ ][E][ ]... [ ][S][ ][ ]... [E][ ][E][ ]... [ ][ ][
	 * ][E]...
	 * 
	 * @param start
	 * @param end
	 * @return
	 */
	boolean isDiagonal(Spot start, Spot end) {
		int diffX = (start.getX() - end.getX());
		int diffY = (start.getY() - end.getY());
		if (!start.isEmpty()) {
			if (Math.abs(diffX) == Math.abs(diffY)) {
				return true;
			}
		}
		return false;
	}

	/**
	 * stabilisce se lo spot di arrivo � sulla stessa colonna o riga rispetto allo
	 * spot di partenza [ ][E][ ][ ]... [ ][E][ ][ ]... [E][S][E][E]... [ ][E][ ][
	 * ]... [ ][E][ ][ ]...
	 * 
	 * @param start
	 * @param end
	 * @return
	 */
	boolean isStraight(Spot start, Spot end) {
		if (!start.isEmpty()) {
			if (start.getX() == end.getX() || start.getY() == end.getY()) {
				return true;
			}
		}
		return false;
	}

	/**
	 * controlla se il percorso dal punto di partenza a quello di arrivo � libero
	 * [S][x][x][E]... controllo su riga [x][x][ ][ ]... [x][ ][x][ ]... [x][ ][
	 * ][E]... controllo su diagonale [E][ ][ ][ ]... controllo su colonna
	 * 
	 * @param start
	 * @param end
	 * @return
	 */
	boolean isFreePath(Spot start, Spot end) {
		int startX = start.getX();
		int startY = start.getY();
		int endX = end.getX();
		int endY = end.getY();

		if (!start.isEmpty()) {
			if (isDiagonal(start, end)) {
				if (start.getPiece() instanceof Queen || start.getPiece() instanceof Bishop) {
					if (startX > endX) {
						if (startY > endY) {
							for (int i = 1; i < 8; i++) { // NW
								if ((startX - i >= 0 && startX - i < 8) && (startY - i >= 0 && startY - i < 8)) {
									Spot examined = getSpot(startX - i, startY - i);
									if (!examined.isEmpty()) {
										if (start.getPiece().isWhite() != examined.getPiece().isWhite()) {
											if (examined.equals(end)) {
												return true;
											} else {
												return false;
											}
										} else {
											return false;
										}
									}
									if (examined.equals(end)) {
										return true;
									}
								}
							}
						} else {
							// TODO NE
						}
					} else {
						if (startY > endY) {
							// TODO SW

						} else {
							// TODO SE
						}
					}
				}
			} else if (isStraight(start, end)) {
				if (start.getPiece() instanceof Queen || start.getPiece() instanceof Rook) {
					if (startX == endX) {
						if (startY > endY) {
							// TODO SX
						} else {
							// TODO DX
						}
					} else if (startY == endY) {
						if (startX > endX) {
							// TODO UP
						} else {
							// TODO DOWN
						}
					}
				}
			}
		}
		return false;
	}// end metodo isFreePath

	/**
	 * metodo che permette la stampa a video della scacchiera nella configurazione
	 * attuale
	 */
	public void showBoard() {
		String orizline = "\u2550";
		String vertline = "\u2551";
		String plusline = "\u256c";

		boolean isBlack = true;

		System.out.println("\n      a   b   c   d   e   f   g   h");
		// stampa delle coordinate sulle righe

		System.out.print("    " + plusline);
		for (int i = 0; i < BOARDDIM; i++) {
			System.out.print(orizline);
			System.out.print(orizline);
			System.out.print(orizline);
			System.out.print(plusline);
		}

		System.out.println("");
		// stampa delle coordinate sulle colonne

		for (int i = 0; i < BOARDDIM; i++) {
			System.out.print("  " + (BOARDDIM - i) + " ");
			System.out.print(vertline);
			for (int j = 0; j < BOARDDIM; j++) {
				Piece piece = this.getSpot(i, j).getPiece();

				if ((i + j) % 2 != 0) {
					if (piece != null) {
						System.out.print(ANSI_BACKGROUND + ANSI_BLACK + " " + piece.draw() + " " + ANSI_RESET);
					} else {
						System.out.print(ANSI_BACKGROUND + "   " + ANSI_RESET);
					}
				} else {
					if (piece != null) {
						System.out.print(ANSI_WHITE_BACKGROUND + ANSI_BLACK + " " + piece.draw() + " " + ANSI_RESET);
					} else {
						System.out.print(ANSI_WHITE_BACKGROUND + "   " + ANSI_RESET);
					}
				}
				System.out.print(vertline);
			}
			System.out.print(" " + (BOARDDIM - i));

			System.out.print("\n" + "    " + plusline);
			for (int k = 0; k < BOARDDIM; k++) {
				System.out.print(orizline);
				System.out.print(orizline);
				System.out.print(orizline);
				System.out.print(plusline);

			}

			isBlack = !isBlack;
			System.out.println("\t");
		}
		System.out.print("      a   b   c   d   e   f   g   h\n");
	}

	/**
	 * mostra tutte le possibili mosse di ogni pezzo sulla scacchiera
	 * 
	 */
	public String toString() {
		String output = "";
		for (int i = 0; i < BOARDDIM; i++) {
			for (int j = 0; j < BOARDDIM; j++) {
				Spot currentSpot = getSpot(i, j);
				if (!currentSpot.isEmpty()) {
					output += currentSpot.getPiece() + "\n";
				}
			}
		}
		return output;
	}
}
