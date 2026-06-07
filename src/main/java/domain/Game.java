package domain;

import edu.umd.cs.findbugs.annotations.SuppressFBWarnings;
import java.util.List;
import java.util.ArrayList;
import java.util.Map;

public class Game {
	Board board;
	Player white;
	Player black;
	Player currentPlayer;
	GameStatus status;
	List<Move> moveHistory;
	Move lastMove;
	int halfMoveClock;
	Map<String, Integer> positionHistory;
	int enPassant;
	int castle;

	@SuppressFBWarnings(
			value = "EI_EXPOSE_REP2",
			justification =
					"Board, moveHistory, lastMove, " +
							"and positionHistory are intentionally " +
							"injected for dependency injection " +
							"and testing."
	)
	public Game(Board board,
				GameStatus status,
				List<Move> moveHistory,
				Move lastMove,
				int halfMoveClock,
				Map<String, Integer> positionHistory) {
		this.board = board;
		this.status = status;
		this.moveHistory = moveHistory;
		this.lastMove = lastMove;
		this.halfMoveClock = halfMoveClock;
		this.positionHistory=positionHistory;
		enPassant=0;
		castle=0;
	}
	@SuppressFBWarnings(
			value = "EI_EXPOSE_REP2",
			justification = "Players are intentionally injected and shared with Game."
	)
	public void startNewGame(Player p1, Player p2)throws IllegalArgumentException {
		if (!p1.getName().equals(p2.getName())) {
			this.white = p1;
			this.black = p2;
			white.setColor(PieceColor.WHITE);
			black.setColor(PieceColor.BLACK);
			currentPlayer = white;
			board.initBoard();
		}
		else {
			throw new IllegalArgumentException("please input different player name");
		}
	}
	@SuppressFBWarnings(
			value = "RCN_REDUNDANT_NULLCHECK_OF_NONNULL_VALUE",
			justification =
					"Board.getPiece may return null " +
							"when the source square is empty."
	)
	public MoveResult makeMove(Location from, Location to, PieceType type){
		boolean isCastle=false;
		boolean isEnPassant=false;
		if (!board.isInsideBoard(from) || !board.isInsideBoard(to)){
			return MoveResult.INVALID_OUT_OF_BOUNDS;
		}
		Piece piece=board.getPiece(from);
		if(piece==null){
			return MoveResult.INVALID_EMPTY_SOURCE;
		}
		Piece object=board.getPiece(to);
		if (object!=null && (object.getColor()==piece.getColor())){
			return MoveResult.INVALID_SAME_COLOR_CAPTURE;
		} else if (!piece.getColor().equals(currentPlayer.getColor())){
			return MoveResult.INVALID_WRONG_TURN;
		} else if (piece.getColor().equals(currentPlayer.getColor()) &&
				piece.canMove(board,from,to)) {
			if (isInCheck(currentPlayer.getColor())) {
				return MoveResult.INVALID_SELF_CHECK;
			}
			if(piece.getPieceType()==PieceType.PAWN ||
					(object!=null &&
					object.getColor()!=piece.getColor())){
				halfMoveClock=0;
			}
			halfMoveClock += 1;
			if (halfMoveClock > 100) {
				return MoveResult.DRAW;
			}
			if (isEnPassantMove(from, to, piece)){
				enPassant+=1;
				Location capturedPawn = lastMove.getTo();
				object = board.getPiece(capturedPawn);
				board.setPiece(capturedPawn, null);
				isEnPassant=true;
			}
			else if (isCastleMove(from,to,piece)){
				performCastle(from,to);
				isCastle=true;
			}else {board.movePiece(from, to);}
			String notation=createNotation(from,to,piece,object,type,isCastle,isEnPassant);
			Move move = new Move(from, to, piece, object, type, isCastle, isEnPassant,notation);
			lastMove = move;
			moveHistory.add(move);
			piece.setMoved(true);
			if ((piece.getPieceType() == PieceType.PAWN &&
					piece.getColor() == PieceColor.BLACK && to.getRow() == 7)
					||(piece.getPieceType() == PieceType.PAWN &&piece.getColor()
					== PieceColor.WHITE && to.getRow() == 0)) {
				Piece newPiece=createPromotedPiece(type,piece.getColor());
				board.setPiece(to, newPiece);
				newPiece.setMoved(true);
			}
			int count = positionHistory.getOrDefault
					(board.toPositionString() + currentPlayer.getName(), 0);
			positionHistory.put(
					board.toPositionString() +
							currentPlayer.getName(), count + 1);
			if (positionHistory.getOrDefault(
					board.toPositionString() +
							currentPlayer.getName(), 0) == 3) {
				return MoveResult.DRAW;
			}
			switchTurn();
			if (isCheckmate(currentPlayer.getColor())) {
				return MoveResult.CHECKMATE;
			} else if (isInCheck(currentPlayer.getColor())) {
				return MoveResult.CHECK;
			} else if (isStalemate(currentPlayer.getColor())) {
				status=GameStatus.DRAW;
				return MoveResult.STALEMATE;
			}
			if (currentPlayer.getColor()==PieceColor.WHITE){
				status=GameStatus.WHITE_TURN;
			}else{
				status=GameStatus.BLACK_TURN;
			}
			return MoveResult.VALID;
		}else{
			return MoveResult.INVALID_ILLEGAL_PIECE_MOVE;
		}
	}

