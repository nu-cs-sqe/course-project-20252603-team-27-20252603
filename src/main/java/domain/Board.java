package domain;

public class Board {
	private Piece[][] pieces;
	public static final int TOTAL_ROWS = 8;
	public static final int TOTAL_COLS = 8;
	public Board() {
		this.pieces = new Piece[TOTAL_ROWS][TOTAL_COLS];
	}

	// Test-friendly constructor to inject a pre-populated pieces array (e.g. mocks)
	public Board(Piece[][] pieces) {
		if (pieces == null) {
			this.pieces = new Piece[TOTAL_ROWS][TOTAL_COLS];
		} else {
			this.pieces = new Piece[TOTAL_ROWS][TOTAL_COLS];
			for (int row = 0; row < TOTAL_ROWS; row++) {
				if (pieces[row] != null) {
					System.arraycopy(pieces[row], 0,
							this.pieces[row], 0, TOTAL_COLS);
				}
			}
		}
	}

	public void clearBoard() {
		for (int row = 0; row < TOTAL_ROWS; row++) {
			for (int col = 0; col < TOTAL_COLS; col++) {
				pieces[row][col] = null;
			}
		}
	}
	public Piece getPiece(Location location) {
		return pieces[location.getRow()][location.getCol()];
	}
	public void movePiece(Location from, Location to){}
	public Location findKing(PieceColor color){
		return new Location(0, 0);
	}
	public String toPositionString(){
		return "position";
	}
	public boolean isInsideBoard(Location location) {
		return true;
	}
	public void setPiece(Location location, Piece piece) {
		int row = location.getRow();
		int col = location.getCol();
//		Piece previous = pieces[row][col];
		pieces[row][col] = piece;
//		return previous;
	}
	public void initBoard() {clearBoard();
//		pieces[0][0] = new Rook(PieceColor.BLACK);
//		pieces[0][1] = new Knight(PieceColor.BLACK);
//		pieces[0][2] = new Bishop(PieceColor.BLACK);
//		pieces[0][3] = new Queen(PieceColor.BLACK);
//		pieces[0][4] = new King(PieceColor.BLACK);
//		pieces[0][5] = new Bishop(PieceColor.BLACK);
//		pieces[0][6] = new Knight(PieceColor.BLACK);
//		pieces[0][7] = new Rook(PieceColor.BLACK);
//
//		for (int col = 0; col < TOTAL_COLS; col++) {
//			pieces[1][col] = new Pawn(PieceColor.BLACK);
//			pieces[6][col] = new Pawn(PieceColor.WHITE);
//		}
//
//		pieces[7][0] = new Rook(PieceColor.WHITE);
//		pieces[7][1] = new Knight(PieceColor.WHITE);
//		pieces[7][2] = new Bishop(PieceColor.WHITE);
//		pieces[7][3] = new Queen(PieceColor.WHITE);
//		pieces[7][4] = new King(PieceColor.WHITE);
//		pieces[7][5] = new Bishop(PieceColor.WHITE);
//		pieces[7][6] = new Knight(PieceColor.WHITE);
//		pieces[7][7] = new Rook(PieceColor.WHITE);
	}
	public boolean isEmpty(Location to){
		return false;
	}

}