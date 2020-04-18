package it.uniba.main;

public class Board {
	private Spot[][] boxes; // matrice scacchiera formata da caselle (elementi di classe Spot)
	private static final int BOARDDIM = 8; // dimensioni della scacchiera
	private static final int INITEMPTYRAW = 2;
	private static final int ENDEMPTYRAW = 6;

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

		for (int x = INITEMPTYRAW; x < ENDEMPTYRAW; x++) {
			for (int j = 0; j < BOARDDIM; j++) {
				boxes[x][j] = new Spot(x, j, null);
			}
		}
	}

	/**
	 * metodo che permette la stampa a video della scacchiera nella configurazione attuale
	 */
	public void showBoard() {
		String upline = "\u2550";
		String upline2 = "\u2566";
		String sxline2 = "\u2560";
		String cornersxline = "\u2554";
		String cornerdxline = "\u2557";
		String dxline = "\u2563";
		String cornersxline2 = "\u255a";
		String cornerdxline2 = "\u255d";
		String downline2 = "\u2569";
		// stampa del contorno della scacchiera
		// in carattere unicode

		System.out.println("      a b c d e f g h");
		// stampa delle coordinate sulle righe

		boolean isblack = true;

		System.out.print("    " + cornersxline);
		for (int i = 0; i < BOARDDIM; i++) {
			System.out.print(upline);
			System.out.print(upline2);
		}
		System.out.print(upline);
		System.out.print(cornerdxline);
		System.out.println("");
		// stampa delle coordinate sulle colonne

		for (int i = 0; i < BOARDDIM; i++) {
			System.out.print("  " + (BOARDDIM - i) + " ");
			System.out.print(sxline2);
			System.out.print(" ");
			for (int j = 0; j < BOARDDIM; j++) {
				Piece piece = this.getSpot(i, j).getPiece();
				if (piece == null) {
					//stampa casella vuota
					if (isblack) {
						System.out.print("\u25A1");
						isblack = false;
					} else {
						System.out.print("\u25A0");
						isblack = true;
					}
				} else {
					// stampa del pezzo nello spot corrente in carattere unicode sulla scacchiera
					piece.draw();
					System.out.print(piece.draw());
					isblack = !isblack;
				}
				System.out.print(" ");
			}

			System.out.print(dxline);
			System.out.print(" " + (BOARDDIM - i));
			isblack = !isblack;
			System.out.println("\t");
		}
		

		System.out.print("    " + cornersxline2);
		for (int i = 0; i < BOARDDIM; i++) {
			System.out.print(upline);
			System.out.print(downline2);
		}
		System.out.print(upline);
		System.out.print(cornerdxline2);
		System.out.println("");
		System.out.println("      a b c d e f g h");
	}

	public Spot[][] getBoxes() {
		return boxes;
	}

	public void setBoxes(Spot[][] boxes) {
		this.boxes = boxes;
	}
}
