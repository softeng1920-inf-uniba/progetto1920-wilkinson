package it.uniba.main;

public class TestBoard {

	public static void main (String[] args) {
		Board board = new Board();

		for(int i=0; i<8; i++) {
			for(int j=0; j<8; j++) {
				Piece piece = board.getSpot(i, j).piece;

				if(piece instanceof Pawn) {
					System.out.print("P");
				}

				if(piece instanceof Bishop) {
					System.out.print("A");
				}

				if(piece instanceof Knight) {
					System.out.print("C");
				}

				if(piece instanceof Rook) {
					System.out.print("T");
				}

				if(piece instanceof Queen) {
					System.out.print("D");
				}

				if(piece instanceof King) {
					System.out.print("R");
				}	
			}
			System.out.println("\n");
		}
	}
}
