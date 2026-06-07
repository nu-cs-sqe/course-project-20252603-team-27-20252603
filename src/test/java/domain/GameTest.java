package domain;

import org.junit.jupiter.api.Test;

import java.util.*;

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
		player1.setColor(PieceColor.WHITE);
		EasyMock.expectLastCall();
		EasyMock.replay(player1);
		player2.setColor(PieceColor.BLACK);
		EasyMock.expectLastCall();
		EasyMock.replay(player2);
		game.startNewGame(player1, player2);
		EasyMock.verify(player1, player2, board);
	}
	@Test
	public void startNewGame_sameInput() {
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
//		player1.setColor(PieceColor.WHITE);
//		EasyMock.expectLastCall();
		EasyMock.replay(player1);
//		player2.setColor(PieceColor.BLACK);
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
		Location source=new Location(7,0);
		Location destination=new Location(0,7);
		Board board = EasyMock.createMock(Board.class);
		Piece rook=EasyMock.createMock(Piece.class);
//		EasyMock.expect(rook.getPieceType()).
//				andReturn(PieceType.ROOK).anyTimes();
		EasyMock.expect(player1.getName()).andStubReturn("p1");
		EasyMock.expect(player2.getName()).andStubReturn("p2");
		board.initBoard();
		EasyMock.expectLastCall();
		board.movePiece(source,destination);
		EasyMock.expectLastCall();
		EasyMock.expect(board.getPiece(destination)).andStubReturn(null);
//		EasyMock.expect(board.findKing(PieceColor.BLACK)).andStubReturn(null);
		EasyMock.expect(board.getPiece(source)).andStubReturn(rook);
		EasyMock.expect(board.isInsideBoard(source)).andReturn(TRUE).anyTimes();
		EasyMock.expect(board.isInsideBoard(destination)).andReturn(TRUE).anyTimes();
		EasyMock.expect(board.toPositionString()).andReturn("last").anyTimes();
		EasyMock.replay(board);
		EasyMock.expect(rook.canMove(board,source,destination)).andStubReturn(TRUE);
		EasyMock.expect(rook.getPieceType()).andStubReturn(PieceType.ROOK);
		EasyMock.expect(rook.getColor()).andStubReturn(PieceColor.WHITE);
//		EasyMock.replay(rook);
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
				.addMockedMethod("isInCheck", PieceColor.class)
				.addMockedMethod("isCheckmate", PieceColor.class)
				.addMockedMethod("isStalemate", PieceColor.class)
//				.addMockedMethod("createNotation")
				.createMock();
		EasyMock.expect(game.isInCheck(PieceColor.WHITE)).andReturn(false);
		EasyMock.expect(game.isInCheck(PieceColor.BLACK)).andReturn(false);
		EasyMock.expect(game.isCheckmate(PieceColor.BLACK)).andReturn(false);
		EasyMock.expect(game.isStalemate(PieceColor.BLACK)).andReturn(false);
		player1.setColor(PieceColor.WHITE);
		EasyMock.expectLastCall();
		EasyMock.expect(player1.getColor()).andStubReturn(PieceColor.WHITE);
		EasyMock.replay(player1);
		player2.setColor(PieceColor.BLACK);
		EasyMock.expectLastCall();
		rook.setMoved(true);
		EasyMock.expectLastCall();
		EasyMock.expect(player2.getColor()).andStubReturn(PieceColor.BLACK);
		EasyMock.replay(player2,game, rook);
		game.startNewGame(player1, player2);
		MoveResult result=game.makeMove(source,destination,PieceType.KNIGHT);
		Move last=new Move(source,destination, rook, null, PieceType.KNIGHT);
		target.put("lastp1",1);
		assertEquals(GameStatus.BLACK_TURN,game.getStatus());
		assertTrue(target.equals(game.positionHistory));
		assertTrue(last.equals(game.lastMove));
		moveHistory.add(last);
		assertTrue(moveHistory.equals(game.moveHistory));
		assertEquals(game.halfMoveClock,1);
		assertEquals(game.currentPlayer.getColor(),PieceColor.BLACK);
		assertEquals(result,MoveResult.VALID);
		EasyMock.verify(player1, player2, board,rook,game);
	}

	@Test
	public void makeMove_InCheck_white() {
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
//		EasyMock.expect(board.findKing(PieceColor.BLACK)).andStubReturn(king);
//		EasyMock.expect(board.getPiece(source)).andStubReturn(rook);
//		EasyMock.replay(board);
//		EasyMock.expect(rook.canMove(board,source,destination)).andStubReturn(TRUE);
//		EasyMock.expect(rook.getType()).andStubReturn(PieceType.ROOK);
////		EasyMock.expect(king1.getType()).andStubReturn(PieceType.PAWN);
////		EasyMock.expect(pawn.getColor()).andStubReturn(PieceColor.WHITE);
////		EasyMock.replay(pawn);
//		EasyMock.expect(rook.getColor()).andStubReturn(PieceColor.WHITE);
//		EasyMock.replay(rook);
//		GameStatus status = GameStatus.WHITE_TURN;
//		List<Move> moveHistory = new ArrayList<>();
//		Move lastMove = null;
//		int halfMoveClock = 0;
//		Game game = new Game(board, status, moveHistory, lastMove, halfMoveClock);
//		player1.setColor(PieceColor.WHITE);
//		EasyMock.expect(player1.getColor()).andStubReturn(PieceColor.WHITE);
//		EasyMock.expectLastCall();
//		EasyMock.replay(player1);
//		player2.setColor(PieceColor.BLACK);
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
				.addMockedMethod("isInCheck", PieceColor.class)
				.addMockedMethod("isCheckmate", PieceColor.class)
				.addMockedMethod("createNotation")
				.createMock();
		Location from = new Location(7, 0);
		Location to = new Location(0, 0);
		EasyMock.expect(board.isInsideBoard(from)).andReturn(TRUE).anyTimes();
		EasyMock.expect(board.isInsideBoard(to)).andReturn(TRUE).anyTimes();
		EasyMock.expect(board.toPositionString()).andReturn("last").anyTimes();
		EasyMock.expect(player1.getName()).andReturn("p1").anyTimes();
		EasyMock.expect(player2.getName()).andReturn("p2").anyTimes();
		player1.setColor(PieceColor.WHITE);
		EasyMock.expectLastCall();
		player2.setColor(PieceColor.BLACK);
		EasyMock.expectLastCall();
		EasyMock.expect(player1.getColor()).andReturn(PieceColor.WHITE).anyTimes();
		EasyMock.expect(player2.getColor()).andReturn(PieceColor.BLACK).anyTimes();
		board.initBoard();
		EasyMock.expectLastCall();
		piece.setMoved(true);
		EasyMock.expectLastCall();
		EasyMock.expect(board.getPiece(from)).andReturn(piece);
		EasyMock.expect(board.getPiece(to)).andReturn(null);
		EasyMock.expect(piece.getPieceType()).andReturn(PieceType.ROOK).anyTimes();
		EasyMock.expect(piece.getColor()).andReturn(PieceColor.WHITE).anyTimes();
		EasyMock.expect(piece.canMove(board, from, to)).andReturn(true);
		board.movePiece(from, to);
		EasyMock.expectLastCall();
		EasyMock.expect(game.isInCheck(PieceColor.WHITE)).andReturn(false);
//		EasyMock.expect(game.isCheckmate(PieceColor.WHITE)).andReturn(false);
		EasyMock.expect(game.isCheckmate(PieceColor.BLACK)).andReturn(false);
		EasyMock.expect(game.isInCheck(PieceColor.BLACK)).andReturn(true);
		EasyMock.expect(game.createNotation
				(from, to, piece,null,
						null,false,false))
				.andReturn("notation");
		EasyMock.replay(board, player1, player2, piece, game);
		game.startNewGame(player1, player2);
		MoveResult result = game.makeMove(from, to, null);
		Move last=new Move(from,to, piece, null, null, false, false, "notation");
		Map<String, Integer> target = new HashMap<>();
		target.put("lastp1", 1);
		assertTrue(target.equals(game.positionHistory));
		assertTrue(last.equals(game.lastMove));
		moveHistory.add(last);
		assertTrue(moveHistory.equals(game.moveHistory));
		assertEquals(game.currentPlayer.getColor(),PieceColor.BLACK);
		assertEquals(game.halfMoveClock,1);
		assertEquals(MoveResult.CHECK, result);
		EasyMock.verify(board, player1, player2, piece, game);
	}
	@Test
	public void makeMove_Checkmate_black(){
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
				.addMockedMethod("isInCheck", PieceColor.class)
				.addMockedMethod("isCheckmate", PieceColor.class)
				.addMockedMethod("createNotation")
				.createMock();
		Location from = new Location(0, 0);
		Location to = new Location(7, 0);
		EasyMock.expect(board.isInsideBoard(from)).andReturn(TRUE).anyTimes();
		EasyMock.expect(board.isInsideBoard(to)).andReturn(TRUE).anyTimes();
		EasyMock.expect(board.toPositionString()).andReturn("last").anyTimes();
		EasyMock.expect(player1.getName()).andReturn("p1").anyTimes();
		EasyMock.expect(player2.getName()).andReturn("p2").anyTimes();
		player1.setColor(PieceColor.WHITE);
		EasyMock.expectLastCall();
		player2.setColor(PieceColor.BLACK);
		EasyMock.expectLastCall();
		EasyMock.expect(player1.getColor()).andReturn(PieceColor.WHITE).anyTimes();
		EasyMock.expect(player2.getColor()).andReturn(PieceColor.BLACK).anyTimes();
		board.initBoard();
		EasyMock.expectLastCall();
		piece.setMoved(true);
		EasyMock.expectLastCall();
		EasyMock.expect(board.getPiece(from)).andReturn(piece);
		EasyMock.expect(board.getPiece(to)).andReturn(null);
		EasyMock.expect(piece.getPieceType()).andReturn(PieceType.KNIGHT).anyTimes();
		EasyMock.expect(piece.getColor()).andReturn(PieceColor.BLACK).anyTimes();
		EasyMock.expect(piece.canMove(board, from, to)).andReturn(true);
		board.movePiece(from, to);
		EasyMock.expectLastCall();
		EasyMock.expect(game.isInCheck(PieceColor.BLACK)).andStubReturn(false);
//		EasyMock.expect(game.isInCheck(PieceColor.WHITE)).andReturn(true);
		EasyMock.expect(game.isCheckmate(PieceColor.WHITE)).andReturn(true);
		EasyMock.expect(game.createNotation(from, to, piece, null,
				null, false, false))
				.andReturn("notation");
		EasyMock.replay(board, player1, player2, piece, game);
		game.startNewGame(player1, player2);
		game.switchTurn();
		MoveResult result = game.makeMove(from, to, null);
		Move last=new Move(from,to, piece, null,
				null,false,false,"notation");
		Map<String, Integer> target = new HashMap<>();
		target.put("first",2);
		target.put("lastp2", 1);
		assertTrue(target.equals(game.positionHistory));
		assertTrue(last.equals(game.lastMove));
		moveHistory.add(last);
		assertTrue(moveHistory.equals(game.moveHistory));
		assertEquals(game.halfMoveClock,1);
		assertEquals(MoveResult.CHECKMATE, result);
		assertEquals(game.currentPlayer.getColor(),PieceColor.WHITE);
		EasyMock.verify(board, player1, player2, piece, game);
	}
	@Test
	public void makeMove_Stalemate_white(){
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
						GameStatus.BLACK_IN_CHECK,
						moveHistory,
						null,
						halfMoveClock,
						positionHistory)
				.addMockedMethod("isInCheck", PieceColor.class)
				.addMockedMethod("isCheckmate", PieceColor.class)
				.addMockedMethod("isStalemate", PieceColor.class)
				.addMockedMethod("createNotation")
				.createMock();
		Location from = new Location(0, 0);
		Location to = new Location(7, 7);
		EasyMock.expect(board.isInsideBoard(from)).andReturn(TRUE).anyTimes();
		EasyMock.expect(board.isInsideBoard(to)).andReturn(TRUE).anyTimes();
		EasyMock.expect(board.toPositionString()).andReturn("last").anyTimes();
		EasyMock.expect(player1.getName()).andReturn("p1").anyTimes();
		EasyMock.expect(player2.getName()).andReturn("p2").anyTimes();
		player1.setColor(PieceColor.WHITE);
		EasyMock.expectLastCall();
		player2.setColor(PieceColor.BLACK);
		EasyMock.expectLastCall();
		piece.setMoved(true);
		EasyMock.expectLastCall();
		EasyMock.expect(player1.getColor()).andReturn(PieceColor.WHITE).anyTimes();
		EasyMock.expect(player2.getColor()).andReturn(PieceColor.BLACK).anyTimes();
		board.initBoard();
		EasyMock.expectLastCall();
		EasyMock.expect(board.getPiece(from)).andReturn(piece);
		EasyMock.expect(board.getPiece(to)).andReturn(null);
		EasyMock.expect(piece.getPieceType()).andReturn(PieceType.KNIGHT).anyTimes();
		EasyMock.expect(piece.getColor()).andReturn(PieceColor.WHITE).anyTimes();
		EasyMock.expect(piece.canMove(board, from, to)).andReturn(true);
		board.movePiece(from, to);
		EasyMock.expectLastCall();
		EasyMock.expect(game.isInCheck(PieceColor.WHITE)).andStubReturn(false);
		EasyMock.expect(game.isInCheck(PieceColor.BLACK)).andReturn(false);
		EasyMock.expect(game.isCheckmate(PieceColor.BLACK)).andReturn(false);
		EasyMock.expect(game.isStalemate(PieceColor.BLACK)).andReturn(true);
		EasyMock.expect(game.createNotation(from, to, piece, null,
						null, false, false))
				.andReturn("notation");
		EasyMock.replay(board, player1, player2, piece, game);
		game.startNewGame(player1, player2);
