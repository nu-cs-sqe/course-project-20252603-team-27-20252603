package domain;

import org.easymock.EasyMock;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static java.lang.Boolean.FALSE;
import static java.lang.Boolean.TRUE;
import static org.junit.jupiter.api.Assertions.*;

public class GameTest {

	@Test
	public void startNewGame_prepareBoard() {
		Player player1 = new Player("p1", PieceColor.WHITE);
		Player player2 = new Player("p2", PieceColor.BLACK);
		Board board = EasyMock.createMock(Board.class);
		board.initBoard();
		EasyMock.expectLastCall();
		EasyMock.replay(board);
		Game game = new Game(board, GameStatus.WHITE_TURN, new ArrayList<>(), null, 0, new HashMap<>());
		game.startNewGame(player1, player2);
		EasyMock.verify(board);
	}

	@Test
	public void startNewGame_sameInput() {
		Player player1 = new Player("p1", PieceColor.WHITE);
		Player player2 = new Player("p1", PieceColor.BLACK);
		Board board = EasyMock.createMock(Board.class);
		EasyMock.replay(board);
		Game game = new Game(board, GameStatus.WHITE_TURN, new ArrayList<>(), null, 0, new HashMap<>());

		assertThrows(IllegalArgumentException.class, () -> game.startNewGame(player1, player2));

		EasyMock.verify(board);
	}

	@Test
	public void makeMove_validMove() {
		Player player1 = new Player("p1", PieceColor.WHITE);
		Player player2 = new Player("p2", PieceColor.BLACK);
		Location source = new Location(7, 0);
		Location destination = new Location(0, 7);
		Board board = EasyMock.createMock(Board.class);
		Piece rook = EasyMock.createMock(Piece.class);
		board.initBoard();
		EasyMock.expectLastCall();

		// -- APPLIED WILDCARDS HERE --
		board.movePiece(EasyMock.anyObject(Location.class), EasyMock.anyObject(Location.class));
		EasyMock.expectLastCall().anyTimes();
		board.setPiece(EasyMock.anyObject(Location.class), EasyMock.anyObject(Piece.class));
		EasyMock.expectLastCall().anyTimes();

		EasyMock.expect(board.getPiece(destination)).andStubReturn(new Piece(PieceType.EMPTY, null));
		EasyMock.expect(board.getPiece(source)).andStubReturn(rook);
		EasyMock.expect(board.isInsideBoard(source)).andReturn(TRUE).anyTimes();
		EasyMock.expect(board.isInsideBoard(destination)).andReturn(TRUE).anyTimes();
		EasyMock.expect(board.toPositionString()).andReturn("last").anyTimes();
		EasyMock.expect(rook.canMove(board, source, destination)).andStubReturn(TRUE);
		EasyMock.expect(rook.getPieceType()).andStubReturn(PieceType.ROOK);
		EasyMock.expect(rook.getColor()).andStubReturn(PieceColor.WHITE);

		Location wKingLoc = new Location(7, 4);
		Location bKingLoc = new Location(0, 4);
		EasyMock.expect(board.findKing(PieceColor.WHITE)).andStubReturn(wKingLoc);
		EasyMock.expect(board.findKing(PieceColor.BLACK)).andStubReturn(bKingLoc);
		EasyMock.expect(board.getPiece(wKingLoc)).andStubReturn(new Piece(PieceType.KING, PieceColor.WHITE));
		EasyMock.expect(board.getPiece(bKingLoc)).andStubReturn(new Piece(PieceType.KING, PieceColor.BLACK));

		Map<String, Integer> positionHistory = new HashMap<>();
		List<Move> moveHistory = new ArrayList<>();
		Game game = EasyMock.partialMockBuilder(Game.class).withConstructor(Board.class, GameStatus.class, List.class, Move.class, int.class, Map.class)
				.withArgs(board, GameStatus.WHITE_TURN, moveHistory, null, 0, positionHistory)
				.addMockedMethod("isInCheck", PieceColor.class).addMockedMethod("isCheckmate", PieceColor.class).addMockedMethod("isStalemate", PieceColor.class).createMock();

		EasyMock.expect(game.isInCheck(PieceColor.WHITE)).andReturn(false);
		EasyMock.expect(game.isInCheck(PieceColor.BLACK)).andReturn(false);
		EasyMock.expect(game.isCheckmate(PieceColor.BLACK)).andReturn(false);
		EasyMock.expect(game.isStalemate(PieceColor.BLACK)).andReturn(false);
		rook.setMoved(true);
		EasyMock.expectLastCall();
		EasyMock.replay(board, rook, game);
		game.startNewGame(player1, player2);

		MoveResult result = game.makeMove(source, destination, PieceType.KNIGHT);
		Move last = new Move(source, destination, rook, new Piece(PieceType.EMPTY, null), PieceType.KNIGHT);
		Map<String, Integer> target = new HashMap<>();
		target.put("last|WHITE|WK_Moved:false,BK_Moved:false|None", 1);

		assertEquals(GameStatus.BLACK_TURN, game.getStatus());
		assertEquals(target, game.positionHistory);
		assertEquals(last, game.lastMove);
		moveHistory.add(last);
		assertEquals(moveHistory, game.moveHistory);
		assertEquals(game.halfMoveClock, 1);
		assertEquals(game.currentPlayer.getColor(), PieceColor.BLACK);
		assertEquals(result, MoveResult.VALID);
		EasyMock.verify(board, rook, game);
	}

	@Test
	public void makeMove_InCheck_white() {
		Board board = EasyMock.createMock(Board.class);
		Player player1 = new Player("p1", PieceColor.WHITE);
		Player player2 = new Player("p2", PieceColor.BLACK);
		Piece piece = EasyMock.createMock(Piece.class);

		Location wKingLoc = new Location(7, 4);
		Location bKingLoc = new Location(0, 4);
		EasyMock.expect(board.findKing(PieceColor.WHITE)).andStubReturn(wKingLoc);
		EasyMock.expect(board.findKing(PieceColor.BLACK)).andStubReturn(bKingLoc);
		EasyMock.expect(board.getPiece(wKingLoc)).andStubReturn(new Piece(PieceType.KING, PieceColor.WHITE));
		EasyMock.expect(board.getPiece(bKingLoc)).andStubReturn(new Piece(PieceType.KING, PieceColor.BLACK));

		List<Move> moveHistory = new ArrayList<>();
		Map<String, Integer> positionHistory = new HashMap<>();
		Game game = EasyMock.partialMockBuilder(Game.class).withConstructor(Board.class, GameStatus.class, List.class, Move.class, int.class, Map.class)
				.withArgs(board, GameStatus.WHITE_TURN, moveHistory, null, 0, positionHistory)
				.addMockedMethod("isInCheck", PieceColor.class).addMockedMethod("isCheckmate", PieceColor.class).addMockedMethod("createNotation").createMock();
		Location from = new Location(7, 0);
		Location to = new Location(0, 0);
		EasyMock.expect(board.isInsideBoard(from)).andReturn(TRUE).anyTimes();
		EasyMock.expect(board.isInsideBoard(to)).andReturn(TRUE).anyTimes();
		EasyMock.expect(board.toPositionString()).andReturn("last").anyTimes();
		board.initBoard();
		EasyMock.expectLastCall();
		piece.setMoved(true);
		EasyMock.expectLastCall();

		// -- APPLIED STUB RETURNS HERE --
		EasyMock.expect(board.getPiece(from)).andStubReturn(piece);
		EasyMock.expect(board.getPiece(to)).andStubReturn(new Piece(PieceType.EMPTY, null));

		EasyMock.expect(piece.getPieceType()).andReturn(PieceType.ROOK).anyTimes();
		EasyMock.expect(piece.getColor()).andReturn(PieceColor.WHITE).anyTimes();
		EasyMock.expect(piece.canMove(board, from, to)).andReturn(true);

		// -- APPLIED WILDCARDS HERE --
		board.movePiece(EasyMock.anyObject(Location.class), EasyMock.anyObject(Location.class));
		EasyMock.expectLastCall().anyTimes();
		board.setPiece(EasyMock.anyObject(Location.class), EasyMock.anyObject(Piece.class));
		EasyMock.expectLastCall().anyTimes();

		EasyMock.expect(game.isInCheck(PieceColor.WHITE)).andReturn(false);
		EasyMock.expect(game.isCheckmate(PieceColor.BLACK)).andReturn(false);
		EasyMock.expect(game.isInCheck(PieceColor.BLACK)).andReturn(true);
		EasyMock.expect(game.createNotation(from, to, piece, new Piece(PieceType.EMPTY, null), null, false, false)).andReturn("notation");
		EasyMock.replay(board, piece, game);

		game.startNewGame(player1, player2);
		MoveResult result = game.makeMove(from, to, null);
		Move last = new Move(from, to, piece, null, null, false, false, "notation");
		Map<String, Integer> target = new HashMap<>();
		target.put("last|WHITE|WK_Moved:false,BK_Moved:false|None", 1);

		assertEquals(target, game.positionHistory);
		assertEquals(last, game.lastMove);
		moveHistory.add(last);
		assertEquals(moveHistory, game.moveHistory);
		assertEquals(game.currentPlayer.getColor(), PieceColor.BLACK);
		assertEquals(game.halfMoveClock, 1);
		assertEquals(MoveResult.CHECK, result);
		EasyMock.verify(board, piece, game);
	}

	@Test
	public void makeMove_Checkmate_black() {
		Board board = EasyMock.createMock(Board.class);
		Player player1 = new Player("p1", PieceColor.WHITE);
		Player player2 = new Player("p2", PieceColor.BLACK);
		Piece piece = EasyMock.createMock(Piece.class);

		Location wKingLoc = new Location(7, 4);
		Location bKingLoc = new Location(0, 4);
		EasyMock.expect(board.findKing(PieceColor.WHITE)).andStubReturn(wKingLoc);
		EasyMock.expect(board.findKing(PieceColor.BLACK)).andStubReturn(bKingLoc);
		EasyMock.expect(board.getPiece(wKingLoc)).andStubReturn(new Piece(PieceType.KING, PieceColor.WHITE));
		EasyMock.expect(board.getPiece(bKingLoc)).andStubReturn(new Piece(PieceType.KING, PieceColor.BLACK));

		List<Move> moveHistory = new ArrayList<>();
		Map<String, Integer> positionHistory = new HashMap<>();
		positionHistory.put("first", 2);
		Game game = EasyMock.partialMockBuilder(Game.class).withConstructor(Board.class, GameStatus.class, List.class, Move.class, int.class, Map.class)
				.withArgs(board, GameStatus.WHITE_TURN, moveHistory, null, 0, positionHistory)
				.addMockedMethod("isInCheck", PieceColor.class).addMockedMethod("isCheckmate", PieceColor.class).addMockedMethod("createNotation").createMock();
		Location from = new Location(0, 0);
		Location to = new Location(7, 0);
		EasyMock.expect(board.isInsideBoard(from)).andReturn(TRUE).anyTimes();
		EasyMock.expect(board.isInsideBoard(to)).andReturn(TRUE).anyTimes();
		EasyMock.expect(board.toPositionString()).andReturn("last").anyTimes();
		board.initBoard();
		EasyMock.expectLastCall();
		piece.setMoved(true);
		EasyMock.expectLastCall();

		// -- APPLIED STUB RETURNS HERE --
		EasyMock.expect(board.getPiece(from)).andStubReturn(piece);
		EasyMock.expect(board.getPiece(to)).andStubReturn(new Piece(PieceType.EMPTY, null));

		EasyMock.expect(piece.getPieceType()).andReturn(PieceType.KNIGHT).anyTimes();
		EasyMock.expect(piece.getColor()).andReturn(PieceColor.BLACK).anyTimes();
		EasyMock.expect(piece.canMove(board, from, to)).andReturn(true);

		// -- APPLIED WILDCARDS HERE --
		board.movePiece(EasyMock.anyObject(Location.class), EasyMock.anyObject(Location.class));
		EasyMock.expectLastCall().anyTimes();
		board.setPiece(EasyMock.anyObject(Location.class), EasyMock.anyObject(Piece.class));
		EasyMock.expectLastCall().anyTimes();

		EasyMock.expect(game.isInCheck(PieceColor.BLACK)).andStubReturn(false);
		EasyMock.expect(game.isCheckmate(PieceColor.WHITE)).andReturn(true);
		EasyMock.expect(game.createNotation(from, to, piece, new Piece(PieceType.EMPTY, null), null, false, false)).andReturn("notation");
		EasyMock.replay(board, piece, game);

		game.startNewGame(player1, player2);
		game.switchTurn();
		MoveResult result = game.makeMove(from, to, null);
		Move last = new Move(from, to, piece, null, null, false, false, "notation");
		Map<String, Integer> target = new HashMap<>();
		target.put("first", 2);
		target.put("last|BLACK|WK_Moved:false,BK_Moved:false|None", 1);

		assertEquals(target, game.positionHistory);
		assertEquals(last, game.lastMove);
		moveHistory.add(last);
		assertEquals(moveHistory, game.moveHistory);
		assertEquals(game.halfMoveClock, 1);
		assertEquals(MoveResult.CHECKMATE, result);
		assertEquals(game.currentPlayer.getColor(), PieceColor.WHITE);
		EasyMock.verify(board, piece, game);
	}

	@Test
	public void makeMove_Stalemate_white() {
		Board board = EasyMock.createMock(Board.class);
		Player player1 = new Player("p1", PieceColor.WHITE);
		Player player2 = new Player("p2", PieceColor.BLACK);
		Piece piece = EasyMock.createMock(Piece.class);

		Location wKingLoc = new Location(7, 4);
		Location bKingLoc = new Location(0, 4);
		EasyMock.expect(board.findKing(PieceColor.WHITE)).andStubReturn(wKingLoc);
		EasyMock.expect(board.findKing(PieceColor.BLACK)).andStubReturn(bKingLoc);
		EasyMock.expect(board.getPiece(wKingLoc)).andStubReturn(new Piece(PieceType.KING, PieceColor.WHITE));
		EasyMock.expect(board.getPiece(bKingLoc)).andStubReturn(new Piece(PieceType.KING, PieceColor.BLACK));

		List<Move> moveHistory = new ArrayList<>();
		Map<String, Integer> positionHistory = new HashMap<>();
		Game game = EasyMock.partialMockBuilder(Game.class).withConstructor(Board.class, GameStatus.class, List.class, Move.class, int.class, Map.class)
				.withArgs(board, GameStatus.BLACK_IN_CHECK, moveHistory, null, 1, positionHistory)
				.addMockedMethod("isInCheck", PieceColor.class).addMockedMethod("isCheckmate", PieceColor.class).addMockedMethod("isStalemate", PieceColor.class).addMockedMethod("createNotation").createMock();
		Location from = new Location(0, 0);
		Location to = new Location(7, 7);
		EasyMock.expect(board.isInsideBoard(from)).andReturn(TRUE).anyTimes();
		EasyMock.expect(board.isInsideBoard(to)).andReturn(TRUE).anyTimes();
		EasyMock.expect(board.toPositionString()).andReturn("last").anyTimes();
		piece.setMoved(true);
		EasyMock.expectLastCall();
		board.initBoard();
		EasyMock.expectLastCall();

		// -- APPLIED STUB RETURNS HERE --
		EasyMock.expect(board.getPiece(from)).andStubReturn(piece);
		EasyMock.expect(board.getPiece(to)).andStubReturn(new Piece(PieceType.EMPTY, null));

		EasyMock.expect(piece.getPieceType()).andReturn(PieceType.KNIGHT).anyTimes();
		EasyMock.expect(piece.getColor()).andReturn(PieceColor.WHITE).anyTimes();
		EasyMock.expect(piece.canMove(board, from, to)).andReturn(true);

		// -- APPLIED WILDCARDS HERE --
		board.movePiece(EasyMock.anyObject(Location.class), EasyMock.anyObject(Location.class));
		EasyMock.expectLastCall().anyTimes();
		board.setPiece(EasyMock.anyObject(Location.class), EasyMock.anyObject(Piece.class));
		EasyMock.expectLastCall().anyTimes();

		EasyMock.expect(game.isInCheck(PieceColor.WHITE)).andStubReturn(false);
		EasyMock.expect(game.isInCheck(PieceColor.BLACK)).andReturn(false);
		EasyMock.expect(game.isCheckmate(PieceColor.BLACK)).andReturn(false);
		EasyMock.expect(game.isStalemate(PieceColor.BLACK)).andReturn(true);
		EasyMock.expect(game.createNotation(from, to, piece, new Piece(PieceType.EMPTY, null), null, false, false)).andReturn("notation");
		EasyMock.replay(board, piece, game);

		game.startNewGame(player1, player2);
		MoveResult result = game.makeMove(from, to, null);
		Move last = new Move(from, to, piece, null, null, false, false, "notation");
		Map<String, Integer> target = new HashMap<>();
		target.put("last|WHITE|WK_Moved:false,BK_Moved:false|None", 1);

		assertEquals(target, game.positionHistory);
		assertEquals(last, game.lastMove);
		moveHistory.add(last);
		assertEquals(moveHistory, game.moveHistory);
		assertEquals(game.halfMoveClock, 2);
		assertEquals(MoveResult.STALEMATE, result);
		assertEquals(game.currentPlayer.getColor(), PieceColor.BLACK);
		EasyMock.verify(board, piece, game);
	}

	@Test
	public void makeMove_sameColorCapture_white() {
		Board board = EasyMock.createMock(Board.class);
		Player player1 = new Player("p1", PieceColor.WHITE);
		Player player2 = new Player("p2", PieceColor.BLACK);
		Piece piece = EasyMock.createMock(Piece.class);
		List<Move> moveHistory = new ArrayList<>();
		Map<String, Integer> positionHistory = new HashMap<>();
		Game game = new Game(board, GameStatus.WHITE_TURN, moveHistory, null, 0, positionHistory);
		Location from = new Location(7, 7);
		Location to = new Location(0, 7);
		EasyMock.expect(board.isInsideBoard(from)).andReturn(TRUE).anyTimes();
		EasyMock.expect(board.isInsideBoard(to)).andReturn(TRUE).anyTimes();
		board.initBoard();
		EasyMock.expectLastCall();
		EasyMock.expect(board.getPiece(from)).andReturn(piece);
		EasyMock.expect(board.getPiece(to)).andReturn(piece);
		EasyMock.expect(piece.getPieceType()).andReturn(PieceType.KNIGHT).times(2);
		EasyMock.expect(piece.getColor()).andReturn(PieceColor.WHITE).anyTimes();
		EasyMock.replay(board, piece);

		game.startNewGame(player1, player2);
		MoveResult result = game.makeMove(from, to, null);

		assertNull(game.lastMove);
		assertEquals(moveHistory, game.moveHistory);
		assertEquals(game.halfMoveClock, 0);
		assertEquals(positionHistory, game.positionHistory);
		assertEquals(MoveResult.INVALID_SAME_COLOR_CAPTURE, result);
		assertEquals(game.currentPlayer.getColor(), PieceColor.WHITE);
		EasyMock.verify(board, piece);
	}

