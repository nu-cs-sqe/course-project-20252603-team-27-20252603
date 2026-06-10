package domain;

import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.HashMap;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class ScholarsMateIntegrationTest {

	@Test
	public void testScholarsMate() {
		Player white = new Player("White", PieceColor.WHITE);
		Player black = new Player("Black", PieceColor.BLACK);
		Board board = new Board();
		board.initBoard();

		Game game = new Game(board, GameStatus.WHITE_TURN, new ArrayList<>(), null, 0, new HashMap<>());
		game.startNewGame(white, black);

		assertEquals(MoveResult.VALID, game.makeMove(new Location(6, 4), new Location(4, 4), null));
		assertEquals(MoveResult.VALID, game.makeMove(new Location(1, 4), new Location(3, 4), null));
		assertEquals(MoveResult.VALID, game.makeMove(new Location(7, 5), new Location(4, 2), null));
		assertEquals(MoveResult.VALID, game.makeMove(new Location(0, 1), new Location(2, 2), null));
		assertEquals(MoveResult.VALID, game.makeMove(new Location(7, 3), new Location(3, 7), null));
		assertEquals(MoveResult.VALID, game.makeMove(new Location(0, 6), new Location(2, 5), null));
		assertEquals(MoveResult.CHECKMATE, game.makeMove(new Location(3, 7), new Location(1, 5), null));
		assertEquals(GameStatus.WHITE_WIN, game.getStatus());
	}
}
