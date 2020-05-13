package it.uniba.main;

/**
 * DESCRIZIONE
 * rappresenta un pezzo regina 
 *
 * RESPONSABILITA' DI CLASSE
 * calcola le mosse legali di una regina seguendo le regole ufficiale degli scacchi
 *
 * CLASSIFICAZIONE ECB
 * <<Entity>>
 * poiche' eredita dalla classe Piece.java
 * 
 * @author wilkinson
 */
public class Queen extends Piece {

	public Queen(boolean white) {
		super(white);
	}

	@Override
	public String draw() {
		if (isWhite()) {
			return "\u2655";
		} else {
			return "\u265b";
		}
	}

	@Override
	public boolean canMove(Board board, Spot start, Spot end) {
		if (board.isFreePath(start, end)) {
			return true;
		}
		return false;
	}
}
