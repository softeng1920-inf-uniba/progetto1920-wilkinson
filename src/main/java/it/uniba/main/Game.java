package it.uniba.main;

import java.util.ArrayList;

import it.uniba.main.Move.GameStatus;

public class Game {
	
	Board board;	//oggetto scacchiera per la partita in corso
	boolean whiteTurn = true;	//true se turno del bianco, false se turno del nero
	GameStatus status;	//stato della partita (se ACTIVE si continua a giocare, altrimenti si quitta)
	ArrayList<String> allMoves = new ArrayList<String>();	//lista contenente tutte le mosse effettuate nel gioco
	ArrayList<String> allCaptures = new ArrayList<String>();
	//ArrayList<Move> allMove;
	//TODO ArrayList<String> blackMoves;	//lista con le mosse effettuate dal nero
	ArrayList<Piece> whiteCaptures;	//lista con i pezzi catturati dal bianco (quindi pezzi neri)
	ArrayList<Piece> blackCaptures;	//lista con i pezzi catturati dal nero (quindi pezzi bianchi)
	boolean isGood = false;
	
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
		if(move.getStart() != null) {
			if(makeMove (move)) {
				whiteTurn = (!whiteTurn);
				if(whiteTurn) {
					System.out.println("Turno del bianco");
				} else {
					System.out.println("Turno del nero");
				}
				allMoves.add(command); //inserisco la stinga command in allMoves 
				if(move.isCapture()){
					allCaptures.add(command);
				}
			}
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
		
		if(start.getPiece() != null && start.getPiece().isWhite() == this.whiteTurn && move.getInterpreter().isGoodMove) {
			if(start.getPiece().canMove(board, start, end)) {
				end.setPiece(start.getPiece());
				start.setPiece(null);	
				end.getPiece().setAsMoved();
				isGood = true;
			//	allMoves.add(move.toString());
				
				return true;
			} 
		} 
		
		return false;
	}
	

	/**Metodo che mostra le mosse giocate durante
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
	
//TODO
/**	Metodo che mostra le catture effettuate
 * durante la partita 
 
	public void showCaptures() {
		int captureNumber = 0;
		int turnControl = 0;
		for (String currentMove: allCaptures) {
			if(turnControl % 2 == 0) {
				captureNumber++;
				System.out.print("\n" + captureNumber + ".");
			}
			turnControl++;
			System.out.print(currentMove + " ");
		}//end for
	}
	*/
	

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

/*	public void showCaptures() {
		System.out.println("Catture del bianco: ");
		for (Piece currentCapture: whiteCaptures) {
			//TODO System.out.println(currentCapture.draw());
		}
		
		System.out.println("Catture del nero: ");
		for (Piece currentCapture: blackCaptures) {
			//TODO System.out.println(currentCapture.draw());
		}
	}*/
}
