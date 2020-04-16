package it.uniba.main;

public class Pawn extends Piece {

	public Pawn(boolean white) {
		super(white);
		// TODO Auto-generated constructor stub
	}

	@Override
<<<<<<< HEAD
	public String draw() {
		if(isWhite())
		{
			return "\u2659";
		}
		else
		{
			return "\u265f";
=======
	public void draw() {
		if(isWhite())
		{
			System.out.println("Il pedone e' di colore bianco ed il suo codice Unicode e': u2659");
		}
		else
		{
			System.out.println("Il pedone e' di colore nero ed il suo codice Unicode e': u265f");
>>>>>>> 1f7370ad7b83c975c9ea9b883754ec70e01a31f3
		}

	}

	@Override
	boolean canMove(Board board, Spot start, Spot end) {
<<<<<<< HEAD
		if(start.piece.isWhite()) {
			if(end.piece==null) {
				if((start.y==end.y) && (start.x ==(end.x+2))){ //possibile movimento di 2 caselle del pedone se non e' stato mai spostato
				//verifico che il pedone bianco non sia stato mai spostato
				if(!isMoved()){
					return true;
				}
					else
						return false;
					} 
				
			else if((start.y == end.y) && (start.x == (end.x +1))) {
					return true;
				}
			}
			else {
				if(start.piece.isWhite()==!(end.piece.isWhite())) {
					if((start.y==end.y+1 || start.y==end.y-1) && (start.x==end.x+1)){
						return true;
					}
				}
			}
				//TODO movimento in avanti del pedone bianco e possibile cattura in obliquo
		} else if(!start.piece.isWhite()) {
			if(end.piece==null) {
				if((start.y==end.y) && (start.x ==(end.x-2))){ //possibile movimento di 2 caselle del pedone se non e' stato mai spostato
				//verifico che il pedone bianco non sia stato mai spostato
				if(!isMoved()){
					return true;
				}
					else
						return false;
					} 
				
			else if((start.y == end.y) && (start.x == (end.x -1))) {
					return true;
				}
			}
			else {
				if(start.piece.isWhite()==!(end.piece.isWhite())) {
					if((start.y==end.y+1 || start.y==end.y-1) && (start.x==end.x-1)){
						return true;
					}
				}
			}
		}
		return false;
	}
}
=======

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
>>>>>>> 1f7370ad7b83c975c9ea9b883754ec70e01a31f3
