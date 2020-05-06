package it.uniba.main;

import java.util.ArrayList;

import it.uniba.main.Move.GameStatus;

/**
 * DESCRIZIONE
 * rappresenta una partita di scacchi in corso 
 * ha associato uno stato e una scacchiera
 * 
 * RESPONSABILITA' DI CLASSE
 * crea e gestisce la partita in corso, ed esegue una mossa a partire dal comando 
 * in input dell'utente, si occupa dello store delle mosse e delle catture eseguite 
 * e relativa stampa
 * 
 * CLASSIFICAZIONE ECB
 * <<Control>>
 * contiene tutta la logica di gioco e fa vari controlli 
 * sui tipi di mosse da eseguire
 * 
 * @author wilkinson
 */
public class Game {
	private Board board; // oggetto scacchiera per la partita in corso
	private boolean whiteTurn; // true se turno del bianco, false se turno del nero
	private GameStatus status; // stato della partita (se ACTIVE si continua a giocare, altrimenti si quitta)
	private ArrayList<String> allMoves; // lista con le mosse effettuate dal bianco
	private ArrayList<Piece> whiteCaptures; // lista con i pezzi catturati dal bianco (quindi pezzi neri)
	private ArrayList<Piece> blackCaptures; // lista con i pezzi catturati dal nero (quindi pezzi bianchi)
	private boolean isCapture; // true se c'e' una cattura nel turno in corso

	public static final String ANSI_RESET = "\u001B[0m";
	public static final String ANSI_BLACK = "\u001B[30m";
	public static final String ANSI_WHITE = "\u001B[37m";
	public static final String ANSI_BLACK_BACKGROUND = "\u001B[40m";
	public static final String ANSI_WHITE_BACKGROUND = "\u001B[47m";

	/**
	 * Costruttore public di Game
	 */
	public Game() {
		initialize();
	}

	/**
	 * Il metodo initialize, pone inizio alla partita. - Inizia la board, setta lo
	 * status su Attivo - Inizializza gli Array per registrare le farie mosse e
	 * catture
	 */
	public void initialize() {
		board = new Board();
		setStatus(GameStatus.ACTIVE);
		allMoves = new ArrayList<String>();
		whiteCaptures = new ArrayList<Piece>();
		blackCaptures = new ArrayList<Piece>();
		isCapture = false;
		whiteTurn = true;
	}

	/**
	 * stabilisce se la partita e' in corso
	 * 
	 * @return true se in corso, false se terminata
	 */
	public boolean isEnd() {
		if (status != GameStatus.ACTIVE) {
			return true;
		} else {
			return false;
		}
	}

	/**
	 * currentGame e' il metodo che gestisce la partita corrente
	 * 
	 * @param command e' il comando/mossa inserita dall'utente
	 */
	public void currentGame(final String command) {
		Move move = new Move(command, this);
		if (move.getStart() != null && makeMove(move)) {
			if (move.getPieceMoved() instanceof Pawn && ((Pawn) move.getPieceMoved()).isCapturingEnPassant()) {
				// se en passant riscrivo la mossa e la aggiungo allo storico
				String enPassantCommand = command.substring(0, 4) + " e.p.";
				getAllMoves().add(" " + enPassantCommand);
			} else if(move.isCastle())	 {
					String commandMod;
					commandMod = command.replace('O', '0');
				// aggiunto la mossa all'arraylist dello storico mosse
				getAllMoves().add(" " + commandMod);
			} else {
				getAllMoves().add(" " + command);
			}

			// ricalcolo le mosse legali per ogni pezzo
			board.recalLegalMoves();
			board.recalKingMoves();
			// setto false i booleani dei pedoni che regolano l'en passant
			setAllPawnNotEP(getBoard());

			whiteTurn = (!whiteTurn);
		} else {
			System.out.println("\nCOMANDO O MOSSA NON VALIDA");
		}
	}

