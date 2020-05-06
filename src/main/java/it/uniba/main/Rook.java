package it.uniba.main;

/**
 * DESCRIZIONE
 * rappresenta un pezzo torre 
 *
 * RESPONSABILITA' DI CLASSE
 * calcola le mosse legali di una torre seguendo le regole ufficiale degli scacchi
 *
 * CLASSIFICAZIONE ECB
 * <<Entity>>
 * poiche' eredita dalla classe Piece.java
 * 
 * @author wilkinson
 */
public class Rook extends Piece {

	public Rook(boolean white) {
		super(white);
	}

	@Override
	public String draw() {
		if (isWhite()) {
			return "\u2656";
		} else {
			return "\u265c";
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
