package domain;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import org.easymock.EasyMock;


class PieceTests {

    private Board board;

    @BeforeEach
    void setUp() {
        board = EasyMock.createNiceMock(Board.class);
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

    @Test
    void PTC4_pawnOneDiagonalForwardRight_hasMoved_foeDest() {
        Piece pawn = new Piece(PieceType.PAWN, PieceColor.WHITE);
        Piece foePawn = new Piece(PieceType.PAWN, PieceColor.BLACK);
        pawn.setMoved(true);

        EasyMock.expect(board.getPiece(EasyMock.anyObject(Location.class))).andReturn(foePawn);
        EasyMock.replay(board);

        assertTrue(pawn.canMove(board, new Location(2, 1), new Location(3, 2)));

        EasyMock.verify(board);
    }

    @Test
    void PTC5_pawnOneDiagonalForwardLeft_hasMoved_foeDest() {
        Piece pawn = new Piece(PieceType.PAWN, PieceColor.WHITE);
        Piece foePawn = new Piece(PieceType.PAWN, PieceColor.BLACK);
        pawn.setMoved(true);

        EasyMock.expect(board.getPiece(EasyMock.anyObject(Location.class))).andReturn(foePawn);
        EasyMock.replay(board);

        assertTrue(pawn.canMove(board, new Location(2, 1), new Location(3, 0)));

        EasyMock.verify(board);
    }

    @Test
    void PTC6_pawnOneDiagonalForwardRight_hasNotMoved_foeDest() {
        Piece pawn = new Piece(PieceType.PAWN, PieceColor.WHITE);
        Piece foePawn = new Piece(PieceType.PAWN, PieceColor.BLACK);
        pawn.setMoved(false);

        EasyMock.expect(board.getPiece(EasyMock.anyObject(Location.class))).andReturn(foePawn);
        EasyMock.replay(board);

        assertTrue(pawn.canMove(board, new Location(1, 1), new Location(2, 2)));

        EasyMock.verify(board);
    }

    @Test
    void PTC7_pawnOneDiagonalForwardLeft_hasNotMoved_foeDest() {
        Piece pawn = new Piece(PieceType.PAWN, PieceColor.WHITE);
        Piece foePawn = new Piece(PieceType.PAWN, PieceColor.BLACK);
        pawn.setMoved(false);

        EasyMock.expect(board.getPiece(EasyMock.anyObject(Location.class))).andReturn(foePawn);
        EasyMock.replay(board);

        assertTrue(pawn.canMove(board, new Location(1, 1), new Location(2, 0)));

        EasyMock.verify(board);
    }

    @Test
    void PTC8_pawnOneForward_hasMoved_foeDest() {
        Piece pawn = new Piece(PieceType.PAWN, PieceColor.WHITE);
        Piece foePawn = new Piece(PieceType.PAWN, PieceColor.BLACK);
        pawn.setMoved(true);
        EasyMock.expect(board.getPiece(EasyMock.anyObject(Location.class))).andReturn(foePawn);
        EasyMock.replay(board);
        assertFalse(pawn.canMove(board, new Location(2, 0), new Location(3, 0)));
    }

    @Test
    void PTC9_pawnOneForward_hasMoved_friendDest() {
        Piece pawn = new Piece(PieceType.PAWN, PieceColor.WHITE);
        Piece friendPawn = new Piece(PieceType.PAWN, PieceColor.WHITE);
        pawn.setMoved(true);
        EasyMock.expect(board.getPiece(EasyMock.anyObject(Location.class))).andReturn(friendPawn);
        EasyMock.replay(board);
        assertFalse(pawn.canMove(board, new Location(2, 0), new Location(3, 0)));
    }

    @Test
    void PTC10_pawnOneForward_notMoved_foeDest() {
        Piece pawn = new Piece(PieceType.PAWN, PieceColor.WHITE);
        Piece foePawn = new Piece(PieceType.PAWN, PieceColor.BLACK);
        EasyMock.expect(board.getPiece(EasyMock.anyObject(Location.class))).andReturn(foePawn);
        EasyMock.replay(board);
        assertFalse(pawn.canMove(board, new Location(1, 0), new Location(2, 0)));
    }

    @Test
    void PTC11_pawnOneForward_notMoved_friendDest() {
        Piece pawn = new Piece(PieceType.PAWN, PieceColor.WHITE);
        Piece friendPawn = new Piece(PieceType.PAWN, PieceColor.WHITE);
        EasyMock.expect(board.getPiece(EasyMock.anyObject(Location.class))).andReturn(friendPawn);
        EasyMock.replay(board);
        assertFalse(pawn.canMove(board, new Location(1, 0), new Location(2, 0)));
    }
}

