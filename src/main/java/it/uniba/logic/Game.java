package it.uniba.logic;

import java.util.ArrayList;

import it.uniba.logic.Move.GameStatus;

/**
 * <body>
 * <h2>DESCRIZIONE</h2>
 * rappresenta una partita di scacchi in corso ha associato uno <br>
 * stato e una scacchiera <br>
 *
 * <h2>RESPONSABILITA' DI CLASSE</h2>
 * crea e gestisce la partita in corso, ed esegue una <br>
 * mossa a partire dal comando in input dell'utente, si occupa dello store delle <br>
 * mosse e delle catture eseguite e relativa stampa <br>
 *
 * <h2>CLASSIFICAZIONE ECB</h2>
 * <strong>Control</strong><br>
 * poiche' contiene tutta la logica di gioco e fa vari <br>
 * controlli sui tipi di mosse da eseguire
 * </body>
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

	private static final int MAXEP = 4;
	private static final int BOARDDIM = 8;
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
		}
		return false;
	}

	/**
	 * currentGame e' il metodo che gestisce la partita corrente
	 *
	 * @param command e' il comando/mossa inserita dall'utente
	 */
	public boolean currentGame(final String command) {
		Move move = new Move(command, board, whiteTurn);
		if (move.getStart() != null && makeMove(move)) {
			if (move.getPieceMoved() instanceof Pawn && ((Pawn)
					move.getPieceMoved()).isCapturingEnPassant()) {

				// se en passant riscrivo la mossa e la aggiungo allo storico
				String enPassantCommand = command.substring(0, MAXEP) + " e.p.";
				getAllMoves().add(" " + enPassantCommand);
			} else if (move.isCastle()) {
				String commandMod;
				commandMod = command.replace('O', '0');

				// aggiunto la mossa all'arraylist dello storico mosse
				getAllMoves().add(" " + commandMod);
			} else {
				getAllMoves().add(" " + command);
			}

			// ricalcolo le mosse legali per ogni pezzo
			board.searchForKings();
			board.recalLegalMoves();
			board.refineLegalMoves();

			// setto false i booleani dei pedoni che regolano l'en passant
			setAllPawnNotEP();
			whiteTurn = (!whiteTurn);
			return true;
		} else {
			setAllPieceNotKilled();
			return false;
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
	private boolean makeMove(final Move move) {

		// controllo se la mossa e' un arrocco
		if (move.isCastle()) {
			if (move.makeCastling(board, whiteTurn)) {
				return true;
			}
			return false;
		}

		// spot di partenza e arrivo derivati dall'interpretazione di move
		Spot start = getBoard().getSpot(move.getStart().getX(), move.getStart().getY());
		Spot end = getBoard().getSpot(move.getEnd().getX(), move.getEnd().getY());

		// cerca se sulla scacchiera c'e' stata una cattura
		searchForCapture(start, end);

		// controllo se il pezzo da catturare e' il re
		if (!end.isEmpty() && end.getPiece() instanceof King) {
			return false;
		}

		// gestisce il caso in cui ci sia una cattura
		if (isCapture) {

			// controllo se c'e' una cattura en passant
			if (start.getPiece() instanceof Pawn && ((Pawn)
					start.getPiece()).isCapturingEnPassant()) {

				// svuoto la casa dell'en passant
				getBoard().getSpot(start.getX(), end.getY()).setPiece(null);
			} else if (checkIfEnPassant(move.getInterpreter())) {
				return false;
			}

			// controlla se nel comando c'e' la x
			if (checkIfIsCapture(move.getInterpreter())) {
				addCapture(); // aggiunge la cattura all'array corrispondente
			} else {
				// se c'e' una cattura ma l'utente non ha scritto la x
				return false;
			}
		}

		// controllo se il pezzo non e' mai stato mosso
		if (!start.getPiece().isMoved()) {

			// lo setto come mosso
			start.getPiece().setAsMoved();

			// se e' un pedone mai mosso setto che e' possibile catturarlo en passant il
			// prossimo turno
			if ((start.getPiece() instanceof Pawn) && board.isTwoSpotsAhead(start, end)) {
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
		for (int i = 0; i < BOARDDIM; i++) {
			for (int j = 0; j < BOARDDIM; j++) {
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
	public boolean showMoves() {
		if (this.isEnd()) {
			return false;
		} else {
			int moveNumber = 0;
			int turnControl = 0;
			if (!allMoves.isEmpty()) {
				for (String currentMove : allMoves) {
					if (turnControl % 2 == 0) {
						moveNumber++;
						System.out.print("\n" + ANSI_WHITE_BACKGROUND
								+ ANSI_BLACK + moveNumber + ".");
					}
					turnControl++;
					System.out.print(ANSI_WHITE_BACKGROUND
							+ ANSI_BLACK + currentMove + " " + ANSI_RESET);
				}
				System.out.println();
			} else {
				System.out.print("\nNESSUNA MOSSA ESEGUITA\n");
			}
			return true;
		}
	}

	/**
	 * mostra i pezzi catturati dalle due fazioni durante la partita
	 *
	 */
	public boolean showCaptures() {
		if (this.isEnd()) {
			return false;
		} else {
			System.out.print("\n" + ANSI_WHITE_BACKGROUND + ANSI_BLACK
					+ "Catture del bianco:" + ANSI_RESET);
			if (!whiteCaptures.isEmpty()) {
				for (Piece currentPiece : whiteCaptures) {
					System.out.print(ANSI_WHITE_BACKGROUND + ANSI_BLACK + " "
							+ currentPiece.draw() + " " + ANSI_RESET);
				}
			} else {
				System.out.print(" Nessuna cattura per il bianco.");
			}
			System.out.print(ANSI_BLACK_BACKGROUND + ANSI_WHITE
					+ "\nCatture del nero  :" + ANSI_RESET);
			if (!blackCaptures.isEmpty()) {
				for (Piece currentPiece : blackCaptures) {
					System.out.print(ANSI_BLACK_BACKGROUND + ANSI_WHITE + " "
							+ currentPiece.draw() + " " + ANSI_RESET);
				}
			} else {
				System.out.print(" Nessuna cattura per il nero.");
			}
			System.out.println();
			return true;
		}
	}

	/**
	 * permette la stampa della scacchiera della partita in corso
	 * @return true se la partita e' in corso, false altrimenti
	 */
	public boolean printBoard() {
		if (this.isEnd()) {
			return false;
		} else {
			board.showBoard();
			return true;
		}
	}

	/**
	 * ricerca sulla scacchiera se c'e' stata una cattura e setta il pezzo come
	 * killed
	 *
	 * @param start
	 * @param end
	 */
	private void searchForCapture(final Spot start, final Spot end) {
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
	 */
	private void setAllPawnNotEP() {
		for (int i = 0; i < BOARDDIM; i++) {
			for (int j = 0; j < BOARDDIM; j++) {
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
	 * setta tutti i pezzi sulla scacchiera come non catturati
	 * (in caso di mossa non valida)
	 */
	private void setAllPieceNotKilled() {
		for (int i = 0; i < BOARDDIM; i++) {
			for (int j = 0; j < BOARDDIM; j++) {
				Spot currentSpot = getBoard().getSpot(i, j);
				if (!currentSpot.isEmpty()) {
					currentSpot.getPiece().setAsNotKilled();
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
	private boolean checkIfIsCapture(final AlgebraicNotation check) {
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
	public boolean checkIfEnPassant(final AlgebraicNotation check) {
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
	@Override
	public String toString() {
		if (this.whiteTurn) {
			return ANSI_WHITE_BACKGROUND + ANSI_BLACK + "(Turno del bianco)" + ANSI_RESET;
		} else {
			return ANSI_BLACK_BACKGROUND + ANSI_WHITE + "(Turno del nero)" + ANSI_RESET;
		}
	}

	// Getters & Setters

	/**
	 * Restituisce lo status del gioco
	 * @return status
	 */
	public GameStatus getStatus() {
		return status;
	}

	/**
	 * Setta lo status del gioco
	 * @param inStatus
	 */
	public void setStatus(final GameStatus inStatus) {
		this.status = inStatus;
	}

	/**
	 * Restituisce la scacchiera
	 * @return board
	 */
	public Board getBoard() {
		return board;
	}
	/**
	 * Setta la scacchiera
	 * @param inBoard
	 */
	public void setBoard(final Board inBoard) {
		this.board = inBoard;
	}

	/**
	 * restituisce un booleano che indica il turno
	 * @return whiteTurn
	 */
	public boolean isWhiteTurn() {
		return whiteTurn;
	}

	/**
	 * Setta un booleano che indica il turno
	 * @param inWhiteTurn
	 */
	public void setWhiteTurn(final boolean inWhiteTurn) {
		this.whiteTurn = inWhiteTurn;
	}

	/**
	 * Restituisce un array che ha all'interno i record
	 * delle mosse effettuate in precedenza
	 * @return allMoves
	 */
	public ArrayList<String> getAllMoves() {
		return allMoves;
	}
}
