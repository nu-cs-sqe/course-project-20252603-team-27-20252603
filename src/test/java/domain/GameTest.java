package domain;

import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.easymock.EasyMock;

import static java.lang.Boolean.FALSE;
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
		EasyMock.expect(board.isInsideBoard(source)).andReturn(TRUE).anyTimes();
		EasyMock.expect(board.isInsideBoard(destination)).andReturn(TRUE).anyTimes();
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
		EasyMock.expect(game.isInCheck(Color.WHITE)).andReturn(false);
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
		target.put("lastp1",1);
		assertTrue(target.equals(game.positionHistory));
		assertTrue(last.equals(game.lastMove));
		moveHistory.add(last);
		assertTrue(moveHistory.equals(game.moveHistory));
		assertEquals(game.halfMoveClock,1);
		assertEquals(game.currentPlayer.getColor(),Color.BLACK);
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
				.addMockedMethod("isCheckmate", Color.class)
				.createMock();
		Location from = new Location(7, 0);
		Location to = new Location(0, 0);
		EasyMock.expect(board.isInsideBoard(from)).andReturn(TRUE).anyTimes();
		EasyMock.expect(board.isInsideBoard(to)).andReturn(TRUE).anyTimes();
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
		EasyMock.expect(piece.getType()).andReturn(PieceType.ROOK).anyTimes();
		EasyMock.expect(piece.getColor()).andReturn(Color.WHITE).anyTimes();
		EasyMock.expect(piece.canMove(board, from, to)).andReturn(true);
		board.movePiece(from, to);
		EasyMock.expectLastCall();
		EasyMock.expect(game.isInCheck(Color.WHITE)).andReturn(false);
//		EasyMock.expect(game.isCheckmate(Color.WHITE)).andReturn(false);
		EasyMock.expect(game.isCheckmate(Color.BLACK)).andReturn(false);
		EasyMock.expect(game.isInCheck(Color.BLACK)).andReturn(true);
		EasyMock.replay(board, player1, player2, piece, game);
		game.startNewGame(player1, player2);
		MoveResult result = game.makeMove(from, to, null);
		Move last=new Move(from,to);
		Map<String, Integer> target = new HashMap<>();
		target.put("lastp1", 1);
		assertTrue(target.equals(game.positionHistory));
		assertTrue(last.equals(game.lastMove));
		moveHistory.add(last);
		assertTrue(moveHistory.equals(game.moveHistory));
		assertEquals(game.currentPlayer.getColor(),Color.BLACK);
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
		EasyMock.expect(board.isInsideBoard(from)).andReturn(TRUE).anyTimes();
		EasyMock.expect(board.isInsideBoard(to)).andReturn(TRUE).anyTimes();
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
		EasyMock.expect(piece.getType()).andReturn(PieceType.KNIGHT).anyTimes();
		EasyMock.expect(piece.getColor()).andReturn(Color.BLACK).anyTimes();
		EasyMock.expect(piece.canMove(board, from, to)).andReturn(true);
		board.movePiece(from, to);
		EasyMock.expectLastCall();
		EasyMock.expect(game.isInCheck(Color.BLACK)).andStubReturn(false);
//		EasyMock.expect(game.isInCheck(Color.WHITE)).andReturn(true);
		EasyMock.expect(game.isCheckmate(Color.WHITE)).andReturn(true);
		EasyMock.replay(board, player1, player2, piece, game);
		game.startNewGame(player1, player2);
		game.switchTurn();
		MoveResult result = game.makeMove(from, to, null);
		Move last=new Move(from,to);
		Map<String, Integer> target = new HashMap<>();
		target.put("first",2);
		target.put("lastp2", 1);
		assertTrue(target.equals(game.positionHistory));
		assertTrue(last.equals(game.lastMove));
		moveHistory.add(last);
		assertTrue(moveHistory.equals(game.moveHistory));
		assertEquals(game.halfMoveClock,1);
		assertEquals(MoveResult.CHECKMATE, result);
		assertEquals(game.currentPlayer.getColor(),Color.WHITE);
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
		EasyMock.expect(board.isInsideBoard(from)).andReturn(TRUE).anyTimes();
		EasyMock.expect(board.isInsideBoard(to)).andReturn(TRUE).anyTimes();
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
		EasyMock.expect(piece.getType()).andReturn(PieceType.KNIGHT).anyTimes();
		EasyMock.expect(piece.getColor()).andReturn(Color.WHITE).anyTimes();
		EasyMock.expect(piece.canMove(board, from, to)).andReturn(true);
		board.movePiece(from, to);
		EasyMock.expectLastCall();
		EasyMock.expect(game.isInCheck(Color.WHITE)).andStubReturn(false);
		EasyMock.expect(game.isInCheck(Color.BLACK)).andReturn(false);
		EasyMock.expect(game.isCheckmate(Color.BLACK)).andReturn(false);
		EasyMock.expect(game.isStalemate(Color.BLACK)).andReturn(true);
		EasyMock.replay(board, player1, player2, piece, game);
		game.startNewGame(player1, player2);
