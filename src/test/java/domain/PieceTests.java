package domain;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import org.easymock.EasyMock;


class PieceTests {

    private Board board;

    @BeforeEach
    void setUp() {
        board = EasyMock.createMock(Board.class);
    }

    @Test
    void PTC1_pawnOneForward_hasMoved_emptyDest() {
        Piece pawn = new Piece(PieceType.PAWN, PieceColor.WHITE);
        pawn.setMoved(true);
        assertTrue(pawn.canMove(board, new Location(2, 0), new Location(3, 0)));
    }

    @Test
    void PTC2_pawnOneForward_hasNotMoved_emptyDest() {
        Piece pawn = new Piece(PieceType.PAWN, PieceColor.WHITE);
        pawn.setMoved(false);
        assertTrue(pawn.canMove(board, new Location(1, 0), new Location(2, 0)));
    }

    @Test
    void PTC3_pawnTwoForward_hasNotMoved_emptyDest() {
        Piece pawn = new Piece(PieceType.PAWN, PieceColor.WHITE);
        pawn.setMoved(false);
        assertTrue(pawn.canMove(board, new Location(1, 0), new Location(3, 0)));
    }
}

