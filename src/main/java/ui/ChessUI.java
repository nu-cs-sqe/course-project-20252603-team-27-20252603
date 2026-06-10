package ui;

import domain.Board;
import domain.Game;
import domain.GameStatus;
import domain.Player;
import domain.PieceColor;
import javax.swing.*;
import java.util.ArrayList;
import java.util.HashMap;

public class ChessUI extends JFrame {

	public ChessUI() {
		super("Team 27 Chess");

		// 1. Initialize the pristine backend
		Board board = new Board();
		board.initBoard();
		Player white = new Player("Player 1", PieceColor.WHITE);
		Player black = new Player("Player 2", PieceColor.BLACK);
		Game game = new Game(board,
				GameStatus.WHITE_TURN,
				new ArrayList<>(),
				null,
				0,
				new HashMap<>());
		game.startNewGame(white, black);

		// 2. Initialize the UI components
		ChessBoardView view = new ChessBoardView();
		ChessController controller = new ChessController(game, view);

		// 3. Wire them together
		view.setController(controller);
		view.updateBoardUI(board); // Draw the starting layout

		// 4. Setup the Window
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setSize(800, 800);
		this.add(view); // Add the board to the window
		this.setLocationRelativeTo(null); // Center on screen
	}

	public static void main(String[] args) {
		// The Golden Rule of Swing: Always start the UI on the Event Dispatch Thread!
		SwingUtilities.invokeLater(() -> {
			ChessUI window = new ChessUI();
			window.setVisible(true);
		});
	}
}