//		game.switchTurn();
		MoveResult result = game.makeMove(from, to, null);
		Move last=new Move(from,to,piece,
				null,null, false,
				false, "notation");
		Map<String, Integer> target = new HashMap<>();
		target.put("lastp1", 1);
		assertTrue(target.equals(game.positionHistory));
		assertTrue(last.equals(game.lastMove));
		moveHistory.add(last);
		assertTrue(moveHistory.equals(game.moveHistory));
		assertEquals(game.halfMoveClock,2);
		assertEquals(MoveResult.STALEMATE, result);
		assertEquals(game.currentPlayer.getColor(),PieceColor.BLACK);
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
		player1.setColor(PieceColor.WHITE);
		EasyMock.expectLastCall();
		player2.setColor(PieceColor.BLACK);
		EasyMock.expectLastCall();
		EasyMock.expect(player1.getColor()).andReturn(PieceColor.WHITE).anyTimes();
		EasyMock.expect(player2.getColor()).andReturn(PieceColor.BLACK).anyTimes();
		board.initBoard();
		EasyMock.expectLastCall();
		EasyMock.expect(board.getPiece(from)).andReturn(piece);
		EasyMock.expect(board.getPiece(to)).andReturn(piece);
//		EasyMock.expect(piece.getType()).andReturn(PieceType.KNIGHT);
		EasyMock.expect(piece.getColor()).andReturn(PieceColor.WHITE).anyTimes();
//		EasyMock.expect(piece.canMove(board, from, to)).andReturn(true);
//		board.movePiece(from, to);
//		EasyMock.expectLastCall();
//		EasyMock.expect(game.isInCheck(PieceColor.BLACK)).andReturn(false);
//		EasyMock.expect(game.isCheckmate(PieceColor.BLACK)).andReturn(false);
//		EasyMock.expect(game.isStalemate(PieceColor.BLACK)).andReturn(true);
		EasyMock.replay(board, player1, player2, piece);
		game.startNewGame(player1, player2);
//		game.switchTurn();
		MoveResult result = game.makeMove(from, to, null);
		assertEquals(null,game.lastMove);
		assertTrue(moveHistory.equals(game.moveHistory));
		assertEquals(game.halfMoveClock, halfMoveClock);
		assertTrue(positionHistory.equals(game.positionHistory));
		assertEquals(MoveResult.INVALID_SAME_COLOR_CAPTURE, result);
		assertEquals(game.currentPlayer.getColor(),PieceColor.WHITE);
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
		player1.setColor(PieceColor.WHITE);
		EasyMock.expectLastCall();
		player2.setColor(PieceColor.BLACK);
		EasyMock.expectLastCall();
		EasyMock.expect(player1.getColor()).andReturn(PieceColor.WHITE).anyTimes();
		EasyMock.expect(player2.getColor()).andReturn(PieceColor.BLACK).anyTimes();
		board.initBoard();
		EasyMock.expectLastCall();
		EasyMock.expect(board.getPiece(from)).andReturn(null);
//		EasyMock.expect(board.getPiece(to)).andReturn(piece);
//		EasyMock.expect(piece.getType()).andReturn(PieceType.KNIGHT);
//		EasyMock.expect(piece.getColor()).andReturn(PieceColor.WHITE).anyTimes();
//		EasyMock.expect(piece.canMove(board, from, to)).andReturn(true);
//		board.movePiece(from, to);
//		EasyMock.expectLastCall();
//		EasyMock.expect(game.isInCheck(PieceColor.BLACK)).andReturn(false);
//		EasyMock.expect(game.isCheckmate(PieceColor.BLACK)).andReturn(false);
//		EasyMock.expect(game.isStalemate(PieceColor.BLACK)).andReturn(true);
		EasyMock.replay(board, player1, player2);
		game.startNewGame(player1, player2);
//		game.switchTurn();
		MoveResult result = game.makeMove(from, to, PieceType.PAWN);
		assertEquals(null,game.lastMove);
		assertTrue(moveHistory.equals(game.moveHistory));
		assertEquals(game.halfMoveClock, halfMoveClock);
		assertTrue(positionHistory.equals(game.positionHistory));
		assertEquals(game.currentPlayer.getColor(),PieceColor.WHITE);
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
		player1.setColor(PieceColor.WHITE);
		EasyMock.expectLastCall();
		player2.setColor(PieceColor.BLACK);
		EasyMock.expectLastCall();
		EasyMock.expect(player1.getColor()).andReturn(PieceColor.WHITE).anyTimes();
		EasyMock.expect(player2.getColor()).andReturn(PieceColor.BLACK).anyTimes();
		board.initBoard();
		EasyMock.expectLastCall();
		EasyMock.expect(board.getPiece(from)).andReturn(piece);
		EasyMock.expect(board.getPiece(to)).andReturn(null);
//		EasyMock.expect(piece.getType()).andReturn(PieceType.BISHOP);
		EasyMock.expect(piece.getColor()).andReturn(PieceColor.BLACK).anyTimes();
//		EasyMock.expect(piece.canMove(board, from, to)).andReturn(true);
//		board.movePiece(from, to);
//		EasyMock.expectLastCall();
//		EasyMock.expect(game.isInCheck(PieceColor.BLACK)).andReturn(false);
//		EasyMock.expect(game.isCheckmate(PieceColor.BLACK)).andReturn(false);
//		EasyMock.expect(game.isStalemate(PieceColor.BLACK)).andReturn(true);
		EasyMock.replay(board, player1, player2, piece);
		game.startNewGame(player1, player2);
//		game.switchTurn();
		MoveResult result = game.makeMove(from, to, PieceType.PAWN);
		assertEquals(null,game.lastMove);
		assertTrue(moveHistory.equals(game.moveHistory));
		assertEquals(game.halfMoveClock, halfMoveClock);
		assertTrue(positionHistory.equals(game.positionHistory));
		assertEquals(MoveResult.INVALID_WRONG_TURN, result);
		assertEquals(game.currentPlayer.getColor(),PieceColor.WHITE);
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
				.addMockedMethod("isInCheck", PieceColor.class)
				.addMockedMethod("isCheckmate", PieceColor.class)
				.addMockedMethod("isStalemate", PieceColor.class)
				.addMockedMethod("createNotation")
				.createMock();
		Location from = new Location(7, 7);
		Location to = new Location(0, 7);
		EasyMock.expect(game.createNotation(from, to, piece, piece1,
						null, false, false))
				.andReturn("notation");
		Move last=new Move(from,to, piece, piece1, null, false, false, "notation");
//		EasyMock.expect(last.getNotation).andReturn("last").anyTimes();
		EasyMock.expect(board.isInsideBoard(from)).andReturn(TRUE).anyTimes();
		EasyMock.expect(board.isInsideBoard(to)).andReturn(TRUE).anyTimes();
		EasyMock.expect(board.toPositionString()).andReturn("last").anyTimes();
		EasyMock.expect(player1.getName()).andReturn("p1").anyTimes();
		EasyMock.expect(player2.getName()).andReturn("p2").anyTimes();
		player1.setColor(PieceColor.WHITE);
		EasyMock.expectLastCall();
		player2.setColor(PieceColor.BLACK);
		EasyMock.expectLastCall();
		EasyMock.expect(player1.getColor()).andReturn(PieceColor.WHITE).anyTimes();
		EasyMock.expect(player2.getColor()).andReturn(PieceColor.BLACK).anyTimes();
		board.initBoard();
		EasyMock.expectLastCall();
		EasyMock.expect(board.getPiece(from)).andReturn(piece);
		EasyMock.expect(board.getPiece(to)).andReturn(piece1);
		EasyMock.expect(piece.getPieceType()).andReturn(PieceType.KNIGHT).anyTimes();
		EasyMock.expect(piece.getColor()).andReturn(PieceColor.WHITE).anyTimes();
//		EasyMock.expect(piece1.getType()).andReturn(PieceType.PAWN);
		EasyMock.expect(piece1.getColor()).andReturn(PieceColor.BLACK).anyTimes();
		EasyMock.expect(piece.canMove(board, from, to)).andReturn(true);
		board.movePiece(from, to);
		EasyMock.expectLastCall();
		piece.setMoved(true);
		EasyMock.expectLastCall();
		EasyMock.expect(game.isInCheck(PieceColor.WHITE)).andReturn(false);
		EasyMock.expect(game.isInCheck(PieceColor.BLACK)).andReturn(false);
		EasyMock.expect(game.isCheckmate(PieceColor.BLACK)).andReturn(false);
		EasyMock.expect(game.isStalemate(PieceColor.BLACK)).andReturn(false);
		EasyMock.replay(board, player1, player2, piece, piece1,game);
		game.startNewGame(player1, player2);
//		game.switchTurn();
		MoveResult result = game.makeMove(from, to, null);
		assertEquals(game.halfMoveClock,1);
		target.put("lastp1",1);
		assertTrue(target.equals(game.positionHistory));
		moveHistory.add(last);
		assertTrue(moveHistory.equals(game.moveHistory));
//		assertEquals(moveHistory,Integer.MAX_VALUE);
		assertTrue(last.equals(game.lastMove));
		assertEquals(MoveResult.VALID, result);
		assertEquals(game.currentPlayer.getColor(),PieceColor.BLACK);
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
				.addMockedMethod("isInCheck", PieceColor.class)
				.addMockedMethod("isCheckmate", PieceColor.class)
				.addMockedMethod("isStalemate", PieceColor.class)
				.createMock();
		Location from = new Location(0, -1);
		Location to = new Location(0, 0);
//		EasyMock.expect(game.createNotation(from, to, piece, null,
//						null, false, false))
//				.andReturn("notation");
//		Move last=new Move(from,to);
//		EasyMock.expect(last.getNotation).andReturn("last").anyTimes();
//		piece.setMoved(true);
//		EasyMock.expectLastCall();
		EasyMock.expect(board.toPositionString()).andReturn("last").anyTimes();
		EasyMock.expect(board.isInsideBoard(from)).andReturn(FALSE).anyTimes();
		EasyMock.expect(player1.getName()).andReturn("p1").anyTimes();
		EasyMock.expect(player2.getName()).andReturn("p2").anyTimes();
		player1.setColor(PieceColor.WHITE);
		EasyMock.expectLastCall();
		player2.setColor(PieceColor.BLACK);
		EasyMock.expectLastCall();
		EasyMock.expect(player1.getColor()).andReturn(PieceColor.WHITE).anyTimes();
		EasyMock.expect(player2.getColor()).andReturn(PieceColor.BLACK).anyTimes();
		board.initBoard();
		EasyMock.expectLastCall();
//		EasyMock.expect(board.getPiece(from)).andReturn(piece);
//		EasyMock.expect(board.getPiece(to)).andReturn(piece1);
//		EasyMock.expect(piece.getType()).andReturn(PieceType.KNIGHT);
//		EasyMock.expect(piece.getColor()).andReturn(PieceColor.WHITE).anyTimes();
////		EasyMock.expect(piece1.getType()).andReturn(PieceType.PAWN);
//		EasyMock.expect(piece1.getColor()).andReturn(PieceColor.BLACK).anyTimes();
//		EasyMock.expect(piece.canMove(board, from, to)).andReturn(true);
//		board.movePiece(from, to);
//		EasyMock.expectLastCall();
//		EasyMock.expect(game.isInCheck(PieceColor.BLACK)).andReturn(false);
//		EasyMock.expect(game.isCheckmate(PieceColor.BLACK)).andReturn(false);
//		EasyMock.expect(game.isStalemate(PieceColor.BLACK)).andReturn(false);
		EasyMock.replay(board, player1, player2, piece, piece1,game);
		game.startNewGame(player1, player2);
		game.switchTurn();
		MoveResult result = game.makeMove(from, to, null);
		assertEquals(game.halfMoveClock,Integer.MAX_VALUE-1);
//		target.put("last",1);
		assertTrue(target.equals(game.positionHistory));
