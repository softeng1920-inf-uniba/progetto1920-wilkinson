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
	boolean canMove(Board board, Spot start, Spot end) {
		if (start.getPiece().isWhite()) { // controllo che le coordinate start di tipo Spot siano del pedone bianco
			if (end.getPiece() == null) { // controllo che nelle cordinate d'arrivo non siano presenti pezzi
				// possibile movimento di 2 righe del pedone bianco senon e' stato mai spostato
				if ((start.getY() == end.getY()) && (start.getX() == (end.getX() + 2))) {
					if (!isMoved()) { // verifico che il pedone bianco non sia stato mai spostato
						return true;
					} else
						return false;
				}
				// possibile movimento di 1 riga del pedone bianco
				else if ((start.getY() == end.getY()) && (start.getX() == (end.getX() + 1))) {
					return true;
				}

				else if (start.getX() == XENPASSANTWHITE) {// controllo che il pedone bianco sia nella riga giusta per
															// effettuare la cattura en-passant
					if (enPassantCheck(board, start, end)) { // controllo se e' possibile catturare in en-passant
						// imposta il booleano del pezzo che sta catturando in en-passant a true, tutti
						// gli altri saranno false
						((Pawn) start.getPiece()).isCapturingEnPassant = true;
						return true;
					}
				}
			} else { // se lo spot d'arrivo non e' null, posso catturare in diagonale
				 // controllo che il pezzo sia avversario
				if (start.getPiece().isWhite() == !(end.getPiece().isWhite())) {
		// controllo che lo spot d'arrivo sia nella riga x+1(pedone bianco sale) e nella colonna y-1 o y+1
					if ((start.getY() == end.getY() + 1 || start.getY() == end.getY() - 1)
							&& (start.getX() == end.getX() + 1)) {
						((Pawn) end.getPiece()).setAsKilled(); // setto il booleano per indicare che il pezzo e'stato
																// catturato
						return true;
					}
				}
			}
		} else if (!start.getPiece().isWhite()) {// controllo che le coordinate start di tipo Spot siano del pedone nero
			if (end.getPiece() == null) {// controllo che nelle cordinate d'arrivo non siano presenti pezzi
				// possibile movimento di 2 righe del pedone nero se non e' stato mai spostato
				if ((start.getY() == end.getY()) && (start.getX() == (end.getX() - 2))) {
					if (!isMoved()) {// verifico che il pedone nero non sia stato mai spostato
						return true;
					} else
						return false;
				}

				else if ((start.getY() == end.getY()) && (start.getX() == (end.getX() - 1))) { // possibile movimento di
																								// 1 riga
					return true;
					// controllo che il pedone nero sia nella riga giusta per effettuare la cattura
					// en-passant
				} else if (start.getX() == XENPASSANTBLACK) {
					if (enPassantCheck(board, start, end)) {
						// controllo se e' possibile catturare in en-passant catturando in en-passant a
						// true tutti gli altri saranno false
						((Pawn) start.getPiece()).isCapturingEnPassant = true;
						return true;
					}
				}
			} else {// se lo spot d'arrivo non e' null, posso catturare in diagonale
				if (start.getPiece().isWhite() == !(end.getPiece().isWhite())) {// controllo che il pezzo sia avversario
					// controllo che lo spot d'arrivo sia nella riga x+1(pedone bianco sale) e nella
					// colonna y-1 o y+1
					if ((start.getY() == end.getY() + 1 || start.getY() == end.getY() - 1)
							&& (start.getX() == end.getX() - 1)) {
						((Pawn) end.getPiece()).setAsKilled();// setto il booleano per indicare che il pezzo e'stato
																// catturato
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
		// assegno a examinedSpot le stesse cordinate del pedone che puo' subire l'en-passant
		Spot examinedSpot = board.getSpot(start.getX(), end.getY());
		// controllo che nell'arrivo non ci siano pezzi e che nello spot esaminato ci sia
		if (start.getPiece() != null && end.getPiece() == null && examinedSpot.getPiece() != null) {
			Piece possibleCapture = examinedSpot.getPiece();
			// controllo che il pedone che puo'essere catturato enpassant sia avversario
			if ((start.getPiece().isWhite() != possibleCapture.isWhite()) && (possibleCapture instanceof Pawn)) {
				if (((Pawn) possibleCapture).isPossibleEnPassantCapture()) {// controllo se il booleano e' true
					possibleCapture.setAsKilled(); // catturo il pezzo
					return true;
				}
			}
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
