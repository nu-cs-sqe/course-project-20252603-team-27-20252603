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

		EasyMock.expect(mockBoard.getPiece(new Location(6, 0))).andReturn(whitePawn);
		mockView.highlightSquare(6, 0);
		EasyMock.expectLastCall();

		EasyMock.expect(mockBoard.getPiece(new Location(5, 0))).andReturn(new Piece(PieceType.EMPTY, null));

		EasyMock.expect(mockGame.makeMove(new Location(6, 0), new Location(5, 0), PieceType.QUEEN))
				.andReturn(MoveResult.VALID);

		mockView.updateBoardUI(mockBoard);
		EasyMock.expectLastCall().times(2);

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

		EasyMock.expect(mockBoard.getPiece(new Location(1, 0))).andStubReturn(whitePawn);
		mockView.highlightSquare(1, 0);
		EasyMock.expectLastCall();

		EasyMock.expect(mockBoard.getPiece(new Location(0, 0))).andReturn(new Piece(PieceType.EMPTY, null));

		EasyMock.expect(mockView.promptForPromotion(true)).andReturn(PieceType.ROOK);

		EasyMock.expect(mockGame.makeMove(new Location(1, 0), new Location(0, 0), PieceType.ROOK))
				.andReturn(MoveResult.VALID);

		mockView.updateBoardUI(mockBoard);
		EasyMock.expectLastCall().times(2);

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
}