	@Test
	public void makeMove_emptySource_white() {
		Board board = EasyMock.createMock(Board.class);
		Player player1 = new Player("p1", PieceColor.WHITE);
		Player player2 = new Player("p2", PieceColor.BLACK);
		List<Move> moveHistory = new ArrayList<>();
		Map<String, Integer> positionHistory = new HashMap<>();
		Game game = new Game(board, GameStatus.WHITE_TURN, moveHistory, null, 0, positionHistory);
		Location from = new Location(7, 7);
		Location to = new Location(0, 7);
		EasyMock.expect(board.isInsideBoard(from)).andReturn(TRUE).anyTimes();
		EasyMock.expect(board.isInsideBoard(to)).andReturn(TRUE).anyTimes();
		board.initBoard();
		EasyMock.expectLastCall();
		EasyMock.expect(board.getPiece(from)).andReturn(new Piece(PieceType.EMPTY, null));
		EasyMock.replay(board);

		game.startNewGame(player1, player2);
		MoveResult result = game.makeMove(from, to, PieceType.PAWN);

		assertNull(game.lastMove);
		assertEquals(moveHistory, game.moveHistory);
		assertEquals(game.halfMoveClock, 0);
		assertEquals(positionHistory, game.positionHistory);
		assertEquals(game.currentPlayer.getColor(), PieceColor.WHITE);
		assertEquals(MoveResult.INVALID_EMPTY_SOURCE, result);
		EasyMock.verify(board);
	}

	@Test
	public void makeMove_wrongTurn_white() {
		Board board = EasyMock.createMock(Board.class);
		Player player1 = new Player("p1", PieceColor.WHITE);
		Player player2 = new Player("p2", PieceColor.BLACK);
		Piece piece = EasyMock.createMock(Piece.class);
		List<Move> moveHistory = new ArrayList<>();
		Map<String, Integer> positionHistory = new HashMap<>();
		Game game = new Game(board, GameStatus.WHITE_TURN, moveHistory, null, 0, positionHistory);
		Location from = new Location(7, 7);
		Location to = new Location(1, 7);
		EasyMock.expect(board.isInsideBoard(from)).andReturn(TRUE).anyTimes();
		EasyMock.expect(board.isInsideBoard(to)).andReturn(TRUE).anyTimes();
		board.initBoard();
		EasyMock.expectLastCall();
		EasyMock.expect(board.getPiece(from)).andReturn(piece);
		EasyMock.expect(board.getPiece(to)).andReturn(new Piece(PieceType.EMPTY, null));
		EasyMock.expect(piece.getPieceType()).andReturn(PieceType.BISHOP);
		EasyMock.expect(piece.getColor()).andReturn(PieceColor.BLACK).anyTimes();
		EasyMock.replay(board, piece);

		game.startNewGame(player1, player2);
		MoveResult result = game.makeMove(from, to, PieceType.PAWN);

		assertNull(game.lastMove);
		assertEquals(moveHistory, game.moveHistory);
		assertEquals(game.halfMoveClock, 0);
		assertEquals(positionHistory, game.positionHistory);
		assertEquals(MoveResult.INVALID_WRONG_TURN, result);
		assertEquals(game.currentPlayer.getColor(), PieceColor.WHITE);
		EasyMock.verify(board, piece);
	}

	@Test
	public void makeMove_capture_white() {
		Board board = EasyMock.createMock(Board.class);
		Player player1 = new Player("p1", PieceColor.WHITE);
		Player player2 = new Player("p2", PieceColor.BLACK);
		Piece piece = EasyMock.createMock(Piece.class);
		Piece piece1 = EasyMock.createMock(Piece.class);

		Location wKingLoc = new Location(7, 4);
		Location bKingLoc = new Location(0, 4);
		EasyMock.expect(board.findKing(PieceColor.WHITE)).andStubReturn(wKingLoc);
		EasyMock.expect(board.findKing(PieceColor.BLACK)).andStubReturn(bKingLoc);
		EasyMock.expect(board.getPiece(wKingLoc)).andStubReturn(new Piece(PieceType.KING, PieceColor.WHITE));
		EasyMock.expect(board.getPiece(bKingLoc)).andStubReturn(new Piece(PieceType.KING, PieceColor.BLACK));

		List<Move> moveHistory = new ArrayList<>();
		Map<String, Integer> positionHistory = new HashMap<>();
		Game game = EasyMock.partialMockBuilder(Game.class).withConstructor(Board.class, GameStatus.class, List.class, Move.class, int.class, Map.class)
				.withArgs(board, GameStatus.WHITE_TURN, moveHistory, null, 99, positionHistory)
				.addMockedMethod("isInCheck", PieceColor.class).addMockedMethod("isCheckmate", PieceColor.class).addMockedMethod("isStalemate", PieceColor.class).addMockedMethod("createNotation").createMock();
		Location from = new Location(7, 7);
		Location to = new Location(0, 7);
		EasyMock.expect(game.createNotation(from, to, piece, piece1, null, false, false)).andReturn("notation");
		Move last = new Move(from, to, piece, piece1, null, false, false, "notation");
		EasyMock.expect(board.isInsideBoard(from)).andReturn(TRUE).anyTimes();
		EasyMock.expect(board.isInsideBoard(to)).andReturn(TRUE).anyTimes();
		EasyMock.expect(board.toPositionString()).andReturn("last").anyTimes();
		board.initBoard();
		EasyMock.expectLastCall();

		EasyMock.expect(board.getPiece(from)).andStubReturn(piece);
		EasyMock.expect(board.getPiece(to)).andStubReturn(piece1);

		EasyMock.expect(piece.getPieceType()).andReturn(PieceType.KNIGHT).anyTimes();
		EasyMock.expect(piece.getColor()).andReturn(PieceColor.WHITE).anyTimes();
		EasyMock.expect(piece1.getPieceType()).andReturn(PieceType.PAWN).anyTimes();
		EasyMock.expect(piece1.getColor()).andReturn(PieceColor.BLACK).anyTimes();
		EasyMock.expect(piece.canMove(board, from, to)).andReturn(true);

		board.movePiece(EasyMock.anyObject(Location.class), EasyMock.anyObject(Location.class));
		EasyMock.expectLastCall().anyTimes();
		board.setPiece(EasyMock.anyObject(Location.class), EasyMock.anyObject(Piece.class));
		EasyMock.expectLastCall().anyTimes();

		piece.setMoved(true);
		EasyMock.expectLastCall();
		EasyMock.expect(game.isInCheck(PieceColor.WHITE)).andReturn(false);
		EasyMock.expect(game.isInCheck(PieceColor.BLACK)).andReturn(false);
		EasyMock.expect(game.isCheckmate(PieceColor.BLACK)).andReturn(false);
		EasyMock.expect(game.isStalemate(PieceColor.BLACK)).andReturn(false);
		EasyMock.replay(board, piece, piece1, game);

		game.startNewGame(player1, player2);
		MoveResult result = game.makeMove(from, to, null);

		assertEquals(1, game.halfMoveClock);

		Map<String, Integer> target = new HashMap<>();
		target.put("last|WHITE|WK_Moved:false,BK_Moved:false|None", 1);
		assertEquals(target, game.positionHistory);
		moveHistory.add(last);
		assertEquals(moveHistory, game.moveHistory);
		assertEquals(last, game.lastMove);
		assertEquals(MoveResult.VALID, result);
		assertEquals(game.currentPlayer.getColor(), PieceColor.BLACK);
		EasyMock.verify(board, piece, piece1, game);
	}

	@Test
	public void makeMove_outOfBound_black() {
		Board board = EasyMock.createMock(Board.class);
		Player player1 = new Player("p1", PieceColor.WHITE);
		Player player2 = new Player("p2", PieceColor.BLACK);
		List<Move> moveHistory = new ArrayList<>();
		Map<String, Integer> positionHistory = new HashMap<>();
		Game game = EasyMock.partialMockBuilder(Game.class).withConstructor(Board.class, GameStatus.class, List.class, Move.class, int.class, Map.class)
				.withArgs(board, GameStatus.WHITE_TURN, moveHistory, null, Integer.MAX_VALUE - 1, positionHistory)
				.addMockedMethod("isInCheck", PieceColor.class).addMockedMethod("isCheckmate", PieceColor.class).addMockedMethod("isStalemate", PieceColor.class).createMock();
		Location from = new Location(0, -1);
		Location to = new Location(0, 0);
		EasyMock.expect(board.toPositionString()).andReturn("last").anyTimes();
		EasyMock.expect(board.isInsideBoard(from)).andReturn(FALSE).anyTimes();
		board.initBoard();
		EasyMock.expectLastCall();
		EasyMock.replay(board, game);

		game.startNewGame(player1, player2);
		game.switchTurn();
		MoveResult result = game.makeMove(from, to, null);

		assertEquals(game.halfMoveClock, Integer.MAX_VALUE - 1);
		assertEquals(positionHistory, game.positionHistory);
		assertEquals(moveHistory, game.moveHistory);
		assertEquals(game.currentPlayer.getColor(), PieceColor.BLACK);
		assertNull(game.lastMove);
		assertEquals(MoveResult.INVALID_OUT_OF_BOUNDS, result);
		EasyMock.verify(board, game);
	}

	@Test
	public void makeMove_outOfBound_white() {
		Board board = EasyMock.createMock(Board.class);
		Player player1 = new Player("p1", PieceColor.WHITE);
		Player player2 = new Player("p2", PieceColor.BLACK);
		List<Move> moveHistory = new ArrayList<>();
		Map<String, Integer> positionHistory = new HashMap<>();
		Game game = EasyMock.partialMockBuilder(Game.class).withConstructor(Board.class, GameStatus.class, List.class, Move.class, int.class, Map.class)
				.withArgs(board, GameStatus.WHITE_TURN, moveHistory, null, Integer.MAX_VALUE - 1, positionHistory)
				.addMockedMethod("isInCheck", PieceColor.class).addMockedMethod("isCheckmate", PieceColor.class).addMockedMethod("isStalemate", PieceColor.class).createMock();
		Location from = new Location(0, -1);
		Location to = new Location(0, 0);
		EasyMock.expect(board.toPositionString()).andReturn("last").anyTimes();
		EasyMock.expect(board.isInsideBoard(from)).andReturn(TRUE).anyTimes();
		EasyMock.expect(board.isInsideBoard(to)).andReturn(FALSE).anyTimes();
		board.initBoard();
		EasyMock.expectLastCall();
		EasyMock.replay(board, game);

		game.startNewGame(player1, player2);
		MoveResult result = game.makeMove(from, to, null);

		assertEquals(game.currentPlayer.getColor(), PieceColor.WHITE);
		assertEquals(game.halfMoveClock, Integer.MAX_VALUE - 1);
		assertEquals(positionHistory, game.positionHistory);
		assertEquals(moveHistory, game.moveHistory);
		assertNull(game.lastMove);
		assertEquals(MoveResult.INVALID_OUT_OF_BOUNDS, result);
		EasyMock.verify(board, game);
	}

	@Test
	public void makeMove_illegalMove_black() {
		Board board = EasyMock.createMock(Board.class);
		Player player1 = new Player("p1", PieceColor.WHITE);
		Player player2 = new Player("p2", PieceColor.BLACK);
		Piece piece = EasyMock.createMock(Piece.class);
		Piece piece1 = EasyMock.createMock(Piece.class);
		List<Move> moveHistory = new ArrayList<>();
		Map<String, Integer> positionHistory = new HashMap<>();
		Game game = EasyMock.partialMockBuilder(Game.class).withConstructor(Board.class, GameStatus.class, List.class, Move.class, int.class, Map.class)
				.withArgs(board, GameStatus.WHITE_TURN, moveHistory, null, Integer.MAX_VALUE - 1, positionHistory)
				.addMockedMethod("isCastleMove", Location.class, Location.class, Piece.class)
				.addMockedMethod("isEnPassantMove", Location.class, Location.class, Piece.class)
				.createMock();
		Location from = new Location(7, 1);
		Location to = new Location(1, 0);
		EasyMock.expect(board.toPositionString()).andReturn("last").anyTimes();
		EasyMock.expect(board.isInsideBoard(from)).andReturn(TRUE).anyTimes();
		EasyMock.expect(board.isInsideBoard(to)).andReturn(TRUE).anyTimes();
		EasyMock.expect(board.getPiece(from)).andReturn(piece);
		EasyMock.expect(board.getPiece(to)).andReturn(piece1);
		EasyMock.expect(game.isCastleMove(from, to, piece)).andReturn(false);
		EasyMock.expect(piece.getPieceType()).andReturn(PieceType.KNIGHT);
		EasyMock.expect(piece.getColor()).andReturn(PieceColor.BLACK).anyTimes();
		EasyMock.expect(piece1.getPieceType()).andReturn(PieceType.PAWN);
		EasyMock.expect(piece1.getColor()).andReturn(PieceColor.WHITE).anyTimes();
		EasyMock.expect(piece.canMove(board, from, to)).andReturn(false).anyTimes();
		EasyMock.expect(game.isEnPassantMove(from, to, piece)).andReturn(false).anyTimes();
		board.initBoard();
		EasyMock.expectLastCall();
		EasyMock.replay(board, piece, piece1, game);

		game.startNewGame(player1, player2);
		game.switchTurn();
		MoveResult result = game.makeMove(from, to, null);

		assertEquals(game.currentPlayer.getColor(), PieceColor.BLACK);
		assertEquals(game.halfMoveClock, Integer.MAX_VALUE - 1);
		assertEquals(positionHistory, game.positionHistory);
		assertEquals(moveHistory, game.moveHistory);
		assertNull(game.lastMove);
		assertEquals(MoveResult.INVALID_ILLEGAL_PIECE_MOVE, result);
		EasyMock.verify(board, piece, piece1, game);
	}

	@Test
	public void makeMove_threefoldRepetition_white() {
		Board board = EasyMock.createMock(Board.class);
		Player player1 = new Player("p1", PieceColor.WHITE);
		Player player2 = new Player("p2", PieceColor.BLACK);
		Piece piece = EasyMock.createMock(Piece.class);
		Piece piece1 = EasyMock.createMock(Piece.class);

		Location wKingLoc = new Location(7, 4);
		Location bKingLoc = new Location(0, 4);
		EasyMock.expect(board.findKing(PieceColor.WHITE)).andStubReturn(wKingLoc);
		EasyMock.expect(board.findKing(PieceColor.BLACK)).andStubReturn(bKingLoc);
		EasyMock.expect(board.getPiece(wKingLoc)).andStubReturn(new Piece(PieceType.KING, PieceColor.WHITE));
		EasyMock.expect(board.getPiece(bKingLoc)).andStubReturn(new Piece(PieceType.KING, PieceColor.BLACK));

		List<Move> moveHistory = new ArrayList<>();
		Map<String, Integer> positionHistory = new HashMap<>();
		positionHistory.put("last|WHITE|WK_Moved:false,BK_Moved:false|None", 2);
		Game game = EasyMock.partialMockBuilder(Game.class).withConstructor(Board.class, GameStatus.class, List.class, Move.class, int.class, Map.class)
				.withArgs(board, GameStatus.WHITE_TURN, moveHistory, null, 99, positionHistory)
				.addMockedMethod("isInCheck", PieceColor.class).addMockedMethod("createNotation").createMock();
		Location from = new Location(7, 1);
		Location to = new Location(1, 0);
		EasyMock.expect(game.createNotation(from, to, piece, new Piece(PieceType.EMPTY, null), null, false, false)).andReturn("notation");
		Move last = new Move(from, to, piece, null, null, false, false, "notation");
		EasyMock.expect(board.toPositionString()).andReturn("last").anyTimes();
		EasyMock.expect(board.isInsideBoard(from)).andReturn(TRUE).anyTimes();
		EasyMock.expect(board.isInsideBoard(to)).andReturn(TRUE).anyTimes();

		// -- APPLIED STUB RETURNS --
		EasyMock.expect(board.getPiece(from)).andStubReturn(piece);
		EasyMock.expect(board.getPiece(to)).andStubReturn(new Piece(PieceType.EMPTY, null));

		EasyMock.expect(piece.getPieceType()).andReturn(PieceType.QUEEN).anyTimes();
		EasyMock.expect(piece.getColor()).andReturn(PieceColor.WHITE).anyTimes();
		EasyMock.expect(piece1.getColor()).andReturn(PieceColor.BLACK).anyTimes();
		EasyMock.expect(piece.canMove(board, from, to)).andReturn(true).anyTimes();

		// -- APPLIED WILDCARDS --
		board.movePiece(EasyMock.anyObject(Location.class), EasyMock.anyObject(Location.class));
		EasyMock.expectLastCall().anyTimes();
		board.setPiece(EasyMock.anyObject(Location.class), EasyMock.anyObject(Piece.class));
		EasyMock.expectLastCall().anyTimes();

		piece.setMoved(true);
		EasyMock.expectLastCall();
		EasyMock.expect(game.isInCheck(PieceColor.WHITE)).andReturn(false);
		board.initBoard();
		EasyMock.expectLastCall();
		EasyMock.replay(board, piece, piece1, game);

		game.startNewGame(player1, player2);
		MoveResult result = game.makeMove(from, to, null);

		assertEquals(game.currentPlayer.getColor(), PieceColor.WHITE);
		assertEquals(game.halfMoveClock, 100);
		Map<String, Integer> target = new HashMap<>();
		target.put("last|WHITE|WK_Moved:false,BK_Moved:false|None", 3);
		assertEquals(target, game.positionHistory);
		List<Move> history = new ArrayList<>();
		history.add(last);
		assertEquals(history, game.moveHistory);
		assertEquals(game.lastMove, last);
		assertEquals(MoveResult.DRAW, result);
		EasyMock.verify(board, piece, piece1, game);
	}

	@Test
	public void makeMove_50Move_white() {
		Board board = EasyMock.createMock(Board.class);
		Player player1 = new Player("p1", PieceColor.WHITE);
		Player player2 = new Player("p2", PieceColor.BLACK);
		Piece piece = EasyMock.createMock(Piece.class);
		Location from = new Location(7, 1);
		Location to = new Location(1, 0);
		List<Move> moveHistory = new ArrayList<>();
		Move lastMove = new Move(from, to, piece, null, null, false, false, "notation");
		Map<String, Integer> positionHistory = new HashMap<>();
		positionHistory.put("lastp1", 1);
		Game game = EasyMock.partialMockBuilder(Game.class).withConstructor(Board.class, GameStatus.class, List.class, Move.class, int.class, Map.class)
				.withArgs(board, GameStatus.WHITE_TURN, moveHistory, lastMove, 100, positionHistory)
				.addMockedMethod("isInCheck", PieceColor.class).createMock();
		EasyMock.expect(board.toPositionString()).andReturn("last").anyTimes();
		EasyMock.expect(board.isInsideBoard(from)).andReturn(TRUE).anyTimes();
		EasyMock.expect(board.isInsideBoard(to)).andReturn(TRUE).anyTimes();

		// -- APPLIED STUB RETURNS --
		EasyMock.expect(board.getPiece(from)).andStubReturn(piece);
		EasyMock.expect(board.getPiece(to)).andStubReturn(new Piece(PieceType.EMPTY, null));

		EasyMock.expect(piece.getPieceType()).andReturn(PieceType.QUEEN).anyTimes();
		EasyMock.expect(piece.getColor()).andReturn(PieceColor.WHITE).anyTimes();
		EasyMock.expect(piece.canMove(board, from, to)).andReturn(true).anyTimes();

		// -- APPLIED WILDCARDS --
		board.movePiece(EasyMock.anyObject(Location.class), EasyMock.anyObject(Location.class));
		EasyMock.expectLastCall().anyTimes();
		board.setPiece(EasyMock.anyObject(Location.class), EasyMock.anyObject(Piece.class));
		EasyMock.expectLastCall().anyTimes();

		EasyMock.expect(game.isInCheck(PieceColor.WHITE)).andReturn(false);
		board.initBoard();
		EasyMock.expectLastCall();
		EasyMock.replay(board, piece, game);

		game.startNewGame(player1, player2);
		MoveResult result = game.makeMove(from, to, null);

		assertEquals(game.currentPlayer.getColor(), PieceColor.WHITE);
		assertEquals(game.halfMoveClock, 101);
		Map<String, Integer> target = new HashMap<>();
		target.put("lastp1", 1);
		assertEquals(target, game.positionHistory);
		assertEquals(new ArrayList<>(), game.moveHistory);
		assertEquals(game.lastMove, lastMove);
		assertEquals(MoveResult.DRAW, result);
		EasyMock.verify(board, piece, game);
	}