	/**
	 * Il metodo makeMove e' un booleano che effettua la mossa. Tiene conto di
	 * questi fattori: - che pezzo si deve muovere - se la mossa e' valida - se c'e'
	 * una cattura - se c'e' una cattura en-passant - muove il pezzo da start ad end
	 * 
	 * @param move mossa da effettuare
	 * @return true se la mossa e' stata effettuata, false viceversa
	 */
	private boolean makeMove(Move move) {		
		// controllo se la mossa è un arrocco
		if (move.isCastle()) {
			if (move.makeCastling(this)) {
				return true;
			}
			return false;
		}
		
		if (!move.getInterpreter().isGoodMove()) {
			return false;
		}

		// spot di partenza e arrivo derivati dall'interpretazione di move
		Spot start = getBoard().getSpot(move.getStart().getX(), move.getStart().getY());
		Spot end = getBoard().getSpot(move.getEnd().getX(), move.getEnd().getY());

		// se lo spot di partenza e' vuoto, non e' stato trovato -> mossa illegale
		if (start == null) {
			return false;
		}

		// cerca se sulla scacchiera c'e' stata una cattura
		searchForCapture(start, end);

		// controllo se il pezzo da catturare e' il re 
		if (!end.isEmpty() && end.getPiece() instanceof King) {
			return false;
		}

		// gestisce il caso in cui ci sia una cattura
		if (isCapture) {
			// controlla se nel comando c'e' la x
			if (checkIfIsCapture(move.getInterpreter())) {
				addCapture(); // aggiunge la cattura all'array corrispondente

				// controllo se c'e' una cattura en passant
				if (start.getPiece() instanceof Pawn && ((Pawn) start.getPiece()).isCapturingEnPassant()) {
					// svuoto la casa dell'en passant
					getBoard().getSpot(start.getX(), end.getY()).setPiece(null);
				} else if (checkIfEnPassant(move.getInterpreter())) {
					return false;
				}
			} else {
				// se c'e' una cattura ma l'utente non ha scritto la x
				return false;
			}
		} else {
			if (checkIfIsCapture(move.getInterpreter())) {
				return false;
			}
		}

		// controllo se il pezzo non e' mai stato mosso
		if (start.getPiece().isMoved() == false) {
			// lo setto come mosso
			start.getPiece().setAsMoved();
			// se e' un pedone mai mosso setto che e' possibile catturarlo en passant il
			// prossimo turno
			if (start.getPiece() instanceof Pawn) {
				((Pawn) start.getPiece()).setPossibleEnPassantCapture(true);
			}
		}

		// muovo il pezzo e svuoto la casa di partenza
		end.setPiece(start.getPiece());
		start.setPiece(null);

		return true;
	}

	/**
	 * aggiunge l'eventuale cattura nell'arraylist corrispondente
	 * 
	 */
	private void addCapture() {
		for (int i = 0; i < 8; i++) {
			for (int j = 0; j < 8; j++) {
				Spot currentSpot = getBoard().getSpot(i, j);
				if (currentSpot.getPiece() != null && currentSpot.getPiece().isKilled()) {
					if (isWhiteTurn()) {
						whiteCaptures.add(currentSpot.getPiece());
					} else {
						blackCaptures.add(currentSpot.getPiece());
					}
				}
			}
		}
	}

	/**
	 * mostra le mosse giocate durante la partita
	 * 
	 */
	public void showMoves() {
		int moveNumber = 0;
		int turnControl = 0;
		if (!allMoves.isEmpty()) {
			for (String currentMove : allMoves) {
				if (turnControl % 2 == 0) {
					moveNumber++;
					System.out.print("\n" + ANSI_WHITE_BACKGROUND + ANSI_BLACK + moveNumber + ".");
				}
				turnControl++;
				System.out.print(ANSI_WHITE_BACKGROUND + ANSI_BLACK + currentMove + " " + ANSI_RESET);
			}
		}
	}

	/**
	 * mostra i pezzi catturati dalle due fazioni durante la partita
	 * 
	 */
	public void showCaptures() {
		System.out.print(ANSI_WHITE_BACKGROUND + ANSI_BLACK + "Catture del bianco:" + ANSI_RESET);
		if (!whiteCaptures.isEmpty()) {
			for (Piece currentPiece : whiteCaptures) {
				System.out.print(ANSI_WHITE_BACKGROUND + ANSI_BLACK + " " + currentPiece.draw() + " " + ANSI_RESET);
			}
		} else {
			System.out.println(" Nessuna cattura per il bianco.");
		}
		System.out.print(ANSI_BLACK_BACKGROUND + ANSI_WHITE + "\nCatture del nero  :" + ANSI_RESET);
		if (!blackCaptures.isEmpty()) {
			for (Piece currentPiece : blackCaptures) {
				System.out.print(ANSI_BLACK_BACKGROUND + ANSI_WHITE + " " + currentPiece.draw() + " " + ANSI_RESET);
			}
		} else {
			System.out.println(" Nessuna cattura per il nero.");

		}
	}

