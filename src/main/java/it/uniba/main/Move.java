package it.uniba.main;

import java.util.ArrayList;

/**
 * DESCRIZIONE
 * rappresenta un movimento di un pezzo degli scacchi
 * da una casa di partenza ad una di arrivo
 * 
 * RESPONSABILITA' DI CLASSE
 * La classe effettua operazioni a partire da un input interpretato
 * - controllare quale pezzo si vuol muovere
 * - controllare la casa di arrivo e stabilire quella di partenza
 * - stabilire se la mossa e' legale
 * - stabilire se e' possibile una cattura (classica o en-passant)
 * - controllare casi di ambiguita' nel movimento
 * - rigettare l'input nel caso di irregolarita'
 * 
 * CLASSIFICAZIONE ECB
 * <<Control>>
 * essendo responsabile della logica che interessa il movimento
 * di un pezzo sulla scacchiera, alla base del concetto di "movimento"
 *
 * @author wilkinson
 */
public final class Move {
	private AlgebraicNotation interpreter; // interprete della mossa scritta in notazione algebrica abbreviata
	private Spot start; // casa di partenza
	private Spot end; // casa di arrivo
	private Piece pieceMoved; // pezzo che deve eseguire il movimento
	private boolean isAmbiguity = false; // caso in cui ci sia ambiguita' di movimento
	private String ambiguity; // primo carattere di un'ambiguitÃ�
	private static final int X_WHYTE_CASTLING = 7;
	private static final int Y_WHYTE_BLACK_CASTLING = 4;
	private static final int X_BLACK_CASTLING = 0;
	private static final int LINE_SPOTS = 8;


	/**
	 * costruttore dell'oggetto Move
	 * 
	 * @param command comando da interpretare in mossa
	 * @param game    partita in corso
	 */
	public Move(final String command, final Game game) {
		this.interpreter = new AlgebraicNotation(command); // Istanzio l'oggetto interpreter

		if (this.isCastle()) { // se e' un arrocco setta le coordinate di partenza del re
			if (game.isWhiteTurn()) {
				this.start = new Spot(X_WHYTE_CASTLING, Y_WHYTE_BLACK_CASTLING);
			} else {
				this.start = new Spot(X_BLACK_CASTLING, Y_WHYTE_BLACK_CASTLING);
			}
		}

		if (this.getInterpreter().isGoodMove() && !this.isCastle()) {
			// estrae le coordinate di arrivo del pezzo
			this.end = extractCoordinates(interpreter.getEndSquareId());

			// individua che tipo di pezzo muovere e cerca lo spot di partenza giusto
			Piece classPiece = classPieceMoved(interpreter.getPieceLetter());
			if (!findStartSpot(game.getBoard(), classPiece, game.isWhiteTurn())) {
				this.start = null;
			}

			// se trova uno start
			if (this.start != null) {
				// avvalora il pezzo da muovere prendendolo da start
				this.pieceMoved = start.getPiece();
				// capisce se e' una cattura en passant
				if (isEnPassantMove(pieceMoved, start, end, game.getBoard())) {
					((Pawn) start.getPiece()).setCapturingEnPassant(true);
				}
			}
		}
	}

	/**
	 * costruttore secondario di un oggetto Move semplice (solo spot di
	 * partenza/arrivo)
	 * 
	 * @param start
	 * @param end
	 */
	public Move(final Spot inStart, final Spot inEnd) {
		this.start = inStart;
		this.end = inEnd;
	}

	static final int EXPECTED_COMMAND_LENGTH = 2;
	static final int EXPECTED_AMBIGUOUS_COMMAND_LENGTH = 3;
	static final int BOARD_LENGTH = 8;
	static final int BOARD_HEIGHT = 8;

	/**
	 * estrae le coordinate della casa di arrivo
	 * 
	 * @param algebraicFinalSpot stringa di coordinate
	 * @return spot di arrivo instanziato come elemento di classe Spot
	 */
	private Spot extractCoordinates(final String algebraicFinalSpot) {
		Spot endSpot;
		// caso in cui la stringa sia lunga 2
		if (algebraicFinalSpot.length() == EXPECTED_COMMAND_LENGTH) {
			endSpot = new Spot(convertCoordinate(algebraicFinalSpot.substring(1, 2)),
					convertCoordinate(algebraicFinalSpot.substring(0, 1)), null);
			return endSpot;
		// caso in cui la stringa sia lunga 3 (ambiguita')
		} else if (algebraicFinalSpot.length() == EXPECTED_AMBIGUOUS_COMMAND_LENGTH) {
			endSpot =
			new Spot(convertCoordinate(algebraicFinalSpot.substring(2, EXPECTED_AMBIGUOUS_COMMAND_LENGTH)),
					convertCoordinate(algebraicFinalSpot.substring(1, 2)), null);
			ambiguity = algebraicFinalSpot.substring(0, 1);

			setAmbiguity(true);
			return endSpot;
		}
		return null;
	}

