package ui;

import domain.Board;
import domain.Game;
import domain.GameStatus;
import domain.Player;
import domain.PieceColor;
import javax.swing.*;
import java.awt.BorderLayout;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Locale;

public final class ChessUI extends JFrame {

	private final Game game;
	private final ChessBoardView view;

	public ChessUI() {
		super("Team 27 Chess");

		Board board = new Board();
		board.initBoard();
		Player white = new Player("Player 1", PieceColor.WHITE);
		Player black = new Player("Player 2", PieceColor.BLACK);

		this.game = new Game(board,
				GameStatus.WHITE_TURN,
				new ArrayList<>(),
				null,
				0,
				new HashMap<>());
		this.game.startNewGame(white, black);

		this.view = new ChessBoardView();
		ChessController controller = new ChessController(this.game, this.view);

		this.view.setController(controller);
		this.view.updateBoardUI(board);

		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setSize(800, 800);

		this.setLayout(new BorderLayout());

		GameTimerPanel timerPanel = new GameTimerPanel(this.game, this.view);

		this.add(timerPanel, BorderLayout.NORTH);
		this.add(this.view, BorderLayout.CENTER);

		this.setLocationRelativeTo(null);
	}

	public static void main(String[] args) {
		SwingUtilities.invokeLater(() -> {
			String[] languages = {"English", "Français"};

			int choice = JOptionPane.showOptionDialog(
					null,
					"Select your game language / Choisissez la langue du jeu :",
					"Language Selection / Choix de la langue",
					JOptionPane.DEFAULT_OPTION,
					JOptionPane.QUESTION_MESSAGE,
					null,
					languages,
					languages[0]
			);

			if (choice == 1) {
				Locale.setDefault(new Locale("fr", "FR"));
			} else {
				Locale.setDefault(new Locale("en", "US"));
			}

			ChessUI mainUI = new ChessUI();
			mainUI.setVisible(true);
		});
	}
}