//		moveHistory.add(last);
		assertTrue(moveHistory.equals(game.moveHistory));
		assertEquals(game.currentPlayer.getColor(),PieceColor.BLACK);
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
				.addMockedMethod("isInCheck", PieceColor.class)
				.addMockedMethod("isCheckmate", PieceColor.class)
				.addMockedMethod("isStalemate", PieceColor.class)
				.createMock();
		Location from = new Location(0, -1);
		Location to = new Location(0, 0);
//		Move last=new Move(from,to);
//		EasyMock.expect(last.getNotation).andReturn("last").anyTimes();
		EasyMock.expect(board.toPositionString()).andReturn("last").anyTimes();
		EasyMock.expect(board.isInsideBoard(from)).andReturn(TRUE).anyTimes();
		EasyMock.expect(board.isInsideBoard(to)).andReturn(FALSE).anyTimes();
		EasyMock.expect(player1.getName()).andReturn("p1").anyTimes();
		EasyMock.expect(player2.getName()).andReturn("p2").anyTimes();
		player1.setColor(PieceColor.WHITE);
		EasyMock.expectLastCall();
		player2.setColor(PieceColor.BLACK);
		EasyMock.expectLastCall();
		EasyMock.expect(player1.getColor()).andReturn(PieceColor.WHITE).anyTimes();
		EasyMock.expect(player2.getColor()).andReturn(PieceColor.BLACK).anyTimes();
		board.initBoard();
		EasyMock.expectLastCall();
		EasyMock.replay(board, player1, player2, piece, piece1,game);
		game.startNewGame(player1, player2);
//		game.switchTurn();
		MoveResult result = game.makeMove(from, to, null);
		assertEquals(game.currentPlayer.getColor(),PieceColor.WHITE);
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
//		Move last=new Move(from,to);
//		EasyMock.expect(last.getNotation).andReturn("last").anyTimes();
		EasyMock.expect(board.toPositionString()).andReturn("last").anyTimes();
		EasyMock.expect(board.isInsideBoard(from)).andReturn(TRUE).anyTimes();
		EasyMock.expect(board.isInsideBoard(to)).andReturn(TRUE).anyTimes();
		EasyMock.expect(board.getPiece(from)).andReturn(piece);
		EasyMock.expect(board.getPiece(to)).andReturn(piece1);
//		EasyMock.expect(piece.getType()).andReturn(PieceType.KNIGHT);
		EasyMock.expect(piece.getColor()).andReturn(PieceColor.BLACK).anyTimes();
//		EasyMock.expect(piece1.getType()).andReturn(PieceType.PAWN);
		EasyMock.expect(piece1.getColor()).andReturn(PieceColor.WHITE).anyTimes();
		EasyMock.expect(piece.canMove(board, from, to)).andReturn(false).anyTimes();
//		board.movePiece(from, to);
//		EasyMock.expectLastCall();
//		EasyMock.expect(game.isInCheck(PieceColor.BLACK)).andReturn(false);
//		EasyMock.expect(game.isCheckmate(PieceColor.BLACK)).andReturn(false);
//		EasyMock.expect(game.isStalemate(PieceColor.BLACK)).andReturn(false);
		EasyMock.expect(player1.getName()).andReturn("p1").anyTimes();
		EasyMock.expect(player2.getName()).andReturn("p2").anyTimes();
		player1.setColor(PieceColor.WHITE);
		EasyMock.expectLastCall();
		player2.setColor(PieceColor.BLACK);
		EasyMock.expectLastCall();
		EasyMock.expect(player1.getColor()).andReturn(PieceColor.WHITE).anyTimes();
		EasyMock.expect(player2.getColor()).andReturn(PieceColor.BLACK).anyTimes();
		board.initBoard();
		EasyMock.expectLastCall();
		EasyMock.replay(board, player1, player2, piece, piece1);
		game.startNewGame(player1, player2);
		game.switchTurn();
		MoveResult result = game.makeMove(from, to, null);
		assertEquals(game.currentPlayer.getColor(),PieceColor.BLACK);
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
				.addMockedMethod("isInCheck", PieceColor.class)
				.addMockedMethod("createNotation")
				.createMock();
		Location from = new Location(7, 1);
		Location to = new Location(1, 0);
		EasyMock.expect(game.createNotation(from, to, piece, null,
						null, false, false))
				.andReturn("notation");
		Move last=new Move(from, to, piece, null,
				null, false, false, "notation");
//		EasyMock.expect(last.getNotation).andReturn("last").anyTimes();
		EasyMock.expect(board.toPositionString()).andReturn("last").anyTimes();
		EasyMock.expect(board.isInsideBoard(from)).andReturn(TRUE).anyTimes();
		EasyMock.expect(board.isInsideBoard(to)).andReturn(TRUE).anyTimes();
		EasyMock.expect(board.getPiece(from)).andReturn(piece);
		EasyMock.expect(board.getPiece(to)).andReturn(null);
		EasyMock.expect(piece.getPieceType()).andReturn(PieceType.QUEEN).anyTimes();
		EasyMock.expect(piece.getColor()).andReturn(PieceColor.WHITE).anyTimes();
//		EasyMock.expect(piece1.getType()).andReturn(PieceType.PAWN);
		EasyMock.expect(piece1.getColor()).andReturn(PieceColor.BLACK).anyTimes();
		EasyMock.expect(piece.canMove(board, from, to)).andReturn(true).anyTimes();
		board.movePiece(from, to);
		EasyMock.expectLastCall();
		piece.setMoved(true);
		EasyMock.expectLastCall();
		EasyMock.expect(game.isInCheck(PieceColor.WHITE)).andReturn(false);
//		EasyMock.expect(game.isCheckmate(PieceColor.BLACK)).andReturn(false);
//		EasyMock.expect(game.isStalemate(PieceColor.BLACK)).andReturn(false);
		EasyMock.expect(player1.getName()).andReturn("p1").anyTimes();
		EasyMock.expect(player2.getName()).andReturn("p2").anyTimes();
		player1.setColor(PieceColor.WHITE);
		EasyMock.expectLastCall();
		player2.setColor(PieceColor.BLACK);
		EasyMock.expectLastCall();
		EasyMock.expect(player1.getColor()).andReturn(PieceColor.WHITE).anyTimes();
		EasyMock.expect(player2.getColor()).andReturn(PieceColor.BLACK).anyTimes();
		board.initBoard();
		EasyMock.expectLastCall();
		EasyMock.replay(board, player1, player2, piece, piece1, game);
		game.startNewGame(player1, player2);
//		game.switchTurn();
		MoveResult result = game.makeMove(from, to, null);
		assertEquals(game.currentPlayer.getColor(),PieceColor.WHITE);
		assertEquals(game.halfMoveClock,100);
		target.put("lastp1",3);
		assertTrue(target.equals(game.positionHistory));
		List<Move> history = new ArrayList<>();
		history.add(last);
		System.out.println(history);
		assertTrue(history.equals(game.moveHistory));
		assertEquals(game.lastMove,last);
		assertEquals(MoveResult.DRAW, result);
		EasyMock.verify(board, player1, player2, piece, piece1, game);
	}
	@Test
	public void makeMove_50Move_white(){
		Board board = EasyMock.createMock(Board.class);
		Player player1 = EasyMock.createMock(Player.class);
		Player player2 = EasyMock.createMock(Player.class);
		Piece piece = EasyMock.createMock(Piece.class);
		Piece piece1 = EasyMock.createMock(Piece.class);
		Location from = new Location(7, 1);
		Location to = new Location(1, 0);
		List<Move> moveHistory = new ArrayList<>();
		Move lastMove = new Move(from,to, piece, null,
				null, false,
				false, "notation");;
		Move last=new Move(from,to, piece, null,
				null, false,
				false, "notation");
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
						lastMove,
						halfMoveClock,
						positionHistory)
				.addMockedMethod("isInCheck", PieceColor.class)
				.createMock();
//		EasyMock.expect(game.createNotation(from, to, piece, null,
//						null, false, false))
//				.andReturn("notation");
//		Move last=new Move(from,to, piece, null,
//				null, false,
//				false, "notation");
//		EasyMock.expect(last.getNotation).andReturn("last").anyTimes();
		EasyMock.expect(board.toPositionString()).andReturn("last").anyTimes();
		EasyMock.expect(board.isInsideBoard(from)).andReturn(TRUE).anyTimes();
		EasyMock.expect(board.isInsideBoard(to)).andReturn(TRUE).anyTimes();
		EasyMock.expect(board.getPiece(from)).andReturn(piece);
		EasyMock.expect(board.getPiece(to)).andReturn(null);
		EasyMock.expect(piece.getPieceType()).andReturn(PieceType.QUEEN).anyTimes();
		EasyMock.expect(piece.getColor()).andReturn(PieceColor.WHITE).anyTimes();
//		EasyMock.expect(piece1.getType()).andReturn(PieceType.PAWN);
//		EasyMock.expect(piece1.getColor()).andReturn(PieceColor.BLACK).anyTimes();
		EasyMock.expect(piece.canMove(board, from, to)).andReturn(true).anyTimes();
//		board.movePiece(from, to);
//		EasyMock.expectLastCall();
		EasyMock.expect(game.isInCheck(PieceColor.WHITE)).andReturn(false);
//		EasyMock.expect(game.isCheckmate(PieceColor.BLACK)).andReturn(false);
//		EasyMock.expect(game.isStalemate(PieceColor.BLACK)).andReturn(false);
		EasyMock.expect(player1.getName()).andReturn("p1").anyTimes();
		EasyMock.expect(player2.getName()).andReturn("p2").anyTimes();
		player1.setColor(PieceColor.WHITE);
		EasyMock.expectLastCall();
		player2.setColor(PieceColor.BLACK);
		EasyMock.expectLastCall();
		EasyMock.expect(player1.getColor()).andReturn(PieceColor.WHITE).anyTimes();
		EasyMock.expect(player2.getColor()).andReturn(PieceColor.BLACK).anyTimes();
		board.initBoard();
		EasyMock.expectLastCall();
		EasyMock.replay(board, player1, player2, piece, piece1, game);
		game.startNewGame(player1, player2);
//		game.switchTurn();
		MoveResult result = game.makeMove(from, to, null);
		assertEquals(game.currentPlayer.getColor(),PieceColor.WHITE);
		assertEquals(game.halfMoveClock,101);
		target.put("lastp1",1);
		assertTrue(target.equals(game.positionHistory));
		List<Move> history = new ArrayList<>();
//		history.add(last);
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
						GameStatus.WHITE_IN_CHECK,
						moveHistory,
						null,
						halfMoveClock,
						positionHistory)
				.addMockedMethod("isInCheck", PieceColor.class)
//				.addMockedMethod("isCheckmate", PieceColor.class)
//				.addMockedMethod("isStalemate", PieceColor.class)
				.createMock();
		Location from = new Location(0, -1);
		Location to = new Location(0, 0);
//		EasyMock.expect(game.createNotation(from, to, piece, null,
//						null, false, false))
//				.andReturn("notation");
//		Move last=new Move(from,to, piece, null,
//				null, false, false, "notation");
//		EasyMock.expect(last.getNotation).andReturn("last").anyTimes();
		EasyMock.expect(board.toPositionString()).andReturn("last").anyTimes();
		EasyMock.expect(board.isInsideBoard(from)).andReturn(TRUE).anyTimes();
		EasyMock.expect(board.isInsideBoard(to)).andReturn(TRUE).anyTimes();
		EasyMock.expect(board.getPiece(from)).andReturn(piece).anyTimes();
		EasyMock.expect(board.getPiece(to)).andStubReturn(null);
		EasyMock.expect(piece.canMove(board,from,to)).andReturn(true);
		EasyMock.expect(player1.getName()).andReturn("p1").anyTimes();
		EasyMock.expect(player2.getName()).andReturn("p2").anyTimes();
		player1.setColor(PieceColor.WHITE);
		EasyMock.expectLastCall();
		player2.setColor(PieceColor.BLACK);
		EasyMock.expectLastCall();
//		EasyMock.expect(game.isInCheck(PieceColor.WHITE)).andReturn(true).anyTimes();
		EasyMock.expect(player1.getColor()).andReturn(PieceColor.WHITE).anyTimes();
		EasyMock.expect(player2.getColor()).andReturn(PieceColor.BLACK).anyTimes();
//		EasyMock.expect(piece.getType()).andReturn(PieceType.QUEEN);
		EasyMock.expect(piece.getColor()).andReturn(PieceColor.WHITE).anyTimes();
		board.initBoard();
		EasyMock.expectLastCall();
		EasyMock.expect(game.isInCheck(PieceColor.WHITE)).andReturn(true);
		EasyMock.replay(board, player1, player2, piece, piece1,game);
		game.startNewGame(player1, player2);