	/**
	 * trova lo spot di partenza cercando il pezzo con: 1) casa di arrivo
	 * corrispodente a quella inserita 2) controlla solo i pezzi della classe
	 * inserita dall'utente 3) controlla eventuali ambiguita' 
	 * 
	 * @param board
	 * @param piece
	 * @return
	 */
	boolean findStartSpot(final Board board, final Piece piece, final boolean turn) {
		ArrayList<Piece> piecesMovable = new ArrayList<Piece>();
		for (int i = 0; i < LINE_SPOTS; i++) {
			for (int j = 0; j < LINE_SPOTS; j++) {
				Spot currentSpot = board.getSpot(i, j);
				if (currentSpot.getPiece() != null
						&& currentSpot.getPiece().getClass().getName()
						   == piece.getClass().getName()
						&& currentSpot.getPiece().isWhite() == turn) {
					for (Move currentMove : currentSpot.getPiece().getLegalMoves()) {
						if (isAmbiguity) {
							if (sameEnd(currentMove)
									&& samePartialStart(currentMove, ambiguity)) {
								  piecesMovable.add(currentSpot.getPiece());
								  this.setStart(currentMove.getStart());
							}
						} else {
							if (sameEnd(currentMove)) {
								piecesMovable.add(currentSpot.getPiece());
								this.setStart(currentMove.getStart());
							}
						}
					}
				}
			}
		}
		if (piecesMovable.size() == 1) {
			return true;
		}
		return false;
	}

	/**
	 * converte la coordinata in notazione matriciale
	 * 
	 * @param coordinate stringa di coordinate
	 * @return indice di riga-colonna
	 */
	private static final int THREE = 3;
	private static final int FOUR = 4;
	private static final int FIVE = 5;
	private static final int SIX = 6;
	private static final int SEVEN = 7;
	private static final int EIGHT = 8;
	private int convertCoordinate(final String coordinate) {
		switch (coordinate) {
		case "a":
		case "8":
			return 0;
		case "b":
		case "7":
			return 1;
		case "c":
		case "6":
			return 2;
		case "d":
		case "5":
			return THREE;
		case "e":
		case "4":
			return FOUR;
		case "f":
		case "3":
			return FIVE;
		case "g":
		case "2":
			return SIX;
		case "h":
		case "1":
			return SEVEN;
		default:
			return EIGHT;
		}

	}

	/**
	 * Intepreta la lettera inserita dall'utente e la converte nel pezzo mosso
	 * 
	 * @param algebraicPiece pezzo mosso in notazione algebrica
	 * @return ritorna il pezzo che si deve muovere
	 */
	private Piece classPieceMoved(final String algebraicPiece) {
		Piece currentPiece;
		switch (algebraicPiece) {
		case "T":
			currentPiece = new Rook(true);
			break;
		case "C":
			currentPiece = new Knight(true);
			break;
		case "A":
			currentPiece = new Bishop(true);
			break;
		case "D":
			currentPiece = new Queen(true);
			break;
		case "R":
			currentPiece = new King(true);
			break;
		default:
			currentPiece = new Pawn(true);
			break;
		}
		return currentPiece;
	}

	/**
	 * enumerazione dello stato di gioco (per verificare se la partita e' ancora in
	 * corso)
	 * 
	 * @author wilkinson
	 *
	 */
	enum GameStatus {
		ACTIVE, BLACK_WIN, WHITE_WIN, DRAW, FORCED_END
	}

	/**
	 * capisce se il pezzo che sta muovendo sta catturando en passant
	 * 
	 * @param piece pezzo mosso
	 * @param start casa di partenza
	 * @param end   casa di arrivo
	 * @param board scacchiera
	 * @return true se cattura en passant, false altrimenti
	 */
	boolean isEnPassantMove(final Piece enPiece, final Spot enStart, final Spot enEnd, final Board enBoard) {
	  if (enPiece instanceof Pawn && enBoard.isFrontDiagonal(enStart, enEnd) && enEnd.getPiece() == null
			&& enBoard.getSpot(enStart.getX(), enEnd.getY()).getPiece() instanceof Pawn
			&& ((Pawn) enBoard.getSpot(enStart.getX(),
				enEnd.getY()).getPiece()).isPossibleEnPassantCapture()) {
			return true;
		}
		return false;
	}

