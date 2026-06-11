package ui;

import domain.Game;
import domain.GameStatus;
import domain.PieceColor;
import domain.Player; // We need this to recreate the players on reset

import javax.swing.*;
import java.awt.*;
import java.util.ResourceBundle;
import java.awt.Window;

public class GameTimerPanel extends JPanel {

	private final Game game;
	private final ChessBoardView view;
	private final JLabel whiteTimerLabel;
	private final JLabel blackTimerLabel;
	private final ResourceBundle messages;

	private static final int TURN_TIME_SECONDS = 60;
	private int whiteTimeRemaining = TURN_TIME_SECONDS;
	private int blackTimeRemaining = TURN_TIME_SECONDS;

	private PieceColor currentTurnColor = PieceColor.WHITE;

	private final Timer countdownTimer;

	public GameTimerPanel(Game game, ChessBoardView view) {
		this.game = game;
		this.view = view;
		this.messages = ResourceBundle.getBundle("Messages");

		this.setLayout(new GridLayout(1, 2, 20, 0));
		this.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
		this.setBackground(new Color(50, 50, 50));

		Font timerFont = new Font("SansSerif", Font.BOLD, 20);

		whiteTimerLabel = new JLabel(formatTimeDisplay(PieceColor.WHITE, whiteTimeRemaining));
		whiteTimerLabel.setFont(timerFont);
		whiteTimerLabel.setForeground(Color.WHITE);
		whiteTimerLabel.setHorizontalAlignment(SwingConstants.CENTER);

		blackTimerLabel = new JLabel(formatTimeDisplay(PieceColor.BLACK, blackTimeRemaining));
		blackTimerLabel.setFont(timerFont);
		blackTimerLabel.setForeground(Color.LIGHT_GRAY);
		blackTimerLabel.setHorizontalAlignment(SwingConstants.CENTER);

		this.add(whiteTimerLabel);
		this.add(blackTimerLabel);

		this.countdownTimer = new Timer(1000, e -> tickSeconds());
		this.countdownTimer.start();
	}

	private void tickSeconds() {
		GameStatus currentStatus = game.getStatus();

		boolean isWhiteTurn = (currentStatus == GameStatus.WHITE_TURN || currentStatus == GameStatus.WHITE_IN_CHECK);
		boolean isBlackTurn = (currentStatus == GameStatus.BLACK_TURN || currentStatus == GameStatus.BLACK_IN_CHECK);

		if (!isWhiteTurn && !isBlackTurn) {
			return;
		}

		PieceColor activeColor = isWhiteTurn ? PieceColor.WHITE : PieceColor.BLACK;


		if (activeColor != currentTurnColor) {
			whiteTimeRemaining = TURN_TIME_SECONDS;
			blackTimeRemaining = TURN_TIME_SECONDS;
			currentTurnColor = activeColor;

			whiteTimerLabel.setText(formatTimeDisplay(PieceColor.WHITE, whiteTimeRemaining));
			blackTimerLabel.setText(formatTimeDisplay(PieceColor.BLACK, blackTimeRemaining));
		}

		if (isWhiteTurn) {
			whiteTimeRemaining--;
			whiteTimerLabel.setText(formatTimeDisplay(PieceColor.WHITE, whiteTimeRemaining));
			if (whiteTimeRemaining <= 0) {
				handleTimeOut(PieceColor.WHITE);
			}
		} else if (isBlackTurn) {
			blackTimeRemaining--;
			blackTimerLabel.setText(formatTimeDisplay(PieceColor.BLACK, blackTimeRemaining));
			if (blackTimeRemaining <= 0) {
				handleTimeOut(PieceColor.BLACK);
			}
		}
	}

	private void handleTimeOut(PieceColor losingColor) {
		countdownTimer.stop();

		String message;
		if (losingColor == PieceColor.WHITE) {
			message = messages.getString("timer.timeout.blackwins");
		} else {
			message = messages.getString("timer.timeout.whitewins");
		}

		String title = messages.getString("timer.timeout.title");

		int choice = JOptionPane.showConfirmDialog(
				this,
				message,
				title,
				JOptionPane.YES_NO_OPTION,
				JOptionPane.QUESTION_MESSAGE
		);

		if (choice == JOptionPane.YES_OPTION) {
			Window currentWindow = SwingUtilities.getWindowAncestor(this);
			if (currentWindow != null) {
				currentWindow.dispose();
			}

			ChessUI freshGame = new ChessUI();
			freshGame.setVisible(true);

		} else {
			System.exit(0);
		}
	}

	private String formatTimeDisplay(PieceColor color, int totalSeconds) {
		if (totalSeconds < 0) totalSeconds = 0;
		int minutes = totalSeconds / 60;
		int seconds = totalSeconds % 60;

		String labelPrefix = (color == PieceColor.WHITE)
				? messages.getString("timer.white")
				: messages.getString("timer.black");

		return String.format("%s %02d:%02d", labelPrefix, minutes, seconds);
	}
}