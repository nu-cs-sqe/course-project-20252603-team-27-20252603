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

	@Test
	public void makeMove_blackPawnPromotes_killsMutantIntegration() throws Exception {
		Player p1 = new Player("p1", PieceColor.WHITE);
		Player p2 = new Player("p2", PieceColor.BLACK);
		Board board = new Board();
		Game game = new Game(board, GameStatus.WHITE_TURN, new ArrayList<>(), null, 0, new HashMap<>());

		game.startNewGame(p1, p2);

		board = game.getBoard();
		board.clearBoard();

		Location from = new Location(6, 4);
		Location to = new Location(7, 4);

		board.setPiece(from, new Piece(PieceType.PAWN, PieceColor.BLACK));

		board.setPiece(new Location(0, 0), new Piece(PieceType.KING, PieceColor.BLACK));
		board.setPiece(new Location(5, 7), new Piece(PieceType.KING, PieceColor.WHITE));

		java.lang.reflect.Field playerField = Game.class.getDeclaredField("currentPlayer");
		playerField.setAccessible(true);
		playerField.set(game, p2);

		MoveResult result = game.makeMove(from, to, PieceType.QUEEN);

		assertEquals(MoveResult.VALID, result);

		Piece promotedPiece = board.getPiece(to);
		assertNotNull(promotedPiece);
		assertEquals(PieceType.QUEEN, promotedPiece.getPieceType());
		assertEquals(PieceColor.BLACK, promotedPiece.getColor());
	}

	@Test
	public void makeMove_castlingIncrementsCounter_killsCastleMutant() throws Exception {
		Player p1 = new Player("p1", PieceColor.WHITE);
		Player p2 = new Player("p2", PieceColor.BLACK);
		Board board = new Board();
		Game game = new Game(board, GameStatus.WHITE_TURN, new ArrayList<>(), null, 0, new HashMap<>());

		game.startNewGame(p1, p2);

		board = game.getBoard();
		board.clearBoard();

		Location kingStart = new Location(7, 4);
		Location kingEnd = new Location(7, 6); // Standard Kingside destination
		Location rookStart = new Location(7, 7);

		Piece king = new Piece(PieceType.KING, PieceColor.WHITE);
		Piece rook = new Piece(PieceType.ROOK, PieceColor.WHITE);

		king.setMoved(false);
		rook.setMoved(false);

		board.setPiece(kingStart, king);
		board.setPiece(rookStart, rook);

		board.setPiece(new Location(0, 4), new Piece(PieceType.KING, PieceColor.BLACK));

		java.lang.reflect.Field castleField = Game.class.getDeclaredField("castle");
		castleField.setAccessible(true);
		int initialCastleCount = (int) castleField.get(game);

		MoveResult result = game.makeMove(kingStart, kingEnd, null);

		assertEquals(MoveResult.VALID, result);

		int finalCastleCount = (int) castleField.get(game);
		assertEquals(initialCastleCount + 1, finalCastleCount);
	}
}
