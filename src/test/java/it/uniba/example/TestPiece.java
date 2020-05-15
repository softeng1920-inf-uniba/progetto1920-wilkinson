package it.uniba.example;


import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.ArrayList;

import org.junit.jupiter.api.Test;

import it.uniba.main.Bishop;
import it.uniba.main.Board;
import it.uniba.main.King;
import it.uniba.main.Knight;
import it.uniba.main.Move;
import it.uniba.main.Pawn;
import it.uniba.main.Piece;
import it.uniba.main.Rook;



public class TestPiece {
	private static Board board;
	private static Piece genericPiece;
	private static final boolean BLACK = false;
	private static final boolean WHITE = true;
	private static final int ROW_1 = 7;
	private static final int ROW_2 = 6;
	private static final int ROW_3 = 5;
	private static final int ROW_4 = 4;
	private static final int ROW_5 = 3;
	private static final int ROW_6 = 2;
	private static final int ROW_7 = 1;
	private static final int ROW_8 = 0;
	private static final int COL_H = 7;
	private static final int COL_G = 6;
	private static final int COL_F = 5;
	private static final int COL_E = 4;
	private static final int COL_D = 3;
	private static final int COL_C = 2;
	private static final int COL_B = 1;
	private static final int COL_A = 0;

	
	
	
	@Test
	public void testPawnLegalMovements(){
		board = new Board(true);
		ArrayList<Move> pawnLegalMoves = new ArrayList<Move>();

		//One step
		board.getSpot(ROW_7, COL_B).setPiece(new Pawn(BLACK));
		genericPiece = board.getSpot(ROW_7, COL_B).getPiece();
		
		//pawnlegalMoves.add(e);
		//genericPiece.findLegalMoves(board, board.getSpot(ROW_7, COL_B) );
		genericPiece.setLegalMoves(pawnLegalMoves);

		System.out.println(genericPiece.getLegalMoves().toString());
		board.showBoard();

	}
}
