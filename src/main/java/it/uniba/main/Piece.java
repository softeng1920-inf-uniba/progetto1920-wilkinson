package it.uniba.main;

import java.util.ArrayList;

/**
 * DESCRIZIONE
 * La classe rappresenta un pezzo degli scacchi generico. ogni pezzo
 * ha associato un colore, uno stato associato alla cattura, uno stato riferito
 * a se e' stato mosso o meno e una lista di mosse legali
 *
 * RESPONSABILITA' DI CLASSE
 * la classe e' astratta in quanto generica, si occupa
 * di restituire l'unicode del pezzo e stabilisce se un pezzo puo' muoversi,
 * quindi se la mossa passata dall'utente e' legale inoltre ricerca tutte le
 * possibili mosse legali del pezzo corrente
 *
 * CLASSIFICAZIONE ECB <<Entity>>
 * Componente formante il gioco degli scacchi,
 * che deriva dal concetto concreto di "pezzo"
 *
 * @author wilkinson
 */
public abstract class Piece {
	private boolean killed = false; // true se pezzo catturato, false altrimenti
	private boolean white = false; // true se pezzo bianco, false altrimenti
	private boolean moved = false; // true se pezzo mosso, false altrimenti
	private ArrayList<Move> legalMoves;
	private static final int BOARDDIM = 8; // dimensioni della scacchiera

	/**
	 * costruttore della classe Pezzo
	 *
	 * @param white true se pezzo bianco, false se pezzo nero
	 */
	public Piece(final boolean whiteIo) {
		this.legalMoves = new ArrayList<Move>();
		this.setWhite(whiteIo);
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
	void findLegalMoves(final Board board, final Spot currentSpot) {
		this.legalMoves.clear();
		for (int i = 0; i < BOARDDIM; i++) {
			for (int j = 0; j < BOARDDIM; j++) {
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

	// Getters & Setters
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
	public void setWhite(final boolean whiteIo) {
		this.white = whiteIo;
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

	/**
	 * Metodo che restituisce un ArrayList contenente le mosse legali
	 *
	 * @return legalMoves
	 */
	public ArrayList<Move> getLegalMoves() {
		return legalMoves;
	}

	/**
	 * Metodo che salva le mosse legali in un ArrayList
	 *
	 * @param legalMoves
	 */
	public void setLegalMoves(final ArrayList<Move> legalMovesIo) {
		this.legalMoves = legalMovesIo;
	}
}
