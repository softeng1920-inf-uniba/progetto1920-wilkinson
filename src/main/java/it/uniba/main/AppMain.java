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
		String userChoice; //Memorizza le scelte che vengono effettuate dall'utente
		boolean quitFlag=true; //Flag per gestire l'uscita dal programma
		int i=1;
		System.out.println("Current working dir: " + System.getProperty("user.dir"));

		if (args.length > 0) {
			switch (args[0]) {
			case "it":
				System.out.println("Applicazione  avviata.");
				break;

			case "en":
				System.out.println("Application started.");
				break;

			default:
				System.out.println("Specify the language. "
						+ "Languages supported: 'it' or 'en'");
				break;
			}
		} else {
			System.out.println("Using default language 'en'");
			System.out.println("Application - started.\n");
		}
		while(quitFlag==true) // Il ciclo di immisione comandi continua fino a che l'utente non digita il comando >quit che setta il flag da true a false, uscendo dal programma
		{    
			Scanner choice=new Scanner(System.in); 
			System.out.println("Inserire il comando che si intende eseguire:");
			userChoice=choice.nextLine().toLowerCase();// Permette all'utente di inserire il comando, trasformando eventuali lettere maiuscole in minuscole per una piu' facile gestione dell'input
			while (userChoice.compareTo("play")!=0 && userChoice.compareTo("help")!=0 && userChoice.compareTo("board")!=0 && userChoice.compareTo("moves")!=0 && userChoice.compareTo("captures")!=0 && userChoice.compareTo("quit")!=0)
			{
				System.out.println("Il comando inserito non è corretto, si prega di inserirne nuovamente uno:");
				userChoice=choice.nextLine().toLowerCase();
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
					System.out.println("Sei sicuro di voler chiudere l'applicazione? Inserire >true per confermare, >false per torrnare a giocare" );// Da implementare
					quitFlag=false; // Imposto la variabile flag a false per uscire dal ciclo e terminare il programma
					break;			    	
				}
				default:
				} 
			}
		} 
	}
}