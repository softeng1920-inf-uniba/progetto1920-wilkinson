package it.uniba.main;

import java.util.ArrayList;

import it.uniba.main.Move.GameStatus;

public class Game {
	
	Board board;	//oggetto scacchiera per la partita in corso
	boolean whiteTurn = true;	//true se turno del bianco, false se turno del nero
	GameStatus status;	//stato della partita (se ACTIVE si continua a giocare, altrimenti si quitta)
	ArrayList<String> allMoves = new ArrayList<String>();	//lista contenente tutte le mosse effettuate nel gioco
	//ArrayList<String> allCaptures = new ArrayList<String>();
	//ArrayList<Move> allMove;
	ArrayList<String> whiteCaptures = new ArrayList<String>();	//lista con i pezzi catturati dal bianco (quindi pezzi neri)
	ArrayList<String> blackCaptures = new ArrayList<String>();	//lista con i pezzi catturati dal nero (quindi pezzi bianchi)
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
		if(allMoves.size()==0) {
			System.out.println("\nL'elenco delle mosse e' vuoto.");
		}
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
 */
	public void showCaptures() {
		if(whiteCaptures.size() ==0) {
			System.out.println("Nessuna cattura fatta dal bianco.");
			} else {
				for (String currentCapture: whiteCaptures) {
					System.out.println("Catture del bianco: ");
					System.out.println(currentCapture);
			}
		}
		
		if(blackCaptures.size() ==0) {
			System.out.println("Nessuna cattura fatta dal nero.");
			} else {
				for (String currentCapture: blackCaptures) {
					System.out.println("Catture del nero: ");
					System.out.println(currentCapture);
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

	
}
