package domain;

import org.junit.jupiter.api.Test;
import java.util.ArrayList;
import java.util.HashMap;
import static org.junit.jupiter.api.Assertions.*;

public class enPassantIntegrationTest {

	@Test
	public void testWhiteCapturesBlackEnPassant() {
		Player white = new Player("WhitePlayer", PieceColor.WHITE);
		Player black = new Player("BlackPlayer", PieceColor.BLACK);
		Board board = new Board();
		board.initBoard();

		Game game = new Game(board, GameStatus.WHITE_TURN, new ArrayList<>(), null, 0, new HashMap<>());
		game.startNewGame(white, black);

		assertEquals(MoveResult.VALID, game.makeMove(new Location(6, 4), new Location(4, 4), null));
		assertEquals(MoveResult.VALID, game.makeMove(new Location(1, 0), new Location(2, 0), null));
		assertEquals(MoveResult.VALID, game.makeMove(new Location(4, 4), new Location(3, 4), null));
		assertEquals(MoveResult.VALID, game.makeMove(new Location(1, 3), new Location(3, 3), null));

		assertFalse(board.isEmpty(new Location(3, 4)), "White pawn should be at (3,4)");
		assertFalse(board.isEmpty(new Location(3, 3)), "Black pawn should be at (3,3)");

		assertEquals(MoveResult.VALID, game.makeMove(new Location(3, 4), new Location(2, 3), null));

		Piece whitePawn = board.getPiece(new Location(2, 3));
		assertNotNull(whitePawn);
		assertEquals(PieceColor.WHITE, whitePawn.getColor());
		assertEquals(PieceType.PAWN, whitePawn.getPieceType());

		assertTrue(board.isEmpty(new Location(3, 3)), "Black pawn was not removed from the board during En Passant!");

		assertTrue(board.isEmpty(new Location(3, 4)));

		Move lastMove = game.getLastMove();
		assertNotNull(lastMove);
		assertTrue(lastMove.isEnPassant(), "The move history did not flag this as an En Passant move.");
	}

	@Test
	public void testBlackCapturesWhiteEnPassant() {
		Player white = new Player("WhitePlayer", PieceColor.WHITE);
		Player black = new Player("BlackPlayer", PieceColor.BLACK);
		Board board = new Board();
		board.initBoard();

		Game game = new Game(board, GameStatus.WHITE_TURN, new ArrayList<>(), null, 0, new HashMap<>());
		game.startNewGame(white, black);

		assertEquals(MoveResult.VALID, game.makeMove(new Location(6, 0), new Location(5, 0), null));

		assertEquals(MoveResult.VALID, game.makeMove(new Location(1, 3), new Location(3, 3), null));

		assertEquals(MoveResult.VALID, game.makeMove(new Location(5, 0), new Location(4, 0), null));

		assertEquals(MoveResult.VALID, game.makeMove(new Location(3, 3), new Location(4, 3), null));

		assertEquals(MoveResult.VALID, game.makeMove(new Location(6, 4), new Location(4, 4), null));

		assertFalse(board.isEmpty(new Location(4, 4)), "White pawn should be at (4,4)");
		assertFalse(board.isEmpty(new Location(4, 3)), "Black pawn should be at (4,3)");

		assertEquals(MoveResult.VALID, game.makeMove(new Location(4, 3), new Location(5, 4), null));

		Piece blackPawn = board.getPiece(new Location(5, 4));
		assertNotNull(blackPawn);
		assertEquals(PieceColor.BLACK, blackPawn.getColor());
		assertEquals(PieceType.PAWN, blackPawn.getPieceType());

		assertTrue(board.isEmpty(new Location(4, 4)), "White pawn was not removed from the board during En Passant!");
		assertTrue(game.getLastMove().isEnPassant());
	}
}
