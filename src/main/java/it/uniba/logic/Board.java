package it.uniba.logic;

/**
 * <body>
 * <h2>DESCRIZIONE</h2>
 * rappresenta una scacchiera (matrice 8x8) <br>
 * ogni casella e' un elemento di classe Spot <br>
 *
 * <h2>RESPONSABILITA' DI CLASSE</h2>
 * si occupa di ricalcolare le mosse legali di ogni pezzo <br>
 * ed effettua controlli sulla posizione reciproca di due Spot; <br>
 * permette la stampa a video della scacchiera nella configurazione attuale <br>
 *
 * <h2>CLASSIFICAZIONE ECB</h2>
 * <strong>Control</strong><br>
 * poiche' responsabile dell'analisi della posizione degli Spot<br>
 * in funzione del movimento peculiare di ogni pezzo<br>
 * seguendo la logica di gioco
 * </body>
 *
 * @author wilkinson
 */
public class Board {
	private Spot[][] boxes; // matrice scacchiera formata da caselle (elementi di classe Spot)
	private Spot whiteKingSpot;
	private Spot blackKingSpot;
	private static final int BOARDDIM = 8; // dimensioni della scacchiera
	private static final int ROW_1 = 7;
	private static final int ROW_2 = 6;
	//	private static final int ROW_3 = 5;
	//	private static final int ROW_4 = 4;
	//	private static final int ROW_5 = 3;
	private static final int ROW_6 = 2;
	private static final int ROW_7 = 1;
	private static final int ROW_8 = 0;
	private static final int COL_H = 7;
	private static final int COL_G = 6;
	private static final int COL_F = 5;
	private static final int COL_E = 4;
	private static final int COL_D = 3;
	private static final int COL_C = 2;
	private static final int COL_B = 1;
	private static final int COL_A = 0;
	// costanti corrispondenti al colore del pezzo (da passare al costruttore)
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
		for (int i = ROW_8; i < BOARDDIM; i++) {
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
			boxes[ROW_7][j] = new Spot(ROW_7, j, new Pawn(BLACK));
		}
		// inizializzo pedoni bianchi
		for (int j = COL_A; j < BOARDDIM; j++) {
			boxes[ROW_2][j] = new Spot(ROW_2, j, new Pawn(WHITE));
		}
		// inizializzo i pezzi pesanti
		boxes[ROW_8][COL_A] = new Spot(ROW_8, COL_A, new Rook(BLACK));
		boxes[ROW_8][COL_H] = new Spot(ROW_8, COL_H, new Rook(BLACK));
		boxes[ROW_1][COL_A] = new Spot(ROW_1, COL_A, new Rook(WHITE));
		boxes[ROW_1][COL_H] = new Spot(ROW_1, COL_H, new Rook(WHITE));
		boxes[ROW_8][COL_B] = new Spot(ROW_8, COL_B, new Knight(BLACK));
		boxes[ROW_8][COL_G] = new Spot(ROW_8, COL_G, new Knight(BLACK));
		boxes[ROW_1][COL_B] = new Spot(ROW_1, COL_B, new Knight(WHITE));
		boxes[ROW_1][COL_G] = new Spot(ROW_1, COL_G, new Knight(WHITE));
		boxes[ROW_8][COL_C] = new Spot(ROW_8, COL_C, new Bishop(BLACK));
		boxes[ROW_8][COL_F] = new Spot(ROW_8, COL_F, new Bishop(BLACK));
		boxes[ROW_1][COL_C] = new Spot(ROW_1, COL_C, new Bishop(WHITE));
		boxes[ROW_1][COL_F] = new Spot(ROW_1, COL_F, new Bishop(WHITE));
		boxes[ROW_8][COL_D] = new Spot(ROW_8, COL_D, new Queen(BLACK));
		boxes[ROW_1][COL_D] = new Spot(ROW_1, COL_D, new Queen(WHITE));
		boxes[ROW_8][COL_E] = new Spot(ROW_8, COL_E, new King(BLACK));
		blackKingSpot = boxes[ROW_8][COL_E];
		boxes[ROW_1][COL_E] = new Spot(ROW_1, COL_E, new King(WHITE));
		whiteKingSpot = boxes[ROW_1][COL_E];
		// riempio gli spot vuoti
		for (int i = ROW_6; i < ROW_2; i++) {
			for (int j = COL_A; j < BOARDDIM; j++) {
				boxes[i][j] = new Spot(i, j, null);
			}
		}
	}

	/**
	 * ricerca i due re sulla scacchiera e ne aggiorna la posizione attuale
	 */
	void searchForKings() {
		for (int i = ROW_8; i < BOARDDIM; i++) {
			for (int j = COL_A; j < BOARDDIM; j++) {
				Spot currentSpot = this.getSpot(i, j);
				if (currentSpot.getPiece() != null && currentSpot.getPiece() instanceof King) {
					if (currentSpot.getPiece().isWhite()) {
						whiteKingSpot = boxes[currentSpot.getX()][currentSpot.getY()];
					} else {
						blackKingSpot = boxes[currentSpot.getX()][currentSpot.getY()];
					}
				}
			}
		}
	}

	/**
	 * ricalcola le mosse legali di tutti i pezzi sulla scacchiera
	 *
	 */
	public void recalLegalMoves() {
		for (int i = ROW_8; i < BOARDDIM; i++) {
			for (int j = COL_A; j < BOARDDIM; j++) {
				Spot currentSpot = this.getSpot(i, j);
				if (!currentSpot.isEmpty()) {
					currentSpot.getPiece().findLegalMoves(this, currentSpot);
				}
			}
		}
	}

	/**
	 * ricalcola le mosse di ogni pezzo controllando se espongono il re a pericolo
	 */
	public void refineLegalMoves() {
		for (int i = ROW_8; i < BOARDDIM; i++) {
			for (int j = COL_A; j < BOARDDIM; j++) {
				Spot currentSpot = this.getSpot(i, j);
				if (!currentSpot.isEmpty()) {
					currentSpot.getPiece().recalculateMoves(this);
				}
			}
		}
	}

	/**
	 * controlla se il re e' sotto attacco dopo aver effettuato la mossa
	 *
	 * @return
	 */
	boolean kingUnderAttackNext(final Spot start, final Spot end) {
		// creo una nuova scacchiera
		Board newBoard = new Board(true);
		// copio la configurazione della scacchiera attuale
		for (int i = ROW_8; i < BOARDDIM; i++) {
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
		// ricalcolo le mosse legali della nuova scacchiera
		newBoard.recalLegalMoves();
		// analizzo la mossa corrente
		Spot newEnd = newBoard.getSpot(end.getX(), end.getY());
		Spot newStart = newBoard.getSpot(start.getX(), start.getY());
		Boolean color = newStart.getPiece().isWhite();
		// effettuo il movimento
		newEnd.setPiece(newStart.getPiece());
		newStart.setPiece(null);
		// ricalcolo le mosse legali dei pezzi
		newBoard.recalLegalMoves();
		// ricerco la nuova posizione del re
		newBoard.searchForKings();
		// controllo se la casa corrente del re e' sotto attacco o meno
		if (newEnd.getPiece().isWhite()) {
			if (newBoard.getWhiteKingSpot().isUnderAttack(newBoard, color)) {
				return true;
			}
		} else {
			if (newBoard.getBlackKingSpot().isUnderAttack(newBoard, color)) {
				return true;
			}
		}
		// se il movimento non mette il re sotto scacco, ritorno false
		return false;
	}

	/**
	 * ritorna lo spot immediatamente di fronte alla casa corrente
	 * [ ][x][ ]... casa restituita per i bianchi
	 * [ ][S][ ]... casa di partenza
	 * [ ][x][ ]... casa restituita per i neri
	 *
	 * @param spot
	 * @return
	 */
	Spot frontSpot(final Spot spot) {
		final int oneDiffPosX = 1; // differenza di 1 sulle coordinate X
		final int oneDiffNegX = -1; // differenza di -1 sulle coordinate X
		if (spot.getPiece().isWhite()) {
			return this.getSpot(spot.getX() + oneDiffNegX, spot.getY());
		} else {
			return this.getSpot(spot.getX() + oneDiffPosX, spot.getY());
		}
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
		final int oneDiffPosX = 1; // differenza di 1 sulle coordinate X
		final int oneDiffNegX = -1; // differenza di -1 sulle coordinate X
		final int oneDiffY = 1; // differenza di 1 sulle coordinate Y
		int diffX = (start.getX() - end.getX());
		int diffY = (start.getY() - end.getY());
		if (!start.isEmpty()) {
			if (start.getPiece().isWhite()) {
				if (diffX == oneDiffPosX && Math.abs(diffY) == oneDiffY) {
					return true;
				}
			} else {
				if (diffX == oneDiffNegX && Math.abs(diffY) == oneDiffY) {
					return true;
				}
			}
		}
		return false;
	}

	/**
	 * stabilisce se lo spot di arrivo e' una casella avanti allo spot di partenza
	 * [ ][E][ ]... direzione giusta per i bianchi
	 * [ ][S][ ]...
	 * [ ][E][ ]... direzione giusta per i neri
	 *
	 * @param start
	 * @param end
	 * @return
	 */
	boolean isFrontSpot(final Spot start, final Spot end) {
		final int oneDiffPosX = 1; // differenza di 1 sulle coordinate X
		final int oneDiffNegX = -1; // differenza di -1 sulle coordinate X
		final int zeroDiffY = 0; // differenza di 0 sulle coordinate Y
		int diffX = (start.getX() - end.getX());
		int diffY = (start.getY() - end.getY());
		if (!start.isEmpty()) {
			if (start.getPiece().isWhite()) {
				if (diffX == oneDiffPosX && diffY == zeroDiffY) {
					return true;
				}
			} else {
				if (diffX == oneDiffNegX && diffY == zeroDiffY) {
					return true;
				}
			}
		}
		return false;
	}

	/**
	 *stabilisce se lo spot di arrivo e' due caselle avanti allo spot di partenza
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
		final int twoDiffPosX = 2; // differenza di 2 sulle coordinate X
		final int twoDiffNegX = -2; // differenza di -2 sulle coordinate X
		final int zeroDiffY = 0; // differenza di 0 sulle coordinate Y
		int diffX = (start.getX() - end.getX());
		int diffY = (start.getY() - end.getY());
		if (!start.isEmpty()) {
			if (start.getPiece().isWhite()) {
				if (diffX == twoDiffPosX && diffY == zeroDiffY) {
					return true;
				}
			} else {
				if (diffX == twoDiffNegX && diffY == zeroDiffY) {
					return true;
				}
			}
		}
		return false;
	}

	/**
	 * stabilisce se lo spot di arrivo e' una casella valida per il cavallo
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
		final int twoDiff = 2; // differenza di 2 sulle coordinate
		int diffX = Math.abs(start.getX() - end.getX());
		int diffY = Math.abs(start.getY() - end.getY());
		return diffX * diffY == twoDiff;
	}

	/**
	 * stabilisce se lo spot di arrivo e' una casella intorno allo spot di partenza
	 * [E][E][E]...
	 * [E][S][E]...
	 * [E][E][E]...
	 *
	 * @param start
	 * @param end
	 * @return
	 */
	boolean isSpotAround(final Spot start, final Spot end) {
		final int oneDiff = 1; // differenza di 1 sulle coordinate
		final int zeroDiff = 0; // differenza di 0 sulle coordinate
		int diffX = Math.abs(start.getX() - end.getX());
		int diffY = Math.abs(start.getY() - end.getY());
		if (!start.isEmpty()) {
			if ((diffX == zeroDiff || diffX == oneDiff)
					&& (diffY == zeroDiff || diffY == oneDiff)) {
				return true;
			}
		}
		return false;
	}

	/**
	 * stabilisce se lo spot di arrivo e' in diagonale rispetto allo spot di partenza
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
	 * stabilisce se lo spot di arrivo e' sulla stessa colonna o riga rispetto allo spot di partenza
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
	 * controlla se il percorso dal punto di partenza a quello di arrivo e' libero
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
								if ((startX - i >= 0
										&& startX - i < BOARDDIM)
										&& (startY - i >= 0
										&& startY - i < BOARDDIM)) {
									Spot examined = getSpot(startX - i, startY - i);
									if (checkPath(examined, start, end) == 1) {
										return true;
									}
									if (checkPath(examined, start, end) == 0) {
										return false;
									}
								}
							} // end movimento NW
						} else {
							for (int i = 1; i < BOARDDIM; i++) { // NE: movimento diagonale
								if ((startX - i >= 0
										&& startX - i < BOARDDIM)
										&& (startY + i >= 0
										&& startY + i < BOARDDIM)) {
									Spot examined = getSpot(startX - i, startY + i);
									if (checkPath(examined, start, end) == 1) {
										return true;
									}
									if (checkPath(examined, start, end) == 0) {
										return false;
									}
								}
							} // end movimento NE
						}
					} else {
						if (startY > endY) {
							for (int i = 1; i < BOARDDIM; i++) { // SW: movimento diagonale
								if ((startX + i >= 0
										&& startX + i < BOARDDIM)
										&& (startY - i >= 0
										&& startY - i < BOARDDIM)) {
									Spot examined = getSpot(startX + i, startY - i);
									if (checkPath(examined, start, end) == 1) {
										return true;
									}
									if (checkPath(examined, start, end) == 0) {
										return false;
									}
								}
							} // end movimento Sw
						} else {
							for (int i = 1; i < BOARDDIM; i++) { // SE: movimento diagonale
								if ((startX + i >= 0
										&& startX + i < BOARDDIM)
										&& (startY + i >= 0
										&& startY + i < BOARDDIM)) {
									Spot examined = getSpot(startX + i, startY + i);
									if (checkPath(examined, start, end) == 1) {
										return true;
									}
									if (checkPath(examined, start, end) == 0) {
										return false;
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
									if (checkPath(examined, start, end) == 1) {
										return true;
									}
									if (checkPath(examined, start, end) == 0) {
										return false;
									}
								}
							} // end movimento SX
						} else {
							for (int i = 1; i < BOARDDIM; i++) { // DX: movimento a destra
								if ((startY + i >= 0 && startY + i < BOARDDIM)) {
									Spot examined = getSpot(startX, startY + i);
									if (checkPath(examined, start, end) == 1) {
										return true;
									}
									if (checkPath(examined, start, end) == 0) {
										return false;
									}
								}
							} // end movimento DX
						}
					} else if (startY == endY) {
						if (startX > endX) {
							for (int i = 1; i < BOARDDIM; i++) { // UP: movimento in alto
								if ((startX - i >= 0 && startX - i < BOARDDIM)) {
									Spot examined = getSpot(startX - i, startY);
									if (checkPath(examined, start, end) == 1) {
										return true;
									}
									if (checkPath(examined, start, end) == 0) {
										return false;
									}
								}
							} // end movimento UP
						} else {
							for (int i = 1; i < BOARDDIM; i++) { // DOWN: movimento in basso
								if ((startX + i >= 0 && startX + i < BOARDDIM)) {
									Spot examined = getSpot(startX + i, startY);
									if (checkPath(examined, start, end) == 1) {
										return true;
									}
									if (checkPath(examined, start, end) == 0) {
										return false;
									}
								}
							} // end movimento DOWN
						}
					}
				}
			}
		}
		return false;
	} // end metodo isFreePath

	/**controlla se lo spot esaminato sia libero
	 *
	 * @param examined
	 * @param start
	 * @param end
	 * @return
	 */
	private int checkPath(final Spot examined, final Spot start, final Spot end) {
		if (!examined.isEmpty()) { // se lo spot esaminato non e' vuoto
			if (start.getPiece().isWhite() != examined.getPiece().isWhite()) {
				// controllo se il pezzo sulla fine del percorso e' un pezzo nemico
				if (examined.equals(end)) {
					return 1;
				} else {
					return 0;
				}
			} else {
				return 0;
			}
		}
		if (examined.equals(end)) {
			return 1;
		}
		return -1; // continuo
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

		for (int i = ROW_8; i < BOARDDIM; i++) {
			System.out.print("  " + (BOARDDIM - i) + " ");
			System.out.print(vertline);
			for (int j = COL_A; j < BOARDDIM; j++) {
				Piece piece = this.getSpot(i, j).getPiece();

				if ((i + j) % 2 != 0) {
					if (piece != null) {
						System.out.print(ANSI_BACKGROUND + ANSI_BLACK + " "
								+ piece.draw() + " " + ANSI_RESET);
					} else {
						System.out.print(ANSI_BACKGROUND + "   " + ANSI_RESET);
					}
				} else {
					if (piece != null) {
						System.out.print(ANSI_WHITE_BACKGROUND + ANSI_BLACK + " "
								+ piece.draw() + " " + ANSI_RESET);
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

	/**restituisce lo spot dove si trova il re bianco
	 *
	 * @return
	 */
	public Spot getWhiteKingSpot() {
		return whiteKingSpot;
	}

	/**restituisce lo spot dove si trova il re nero
	 *
	 * @return
	 */
	public Spot getBlackKingSpot() {
		return blackKingSpot;
	}
}
