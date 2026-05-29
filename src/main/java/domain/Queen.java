package domain;

public class Queen extends Piece {
    public Queen(Color color) {
        super(color);
    }

    @Override
    public String getType() {
        return "Queen";
    }

    @Override
    public Piece makeCopy() {
        return new Queen(getColor());
    }
}
