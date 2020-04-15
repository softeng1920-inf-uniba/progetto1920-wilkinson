package it.uniba.main;


import java.util.Scanner;

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
		boolean quitGame=false; //Flag per gestire l'uscita dal programma

		System.out.println("**Benvenuto nel gioco degli Scacchi**\n");	        	
		System.out.println(">play     :: inizia una nuova partita");
		System.out.println(">exit     :: chiudi il gioco");
		
		Scanner scanner=new Scanner(System.in);
		
		/*Il ciclo di immissione comandi continua fino a che l'utente non digita il comando >quit 
		  che setta il flag da true a false, uscendo dal programma*/
		
		while(quitFlag==false)
		{    
			//TODO Scanner scanner=new Scanner(System.in); 
			System.out.println("Inserire il comando che si intende eseguire: (play/exit)");
			userChoice=scanner.nextLine().toLowerCase();
		
			switch(userChoice){
				
				case "exit":
					System.out.println("Sei sicuro di voler chiudere il gioco?"
							+ " Inserire> 'si' per confermare, inserire > 'no' per tornare al menu principale" );
					userChoice=scanner.nextLine().toLowerCase();
					if(userChoice.compareTo("si")== 0) {
						System.out.println("...uscita dal gioco");
						quitFlag = true;
						//System.exit(0);
					} else if (userChoice.compareTo("no")== 0){
						quitFlag = false;
					} else {
						System.out.println("Risposta non valida, inserire 'si' oppure 'no'");
					}
					break;		
					
					
				case "play":
					System.out.println("**Inizio partita**\nE' possibile digitare 'help' per ottenere la lista dei comandi disponibili"
							+"\nLe pedine si muovono usando la notazione algebrica"); 
					game = new Game();

					while(!game.isEnd() && !quitGame) {
						System.out.println("Inserire comando: ");
						userChoice=scanner.nextLine().toLowerCase();
						if (userChoice.compareTo("exit")==0){
							userChoice = "quit";
						};
						switch(userChoice) {
						
						case "help":
							System.out.println(">board    :: mostra al scacchiera"); 
							System.out.println(">moves    :: mostra lo storico delle mosse giocate");
							System.out.println(">captures :: mostra i pezzi catturati");
							System.out.println(">quit     :: esci dalla partita");
							break;
							
						case "board":
							game.board.showBoard();
							break;
						case "moves":                                           
							System.out.println("MOVES..."); //TODO da implementare
							break;
						case "captures":
							System.out.println("CAPTURES..."); //TODO da implementare
							break;
															
						case "quit":
						{
							System.out.println("Sei sicuro di voler chiudere la partita?\n"
									+ "Inserire> 'si' per confermare e tornare al menu principale, inserire > 'no' per continuare a giocare" );
							userChoice=scanner.nextLine().toLowerCase();
							if(userChoice.compareTo("si")== 0) {
								System.out.println("...ritorno al menu principale");
								quitGame = true;
							//	System.exit(0);
							} else if (userChoice.compareTo("no")== 0){
								quitGame = false;
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
					break;//fine del caso "play"
				
			
				default:
					quitFlag = false;
				}//end switch 
			
		}//end while
		scanner.close(); //chiusura dello scanner
	}
}