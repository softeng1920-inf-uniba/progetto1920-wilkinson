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
		//controllo se c'e' un pezzo nella casa di partenza, se il pezzo da muovere puo' essere mosso in questo turno e se la mossa e' scritta in notazione corretta
		if(start.getPiece() != null && start.getPiece().isWhite() == this.whiteTurn && move.getInterpreter().isGoodMove) {	
			//effettuo il movimento
			if(start.getPiece().canMove(getBoard(), start, end)) {
				//controllo se la mossa e' una catura e nel comando c'è il simbolo 'x' 
				if(end.getPiece() != null && !checkIfIsCapture(move.getInterpreter())) {	
					return false;
				} else if (checkIfIsCapture(move.getInterpreter())) {
					//TODO aggiungo la cattura all'arraylist delle catture corrispondenti
				}
				//setto a false la possibile cattura en passant di tutti i pedoni ogni turno
				if(start.getPiece() instanceof Pawn) {
					for(int i=0; i<8; i++) {
						for(int j=0; j<8; j++) {
							Spot currentSpot = getBoard().getSpot(i, j);
							if(currentSpot.getPiece() instanceof Pawn) {
								((Pawn)start.getPiece()).setPossibleEnPassantCapture(false);
							}
						}
					}
					//se il movimento riguarda un pedone ed e' la sua prima mossa setto a true la possibile cattura en passant
					if(!start.getPiece().isMoved()) {
						((Pawn)start.getPiece()).setPossibleEnPassantCapture(true);
					}

				}
				//rimpiazzo il pezzo in end con quello in start impostandolo come mosso e setto la casa start come vuota
				end.setPiece(start.getPiece());
				start.setPiece(null);	
				end.getPiece().setAsMoved();
				//TODO aggiungere la mossa all'arraylist delle mosse giocate
				return true;
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
}