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
}
