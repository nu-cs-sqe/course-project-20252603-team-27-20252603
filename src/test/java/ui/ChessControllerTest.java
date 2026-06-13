package ui;

import domain.Board;
import domain.Game;
import domain.GameStatus;
import domain.Location;
import domain.MoveResult;
import domain.Piece;
import domain.PieceColor;
import domain.PieceType;
import org.easymock.EasyMock;
import org.junit.jupiter.api.Test;

public class ChessControllerTest {

	@Test
	public void testValidMoveSequence() {
		Game mockGame = EasyMock.createMock(Game.class);
		ChessBoardView mockView = EasyMock.createMock(ChessBoardView.class);
		Board mockBoard = EasyMock.createMock(Board.class);
		Piece whitePawn = new Piece(PieceType.PAWN, PieceColor.WHITE);

		EasyMock.expect(mockGame.getBoard()).andReturn(mockBoard).anyTimes();
		EasyMock.expect(mockGame.getStatus()).andReturn(GameStatus.WHITE_TURN).anyTimes();

		// -- THE FIX: Swapped to andStubReturn for double lookup safety --
		EasyMock.expect(mockBoard.getPiece(new Location(6, 0))).andStubReturn(whitePawn);
		mockView.highlightSquare(6, 0);
		EasyMock.expectLastCall();

		EasyMock.expect(mockBoard.getPiece(new Location(5, 0))).andStubReturn(new Piece(PieceType.EMPTY, null));

		// Notice we pass null here instead of PieceType.QUEEN because a basic move won't promote!
		EasyMock.expect(mockGame.makeMove(new Location(6, 0), new Location(5, 0), null))
				.andReturn(MoveResult.VALID);

		mockView.updateBoardUI(mockBoard);
		EasyMock.expectLastCall().times(2);

		// -- THE FIX: Stub the new Phase 4 End-Game evaluations --
		EasyMock.expect(mockGame.isCheckmate(EasyMock.anyObject(PieceColor.class))).andReturn(false).anyTimes();
		EasyMock.expect(mockGame.isInCheck(EasyMock.anyObject(PieceColor.class))).andReturn(false).anyTimes();

		EasyMock.replay(mockGame, mockView, mockBoard);

		ChessController controller = new ChessController(mockGame, mockView);
		controller.onSquareClicked(6, 0);
		controller.onSquareClicked(5, 0);

		EasyMock.verify(mockGame, mockView, mockBoard);
	}

	@Test
	public void testClickEmptySquareFirst() {
		Game mockGame = EasyMock.createMock(Game.class);
		ChessBoardView mockView = EasyMock.createMock(ChessBoardView.class);
		Board mockBoard = EasyMock.createMock(Board.class);

		EasyMock.expect(mockGame.getBoard()).andReturn(mockBoard).anyTimes();

		EasyMock.expect(mockBoard.getPiece(new Location(4, 4))).andReturn(new Piece(PieceType.EMPTY, null));

		EasyMock.replay(mockGame, mockView, mockBoard);

		ChessController controller = new ChessController(mockGame, mockView);
		controller.onSquareClicked(4, 4);

		EasyMock.verify(mockGame, mockView, mockBoard);
	}

	@Test
	public void testSwitchSelection() {
		Game mockGame = EasyMock.createMock(Game.class);
		ChessBoardView mockView = EasyMock.createMock(ChessBoardView.class);
		Board mockBoard = EasyMock.createMock(Board.class);
		Piece whitePawn1 = new Piece(PieceType.PAWN, PieceColor.WHITE);
		Piece whitePawn2 = new Piece(PieceType.PAWN, PieceColor.WHITE);

		EasyMock.expect(mockGame.getBoard()).andReturn(mockBoard).anyTimes();
		EasyMock.expect(mockGame.getStatus()).andReturn(GameStatus.WHITE_TURN).anyTimes();

		EasyMock.expect(mockBoard.getPiece(new Location(6, 0))).andReturn(whitePawn1);
		mockView.highlightSquare(6, 0);
		EasyMock.expectLastCall();

		EasyMock.expect(mockBoard.getPiece(new Location(6, 1))).andReturn(whitePawn2);
		mockView.highlightSquare(6, 1);
		EasyMock.expectLastCall();

		EasyMock.replay(mockGame, mockView, mockBoard);

		ChessController controller = new ChessController(mockGame, mockView);
		controller.onSquareClicked(6, 0);
		controller.onSquareClicked(6, 1);

		EasyMock.verify(mockGame, mockView, mockBoard);
	}

