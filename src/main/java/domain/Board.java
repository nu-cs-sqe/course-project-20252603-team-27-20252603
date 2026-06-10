package domain;

public class Board {
	public static final int TOTAL_ROWS = 8;
	public static final int TOTAL_COLS = 8;
	private final Piece[][] pieces;

	public Board() {
		this.pieces = new Piece[TOTAL_ROWS][TOTAL_COLS];
		clearBoard();
	}

	public Board(Piece[][] pieces) {
		this.pieces = new Piece[TOTAL_ROWS][TOTAL_COLS];
		if (pieces != null) {
			for (int row = 0; row < TOTAL_ROWS; row++) {
				if (pieces[row] != null) {
					int maxLength = Math.min(pieces[row].length, TOTAL_COLS);
					for (int col = 0; col < maxLength; col++) {
						Piece piece = pieces[row][col];
						PieceType type = (piece == null)
								? null : piece.getPieceType();
						PieceColor color = (piece == null)
								? null : piece.getColor();
						this.pieces[row][col] = (piece == null)
								? null : new Piece(type, color);
					}
				}
			}
		}
	}

	public void clearBoard() {
		for (int row = 0; row < TOTAL_ROWS; row++) {
			for (int col = 0; col < TOTAL_COLS; col++) {
				pieces[row][col] = new Piece(PieceType.EMPTY, PieceColor.WHITE);
			}
		}
	}

	public boolean isInsideBoard(Location location) {
		int row = location.getRow();
		int col = location.getCol();
		return row >= 0 && row < TOTAL_ROWS && col >= 0 && col < TOTAL_COLS;
	}

	public void initBoard() {
		clearBoard();
		pieces[0][0] = new Piece(PieceType.ROOK, PieceColor.BLACK);
		pieces[0][1] = new Piece(PieceType.KNIGHT, PieceColor.BLACK);
		pieces[0][2] = new Piece(PieceType.BISHOP, PieceColor.BLACK);
		pieces[0][3] = new Piece(PieceType.QUEEN, PieceColor.BLACK);
		pieces[0][4] = new Piece(PieceType.KING, PieceColor.BLACK);
		pieces[0][5] = new Piece(PieceType.BISHOP, PieceColor.BLACK);
		pieces[0][6] = new Piece(PieceType.KNIGHT, PieceColor.BLACK);
		pieces[0][7] = new Piece(PieceType.ROOK, PieceColor.BLACK);

		for (int col = 0; col < TOTAL_COLS; col++) {
			pieces[1][col] = new Piece(PieceType.PAWN, PieceColor.BLACK);
			pieces[6][col] = new Piece(PieceType.PAWN, PieceColor.WHITE);
		}

		pieces[7][0] = new Piece(PieceType.ROOK, PieceColor.WHITE);
		pieces[7][1] = new Piece(PieceType.KNIGHT, PieceColor.WHITE);
		pieces[7][2] = new Piece(PieceType.BISHOP, PieceColor.WHITE);
		pieces[7][3] = new Piece(PieceType.QUEEN, PieceColor.WHITE);
		pieces[7][4] = new Piece(PieceType.KING, PieceColor.WHITE);
		pieces[7][5] = new Piece(PieceType.BISHOP, PieceColor.WHITE);
		pieces[7][6] = new Piece(PieceType.KNIGHT, PieceColor.WHITE);
		pieces[7][7] = new Piece(PieceType.ROOK, PieceColor.WHITE);
	}

	public Piece getPiece(Location location) {
		return pieces[location.getRow()][location.getCol()];
	}

	/**
	 * Set piece at location; return previous piece (may be null). No validation.
	 */
	public void setPiece(Location location, Piece piece) {
		int row = location.getRow();
		int col = location.getCol();
		pieces[row][col] = piece;
	}

	public Piece[][] getSnapshot() {
		Piece[][] snapshot = new Piece[TOTAL_ROWS][TOTAL_COLS];
		for (int row = 0; row < TOTAL_ROWS; row++) {
			for (int col = 0; col < TOTAL_COLS; col++) {
				Piece piece = pieces[row][col];
				snapshot[row][col] = piece == null ? null
						: new Piece(piece.getPieceType(),
						piece.getColor());
			}
		}
		return snapshot;
	}

	public void movePiece(Location from, Location to) {
		Piece movingPiece = getPiece(from);
		pieces[to.getRow()][to.getCol()] = movingPiece;
		pieces[from.getRow()][from.getCol()] = new Piece(PieceType.EMPTY, PieceColor.WHITE);
	}

	public boolean isEmpty(Location location) {
		Piece piece = getPiece(location);
		return piece == null || piece.getPieceType() == PieceType.EMPTY;
	}

	public Location findKing(PieceColor color) {
		for (int row = 0; row < TOTAL_ROWS; row++) {
			for (int col = 0; col < TOTAL_COLS; col++) {
				Piece piece = pieces[row][col];
				if (piece.getPieceType() != PieceType.EMPTY
						&& piece.getPieceType() == PieceType.KING
						&& piece.getColor() == color) {
					return new Location(row, col);
				}
			}
		}
		throw new IllegalStateException("King not found for color: " + color);
	}

	public String toPositionString() {
		StringBuilder builder = new StringBuilder();
		for (int row = 0; row < TOTAL_ROWS; row++) {
			for (int col = 0; col < TOTAL_COLS; col++) {
				Piece piece = pieces[row][col];
				if (piece.getPieceType() == PieceType.EMPTY) {
					builder.append('.');
				} else {
					char symbol = piece.getPieceType() == PieceType.KNIGHT ? 'N'
							: piece.getPieceType().name().charAt(0);
					if (piece.getColor() == PieceColor.BLACK) {
						symbol = Character.toLowerCase(symbol);
					}
					builder.append(symbol);
				}
			}
			if (row < TOTAL_ROWS - 1) {
				builder.append(System.lineSeparator());
			}
		}
		return builder.toString();
	}
}