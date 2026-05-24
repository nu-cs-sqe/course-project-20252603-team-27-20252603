package domain;

import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import org.easymock.EasyMock;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class GameTest {
	//    Board board;
	@Test
	public void startNewGame_prepareBoard() {
		Player player1 = EasyMock.createMock(Player.class);
		Player player2 = EasyMock.createMock(Player.class);
		Board board = EasyMock.createMock(Board.class);
		EasyMock.expect(player1.getName()).andStubReturn("p1");
		//        EasyMock.replay(player1);
		EasyMock.expect(player2.getName()).andStubReturn("p2");
		//        EasyMock.replay(player2);
		board.initBoard();
		EasyMock.expectLastCall();
		EasyMock.replay(board);
		//        List<String> names = List.of("ROOK");
		//        EasyMock.expect(board.initBoard()).andStubReturn(names);
		GameStatus status = GameStatus.WHITE_TURN;
		List<Move> moveHistory = new ArrayList<>();
		Move lastMove = null;
		int halfMoveClock = 0;
		Game game = new Game(board, status, moveHistory, lastMove, halfMoveClock);
		//        Player white = EasyMock.createMock(Player.class);
		//        Player black = EasyMock.createMock(Player.class);
		player1.setColor(Color.WHITE);
		EasyMock.expectLastCall();
		EasyMock.replay(player1);
		player2.setColor(Color.BLACK);
		EasyMock.expectLastCall();
		EasyMock.replay(player2);
		game.startNewGame(player1, player2);
		EasyMock.verify(player1, player2, board);
	}
}
