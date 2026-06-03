package domain;

public class Location {
	private int row;
	private int col;

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

	public boolean equals(Location otherLocation) {
		if (otherLocation == null) return false;
		return this.row == otherLocation.row && this.col == otherLocation.col;
	}
}