	@Test
	public void makeMove_selfCheck_white() {
		Board board = EasyMock.createMock(Board.class);
		Player player1 = new Player("p1", PieceColor.WHITE);
		Player player2 = new Player("p2", PieceColor.BLACK);
		Piece piece = EasyMock.createMock(Piece.class);
		List<Move> moveHistory = new ArrayList<>();
		Map<String, Integer> positionHistory = new HashMap<>();
		Game game = EasyMock.partialMockBuilder(Game.class).withConstructor(Board.class, GameStatus.class, List.class, Move.class, int.class, Map.class)
				.withArgs(board, GameStatus.WHITE_IN_CHECK, moveHistory, null, 100, positionHistory)
				.addMockedMethod("isInCheck", PieceColor.class).createMock();
		Location from = new Location(0, -1);
		Location to = new Location(0, 0);
		EasyMock.expect(board.toPositionString()).andReturn("last").anyTimes();
		EasyMock.expect(board.isInsideBoard(from)).andReturn(TRUE).anyTimes();
		EasyMock.expect(board.isInsideBoard(to)).andReturn(TRUE).anyTimes();

		// -- APPLIED STUB RETURNS --
		EasyMock.expect(board.getPiece(from)).andStubReturn(piece);
		EasyMock.expect(board.getPiece(to)).andStubReturn(new Piece(PieceType.EMPTY, null));

		EasyMock.expect(piece.canMove(board, from, to)).andReturn(true);
		EasyMock.expect(piece.getPieceType()).andReturn(PieceType.QUEEN).anyTimes();
		EasyMock.expect(piece.getColor()).andReturn(PieceColor.WHITE).anyTimes();

		// -- APPLIED WILDCARDS --
		board.movePiece(EasyMock.anyObject(Location.class), EasyMock.anyObject(Location.class));
		EasyMock.expectLastCall().anyTimes();
		board.setPiece(EasyMock.anyObject(Location.class), EasyMock.anyObject(Piece.class));
		EasyMock.expectLastCall().anyTimes();

		board.initBoard();
		EasyMock.expectLastCall();
		EasyMock.expect(game.isInCheck(PieceColor.WHITE)).andReturn(true);
		EasyMock.replay(board, piece, game);

		game.startNewGame(player1, player2);
		MoveResult result = game.makeMove(from, to, null);

		assertEquals(game.currentPlayer.getColor(), PieceColor.WHITE);
		assertEquals(game.halfMoveClock, 100);
		assertEquals(new HashMap<>(), game.positionHistory);
		assertEquals(moveHistory, game.moveHistory);
		assertNull(game.lastMove);
		assertEquals(MoveResult.INVALID_SELF_CHECK, result);
		EasyMock.verify(board, piece, game);
	}

	@Test
	public void makeMove_pawnPromoption_white() {
		Board board = EasyMock.createMock(Board.class);
		Player player1 = new Player("p1", PieceColor.WHITE);
		Player player2 = new Player("p2", PieceColor.BLACK);
		Piece piece = EasyMock.createMock(Piece.class);
		Piece promptPiece = EasyMock.createMock(Piece.class);

		Location wKingLoc = new Location(7, 4);
		Location bKingLoc = new Location(0, 4);
		EasyMock.expect(board.findKing(PieceColor.WHITE)).andStubReturn(wKingLoc);
		EasyMock.expect(board.findKing(PieceColor.BLACK)).andStubReturn(bKingLoc);
		EasyMock.expect(board.getPiece(wKingLoc)).andStubReturn(new Piece(PieceType.KING, PieceColor.WHITE));
		EasyMock.expect(board.getPiece(bKingLoc)).andStubReturn(new Piece(PieceType.KING, PieceColor.BLACK));

		List<Move> moveHistory = new ArrayList<>();
		Map<String, Integer> positionHistory = new HashMap<>();
		Game game = EasyMock.partialMockBuilder(Game.class).withConstructor(Board.class, GameStatus.class, List.class, Move.class, int.class, Map.class)
				.withArgs(board, GameStatus.WHITE_TURN, moveHistory, null, 0, positionHistory)
				.addMockedMethod("isInCheck", PieceColor.class).addMockedMethod("isCheckmate", PieceColor.class).addMockedMethod("isStalemate", PieceColor.class)
				.addMockedMethod("createNotation", Location.class, Location.class, Piece.class, Piece.class, PieceType.class, boolean.class, boolean.class)
				.addMockedMethod("createPromotedPiece", PieceType.class, PieceColor.class).createMock();
		Location from = new Location(7, 0);
		Location to = new Location(0, 0);
		EasyMock.expect(board.isInsideBoard(from)).andReturn(TRUE).anyTimes();
		EasyMock.expect(board.isInsideBoard(to)).andReturn(TRUE).anyTimes();
		EasyMock.expect(board.toPositionString()).andReturn("last").anyTimes();
		board.initBoard();
		EasyMock.expectLastCall();
		EasyMock.expect(game.createPromotedPiece(PieceType.BISHOP, PieceColor.WHITE)).andReturn(promptPiece);

		// -- APPLIED STUB RETURNS --
		EasyMock.expect(board.getPiece(from)).andStubReturn(piece);
		EasyMock.expect(board.getPiece(to)).andStubReturn(new Piece(PieceType.EMPTY, null));

		EasyMock.expect(piece.getPieceType()).andReturn(PieceType.PAWN).anyTimes();
		EasyMock.expect(piece.getColor()).andReturn(PieceColor.WHITE).anyTimes();
		EasyMock.expect(piece.canMove(board, from, to)).andReturn(true);

		// -- APPLIED WILDCARDS --
		board.movePiece(EasyMock.anyObject(Location.class), EasyMock.anyObject(Location.class));
		EasyMock.expectLastCall().anyTimes();
		board.setPiece(EasyMock.anyObject(Location.class), EasyMock.anyObject(Piece.class));
		EasyMock.expectLastCall().anyTimes();

		piece.setMoved(true);
		EasyMock.expectLastCall();
		promptPiece.setMoved(true);
		EasyMock.expectLastCall();
		EasyMock.expect(game.isInCheck(PieceColor.WHITE)).andReturn(false);
		EasyMock.expect(game.isCheckmate(PieceColor.BLACK)).andReturn(false);
		EasyMock.expect(game.isInCheck(PieceColor.BLACK)).andReturn(true);
		EasyMock.expect(game.createNotation(from, to, piece, new Piece(PieceType.EMPTY, null), PieceType.BISHOP, false, false)).andReturn("notation");
		EasyMock.replay(board, piece, game, promptPiece);

		game.startNewGame(player1, player2);
		MoveResult result = game.makeMove(from, to, PieceType.BISHOP);
		Move last = new Move(from, to, piece, null, PieceType.BISHOP, false, false, "notation");
		Map<String, Integer> target = new HashMap<>();
		target.put("last|WHITE|WK_Moved:false,BK_Moved:false|None", 1);

		assertEquals(target, game.positionHistory);
		assertEquals(last, game.lastMove);
		moveHistory.add(last);
		assertEquals(moveHistory, game.moveHistory);
		assertEquals(game.currentPlayer.getColor(), PieceColor.BLACK);
		assertEquals(game.halfMoveClock, 1);
		assertEquals(MoveResult.CHECK, result);
		EasyMock.verify(board, piece, game, promptPiece);
	}

	@Test
	public void makeMove_pawnPromoption_black() {
		Board board = EasyMock.createMock(Board.class);
		Player player1 = new Player("p1", PieceColor.WHITE);
		Player player2 = new Player("p2", PieceColor.BLACK);
		Piece piece = EasyMock.createMock(Piece.class);
		Piece bishop = new Piece(PieceType.BISHOP, PieceColor.BLACK);

		Location wKingLoc = new Location(7, 4);
		Location bKingLoc = new Location(0, 4);
		EasyMock.expect(board.findKing(PieceColor.WHITE)).andStubReturn(wKingLoc);
		EasyMock.expect(board.findKing(PieceColor.BLACK)).andStubReturn(bKingLoc);
		EasyMock.expect(board.getPiece(wKingLoc)).andStubReturn(new Piece(PieceType.KING, PieceColor.WHITE));
		EasyMock.expect(board.getPiece(bKingLoc)).andStubReturn(new Piece(PieceType.KING, PieceColor.BLACK));

		List<Move> moveHistory = new ArrayList<>();
		Map<String, Integer> positionHistory = new HashMap<>();
		Game game = EasyMock.partialMockBuilder(Game.class).withConstructor(Board.class, GameStatus.class, List.class, Move.class, int.class, Map.class)
				.withArgs(board, GameStatus.WHITE_TURN, moveHistory, null, 0, positionHistory)
				.addMockedMethod("isInCheck", PieceColor.class).addMockedMethod("isCheckmate", PieceColor.class).addMockedMethod("isStalemate", PieceColor.class)
				.addMockedMethod("createNotation").createMock();
		Location from = new Location(0, 1);
		Location to = new Location(7, 1);
		EasyMock.expect(board.isInsideBoard(from)).andReturn(TRUE).anyTimes();
		EasyMock.expect(board.isInsideBoard(to)).andReturn(TRUE).anyTimes();
		EasyMock.expect(board.toPositionString()).andReturn("last").anyTimes();
		piece.setMoved(true);
		EasyMock.expectLastCall();
		board.initBoard();
		EasyMock.expectLastCall();

		// -- APPLIED STUB RETURNS --
		EasyMock.expect(board.getPiece(from)).andStubReturn(piece);
		EasyMock.expect(board.getPiece(to)).andStubReturn(new Piece(PieceType.EMPTY, null));

		EasyMock.expect(piece.getPieceType()).andReturn(PieceType.PAWN).anyTimes();
		EasyMock.expect(piece.getColor()).andReturn(PieceColor.BLACK).anyTimes();
		EasyMock.expect(piece.canMove(board, from, to)).andReturn(true);

		// -- APPLIED WILDCARDS --
		board.movePiece(EasyMock.anyObject(Location.class), EasyMock.anyObject(Location.class));
		EasyMock.expectLastCall().anyTimes();
		board.setPiece(EasyMock.anyObject(Location.class), EasyMock.anyObject(Piece.class));
		EasyMock.expectLastCall().anyTimes();

		EasyMock.expect(game.isInCheck(PieceColor.BLACK)).andReturn(false);
		EasyMock.expect(game.isCheckmate(PieceColor.WHITE)).andReturn(false);
		EasyMock.expect(game.isInCheck(PieceColor.WHITE)).andReturn(true);
		EasyMock.expect(game.createNotation(from, to, piece, new Piece(PieceType.EMPTY, null), PieceType.BISHOP, false, false)).andReturn("notation");
		EasyMock.replay(board, piece, game);

		game.startNewGame(player1, player2);
		game.switchTurn();
		MoveResult result = game.makeMove(from, to, PieceType.BISHOP);
		Move last = new Move(from, to, piece, null, PieceType.BISHOP, false, false, "notation");
		Map<String, Integer> target = new HashMap<>();
		target.put("last|BLACK|WK_Moved:false,BK_Moved:false|None", 1);

		assertEquals(MoveResult.CHECK, result);
		assertEquals(target, game.positionHistory);
		assertEquals(last, game.lastMove);
		moveHistory.add(last);
		assertEquals(moveHistory, game.moveHistory);
		assertEquals(game.currentPlayer.getColor(), PieceColor.WHITE);
		assertEquals(game.halfMoveClock, 1);
		EasyMock.verify(board, piece, game);
	}

	@Test
	public void switchTurn_white() {
		Board board = EasyMock.createMock(Board.class);
		Player player1 = new Player("p1", PieceColor.WHITE);
		Player player2 = new Player("p2", PieceColor.BLACK);
		Game game = new Game(board, GameStatus.WHITE_TURN, new ArrayList<>(), null, 0, new HashMap<>());
		board.initBoard();
		EasyMock.expectLastCall();
		EasyMock.replay(board);
		game.startNewGame(player1, player2);
		game.switchTurn();
		assertEquals(game.currentPlayer.getColor(), PieceColor.BLACK);
		EasyMock.verify(board);
	}

	@Test
	public void switchTurn_black() {
		Board board = EasyMock.createMock(Board.class);
		Player player1 = new Player("p1", PieceColor.WHITE);
		Player player2 = new Player("p2", PieceColor.BLACK);
		Game game = new Game(board, GameStatus.WHITE_TURN, new ArrayList<>(), null, 0, new HashMap<>());
		board.initBoard();
		EasyMock.expectLastCall();
		EasyMock.replay(board);
		game.startNewGame(player1, player2);
		game.switchTurn();
		game.switchTurn();
		assertEquals(game.currentPlayer.getColor(), PieceColor.WHITE);
		EasyMock.verify(board);
	}

	@Test
	public void isInCheck_check() {
		Board board = EasyMock.createMock(Board.class);
		Piece attacker = EasyMock.createMock(Piece.class);
		Location kingLocation = new Location(0, 0);
		Location attackerLocation = new Location(0, 1);
		Game game = new Game(board, GameStatus.WHITE_TURN, new ArrayList<>(), null, 0, new HashMap<>());
		EasyMock.expect(board.findKing(PieceColor.BLACK)).andReturn(kingLocation);
		EasyMock.expect(board.getPiece(new Location(0, 0))).andReturn(new Piece(PieceType.EMPTY, null));
		EasyMock.expect(board.getPiece(attackerLocation)).andReturn(attacker);
		EasyMock.expect(attacker.getPieceType()).andReturn(PieceType.BISHOP);
		EasyMock.expect(attacker.getColor()).andReturn(PieceColor.WHITE);
		EasyMock.expect(attacker.canMove(board, attackerLocation, kingLocation)).andReturn(true);
		EasyMock.replay(board, attacker);
		game.switchTurn();
		boolean result = game.isInCheck(PieceColor.BLACK);
		assertTrue(result);
		assertEquals(GameStatus.BLACK_IN_CHECK, game.status);
		EasyMock.verify(board, attacker);
	}

	@Test
	public void isInCheck_check_firstIsNull() {
		Board board = EasyMock.createMock(Board.class);
		Player player1 = new Player("p1", PieceColor.WHITE);
		Player player2 = new Player("p2", PieceColor.BLACK);
		Piece piece = EasyMock.createMock(Piece.class);
		Game game = new Game(board, GameStatus.WHITE_TURN, new ArrayList<>(), null, 0, new HashMap<>());
		Location first = new Location(0, 0);
		Location from = new Location(0, 1);
		Location to = new Location(7, 7);
		EasyMock.expect(board.getPiece(from)).andReturn(piece).anyTimes();
		EasyMock.expect(board.getPiece(first)).andReturn(new Piece(PieceType.EMPTY, null)).anyTimes();
		EasyMock.expect(piece.getPieceType()).andReturn(PieceType.PAWN);
		EasyMock.expect(piece.getColor()).andReturn(PieceColor.WHITE);
		EasyMock.expect(board.findKing(PieceColor.BLACK)).andReturn(to);
		EasyMock.expect(piece.canMove(board, from, to)).andReturn(true);
		board.initBoard();
		EasyMock.expectLastCall();
		EasyMock.replay(board, piece);
		game.startNewGame(player1, player2);
		Boolean result = game.isInCheck(PieceColor.BLACK);
		assertTrue(result);
		EasyMock.verify(board, piece);
	}

	@Test
	public void isInCheck_check_false() {
		Board board = EasyMock.createMock(Board.class);
		Player player1 = new Player("p1", PieceColor.WHITE);
		Player player2 = new Player("p2", PieceColor.BLACK);
		Piece piece = EasyMock.createMock(Piece.class);
		Game game = new Game(board, GameStatus.WHITE_TURN, new ArrayList<>(), null, 0, new HashMap<>());
		Location from = new Location(7, 7);
		Location to = new Location(0, 0);
		EasyMock.expect(board.getPiece(EasyMock.anyObject(Location.class))).andReturn(piece).anyTimes();
		EasyMock.expect(piece.getPieceType()).andReturn(PieceType.BISHOP).anyTimes();
		EasyMock.expect(piece.getColor()).andReturn(PieceColor.BLACK).anyTimes();
		EasyMock.expect(board.findKing(PieceColor.WHITE)).andReturn(to);
		EasyMock.expect(piece.canMove(EasyMock.anyObject(Board.class), EasyMock.anyObject(Location.class), EasyMock.anyObject(Location.class))).andReturn(false).anyTimes();
		board.initBoard();
		EasyMock.expectLastCall();
		EasyMock.replay(board, piece);
		game.startNewGame(player1, player2);
		Boolean result = game.isInCheck(PieceColor.WHITE);
		assertFalse(result);
		EasyMock.verify(board, piece);
	}

	@Test
	public void isCheckmate_true() {
		Board board = EasyMock.createMock(Board.class);
		Player player1 = new Player("p1", PieceColor.WHITE);
		Player player2 = new Player("p2", PieceColor.BLACK);
		Piece piece = EasyMock.createMock(Piece.class);
		Game game = EasyMock.partialMockBuilder(Game.class).withConstructor(Board.class, GameStatus.class, List.class, Move.class, int.class, Map.class)
				.withArgs(board, GameStatus.WHITE_TURN, new ArrayList<>(), null, 0, new HashMap<>())
				.addMockedMethod("isInCheck", PieceColor.class).createMock();
		EasyMock.expect(game.isInCheck(PieceColor.BLACK)).andReturn(true).anyTimes();
		EasyMock.expect(board.getPiece(EasyMock.anyObject(Location.class))).andReturn(piece).anyTimes();
		EasyMock.expect(piece.getPieceType()).andReturn(PieceType.KNIGHT).anyTimes();
		EasyMock.expect(piece.getColor()).andReturn(PieceColor.BLACK).anyTimes();
		EasyMock.expect(piece.canMove(EasyMock.anyObject(Board.class), EasyMock.anyObject(Location.class), EasyMock.anyObject(Location.class))).andReturn(true).anyTimes();
		board.initBoard();
		EasyMock.expectLastCall();
		board.movePiece(EasyMock.anyObject(Location.class), EasyMock.anyObject(Location.class));
		EasyMock.expectLastCall().anyTimes();
		board.setPiece(EasyMock.anyObject(Location.class), EasyMock.anyObject(Piece.class));
		EasyMock.expectLastCall().anyTimes();
		EasyMock.replay(board, piece, game);
		game.startNewGame(player1, player2);
		Boolean result = game.isCheckmate(PieceColor.BLACK);
		assertTrue(result);
		assertEquals(game.status, GameStatus.WHITE_WIN);
		EasyMock.verify(board, piece, game);
	}

