package domain;

import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.easymock.EasyMock;

import static java.lang.Boolean.TRUE;
import static org.junit.jupiter.api.Assertions.*;

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
		Map<String, Integer> positionHistory = new HashMap<>();
		Game game = new Game(board, status, moveHistory,
				lastMove, halfMoveClock, positionHistory);
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
	@Test
	public void startNewGame_samePlayer() {
		Player player1 = EasyMock.createMock(Player.class);
		Player player2 = EasyMock.createMock(Player.class);
		Board board = EasyMock.createMock(Board.class);
		Map<String, Integer> positionHistory = new HashMap<>();
		EasyMock.expect(player1.getName()).andStubReturn("p1");
		//        EasyMock.replay(player1);
		EasyMock.expect(player2.getName()).andStubReturn("p1");
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
		Game game = new Game(board, status, moveHistory,
				lastMove, halfMoveClock, positionHistory);
		//        Player white = EasyMock.createMock(Player.class);
		//        Player black = EasyMock.createMock(Player.class);
//		player1.setColor(Color.WHITE);
//		EasyMock.expectLastCall();
		EasyMock.replay(player1);
//		player2.setColor(Color.BLACK);
//		EasyMock.expectLastCall();
		EasyMock.replay(player2);
		assertThrows(IllegalArgumentException.class,
				()->game.startNewGame(player1, player2));
//		EasyMock.verify(player1, player2, board);
	}
	@Test
	public void makeMove_validMove() {
		Player player1 = EasyMock.createMock(Player.class);
		Player player2 = EasyMock.createMock(Player.class);
		Location source=EasyMock.createMock(Location.class);
		Location destination=EasyMock.createMock(Location.class);
		Board board = EasyMock.createMock(Board.class);
		Rook rook=EasyMock.createMock(Rook.class);
		EasyMock.expect(player1.getName()).andStubReturn("p1");
		EasyMock.expect(player2.getName()).andStubReturn("p2");
		board.initBoard();
		EasyMock.expectLastCall();
		board.movePiece(source,destination);
		EasyMock.expectLastCall();
		EasyMock.expect(board.getPiece(destination)).andStubReturn(null);
//		EasyMock.expect(board.findKing(Color.BLACK)).andStubReturn(null);
		EasyMock.expect(board.getPiece(source)).andStubReturn(rook);
		EasyMock.expect(board.toPositionString()).andReturn("last").anyTimes();
		EasyMock.replay(board);
		EasyMock.expect(rook.canMove(board,source,destination)).andStubReturn(TRUE);
		EasyMock.expect(rook.getType()).andStubReturn(PieceType.ROOK);
		EasyMock.expect(rook.getColor()).andStubReturn(Color.WHITE);
		EasyMock.replay(rook);
		GameStatus status = GameStatus.WHITE_TURN;
		Map<String, Integer> positionHistory = new HashMap<>();
		Map<String, Integer> target = new HashMap<>();
		List<Move> moveHistory = new ArrayList<>();
		Move lastMove = null;
		int halfMoveClock = 0;
		Game game = EasyMock.partialMockBuilder(Game.class)
				.withConstructor(
						Board.class,
						GameStatus.class,
						List.class,
						Move.class,
						int.class,
						Map.class)
				.withArgs(
						board,
						GameStatus.WHITE_TURN,
						moveHistory,
						null,
						0,
						positionHistory)
				.addMockedMethod("isInCheck", Color.class)
				.addMockedMethod("isCheckmate", Color.class)
				.addMockedMethod("isStalemate", Color.class)
				.createMock();
		EasyMock.expect(game.isInCheck(Color.BLACK)).andReturn(false);
		EasyMock.expect(game.isCheckmate(Color.BLACK)).andReturn(false);
		EasyMock.expect(game.isStalemate(Color.BLACK)).andReturn(false);
		player1.setColor(Color.WHITE);
		EasyMock.expectLastCall();
		EasyMock.expect(player1.getColor()).andStubReturn(Color.WHITE);
		EasyMock.replay(player1);
		player2.setColor(Color.BLACK);
		EasyMock.expectLastCall();
		EasyMock.expect(player2.getColor()).andStubReturn(Color.BLACK);
		EasyMock.replay(player2,game);
		game.startNewGame(player1, player2);
		MoveResult result=game.makeMove(source,destination,PieceType.KNIGHT);
		Move last=new Move(source,destination);
		target.put("last",1);
		assertTrue(target.equals(game.positionHistory));
		assertTrue(last.equal(game.lastMove));
		moveHistory.add(last);
		assertTrue(moveHistory.equals(game.moveHistory));
		assertEquals(game.halfMoveClock,1);
		assertEquals(result,MoveResult.VALID);
		EasyMock.verify(player1, player2, board,rook,game);
	}

	@Test
	public void makeMove_check() {
//		Player player1 = EasyMock.createMock(Player.class);
//		Player player2 = EasyMock.createMock(Player.class);
//		Location source=EasyMock.createMock(Location.class);
//		Location destination=EasyMock.createMock(Location.class);
//		Location king=EasyMock.createMock(Location.class);
//		Board board = EasyMock.createMock(Board.class);
//		Rook rook=EasyMock.createMock(Rook.class);
//		EasyMock.expect(board.getPiece(source))
//				.andReturn(rook)
//				.anyTimes();
////		Pawn pawn=EasyMock.createMock(Pawn.class);
//		EasyMock.expect(player1.getName()).andStubReturn("p1");
//		EasyMock.expect(player2.getName()).andStubReturn("p2");
//		board.initBoard();
//		EasyMock.expectLastCall();
//		board.movePiece(source,destination);
//		EasyMock.expectLastCall();
//		EasyMock.expect(
//				board.getPiece(EasyMock.anyObject(Location.class))
//		).andStubReturn(null);
//		EasyMock.expect(board.getPiece(destination)).andStubReturn(null);
//		EasyMock.expect(board.findKing(Color.BLACK)).andStubReturn(king);
//		EasyMock.expect(board.getPiece(source)).andStubReturn(rook);
//		EasyMock.replay(board);
//		EasyMock.expect(rook.canMove(board,source,destination)).andStubReturn(TRUE);
//		EasyMock.expect(rook.getType()).andStubReturn(PieceType.ROOK);
////		EasyMock.expect(king1.getType()).andStubReturn(PieceType.PAWN);
////		EasyMock.expect(pawn.getColor()).andStubReturn(Color.WHITE);
////		EasyMock.replay(pawn);
//		EasyMock.expect(rook.getColor()).andStubReturn(Color.WHITE);
//		EasyMock.replay(rook);
//		GameStatus status = GameStatus.WHITE_TURN;
//		List<Move> moveHistory = new ArrayList<>();
//		Move lastMove = null;
//		int halfMoveClock = 0;
//		Game game = new Game(board, status, moveHistory, lastMove, halfMoveClock);
//		player1.setColor(Color.WHITE);
//		EasyMock.expect(player1.getColor()).andStubReturn(Color.WHITE);
//		EasyMock.expectLastCall();
//		EasyMock.replay(player1);
//		player2.setColor(Color.BLACK);
//		EasyMock.expectLastCall();
//		EasyMock.replay(player2);
//		game.startNewGame(player1, player2);
//		MoveResult result=game.makeMove(source,destination,PieceType.KNIGHT);
//		assertEquals(result,MoveResult.CHECK);
//		EasyMock.verify(player1, player2, board);
		Board board = EasyMock.createMock(Board.class);
		Player player1 = EasyMock.createMock(Player.class);
		Player player2 = EasyMock.createMock(Player.class);
		Piece piece = EasyMock.createMock(Piece.class);
		List<Move> moveHistory = new ArrayList<>();
		Map<String, Integer> positionHistory = new HashMap<>();
		Move lastMove = null;
		int halfMoveClock = 0;
		Game game = EasyMock.partialMockBuilder(Game.class)
				.withConstructor(
						Board.class,
						GameStatus.class,
						List.class,
						Move.class,
						int.class,
						Map.class)
				.withArgs(
						board,
						GameStatus.WHITE_TURN,
						moveHistory,
						null,
						0,
						positionHistory)
				.addMockedMethod("isInCheck", Color.class)
				.createMock();
		Location from = new Location(7, 0);
		Location to = new Location(0, 0);
		EasyMock.expect(board.toPositionString()).andReturn("last").anyTimes();
		EasyMock.expect(player1.getName()).andReturn("p1").anyTimes();
		EasyMock.expect(player2.getName()).andReturn("p2").anyTimes();
		player1.setColor(Color.WHITE);
		EasyMock.expectLastCall();
		player2.setColor(Color.BLACK);
		EasyMock.expectLastCall();
		EasyMock.expect(player1.getColor()).andReturn(Color.WHITE).anyTimes();
		EasyMock.expect(player2.getColor()).andReturn(Color.BLACK).anyTimes();
		board.initBoard();
		EasyMock.expectLastCall();
		EasyMock.expect(board.getPiece(from)).andReturn(piece);
		EasyMock.expect(board.getPiece(to)).andReturn(null);
		EasyMock.expect(piece.getType()).andReturn(PieceType.ROOK);
		EasyMock.expect(piece.getColor()).andReturn(Color.WHITE).anyTimes();
		EasyMock.expect(piece.canMove(board, from, to)).andReturn(true);
		board.movePiece(from, to);
		EasyMock.expectLastCall();
		EasyMock.expect(game.isInCheck(Color.BLACK)).andReturn(true);
		EasyMock.replay(board, player1, player2, piece, game);
		game.startNewGame(player1, player2);
		MoveResult result = game.makeMove(from, to, null);
		Move last=new Move(from,to);
		Map<String, Integer> target = new HashMap<>();
		target.put("last", 1);
		assertTrue(target.equals(game.positionHistory));
		assertTrue(last.equal(game.lastMove));
		moveHistory.add(last);
		assertTrue(moveHistory.equals(game.moveHistory));
		assertEquals(game.halfMoveClock,1);
		assertEquals(MoveResult.CHECK, result);
		EasyMock.verify(board, player1, player2, piece, game);
	}
	@Test
	public void move_Checkmate(){
		Board board = EasyMock.createMock(Board.class);
		Player player1 = EasyMock.createMock(Player.class);
		Player player2 = EasyMock.createMock(Player.class);
		Piece piece = EasyMock.createMock(Piece.class);
		List<Move> moveHistory = new ArrayList<>();
		Map<String, Integer> positionHistory = new HashMap<>();
		positionHistory.put("first",2);
		Move lastMove = null;
		int halfMoveClock = 0;
		Game game = EasyMock.partialMockBuilder(Game.class)
				.withConstructor(
						Board.class,
						GameStatus.class,
						List.class,
						Move.class,
						int.class,
						Map.class)
				.withArgs(
						board,
						GameStatus.WHITE_TURN,
						moveHistory,
						lastMove,
						halfMoveClock,
						positionHistory)
				.addMockedMethod("isInCheck", Color.class)
				.addMockedMethod("isCheckmate", Color.class)
				.createMock();
		Location from = new Location(0, 0);
		Location to = new Location(7, 0);
		EasyMock.expect(board.toPositionString()).andReturn("last").anyTimes();
		EasyMock.expect(player1.getName()).andReturn("p1").anyTimes();
		EasyMock.expect(player2.getName()).andReturn("p2").anyTimes();
		player1.setColor(Color.WHITE);
		EasyMock.expectLastCall();
		player2.setColor(Color.BLACK);
		EasyMock.expectLastCall();
		EasyMock.expect(player1.getColor()).andReturn(Color.WHITE).anyTimes();
		EasyMock.expect(player2.getColor()).andReturn(Color.BLACK).anyTimes();
		board.initBoard();
		EasyMock.expectLastCall();
		EasyMock.expect(board.getPiece(from)).andReturn(piece);
		EasyMock.expect(board.getPiece(to)).andReturn(null);
		EasyMock.expect(piece.getType()).andReturn(PieceType.KNIGHT);
		EasyMock.expect(piece.getColor()).andReturn(Color.BLACK).anyTimes();
		EasyMock.expect(piece.canMove(board, from, to)).andReturn(true);
		board.movePiece(from, to);
		EasyMock.expectLastCall();
		EasyMock.expect(game.isInCheck(Color.WHITE)).andReturn(false);
		EasyMock.expect(game.isCheckmate(Color.WHITE)).andReturn(true);
		EasyMock.replay(board, player1, player2, piece, game);
		game.startNewGame(player1, player2);
		game.switchTurn();
		MoveResult result = game.makeMove(from, to, null);
		Move last=new Move(from,to);
		Map<String, Integer> target = new HashMap<>();
		target.put("first",2);
		target.put("last", 1);
		assertTrue(target.equals(game.positionHistory));
		assertTrue(last.equal(game.lastMove));
		moveHistory.add(last);
		assertTrue(moveHistory.equals(game.moveHistory));
		assertEquals(game.halfMoveClock,1);
		assertEquals(MoveResult.CHECKMATE, result);
		EasyMock.verify(board, player1, player2, piece, game);
	}
	@Test
	public void move_Stalemate(){
		Board board = EasyMock.createMock(Board.class);
		Player player1 = EasyMock.createMock(Player.class);
		Player player2 = EasyMock.createMock(Player.class);
		Piece piece = EasyMock.createMock(Piece.class);
		List<Move> moveHistory = new ArrayList<>();
		Map<String, Integer> positionHistory = new HashMap<>();
		Move lastMove = null;
		int halfMoveClock = 1;
		Game game = EasyMock.partialMockBuilder(Game.class)
				.withConstructor(
						Board.class,
						GameStatus.class,
						List.class,
						Move.class,
						int.class,
						Map.class)
				.withArgs(
						board,
						GameStatus.WHITE_TURN,
						moveHistory,
						null,
						halfMoveClock,
						positionHistory)
				.addMockedMethod("isInCheck", Color.class)
				.addMockedMethod("isCheckmate", Color.class)
				.addMockedMethod("isStalemate", Color.class)
				.createMock();
		Location from = new Location(0, 0);
		Location to = new Location(7, 7);
		EasyMock.expect(board.toPositionString()).andReturn("last").anyTimes();
		EasyMock.expect(player1.getName()).andReturn("p1").anyTimes();
		EasyMock.expect(player2.getName()).andReturn("p2").anyTimes();
		player1.setColor(Color.WHITE);
		EasyMock.expectLastCall();
		player2.setColor(Color.BLACK);
		EasyMock.expectLastCall();
		EasyMock.expect(player1.getColor()).andReturn(Color.WHITE).anyTimes();
		EasyMock.expect(player2.getColor()).andReturn(Color.BLACK).anyTimes();
		board.initBoard();
		EasyMock.expectLastCall();
		EasyMock.expect(board.getPiece(from)).andReturn(piece);
		EasyMock.expect(board.getPiece(to)).andReturn(null);
		EasyMock.expect(piece.getType()).andReturn(PieceType.KNIGHT);
		EasyMock.expect(piece.getColor()).andReturn(Color.WHITE).anyTimes();
		EasyMock.expect(piece.canMove(board, from, to)).andReturn(true);
		board.movePiece(from, to);
		EasyMock.expectLastCall();
		EasyMock.expect(game.isInCheck(Color.BLACK)).andReturn(false);
		EasyMock.expect(game.isCheckmate(Color.BLACK)).andReturn(false);
		EasyMock.expect(game.isStalemate(Color.BLACK)).andReturn(true);
		EasyMock.replay(board, player1, player2, piece, game);
		game.startNewGame(player1, player2);
//		game.switchTurn();
		MoveResult result = game.makeMove(from, to, null);
		Move last=new Move(from,to);
		Map<String, Integer> target = new HashMap<>();
		target.put("last", 1);
		assertTrue(target.equals(game.positionHistory));
		assertTrue(last.equal(game.lastMove));
		moveHistory.add(last);
		assertTrue(moveHistory.equals(game.moveHistory));
		assertEquals(game.halfMoveClock,2);
		assertEquals(MoveResult.STALEMATE, result);
		EasyMock.verify(board, player1, player2, piece, game);
	}
	@Test
	public void makeMove_sameColorCapture_white(){
		Board board = EasyMock.createMock(Board.class);
		Player player1 = EasyMock.createMock(Player.class);
		Player player2 = EasyMock.createMock(Player.class);
		Piece piece = EasyMock.createMock(Piece.class);
		List<Move> moveHistory = new ArrayList<>();
		Map<String, Integer> positionHistory = new HashMap<>();
		Move lastMove = null;
		int halfMoveClock = 0;
		Game game = new Game(
						board,
						GameStatus.WHITE_TURN,
						moveHistory,
						null,
						0,
						positionHistory);
		Location from = new Location(7, 7);
		Location to = new Location(0, 7);
		EasyMock.expect(player1.getName()).andReturn("p1").anyTimes();
		EasyMock.expect(player2.getName()).andReturn("p2").anyTimes();
		player1.setColor(Color.WHITE);
		EasyMock.expectLastCall();
		player2.setColor(Color.BLACK);
		EasyMock.expectLastCall();
		EasyMock.expect(player1.getColor()).andReturn(Color.WHITE).anyTimes();
		EasyMock.expect(player2.getColor()).andReturn(Color.BLACK).anyTimes();
		board.initBoard();
		EasyMock.expectLastCall();
		EasyMock.expect(board.getPiece(from)).andReturn(piece);
		EasyMock.expect(board.getPiece(to)).andReturn(piece);
		EasyMock.expect(piece.getType()).andReturn(PieceType.KNIGHT);
		EasyMock.expect(piece.getColor()).andReturn(Color.WHITE).anyTimes();
//		EasyMock.expect(piece.canMove(board, from, to)).andReturn(true);
//		board.movePiece(from, to);
//		EasyMock.expectLastCall();
//		EasyMock.expect(game.isInCheck(Color.BLACK)).andReturn(false);
//		EasyMock.expect(game.isCheckmate(Color.BLACK)).andReturn(false);
//		EasyMock.expect(game.isStalemate(Color.BLACK)).andReturn(true);
		EasyMock.replay(board, player1, player2, piece);
		game.startNewGame(player1, player2);
//		game.switchTurn();
		MoveResult result = game.makeMove(from, to, null);
		assertEquals(null,game.lastMove);
		assertTrue(moveHistory.equals(game.moveHistory));
		assertEquals(game.halfMoveClock, halfMoveClock);
		assertTrue(positionHistory.equals(game.positionHistory));
		assertEquals(MoveResult.INVALID_SAME_COLOR_CAPTURE, result);
		EasyMock.verify(board, player1, player2, piece);
	}
	@Test
	public void makeMove_emptySource_white(){
		Board board = EasyMock.createMock(Board.class);
		Player player1 = EasyMock.createMock(Player.class);
		Player player2 = EasyMock.createMock(Player.class);
//		Piece piece = EasyMock.createMock(Piece.class);
		List<Move> moveHistory = new ArrayList<>();
		Map<String, Integer> positionHistory=new HashMap<>();
		Move lastMove = null;
		int halfMoveClock = 0;
		Game game = new Game(
				board,
				GameStatus.WHITE_TURN,
				moveHistory,
				null,
				0,
				positionHistory);
		Location from = new Location(7, 7);
		Location to = new Location(0, 7);
		EasyMock.expect(player1.getName()).andReturn("p1").anyTimes();
		EasyMock.expect(player2.getName()).andReturn("p2").anyTimes();
		player1.setColor(Color.WHITE);
		EasyMock.expectLastCall();
		player2.setColor(Color.BLACK);
		EasyMock.expectLastCall();
		EasyMock.expect(player1.getColor()).andReturn(Color.WHITE).anyTimes();
		EasyMock.expect(player2.getColor()).andReturn(Color.BLACK).anyTimes();
		board.initBoard();
		EasyMock.expectLastCall();
		EasyMock.expect(board.getPiece(from)).andReturn(null);
//		EasyMock.expect(board.getPiece(to)).andReturn(piece);
//		EasyMock.expect(piece.getType()).andReturn(PieceType.KNIGHT);
//		EasyMock.expect(piece.getColor()).andReturn(Color.WHITE).anyTimes();
//		EasyMock.expect(piece.canMove(board, from, to)).andReturn(true);
//		board.movePiece(from, to);
//		EasyMock.expectLastCall();
//		EasyMock.expect(game.isInCheck(Color.BLACK)).andReturn(false);
//		EasyMock.expect(game.isCheckmate(Color.BLACK)).andReturn(false);
//		EasyMock.expect(game.isStalemate(Color.BLACK)).andReturn(true);
		EasyMock.replay(board, player1, player2);
		game.startNewGame(player1, player2);
//		game.switchTurn();
		MoveResult result = game.makeMove(from, to, PieceType.PAWN);
		assertEquals(null,game.lastMove);
		assertTrue(moveHistory.equals(game.moveHistory));
		assertEquals(game.halfMoveClock, halfMoveClock);
		assertTrue(positionHistory.equals(game.positionHistory));
		assertEquals(MoveResult.INVALID_EMPTY_SOURCE, result);
		EasyMock.verify(board, player1, player2);
	}
	@Test
	public void makeMove_wrongTurn_white(){
		Board board = EasyMock.createMock(Board.class);
		Player player1 = EasyMock.createMock(Player.class);
		Player player2 = EasyMock.createMock(Player.class);
		Piece piece = EasyMock.createMock(Piece.class);
		List<Move> moveHistory = new ArrayList<>();
		Map<String, Integer> positionHistory=new HashMap<>();
		Move lastMove = null;
		int halfMoveClock = 0;
		Game game = new Game(
				board,
				GameStatus.WHITE_TURN,
				moveHistory,
				null,
				0,
				positionHistory);
		Location from = new Location(7, 7);
		Location to = new Location(0, 7);
		EasyMock.expect(player1.getName()).andReturn("p1").anyTimes();
		EasyMock.expect(player2.getName()).andReturn("p2").anyTimes();
		player1.setColor(Color.WHITE);
		EasyMock.expectLastCall();
		player2.setColor(Color.BLACK);
		EasyMock.expectLastCall();
		EasyMock.expect(player1.getColor()).andReturn(Color.WHITE).anyTimes();
		EasyMock.expect(player2.getColor()).andReturn(Color.BLACK).anyTimes();
		board.initBoard();
		EasyMock.expectLastCall();
		EasyMock.expect(board.getPiece(from)).andReturn(piece);
		EasyMock.expect(board.getPiece(to)).andReturn(null);
		EasyMock.expect(piece.getType()).andReturn(PieceType.BISHOP);
		EasyMock.expect(piece.getColor()).andReturn(Color.BLACK).anyTimes();
//		EasyMock.expect(piece.canMove(board, from, to)).andReturn(true);
//		board.movePiece(from, to);
//		EasyMock.expectLastCall();
//		EasyMock.expect(game.isInCheck(Color.BLACK)).andReturn(false);
//		EasyMock.expect(game.isCheckmate(Color.BLACK)).andReturn(false);
//		EasyMock.expect(game.isStalemate(Color.BLACK)).andReturn(true);
		EasyMock.replay(board, player1, player2, piece);
		game.startNewGame(player1, player2);
//		game.switchTurn();
		MoveResult result = game.makeMove(from, to, PieceType.PAWN);
		assertEquals(null,game.lastMove);
		assertTrue(moveHistory.equals(game.moveHistory));
		assertEquals(game.halfMoveClock, halfMoveClock);
		assertTrue(positionHistory.equals(game.positionHistory));
		assertEquals(MoveResult.INVALID_WRONG_TURN, result);
		EasyMock.verify(board, player1, player2, piece);
	}
	@Test
	public void makeMove_capture_white(){
		Board board = EasyMock.createMock(Board.class);
		Player player1 = EasyMock.createMock(Player.class);
		Player player2 = EasyMock.createMock(Player.class);
		Piece piece = EasyMock.createMock(Piece.class);
		Piece piece1 = EasyMock.createMock(Piece.class);
		List<Move> moveHistory = new ArrayList<>();
		Move lastMove = null;
		Map<String, Integer> positionHistory=new HashMap<>();
		Map<String, Integer> target=new HashMap<>();
		int halfMoveClock = Integer.MAX_VALUE-1;
		Game game = EasyMock.partialMockBuilder(Game.class)
				.withConstructor(
						Board.class,
						GameStatus.class,
						List.class,
						Move.class,
						int.class,
						Map.class)
				.withArgs(
						board,
						GameStatus.WHITE_TURN,
						moveHistory,
						null,
						halfMoveClock,
						positionHistory)
				.addMockedMethod("isInCheck", Color.class)
				.addMockedMethod("isCheckmate", Color.class)
				.addMockedMethod("isStalemate", Color.class)
				.createMock();
		Location from = new Location(7, 7);
		Location to = new Location(0, 7);
		Move last=new Move(from,to);
//		EasyMock.expect(last.getNotation).andReturn("last").anyTimes();
		EasyMock.expect(board.toPositionString()).andReturn("last").anyTimes();
		EasyMock.expect(player1.getName()).andReturn("p1").anyTimes();
		EasyMock.expect(player2.getName()).andReturn("p2").anyTimes();
		player1.setColor(Color.WHITE);
		EasyMock.expectLastCall();
		player2.setColor(Color.BLACK);
		EasyMock.expectLastCall();
		EasyMock.expect(player1.getColor()).andReturn(Color.WHITE).anyTimes();
		EasyMock.expect(player2.getColor()).andReturn(Color.BLACK).anyTimes();
		board.initBoard();
		EasyMock.expectLastCall();
		EasyMock.expect(board.getPiece(from)).andReturn(piece);
		EasyMock.expect(board.getPiece(to)).andReturn(piece1);
		EasyMock.expect(piece.getType()).andReturn(PieceType.KNIGHT);
		EasyMock.expect(piece.getColor()).andReturn(Color.WHITE).anyTimes();
//		EasyMock.expect(piece1.getType()).andReturn(PieceType.PAWN);
		EasyMock.expect(piece1.getColor()).andReturn(Color.BLACK).anyTimes();
		EasyMock.expect(piece.canMove(board, from, to)).andReturn(true);
		board.movePiece(from, to);
		EasyMock.expectLastCall();
		EasyMock.expect(game.isInCheck(Color.BLACK)).andReturn(false);
		EasyMock.expect(game.isCheckmate(Color.BLACK)).andReturn(false);
		EasyMock.expect(game.isStalemate(Color.BLACK)).andReturn(false);
		EasyMock.replay(board, player1, player2, piece, piece1,game);
		game.startNewGame(player1, player2);
//		game.switchTurn();
		MoveResult result = game.makeMove(from, to, null);
		assertEquals(game.halfMoveClock,Integer.MAX_VALUE);
		target.put("last",1);
		assertTrue(target.equals(game.positionHistory));
		moveHistory.add(last);
		assertTrue(moveHistory.equals(game.moveHistory));
//		assertEquals(moveHistory,Integer.MAX_VALUE);
		assertTrue(last.equal(game.lastMove));
		assertEquals(MoveResult.VALID, result);
		EasyMock.verify(board, player1, player2, piece, piece1,game);
	}
}
