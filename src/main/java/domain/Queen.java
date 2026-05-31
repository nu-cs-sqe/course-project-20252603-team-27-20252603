package domain;

public class Queen extends Piece{
	public Queen(Color color) {
		super(color);
	}

	@Override
	public PieceType getType() {

		return PieceType.QUEEN;
	}

	@Override
	public Piece makeCopy() {
		return new Queen(getColor());
	}
	@Override
	public boolean equals(Object obj){
		if (this == obj) return true;
		if (obj == null || getClass() != obj.getClass()) return false;
		Queen queen = (Queen) obj;
		return getColor() == queen.getColor();
	}
	@Override
	public int hashCode() {
		return getColor().hashCode();
	}
}
