package it.uniba.main;

import java.util.ArrayList;

/**
 * DESCRIZIONE
 * La classe rappresenta un pezzo degli scacchi generico.
 * ogni pezzo ha associato un colore, uno stato associato alla cattura,
 * uno stato riferito a se e' stato mosso o meno e una lista di mosse legali
 *
 * RESPONSABILITA' DI CLASSE
 * la classe e' astratta in quanto generica, si occupa di restituire l'unicode
 * del pezzo e stabilisce se un pezzo puo' muoversi, quindi se la mossa passata dall'utente e' legale
 * inoltre ricerca tutte le possibili mosse legali del pezzo corrente
 *
 * CLASSIFICAZIONE ECB
 * <<Entity>>
 * Componente formante il gioco degli scacchi, che deriva dal concetto concreto di "pezzo"
 * 
 * @author wilkinson
 */
public abstract class Piece {
	boolean killed = false; // true se pezzo catturato, false altrimenti
	boolean white = false; // true se pezzo bianco, false altrimenti
	boolean moved = false; // true se pezzo mosso, false altrimenti
	private ArrayList<Move> legalMoves;

	/**
	 * costruttore della classe Pezzo
	 * 
	 * @param white true se pezzo bianco, false se pezzo nero
	 */
	public Piece(boolean white) {
		this.legalMoves = new ArrayList<Move>();
		this.setWhite(white);
	}

	/**
	 * metodo che stampa a console il corrispondente carattere Unicode del pezzo
	 * stabilendo se e' nero o bianco
	 */
	public abstract String draw();

	/**
	 * trova tutte le possibili mosse legali del pezzo corrente
	 * 
	 */
	void findLegalMoves(Board board, Spot currentSpot) {
		this.legalMoves.clear();
		for (int i = 0; i < 8; i++) {
			for (int j = 0; j < 8; j++) {
				Spot startSpot = board.getSpot(currentSpot.getX(), currentSpot.getY());
				Spot endSpot = board.getSpot(i, j);
				Move currentMove = new Move(startSpot, endSpot);
				if (this.canMove(board, startSpot, endSpot)) {
					this.legalMoves.add(currentMove);
				}
			}
		}
	}

	/**
	 * metodo che stabilisce se il pezzo puo' muoversi (la mossa e' legale)
	 * 
	 * @param board scacchiera attuale
	 * @param start casa di partenza
	 * @param end   casa di arrivo
	 * @return true se movimento possibile, false se mossa illegale
	 */
	public abstract boolean canMove(Board board, Spot start, Spot end);

	/**stampa di un pezzo e le sue mosse legali
	 * 
	 */
	public String toString() {
		String output = "";
		output += this.draw() + " ";
		if (!legalMoves.isEmpty()) {
			output += legalMoves.get(0).getStart();
		}
		output += " Mosse possibili: \n";
		for (Move cmove: legalMoves) {
			output += cmove + "\n";
		}
		if (this instanceof Pawn) {
			output += "capturing EP: " + ((Pawn)this).isCapturingEnPassant() + "\n";
			output += "capturable EP: " + ((Pawn)this).isPossibleEnPassantCapture() + "\n";
		}
		output += "isKilled: " + this.isKilled() + "\n";
		return output;
	}

	//Getters & Setters
	/**
	 * ritorna il valore dell'attributo white
	 * 
	 * @return white true se il pezzo e' bianco, false se il pezzo e' nero
	 */
	public boolean isWhite() {
		return this.white;
	}

	/**
	 * setta il colore del pezzo
	 * 
	 * @param white true se pezzo bianco, false se pezzo nero
	 */
	public void setWhite(boolean white) {
		this.white = white;
	}

	/**
	 * ritorna il valore dell'attributo killed
	 * 
	 * @return killed true se e' stato catturato, false altrimenti
	 */
	public boolean isKilled() {
		return this.killed;
	}

	/**
	 * setta il pezzo come catturato
	 * 
	 */
	public void setAsKilled() {
		this.killed = true;
	}

	/**
	 * ritorna il valore dell'attributo moved
	 * 
	 * @return moved true se il pezzo e' stato mosso, false altrimenti
	 */
	public boolean isMoved() {
		return this.moved;
	}

	/**
	 * setta il pezzo come mosso
	 * 
	 */
	public void setAsMoved() {
		this.moved = true;
	}
	
	public ArrayList<Move> getLegalMoves() {
		return legalMoves;
	}

	public void setLegalMoves(ArrayList<Move> legalMoves) {
		this.legalMoves = legalMoves;
	}
}
