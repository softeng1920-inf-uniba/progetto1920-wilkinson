package it.uniba.main;

/**rappresenta una torre sulla scacchiera
 * 
 * @author wilkinson
 *
 */
public class Rook extends Piece {

	public Rook(boolean white) {
		super(white);
		// TODO Auto-generated constructor stub
	}

	@Override
	/**
	 * Metodo per ottenere l'unicode della torre nera e della torre bianca
	 */
	public String draw() {
		if (isWhite()) {
			return "\u2656";
		} else {
			return "\u265c";
		}
	}

	@Override
	/**
	 * metodo che verifica il movimento legale di una  torre
	 * e che verifica la possibile cattura di un pezzo avversario
	 */
	boolean canMove(Board board, Spot start, Spot end, boolean isWhiteTurn) {
		Rook startPiece = (Rook) start.getPiece();
		Piece endPiece = end.getPiece();

		// turno del bianco
		if (isWhiteTurn) {
			// pezzo in start bianco
			if (startPiece.isWhite()) {
				// nessun pezzo in end
				if (endPiece == null) {
		            // spot di arrivo sulla stessa RIGA dello spot di partenza 
					if (board.isEndRow(start, end) == true) {
					    // nessun pezzo lungo il percorso (riga)
						int i = start.getX();
						    //scorri destra
							if (start.getX() < end.getX()) {
								while(i < end.getX()) {
									i++;
									if (board.getSpot(i, start.getY()) != null) {
										return false;
									}

								}
							// scorri sinistra
							} else {
								while(i > end.getX()) {
									i--;
									if (board.getSpot(i, start.getY()) != null) {
										return false;
									}

								}
							}
					} else {
					        // spot di arrivo sulla stessa COLONNA dello spot di partenza 
					        if (board.isEndColumn(start, end) == true) {
					            // nessun pezzo lungo il percorso della torre (colonna)
					        	int i = start.getY();
					        	    // scorri a sopra
						        	if (start.getY() < end.getY()) {
										while(i < end.getY()) {
											i++;
											if (board.getSpot(start.getX(), i) != null) {
												return false;
											}
	
										}
									// scorri a sotto
									} else {
										while(i > end.getY()) {
											i--;
											if (board.getSpot(start.getX(), i) != null) {
												return false;
											}
	
										}
									}
					        }
					}
				} else {
					// pezzo in end bianco (stesso colore)
					if (endPiece.isWhite()) {
						return false;
					} else {
						//cattura del pezzo
						return true;
					}
				}
			// turno del bianco ma pezzo nero da muovere
			} else {
				return false;
			}

		// turno del nero
		} else {
			// pezzo in start nero
			if (!startPiece.isWhite()) {
				// nessun pezzo in end
				if (endPiece == null) {
					// spot di arrivo sulla stessa RIGA dello spot di partenza 
					if (board.isEndRow(start, end) == true) {
					    // nessun pezzo lungo il percorso (riga)
							int i = start.getX();
							//scorri destra
							if (start.getX() < end.getX()) {
								while(i < end.getX()) {
									i++;
									if (board.getSpot(i, start.getY()) != null) {
										return false; //trovato un pezzo sul percorso, mossa illegale
									}
	
								}
							// scorri sinistra
							} else {
								while(i > end.getX()) {
									i--;
									if (board.getSpot(i, start.getY()) != null) {
										return false; //trovato un pezzo sul percorso, mossa illegale
									}
	
								}
							}
					} else {
					        // spot di arrivo sulla stessa COLONNA dello spot di partenza 
					        if (board.isEndColumn(start, end) == true) {
					            // nessun pezzo lungo il percorso (colonna)
					        	int i = start.getY();
					        	    // scorri a sopra
						        	if (start.getY() < end.getY()) {
										while(i < end.getY()) {
											i++;
											if (board.getSpot(start.getX(), i) != null) {
												return false; //trovato un pezzo sul percorso, mossa illegale
											}
	
										}
									// scorri a sotto
									} else {
										while(i > end.getY()) {
											i--;
											if (board.getSpot(start.getX(), i) != null) {
												return false; //trovato un pezzo sul percorso, mossa illegale
											}
	
										}
									}
					        }
				}
					
				// pezzo in end nero (stesso colore)
				} else {
					if (!endPiece.isWhite()) {
						return false;
					} else {
						//cattura del pezzo
						return true;
					}
				}
				// turno del nero ma pezzo bianco da muovere
			} else {
				return false;
			}
		}
		return false;
	}
	
	
}
