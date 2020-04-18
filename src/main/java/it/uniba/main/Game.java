package it.uniba.main;

import java.util.ArrayList;

import it.uniba.main.Move.GameStatus;

public class Game {
	Board board; // oggetto scacchiera per la partita in corso
	boolean whiteTurn = true; // true se turno del bianco, false se turno del nero
	GameStatus status; // stato della partita (se ACTIVE si continua a giocare, altrimenti si quitta)
	ArrayList<String> allMoves; // lista con le mosse effettuate dal bianco
	ArrayList<Piece> whiteCaptures; // lista con i pezzi catturati dal bianco (quindi pezzi neri)
	ArrayList<Piece> blackCaptures; // lista con i pezzi catturati dal nero (quindi pezzi bianchi)
	boolean isCapture = false;

	/**
	 * Costruttore pubblic di Game
	 */
	public Game() {
		initialize();
	}

	/**
	 * Il metodo initialize, pone inizio alla partita. 
	 * - Inizia la board, 
	 * - setta lo
	 * status su Attivo 
	 * - Inizializza gli Array per registrare le farie mosse e catture
	 */
	public void initialize() {
		board = new Board();
		setStatus(GameStatus.ACTIVE);
		allMoves = new ArrayList<String>();
		whiteCaptures = new ArrayList<Piece>();
		blackCaptures = new ArrayList<Piece>();
	}

	/**
	 * Il metodo isEnd: 
	 * - @return true se la partita e' terminata 
	 * - @return false se la partita e' ancora in corso
	 */
	public boolean isEnd() {
		if (status != GameStatus.ACTIVE) {
			return true;
		} else {
			return false;
		}
	}

	/**
	 * currentGame è il metodo che gestisce la partita corrente
	 * 
	 * @param command è il comando/mossa inserita dall'utente
	 */
	public void currentGame(String command) {
		Move move = new Move(command, this);
		if (move.getStart() != null && makeMove(move)) {
			allMoves.add(move.getPieceMoved().draw()+ " " + command);
			whiteTurn = (!whiteTurn);
		} else {
			System.out.println("\nCOMANDO O MOSSA NON VALIDA");
		}
	}

	/**
	 * Il metodo makeMove è un booleano che effettua la mossa. 
	 * Tiene conto di questi fattori: 
	 * - che pezzo si deve muovere 
	 * - se la mossa e' valida 
	 * - se c'e' una cattura 
	 * - se c'e' una cattura en-passant 
	 * - muove il pezzo da start ad end
	 * 
	 * @param move mossa da effettuare
	 * @return true se la mossa e' stata effettuata, false viceversa
	 */
	private boolean makeMove(Move move) {
		isCapture = false;
		Spot start = board.getSpot(move.getStart().getX(), move.getStart().getY());
		Spot end = board.getSpot(move.getEnd().getX(), move.getEnd().getY());
		searchForCapture(getBoard());

		/*
		 * Controllo: - se c'e' un pezzo nella casa di partenza - se il pezzo da muovere
		 * puo' essere mosso in questo turno - se la mossa e' scritta in notazione
		 * corretta
		 */
		if (start.getPiece() != null && start.getPiece().isWhite() == this.whiteTurn && move.getInterpreter().isGoodMove()) {
			
			// effettuo il movimento
			if (start.getPiece().canMove(getBoard(), start, end)) {

				/*
				 * Controllo: - se la mossa e' una cattura - se nel comando c'e' il simbolo 'x'
				 * - se la cattura e' en-passant
				 */
				if (checkIfIsCapture(move.getInterpreter())) {
					if (isCapture) {
						if (start.getPiece() instanceof Pawn) {
							if (((Pawn) start.getPiece()).isCapturingEnPassant) {
								getBoard().getSpot(start.getX(), end.getY()).setPiece(null);
								if (whiteTurn) {
									whiteCaptures.add(end.getPiece());
								} else {
									blackCaptures.add(end.getPiece());
								}
								end.setPiece(start.getPiece());
								start.setPiece(null);
								end.getPiece().setAsMoved();
								return true;
							} else {
								if (whiteTurn) {
									whiteCaptures.add(end.getPiece());
								} else {
									blackCaptures.add(end.getPiece());
								}
								end.setPiece(start.getPiece());
								start.setPiece(null);
								end.getPiece().setAsMoved();
								return true;
							}
						}
						if (whiteTurn) {
							whiteCaptures.add(end.getPiece());
						} else {
							blackCaptures.add(end.getPiece());
						}
						end.setPiece(start.getPiece());
						start.setPiece(null);
						end.getPiece().setAsMoved();
						return true;
					} else {
						return false;
					}
				} else if (!isCapture) {
					setAllPawnNotEP(getBoard());
					if (start.getPiece() instanceof Pawn) {
						
						/*se il movimento riguarda un pedone ed e' la sua prima mossa,
						*setto a true la possibile cattura en passant
						*/
						if (!start.getPiece().isMoved()) {
							((Pawn) start.getPiece()).setPossibleEnPassantCapture(true);
						}
					}
					end.setPiece(start.getPiece());
					start.setPiece(null);
					end.getPiece().setAsMoved();
					return true;
				}
			}
		}
		return false;
	}

