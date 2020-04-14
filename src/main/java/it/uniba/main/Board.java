package it.uniba.main;

public class Board {
	Spot[][] boxes = new Spot[8][8];	//matrice scacchiera formata da caselle (elementi di classe Spot)


	/**costruttore di Board, inizializza la scacchiera con la configurazione iniziale 
	 * usando il metodo resetBoard()
	 */
	public Board() {
		this.resetBoard();
		
		//TODO
	}

	/**metodo che ritorna una casa della scacchiera identificato con (riga, colonna)
	 * 
	 * @param x riga
	 * @param y colonna
	 * @return elemento di classe Spot 
	 */
	public Spot getSpot(int x, int y) {
		return boxes[x][y];
	}

	/**metodo che inizializza la scacchiera con pezzi nelle posizioni iniziali
	 * 
	 */
	void resetBoard() {

		boxes[1][0] = new Spot(1,0, new Pawn(false));
		boxes[1][1] = new Spot(1,1, new Pawn(false));
		boxes[1][2] = new Spot(1,2, new Pawn(false));
		boxes[1][3] = new Spot(1,3, new Pawn(false));
		boxes[1][4] = new Spot(1,4, new Pawn(false));
		boxes[1][5] = new Spot(1,5, new Pawn(false));
		boxes[1][6] = new Spot(1,6, new Pawn(false));
		boxes[1][7] = new Spot(1,7, new Pawn(false));
		boxes[6][0] = new Spot(6,0, new Pawn(true));
		boxes[6][1] = new Spot(6,1, new Pawn(true));
		boxes[6][2] = new Spot(6,2, new Pawn(true));
		boxes[6][3] = new Spot(6,3, new Pawn(true));
		boxes[6][4] = new Spot(6,4, new Pawn(true));
		boxes[6][5] = new Spot(6,5, new Pawn(true));
		boxes[6][6] = new Spot(6,6, new Pawn(true));
		boxes[6][7] = new Spot(6,7, new Pawn(true));
		boxes[0][0] = new Spot(0,0, new Rook(false));
		boxes[0][7] = new Spot(0,7, new Rook(false));
		boxes[7][0] = new Spot(7,0, new Rook(true));
		boxes[7][7] = new Spot(7,7, new Rook(true));
		boxes[0][1] = new Spot(0,1, new Knight(false));
		boxes[0][6] = new Spot(0,6, new Knight(false));
		boxes[7][1] = new Spot(7,1, new Knight(true));
		boxes[7][6] = new Spot(7,6, new Knight(true));
		boxes[0][2] = new Spot(0,2, new Bishop(false));
		boxes[0][5] = new Spot(0,5, new Bishop(false));
		boxes[7][2] = new Spot(7,2, new Bishop(true));
		boxes[7][5] = new Spot(7,5, new Bishop(true));
		boxes[0][3] = new Spot(0,3, new Queen(false));
		boxes[7][3] = new Spot(7,3, new Queen(true));
		boxes[0][4] = new Spot(0,4, new King(false));
		boxes[7][4] = new Spot(7,4, new King(true));

		for (int x=2; x<6; x++) {
			for (int j=0; j<8; j++) {
				boxes[x][j] = new Spot(x, j, null);
			}
		}
	}
	

	/**metodo che permette la stampa a video della scacchiera nella configurazione attuale
	 * 
	 */
	public void showBoard() {
		for(int i=0; i<8; i++) {
			for(int j=0; j<8; j++) {
				Piece piece = this.getSpot(i, j).getPiece();
				System.out.print("|");
				if(piece == null) {
					System.out.print(" ");
				} else {
					System.out.print(piece.draw());
				}
				
			}
			System.out.print("|");
			System.out.println("\t");
		}
	}
}