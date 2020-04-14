package it.uniba.main;

public class King extends Piece {

	public King(boolean white) {
		super(white);
		// TODO Auto-generated constructor stub
	}
	
	@Override
	public String draw() {
		if(isWhite())
		{
			return "\u2654";
		}
		else
		{
			return "\u265a";
		}
	}

	@Override
	boolean canMove(Board board, Spot start, Spot end) {
		return false;
	}
}