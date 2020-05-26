package it.uniba.logic;

import java.util.ArrayList;

/**
 * <body>
 * <h2>DESCRIZIONE</h2>
 * La classe rappresenta un pezzo degli scacchi generico. ogni pezzo <br>
 * ha associato un colore, uno stato associato alla cattura, uno stato riferito <br>
 * a se e' stato mosso o meno e una lista di mosse legali <br>
 *
 * <h2>RESPONSABILITA' DI CLASSE</h2>
 * la classe e' astratta in quanto generica, si occupa di restituire<br>
 * l'unicode del pezzo nel momento di stampa a video,<br>
 * ed e' responsabile dello store delle mosse legali del pezzo sulla scacchiera<br>
 *
 * <h2>CLASSIFICAZIONE ECB</h2>
 * <strong>Entity</strong><br>
 * Componente formante il gioco degli scacchi, <br>
 * che deriva dal concetto concreto di "pezzo"
 * </body>
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
	 * @param whiteIo true se pezzo bianco, false se pezzo nero
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
	 * ricalcola le mosse del pezzo tenendo conto delle minacce future
	 *
	 * @param board
	 */
	void recalculateMoves(final Board board) {
		ArrayList<Move> movesCopy = new ArrayList<Move>();
		ArrayList<Move> movesToRemove = new ArrayList<Move>();

		if (!this.getLegalMoves().isEmpty()) {
			for (Move currentMove : this.getLegalMoves()) {
				movesCopy.add(currentMove);
			}

			for (Move currentMove : movesCopy) {
				if (board.kingUnderAttackNext(currentMove.getStart(), currentMove.getEnd())) {
					movesToRemove.add(currentMove);
				}
			}

			this.getLegalMoves().removeAll(movesToRemove);
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
	protected abstract boolean canMove(Board board, Spot start, Spot end);

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
	 * @param whiteIo true se pezzo bianco, false se pezzo nero
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
	 * setta il pezzo come non catturato
	 *
	 */
	public void setAsNotKilled() {
		this.killed = false;
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
}
