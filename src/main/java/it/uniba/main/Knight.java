package it.uniba.main;

public class Knight extends Piece {

	public Knight(boolean white) {
		super(white);
		// TODO Auto-generated constructor stub
	}
	
	@Override
<<<<<<< HEAD
	public String draw() {
		if(isWhite())
		{
			return "\u2658";
		}
		else
		{
			return "\u265e";
		}
=======
	public void draw() {
		// TODO Auto-generated method stub
		
>>>>>>> 1f7370ad7b83c975c9ea9b883754ec70e01a31f3
	}

	@Override
	boolean canMove(Board board, Spot start, Spot end) {
		return false;
	}
<<<<<<< HEAD
}
=======
}
>>>>>>> 1f7370ad7b83c975c9ea9b883754ec70e01a31f3