//		game.switchTurn();
		MoveResult result = game.makeMove(from, to, null);
		assertEquals(game.currentPlayer.getColor(),PieceColor.WHITE);
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
		Piece bishop=new Piece(PieceType.BISHOP,PieceColor.WHITE);
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
				.addMockedMethod("isInCheck", PieceColor.class)
				.addMockedMethod("isCheckmate", PieceColor.class)
				.addMockedMethod("isStalemate", PieceColor.class)
				.addMockedMethod("createNotation")
				.createMock();
		Location from = new Location(7, 0);
		Location to = new Location(0, 0);
		EasyMock.expect(board.isInsideBoard(from)).andReturn(TRUE).anyTimes();
		EasyMock.expect(board.isInsideBoard(to)).andReturn(TRUE).anyTimes();
		EasyMock.expect(board.toPositionString()).andReturn("last").anyTimes();
		EasyMock.expect(player1.getName()).andReturn("p1").anyTimes();
		EasyMock.expect(player2.getName()).andReturn("p2").anyTimes();
		player1.setColor(PieceColor.WHITE);
		EasyMock.expectLastCall();
		player2.setColor(PieceColor.BLACK);
		EasyMock.expectLastCall();
		EasyMock.expect(player1.getColor()).andReturn(PieceColor.WHITE).anyTimes();
		EasyMock.expect(player2.getColor()).andReturn(PieceColor.BLACK).anyTimes();
		board.initBoard();
		EasyMock.expectLastCall();
		EasyMock.expect(board.getPiece(from)).andReturn(piece);
		EasyMock.expect(board.getPiece(to)).andReturn(null);
		EasyMock.expect(piece.getPieceType()).andReturn(PieceType.PAWN).anyTimes();
		EasyMock.expect(piece.getColor()).andReturn(PieceColor.WHITE).anyTimes();
		EasyMock.expect(piece.canMove(board, from, to)).andReturn(true);
		board.movePiece(from, to);
		EasyMock.expectLastCall();
		board.setPiece(to,bishop);
		EasyMock.expectLastCall();
		piece.setMoved(true);
		EasyMock.expectLastCall();
		bishop.setMoved(true);
		EasyMock.expectLastCall();
		EasyMock.expect(game.isInCheck(PieceColor.WHITE)).andReturn(false);
		EasyMock.expect(game.isCheckmate(PieceColor.BLACK)).andReturn(false);
		EasyMock.expect(game.isInCheck(PieceColor.BLACK)).andReturn(true);
//		EasyMock.expect(game.isCheckmate(PieceColor.BLACK)).andReturn(false);
//		EasyMock.expect(game.isStalemate(PieceColor.BLACK)).andReturn(false);
		EasyMock.expect(game.createNotation(from, to, piece, null,
						PieceType.BISHOP, false, false))
				.andReturn("notation");
		EasyMock.replay(board, player1, player2, piece, game);
		game.startNewGame(player1, player2);
		MoveResult result = game.makeMove(from, to, PieceType.BISHOP);
		Move last=new Move(from,to, piece, null,
				PieceType.BISHOP, false, false, "notation");
		Map<String, Integer> target = new HashMap<>();
		target.put("lastp1", 1);
