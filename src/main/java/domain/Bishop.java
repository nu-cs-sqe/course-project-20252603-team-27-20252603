package domain;

public class Bishop extends Piece{
	public Bishop(Color color) {
		super(color);
	}

	@Override
	public PieceType getType() {

		return PieceType.BISHOP;
	}

//	@Override
////	public boolean equals(Object obj) {
////		return true;
////	}

	@Override
	public Piece makeCopy() {
		return new Bishop(getColor());
	}
	@Override
	public boolean equals(Object obj){
		if (this == obj) return true;
		if (obj == null || getClass() != obj.getClass()) return false;
		Bishop queen = (Bishop) obj;
		return getColor() == queen.getColor();
	}
}
