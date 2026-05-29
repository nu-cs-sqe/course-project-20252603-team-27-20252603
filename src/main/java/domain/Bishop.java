package domain;

public class Bishop extends Piece {
	public Bishop(Color color) {
		super(color);
	}

	@Override
	public String getType() {
		return "Bishop";
	}

	@Override
	public Piece makeCopy() {
		return new Bishop(getColor());
	}
}
