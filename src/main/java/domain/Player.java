package domain;

public class Player {
	private String name;
	private PieceColor color;

	Player(String name, PieceColor color) {
		if (color == null) {
			throw new IllegalArgumentException("Color cannot be null");
		}

		this.name = name;
		this.color = color;
	}

	public PieceColor getColor() {
		return color;
	}
}
