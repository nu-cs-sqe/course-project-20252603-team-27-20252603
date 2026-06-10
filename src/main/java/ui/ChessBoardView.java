package ui;

import domain.*;
import edu.umd.cs.findbugs.annotations.SuppressFBWarnings;

import javax.swing.*;
import java.awt.*;

public class ChessBoardView extends JPanel {

	private ChessController controller;
	private final JButton[][] squareButtons;

	private final Color lightSquareColor = new Color(240, 217, 181);
	private final Color darkSquareColor = new Color(154, 83, 23);
	private final Color highlightColor = new Color(186, 202, 68);

	public ChessBoardView() {
		this.squareButtons = new JButton[8][8];
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
}
