package it.uniba.main;

public abstract class Piece {
	boolean killed = false;	//true se pezzo catturato, false altrimenti
	boolean white = false;	//true se pezzo bianco, false altrimenti
	boolean moved = false;  //true se pezzo mosso, false altrimenti
	
	/**costruttore della classe Pezzo
	 * 
	 * @param white true se pezzo bianco, false se pezzo nero
	 */
	public Piece(boolean white) { 
        this.setWhite(white); 
    } 
  
	/**ritorna il valore dell'attributo ehite
	 * 
<<<<<<< HEAD
	 * @return white true se il pezzo è bianco, false se il pezzo è nero
=======
	 * @return white true se il pezzo Ã¨ bianco, false se il pezzo Ã¨ nero
>>>>>>> 1f7370ad7b83c975c9ea9b883754ec70e01a31f3
	 */
    public boolean isWhite() { 
        return this.white; 
    } 
  
    /**setta il colore del pezzo
     * 
     * @param white true se pezzo bianco, false se pezzo nero
     */
    public void setWhite(boolean white) { 
        this.white = white; 
    } 
  
    /**ritorna il valore dell'attributo killed
     * 
<<<<<<< HEAD
     * @return killed true se è stato catturato, false altrimenti
=======
     * @return killed true se Ã¨ stato catturato, false altrimenti
>>>>>>> 1f7370ad7b83c975c9ea9b883754ec70e01a31f3
     */
    public boolean isKilled() { 
        return this.killed; 
    } 
  
    /**setta il pezzo come catturato
     * 
     */
    public void setAsKilled() { 
        this.killed = true; 
    } 
    
    /**ritorna il valore dell'attributo moved
     * 
<<<<<<< HEAD
     * @return moved true se il pezzo è stato mosso, false altrimenti
=======
     * @return moved true se il pezzo Ã¨ stato mosso, false altrimenti
>>>>>>> 1f7370ad7b83c975c9ea9b883754ec70e01a31f3
     */
    public boolean isMoved() {
    	return this.moved;
    }
    
    /**setta il pezzo come mosso
     * 
     */
    public void setAsMoved() {
    	this.moved = true;
    }
    
    /**metodo che stampa a console il corrispondente carattere Unicode del pezzo
<<<<<<< HEAD
     * stabilendo se è nero o bianco
<<<<<<< HEAD
     */
    public abstract String draw();
=======
     * stabilendo se Ã¨ nero o bianco
     */
    public abstract void draw();
>>>>>>> 1f7370ad7b83c975c9ea9b883754ec70e01a31f3
	
	/**metodo che stabilisce se il pezzo può muoversi (la mossa è legale)
	 * 
	 * @param board scacchiera attuale
	 * @param start casa di partenza
	 * @param end casa di arrivo
	 * @return true se movimento possibile, false se mossa illegale
	 */
	abstract boolean canMove(Board board, Spot start, Spot end);
}