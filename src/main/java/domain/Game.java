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

	public void startNewGame(Player p1, Player p2) {
		if (!p1.getName().equals(p2.getName())) {
			this.white = p1;
			this.black = p2;
			white.setColor(Color.WHITE);
			black.setColor(Color.BLACK);
			currentPlayer = white;
			board.initBoard();
		}
	}

}
