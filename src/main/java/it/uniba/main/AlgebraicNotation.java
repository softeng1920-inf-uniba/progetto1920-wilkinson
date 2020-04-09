package it.uniba.main;

import java.util.ArrayList;


public class AlgebraicNotation {
	String pieceLetter = "";	//lettera corrispondente al pezzo 
	ArrayList<String> symbol = new ArrayList<String>();	//lista di simboli speciali nel comando
	String endSquareId;	//casa di arrivo del pezzo
	ArrayList<String> symbolList = new ArrayList<String>();	//simboli possibili
	boolean isCastle = false;	//controllo se arrocco
	boolean isEnPassant = false;	//controllo se en passant
	boolean isDoubleCheck = false;	//
	
	/**costruttore che fa partire i metodi che dividono e interpretano il comando in notazione algebrica
	 * 
	 * @param command comando in notazione algebrica derivato dallo standard di input (utente)
	 */
	AlgebraicNotation(String command){
		initializeSymbolList();
		divideCommand(command);
	}

	/**inizializza la lista dei simboli speciali ritrovabili nel comando
	 * 
	 */
	void initializeSymbolList() {	//elenco simboli possibili
		symbolList.add("+");
		symbolList.add("x");
		symbolList.add("#");
		symbolList.add("++");
		symbolList.add("e.p.");
		symbolList.add("0-0-0");
		symbolList.add("0-0");
	}

	/**metodo che avvalora (a partire dal comando) gli attributi di classe 
	 * 
	 * @param command comando in notazione algebrica
	 */
	void divideCommand(String command){
		//TODO

	}

	/**controlla se il pezzo da muovere è un pedone 
	 * 
	 * @param command comando in notazione algebrica
	 * @return true se mossa di un pedone, false altrimenti
	 */
	boolean isPawn(String command) {	//controllo se la mossa è di un pedone (nessuna lettera iniziale)
		//TODO
		return false;
	}

	/**metodo che estrare gli eventuali simboli speciali dal comando
	 * 
	 * @param command comando in notazione algebrica
	 */
	void extractSymbol(String command) {	//estrae l'eventuale simbolo speciale 
		//TODO
	}

	/**metodo che toglie dalla stringa comando un altro simbolo o una lista di simboli speciali
	 * 
	 * @param command comando in notazione algebrica
	 * @param extracted simbolo(i) da rimuovere
	 * @return newCommand stringa "differenza"
	 */
	String reduceString(String command, String extracted) {	//riduce la stringa di comando eliminando i caratteri già estratti
		String newCommand = "";
		//TODO
		return newCommand;
		
	}

	/**metodo che stampa a video il comando scomposto (per testarlo) 
	 * 
	 */
	public String toString(){	//stampa a video del comando scomposto
		String output = "";
		//TODO
		return output;
	}
}
