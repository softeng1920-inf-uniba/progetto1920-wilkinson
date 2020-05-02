package it.uniba.main;

/**
 * rappresenta una scacchiera (matrice 8x8) ogni casella e' un elemento di
 * classe Spot
 * 
 * @author wilkinson
 *
 */
public class Board {
	private Spot[][] boxes; // matrice scacchiera formata da caselle (elementi di classe Spot)
	private static final int BOARDDIM = 8; // dimensioni della scacchiera
	private static final int RAW_1 = 7;
	private static final int RAW_2 = 6;
//	private static final int RAW_3 = 5;
//	private static final int RAW_4 = 4;
//	private static final int RAW_5 = 3;
	private static final int RAW_6 = 2;
	private static final int RAW_7 = 1;
	private static final int RAW_8 = 0;
	private static final int COL_H = 7;
	private static final int COL_G = 6;
	private static final int COL_F = 5;
	private static final int COL_E = 4;
	private static final int COL_D = 3;
	private static final int COL_C = 2;
	private static final int COL_B = 1;
	private static final int COL_A = 0;
	// costanti che indicano il colore dei pezzi
	private static final boolean WHITE = true;
	private static final boolean BLACK = false;
	// costanti ANSI per background o colore font
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
		this.recalLegalMoves();
	}

	/**
	 * costruttore di una board vuota
	 * 
	 * @param empty
	 */
	public Board(final boolean empty) {
		boxes = new Spot[BOARDDIM][BOARDDIM];
		for (int i = RAW_8; i < BOARDDIM; i++) {
			for (int j = COL_A; j < BOARDDIM; j++) {
				boxes[i][j] = new Spot(i, j, null);
			}
		}
	}

	/**
	 * metodo che ritorna una casa della scacchiera identificato con (riga, colonna)
	 * 
	 * @param x riga
	 * @param y colonna
	 * @return elemento di classe Spot
	 */
	public Spot getSpot(final int x, final int y) {
		return boxes[x][y];
	}

	/**
	 * metodo che inizializza la scacchiera con pezzi nelle posizioni iniziali
	 */
	private void resetBoard() {
		// inizializzo pedoni neri
		for (int j = COL_A; j < BOARDDIM; j++) {
			boxes[RAW_7][j] = new Spot(RAW_7, j, new Pawn(BLACK));
		}
		// inizializzo pedoni bianchi
		for (int j = COL_A; j < BOARDDIM; j++) {
			boxes[RAW_2][j] = new Spot(RAW_2, j, new Pawn(WHITE));
		}
		// riempio con i pezzi pesanti
		boxes[RAW_8][COL_A] = new Spot(RAW_8, COL_A, new Rook(BLACK));
		boxes[RAW_8][COL_H] = new Spot(RAW_8, COL_H, new Rook(BLACK));
		boxes[RAW_1][COL_A] = new Spot(RAW_1, COL_A, new Rook(WHITE));
		boxes[RAW_1][COL_H] = new Spot(RAW_1, COL_H, new Rook(WHITE));
		boxes[RAW_8][COL_B] = new Spot(RAW_8, COL_B, new Knight(BLACK));
		boxes[RAW_8][COL_G] = new Spot(RAW_8, COL_G, new Knight(BLACK));
		boxes[RAW_1][COL_B] = new Spot(RAW_1, COL_B, new Knight(WHITE));
		boxes[RAW_1][COL_G] = new Spot(RAW_1, COL_G, new Knight(WHITE));
		boxes[RAW_8][COL_C] = new Spot(RAW_8, COL_C, new Bishop(BLACK));
		boxes[RAW_8][COL_F] = new Spot(RAW_8, COL_F, new Bishop(BLACK));
		boxes[RAW_1][COL_C] = new Spot(RAW_1, COL_C, new Bishop(WHITE));
		boxes[RAW_1][COL_F] = new Spot(RAW_1, COL_F, new Bishop(WHITE));
		boxes[RAW_8][COL_D] = new Spot(RAW_8, COL_D, new Queen(BLACK));
		boxes[RAW_1][COL_D] = new Spot(RAW_1, COL_D, new Queen(WHITE));
		boxes[RAW_8][COL_E] = new Spot(RAW_8, COL_E, new King(BLACK));
		boxes[RAW_1][COL_E] = new Spot(RAW_1, COL_E, new King(WHITE));
		// riempio gli spot vuoti
		for (int i = RAW_6; i < RAW_2; i++) {
			for (int j = COL_A; j < BOARDDIM; j++) {
				boxes[i][j] = new Spot(i, j, null);
			}
		}
	}

	/**
	 * ricalcola le mosse legali di tutti i pezzi sulla scacchiera
	 * 
	 */
	void recalLegalMoves() {
		for (int i = RAW_8; i < BOARDDIM; i++) {
			for (int j = COL_A; j < BOARDDIM; j++) {
				Spot currentSpot = this.getSpot(i, j);
				if (currentSpot.getPiece() != null) {
					currentSpot.getPiece().findLegalMoves(this, currentSpot);
				}
			}
		}
	}

	/**
	 * ricalcola le mosse legali dei due re sulla scacchiera
	 */
	void recalKingMoves() {
		for (int i = RAW_8; i < BOARDDIM; i++) {
			for (int j = COL_A; j < BOARDDIM; j++) {
				Spot currentSpot = this.getSpot(i, j);
				if (!currentSpot.isEmpty() && currentSpot.getPiece() instanceof King) {
					((King) currentSpot.getPiece()).recalculateMoves(this);
				}
			}
		}
	}

	/**
	 * controlla se il re è sotto attacco dopo aver effettuato la mossa
	 * 
	 * @return
	 */
	boolean kingUnderAttackNext(final Spot start, final Spot end) {
		// creo una nuova scacchiera
		Board newBoard = new Board(true);
		// copio la configurazione della scacchiera attuale
		for (int i = RAW_8; i < BOARDDIM; i++) {
			for (int j = COL_A; j < BOARDDIM; j++) {
				Spot currentSpot = newBoard.getSpot(i, j);
				Piece currentPiece = this.getSpot(i, j).getPiece();
				if (currentPiece instanceof King) {
					currentSpot.setPiece(new King(currentPiece.isWhite()));
				} else if (currentPiece instanceof Rook) {
					currentSpot.setPiece(new Rook(currentPiece.isWhite()));
				} else if (currentPiece instanceof Bishop) {
					currentSpot.setPiece(new Bishop(currentPiece.isWhite()));
				} else if (currentPiece instanceof Queen) {
					currentSpot.setPiece(new Queen(currentPiece.isWhite()));
				} else if (currentPiece instanceof Knight) {
					currentSpot.setPiece(new Knight(currentPiece.isWhite()));
				} else if (currentPiece instanceof Pawn) {
					currentSpot.setPiece(new Pawn(currentPiece.isWhite()));
				}
			}
		}
		// ricalcolo le mosse legali
		newBoard.recalLegalMoves();
		// analizzo la mossa corrente del re
		Spot newEnd = newBoard.getSpot(end.getX(), end.getY());
		Spot newStart = newBoard.getSpot(start.getX(), start.getY());
		// se c'è un pezzo nemico lo setto null per vedere se la casa
		// sarebbe sotto attacco dopo il movimento
		newEnd.setPiece(null);
		// ricalcolo le mosse legali dei pezzi
		newBoard.recalLegalMoves();
		// controllo se la casa di movimento è sotto attacco o meno
		if (newEnd.isUnderAttack(newBoard, newStart.getPiece().isWhite())) {
			return true;
		}
		// se il movimento non mette il re sotto scacco, ritorno false
		return false;
	}

	/**
	 * stabilisce se i due spot in input sono diagonali 
	 * rispetto alla direzione del pezzo 
	 * [E][ ][E]... direzione giusta per i bianchi 
	 * [ ][S][ ]... 
	 * [E][ ][E]... direzione giusta per i neri 
	 * 
	 * @param start
	 * @param end
	 * @return
	 */
	boolean isFrontDiagonal(final Spot start, final Spot end) {
		int diffX = (start.getX() - end.getX());
		int diffY = (start.getY() - end.getY());
		if (!start.isEmpty()) {
			if (start.getPiece().isWhite()) {
				if (diffX == 1 && Math.abs(diffY) == 1) {
					return true;
				}
			} else {
				if (diffX == -1 && Math.abs(diffY) == 1) {
					return true;
				}
			}
		}
		return false;
	}

	/**
	 * stabilisce se lo spot di arrivo è una casella avanti allo spot di partenza
	 * [ ][E][ ]... direzione giusta per i bianchi
	 * [ ][S][ ]...
	 * [ ][E][ ]... direzione giusta per i neri
	 * 
	 * @param start
	 * @param end
	 * @return
	 */
	boolean isFrontSpot(final Spot start, final Spot end) {
		int diffX = (start.getX() - end.getX());
		int diffY = (start.getY() - end.getY());
		if (!start.isEmpty()) {
			if (start.getPiece().isWhite()) {
				if (diffX == 1 && diffY == 0) {
					return true;
				} 
			} else {
				if (diffX == -1 && diffY == 0) {
					return true;
				} 
			}
		}
		return false;
	}

	/**
	 *stabilisce se lo spot di arrivo è due caselle avanti allo spot di partenza
	 * [ ][E][ ]... direzione giusta per i bianchi
	 * [ ][ ][ ]...
	 * [ ][S][ ]...
	 * [ ][ ][ ]...
	 * [ ][E][ ]... direzione giusta per i neri
	 * 
	 * @param start
	 * @param end
	 * @return
	 */
	boolean isTwoSpotsAhead(final Spot start, final Spot end) {
		int diffX = (start.getX() - end.getX());
		int diffY = (start.getY() - end.getY());
		if (!start.isEmpty()) {
			if (start.getPiece().isWhite()) {
				if (diffX == 2 && diffY == 0) {
					return true;
				} 
			} else {
				if (diffX == -2 && diffY == 0) {
					return true;
				}
			}
		}
		return false;
	}

	/**
	 * stabilisce se lo spot di arrivo è una casella valida per il cavallo 
	 * rispetto allo spot di partenza
	 * [E][ ][E][ ]... 
	 * [ ][ ][ ][E]...
	 * [ ][S][ ][ ]...
	 * [ ][ ][ ][E]...
	 * [E][ ][E][ ]... 
	 * 
	 * @param start
	 * @param end
	 * @return
	 */
	boolean isLMove(final Spot start, final Spot end) {
		int diffX = Math.abs(start.getX() - end.getX());
		int diffY = Math.abs(start.getY() - end.getY());
		return diffX * diffY == 2;
	}

	/**
	 * stabilisce se lo spot di arrivo è una casella intorno allo spot di partenza
	 * [E][E][E]... 
	 * [E][S][E]...
	 * [E][E][E]... 
	 * 
	 * @param start
	 * @param end
	 * @return
	 */
	public boolean isSpotAround(final Spot start, final Spot end) {
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
	 * stabilisce se lo spot di arrivo è in diagonale rispetto allo spot di partenza
	 * [ ][ ][ ][E]... 
	 * [E][ ][E][ ]...
	 * [ ][S][ ][ ]...
	 * [E][ ][E][ ]...
	 * [ ][ ][ ][E]... 
	 * 
	 * @param start
	 * @param end
	 * @return
	 */
	boolean isDiagonal(final Spot start, final Spot end) {
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
	 * stabilisce se lo spot di arrivo è sulla stessa colonna o riga rispetto allo spot di partenza
	 * [ ][E][ ][ ]... 
	 * [ ][E][ ][ ]...
	 * [E][S][E][E]...
	 * [ ][E][ ][ ]...
	 * [ ][E][ ][ ]... 
	 * 
	 * @param start
	 * @param end
	 * @return
	 */
	boolean isStraight(final Spot start, final Spot end) {
		if (!start.isEmpty()) {
			if (start.getX() == end.getX() || start.getY() == end.getY()) {
				return true;
			}
		}
		return false;
	}

	/**
	 * controlla se il percorso dal punto di partenza a quello di arrivo è libero
     * [S][x][x][E]... controllo su riga 
     * [x][x][ ][ ]... 
     * [x][ ][x][ ]...
     * [x][ ][ ][E]... controllo su diagonale
     * [E][ ][ ][ ]... controllo su colonna
     * 
     * @param start
     * @param end
     * @return
     */
	boolean isFreePath(final Spot start, final Spot end) {
		int startX = start.getX();
		int startY = start.getY();
		int endX = end.getX();
		int endY = end.getY();
		if (!start.isEmpty()) {
			if (isDiagonal(start, end)) {
				if (start.getPiece() instanceof Queen || start.getPiece() instanceof Bishop) {
					if (startX > endX) {
						if (startY > endY) {
							for (int i = 1; i < BOARDDIM; i++) { // NW: movimento diagonale
								if ((startX - i >= 0 && startX - i < BOARDDIM)
										&& (startY - i >= 0 && startY - i < BOARDDIM)) {
									Spot examined = getSpot(startX - i, startY - i);
									if (controlPath(examined, start, end)) {
										return true;
									}
								}
							} // end movimento NW
						} else {
							for (int i = 1; i < BOARDDIM; i++) { // NE: movimento diagonale
								if ((startX - i >= 0 && startX - i < BOARDDIM)
										&& (startY + i >= 0 && startY + i < BOARDDIM)) {
									Spot examined = getSpot(startX - i, startY + i);
									if (controlPath(examined, start, end)) {
										return true;
									}
								}
							} // end movimento NE
						}
					} else {
						if (startY > endY) {
							for (int i = 1; i < BOARDDIM; i++) { // SW: movimento diagonale
								if ((startX + i >= 0 && startX + i < BOARDDIM)
										&& (startY - i >= 0 && startY - i < BOARDDIM)) {
									Spot examined = getSpot(startX + i, startY - i);
									if (controlPath(examined, start, end)) {
										return true;
									}
								}
							} // end movimento Sw
						} else {
							for (int i = 1; i < BOARDDIM; i++) { // SE: movimento diagonale
								if ((startX + i >= 0 && startX + i < BOARDDIM)
										&& (startY + i >= 0 && startY + i < BOARDDIM)) {
									Spot examined = getSpot(startX + i, startY + i);
									if (controlPath(examined, start, end)) {
										return true;
									}
								}
							} // end movimento SE
						}
					}
				}
			} else if (isStraight(start, end)) {
				if (start.getPiece() instanceof Queen || start.getPiece() instanceof Rook) {
					if (startX == endX) {
						if (startY > endY) {
							for (int i = 1; i < BOARDDIM; i++) { // SX: movimento a sinistra
								if ((startY - i >= 0 && startY - i < BOARDDIM)) {
									Spot examined = getSpot(startX, startY - i);
									if (controlPath(examined, start, end)) {
										return true;
									}
								}
							} // end movimento SX
						} else {
							for (int i = 1; i < BOARDDIM; i++) { // DX: movimento a destra
								if ((startY + i >= 0 && startY + i < BOARDDIM)) {
									Spot examined = getSpot(startX, startY + i);
									if (controlPath(examined, start, end)) {
										return true;
									}
								}
							} // end movimento DX
						}
					} else if (startY == endY) {
						if (startX > endX) {
							for (int i = 1; i < BOARDDIM; i++) { // UP: movimento in alto
								if ((startX - i >= 0 && startX - i < BOARDDIM)) {
									Spot examined = getSpot(startX - i, startY);
									if (controlPath(examined, start, end)) {
										return true;
									}
								}
							} // end movimento UP
						} else {
							for (int i = 1; i < BOARDDIM; i++) { // DOWN: movimento in basso
								if ((startX + i >= 0 && startX + i < BOARDDIM)) {
									Spot examined = getSpot(startX + i, startY);
									if (controlPath(examined, start, end)) {
										return true;
									}
								}
							} // end movimento DOWN
						}
					}
				}
			}
		}
		return false;
	}// end metodo isFreePath
	
	/**
	 * controlla se lo Spot examined e' vuoto, se invece contiene un pezzo 
	 * ne verifica il colore, dopodiche' se examined coincide con end restituisce vero
	 * 
	 * @param examined
	 * @param start
	 * @param end
	 * @return
	 */
	private boolean controlPath(final Spot examined, final Spot start, final Spot end) {
		if (!examined.isEmpty()) {
			if (start.getPiece().isWhite() != examined.getPiece().isWhite()) {
				if (examined.equals(end)) {
					return true;
				} 
			} 
		}
		if (examined.equals(end)) {
			return true;
		}
		return false;
	}

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

		for (int i = RAW_8; i < BOARDDIM; i++) {
			System.out.print("  " + (BOARDDIM - i) + " ");
			System.out.print(vertline);
			for (int j = COL_A; j < BOARDDIM; j++) {
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
				if (!currentSpot.isEmpty() && currentSpot.getPiece() instanceof King) {
					output += currentSpot.getPiece() + "\n";
				}
			}
		}
		return output;
	}
}