//		Bishop bishop=new Bishop(PieceColor.WHITE);
		assertTrue(target.equals(game.positionHistory));
		assertTrue(last.equals(game.lastMove));
		moveHistory.add(last);
		assertTrue(moveHistory.equals(game.moveHistory));
		assertEquals(game.currentPlayer.getColor(),PieceColor.BLACK);
		assertEquals(game.halfMoveClock,1);
		System.out.println(result);
		assertEquals(MoveResult.CHECK, result);
		EasyMock.verify(board, player1, player2, piece, game);
	}
	@Test
	public void makeMove_pawnPromoption_black() {
		Board board = EasyMock.createMock(Board.class);
		Player player1 = EasyMock.createMock(Player.class);
		Player player2 = EasyMock.createMock(Player.class);
		Piece piece = EasyMock.createMock(Piece.class);
		Piece bishop=new Piece(PieceType.BISHOP, PieceColor.BLACK);
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
				.addMockedMethod("isInCheck", PieceColor.class)
				.addMockedMethod("isCheckmate", PieceColor.class)
				.addMockedMethod("isStalemate", PieceColor.class)
				.addMockedMethod("createNotation")
				.createMock();
		Location from = new Location(0, 1);
		Location to = new Location(7, 1);
		EasyMock.expect(board.isInsideBoard(from)).andReturn(TRUE).anyTimes();
		EasyMock.expect(board.isInsideBoard(to)).andReturn(TRUE).anyTimes();
		EasyMock.expect(board.toPositionString()).andReturn("last").anyTimes();
		EasyMock.expect(player1.getName()).andReturn("p1").anyTimes();
		EasyMock.expect(player2.getName()).andReturn("p2").anyTimes();
		player1.setColor(PieceColor.WHITE);
		EasyMock.expectLastCall();
		player2.setColor(PieceColor.BLACK);
		EasyMock.expectLastCall();
		piece.setMoved(true);
		EasyMock.expectLastCall();
		EasyMock.expect(player1.getColor()).andReturn(PieceColor.WHITE).anyTimes();
		EasyMock.expect(player2.getColor()).andReturn(PieceColor.BLACK).anyTimes();
		board.initBoard();
		EasyMock.expectLastCall();
		EasyMock.expect(board.getPiece(from)).andReturn(piece);
		EasyMock.expect(board.getPiece(to)).andReturn(null);
		EasyMock.expect(piece.getPieceType()).andReturn(PieceType.PAWN).anyTimes();
		EasyMock.expect(piece.getColor()).andReturn(PieceColor.BLACK).anyTimes();
		EasyMock.expect(piece.canMove(board, from, to)).andReturn(true);
		board.movePiece(from, to);
		EasyMock.expectLastCall();
		board.setPiece(to,bishop);
		EasyMock.expectLastCall();
		bishop.setMoved(true);
		EasyMock.expectLastCall();
		EasyMock.expect(game.isInCheck(PieceColor.BLACK)).andReturn(false);
		EasyMock.expect(game.isCheckmate(PieceColor.WHITE)).andReturn(false);
		EasyMock.expect(game.isInCheck(PieceColor.WHITE)).andReturn(true);
//		EasyMock.expect(game.isCheckmate(PieceColor.BLACK)).andReturn(false);
//		EasyMock.expect(game.isStalemate(PieceColor.BLACK)).andReturn(false);
		EasyMock.expect(game.createNotation(from, to, piece, null,
						PieceType.BISHOP, false, false))
				.andReturn("notation");
		EasyMock.replay(board, player1, player2, piece, game);
		game.startNewGame(player1, player2);
		game.switchTurn();
		MoveResult result = game.makeMove(from, to, PieceType.BISHOP);
		Move last=new Move(from,to, piece, null,
				PieceType.BISHOP, false, false, "notation");
		Map<String, Integer> target = new HashMap<>();
		target.put("lastp2", 1);
//		Bishop bishop=new Bishop(PieceColor.WHITE);
		System.out.println(result);
		assertEquals(MoveResult.CHECK, result);
		System.out.println(game.positionHistory);
		assertTrue(target.equals(game.positionHistory));
		assertTrue(last.equals(game.lastMove));
		moveHistory.add(last);
		assertTrue(moveHistory.equals(game.moveHistory));
		assertEquals(game.currentPlayer.getColor(),PieceColor.WHITE);
		assertEquals(game.halfMoveClock,1);
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
		EasyMock.expect(player1.getColor()).andReturn(PieceColor.WHITE).anyTimes();
		EasyMock.expect(player2.getColor()).andReturn(PieceColor.BLACK).anyTimes();
		player1.setColor(PieceColor.WHITE);
		EasyMock.expectLastCall();
		player2.setColor(PieceColor.BLACK);
		EasyMock.expectLastCall();
		board.initBoard();
		EasyMock.expectLastCall();
		EasyMock.replay(board, player1, player2);
		game.startNewGame(player1, player2);
		game.switchTurn();
		assertEquals(game.currentPlayer.getColor(),PieceColor.BLACK);
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
		EasyMock.expect(player1.getColor()).andReturn(PieceColor.WHITE).anyTimes();
		EasyMock.expect(player2.getColor()).andReturn(PieceColor.BLACK).anyTimes();
		player1.setColor(PieceColor.WHITE);
		EasyMock.expectLastCall();
		player2.setColor(PieceColor.BLACK);
		EasyMock.expectLastCall();
		board.initBoard();
		EasyMock.expectLastCall();
		EasyMock.replay(board, player1, player2);
		game.startNewGame(player1, player2);
		game.switchTurn();
		game.switchTurn();
		assertEquals(game.currentPlayer.getColor(),PieceColor.WHITE);
		EasyMock.verify(board, player1, player2);
	}
	@Test
	public void isInCheck_check() {
		Board board = new Board();
		board.clearBoard();
		Location king = new Location(0, 0);
		Location attacker = new Location(7, 7);
		board.setPiece(king, new Piece(PieceType.KING,PieceColor.BLACK));
		board.setPiece(attacker, new Piece(PieceType.BISHOP,PieceColor.WHITE));
//		Player player1 = EasyMock.createMock(Player.class);
//		Player player2 = EasyMock.createMock(Player.class);
//		Piece piece = EasyMock.createMock(Piece.class);
		List<Move> moveHistory = new ArrayList<>();
		Map<String, Integer> positionHistory = new HashMap<>();
		Move lastMove = null;
		int halfMoveClock = 0;
		assertEquals(0, board.findKing(PieceColor.BLACK).getRow());
		assertEquals(0, board.findKing(PieceColor.BLACK).getCol());
		assertEquals(PieceType.BISHOP, board.getPiece(attacker).getPieceType());
		assertEquals(PieceColor.WHITE, board.getPiece(attacker).getColor());
		Game game = new Game(
				board,
				GameStatus.WHITE_TURN,
				moveHistory,
				null,
				0,
				positionHistory);
//		Location from = new Location(0, 0);
//		Location to = new Location(0, 7);
//		EasyMock.expect(board.getPiece(from)).andReturn(piece).anyTimes();
//		EasyMock.expect(piece.getType()).andReturn(PieceType.PAWN);
//		EasyMock.expect(piece.getColor()).andReturn(PieceColor.WHITE);
//		EasyMock.expect(board.findKing(PieceColor.BLACK)).andReturn(to);
//		EasyMock.expect(player1.getName()).andReturn("p1").anyTimes();
//		EasyMock.expect(player2.getName()).andReturn("p2").anyTimes();
//		EasyMock.expect(player1.getColor()).andReturn(PieceColor.WHITE).anyTimes();
//		EasyMock.expect(player2.getColor()).andReturn(PieceColor.BLACK).anyTimes();
////		EasyMock.expect(piece.canMove(board,from,to)).andReturn(true);
//		player1.setColor(PieceColor.WHITE);
//		EasyMock.expectLastCall();
//		player2.setColor(PieceColor.BLACK);
//		EasyMock.expectLastCall();
//		board.initBoard();
//		EasyMock.expectLastCall();
//		EasyMock.replay(player1, player2);
//		game.white=player1;
//		game.black=player2;
//		game.currentPlayer=player1;
//		game.white.setColor(PieceColor.WHITE);
//		game.black.setColor(PieceColor.BLACK);
//		game.startNewGame(player1, player2);
//		game.switchTurn();
//		game.switchTurn();
		Boolean result=game.isInCheck(PieceColor.BLACK);
		assertEquals(game.status,GameStatus.BLACK_IN_CHECK);
		assertTrue(result);
		board.setPiece(attacker, null);
		assertFalse(game.isInCheck(PieceColor.BLACK));
//		EasyMock.verify(player1, player2);
	}


	@Test
	public void isInCheck_check_firstIsNull() {
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
		EasyMock.expect(piece.getColor()).andReturn(PieceColor.WHITE);
		EasyMock.expect(board.findKing(PieceColor.BLACK)).andReturn(to);
		EasyMock.expect(player1.getName()).andReturn("p1").anyTimes();
		EasyMock.expect(player2.getName()).andReturn("p2").anyTimes();
		EasyMock.expect(player1.getColor()).andReturn(PieceColor.WHITE).anyTimes();
		EasyMock.expect(player2.getColor()).andReturn(PieceColor.BLACK).anyTimes();
		EasyMock.expect(piece.canMove(board,from,to)).andReturn(true);
		player1.setColor(PieceColor.WHITE);
		EasyMock.expectLastCall();
		player2.setColor(PieceColor.BLACK);
		EasyMock.expectLastCall();
		board.initBoard();
		EasyMock.expectLastCall();
		EasyMock.replay(board, player1, player2, piece);
		game.startNewGame(player1, player2);
//		game.switchTurn();
//		game.switchTurn();
		Boolean result=game.isInCheck(PieceColor.BLACK);
		assertTrue(result);
		EasyMock.verify(board, player1, player2, piece);
	}
	@Test
	public void isInCheck_check_false() {
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
		EasyMock.expect(board.getPiece
				(EasyMock.anyObject(Location.class)))
				.andReturn(piece).anyTimes();
//		EasyMock.expect(board.getPiece(first)).andReturn(null).anyTimes();
//		EasyMock.expect(piece.getType()).andReturn(PieceType.PAWN);
		EasyMock.expect(piece.getColor()).andReturn(PieceColor.BLACK).anyTimes();
		EasyMock.expect(board.findKing(PieceColor.WHITE)).andReturn(to);
		EasyMock.expect(player1.getName()).andReturn("p1").anyTimes();
		EasyMock.expect(player2.getName()).andReturn("p2").anyTimes();
		EasyMock.expect(player1.getColor()).andReturn(PieceColor.WHITE).anyTimes();
		EasyMock.expect(player2.getColor()).andReturn(PieceColor.BLACK).anyTimes();
		EasyMock.expect(piece.canMove
				(EasyMock.anyObject(Board.class),
						EasyMock.anyObject(Location.class),
				EasyMock.anyObject(Location.class))).andReturn(false).anyTimes();
		player1.setColor(PieceColor.WHITE);
		EasyMock.expectLastCall();
		player2.setColor(PieceColor.BLACK);
		EasyMock.expectLastCall();
		board.initBoard();
		EasyMock.expectLastCall();
		EasyMock.replay(board, player1, player2, piece);
		game.startNewGame(player1, player2);
//		game.switchTurn();
//		game.switchTurn();
		Boolean result=game.isInCheck(PieceColor.WHITE);
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
				.addMockedMethod("isInCheck", PieceColor.class)
//				.addMockedMethod("isCheckmate", PieceColor.class)
//				.addMockedMethod("isStalemate", PieceColor.class)
				.createMock();
//		Location first=new Location(0,0);
		Location from = new Location(7, 7);
		Location to = new Location(0, 0);
		EasyMock.expect(game.isInCheck(PieceColor.BLACK))
				.andReturn(true).anyTimes();
		EasyMock.expect(board.getPiece
				(EasyMock.anyObject(Location.class)))
				.andReturn(piece).anyTimes();
//		EasyMock.expect(board.getPiece(first)).andReturn(null).anyTimes();
//		EasyMock.expect(piece.getType()).andReturn(PieceType.PAWN);
		EasyMock.expect(piece.getColor()).andReturn(PieceColor.BLACK).anyTimes();
//		EasyMock.expect(board.findKing(PieceColor.BLACK)).andReturn(to);
		EasyMock.expect(player1.getName()).andReturn("p1").anyTimes();
		EasyMock.expect(player2.getName()).andReturn("p2").anyTimes();
		EasyMock.expect(player1.getColor()).andReturn(PieceColor.WHITE).anyTimes();
		EasyMock.expect(player2.getColor()).andReturn(PieceColor.BLACK).anyTimes();
		EasyMock.expect(piece.canMove
				(EasyMock.anyObject(Board.class),
						EasyMock.anyObject(Location.class),
						EasyMock.anyObject(Location.class)))
				.andReturn(true).anyTimes();
		player1.setColor(PieceColor.WHITE);
		EasyMock.expectLastCall();
		player2.setColor(PieceColor.BLACK);
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
		Boolean result=game.isCheckmate(PieceColor.BLACK);
		assertTrue(result);
		assertEquals(game.status,GameStatus.WHITE_WIN);
		EasyMock.verify(board, player1, player2, piece,game);
	}
	@Test
	public void isCheckmate_true_white() {
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
				.addMockedMethod("isInCheck", PieceColor.class)
//				.addMockedMethod("isCheckmate", PieceColor.class)
//				.addMockedMethod("isStalemate", PieceColor.class)
				.createMock();
//		Location first=new Location(0,0);
		Location from = new Location(7, 7);
		Location to = new Location(0, 0);
		EasyMock.expect(game.isInCheck(PieceColor.WHITE))
				.andReturn(true).anyTimes();
		EasyMock.expect(board.getPiece
						(EasyMock.anyObject(Location.class)))
				.andReturn(piece).anyTimes();
//		EasyMock.expect(board.getPiece(first)).andReturn(null).anyTimes();
//		EasyMock.expect(piece.getType()).andReturn(PieceType.PAWN);
		EasyMock.expect(piece.getColor()).andReturn(PieceColor.WHITE).anyTimes();
//		EasyMock.expect(board.findKing(PieceColor.WHITE)).andReturn(to);
		EasyMock.expect(player1.getName()).andReturn("p1").anyTimes();
		EasyMock.expect(player2.getName()).andReturn("p2").anyTimes();
		EasyMock.expect(player1.getColor()).andReturn(PieceColor.WHITE).anyTimes();
		EasyMock.expect(player2.getColor()).andReturn(PieceColor.BLACK).anyTimes();
		EasyMock.expect(piece.canMove
						(EasyMock.anyObject(Board.class),
								EasyMock.anyObject(Location.class),
								EasyMock.anyObject(Location.class)))
				.andReturn(true).anyTimes();
		player1.setColor(PieceColor.WHITE);
		EasyMock.expectLastCall();
		player2.setColor(PieceColor.BLACK);
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
		game.switchTurn();
//		game.switchTurn();
		Boolean result=game.isCheckmate(PieceColor.WHITE);
		assertTrue(result);
		assertEquals(game.status,GameStatus.BLACK_WIN);
		EasyMock.verify(board, player1, player2, piece,game);
	}

	@Test
	public void isCheckmate_notCheck() {
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
				.addMockedMethod("isInCheck", PieceColor.class)
//				.addMockedMethod("isCheckmate", PieceColor.class)
//				.addMockedMethod("isStalemate", PieceColor.class)
				.createMock();
//		Location first=new Location(0,0);
		Location from = new Location(7, 7);
		Location to = new Location(0, 0);
		EasyMock.expect(game.isInCheck(PieceColor.BLACK))
				.andReturn(false).anyTimes();
		EasyMock.expect(board.getPiece
						(EasyMock.anyObject(Location.class)))
				.andReturn(piece).anyTimes();
//		EasyMock.expect(board.getPiece(first)).andReturn(null).anyTimes();
//		EasyMock.expect(piece.getType()).andReturn(PieceType.PAWN);
//		EasyMock.expect(piece.getColor()).andReturn(PieceColor.BLACK).anyTimes();
//		EasyMock.expect(board.findKing(PieceColor.BLACK)).andReturn(to);
		EasyMock.expect(player1.getName()).andReturn("p1").anyTimes();
		EasyMock.expect(player2.getName()).andReturn("p2").anyTimes();
		EasyMock.expect(player1.getColor()).andReturn(PieceColor.WHITE).anyTimes();
		EasyMock.expect(player2.getColor()).andReturn(PieceColor.BLACK).anyTimes();
		EasyMock.expect(piece.canMove
						(EasyMock.anyObject(Board.class),
								EasyMock.anyObject(Location.class),
								EasyMock.anyObject(Location.class)))
				.andReturn(true).anyTimes();
		player1.setColor(PieceColor.WHITE);
		EasyMock.expectLastCall();
		player2.setColor(PieceColor.BLACK);
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
		Boolean result=game.isCheckmate(PieceColor.BLACK);
		assertFalse(result);
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
				.addMockedMethod("isInCheck", PieceColor.class)
//				.addMockedMethod("isCheckmate", PieceColor.class)
//				.addMockedMethod("isStalemate", PieceColor.class)
				.createMock();
//		Location first=new Location(0,0);
		Location from = new Location(7, 7);
		Location to = new Location(7, 7);
		EasyMock.expect(game.isInCheck(PieceColor.WHITE))
				.andReturn(true).times(2);
		EasyMock.expect(game.isInCheck(PieceColor.WHITE))
				.andReturn(false).once();
		EasyMock.expect(board.getPiece
				(EasyMock.anyObject(Location.class)))
				.andReturn(null).times(63);
		EasyMock.expect(board.getPiece
						(EasyMock.anyObject(Location.class)))
				.andReturn(piece).anyTimes();
//		EasyMock.expect(board.getPiece
//						(EasyMock.anyObject(Location.class)))
//				.andReturn(null).times(64);
//		EasyMock.expect(board.getPiece(first)).andReturn(null).anyTimes();
//		EasyMock.expect(piece.getType()).andReturn(PieceType.PAWN);
		EasyMock.expect(piece.getColor()).andReturn(PieceColor.WHITE).anyTimes();
//		EasyMock.expect(board.findKing(PieceColor.WHITE)).andReturn(to);
		EasyMock.expect(player1.getName()).andReturn("p1").anyTimes();
		EasyMock.expect(player2.getName()).andReturn("p2").anyTimes();
		EasyMock.expect(player1.getColor()).andReturn(PieceColor.WHITE).anyTimes();
		EasyMock.expect(player2.getColor()).andReturn(PieceColor.BLACK).anyTimes();
		EasyMock.expect(piece.canMove
				(EasyMock.anyObject(Board.class),
						EasyMock.anyObject(Location.class),
						EasyMock.anyObject(Location.class)))
				.andReturn(false).times(62);
		EasyMock.expect(piece.canMove
				(EasyMock.anyObject(Board.class),
						EasyMock.anyObject(Location.class),
						EasyMock.anyObject(Location.class)))
				.andReturn(true).times(2);
		player1.setColor(PieceColor.WHITE);
		EasyMock.expectLastCall();
		player2.setColor(PieceColor.BLACK);
		EasyMock.expectLastCall();
		board.initBoard();
		EasyMock.expectLastCall();
		board.movePiece(EasyMock.anyObject(Location.class),
				EasyMock.anyObject(Location.class));
		EasyMock.expectLastCall().times(4);
		board.setPiece(EasyMock.anyObject(Location.class),
				EasyMock.anyObject(Piece.class));
		EasyMock.expectLastCall().times(2);
		EasyMock.replay(board, player1, player2, piece, game);
		game.startNewGame(player1, player2);
//		game.switchTurn();
//		game.switchTurn();
		Boolean result=game.isCheckmate(PieceColor.WHITE);
		assertFalse(result);
		EasyMock.verify(board, player1, player2, piece,game);
	}
	@Test
	public void isCheckmate_targetOutBound() {
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
				.addMockedMethod("isInCheck", PieceColor.class)
//				.addMockedMethod("isCheckmate", PieceColor.class)
//				.addMockedMethod("isStalemate", PieceColor.class)
				.createMock();
//		Location first=new Location(0,0);
		Location from = new Location(7, 7);
		Location to = new Location(7, 7);
		EasyMock.expect(game.isInCheck(PieceColor.WHITE))
				.andReturn(true).once();
//		EasyMock.expect(game.isInCheck(PieceColor.WHITE))
//				.andReturn(false).once();
		EasyMock.expect(board.getPiece
						(EasyMock.anyObject(Location.class)))
				.andReturn(null).times(63);
		EasyMock.expect(board.getPiece
						(EasyMock.anyObject(Location.class)))
				.andReturn(piece).anyTimes();
//		EasyMock.expect(board.getPiece
//						(EasyMock.anyObject(Location.class)))
//				.andReturn(null).times(64);
//		EasyMock.expect(board.getPiece(first)).andReturn(null).anyTimes();
//		EasyMock.expect(piece.getType()).andReturn(PieceType.PAWN);
		EasyMock.expect(piece.getColor()).andReturn(PieceColor.WHITE).anyTimes();
//		EasyMock.expect(board.findKing(PieceColor.WHITE)).andReturn(to);
		EasyMock.expect(player1.getName()).andReturn("p1").anyTimes();
		EasyMock.expect(player2.getName()).andReturn("p2").anyTimes();
		EasyMock.expect(player1.getColor()).andReturn(PieceColor.WHITE).anyTimes();
		EasyMock.expect(player2.getColor()).andReturn(PieceColor.BLACK).anyTimes();
		EasyMock.expect(piece.canMove
						(EasyMock.anyObject(Board.class),
								EasyMock.anyObject(Location.class),
								EasyMock.anyObject(Location.class)))
				.andReturn(false).times(64);
		EasyMock.expect(piece.canMove
						(EasyMock.anyObject(Board.class),
								EasyMock.anyObject(Location.class),
								EasyMock.anyObject(Location.class)))
				.andReturn(true).anyTimes();
		player1.setColor(PieceColor.WHITE);
		EasyMock.expectLastCall();
		player2.setColor(PieceColor.BLACK);
		EasyMock.expectLastCall();
		board.initBoard();
		EasyMock.expectLastCall();
//		board.movePiece(EasyMock.anyObject(Location.class),
//				EasyMock.anyObject(Location.class));
//		EasyMock.expectLastCall().times(4);
//		board.setPiece(EasyMock.anyObject(Location.class),
//				EasyMock.anyObject(Piece.class));
//		EasyMock.expectLastCall().times(2);
		EasyMock.replay(board, player1, player2, piece, game);
		game.startNewGame(player1, player2);
//		game.switchTurn();
//		game.switchTurn();
		Boolean result=game.isCheckmate(PieceColor.WHITE);
		assertTrue(result);
		EasyMock.verify(board, player1, player2, piece,game);
	}
	@Test
	public void isCheckmate_outbound() {
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
				.addMockedMethod("isInCheck", PieceColor.class)
//				.addMockedMethod("isCheckmate", PieceColor.class)
//				.addMockedMethod("isStalemate", PieceColor.class)
				.createMock();
//		Location first=new Location(0,0);
		Location from = new Location(7, 7);
		Location to = new Location(7, 7);
		EasyMock.expect(game.isInCheck(PieceColor.WHITE))
				.andReturn(true).once();
//		EasyMock.expect(game.isInCheck(PieceColor.WHITE))
//				.andReturn(false).once();
		EasyMock.expect(board.getPiece
						(EasyMock.anyObject(Location.class)))
				.andReturn(null).times(64);
		EasyMock.expect(board.getPiece
						(EasyMock.anyObject(Location.class)))
				.andReturn(piece).anyTimes();
//		EasyMock.expect(board.getPiece
//						(EasyMock.anyObject(Location.class)))
//				.andReturn(null).times(64);
//		EasyMock.expect(board.getPiece(first)).andReturn(null).anyTimes();
//		EasyMock.expect(piece.getType()).andReturn(PieceType.PAWN);
		EasyMock.expect(piece.getColor()).andReturn(PieceColor.WHITE).anyTimes();
//		EasyMock.expect(board.findKing(PieceColor.WHITE)).andReturn(to);
		EasyMock.expect(player1.getName()).andReturn("p1").anyTimes();
		EasyMock.expect(player2.getName()).andReturn("p2").anyTimes();
		EasyMock.expect(player1.getColor()).andReturn(PieceColor.WHITE).anyTimes();
		EasyMock.expect(player2.getColor()).andReturn(PieceColor.BLACK).anyTimes();
//		EasyMock.expect(piece.canMove
//						(EasyMock.anyObject(Board.class),
//								EasyMock.anyObject(Location.class),
//								EasyMock.anyObject(Location.class)))
//				.andReturn(false).times(62);
//		EasyMock.expect(piece.canMove
//						(EasyMock.anyObject(Board.class),
//								EasyMock.anyObject(Location.class),
//								EasyMock.anyObject(Location.class)))
//				.andReturn(true).times(2);
		player1.setColor(PieceColor.WHITE);
		EasyMock.expectLastCall();
		player2.setColor(PieceColor.BLACK);
		EasyMock.expectLastCall();
		board.initBoard();
		EasyMock.expectLastCall();
//		board.movePiece(EasyMock.anyObject(Location.class),
//				EasyMock.anyObject(Location.class));
//		EasyMock.expectLastCall().times(4);
//		board.setPiece(EasyMock.anyObject(Location.class),
//				EasyMock.anyObject(Piece.class));
//		EasyMock.expectLastCall().times(2);
		EasyMock.replay(board, player1, player2, piece, game);
		game.startNewGame(player1, player2);
//		game.switchTurn();
//		game.switchTurn();
		Boolean result=game.isCheckmate(PieceColor.WHITE);
		assertTrue(result);
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
				.addMockedMethod("isInCheck", PieceColor.class)
//				.addMockedMethod("isCheckmate", PieceColor.class)
//				.addMockedMethod("isStalemate", PieceColor.class)
				.createMock();
//		Location first=new Location(0,0);
		Location from = new Location(7, 7);
		Location to = new Location(7, 7);
		EasyMock.expect(game.isInCheck(PieceColor.WHITE))
				.andReturn(false).andReturn(true).anyTimes();
		EasyMock.expect(board.getPiece
				(EasyMock.anyObject(Location.class)))
				.andReturn(piece).anyTimes();
//		EasyMock.expect(board.getPiece(first)).andReturn(null).anyTimes();
//		EasyMock.expect(piece.getType()).andReturn(PieceType.PAWN);
		EasyMock.expect(piece.getColor()).andReturn(PieceColor.WHITE).anyTimes();
//		EasyMock.expect(board.findKing(PieceColor.WHITE)).andReturn(to);
		EasyMock.expect(player1.getName()).andReturn("p1").anyTimes();
		EasyMock.expect(player2.getName()).andReturn("p2").anyTimes();
		EasyMock.expect(player1.getColor()).andReturn(PieceColor.WHITE).anyTimes();
		EasyMock.expect(player2.getColor()).andReturn(PieceColor.BLACK).anyTimes();
		EasyMock.expect(piece.canMove
				(EasyMock.anyObject(Board.class),
						EasyMock.anyObject(Location.class),
						EasyMock.anyObject(Location.class)))
				.andReturn(true).anyTimes();
		player1.setColor(PieceColor.WHITE);
		EasyMock.expectLastCall();
		player2.setColor(PieceColor.BLACK);
		EasyMock.expectLastCall();
		board.initBoard();
		EasyMock.expectLastCall();
		board.movePiece(EasyMock.anyObject(Location.class),
				EasyMock.anyObject(Location.class));
		EasyMock.expectLastCall().times(8192);
		board.setPiece(EasyMock.anyObject(Location.class),
				EasyMock.anyObject(Piece.class));
		EasyMock.expectLastCall().times(4096);
		EasyMock.replay(board, player1, player2, piece, game);
		game.startNewGame(player1, player2);
//		game.switchTurn();
//		game.switchTurn();
		Boolean result=game.isStalemate(PieceColor.WHITE);
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
				.addMockedMethod("isInCheck", PieceColor.class)
//				.addMockedMethod("isCheckmate", PieceColor.class)
//				.addMockedMethod("isStalemate", PieceColor.class)
				.createMock();
//		Location first=new Location(0,0);
		Location from = new Location(7, 7);
		Location to = new Location(7, 7);
		EasyMock.expect(game.isInCheck(PieceColor.WHITE))
				.andReturn(false);
//				.andReturn(false).andReturn(true).anyTimes();
		EasyMock.expect(board.getPiece
				(EasyMock.anyObject(Location.class)))
				.andReturn(piece).anyTimes();
//		EasyMock.expect(board.getPiece(first)).andReturn(null).anyTimes();
//		EasyMock.expect(piece.getType()).andReturn(PieceType.PAWN);
		EasyMock.expect(piece.getColor()).andReturn(PieceColor.WHITE).anyTimes();
//		EasyMock.expect(board.findKing(PieceColor.WHITE)).andReturn(to);
		EasyMock.expect(player1.getName()).andReturn("p1").anyTimes();
		EasyMock.expect(player2.getName()).andReturn("p2").anyTimes();
		EasyMock.expect(player1.getColor()).andReturn(PieceColor.WHITE).anyTimes();
		EasyMock.expect(player2.getColor()).andReturn(PieceColor.BLACK).anyTimes();
		EasyMock.expect(piece.canMove
				(EasyMock.anyObject(Board.class),
						EasyMock.anyObject(Location.class),
						EasyMock.anyObject(Location.class)))
				.andReturn(false).anyTimes();
		player1.setColor(PieceColor.WHITE);
		EasyMock.expectLastCall();
		player2.setColor(PieceColor.BLACK);
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
		Boolean result=game.isStalemate(PieceColor.WHITE);
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
				.addMockedMethod("isInCheck", PieceColor.class)
//				.addMockedMethod("isCheckmate", PieceColor.class)
//				.addMockedMethod("isStalemate", PieceColor.class)
				.createMock();
//		Location first=new Location(0,0);
		Location from = new Location(7, 7);
		Location to = new Location(7, 7);
		EasyMock.expect(game.isInCheck(PieceColor.WHITE))
				.andReturn(false).anyTimes();
//				.andReturn(false).andReturn(true).anyTimes();
		EasyMock.expect(board.getPiece
				(EasyMock.anyObject(Location.class)))
				.andReturn(piece).anyTimes();
//		EasyMock.expect(board.getPiece(first)).andReturn(null).anyTimes();
//		EasyMock.expect(piece.getType()).andReturn(PieceType.PAWN);
		EasyMock.expect(piece.getColor()).andReturn(PieceColor.WHITE).anyTimes();
//		EasyMock.expect(board.findKing(PieceColor.WHITE)).andReturn(to);
		EasyMock.expect(player1.getName()).andReturn("p1").anyTimes();
		EasyMock.expect(player2.getName()).andReturn("p2").anyTimes();
		EasyMock.expect(player1.getColor()).andReturn(PieceColor.WHITE).anyTimes();
		EasyMock.expect(player2.getColor()).andReturn(PieceColor.BLACK).anyTimes();
		EasyMock.expect(piece.canMove
				(EasyMock.anyObject(Board.class),
						EasyMock.anyObject(Location.class),
						EasyMock.anyObject(Location.class)))
				.andReturn(true).anyTimes();
		player1.setColor(PieceColor.WHITE);
		EasyMock.expectLastCall();
		player2.setColor(PieceColor.BLACK);
		EasyMock.expectLastCall();
		board.initBoard();
		EasyMock.expectLastCall();
		board.movePiece(EasyMock.anyObject(Location.class),
				EasyMock.anyObject(Location.class));
		EasyMock.expectLastCall().times(2);
		board.setPiece(EasyMock.anyObject(Location.class),
				EasyMock.anyObject(Piece.class));
		EasyMock.expectLastCall().once();
		EasyMock.replay(board, player1, player2, piece, game);
		game.startNewGame(player1, player2);
//		game.switchTurn();
//		game.switchTurn();
		Boolean result=game.isStalemate(PieceColor.WHITE);
		assertFalse(result);
		EasyMock.verify(board, player1, player2, piece,game);
	}

	@Test
	public void isStalemate_false_check() {
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
				.addMockedMethod("isInCheck", PieceColor.class)
//				.addMockedMethod("isCheckmate", PieceColor.class)
//				.addMockedMethod("isStalemate", PieceColor.class)
				.createMock();
//		Location first=new Location(0,0);
		Location from = new Location(7, 7);
		Location to = new Location(7, 7);
		EasyMock.expect(game.isInCheck(PieceColor.BLACK))
				.andReturn(true).anyTimes();
//				.andReturn(false).andReturn(true).anyTimes();
//		EasyMock.expect(board.getPiece
//						(EasyMock.anyObject(Location.class)))
//				.andReturn(piece).anyTimes();
//		EasyMock.expect(board.getPiece(first)).andReturn(null).anyTimes();
//		EasyMock.expect(piece.getType()).andReturn(PieceType.PAWN);
		EasyMock.expect(piece.getColor()).andReturn(PieceColor.WHITE).anyTimes();
//		EasyMock.expect(board.findKing(PieceColor.WHITE)).andReturn(to);
		EasyMock.expect(player1.getName()).andReturn("p1").anyTimes();
		EasyMock.expect(player2.getName()).andReturn("p2").anyTimes();
		EasyMock.expect(player1.getColor()).andReturn(PieceColor.WHITE).anyTimes();
		EasyMock.expect(player2.getColor()).andReturn(PieceColor.BLACK).anyTimes();
//		EasyMock.expect(piece.canMove
//						(EasyMock.anyObject(Board.class),
//								EasyMock.anyObject(Location.class),
//								EasyMock.anyObject(Location.class)))
//				.andReturn(true).anyTimes();
		player1.setColor(PieceColor.WHITE);
		EasyMock.expectLastCall();
		player2.setColor(PieceColor.BLACK);
		EasyMock.expectLastCall();
		board.initBoard();
		EasyMock.expectLastCall();
//		board.movePiece(EasyMock.anyObject(Location.class),
//				EasyMock.anyObject(Location.class));
//		EasyMock.expectLastCall().times(2);
//		board.setPiece(EasyMock.anyObject(Location.class),
//				EasyMock.anyObject(Piece.class));
//		EasyMock.expectLastCall().once();
		EasyMock.replay(board, player1, player2, piece, game);
		game.startNewGame(player1, player2);
//		game.switchTurn();
//		game.switchTurn();
		Boolean result=game.isStalemate(PieceColor.BLACK);
		assertFalse(result);
		EasyMock.verify(board, player1, player2, piece,game);
	}

	@Test
	public void resign() {
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
//		Location from = new Location(7, 7);
//		Location to = new Location(7, 7);
		game.resign();
		assertEquals(GameStatus.RESIGNED,game.status);
	}
	@Test
	public void pawnPromption_Queen() {
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
//		Location from = new Location(7, 7);
//		Location to = new Location(7, 7);
		Piece result=game.createPromotedPiece(PieceType.QUEEN, PieceColor.WHITE);
		Piece queen=new Piece(PieceType.QUEEN, PieceColor.WHITE);
		assertEquals(result, queen);
	}
	@Test
	public void pawnPromption_Bishop() {
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
//		Location from = new Location(7, 7);
//		Location to = new Location(7, 7);
		Piece result=game.createPromotedPiece(PieceType.BISHOP, PieceColor.BLACK);
		Piece bishop=new Piece(PieceType.BISHOP, PieceColor.BLACK);
		assertEquals(result, bishop);
	}
	@Test
	public void pawnPromption_Knight() {
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
//		Location from = new Location(7, 7);
//		Location to = new Location(7, 7);
		Piece result=game.createPromotedPiece(PieceType.KNIGHT, PieceColor.BLACK);
		Piece bishop=new Piece(PieceType.KNIGHT,PieceColor.BLACK);
		assertEquals(result, bishop);
	}
	@Test
	public void pawnPromption_Rook() {
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
//		Location from = new Location(7, 7);
//		Location to = new Location(7, 7);
		Piece result=game.createPromotedPiece(PieceType.ROOK, PieceColor.WHITE);
		Piece bishop=new Piece(PieceType.ROOK,PieceColor.WHITE);
		assertEquals(result, bishop);
	}
	@Test
	public void getStatus_whiteTurn() {
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
//		Location from = new Location(7, 7);
//		Location to = new Location(7, 7);
		GameStatus result=game.getStatus();
		assertEquals(result, GameStatus.WHITE_TURN);
	}
	@Test
	public void getStatus_blackTurn() {
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
				GameStatus.BLACK_TURN,
				moveHistory,
				null,
				0,
				positionHistory);
