package it.uniba.main;

import java.util.ArrayList;

/**
 * interpreta un comando scritto in notazione algebrica abbreviata
 * 
 * @author wilkinson
 *
 */
public final class AlgebraicNotation {
	private String pieceLetter; // lettera corrispondente al pezzo
	private ArrayList<String> symbol; // simbolo speciale nel comando
	private String endSquareId; // casa di arrivo del pezzo
	private static ArrayList<String> symbolList; // simboli possibili
	private String command;
	private boolean isCastleShort = false;
	private boolean isCastleLong = false;
	private boolean isEnPassant = false;
	private boolean isDoubleCheck = false;
	private boolean isCapture = false;
	private boolean isCheck = false;
	private boolean isGoodMove = false;
	// costanti per lettera corrispondente al pezzo
	private static final int FIRST = 0;
	private static final int PIECELETTERINDEX = 1;
	// costanti per le lunghezze delle stringhe
	private static final int MINLENGTHENDSQ = 1;
	private static final int MAXLENGTHENDSQ = 3;
	private static final int MAXSYMBOLS = 2;
	private static final int ENPASSANTLENGTH = 3;
	// costanti per le posizioni nell'arraylist di tutti i simboli possibili
	private static final int CHECKINDEX = 0;
	private static final int CAPTUREINDEX = 1;
	private static final int DOUBLECHECKSPOTS = 2;
	private static final int DOUBLECHECKINDEX = 3;
	private static final int ENPASSANTINDEX = 4;
	private static final int FIRSTCASTLING = 5;
	private static final int CASTLELONGINDEX = 5;
	private static final int CASTLESHORTINDEX = 6;
	private static final int MAXCOMMANDLENGTH = 9;

	/**
	 * costruttore, inizializza i membri, l'arraylist dei simboli, interpreta la
	 * stringa in input e decide se è una stringa valida
	 * 
	 * @param command stringa da interpretare
	 */
	AlgebraicNotation(final String command) {
		pieceLetter = "";
		symbol = new ArrayList<String>();
		symbolList = new ArrayList<String>();
		initializeSymbolList();
		this.command = command;
		divideCommand(command);
		isGoodMove = isValidAlgebraicNotation();
	}

	/**
	 * inizializza la lista dei possibili simboli ritrovabili
	 */
	private static void initializeSymbolList() { // elenco simboli possibili
		symbolList.add("+");
		symbolList.add("x");
		symbolList.add("#");
		symbolList.add("++");
		symbolList.add("ep");
		symbolList.add("0-0-0");
		symbolList.add("0-0");
	}

	/**
	 * interpreta la stringa in input avvalorando gli attributi di classe
	 * 
	 * @param command stringa in input da interpretare
	 */
	private final void divideCommand(final String command) {
		String commandInterpreted = command.replace('O', '0');

		if (!isPawn(commandInterpreted)) { // controllo lettera se non pedone
			this.pieceLetter = commandInterpreted.substring(FIRST, PIECELETTERINDEX);
			commandInterpreted = reduceString(commandInterpreted, getPieceLetter());
		}

		extractSymbol(commandInterpreted); // estrazione simbolo speciale

		if (isDoubleCheck) { // caso scacco doppio
			commandInterpreted = commandInterpreted.substring(FIRST,
					commandInterpreted.length() - DOUBLECHECKSPOTS);
		}

		for (String currentSymbol : getSymbol()) { // elimino eventuali altri simboli
			commandInterpreted = reduceString(commandInterpreted, currentSymbol);
		}

		if (isEnPassant) { // caso en passant
			// elimino la dicitura e.p.
			setEndSquareId(commandInterpreted.substring(FIRST, ENPASSANTLENGTH));
			setEndSquareId(reduceString(getEndSquareId(), " "));
		} else if (isCastleLong || isCastleShort) {
			// caso arrocco
			setEndSquareId("");
		} else {
			// il resto della stringa è la casella di partenza/arrivo
			setEndSquareId(commandInterpreted);
		}
	}

	/**
	 * controllo se il comando riguarda un pedone
	 * 
	 * @param command stringa in input da interpretare
	 * @return
	 */
	private boolean isPawn(String command) { // controllo se la mossa è di un pedone (nessuna lettera iniziale)
		char firstLetter = command.charAt(FIRST);
		if (Character.isUpperCase(firstLetter)) {
			return false;
		}
		return true;
	}