	@Test
	public void isCheckmate_true_white() {
		Board board = EasyMock.createMock(Board.class);
		Player player1 = new Player("p1", PieceColor.WHITE);
		Player player2 = new Player("p2", PieceColor.BLACK);
		Piece piece = EasyMock.createMock(Piece.class);
		Game game = EasyMock.partialMockBuilder(Game.class).withConstructor(Board.class, GameStatus.class, List.class, Move.class, int.class, Map.class)
				.withArgs(board, GameStatus.WHITE_TURN, new ArrayList<>(), null, 0, new HashMap<>())
				.addMockedMethod("isInCheck", PieceColor.class).createMock();
		EasyMock.expect(game.isInCheck(PieceColor.WHITE)).andReturn(true).anyTimes();
		EasyMock.expect(board.getPiece(EasyMock.anyObject(Location.class))).andReturn(piece).anyTimes();
		EasyMock.expect(piece.getPieceType()).andReturn(PieceType.PAWN).anyTimes();
		EasyMock.expect(piece.getColor()).andReturn(PieceColor.WHITE).anyTimes();
		EasyMock.expect(piece.canMove(EasyMock.anyObject(Board.class), EasyMock.anyObject(Location.class), EasyMock.anyObject(Location.class))).andReturn(true).anyTimes();
		board.initBoard();
		EasyMock.expectLastCall();
		board.movePiece(EasyMock.anyObject(Location.class), EasyMock.anyObject(Location.class));
		EasyMock.expectLastCall().anyTimes();
		board.setPiece(EasyMock.anyObject(Location.class), EasyMock.anyObject(Piece.class));
		EasyMock.expectLastCall().anyTimes();
		EasyMock.replay(board, piece, game);
		game.startNewGame(player1, player2);
		game.switchTurn();
		Boolean result = game.isCheckmate(PieceColor.WHITE);
		assertTrue(result);
		assertEquals(game.status, GameStatus.BLACK_WIN);
		EasyMock.verify(board, piece, game);
	}

	@Test
	public void isCheckmate_notCheck() {
		Board board = EasyMock.createMock(Board.class);
		Player player1 = new Player("p1", PieceColor.WHITE);
		Player player2 = new Player("p2", PieceColor.BLACK);
		Piece piece = EasyMock.createMock(Piece.class);
		Game game = EasyMock.partialMockBuilder(Game.class).withConstructor(Board.class, GameStatus.class, List.class, Move.class, int.class, Map.class)
				.withArgs(board, GameStatus.WHITE_TURN, new ArrayList<>(), null, 0, new HashMap<>())
				.addMockedMethod("isInCheck", PieceColor.class).createMock();
		EasyMock.expect(game.isInCheck(PieceColor.BLACK)).andReturn(false).anyTimes();
		EasyMock.expect(board.getPiece(EasyMock.anyObject(Location.class))).andReturn(piece).anyTimes();
		EasyMock.expect(piece.canMove(EasyMock.anyObject(Board.class), EasyMock.anyObject(Location.class), EasyMock.anyObject(Location.class))).andReturn(true).anyTimes();
		board.initBoard();
		EasyMock.expectLastCall();
		board.movePiece(EasyMock.anyObject(Location.class), EasyMock.anyObject(Location.class));
		EasyMock.expectLastCall().anyTimes();
		board.setPiece(EasyMock.anyObject(Location.class), EasyMock.anyObject(Piece.class));
		EasyMock.expectLastCall().anyTimes();
		EasyMock.replay(board, piece, game);
		game.startNewGame(player1, player2);
		Boolean result = game.isCheckmate(PieceColor.BLACK);
		assertFalse(result);
		EasyMock.verify(board, piece, game);
	}

	@Test
	public void isCheckmate_false() {
		Board board = EasyMock.createMock(Board.class);
		Player player1 = new Player("p1", PieceColor.WHITE);
		Player player2 = new Player("p2", PieceColor.BLACK);
		Piece piece = EasyMock.createMock(Piece.class);
		Game game = EasyMock.partialMockBuilder(Game.class).withConstructor(Board.class, GameStatus.class, List.class, Move.class, int.class, Map.class)
				.withArgs(board, GameStatus.WHITE_TURN, new ArrayList<>(), null, 0, new HashMap<>())
				.addMockedMethod("isInCheck", PieceColor.class).createMock();
		EasyMock.expect(game.isInCheck(PieceColor.WHITE)).andReturn(true).times(2);
		EasyMock.expect(game.isInCheck(PieceColor.WHITE)).andReturn(false).once();
		EasyMock.expect(board.getPiece(EasyMock.anyObject(Location.class))).andReturn(new Piece(PieceType.EMPTY, null)).times(63);
		EasyMock.expect(board.getPiece(EasyMock.anyObject(Location.class))).andReturn(piece).anyTimes();
		EasyMock.expect(piece.getPieceType()).andReturn(PieceType.QUEEN);
		EasyMock.expect(piece.getColor()).andReturn(PieceColor.WHITE).anyTimes();
		EasyMock.expect(piece.canMove(EasyMock.anyObject(Board.class), EasyMock.anyObject(Location.class), EasyMock.anyObject(Location.class))).andReturn(false).times(62);
		EasyMock.expect(piece.canMove(EasyMock.anyObject(Board.class), EasyMock.anyObject(Location.class), EasyMock.anyObject(Location.class))).andReturn(true).times(2);
		board.initBoard();
		EasyMock.expectLastCall();
		board.movePiece(EasyMock.anyObject(Location.class), EasyMock.anyObject(Location.class));
		EasyMock.expectLastCall().times(4);
		board.setPiece(EasyMock.anyObject(Location.class), EasyMock.anyObject(Piece.class));
		EasyMock.expectLastCall().times(2);
		EasyMock.replay(board, piece, game);
		game.startNewGame(player1, player2);
		Boolean result = game.isCheckmate(PieceColor.WHITE);
		assertFalse(result);
		EasyMock.verify(board, piece, game);
	}

	@Test
	public void isCheckmate_targetOutBound() {
		Board board = EasyMock.createMock(Board.class);
		Player player1 = new Player("p1", PieceColor.WHITE);
		Player player2 = new Player("p2", PieceColor.BLACK);
		Piece piece = EasyMock.createMock(Piece.class);
		Game game = EasyMock.partialMockBuilder(Game.class).withConstructor(Board.class, GameStatus.class, List.class, Move.class, int.class, Map.class)
				.withArgs(board, GameStatus.WHITE_TURN, new ArrayList<>(), null, 0, new HashMap<>())
				.addMockedMethod("isInCheck", PieceColor.class).createMock();
		EasyMock.expect(game.isInCheck(PieceColor.WHITE)).andReturn(true).once();
		EasyMock.expect(board.getPiece(EasyMock.anyObject(Location.class))).andReturn(new Piece(PieceType.EMPTY, null)).times(63);
		EasyMock.expect(board.getPiece(EasyMock.anyObject(Location.class))).andReturn(piece).anyTimes();
		EasyMock.expect(piece.getPieceType()).andReturn(PieceType.PAWN);
		EasyMock.expect(piece.getColor()).andReturn(PieceColor.WHITE).anyTimes();
		EasyMock.expect(piece.canMove(EasyMock.anyObject(Board.class), EasyMock.anyObject(Location.class), EasyMock.anyObject(Location.class))).andReturn(false).times(64);
		EasyMock.expect(piece.canMove(EasyMock.anyObject(Board.class), EasyMock.anyObject(Location.class), EasyMock.anyObject(Location.class))).andReturn(true).anyTimes();
		board.initBoard();
		EasyMock.expectLastCall();
		EasyMock.replay(board, piece, game);
		game.startNewGame(player1, player2);
		Boolean result = game.isCheckmate(PieceColor.WHITE);
		assertTrue(result);
		EasyMock.verify(board, piece, game);
	}

	@Test
	public void isCheckmate_outbound() {
		Board board = EasyMock.createMock(Board.class);
		Player player1 = new Player("p1", PieceColor.WHITE);
		Player player2 = new Player("p2", PieceColor.BLACK);
		Piece piece = EasyMock.createMock(Piece.class);
		Game game = EasyMock.partialMockBuilder(Game.class).withConstructor(Board.class, GameStatus.class, List.class, Move.class, int.class, Map.class)
				.withArgs(board, GameStatus.WHITE_TURN, new ArrayList<>(), null, 0, new HashMap<>())
				.addMockedMethod("isInCheck", PieceColor.class).createMock();
		EasyMock.expect(game.isInCheck(PieceColor.WHITE)).andReturn(true).once();
		EasyMock.expect(board.getPiece(EasyMock.anyObject(Location.class))).andReturn(new Piece(PieceType.EMPTY, null)).times(64);
		EasyMock.expect(board.getPiece(EasyMock.anyObject(Location.class))).andReturn(piece).anyTimes();
		EasyMock.expect(piece.getColor()).andReturn(PieceColor.WHITE).anyTimes();
		board.initBoard();
		EasyMock.expectLastCall();
		EasyMock.replay(board, piece, game);
		game.startNewGame(player1, player2);
		Boolean result = game.isCheckmate(PieceColor.WHITE);
		assertTrue(result);
		EasyMock.verify(board, piece, game);
	}

	@Test
	public void isStalemate_allCheck_true() {
		Board board = EasyMock.createMock(Board.class);
		Player player1 = new Player("p1", PieceColor.WHITE);
		Player player2 = new Player("p2", PieceColor.BLACK);
		Piece piece = EasyMock.createMock(Piece.class);
		Game game = EasyMock.partialMockBuilder(Game.class).withConstructor(Board.class, GameStatus.class, List.class, Move.class, int.class, Map.class)
				.withArgs(board, GameStatus.WHITE_TURN, new ArrayList<>(), null, 0, new HashMap<>())
				.addMockedMethod("isInCheck", PieceColor.class).createMock();
		EasyMock.expect(game.isInCheck(PieceColor.WHITE)).andReturn(false).andReturn(true).anyTimes();
		EasyMock.expect(board.getPiece(EasyMock.anyObject(Location.class))).andReturn(piece).anyTimes();
		EasyMock.expect(piece.getPieceType()).andReturn(PieceType.PAWN).anyTimes();
		EasyMock.expect(piece.getColor()).andReturn(PieceColor.WHITE).anyTimes();
		EasyMock.expect(piece.canMove(EasyMock.anyObject(Board.class), EasyMock.anyObject(Location.class), EasyMock.anyObject(Location.class))).andReturn(true).anyTimes();
		board.initBoard();
		EasyMock.expectLastCall();
		board.movePiece(EasyMock.anyObject(Location.class), EasyMock.anyObject(Location.class));
		EasyMock.expectLastCall().times(8192);
		board.setPiece(EasyMock.anyObject(Location.class), EasyMock.anyObject(Piece.class));
		EasyMock.expectLastCall().times(4096);
		EasyMock.replay(board, piece, game);
		game.startNewGame(player1, player2);
		Boolean result = game.isStalemate(PieceColor.WHITE);
		assertTrue(result);
		EasyMock.verify(board, piece, game);
	}

	@Test
	public void isStalemate_noMove_true() {
		Board board = EasyMock.createMock(Board.class);
		Player player1 = new Player("p1", PieceColor.WHITE);
		Player player2 = new Player("p2", PieceColor.BLACK);
		Piece piece = EasyMock.createMock(Piece.class);
		Game game = EasyMock.partialMockBuilder(Game.class).withConstructor(Board.class, GameStatus.class, List.class, Move.class, int.class, Map.class)
				.withArgs(board, GameStatus.WHITE_TURN, new ArrayList<>(), null, 0, new HashMap<>())
				.addMockedMethod("isInCheck", PieceColor.class).createMock();
		EasyMock.expect(game.isInCheck(PieceColor.WHITE)).andReturn(false);
		EasyMock.expect(board.getPiece(EasyMock.anyObject(Location.class))).andReturn(piece).anyTimes();
		EasyMock.expect(piece.getPieceType()).andReturn(PieceType.PAWN).anyTimes();
		EasyMock.expect(piece.getColor()).andReturn(PieceColor.WHITE).anyTimes();
		EasyMock.expect(piece.canMove(EasyMock.anyObject(Board.class), EasyMock.anyObject(Location.class), EasyMock.anyObject(Location.class))).andReturn(false).anyTimes();
		board.initBoard();
		EasyMock.expectLastCall();
		EasyMock.replay(board, piece, game);
		game.startNewGame(player1, player2);
		Boolean result = game.isStalemate(PieceColor.WHITE);
		assertTrue(result);
		EasyMock.verify(board, piece, game);
	}

	@Test
	public void isStalemate_false() {
		Board board = EasyMock.createMock(Board.class);
		Player player1 = new Player("p1", PieceColor.WHITE);
		Player player2 = new Player("p2", PieceColor.BLACK);
		Piece piece = EasyMock.createMock(Piece.class);
		Game game = EasyMock.partialMockBuilder(Game.class).withConstructor(Board.class, GameStatus.class, List.class, Move.class, int.class, Map.class)
				.withArgs(board, GameStatus.WHITE_TURN, new ArrayList<>(), null, 0, new HashMap<>())
				.addMockedMethod("isInCheck", PieceColor.class).createMock();
		EasyMock.expect(game.isInCheck(PieceColor.WHITE)).andReturn(false).anyTimes();
		EasyMock.expect(board.getPiece(EasyMock.anyObject(Location.class))).andReturn(piece).anyTimes();
		EasyMock.expect(piece.getPieceType()).andReturn(PieceType.PAWN);
		EasyMock.expect(piece.getColor()).andReturn(PieceColor.WHITE).anyTimes();
		EasyMock.expect(piece.canMove(EasyMock.anyObject(Board.class), EasyMock.anyObject(Location.class), EasyMock.anyObject(Location.class))).andReturn(true).anyTimes();
		board.initBoard();
		EasyMock.expectLastCall();
		board.movePiece(EasyMock.anyObject(Location.class), EasyMock.anyObject(Location.class));
		EasyMock.expectLastCall().times(2);
		board.setPiece(EasyMock.anyObject(Location.class), EasyMock.anyObject(Piece.class));
		EasyMock.expectLastCall().once();
		EasyMock.replay(board, piece, game);
		game.startNewGame(player1, player2);
		Boolean result = game.isStalemate(PieceColor.WHITE);
		assertFalse(result);
		EasyMock.verify(board, piece, game);
	}

	@Test
	public void isStalemate_false_check() {
		Board board = EasyMock.createMock(Board.class);
		Player player1 = new Player("p1", PieceColor.WHITE);
		Player player2 = new Player("p2", PieceColor.BLACK);
		Piece piece = EasyMock.createMock(Piece.class);
		Game game = EasyMock.partialMockBuilder(Game.class).withConstructor(Board.class, GameStatus.class, List.class, Move.class, int.class, Map.class)
				.withArgs(board, GameStatus.WHITE_TURN, new ArrayList<>(), null, 0, new HashMap<>())
				.addMockedMethod("isInCheck", PieceColor.class).createMock();
		EasyMock.expect(game.isInCheck(PieceColor.BLACK)).andReturn(true).anyTimes();
		EasyMock.expect(piece.getColor()).andReturn(PieceColor.WHITE).anyTimes();
		board.initBoard();
		EasyMock.expectLastCall();
		EasyMock.replay(board, piece, game);
		game.startNewGame(player1, player2);
		Boolean result = game.isStalemate(PieceColor.BLACK);
		assertFalse(result);
		EasyMock.verify(board, piece, game);
	}

	@Test
	public void resign() {
		Board board = EasyMock.createMock(Board.class);
		Game game = new Game(board, GameStatus.WHITE_TURN, new ArrayList<>(), null, 0, new HashMap<>());
		game.resign();
		assertEquals(GameStatus.RESIGNED, game.status);
	}

	@Test
	public void pawnPromption_Queen() {
		Board board = EasyMock.createMock(Board.class);
		Game game = new Game(board, GameStatus.WHITE_TURN, new ArrayList<>(), null, 0, new HashMap<>());
		Piece result = game.createPromotedPiece(PieceType.QUEEN, PieceColor.WHITE);
		Piece queen = new Piece(PieceType.QUEEN, PieceColor.WHITE);
		assertEquals(result, queen);
	}

	@Test
	public void pawnPromption_Bishop() {
		Board board = EasyMock.createMock(Board.class);
		Game game = new Game(board, GameStatus.WHITE_TURN, new ArrayList<>(), null, 0, new HashMap<>());
		Piece result = game.createPromotedPiece(PieceType.BISHOP, PieceColor.BLACK);
		Piece bishop = new Piece(PieceType.BISHOP, PieceColor.BLACK);
		assertEquals(result, bishop);
	}

	@Test
	public void pawnPromption_Knight() {
		Board board = EasyMock.createMock(Board.class);
		Game game = new Game(board, GameStatus.WHITE_TURN, new ArrayList<>(), null, 0, new HashMap<>());
		Piece result = game.createPromotedPiece(PieceType.KNIGHT, PieceColor.BLACK);
		Piece bishop = new Piece(PieceType.KNIGHT, PieceColor.BLACK);
		assertEquals(result, bishop);
	}

	@Test
	public void pawnPromption_Rook() {
		Board board = EasyMock.createMock(Board.class);
		Game game = new Game(board, GameStatus.WHITE_TURN, new ArrayList<>(), null, 0, new HashMap<>());
		Piece result = game.createPromotedPiece(PieceType.ROOK, PieceColor.WHITE);
		Piece bishop = new Piece(PieceType.ROOK, PieceColor.WHITE);
		assertEquals(result, bishop);
	}

	@Test
	public void getStatus_whiteTurn() {
		Board board = EasyMock.createMock(Board.class);
		Game game = new Game(board, GameStatus.WHITE_TURN, new ArrayList<>(), null, 0, new HashMap<>());
		assertEquals(game.getStatus(), GameStatus.WHITE_TURN);
	}

	@Test
	public void getStatus_blackTurn() {
		Board board = EasyMock.createMock(Board.class);
		Game game = new Game(board, GameStatus.BLACK_TURN, new ArrayList<>(), null, 0, new HashMap<>());
		assertEquals(game.getStatus(), GameStatus.BLACK_TURN);
	}

	@Test
	public void getStatus_whiteWin() {
		Board board = EasyMock.createMock(Board.class);
		Game game = new Game(board, GameStatus.WHITE_WIN, new ArrayList<>(), null, 0, new HashMap<>());
		assertEquals(game.getStatus(), GameStatus.WHITE_WIN);
	}

	@Test
	public void getStatus_blackWin() {
		Board board = EasyMock.createMock(Board.class);
		Game game = new Game(board, GameStatus.BLACK_WIN, new ArrayList<>(), null, 0, new HashMap<>());
		assertEquals(game.getStatus(), GameStatus.BLACK_WIN);
	}

	@Test
	public void getStatus_white_check() {
		Board board = EasyMock.createMock(Board.class);
		Game game = new Game(board, GameStatus.WHITE_IN_CHECK, new ArrayList<>(), null, 0, new HashMap<>());
		assertEquals(game.getStatus(), GameStatus.WHITE_IN_CHECK);
	}

	@Test
	public void getStatus_black_check() {
		Board board = EasyMock.createMock(Board.class);
		Game game = new Game(board, GameStatus.BLACK_IN_CHECK, new ArrayList<>(), null, 0, new HashMap<>());
		assertEquals(game.getStatus(), GameStatus.BLACK_IN_CHECK);
	}

	@Test
	public void getStatus_draw() {
		Board board = EasyMock.createMock(Board.class);
		Game game = new Game(board, GameStatus.DRAW, new ArrayList<>(), null, 0, new HashMap<>());
		assertEquals(game.getStatus(), GameStatus.DRAW);
	}

	@Test
	public void getStatus_resigned() {
		Board board = EasyMock.createMock(Board.class);
		Game game = new Game(board, GameStatus.RESIGNED, new ArrayList<>(), null, 0, new HashMap<>());
		assertEquals(game.getStatus(), GameStatus.RESIGNED);
	}

	@Test
	public void getMoveHistory_empty() {
		Board board = EasyMock.createMock(Board.class);
		Game game = new Game(board, GameStatus.WHITE_IN_CHECK, new ArrayList<>(), null, 0, new HashMap<>());
		assertEquals(game.getMoveHistory(), new ArrayList<>());
	}

