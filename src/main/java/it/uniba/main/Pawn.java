package it.uniba.main;

public class Pawn extends Piece {
	
	public Pawn(boolean white) {
		super(white);
		// TODO Auto-generated constructor stub
	}

	@Override
	public String draw() {
		if(isWhite())
		{
			return "\u2659";
		}
		else
		{
			return "\u265f";
		}

	}

	@Override
	boolean canMove(Board board, Spot start, Spot end) {
		if(start.piece.isWhite()) {
			if((start.y==end.y) && (start.x ==(end.x+2))){ //possibile movimento di 2 caselle del pedone se non e' stato mai spostato
				//verifico che il pedone bianco non sia stato mai spostato
				if(!isMoved()){
					if(end.piece==null) { //verifico che nello spot d'arrivo non siano gi� presenti pezzi			
						return true;
					}
					else{
						return false;
					} 
				}
				else return false;
			}
			else if((start.y == end.y) && (start.x == (end.x +1))) {
				if(end.piece==null) { //verifico che nello spot d'arrivo non siano gi� presenti pezzi			
					return true;
				}
				else{
					return false;
				}
				//TODO movimento in avanti del pedone bianco e possibile cattura in obliquo
			}
		} else if(!start.piece.isWhite()) {
			if((start.y==end.y) && (start.x ==(end.x-2))){ //possibile movimento di 2 caselle del pedone se non e' stato mai spostato
				//verifico che il pedone nero non sia mai stato spostato
				if(!isMoved()){
					if(end.piece==null) { //verifico che nello spot d'arrivo non siano gi� presenti pezzi			
						return true;
					}
					else{
						return false;
					} 
				}
				else return false;
			}
			else if((start.y == end.y) && (start.x == (end.x -1))) {
				if(end.piece==null) { //verifico che nello spot d'arrivo non siano gi� presenti pezzi
					return true;
				}
				else{
					return false;
				}
			}
			//TODO movimento in avanti del pedone nero e possibile cattura in obliquo
		}      

		return false;
	}
}