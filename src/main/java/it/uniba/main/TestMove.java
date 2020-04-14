package it.uniba.main;

import java.util.Scanner;

public class TestMove {
	
	public static void main(String[] args) {
		Board board = new Board();
		String command;
		System.out.print("Inserire comando: ");
		try (Scanner scan = new Scanner(System.in)) {
			command = scan.nextLine();
		}
		
		Move move = new Move(command, board);
		
		System.out.println(move.getEnd());
		System.out.println(move.getStart());
		//System.out.println(move.getPieceMoved().draw());
		
	}
}