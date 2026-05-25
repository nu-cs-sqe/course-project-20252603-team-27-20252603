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

    @ParameterizedTest(name = "Valid Rook max slide: {0}")
    @MethodSource("provideValidRookMaxMoves")
    void rookMaxMove_clearPath_valid(
            String testName,
            int fromRow, int fromCol,
            int toRow, int toCol,
            Piece destPiece
    ) {
        Piece rook = new Piece(PieceType.ROOK, PieceColor.WHITE);

        // 1. Setup a default baseline: assume the entire board/path is clear (null)
        EasyMock.expect(board.getPiece(EasyMock.anyObject(Location.class)))
                .andReturn(null).anyTimes();

        // 2. Override the destination square with our specific target piece (or null)
        EasyMock.expect(board.getPiece(matchesLoc(toRow, toCol)))
                .andReturn(destPiece).anyTimes();

        EasyMock.replay(board);

        // Full-board sliding moves across a clear path to empty/foe targets must return true
        assertTrue(rook.canMove(board, new Location(fromRow, fromCol), new Location(toRow, toCol)));

        EasyMock.verify(board);
    }

    private static Stream<Arguments> provideValidRookMaxMoves() {
        Piece foe = new Piece(PieceType.PAWN, PieceColor.BLACK);

        return Stream.of(
                // Forward max slides (Row 0 -> Row 7)
                Arguments.of("RTC9: forward max, empty", 0, 0, 7, 0, null),
                Arguments.of("RTC10: forward max, foe",   0, 0, 7, 0, foe),

                // Backward max slides (Row 7 -> Row 0)
                Arguments.of("RTC11: backward max, empty",7, 0, 0, 0, null),
                Arguments.of("RTC12: backward max, foe",  7, 0, 0, 0, foe),

                // Right max slides (Col 0 -> Col 7)
                Arguments.of("RTC13: right max, empty",   7, 0, 7, 7, null),
                Arguments.of("RTC14: right max, foe",     7, 0, 7, 7, foe),

                // Left max slides (Col 7 -> Col 0)
                Arguments.of("RTC15: left max, empty",    7, 7, 7, 0, null),
                Arguments.of("RTC16: left max, foe",      7, 7, 7, 0, foe)
        );
    }

    @ParameterizedTest(name = "Invalid Rook friend block: {0}")
    @MethodSource("provideFriendBlockedRookSingleMoves")
    void rookSingleMove_friendOccupied_invalid(
            String testName,
            int fromRow, int fromCol,
            int toRow, int toCol
    ) {
        Piece rook = new Piece(PieceType.ROOK, PieceColor.WHITE);
        Piece friend = new Piece(PieceType.PAWN, PieceColor.WHITE);

        // Mock a friendly piece sitting on the destination square
        EasyMock.expect(board.getPiece(matchesLoc(toRow, toCol))).andReturn(friend).anyTimes();
        EasyMock.replay(board);

        // Single orthogonal moves to friendly targets must always return false
        assertFalse(rook.canMove(board, new Location(fromRow, fromCol), new Location(toRow, toCol)));

        EasyMock.verify(board);
    }

    private static Stream<Arguments> provideFriendBlockedRookSingleMoves() {
        return Stream.of(
                Arguments.of("RTC17: forward, friend",  0, 0, 1, 0),
                Arguments.of("RTC18: backward, friend", 1, 0, 0, 0),
                Arguments.of("RTC19: left, friend",     1, 1, 1, 0),
                Arguments.of("RTC20: right, friend",    0, 0, 0, 1)
        );
    }

    @ParameterizedTest(name = "Invalid Rook max slide: {0}")
    @MethodSource("provideFriendBlockedRookMaxMoves")
    void rookMaxMove_friendOccupied_invalid(
            String testName,
            int fromRow, int fromCol,
            int toRow, int toCol
    ) {
        Piece rook = new Piece(PieceType.ROOK, PieceColor.WHITE);
        Piece friend = new Piece(PieceType.PAWN, PieceColor.WHITE);

        // 1. Specific rule FIRST: Tell EasyMock exactly what is at the destination
        EasyMock.expect(board.getPiece(matchesLoc(toRow, toCol)))
                .andReturn(friend).anyTimes();

        // 2. Generic rule SECOND: Fallback wildcard for all intermediate path squares
        EasyMock.expect(board.getPiece(EasyMock.anyObject(Location.class)))
                .andReturn(null).anyTimes();

        EasyMock.replay(board);

        // Max slides to a friendly-occupied target must always return false
        assertFalse(rook.canMove(board, new Location(fromRow, fromCol), new Location(toRow, toCol)));

        EasyMock.verify(board);
    }

    private static Stream<Arguments> provideFriendBlockedRookMaxMoves() {
        return Stream.of(
                Arguments.of("RTC21: forward max, friend",  0, 0, 7, 0),
                Arguments.of("RTC22: backward max, friend", 7, 0, 0, 0),
                Arguments.of("RTC23: left max, friend",     0, 7, 0, 0),
                Arguments.of("RTC24: right max, friend",    0, 0, 0, 7)
        );
    }

    @ParameterizedTest(name = "Rook path friend-blocked: {0}")
    @MethodSource("provideFriendObstructedPathCases")
    void rookMaxMove_friendObstructedPath_invalid(
            String testName,
            int fromRow, int fromCol,
            int blockRow, int blockCol,
            int toRow, int toCol,
            Piece destPiece
    ) {
        Piece rook = new Piece(PieceType.ROOK, PieceColor.WHITE);
        Piece friendBlocker = new Piece(PieceType.PAWN, PieceColor.WHITE);

        // 1. SPECIFIC RULES FIRST: Place the friendly blocking piece on the path
        EasyMock.expect(board.getPiece(matchesLoc(blockRow, blockCol)))
                .andReturn(friendBlocker).anyTimes();

        // 2. Specify what is sitting at the final destination
        EasyMock.expect(board.getPiece(matchesLoc(toRow, toCol)))
                .andReturn(destPiece).anyTimes();

        // 3. GENERIC RULE LAST: Default all other random squares to empty (null)
        EasyMock.expect(board.getPiece(EasyMock.anyObject(Location.class)))
                .andReturn(null).anyTimes();

        EasyMock.replay(board);

        // Attempting to move through a piece must always return false
        assertFalse(rook.canMove(board, new Location(fromRow, fromCol), new Location(toRow, toCol)));

        EasyMock.verify(board);
    }

    private static Stream<Arguments> provideFriendObstructedPathCases() {
        Piece friend = new Piece(PieceType.PAWN, PieceColor.WHITE);
        Piece foe = new Piece(PieceType.PAWN, PieceColor.BLACK);

        return Stream.of(
                // Forward max slides (From 0,0 to 7,0 | Blocked at 1,0)
                Arguments.of("RTC25: forward max, blocked, dest empty",  0, 0,  1, 0,  7, 0, null),
                Arguments.of("RTC26: forward max, blocked, dest friend", 0, 0,  1, 0,  7, 0, friend),
                Arguments.of("RTC27: forward max, blocked, dest foe",    0, 0,  1, 0,  7, 0, foe),

                // Backward max slides (From 7,0 to 0,0 | Blocked at 6,0)
                Arguments.of("RTC28: backward max, blocked, dest empty", 7, 0,  6, 0,  0, 0, null),
                Arguments.of("RTC29: backward max, blocked, dest friend",7, 0,  6, 0,  0, 0, friend),
                Arguments.of("RTC30: backward max, blocked, dest foe",   7, 0,  6, 0,  0, 0, foe),

                // Left max slides (From 7,7 to 7,0 | Blocked at 7,6)
                Arguments.of("RTC31: left max, blocked, dest empty",     7, 7,  7, 6,  7, 0, null),
                Arguments.of("RTC32: left max, blocked, dest friend",    7, 7,  7, 6,  7, 0, friend),
                Arguments.of("RTC33: left max, blocked, dest foe",       7, 7,  7, 6,  7, 0, foe),

                // Right max slides (From 7,0 to 7,7 | Blocked at 7,1)
                Arguments.of("RTC34: right max, blocked, dest empty",    7, 0,  7, 1,  7, 7, null),
                Arguments.of("RTC35: right max, blocked, dest friend",   7, 0,  7, 1,  7, 7, friend),
                Arguments.of("RTC36: right max, blocked, dest foe",      7, 0,  7, 1,  7, 7, foe)
        );
    }

    @ParameterizedTest(name = "Rook path foe-blocked: {0}")
    @MethodSource("provideFoeObstructedPathCases")
    void rookMaxMove_foeObstructedPath_invalid(
            String testName,
            int fromRow, int fromCol,
            int blockRow, int blockCol,
            int toRow, int toCol,
            Piece destPiece
    ) {
        Piece rook = new Piece(PieceType.ROOK, PieceColor.WHITE);
        Piece foeBlocker = new Piece(PieceType.PAWN, PieceColor.BLACK);

        // 1. SPECIFIC RULES FIRST: Place the enemy blocking piece on the intermediate path
        EasyMock.expect(board.getPiece(matchesLoc(blockRow, blockCol)))
                .andReturn(foeBlocker).anyTimes();

        // 2. Specify what is sitting at the final destination square
        EasyMock.expect(board.getPiece(matchesLoc(toRow, toCol)))
                .andReturn(destPiece).anyTimes();

        // 3. GENERIC RULE LAST: Default all other random path squares to empty (null)
        EasyMock.expect(board.getPiece(EasyMock.anyObject(Location.class)))
                .andReturn(null).anyTimes();

        EasyMock.replay(board);

        // Attempting to move through an enemy piece must always return false
        assertFalse(rook.canMove(board, new Location(fromRow, fromCol), new Location(toRow, toCol)));

        EasyMock.verify(board);
    }

    private static Stream<Arguments> provideFoeObstructedPathCases() {
        Piece friend = new Piece(PieceType.PAWN, PieceColor.WHITE);
        Piece foe = new Piece(PieceType.PAWN, PieceColor.BLACK);

        return Stream.of(
                // Forward max slides (From 0,0 to 7,0 | Blocked at 1,0)
                Arguments.of("RTC37: forward max, foe-blocked, dest empty",  0, 0,  1, 0,  7, 0, null),
                Arguments.of("RTC38: forward max, foe-blocked, dest friend", 0, 0,  1, 0,  7, 0, friend),
                Arguments.of("RTC39: forward max, foe-blocked, dest foe",    0, 0,  1, 0,  7, 0, foe),

                // Backward max slides (From 7,0 to 0,0 | Blocked at 6,0)
                Arguments.of("RTC40: backward max, foe-blocked, dest empty", 7, 0,  6, 0,  0, 0, null),
                Arguments.of("RTC41: backward max, foe-blocked, dest friend",7, 0,  6, 0,  0, 0, friend),
                Arguments.of("RTC42: backward max, foe-blocked, dest foe",   7, 0,  6, 0,  0, 0, foe),

                // Left max slides (From 7,7 to 7,0 | Blocked at 7,6)
                Arguments.of("RTC43: left max, foe-blocked, dest empty",     7, 7,  7, 6,  7, 0, null),
                Arguments.of("RTC44: left max, foe-blocked, dest friend",    7, 7,  7, 6,  7, 0, friend),
                Arguments.of("RTC45: left max, foe-blocked, dest foe",       7, 7,  7, 6,  7, 0, foe),

                // Right max slides (From 7,0 to 7,7 | Blocked at 7,1)
                Arguments.of("RTC46: right max, foe-blocked, dest empty",    7, 0,  7, 1,  7, 7, null),
                Arguments.of("RTC47: right max, foe-blocked, dest friend",   7, 0,  7, 1,  7, 7, friend),
                Arguments.of("RTC48: right max, foe-blocked, dest foe",      7, 0,  7, 1,  7, 7, foe)
        );
    }

    @ParameterizedTest(name = "Invalid Rook diagonal move: {0}")
    @MethodSource("provideDiagonalRookCases")
    void rookDiagonalMove_invalid(
            String testName,
            int fromRow, int fromCol,
            int toRow, int toCol,
            Piece destPiece
    ) {
        Piece rook = new Piece(PieceType.ROOK, PieceColor.WHITE);

        // Mock whatever state the destination square is in
        EasyMock.expect(board.getPiece(matchesLoc(toRow, toCol))).andReturn(destPiece).anyTimes();
        EasyMock.replay(board);

        // Any non-orthogonal move attempt must return false
        assertFalse(rook.canMove(board, new Location(fromRow, fromCol), new Location(toRow, toCol)));

        EasyMock.verify(board);
    }

    private static Stream<Arguments> provideDiagonalRookCases() {
        Piece friend = new Piece(PieceType.PAWN, PieceColor.WHITE);
        Piece foe = new Piece(PieceType.PAWN, PieceColor.BLACK);

        return Stream.of(
                // --- Empty Destination Squares ---
                Arguments.of("RTC49: forward-right, empty",  3, 4, 4, 3, null),
                Arguments.of("RTC50: forward-left, empty",   3, 4, 4, 5, null),
                Arguments.of("RTC51: backward-left, empty",  3, 4, 2, 5, null),
                Arguments.of("RTC52: backward-right, empty", 3, 4, 2, 3, null),

                // --- Friend-Occupied Destination Squares ---
                Arguments.of("RTC53: forward-right, friend",  3, 4, 4, 3, friend),
                Arguments.of("RTC54: forward-left, friend",   3, 4, 4, 5, friend),
                Arguments.of("RTC55: backward-left, friend",  3, 4, 2, 5, friend),
                Arguments.of("RTC56: backward-right, friend", 3, 4, 2, 3, friend),

                // --- Foe-Occupied Destination Squares ---
                Arguments.of("RTC57: forward-right, foe",  3, 4, 4, 3, foe),
                Arguments.of("RTC58: forward-left, foe",   3, 4, 4, 5, foe),
                Arguments.of("RTC59: backward-left, foe",  3, 4, 2, 5, foe),
                Arguments.of("RTC60: backward-right, foe", 3, 4, 2, 3, foe)
        );
    }

    @ParameterizedTest(name = "Rook out-of-bounds invalid: {0}")
    @MethodSource("provideOutOfBoundsRookCases")
    void rookOutOfBoundsMove_invalid(
            String testName,
            int fromRow, int fromCol,
            int toRow, int toCol
    ) {
        Piece rook = new Piece(PieceType.ROOK, PieceColor.WHITE);

        // Mocking an empty response for safety, though the guard clause should intercept first
        EasyMock.expect(board.getPiece(EasyMock.anyObject(Location.class))).andReturn(null).anyTimes();
        EasyMock.replay(board);

        // Any move target outside the 0-7 coordinate index matrix must return false
        assertFalse(rook.canMove(board, new Location(fromRow, fromCol), new Location(toRow, toCol)));

        EasyMock.verify(board);
    }

    private static Stream<Arguments> provideOutOfBoundsRookCases() {
        return Stream.of(
                Arguments.of("RTC61: right out-of-bounds",    0, 7,  0,  8),
                Arguments.of("RTC62: forward out-of-bounds",  7, 0,  8,  0),
                Arguments.of("RTC63: backward out-of-bounds", 0, 0, -1,  0),
                Arguments.of("RTC64: left out-of-bounds",     0, 0,  0, -1)
        );
    }

    @Test
    void rookZeroDistanceMove_invalid() {
        Piece rook = new Piece(PieceType.ROOK, PieceColor.WHITE);

        // Mock the square to return the rook itself since it is sitting there
        EasyMock.expect(board.getPiece(matchesLoc(3, 3))).andReturn(rook).anyTimes();
        EasyMock.replay(board);

        // A piece cannot move to the exact square it already occupies
        assertFalse(rook.canMove(board, new Location(3, 3), new Location(3, 3)));

        EasyMock.verify(board);
    }

    @ParameterizedTest(name = "Valid Knight L-move: {0}")
    @MethodSource("provideValidKnightEmptyMoves")
    void knightLMove_empty_valid(
            String testName,
            int fromRow, int fromCol,
            int toRow, int toCol
    ) {
        Piece knight = new Piece(PieceType.KNIGHT, PieceColor.WHITE);

        // All targets in this batch are empty positions (null)
        EasyMock.expect(board.getPiece(matchesLoc(toRow, toCol))).andReturn(null).anyTimes();
        EasyMock.replay(board);

        // Standard legal L-shape translations must return true
        assertTrue(knight.canMove(board, new Location(fromRow, fromCol), new Location(toRow, toCol)));

        EasyMock.verify(board);
    }

    private static Stream<Arguments> provideValidKnightEmptyMoves() {
        return Stream.of(
                Arguments.of("KTC1: forward-left (2 up, 1 right)",  3, 3, 5, 4),
                Arguments.of("KTC2: forward-right (2 up, 1 left)",  3, 3, 5, 2),
                Arguments.of("KTC3: right-forward (1 up, 2 left)",  3, 3, 4, 1),
                Arguments.of("KTC4: right-backward (1 down, 2 left)",3, 3, 2, 1),
                Arguments.of("KTC5: backward-left (2 down, 1 right)",1, 4, 3, 3), // adjusted per case spec directionals
                Arguments.of("KTC6: backward-right (2 down, 1 left)",3, 3, 1, 2),
                Arguments.of("KTC7: left-forward (1 up, 2 right)",  3, 3, 4, 5),
                Arguments.of("KTC8: left-backward (1 down, 2 right)",3, 3, 2, 5)
        );
    }

    @ParameterizedTest(name = "Valid Knight capture: {0}")
    @MethodSource("provideValidKnightFoeMoves")
    void knightLMove_foeOccupied_valid(
            String testName,
            int fromRow, int fromCol,
            int toRow, int toCol
    ) {
        Piece knight = new Piece(PieceType.KNIGHT, PieceColor.WHITE);
        Piece foe = new Piece(PieceType.PAWN, PieceColor.BLACK);

        // Mock an enemy piece sitting directly at the destination square
        EasyMock.expect(board.getPiece(matchesLoc(toRow, toCol))).andReturn(foe).anyTimes();
        EasyMock.replay(board);

        // Standard L-shape translations ending on a foe must return true
        assertTrue(knight.canMove(board, new Location(fromRow, fromCol), new Location(toRow, toCol)));

        EasyMock.verify(board);
    }

    private static Stream<Arguments> provideValidKnightFoeMoves() {
        return Stream.of(
                Arguments.of("KTC9: forward-left, foe",   3, 3, 5, 4),
                Arguments.of("KTC10: forward-right, foe", 3, 3, 5, 2),
                Arguments.of("KTC11: right-forward, foe", 3, 3, 4, 1),
                Arguments.of("KTC12: right-backward, foe",3, 3, 2, 1),
                Arguments.of("KTC13: backward-left, foe", 3, 3, 1, 4),
                Arguments.of("KTC14: backward-right, foe",3, 3, 1, 2),
                Arguments.of("KTC15: left-forward, foe",  3, 3, 4, 5),
                Arguments.of("KTC16: left-backward, foe", 3, 3, 2, 5)
        );
    }

    @ParameterizedTest(name = "Valid Knight jump (friend path block): {0}")
    @MethodSource("provideValidKnightFriendObstructedPathCases")
    void knightLMove_friendObstructedPath_valid(
            String testName,
            int fromRow, int fromCol,
            int blockRow, int blockCol,
            int toRow, int toCol
    ) {
        Piece knight = new Piece(PieceType.KNIGHT, PieceColor.WHITE);
        Piece friendBlocker = new Piece(PieceType.PAWN, PieceColor.WHITE);

        // 1. Specific Rule: Place a friendly piece right on the intermediate path
        EasyMock.expect(board.getPiece(matchesLoc(blockRow, blockCol)))
                .andReturn(friendBlocker).anyTimes();

        // 2. Specific Rule: The final destination square is completely empty (null)
        EasyMock.expect(board.getPiece(matchesLoc(toRow, toCol)))
                .andReturn(null).anyTimes();

        // 3. Generic Rule: Any other lookup falls back to empty
        EasyMock.expect(board.getPiece(EasyMock.anyObject(Location.class)))
                .andReturn(null).anyTimes();

        EasyMock.replay(board);

        // Knights jump over obstacles, so path obstructions must evaluate to true
        assertTrue(knight.canMove(board, new Location(fromRow, fromCol), new Location(toRow, toCol)));

        EasyMock.verify(board);
    }

    private static Stream<Arguments> provideValidKnightFriendObstructedPathCases() {
        return Stream.of(
                Arguments.of("KTC17: forward-left, friend on path",  3, 3,  4, 3,  5, 4),
                Arguments.of("KTC18: forward-right, friend on path", 3, 3,  4, 3,  5, 2),
                Arguments.of("KTC19: right-forward, friend on path", 3, 3,  3, 2,  4, 1),
                Arguments.of("KTC20: right-backward, friend on path",3, 3,  3, 2,  2, 1),
                Arguments.of("KTC21: backward-left, friend on path", 3, 3,  2, 3,  1, 4),
                Arguments.of("KTC22: backward-right, friend on path",3, 3,  2, 3,  1, 2),
                Arguments.of("KTC23: left-forward, friend on path",  3, 3,  3, 4,  4, 5),
                Arguments.of("KTC24: left-backward, friend on path", 3, 3,  3, 4,  2, 5)
        );
    }

    @ParameterizedTest(name = "Valid Knight jump capture: {0}")
    @MethodSource("provideValidKnightFriendObstructedCaptureCases")
    void knightLMove_friendObstructedPath_foeOccupied_valid(
            String testName,
            int fromRow, int fromCol,
            int blockRow, int blockCol,
            int toRow, int toCol
    ) {
        Piece knight = new Piece(PieceType.KNIGHT, PieceColor.WHITE);
        Piece friendBlocker = new Piece(PieceType.PAWN, PieceColor.WHITE);
        Piece foe = new Piece(PieceType.PAWN, PieceColor.BLACK);

        // 1. Specific Rule: Place a friendly piece on the intermediate path
        EasyMock.expect(board.getPiece(matchesLoc(blockRow, blockCol)))
                .andReturn(friendBlocker).anyTimes();

        // 2. Specific Rule: Place an enemy piece at the final destination target
        EasyMock.expect(board.getPiece(matchesLoc(toRow, toCol)))
                .andReturn(foe).anyTimes();

        // 3. Generic Rule: Any other lookup falls back to empty
        EasyMock.expect(board.getPiece(EasyMock.anyObject(Location.class)))
                .andReturn(null).anyTimes();

        EasyMock.replay(board);

        // Path obstructions are ignored, and foe destinations are valid capture zones
        assertTrue(knight.canMove(board, new Location(fromRow, fromCol), new Location(toRow, toCol)));

        EasyMock.verify(board);
    }

    private static Stream<Arguments> provideValidKnightFriendObstructedCaptureCases() {
        return Stream.of(
                Arguments.of("KTC25: forward-left, jump enemy capture",  3, 3,  4, 3,  5, 4),
                Arguments.of("KTC26: forward-right, jump enemy capture", 3, 3,  4, 3,  5, 2),
                Arguments.of("KTC27: right-forward, jump enemy capture", 3, 3,  3, 2,  4, 1),
                Arguments.of("KTC28: right-backward, jump enemy capture",3, 3,  3, 2,  2, 1),
                Arguments.of("KTC29: backward-left, jump enemy capture", 3, 3,  2, 3,  1, 4),
                Arguments.of("KTC30: backward-right, jump enemy capture",3, 3,  2, 3,  1, 2),
                Arguments.of("KTC31: left-forward, jump enemy capture",  3, 3,  3, 4,  4, 5),
                Arguments.of("KTC32: left-backward, jump enemy capture", 3, 3,  3, 4,  2, 5)
        );
    }

    @ParameterizedTest(name = "Invalid Knight friend landing: {0}")
    @MethodSource("provideInvalidKnightFriendMoves")
    void knightLMove_friendOccupied_invalid(
            String testName,
            int fromRow, int fromCol,
            int toRow, int toCol
    ) {
        Piece knight = new Piece(PieceType.KNIGHT, PieceColor.WHITE);
        Piece friend = new Piece(PieceType.PAWN, PieceColor.WHITE);

        // Mock a friendly piece sitting directly at the destination square
        EasyMock.expect(board.getPiece(matchesLoc(toRow, toCol))).andReturn(friend).anyTimes();
        EasyMock.replay(board);

        // Legal geometric shapes ending on a friendly piece must return false
        assertFalse(knight.canMove(board, new Location(fromRow, fromCol), new Location(toRow, toCol)));

        EasyMock.verify(board);
    }

    private static Stream<Arguments> provideInvalidKnightFriendMoves() {
        return Stream.of(
                Arguments.of("KTC33: forward-left, friend",   3, 3, 5, 4),
                Arguments.of("KTC34: forward-right, friend", 3, 3, 5, 2),
                Arguments.of("KTC35: right-forward, friend", 3, 3, 4, 1),
                Arguments.of("KTC36: right-backward, friend",3, 3, 2, 1),
                Arguments.of("KTC37: backward-left, friend", 3, 3, 1, 4),
                Arguments.of("KTC38: backward-right, friend",3, 3, 1, 2),
                Arguments.of("KTC39: left-forward, friend",  3, 3, 4, 5),
                Arguments.of("KTC40: left-backward, friend", 3, 3, 2, 5)
        );
    }

    @ParameterizedTest(name = "Invalid Knight single-diagonal move: {0}")
    @MethodSource("provideInvalidKnightSingleDiagonalMoves")
    void knightMove_singleDiagonal_invalid(
            String testName,
            int fromRow, int fromCol,
            int toRow, int toCol,
            Piece destPiece
    ) {
        Piece knight = new Piece(PieceType.KNIGHT, PieceColor.WHITE);

        // Mock the exact piece status of the destination square
        EasyMock.expect(board.getPiece(matchesLoc(toRow, toCol))).andReturn(destPiece).anyTimes();
        EasyMock.replay(board);

        // One-space diagonal steps must return false
        assertFalse(knight.canMove(board, new Location(fromRow, fromCol), new Location(toRow, toCol)));

        EasyMock.verify(board);
    }

    private static Stream<Arguments> provideInvalidKnightSingleDiagonalMoves() {
        Piece friend = new Piece(PieceType.PAWN, PieceColor.WHITE);
        Piece foe = new Piece(PieceType.PAWN, PieceColor.BLACK);

        return Stream.of(
                // Forward-Right (4,4) variants
                Arguments.of("KTC41: forward-right, empty",  3, 3, 4, 4, null),
                Arguments.of("KTC42: forward-right, friend", 3, 3, 4, 4, friend),
                Arguments.of("KTC43: forward-right, foe",    3, 3, 4, 4, foe),

                // Forward-Left (4,2) variants
                Arguments.of("KTC44: forward-left, empty",   3, 3, 4, 2, null),
                Arguments.of("KTC45: forward-left, friend",  3, 3, 4, 2, friend),
                Arguments.of("KTC46: forward-left, foe",     3, 3, 4, 2, foe),

                // Backward-Right (2,4) variants
                Arguments.of("KTC47: backward-right, empty", 3, 3, 2, 4, null),
                Arguments.of("KTC48: backward-right, friend",3, 3, 2, 4, friend),
                Arguments.of("KTC49: backward-right, foe",   3, 3, 2, 4, foe),

                // Backward-Left (2,2) variants
                Arguments.of("KTC50: backward-left, empty",  3, 3, 2, 2, null),
                Arguments.of("KTC51: backward-left, friend", 3, 3, 2, 2, friend),
                Arguments.of("KTC52: backward-left, foe",    3, 3, 2, 2, foe)
        );
    }

    @ParameterizedTest(name = "Invalid Knight two-diagonal move: {0}")
    @MethodSource("provideInvalidKnightTwoDiagonalMoves")
    void knightMove_twoDiagonal_invalid(
            String testName,
            int fromRow, int fromCol,
            int toRow, int toCol,
            Piece destPiece
    ) {
        Piece knight = new Piece(PieceType.KNIGHT, PieceColor.WHITE);

        // Mock the exact piece status of the destination square
        EasyMock.expect(board.getPiece(matchesLoc(toRow, toCol))).andReturn(destPiece).anyTimes();
        EasyMock.replay(board);

        // Two-space diagonal steps must return false
        assertFalse(knight.canMove(board, new Location(fromRow, fromCol), new Location(toRow, toCol)));

        EasyMock.verify(board);
    }

    private static Stream<Arguments> provideInvalidKnightTwoDiagonalMoves() {
        Piece friend = new Piece(PieceType.PAWN, PieceColor.WHITE);
        Piece foe = new Piece(PieceType.PAWN, PieceColor.BLACK);

        return Stream.of(
                // Forward-Right (5,5) variants
                Arguments.of("KTC53: forward-right two-diagonals, empty",  3, 3, 5, 5, null),
                Arguments.of("KTC54: forward-right two-diagonals, friend", 3, 3, 5, 5, friend),
                Arguments.of("KTC55: forward-right two-diagonals, foe",    3, 3, 5, 5, foe),

                // Forward-Left (5,1) variants
                Arguments.of("KTC56: forward-left two-diagonals, empty",   3, 3, 5, 1, null),
                Arguments.of("KTC57: forward-left two-diagonals, friend",  3, 3, 5, 1, friend),
                Arguments.of("KTC58: forward-left two-diagonals, foe",     3, 3, 5, 1, foe),

                // Backward-Right (1,5) variants
                Arguments.of("KTC59: backward-right two-diagonals, empty", 3, 3, 1, 5, null),
                Arguments.of("KTC60: backward-right two-diagonals, friend",3, 3, 1, 5, friend),
                Arguments.of("KTC61: backward-right two-diagonals, foe",   3, 3, 1, 5, foe),

                // Backward-Left (1,1) variants
                Arguments.of("KTC62: backward-left two-diagonals, empty",  3, 3, 1, 1, null),
                Arguments.of("KTC63: backward-left two-diagonals, friend", 3, 3, 1, 1, friend),
                Arguments.of("KTC64: backward-left two-diagonals, foe",    3, 3, 1, 1, foe)
        );
    }

    @ParameterizedTest(name = "Invalid Knight single-orthogonal move: {0}")
    @MethodSource("provideInvalidKnightSingleOrthogonalMoves")
    void knightMove_singleOrthogonal_invalid(
            String testName,
            int fromRow, int fromCol,
            int toRow, int toCol,
            Piece destPiece
    ) {
        Piece knight = new Piece(PieceType.KNIGHT, PieceColor.WHITE);

        // Mock the exact piece status of the destination square
        EasyMock.expect(board.getPiece(matchesLoc(toRow, toCol))).andReturn(destPiece).anyTimes();
        EasyMock.replay(board);

        // One-space orthogonal steps must return false
        assertFalse(knight.canMove(board, new Location(fromRow, fromCol), new Location(toRow, toCol)));

        EasyMock.verify(board);
    }

    private static Stream<Arguments> provideInvalidKnightSingleOrthogonalMoves() {
        Piece friend = new Piece(PieceType.PAWN, PieceColor.WHITE);
        Piece foe = new Piece(PieceType.PAWN, PieceColor.BLACK);

        return Stream.of(
                // Forward (4,3) variants
                Arguments.of("KTC65: forward one space, empty",  3, 3, 4, 3, null),
                Arguments.of("KTC66: forward one space, friend", 3, 3, 4, 3, friend),
                Arguments.of("KTC67: forward one space, foe",    3, 3, 4, 3, foe),

                // Backward (2,3) variants
                Arguments.of("KTC68: backward one space, empty", 3, 3, 2, 3, null),
                Arguments.of("KTC69: backward one space, friend",3, 3, 2, 3, friend),
                Arguments.of("KTC70: backward one space, foe",   3, 3, 2, 3, foe),

                // Left (3,2) variants
                Arguments.of("KTC71: left one space, empty",     3, 3, 3, 2, null),
                Arguments.of("KTC72: left one space, friend",    3, 3, 3, 2, friend),
                Arguments.of("KTC73: left one space, foe",       3, 3, 3, 2, foe),

                // Right (3,4) variants
                Arguments.of("KTC74: right one space, empty",    3, 3, 3, 4, null),
                Arguments.of("KTC75: right one space, friend",   3, 3, 3, 4, friend),
                Arguments.of("KTC76: right one space, foe",      3, 3, 3, 4, foe)
        );
    }

    @ParameterizedTest(name = "Knight out-of-bounds invalid: {0}")
    @MethodSource("provideOutOfBoundsKnightCases")
    void knightOutOfBoundsMove_invalid(
            String testName,
            int fromRow, int fromCol,
            int toRow, int toCol
    ) {
        Piece knight = new Piece(PieceType.KNIGHT, PieceColor.WHITE);

        // Mocking an empty response for safety, though the guard clause should intercept first
        EasyMock.expect(board.getPiece(EasyMock.anyObject(Location.class))).andReturn(null).anyTimes();
        EasyMock.replay(board);

        // Any move target outside the 0-7 coordinate index matrix must return false
        assertFalse(knight.canMove(board, new Location(fromRow, fromCol), new Location(toRow, toCol)));

        EasyMock.verify(board);
    }

    private static Stream<Arguments> provideOutOfBoundsKnightCases() {
        return Stream.of(
                // --- Top Boundary (Row > 7) ---
                Arguments.of("KTC77: top boundary, forward-left",  6, 3, 8, 2),
                Arguments.of("KTC78: top boundary, forward-right", 6, 3, 8, 4),
                Arguments.of("KTC79: top boundary, left-forward",  7, 3, 8, 1),
                Arguments.of("KTC80: top boundary, right-forward", 7, 3, 8, 5),

                // --- Bottom Boundary (Row < 0) ---
                Arguments.of("KTC81: bottom boundary, backward-left",  1, 3, -1, 2),
                Arguments.of("KTC82: bottom boundary, backward-right", 1, 3, -1, 4),
                Arguments.of("KTC83: bottom boundary, left-backward",  0, 3, -1, 1),
                Arguments.of("KTC84: bottom boundary, right-backward", 0, 3, -1, 5),

                // --- Left Boundary (Col < 0) ---
                Arguments.of("KTC85: left boundary, forward-left",  3, 1, 5, -1), // Corrected from 5,0
                Arguments.of("KTC86: left boundary, left-forward",  3, 1, 4, -1), // Corrected from 4,0
                Arguments.of("KTC87: left boundary, left-backward", 3, 1, 2, -1), // Corrected from 2,0
                Arguments.of("KTC88: left boundary, backward-left", 3, 1, 1, -1), // Corrected from 1,0

                // --- Right Boundary (Col > 7) ---
                Arguments.of("KTC89: right boundary, forward-right", 3, 6, 5, 8),
                Arguments.of("KTC90: right boundary, right-forward", 3, 6, 4, 8), // Corrected from 4,7
                Arguments.of("KTC91: right boundary, right-backward",3, 6, 2, 8), // Corrected from 2,7
                Arguments.of("KTC92: right boundary, backward-right",3, 6, 1, 8)
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

