package domain;

public class Knight extends Piece{
	public Knight(Color color) {
		super(color);
	}

	@Override
	public PieceType getType() {

		return PieceType.KNIGHT;
	}

	@Override
	public Piece makeCopy() {
		return new Knight(getColor());
	}
	@Override
	public boolean equals(Object obj){
		if (this == obj) return true;
		if (obj == null || getClass() != obj.getClass()) return false;
		Knight queen = (Knight) obj;
		return getColor() == queen.getColor();
	}
}