	@Test
	public void getMoveHistory_nonEmpty() {
		Board board = EasyMock.createMock(Board.class);
		Piece piece = EasyMock.createMock(Piece.class);
		List<Move> moveHistory = new ArrayList<>();
		Move move = new Move(new Location(0, 0), new Location(7, 7), piece, null, null, false, false, "notation");
		moveHistory.add(move);
		List<Move> target = new ArrayList<>();
		target.add(move);
		Game game = new Game(board, GameStatus.WHITE_IN_CHECK, moveHistory, null, 0, new HashMap<>());
		assertEquals(game.getMoveHistory(), target);
	}

	@Test
	public void getLastMove() {
		Board board = EasyMock.createMock(Board.class);
		Piece piece = EasyMock.createMock(Piece.class);
		List<Move> moveHistory = new ArrayList<>();
		Move move = new Move(new Location(0, 0), new Location(7, 7), piece, null, null, false, false, "notation");
		moveHistory.add(move);
		Game game = new Game(board, GameStatus.WHITE_TURN, moveHistory, move, 0, new HashMap<>());
		assertEquals(move, game.getLastMove());
	}

	@Test
	public void timeOut_black() {
		Board board = EasyMock.createMock(Board.class);
		Player player1 = new Player("p1", PieceColor.WHITE);
		Player player2 = new Player("p2", PieceColor.BLACK);
		Game game = new Game(board, GameStatus.WHITE_TURN, new ArrayList<>(), null, 0, new HashMap<>());
		game.startNewGame(player1, player2);
		game.timeOut();
		assertEquals(GameStatus.BLACK_WIN, game.getStatus());
	}

	@Test
	public void timeOut_white() {
		Board board = EasyMock.createMock(Board.class);
		Player player1 = new Player("p1", PieceColor.WHITE);
		Player player2 = new Player("p2", PieceColor.BLACK);
		Piece piece = EasyMock.createMock(Piece.class);
		List<Move> moveHistory = new ArrayList<>();
		Move move = new Move(new Location(0, 0), new Location(7, 7), piece, null, null, false, false, "notation");
		moveHistory.add(move);
		Game game = new Game(board, GameStatus.WHITE_TURN, moveHistory, move, 0, new HashMap<>());
		game.startNewGame(player1, player2);
		game.switchTurn();
		game.timeOut();
		assertEquals(GameStatus.WHITE_WIN, game.getStatus());
	}

	@Test
	public void createNotation_castle() {
		Board board = EasyMock.createMock(Board.class);
		Piece king = EasyMock.createMock(Piece.class);
		Game game = new Game(board, GameStatus.WHITE_TURN, new ArrayList<>(), null, 0, new HashMap<>());
		EasyMock.replay(board, king);
		String notation = game.createNotation(new Location(7, 4), new Location(7, 6), king, null, null, true, false);
		assertEquals("O-O", notation);
		EasyMock.verify(board, king);
	}

	@Test
	public void createNotation_QueenCastle() {
		Board board = EasyMock.createMock(Board.class);
		Piece king = EasyMock.createMock(Piece.class);
		Game game = new Game(board, GameStatus.WHITE_TURN, new ArrayList<>(), null, 0, new HashMap<>());
		EasyMock.replay(board, king);
		String notation = game.createNotation(new Location(7, 4), new Location(7, 2), king, null, null, true, false);
		assertEquals("O-O-O", notation);
		EasyMock.verify(board, king);
	}

	@Test
	public void createNotation_normalMove() {
		Board board = EasyMock.createMock(Board.class);
		Piece pawn = EasyMock.createMock(Piece.class);
		Game game = new Game(board, GameStatus.WHITE_TURN, new ArrayList<>(), null, 0, new HashMap<>());
		EasyMock.expect(pawn.getPieceType()).andReturn(PieceType.PAWN);
		EasyMock.replay(board, pawn);
		String notation = game.createNotation(new Location(6, 4), new Location(4, 4), pawn, new Piece(PieceType.EMPTY, null), null, false, false);
		assertEquals("PAWN (6,4) -> (4,4)", notation);
		EasyMock.verify(board, pawn);
	}

	@Test
	public void createNotation_captureByEnPassant() {
		Board board = EasyMock.createMock(Board.class);
		Piece whitePawn = EasyMock.createMock(Piece.class);
		Piece blackPawn = EasyMock.createMock(Piece.class);
		Game game = new Game(board, GameStatus.WHITE_TURN, new ArrayList<>(), null, 0, new HashMap<>());
		EasyMock.expect(whitePawn.getPieceType()).andReturn(PieceType.PAWN);
		EasyMock.expect(blackPawn.getPieceType()).andReturn(PieceType.PAWN).times(2);
		EasyMock.replay(board, whitePawn, blackPawn);
		String notation = game.createNotation(new Location(3, 4), new Location(2, 5), whitePawn, blackPawn, null, false, true);
		assertEquals("PAWN (3,4) -> (2,5) captures PAWN en passant", notation);
		EasyMock.verify(board, whitePawn, blackPawn);
	}

	@Test
	public void createNotation_promotion() {
		Board board = EasyMock.createMock(Board.class);
		Piece blackPawn = EasyMock.createMock(Piece.class);
		Game game = new Game(board, GameStatus.BLACK_TURN, new ArrayList<>(), null, 0, new HashMap<>());
		EasyMock.expect(blackPawn.getPieceType()).andReturn(PieceType.PAWN);
		EasyMock.replay(board, blackPawn);
		String notation = game.createNotation(new Location(6, 4), new Location(7, 4), blackPawn, new Piece(PieceType.EMPTY, null), PieceType.BISHOP, false, false);
		assertEquals("PAWN (6,4) -> (7,4) promotes to BISHOP", notation);
		EasyMock.verify(board, blackPawn);
	}

	@Test
	public void isCastleMove_false() {
		Board board = EasyMock.createMock(Board.class);
		Piece king = EasyMock.createMock(Piece.class);
		Game game = new Game(board, GameStatus.WHITE_TURN, new ArrayList<>(), null, 0, new HashMap<>());
		EasyMock.expect(king.getPieceType()).andReturn(PieceType.KING);
		EasyMock.expect(king.hasMoved()).andReturn(false);
		EasyMock.replay(board, king);
		assertFalse(game.isCastleMove(new Location(7, 4), new Location(7, 5), king));
		EasyMock.verify(board, king);
	}

	@Test
	public void isCastleMove_differentRow_false() {
		Board board = EasyMock.createMock(Board.class);
		Piece king = EasyMock.createMock(Piece.class);
		Game game = new Game(board, GameStatus.WHITE_TURN, new ArrayList<>(), null, 0, new HashMap<>());
		EasyMock.expect(king.getPieceType()).andReturn(PieceType.KING);
		EasyMock.expect(king.hasMoved()).andReturn(false);
		EasyMock.replay(board, king);
		assertFalse(game.isCastleMove(new Location(7, 4), new Location(6, 4), king));
		EasyMock.verify(board, king);
	}

	@Test
	public void isCastleMove_blocked_false() {
		Board board = EasyMock.createMock(Board.class);
		Piece king = EasyMock.createMock(Piece.class);
		Piece rook = EasyMock.createMock(Piece.class);
		Game game = new Game(board, GameStatus.WHITE_TURN, new ArrayList<>(), null, 0, new HashMap<>());
		EasyMock.expect(king.getPieceType()).andReturn(PieceType.KING);
		EasyMock.expect(king.hasMoved()).andReturn(false);
		EasyMock.expect(board.getPiece(new Location(7, 7))).andReturn(rook);
		EasyMock.expect(rook.getPieceType()).andReturn(PieceType.ROOK);
		EasyMock.expect(rook.getColor()).andReturn(PieceColor.WHITE);
		EasyMock.expect(king.getColor()).andReturn(PieceColor.WHITE);
		EasyMock.expect(rook.hasMoved()).andReturn(false);
		EasyMock.expect(board.isEmpty(new Location(7, 5))).andReturn(false);
		EasyMock.replay(board, king, rook);
		assertFalse(game.isCastleMove(new Location(7, 4), new Location(7, 6), king));
		EasyMock.verify(board, king, rook);
	}

	@Test
	public void isCastleMove_move3Col_false() {
		Board board = EasyMock.createMock(Board.class);
		Piece king = EasyMock.createMock(Piece.class);
		Game game = new Game(board, GameStatus.WHITE_TURN, new ArrayList<>(), null, 0, new HashMap<>());
		EasyMock.expect(king.getPieceType()).andReturn(PieceType.KING);
		EasyMock.expect(king.hasMoved()).andReturn(false);
		EasyMock.replay(board, king);
		assertFalse(game.isCastleMove(new Location(7, 4), new Location(7, 7), king));
		EasyMock.verify(board, king);
	}

	@Test
	public void isCastleMove_true() {
		Board board = EasyMock.createMock(Board.class);
		Piece king = EasyMock.createMock(Piece.class);
		Piece rook = EasyMock.createMock(Piece.class);
		Game game = new Game(board, GameStatus.WHITE_TURN, new ArrayList<>(), null, 0, new HashMap<>());
		EasyMock.expect(king.getPieceType()).andReturn(PieceType.KING);
		EasyMock.expect(king.hasMoved()).andReturn(false);
		EasyMock.expect(board.getPiece(new Location(7, 7))).andReturn(rook);
		EasyMock.expect(rook.getPieceType()).andReturn(PieceType.ROOK);
		EasyMock.expect(rook.getColor()).andReturn(PieceColor.WHITE);
		EasyMock.expect(king.getColor()).andReturn(PieceColor.WHITE);
		EasyMock.expect(rook.hasMoved()).andReturn(false);
		EasyMock.expect(board.isEmpty(new Location(7, 5))).andReturn(true);
		EasyMock.expect(board.isEmpty(new Location(7, 6))).andReturn(true);
		EasyMock.replay(board, king, rook);
		assertTrue(game.isCastleMove(new Location(7, 4), new Location(7, 6), king));
		EasyMock.verify(board, king, rook);
	}

	@Test
	public void performCastle() {
		Board board = EasyMock.createMock(Board.class);
		Piece king = EasyMock.createMock(Piece.class);
		Piece rook = EasyMock.createMock(Piece.class);
		Game game = new Game(board, GameStatus.WHITE_TURN, new ArrayList<>(), null, 0, new HashMap<>());
		Location kingFrom = new Location(7, 4);
		Location kingTo = new Location(7, 6);
		Location rookFrom = new Location(7, 7);
		Location rookTo = new Location(7, 5);
		board.movePiece(kingFrom, kingTo);
		EasyMock.expectLastCall();
		board.movePiece(rookFrom, rookTo);
		EasyMock.expectLastCall();
		EasyMock.expect(board.getPiece(kingTo)).andReturn(king);
		king.setMoved(true);
		EasyMock.expectLastCall();
		EasyMock.expect(board.getPiece(rookTo)).andReturn(rook);
		rook.setMoved(true);
		EasyMock.expectLastCall();
		EasyMock.replay(board, king, rook);
		game.performCastle(kingFrom, kingTo);
		EasyMock.verify(board, king, rook);
	}

	@Test
	public void isEnPassantMove_false() {
		Board board = EasyMock.createMock(Board.class);
		Piece whitePawn = EasyMock.createMock(Piece.class);
		Piece blackPawn = EasyMock.createMock(Piece.class);
		Move lastMove = new Move(new Location(1, 5), new Location(3, 5), blackPawn, null, null, false, false, "PAWN (1,5) -> (3,5)");
		Game game = new Game(board, GameStatus.WHITE_TURN, new ArrayList<>(), lastMove, 0, new HashMap<>());
		EasyMock.expect(whitePawn.getPieceType()).andReturn(PieceType.PAWN).anyTimes();
		EasyMock.expect(blackPawn.getPieceType()).andReturn(PieceType.PAWN).anyTimes();
		EasyMock.expect(whitePawn.getColor()).andReturn(PieceColor.WHITE).anyTimes();
		EasyMock.expect(blackPawn.getColor()).andReturn(PieceColor.BLACK).anyTimes();
		EasyMock.expect(board.isEmpty(new Location(2, 4))).andReturn(true);
		EasyMock.replay(board, whitePawn, blackPawn);
		assertFalse(game.isEnPassantMove(new Location(3, 4), new Location(2, 4), whitePawn));
		EasyMock.verify(board, whitePawn, blackPawn);
	}

	@Test
	public void isEnPassantMove_cross2Col_false() {
		Board board = EasyMock.createMock(Board.class);
		Piece whitePawn = EasyMock.createMock(Piece.class);
		Piece blackPawn = EasyMock.createMock(Piece.class);
		Move lastMove = new Move(new Location(1, 5), new Location(3, 5), blackPawn, null, null, false, false, "PAWN (1,5) -> (3,5)");
		Game game = new Game(board, GameStatus.WHITE_TURN, new ArrayList<>(), lastMove, 0, new HashMap<>());
		EasyMock.expect(whitePawn.getPieceType()).andReturn(PieceType.PAWN).anyTimes();
		EasyMock.expect(blackPawn.getPieceType()).andReturn(PieceType.PAWN).anyTimes();
		EasyMock.expect(whitePawn.getColor()).andReturn(PieceColor.WHITE).anyTimes();
		EasyMock.expect(blackPawn.getColor()).andReturn(PieceColor.BLACK).anyTimes();
		EasyMock.expect(board.isEmpty(new Location(2, 6))).andReturn(true);
		EasyMock.replay(board, whitePawn, blackPawn);
		assertFalse(game.isEnPassantMove(new Location(3, 4), new Location(2, 6), whitePawn));
		EasyMock.verify(board, whitePawn, blackPawn);
	}

	@Test
	public void isEnPassantMove_previousMove1Row_false() {
		Board board = EasyMock.createMock(Board.class);
		Piece whitePawn = EasyMock.createMock(Piece.class);
		Piece blackPawn = EasyMock.createMock(Piece.class);
		Move lastMove = new Move(new Location(2, 5), new Location(3, 5), blackPawn, null, null, false, false, "PAWN (2,5) -> (3,5)");
		Game game = new Game(board, GameStatus.WHITE_TURN, new ArrayList<>(), lastMove, 0, new HashMap<>());
		EasyMock.expect(whitePawn.getPieceType()).andReturn(PieceType.PAWN);
		EasyMock.expect(blackPawn.getPieceType()).andReturn(PieceType.PAWN);
		EasyMock.expect(blackPawn.getColor()).andReturn(PieceColor.BLACK);
		EasyMock.expect(whitePawn.getColor()).andReturn(PieceColor.WHITE).times(2);
		EasyMock.expect(board.isEmpty(new Location(2, 5))).andReturn(true);
		EasyMock.replay(board, whitePawn, blackPawn);
		assertFalse(game.isEnPassantMove(new Location(3, 4), new Location(2, 5), whitePawn));
		EasyMock.verify(board, whitePawn, blackPawn);
	}

	@Test
	public void isEnPassantMove_true() {
		Board board = EasyMock.createMock(Board.class);
		Piece whitePawn = EasyMock.createMock(Piece.class);
		Piece blackPawn = EasyMock.createMock(Piece.class);
		Move lastMove = new Move(new Location(1, 5), new Location(3, 5), blackPawn, null, null, false, false, "PAWN (1,5) -> (3,5)");
		Game game = new Game(board, GameStatus.WHITE_TURN, new ArrayList<>(), lastMove, 0, new HashMap<>());
		EasyMock.expect(whitePawn.getPieceType()).andReturn(PieceType.PAWN);
		EasyMock.expect(blackPawn.getPieceType()).andReturn(PieceType.PAWN);
		EasyMock.expect(blackPawn.getColor()).andReturn(PieceColor.BLACK);
		EasyMock.expect(whitePawn.getColor()).andReturn(PieceColor.WHITE).times(2);
		EasyMock.expect(board.isEmpty(new Location(2, 5))).andReturn(true);
		EasyMock.replay(board, whitePawn, blackPawn);
		assertTrue(game.isEnPassantMove(new Location(3, 4), new Location(2, 5), whitePawn));
		EasyMock.verify(board, whitePawn, blackPawn);
	}

	@Test
	public void makeMove_enPassant() {
		Player player1 = new Player("p1", PieceColor.WHITE);
		Player player2 = new Player("p2", PieceColor.BLACK);
		Location source = new Location(0, 0);
		Location destination = new Location(7, 7);
		Board board = EasyMock.createMock(Board.class);
		Piece rook = EasyMock.createMock(Piece.class);
		Piece lastPiece = EasyMock.createMock(Piece.class);

		board.initBoard();
		EasyMock.expectLastCall().anyTimes();

		// -- WILDCARDS FOR TIME TRAVEL --
		board.movePiece(EasyMock.anyObject(Location.class), EasyMock.anyObject(Location.class));
		EasyMock.expectLastCall().anyTimes();
		board.setPiece(EasyMock.anyObject(Location.class), EasyMock.anyObject(Piece.class));
		EasyMock.expectLastCall().anyTimes();

		EasyMock.expect(board.isInsideBoard(source)).andReturn(TRUE).anyTimes();
		EasyMock.expect(board.isInsideBoard(destination)).andReturn(TRUE).anyTimes();
		EasyMock.expect(board.toPositionString()).andReturn("last").anyTimes();

		EasyMock.expect(rook.canMove(board, source, destination)).andStubReturn(TRUE);
		EasyMock.expect(rook.getPieceType()).andStubReturn(PieceType.ROOK);
		EasyMock.expect(rook.getColor()).andStubReturn(PieceColor.WHITE);

		Location wKingLoc = new Location(7, 4);
		Location bKingLoc = new Location(0, 4);
		EasyMock.expect(board.findKing(PieceColor.WHITE)).andStubReturn(wKingLoc);
		EasyMock.expect(board.findKing(PieceColor.BLACK)).andStubReturn(bKingLoc);

		EasyMock.expect(board.getPiece(wKingLoc)).andStubReturn(new Piece(PieceType.KING, PieceColor.WHITE));
		EasyMock.expect(board.getPiece(bKingLoc)).andStubReturn(new Piece(PieceType.KING, PieceColor.BLACK));
		EasyMock.expect(board.getPiece(destination)).andStubReturn(new Piece(PieceType.EMPTY, null));
		EasyMock.expect(board.getPiece(source)).andStubReturn(rook);

		List<Move> moveHistory = new ArrayList<>();
		Location lastMoveto = new Location(7, 4);
		Move lastMove = new Move(new Location(1, 5), lastMoveto, lastPiece, null, null, false, false, "string");

		Game game = EasyMock.partialMockBuilder(Game.class).withConstructor(Board.class, GameStatus.class, List.class, Move.class, int.class, Map.class)
				.withArgs(board, GameStatus.WHITE_TURN, moveHistory, lastMove, 0, new HashMap<>())
				.addMockedMethod("isInCheck", PieceColor.class)
				.addMockedMethod("isCheckmate", PieceColor.class)
				.addMockedMethod("isStalemate", PieceColor.class)
				.addMockedMethod("isEnPassantMove", Location.class, Location.class, Piece.class)
				.addMockedMethod("createNotation").createMock();

		EasyMock.expect(game.isInCheck(EasyMock.anyObject(PieceColor.class))).andReturn(false).anyTimes();
		EasyMock.expect(game.isCheckmate(EasyMock.anyObject(PieceColor.class))).andReturn(false).anyTimes();
		EasyMock.expect(game.isStalemate(EasyMock.anyObject(PieceColor.class))).andReturn(false).anyTimes();

		// -- BULLETPROOFED NOTATION MATCHING --
		EasyMock.expect(game.createNotation(
				EasyMock.anyObject(), EasyMock.anyObject(), EasyMock.anyObject(),
				EasyMock.anyObject(), EasyMock.anyObject(), EasyMock.anyBoolean(), EasyMock.anyBoolean()
		)).andReturn("string").anyTimes();

		// -- BULLETPROOFED EN PASSANT MATCHING --
		EasyMock.expect(game.isEnPassantMove(EasyMock.anyObject(), EasyMock.anyObject(), EasyMock.anyObject())).andReturn(true).anyTimes();

		EasyMock.expect(board.getPiece(lastMoveto)).andStubReturn(lastPiece);

		rook.setMoved(true);
		EasyMock.expectLastCall().anyTimes();

		EasyMock.replay(game, rook, board);

		game.startNewGame(player1, player2);
		MoveResult result = game.makeMove(source, destination, PieceType.KNIGHT);

		Move expectedMove = new Move(source, destination, rook, lastPiece, PieceType.KNIGHT, false, true, "string");

		Map<String, Integer> target = new HashMap<>();
		target.put("last|WHITE|WK_Moved:false,BK_Moved:false|None", 1);

		assertEquals(GameStatus.BLACK_TURN, game.getStatus());
		assertEquals(target, game.positionHistory);

		moveHistory.add(expectedMove);
		assertEquals(moveHistory, game.moveHistory);
		assertEquals(1, game.halfMoveClock);
		assertEquals(PieceColor.BLACK, game.currentPlayer.getColor());
		assertEquals(1, game.enPassant);
		assertEquals(MoveResult.VALID, result);

		EasyMock.verify(board, rook, game);
	}

