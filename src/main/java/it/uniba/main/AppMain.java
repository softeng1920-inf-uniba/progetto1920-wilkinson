package it.uniba.main;

import java.util.Scanner;

/**
 * DESCRIZIONE
 * main dell'applicazione scacchi
 *
 * RESPONSABILITA' DI CLASSE
 * avvia l'applicazione per giocare a scacchi non va
 * rinominato in quanto l'innesco di diversi processi automatici e'
 * associato in maniera specifica al nome "AppMain.java".
 *
 * CLASSIFICAZIONE ECB
 * <<Boundary>>
<<<<<<< HEAD
 * poichÃ© Ã¨ la classe che comunica con gli attori esterni: cioÃ¨ i giocatori.
 * Questa classe Ã¨ il punto di accesso al programma e quella che permette
=======
 * poiché è la classe che comunica con gli attori esterni: cioè i giocatori.
 * Questa classe è il punto di accesso al programma e quella che permette
>>>>>>> eade92e6fdbe9d7da2ff4108da23a2c050dad4fa
 * ai giocatori di introdurre i comandi i notazione algebrica per muovere
 * i pezzi, e di utilizzare i comandi ausiliari messi a disposizione
 * dal gioco.
 *
 * - I M P O R T A N T E: -
 * NON RINOMINARE
 * <h>DO NOT RENAME</h>
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
		String userChoiceGame; // memorizza le scelte  deii comandi per il movimento dei pezzi
		String userChoiceMenu; // Memorizza le scelte che vengono effettuate dall'utente nel menu'
		boolean quitGame = false; // Flag per gestire l'uscita dal programma
		System.out.println("\n**BENVENUTO NEL GIOCO DEGLI SCACCHI**\n");
		System.out.println(" COMANDI ");
		System.out.println(">play     :: inizia una nuova partita");
<<<<<<< HEAD
		System.out.println(">help     :: mostra i comandi disponibili");
		System.out.println(">quit     :: chiudi il gioco\n");
		Scanner scanner = new Scanner(System.in);
=======
		System.out.println(">exit     :: chiudi il gioco\n");
>>>>>>> eade92e6fdbe9d7da2ff4108da23a2c050dad4fa

		/*
		 * Il ciclo di immissione comandi continua fino a che l'utente non digita il
		 * comando >quit che setta il flag da true a false, uscendo dal programma. Se il
		 * giocatore sceglie il comando "play" dopo aver gia' avviato una partita, il
		 * programma fara' partire una nuova partita previa conferma del giocatore.
		 */
