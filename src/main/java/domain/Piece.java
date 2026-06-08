package domain;

public class Piece {

	private final PieceType pieceType;
	private final PieceColor pieceColor;
	private boolean moved = false;

	public Piece(PieceType pieceType, PieceColor pieceColor) {
		this.pieceType = pieceType;
		this.pieceColor = pieceColor;
	}

	public boolean hasMoved() {
		return moved;
	}

	public void setMoved(boolean moved) {
		this.moved = moved;
	}

	public PieceColor getColor() {
		return pieceColor;
	}

	public PieceType getPieceType() {
		return pieceType;
	}

	public boolean canMove(Board board, Location from, Location to) {
		if (isOutOfBounds(to)) {
			return false;
		}
		return isValidMoveForPieceType(board, from, to);
	}

	private boolean isValidMoveForPieceType(Board board, Location from, Location to) {
		if (pieceType == PieceType.PAWN) {
			return isValidPawnMove(board, from, to);
		}
		if (pieceType == PieceType.ROOK) {
			return isValidRookMove(board, from, to);
		}
		if (pieceType == PieceType.KNIGHT) {
			return isValidKnightMove(board, from, to);
		}
		if (pieceType == PieceType.BISHOP) {
			return isValidBishopMove(board, from, to);
		}
		if (pieceType == PieceType.KING) {
			return isValidKingMove(board, from, to);
		}
		return isValidQueenMove(board, from, to);
	}

	private boolean isOutOfBounds(Location loc) {
		return loc.getRow() < 0 || loc.getRow() > 7 || loc.getCol() < 0 || loc.getCol() > 7;
	}

	private boolean isValidPawnMove(Board board, Location from, Location to) {
		int rowDiff = to.getRow() - from.getRow();
		int colDiff = to.getCol() - from.getCol();

		if (isPawnOneForward(rowDiff, colDiff)) {
			return board.getPiece(to) == null;
		}

		if (isPawnTwoForwardInitial(rowDiff, colDiff)) {
			Location intermediate = new Location(from.getRow() + 1, from.getCol());
			return board.getPiece(intermediate) == null && board.getPiece(to) == null;
		}

		if (isPawnDiagonalCapture(rowDiff, colDiff)) {
			Piece target = board.getPiece(to);
			return target != null && target.getColor() != getColor();
		}

		return false;
	}

	private boolean isPawnOneForward(int rowDiff, int colDiff) {
		return rowDiff == 1 && colDiff == 0;
	}

	private boolean isPawnTwoForwardInitial(int rowDiff, int colDiff) {
		return rowDiff == 2 && colDiff == 0 && !hasMoved();
	}

	private boolean isPawnDiagonalCapture(int rowDiff, int colDiff) {
		return rowDiff == 1 && Math.abs(colDiff) == 1;
	}

	private boolean isValidRookMove(Board board, Location from, Location to) {
		int rowDiff = to.getRow() - from.getRow();
		int colDiff = to.getCol() - from.getCol();

		boolean isStraightLine = (rowDiff != 0 && colDiff == 0)
				|| (colDiff != 0 && rowDiff == 0);
		if (!isStraightLine) {
			return false;
		}

		if (isStraightPathObstructed(board, from, to)) {
			return false;
		}

		Piece target = board.getPiece(to);
		return target == null || target.getColor() != getColor();
	}

	private boolean isStraightPathObstructed(Board board, Location from, Location to) {
		int rowDiff = to.getRow() - from.getRow();
		int colDiff = to.getCol() - from.getCol();

		int rowStep = Integer.compare(rowDiff, 0);
		int colStep = Integer.compare(colDiff, 0);

		int currentRow = from.getRow() + rowStep;
		int currentCol = from.getCol() + colStep;

		while (currentRow != to.getRow() || currentCol != to.getCol()) {
			if (board.getPiece(new Location(currentRow, currentCol)) != null) {
				return true;
			}
			currentRow += rowStep;
			currentCol += colStep;
		}

		return false;
	}

	private boolean isValidKnightMove(Board board, Location from, Location to) {
		int rowDiff = Math.abs(to.getRow() - from.getRow());
		int colDiff = Math.abs(to.getCol() - from.getCol());

		boolean isValidLShape = ((colDiff == 1 && rowDiff == 2)
				|| (colDiff == 2 && rowDiff == 1));
		if (!isValidLShape) {
			return false;
		}
		Piece target = board.getPiece(to);
		return target == null || target.getColor() != getColor();
	}

	private boolean isDiagonalPathObstructed(Board board, Location from, Location to) {
		int rowDiff = to.getRow() - from.getRow();
		int colDiff = to.getCol() - from.getCol();

		int rowStep = Integer.compare(rowDiff, 0);
		int colStep = Integer.compare(colDiff, 0);
		int steps = Math.abs(rowDiff);

		for (int i = 1; i < steps; i++) {
			if (board.getPiece(new Location(
					from.getRow() + i * rowStep,
					from.getCol() + i * colStep)) != null) {
				return true;
			}
		}
		return false;
	}

	private boolean isValidBishopMove(Board board, Location from, Location to) {
		int rawRowDiff = to.getRow() - from.getRow();
		int rawColDiff = to.getCol() - from.getCol();

		boolean isValidDiagonal =
				Math.abs(rawRowDiff) == Math.abs(rawColDiff) && rawRowDiff != 0;
		if (!isValidDiagonal) {
			return false;
		}

		if (isDiagonalPathObstructed(board, from, to)) {
			return false;
		}

		Piece target = board.getPiece(to);
		return target == null || target.getColor() != getColor();
	}

	private boolean isValidKingMove(Board board, Location from, Location to) {
		int rowDiff = Math.abs(to.getRow() - from.getRow());
		int colDiff = Math.abs(to.getCol() - from.getCol());

		if (rowDiff == 0 && colDiff == 2 && !hasMoved()) {
			return isValidCastlingAttempt(board, from, to);
		}

		if ((rowDiff == 0 && colDiff == 0) || rowDiff > 1 || colDiff > 1) {
			return false;
		}

		Piece target = board.getPiece(to);
		return target == null || target.getColor() != getColor();
	}

	private boolean isValidCastlingAttempt(Board board, Location from, Location to) {
		boolean isKingside = (to.getCol() == 6);
		int rookSourceCol = isKingside ? 7 : 0;

		Location rookLocation = new Location(from.getRow(), rookSourceCol);
		Piece rook = board.getPiece(rookLocation);

		if (rook == null
				|| rook.getPieceType() != PieceType.ROOK
				|| rook.getColor() != getColor()
				|| rook.hasMoved()) {
			return false;
		}

		int startCol = Math.min(from.getCol(), rookSourceCol) + 1;
		int endCol = Math.max(from.getCol(), rookSourceCol);

		for (int col = startCol; col < endCol; col++) {
			if (board.getPiece(new Location(from.getRow(), col)) != null) {
				return false;
			}
		}

		return true;
	}

	private boolean isValidQueenMove(Board board, Location from, Location to) {
		int rowDiff = to.getRow() - from.getRow();
		int colDiff = to.getCol() - from.getCol();

		boolean isStraightLine =
				(rowDiff != 0 && colDiff == 0)
						|| (colDiff != 0 && rowDiff == 0);
		boolean isValidDiagonal = Math.abs(rowDiff) == Math.abs(colDiff) && rowDiff != 0;

		if (!isStraightLine && !isValidDiagonal) {
			return false;
		}

		if (isStraightPathObstructed(board, from, to)) {
			return false;
		}

		Piece target = board.getPiece(to);
		return target == null || target.getColor() != getColor();
	}
}
