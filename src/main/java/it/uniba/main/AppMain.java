package it.uniba.main;

import java.util.Scanner;

import it.uniba.logic.Game;
import it.uniba.logic.Move.GameStatus;

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
		Game game = new Game();
		game.setStatus(GameStatus.NOT_STARTED);

		String userChoiceGame; // memorizza le scelte dell'utente durante il gioco, ossia i comandi per il
		// movimento dei pezzi
		String userChoiceMenu; // Memorizza le scelte che vengono effettuate dall'utente nel menu'
		boolean quitGame = false; // Flag per gestire l'uscita dal programma
		boolean notFirstMatch = false; // true se non e' il primo lancio di 'play'
		boolean confirmed = true;
		System.out.println("\n**BENVENUTO NEL GIOCO DEGLI SCACCHI**");
		System.out.println("(Digitare 'help' per visualizzare la lista dei comandi disponibili)");

		while (!quitGame) {
			if (!game.isEnd()) {
				System.out.print("\n" + game);
			}
			System.out.print("\nInserire comando/mossa ('help' per elenco)\n > ");
			userChoiceMenu = scanner().nextLine();
			userChoiceGame = userChoiceMenu;

			switch (userChoiceMenu.toLowerCase()) {
			case "help":
				helpPrint();
				break;
			case "play":
				if (notFirstMatch) {
					do {
						System.out.println("\nSei sicuro di voler resettare le partita?");
						confirmPrint();

						userChoiceMenu = scanner().nextLine().toLowerCase();

						if (userChoiceMenu.equals("si")) {
							System.out.println("...ripristino partita");
							confirmed = true;
						} else if (userChoiceMenu.equals("no")) {
							confirmed = false;
						} else {
							System.out.println("\nCONFERMA NON VALIDA\n");
							confirmed = false;
						}
					} while (!userChoiceMenu.equals("si") && !userChoiceMenu.equals("no"));
				}
				if (confirmed) {
					startPrint();
					game = new Game();
					notFirstMatch = true;
					confirmed = false;
				}
				break;
			case "board":
				if (!game.printBoard()) {
					System.out.print("\nCOMANDO NON VALIDO A PARTITA NON IN CORSO\n");
				}
				break;
			case "moves":
				if (!game.showMoves()) {
					System.out.print("\nCOMANDO NON VALIDO A PARTITA NON IN CORSO\n");
				}
				break;
			case "captures":
				if (!game.showCaptures()) {
					System.out.print("\nCOMANDO NON VALIDO A PARTITA NON IN CORSO\n");
				}
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
					if (!game.currentGame(userChoiceGame)) {
						System.out.println("\nCOMANDO O MOSSA NON VALIDA");
					}
				}
				break;
			}
		}
		scanner().close();
	}

	private static Scanner scanner() {
		return new Scanner(System.in, "UTF-8");
	}

	private static void confirmPrint() {
		System.out.println(">si        ::  conferma");
		System.out.println(">no        ::  annulla");
		System.out.print("> ");
	}

	private static void helpPrint() {
		System.out.println("\n COMANDI ");
		System.out.println(">play     ::  inizia/ripristina la partita");
		System.out.println(">board    ::  mostra al scacchiera");
		System.out.println(">moves    ::  mostra lo storico delle mosse giocate");
		System.out.println(">captures ::  mostra i pezzi catturati");
		System.out.println(">quit     ::  esci dalla partita");
	}

	private static void startPrint() {
		System.out.println("\n**INIZIO PARTITA**\n");
		System.out.println("Le pedine si muovono usando la notazione algebrica abbreviata.");
	}
}