//		Location first=new Location(0,0);
//		Location from = new Location(7, 7);
//		Location to = new Location(7, 7);
		GameStatus result=game.getStatus();
		assertEquals(result, GameStatus.BLACK_TURN);
	}
	@Test
	public void getStatus_whiteWin() {
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
				GameStatus.WHITE_WIN,
				moveHistory,
				null,
				0,
				positionHistory);
//		Location first=new Location(0,0);
//		Location from = new Location(7, 7);
//		Location to = new Location(7, 7);
		GameStatus result=game.getStatus();
		assertEquals(result, GameStatus.WHITE_WIN);
	}
	@Test
	public void getStatus_blackWin() {
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
				GameStatus.BLACK_WIN,
				moveHistory,
				null,
				0,
				positionHistory);
//		Location first=new Location(0,0);
//		Location from = new Location(7, 7);
//		Location to = new Location(7, 7);
		GameStatus result=game.getStatus();
		assertEquals(result, GameStatus.BLACK_WIN);
	}
	@Test
	public void getStatus_white_check() {
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
				GameStatus.WHITE_IN_CHECK,
				moveHistory,
				null,
				0,
				positionHistory);
//		Location first=new Location(0,0);
//		Location from = new Location(7, 7);
//		Location to = new Location(7, 7);
		GameStatus result=game.getStatus();
		assertEquals(result, GameStatus.WHITE_IN_CHECK);
	}
	@Test
	public void getStatus_black_check() {
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
				GameStatus.BLACK_IN_CHECK,
				moveHistory,
				null,
				0,
				positionHistory);
