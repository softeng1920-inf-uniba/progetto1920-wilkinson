package it.uniba.main;

public abstract class Piece {
	boolean killed = false;	//true se pezzo catturato, false altrimenti
	boolean white = false;	//true se pezzo bianco, false altrimenti
	
	/**metodo che cambia la variabile 'killed' in true se un pezzo è stato catturato
	 * 
	 */
	public void isKilled() {
		//TODO
	}
	
	/**metodo che stabilisce se il pezzo può muoversi (la mossa è legale)
	 * 
	 * @param board scacchiera attuale
	 * @param start casa di partenza
	 * @param end casa di arrivo
	 * @return true se movimento possibile, false se mossa illegale
	 */
	abstract boolean canMove(Board board, Spot start, Spot end);
}
