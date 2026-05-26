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
				int halfMoveClock) {
		this.board = board;
		this.status = status;
		this.moveHistory = moveHistory;
		this.lastMove = lastMove;
		this.halfMoveClock = halfMoveClock;
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
		Piece object=board.getPiece(to);
		if(piece.getType()!=PieceType.PAWN){
			if (piece.getColor().equals(currentPlayer.getColor())){
				if(piece.canMove(board,from,to) && (object==null
						|| object.getColor()!=piece.getColor())){
					board.movePiece(from,to);
					if (currentPlayer==white){
						if (isInCheck(Color.BLACK)){
//							throw new IllegalArgumentException("3");
							currentPlayer=black;
							return MoveResult.CHECK;
						}
						currentPlayer=black;
					}else{
						if (isInCheck(Color.WHITE)){
							currentPlayer=white;
							return MoveResult.CHECK;
						}
						currentPlayer=white;
					}
					return MoveResult.VALID;
				}
			}
		}
		throw new UnsupportedOperationException("1");
	}
	public boolean isInCheck(Color color) {
		Location kingLocation = board.findKing(color);

		if (kingLocation == null) {
			return false;
		}
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
}