	/**
	 * estrae i simboli contenuti nel comando
	 * 
	 * @param command stringa in input
	 */
	private void extractSymbol(String command) { // estrae l'eventuale simbolo speciale
		int pos = 0;

		for (String currentSymbol : symbolList) {
			if (command.contains(currentSymbol)) {

				getSymbol().add(currentSymbol);

				if (pos >= FIRSTCASTLING) { // check di simboli che innescano eventi
					if (pos == CASTLELONGINDEX) {
						this.isCastleLong = true;
						break;
					} else if (pos == CASTLESHORTINDEX) {
						this.isCastleShort = true;
						break;
					}
				} else if (pos == ENPASSANTINDEX) {
					this.isEnPassant = true;
				} else if (pos == DOUBLECHECKINDEX) {
					this.isDoubleCheck = true;
					getSymbol().remove(symbolList.get(FIRST));
				} else if (pos == CAPTUREINDEX) {
					this.isCapture = true;
				} else if (pos == CHECKINDEX) {
					this.isCheck = true;
				}

				if (command.contains("e.p.") || command.contains("e.p")) {
					this.isEnPassant = true;
					getSymbol().add("ep");
				}
			}
			pos++;
		}
	}

	/**
	 * riduce la prima stringa togliendo il contenuto della seconda
	 * 
	 * @param command   prima stringa contenente il comando da ridurre
	 * @param extracted seconda stringa da sottrarre a command
	 * @return stringa ridotta
	 */
	private String reduceString(String command, String extracted) { // riduce la stringa di comando eliminando i
		// caratteri gia' estratti

		// controllo se la stringa da sottrarre è vuota o se contiene un simbolo da
		// trattare diversamente*/
		if (!extracted.isEmpty() && !(extracted == symbolList.get(ENPASSANTINDEX))
				&& !(extracted == symbolList.get(DOUBLECHECKINDEX))) {
			String newCommand = "";

			for (int i = 0; i < command.length(); i++) {
				char commandToken = command.charAt(i);

				for (int j = 0; j < extracted.length(); j++) {
					char extractedToken = extracted.charAt(j);

					if (commandToken != extractedToken) {
						newCommand += commandToken;
					}
				}
			}
			return newCommand;
		}
		return command;
	}

	/**
	 * controlla se il comando inserito e' scritto in notazione algebrica abbreviata
	 * ed e' sintatticamente corretto
	 * 
	 * @return true se comando valido, false altrimenti
	 */
	public boolean isValidAlgebraicNotation() {	
		if (!isValidCommand()) {
			return false;
		}
		
		if (isCastleShort()) {
			return getSymbol().get(FIRST).equals("0-0");
		} else if (isCastleLong()) {
			return getSymbol().get(FIRST).equals("0-0-0");
		}

		if (this.getPieceLetter() != "") {
			switch (this.getPieceLetter()) {
			case "A":
			case "T":
			case "C":
			case "D":
			case "R":
				break;
			default:
				return false;
			}
		}

		if (getEndSquareId().length() > MAXLENGTHENDSQ || getEndSquareId().length() <= MINLENGTHENDSQ) {
			return false;
		} else {
			int length = getEndSquareId().length();
			String square = getEndSquareId();

			if (length == MAXLENGTHENDSQ) {
				switch (square.substring(FIRST, PIECELETTERINDEX)) {
				case "a": case "b": case "c": case "d": case "e": case "f": case "g": case "h":
				case "1": case "2": case "3": case "4": case "5": case "6": case "7": case "8":
					break;
				default:
					return false;
				}
				square = reduceString(square, square.substring(FIRST, PIECELETTERINDEX));
			}

			switch(square) {
			case "a1": case "a2": case "a3": case "a4": case "a5": case "a6": case "a7": case "a8":
			case "b1": case "b2": case "b3": case "b4": case "b5": case "b6": case "b7": case "b8":
			case "c1": case "c2": case "c3": case "c4": case "c5": case "c6": case "c7": case "c8":
			case "d1": case "d2": case "d3": case "d4": case "d5": case "d6": case "d7": case "d8":
			case "e1": case "e2": case "e3": case "e4": case "e5": case "e6": case "e7": case "e8":
			case "f1": case "f2": case "f3": case "f4": case "f5": case "f6": case "f7": case "f8":
			case "g1": case "g2": case "g3": case "g4": case "g5": case "g6": case "g7": case "g8":
			case "h1": case "h2": case "h3": case "h4": case "h5": case "h6": case "h7": case "h8":
				break;
			default:
				return false;
			}
		}
		if (getSymbol().size() > MAXSYMBOLS) {
			return false;
		}
		return true;
	}

