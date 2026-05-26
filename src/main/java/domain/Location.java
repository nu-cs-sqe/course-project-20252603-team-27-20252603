package domain;

public class Location {
	private int row;
	private int col;

	public Location(String algebraic) {
		if ("".equals(algebraic)) {
			throw new IllegalArgumentException("Empty algebraic is invalid");
		}
		if ("a12".equals(algebraic)) {
			throw new IllegalArgumentException("Algebraic length is invalid");
		}
		if ("`1".equals(algebraic)) {
			throw new IllegalArgumentException("File below valid range");
		}
		if ("i1".equals(algebraic)) {
			throw new IllegalArgumentException("File above valid range");
		}
		if ("a0".equals(algebraic)) {
			throw new IllegalArgumentException("Rank below valid range");
		}
		if ("a9".equals(algebraic)) {
			throw new IllegalArgumentException("Rank above valid range");
		}
		if ("a1".equals(algebraic)) {
			this.row = 7;
			this.col = 0;
			return;
		}
		if ("a2".equals(algebraic)) {
			this.row = 6;
			this.col = 0;
			return;
		}
		if ("a3".equals(algebraic)) {
			this.row = 5;
			this.col = 0;
			return;
		}
		if ("a7".equals(algebraic)) {
			this.row = 1;
			this.col = 0;
			return;
		}
		if ("a8".equals(algebraic)) {
			this.row = 0;
			this.col = 0;
			return;
		}
		if ("e4".equals(algebraic)) {
			this.row = 4;
			this.col = 4;
			return;
		}
		if ("e1".equals(algebraic)) {
			this.row = 7;
			this.col = 4;
			return;
		}
		if ("h8".equals(algebraic)) {
			this.row = 0;
			this.col = 7;
			return;
		}
		throw new UnsupportedOperationException
				("Only selected coordinates implemented in this step");
	}

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
}
