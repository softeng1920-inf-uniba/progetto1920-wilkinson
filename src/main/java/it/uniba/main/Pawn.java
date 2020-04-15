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