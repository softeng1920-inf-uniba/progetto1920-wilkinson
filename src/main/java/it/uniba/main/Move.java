
package it.uniba.main;

import java.util.ArrayList;

/**
 * <body>
 * <h2>DESCRIZIONE</h2>
 * rappresenta un movimento di un pezzo degli scacchi <br>
 * da una casa di partenza ad una di arrivo <br>
 *
 * <h2>RESPONSABILITA' DI CLASSE</h2>
 * La classe effettua operazioni a partire da un input interpretato <br>
 * - controlla quale pezzo si vuol muovere <br>
 * - controlla la casa di arrivo e stabilisce quella di partenza <br>
 * - stabilisce se e' possibile una cattura (classica o en-passant) <br>
 * - controlla casi di ambiguita' nel movimento <br>
 *
 * <h2>CLASSIFICAZIONE ECB</h2>
 * <strong>Control</strong><br>
 * essendo responsabile della logica che interessa il movimento <br>
 * di un pezzo sulla scacchiera, alla base del concetto di "movimento"
 * </body>
 *
 * @author wilkinson
 */
public final class Move {
	private AlgebraicNotation interpreter; // interprete della mossa scritta in notazione algebrica abbreviata
	private Spot start; // casa di partenza
	private Spot end; // casa di arrivo
	private Piece pieceMoved; // pezzo che deve eseguire il movimento
	private boolean isAmbiguity = false; // caso in cui ci sia ambiguita' di movimento
	private String ambiguity; // primo carattere di un'ambiguitÃ 

	/**
	 * costruttore dell'oggetto Move
	 *
	 * @param command comando da interpretare in mossa
	 * @param game    partita in corso
	 */
	public Move(final String command, final Game game) {
		this.interpreter = new AlgebraicNotation(command); // Istanzio l'oggetto interpreter
		final int kingCol = 4;
		final int whiteKingROW = 7;
		final int blackKingROW = 0;
		if (this.isCastle()) { // se e' un arrocco setta le coordinate di partenza del re
			if (game.isWhiteTurn()) {
				this.start = new Spot(whiteKingROW, kingCol);
			} else {
				this.start = new Spot(blackKingROW, kingCol);
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
				if (isEnPassantMove(game.getBoard())) {
					((Pawn) start.getPiece()).setCapturingEnPassant(true);
				}
			}
		}
	}

	/**
	 * costruttore secondario di un oggetto Move semplice (solo spot di
	 * partenza/arrivo)
	 *
	 * @param inStart
	 * @param inEnd
	 */
	public Move(final Spot inStart, final Spot inEnd) {
		this.start = inStart;
		this.end = inEnd;
	}

	private static final int EXPECTED_COMMAND_LENGTH = 2;
	private static final int EXPECTED_AMBIGUOUS_COMMAND_LENGTH = 3;
	private static final int BOARD_LENGTH = 8;
	private static final int BOARD_HEIGHT = 8;
	private static final int LETTER_INDEX = 2;
	private static final int NUMBER_INDEX = 3;

