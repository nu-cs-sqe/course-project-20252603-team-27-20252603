package ui;

import domain.*;
import edu.umd.cs.findbugs.annotations.SuppressFBWarnings;

import javax.swing.*;
import java.awt.*;

import java.util.ResourceBundle;
import java.util.Locale;

public class ChessBoardView extends JPanel {

	private ChessController controller;
	private final JButton[][] squareButtons;

	private final Color lightSquareColor = new Color(240, 217, 181);
	private final Color darkSquareColor = new Color(154, 83, 23);
	private final Color highlightColor = new Color(186, 202, 68);
	private final ResourceBundle messages;

	public ChessBoardView() {
		this.squareButtons = new JButton[8][8];
		this.messages = ResourceBundle.getBundle("Messages");
		initializeGrid();
	}

	private void initializeGrid() {
		this.setLayout(new GridLayout(8, 8));
		Font pieceFont = new Font("SansSerif", Font.PLAIN, 60);

		for (int row = 0; row < 8; row++) {
			for (int col = 0; col < 8; col++) {
				JButton button = new JButton();
				button.setFont(pieceFont);
				button.setFocusPainted(false);
				button.setOpaque(true);
				button.setBorderPainted(false);

				if ((row + col) % 2 == 0) {
					button.setBackground(lightSquareColor);
				} else {
					button.setBackground(darkSquareColor);
				}

				final int r = row;
				final int c = col;

				button.addActionListener(e -> {
					if (controller != null) {
						controller.onSquareClicked(r, c);
					}
				});

				squareButtons[row][col] = button;
				this.add(button);
			}
		}
	}

	public void updateBoardUI(Board board) {
		for (int row = 0; row < 8; row++) {
			for (int col = 0; col < 8; col++) {
				Piece piece = board.getPiece(new Location(row, col));
				JButton button = squareButtons[row][col];

				if ((row + col) % 2 == 0) {
					button.setBackground(lightSquareColor);
				} else {
					button.setBackground(darkSquareColor);
				}
				if (piece == null || piece.getPieceType() == PieceType.EMPTY) {
					button.setText("");
				} else {
					button.setText(getPieceSymbol(piece));
				}
			}
		}
	}

	private String getPieceSymbol(Piece piece) {
		boolean isWhite = piece.getColor() == PieceColor.WHITE;

		switch (piece.getPieceType()) {
			case KING:   return isWhite ? "♔" : "♚";
			case QUEEN:  return isWhite ? "♕" : "♛";
			case ROOK:   return isWhite ? "♖" : "♜";
			case BISHOP: return isWhite ? "♗" : "♝";
			case KNIGHT: return isWhite ? "♘" : "♞";
			case PAWN:   return isWhite ? "♙" : "♟";
			default:     return "";
		}
	}

	@SuppressFBWarnings(
			value = "EI_EXPOSE_REP2",
			justification = "View requires a direct reference to live Controller."
	)
	public void setController(ChessController controller) {
		this.controller = controller;
	}

	public void highlightSquare(int row, int col) {
		squareButtons[row][col].setBackground(highlightColor);
	}

	public PieceType promptForPromotion(boolean isWhite) {
		String title = messages.getString("promotion.title");
		String message = messages.getString("promotion.message");

		String queenLabel = messages.getString("piece.queen");
		String rookLabel = messages.getString("piece.rook");
		String bishopLabel = messages.getString("piece.bishop");
		String knightLabel = messages.getString("piece.knight");

		String[] options = isWhite
				? new String[]{"♕ " + queenLabel, "♖ " + rookLabel, "♗ " + bishopLabel, "♘ " + knightLabel}
				: new String[]{"♛ " + queenLabel, "♜ " + rookLabel, "♝ " + bishopLabel, "♞ " + knightLabel};

		int choice = JOptionPane.showOptionDialog(
				this,
				message,
				title,
				JOptionPane.DEFAULT_OPTION,
				JOptionPane.QUESTION_MESSAGE,
				null,
				options,
				options[0]
		);

		switch (choice) {
			case 1: return PieceType.ROOK;
			case 2: return PieceType.BISHOP;
			case 3: return PieceType.KNIGHT;
			default: return PieceType.QUEEN;
		}
	}
}
