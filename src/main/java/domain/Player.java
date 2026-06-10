package domain;

public final class Player {
	private final String name;
	private PieceColor color;

	public Player(String name, PieceColor color) {
		if (name == null || name.trim().isEmpty()) {
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

	public void setColor(PieceColor color) {
		if (color == null) {
			throw new IllegalArgumentException("Color cannot be null");
		}
		this.color = color;
	}

	public String getName() {
		return name;
	}
}