<<<<<<< HEAD
		while (quitGame == false) {
=======
		while (!quitFlag) {
>>>>>>> eade92e6fdbe9d7da2ff4108da23a2c050dad4fa
			System.out.print("Inserire il comando che si intende eseguire => ");
			//gestisce la possibile immissione di caratteri maiuscoli
			userChoiceMenu = scanner().nextLine().toLowerCase();
			switch (userChoiceMenu) {
			case "help":
				System.out.println("\nCOMANDI ");
				System.out.println(">play     :: inizia una nuova partita");
				System.out.println(">help     :: mostra i comandi disponibili");
				System.out.println(">quit     :: chiudi il gioco\n");
				break;

			case "quit":
				do {
					System.out.println("Sei sicuro di voler chiudere il gioco?\n");
					System.out.println(" COMANDI ");
					System.out.println(">si        ::  conferma");
					System.out.println(">no        ::  annulla");
					System.out.print("Inserire comando che si intende eseguire => ");
					userChoiceMenu = scanner().nextLine().toLowerCase();
					if (userChoiceMenu.compareTo("si") == 0) {
						System.out.println("...uscita dal gioco");
						quitGame = true;
					} else if (userChoiceMenu.compareTo("no") == 0) {
						quitGame = false;
					} else {
						System.out.println("COMANDO NON VALIDO\n");
					}
				}
				while  ((!(userChoiceMenu.compareTo("si") == 0)
						&& !(userChoiceMenu.compareTo("no") == 0)));
				break;
			case "play":
				quitGame = false;
				System.out.println("\n**INIZIO PARTITA**\n");
				System.out.println("Le pedine si muovono usando la notazione algebrica abbreviata.");
				System.out.println("(Digitare 'help' per visualizzare "
						+ "la lista dei comandi disponibili)");
				game = new Game();
				while (!game.isEnd() && !quitGame) {
					System.out.print("\n" + game + " \nInserire comando "
							+ "o mossa che si intende eseguire => ");
<<<<<<< HEAD
					userChoiceMenu = scanner.nextLine();
					userChoiceGame=userChoiceMenu; //assegno la stringa digitata dall'utente alla variabile userChoigeGame prima di convertire la stringa in miuscolo

					switch (userChoiceMenu.toLowerCase()) { // converto in miniscolo per gestire i casi del menu'
=======
					userChoiceMenu = scanner().nextLine();
					//assegno la scelta ad userChoigeGame prima di convertirla in miuscolo
					userChoiceGame = userChoiceMenu;
					if (userChoiceMenu.compareTo("exit") == 0) {
						userChoiceMenu = "quit";
					}
					//conversione in minuscolo per gestire i casi del menu'
					switch (userChoiceMenu.toLowerCase()) {
>>>>>>> eade92e6fdbe9d7da2ff4108da23a2c050dad4fa
					case "help":
						System.out.println("\n COMANDI ");
						System.out.println(">play     ::  ripristina la partita");
						System.out.println(">board    ::  mostra al scacchiera");
						System.out.println(
								">moves    ::  mostra lo storico delle mosse giocate");
						System.out.println(">captures ::  mostra i pezzi catturati");
						System.out.println(">help     ::  mostra i comandi disponibili");
						System.out.println(">quit     ::  esci dalla partita");
						break;
					case "play":
						do {
							System.out.println("\nSei sicuro di voler "
									+ "iniziare una nuova partita?\n");
							System.out.println(" COMANDI ");
							System.out.println(">si        ::   conferma");
							System.out.println(">no        ::   annulla");
							System.out.print(
									"Inserire comando che si intende eseguire => ");
							userChoiceMenu = scanner().nextLine().toLowerCase();
							if (userChoiceMenu.compareTo("si") == 0) {
								System.out.println("...ripristino partita");
								game.initialize();
								game.setWhiteTurn(true);
							} else if (userChoiceMenu.compareTo("no") == 0) {
								quitGame = false;
							} else {
								System.out.println("COMANDO NON VALIDO\n");
							}
						}
						while ((!(userChoiceMenu.compareTo("si") == 0)
								&& !(userChoiceMenu.compareTo("no") == 0)));

						break;
					case "board":
						game.getBoard().showBoard();
						break;
					case "moves":
						System.out.print("\nSERIE DI MOSSE:");
						game.showMoves();
						System.out.print("\n");
						break;
					case "captures":
						System.out.println("\nELENCO DELLE CATTURE PER COLORE:");
						game.showCaptures();
						System.out.print("\n");
						break;
					case "quit":
						do {
							System.out.println(
									"\nSei sicuro di voler chiudere la partita?");
							System.out.println("\n COMANDI ");
							System.out.println(">si        ::   conferma");
							System.out.println(">no        ::   annulla");
							System.out.print(
									"Inserire comando che si intende eseguire => ");

							userChoiceMenu = scanner().nextLine().toLowerCase();

							if (userChoiceMenu.compareTo("si") == 0) {
								System.out.println("...uscita dal gioco");
								quitGame = true;
							} else if (userChoiceMenu.compareTo("no") == 0) {
								quitGame = false;
							} else {
								System.out.println("COMANDO NON VALIDO\n");
							}
						}

						while ((!(userChoiceMenu.compareTo("si") == 0)
								&& !(userChoiceMenu.compareTo("no") == 0)));
						break;
					default:
						if (!userChoiceMenu.matches("")) {
<<<<<<< HEAD
							game.currentGame(userChoiceGame); //uso la stringa non convertita in minuscolo per proseguire con l'interpretazione della mossa
=======
							/**
							 * uso la stringa non convertita in minuscolo
							 *  per proseguire con l'interpretazione della mossa
							 */
							game.currentGame(userChoiceGame);
>>>>>>> eade92e6fdbe9d7da2ff4108da23a2c050dad4fa
						}
					}
				}
				break;
			default:
				quitGame = false;
			}
		}
		scanner().close();
	}

	/**nuova dichiarazione di scanner per lo standard di input
	 *
	 * @return scanner di input
	 */
	private static Scanner scanner() {
		return new Scanner(System.in, "UTF-8");
	}
}
