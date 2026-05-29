package domain;

public class Knight extends Piece {
    public Knight(Color color) {
        super(color);
    }

    @Override
    public String getType() {
        return "Knight";
    }

    @Override
    public Piece makeCopy() {
        return new Knight(getColor());
    }
}
