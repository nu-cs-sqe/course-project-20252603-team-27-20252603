package domain;

public class Player {
	private String name;
	private PieceColor color;

	Player(String name, PieceColor color) {
		if (name == null || name.isEmpty()) {
			throw new IllegalArgumentException("Name cannot be null or empty");
		}
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
