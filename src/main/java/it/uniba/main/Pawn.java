package it.uniba.main;

public class Pawn extends Piece {

	public Pawn(boolean white) {
		super(white);
		// TODO Auto-generated constructor stub
	}

	@Override
	public void draw() {
		// TODO Auto-generated method stub
		
	}
	
	@Override
	boolean canMove(Board board, Spot start, Spot end) {
		
		//non posso muovere in una casella dove c'è un pezzo dello stesso colore
		if(end.piece.isWhite() == this.isWhite()) {
			return false;
		}
		
		if(start.piece.isWhite()) {
			if((start.y == end.y) || (start.x == (end.x +1))) {
				//TODO movimento in avanti del pedone bianco
				return true;
			}
		} else if(!start.piece.isWhite()) {
			if((start.y == end.y) || (start.x == (end.x -1))) {
				//TODO movimento in avanti del pedone nero
				return true;
			}
		}      
		
		return false;
	}

	

}