//		game.switchTurn();
		MoveResult result = game.makeMove(from, to, null);
		Move last=new Move(from,to);
		Map<String, Integer> target = new HashMap<>();
		target.put("lastp1", 1);
		assertTrue(target.equals(game.positionHistory));
		assertTrue(last.equals(game.lastMove));
		moveHistory.add(last);
		assertTrue(moveHistory.equals(game.moveHistory));
		assertEquals(game.halfMoveClock,2);
		assertEquals(MoveResult.STALEMATE, result);
		assertEquals(game.currentPlayer.getColor(),Color.BLACK);
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
		EasyMock.expect(board.isInsideBoard(from)).andReturn(TRUE).anyTimes();
		EasyMock.expect(board.isInsideBoard(to)).andReturn(TRUE).anyTimes();
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
//		EasyMock.expect(piece.getType()).andReturn(PieceType.KNIGHT);
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
		assertEquals(game.currentPlayer.getColor(),Color.WHITE);
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
		EasyMock.expect(board.isInsideBoard(from)).andReturn(TRUE).anyTimes();
		EasyMock.expect(board.isInsideBoard(to)).andReturn(TRUE).anyTimes();
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
		assertEquals(game.currentPlayer.getColor(),Color.WHITE);
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
		Location to = new Location(1, 7);
		EasyMock.expect(board.isInsideBoard(from)).andReturn(TRUE).anyTimes();
		EasyMock.expect(board.isInsideBoard(to)).andReturn(TRUE).anyTimes();
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
//		EasyMock.expect(piece.getType()).andReturn(PieceType.BISHOP);
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
		assertEquals(game.currentPlayer.getColor(),Color.WHITE);
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
		int halfMoveClock = 99;
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
		EasyMock.expect(board.isInsideBoard(from)).andReturn(TRUE).anyTimes();
		EasyMock.expect(board.isInsideBoard(to)).andReturn(TRUE).anyTimes();
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
		EasyMock.expect(piece.getType()).andReturn(PieceType.KNIGHT).anyTimes();
		EasyMock.expect(piece.getColor()).andReturn(Color.WHITE).anyTimes();
//		EasyMock.expect(piece1.getType()).andReturn(PieceType.PAWN);
		EasyMock.expect(piece1.getColor()).andReturn(Color.BLACK).anyTimes();
		EasyMock.expect(piece.canMove(board, from, to)).andReturn(true);
		board.movePiece(from, to);
		EasyMock.expectLastCall();
		EasyMock.expect(game.isInCheck(Color.WHITE)).andReturn(false);
		EasyMock.expect(game.isInCheck(Color.BLACK)).andReturn(false);
		EasyMock.expect(game.isCheckmate(Color.BLACK)).andReturn(false);
		EasyMock.expect(game.isStalemate(Color.BLACK)).andReturn(false);
		EasyMock.replay(board, player1, player2, piece, piece1,game);
		game.startNewGame(player1, player2);
//		game.switchTurn();
		MoveResult result = game.makeMove(from, to, null);
		assertEquals(game.halfMoveClock,100);
		target.put("lastp1",1);
		assertTrue(target.equals(game.positionHistory));
		moveHistory.add(last);
		assertTrue(moveHistory.equals(game.moveHistory));
