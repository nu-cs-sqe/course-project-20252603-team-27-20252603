package domain;

public class Rook extends Piece{
	public Rook(Color color) {
		super(color);
	}

	@Override
	public PieceType getType() {

		return PieceType.ROOK;
	}

	@Override
	public Piece makeCopy() {
		return new Rook(getColor());
	}
	@Override
	public boolean equals(Object obj){
		if (this == obj) return true;
		if (obj == null || getClass() != obj.getClass()) return false;
		Rook queen = (Rook) obj;
		return getColor() == queen.getColor();
	}
	@Override
	public int hashCode() {
		return getColor().hashCode();
	}
}