	/**
	 * controlla se l'input e' un arrocco
	 * 
	 * @return
	 */
	boolean isCastle() {
		if (this.getInterpreter().isCastleShort() || this.getInterpreter().isCastleLong()) {
			return true;
		}
		return false;
	}

	/**
	 * controlla se e' possibile arroccare ed effettua l'arrocco
	 * 
	 * @param game
	 * @return
	 */
	boolean makeCastling(final Game game) {
		if (game.isWhiteTurn()) {
			Spot whiteKingSpot = game.getBoard().getSpot(SEVEN, FOUR);
			if (this.getInterpreter().isCastleShort()) { // arrocco corto bianco
				Spot whiteDxRookSpot = game.getBoard().getSpot(SEVEN, SEVEN);
				Spot whiteNewKingSpot = game.getBoard().getSpot(SEVEN, SIX);
				Spot whiteNewRookSpot = game.getBoard().getSpot(SEVEN, FIVE);
				if (isCastlePossible(game.getBoard(), whiteNewKingSpot, whiteNewRookSpot, whiteKingSpot,
						whiteDxRookSpot, null)) {
					whiteNewKingSpot.setPiece(whiteKingSpot.getPiece());
					whiteNewRookSpot.setPiece(whiteDxRookSpot.getPiece());
					whiteKingSpot.setPiece(null);
					whiteDxRookSpot.setPiece(null);
					return true;
				}
			} else if (this.getInterpreter().isCastleLong()) { // arrocco lungo bianco
				Spot whiteSxRookSpot = game.getBoard().getSpot(SEVEN, 0);
				Spot whiteNewKingSpot = game.getBoard().getSpot(SEVEN, 2);
				Spot whiteNewRookSpot = game.getBoard().getSpot(SEVEN, THREE);
				Spot knightSpot = game.getBoard().getSpot(SEVEN, 1);
				if (isCastlePossible(game.getBoard(), whiteNewKingSpot, whiteNewRookSpot, whiteKingSpot,
						whiteSxRookSpot, knightSpot)) {
					whiteNewKingSpot.setPiece(whiteKingSpot.getPiece());
					whiteNewRookSpot.setPiece(whiteSxRookSpot.getPiece());
					whiteKingSpot.setPiece(null);
					whiteSxRookSpot.setPiece(null);
					return true;
				}
			}
		} else {
			Spot blackKingSpot = game.getBoard().getSpot(0, FOUR);
			if (this.getInterpreter().isCastleShort()) { // arrocco corto nero
				Spot blackDxRookSpot = game.getBoard().getSpot(0, SEVEN);
				Spot blackNewKingSpot = game.getBoard().getSpot(0, SIX);
				Spot blackNewRookSpot = game.getBoard().getSpot(0, FIVE);
				if (isCastlePossible(game.getBoard(), blackNewKingSpot, blackNewRookSpot, blackKingSpot,
						blackDxRookSpot, null)) {
					blackNewKingSpot.setPiece(blackKingSpot.getPiece());
					blackNewRookSpot.setPiece(blackDxRookSpot.getPiece());
					blackKingSpot.setPiece(null);
					blackDxRookSpot.setPiece(null);
					return true;
				}
			} else if (this.getInterpreter().isCastleLong()) { // arrocco lungo nero
				Spot blackSxRookSpot = game.getBoard().getSpot(0, 0);
				Spot blackNewKingSpot = game.getBoard().getSpot(0, 2);
				Spot blackNewRookSpot = game.getBoard().getSpot(0, THREE);
				Spot knightSpot = game.getBoard().getSpot(0, 1);
				if (isCastlePossible(game.getBoard(), blackNewKingSpot, blackNewRookSpot, blackKingSpot,
						blackSxRookSpot, knightSpot)) {
					blackNewKingSpot.setPiece(blackKingSpot.getPiece());
					blackNewRookSpot.setPiece(blackSxRookSpot.getPiece());
					blackKingSpot.setPiece(null);
					blackSxRookSpot.setPiece(null);
					return true;
				}
			}
		}
		return false;
	}

	/**
	 * controlla se le case fra re e torre sono sotto attacco
	 * 
	 * @param board
	 * @param kingSpot
	 * @param rookSpot
	 * @param king
	 * @return
	 */
	private boolean isPathCastleNotAttacked(final Board board, final Spot kingSpot,
			final Spot rookSpot, final Spot king) {
		if (king.isUnderAttack(board, king.getPiece().isWhite())
				|| kingSpot.isUnderAttack(board, king.getPiece().isWhite())
				|| rookSpot.isUnderAttack(board, king.getPiece().isWhite())) {
			return false;
		}
		return true;
	}