//		assertEquals(moveHistory,Integer.MAX_VALUE);
		assertTrue(last.equals(game.lastMove));
		assertEquals(MoveResult.VALID, result);
		assertEquals(game.currentPlayer.getColor(),Color.BLACK);
		EasyMock.verify(board, player1, player2, piece, piece1,game);
	}
	@Test
	public void makeMove_outOfBound_black(){
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
		Location from = new Location(0, -1);
		Location to = new Location(0, 0);
		Move last=new Move(from,to);
//		EasyMock.expect(last.getNotation).andReturn("last").anyTimes();
		EasyMock.expect(board.toPositionString()).andReturn("last").anyTimes();
		EasyMock.expect(board.isInsideBoard(from)).andReturn(FALSE).anyTimes();
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
//		EasyMock.expect(board.getPiece(from)).andReturn(piece);
//		EasyMock.expect(board.getPiece(to)).andReturn(piece1);
//		EasyMock.expect(piece.getType()).andReturn(PieceType.KNIGHT);
//		EasyMock.expect(piece.getColor()).andReturn(Color.WHITE).anyTimes();
////		EasyMock.expect(piece1.getType()).andReturn(PieceType.PAWN);
//		EasyMock.expect(piece1.getColor()).andReturn(Color.BLACK).anyTimes();
//		EasyMock.expect(piece.canMove(board, from, to)).andReturn(true);
//		board.movePiece(from, to);
//		EasyMock.expectLastCall();
//		EasyMock.expect(game.isInCheck(Color.BLACK)).andReturn(false);
//		EasyMock.expect(game.isCheckmate(Color.BLACK)).andReturn(false);
//		EasyMock.expect(game.isStalemate(Color.BLACK)).andReturn(false);
		EasyMock.replay(board, player1, player2, piece, piece1,game);
		game.startNewGame(player1, player2);
		game.switchTurn();
		MoveResult result = game.makeMove(from, to, null);
		assertEquals(game.halfMoveClock,Integer.MAX_VALUE-1);
//		target.put("last",1);
		assertTrue(target.equals(game.positionHistory));
//		moveHistory.add(last);
		assertTrue(moveHistory.equals(game.moveHistory));
		assertEquals(game.currentPlayer.getColor(),Color.BLACK);
//		assertEquals(moveHistory,Integer.MAX_VALUE);
		assertEquals(game.lastMove,null);
		assertEquals(MoveResult.INVALID_OUT_OF_BOUNDS, result);
		EasyMock.verify(board, player1, player2, piece, piece1,game);
	}
	@Test
	public void makeMove_outOfBound_white(){
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
		Location from = new Location(0, -1);
		Location to = new Location(0, 0);
		Move last=new Move(from,to);
//		EasyMock.expect(last.getNotation).andReturn("last").anyTimes();
		EasyMock.expect(board.toPositionString()).andReturn("last").anyTimes();
		EasyMock.expect(board.isInsideBoard(from)).andReturn(TRUE).anyTimes();
		EasyMock.expect(board.isInsideBoard(to)).andReturn(FALSE).anyTimes();
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
		EasyMock.replay(board, player1, player2, piece, piece1,game);
		game.startNewGame(player1, player2);
//		game.switchTurn();
		MoveResult result = game.makeMove(from, to, null);
		assertEquals(game.currentPlayer.getColor(),Color.WHITE);
		assertEquals(game.halfMoveClock,Integer.MAX_VALUE-1);
//		target.put("last",1);
		assertTrue(target.equals(game.positionHistory));
//		moveHistory.add(last);
		assertTrue(moveHistory.equals(game.moveHistory));
//		assertEquals(moveHistory,Integer.MAX_VALUE);
		assertEquals(game.lastMove,null);
		assertEquals(MoveResult.INVALID_OUT_OF_BOUNDS, result);
		EasyMock.verify(board, player1, player2, piece, piece1,game);
	}
	@Test
	public void makeMove_illegalMove_black(){
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
		Game game = new Game(
						board,
						GameStatus.WHITE_TURN,
						moveHistory,
						null,
						halfMoveClock,
						positionHistory);
		Location from = new Location(7, 1);
		Location to = new Location(1, 0);
		Move last=new Move(from,to);
//		EasyMock.expect(last.getNotation).andReturn("last").anyTimes();
		EasyMock.expect(board.toPositionString()).andReturn("last").anyTimes();
		EasyMock.expect(board.isInsideBoard(from)).andReturn(TRUE).anyTimes();
		EasyMock.expect(board.isInsideBoard(to)).andReturn(TRUE).anyTimes();
		EasyMock.expect(board.getPiece(from)).andReturn(piece);
		EasyMock.expect(board.getPiece(to)).andReturn(piece1);
//		EasyMock.expect(piece.getType()).andReturn(PieceType.KNIGHT);
		EasyMock.expect(piece.getColor()).andReturn(Color.BLACK).anyTimes();
//		EasyMock.expect(piece1.getType()).andReturn(PieceType.PAWN);
		EasyMock.expect(piece1.getColor()).andReturn(Color.WHITE).anyTimes();
		EasyMock.expect(piece.canMove(board, from, to)).andReturn(false).anyTimes();
//		board.movePiece(from, to);
//		EasyMock.expectLastCall();
//		EasyMock.expect(game.isInCheck(Color.BLACK)).andReturn(false);
//		EasyMock.expect(game.isCheckmate(Color.BLACK)).andReturn(false);
//		EasyMock.expect(game.isStalemate(Color.BLACK)).andReturn(false);
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
		EasyMock.replay(board, player1, player2, piece, piece1);
		game.startNewGame(player1, player2);
		game.switchTurn();
		MoveResult result = game.makeMove(from, to, null);
		assertEquals(game.currentPlayer.getColor(),Color.BLACK);
		assertEquals(game.halfMoveClock,Integer.MAX_VALUE-1);
//		target.put("last",1);
		assertTrue(target.equals(game.positionHistory));
//		moveHistory.add(last);
		assertTrue(moveHistory.equals(game.moveHistory));
//		assertEquals(moveHistory,Integer.MAX_VALUE);
		assertEquals(game.lastMove,null);
		assertEquals(MoveResult.INVALID_ILLEGAL_PIECE_MOVE, result);
		EasyMock.verify(board, player1, player2, piece, piece1);
	}
	@Test
	public void makeMove_threefoldRepetition_white(){
		Board board = EasyMock.createMock(Board.class);
		Player player1 = EasyMock.createMock(Player.class);
		Player player2 = EasyMock.createMock(Player.class);
		Piece piece = EasyMock.createMock(Piece.class);
		Piece piece1 = EasyMock.createMock(Piece.class);
		List<Move> moveHistory = new ArrayList<>();
		Move lastMove = null;
		Map<String, Integer> positionHistory=new HashMap<>();
		positionHistory.put("lastp1",2);
		Map<String, Integer> target=new HashMap<>();
		int halfMoveClock = 99;
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
				.createMock();
		Location from = new Location(7, 1);
		Location to = new Location(1, 0);
		Move last=new Move(from,to);
//		EasyMock.expect(last.getNotation).andReturn("last").anyTimes();
		EasyMock.expect(board.toPositionString()).andReturn("last").anyTimes();
		EasyMock.expect(board.isInsideBoard(from)).andReturn(TRUE).anyTimes();
		EasyMock.expect(board.isInsideBoard(to)).andReturn(TRUE).anyTimes();
		EasyMock.expect(board.getPiece(from)).andReturn(piece);
		EasyMock.expect(board.getPiece(to)).andReturn(piece1);
		EasyMock.expect(piece.getType()).andReturn(PieceType.QUEEN).anyTimes();
		EasyMock.expect(piece.getColor()).andReturn(Color.WHITE).anyTimes();
//		EasyMock.expect(piece1.getType()).andReturn(PieceType.PAWN);
		EasyMock.expect(piece1.getColor()).andReturn(Color.BLACK).anyTimes();
		EasyMock.expect(piece.canMove(board, from, to)).andReturn(true).anyTimes();
		board.movePiece(from, to);
		EasyMock.expectLastCall();
		EasyMock.expect(game.isInCheck(Color.WHITE)).andReturn(false);
//		EasyMock.expect(game.isCheckmate(Color.BLACK)).andReturn(false);
//		EasyMock.expect(game.isStalemate(Color.BLACK)).andReturn(false);
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
		EasyMock.replay(board, player1, player2, piece, piece1);
		game.startNewGame(player1, player2);
//		game.switchTurn();
		MoveResult result = game.makeMove(from, to, null);
		assertEquals(game.currentPlayer.getColor(),Color.WHITE);
		assertEquals(game.halfMoveClock,100);
		target.put("lastp1",3);
		assertTrue(target.equals(game.positionHistory));
		List<Move> history = new ArrayList<>();
		history.add(last);
//		System.out.println(history);
		assertTrue(history.equals(game.moveHistory));
		assertEquals(game.lastMove,last);
		assertEquals(MoveResult.DRAW, result);
		EasyMock.verify(board, player1, player2, piece, piece1);
	}
	@Test
	public void makeMove_50Move_white(){
		Board board = EasyMock.createMock(Board.class);
		Player player1 = EasyMock.createMock(Player.class);
		Player player2 = EasyMock.createMock(Player.class);
		Piece piece = EasyMock.createMock(Piece.class);
		Piece piece1 = EasyMock.createMock(Piece.class);
		List<Move> moveHistory = new ArrayList<>();
		Move lastMove = null;
		Map<String, Integer> positionHistory=new HashMap<>();
		positionHistory.put("lastp1",1);
		Map<String, Integer> target=new HashMap<>();
		int halfMoveClock = 100;
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
				.createMock();
		Location from = new Location(7, 1);
		Location to = new Location(1, 0);
		Move last=new Move(from,to);
//		EasyMock.expect(last.getNotation).andReturn("last").anyTimes();
		EasyMock.expect(board.toPositionString()).andReturn("last").anyTimes();
		EasyMock.expect(board.isInsideBoard(from)).andReturn(TRUE).anyTimes();
		EasyMock.expect(board.isInsideBoard(to)).andReturn(TRUE).anyTimes();
		EasyMock.expect(board.getPiece(from)).andReturn(piece);
		EasyMock.expect(board.getPiece(to)).andReturn(piece1);
		EasyMock.expect(piece.getType()).andReturn(PieceType.QUEEN).anyTimes();
		EasyMock.expect(piece.getColor()).andReturn(Color.WHITE).anyTimes();
//		EasyMock.expect(piece1.getType()).andReturn(PieceType.PAWN);
		EasyMock.expect(piece1.getColor()).andReturn(Color.BLACK).anyTimes();
		EasyMock.expect(piece.canMove(board, from, to)).andReturn(true).anyTimes();
//		board.movePiece(from, to);
//		EasyMock.expectLastCall();
		EasyMock.expect(game.isInCheck(Color.WHITE)).andReturn(false);
//		EasyMock.expect(game.isCheckmate(Color.BLACK)).andReturn(false);
//		EasyMock.expect(game.isStalemate(Color.BLACK)).andReturn(false);
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
		EasyMock.replay(board, player1, player2, piece, piece1, game);
		game.startNewGame(player1, player2);
//		game.switchTurn();
		MoveResult result = game.makeMove(from, to, null);
		assertEquals(game.currentPlayer.getColor(),Color.WHITE);
		assertEquals(game.halfMoveClock,101);
		target.put("lastp1",1);
		assertTrue(target.equals(game.positionHistory));
		List<Move> history = new ArrayList<>();
		history.add(last);
//		System.out.println(history);
		assertTrue(history.equals(game.moveHistory));
		assertEquals(game.lastMove,last);
		assertEquals(MoveResult.DRAW, result);
		EasyMock.verify(board, player1, player2, piece, piece1, game);
	}
	@Test
	public void makeMove_selfCheck_white(){
		Board board = EasyMock.createMock(Board.class);
		Player player1 = EasyMock.createMock(Player.class);
		Player player2 = EasyMock.createMock(Player.class);
		Piece piece = EasyMock.createMock(Piece.class);
		Piece piece1 = EasyMock.createMock(Piece.class);
		List<Move> moveHistory = new ArrayList<>();
		Move lastMove = null;
		Map<String, Integer> positionHistory=new HashMap<>();
		Map<String, Integer> target=new HashMap<>();
		int halfMoveClock = 100;
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
//				.addMockedMethod("isCheckmate", Color.class)
//				.addMockedMethod("isStalemate", Color.class)
				.createMock();
		Location from = new Location(0, -1);
		Location to = new Location(0, 0);
		Move last=new Move(from,to);
//		EasyMock.expect(last.getNotation).andReturn("last").anyTimes();
		EasyMock.expect(board.toPositionString()).andReturn("last").anyTimes();
		EasyMock.expect(board.isInsideBoard(from)).andReturn(TRUE).anyTimes();
		EasyMock.expect(board.isInsideBoard(to)).andReturn(TRUE).anyTimes();
		EasyMock.expect(board.getPiece(from)).andReturn(piece).anyTimes();
		EasyMock.expect(board.getPiece(to)).andStubReturn(null);
		EasyMock.expect(piece.canMove(board,from,to)).andReturn(true);
		EasyMock.expect(player1.getName()).andReturn("p1").anyTimes();
		EasyMock.expect(player2.getName()).andReturn("p2").anyTimes();
		player1.setColor(Color.WHITE);
		EasyMock.expectLastCall();
		player2.setColor(Color.BLACK);
		EasyMock.expectLastCall();
//		EasyMock.expect(game.isInCheck(Color.WHITE)).andReturn(true).anyTimes();
		EasyMock.expect(player1.getColor()).andReturn(Color.WHITE).anyTimes();
		EasyMock.expect(player2.getColor()).andReturn(Color.BLACK).anyTimes();
//		EasyMock.expect(piece.getType()).andReturn(PieceType.QUEEN);
		EasyMock.expect(piece.getColor()).andReturn(Color.WHITE).anyTimes();
		board.initBoard();
		EasyMock.expectLastCall();
		EasyMock.expect(game.isInCheck(Color.WHITE)).andReturn(true);
		EasyMock.replay(board, player1, player2, piece, piece1,game);
		game.startNewGame(player1, player2);
//		game.switchTurn();
		MoveResult result = game.makeMove(from, to, null);
		assertEquals(game.currentPlayer.getColor(),Color.WHITE);
		assertEquals(game.halfMoveClock,100);
//		target.put("last",1);
		assertTrue(target.equals(game.positionHistory));
//		moveHistory.add(last);
		assertTrue(moveHistory.equals(game.moveHistory));
//		assertEquals(moveHistory,Integer.MAX_VALUE);
		assertEquals(game.lastMove,null);
		assertEquals(MoveResult.INVALID_SELF_CHECK, result);
		EasyMock.verify(board, player1, player2, piece, piece1,game);
	}
	@Test
	public void makeMove_pawnPromoption_white() {
		Board board = EasyMock.createMock(Board.class);
		Player player1 = EasyMock.createMock(Player.class);
		Player player2 = EasyMock.createMock(Player.class);
		Piece piece = EasyMock.createMock(Piece.class);
		Bishop bishop=new Bishop(Color.WHITE);
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
				.addMockedMethod("isCheckmate", Color.class)
				.addMockedMethod("isStalemate", Color.class)
				.createMock();
		Location from = new Location(7, 0);
		Location to = new Location(0, 0);
		EasyMock.expect(board.isInsideBoard(from)).andReturn(TRUE).anyTimes();
		EasyMock.expect(board.isInsideBoard(to)).andReturn(TRUE).anyTimes();
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
		EasyMock.expect(piece.getType()).andReturn(PieceType.PAWN).anyTimes();
		EasyMock.expect(piece.getColor()).andReturn(Color.WHITE).anyTimes();
		EasyMock.expect(piece.canMove(board, from, to)).andReturn(true);
		board.movePiece(from, to);
		EasyMock.expectLastCall();
		board.setPiece(to,bishop);
		EasyMock.expectLastCall();
		EasyMock.expect(game.isInCheck(Color.WHITE)).andReturn(false);
		EasyMock.expect(game.isCheckmate(Color.BLACK)).andReturn(false);
		EasyMock.expect(game.isInCheck(Color.BLACK)).andReturn(true);
//		EasyMock.expect(game.isCheckmate(Color.BLACK)).andReturn(false);
//		EasyMock.expect(game.isStalemate(Color.BLACK)).andReturn(false);
		EasyMock.replay(board, player1, player2, piece, game);
		game.startNewGame(player1, player2);
		MoveResult result = game.makeMove(from, to, PieceType.BISHOP);
		Move last=new Move(from,to);
		Map<String, Integer> target = new HashMap<>();
		target.put("lastp1", 1);
//		Bishop bishop=new Bishop(Color.WHITE);
		assertTrue(target.equals(game.positionHistory));
		assertTrue(last.equals(game.lastMove));
		moveHistory.add(last);
		assertTrue(moveHistory.equals(game.moveHistory));
		assertEquals(game.currentPlayer.getColor(),Color.BLACK);
		assertEquals(game.halfMoveClock,1);
		System.out.println(result);
		assertEquals(MoveResult.CHECK, result);
		EasyMock.verify(board, player1, player2, piece, game);
	}
	@Test
	public void switchTurn_white() {
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
		EasyMock.expect(player1.getName()).andReturn("p1").anyTimes();
		EasyMock.expect(player2.getName()).andReturn("p2").anyTimes();
		EasyMock.expect(player1.getColor()).andReturn(Color.WHITE).anyTimes();
		EasyMock.expect(player2.getColor()).andReturn(Color.BLACK).anyTimes();
		player1.setColor(Color.WHITE);
		EasyMock.expectLastCall();
		player2.setColor(Color.BLACK);
		EasyMock.expectLastCall();
		board.initBoard();
		EasyMock.expectLastCall();
		EasyMock.replay(board, player1, player2);
		game.startNewGame(player1, player2);
		game.switchTurn();
		assertEquals(game.currentPlayer.getColor(),Color.BLACK);
		EasyMock.verify(board, player1, player2);
	}
	@Test
	public void switchTurn_black() {
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
		EasyMock.expect(player1.getName()).andReturn("p1").anyTimes();
		EasyMock.expect(player2.getName()).andReturn("p2").anyTimes();
		EasyMock.expect(player1.getColor()).andReturn(Color.WHITE).anyTimes();
		EasyMock.expect(player2.getColor()).andReturn(Color.BLACK).anyTimes();
		player1.setColor(Color.WHITE);
		EasyMock.expectLastCall();
		player2.setColor(Color.BLACK);
		EasyMock.expectLastCall();
		board.initBoard();
		EasyMock.expectLastCall();
		EasyMock.replay(board, player1, player2);
		game.startNewGame(player1, player2);
		game.switchTurn();
		game.switchTurn();
		assertEquals(game.currentPlayer.getColor(),Color.WHITE);
		EasyMock.verify(board, player1, player2);
	}
	@Test
	public void isInCheck() {
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
		Location from = new Location(0, 0);
		Location to = new Location(0, 7);
		EasyMock.expect(board.getPiece(from)).andReturn(piece).anyTimes();
//		EasyMock.expect(piece.getType()).andReturn(PieceType.PAWN);
		EasyMock.expect(piece.getColor()).andReturn(Color.WHITE);
		EasyMock.expect(board.findKing(Color.BLACK)).andReturn(to);
		EasyMock.expect(player1.getName()).andReturn("p1").anyTimes();
		EasyMock.expect(player2.getName()).andReturn("p2").anyTimes();
		EasyMock.expect(player1.getColor()).andReturn(Color.WHITE).anyTimes();
		EasyMock.expect(player2.getColor()).andReturn(Color.BLACK).anyTimes();
		EasyMock.expect(piece.canMove(board,from,to)).andReturn(true);
		player1.setColor(Color.WHITE);
		EasyMock.expectLastCall();
		player2.setColor(Color.BLACK);
		EasyMock.expectLastCall();
		board.initBoard();
		EasyMock.expectLastCall();
		EasyMock.replay(board, player1, player2, piece);
		game.startNewGame(player1, player2);
//		game.switchTurn();
//		game.switchTurn();
		Boolean result=game.isInCheck(Color.BLACK);
		assertTrue(result);
		EasyMock.verify(board, player1, player2, piece);
	}
	@Test
	public void isInCheck_firstIsNull() {
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
		Location first=new Location(0,0);
		Location from = new Location(0, 1);
		Location to = new Location(7, 7);
		EasyMock.expect(board.getPiece(from)).andReturn(piece).anyTimes();
		EasyMock.expect(board.getPiece(first)).andReturn(null).anyTimes();
//		EasyMock.expect(piece.getType()).andReturn(PieceType.PAWN);
		EasyMock.expect(piece.getColor()).andReturn(Color.WHITE);
		EasyMock.expect(board.findKing(Color.BLACK)).andReturn(to);
		EasyMock.expect(player1.getName()).andReturn("p1").anyTimes();
		EasyMock.expect(player2.getName()).andReturn("p2").anyTimes();
		EasyMock.expect(player1.getColor()).andReturn(Color.WHITE).anyTimes();
		EasyMock.expect(player2.getColor()).andReturn(Color.BLACK).anyTimes();
		EasyMock.expect(piece.canMove(board,from,to)).andReturn(true);
		player1.setColor(Color.WHITE);
		EasyMock.expectLastCall();
		player2.setColor(Color.BLACK);
		EasyMock.expectLastCall();
		board.initBoard();
		EasyMock.expectLastCall();
		EasyMock.replay(board, player1, player2, piece);
		game.startNewGame(player1, player2);
//		game.switchTurn();
//		game.switchTurn();
		Boolean result=game.isInCheck(Color.BLACK);
		assertTrue(result);
		EasyMock.verify(board, player1, player2, piece);
	}
	@Test
	public void isInCheck_false() {
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
//		Location first=new Location(0,0);
		Location from = new Location(7, 7);
		Location to = new Location(0, 0);
		EasyMock.expect(board.getPiece(EasyMock.anyObject(Location.class))).andReturn(piece).anyTimes();
//		EasyMock.expect(board.getPiece(first)).andReturn(null).anyTimes();
//		EasyMock.expect(piece.getType()).andReturn(PieceType.PAWN);
		EasyMock.expect(piece.getColor()).andReturn(Color.BLACK).anyTimes();
		EasyMock.expect(board.findKing(Color.WHITE)).andReturn(to);
		EasyMock.expect(player1.getName()).andReturn("p1").anyTimes();
		EasyMock.expect(player2.getName()).andReturn("p2").anyTimes();
		EasyMock.expect(player1.getColor()).andReturn(Color.WHITE).anyTimes();
		EasyMock.expect(player2.getColor()).andReturn(Color.BLACK).anyTimes();
		EasyMock.expect(piece.canMove
				(EasyMock.anyObject(Board.class),
						EasyMock.anyObject(Location.class),
				EasyMock.anyObject(Location.class))).andReturn(false).anyTimes();
		player1.setColor(Color.WHITE);
		EasyMock.expectLastCall();
		player2.setColor(Color.BLACK);
		EasyMock.expectLastCall();
		board.initBoard();
		EasyMock.expectLastCall();
		EasyMock.replay(board, player1, player2, piece);
		game.startNewGame(player1, player2);
//		game.switchTurn();
//		game.switchTurn();
		Boolean result=game.isInCheck(Color.WHITE);
		assertFalse(result);
		EasyMock.verify(board, player1, player2, piece);
	}
	@Test
	public void isCheckmate_true() {
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
//				.addMockedMethod("isCheckmate", Color.class)
//				.addMockedMethod("isStalemate", Color.class)
				.createMock();
//		Location first=new Location(0,0);
		Location from = new Location(7, 7);
		Location to = new Location(0, 0);
		EasyMock.expect(game.isInCheck(Color.BLACK)).andReturn(true).anyTimes();
		EasyMock.expect(board.getPiece(EasyMock.anyObject(Location.class))).andReturn(piece).anyTimes();
//		EasyMock.expect(board.getPiece(first)).andReturn(null).anyTimes();
//		EasyMock.expect(piece.getType()).andReturn(PieceType.PAWN);
		EasyMock.expect(piece.getColor()).andReturn(Color.BLACK).anyTimes();
		EasyMock.expect(board.findKing(Color.BLACK)).andReturn(to);
		EasyMock.expect(player1.getName()).andReturn("p1").anyTimes();
		EasyMock.expect(player2.getName()).andReturn("p2").anyTimes();
		EasyMock.expect(player1.getColor()).andReturn(Color.WHITE).anyTimes();
		EasyMock.expect(player2.getColor()).andReturn(Color.BLACK).anyTimes();
		EasyMock.expect(piece.canMove
				(EasyMock.anyObject(Board.class),
						EasyMock.anyObject(Location.class),
						EasyMock.anyObject(Location.class))).andReturn(true).anyTimes();
		player1.setColor(Color.WHITE);
		EasyMock.expectLastCall();
		player2.setColor(Color.BLACK);
		EasyMock.expectLastCall();
		board.initBoard();
		EasyMock.expectLastCall();
		board.movePiece(EasyMock.anyObject(Location.class),
				EasyMock.anyObject(Location.class));
		EasyMock.expectLastCall().anyTimes();
		board.setPiece(EasyMock.anyObject(Location.class),
				EasyMock.anyObject(Piece.class));
		EasyMock.expectLastCall().anyTimes();
		EasyMock.replay(board, player1, player2, piece, game);
		game.startNewGame(player1, player2);
//		game.switchTurn();
//		game.switchTurn();
		Boolean result=game.isCheckmate(Color.BLACK);
		assertTrue(result);
		EasyMock.verify(board, player1, player2, piece,game);
	}
	@Test
	public void isCheckmate_false() {
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
//				.addMockedMethod("isCheckmate", Color.class)
//				.addMockedMethod("isStalemate", Color.class)
				.createMock();
//		Location first=new Location(0,0);
		Location from = new Location(7, 7);
		Location to = new Location(7, 7);
		EasyMock.expect(game.isInCheck(Color.WHITE))
				.andReturn(true).andReturn(false);
		EasyMock.expect(board.getPiece(EasyMock.anyObject(Location.class))).andReturn(piece).anyTimes();
//		EasyMock.expect(board.getPiece(first)).andReturn(null).anyTimes();
//		EasyMock.expect(piece.getType()).andReturn(PieceType.PAWN);
		EasyMock.expect(piece.getColor()).andReturn(Color.WHITE).anyTimes();
		EasyMock.expect(board.findKing(Color.WHITE)).andReturn(to);
		EasyMock.expect(player1.getName()).andReturn("p1").anyTimes();
		EasyMock.expect(player2.getName()).andReturn("p2").anyTimes();
		EasyMock.expect(player1.getColor()).andReturn(Color.WHITE).anyTimes();
		EasyMock.expect(player2.getColor()).andReturn(Color.BLACK).anyTimes();
		EasyMock.expect(piece.canMove
				(EasyMock.anyObject(Board.class),
						EasyMock.anyObject(Location.class),
						EasyMock.anyObject(Location.class))).andReturn(true).anyTimes();
		player1.setColor(Color.WHITE);
		EasyMock.expectLastCall();
		player2.setColor(Color.BLACK);
		EasyMock.expectLastCall();
		board.initBoard();
		EasyMock.expectLastCall();
		board.movePiece(EasyMock.anyObject(Location.class),
				EasyMock.anyObject(Location.class));
		EasyMock.expectLastCall().anyTimes();
		board.setPiece(EasyMock.anyObject(Location.class),
				EasyMock.anyObject(Piece.class));
		EasyMock.expectLastCall().anyTimes();
		EasyMock.replay(board, player1, player2, piece, game);
		game.startNewGame(player1, player2);
//		game.switchTurn();
//		game.switchTurn();
		Boolean result=game.isCheckmate(Color.WHITE);
		assertFalse(result);
		EasyMock.verify(board, player1, player2, piece,game);
	}
	@Test
	public void isStalemate_allCheck_true() {
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
//				.addMockedMethod("isCheckmate", Color.class)
//				.addMockedMethod("isStalemate", Color.class)
				.createMock();
//		Location first=new Location(0,0);
		Location from = new Location(7, 7);
		Location to = new Location(7, 7);
		EasyMock.expect(game.isInCheck(Color.WHITE))
				.andReturn(false).andReturn(true).anyTimes();
		EasyMock.expect(board.getPiece(EasyMock.anyObject(Location.class))).andReturn(piece).anyTimes();
//		EasyMock.expect(board.getPiece(first)).andReturn(null).anyTimes();
//		EasyMock.expect(piece.getType()).andReturn(PieceType.PAWN);
		EasyMock.expect(piece.getColor()).andReturn(Color.WHITE).anyTimes();
//		EasyMock.expect(board.findKing(Color.WHITE)).andReturn(to);
		EasyMock.expect(player1.getName()).andReturn("p1").anyTimes();
		EasyMock.expect(player2.getName()).andReturn("p2").anyTimes();
		EasyMock.expect(player1.getColor()).andReturn(Color.WHITE).anyTimes();
		EasyMock.expect(player2.getColor()).andReturn(Color.BLACK).anyTimes();
		EasyMock.expect(piece.canMove
				(EasyMock.anyObject(Board.class),
						EasyMock.anyObject(Location.class),
						EasyMock.anyObject(Location.class))).andReturn(true).anyTimes();
		player1.setColor(Color.WHITE);
		EasyMock.expectLastCall();
		player2.setColor(Color.BLACK);
		EasyMock.expectLastCall();
		board.initBoard();
		EasyMock.expectLastCall();
		board.movePiece(EasyMock.anyObject(Location.class),
				EasyMock.anyObject(Location.class));
		EasyMock.expectLastCall().anyTimes();
		board.setPiece(EasyMock.anyObject(Location.class),
				EasyMock.anyObject(Piece.class));
		EasyMock.expectLastCall().anyTimes();
		EasyMock.replay(board, player1, player2, piece, game);
		game.startNewGame(player1, player2);
//		game.switchTurn();
//		game.switchTurn();
		Boolean result=game.isStalemate(Color.WHITE);
		assertTrue(result);
		EasyMock.verify(board, player1, player2, piece,game);
	}
	@Test
	public void isStalemate_noMove_true() {
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
//				.addMockedMethod("isCheckmate", Color.class)
//				.addMockedMethod("isStalemate", Color.class)
				.createMock();
//		Location first=new Location(0,0);
		Location from = new Location(7, 7);
		Location to = new Location(7, 7);
		EasyMock.expect(game.isInCheck(Color.WHITE)).andReturn(false);
//				.andReturn(false).andReturn(true).anyTimes();
		EasyMock.expect(board.getPiece(EasyMock.anyObject(Location.class))).andReturn(piece).anyTimes();
//		EasyMock.expect(board.getPiece(first)).andReturn(null).anyTimes();
//		EasyMock.expect(piece.getType()).andReturn(PieceType.PAWN);
		EasyMock.expect(piece.getColor()).andReturn(Color.WHITE).anyTimes();
//		EasyMock.expect(board.findKing(Color.WHITE)).andReturn(to);
		EasyMock.expect(player1.getName()).andReturn("p1").anyTimes();
		EasyMock.expect(player2.getName()).andReturn("p2").anyTimes();
		EasyMock.expect(player1.getColor()).andReturn(Color.WHITE).anyTimes();
		EasyMock.expect(player2.getColor()).andReturn(Color.BLACK).anyTimes();
		EasyMock.expect(piece.canMove
				(EasyMock.anyObject(Board.class),
						EasyMock.anyObject(Location.class),
						EasyMock.anyObject(Location.class))).andReturn(false).anyTimes();
		player1.setColor(Color.WHITE);
		EasyMock.expectLastCall();
		player2.setColor(Color.BLACK);
		EasyMock.expectLastCall();
		board.initBoard();
		EasyMock.expectLastCall();
//		board.movePiece(EasyMock.anyObject(Location.class),
//				EasyMock.anyObject(Location.class));
//		EasyMock.expectLastCall().anyTimes();
//		board.setPiece(EasyMock.anyObject(Location.class),
//				EasyMock.anyObject(Piece.class));
//		EasyMock.expectLastCall().anyTimes();
		EasyMock.replay(board, player1, player2, piece, game);
		game.startNewGame(player1, player2);
//		game.switchTurn();
//		game.switchTurn();
		Boolean result=game.isStalemate(Color.WHITE);
		assertTrue(result);
		EasyMock.verify(board, player1, player2, piece,game);
	}
	@Test
	public void isStalemate_false() {
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
//				.addMockedMethod("isCheckmate", Color.class)
//				.addMockedMethod("isStalemate", Color.class)
				.createMock();
//		Location first=new Location(0,0);
		Location from = new Location(7, 7);
		Location to = new Location(7, 7);
		EasyMock.expect(game.isInCheck(Color.WHITE)).andReturn(false).anyTimes();
//				.andReturn(false).andReturn(true).anyTimes();
		EasyMock.expect(board.getPiece(EasyMock.anyObject(Location.class))).andReturn(piece).anyTimes();
//		EasyMock.expect(board.getPiece(first)).andReturn(null).anyTimes();
//		EasyMock.expect(piece.getType()).andReturn(PieceType.PAWN);
		EasyMock.expect(piece.getColor()).andReturn(Color.WHITE).anyTimes();
//		EasyMock.expect(board.findKing(Color.WHITE)).andReturn(to);
		EasyMock.expect(player1.getName()).andReturn("p1").anyTimes();
		EasyMock.expect(player2.getName()).andReturn("p2").anyTimes();
		EasyMock.expect(player1.getColor()).andReturn(Color.WHITE).anyTimes();
		EasyMock.expect(player2.getColor()).andReturn(Color.BLACK).anyTimes();
		EasyMock.expect(piece.canMove
				(EasyMock.anyObject(Board.class),
						EasyMock.anyObject(Location.class),
						EasyMock.anyObject(Location.class))).andReturn(true).anyTimes();
		player1.setColor(Color.WHITE);
		EasyMock.expectLastCall();
		player2.setColor(Color.BLACK);
		EasyMock.expectLastCall();
		board.initBoard();
		EasyMock.expectLastCall();
		board.movePiece(EasyMock.anyObject(Location.class),
				EasyMock.anyObject(Location.class));
		EasyMock.expectLastCall().anyTimes();
		board.setPiece(EasyMock.anyObject(Location.class),
				EasyMock.anyObject(Piece.class));
		EasyMock.expectLastCall().anyTimes();
		EasyMock.replay(board, player1, player2, piece, game);
		game.startNewGame(player1, player2);
//		game.switchTurn();
//		game.switchTurn();
		Boolean result=game.isStalemate(Color.WHITE);
		assertFalse(result);
		EasyMock.verify(board, player1, player2, piece,game);
	}
}
