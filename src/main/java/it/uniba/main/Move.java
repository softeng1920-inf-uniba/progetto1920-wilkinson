package it.uniba.main;

public class Move {
  private AlgebraicNotation interpreter; // interprete della mossa scritta in notazione algebrica abbreviata
  private final Spot start; // casa di partenza
  private final Spot end; // casa di arrivo
  private final Piece pieceMoved; // pezzo che deve eseguire il movimento
  
  /**
   * costruttore dell'oggetto Move
   * 
   * @param command comando da interpretare in mossa
   */
  // Movimento con cattura
  public Move(final String command) {				                                                                        //Probabilmente andrebbe messo private
    this.interpreter = new AlgebraicNotation(command); 				                                                     // Istanzio l'oggetto interpreter
    String algebraicPieceMoved = interpreter.getPieceLetter();                                                    // Stringa in notazione algebrica del pezzo che deve muoversi
    String algebraicFinalSpot = interpreter.getEndSquareId();                                                    // Stringa della posizione d'arrivo in notazione algebrica che deve essere convertita
    this.start = new Spot ();// TODO											                                                      // prende la casella di partenza
    this.pieceMoved = new Piece (setPieceMoved());				                                       	             // prende il pezzo che si muove
    this.end = new Spot (coordinateRow(algebraicFinalSpot) , coordinateColumn(algebraicFinalSpot), null);     // prende la casella d'arrivo
  }
  
	// Metodo che trasforma la colonna passata come lettera nel corrispondente indice numerico che identifica la colonna della matrice scacchiera
  public int coordinateColumn(String columnFinalSpot){
    char charColumn = algebraicFinalSpot.charAt(0);
    int coordinateY = charColumn - 'a' + 1;
    return coordinateY;
  }

  // Metodo che trasforma la riga passata come stringa nell'intero, che identifica la riga della matrice della matrice scacchiera
  public int coordinateRow(String rowFinalSpot){
    int coordinateX = Integer.parseInt(algebraicFinalSpot.substring(1,2));
    return coordinateX;
  }

  //Metodo che acquisisce la dicitura in notazione algebrica ed interpreta il pezzo che deve muoversi
  public void setPieceMoved (){
    switch (algebraicPieceMoved){          
      case null:
        pieceMoved = new Pawn();
        break;
      case 'T':
        pieceMoved = new Rook();
        break;
      case 'C':
        pieceMoved = new Knight();
        break;
      case 'A':
        pieceMoved = new Bishop();
        break;
      case 'D':
        pieceMoved = new Queen();
        break;
      case 'R':
        pieceMoved = new King();
        break;
      default :

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
}