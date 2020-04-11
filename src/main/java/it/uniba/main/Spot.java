package it.uniba.main;

public class Spot {
	Piece piece;	//pezzo che la occupa
	int x;			//coordinata riga
	int y;			//coordinata colonna
	
	
	/** costruttore di Spot (che rappresenta una casella della scacchiera)
	 * 
	 * @param x	coordinata della riga (corrispondente ad un numero)
	 * @param y coordinata della colonna (corrispondente ad una lettera)
	 */
	public Spot(int x, int y, Piece piece) {
		this.x = x;
		this.y = y;
		this.piece = piece;
	}
	
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
}
