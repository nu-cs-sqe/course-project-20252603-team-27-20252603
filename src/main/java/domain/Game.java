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
	}
	@SuppressFBWarnings(
			value = "EI_EXPOSE_REP2",
			justification = "Players are intentionally injected and shared with Game."
	)
	public void startNewGame(Player p1, Player p2)throws IllegalArgumentException {
		if (!p1.getName().equals(p2.getName())) {
			this.white = p1;
			this.black = p2;
			white.setColor(Color.WHITE);
			black.setColor(Color.BLACK);
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
			if(piece.getType()==PieceType.PAWN ||
					(object!=null &&
					object.getColor()!=piece.getColor())){
				halfMoveClock=0;
			}
			halfMoveClock += 1;
			Move move = new Move(from, to);
			lastMove = move;
			moveHistory.add(move);
			if (halfMoveClock > 100) {
				return MoveResult.DRAW;
			}
			board.movePiece(from, to);
			if ((piece.getType() == PieceType.PAWN &&
					piece.getColor() == Color.BLACK && to.getRow() == 7)
					||(piece.getType() == PieceType.PAWN &&piece.getColor()
					== Color.WHITE && to.getRow() == 0)) {
				Piece newPiece=createPromotedPiece(type,piece.getColor());
				board.setPiece(to, newPiece);
//				piece=type;
			}
//				positionHistory.add(board.toPositionString);
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
			if (currentPlayer.getColor()==Color.WHITE){
				status=GameStatus.WHITE_TURN;
			}else{
				status=GameStatus.BLACK_TURN;
			}
			return MoveResult.VALID;
//		} else if (!piece.canMove(board, from,to)) {
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
	public boolean isInCheck(Color color) {
		Location kingLocation = board.findKing(color);
//		if (kingLocation == null) {
//			return false;
//		}
		Color opponentColor=Color.WHITE;
		if(color==Color.WHITE){
			opponentColor = Color.BLACK;
		}
		for (int row = 0; row < 8; row++) {
			for (int col = 0; col < 8; col++) {
				Location from = new Location(row, col);
				Piece piece = board.getPiece(from);
				if (piece != null && piece.getColor() == opponentColor) {
					if (piece.canMove(board, from, kingLocation)) {
						if (opponentColor==Color.WHITE){
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
	public boolean isCheckmate(Color color){
		if (!isInCheck(color)){
			return false;
		}
//		Location kingLocation = board.findKing(color);
		Color opponentColor=Color.WHITE;
		if(color==Color.WHITE){
			opponentColor = Color.BLACK;
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
		if (opponentColor==Color.WHITE){
			status=GameStatus.WHITE_WIN;
		}else {
			status=GameStatus.BLACK_WIN;
		}
		return true;
	}

	public boolean isStalemate(Color color){
		if (isInCheck(color)){
			return false;
		}
//		Location kingLocation = board.findKing(color);
//		Color opponentColor=Color.WHITE;
//		if(color==Color.WHITE){
//			opponentColor = Color.BLACK;
//		}
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
	public Piece createPromotedPiece(PieceType promotionType, Color color) {
		switch (promotionType) {
			case QUEEN:
				return new Queen(color);
			case ROOK:
				return new Rook(color);
			case BISHOP:
				return new Bishop(color);
			default:
				return new Knight(color);
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
		if (currentPlayer.getColor()==Color.WHITE){
			status=GameStatus.BLACK_WIN;
		}else{
			status=GameStatus.WHITE_WIN;
		}
	}
}
