package domain;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import org.easymock.EasyMock;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.stream.Stream;


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
        EasyMock.replay(board);
        assertTrue(pawn.canMove(board, new Location(2, 0), new Location(3, 0)));
    }

    @Test
    void PTC2_pawnOneForward_hasNotMoved_emptyDest() {
        Piece pawn = new Piece(PieceType.PAWN, PieceColor.WHITE);
        pawn.setMoved(false);
        EasyMock.replay(board);
        assertTrue(pawn.canMove(board, new Location(1, 0), new Location(2, 0)));
    }

    @Test
    void PTC3_pawnTwoForward_hasNotMoved_emptyDest() {
        Piece pawn = new Piece(PieceType.PAWN, PieceColor.WHITE);
        pawn.setMoved(false);
        EasyMock.replay(board);
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

    @Test
    void PTC12_pawnTwoForward_notMoved_foeDest() {
        Piece pawn = new Piece(PieceType.PAWN, PieceColor.WHITE);
        Piece foePawn = new Piece(PieceType.PAWN, PieceColor.BLACK);
        EasyMock.expect(board.getPiece(EasyMock.anyObject(Location.class)))
                .andReturn(null)      // intermediate square empty
                .andReturn(foePawn);  // destination occupied
        EasyMock.replay(board);
        assertFalse(pawn.canMove(board, new Location(1, 0), new Location(3, 0)));
    }

    @Test
    void PTC13_pawnTwoForward_notMoved_friendDest() {
        Piece pawn = new Piece(PieceType.PAWN, PieceColor.WHITE);
        Piece friendPawn = new Piece(PieceType.PAWN, PieceColor.WHITE);
        EasyMock.expect(board.getPiece(EasyMock.anyObject(Location.class)))
                .andReturn(null)        // intermediate square empty
                .andReturn(friendPawn); // destination occupied
        EasyMock.replay(board);
        assertFalse(pawn.canMove(board, new Location(1, 0), new Location(3, 0)));
    }

    @ParameterizedTest(name = "Two forward blocked path: hasMoved=false, blockingPiece={0}")
    @MethodSource("providePathBlockedMoves")
    void pawnTwoForward_pathBlocked_invalid_Case3(PieceColor blockingColor) {
        Piece pawn = new Piece(PieceType.PAWN, PieceColor.WHITE);
        pawn.setMoved(false);

        Piece blockingPiece = new Piece(PieceType.PAWN, blockingColor);

        // 1. Tell the board to return the blocking piece ONLY at row 2, col 0
        EasyMock.expect(board.getPiece(matchesLoc(2, 0))).andReturn(blockingPiece).anyTimes();

        // 2. Tell the board to return null (empty) for the destination row 3, col 0
        EasyMock.expect(board.getPiece(matchesLoc(3, 0))).andReturn(null).anyTimes();

        EasyMock.replay(board);

        // Attempting to move from (1,0) to (3,0) should fail
        assertFalse(pawn.canMove(board, new Location(1, 0), new Location(3, 0)));

        EasyMock.verify(board);
    }

    private static Stream<Arguments> providePathBlockedMoves() {
        return Stream.of(
                Arguments.of(PieceColor.BLACK), // PTC14: foe in path
                Arguments.of(PieceColor.WHITE)  // PTC15: friend in path
        );
    }

    @ParameterizedTest(name = "Two forward after moved: destination contains {0}")
    @MethodSource("provideAlreadyMovedTwoForwardCases")
    void pawnTwoForward_alreadyMoved_invalid_Case4(String caseName, Piece destPiece) {
        Piece pawn = new Piece(PieceType.PAWN, PieceColor.WHITE);
        pawn.setMoved(true); // Crucial: the pawn HAS moved

        EasyMock.expect(board.getPiece(matchesLoc(3, 0))).andReturn(null).anyTimes();
        EasyMock.expect(board.getPiece(matchesLoc(4, 0))).andReturn(destPiece).anyTimes();

        EasyMock.replay(board);

        assertFalse(pawn.canMove(board, new Location(2, 0), new Location(4, 0)));

        EasyMock.verify(board);
    }

    private static Stream<Arguments> provideAlreadyMovedTwoForwardCases() {
        return Stream.of(
                Arguments.of("Empty space",  null),                                // PTC16
                Arguments.of("Foe piece",   new Piece(PieceType.PAWN, PieceColor.BLACK)), // PTC17
                Arguments.of("Friend piece",new Piece(PieceType.PAWN, PieceColor.WHITE))  // PTC18
        );
    }

    private static Location matchesLoc(int expectedRow, int expectedCol) {
        EasyMock.reportMatcher(new org.easymock.IArgumentMatcher() {
            @Override
            public boolean matches(Object argument) {
                if (!(argument instanceof Location)) return false;
                Location loc = (Location) argument;
                return loc.getRow() == expectedRow && loc.getCol() == expectedCol;
            }

            @Override
            public void appendTo(StringBuffer buffer) {
                buffer.append("matchesLoc(").append(expectedRow).append(", ").append(expectedCol).append(")");
            }
        });
        return null; // EasyMock matchers always return a dummy value (null) during recording
    }

}

