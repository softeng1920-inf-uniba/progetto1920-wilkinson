package it.uniba.main;

import java.util.Scanner;

/**
 * *Il file AppMain.java avvia l'applicazione per giocare a scacchi non va
 * rinominato in quanto il nome innesca diversi processi automatici. <b>DO NOT
 * RENAME</b>
 */
public final class AppMain {

	/**
	 * Costruttore private
	 * 
	 */
	private AppMain() {

	}

	/**
	 * Main del gioco SCACCHI
	 *
	 * @param args argomento command-line.
	 */
	public static void main(final String[] args) {
		Game game;
		String userChoice; // Memorizza le scelte che vengono effettuate dall'utente
		boolean quitFlag = false; // Flag per gestire l'uscita dal programma
		boolean quitGame = false; // Flag per gestire l'uscita dal programma
		System.out.println("\n**BENVENUTO NEL GIOCO DEGLI SCACCHI**\n");
		System.out.println(" COMANDI ");
		System.out.println(">play     :: inizia una nuova partita");
		System.out.println(">exit     :: chiudi il gioco\n");
		Scanner scanner = new Scanner(System.in);

		/*
		 * Il ciclo di immissione comandi continua fino a che l'utente non digita il
		 * comando >quit che setta il flag da true a false, uscendo dal programma. Se il
		 * giocatore sceglie il comando "play" dopo aver già avviato una partita, il
		 * programma fara' partire una nuova partita previa conferma del giocatore.
		 */
		while (quitFlag == false) {
			System.out.print("Inserire il comando che si intende eseguire => ");
			userChoice = scanner.nextLine().toLowerCase();
			switch (userChoice) {
			case "exit":
				System.out.println("Sei sicuro di voler chiudere il gioco?\n");
				System.out.println(" COMANDI ");
				System.out.println(">si        ::  conferma");
				System.out.println(">no        ::  annulla");
				System.out.print("Inserire comando che si intende eseguire => ");
				userChoice = scanner.nextLine().toLowerCase();
				if (userChoice.compareTo("si") == 0) {
					System.out.println("...uscita dal gioco");
					quitFlag = true;
				} else if (userChoice.compareTo("no") == 0) {
					quitFlag = false;
				} else {
					System.out.println("COMANDO NON VALIDO\n");
				}
				break;
			case "play":
				quitGame = false;
				System.out.println("\n**INIZIO PARTITA**\n");
				System.out.println("Le pedine si muovono usando la notazione algebrica abbreviata.");
				System.out.println("(Digitare 'help' per visualizzare la lista dei comandi disponibili)");
				game = new Game();
				while (!game.isEnd() && !quitGame) {
					System.out.print("\n" + game + " \nInserire comando o mossa che si intende eseguire => ");
					userChoice = scanner.nextLine().toLowerCase();
					if (userChoice.compareTo("exit") == 0) {
						userChoice = "quit";
					}
					switch (userChoice) {
					case "help":
						System.out.println("\n COMANDI ");
						System.out.println(">play     ::  ripristina la partita");
						System.out.println(">board    ::  mostra al scacchiera");
						System.out.println(">moves    ::  mostra lo storico delle mosse giocate");
						System.out.println(">captures ::  mostra i pezzi catturati");
						System.out.println(">quit     ::  esci dalla partita");
						break;
					case "play":
						System.out.println("\nSei sicuro di voler iniziare una nuova partita?\n");
						System.out.println(" COMANDI ");
						System.out.println(">si        ::   conferma");
						System.out.println(">no        ::   annulla");
						System.out.print("Inserire comando che si intende eseguire => ");
						userChoice = scanner.nextLine().toLowerCase();
						if (userChoice.compareTo("si") == 0) {
							System.out.println("...ripristino partita");
							game.initialize();
							game.setWhiteTurn(true);
						} else if (userChoice.compareTo("no") == 0) {
							quitGame = false;
						} else {
							System.out.println("COMANDO NON VALIDO\n");
						}
						break;
					case "board":
						game.board.showBoard();
						break;
					case "moves":
						System.out.print("\nSERIE DI MOSSE:");
						game.showMoves();
						break;
					case "captures":
						System.out.println("\nELENCO DELLE CATTURE PER COLORE:");
						game.showCaptures();
						break;
					case "quit":
						System.out.println("\nSei sicuro di voler chiudere la partita?\n");
						System.out.println("\n COMANDI ");
						System.out.println(">si        ::   conferma");
						System.out.println(">no        ::   annulla");
						System.out.print("Inserire comando che si intende eseguire => ");
						userChoice = scanner.nextLine().toLowerCase();
						if (userChoice.compareTo("si") == 0) {
							System.out.println("...ritorno al menu principale");
							quitGame = true;
						} else if (userChoice.compareTo("no") == 0) {
							quitGame = false;
						} else {
							System.out.println("COMANDO NON VALIDO\n");
						}
						break;
					default:
						if (!userChoice.matches("")) {
							game.currentGame(userChoice);
						}
					}
				}
				break;
			default:
				quitFlag = false;
			}
		}
		scanner.close();
	}
}