	@Test
	public void makeMove_castleMove() {
		Board board = EasyMock.createMock(Board.class);
		Player player1 = new Player("p1", PieceColor.WHITE);
		Player player2 = new Player("p2", PieceColor.BLACK);
		Piece king = EasyMock.createMock(Piece.class);

		Location wKingLoc = new Location(7, 4);
		Location bKingLoc = new Location(0, 4);
		Location from = new Location(7, 4);
		Location to = new Location(7, 6);

		EasyMock.expect(board.findKing(PieceColor.WHITE)).andStubReturn(wKingLoc);
		EasyMock.expect(board.findKing(PieceColor.BLACK)).andStubReturn(bKingLoc);

		EasyMock.expect(board.getPiece(wKingLoc)).andStubReturn(king);
		EasyMock.expect(board.getPiece(bKingLoc)).andStubReturn(new Piece(PieceType.KING, PieceColor.BLACK));

		List<Move> moveHistory = new ArrayList<>();
		Map<String, Integer> positionHistory = new HashMap<>();

		Game game = EasyMock.partialMockBuilder(Game.class).withConstructor(Board.class, GameStatus.class, List.class, Move.class, int.class, Map.class)
				.withArgs(board, GameStatus.WHITE_TURN, moveHistory, null, 0, positionHistory)
				.addMockedMethod("isCastleMove", Location.class, Location.class, Piece.class)
				.addMockedMethod("performCastle", Location.class, Location.class)
				.addMockedMethod("isInCheck", PieceColor.class)
				.addMockedMethod("isCheckmate", PieceColor.class)
				.addMockedMethod("isStalemate", PieceColor.class)
				.addMockedMethod("createNotation").createMock();

		board.initBoard();
		EasyMock.expectLastCall().anyTimes();

		EasyMock.expect(king.canMove(board, from, to)).andReturn(false).anyTimes();
		EasyMock.expect(board.isInsideBoard(from)).andReturn(true).anyTimes();
		EasyMock.expect(board.isInsideBoard(to)).andReturn(true).anyTimes();

		Piece emptyTarget = new Piece(PieceType.EMPTY, null);
		EasyMock.expect(board.getPiece(to)).andStubReturn(emptyTarget);

		board.movePiece(EasyMock.anyObject(Location.class), EasyMock.anyObject(Location.class));
		EasyMock.expectLastCall().anyTimes();
		board.setPiece(EasyMock.anyObject(Location.class), EasyMock.anyObject(Piece.class));
		EasyMock.expectLastCall().anyTimes();

		EasyMock.expect(king.getPieceType()).andReturn(PieceType.KING).anyTimes();
		EasyMock.expect(king.getColor()).andReturn(PieceColor.WHITE).anyTimes();

		// -- THE FIX: Give the mock permission to answer the hasMoved() question --
		EasyMock.expect(king.hasMoved()).andStubReturn(false);

		EasyMock.expect(game.isCastleMove(EasyMock.anyObject(), EasyMock.anyObject(), EasyMock.anyObject())).andReturn(true).anyTimes();

		king.setMoved(true);
		EasyMock.expectLastCall().anyTimes();

		game.performCastle(EasyMock.anyObject(), EasyMock.anyObject());
		EasyMock.expectLastCall().anyTimes();

		EasyMock.expect(game.isInCheck(EasyMock.anyObject(PieceColor.class))).andReturn(false).anyTimes();
		EasyMock.expect(game.isCheckmate(EasyMock.anyObject(PieceColor.class))).andReturn(false).anyTimes();
		EasyMock.expect(game.isStalemate(EasyMock.anyObject(PieceColor.class))).andReturn(false).anyTimes();

		EasyMock.expect(game.createNotation(
				EasyMock.anyObject(), EasyMock.anyObject(), EasyMock.anyObject(),
				EasyMock.anyObject(), EasyMock.anyObject(), EasyMock.anyBoolean(), EasyMock.anyBoolean()
		)).andReturn("O-O").anyTimes();

		EasyMock.expect(board.toPositionString()).andReturn("last").anyTimes();

		EasyMock.replay(board, king, game);

		game.startNewGame(player1, player2);
		MoveResult result = game.makeMove(from, to, null);

		Move expectedMove = new Move(from, to, king, emptyTarget, null, true, false, "O-O");
		Map<String, Integer> expectedPositionHistory = new HashMap<>();
		expectedPositionHistory.put("last|WHITE|WK_Moved:false,BK_Moved:false|None", 1);
		List<Move> expectedMoveHistory = new ArrayList<>();
		expectedMoveHistory.add(expectedMove);

		assertEquals(MoveResult.VALID, result);
		assertEquals(GameStatus.BLACK_TURN, game.getStatus());
		assertEquals(PieceColor.BLACK, game.currentPlayer.getColor());

		assertEquals(expectedMove, game.lastMove);
		assertEquals(expectedMoveHistory, game.moveHistory);
		assertEquals(expectedPositionHistory, game.positionHistory);

		EasyMock.verify(board, king, game);
	}

	@Test
	public void makeMove_validMove_blackTurn() {
		Player player1 = new Player("p1", PieceColor.WHITE);
		Player player2 = new Player("p2", PieceColor.BLACK);
		Location source = new Location(7, 0);
		Location destination = new Location(0, 7);
		Board board = EasyMock.createMock(Board.class);
		Piece rook = EasyMock.createMock(Piece.class);
		board.initBoard();
		EasyMock.expectLastCall();

		// -- APPLIED WILDCARDS HERE --
		board.movePiece(EasyMock.anyObject(Location.class), EasyMock.anyObject(Location.class));
		EasyMock.expectLastCall().anyTimes();
		board.setPiece(EasyMock.anyObject(Location.class), EasyMock.anyObject(Piece.class));
		EasyMock.expectLastCall().anyTimes();

		EasyMock.expect(board.isInsideBoard(source)).andReturn(TRUE).anyTimes();
		EasyMock.expect(board.isInsideBoard(destination)).andReturn(TRUE).anyTimes();
		EasyMock.expect(board.toPositionString()).andReturn("last").anyTimes();
		EasyMock.expect(rook.canMove(board, source, destination)).andStubReturn(TRUE);
		EasyMock.expect(rook.getPieceType()).andStubReturn(PieceType.ROOK);
		EasyMock.expect(rook.getColor()).andStubReturn(PieceColor.BLACK);

		Location wKingLoc = new Location(7, 4);
		Location bKingLoc = new Location(0, 4);
		EasyMock.expect(board.findKing(PieceColor.WHITE)).andStubReturn(wKingLoc);
		EasyMock.expect(board.findKing(PieceColor.BLACK)).andStubReturn(bKingLoc);

		// -- APPLIED STUB RETURNS HERE --
		EasyMock.expect(board.getPiece(wKingLoc)).andStubReturn(new Piece(PieceType.KING, PieceColor.WHITE));
		EasyMock.expect(board.getPiece(bKingLoc)).andStubReturn(new Piece(PieceType.KING, PieceColor.BLACK));
		EasyMock.expect(board.getPiece(destination)).andStubReturn(new Piece(PieceType.EMPTY, null));
		EasyMock.expect(board.getPiece(source)).andStubReturn(rook);

		List<Move> moveHistory = new ArrayList<>();
		Map<String, Integer> positionHistory = new HashMap<>();
		Game game = EasyMock.partialMockBuilder(Game.class).withConstructor(Board.class, GameStatus.class, List.class, Move.class, int.class, Map.class)
				.withArgs(board, GameStatus.WHITE_TURN, moveHistory, null, 0, positionHistory)
				.addMockedMethod("isInCheck", PieceColor.class).addMockedMethod("isCheckmate", PieceColor.class).addMockedMethod("isStalemate", PieceColor.class).addMockedMethod("createNotation").createMock();
		EasyMock.expect(game.isInCheck(PieceColor.BLACK)).andReturn(false).anyTimes();
		EasyMock.expect(game.isInCheck(PieceColor.WHITE)).andReturn(false).anyTimes();
		EasyMock.expect(game.isCheckmate(PieceColor.WHITE)).andReturn(false);
		EasyMock.expect(game.isStalemate(PieceColor.WHITE)).andReturn(false);
		EasyMock.expect(game.createNotation(source, destination, rook, new Piece(PieceType.EMPTY, null), PieceType.KNIGHT, false, false)).andReturn("notation");
		rook.setMoved(true);
		EasyMock.expectLastCall();
		EasyMock.replay(board, game, rook);

		game.startNewGame(player1, player2);
		game.switchTurn();
		MoveResult result = game.makeMove(source, destination, PieceType.KNIGHT);
		Map<String, Integer> target = new HashMap<>();
		target.put("last|BLACK|WK_Moved:false,BK_Moved:false|None", 1);

		assertEquals(GameStatus.WHITE_TURN, game.getStatus());
		assertEquals(target, game.positionHistory);
		assertEquals(game.halfMoveClock, 1);
		assertEquals(game.currentPlayer.getColor(), PieceColor.WHITE);
		assertEquals(result, MoveResult.VALID);
		EasyMock.verify(board, rook, game);
	}

	@Test
	public void isInCheck_check_whiteInCheck() {
		Board board = EasyMock.createMock(Board.class);
		Piece attacker = EasyMock.createMock(Piece.class);
		Location kingLocation = new Location(0, 0);
		Location attackerLocation = new Location(0, 1);
		Game game = new Game(board, GameStatus.WHITE_TURN, new ArrayList<>(), null, 0, new HashMap<>());
		EasyMock.expect(board.findKing(PieceColor.WHITE)).andReturn(kingLocation);
		EasyMock.expect(board.getPiece(new Location(0, 0))).andReturn(new Piece(PieceType.EMPTY, null));
		EasyMock.expect(board.getPiece(attackerLocation)).andReturn(attacker);
		EasyMock.expect(attacker.getPieceType()).andReturn(PieceType.BISHOP);
		EasyMock.expect(attacker.getColor()).andReturn(PieceColor.BLACK);
		EasyMock.expect(attacker.canMove(board, attackerLocation, kingLocation)).andReturn(true);
		EasyMock.replay(board, attacker);
		boolean result = game.isInCheck(PieceColor.WHITE);
		assertTrue(result);
		assertEquals(GameStatus.WHITE_IN_CHECK, game.status);
		EasyMock.verify(board, attacker);
	}

	@Test
	public void isEnPassantMove_movingPieceNull() {
		Board board = new Board();
		board.clearBoard();
		Game game = new Game(board, GameStatus.WHITE_TURN, new ArrayList<>(), null, 0, new HashMap<>());
		game.switchTurn();
		Boolean result = game.isEnPassantMove(new Location(0, 0), new Location(7, 7), new Piece(PieceType.EMPTY, null));
		assertFalse(result);
	}

	@Test
	public void isEnPassantMove_lastMoveNotPawn() {
		Board board = EasyMock.createMock(Board.class);
		Piece whitePawn = EasyMock.createMock(Piece.class);
		Piece blackKnight = EasyMock.createMock(Piece.class);
		Move lastMove = new Move(new Location(1, 5), new Location(3, 5), blackKnight, null, null, false, false, "last move");
		Game game = new Game(board, GameStatus.WHITE_TURN, new ArrayList<>(), lastMove, 0, new HashMap<>());
		EasyMock.expect(whitePawn.getPieceType()).andReturn(PieceType.PAWN);
		EasyMock.expect(blackKnight.getPieceType()).andReturn(PieceType.KNIGHT);
		EasyMock.replay(board, whitePawn, blackKnight);
		assertFalse(game.isEnPassantMove(new Location(3, 4), new Location(2, 5), whitePawn));
		EasyMock.verify(board, whitePawn, blackKnight);
	}

	@Test
	public void isEnPassantMove_sameColor() {
		Board board = EasyMock.createMock(Board.class);
		Piece whitePawn = EasyMock.createMock(Piece.class);
		Piece lastWhitePawn = EasyMock.createMock(Piece.class);
		Move lastMove = new Move(new Location(1, 5), new Location(3, 5), lastWhitePawn, null, null, false, false, "last move");
		Game game = new Game(board, GameStatus.WHITE_TURN, new ArrayList<>(), lastMove, 0, new HashMap<>());
		EasyMock.expect(whitePawn.getPieceType()).andReturn(PieceType.PAWN);
		EasyMock.expect(lastWhitePawn.getPieceType()).andReturn(PieceType.PAWN);
		EasyMock.expect(lastWhitePawn.getColor()).andReturn(PieceColor.WHITE);
		EasyMock.expect(whitePawn.getColor()).andReturn(PieceColor.WHITE);
		EasyMock.replay(board, whitePawn, lastWhitePawn);
		assertFalse(game.isEnPassantMove(new Location(3, 4), new Location(2, 5), whitePawn));
		EasyMock.verify(board, whitePawn, lastWhitePawn);
	}

	@Test
	public void isEnPassantMove_block() {
		Board board = EasyMock.createMock(Board.class);
		Piece whitePawn = EasyMock.createMock(Piece.class);
		Piece blackPawn = EasyMock.createMock(Piece.class);
		Move lastMove = new Move(new Location(1, 5), new Location(3, 5), blackPawn, null, null, false, false, "last move");
		Game game = new Game(board, GameStatus.WHITE_TURN, new ArrayList<>(), lastMove, 0, new HashMap<>());
		EasyMock.expect(whitePawn.getPieceType()).andReturn(PieceType.PAWN);
		EasyMock.expect(blackPawn.getPieceType()).andReturn(PieceType.PAWN);
		EasyMock.expect(blackPawn.getColor()).andReturn(PieceColor.BLACK);
		EasyMock.expect(whitePawn.getColor()).andReturn(PieceColor.WHITE);
		EasyMock.expect(board.isEmpty(new Location(2, 5))).andReturn(false);
		EasyMock.replay(board, whitePawn, blackPawn);
		assertFalse(game.isEnPassantMove(new Location(3, 4), new Location(2, 5), whitePawn));
		EasyMock.verify(board, whitePawn, blackPawn);
	}

	@Test
	public void isEnPassantMove_differentRow() {
		Board board = EasyMock.createMock(Board.class);
		Piece whitePawn = EasyMock.createMock(Piece.class);
		Piece blackPawn = EasyMock.createMock(Piece.class);
		Move lastMove = new Move(new Location(0, 5), new Location(2, 5), blackPawn, null, null, false, false, "last move");
		Game game = new Game(board, GameStatus.WHITE_TURN, new ArrayList<>(), lastMove, 0, new HashMap<>());
		EasyMock.expect(whitePawn.getPieceType()).andReturn(PieceType.PAWN);
		EasyMock.expect(blackPawn.getPieceType()).andReturn(PieceType.PAWN);
		EasyMock.expect(blackPawn.getColor()).andReturn(PieceColor.BLACK);
		EasyMock.expect(whitePawn.getColor()).andReturn(PieceColor.WHITE).times(2);
		EasyMock.expect(board.isEmpty(new Location(2, 5))).andReturn(true);
		EasyMock.replay(board, whitePawn, blackPawn);
		assertFalse(game.isEnPassantMove(new Location(3, 4), new Location(2, 5), whitePawn));
		EasyMock.verify(board, whitePawn, blackPawn);
	}

	@Test
	public void isEnPassantMove_black() {
		Board board = EasyMock.createMock(Board.class);
		Piece blackPawn = EasyMock.createMock(Piece.class);
		Piece whitePawn = EasyMock.createMock(Piece.class);
		Move lastMove = new Move(new Location(6, 2), new Location(4, 2), whitePawn, null, null, false, false, "last move");
		Game game = new Game(board, GameStatus.WHITE_TURN, new ArrayList<>(), lastMove, 0, new HashMap<>());
		EasyMock.expect(blackPawn.getPieceType()).andReturn(PieceType.PAWN);
		EasyMock.expect(whitePawn.getPieceType()).andReturn(PieceType.PAWN);
		EasyMock.expect(whitePawn.getColor()).andReturn(PieceColor.WHITE);
		EasyMock.expect(blackPawn.getColor()).andReturn(PieceColor.BLACK).times(2);
		EasyMock.expect(board.isEmpty(new Location(5, 2))).andReturn(true);
		EasyMock.replay(board, blackPawn, whitePawn);
		game.switchTurn();
		assertTrue(game.isEnPassantMove(new Location(4, 3), new Location(5, 2), blackPawn));
		EasyMock.verify(board, blackPawn, whitePawn);
	}

	@Test
	public void isEnPassantMove_wrongCol() {
		Board board = EasyMock.createMock(Board.class);
		Piece whitePawn = EasyMock.createMock(Piece.class);
		Piece blackPawn = EasyMock.createMock(Piece.class);
		Move lastMove = new Move(new Location(1, 6), new Location(3, 6), blackPawn, null, null, false, false, "last move");
		Game game = new Game(board, GameStatus.WHITE_TURN, new ArrayList<>(), lastMove, 0, new HashMap<>());
		EasyMock.expect(whitePawn.getPieceType()).andReturn(PieceType.PAWN);
		EasyMock.expect(blackPawn.getPieceType()).andReturn(PieceType.PAWN);
		EasyMock.expect(blackPawn.getColor()).andReturn(PieceColor.BLACK);
		EasyMock.expect(whitePawn.getColor()).andReturn(PieceColor.WHITE).times(2);
		EasyMock.expect(board.isEmpty(new Location(2, 5))).andReturn(true);
		EasyMock.replay(board, whitePawn, blackPawn);
		assertFalse(game.isEnPassantMove(new Location(3, 4), new Location(2, 5), whitePawn));
		EasyMock.verify(board, whitePawn, blackPawn);
	}

	@Test
	public void isEnPassantMove_wrongDirection() {
		Board board = EasyMock.createMock(Board.class);
		Piece whitePawn = EasyMock.createMock(Piece.class);
		Piece blackPawn = EasyMock.createMock(Piece.class);
		Move lastMove = new Move(new Location(1, 6), new Location(3, 6), blackPawn, null, null, false, false, "last move");
		Game game = new Game(board, GameStatus.WHITE_TURN, new ArrayList<>(), lastMove, 0, new HashMap<>());
		EasyMock.expect(whitePawn.getPieceType()).andReturn(PieceType.PAWN);
		EasyMock.expect(blackPawn.getPieceType()).andReturn(PieceType.PAWN);
		EasyMock.expect(blackPawn.getColor()).andReturn(PieceColor.BLACK);
		EasyMock.expect(whitePawn.getColor()).andReturn(PieceColor.WHITE).times(2);
		EasyMock.expect(board.isEmpty(new Location(3, 5))).andReturn(true);
		EasyMock.replay(board, whitePawn, blackPawn);
		assertFalse(game.isEnPassantMove(new Location(2, 4), new Location(3, 5), whitePawn));
		EasyMock.verify(board, whitePawn, blackPawn);
	}

