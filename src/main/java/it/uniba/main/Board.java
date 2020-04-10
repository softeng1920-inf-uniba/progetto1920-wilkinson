package it.uniba.main;

public class Board {
	Spot[][] boxes;	//matrice scacchiera formata da caselle (elementi di classe Spot)


	/**costruttore di Board, inizializza la scacchiera con la configurazione iniziale 
	 * usando il metodo resetBoard()
	 */
	public Board() {
		resetBoard();
		
		//TODO
	}

	/**metodo che ritorna una casa della scacchiera identificato con (riga, colonna)
	 * 
	 * @param x riga
	 * @param y colonna
	 * @return elemento di classe Spot 
	 */
	public Spot getSpot(int x, int y) {
		//TODO
		return null;
	}

	/**metodo che inizializza la scacchiera con pezzi nelle posizioni iniziali
	 * 
	 */
	void resetBoard() {
		Spot boxes[][] = new Spot [8][8];
		boxes[1][0] = new Spot(1,0, new Pawn());
		boxes[1][1] = new Spot(1,1, new Pawn());
		boxes[1][2] = new Spot(1,2, new Pawn());
		boxes[1][3] = new Spot(1,3, new Pawn());
		boxes[1][4] = new Spot(1,4, new Pawn());
		boxes[1][5] = new Spot(1,5, new Pawn());
		boxes[1][6] = new Spot(1,6, new Pawn());
		boxes[1][7] = new Spot(1,7, new Pawn());
		boxes[6][0] = new Spot(6,0, new Pawn());
		boxes[6][1] = new Spot(6,1, new Pawn());
		boxes[6][2] = new Spot(6,2, new Pawn());
		boxes[6][3] = new Spot(6,4, new Pawn());
		boxes[6][4] = new Spot(6,4, new Pawn());
		boxes[6][5] = new Spot(6,5, new Pawn());
		boxes[6][6] = new Spot(6,6, new Pawn());
		boxes[6][7] = new Spot(6,7, new Pawn());
		boxes[0][0] = new Spot(0,0, new Rook());
		boxes[0][7] = new Spot(0,7, new Rook());
		boxes[7][0] = new Spot(7,0, new Rook());
		boxes[7][7] = new Spot(7,7, new Rook());
		boxes[0][1] = new Spot(0,1, new Knight());
		boxes[0][6] = new Spot(0,6, new Knight());
		boxes[7][1] = new Spot(7,1, new Knight());
		boxes[7][6] = new Spot(7,6, new Knight());
		boxes[0][2] = new Spot(0,2, new Bishop());
		boxes[0][5] = new Spot(0,5, new Bishop());
		boxes[7][2] = new Spot(7,2, new Bishop());
		boxes[7][5] = new Spot(7,5, new Bishop());
		boxes[0][3] = new Spot(0,3, new Queen());
		boxes[7][3] = new Spot(7,3, new Queen());
		boxes[0][4] = new Spot(0,4, new King());
		boxes[7][4] = new Spot(7,4, new King());
		boxes[2][0] = new Spot(2,0, null);
		boxes[2][1] = new Spot(2,1, null);
		boxes[2][2] = new Spot(2,2, null);
		boxes[2][3] = new Spot(2,3, null);
		boxes[2][4] = new Spot(2,4, null);
		boxes[2][5] = new Spot(2,5, null);
		boxes[2][6] = new Spot(2,6, null);
		boxes[2][7] = new Spot(2,7, null);
		boxes[3][0] = new Spot(3,0, null);
		boxes[3][1] = new Spot(3,1, null);
		boxes[3][2] = new Spot(3,2, null);
		boxes[3][3] = new Spot(3,3, null);
		boxes[3][4] = new Spot(3,4, null);
		boxes[3][5] = new Spot(3,5, null);
		boxes[3][6] = new Spot(3,6, null);
		boxes[3][7] = new Spot(3,7, null);
		boxes[4][0] = new Spot(4,0, null);
		boxes[4][1] = new Spot(4,1, null);
		boxes[4][2] = new Spot(4,2, null);
		boxes[4][3] = new Spot(4,3, null);
		boxes[4][4] = new Spot(4,4, null);
		boxes[4][5] = new Spot(4,5, null);
		boxes[4][6] = new Spot(4,6, null);
		boxes[4][7] = new Spot(4,7, null);
		boxes[5][0] = new Spot(5,0, null);
		boxes[5][1] = new Spot(5,1, null);
		boxes[5][2] = new Spot(5,2, null);
		boxes[5][3] = new Spot(5,3, null);
		boxes[5][4] = new Spot(5,4, null);
		boxes[5][5] = new Spot(5,5, null);
		boxes[5][6] = new Spot(5,6, null);
		boxes[5][7] = new Spot(5,7, null);
		System.out.println(boxes);
		//TODO
	}

	/**metodo che permette la stampa a video della scacchiera nella configurazione attuale
	 * 
	 */
	public void showBoard() {
		//TODO
	}
}
