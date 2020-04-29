package it.uniba.main;

import java.util.Scanner;

public class TestAlgebraicNotation {

	public static void main(String[] args) {
		AlgebraicNotation move;
		String command = "";

		System.out.println("Inserire mossa in notazione algebrica: ");
		try (Scanner scan = new Scanner(System.in)) {
			command = scan.nextLine();
		}

		move = new AlgebraicNotation(command);
		System.out.println(move.toString());
	}
}
