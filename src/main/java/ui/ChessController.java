package ui;

import domain.Game;
import domain.Location;
import domain.MoveResult;

public class ChessController {
	private final Game game;
	private final ChessBoardView view;
	private Location selectedSource;

	public ChessController(Game game, ChessBoardView view) {
		this.game = game;
		this.view = view;
		this.selectedSource = null;
	}

	public void onSquareClicked(int row, int col) {
		Location clickedLocation = new Location(row, col);

		if (selectedSource == null) {
			selectedSource = clickedLocation;
			System.out.println("Selected source: " + selectedSource);

		} else {
			Location destination = clickedLocation;
			System.out.println("Attempting to move to: " + destination);

			MoveResult result = game.makeMove(selectedSource, destination, null);

			if (result == MoveResult.VALID
					|| result == MoveResult.CHECK
					|| result == MoveResult.CHECKMATE
					|| result == MoveResult.DRAW) {
				view.updateBoardUI(game.getBoard());
			} else {
				System.out.println("Invalid Move: " + result);
			}

			// Clear the selection for the next turn
			selectedSource = null;
		}
	}
}