	/**
	 * ricerca sulla scacchiera se c'e' stata una cattura e setta il pezzo come
	 * killed
	 * 
	 * @param start
	 * @param end
	 */
	private void searchForCapture(Spot start, Spot end) {
		if (!end.isEmpty()) {
			if (end.getPiece() instanceof King) {
				isCapture = false;
			} else {
				isCapture = true;
				end.getPiece().setAsKilled();
			}
		} else {
			if (start.getPiece() instanceof Pawn && ((Pawn) start.getPiece()).isCapturingEnPassant()) {
				isCapture = true;
				getBoard().getSpot(start.getX(), end.getY()).getPiece().setAsKilled();
			} else {
				isCapture = false;
			}
		}

	}

	/**
	 * Il metodo setAllPawnNotEP setta false la possibile cattura en-passant di
	 * tutti i pedoni ogni turno
	 * 
	 * @param board
	 */
	private void setAllPawnNotEP(Board board) {
		for (int i = 0; i < 8; i++) {
			for (int j = 0; j < 8; j++) {
				Spot currentSpot = getBoard().getSpot(i, j);
				if (currentSpot.getPiece() instanceof Pawn) {
					if (((Pawn) currentSpot.getPiece()).isWhite() != isWhiteTurn()) {
						((Pawn) currentSpot.getPiece()).setPossibleEnPassantCapture(false);
					}
					((Pawn) currentSpot.getPiece()).setCapturingEnPassant(false);
				}
			}
		}
	}

	/**
	 * Il metodo checkIfIsCapture e' un booleano. Controlla se il comando e' una
	 * cattura Il metodo: - @return true se e' una cattura - @return false viceversa
	 * 
	 * @param check
	 */
	private boolean checkIfIsCapture(AlgebraicNotation check) {
		if (check.isCapture()) {
			return true;
		}
		return false;
	}

	/**
	 * Il metodo checkIfEnPassant e' un booleano. Controlla se il comando e' una
	 * cattura en-passant
	 * 
	 * Il metodo: - @return true se la cattura e' en-passant - @return false
	 * viceversa
	 * 
	 * @param check
	 */
	public boolean checkIfEnPassant(AlgebraicNotation check) {
		if (check.isEnPassant()) {
			return true;
		}
		return false;
	}

	/**
	 * toString e' il metodo che restituisce delle stringhe. Indicano il turno
	 * - @return (Turno del bianco) se e' il turno del bianco - @return (Turno del
	 * nero) se e' il turno del nero
	 */
	public String toString() {
		if (this.whiteTurn) {
			return ANSI_WHITE_BACKGROUND + ANSI_BLACK + "(Turno del bianco)" + ANSI_RESET;
		} else {
			return ANSI_BLACK_BACKGROUND + ANSI_WHITE + "(Turno del nero)" + ANSI_RESET;
		}
	}

	// Getters & Setters
	public GameStatus getStatus() {
		return status;
	}

	public void setStatus(GameStatus status) {
		this.status = status;
	}

	public Board getBoard() {
		return board;
	}

	public void setBoard(Board board) {
		this.board = board;
	}

	public boolean isWhiteTurn() {
		return whiteTurn;
	}

	public void setWhiteTurn(boolean whiteTurn) {
		this.whiteTurn = whiteTurn;
	}

	public boolean isCapture() {
		return isCapture;
	}

	public void setCapture(boolean isCapture) {
		this.isCapture = isCapture;
	}

	public ArrayList<String> getAllMoves() {
		return allMoves;
	}

	public void setAllMoves(ArrayList<String> allMoves) {
		this.allMoves = allMoves;
	}

	public ArrayList<Piece> getWhiteCaptures() {
		return whiteCaptures;
	}

	public void setWhiteCaptures(ArrayList<Piece> whiteCaptures) {
		this.whiteCaptures = whiteCaptures;
	}

	public ArrayList<Piece> getBlackCaptures() {
		return blackCaptures;
	}

	public void setBlackCaptures(ArrayList<Piece> blackCaptures) {
		this.blackCaptures = blackCaptures;
	}
}
