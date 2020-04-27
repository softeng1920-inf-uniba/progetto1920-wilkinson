package it.uniba.main;

/**rappresenta una casa della scacchiera
 * ha una coordinata X e una Y corrispondenti a numeri (da 1 a 8) e lettere (da 'a' ad 'h')
 * 
 * @author pierpaolo
 *
 */
public class Spot {
	Piece piece; // pezzo che la occupa
	int x; // coordinata riga
	int y; // coordinata colonna

	public boolean isEmpty() {
		if (getPiece()==null) {
			return true;
		}
		return false;
	}
	/**
	 * costruttore di Spot (che rappresenta una casella della scacchiera)
	 * 
	 * @param x coordinata della riga (corrispondente ad un numero)
	 * @param y coordinata della colonna (corrispondente ad una lettera)
	 */
	public Spot(int x, int y, Piece piece) {
		this.x = x;
		this.y = y;
		this.piece = piece;
	}
	
	public Spot(int x, int y) {
		this.x = x;
		this.y = y;
	}
	
	/**converte la coordinata Y di uno spot in lettera
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
		}
		return null;
	}

	//Getters & Setters
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
		return output += convertCoordinate(y) + (8-x);
	}
}