	/**effettua dei controlli sulla stringa di comando dell'utente
	 * 
	 * @return
	 */
	boolean isValidCommand() {
		if (command.isEmpty()) {
			return false;
		}
		char[] tokens = this.command.toCharArray();
		if (isCastleShort()) {
			if (!command.equals("0-0") || !command.equals("O-O")) {
				return false;
			}
		} else if (isCastleLong()) {
			if (!command.equals("0-0-0") || !command.equals("O-O-O")) {
				return false;
			}
		}
		
		if (tokens.length > MAXCOMMANDLENGTH) {
			return false; // stringa troppo lunga
		}

		for (int i = 0; i < MAXCOMMANDLENGTH; i++) {
			// la x non e' seguita da una lettera (colonna della casa)
			if (i < tokens.length && i > FIRST && tokens[i] == 'x') { 
				if (this.getPieceLetter().equals("")) {
					if (!(isGoodLetter(tokens[i-1]) && isGoodLetter(tokens[i+1]))) {
						return false;
					} 
				} else {
					if (!isGoodLetter(tokens[i+1])) {
						return false;
					}
				}
			}
		}
		return true;
	}
	
	/**controlla se il carattere in input e' una lettera da 'a' ad 'h'
	 * 
	 * @param letter
	 * @return
	 */
	boolean isGoodLetter(char letter) {
		if (letter >= 97 && letter <= 104) {
			return true;
		}
		return false;
	}
	
	/**controlla se il carattere in input e' un numero fra 1 e 8
	 * 
	 * @param number
	 * @return
	 */
	boolean isGoodDigit(char number) {
		if (number >= 49 && number <= 56) {
			return true;
		}
		return false;
	}

	public String toString() {
		String output = "";
		return output += "Pezzo mosso: " + this.getPieceLetter() + "\nCasa d'arrivo: " + this.getEndSquareId()
		+ "\nSimboli: " + this.getSymbol() + "\nMossa legale: " + this.isGoodMove() + "\nCastleShort: " + this.isCastleShort()
		+ "\nCastleLong: " + this.isCastleLong() + "\nIsCapture: " + this.isCapture();
	}

	// Getters & Setters
	public String getPieceLetter() {
		return pieceLetter;
	}

	public void setPieceLetter(String pieceLetter) {
		this.pieceLetter = pieceLetter;
	}

	public ArrayList<String> getSymbol() {
		return symbol;
	}

	public void setSymbol(ArrayList<String> symbol) {
		this.symbol = symbol;
	}

	public String getEndSquareId() {
		return endSquareId;
	}

	public void setEndSquareId(String endSquareId) {
		this.endSquareId = endSquareId;
	}

	public boolean isEnPassant() {
		return isEnPassant;
	}

	public void setEnPassant(boolean isEnPassant) {
		this.isEnPassant = isEnPassant;
	}

	public boolean isDoubleCheck() {
		return isDoubleCheck;
	}

	public void setDoubleCheck(boolean isDoubleCheck) {
		this.isDoubleCheck = isDoubleCheck;
	}

	public boolean isCapture() {
		return isCapture;
	}

	public void setCapture(boolean isCapture) {
		this.isCapture = isCapture;
	}

	public boolean isCheck() {
		return isCheck;
	}

	public void setCheck(boolean isCheck) {
		this.isCheck = isCheck;
	}

	public boolean isGoodMove() {
		return isGoodMove;
	}

	public void setGoodMove(boolean isGoodMove) {
		this.isGoodMove = isGoodMove;
	}

	public boolean isCastleShort() {
		return isCastleShort;
	}

	public void setCastleShort(boolean isCastleShort) {
		this.isCastleShort = isCastleShort;
	}

	public boolean isCastleLong() {
		return isCastleLong;
	}

	public void setCastleLong(boolean isCastleLong) {
		this.isCastleLong = isCastleLong;
	}
}
