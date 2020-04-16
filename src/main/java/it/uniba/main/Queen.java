package it.uniba.main;

public class Queen extends Piece {

	public Queen(boolean white) {
		super(white);
		// TODO Auto-generated constructor stub
	}
	
	@Override
<<<<<<< HEAD
	public String draw() {
		if(isWhite())
		{
			return "\u2655";
		}
		else
		{
			return "\u265b";
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
