package domain;

import org.junit.jupiter.api.Test;
import java.util.ArrayList;
import java.util.HashMap;
import static org.junit.jupiter.api.Assertions.*;

public class CastlingIntegrationTest {
	@Test
	public void testWhiteKingsideCastle() {
		Player white = new Player("White", PieceColor.WHITE);
		Player black = new Player("Black", PieceColor.BLACK);
		Board board = new Board();
		board.initBoard();

		Game game = new Game(board, GameStatus.WHITE_TURN, new ArrayList<>(), null, 0, new HashMap<>());
		game.startNewGame(white, black);

		game.makeMove(new Location(6, 4), new Location(4, 4), null);
		game.makeMove(new Location(1, 0), new Location(2, 0), null); // Black waste move

		game.makeMove(new Location(7, 5), new Location(4, 2), null);
		game.makeMove(new Location(2, 0), new Location(3, 0), null); // Black waste move

		game.makeMove(new Location(7, 6), new Location(5, 5), null);
		game.makeMove(new Location(3, 0), new Location(4, 0), null); // Black waste move

		assertEquals(MoveResult.VALID, game.makeMove(new Location(7, 4), new Location(7, 6), null));

		assertEquals(PieceType.KING, board.getPiece(new Location(7, 6)).getPieceType());

		assertEquals(PieceType.ROOK, board.getPiece(new Location(7, 5)).getPieceType());

		assertTrue(board.isEmpty(new Location(7, 4))); // Old King square
		assertTrue(board.isEmpty(new Location(7, 7))); // Old Rook square
	}
}
