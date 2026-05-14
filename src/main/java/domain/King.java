package domain;

public class King extends Piece {
    public King(Color color) {

        super(color);
    }

    @Override
    public String getType() {

        return "King";
    }

    @Override
    public Piece makeCopy() {

        return new King(getColor());
    }
}
