package it.uniba.main;

import java.util.Scanner;

public class TestMove {
	
	public static void main(String[] args) {
		Game game = new Game();
		String command;
		System.out.print("Inserire comando: ");
		try (Scanner scan = new Scanner(System.in)) {
			command = scan.nextLine();
		}
		
		Move move = new Move(command, game);
		
		System.out.println(move.getEnd());
		System.out.println(move.getStart());
		move.getPieceMoved().draw();
		
	}
}