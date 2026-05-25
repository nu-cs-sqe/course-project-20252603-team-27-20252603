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

    @ParameterizedTest(name = "Diagonal forward invalid: {0}")
    @MethodSource("provideInvalidDiagonalCases")
    void pawnDiagonal_emptyOrFriendDest_invalid(
            String testName,
            boolean hasMoved,
            int fromRow, int fromCol,
            int toRow, int toCol,
            Piece destPiece
    ) {
        Piece pawn = new Piece(PieceType.PAWN, PieceColor.WHITE);
        pawn.setMoved(hasMoved);

        // Tell the board mock what to return at the destination square
        EasyMock.expect(board.getPiece(matchesLoc(toRow, toCol))).andReturn(destPiece).anyTimes();
        EasyMock.replay(board);

        // This diagonal move should always be denied (false)
        assertFalse(pawn.canMove(board, new Location(fromRow, fromCol), new Location(toRow, toCol)));

        EasyMock.verify(board);
    }

    private static Stream<Arguments> provideInvalidDiagonalCases() {
        Piece friend = new Piece(PieceType.PAWN, PieceColor.WHITE);

        return Stream.of(
                // Empty destination cases
                Arguments.of("PTC19: moved, right, empty",   true,  2, 1, 3, 2, null),
                Arguments.of("PTC20: moved, left, empty",    true,  2, 1, 3, 0, null),
                Arguments.of("PTC21: not moved, right, empty", false, 1, 1, 2, 2, null),
                Arguments.of("PTC22: not moved, left, empty",  false, 1, 1, 2, 0, null),

                // Friend-occupied destination cases
                Arguments.of("PTC23: moved, right, friend",  true,  2, 1, 3, 2, friend),
                Arguments.of("PTC24: moved, left, friend",   true,  2, 1, 3, 0, friend),
                Arguments.of("PTC25: not moved, right, friend",false, 1, 1, 2, 2, friend),
                Arguments.of("PTC26: not moved, left, friend", false, 1, 1, 2, 0, friend)
        );
    }

    @ParameterizedTest(name = "Two-space diagonal invalid: {0}")
    @MethodSource("provideInvalidTwoSpaceDiagonalCases")
    void pawnTwoSpaceDiagonal_invalid(
            String testName,
            int fromRow, int fromCol,
            int toRow, int toCol,
            Piece destPiece
    ) {
        Piece pawn = new Piece(PieceType.PAWN, PieceColor.WHITE);
        pawn.setMoved(false); // Testing the first-move context explicitly

        // Tell the board mock what to return at the destination square
        EasyMock.expect(board.getPiece(matchesLoc(toRow, toCol))).andReturn(destPiece).anyTimes();
        EasyMock.replay(board);

        // Moving two spaces diagonally forward must always be denied (false)
        assertFalse(pawn.canMove(board, new Location(fromRow, fromCol), new Location(toRow, toCol)));

        EasyMock.verify(board);
    }

    private static Stream<Arguments> provideInvalidTwoSpaceDiagonalCases() {
        Piece foe = new Piece(PieceType.PAWN, PieceColor.BLACK);
        Piece friend = new Piece(PieceType.PAWN, PieceColor.WHITE);

        return Stream.of(
                // Empty destination cases
                Arguments.of("PTC27: forward-right, empty", 1, 0, 3, 2, null),
                Arguments.of("PTC28: forward-left, empty",  1, 2, 3, 0, null),

                // Foe-occupied destination cases
                Arguments.of("PTC29: forward-right, foe",   1, 0, 3, 2, foe),
                Arguments.of("PTC30: forward-left, foe",    1, 2, 3, 0, foe),

                // Friend-occupied destination cases
                Arguments.of("PTC31: forward-right, friend",1, 0, 3, 2, friend),
                Arguments.of("PTC32: forward-left, friend", 1, 2, 3, 0, friend)
        );
    }

    @ParameterizedTest(name = "Backward move invalid: {0}")
    @MethodSource("provideInvalidBackwardCases")
    void pawnBackwardMove_invalid(
            String testName,
            boolean hasMoved,
            int fromRow, int fromCol,
            int toRow, int toCol,
            Piece destPiece
    ) {
        Piece pawn = new Piece(PieceType.PAWN, PieceColor.WHITE);
        pawn.setMoved(hasMoved);

        // Tell the board mock what to return at the destination square
        EasyMock.expect(board.getPiece(matchesLoc(toRow, toCol))).andReturn(destPiece).anyTimes();
        EasyMock.replay(board);

        // Any attempt to move a pawn to a lower row (-row direction) must return false
        assertFalse(pawn.canMove(board, new Location(fromRow, fromCol), new Location(toRow, toCol)));

        EasyMock.verify(board);
    }

    private static Stream<Arguments> provideInvalidBackwardCases() {
        Piece foe = new Piece(PieceType.PAWN, PieceColor.BLACK);
        Piece friend = new Piece(PieceType.PAWN, PieceColor.WHITE);

        return Stream.of(
                // --- PAWN HAS MOVED (true) ---
                // Straight back
                Arguments.of("PTC33: moved, straight back, empty",     true, 2, 0, 1, 0, null),
                Arguments.of("PTC34: moved, straight back, foe",       true, 2, 0, 1, 0, foe),
                Arguments.of("PTC35: moved, straight back, friend",    true, 2, 0, 1, 0, friend),
                // Backward-Right
                Arguments.of("PTC36: moved, backward-right, empty",   true, 2, 1, 1, 2, null),
                Arguments.of("PTC37: moved, backward-right, foe",     true, 2, 1, 1, 2, foe),
                Arguments.of("PTC38: moved, backward-right, friend",  true, 2, 1, 1, 2, friend),
                // Backward-Left
                Arguments.of("PTC39: moved, backward-left, empty",    true, 2, 1, 1, 0, null),
                Arguments.of("PTC40: moved, backward-left, foe",      true, 2, 1, 1, 0, foe),
                Arguments.of("PTC41: moved, backward-left, friend",   true, 2, 1, 1, 0, friend),

                // --- PAWN HAS NOT MOVED (false) ---
                // Straight back
                Arguments.of("PTC42: not moved, straight back, empty",  false, 1, 0, 0, 0, null),
                Arguments.of("PTC43: not moved, straight back, foe",    false, 1, 0, 0, 0, foe),
                Arguments.of("PTC44: not moved, straight back, friend", false, 1, 0, 0, 0, friend),
                // Backward-Right
                Arguments.of("PTC45: not moved, backward-right, empty", false, 1, 1, 0, 2, null),
                Arguments.of("PTC46: not moved, backward-right, foe",   false, 1, 1, 0, 2, foe),
                Arguments.of("PTC47: not moved, backward-right, friend",false, 1, 1, 0, 2, friend),
                // Backward-Left
                Arguments.of("PTC48: not moved, backward-left, empty",  false, 1, 1, 0, 0, null),
                Arguments.of("PTC49: not moved, backward-left, foe",    false, 1, 1, 0, 0, foe),
                Arguments.of("PTC50: not moved, backward-left, friend", false, 1, 1, 0, 0, friend)
        );
    }

    @ParameterizedTest(name = "Sideways move invalid: {0}")
    @MethodSource("provideInvalidSidewaysCases")
    void pawnSidewaysMove_invalid(
            String testName,
            int fromRow, int fromCol,
            int toRow, int toCol,
            Piece destPiece
    ) {
        Piece pawn = new Piece(PieceType.PAWN, PieceColor.WHITE);
        pawn.setMoved(true); // Applied to all cases in this batch

        // Tell the board mock what to return at the destination square
        EasyMock.expect(board.getPiece(matchesLoc(toRow, toCol))).andReturn(destPiece).anyTimes();
        EasyMock.replay(board);

        // Any attempt to move a pawn sideways (row stays the same, col changes) must return false
        assertFalse(pawn.canMove(board, new Location(fromRow, fromCol), new Location(toRow, toCol)));

        EasyMock.verify(board);
    }

    private static Stream<Arguments> provideInvalidSidewaysCases() {
        Piece foe = new Piece(PieceType.PAWN, PieceColor.BLACK);
        Piece friend = new Piece(PieceType.PAWN, PieceColor.WHITE);

        return Stream.of(
                // --- Moving Left (col decreases) ---
                Arguments.of("PTC51: left, empty",  2, 1, 2, 0, null),
                Arguments.of("PTC52: left, foe",    2, 1, 2, 0, foe),
                Arguments.of("PTC53: left, friend", 2, 1, 2, 0, friend),

                // --- Moving Right (col increases) ---
                Arguments.of("PTC54: right, empty", 2, 0, 2, 1, null),
                Arguments.of("PTC55: right, foe",   2, 0, 2, 1, foe),
                Arguments.of("PTC56: right, friend",2, 0, 2, 1, friend)
        );
    }

    @ParameterizedTest(name = "Out-of-bounds invalid: {0}")
    @MethodSource("provideOutOfBoundsCases")
    void pawnOutOfBoundsMove_invalid(
            String testName,
            int fromRow, int fromCol,
            int toRow, int toCol
    ) {
        Piece pawn = new Piece(PieceType.PAWN, PieceColor.WHITE);
        pawn.setMoved(true); // Applied to all cases in this batch

        // Even for out-of-bounds coordinates, our mock safely says the square is empty (null)
        EasyMock.expect(board.getPiece(matchesLoc(toRow, toCol))).andReturn(null).anyTimes();
        EasyMock.replay(board);

        // Any attempt to move to a row or column outside 0-7 must return false
        assertFalse(pawn.canMove(board, new Location(fromRow, fromCol), new Location(toRow, toCol)));

        EasyMock.verify(board);
    }

    private static Stream<Arguments> provideOutOfBoundsCases() {
        return Stream.of(
                Arguments.of("PTC57: top boundary straight", 7, 0,  8,  0),
                Arguments.of("PTC58: top boundary right",    7, 0,  8,  1),
                Arguments.of("PTC59: top boundary left",     7, 1,  8,  0),
                Arguments.of("PTC60: left boundary diagonal", 2, 0,  3, -1),
                Arguments.of("PTC61: right boundary diagonal",2, 7,  3,  8)
        );
    }

    @ParameterizedTest(name = "Zero-distance move invalid: hasMoved={0}")
    @MethodSource("provideZeroDistanceCases")
    void pawnZeroDistanceMove_invalid(boolean hasMoved) {
        Piece pawn = new Piece(PieceType.PAWN, PieceColor.WHITE);
        pawn.setMoved(hasMoved);

        // A piece is sitting on (2,0) because the pawn itself is there!
        // Our mock returns the pawn itself if asked about its own square.
        EasyMock.expect(board.getPiece(matchesLoc(2, 0))).andReturn(pawn).anyTimes();
        EasyMock.replay(board);

        // Any attempt to move from (2,0) to (2,0) must return false
        assertFalse(pawn.canMove(board, new Location(2, 0), new Location(2, 0)));

        EasyMock.verify(board);
    }

    private static Stream<Arguments> provideZeroDistanceCases() {
        return Stream.of(
                Arguments.of(true),  // PTC62: has moved before
                Arguments.of(false) // PTC63: hasn't moved before
        );
    }

    @ParameterizedTest(name = "Valid Rook single move: {0}")
    @MethodSource("provideValidRookSingleMoves")
    void rookSingleMove_valid(
            String testName,
            int fromRow, int fromCol,
            int toRow, int toCol,
            Piece destPiece
    ) {
        Piece rook = new Piece(PieceType.ROOK, PieceColor.WHITE);

        // Mock what is sitting on the destination square
        EasyMock.expect(board.getPiece(matchesLoc(toRow, toCol))).andReturn(destPiece).anyTimes();
        EasyMock.replay(board);

        // Single orthogonal moves to empty/foe targets must return true
        assertTrue(rook.canMove(board, new Location(fromRow, fromCol), new Location(toRow, toCol)));

        EasyMock.verify(board);
    }

    private static Stream<Arguments> provideValidRookSingleMoves() {
        Piece foe = new Piece(PieceType.PAWN, PieceColor.BLACK);

        return Stream.of(
                // Forward moves
                Arguments.of("RTC1: forward, empty", 0, 0, 1, 0, null),
                Arguments.of("RTC2: forward, foe",   0, 0, 1, 0, foe),

                // Backward moves
                Arguments.of("RTC3: backward, empty",1, 0, 0, 0, null),
                Arguments.of("RTC4: backward, foe",  1, 0, 0, 0, foe),

                // Right moves
                Arguments.of("RTC5: right, empty",   1, 0, 1, 1, null),
                Arguments.of("RTC6: right, foe",     1, 0, 1, 1, foe),

                // Left moves
                Arguments.of("RTC7: left, empty",    1, 1, 1, 0, null),
                Arguments.of("RTC8: left, foe",      1, 1, 1, 0, foe)
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