	/**
	 * controlla se le case fra re e torre sono libere da pezzi
	 * 
	 * @param board
	 * @param kingSpot
	 * @param rookSpot
	 * @return
	 */
	private boolean isPathCastleFree(final Board board, final Spot kingSpot,
			final Spot rookSpot, final Spot knightPos) {
		if (this.getInterpreter().isCastleShort()) {
			if (kingSpot.isEmpty() && rookSpot.isEmpty()) {
				return true;
			}
		} else if (this.getInterpreter().isCastleLong()) {
			if (kingSpot.isEmpty() && rookSpot.isEmpty() && knightPos.isEmpty()) {
				return true;
			}
		}
		return false;
	}

	/**
	 * controlla se re e torre non sono stati mossi
	 * 
	 * @param king
	 * @param rook
	 * @return
	 */
	private boolean areCastlePiecesNotMoved(final King king, final Rook rook) {
		if (king.isMoved() || rook.isMoved()) {
			return false;
		}
		return true;
	}

	/**
	 * controlla che i pezzi nelle case di partenza di re e torre siano giusti
	 * 
	 * @param king
	 * @param rook
	 * @return
	 */
	private boolean areCastlePiecesThere(final Spot king, final Spot rook) {
		if (!king.isEmpty() && !rook.isEmpty()) {
			if (king.getPiece() instanceof King && rook.getPiece() instanceof Rook) {
				return true;
			}
		}
		return false;
	}
	/**
	 * applica una serie di controlli per stabilire se l'arrocco è possibile
	 * 
	 * @param board
	 * @param king
	 * @param rook
	 * @param kingSpot
	 * @param rookSpot
	 * @param kingOrigin
	 * @return
	 */
	private boolean isCastlePossible(final Board board, final Spot kingSpot, final Spot rookSpot,
			final Spot kingOrigin, final Spot rookOrigin, final Spot knightSpot) {
		if (areCastlePiecesThere(kingOrigin, rookOrigin)) {
			if (areCastlePiecesNotMoved((King) kingOrigin.getPiece(), (Rook) rookOrigin.getPiece())) {
				if (isPathCastleFree(board, kingSpot, rookSpot, knightSpot)) {
					if (isPathCastleNotAttacked(board, kingSpot, rookSpot, kingOrigin)) {
						return true;
					}
				}
			}
		}
		return false;
	}

	/**
	 * confronta due oggetti di tipo Move e controlla se hanno le stesse coordinate
	 * 
	 * @param move
	 * @return true se hanno le stesse coordinate, false altrimenti
	 */
	public boolean equals(final Move move) {
		if (this.getEnd().getX() == move.getEnd().getX() && this.getEnd().getY() == move.getEnd().getY()
				&& this.getStart().getX() == move.getStart().getX()
				&& this.getStart().getY() == move.getStart().getY()) {
			return true;
		}
		return false;
	}

	/**
	 * controlla se due oggetti di tipo move hanno la stessa casa di arrivo
	 * 
	 * @param move
	 * @return
	 */
	public boolean sameEnd(final Move move) {
		if (this.getEnd().getX() == move.getEnd().getX() && this.getEnd().getY() == move.getEnd().getY()) {
			return true;
		}
		return false;
	}

	/**
	 * controlla se la casa di start della mossa corrisponde parzialmente
	 * all'ambiguita'
	 * 
	 * @param move
	 * @param coord
	 * @return
	 */
	public boolean samePartialStart(final Move move, final String coord) {
		if (Character.isDigit(coord.charAt(0))) {
			if (move.getStart().getX() == convertCoordinate(coord)) {
				return true;
			}
		} else {
			if (move.getStart().getY() == convertCoordinate(coord)) {
				return true;
			}
		}
		return false;
	}

	// Getters & Setters
	public AlgebraicNotation getInterpreter() {
		return interpreter;
	}

	public Spot getStart() {
		return start;
	}

	public Spot getEnd() {
		return end;
	}

	public Piece getPieceMoved() {
		return pieceMoved;
	}

	public boolean isAmbiguity() {
		return isAmbiguity;
	}

	public void setAmbiguity(final boolean inAmbiguity) {
		this.isAmbiguity = inAmbiguity;
	}

	public void setStart(final Spot inStart) {
		this.start = inStart;
	}

	public void setEnd(final Spot inEnd) {
		this.end = inEnd;
	}

	public void setPieceMoved(final Piece inPieceMoved) {
		this.pieceMoved = inPieceMoved;
	}
}
