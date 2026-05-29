package domain;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class MoveTest {
    private static final Piece WHITE_PAWN = new Piece(PieceType.PAWN, PieceColor.WHITE);

    @Test
    void ctor_acceptsRequiredFields() {
        Location from = new Location(6, 4);
        Location to = new Location(4, 4);
        Move move = new Move(from, to, WHITE_PAWN, null, null);

        assertEquals(from, move.getFrom());
        assertEquals(to, move.getTo());
        assertEquals(WHITE_PAWN, move.getMovedPiece());
        assertNull(move.getCapturedPiece());
        assertNull(move.getPromotionType());
        assertFalse(move.isCastle());
        assertFalse(move.isEnPassant());
        assertEquals("", move.getNotation());
    }
}