	public void switchTurn(){
		if (currentPlayer==white){
			status=GameStatus.BLACK_TURN;
			currentPlayer=black;
		}else{
			status=GameStatus.WHITE_TURN;
			currentPlayer=white;
		}
	}
	public boolean isInCheck(PieceColor color) {
		Location kingLocation = board.findKing(color);
		PieceColor opponentColor=PieceColor.WHITE;
		if(color==PieceColor.WHITE){
			opponentColor = PieceColor.BLACK;
		}
		for (int row = 0; row < 8; row++) {
			for (int col = 0; col < 8; col++) {
				Location from = new Location(row, col);
				Piece piece = board.getPiece(from);
				if (piece != null && piece.getColor() == opponentColor) {
					if (piece.canMove(board, from, kingLocation)) {
						if (opponentColor==PieceColor.WHITE){
							status=GameStatus.BLACK_IN_CHECK;
						}else{
							status=GameStatus.WHITE_IN_CHECK;
						}
						return true;
					}
				}
			}
		}
		return false;
	}
	public boolean isCheckmate(PieceColor color){
		if (!isInCheck(color)){
			return false;
		}
		PieceColor opponentColor=PieceColor.WHITE;
		if(color==PieceColor.WHITE){
			opponentColor = PieceColor.BLACK;
		}
		for (int row = 0; row < 8; row++) {
			for (int col = 0; col < 8; col++) {
				Location from = new Location(row, col);
				Piece piece = board.getPiece(from);
				if (piece != null && piece.getColor() == color) {
					for (int row_des = 0; row_des < 8; row_des++) {
						for (int col_des = 0; col_des < 8; col_des++) {
							Location to=new Location(row_des, col_des);
							if (piece.canMove(board, from, to)){
								Piece target = board.getPiece(to);
								board.movePiece(from, to);
								if (!isInCheck(color)) {
									board.movePiece(to, from);
									board.setPiece(to, target);
									return false;
								}else{
									board.movePiece(to, from);
									board.setPiece(to, target);
								}
							}
						}
					}
				}
			}
		}
		if (opponentColor==PieceColor.WHITE){
			status=GameStatus.WHITE_WIN;
		}else {
			status=GameStatus.BLACK_WIN;
		}
		return true;
	}

