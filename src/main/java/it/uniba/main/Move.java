package it.uniba.main;

public class Move {
  private AlgebraicNotation interpreter; // interprete della mossa scritta in notazione algebrica abbreviata
  private final Spot start; // casa di partenza
  private final Spot end; // casa di arrivo
  private final Piece pieceMoved; // pezzo che deve eseguire il movimento
  private Piece pieceKilled = null; // eventuale pezzo catturato col movimento
  private Spot coordinateKilling = null; // coordinate in cui avviene la cattura, non sono sicuro che questo serva...

  // Overide del costruttore. Movimento senza cattura
  public Move(String command) {
    this(command, null);
  }

  /**
   * costruttore dell'oggetto Move
   * 
   * @param command comando da interpretare in mossa
   */
  // Movimento con cattura
  public Move(final String command, Spot spotKilling) {				//Probabilmente andrebbe messo private
    this.interpreter = new AlgebraicNotation(command); 				// Istanzio l'oggetto interpreter
    this.start = // TODO											//prende la casella di partenza
    this.pieceMoved = interpreter.getPieceLetter();					//prende il pezzo che si muove
	this.end = interpreter.getEndSquareId();						//prende la casella d'arrivo
	
	//verifico se sta avvenendo una cattura, se true, prendo il pezzo catturato e la posizione della cattura
	if (spotKilling != null) {
	  this.pieceKilled = spotKilling.getPieceKilled();											
      this.coordinateKilling = spotKilling.getCoordinate();									
    }
  }

  // Controlla se si il movimento è una cattura, servirà per le sottoclassi
  public boolean isKilled() {
    if (pieceKilled == null) {
      return false;
	} 
	else {
      return true;
    }
  }
  //Pezzo catturato
  public Piece getPieceKilled() {
    return pieceKilled;
  }
  //Coordinate della cattura
  public Spot getCoordinateKilling() {		//Ho messo Spot, ma in realtà non sono sicuro che debba prenderli da qui
    return coordinateKilling;
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
}