	@Test
	public void isCastleMove_QueenSide() {
		Board board = EasyMock.createMock(Board.class);
		Piece king = EasyMock.createMock(Piece.class);
		Piece rook = EasyMock.createMock(Piece.class);
		Game game = new Game(board, GameStatus.WHITE_TURN, new ArrayList<>(), null, 0, new HashMap<>());
		EasyMock.expect(king.getPieceType()).andReturn(PieceType.KING);
		EasyMock.expect(king.hasMoved()).andReturn(false);
		EasyMock.expect(board.getPiece(new Location(7, 0))).andReturn(rook);
		EasyMock.expect(rook.getPieceType()).andReturn(PieceType.ROOK);
		EasyMock.expect(rook.getColor()).andReturn(PieceColor.WHITE);
		EasyMock.expect(king.getColor()).andReturn(PieceColor.WHITE);
		EasyMock.expect(rook.hasMoved()).andReturn(false);
		EasyMock.expect(board.isEmpty(new Location(7, 1))).andReturn(true);
		EasyMock.expect(board.isEmpty(new Location(7, 2))).andReturn(true);
		EasyMock.expect(board.isEmpty(new Location(7, 3))).andReturn(true);
		EasyMock.replay(board, king, rook);
		assertTrue(game.isCastleMove(new Location(7, 4), new Location(7, 2), king));
		EasyMock.verify(board, king, rook);
	}

	@Test
	public void isCastleMove_kingMoved() {
		Board board = EasyMock.createMock(Board.class);
		Piece king = EasyMock.createMock(Piece.class);
		Game game = new Game(board, GameStatus.WHITE_TURN, new ArrayList<>(), null, 0, new HashMap<>());
		EasyMock.expect(king.getPieceType()).andReturn(PieceType.KING);
		EasyMock.expect(king.hasMoved()).andReturn(true);
		EasyMock.replay(board, king);
		assertFalse(game.isCastleMove(new Location(7, 4), new Location(7, 2), king));
		EasyMock.verify(board, king);
	}

	@Test
	public void isCastleMove_notRook() {
		Board board = EasyMock.createMock(Board.class);
		Piece king = EasyMock.createMock(Piece.class);
		Piece notRook = EasyMock.createMock(Piece.class);
		Game game = new Game(board, GameStatus.WHITE_TURN, new ArrayList<>(), null, 0, new HashMap<>());
		EasyMock.expect(king.getPieceType()).andReturn(PieceType.KING);
		EasyMock.expect(king.hasMoved()).andReturn(false);
		EasyMock.expect(board.getPiece(new Location(7, 7))).andReturn(notRook);
		EasyMock.expect(notRook.getPieceType()).andReturn(PieceType.KNIGHT);
		EasyMock.replay(board, king, notRook);
		assertFalse(game.isCastleMove(new Location(7, 4), new Location(7, 6), king));
		EasyMock.verify(board, king, notRook);
	}

	@Test
	public void isCastleMove_rookMoved() {
		Board board = EasyMock.createMock(Board.class);
		Piece king = EasyMock.createMock(Piece.class);
		Piece rook = EasyMock.createMock(Piece.class);
		Game game = new Game(board, GameStatus.WHITE_TURN, new ArrayList<>(), null, 0, new HashMap<>());
		EasyMock.expect(king.getPieceType()).andReturn(PieceType.KING);
		EasyMock.expect(king.hasMoved()).andReturn(false);
		EasyMock.expect(board.getPiece(new Location(7, 7))).andReturn(rook);
		EasyMock.expect(rook.getPieceType()).andReturn(PieceType.ROOK);
		EasyMock.expect(rook.getColor()).andReturn(PieceColor.WHITE);
		EasyMock.expect(king.getColor()).andReturn(PieceColor.WHITE);
		EasyMock.expect(rook.hasMoved()).andReturn(true);
		EasyMock.replay(board, king, rook);
		assertFalse(game.isCastleMove(new Location(7, 4), new Location(7, 6), king));
		EasyMock.verify(board, king, rook);
	}

	@Test
	public void isCastleMove_differentColorKingRook() {
		Board board = EasyMock.createMock(Board.class);
		Piece king = EasyMock.createMock(Piece.class);
		Piece rook = EasyMock.createMock(Piece.class);
		Game game = new Game(board, GameStatus.WHITE_TURN, new ArrayList<>(), null, 0, new HashMap<>());
		EasyMock.expect(king.getPieceType()).andReturn(PieceType.KING);
		EasyMock.expect(king.hasMoved()).andReturn(false);
		EasyMock.expect(board.getPiece(new Location(7, 7))).andReturn(rook);
		EasyMock.expect(rook.getPieceType()).andReturn(PieceType.ROOK);
		EasyMock.expect(rook.getColor()).andReturn(PieceColor.BLACK);
		EasyMock.expect(king.getColor()).andReturn(PieceColor.WHITE);
		EasyMock.replay(board, king, rook);
		assertFalse(game.isCastleMove(new Location(7, 4), new Location(7, 6), king));
		EasyMock.verify(board, king, rook);
	}

	@Test
	public void performCastle_mutant() {
		Board board = EasyMock.createMock(Board.class);
		Piece king = EasyMock.createMock(Piece.class);
		Piece rook = EasyMock.createMock(Piece.class);
		Game game = new Game(board, GameStatus.WHITE_TURN, new ArrayList<>(), null, 0, new HashMap<>());
		Location kingFrom = new Location(7, 4);
		Location kingTo = new Location(7, 4);
		Location rookFrom = new Location(7, 0);
		Location rookTo = new Location(7, 3);
		board.movePiece(kingFrom, kingTo);
		EasyMock.expectLastCall();
		board.movePiece(rookFrom, rookTo);
		EasyMock.expectLastCall();
		EasyMock.expect(board.getPiece(kingTo)).andReturn(king);
		king.setMoved(true);
		EasyMock.expectLastCall();
		EasyMock.expect(board.getPiece(rookTo)).andReturn(rook);
		rook.setMoved(true);
		EasyMock.expectLastCall();
		EasyMock.replay(board, king, rook);
		game.performCastle(kingFrom, kingTo);
		EasyMock.verify(board, king, rook);
	}

	@Test
	public void makeMove_blackPawn_noPromotion() {
		Board board = EasyMock.createMock(Board.class);
		Player player1 = new Player("p1", PieceColor.WHITE);
		Player player2 = new Player("p2", PieceColor.BLACK);
		Piece blackPawn = EasyMock.createMock(Piece.class);
		Location from = new Location(1, 0);
		Location to = new Location(2, 0);

		Location wKingLoc = new Location(7, 4);
		Location bKingLoc = new Location(0, 4);
		EasyMock.expect(board.findKing(PieceColor.WHITE)).andStubReturn(wKingLoc);
		EasyMock.expect(board.findKing(PieceColor.BLACK)).andStubReturn(bKingLoc);
		EasyMock.expect(board.getPiece(wKingLoc)).andStubReturn(new Piece(PieceType.KING, PieceColor.WHITE));
		EasyMock.expect(board.getPiece(bKingLoc)).andStubReturn(new Piece(PieceType.KING, PieceColor.BLACK));

		Game game = EasyMock.partialMockBuilder(Game.class).withConstructor(Board.class, GameStatus.class, List.class, Move.class, int.class, Map.class)
				.withArgs(board, GameStatus.BLACK_TURN, new ArrayList<>(), null, 0, new HashMap<>())
				.addMockedMethod("isInCheck", PieceColor.class).addMockedMethod("isCheckmate", PieceColor.class).addMockedMethod("isStalemate", PieceColor.class).createMock();

		board.initBoard();
		EasyMock.expectLastCall().anyTimes();
		EasyMock.expect(board.isInsideBoard(from)).andReturn(true).anyTimes();
		EasyMock.expect(board.isInsideBoard(to)).andReturn(true).anyTimes();

		// -- APPLIED STUB RETURNS --
		EasyMock.expect(board.getPiece(from)).andStubReturn(blackPawn);
		EasyMock.expect(board.getPiece(to)).andStubReturn(new Piece(PieceType.EMPTY, null));

		EasyMock.expect(blackPawn.getPieceType()).andReturn(PieceType.PAWN).anyTimes();
		EasyMock.expect(blackPawn.getColor()).andReturn(PieceColor.BLACK).anyTimes();
		EasyMock.expect(blackPawn.canMove(board, from, to)).andReturn(true).anyTimes();

		// -- ADDED ANYTIMES FOR SIMULATION CHECK --
		EasyMock.expect(game.isInCheck(PieceColor.BLACK)).andReturn(false).anyTimes();
		EasyMock.expect(game.isInCheck(PieceColor.WHITE)).andReturn(false).anyTimes();
		EasyMock.expect(game.isCheckmate(PieceColor.WHITE)).andReturn(false).anyTimes();
		EasyMock.expect(game.isStalemate(PieceColor.WHITE)).andReturn(false).anyTimes();

		// -- APPLIED WILDCARDS --
		board.movePiece(EasyMock.anyObject(Location.class), EasyMock.anyObject(Location.class));
		EasyMock.expectLastCall().anyTimes();
		board.setPiece(EasyMock.anyObject(Location.class), EasyMock.anyObject(Piece.class));
		EasyMock.expectLastCall().anyTimes();

		blackPawn.setMoved(true);
		EasyMock.expectLastCall().anyTimes();
		EasyMock.expect(board.toPositionString()).andReturn("last").anyTimes();
		EasyMock.replay(board, blackPawn, game);

		game.startNewGame(player1, player2);
		game.switchTurn();
		MoveResult result = game.makeMove(from, to, PieceType.QUEEN);

		assertEquals(MoveResult.VALID, result);
		assertEquals(GameStatus.WHITE_TURN, game.getStatus());
		assertEquals(1, game.halfMoveClock);
		EasyMock.verify(board, blackPawn, game);
	}

	@Test
	public void makeMove_whitePawn_noPromotion() {
		Board board = EasyMock.createMock(Board.class);
		Player player1 = new Player("p1", PieceColor.WHITE);
		Player player2 = new Player("p2", PieceColor.BLACK);
		Piece whitePawn = EasyMock.createMock(Piece.class);
		Location from = new Location(6, 0);
		Location to = new Location(5, 0);

		Location wKingLoc = new Location(7, 4);
		Location bKingLoc = new Location(0, 4);
		EasyMock.expect(board.findKing(PieceColor.WHITE)).andStubReturn(wKingLoc);
		EasyMock.expect(board.findKing(PieceColor.BLACK)).andStubReturn(bKingLoc);
		EasyMock.expect(board.getPiece(wKingLoc)).andStubReturn(new Piece(PieceType.KING, PieceColor.WHITE));
		EasyMock.expect(board.getPiece(bKingLoc)).andStubReturn(new Piece(PieceType.KING, PieceColor.BLACK));

		Game game = EasyMock.partialMockBuilder(Game.class).withConstructor(Board.class, GameStatus.class, List.class, Move.class, int.class, Map.class)
				.withArgs(board, GameStatus.WHITE_TURN, new ArrayList<>(), null, 0, new HashMap<>())
				.addMockedMethod("isInCheck", PieceColor.class).addMockedMethod("isCheckmate", PieceColor.class).addMockedMethod("isStalemate", PieceColor.class).createMock();

		board.initBoard();
		EasyMock.expectLastCall().anyTimes();
		EasyMock.expect(board.isInsideBoard(from)).andReturn(true).anyTimes();
		EasyMock.expect(board.isInsideBoard(to)).andReturn(true).anyTimes();

		// -- APPLIED STUB RETURNS --
		EasyMock.expect(board.getPiece(from)).andStubReturn(whitePawn);
		EasyMock.expect(board.getPiece(to)).andStubReturn(new Piece(PieceType.EMPTY, null));

		EasyMock.expect(whitePawn.getPieceType()).andReturn(PieceType.PAWN).anyTimes();
		EasyMock.expect(whitePawn.getColor()).andReturn(PieceColor.WHITE).anyTimes();
		EasyMock.expect(whitePawn.canMove(board, from, to)).andReturn(true).anyTimes();

		// -- ADDED ANYTIMES FOR SIMULATION CHECK --
		EasyMock.expect(game.isInCheck(PieceColor.WHITE)).andReturn(false).anyTimes();
		EasyMock.expect(game.isInCheck(PieceColor.BLACK)).andReturn(false).anyTimes();
		EasyMock.expect(game.isCheckmate(PieceColor.BLACK)).andReturn(false).anyTimes();
		EasyMock.expect(game.isStalemate(PieceColor.BLACK)).andReturn(false).anyTimes();

		// -- APPLIED WILDCARDS --
		board.movePiece(EasyMock.anyObject(Location.class), EasyMock.anyObject(Location.class));
		EasyMock.expectLastCall().anyTimes();
		board.setPiece(EasyMock.anyObject(Location.class), EasyMock.anyObject(Piece.class));
		EasyMock.expectLastCall().anyTimes();

		whitePawn.setMoved(true);
		EasyMock.expectLastCall().anyTimes();
		EasyMock.expect(board.toPositionString()).andReturn("last").anyTimes();
		EasyMock.replay(board, whitePawn, game);

		game.startNewGame(player1, player2);
		MoveResult result = game.makeMove(from, to, PieceType.QUEEN);

		assertEquals(MoveResult.VALID, result);
		assertEquals(GameStatus.BLACK_TURN, game.getStatus());
		assertEquals(1, game.halfMoveClock);
		EasyMock.verify(board, whitePawn, game);
	}

	@Test
	public void isInCheck_check_firstSelfColor() {
		Board board = EasyMock.createMock(Board.class);
		Player player1 = new Player("p1", PieceColor.WHITE);
		Player player2 = new Player("p2", PieceColor.BLACK);
		Piece piece = EasyMock.createMock(Piece.class);
		Piece block = EasyMock.createMock(Piece.class);
		Game game = new Game(board, GameStatus.WHITE_TURN, new ArrayList<>(), null, 0, new HashMap<>());
		Location first = new Location(0, 0);
		Location from = new Location(0, 1);
		Location to = new Location(7, 7);
		EasyMock.expect(board.getPiece(from)).andReturn(piece).anyTimes();
		EasyMock.expect(board.getPiece(first)).andReturn(block).anyTimes();
		EasyMock.expect(block.getPieceType()).andReturn(PieceType.PAWN);
		EasyMock.expect(block.getColor()).andReturn(PieceColor.BLACK);
		EasyMock.expect(piece.getPieceType()).andReturn(PieceType.PAWN);
		EasyMock.expect(piece.getColor()).andReturn(PieceColor.WHITE);
		EasyMock.expect(board.findKing(PieceColor.BLACK)).andReturn(to);
		EasyMock.expect(piece.canMove(board, from, to)).andReturn(true);
		board.initBoard();
		EasyMock.expectLastCall();
		EasyMock.replay(board, piece, block);

		game.startNewGame(player1, player2);
		Boolean result = game.isInCheck(PieceColor.BLACK);
		assertTrue(result);
		EasyMock.verify(board, piece, block);
	}

	@Test
	public void isCheckmate_true_white_opponentColor() {
		Board board = EasyMock.createMock(Board.class);
		Player player1 = new Player("p1", PieceColor.WHITE);
		Player player2 = new Player("p2", PieceColor.BLACK);
		Piece piece = EasyMock.createMock(Piece.class);
		Piece block = EasyMock.createMock(Piece.class);
		Game game = EasyMock.partialMockBuilder(Game.class).withConstructor(Board.class, GameStatus.class, List.class, Move.class, int.class, Map.class)
				.withArgs(board, GameStatus.WHITE_TURN, new ArrayList<>(), null, 0, new HashMap<>())
				.addMockedMethod("isInCheck", PieceColor.class).createMock();
		Location first = new Location(0, 1);
		EasyMock.expect(game.isInCheck(PieceColor.WHITE)).andReturn(true).anyTimes();
		EasyMock.expect(board.getPiece(first)).andReturn(block).anyTimes();
		EasyMock.expect(board.getPiece(EasyMock.anyObject(Location.class))).andReturn(piece).anyTimes();
		EasyMock.expect(piece.getPieceType()).andReturn(PieceType.PAWN).anyTimes();
		EasyMock.expect(piece.getColor()).andReturn(PieceColor.WHITE).anyTimes();
		EasyMock.expect(block.getPieceType()).andReturn(PieceType.PAWN).anyTimes();
		EasyMock.expect(block.getColor()).andReturn(PieceColor.BLACK).anyTimes();
		EasyMock.expect(piece.canMove(EasyMock.anyObject(Board.class), EasyMock.anyObject(Location.class), EasyMock.anyObject(Location.class))).andReturn(true).anyTimes();
		board.initBoard();
		EasyMock.expectLastCall();
		board.movePiece(EasyMock.anyObject(Location.class), EasyMock.anyObject(Location.class));
		EasyMock.expectLastCall().anyTimes();
		board.setPiece(EasyMock.anyObject(Location.class), EasyMock.anyObject(Piece.class));
		EasyMock.expectLastCall().anyTimes();
		EasyMock.replay(board, piece, game, block);

		game.startNewGame(player1, player2);
		game.switchTurn();
		Boolean result = game.isCheckmate(PieceColor.WHITE);
		assertTrue(result);
		assertEquals(game.status, GameStatus.BLACK_WIN);
		EasyMock.verify(board, piece, game, block);
	}

	@Test
	public void isStalemate_allCheck_true_withEmptyCell() {
		Board board = EasyMock.createMock(Board.class);
		Player player1 = new Player("p1", PieceColor.WHITE);
		Player player2 = new Player("p2", PieceColor.BLACK);
		Piece piece = EasyMock.createMock(Piece.class);
		Game game = EasyMock.partialMockBuilder(Game.class).withConstructor(Board.class, GameStatus.class, List.class, Move.class, int.class, Map.class)
				.withArgs(board, GameStatus.WHITE_TURN, new ArrayList<>(), null, 0, new HashMap<>())
				.addMockedMethod("isInCheck", PieceColor.class).createMock();
		EasyMock.expect(game.isInCheck(PieceColor.WHITE)).andReturn(false).andReturn(true).anyTimes();
		EasyMock.expect(board.getPiece(EasyMock.anyObject(Location.class))).andReturn(new Piece(PieceType.EMPTY, null));
		EasyMock.expect(board.getPiece(EasyMock.anyObject(Location.class))).andReturn(piece).anyTimes();
		EasyMock.expect(piece.getPieceType()).andReturn(PieceType.PAWN).anyTimes();
		EasyMock.expect(piece.getColor()).andReturn(PieceColor.WHITE).anyTimes();
		EasyMock.expect(piece.canMove(EasyMock.anyObject(Board.class), EasyMock.anyObject(Location.class), EasyMock.anyObject(Location.class))).andReturn(true).anyTimes();
		board.initBoard();
		EasyMock.expectLastCall();
		board.movePiece(EasyMock.anyObject(Location.class), EasyMock.anyObject(Location.class));
		EasyMock.expectLastCall().times(8064);
		board.setPiece(EasyMock.anyObject(Location.class), EasyMock.anyObject(Piece.class));
		EasyMock.expectLastCall().times(4032);
		EasyMock.replay(board, piece, game);

		game.startNewGame(player1, player2);
		Boolean result = game.isStalemate(PieceColor.WHITE);
		assertTrue(result);
		EasyMock.verify(board, piece, game);
	}

