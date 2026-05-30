package domain;

import java.util.Objects;

public final class Location {
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
	public boolean equals(Object other) {
		if (this == other) {
			return true;
		}
		if (!(other instanceof Location)) {
			return false;
		}
		Location that = (Location) other;
		return row == that.row && col == that.col;
	}

	@Override
	public int hashCode() {
		return Objects.hash(row, col);
	}
}