	/** showMoves è il metodo che mostrera' le mosse giocate durante la partita
	 * 
	 */
	public void showMoves() {
		int moveNumber = 0;
		int turnControl = 0;
		if (!allMoves.isEmpty()) {
			for (String currentMove : allMoves) {
				if (turnControl % 2 == 0) {
					moveNumber++;
					System.out.print("\n" + moveNumber + ".");
				}
				turnControl++;
				System.out.print(currentMove + " ");
			}
		}
	}

	/** showCaptures è il metodo che mostrera' le catture fatte durante la partita.
	 * Distingue in:
	 * - catture del bianco
	 * - catture del nero
	 */
	public void showCaptures() {
		System.out.print("Catture del bianco: ");
		if (!whiteCaptures.isEmpty()) {
			for (Piece currentPiece : whiteCaptures) {
				System.out.print(currentPiece.draw() + " ");
			}
		}
		System.out.print("\nCatture del nero: ");
		if (!blackCaptures.isEmpty()) {
			for (Piece currentPiece : blackCaptures) {
				System.out.print(currentPiece.draw() + " ");
			}
		}
	}

	/** Il metodo checkIfIsCapture e' un booleano.
	 * Controlla se il comando e' una cattura
	 * Il metodo:
	 * - @return true se e' una cattura
	 * - @return false viceversa
	 * 
	 * @param check
	 */
	private boolean checkIfIsCapture(AlgebraicNotation check) {
		if (check.isCapture()) {
			return true;
		}
		return false;
	}

	/** Il metodo checkIfEnPassant e' un booleano.
	 * Controlla se il comando e' una cattura en-passant
	 * 
	 * Il metodo:
	 * - @return true se la cattura e' en-passant
	 * - @return false viceversa
	 * @param check
	 */
	public boolean checkIfEnPassant(AlgebraicNotation check) {
		if (check.isEnPassant()) {
			return true;
		}
		return false;
	}

	/** searchForCapture è il metodo che controlla se un pezzo può catturare
	 * 
	 * @param board
	 */
	private void searchForCapture(Board board) {
		for (int i = 0; i < 8; i++) {
			for (int j = 0; j < 8; j++) {
				Spot currentSpot = getBoard().getSpot(i, j);
				if (currentSpot.getPiece() != null && currentSpot.getPiece().isKilled()) {
					isCapture = true;
					break;
				}
			}
		}
	}

	/** toString è il metodo che restituisce delle stringhe.
	 * Indicano il turno
	 * - @return (Turno del bianco) se e' il turno del bianco
	 * - @return (Turno del nero) se e' il turno del nero
	 */
	public String toString() {
		if (this.whiteTurn) {
			return "(Turno del bianco)";
		} else {
			return "(Turno del nero)";
		}
	}
	
	// Di seguito GETTER e SETTER

	/** Il metodo setAllPawnNotEP setta false 
	 * la possibile cattura en-passant di tutti i pedoni ogni turno
	 * 
	 * @param board
	 */
	private void setAllPawnNotEP(Board board) {
		for (int i = 0; i < 8; i++) {
			for (int j = 0; j < 8; j++) {
				Spot currentSpot = getBoard().getSpot(i, j);
				if (currentSpot.getPiece() instanceof Pawn) {
					if (((Pawn) currentSpot.getPiece()).isWhite() != whiteTurn) {
						((Pawn) currentSpot.getPiece()).setPossibleEnPassantCapture(false);
						((Pawn) currentSpot.getPiece()).isCapturingEnPassant = false;
					}

				}
			}
		}
	}
	
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
}