	/**
	 * estrae le coordinate della casa di arrivo
	 *
	 * @param algebraicFinalSpot stringa di coordinate
	 * @return spot di arrivo instanziato come elemento di classe Spot
	 */
	private Spot extractCoordinates(final String algebraicFinalSpot) {
		Spot endSpot;
		if (algebraicFinalSpot.length() == EXPECTED_COMMAND_LENGTH) {
			// caso in cui la stringa sia lunga 2
			endSpot = new Spot(convertCoordinate(algebraicFinalSpot.substring(1, 2)),
					convertCoordinate(algebraicFinalSpot.substring(0, 1)), null);
			return endSpot;
		} else if (algebraicFinalSpot.length() == EXPECTED_AMBIGUOUS_COMMAND_LENGTH) {
			// caso in cui la stringa sia lunga 3 (ambiguita')
			endSpot = new Spot(convertCoordinate(algebraicFinalSpot.substring(LETTER_INDEX, NUMBER_INDEX)),
					convertCoordinate(algebraicFinalSpot.substring(1, LETTER_INDEX)), null);
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
		for (int i = 0; i < BOARD_LENGTH; i++) {
			for (int j = 0; j < BOARD_HEIGHT; j++) {
				Spot currentSpot = board.getSpot(i, j);
				if (!currentSpot.isEmpty()
						&& currentSpot.getPiece().getClass().getName().equals(
							piece.getClass().getName())
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

	static final int COLA_ROW1 = 0;
	static final int COLB_ROW2 = 1;
	static final int COLC_ROW3 = 2;
	static final int COLD_ROW4 = 3;
	static final int COLE_ROW5 = 4;
	static final int COLF_ROW6 = 5;
	static final int COLG_ROW7 = 6;
	static final int COLH_ROW8 = 7;
	static final int NOT_ON_BOARD = 8;

	/**
	 * converte la coordinata in notazione matriciale
	 *
	 * @param coordinate stringa di coordinate
	 * @return indice di riga-colonna
	 */
	private int convertCoordinate(final String coordinate) {
		switch (coordinate) {
		case "a":
		case "8":
			return COLA_ROW1;
		case "b":
		case "7":
			return COLB_ROW2;
		case "c":
		case "6":
			return COLC_ROW3;
		case "d":
		case "5":
			return COLD_ROW4;
		case "e":
		case "4":
			return COLE_ROW5;
		case "f":
		case "3":
			return COLF_ROW6;
		case "g":
		case "2":
			return COLG_ROW7;
		case "h":
		case "1":
			return COLH_ROW8;
		default:
			return NOT_ON_BOARD;
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
		ACTIVE, BLACK_WIN, WHITE_WIN, DROW, FORCED_END
	}

	/**
	 * capisce se il pezzo che sta muovendo sta catturando en passant
	 *
	 * @param board scacchiera
	 * @return true se cattura en passant, false altrimenti
	 */
	boolean isEnPassantMove(final Board board) {
		if (pieceMoved instanceof Pawn
				&& board.isFrontDiagonal(start, end)
				&& board.getSpot(end.getX(), end.getY()).isEmpty()
				&& board.getSpot(start.getX(), end.getY()).getPiece() instanceof Pawn
				&& ((Pawn) board.getSpot(start.getX(), end.getY())
						.getPiece()).isPossibleEnPassantCapture()) {
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

	private static final int ROW_1 = 7;
	private static final int ROW_8 = 0;
	private static final int COL_H = 7;
	private static final int COL_G = 6;
	private static final int COL_F = 5;
	private static final int COL_E = 4;
	private static final int COL_D = 3;
	private static final int COL_C = 2;
	private static final int COL_B = 1;
	private static final int COL_A = 0;

	/**
	 * controlla se e' possibile arroccare ed effettua l'arrocco
	 *
	 * @param game
	 * @return
	 */
	boolean makeCastling(final Game game) {
		if (game.isWhiteTurn()) {
			Spot whiteKingSpot = game.getBoard().getSpot(ROW_1, COL_E);
			if (this.getInterpreter().isCastleShort()) { // arrocco corto bianco
				Spot whiteDxRookSpot = game.getBoard().getSpot(ROW_1, COL_H);
				Spot whiteNewKingSpot = game.getBoard().getSpot(ROW_1, COL_G);
				Spot whiteNewRookSpot = game.getBoard().getSpot(ROW_1, COL_F);
				if (isCastlePossible(game.getBoard(), whiteNewKingSpot, whiteNewRookSpot, whiteKingSpot,
						whiteDxRookSpot, null)) {
					whiteNewKingSpot.setPiece(whiteKingSpot.getPiece());
					whiteNewRookSpot.setPiece(whiteDxRookSpot.getPiece());
					whiteKingSpot.setPiece(null);
					whiteDxRookSpot.setPiece(null);
					return true;
				}
			} else if (this.getInterpreter().isCastleLong()) { // arrocco lungo bianco
				Spot whiteSxRookSpot = game.getBoard().getSpot(ROW_1, COL_A);
				Spot whiteNewKingSpot = game.getBoard().getSpot(ROW_1, COL_C);
				Spot whiteNewRookSpot = game.getBoard().getSpot(ROW_1, COL_D);
				Spot knightSpot = game.getBoard().getSpot(ROW_1, COL_B);
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
			Spot blackKingSpot = game.getBoard().getSpot(ROW_8, COL_E);
			if (this.getInterpreter().isCastleShort()) { // arrocco corto nero
				Spot blackDxRookSpot = game.getBoard().getSpot(ROW_8, COL_H);
				Spot blackNewKingSpot = game.getBoard().getSpot(ROW_8, COL_G);
				Spot blackNewRookSpot = game.getBoard().getSpot(ROW_8, COL_F);
				if (isCastlePossible(game.getBoard(), blackNewKingSpot, blackNewRookSpot, blackKingSpot,
						blackDxRookSpot, null)) {
					blackNewKingSpot.setPiece(blackKingSpot.getPiece());
					blackNewRookSpot.setPiece(blackDxRookSpot.getPiece());
					blackKingSpot.setPiece(null);
					blackDxRookSpot.setPiece(null);
					return true;
				}
			} else if (this.getInterpreter().isCastleLong()) { // arrocco lungo nero
				Spot blackSxRookSpot = game.getBoard().getSpot(ROW_8, COL_A);
				Spot blackNewKingSpot = game.getBoard().getSpot(ROW_8, COL_C);
				Spot blackNewRookSpot = game.getBoard().getSpot(ROW_8, COL_D);
				Spot knightSpot = game.getBoard().getSpot(ROW_8, COL_B);
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

	/**applica una serie di controlli per stabilire se l'arrocco è possibile
	 *
	 * @param board
	 * @param kingSpot
	 * @param rookSpot
	 * @param kingOrigin
	 * @param rookOrigin
	 * @param knightSpot
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
	@Override
	public boolean equals(final Object move) {
		if (!(move instanceof Move)) {
			return false;
		}
		Move inMove = (Move) move;
		if (this.getEnd().getX() == inMove.getEnd().getX()
				&& this.getEnd().getY() == inMove.getEnd().getY()
				&& this.getStart().getX() == inMove.getStart().getX()
				&& this.getStart().getY() == inMove.getStart().getY()) {
			return true;
		}
		return false;
	}

	private static final int HASH = 42;

	/**
	 * default hashcode (di nessuna utilita', poiche' non c'e' un Set implementato)
	 */
	@Override
	public int hashCode() {
	    assert false : "hashCode not designed";
	    return HASH; // any arbitrary constant will do
	}

	/**
	 * controlla se due oggetti di tipo move hanno la stessa casa di arrivo
	 *
	 * @param move
	 * @return
	 */
	public boolean sameEnd(final Move move) {
		if (this.getEnd().getX() == move.getEnd().getX()
				&& this.getEnd().getY() == move.getEnd().getY()) {
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

	public void setAmbiguity(final boolean inIsAmbiguity) {
		this.isAmbiguity = inIsAmbiguity;
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
