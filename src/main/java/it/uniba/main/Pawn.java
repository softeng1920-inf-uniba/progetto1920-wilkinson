package it.uniba.main;

public class Pawn extends Piece {

	public Pawn(boolean white) {
		super(white);
		// TODO Auto-generated constructor stub
	}

	@Override
	public void draw() {
		if(isWhite())
		{
			System.out.println("Il pedone e' di colore bianco ed il suo codice Unicode e': u2659");
		}
		else
		{
			System.out.println("Il pedone e' di colore nero ed il suo codice Unicode e': u265f");
		}

	}

	@Override
	boolean canMove(Board board, Spot start, Spot end) {

		//non posso muovere in una casella dove c'Ã¨ un pezzo dello stesso colore
		if(end.piece.isWhite() == this.isWhite()) {
			return false;
		}
		if(start.piece.isWhite()) {
			if((start.y == end.y) && (start.x == (end.x +1))) {
				if(end.piece==null) //verifico che nello spot d'arrivo non siano già presenti pezzi
				{
					return true;
				}
				else 
				{
					return false;
				}
				//TODO movimento in avanti del pedone bianco e possibile cattura in obliquo
			}
		} else if(!start.piece.isWhite()) {
			if((start.y == end.y) && (start.x == (end.x -1))) {
				if(end.piece==null) //verifico che nello spot d'arrivo non siano già presenti pezzi
				{
					return true;
				}
				else 
				{
					return false;
				}
			}
			//TODO movimento in avanti del pedone nero e possibile cattura in obliquo
		}      
		return false;
	}
}
