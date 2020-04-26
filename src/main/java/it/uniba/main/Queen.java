package it.uniba.main;


/**
 * rappresenta una regina sulla scacchiera
 * 
 * @author wilkinson
 *
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
	boolean canMove(Board board, Spot start, Spot end, boolean isWhiteTurn) {
		Queen startPiece = (Queen) start.getPiece();
		Piece endPiece = end.getPiece();
		
		
		
		return false;
	}

	
	/**stabilisce se la regina ha percorsi liberi
	 * 
	 * @param board
	 * @param start
	 * @param end
	 * @return
	 */
/*
	boolean hasFreePath(Board board, Spot start, Spot end) {
		ArrayList<String> path = new ArrayList<String>();
		int dim = Math.abs(start.getX() - end.getX());
		//System.out.println(dim);
		boolean free = true;
		for (int i = 0; i < dim; i++) {
			path.add(start.toString());
					}
		
		for (String s : path) {
			System.out.println(s);
			if(s.equals("")){
			
				free = false;
			}
		}
		
		return free;
	}*/
}