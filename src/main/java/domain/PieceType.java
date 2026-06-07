package domain;

public enum PieceType {
	PAWN("Pawn", 'P'),
	ROOK("Rook", 'R'),
	KNIGHT("Knight", 'N'),
	BISHOP("Bishop", 'B'),
	QUEEN("Queen", 'Q'),
	KING("King", 'K');

	private final String displayName;
	private final char boardSymbol;

	PieceType(String displayName, char boardSymbol) {
		this.displayName = displayName;
		this.boardSymbol = boardSymbol;
	}

	public String getDisplayName() {
		return displayName;
	}

	public char getBoardSymbol() {
		return boardSymbol;
	}
}
