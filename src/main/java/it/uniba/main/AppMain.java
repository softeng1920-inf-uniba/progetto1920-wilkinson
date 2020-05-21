package it.uniba.main;

import java.util.Scanner;

/**
 * <body>
 * <h2>DESCRIZIONE</h2>
 * main dell'applicazione scacchi <br>
 *
 * <h2>RESPONSABILITA' DI CLASSE</h2>
 * avvia l'applicazione per giocare a scacchi
 * non va rinominato in quanto l'innesco di diversi processi automatici e'
 * associato in maniera specifica al nome "AppMain.java". <br>
 *
 * <h2>CLASSIFICAZIONE ECB</h2>
 * <strong>Boundary</strong> <br>
 * poiche' e' la classe che comunica con gli attori esterni: cioe' i giocatori. <br>
 * Questa classe e' il punto di accesso al programma e quella che permette ai
 * giocatori di introdurre i comandi in notazione <br>
 * algebrica per muovere i pezzi, e di utilizzare i comandi ausiliari messi a
 * disposizione dal gioco.
 *
 * <h4>- I M P O R T A N T E: - NON RINOMINARE <h>DO NOT RENAME</h></h4>
 * </body>
 *
 * @author wilkinson
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
		String userChoiceGame; // memorizza le scelte dell'utente durante il gioco, ossia i comandi per il
								// movimento dei pezzi
		String userChoiceMenu; // Memorizza le scelte che vengono effettuate dall'utente nel menu'
		boolean quitGame = false; // Flag per gestire l'uscita dal programma
		System.out.println("\n**BENVENUTO NEL GIOCO DEGLI SCACCHI**\n");
		welcomePrint();

		/*
		 * Il ciclo di immissione comandi continua fino a che l'utente non digita il
		 * comando >quit che setta il flag da true a false, uscendo dal programma. Se il
		 * giocatore sceglie il comando "play" dopo aver gia' avviato una partita, il
		 * programma fara' partire una nuova partita previa conferma del giocatore.
		 */
		while (!quitGame) {
			System.out.print("Inserire il comando che si intende eseguire\n > ");
			// gestisce la possibile immissione di caratteri maiuscoli
			userChoiceMenu = scanner().nextLine().toLowerCase();
			switch (userChoiceMenu) {
			case "help":
				welcomePrint();
				break;

			case "quit":
				do {
					confirmPrint();
					userChoiceMenu = scanner().nextLine().toLowerCase();
					if (userChoiceMenu.equals("si")) {
						System.out.println("...uscita dal gioco");
						quitGame = true;
					} else if (userChoiceMenu.equals("no")) {
						quitGame = false;
					} else {
						System.out.println("COMANDO NON VALIDO\n");
					}
				} while (!userChoiceMenu.equals("si") && !userChoiceMenu.equals("no"));
				break;

			case "play":
				quitGame = false;
				startPrint();
				game = new Game();
				while (!game.isEnd() && !quitGame) {
					System.out.print("\n" + game);
					System.out.print(" \nInserire comando o mossa che si intende eseguire\n > ");
					userChoiceMenu = scanner().nextLine();
					// assegno la stringa digitata dall'utente alla variabile
					 // userChoiceGame prima di convertire la stringa in miuscolo
					userChoiceGame = userChoiceMenu;
					// converto in miniscolo per gestire i casi del menu'
					switch (userChoiceMenu.toLowerCase()) {

					case "help":
						helpPrint();
						break;

					case "play":
						do {
							System.out.println("\nSei sicuro di voler "
									+ "iniziare una nuova partita?\n");
							confirmPrint();
							userChoiceMenu = scanner().nextLine().toLowerCase();
							if (userChoiceMenu.equals("si")) {
								System.out.println("...ripristino partita");
								game.initialize();
								game.setWhiteTurn(true);
							} else if (userChoiceMenu.equals("no")) {
								quitGame = false;
							} else {
								System.out.println("COMANDO NON VALIDO\n");
							}
						} while (!userChoiceMenu.equals("si") && !userChoiceMenu.equals("no"));

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
							System.out.println("\nSei sicuro di voler chiudere il gioco?");
							confirmPrint();

							userChoiceMenu = scanner().nextLine().toLowerCase();

							if (userChoiceMenu.equals("si")) {
								System.out.println("...uscita dal gioco");
								quitGame = true;
							} else if (userChoiceMenu.equals("no")) {
								quitGame = false;
							} else {
								System.out.println("COMANDO NON VALIDO\n");
							}
						} while (!userChoiceMenu.equals("si") && !userChoiceMenu.equals("no"));
						break;

					default:
						if (!userChoiceMenu.matches("")) {
							// uso la stringa non convertita in minuscolo per
							// proseguire con l'interpretazione della mossa
							game.currentGame(userChoiceGame);
							if (!game.currentGame(userChoiceGame)) {
								System.out.println("\nCOMANDO O MOSSA NON VALIDA");
							}
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

	private static Scanner scanner() {
		return new Scanner(System.in, "UTF-8");
	}

	private static void welcomePrint() {
		System.out.println("\n COMANDI ");
		System.out.println(">play     :: inizia una nuova partita");
		System.out.println(">help     :: mostra i comandi disponibili");
		System.out.println(">quit     :: chiudi il gioco\n");
	}

	private static void confirmPrint() {
		System.out.println(">si        ::  conferma");
		System.out.println(">no        ::  annulla");
		System.out.print("> ");
	}

	private static void helpPrint() {
		System.out.println("\n COMANDI ");
		System.out.println(">play     ::  ripristina la partita");
		System.out.println(">board    ::  mostra al scacchiera");
		System.out.println(">moves    ::  mostra lo storico delle mosse giocate");
		System.out.println(">captures ::  mostra i pezzi catturati");
		System.out.println(">quit     ::  esci dalla partita");
	}

	private static void startPrint() {
		System.out.println("\n**INIZIO PARTITA**\n");
		System.out.println("Le pedine si muovono usando la notazione algebrica abbreviata.");
		System.out.println("(Digitare 'help' per visualizzare la lista dei comandi disponibili)");
	}
}
