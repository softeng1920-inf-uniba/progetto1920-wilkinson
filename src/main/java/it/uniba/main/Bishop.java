package it.uniba.main;

/**
 * DESCRIZIONE
 * rappresenta un pezzo alfiere 
 *
 * RESPONSABILITA' DI CLASSE
 * calcola le mosse legali di un alfiere seguendo le regole ufficiale degli scacchi
 *
 * CLASSIFICAZIONE ECB
 * <<Entity>>
 * poiche' eredita dalla classe Piece.java
 * 
 * @author wilkinson
 */
public class Bishop extends Piece {

	public Bishop(boolean white) {
		super(white);
	}

	@Override
	public String draw() {
		if (isWhite()) {
			return "\u2657";
		} else {
			return "\u265d";
		}

	}

	@Override
	boolean canMove(Board board, Spot start, Spot end) {
		if (board.isFreePath(start, end)) {
			return true;
		}
		return false;
	}
}
