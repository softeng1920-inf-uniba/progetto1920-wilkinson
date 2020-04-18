package it.uniba.main;

public class Pawn extends Piece {
	private boolean possibleEnPassantCapture;
	private boolean isCapturingEnPassant;

	public Pawn(boolean white) {
		super(white); // chiamo il costruttore della classe astratta Piece
		possibleEnPassantCapture = false; // settato a true quando il pezzo potra' essere catturato en-passant
		isCapturingEnPassant = false; // settato a true quando il pezzo stara' catturando en-passant
	}

	private static final int XENPASSANTWHITE = 3; // controlla che il pedone bianco sia nella riga giusta per effettuare
	// la cattura en-passant
	private static final int XENPASSANTBLACK = 4; // controlla che il pedone nero sia nella riga giusta per effettuare
	// la cattura en-passant

	@Override
	/**
	 * Metodo per ottenere l'unicode del pedone nero e del pedone bianco
	 */
	public String draw() {
		if (isWhite()) {
			return "\u2659"; // unicode del bianco
		} else {
			return "\u265f"; // unicode del nero
		}

	}

	/**
	 * metodo che verifica il possibile movimento di una o due posizioni del pedone
	 * e che verifica la possibile cattura di un pezzo avversario attraverso una
	 * cattura classica o attraverso la cattura en-passant
	 */
	@Override
	boolean canMove(Board board, Spot start, Spot end, boolean isWhiteTurn) {
		if(end.getPiece() != null) {		/*CASO DI CATTURA*/
			if((start.getPiece().isWhite() != end.getPiece().isWhite()) && start.getPiece().isWhite()) {
				if((start.getX() == (end.getX()+1)) && ((start.getY() == (end.getY()+1)) || (start.getY() == (end.getY()-1)))) {
					end.getPiece().setAsKilled();
					return true;
				}
			} else if((start.getPiece().isWhite() != end.getPiece().isWhite()) && !start.getPiece().isWhite()) {
				if((start.getX() == (end.getX()-1)) && ((start.getY() == (end.getY()+1)) || (start.getY() == (end.getY()-1)))) {
					end.getPiece().setAsKilled();
					return true;
				}
			} else {
				return false;
			}

		} else {						/*CASO DI MOVIMENTO CON CELLA end VUOTA*/
			if(isWhiteTurn) {
				if((start.getY() == end.getY()) && (start.getX() == (end.getX()+1))) {
					return true;
				} else if((start.getY() == end.getY()) && (start.getX() == (end.getX()+2)) && (!start.getPiece().isMoved())){
					return true;
				} else if (start.getX() == XENPASSANTWHITE) {// controllo che il pedone bianco sia nella riga giusta per
					// effettuare la cattura en-passant
					if (enPassantCheck(board, start, end)) { // controllo se e' possibile catturare in en-passant
						// imposta il booleano del pezzo che sta catturando in en-passant a true, tutti
						// gli altri saranno false
						((Pawn) start.getPiece()).isCapturingEnPassant = true;
						return true;
					}
				}
			} else {
				if((start.getY() == end.getY()) && (start.getX() == (end.getX()-1))) {
					return true;
				} else if((start.getY() == end.getY()) && (start.getX() == (end.getX()-2)) && (!start.getPiece().isMoved())){
					return true;
				} else if (start.getX() == XENPASSANTBLACK) {
					if (enPassantCheck(board, start, end)) {
						// controllo se e' possibile catturare in en-passant catturando in en-passant a
						// true tutti gli altri saranno false
						((Pawn) start.getPiece()).isCapturingEnPassant = true;
						return true;
					}
				}
			}
		}
		return false;
	}

	/**
	 * Metodo che gestisce la cattura en-passant
	 * 
	 * @param board schacchiera allo stato attuale
	 * @param start spot di partenza del pedone
	 * @param end   spot di arrivo del pedone
	 * @return booleano settato a true se puo' essere effettuata la cattura
	 *         en-passant
	 */
	private boolean enPassantCheck(Board board, Spot start, Spot end) {
		Spot examinedSpot1 = null;
		Spot examinedSpot2 = null;
		// assegno a examinedSpot le stesse cordinate del pedone che puo' subire l'en-passant
		if (start.getY() == 0) {
			examinedSpot1 = board.getSpot(start.getX(), start.getY()+1);
		} else if (start.getY() == 7) {
			examinedSpot1 = board.getSpot(start.getX(), start.getY()-1);
		}
		else {
			examinedSpot1 = board.getSpot(start.getX(), start.getY()+1);
			examinedSpot2 = board.getSpot(start.getX(), start.getY()-1);
		}

		// controllo che nell'arrivo non ci siano pezzi e che nello spot esaminato ci sia
		if (start.getPiece() != null && end.getPiece() == null) {
			if (examinedSpot1 != null && examinedSpot1.getPiece() != null) {
				Piece possibleCapture = examinedSpot1.getPiece();
				if ((start.getPiece().isWhite() != possibleCapture.isWhite()) && (possibleCapture instanceof Pawn)) {
					if (((Pawn) possibleCapture).isPossibleEnPassantCapture()) {// controllo se il booleano e' true
						possibleCapture.setAsKilled(); // catturo il pezzo
						return true;
					}
				}
			} 
			if (examinedSpot2 != null && examinedSpot2.getPiece() != null) {
				Piece possibleCapture = examinedSpot2.getPiece();
				if ((start.getPiece().isWhite() != possibleCapture.isWhite()) && (possibleCapture instanceof Pawn)) {
					if (((Pawn) possibleCapture).isPossibleEnPassantCapture()) {// controllo se il booleano e' true
						possibleCapture.setAsKilled(); // catturo il pezzo
						return true;
					}
				}
			}
			// controllo che il pedone che puo'essere catturato enpassant sia avversario
		}
		return false;
	}

	//Getters & Setters
	public boolean isPossibleEnPassantCapture() {
		return possibleEnPassantCapture;
	}

	public void setPossibleEnPassantCapture(boolean possibleEnPassantCapture) {
		this.possibleEnPassantCapture = possibleEnPassantCapture;
	}

	public boolean isCapturingEnPassant() {
		return isCapturingEnPassant;
	}

	public void setCapturingEnPassant(boolean isCapturingEnPassant) {
		this.isCapturingEnPassant = isCapturingEnPassant;
	}

}
