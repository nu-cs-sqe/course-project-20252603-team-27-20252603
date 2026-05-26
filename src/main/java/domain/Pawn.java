package domain;

public class Pawn extends Piece{
	public Pawn(Color color) {
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
}