//		Location first=new Location(0,0);
//		Location from = new Location(7, 7);
//		Location to = new Location(7, 7);
		GameStatus result=game.getStatus();
		assertEquals(result, GameStatus.BLACK_IN_CHECK);
	}
	@Test
	public void getStatus_draw() {
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
				GameStatus.DRAW,
				moveHistory,
				null,
				0,
				positionHistory);
//		Location first=new Location(0,0);
//		Location from = new Location(7, 7);
//		Location to = new Location(7, 7);
		GameStatus result=game.getStatus();
		assertEquals(result, GameStatus.DRAW);
	}
	@Test
	public void getStatus_resigned() {
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
				GameStatus.RESIGNED,
				moveHistory,
				null,
				0,
				positionHistory);
//		Location first=new Location(0,0);
//		Location from = new Location(7, 7);
//		Location to = new Location(7, 7);
		GameStatus result=game.getStatus();
		assertEquals(result, GameStatus.RESIGNED);
	}
	@Test
	public void getMoveHistory_empty() {
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
				GameStatus.WHITE_IN_CHECK,
				moveHistory,
				null,
				0,
				positionHistory);
//		Location first=new Location(0,0);
//		Location from = new Location(7, 7);
//		Location to = new Location(7, 7);
		List<Move> history=new ArrayList<>();
		List<Move> result=game.getMoveHistory();
		assertEquals(result, history);
	}
	@Test
	public void getMoveHistory_nonEmpty() {
		Board board = EasyMock.createMock(Board.class);
		Player player1 = EasyMock.createMock(Player.class);
		Player player2 = EasyMock.createMock(Player.class);
		Piece piece = EasyMock.createMock(Piece.class);
		List<Move> moveHistory = new ArrayList<>();
		Map<String, Integer> positionHistory = new HashMap<>();
//		Move move=EasyMock.createMock(Move.class);
		Move move=new Move(new Location(0,0),
				new Location(7,7),piece, null,
				null, false,
				false, "notation");
		moveHistory.add(move);
		List<Move> target=new ArrayList<>();
		target.add(move);
		Move lastMove = null;
		int halfMoveClock = 0;
		Game game = new Game(
				board,
				GameStatus.WHITE_IN_CHECK,
				moveHistory,
				null,
				0,
				positionHistory);
//		Location first=new Location(0,0);
//		Location from = new Location(7, 7);
//		Location to = new Location(7, 7);
//		List<Move> history=new ArrayList<>();
		List<Move> result=game.getMoveHistory();
		assertEquals(result, target);
	}
	@Test
	public void getLastMove() {
		Board board = EasyMock.createMock(Board.class);
		Player player1 = EasyMock.createMock(Player.class);
		Player player2 = EasyMock.createMock(Player.class);
		Piece piece = EasyMock.createMock(Piece.class);
		List<Move> moveHistory = new ArrayList<>();
		Map<String, Integer> positionHistory = new HashMap<>();
//		Move move=EasyMock.createMock(Move.class);
		Move move=new Move(new Location(0,0),new Location(7,7),
				piece, null, null,
				false, false, "notation");
		moveHistory.add(move);
		List<Move> target=new ArrayList<>();
		target.add(move);
		Move lastMove = null;
		int halfMoveClock = 0;
		Game game = new Game(
				board,
				GameStatus.WHITE_TURN,
				moveHistory,
				move,
				0,
				positionHistory);
//		Location first=new Location(0,0);
//		Location from = new Location(7, 7);
//		Location to = new Location(7, 7);
//		List<Move> history=new ArrayList<>();
//		List<Move> result=game.getMoveHistory();
		assertEquals(move, game.getLastMove());
	}
	@Test
	public void timeOut_black() {
		Board board = EasyMock.createMock(Board.class);
		List<Move> moveHistory = new ArrayList<>();
		Map<String, Integer> positionHistory = new HashMap<>();
//		Move move=new Move(new Location(0,0),new Location(7,7));
//		moveHistory.add(move);
//		List<Move> target=new ArrayList<>();
//		target.add(move);
		Player player1 = EasyMock.createMock(Player.class);
		Player player2 = EasyMock.createMock(Player.class);
		EasyMock.expect(player1.getColor()).andReturn(PieceColor.WHITE);
		EasyMock.expect(player2.getColor()).andReturn(PieceColor.BLACK);
		player1.setColor(PieceColor.WHITE);
		EasyMock.expectLastCall();
		player2.setColor(PieceColor.BLACK);
		EasyMock.expectLastCall();
		EasyMock.expect(player1.getName()).andReturn("p1");
		EasyMock.expect(player2.getName()).andReturn("p2");
		EasyMock.replay(player1,player2);
		Move lastMove = null;
		int halfMoveClock = 0;
		Game game = new Game(
				board,
				GameStatus.WHITE_TURN,
				moveHistory,
				null,
				0,
				positionHistory);
		game.startNewGame(player1,player2);
		game.timeOut();
		assertEquals(GameStatus.BLACK_WIN, game.getStatus());
	}
	@Test
	public void timeOut_white() {
		Board board = EasyMock.createMock(Board.class);
		List<Move> moveHistory = new ArrayList<>();
		Map<String, Integer> positionHistory = new HashMap<>();
		Piece piece=EasyMock.createMock(Piece.class);
//		Move move=EasyMock.createMock(Move.class);
		Move move=new Move(new Location(0,0),
				new Location(7,7), piece, null,
				null, false, false,
				"notation");
		moveHistory.add(move);
		List<Move> target=new ArrayList<>();
		target.add(move);
		Player player1 = EasyMock.createMock(Player.class);
		Player player2 = EasyMock.createMock(Player.class);
		EasyMock.expect(player1.getColor()).andReturn(PieceColor.WHITE);
		EasyMock.expect(player2.getColor()).andReturn(PieceColor.BLACK);
		player1.setColor(PieceColor.WHITE);
		EasyMock.expectLastCall();
		player2.setColor(PieceColor.BLACK);
		EasyMock.expectLastCall();
		EasyMock.expect(player1.getName()).andReturn("p1");
		EasyMock.expect(player2.getName()).andReturn("p2");
		EasyMock.replay(player1,player2);
		Move lastMove = null;
		int halfMoveClock = 0;
		Game game = new Game(
				board,
				GameStatus.WHITE_TURN,
				moveHistory,
				move,
				0,
				positionHistory);
		game.startNewGame(player1,player2);
		game.switchTurn();
		game.timeOut();
		assertEquals(GameStatus.WHITE_WIN, game.getStatus());
	}
	@Test
	public void createNotation_castle() {
		Board board = EasyMock.createMock(Board.class);
		Piece king = EasyMock.createMock(Piece.class);
		List<Move> moveHistory = new ArrayList<>();
		Map<String, Integer> positionHistory = new HashMap<>();
		Game game = new Game(board,
				GameStatus.WHITE_TURN,
				moveHistory,
				null,
				0,
				positionHistory);
		Location from = new Location(7, 4);
		Location to = new Location(7, 6);
		EasyMock.replay(board, king);
		String notation = game.createNotation(
				from,
				to,
				king,
				null,
				null,
				true,
				false
		);
		assertEquals("O-O", notation);
		EasyMock.verify(board, king);
	}
	@Test
	public void createNotation_QueenCastle() {
		Board board = EasyMock.createMock(Board.class);
		Piece king = EasyMock.createMock(Piece.class);
		List<Move> moveHistory = new ArrayList<>();
		Map<String, Integer> positionHistory = new HashMap<>();
		Game game = new Game(board,
				GameStatus.WHITE_TURN,
				moveHistory,
				null,
				0,
				positionHistory);
		Location from = new Location(7, 4);
		Location to = new Location(7, 2);
		EasyMock.replay(board, king);
		String notation = game.createNotation(
				from,
				to,
				king,
				null,
				null,
				true,
				false
		);
		assertEquals("O-O-O", notation);
		EasyMock.verify(board, king);
	}
	@Test
	public void createNotation_normalMove() {
		Board board = EasyMock.createMock(Board.class);
		Piece pawn = EasyMock.createMock(Piece.class);
		List<Move> moveHistory = new ArrayList<>();
		Map<String, Integer> positionHistory = new HashMap<>();
		Game game = new Game(board, GameStatus.WHITE_TURN, moveHistory, null, 0, positionHistory);
		Location from = new Location(6, 4);
		Location to = new Location(4, 4);
		EasyMock.expect(pawn.getPieceType())
				.andReturn(PieceType.PAWN);
		EasyMock.replay(board, pawn);
		String notation = game.createNotation(
				from,
				to,
				pawn,
				null,
				null,
				false,
				false
		);
		assertEquals("PAWN (6,4) -> (4,4)", notation);
		EasyMock.verify(board, pawn);
	}
	@Test
	public void createNotation_captureByEnPassant() {
		Board board = EasyMock.createMock(Board.class);
		Piece whitePawn = EasyMock.createMock(Piece.class);
		Piece blackPawn = EasyMock.createMock(Piece.class);
		List<Move> moveHistory = new ArrayList<>();
		Map<String, Integer> positionHistory = new HashMap<>();
		Game game = new Game(board,
				GameStatus.WHITE_TURN,
				moveHistory,
				null,
				0,
				positionHistory);
		Location from = new Location(3, 4);
		Location to = new Location(2, 5);
		EasyMock.expect(whitePawn.getPieceType()).andReturn(PieceType.PAWN);
		EasyMock.expect(blackPawn.getPieceType()).andReturn(PieceType.PAWN);
		EasyMock.replay(board, whitePawn, blackPawn);
		String notation = game.createNotation(
				from,
				to,
				whitePawn,
				blackPawn,
				null,
				false,
				true
		);
		assertEquals(
				"PAWN (3,4) -> (2,5) " +
						"captures PAWN en passant",
				notation);
		EasyMock.verify(board, whitePawn, blackPawn);
	}
	@Test
	public void createNotation_promotion() {
		Board board = EasyMock.createMock(Board.class);
//		Piece whitePawn = EasyMock.createMock(Piece.class);
		Piece blackPawn = EasyMock.createMock(Piece.class);
		List<Move> moveHistory = new ArrayList<>();
		Map<String, Integer> positionHistory = new HashMap<>();
		Game game = new Game(board,
				GameStatus.BLACK_TURN,
				moveHistory,
				null,
				0,
				positionHistory);
		Location from = new Location(6, 4);
		Location to = new Location(7, 4);
//		EasyMock.expect(whitePawn.getPieceType()).andReturn(PieceType.PAWN);
		EasyMock.expect(blackPawn.getPieceType())
				.andReturn(PieceType.PAWN);
		EasyMock.replay(board, blackPawn);
		String notation = game.createNotation(
				from,
				to,
				blackPawn,
				null,
				PieceType.BISHOP,
				false,
				false
		);
		assertEquals(
				"PAWN (6,4) -> (7,4) " +
						"promotes to BISHOP",
				notation);
		EasyMock.verify(board, blackPawn);
	}
	@Test
	public void isCastleMove_false() {
		Board board = EasyMock.createMock(Board.class);
		Piece king = EasyMock.createMock(Piece.class);
		List<Move> moveHistory = new ArrayList<>();
		Map<String, Integer> positionHistory = new HashMap<>();
		Game game = new Game(board,
				GameStatus.WHITE_TURN,
				moveHistory,
				null,
				0,
				positionHistory);
		Location from = new Location(7, 4);
		Location to = new Location(7, 5);
		EasyMock.expect(king.getPieceType())
				.andReturn(PieceType.KING);
		EasyMock.expect(king.hasMoved()).andReturn(false);
		EasyMock.replay(board, king);
		assertFalse(game.isCastleMove(from, to, king));
		EasyMock.verify(board, king);
	}
	@Test
	public void isCastleMove_differentRow_false() {
		Board board = EasyMock.createMock(Board.class);
		Piece king = EasyMock.createMock(Piece.class);
		List<Move> moveHistory = new ArrayList<>();
		Map<String, Integer> positionHistory = new HashMap<>();
		Game game = new Game(board,
				GameStatus.WHITE_TURN,
				moveHistory,
				null,
				0,
				positionHistory);
		Location from = new Location(7, 4);
		Location to = new Location(6, 4);
		EasyMock.expect(king.getPieceType())
				.andReturn(PieceType.KING);
		EasyMock.expect(king.hasMoved()).andReturn(false);
		EasyMock.replay(board, king);
		assertFalse(game.isCastleMove(from, to, king));
		EasyMock.verify(board, king);
	}
	@Test
	public void isCastleMove_blocked_false() {
		Board board = EasyMock.createMock(Board.class);
		Piece king = EasyMock.createMock(Piece.class);
		Piece rook = EasyMock.createMock(Piece.class);
		List<Move> moveHistory = new ArrayList<>();
		Map<String, Integer> positionHistory = new HashMap<>();
		Game game = new Game(board,
				GameStatus.WHITE_TURN,
				moveHistory,
				null,
				0,
				positionHistory);
		Location from = new Location(7, 4);
		Location to = new Location(7, 6);
		Location rookLocation = new Location(7, 7);
		Location blockedSquare = new Location(7, 5);
		EasyMock.expect(king.getPieceType())
				.andReturn(PieceType.KING);
		EasyMock.expect(king.hasMoved()).andReturn(false);
		EasyMock.expect(board.getPiece(rookLocation)).andReturn(rook);
		EasyMock.expect(rook.getPieceType()).andReturn(PieceType.ROOK);
		EasyMock.expect(rook.getColor()).andReturn(PieceColor.WHITE);
		EasyMock.expect(king.getColor()).andReturn(PieceColor.WHITE);
		EasyMock.expect(rook.hasMoved()).andReturn(false);
		EasyMock.expect(board.isEmpty(blockedSquare)).andReturn(false);
		EasyMock.replay(board, king, rook);
		assertFalse(game.isCastleMove(from, to, king));
		EasyMock.verify(board, king, rook);
	}
	@Test
	public void isCastleMove_move3Col_false() {
		Board board = EasyMock.createMock(Board.class);
		Piece king = EasyMock.createMock(Piece.class);
		List<Move> moveHistory = new ArrayList<>();
		Map<String, Integer> positionHistory = new HashMap<>();
		Game game = new Game(board,
				GameStatus.WHITE_TURN,
				moveHistory,
				null,
				0,
				positionHistory);
		Location from = new Location(7, 4);
		Location to = new Location(7, 7);
		EasyMock.expect(king.getPieceType())
				.andReturn(PieceType.KING);
		EasyMock.expect(king.hasMoved()).andReturn(false);
		EasyMock.replay(board, king);
		assertFalse(game.isCastleMove(from, to, king));
		EasyMock.verify(board, king);
	}
	@Test
	public void isCastleMove_true() {
		Board board = EasyMock.createMock(Board.class);
		Piece king = EasyMock.createMock(Piece.class);
		Piece rook = EasyMock.createMock(Piece.class);
		List<Move> moveHistory = new ArrayList<>();
		Map<String, Integer> positionHistory = new HashMap<>();
		Game game = new Game(board, GameStatus.WHITE_TURN,
				moveHistory, null, 0,
				positionHistory);
		Location from = new Location(7, 4);
		Location to = new Location(7, 6);
		Location rookLocation = new Location(7, 7);
		Location squareOne = new Location(7, 5);
		Location squareTwo = new Location(7, 6);
		EasyMock.expect(king.getPieceType()).andReturn(PieceType.KING);
		EasyMock.expect(king.hasMoved()).andReturn(false);
		EasyMock.expect(board.getPiece(rookLocation)).andReturn(rook);
		EasyMock.expect(rook.getPieceType()).andReturn(PieceType.ROOK);
		EasyMock.expect(rook.getColor()).andReturn(PieceColor.WHITE);
		EasyMock.expect(king.getColor()).andReturn(PieceColor.WHITE);
		EasyMock.expect(rook.hasMoved()).andReturn(false);
		EasyMock.expect(board.isEmpty(squareOne)).andReturn(true);
		EasyMock.expect(board.isEmpty(squareTwo)).andReturn(true);
		EasyMock.replay(board, king, rook);
		assertTrue(game.isCastleMove(from, to, king));
		EasyMock.verify(board, king, rook);
	}
	@Test
	public void performCastle() {
		Board board = EasyMock.createMock(Board.class);
		Piece king = EasyMock.createMock(Piece.class);
		Piece rook = EasyMock.createMock(Piece.class);
		List<Move> moveHistory = new ArrayList<>();
		Map<String, Integer> positionHistory = new HashMap<>();
		Game game = new Game(board, GameStatus.WHITE_TURN,
				moveHistory, null, 0,
				positionHistory);
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
		List<Move> moveHistory = new ArrayList<>();
		Map<String, Integer> positionHistory = new HashMap<>();
		Location blackFrom = new Location(1, 5);
		Location blackTo = new Location(3, 5);
		Move lastMove = new Move(
				blackFrom,
				blackTo,
				blackPawn,
				null,
				null,
				false,
				false,
				"PAWN (1,5) -> (3,5)"
		);
		Game game = new Game(
				board,
				GameStatus.WHITE_TURN,
				moveHistory,
				lastMove,
				0,
				positionHistory
		);
		Location whiteFrom = new Location(3, 4);
		Location whiteTo = new Location(2, 4);
		EasyMock.expect(whitePawn.getPieceType()).andReturn(PieceType.PAWN).anyTimes();
		EasyMock.expect(blackPawn.getPieceType()).andReturn(PieceType.PAWN).anyTimes();
		EasyMock.expect(whitePawn.getColor()).andReturn(PieceColor.WHITE).anyTimes();
		EasyMock.expect(blackPawn.getColor()).andReturn(PieceColor.BLACK).anyTimes();
		EasyMock.expect(board.isEmpty(whiteTo)).andReturn(true);
		EasyMock.replay(board, whitePawn, blackPawn);
		assertFalse(game.isEnPassantMove(whiteFrom, whiteTo, whitePawn));
		EasyMock.verify(board, whitePawn, blackPawn);
	}
	@Test
	public void isEnPassantMove_cross2Col_false() {
		Board board = EasyMock.createMock(Board.class);
		Piece whitePawn = EasyMock.createMock(Piece.class);
		Piece blackPawn = EasyMock.createMock(Piece.class);
		List<Move> moveHistory = new ArrayList<>();
		Map<String, Integer> positionHistory = new HashMap<>();
		Location blackFrom = new Location(1, 5);
		Location blackTo = new Location(3, 5);
		Move lastMove = new Move(
				blackFrom,
				blackTo,
				blackPawn,
				null,
				null,
				false,
				false,
				"PAWN (1,5) -> (3,5)"
		);
		Game game = new Game(
				board,
				GameStatus.WHITE_TURN,
				moveHistory,
				lastMove,
				0,
				positionHistory
		);
		Location whiteFrom = new Location(3, 4);
		Location whiteTo = new Location(2, 6);
		EasyMock.expect(whitePawn.getPieceType()).andReturn(PieceType.PAWN).anyTimes();
		EasyMock.expect(blackPawn.getPieceType()).andReturn(PieceType.PAWN).anyTimes();
		EasyMock.expect(whitePawn.getColor()).andReturn(PieceColor.WHITE).anyTimes();
		EasyMock.expect(blackPawn.getColor()).andReturn(PieceColor.BLACK).anyTimes();
		EasyMock.expect(board.isEmpty(whiteTo)).andReturn(true);
		EasyMock.replay(board, whitePawn, blackPawn);
		assertFalse(game.isEnPassantMove(whiteFrom, whiteTo, whitePawn));
		EasyMock.verify(board, whitePawn, blackPawn);
	}
	@Test
	public void isEnPassantMove_previousMove1Row_false() {
		Board board = EasyMock.createMock(Board.class);
		Piece whitePawn = EasyMock.createMock(Piece.class);
		Piece blackPawn = EasyMock.createMock(Piece.class);
		List<Move> moveHistory = new ArrayList<>();
		Map<String, Integer> positionHistory = new HashMap<>();
		Location whiteFrom = new Location(3, 4);
		Location whiteTo = new Location(2, 5);
		Location blackFrom = new Location(2, 5);
		Location blackTo = new Location(3, 5);
		Move lastMove = new Move(
				blackFrom,
				blackTo,
				blackPawn,
				null,
				null,
				false,
				false,
				"PAWN (2,5) -> (3,5)"
		);
		Game game = new Game(
				board,
				GameStatus.WHITE_TURN,
				moveHistory,
				lastMove,
				0,
				positionHistory
		);
		EasyMock.expect(whitePawn.getPieceType()).andReturn(PieceType.PAWN);
		EasyMock.expect(blackPawn.getPieceType()).andReturn(PieceType.PAWN);
		EasyMock.expect(blackPawn.getColor()).andReturn(PieceColor.BLACK);
		EasyMock.expect(whitePawn.getColor()).andReturn(PieceColor.WHITE);
		EasyMock.expect(board.isEmpty(whiteTo)).andReturn(true);
		EasyMock.expect(whitePawn.getColor()).andReturn(PieceColor.WHITE);
		EasyMock.replay(board, whitePawn, blackPawn);
		assertFalse(game.isEnPassantMove(whiteFrom, whiteTo, whitePawn));
		EasyMock.verify(board, whitePawn, blackPawn);
	}
	@Test
	public void isEnPassantMove_true() {
		Board board = EasyMock.createMock(Board.class);
		Piece whitePawn = EasyMock.createMock(Piece.class);
		Piece blackPawn = EasyMock.createMock(Piece.class);
		List<Move> moveHistory = new ArrayList<>();
		Map<String, Integer> positionHistory = new HashMap<>();
		Location whiteFrom = new Location(3, 4);
		Location whiteTo = new Location(2, 5);
		Location blackFrom = new Location(1, 5);
		Location blackTo = new Location(3, 5);
		Move lastMove = new Move(
				blackFrom,
				blackTo,
				blackPawn,
				null,
				null,
				false,
				false,
				"PAWN (1,5) -> (3,5)"
		);
		Game game = new Game(
				board,
				GameStatus.WHITE_TURN,
				moveHistory,
				lastMove,
				0,
				positionHistory
		);
		EasyMock.expect(whitePawn.getPieceType()).andReturn(PieceType.PAWN);
		EasyMock.expect(blackPawn.getPieceType()).andReturn(PieceType.PAWN);
		EasyMock.expect(blackPawn.getColor()).andReturn(PieceColor.BLACK);
		EasyMock.expect(whitePawn.getColor()).andReturn(PieceColor.WHITE);
		EasyMock.expect(board.isEmpty(whiteTo)).andReturn(true);
		EasyMock.expect(whitePawn.getColor()).andReturn(PieceColor.WHITE);
		EasyMock.replay(board, whitePawn, blackPawn);
		assertTrue(game.isEnPassantMove(whiteFrom, whiteTo, whitePawn));
		EasyMock.verify(board, whitePawn, blackPawn);
	}
}