	@Test
	public void isStalemate_allCheck_true_withOpponentColor() {
		Board board = EasyMock.createMock(Board.class);
		Player player1 = new Player("p1", PieceColor.WHITE);
		Player player2 = new Player("p2", PieceColor.BLACK);
		Piece piece = EasyMock.createMock(Piece.class);
		Piece block = EasyMock.createMock(Piece.class);
		Game game = EasyMock.partialMockBuilder(Game.class).withConstructor(Board.class, GameStatus.class, List.class, Move.class, int.class, Map.class)
				.withArgs(board, GameStatus.WHITE_TURN, new ArrayList<>(), null, 0, new HashMap<>())
				.addMockedMethod("isInCheck", PieceColor.class).createMock();
		EasyMock.expect(game.isInCheck(PieceColor.WHITE)).andReturn(false).andReturn(true).anyTimes();
		EasyMock.expect(board.getPiece(EasyMock.anyObject(Location.class))).andReturn(block);
		EasyMock.expect(board.getPiece(EasyMock.anyObject(Location.class))).andReturn(piece).anyTimes();
		EasyMock.expect(piece.getPieceType()).andReturn(PieceType.PAWN).anyTimes();
		EasyMock.expect(piece.getColor()).andReturn(PieceColor.WHITE).anyTimes();
		EasyMock.expect(block.getPieceType()).andReturn(PieceType.PAWN).anyTimes();
		EasyMock.expect(block.getColor()).andReturn(PieceColor.BLACK).anyTimes();
		EasyMock.expect(piece.canMove(EasyMock.anyObject(Board.class), EasyMock.anyObject(Location.class), EasyMock.anyObject(Location.class))).andReturn(true).anyTimes();
		board.initBoard();
		EasyMock.expectLastCall();
		board.movePiece(EasyMock.anyObject(Location.class), EasyMock.anyObject(Location.class));
		EasyMock.expectLastCall().times(8064);
		board.setPiece(EasyMock.anyObject(Location.class), EasyMock.anyObject(Piece.class));
		EasyMock.expectLastCall().times(4032);
		EasyMock.replay(board, piece, game, block);

		game.startNewGame(player1, player2);
		Boolean result = game.isStalemate(PieceColor.WHITE);
		assertTrue(result);
		EasyMock.verify(board, piece, game, block);
	}

	@Test
	public void isInCheck_mutant() {
		Board board = EasyMock.createMock(Board.class);
		Location kingLocation = new Location(7, 4);
		Game game = new Game(board, GameStatus.WHITE_TURN, new ArrayList<>(), null, 0, new HashMap<>());
		Piece emptyPiece = new Piece(PieceType.EMPTY, null);
		EasyMock.expect(board.findKing(PieceColor.WHITE)).andReturn(kingLocation);
		EasyMock.expect(board.getPiece(EasyMock.anyObject(Location.class))).andReturn(emptyPiece).times(64);
		EasyMock.replay(board);
		boolean result = game.isInCheck(PieceColor.WHITE);
		assertFalse(result);
		assertEquals(GameStatus.WHITE_TURN, game.status);
		EasyMock.verify(board);
	}

	@Test
	public void makeMove_nullKingsForCoverage() {
		Player player1 = new Player("p1", PieceColor.WHITE);
		Player player2 = new Player("p2", PieceColor.BLACK);
		Location source = new Location(7, 0);
		Location destination = new Location(0, 7);
		Board board = EasyMock.createMock(Board.class);
		Piece rook = EasyMock.createMock(Piece.class);

		board.initBoard();
		EasyMock.expectLastCall().anyTimes();

		// -- APPLIED WILDCARDS HERE --
		board.movePiece(EasyMock.anyObject(Location.class), EasyMock.anyObject(Location.class));
		EasyMock.expectLastCall().anyTimes();
		board.setPiece(EasyMock.anyObject(Location.class), EasyMock.anyObject(Piece.class));
		EasyMock.expectLastCall().anyTimes();

		EasyMock.expect(board.getPiece(destination)).andStubReturn(new Piece(PieceType.EMPTY, null));
		EasyMock.expect(board.getPiece(source)).andStubReturn(rook);
		EasyMock.expect(board.isInsideBoard(source)).andReturn(TRUE).anyTimes();
		EasyMock.expect(board.isInsideBoard(destination)).andReturn(TRUE).anyTimes();
		EasyMock.expect(board.toPositionString()).andReturn("last").anyTimes();
		EasyMock.expect(rook.canMove(board, source, destination)).andStubReturn(TRUE);
		EasyMock.expect(rook.getPieceType()).andStubReturn(PieceType.ROOK);
		EasyMock.expect(rook.getColor()).andStubReturn(PieceColor.WHITE);

		Location wKingLoc = new Location(7, 4);
		Location bKingLoc = new Location(0, 4);
		EasyMock.expect(board.findKing(PieceColor.WHITE)).andStubReturn(wKingLoc);
		EasyMock.expect(board.findKing(PieceColor.BLACK)).andStubReturn(bKingLoc);

		// THIS IS THE FIX FOR THE YELLOW LINES
		// Returning null forces the '!= null' checks on lines 145/146 to fail safely
		EasyMock.expect(board.getPiece(wKingLoc)).andStubReturn(null);
		EasyMock.expect(board.getPiece(bKingLoc)).andStubReturn(null);

		Map<String, Integer> positionHistory = new HashMap<>();
		List<Move> moveHistory = new ArrayList<>();
		Game game = EasyMock.partialMockBuilder(Game.class)
				.withConstructor(Board.class, GameStatus.class, List.class, Move.class, int.class, Map.class)
				.withArgs(board, GameStatus.WHITE_TURN, moveHistory, null, 0, positionHistory)
				.addMockedMethod("isInCheck", PieceColor.class)
				.addMockedMethod("isCheckmate", PieceColor.class)
				.addMockedMethod("isStalemate", PieceColor.class)
				.createMock();

		// -- ADDED ANYTIMES FOR SIMULATION CHECK --
		EasyMock.expect(game.isInCheck(PieceColor.WHITE)).andReturn(false).anyTimes();
		EasyMock.expect(game.isInCheck(PieceColor.BLACK)).andReturn(false).anyTimes();
		EasyMock.expect(game.isCheckmate(PieceColor.BLACK)).andReturn(false).anyTimes();
		EasyMock.expect(game.isStalemate(PieceColor.BLACK)).andReturn(false).anyTimes();

		rook.setMoved(true);
		EasyMock.expectLastCall().anyTimes();
		EasyMock.replay(board, rook, game);

		game.startNewGame(player1, player2);

		MoveResult result = game.makeMove(source, destination, PieceType.KNIGHT);

		Map<String, Integer> target = new HashMap<>();
		// Because the kings were null, the string defaults to false for both, exactly as intended
		target.put("last|WHITE|WK_Moved:false,BK_Moved:false|None", 1);

		assertEquals(GameStatus.BLACK_TURN, game.getStatus());
		assertEquals(target, game.positionHistory);
		assertEquals(result, MoveResult.VALID);

		EasyMock.verify(board, rook, game);
	}

	@Test
	public void makeMove_blackKingMovedForCoverage() {
		Player player1 = new Player("p1", PieceColor.WHITE);
		Player player2 = new Player("p2", PieceColor.BLACK);
		Location source = new Location(7, 0);
		Location destination = new Location(0, 7);
		Board board = EasyMock.createMock(Board.class);
		Piece rook = EasyMock.createMock(Piece.class);

		board.initBoard();
		EasyMock.expectLastCall().anyTimes();

		// -- APPLIED WILDCARDS HERE --
		board.movePiece(EasyMock.anyObject(Location.class), EasyMock.anyObject(Location.class));
		EasyMock.expectLastCall().anyTimes();
		board.setPiece(EasyMock.anyObject(Location.class), EasyMock.anyObject(Piece.class));
		EasyMock.expectLastCall().anyTimes();

		EasyMock.expect(board.getPiece(destination)).andStubReturn(new Piece(PieceType.EMPTY, null));
		EasyMock.expect(board.getPiece(source)).andStubReturn(rook);
		EasyMock.expect(board.isInsideBoard(source)).andReturn(TRUE).anyTimes();
		EasyMock.expect(board.isInsideBoard(destination)).andReturn(TRUE).anyTimes();
		EasyMock.expect(board.toPositionString()).andReturn("last").anyTimes();
		EasyMock.expect(rook.canMove(board, source, destination)).andStubReturn(TRUE);
		EasyMock.expect(rook.getPieceType()).andStubReturn(PieceType.ROOK);
		EasyMock.expect(rook.getColor()).andStubReturn(PieceColor.WHITE);

		Location wKingLoc = new Location(7, 4);
		Location bKingLoc = new Location(0, 4);
		EasyMock.expect(board.findKing(PieceColor.WHITE)).andStubReturn(wKingLoc);
		EasyMock.expect(board.findKing(PieceColor.BLACK)).andStubReturn(bKingLoc);

		// --- THIS IS THE FIX FOR THE FINAL BRANCH ---
		// Create a Black King and explicitly mark it as having moved.
		Piece movedBlackKing = new Piece(PieceType.KING, PieceColor.BLACK);
		movedBlackKing.setMoved(true);

		EasyMock.expect(board.getPiece(wKingLoc)).andStubReturn(new Piece(PieceType.KING, PieceColor.WHITE));
		EasyMock.expect(board.getPiece(bKingLoc)).andStubReturn(movedBlackKing);

		Map<String, Integer> positionHistory = new HashMap<>();
		List<Move> moveHistory = new ArrayList<>();
		Game game = EasyMock.partialMockBuilder(Game.class)
				.withConstructor(Board.class, GameStatus.class, List.class, Move.class, int.class, Map.class)
				.withArgs(board, GameStatus.WHITE_TURN, moveHistory, null, 0, positionHistory)
				.addMockedMethod("isInCheck", PieceColor.class)
				.addMockedMethod("isCheckmate", PieceColor.class)
				.addMockedMethod("isStalemate", PieceColor.class)
				.createMock();

		// -- ADDED ANYTIMES FOR SIMULATION CHECK --
		EasyMock.expect(game.isInCheck(PieceColor.WHITE)).andReturn(false).anyTimes();
		EasyMock.expect(game.isInCheck(PieceColor.BLACK)).andReturn(false).anyTimes();
		EasyMock.expect(game.isCheckmate(PieceColor.BLACK)).andReturn(false).anyTimes();
		EasyMock.expect(game.isStalemate(PieceColor.BLACK)).andReturn(false).anyTimes();

		rook.setMoved(true);
		EasyMock.expectLastCall().anyTimes();
		EasyMock.replay(board, rook, game);

		game.startNewGame(player1, player2);

		MoveResult result = game.makeMove(source, destination, PieceType.KNIGHT);

		Map<String, Integer> target = new HashMap<>();
		// Notice the key now expects "BK_Moved:true" because our mocked king had moved!
		target.put("last|WHITE|WK_Moved:false,BK_Moved:true|None", 1);

		assertEquals(GameStatus.BLACK_TURN, game.getStatus());
		assertEquals(target, game.positionHistory);
		assertEquals(result, MoveResult.VALID);

		EasyMock.verify(board, rook, game);
	}

	@Test
	public void testGetBoard_returnsCorrectInstance() {
		Board mockBoard = EasyMock.createMock(Board.class);
		Game game = new Game(
				mockBoard,
				GameStatus.WHITE_TURN,
				new ArrayList<>(),
				null,
				0,
				new HashMap<>()
		);
		EasyMock.replay(mockBoard);
		Board actualBoard = game.getBoard();


		assertNotNull(actualBoard);
		assertSame(mockBoard, actualBoard);
	}

	@Test
	public void makeMove_restoresCapturedPieceAfterSelfCheckProbe() {
		Player player1 = new Player("p1", PieceColor.WHITE);
		Player player2 = new Player("p2", PieceColor.BLACK);
		Location source = new Location(7, 0);
		Location destination = new Location(0, 7);
		Board board = EasyMock.createMock(Board.class);
		Piece rook = EasyMock.createMock(Piece.class);
		Piece capturedTarget = EasyMock.createMock(Piece.class);

		board.initBoard();
		EasyMock.expectLastCall().anyTimes();

		board.movePiece(EasyMock.anyObject(Location.class), EasyMock.anyObject(Location.class));
		EasyMock.expectLastCall().anyTimes();

		// Capture-and-verify: setPiece(to, targetPiece) must be called to restore the captured piece
		board.setPiece(destination, capturedTarget);
		EasyMock.expectLastCall().times(1);
		// Allow other setPiece calls (e.g. promotion path) generically
		board.setPiece(EasyMock.anyObject(Location.class), EasyMock.anyObject(Piece.class));
		EasyMock.expectLastCall().anyTimes();

		EasyMock.expect(board.getPiece(destination)).andStubReturn(capturedTarget);
		EasyMock.expect(board.getPiece(source)).andStubReturn(rook);
		EasyMock.expect(board.isInsideBoard(source)).andReturn(TRUE).anyTimes();
		EasyMock.expect(board.isInsideBoard(destination)).andReturn(TRUE).anyTimes();
		EasyMock.expect(board.toPositionString()).andReturn("last").anyTimes();
		EasyMock.expect(rook.canMove(board, source, destination)).andStubReturn(TRUE);
		EasyMock.expect(rook.getPieceType()).andStubReturn(PieceType.ROOK);
		EasyMock.expect(rook.getColor()).andStubReturn(PieceColor.WHITE);

		EasyMock.expect(capturedTarget.getPieceType()).andStubReturn(PieceType.BISHOP);
		EasyMock.expect(capturedTarget.getColor()).andStubReturn(PieceColor.BLACK);

		Location wKingLoc = new Location(7, 4);
		Location bKingLoc = new Location(0, 4);
		EasyMock.expect(board.findKing(PieceColor.WHITE)).andStubReturn(wKingLoc);
		EasyMock.expect(board.findKing(PieceColor.BLACK)).andStubReturn(bKingLoc);
		EasyMock.expect(board.getPiece(wKingLoc)).andStubReturn(new Piece(PieceType.KING, PieceColor.WHITE));
		EasyMock.expect(board.getPiece(bKingLoc)).andStubReturn(new Piece(PieceType.KING, PieceColor.BLACK));

		Map<String, Integer> positionHistory = new HashMap<>();
		List<Move> moveHistory = new ArrayList<>();
		Game game = EasyMock.partialMockBuilder(Game.class)
				.withConstructor(Board.class, GameStatus.class, List.class, Move.class, int.class, Map.class)
				.withArgs(board, GameStatus.WHITE_TURN, moveHistory, null, 0, positionHistory)
				.addMockedMethod("isInCheck", PieceColor.class)
				.addMockedMethod("isCheckmate", PieceColor.class)
				.addMockedMethod("isStalemate", PieceColor.class)
				.createMock();

		EasyMock.expect(game.isInCheck(PieceColor.WHITE)).andReturn(false).anyTimes();
		EasyMock.expect(game.isInCheck(PieceColor.BLACK)).andReturn(false).anyTimes();
		EasyMock.expect(game.isCheckmate(PieceColor.BLACK)).andReturn(false).anyTimes();
		EasyMock.expect(game.isStalemate(PieceColor.BLACK)).andReturn(false).anyTimes();

		rook.setMoved(true);
		EasyMock.expectLastCall().anyTimes();

		EasyMock.replay(board, rook, capturedTarget, game);

		game.startNewGame(player1, player2);
		MoveResult result = game.makeMove(source, destination, PieceType.KNIGHT);

		assertEquals(MoveResult.VALID, result);

		EasyMock.verify(board, rook, capturedTarget, game);
	}

	@Test
	public void makeMove_blackPawnDoublePushComputesCorrectRowDiff() throws Exception {
		Player player1 = new Player("p1", PieceColor.WHITE);
		Player player2 = new Player("p2", PieceColor.BLACK);
		Board board = EasyMock.createMock(Board.class);
		Piece pawn = EasyMock.createMock(Piece.class);

		Location from = new Location(1, 0);
		Location to = new Location(3, 0);
		Location wKingLoc = new Location(7, 4);
		Location bKingLoc = new Location(0, 4);

		List<Move> moveHistory = new ArrayList<>();
		Map<String, Integer> positionHistory = new HashMap<>();

		Game game = EasyMock.partialMockBuilder(Game.class)
				.withConstructor(Board.class, GameStatus.class, List.class, Move.class, int.class, Map.class)
				.withArgs(board, GameStatus.BLACK_TURN, moveHistory, null, 0, positionHistory)
				.addMockedMethod("isInCheck", PieceColor.class)
				.addMockedMethod("isCheckmate", PieceColor.class)
				.addMockedMethod("isStalemate", PieceColor.class)
				.addMockedMethod("createNotation")
				.createMock();

		board.initBoard();
		EasyMock.expectLastCall();

		EasyMock.expect(board.isInsideBoard(from)).andStubReturn(true);
		EasyMock.expect(board.isInsideBoard(to)).andStubReturn(true);

		EasyMock.expect(board.getPiece(from)).andStubReturn(pawn);
		EasyMock.expect(board.getPiece(to)).andStubReturn(new Piece(PieceType.EMPTY, null));
		EasyMock.expect(board.getPiece(new Location(2, 0))).andStubReturn(new Piece(PieceType.EMPTY, null));

		EasyMock.expect(pawn.getPieceType()).andStubReturn(PieceType.PAWN);
		EasyMock.expect(pawn.getColor()).andStubReturn(PieceColor.BLACK);
		EasyMock.expect(pawn.canMove(board, from, to)).andStubReturn(true);

		board.movePiece(from, to);
		EasyMock.expectLastCall().times(2);

		board.movePiece(to, from);
		EasyMock.expectLastCall();

		board.setPiece(EasyMock.anyObject(Location.class), EasyMock.anyObject(Piece.class));
		EasyMock.expectLastCall().asStub();

		pawn.setMoved(true);
		EasyMock.expectLastCall();

		EasyMock.expect(game.isInCheck(PieceColor.WHITE)).andStubReturn(false);
		EasyMock.expect(game.isInCheck(PieceColor.BLACK)).andStubReturn(false);
		EasyMock.expect(game.isCheckmate(PieceColor.WHITE)).andStubReturn(false);
		EasyMock.expect(game.isStalemate(PieceColor.WHITE)).andStubReturn(false);

		EasyMock.expect(game.createNotation(
				EasyMock.anyObject(), EasyMock.anyObject(), EasyMock.anyObject(),
				EasyMock.anyObject(), EasyMock.anyObject(), EasyMock.anyBoolean(), EasyMock.anyBoolean()
		)).andStubReturn("notation");

		EasyMock.expect(board.toPositionString()).andStubReturn("mockPositionString");
		EasyMock.expect(board.findKing(PieceColor.WHITE)).andStubReturn(wKingLoc);
		EasyMock.expect(board.findKing(PieceColor.BLACK)).andStubReturn(bKingLoc);
		EasyMock.expect(board.getPiece(wKingLoc)).andStubReturn(new Piece(PieceType.KING, PieceColor.WHITE));
		EasyMock.expect(board.getPiece(bKingLoc)).andStubReturn(new Piece(PieceType.KING, PieceColor.BLACK));

		EasyMock.replay(board, pawn, game);

		game.startNewGame(player1, player2);

		java.lang.reflect.Field playerField = Game.class.getDeclaredField("currentPlayer");
		playerField.setAccessible(true);
		playerField.set(game, player2);

		MoveResult result = game.makeMove(from, to, null);

		assertEquals(MoveResult.VALID, result);

		boolean epTargetSetToDestination = false;
		for (String key : game.positionHistory.keySet()) {
			if (key.endsWith("|" + to.toString())) {
				epTargetSetToDestination = true;
			}
		}
		assertTrue(epTargetSetToDestination);

		EasyMock.verify(board, pawn, game);
	}
}