	public boolean isStalemate(PieceColor color){
		if (isInCheck(color)){
			return false;
		}
		for (int row = 0; row < 8; row++) {
			for (int col = 0; col < 8; col++) {
				Location from = new Location(row, col);
				Piece piece = board.getPiece(from);
				if (piece != null && piece.getColor() == color) {
					for (int row_des = 0; row_des < 8; row_des++) {
						for (int col_des = 0; col_des < 8; col_des++) {
							Location to=new Location
									(row_des, col_des);
							if (piece.canMove(board, from, to)){
								Piece target=board.getPiece(to);
								board.movePiece(from, to);
								if(!isInCheck(color)){
									board.movePiece(to, from);
									board.setPiece(to, target);
									return false;
								}else{
									board.movePiece(to, from);
									board.setPiece(to, target);
								}

							}
						}
					}
				}
			}
		}
		status=GameStatus.DRAW;
		return true;
	}
	public Piece createPromotedPiece(PieceType promotionType, PieceColor color) {
		switch (promotionType) {
			case QUEEN:
				return new Piece(PieceType.QUEEN,color);
			case ROOK:
				return new Piece(PieceType.ROOK,color);
			case BISHOP:
				return new Piece(PieceType.BISHOP,color);
			default:
				return new Piece(PieceType.KNIGHT,color);
		}
	}
	public void  resign(){
		status=GameStatus.RESIGNED;
	}
	public  GameStatus getStatus(){
		return status;
	}
	public  List<Move> getMoveHistory(){
		return new ArrayList<>(moveHistory);
	}
	public Move getLastMove(){//not used in project, just for test
		return lastMove;
	}
	public void timeOut(){
		if (currentPlayer.getColor()==PieceColor.WHITE){
			status=GameStatus.BLACK_WIN;
		}else{
			status=GameStatus.WHITE_WIN;
		}
	}
	public boolean isEnPassantMove(Location from, Location to, Piece movingPiece) {
		return false;
		//		if (movingPiece == null) {
//			return false;
//		}
//		if (movingPiece.getPieceType() != PieceType.PAWN) {
//			return false;
//		}
//		if (lastMove == null) {
//			return false;
//		}
//		Piece lastMovedPiece = lastMove.getMovedPiece();
//		if (lastMovedPiece == null || lastMovedPiece.getPieceType() != PieceType.PAWN) {
//			return false;
//		}
//		if (lastMovedPiece.getColor() == movingPiece.getColor()) {
//			return false;
//		}
//		if (!board.isEmpty(to)) {
//			return false;
//		}
//		int direction = movingPiece.getColor() == PieceColor.WHITE ? -1 : 1;
//		int rowDiff = to.getRow() - from.getRow();
//		int colDiff = Math.abs(to.getCol() - from.getCol());
//		if (rowDiff != direction || colDiff != 1) {
//			return false;
//		}
//		Location lastFrom = lastMove.getFrom();
//		Location lastTo = lastMove.getTo();
//		if (Math.abs(lastTo.getRow() - lastFrom.getRow()) != 2) {
//			return false;
//		}
//		if (lastTo.getRow() != from.getRow()) {
//			return false;
//		}
//		if (lastTo.getCol() != to.getCol()) {
//			return false;
//		}
//		return true;
	}
	public boolean isCastleMove(Location from, Location to, Piece king) {
		return false;
//		if (king == null || king.getPieceType() != PieceType.KING || king.hasMoved()) {
//			return false;
//		}
//		if (from.getRow() != to.getRow()) {
//			return false;
//		}
//		if (Math.abs(to.getCol() - from.getCol()) != 2) {
//			return false;
//		}
//		int row = from.getRow();
//		boolean kingSide = to.getCol() > from.getCol();
//		Location rookFrom = new Location(row, kingSide ? 7 : 0);
//		Piece rook = board.getPiece(rookFrom);
//		if (rook == null || rook.getPieceType() != PieceType.ROOK) {
//			return false;
//		}
//		if (rook.getColor() != king.getColor() || rook.hasMoved()) {
//			return false;
//		}
//		int startCol = Math.min(from.getCol(), rookFrom.getCol()) + 1;
//		int endCol = Math.max(from.getCol(), rookFrom.getCol()) - 1;
//		for (int col = startCol; col <= endCol; col++) {
//			if (!board.isEmpty(new Location(row, col))) {
//				return false;
//			}
//		}
//		return true;
	}
	public void performCastle(Location kingFrom, Location kingTo) {
//		int row = kingFrom.getRow();
//		boolean kingSide = kingTo.getCol() > kingFrom.getCol();
//		Location rookFrom = new Location(row, kingSide ? 7 : 0);
//		Location rookTo = new Location(row, kingSide ? 5 : 3);
//		board.movePiece(kingFrom, kingTo);
//		board.movePiece(rookFrom, rookTo);
//		board.getPiece(kingTo).setMoved(true);
//		board.getPiece(rookTo).setMoved(true);
	}
	public String createNotation(
			Location from,
			Location to,
			Piece movedPiece,
			Piece capturedPiece,
			PieceType promotionType,
			boolean isCastle,
			boolean isEnPassant) {
		if (isCastle) {
			return to.getCol() == 6 ? "O-O" : "O-O-O";
		}
		String notation = movedPiece.getPieceType()
				+ " (" + from.getRow() + "," + from.getCol() + ")"
				+ " -> "
				+ "(" + to.getRow() + "," + to.getCol() + ")";
		if (capturedPiece != null) {
			notation += " captures " + capturedPiece.getPieceType();
		}
		if (isEnPassant) {
			notation += " en passant";
		}
//		if (promotionType != null) {
//			notation += " promotes to " + promotionType;
//		}
		return notation;
	}
}
