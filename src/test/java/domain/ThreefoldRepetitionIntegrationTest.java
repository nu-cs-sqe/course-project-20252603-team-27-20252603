package domain;

import org.junit.jupiter.api.Test;
import java.util.ArrayList;
import java.util.HashMap;
import static org.junit.jupiter.api.Assertions.*;

public class ThreefoldRepetitionIntegrationTest {

	@Test
	public void testThreefoldRepetitionDraw() {
		Player white = new Player("WhitePlayer", PieceColor.WHITE);
		Player black = new Player("BlackPlayer", PieceColor.BLACK);
		Board board = new Board();
		board.initBoard();

		Game game = new Game(board, GameStatus.WHITE_TURN, new ArrayList<>(), null, 0, new HashMap<>());
		game.startNewGame(white, black);

		assertEquals(MoveResult.VALID, game.makeMove(new Location(7, 6), new Location(5, 5), null));
		assertEquals(MoveResult.VALID, game.makeMove(new Location(0, 6), new Location(2, 5), null));
		assertEquals(MoveResult.VALID, game.makeMove(new Location(5, 5), new Location(7, 6), null));
		assertEquals(MoveResult.VALID, game.makeMove(new Location(2, 5), new Location(0, 6), null));

		assertEquals(MoveResult.VALID, game.makeMove(new Location(7, 1), new Location(5, 2), null));
		assertEquals(MoveResult.VALID, game.makeMove(new Location(0, 1), new Location(2, 2), null));
		assertEquals(MoveResult.VALID, game.makeMove(new Location(5, 2), new Location(7, 1), null));
		assertEquals(MoveResult.VALID, game.makeMove(new Location(2, 2), new Location(0, 1), null));

		assertEquals(MoveResult.VALID, game.makeMove(new Location(7, 6), new Location(5, 5), null));
		assertEquals(MoveResult.VALID, game.makeMove(new Location(0, 6), new Location(2, 5), null));
		assertEquals(MoveResult.VALID, game.makeMove(new Location(5, 5), new Location(7, 6), null));

		assertEquals(MoveResult.DRAW, game.makeMove(new Location(2, 5), new Location(0, 6), null));

		assertEquals(GameStatus.DRAW, game.getStatus());
	}
}