	@Test
	public void testDeselectSquareByClickingTwice() {
		Game mockGame = EasyMock.createMock(Game.class);
		ChessBoardView mockView = EasyMock.createMock(ChessBoardView.class);
		Board mockBoard = EasyMock.createMock(Board.class);
		Piece whitePawn = new Piece(PieceType.PAWN, PieceColor.WHITE);

		EasyMock.expect(mockGame.getBoard()).andReturn(mockBoard).anyTimes();
		EasyMock.expect(mockGame.getStatus()).andReturn(GameStatus.WHITE_TURN).anyTimes();

		EasyMock.expect(mockBoard.getPiece(new Location(6, 0))).andReturn(whitePawn).times(2);
		mockView.highlightSquare(6, 0);
		EasyMock.expectLastCall();

		mockView.updateBoardUI(mockBoard);
		EasyMock.expectLastCall();

		EasyMock.replay(mockGame, mockView, mockBoard);

		ChessController controller = new ChessController(mockGame, mockView);
		controller.onSquareClicked(6, 0);
		controller.onSquareClicked(6, 0);

		EasyMock.verify(mockGame, mockView, mockBoard);
	}

	@Test
	public void testPawnPromotionTrigger() {
		Game mockGame = EasyMock.createMock(Game.class);
		ChessBoardView mockView = EasyMock.createMock(ChessBoardView.class);
		Board mockBoard = EasyMock.createMock(Board.class);

		Piece whitePawn = new Piece(PieceType.PAWN, PieceColor.WHITE);

		EasyMock.expect(mockGame.getBoard()).andReturn(mockBoard).anyTimes();
		EasyMock.expect(mockGame.getStatus()).andReturn(GameStatus.WHITE_TURN).anyTimes();

		// -- STUB REMEDY FOR DOUBLE LOOKUP --
		EasyMock.expect(mockBoard.getPiece(new Location(1, 0))).andStubReturn(whitePawn);
		mockView.highlightSquare(1, 0);
		EasyMock.expectLastCall();

		EasyMock.expect(mockBoard.getPiece(new Location(0, 0))).andStubReturn(new Piece(PieceType.EMPTY, null));

		EasyMock.expect(mockView.promptForPromotion(true)).andReturn(PieceType.ROOK);

		EasyMock.expect(mockGame.makeMove(new Location(1, 0), new Location(0, 0), PieceType.ROOK))
				.andReturn(MoveResult.VALID);

		mockView.updateBoardUI(mockBoard);
		EasyMock.expectLastCall().times(2);

		// -- THE FIX: Stub the new Phase 4 End-Game evaluations --
		EasyMock.expect(mockGame.isCheckmate(EasyMock.anyObject(PieceColor.class))).andReturn(false).anyTimes();
		EasyMock.expect(mockGame.isInCheck(EasyMock.anyObject(PieceColor.class))).andReturn(false).anyTimes();

		EasyMock.replay(mockGame, mockView, mockBoard);

		ChessController controller = new ChessController(mockGame, mockView);
		controller.onSquareClicked(1, 0);
		controller.onSquareClicked(0, 0);

		EasyMock.verify(mockGame, mockView, mockBoard);
	}

