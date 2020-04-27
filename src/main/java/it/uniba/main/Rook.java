package it.uniba.main;

/**rappresenta una torre sulla scacchiera
 * 
 * @author wilkinson
 *
 */
public class Rook extends Piece {

	public Rook(boolean white) {
		super(white);
		// TODO Auto-generated constructor stub
	}

	@Override
	/**
	 * Metodo per ottenere l'unicode della torre nera e della torre bianca
	 */
	public String draw() {
		if (isWhite()) {
			return "\u2656";
		} else {
			return "\u265c";
		}
	}

	@Override
	/**
	 * metodo che verifica il movimento legale di una  torre
	 * e che verifica la possibile cattura di un pezzo avversario
	 */
	boolean canMove(Board board, Spot start, Spot end, boolean isWhiteTurn) {
		if (board.isFreePath(start, end)) {
			return true;
		}
		return false;
	}
	
}
