package it.uniba.main;

import java.util.ArrayList;

public class Move {
	private AlgebraicNotation interpreter; // interprete della mossa scritta in notazione algebrica abbreviata
	private Spot start; // casa di partenza
	private Spot end; // casa di arrivo
	private Piece pieceMoved; // pezzo che deve eseguire il movimento
	private boolean isAmbiguity = false;	//caso in cui ci sia ambiguità di movimento

	/**
	 * costruttore dell'oggetto Move
	 * 
	 * @param command comando da interpretare in mossa
	 */
	// Movimento con cattura
	public Move(final String command, Game game) {				                                                                        //Probabilmente andrebbe messo private
		this.interpreter = new AlgebraicNotation(command); 			// Istanzio l'oggetto interpreter

		String algebraicPieceMoved = interpreter.getPieceLetter();                                                    // Stringa in notazione algebrica del pezzo che deve muoversi
		String algebraicFinalSpot = interpreter.getEndSquareId();    // Stringa della posizione d'arrivo in notazione algebrica che deve essere convertita
		
		this.end = extractCoordinates(algebraicFinalSpot);	//estraggo le coordinate di arrivo

		findStartSpotOnBoard(game, getEnd(), algebraicPieceMoved); //estraggo le coordinate di partenza
		
		this.pieceMoved = start.getPiece();		// prende il pezzo che si muove direttamente dallo Spot di partenza

	}

	/**estrae le coordinate della casa di arrivo
	 * 
	 * @param algebraicFinalSpot stringa di coordinate
	 * @return spot di arrivo instanziato come elemento di classe Spot
	 */
	Spot extractCoordinates(String algebraicFinalSpot) {
		Spot endSpot;
		if(algebraicFinalSpot.length() == 2) {	//caso in cui la stringa sia lunga 2 
			endSpot = new Spot(convertCoordinate(algebraicFinalSpot.substring(1, 2)), 
					convertCoordinate(algebraicFinalSpot.substring(0, 1)), null);
			return endSpot;
		} else if(algebraicFinalSpot.length() == 3) { //caso in cui la stringa sia lunga 3 (ambiguità)
			endSpot = new Spot(convertCoordinate(algebraicFinalSpot.substring(2, 3)), 
					convertCoordinate(algebraicFinalSpot.substring(1, 2)), null);
			setAmbiguity(true);
			return endSpot;
		}
		return null;
	}

	/**converte la coordinata in notazione matriciale
	 * 
	 * @param coordinate stringa di coordinate
	 * @return indice di riga-colonna 
	 */
	int convertCoordinate(String coordinate) {
		switch(coordinate) {
		case "a": case "8":
			return 0;
		case "b": case "7":
			return 1;
		case "c": case "6":
			return 2;
		case "d": case "5":
			return 3;
		case "e": case "4":
			return 4;
		case "f": case "3":
			return 5;
		case "g": case "2":
			return 6;
		case "h": case "1":
			return 7;
		default:
			return 8;
		}

	}

	/**ricerca lo spot di partenza nella scacchiera a partire da:
	 * 
	 * @param game.getBoard() scacchiera
	 * @param endSpot punto di arrivo
	 * @param piece lettera del pezzo da muovere
	 * @return spot di partenza
	 */
	void findStartSpotOnBoard(Game game, Spot endSpot, String piece) {
		Piece currentPiece = classPieceMoved(piece);	//tipo di pezzo da muovere (instanziato come elemento della classe

		if(isAmbiguity) {
			/*TODO caso in cui la notazione algebrica sia lunga 3 (quindi più pezzi dello stesso
			 * tipo possano raggiungere la stessa casa
			 */
		} else {
			findCandidates(game, currentPiece, endSpot);	//Spot candidati ad essere spot di partenza
		}
	
		//TODO a partire dalla lista degli Spot candidati selezionare lo spot giusto

	}

	Piece classPieceMoved (String algebraicPiece){
		Piece currentPiece;
		switch (algebraicPiece){          
		case "T":
			currentPiece = new Rook(true);
			break;
		case "C":
			currentPiece = new Knight(true);
			break;
		case "A":
			currentPiece = new Bishop(true);
			break;
		case "D":
			currentPiece = new Queen(true);
			break;
		case "R":
			currentPiece = new King(true);
			break;
		default :
			currentPiece = new Pawn(true);
			break;
		}
		return currentPiece;
	}

	void findCandidates(Game game, Piece piece, Spot end) {
		for(int i=0; i<8; i++) {
			for(int j=0; j<8; j++) {
				Spot start = game.getBoard().getSpot(i, j);
				if(start.getPiece() != null && start.getPiece().canMove(game.getBoard(), start, end) && start.getPiece().isWhite() == game.whiteTurn) {
					this.start = start;
				}
			}
		}
	}

	/**
	 * enumerazione dello stato di gioco (per verificare se la partita è ancora in
	 * corso)
	 * 
	 * @author wilkinson
	 *
	 */
	enum GameStatus {
		ACTIVE, 
		BLACK_WIN, 
		WHITE_WIN, 
		DRAW, 
		FORCED_END
	}


	/*Getters & Setters*/
	public AlgebraicNotation getInterpreter() {
		return interpreter;
	}

	public void setInterpreter(AlgebraicNotation interpreter) {
		this.interpreter = interpreter;
	}

	public Spot getStart() {
		return start;
	}

	public Spot getEnd() {
		return end;
	}

	public Piece getPieceMoved() {
		return pieceMoved;
	}

	public boolean isAmbiguity() {
		return isAmbiguity;
	}

	public void setAmbiguity(boolean isAmbiguity) {
		this.isAmbiguity = isAmbiguity;
	}
}