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

    @Test
    void ctor_rejectsNullFrom() {
        IllegalArgumentException ex = assertThrows(
                IllegalArgumentException.class,
                () -> new Move(null, new Location(4, 4), WHITE_PAWN, null, null)
        );
        assertTrue(ex.getMessage().contains("from"));
    }

    @Test
    void ctor_rejectsNullTo() {
        IllegalArgumentException ex = assertThrows(
                IllegalArgumentException.class,
                () -> new Move(new Location(6, 4), null, WHITE_PAWN, null, null)
        );
        assertTrue(ex.getMessage().contains("to"));
    }

    @Test
    void ctor_rejectsNullMovedPiece() {
        IllegalArgumentException ex = assertThrows(
                IllegalArgumentException.class,
                () -> new Move(new Location(6, 4), new Location(4, 4), null, null, null)
        );
        assertTrue(ex.getMessage().contains("movedPiece"));
    }

    @Test
    void ctor_storesCapturedPiece() {
        Location from = new Location(6, 4);
        Location to = new Location(5, 3);
        Piece captured = new Piece(PieceType.PAWN, PieceColor.BLACK);
        Move move = new Move(from, to, WHITE_PAWN, captured, null);

        assertEquals(captured, move.getCapturedPiece());
        assertNull(move.getPromotionType());
    }

    @Test
    void ctor_storesPromotionType() {
        Location from = new Location(1, 4);
        Location to = new Location(0, 4);
        Move move = new Move(from, to, WHITE_PAWN, null, PieceType.QUEEN);

        assertNull(move.getCapturedPiece());
        assertEquals(PieceType.QUEEN, move.getPromotionType());
    }

    @Test
    void extendedCtor_storesCastleFlag() {
        Piece whiteKing = new Piece(PieceType.KING, PieceColor.WHITE);
        Location from = new Location(7, 4);
        Location to = new Location(7, 6);
        Move move = new Move(from, to, whiteKing, null, null, true, false, "");

        assertTrue(move.isCastle());
        assertFalse(move.isEnPassant());
    }
}
