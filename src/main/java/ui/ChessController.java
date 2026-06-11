package ui;

import domain.Game;
import domain.GameStatus;
import domain.Location;
import domain.MoveResult;
import domain.Piece;
import domain.PieceColor;
import domain.PieceType;
import edu.umd.cs.findbugs.annotations.SuppressFBWarnings;

public class ChessController {

	private final Game game;
	private final ChessBoardView view;
	private Location selectedSource;

	@SuppressFBWarnings(
			value = "EI_EXPOSE_REP2",
			justification = "Controller needs reference to live Game and View."
	)
	public ChessController(Game game, ChessBoardView view) {
		this.game = game;
		this.view = view;
		this.selectedSource = null;
	}

	public void onSquareClicked(int row, int col) {
		Location clickedLocation = new Location(row, col);
		Piece clickedPiece = game.getBoard().getPiece(clickedLocation);

		if (selectedSource == null) {
			if (clickedPiece != null
					&& clickedPiece.getPieceType() != PieceType.EMPTY) {
				if (isCorrectTurn(clickedPiece.getColor())) {
					selectedSource = clickedLocation;
					view.highlightSquare(row, col);
				}
			}
		} else {

			if (selectedSource.equals(clickedLocation)) {
				clearSelection();
				return;
			}

			if (clickedPiece != null
					&& clickedPiece.getPieceType() != PieceType.EMPTY
					&& isCorrectTurn(clickedPiece.getColor())) {
				selectedSource = clickedLocation;
				view.highlightSquare(row, col);
				return;
			}

			Piece movingPiece = game.getBoard().getPiece(selectedSource);
			PieceType promotionType = null;

			if (movingPiece.getPieceType() == PieceType.PAWN &&
					(clickedLocation.getRow() == 0 || clickedLocation.getRow() == 7)) {
				boolean isWhite = movingPiece.getColor() == PieceColor.WHITE;
				promotionType = view.promptForPromotion(isWhite);
			}
			MoveResult result = game.makeMove(selectedSource, clickedLocation, promotionType);

			if (isSuccessfulMove(result)) {
				view.updateBoardUI(game.getBoard());
			} else {
				System.out.println("Invalid Move: " + result);
			}

			clearSelection();
		}
	}

	private void clearSelection() {
		selectedSource = null;
		view.updateBoardUI(game.getBoard());
	}

	private boolean isCorrectTurn(PieceColor pieceColor) {
		GameStatus status = game.getStatus();
		boolean isWhiteTurn = (status == GameStatus.WHITE_TURN
				|| status == GameStatus.WHITE_IN_CHECK);
		boolean isBlackTurn = (status == GameStatus.BLACK_TURN
				|| status == GameStatus.BLACK_IN_CHECK);

		return (isWhiteTurn && pieceColor == PieceColor.WHITE) ||
				(isBlackTurn && pieceColor == PieceColor.BLACK);
	}

	private boolean isSuccessfulMove(MoveResult result) {
		return result == MoveResult.VALID ||
				result == MoveResult.CHECK ||
				result == MoveResult.CHECKMATE ||
				result == MoveResult.DRAW ||
				result == MoveResult.STALEMATE;
	}
}
