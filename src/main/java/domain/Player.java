package domain;

public class Player {
	private String name;
	private PieceColor color;

	Player(String name, PieceColor color) {
		this.name = name;
		this.color = color;
	}

	public PieceColor getColor() {
		return color;
	}
}
