package ui;

import domain.Game;
import domain.GameStatus;
import domain.Location;
import domain.MoveResult;
import domain.Piece;
import domain.PieceColor;
import domain.PieceType;
import edu.umd.cs.findbugs.annotations.SuppressFBWarnings;
import java.util.ResourceBundle;
import java.awt.Window;
import javax.swing.SwingUtilities;
import javax.swing.JOptionPane;

public class ChessController {

	private final Game game;
	private final ChessBoardView view;
	private Location selectedSource;
	private final ResourceBundle messages;

	@SuppressFBWarnings(
			value = "EI_EXPOSE_REP2",
			justification = "Controller needs reference to live Game and View."
	)
	public ChessController(Game game, ChessBoardView view) {
		this.game = game;
		this.view = view;
		this.selectedSource = null;
		this.messages = ResourceBundle.getBundle("Messages");
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
				boolean whiteMated = game.isCheckmate(PieceColor.WHITE);
				boolean blackMated = game.isCheckmate(PieceColor.BLACK);

				if (whiteMated || blackMated) {
					PieceColor winner = whiteMated ? PieceColor.BLACK : PieceColor.WHITE;
					handleCheckmate(winner);
				}
				else if (game.isInCheck(PieceColor.WHITE) || game.isInCheck(PieceColor.BLACK)) {
					view.showTemporaryMessage(messages.getString("status.check"));
				}
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

	private void handleCheckmate(PieceColor winnerColor) {
		String winnerName = (winnerColor == PieceColor.WHITE)
				? messages.getString("color.white")
				: messages.getString("color.black");

		String checkmateText = String.format(messages.getString("status.checkmate"), winnerName);
		String fullMessage = checkmateText + "\n\n" + messages.getString("status.playagain");

		int choice = JOptionPane.showConfirmDialog(
				null,
				fullMessage,
				messages.getString("status.gameover"),
				JOptionPane.YES_NO_OPTION,
				JOptionPane.WARNING_MESSAGE
		);

		if (choice == JOptionPane.YES_OPTION) {
			Window currentWindow = SwingUtilities.getWindowAncestor(view);
			if (currentWindow != null) {
				currentWindow.dispose();
			}
			new ChessUI().setVisible(true);
		} else {
			System.exit(0);
		}
	}
}
