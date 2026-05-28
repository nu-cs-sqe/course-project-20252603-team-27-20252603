package domain;

import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import org.easymock.EasyMock;

import static java.lang.Boolean.TRUE;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

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
	@Test
	public void startNewGame_samePlayer() {
		Player player1 = EasyMock.createMock(Player.class);
		Player player2 = EasyMock.createMock(Player.class);
		Board board = EasyMock.createMock(Board.class);
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
		Game game = new Game(board, status, moveHistory, lastMove, halfMoveClock);
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
		EasyMock.replay(board);
		EasyMock.expect(rook.canMove(board,source,destination)).andStubReturn(TRUE);
		EasyMock.expect(rook.getType()).andStubReturn(PieceType.ROOK);
		EasyMock.expect(rook.getColor()).andStubReturn(Color.WHITE);
		EasyMock.replay(rook);
		GameStatus status = GameStatus.WHITE_TURN;
		List<Move> moveHistory = new ArrayList<>();
		Move lastMove = null;
		int halfMoveClock = 0;
		Game game = EasyMock.partialMockBuilder(Game.class)
				.withConstructor(
						Board.class,
						GameStatus.class,
						List.class,
						Move.class,
						int.class)
				.withArgs(
						board,
						GameStatus.WHITE_TURN,
						moveHistory,
						null,
						0)
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
		Move lastMove = null;
		int halfMoveClock = 0;
		Game game = EasyMock.partialMockBuilder(Game.class)
				.withConstructor(
						Board.class,
						GameStatus.class,
						List.class,
						Move.class,
						int.class)
				.withArgs(
						board,
						GameStatus.WHITE_TURN,
						moveHistory,
						null,
						0)
				.addMockedMethod("isInCheck", Color.class)
				.createMock();
		Location from = new Location(7, 0);
		Location to = new Location(0, 0);
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
		Move lastMove = null;
		int halfMoveClock = 0;
		Game game = EasyMock.partialMockBuilder(Game.class)
				.withConstructor(
						Board.class,
						GameStatus.class,
						List.class,
						Move.class,
						int.class)
				.withArgs(
						board,
						GameStatus.WHITE_TURN,
						moveHistory,
						null,
						0)
				.addMockedMethod("isInCheck", Color.class)
				.addMockedMethod("isCheckmate", Color.class)
				.createMock();
		Location from = new Location(0, 0);
		Location to = new Location(7, 0);
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
		Move lastMove = null;
		int halfMoveClock = 0;
		Game game = EasyMock.partialMockBuilder(Game.class)
				.withConstructor(
						Board.class,
						GameStatus.class,
						List.class,
						Move.class,
						int.class)
				.withArgs(
						board,
						GameStatus.WHITE_TURN,
						moveHistory,
						null,
						0)
				.addMockedMethod("isInCheck", Color.class)
				.addMockedMethod("isCheckmate", Color.class)
				.addMockedMethod("isStalemate", Color.class)
				.createMock();
		Location from = new Location(0, 0);
		Location to = new Location(7, 7);
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
		Move lastMove = null;
		int halfMoveClock = 0;
		Game game = new Game(
						board,
						GameStatus.WHITE_TURN,
						moveHistory,
						null,
						0);
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
		Move lastMove = null;
		int halfMoveClock = 0;
		Game game = new Game(
				board,
				GameStatus.WHITE_TURN,
				moveHistory,
				null,
				0);
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
		Move lastMove = null;
		int halfMoveClock = 0;
		Game game = new Game(
				board,
				GameStatus.WHITE_TURN,
				moveHistory,
				null,
				0);
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
		assertEquals(MoveResult.INVALID_WRONG_TURN, result);
		EasyMock.verify(board, player1, player2, piece);
	}
}
