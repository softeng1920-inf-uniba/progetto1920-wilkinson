package it.uniba.logic;

import java.util.ArrayList;

/**
 * <body>
 * <h2>DESCRIZIONE</h2>
 * interprete di notazione algebrica abbreviata <br>
 *
 * <h2>RESPONSABILITA' DI CLASSE</h2>
 * interpreta un comando scritto in notazione algebrica abbreviata, <br>
 * capendo che pezzo muovere, la casa finale di movimento sulla scacchiera <br>
 * e gli eventuali simboli associati ad eventi specifici; <br>
 * controlla inoltre se il comando inserito dall'utente e' scritto <br>
 * con una sintassi corretta <br>
 *
 * <h2>CLASSIFICAZIONE ECB</h2>
 * <strong>Control</strong><br>
 * poiche' gestisce l'interpretazione dell'input utente <br>
 * alla base della logica dell'applicazione
 * </body>
 *
 * @author wilkinson
 */
public final class AlgebraicNotation {
	private String pieceLetter; // lettera corrispondente al pezzo
	private ArrayList<String> symbol; // simbolo speciale nel comando
	private String endSquareId; // casa di arrivo del pezzo
	private ArrayList<String> symbolList; // simboli possibili
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
	private static final int AMBIGUITYLENGTH = 3;
	private static final int EPSTRINGLENSHORT = 7;
	private static final int EPSTRINGLENLONG = 9;
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
	 * stringa in input e decide se e' una stringa valida
	 *
	 * @param inCommand stringa da interpretare
	 */
	public AlgebraicNotation(final String inCommand) {
		pieceLetter = "";
		symbol = new ArrayList<String>();
		symbolList = new ArrayList<String>();
		initializeSymbolList();
		this.command = inCommand;
		if (!command.isEmpty()) {
			divideCommand(inCommand);
		}
		isGoodMove = isValidAlgebraicNotation();
	}

	/**
	 * inizializza la lista dei possibili simboli ritrovabili
	 */
	private void initializeSymbolList() { // elenco simboli possibili
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
	 * @param inCommand stringa in input da interpretare
	 */
	private void divideCommand(final String inCommand) {
		String commandInterpreted = inCommand.replace('O', '0');

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
			// il resto della stringa e' la casella di partenza/arrivo
			setEndSquareId(commandInterpreted);
		}
	}

	/**
	 * controllo se il comando riguarda un pedone
	 *
	 * @param inCommand stringa in input da interpretare
	 * @return
	 */
	private boolean isPawn(final String inCommand) {
		// controllo se la mossa e' di un pedone (nessuna lettera iniziale)
		char firstLetter = inCommand.charAt(FIRST);
		if (Character.isUpperCase(firstLetter)) {
			return false;
		}
		return true;
	}

	/**
	 * estrae i simboli contenuti nel comando
	 *
	 * @param inCommand stringa in input
	 */
	private void extractSymbol(final String inCommand) { // estrae l'eventuale simbolo speciale
		int pos = 0;

		for (String currentSymbol : symbolList) {
			if (inCommand.contains(currentSymbol)) {

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

				if (!this.isEnPassant()
						&& inCommand.contains("e.p.")) {
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
	 * @param inCommand   prima stringa contenente il comando da ridurre
	 * @param extracted seconda stringa da sottrarre a command
	 * @return stringa ridotta
	 */
	private String reduceString(final String inCommand, final String extracted) {
		// riduce la stringa di comando eliminando i
		// caratteri gia' estratti

		// controllo se la stringa da sottrarre e' vuota o se contiene un simbolo da
		// trattare diversamente*/
		if (!extracted.isEmpty()
				&& !(extracted.equals("ep"))
				&& !(extracted.equals("++"))) {
			StringBuffer buf = new StringBuffer();
			String newCommand = "";

			for (int i = 0; i < inCommand.length(); i++) {
				char commandToken = inCommand.charAt(i);

				for (int j = 0; j < extracted.length(); j++) {
					char extractedToken = extracted.charAt(j);

					if (commandToken != extractedToken) {
						buf.append(commandToken);
					}
				}
			}
			newCommand = buf.toString();
			return newCommand;
		}
		return inCommand;
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

		if (getSymbol().size() > MAXSYMBOLS) {
			return false;
		}

		if (!this.getPieceLetter().equals("")) {
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
				final int startSquareIndex = 1;
				final int endSquareIndex = 3;
				square = square.substring(startSquareIndex, endSquareIndex); // riduco l'ambiguita'
			}

			switch (square) {
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
		if (isCastleShort()) {
			if (!(command.equals("0-0") || command.equals("O-O"))) {
				return false;
			}
		} else if (isCastleLong()) {
			if (!(command.equals("0-0-0") || command.equals("O-O-O"))) {
				return false;
			}
		} else if (isEnPassant()) {
			if (command.contains("ep") && command.length() != EPSTRINGLENSHORT) {
				return false;
			} else if (command.contains("e.p.") && command.length() != EPSTRINGLENLONG) {
				return false;
			}
		}

		// scompongo il comando in token
		char[] tokens = this.command.toCharArray();

		// controllo la posizione della 'x'
		for (int i = 0; i < MAXCOMMANDLENGTH; i++) {
			// la x non e' seguita da una lettera (colonna della casa)
			if (i < tokens.length - 1 && i >= FIRST && tokens[i] == 'x') {
				if (this.getPieceLetter().equals("")) {
					if (i == FIRST) {
						return false;
					} else if (!(isGoodLetter(tokens[i - 1]) && isGoodLetter(tokens[i + 1]))) {
						return false;
					}
				} else {
					if (!isGoodLetter(tokens[i + 1])) {
						return false;
					}
					if (getEndSquareId().length() == AMBIGUITYLENGTH) {
						if (!(isGoodLetter(tokens[i - 1]) || isGoodDigit(tokens[i - 1]))) {
							return false;
						}
					}
				}
			}
		}
		if (tokens[command.length() - 1] == 'x') {
			return false;
		}
		return true;
	}

	/**controlla se il carattere in input e' una lettera da 'a' ad 'h'
	 *
	 * @param letter
	 * @return
	 */
	boolean isGoodLetter(final char letter) {
		final int aAsciiIndex = 97;
		final int hAsciiIndex = 104;
		if (letter >= aAsciiIndex && letter <= hAsciiIndex) {
			return true;
		}
		return false;
	}

	/**controlla se il carattere in input e' un numero fra 1 e 8
	 *
	 * @param number
	 * @return
	 */
	boolean isGoodDigit(final char number) {
		final int oneAsciiIndex = 49;
		final int eightAsciiIndex = 56;
		if (number >= oneAsciiIndex && number <= eightAsciiIndex) {
			return true;
		}
		return false;
	}

	// Getters & Setters
	public String getPieceLetter() {
		return pieceLetter;
	}

	public ArrayList<String> getSymbol() {
		return symbol;
	}

	public String getEndSquareId() {
		return endSquareId;
	}

	public void setEndSquareId(final String inEndSquareId) {
		this.endSquareId = inEndSquareId;
	}

	public boolean isEnPassant() {
		return isEnPassant;
	}

	public boolean isDoubleCheck() {
		return isDoubleCheck;
	}

	public boolean isCapture() {
		return isCapture;
	}

	public boolean isCheck() {
		return isCheck;
	}

	public boolean isGoodMove() {
		return isGoodMove;
	}

	public boolean isCastleShort() {
		return isCastleShort;
	}

	public boolean isCastleLong() {
		return isCastleLong;
	}
}
