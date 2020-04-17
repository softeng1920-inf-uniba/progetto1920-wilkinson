package it.uniba.main;

import java.util.ArrayList;

import it.uniba.main.Move.GameStatus;

public class Game {

	Board board;	//oggetto scacchiera per la partita in corso
	boolean whiteTurn = true;	//true se turno del bianco, false se turno del nero
	GameStatus status;	//stato della partita (se ACTIVE si continua a giocare, altrimenti si quitta)
	ArrayList<String> allMoves;	//lista con le mosse effettuate dal bianco
	//TODO ArrayList<String> blackMoves;	//lista con le mosse effettuate dal nero
	ArrayList<Piece> whiteCaptures;	//lista con i pezzi catturati dal bianco (quindi pezzi neri)
	ArrayList<Piece> blackCaptures;	//lista con i pezzi catturati dal nero (quindi pezzi bianchi)
	boolean isCapture = false;


	/**metodo che pone inizio alla partita (inizializzando la board, settando ad ACTIVE lo status, ecc
	 * 
	 */
	Game(){
		initialize();
	}

	public void initialize() { //inizializza la partita
		board = new Board();
		setStatus(GameStatus.ACTIVE);
	}

	public boolean isEnd() { //metodo booleano che restituisce true se la partita è terminata
		if(status != GameStatus.ACTIVE) {
			return true;
		}else{
			return false;
		}
	}

	public void currentGame(String command) { //

		Move move = new Move(command, this);
		if(move.getStart() != null && makeMove (move)) {
			whiteTurn = (!whiteTurn);
		} else {
			System.out.println("Mossa non valida, reinserila");
		}
	}

	/**metodo che effettua la mossa
	 * tiene conto di:
	 * 		- che pezzo si deve muovere?
	 * 		- la mossa è valida?
	 * 		- c'è una cattura?
	 * 		- la mossa è un arrocco?
	 * 		- la mossa se valida va memorizzata
	 * 		- muove il pezzo da start ad end
	 * 
	 * @param move mossa da effettuare
	 * @return true se la mossa è stata effettuata, false altrimenti
	 */
	public boolean makeMove(Move move) {
		Spot start = board.getSpot(move.getStart().getX(), move.getStart().getY());
		Spot end = board.getSpot(move.getEnd().getX(), move.getEnd().getY());
		searchForCapture(getBoard());

		//controllo se c'e' un pezzo nella casa di partenza, se il pezzo da muovere puo' essere mosso in questo turno e se la mossa e' scritta in notazione corretta
		if(start.getPiece() != null && start.getPiece().isWhite() == this.whiteTurn && move.getInterpreter().isGoodMove) {	
			//effettuo il movimento
			if(start.getPiece().canMove(getBoard(), start, end)) {
				//controllo se la mossa e' una cattura e nel comando c'è il simbolo 'x' 
				if(checkIfIsCapture(move.getInterpreter())) {
					if(isCapture) {
						
						if(start.getPiece() instanceof Pawn) {
							if(((Pawn)start.getPiece()).isCapturingEnPassant){
								getBoard().getSpot(start.getX(), end.getY()).setPiece(null);
								end.setPiece(start.getPiece());
								start.setPiece(null);	
								end.getPiece().setAsMoved();
								return true;
							} else {
								return false;
							}
						}
					} else {
						return false;
					}
					
				} else if (!isCapture) {
					setAllPawnNotEP(getBoard());

					if(start.getPiece() instanceof Pawn) {
						//se il movimento riguarda un pedone ed e' la sua prima mossa setto a true la possibile cattura en passant
						if(!start.getPiece().isMoved()) {
							((Pawn)start.getPiece()).setPossibleEnPassantCapture(true);
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

	/**Metodo che mostrera' le mosse giocate durante
	 * la partita.
	 */

	public void showMoves() {
		int moveNumber = 0;
		int turnControl = 0;
		for (String currentMove: allMoves) {
			if(turnControl % 2 == 0) {
				moveNumber++;
				System.out.print("\n" + moveNumber + ".");
			}
			turnControl++;
			System.out.print(currentMove + " ");
		}//end for
	}
	
	public void showCaptures() {

	}

	/**controlla se il comando e' una cattura
	 * 
	 * @param check
	 * @return
	 */
	boolean checkIfIsCapture(AlgebraicNotation check) {
		if(check.isCapture) {
			return true;
		}
		return false;
	}

	/**controlla se il comando e' una cattura
	 * 
	 * @param check
	 * @return
	 */
	boolean checkIfEnPassant(AlgebraicNotation check) {
		if(check.isEnPassant) {
			return true;
		}
		return false;
	}

	void setAllPawnNotEP(Board board) {
		//setto a false la possibile cattura en passant di tutti i pedoni ogni turno
		for(int i=0; i<8; i++) {
			for(int j=0; j<8; j++) {
				Spot currentSpot = getBoard().getSpot(i, j);
				if(currentSpot.getPiece() instanceof Pawn) {
					if(((Pawn)currentSpot.getPiece()).isWhite() != whiteTurn) {
						((Pawn)currentSpot.getPiece()).setPossibleEnPassantCapture(false);
						((Pawn)currentSpot.getPiece()).isCapturingEnPassant = false;
					}

				}
			}
		}
	}

	void searchForCapture(Board board) {
		for(int i=0; i<8; i++) {
			for(int j=0; j<8; j++) {
				Spot currentSpot = getBoard().getSpot(i, j);
				if(currentSpot.getPiece() != null && currentSpot.getPiece().isKilled()) {
					isCapture = true;
					break;
				}
			}
		}
	}


	public String toString() {
		if(this.whiteTurn) {
			return "(Turno del bianco) ";
		} else {
			return "(Turno del nero) ";
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