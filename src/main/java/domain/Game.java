package domain;

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
	public MoveResult makeMove(Location from, Location to, PieceType type){
		Piece piece=board.getPiece(from);
		if(piece==null){
			return MoveResult.INVALID_EMPTY_SOURCE;
		}
		Piece object=board.getPiece(to);
		if(piece.getType()!=PieceType.PAWN){
			if (object!=null && (object.getColor()==piece.getColor())){
				System.out.println(object.getColor());
				return MoveResult.INVALID_SAME_COLOR_CAPTURE;
			} else if (!piece.getColor().equals(currentPlayer.getColor())){
				return MoveResult.INVALID_WRONG_TURN;
			} else if (piece.getColor().equals(currentPlayer.getColor()) &&
					piece.canMove(board,from,to)){
				halfMoveClock+=1;
				Move move=new Move(from,to);
				lastMove=move;
				moveHistory.add(move);
				board.movePiece(from,to);
//				positionHistory.add(board.toPositionString);
				int count = positionHistory.getOrDefault
						(board.toPositionString(), 0);
				positionHistory.put(board.toPositionString(), count + 1);
				switchTurn();
				if (isInCheck(currentPlayer.getColor())){
					return  MoveResult.CHECK;
				}
				else if (isCheckmate(currentPlayer.getColor())){
					return MoveResult.CHECKMATE;
				} else if (isStalemate(currentPlayer.getColor())){
					return MoveResult.STALEMATE;
				}
				return MoveResult.VALID;
			}
		}
		throw new UnsupportedOperationException("pawn error");
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
						return true;
					}
				}
			}
		}
		return false;
	}
	public boolean isCheckmate(Color color){
		return false;
	}
	public boolean isStalemate(Color color){
		return false;
	}
}
