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
		return false;
	}
}

	
