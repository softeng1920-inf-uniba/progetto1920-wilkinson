package it.uniba.main;

public class Pawn extends Piece {
	boolean possibleEnPassantCapture;
	boolean isCapturingEnPassant;

	public Pawn(boolean white) {
		super(white);
		// TODO Auto-generated constructor stub
		possibleEnPassantCapture = false;
		isCapturingEnPassant = false;
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

				else if(start.getX() == 3) {
					if(enPassantCheck(board, start, end)) {
						((Pawn)start.getPiece()).isCapturingEnPassant = true;

						return true;
					}
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
				else if(start.getX() == 4) {
					if(enPassantCheck(board, start, end)) {
						((Pawn)start.getPiece()).isCapturingEnPassant = true;
						((Pawn)end.getPiece()).setAsKilled();
						return true;
					}
				}
			}
			else {
				if(start.piece.isWhite()==!(end.piece.isWhite())) {
					if((start.y==end.y+1 || start.y==end.y-1) && (start.x==end.x-1)){
						((Pawn)end.getPiece()).setAsKilled();
						return true;
					}
				}
			}
		}
		return false;
	}

	boolean enPassantCheck(Board board, Spot start, Spot end) {
		Spot examinedSpot = board.getSpot(start.getX(), end.getY());

		if(start.getPiece() != null && end.getPiece() == null && examinedSpot.getPiece() != null) {
			Piece possibleCapture = examinedSpot.getPiece();
			if((start.getPiece().isWhite() != possibleCapture.isWhite()) && (possibleCapture instanceof Pawn)) {
				if(((Pawn)possibleCapture).isPossibleEnPassantCapture()) {
					possibleCapture.setAsKilled();
					return true;
				}
			}
		}
		return false;
	}

	public boolean isPossibleEnPassantCapture() {
		return possibleEnPassantCapture;
	}

	public void setPossibleEnPassantCapture(boolean possibleEnPassantCapture) {
		this.possibleEnPassantCapture = possibleEnPassantCapture;
	}
}