	@Test
	public void testOnSquareClicked_triggersUIStalemateFlow() {
		Game mockGame = EasyMock.createMock(Game.class);
		ChessBoardView mockView = EasyMock.createMock(ChessBoardView.class);
		Board mockBoard = EasyMock.createMock(Board.class);

		Piece whiteQueen = new Piece(PieceType.QUEEN, PieceColor.WHITE);
		Location source = new Location(1, 0);
		Location destination = new Location(2, 0);

		EasyMock.expect(mockGame.getBoard()).andReturn(mockBoard).anyTimes();
		EasyMock.expect(mockGame.getStatus()).andReturn(GameStatus.WHITE_TURN).anyTimes();
		EasyMock.expect(mockBoard.getPiece(source)).andStubReturn(whiteQueen);
		EasyMock.expect(mockBoard.getPiece(destination)).andStubReturn(new Piece(PieceType.EMPTY, null));

		mockView.highlightSquare(1, 0);
		EasyMock.expectLastCall();

		EasyMock.expect(mockGame.makeMove(source, destination, null))
				.andReturn(MoveResult.STALEMATE);

		mockView.updateBoardUI(mockBoard);
		EasyMock.expectLastCall().times(2);

		EasyMock.expect(mockGame.isCheckmate(PieceColor.WHITE)).andReturn(false).anyTimes();
		EasyMock.expect(mockGame.isCheckmate(PieceColor.BLACK)).andReturn(false).anyTimes();

		ChessController controller = EasyMock.partialMockBuilder(ChessController.class)
				.withConstructor(Game.class, ChessBoardView.class)
				.withArgs(mockGame, mockView)
				.addMockedMethod("handleDraw", String.class)
				.createMock();

		controller.handleDraw(EasyMock.anyObject(String.class));
		EasyMock.expectLastCall().once();

		EasyMock.replay(mockGame, mockView, mockBoard, controller);

		controller.onSquareClicked(1, 0);
		controller.onSquareClicked(2, 0);

		EasyMock.verify(mockGame, mockView, mockBoard, controller);
	}
	@Test
	public void onSquareClicked_drawStatus_callsHandleDraw() {
		Game game = EasyMock.createMock(Game.class);
		Board board = EasyMock.createMock(Board.class);
		ChessBoardView view = EasyMock.createMock(ChessBoardView.class);
		Location source = new Location(7, 1);
		Location destination = new Location(5, 2);
		Piece whiteKnight =
				new Piece(PieceType.KNIGHT, PieceColor.WHITE);
		Piece empty =
				new Piece(PieceType.EMPTY, null);
		ChessController controller =
				EasyMock.partialMockBuilder(ChessController.class)
						.withConstructor(Game.class, ChessBoardView.class)
						.withArgs(game, view)
						.addMockedMethod("handleDraw", String.class)
						.createMock();
		EasyMock.expect(game.getBoard())
				.andStubReturn(board);
		EasyMock.expect(board.getPiece(source))
				.andStubReturn(whiteKnight);
		EasyMock.expect(board.getPiece(destination))
				.andStubReturn(empty);
		EasyMock.expect(game.getStatus())
				.andReturn(GameStatus.WHITE_TURN);
		view.highlightSquare(7, 1);
		EasyMock.expectLastCall();
		EasyMock.expect(game.makeMove(source, destination, null))
				.andReturn(MoveResult.DRAW);
		view.updateBoardUI(board);
		EasyMock.expectLastCall();
		EasyMock.expect(game.isCheckmate(PieceColor.WHITE))
				.andReturn(false);
		EasyMock.expect(game.isCheckmate(PieceColor.BLACK))
				.andReturn(false);
		EasyMock.expect(game.getStatus())
				.andReturn(GameStatus.DRAW);
		controller.handleDraw(EasyMock.anyString());
		EasyMock.expectLastCall();
		view.updateBoardUI(board);
		EasyMock.expectLastCall();
		EasyMock.replay(game, board, view, controller);
		controller.onSquareClicked(7, 1);
		controller.onSquareClicked(5, 2);
		EasyMock.verify(game, board, view, controller);
	}
}
