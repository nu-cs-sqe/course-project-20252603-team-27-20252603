package ui;

import domain.Board;
import domain.Piece;
import javax.swing.*;
import java.awt.*;

public class ChessBoardView extends JPanel {

	private ChessController controller;
	private final JButton[][] squareButtons; // The 8x8 visual grid

	public ChessBoardView() {
		this.squareButtons = new JButton[8][8];
		initializeGrid();
	}

	private void initializeGrid() {
		this.setLayout(new GridLayout(8, 8));

		for (int row = 0; row < 8; row++) {
			for (int col = 0; col < 8; col++) {
				JButton button = new JButton();

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
			}
		}
	}

	public void setController(ChessController controller) {
		this.controller = controller;
	}
}
