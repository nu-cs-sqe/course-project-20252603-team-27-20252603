package domain;

public class Location {
	private final int row;
	private final int col;

	public Location(int row, int col) {
		this.row = row;
		this.col = col;
	}

	public int getRow() {
		return row;
	}

	public int getCol() {
		return col;
	}

	@Override
	public boolean equals(Object obj) {
		if (obj == null) return false;
		if (!(obj instanceof Location)) return false;
		Location other = (Location) obj;
		return this.row == other.row && this.col == other.col;
	}

	@Override
	public int hashCode() {
		return 31 * row + col;
	}
}
