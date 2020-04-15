package it.uniba.main;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.URISyntaxException;
import java.security.GeneralSecurityException;
import java.util.Scanner;
import it.uniba.sotorrent.GoogleDocsUtils;

/**
 * The main class for the project. It must be customized to meet the project
 * assignment specifications.
 * 
 * <b>DO NOT RENAME</b>
 */
public final class AppMain {

	/**
	 * Private constructor. Change if needed.
	 */
	private AppMain() {

	}


	/**
	 * 	 * This is the main entry of the application.
	 *
	 * @param args The command-line arguments.
	 */
	public static void main(final String[] args) {
		Game game;
		String userChoice; //Memorizza le scelte che vengono effettuate dall'utente
		boolean quitFlag=false; //Flag per gestire l'uscita dal programma
		//TODO int i=1;

		//System.out.println("Current working dir: " + System.getProperty("user.dir"));
		System.out.println("**Benvenuto nel gioco degli Scacchi**\n");	        	
		System.out.println("**Elenco dei comandi disponibili**\n");	        	
		System.out.println(">play     :: inizia una nuova partita");
		System.out.println(">board    :: mostra al scacchiera"); 
		System.out.println(">moves    :: mostra lo storico delle mosse giocate");
		System.out.println(">captures :: mostra i pezzi catturati");
		System.out.println(">quit     :: esci dalla partita");
		
		//Ho ritenuto opportuno rinominare choice in scanner
		Scanner scanner=new Scanner(System.in);
		
		/*Il ciclo di immissione comandi continua fino a che l'utente non digita il comando >quit 
		  che setta il flag da true a false, uscendo dal programma*/
		
		while(quitFlag==false)
		{    
			//TODO Scanner scanner=new Scanner(System.in); 
			System.out.println("Inserire il comando che si intende eseguire:");
			// Permette all'utente di inserire il comando, trasformando eventuali lettere maiuscole in minuscole per una piu' facile gestione dell'input
			userChoice=scanner.nextLine().toLowerCase();
			while (userChoice.compareTo("play")!=0 && userChoice.compareTo("help")!=0 && userChoice.compareTo("board")!=0 && userChoice.compareTo("moves")!=0 && userChoice.compareTo("captures")!=0 && userChoice.compareTo("quit")!=0)
			{
				System.out.println("Il comando inserito non e' corretto, si prega di inserirne nuovamente uno:");
				userChoice=scanner.nextLine().toLowerCase();
			} //Controllo sulla stringa in input
			if (userChoice.compareTo("help")==0) 
			{
				System.out.println("**Elenco dei comandi**\n");	        	
				System.out.println(">play     :: inizia una nuova partita");
				System.out.println(">board    :: mostra al scacchiera"); 
				System.out.println(">moves    :: mostra lo storico delle mosse giocate");
				System.out.println(">captures :: mostra i pezzi catturati");
				System.out.println(">quit     :: esci dalla partita");
			}
			else 
			{
				switch(userChoice) // tutti i vari casi sono da implementare 
				{
				case "play":
					System.out.println("**Inizio partita**\n"); 
					game = new Game();

					while(!game.isEnd()) {
						System.out.println("Inserire comando: ");
						userChoice=scanner.nextLine();

						switch(userChoice) {
						case "board":
							game.board.showBoard();
							break;
						case "quit":
						{
							System.out.println("Sei sicuro di voler chiudere l'applicazione?"
									+ " Inserire> 'si' per confermare, inserire > 'no' per tornare a giocare" );
							userChoice=scanner.nextLine().toLowerCase();
							if(userChoice.compareTo("si")== 0) {
								System.out.println("...uscita dal programma");
								quitFlag = true;
							} else if (userChoice.compareTo("no")== 0){
								quitFlag = false;
							} else {
								System.out.println("Risposta non valida, inserire 'si' oppure 'no'");
							}
							break;			    	
						}
							
						default:
							game.currentGame(userChoice);
							game.board.showBoard();
							
							
						}	
					}
					break;
				case "board":
					System.out.println("visualizzazione scacchiera\n");
					break;
				case "moves":                                           
					System.out.println("");
					break;
				case "captures":
					System.out.println("");
					break;
				case "quit":
				{
					System.out.println("Sei sicuro di voler chiudere l'applicazione?"
							+ " Inserire> 'si' per confermare, inserire > 'no' per tornare a giocare" );
					userChoice=scanner.nextLine().toLowerCase();
					if(userChoice.compareTo("si")== 0) {
						System.out.println("...uscita dal programma");
						quitFlag = true;
					} else if (userChoice.compareTo("no")== 0){
						quitFlag = false;
					} else {
						System.out.println("Risposta non valida, inserire 'si' oppure 'no'");
					}
					break;			    	
				}
				default:
					quitFlag = false;
				}//end switch 
			}
		}//end while
		scanner.close(); //chiusura dello scanner
	}
}