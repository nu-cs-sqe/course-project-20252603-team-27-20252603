package domain;

public class Rook extends Piece {
    public Rook(Color color) {
        super(color);
    }

    @Override
    public String getType() {

        return "Rook";
    }

    @Override
    public Piece makeCopy() {
        return new Rook(getColor());
    }
}
