package domain;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import org.easymock.EasyMock;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.stream.Stream;


class PieceTest {

    private Board board;

    @BeforeEach
    void setUp() {
        board = EasyMock.createNiceMock(Board.class);
    }

    @Test
    void PTC1_pawnOneForward_hasMoved_emptyDest() {
        Piece pawn = new Piece(PieceType.PAWN, PieceColor.BLACK);
        pawn.setMoved(true);
        EasyMock.replay(board);
        assertTrue(pawn.canMove(board, new Location(2, 0), new Location(3, 0)));
    }

    @Test
    void PTC2_pawnOneForward_hasNotMoved_emptyDest() {
        Piece pawn = new Piece(PieceType.PAWN, PieceColor.BLACK);
        pawn.setMoved(false);
        EasyMock.replay(board);
        assertTrue(pawn.canMove(board, new Location(1, 0), new Location(2, 0)));
    }

    @Test
    void PTC3_pawnTwoForward_hasNotMoved_emptyDest() {
        Piece pawn = new Piece(PieceType.PAWN, PieceColor.BLACK);
        pawn.setMoved(false);
        EasyMock.replay(board);
        assertTrue(pawn.canMove(board, new Location(1, 0), new Location(3, 0)));
    }

    @Test
    void PTC4_pawnOneDiagonalForwardRight_hasMoved_foeDest() {
        Piece pawn = new Piece(PieceType.PAWN, PieceColor.BLACK);
        Piece foePawn = new Piece(PieceType.PAWN, PieceColor.WHITE);
        pawn.setMoved(true);

        EasyMock.expect(board.getPiece(EasyMock.anyObject(Location.class))).andReturn(foePawn);
        EasyMock.replay(board);

        assertTrue(pawn.canMove(board, new Location(2, 1), new Location(3, 2)));

        EasyMock.verify(board);
    }

    @Test
    void PTC5_pawnOneDiagonalForwardLeft_hasMoved_foeDest() {
        Piece pawn = new Piece(PieceType.PAWN, PieceColor.BLACK);
        Piece foePawn = new Piece(PieceType.PAWN, PieceColor.WHITE);
        pawn.setMoved(true);

        EasyMock.expect(board.getPiece(EasyMock.anyObject(Location.class))).andReturn(foePawn);
        EasyMock.replay(board);

        assertTrue(pawn.canMove(board, new Location(2, 1), new Location(3, 0)));

        EasyMock.verify(board);
    }

    @Test
    void PTC6_pawnOneDiagonalForwardRight_hasNotMoved_foeDest() {
        Piece pawn = new Piece(PieceType.PAWN, PieceColor.BLACK);
        Piece foePawn = new Piece(PieceType.PAWN, PieceColor.WHITE);
        pawn.setMoved(false);

        EasyMock.expect(board.getPiece(EasyMock.anyObject(Location.class))).andReturn(foePawn);
        EasyMock.replay(board);

        assertTrue(pawn.canMove(board, new Location(1, 1), new Location(2, 2)));

        EasyMock.verify(board);
    }

    @Test
    void PTC7_pawnOneDiagonalForwardLeft_hasNotMoved_foeDest() {
        Piece pawn = new Piece(PieceType.PAWN, PieceColor.BLACK);
        Piece foePawn = new Piece(PieceType.PAWN, PieceColor.WHITE);
        pawn.setMoved(false);

        EasyMock.expect(board.getPiece(EasyMock.anyObject(Location.class))).andReturn(foePawn);
        EasyMock.replay(board);

        assertTrue(pawn.canMove(board, new Location(1, 1), new Location(2, 0)));

        EasyMock.verify(board);
    }

    @Test
    void PTC8_pawnOneForward_hasMoved_foeDest() {
        Piece pawn = new Piece(PieceType.PAWN, PieceColor.BLACK);
        Piece foePawn = new Piece(PieceType.PAWN, PieceColor.WHITE);
        pawn.setMoved(true);
        EasyMock.expect(board.getPiece(EasyMock.anyObject(Location.class))).andReturn(foePawn);
        EasyMock.replay(board);
        assertFalse(pawn.canMove(board, new Location(2, 0), new Location(3, 0)));
    }

    @Test
    void PTC9_pawnOneForward_hasMoved_friendDest() {
        Piece pawn = new Piece(PieceType.PAWN, PieceColor.BLACK);
        Piece friendPawn = new Piece(PieceType.PAWN, PieceColor.BLACK);
        pawn.setMoved(true);
        EasyMock.expect(board.getPiece(EasyMock.anyObject(Location.class))).andReturn(friendPawn);
        EasyMock.replay(board);
        assertFalse(pawn.canMove(board, new Location(2, 0), new Location(3, 0)));
    }

    @Test
    void PTC10_pawnOneForward_notMoved_foeDest() {
        Piece pawn = new Piece(PieceType.PAWN, PieceColor.BLACK);
        Piece foePawn = new Piece(PieceType.PAWN, PieceColor.WHITE);
        EasyMock.expect(board.getPiece(EasyMock.anyObject(Location.class))).andReturn(foePawn);
        EasyMock.replay(board);
        assertFalse(pawn.canMove(board, new Location(1, 0), new Location(2, 0)));
    }

    @Test
    void PTC11_pawnOneForward_notMoved_friendDest() {
        Piece pawn = new Piece(PieceType.PAWN, PieceColor.BLACK);
        Piece friendPawn = new Piece(PieceType.PAWN, PieceColor.BLACK);
        EasyMock.expect(board.getPiece(EasyMock.anyObject(Location.class))).andReturn(friendPawn);
        EasyMock.replay(board);
        assertFalse(pawn.canMove(board, new Location(1, 0), new Location(2, 0)));
    }

    @Test
    void PTC12_pawnTwoForward_notMoved_foeDest() {
        Piece pawn = new Piece(PieceType.PAWN, PieceColor.BLACK);
        Piece foePawn = new Piece(PieceType.PAWN, PieceColor.WHITE);
        EasyMock.expect(board.getPiece(EasyMock.anyObject(Location.class)))
                .andReturn(null)      
                .andReturn(foePawn);  
        EasyMock.replay(board);
        assertFalse(pawn.canMove(board, new Location(1, 0), new Location(3, 0)));
    }

    @Test
    void PTC13_pawnTwoForward_notMoved_friendDest() {
        Piece pawn = new Piece(PieceType.PAWN, PieceColor.BLACK);
        Piece friendPawn = new Piece(PieceType.PAWN, PieceColor.BLACK);
        EasyMock.expect(board.getPiece(EasyMock.anyObject(Location.class)))
                .andReturn(null)        
                .andReturn(friendPawn); 
        EasyMock.replay(board);
        assertFalse(pawn.canMove(board, new Location(1, 0), new Location(3, 0)));
    }

    @ParameterizedTest(name = "Two forward blocked path: hasMoved=false, blockingPiece={0}")
    @MethodSource("providePathBlockedMoves")
    void pawnTwoForward_pathBlocked_invalid_Case3(PieceColor blockingColor) {
        Piece pawn = new Piece(PieceType.PAWN, PieceColor.BLACK);
        pawn.setMoved(false);

        Piece blockingPiece = new Piece(PieceType.PAWN, blockingColor);

        
        EasyMock.expect(board.getPiece(matchesLoc(2, 0))).andReturn(blockingPiece).anyTimes();

        
        EasyMock.expect(board.getPiece(matchesLoc(3, 0))).andReturn(null).anyTimes();

        EasyMock.replay(board);

        
        assertFalse(pawn.canMove(board, new Location(1, 0), new Location(3, 0)));

        EasyMock.verify(board);
    }

    private static Stream<Arguments> providePathBlockedMoves() {
        return Stream.of(
                Arguments.of(PieceColor.WHITE), 
                Arguments.of(PieceColor.BLACK)  
        );
    }

    @ParameterizedTest(name = "Two forward after moved: destination contains {0}")
    @MethodSource("provideAlreadyMovedTwoForwardCases")
    void pawnTwoForward_alreadyMoved_invalid_Case4(String caseName, Piece destPiece) {
        Piece pawn = new Piece(PieceType.PAWN, PieceColor.BLACK);
        pawn.setMoved(true); 

        EasyMock.expect(board.getPiece(matchesLoc(3, 0))).andReturn(null).anyTimes();
        EasyMock.expect(board.getPiece(matchesLoc(4, 0))).andReturn(destPiece).anyTimes();

        EasyMock.replay(board);

        assertFalse(pawn.canMove(board, new Location(2, 0), new Location(4, 0)));

        EasyMock.verify(board);
    }

    private static Stream<Arguments> provideAlreadyMovedTwoForwardCases() {
        return Stream.of(
                Arguments.of("Empty space",  null),                                
                Arguments.of("Foe piece",   new Piece(PieceType.PAWN, PieceColor.WHITE)), 
                Arguments.of("Friend piece",new Piece(PieceType.PAWN, PieceColor.BLACK))  
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
        Piece pawn = new Piece(PieceType.PAWN, PieceColor.BLACK);
        pawn.setMoved(hasMoved);

        
        EasyMock.expect(board.getPiece(matchesLoc(toRow, toCol))).andReturn(destPiece).anyTimes();
        EasyMock.replay(board);

        
        assertFalse(pawn.canMove(board, new Location(fromRow, fromCol), new Location(toRow, toCol)));

        EasyMock.verify(board);
    }

    private static Stream<Arguments> provideInvalidDiagonalCases() {
        Piece friend = new Piece(PieceType.PAWN, PieceColor.BLACK);

        return Stream.of(
                
                Arguments.of("PTC19: moved, right, empty",   true,  2, 1, 3, 2, null),
                Arguments.of("PTC20: moved, left, empty",    true,  2, 1, 3, 0, null),
                Arguments.of("PTC21: not moved, right, empty", false, 1, 1, 2, 2, null),
                Arguments.of("PTC22: not moved, left, empty",  false, 1, 1, 2, 0, null),

                
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
        Piece pawn = new Piece(PieceType.PAWN, PieceColor.BLACK);
        pawn.setMoved(false); 

        
        EasyMock.expect(board.getPiece(matchesLoc(toRow, toCol))).andReturn(destPiece).anyTimes();
        EasyMock.replay(board);

        
        assertFalse(pawn.canMove(board, new Location(fromRow, fromCol), new Location(toRow, toCol)));

        EasyMock.verify(board);
    }

    private static Stream<Arguments> provideInvalidTwoSpaceDiagonalCases() {
        Piece foe = new Piece(PieceType.PAWN, PieceColor.WHITE);
        Piece friend = new Piece(PieceType.PAWN, PieceColor.BLACK);

        return Stream.of(
                
                Arguments.of("PTC27: forward-right, empty", 1, 0, 3, 2, null),
                Arguments.of("PTC28: forward-left, empty",  1, 2, 3, 0, null),

                
                Arguments.of("PTC29: forward-right, foe",   1, 0, 3, 2, foe),
                Arguments.of("PTC30: forward-left, foe",    1, 2, 3, 0, foe),

                
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
        Piece pawn = new Piece(PieceType.PAWN, PieceColor.BLACK);
        pawn.setMoved(hasMoved);

        
        EasyMock.expect(board.getPiece(matchesLoc(toRow, toCol))).andReturn(destPiece).anyTimes();
        EasyMock.replay(board);

        
        assertFalse(pawn.canMove(board, new Location(fromRow, fromCol), new Location(toRow, toCol)));

        EasyMock.verify(board);
    }

    private static Stream<Arguments> provideInvalidBackwardCases() {
        Piece foe = new Piece(PieceType.PAWN, PieceColor.WHITE);
        Piece friend = new Piece(PieceType.PAWN, PieceColor.BLACK);

        return Stream.of(
                
                
                Arguments.of("PTC33: moved, straight back, empty",     true, 2, 0, 1, 0, null),
                Arguments.of("PTC34: moved, straight back, foe",       true, 2, 0, 1, 0, foe),
                Arguments.of("PTC35: moved, straight back, friend",    true, 2, 0, 1, 0, friend),
                
                Arguments.of("PTC36: moved, backward-right, empty",   true, 2, 1, 1, 2, null),
                Arguments.of("PTC37: moved, backward-right, foe",     true, 2, 1, 1, 2, foe),
                Arguments.of("PTC38: moved, backward-right, friend",  true, 2, 1, 1, 2, friend),
                
                Arguments.of("PTC39: moved, backward-left, empty",    true, 2, 1, 1, 0, null),
                Arguments.of("PTC40: moved, backward-left, foe",      true, 2, 1, 1, 0, foe),
                Arguments.of("PTC41: moved, backward-left, friend",   true, 2, 1, 1, 0, friend),

                
                
                Arguments.of("PTC42: not moved, straight back, empty",  false, 1, 0, 0, 0, null),
                Arguments.of("PTC43: not moved, straight back, foe",    false, 1, 0, 0, 0, foe),
                Arguments.of("PTC44: not moved, straight back, friend", false, 1, 0, 0, 0, friend),
                
                Arguments.of("PTC45: not moved, backward-right, empty", false, 1, 1, 0, 2, null),
                Arguments.of("PTC46: not moved, backward-right, foe",   false, 1, 1, 0, 2, foe),
                Arguments.of("PTC47: not moved, backward-right, friend",false, 1, 1, 0, 2, friend),
                
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
        Piece pawn = new Piece(PieceType.PAWN, PieceColor.BLACK);
        pawn.setMoved(true); 

        
        EasyMock.expect(board.getPiece(matchesLoc(toRow, toCol))).andReturn(destPiece).anyTimes();
        EasyMock.replay(board);

        
        assertFalse(pawn.canMove(board, new Location(fromRow, fromCol), new Location(toRow, toCol)));

        EasyMock.verify(board);
    }

    private static Stream<Arguments> provideInvalidSidewaysCases() {
        Piece foe = new Piece(PieceType.PAWN, PieceColor.WHITE);
        Piece friend = new Piece(PieceType.PAWN, PieceColor.BLACK);

        return Stream.of(
                
                Arguments.of("PTC51: left, empty",  2, 1, 2, 0, null),
                Arguments.of("PTC52: left, foe",    2, 1, 2, 0, foe),
                Arguments.of("PTC53: left, friend", 2, 1, 2, 0, friend),

                
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
        Piece pawn = new Piece(PieceType.PAWN, PieceColor.BLACK);
        pawn.setMoved(true); 

        
        EasyMock.expect(board.getPiece(matchesLoc(toRow, toCol))).andReturn(null).anyTimes();
        EasyMock.replay(board);

        
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
        Piece pawn = new Piece(PieceType.PAWN, PieceColor.BLACK);
        pawn.setMoved(hasMoved);

        
        
        EasyMock.expect(board.getPiece(matchesLoc(2, 0))).andReturn(pawn).anyTimes();
        EasyMock.replay(board);

        
        assertFalse(pawn.canMove(board, new Location(2, 0), new Location(2, 0)));

        EasyMock.verify(board);
    }

    private static Stream<Arguments> provideZeroDistanceCases() {
        return Stream.of(
                Arguments.of(true),  
                Arguments.of(false) 
        );
    }

    @Test
    void PTC64_pawn_diagonalTwoColumns_invalid() {
        Piece pawn = new Piece(PieceType.PAWN, PieceColor.BLACK);
        EasyMock.replay(board);
        assertFalse(pawn.canMove(board, new Location(2, 0), new Location(3, 2)));
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

        
        EasyMock.expect(board.getPiece(matchesLoc(toRow, toCol))).andReturn(destPiece).anyTimes();
        EasyMock.replay(board);

        
        assertTrue(rook.canMove(board, new Location(fromRow, fromCol), new Location(toRow, toCol)));

        EasyMock.verify(board);
    }

    private static Stream<Arguments> provideValidRookSingleMoves() {
        Piece foe = new Piece(PieceType.PAWN, PieceColor.BLACK);

        return Stream.of(
                
                Arguments.of("RTC1: forward, empty", 0, 0, 1, 0, null),
                Arguments.of("RTC2: forward, foe",   0, 0, 1, 0, foe),

                
                Arguments.of("RTC3: backward, empty",1, 0, 0, 0, null),
                Arguments.of("RTC4: backward, foe",  1, 0, 0, 0, foe),

                
                Arguments.of("RTC5: right, empty",   1, 0, 1, 1, null),
                Arguments.of("RTC6: right, foe",     1, 0, 1, 1, foe),

                
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

        
        EasyMock.expect(board.getPiece(EasyMock.anyObject(Location.class)))
                .andReturn(null).anyTimes();

        
        EasyMock.expect(board.getPiece(matchesLoc(toRow, toCol)))
                .andReturn(destPiece).anyTimes();

        EasyMock.replay(board);

        
        assertTrue(rook.canMove(board, new Location(fromRow, fromCol), new Location(toRow, toCol)));

        EasyMock.verify(board);
    }

    private static Stream<Arguments> provideValidRookMaxMoves() {
        Piece foe = new Piece(PieceType.PAWN, PieceColor.BLACK);

        return Stream.of(
                
                Arguments.of("RTC9: forward max, empty", 0, 0, 7, 0, null),
                Arguments.of("RTC10: forward max, foe",   0, 0, 7, 0, foe),

                
                Arguments.of("RTC11: backward max, empty",7, 0, 0, 0, null),
                Arguments.of("RTC12: backward max, foe",  7, 0, 0, 0, foe),

                
                Arguments.of("RTC13: right max, empty",   7, 0, 7, 7, null),
                Arguments.of("RTC14: right max, foe",     7, 0, 7, 7, foe),

                
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

        
        EasyMock.expect(board.getPiece(matchesLoc(toRow, toCol))).andReturn(friend).anyTimes();
        EasyMock.replay(board);

        
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

        
        EasyMock.expect(board.getPiece(matchesLoc(toRow, toCol)))
                .andReturn(friend).anyTimes();

        
        EasyMock.expect(board.getPiece(EasyMock.anyObject(Location.class)))
                .andReturn(null).anyTimes();

        EasyMock.replay(board);

        
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

        
        EasyMock.expect(board.getPiece(matchesLoc(blockRow, blockCol)))
                .andReturn(friendBlocker).anyTimes();

        
        EasyMock.expect(board.getPiece(matchesLoc(toRow, toCol)))
                .andReturn(destPiece).anyTimes();

        
        EasyMock.expect(board.getPiece(EasyMock.anyObject(Location.class)))
                .andReturn(null).anyTimes();

        EasyMock.replay(board);

        
        assertFalse(rook.canMove(board, new Location(fromRow, fromCol), new Location(toRow, toCol)));

        EasyMock.verify(board);
    }

    private static Stream<Arguments> provideFriendObstructedPathCases() {
        Piece friend = new Piece(PieceType.PAWN, PieceColor.WHITE);
        Piece foe = new Piece(PieceType.PAWN, PieceColor.BLACK);

        return Stream.of(
                
                Arguments.of("RTC25: forward max, blocked, dest empty",  0, 0,  1, 0,  7, 0, null),
                Arguments.of("RTC26: forward max, blocked, dest friend", 0, 0,  1, 0,  7, 0, friend),
                Arguments.of("RTC27: forward max, blocked, dest foe",    0, 0,  1, 0,  7, 0, foe),

                
                Arguments.of("RTC28: backward max, blocked, dest empty", 7, 0,  6, 0,  0, 0, null),
                Arguments.of("RTC29: backward max, blocked, dest friend",7, 0,  6, 0,  0, 0, friend),
                Arguments.of("RTC30: backward max, blocked, dest foe",   7, 0,  6, 0,  0, 0, foe),

                
                Arguments.of("RTC31: left max, blocked, dest empty",     7, 7,  7, 6,  7, 0, null),
                Arguments.of("RTC32: left max, blocked, dest friend",    7, 7,  7, 6,  7, 0, friend),
                Arguments.of("RTC33: left max, blocked, dest foe",       7, 7,  7, 6,  7, 0, foe),

                
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

        
        EasyMock.expect(board.getPiece(matchesLoc(blockRow, blockCol)))
                .andReturn(foeBlocker).anyTimes();

        
        EasyMock.expect(board.getPiece(matchesLoc(toRow, toCol)))
                .andReturn(destPiece).anyTimes();

        
        EasyMock.expect(board.getPiece(EasyMock.anyObject(Location.class)))
                .andReturn(null).anyTimes();

        EasyMock.replay(board);

        
        assertFalse(rook.canMove(board, new Location(fromRow, fromCol), new Location(toRow, toCol)));

        EasyMock.verify(board);
    }

    private static Stream<Arguments> provideFoeObstructedPathCases() {
        Piece friend = new Piece(PieceType.PAWN, PieceColor.WHITE);
        Piece foe = new Piece(PieceType.PAWN, PieceColor.BLACK);

        return Stream.of(
                
                Arguments.of("RTC37: forward max, foe-blocked, dest empty",  0, 0,  1, 0,  7, 0, null),
                Arguments.of("RTC38: forward max, foe-blocked, dest friend", 0, 0,  1, 0,  7, 0, friend),
                Arguments.of("RTC39: forward max, foe-blocked, dest foe",    0, 0,  1, 0,  7, 0, foe),

                
                Arguments.of("RTC40: backward max, foe-blocked, dest empty", 7, 0,  6, 0,  0, 0, null),
                Arguments.of("RTC41: backward max, foe-blocked, dest friend",7, 0,  6, 0,  0, 0, friend),
                Arguments.of("RTC42: backward max, foe-blocked, dest foe",   7, 0,  6, 0,  0, 0, foe),

                
                Arguments.of("RTC43: left max, foe-blocked, dest empty",     7, 7,  7, 6,  7, 0, null),
                Arguments.of("RTC44: left max, foe-blocked, dest friend",    7, 7,  7, 6,  7, 0, friend),
                Arguments.of("RTC45: left max, foe-blocked, dest foe",       7, 7,  7, 6,  7, 0, foe),

                
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

        
        EasyMock.expect(board.getPiece(matchesLoc(toRow, toCol))).andReturn(destPiece).anyTimes();
        EasyMock.replay(board);

        
        assertFalse(rook.canMove(board, new Location(fromRow, fromCol), new Location(toRow, toCol)));

        EasyMock.verify(board);
    }

    private static Stream<Arguments> provideDiagonalRookCases() {
        Piece friend = new Piece(PieceType.PAWN, PieceColor.WHITE);
        Piece foe = new Piece(PieceType.PAWN, PieceColor.BLACK);

        return Stream.of(
                
                Arguments.of("RTC49: forward-right, empty",  3, 4, 4, 3, null),
                Arguments.of("RTC50: forward-left, empty",   3, 4, 4, 5, null),
                Arguments.of("RTC51: backward-left, empty",  3, 4, 2, 5, null),
                Arguments.of("RTC52: backward-right, empty", 3, 4, 2, 3, null),

                
                Arguments.of("RTC53: forward-right, friend",  3, 4, 4, 3, friend),
                Arguments.of("RTC54: forward-left, friend",   3, 4, 4, 5, friend),
                Arguments.of("RTC55: backward-left, friend",  3, 4, 2, 5, friend),
                Arguments.of("RTC56: backward-right, friend", 3, 4, 2, 3, friend),

                
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

        
        EasyMock.expect(board.getPiece(EasyMock.anyObject(Location.class))).andReturn(null).anyTimes();
        EasyMock.replay(board);

        
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

        
        EasyMock.expect(board.getPiece(matchesLoc(3, 3))).andReturn(rook).anyTimes();
        EasyMock.replay(board);

        
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

        
        EasyMock.expect(board.getPiece(matchesLoc(toRow, toCol))).andReturn(null).anyTimes();
        EasyMock.replay(board);

        
        assertTrue(knight.canMove(board, new Location(fromRow, fromCol), new Location(toRow, toCol)));

        EasyMock.verify(board);
    }

    private static Stream<Arguments> provideValidKnightEmptyMoves() {
        return Stream.of(
                Arguments.of("KTC1: forward-left (2 up, 1 right)",  3, 3, 5, 4),
                Arguments.of("KTC2: forward-right (2 up, 1 left)",  3, 3, 5, 2),
                Arguments.of("KTC3: right-forward (1 up, 2 left)",  3, 3, 4, 1),
                Arguments.of("KTC4: right-backward (1 down, 2 left)",3, 3, 2, 1),
                Arguments.of("KTC5: backward-left (2 down, 1 right)",1, 4, 3, 3), 
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

        
        EasyMock.expect(board.getPiece(matchesLoc(toRow, toCol))).andReturn(foe).anyTimes();
        EasyMock.replay(board);

        
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

        
        EasyMock.expect(board.getPiece(matchesLoc(blockRow, blockCol)))
                .andReturn(friendBlocker).anyTimes();

        
        EasyMock.expect(board.getPiece(matchesLoc(toRow, toCol)))
                .andReturn(null).anyTimes();

        
        EasyMock.expect(board.getPiece(EasyMock.anyObject(Location.class)))
                .andReturn(null).anyTimes();

        EasyMock.replay(board);

        
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

        
        EasyMock.expect(board.getPiece(matchesLoc(blockRow, blockCol)))
                .andReturn(friendBlocker).anyTimes();

        
        EasyMock.expect(board.getPiece(matchesLoc(toRow, toCol)))
                .andReturn(foe).anyTimes();

        
        EasyMock.expect(board.getPiece(EasyMock.anyObject(Location.class)))
                .andReturn(null).anyTimes();

        EasyMock.replay(board);

        
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

        
        EasyMock.expect(board.getPiece(matchesLoc(toRow, toCol))).andReturn(friend).anyTimes();
        EasyMock.replay(board);

        
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

        
        EasyMock.expect(board.getPiece(matchesLoc(toRow, toCol))).andReturn(destPiece).anyTimes();
        EasyMock.replay(board);

        
        assertFalse(knight.canMove(board, new Location(fromRow, fromCol), new Location(toRow, toCol)));

        EasyMock.verify(board);
    }

    private static Stream<Arguments> provideInvalidKnightSingleDiagonalMoves() {
        Piece friend = new Piece(PieceType.PAWN, PieceColor.WHITE);
        Piece foe = new Piece(PieceType.PAWN, PieceColor.BLACK);

        return Stream.of(
                
                Arguments.of("KTC41: forward-right, empty",  3, 3, 4, 4, null),
                Arguments.of("KTC42: forward-right, friend", 3, 3, 4, 4, friend),
                Arguments.of("KTC43: forward-right, foe",    3, 3, 4, 4, foe),

                
                Arguments.of("KTC44: forward-left, empty",   3, 3, 4, 2, null),
                Arguments.of("KTC45: forward-left, friend",  3, 3, 4, 2, friend),
                Arguments.of("KTC46: forward-left, foe",     3, 3, 4, 2, foe),

                
                Arguments.of("KTC47: backward-right, empty", 3, 3, 2, 4, null),
                Arguments.of("KTC48: backward-right, friend",3, 3, 2, 4, friend),
                Arguments.of("KTC49: backward-right, foe",   3, 3, 2, 4, foe),

                
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

        
        EasyMock.expect(board.getPiece(matchesLoc(toRow, toCol))).andReturn(destPiece).anyTimes();
        EasyMock.replay(board);

        
        assertFalse(knight.canMove(board, new Location(fromRow, fromCol), new Location(toRow, toCol)));

        EasyMock.verify(board);
    }

    private static Stream<Arguments> provideInvalidKnightTwoDiagonalMoves() {
        Piece friend = new Piece(PieceType.PAWN, PieceColor.WHITE);
        Piece foe = new Piece(PieceType.PAWN, PieceColor.BLACK);

        return Stream.of(
                
                Arguments.of("KTC53: forward-right two-diagonals, empty",  3, 3, 5, 5, null),
                Arguments.of("KTC54: forward-right two-diagonals, friend", 3, 3, 5, 5, friend),
                Arguments.of("KTC55: forward-right two-diagonals, foe",    3, 3, 5, 5, foe),

                
                Arguments.of("KTC56: forward-left two-diagonals, empty",   3, 3, 5, 1, null),
                Arguments.of("KTC57: forward-left two-diagonals, friend",  3, 3, 5, 1, friend),
                Arguments.of("KTC58: forward-left two-diagonals, foe",     3, 3, 5, 1, foe),

                
                Arguments.of("KTC59: backward-right two-diagonals, empty", 3, 3, 1, 5, null),
                Arguments.of("KTC60: backward-right two-diagonals, friend",3, 3, 1, 5, friend),
                Arguments.of("KTC61: backward-right two-diagonals, foe",   3, 3, 1, 5, foe),

                
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

        
        EasyMock.expect(board.getPiece(matchesLoc(toRow, toCol))).andReturn(destPiece).anyTimes();
        EasyMock.replay(board);

        
        assertFalse(knight.canMove(board, new Location(fromRow, fromCol), new Location(toRow, toCol)));

        EasyMock.verify(board);
    }

    private static Stream<Arguments> provideInvalidKnightSingleOrthogonalMoves() {
        Piece friend = new Piece(PieceType.PAWN, PieceColor.WHITE);
        Piece foe = new Piece(PieceType.PAWN, PieceColor.BLACK);

        return Stream.of(
                
                Arguments.of("KTC65: forward one space, empty",  3, 3, 4, 3, null),
                Arguments.of("KTC66: forward one space, friend", 3, 3, 4, 3, friend),
                Arguments.of("KTC67: forward one space, foe",    3, 3, 4, 3, foe),

                
                Arguments.of("KTC68: backward one space, empty", 3, 3, 2, 3, null),
                Arguments.of("KTC69: backward one space, friend",3, 3, 2, 3, friend),
                Arguments.of("KTC70: backward one space, foe",   3, 3, 2, 3, foe),

                
                Arguments.of("KTC71: left one space, empty",     3, 3, 3, 2, null),
                Arguments.of("KTC72: left one space, friend",    3, 3, 3, 2, friend),
                Arguments.of("KTC73: left one space, foe",       3, 3, 3, 2, foe),

                
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

        
        EasyMock.expect(board.getPiece(EasyMock.anyObject(Location.class))).andReturn(null).anyTimes();
        EasyMock.replay(board);

        
        assertFalse(knight.canMove(board, new Location(fromRow, fromCol), new Location(toRow, toCol)));

        EasyMock.verify(board);
    }

    private static Stream<Arguments> provideOutOfBoundsKnightCases() {
        return Stream.of(
                
                Arguments.of("KTC77: top boundary, forward-left",  6, 3, 8, 2),
                Arguments.of("KTC78: top boundary, forward-right", 6, 3, 8, 4),
                Arguments.of("KTC79: top boundary, left-forward",  7, 3, 8, 1),
                Arguments.of("KTC80: top boundary, right-forward", 7, 3, 8, 5),

                
                Arguments.of("KTC81: bottom boundary, backward-left",  1, 3, -1, 2),
                Arguments.of("KTC82: bottom boundary, backward-right", 1, 3, -1, 4),
                Arguments.of("KTC83: bottom boundary, left-backward",  0, 3, -1, 1),
                Arguments.of("KTC84: bottom boundary, right-backward", 0, 3, -1, 5),

                
                Arguments.of("KTC85: left boundary, forward-left",  3, 1, 5, -1), 
                Arguments.of("KTC86: left boundary, left-forward",  3, 1, 4, -1), 
                Arguments.of("KTC87: left boundary, left-backward", 3, 1, 2, -1), 
                Arguments.of("KTC88: left boundary, backward-left", 3, 1, 1, -1), 

                
                Arguments.of("KTC89: right boundary, forward-right", 3, 6, 5, 8),
                Arguments.of("KTC90: right boundary, right-forward", 3, 6, 4, 8), 
                Arguments.of("KTC91: right boundary, right-backward",3, 6, 2, 8), 
                Arguments.of("KTC92: right boundary, backward-right",3, 6, 1, 8)
        );
    }

    @Test
    void knightZeroDistanceMove_invalid() {
        Piece knight = new Piece(PieceType.KNIGHT, PieceColor.WHITE);

        
        EasyMock.expect(board.getPiece(matchesLoc(3, 3))).andReturn(knight).anyTimes();
        EasyMock.replay(board);

        
        assertFalse(knight.canMove(board, new Location(3, 3), new Location(3, 3)));

        EasyMock.verify(board);
    }

    @ParameterizedTest(name = "Valid Knight jump over foe: {0}")
    @MethodSource("provideValidKnightFoeObstructedPathCases")
    void knightLMove_foeObstructedPath_valid(
            String testName,
            int fromRow, int fromCol,
            int blockRow, int blockCol,
            int toRow, int toCol,
            Piece destPiece
    ) {
        Piece knight = new Piece(PieceType.KNIGHT, PieceColor.WHITE);
        Piece foeBlocker = new Piece(PieceType.PAWN, PieceColor.BLACK);

        
        EasyMock.expect(board.getPiece(matchesLoc(blockRow, blockCol)))
                .andReturn(foeBlocker).anyTimes();

        
        EasyMock.expect(board.getPiece(matchesLoc(toRow, toCol)))
                .andReturn(destPiece).anyTimes();

        
        EasyMock.expect(board.getPiece(EasyMock.anyObject(Location.class)))
                .andReturn(null).anyTimes();

        EasyMock.replay(board);

        
        assertTrue(knight.canMove(board, new Location(fromRow, fromCol), new Location(toRow, toCol)));

        EasyMock.verify(board);
    }

    private static Stream<Arguments> provideValidKnightFoeObstructedPathCases() {
        Piece enemyCaptureTarget = new Piece(PieceType.PAWN, PieceColor.BLACK);

        return Stream.of(
                Arguments.of("KTC94: forward-left, jump enemy to empty square", 3, 3, 4, 3, 5, 4, null),
                Arguments.of("KTC95: forward-left, jump enemy to capture enemy", 3, 3, 4, 3, 5, 4, enemyCaptureTarget)
        );
    }

    @ParameterizedTest(name = "Valid Bishop single step: {0}")
    @MethodSource("provideValidBishopSingleEmptyMoves")
    void bishopSingleMove_empty_valid(
            String testName,
            int fromRow, int fromCol,
            int toRow, int toCol
    ) {
        Piece bishop = new Piece(PieceType.BISHOP, PieceColor.WHITE);

        
        EasyMock.expect(board.getPiece(matchesLoc(toRow, toCol))).andReturn(null).anyTimes();
        EasyMock.replay(board);

        
        assertTrue(bishop.canMove(board, new Location(fromRow, fromCol), new Location(toRow, toCol)));

        EasyMock.verify(board);
    }

    private static Stream<Arguments> provideValidBishopSingleEmptyMoves() {
        return Stream.of(
                Arguments.of("BTC1: forward-left (row+1, col-1)",   1, 1, 2, 0),
                Arguments.of("BTC2: forward-right (row+1, col+1)",  1, 1, 2, 2),
                Arguments.of("BTC3: backward-right (row-1, col+1)", 1, 1, 0, 2),
                Arguments.of("BTC4: backward-left (row-1, col-1)",  1, 1, 0, 0)
        );
    }

    @ParameterizedTest(name = "Valid Bishop single capture: {0}")
    @MethodSource("provideValidBishopSingleFoeMoves")
    void bishopSingleMove_foeOccupied_valid(
            String testName,
            int fromRow, int fromCol,
            int toRow, int toCol
    ) {
        Piece bishop = new Piece(PieceType.BISHOP, PieceColor.WHITE);
        Piece foe = new Piece(PieceType.PAWN, PieceColor.BLACK);

        
        EasyMock.expect(board.getPiece(matchesLoc(toRow, toCol))).andReturn(foe).anyTimes();
        EasyMock.replay(board);

        
        assertTrue(bishop.canMove(board, new Location(fromRow, fromCol), new Location(toRow, toCol)));

        EasyMock.verify(board);
    }

    private static Stream<Arguments> provideValidBishopSingleFoeMoves() {
        return Stream.of(
                Arguments.of("BTC5: forward-left capture (row+1, col-1)",   1, 1, 2, 0),
                Arguments.of("BTC6: forward-right capture (row+1, col+1)",  1, 1, 2, 2),
                Arguments.of("BTC7: backward-right capture (row-1, col+1)", 1, 1, 0, 2),
                Arguments.of("BTC8: backward-left capture (row-1, col-1)",  1, 1, 0, 0)
        );
    }

    @ParameterizedTest(name = "Valid Bishop max slide: {0}")
    @MethodSource("provideValidBishopMaxEmptyMoves")
    void bishopMaxMove_empty_valid(
            String testName,
            int fromRow, int fromCol,
            int toRow, int toCol
    ) {
        Piece bishop = new Piece(PieceType.BISHOP, PieceColor.WHITE);

        
        EasyMock.expect(board.getPiece(EasyMock.anyObject(Location.class))).andReturn(null).anyTimes();
        EasyMock.replay(board);

        
        assertTrue(bishop.canMove(board, new Location(fromRow, fromCol), new Location(toRow, toCol)));

        EasyMock.verify(board);
    }

    private static Stream<Arguments> provideValidBishopMaxEmptyMoves() {
        return Stream.of(
                Arguments.of("BTC9: forward-right max (0,0 to 7,7)",  0, 0, 7, 7),
                Arguments.of("BTC10: forward-left max (0,7 to 7,0)",  0, 7, 7, 0),
                Arguments.of("BTC11: backward-left max (7,7 to 0,0)", 7, 7, 0, 0),
                Arguments.of("BTC12: backward-right max (7,0 to 0,7)",7, 0, 0, 7)
        );
    }

    @ParameterizedTest(name = "Valid Bishop max capture: {0}")
    @MethodSource("provideValidBishopMaxCaptureCases")
    void bishopMaxMove_foeOccupied_valid(
            String testName,
            int fromRow, int fromCol,
            int toRow, int toCol
    ) {
        Piece bishop = new Piece(PieceType.BISHOP, PieceColor.WHITE);
        Piece foe = new Piece(PieceType.PAWN, PieceColor.BLACK);

        
        EasyMock.expect(board.getPiece(matchesLoc(toRow, toCol))).andReturn(foe).anyTimes();

        
        EasyMock.expect(board.getPiece(EasyMock.anyObject(Location.class))).andReturn(null).anyTimes();

        EasyMock.replay(board);

        
        assertTrue(bishop.canMove(board, new Location(fromRow, fromCol), new Location(toRow, toCol)));

        EasyMock.verify(board);
    }

    private static Stream<Arguments> provideValidBishopMaxCaptureCases() {
        return Stream.of(
                Arguments.of("BTC13: forward-right max capture (0,0 to 7,7)",  0, 0, 7, 7),
                Arguments.of("BTC14: forward-left max capture (0,7 to 7,0)",  0, 7, 7, 0),
                Arguments.of("BTC15: backward-left max capture (7,7 to 0,0)", 7, 7, 0, 0),
                Arguments.of("BTC16: backward-right max capture (7,0 to 0,7)",7, 0, 0, 7)
        );
    }

    @ParameterizedTest(name = "Invalid Bishop friend landing: {0}")
    @MethodSource("provideInvalidBishopSingleFriendMoves")
    void bishopSingleMove_friendOccupied_invalid(
            String testName,
            int fromRow, int fromCol,
            int toRow, int toCol
    ) {
        Piece bishop = new Piece(PieceType.BISHOP, PieceColor.WHITE);
        Piece friend = new Piece(PieceType.PAWN, PieceColor.WHITE);

        
        EasyMock.expect(board.getPiece(matchesLoc(toRow, toCol))).andReturn(friend).anyTimes();
        EasyMock.replay(board);

        
        assertFalse(bishop.canMove(board, new Location(fromRow, fromCol), new Location(toRow, toCol)));

        EasyMock.verify(board);
    }

    private static Stream<Arguments> provideInvalidBishopSingleFriendMoves() {
        return Stream.of(
                Arguments.of("BTC17: forward-left friend block (row+1, col-1)",   1, 1, 2, 0),
                Arguments.of("BTC18: forward-right friend block (row+1, col+1)",  1, 1, 2, 2),
                Arguments.of("BTC19: backward-right friend block (row-1, col+1)", 1, 1, 0, 2),
                Arguments.of("BTC20: backward-left friend block (row-1, col-1)",  1, 1, 0, 0)
        );
    }

    @ParameterizedTest(name = "Invalid Bishop max friend landing: {0}")
    @MethodSource("provideInvalidBishopMaxFriendCases")
    void bishopMaxMove_friendOccupied_invalid(
            String testName,
            int fromRow, int fromCol,
            int toRow, int toCol
    ) {
        Piece bishop = new Piece(PieceType.BISHOP, PieceColor.WHITE);
        Piece friend = new Piece(PieceType.PAWN, PieceColor.WHITE);

        
        EasyMock.expect(board.getPiece(matchesLoc(toRow, toCol))).andReturn(friend).anyTimes();

        
        EasyMock.expect(board.getPiece(EasyMock.anyObject(Location.class))).andReturn(null).anyTimes();

        EasyMock.replay(board);

        
        assertFalse(bishop.canMove(board, new Location(fromRow, fromCol), new Location(toRow, toCol)));

        EasyMock.verify(board);
    }

    private static Stream<Arguments> provideInvalidBishopMaxFriendCases() {
        return Stream.of(
                Arguments.of("BTC21: forward-right max friend landing (0,0 to 7,7)",  0, 0, 7, 7),
                Arguments.of("BTC22: forward-left max friend landing (0,7 to 7,0)",  0, 7, 7, 0),
                Arguments.of("BTC23: backward-left max friend landing (7,7 to 0,0)", 7, 7, 0, 0),
                Arguments.of("BTC24: backward-right max friend landing (7,0 to 0,7)",7, 0, 0, 7)
        );
    }

    @ParameterizedTest(name = "Invalid Bishop path block: {0}")
    @MethodSource("provideObstructedBishopPathCases")
    void bishopMaxMove_obstructedPath_invalid(
            String testName,
            int fromRow, int fromCol,
            int blockRow, int blockCol, Piece blockerPiece,
            int toRow, int toCol
    ) {
        Piece bishop = new Piece(PieceType.BISHOP, PieceColor.WHITE);

        
        EasyMock.expect(board.getPiece(matchesLoc(blockRow, blockCol)))
                .andReturn(blockerPiece).anyTimes();

        
        EasyMock.expect(board.getPiece(matchesLoc(toRow, toCol)))
                .andReturn(null).anyTimes();

        
        EasyMock.expect(board.getPiece(EasyMock.anyObject(Location.class)))
                .andReturn(null).anyTimes();

        EasyMock.replay(board);

        
        assertFalse(bishop.canMove(board, new Location(fromRow, fromCol), new Location(toRow, toCol)));

        EasyMock.verify(board);
    }

    private static Stream<Arguments> provideObstructedBishopPathCases() {
        Piece friend = new Piece(PieceType.PAWN, PieceColor.WHITE);
        Piece foe = new Piece(PieceType.PAWN, PieceColor.BLACK);

        return Stream.of(
                
                Arguments.of("BTC25: forward-right max, friend-blocked", 0, 0,  1, 1, friend,  7, 7),
                Arguments.of("BTC26: forward-right max, foe-blocked",    0, 0,  1, 1, foe,     7, 7),

                
                Arguments.of("BTC27: forward-left max, friend-blocked",  0, 7,  1, 6, friend,  7, 0),
                Arguments.of("BTC28: forward-left max, foe-blocked",     0, 7,  1, 6, foe,     7, 0),

                
                Arguments.of("BTC29: backward-right max, friend-blocked",7, 0,  6, 1, friend,  0, 7),
                Arguments.of("BTC30: backward-right max, foe-blocked",   7, 0,  6, 1, foe,     0, 7),

                
                Arguments.of("BTC31: backward-left max, friend-blocked", 7, 7,  6, 6, friend,  0, 0),
                Arguments.of("BTC32: backward-left max, foe-blocked",    7, 7,  6, 6, foe,     0, 0)
        );
    }

    @ParameterizedTest(name = "Invalid Bishop combination block: {0}")
    @MethodSource("provideObstructedPathFriendDestBishopCases")
    void bishopMaxMove_obstructedPath_friendDest_invalid(
            String testName,
            int fromRow, int fromCol,
            int blockRow, int blockCol, Piece blockerPiece,
            int toRow, int toCol
    ) {
        Piece bishop = new Piece(PieceType.BISHOP, PieceColor.WHITE);
        Piece friendDest = new Piece(PieceType.PAWN, PieceColor.WHITE);

        
        EasyMock.expect(board.getPiece(matchesLoc(blockRow, blockCol)))
                .andReturn(blockerPiece).anyTimes();

        
        EasyMock.expect(board.getPiece(matchesLoc(toRow, toCol)))
                .andReturn(friendDest).anyTimes();

        
        EasyMock.expect(board.getPiece(EasyMock.anyObject(Location.class)))
                .andReturn(null).anyTimes();

        EasyMock.replay(board);

        
        assertFalse(bishop.canMove(board, new Location(fromRow, fromCol), new Location(toRow, toCol)));

        EasyMock.verify(board);
    }

    private static Stream<Arguments> provideObstructedPathFriendDestBishopCases() {
        Piece friend = new Piece(PieceType.PAWN, PieceColor.WHITE);
        Piece foe = new Piece(PieceType.PAWN, PieceColor.BLACK);

        return Stream.of(
                
                Arguments.of("BTC33: forward-right, path friend-blocked, dest friend", 0, 0,  1, 1, friend,  7, 7),
                Arguments.of("BTC34: forward-right, path foe-blocked, dest friend",    0, 0,  1, 1, foe,     7, 7),

                
                Arguments.of("BTC35: forward-left, path friend-blocked, dest friend",  0, 7,  1, 6, friend,  7, 0),
                Arguments.of("BTC36: forward-left, path foe-blocked, dest friend",     0, 7,  1, 6, foe,     7, 0),

                
                Arguments.of("BTC37: backward-right, path friend-blocked, dest friend",7, 0,  6, 1, friend,  0, 7),
                Arguments.of("BTC38: backward-right, path foe-blocked, dest friend",   7, 0,  6, 1, foe,     0, 7),

                
                Arguments.of("BTC39: backward-left, path friend-blocked, dest friend", 7, 7,  6, 6, friend,  0, 0),
                Arguments.of("BTC40: backward-left, path foe-blocked, dest friend",    7, 7,  6, 6, foe,     0, 0)
        );
    }

    @ParameterizedTest(name = "Invalid Bishop blocked capture: {0}")
    @MethodSource("provideObstructedPathFoeDestBishopCases")
    void bishopMaxMove_obstructedPath_foeDest_invalid(
            String testName,
            int fromRow, int fromCol,
            int blockRow, int blockCol, Piece blockerPiece,
            int toRow, int toCol
    ) {
        Piece bishop = new Piece(PieceType.BISHOP, PieceColor.WHITE);
        Piece foeDest = new Piece(PieceType.PAWN, PieceColor.BLACK);

        
        EasyMock.expect(board.getPiece(matchesLoc(blockRow, blockCol)))
                .andReturn(blockerPiece).anyTimes();

        
        EasyMock.expect(board.getPiece(matchesLoc(toRow, toCol)))
                .andReturn(foeDest).anyTimes();

        
        EasyMock.expect(board.getPiece(EasyMock.anyObject(Location.class)))
                .andReturn(null).anyTimes();

        EasyMock.replay(board);

        
        assertFalse(bishop.canMove(board, new Location(fromRow, fromCol), new Location(toRow, toCol)));

        EasyMock.verify(board);
    }

    private static Stream<Arguments> provideObstructedPathFoeDestBishopCases() {
        Piece friend = new Piece(PieceType.PAWN, PieceColor.WHITE);
        Piece foe = new Piece(PieceType.PAWN, PieceColor.BLACK);

        return Stream.of(
                
                Arguments.of("BTC41: forward-right, path friend-blocked, dest foe", 0, 0,  1, 1, friend,  7, 7),
                Arguments.of("BTC42: forward-right, path foe-blocked, dest foe",    0, 0,  1, 1, foe,     7, 7),

                
                Arguments.of("BTC43: forward-left, path friend-blocked, dest foe",  0, 7,  1, 6, friend,  7, 0),
                Arguments.of("BTC44: forward-left, path foe-blocked, dest foe",     0, 7,  1, 6, foe,     7, 0),

                
                Arguments.of("BTC45: backward-right, path friend-blocked, dest foe",7, 0,  6, 1, friend,  0, 7),
                Arguments.of("BTC46: backward-right, path foe-blocked, dest foe",   7, 0,  6, 1, foe,     0, 7),

                
                Arguments.of("BTC47: backward-left, path friend-blocked, dest foe", 7, 7,  6, 6, friend,  0, 0),
                Arguments.of("BTC48: backward-left, path foe-blocked, dest foe",    7, 7,  6, 6, foe,     0, 0)
        );
    }

    @ParameterizedTest(name = "Invalid Bishop orthogonal move: {0}")
    @MethodSource("provideInvalidBishopOrthogonalMoves")
    void bishopMove_orthogonal_invalid(
            String testName,
            int fromRow, int fromCol,
            int toRow, int toCol,
            Piece destPiece
    ) {
        Piece bishop = new Piece(PieceType.BISHOP, PieceColor.WHITE);

        
        EasyMock.expect(board.getPiece(matchesLoc(toRow, toCol))).andReturn(destPiece).anyTimes();
        EasyMock.replay(board);

        
        assertFalse(bishop.canMove(board, new Location(fromRow, fromCol), new Location(toRow, toCol)));

        EasyMock.verify(board);
    }

    private static Stream<Arguments> provideInvalidBishopOrthogonalMoves() {
        Piece friend = new Piece(PieceType.PAWN, PieceColor.WHITE);
        Piece foe = new Piece(PieceType.PAWN, PieceColor.BLACK);

        return Stream.of(
                
                Arguments.of("BTC49: forward one space, empty",  0, 0, 1, 0, null),
                Arguments.of("BTC50: forward one space, friend", 0, 0, 1, 0, friend),
                Arguments.of("BTC51: forward one space, foe",    0, 0, 1, 0, foe),

                
                Arguments.of("BTC52: backward one space, empty", 1, 0, 0, 0, null),
                Arguments.of("BTC53: backward one space, friend",1, 0, 0, 0, friend),
                Arguments.of("BTC54: backward one space, foe",   1, 0, 0, 0, foe),

                
                Arguments.of("BTC55: left one space, empty",     0, 1, 0, 0, null),
                Arguments.of("BTC56: left one space, friend",    0, 1, 0, 0, friend),
                Arguments.of("BTC57: left one space, foe",       0, 1, 0, 0, foe),

                
                Arguments.of("BTC58: right one space, empty",    0, 0, 0, 1, null),
                Arguments.of("BTC59: right one space, friend",   0, 0, 0, 1, friend),
                Arguments.of("BTC60: right one space, foe",      0, 0, 0, 1, foe)
        );
    }

    @ParameterizedTest(name = "Bishop out-of-bounds invalid: {0}")
    @MethodSource("provideOutOfBoundsBishopCases")
    void bishopOutOfBoundsMove_invalid(
            String testName,
            int fromRow, int fromCol,
            int toRow, int toCol
    ) {
        Piece bishop = new Piece(PieceType.BISHOP, PieceColor.WHITE);

        
        EasyMock.expect(board.getPiece(EasyMock.anyObject(Location.class))).andReturn(null).anyTimes();
        EasyMock.replay(board);

        
        assertFalse(bishop.canMove(board, new Location(fromRow, fromCol), new Location(toRow, toCol)));

        EasyMock.verify(board);
    }

    private static Stream<Arguments> provideOutOfBoundsBishopCases() {
        return Stream.of(
                Arguments.of("BTC61: out-of-bounds top edge",    7, 6,  8, 7),
                Arguments.of("BTC62: out-of-bounds bottom edge", 0, 1, -1, 0),
                Arguments.of("BTC63: out-of-bounds left edge",   1, 0,  2, -1),
                Arguments.of("BTC64: out-of-bounds right edge",  1, 7,  2, 8)
        );
    }

    @Test
    void bishopZeroDistanceMove_invalid() {
        Piece bishop = new Piece(PieceType.BISHOP, PieceColor.WHITE);

        
        EasyMock.expect(board.getPiece(matchesLoc(3, 3))).andReturn(bishop).anyTimes();
        EasyMock.replay(board);

        
        assertFalse(bishop.canMove(board, new Location(3, 3), new Location(3, 3)));

        EasyMock.verify(board);
    }

    @ParameterizedTest(name = "Valid King orthogonal move: {0}")
    @MethodSource("provideValidKingOrthogonalCases")
    void kingMove_orthogonal_valid(
            String testName,
            int fromRow, int fromCol,
            int toRow, int toCol,
            Piece destPiece
    ) {
        Piece king = new Piece(PieceType.KING, PieceColor.WHITE);

        
        EasyMock.expect(board.getPiece(matchesLoc(toRow, toCol))).andReturn(destPiece).anyTimes();
        EasyMock.replay(board);

        
        assertTrue(king.canMove(board, new Location(fromRow, fromCol), new Location(toRow, toCol)));

        EasyMock.verify(board);
    }

    private static Stream<Arguments> provideValidKingOrthogonalCases() {
        Piece foe = new Piece(PieceType.PAWN, PieceColor.BLACK);

        return Stream.of(
                
                Arguments.of("KiTC1: forward, empty",  3, 3, 4, 3, null),
                Arguments.of("KiTC2: forward, foe",    3, 3, 4, 3, foe),

                
                Arguments.of("KiTC3: backward, empty", 3, 3, 2, 3, null),
                Arguments.of("KiTC4: backward, foe",   3, 3, 2, 3, foe),

                
                Arguments.of("KiTC5: left, empty",     3, 3, 3, 2, null),
                Arguments.of("KiTC6: left, foe",       3, 3, 3, 2, foe),

                
                Arguments.of("KiTC7: right, empty",    3, 3, 3, 4, null),
                Arguments.of("KiTC8: right, foe",      3, 3, 3, 4, foe)
        );
    }

    @ParameterizedTest(name = "Valid King diagonal move: {0}")
    @MethodSource("provideValidKingDiagonalCases")
    void kingMove_diagonal_valid(
            String testName,
            int fromRow, int fromCol,
            int toRow, int toCol,
            Piece destPiece
    ) {
        Piece king = new Piece(PieceType.KING, PieceColor.WHITE);

        
        EasyMock.expect(board.getPiece(matchesLoc(toRow, toCol))).andReturn(destPiece).anyTimes();
        EasyMock.replay(board);

        
        assertTrue(king.canMove(board, new Location(fromRow, fromCol), new Location(toRow, toCol)));

        EasyMock.verify(board);
    }

    private static Stream<Arguments> provideValidKingDiagonalCases() {
        Piece foe = new Piece(PieceType.PAWN, PieceColor.BLACK);

        return Stream.of(
                
                Arguments.of("KiTC9: forward-left, empty",  3, 3, 4, 2, null),
                Arguments.of("KiTC10: forward-left, foe",    3, 3, 4, 2, foe),

                
                Arguments.of("KiTC11: forward-right, empty", 3, 3, 4, 4, null),
                Arguments.of("KiTC12: forward-right, foe",   3, 3, 4, 4, foe),

                
                Arguments.of("KiTC13: backward-left, empty", 3, 3, 2, 2, null),
                Arguments.of("KiTC14: backward-left, foe",   3, 3, 2, 2, foe),

                
                Arguments.of("KiTC15: backward-right, empty",3, 3, 2, 4, null),
                Arguments.of("KiTC16: backward-right, foe",  3, 3, 2, 4, foe)
        );
    }

    @ParameterizedTest(name = "Valid King Castling: {0}")
    @MethodSource("provideValidCastlingCases")
    void kingMove_castling_valid(
            String testName,
            int fromRow, int fromCol,
            int toRow, int toCol,
            int rookRow, int rookCol
    ) {
        
        Piece king = new Piece(PieceType.KING, PieceColor.WHITE);
        king.setMoved(false);

        
        Piece castlingRook = new Piece(PieceType.ROOK, PieceColor.WHITE);
        castlingRook.setMoved(false);

        
        EasyMock.expect(board.getPiece(matchesLoc(rookRow, rookCol))).andReturn(castlingRook).anyTimes();

        
        EasyMock.expect(board.getPiece(EasyMock.anyObject(Location.class))).andReturn(null).anyTimes();

        EasyMock.replay(board);

        
        assertTrue(king.canMove(board, new Location(fromRow, fromCol), new Location(toRow, toCol)));

        EasyMock.verify(board);
    }

    private static Stream<Arguments> provideValidCastlingCases() {
        return Stream.of(
                Arguments.of("KiTC17: Kingside Castling",  0, 4, 0, 6,  0, 7),
                Arguments.of("KiTC18: Queenside Castling", 0, 4, 0, 2,  0, 0)
        );
    }

    @ParameterizedTest(name = "Invalid King orthogonal friend landing: {0}")
    @MethodSource("provideInvalidKingOrthogonalFriendMoves")
    void kingMove_orthogonalFriendOccupied_invalid(
            String testName,
            int fromRow, int fromCol,
            int toRow, int toCol
    ) {
        Piece king = new Piece(PieceType.KING, PieceColor.WHITE);
        Piece friend = new Piece(PieceType.PAWN, PieceColor.WHITE);

        
        EasyMock.expect(board.getPiece(matchesLoc(toRow, toCol))).andReturn(friend).anyTimes();
        EasyMock.replay(board);

        
        assertFalse(king.canMove(board, new Location(fromRow, fromCol), new Location(toRow, toCol)));

        EasyMock.verify(board);
    }

    private static Stream<Arguments> provideInvalidKingOrthogonalFriendMoves() {
        return Stream.of(
                Arguments.of("KiTC19: forward, friend block",  3, 3, 4, 3),
                Arguments.of("KiTC20: backward, friend block", 3, 3, 2, 3),
                Arguments.of("KiTC21: left, friend block",     3, 3, 3, 2),
                Arguments.of("KiTC22: right, friend block",    3, 3, 3, 4)
        );
    }

    @ParameterizedTest(name = "Invalid King diagonal friend landing: {0}")
    @MethodSource("provideInvalidKingDiagonalFriendMoves")
    void kingMove_diagonalFriendOccupied_invalid(
            String testName,
            int fromRow, int fromCol,
            int toRow, int toCol
    ) {
        Piece king = new Piece(PieceType.KING, PieceColor.WHITE);
        Piece friend = new Piece(PieceType.PAWN, PieceColor.WHITE);

        
        EasyMock.expect(board.getPiece(matchesLoc(toRow, toCol))).andReturn(friend).anyTimes();
        EasyMock.replay(board);

        
        assertFalse(king.canMove(board, new Location(fromRow, fromCol), new Location(toRow, toCol)));

        EasyMock.verify(board);
    }

    private static Stream<Arguments> provideInvalidKingDiagonalFriendMoves() {
        return Stream.of(
                Arguments.of("KiTC23: forward-left, friend block",  3, 3, 4, 2),
                Arguments.of("KiTC24: forward-right, friend block", 3, 3, 4, 4),
                Arguments.of("KiTC25: backward-left, friend block", 3, 3, 2, 2),
                Arguments.of("KiTC26: backward-right, friend block", 3, 3, 2, 4)
        );
    }

    @ParameterizedTest(name = "Invalid King two-space orthogonal: {0}")
    @MethodSource("provideInvalidKingTwoSpaceOrthogonalMoves")
    void kingMove_twoSpaceOrthogonal_invalid(
            String testName,
            int fromRow, int fromCol,
            int toRow, int toCol,
            Piece destPiece
    ) {
        Piece king = new Piece(PieceType.KING, PieceColor.WHITE);

        
        EasyMock.expect(board.getPiece(matchesLoc(toRow, toCol))).andReturn(destPiece).anyTimes();
        EasyMock.replay(board);

        
        assertFalse(king.canMove(board, new Location(fromRow, fromCol), new Location(toRow, toCol)));

        EasyMock.verify(board);
    }

    private static Stream<Arguments> provideInvalidKingTwoSpaceOrthogonalMoves() {
        Piece foe = new Piece(PieceType.PAWN, PieceColor.BLACK);

        return Stream.of(
                
                Arguments.of("KiTC27: forward two spaces, empty",  3, 3, 5, 3, null),
                Arguments.of("KiTC28: forward two spaces, foe",    3, 3, 5, 3, foe),

                
                Arguments.of("KiTC29: backward two spaces, empty", 3, 3, 1, 3, null),
                Arguments.of("KiTC30: backward two spaces, foe",   3, 3, 1, 3, foe),

                
                Arguments.of("KiTC31: left two spaces, empty",     3, 3, 3, 1, null),
                Arguments.of("KiTC32: left two spaces, foe",       3, 3, 3, 1, foe),

                
                Arguments.of("KiTC33: right two spaces, empty",    3, 3, 3, 5, null),
                Arguments.of("KiTC34: right two spaces, foe",      3, 3, 3, 5, foe)
        );
    }

    @ParameterizedTest(name = "Invalid King two-space diagonal: {0}")
    @MethodSource("provideInvalidKingTwoSpaceDiagonalMoves")
    void kingMove_twoSpaceDiagonal_invalid(
            String testName,
            int fromRow, int fromCol,
            int toRow, int toCol,
            Piece destPiece
    ) {
        Piece king = new Piece(PieceType.KING, PieceColor.WHITE);

        
        EasyMock.expect(board.getPiece(matchesLoc(toRow, toCol))).andReturn(destPiece).anyTimes();
        EasyMock.replay(board);

        
        assertFalse(king.canMove(board, new Location(fromRow, fromCol), new Location(toRow, toCol)));

        EasyMock.verify(board);
    }

    private static Stream<Arguments> provideInvalidKingTwoSpaceDiagonalMoves() {
        Piece foe = new Piece(PieceType.PAWN, PieceColor.BLACK);

        return Stream.of(
                
                Arguments.of("KiTC35: forward-left two spaces, empty",  3, 3, 5, 1, null),
                Arguments.of("KiTC36: forward-left two spaces, foe",    3, 3, 5, 1, foe),

                
                Arguments.of("KiTC37: forward-right two spaces, empty", 3, 3, 5, 5, null),
                Arguments.of("KiTC38: forward-right two spaces, foe",   3, 3, 5, 5, foe),

                
                Arguments.of("KiTC39: backward-left two spaces, empty", 3, 3, 1, 1, null),
                Arguments.of("KiTC40: backward-left two spaces, foe",   3, 3, 1, 1, foe),

                
                Arguments.of("KiTC41: backward-right two spaces, empty",3, 3, 1, 5, null),
                Arguments.of("KiTC42: backward-right two spaces, foe",  3, 3, 1, 5, foe)
        );
    }

    @ParameterizedTest(name = "King out-of-bounds invalid: {0}")
    @MethodSource("provideOutOfBoundsKingCases")
    void kingOutOfBoundsMove_invalid(
            String testName,
            int fromRow, int fromCol,
            int toRow, int toCol
    ) {
        Piece king = new Piece(PieceType.KING, PieceColor.WHITE);

        
        EasyMock.expect(board.getPiece(EasyMock.anyObject(Location.class))).andReturn(null).anyTimes();
        EasyMock.replay(board);

        
        assertFalse(king.canMove(board, new Location(fromRow, fromCol), new Location(toRow, toCol)));

        EasyMock.verify(board);
    }

    private static Stream<Arguments> provideOutOfBoundsKingCases() {
        return Stream.of(
                
                Arguments.of("KiTC43: top boundary, straight forward",     7, 3,  8, 3),
                Arguments.of("KiTC44: top boundary, diagonal forward-left", 7, 3,  8, 2),
                Arguments.of("KiTC45: top boundary, diagonal forward-right",7, 3,  8, 4),

                
                Arguments.of("KiTC46: bottom boundary, straight backward",     0, 3, -1, 3),
                Arguments.of("KiTC47: bottom boundary, diagonal backward-left", 0, 3, -1, 2),
                Arguments.of("KiTC48: bottom boundary, diagonal backward-right",0, 3, -1, 4),

                
                Arguments.of("KiTC49: left boundary, straight left",      3, 0,  3, -1),
                Arguments.of("KiTC50: left boundary, diagonal forward-left",  3, 0,  4, -1), 
                Arguments.of("KiTC51: left boundary, diagonal backward-left", 3, 0,  2, -1),

                
                Arguments.of("KiTC52: right boundary, straight right",       3, 7,  3, 8),
                Arguments.of("KiTC53: right boundary, diagonal forward-right", 3, 7,  4, 8),
                Arguments.of("KiTC54: right boundary, diagonal backward-right",3, 7,  2, 8)
        );
    }

    @Test
    void kingZeroDistanceMove_invalid() {
        Piece king = new Piece(PieceType.KING, PieceColor.WHITE);

        
        EasyMock.expect(board.getPiece(matchesLoc(3, 3))).andReturn(king).anyTimes();
        EasyMock.replay(board);

        
        assertFalse(king.canMove(board, new Location(3, 3), new Location(3, 3)));

        EasyMock.verify(board);
    }

    @ParameterizedTest(name = "Valid Queen single move: {0}")
    @MethodSource("provideValidQueenSingleEmptyMoves")
    void queenSingleMove_empty_valid(
            String testName,
            int fromRow, int fromCol,
            int toRow, int toCol
    ) {
        Piece queen = new Piece(PieceType.QUEEN, PieceColor.WHITE);

        
        EasyMock.expect(board.getPiece(matchesLoc(toRow, toCol))).andReturn(null).anyTimes();
        EasyMock.replay(board);

        
        assertTrue(queen.canMove(board, new Location(fromRow, fromCol), new Location(toRow, toCol)));

        EasyMock.verify(board);
    }

    private static Stream<Arguments> provideValidQueenSingleEmptyMoves() {
        return Stream.of(
                Arguments.of("QTC1: forward one space",       3, 3, 4, 3),
                Arguments.of("QTC2: backward one space",      3, 3, 2, 3),
                Arguments.of("QTC3: left one space",          3, 3, 3, 2),
                Arguments.of("QTC4: right one space",         3, 3, 3, 4),
                Arguments.of("QTC5: forward-left one space",  3, 3, 4, 2),
                Arguments.of("QTC6: forward-right one space", 3, 3, 4, 4),
                Arguments.of("QTC7: backward-left one space", 3, 3, 2, 2),
                Arguments.of("QTC8: backward-right one space",3, 3, 2, 4)
        );
    }

    @ParameterizedTest(name = "Valid Queen single capture: {0}")
    @MethodSource("provideValidQueenSingleFoeMoves")
    void queenSingleMove_foeOccupied_valid(
            String testName,
            int fromRow, int fromCol,
            int toRow, int toCol
    ) {
        Piece queen = new Piece(PieceType.QUEEN, PieceColor.WHITE);
        Piece foe = new Piece(PieceType.PAWN, PieceColor.BLACK);

        
        EasyMock.expect(board.getPiece(matchesLoc(toRow, toCol))).andReturn(foe).anyTimes();
        EasyMock.replay(board);

        
        assertTrue(queen.canMove(board, new Location(fromRow, fromCol), new Location(toRow, toCol)));

        EasyMock.verify(board);
    }

    private static Stream<Arguments> provideValidQueenSingleFoeMoves() {
        return Stream.of(
                Arguments.of("QTC9: forward capture",        3, 3, 4, 3),
                Arguments.of("QTC10: backward capture",      3, 3, 2, 3),
                Arguments.of("QTC11: left capture",          3, 3, 3, 2),
                Arguments.of("QTC12: right capture",         3, 3, 3, 4),
                Arguments.of("QTC13: forward-left capture",  3, 3, 4, 2),
                Arguments.of("QTC14: forward-right capture", 3, 3, 4, 4),
                Arguments.of("QTC15: backward-left capture", 3, 3, 2, 2),
                Arguments.of("QTC16: backward-right capture",3, 3, 2, 4)
        );
    }

    @ParameterizedTest(name = "Valid Queen max slide: {0}")
    @MethodSource("provideValidQueenMaxEmptyMoves")
    void queenMaxMove_empty_valid(
            String testName,
            int fromRow, int fromCol,
            int toRow, int toCol
    ) {
        Piece queen = new Piece(PieceType.QUEEN, PieceColor.WHITE);

        
        EasyMock.expect(board.getPiece(EasyMock.anyObject(Location.class))).andReturn(null).anyTimes();
        EasyMock.replay(board);

        
        assertTrue(queen.canMove(board, new Location(fromRow, fromCol), new Location(toRow, toCol)));

        EasyMock.verify(board);
    }

    private static Stream<Arguments> provideValidQueenMaxEmptyMoves() {
        return Stream.of(
                Arguments.of("QTC17: max forward (0,3 to 7,3)",       0, 3, 7, 3),
                Arguments.of("QTC18: max backward (7,3 to 0,3)",      7, 3, 0, 3),
                Arguments.of("QTC19: max left (3,7 to 3,0)",          3, 7, 3, 0),
                Arguments.of("QTC20: max right (3,0 to 3,7)",         3, 0, 3, 7),
                Arguments.of("QTC21: max forward-left (0,7 to 7,0)",  0, 7, 7, 0),
                Arguments.of("QTC22: max forward-right (0,0 to 7,7)", 0, 0, 7, 7),
                Arguments.of("QTC23: max backward-left (7,7 to 0,0)", 7, 7, 0, 0),
                Arguments.of("QTC24: max backward-right (7,0 to 0,7)",7, 0, 0, 7)
        );
    }

    @ParameterizedTest(name = "Valid Queen max capture: {0}")
    @MethodSource("provideValidQueenMaxCaptureCases")
    void queenMaxMove_foeOccupied_valid(
            String testName,
            int fromRow, int fromCol,
            int toRow, int toCol
    ) {
        Piece queen = new Piece(PieceType.QUEEN, PieceColor.WHITE);
        Piece foe = new Piece(PieceType.PAWN, PieceColor.BLACK);

        
        EasyMock.expect(board.getPiece(matchesLoc(toRow, toCol))).andReturn(foe).anyTimes();

        
        EasyMock.expect(board.getPiece(EasyMock.anyObject(Location.class))).andReturn(null).anyTimes();

        EasyMock.replay(board);

        
        assertTrue(queen.canMove(board, new Location(fromRow, fromCol), new Location(toRow, toCol)));

        EasyMock.verify(board);
    }

    private static Stream<Arguments> provideValidQueenMaxCaptureCases() {
        return Stream.of(
                Arguments.of("QTC25: max forward capture (0,3 to 7,3)",       0, 3, 7, 3),
                Arguments.of("QTC26: max backward capture (7,3 to 0,3)",      7, 3, 0, 3),
                Arguments.of("QTC27: max left capture (3,7 to 3,0)",          3, 7, 3, 0),
                Arguments.of("QTC28: max right capture (3,0 to 3,7)",         3, 0, 3, 7),
                Arguments.of("QTC29: max forward-left capture (0,7 to 7,0)",  0, 7, 7, 0),
                Arguments.of("QTC30: max forward-right capture (0,0 to 7,7)", 0, 0, 7, 7),
                Arguments.of("QTC31: max backward-left capture (7,7 to 0,0)", 7, 7, 0, 0),
                Arguments.of("QTC32: max backward-right capture (7,0 to 0,7)",7, 0, 0, 7)
        );
    }

    @ParameterizedTest(name = "Invalid Queen single friend landing: {0}")
    @MethodSource("provideInvalidQueenSingleFriendMoves")
    void queenSingleMove_friendOccupied_invalid(
            String testName,
            int fromRow, int fromCol,
            int toRow, int toCol
    ) {
        Piece queen = new Piece(PieceType.QUEEN, PieceColor.WHITE);
        Piece friend = new Piece(PieceType.PAWN, PieceColor.WHITE);

        
        EasyMock.expect(board.getPiece(matchesLoc(toRow, toCol))).andReturn(friend).anyTimes();
        EasyMock.replay(board);

        
        assertFalse(queen.canMove(board, new Location(fromRow, fromCol), new Location(toRow, toCol)));

        EasyMock.verify(board);
    }

    private static Stream<Arguments> provideInvalidQueenSingleFriendMoves() {
        return Stream.of(
                Arguments.of("QTC33: forward, friend blocked",        3, 3, 4, 3),
                Arguments.of("QTC34: backward, friend blocked",       3, 3, 2, 3),
                Arguments.of("QTC35: left, friend blocked",           3, 3, 3, 2),
                Arguments.of("QTC36: right, friend blocked",          3, 3, 3, 4),
                Arguments.of("QTC37: forward-left, friend blocked",   3, 3, 4, 2),
                Arguments.of("QTC38: forward-right, friend blocked",  3, 3, 4, 4),
                Arguments.of("QTC39: backward-left, friend blocked",  3, 3, 2, 2),
                Arguments.of("QTC40: backward-right, friend blocked", 3, 3, 2, 4)
        );
    }

    @ParameterizedTest(name = "Invalid Queen max friend landing: {0}")
    @MethodSource("provideInvalidQueenMaxFriendMoves")
    void queenMaxMove_friendOccupied_invalid(
            String testName,
            int fromRow, int fromCol,
            int toRow, int toCol
    ) {
        Piece queen = new Piece(PieceType.QUEEN, PieceColor.WHITE);
        Piece friend = new Piece(PieceType.PAWN, PieceColor.WHITE);

        
        EasyMock.expect(board.getPiece(matchesLoc(toRow, toCol))).andReturn(friend).anyTimes();

        
        EasyMock.expect(board.getPiece(EasyMock.anyObject(Location.class))).andReturn(null).anyTimes();

        EasyMock.replay(board);

        
        assertFalse(queen.canMove(board, new Location(fromRow, fromCol), new Location(toRow, toCol)));

        EasyMock.verify(board);
    }

    private static Stream<Arguments> provideInvalidQueenMaxFriendMoves() {
        return Stream.of(
                Arguments.of("QTC41: max forward, friend blocked (0,3 to 7,3)",       0, 3, 7, 3),
                Arguments.of("QTC42: max backward, friend blocked (7,3 to 0,3)",      7, 3, 0, 3),
                Arguments.of("QTC43: max left, friend blocked (3,7 to 3,0)",          3, 7, 3, 0),
                Arguments.of("QTC44: max right, friend blocked (3,0 to 3,7)",         3, 0, 3, 7),
                Arguments.of("QTC45: max forward-left, friend blocked (0,7 to 7,0)",  0, 7, 7, 0),
                Arguments.of("QTC46: max forward-right, friend blocked (0,0 to 7,7)", 0, 0, 7, 7),
                Arguments.of("QTC47: max backward-left, friend blocked (7,7 to 0,0)", 7, 7, 0, 0),
                Arguments.of("QTC48: max backward-right, friend blocked (7,0 to 0,7)",7, 0, 0, 7)
        );
    }

    @ParameterizedTest(name = "Invalid Queen path friend blocked: {0}")
    @MethodSource("provideFriendObstructedPathQueenCases")
    void queenMaxMove_friendObstructedPath_invalid(
            String testName,
            int fromRow, int fromCol,
            int blockRow, int blockCol,
            int toRow, int toCol
    ) {
        Piece queen = new Piece(PieceType.QUEEN, PieceColor.WHITE);
        Piece friendBlocker = new Piece(PieceType.PAWN, PieceColor.WHITE);

        
        EasyMock.expect(board.getPiece(matchesLoc(blockRow, blockCol)))
                .andReturn(friendBlocker).anyTimes();

        
        EasyMock.expect(board.getPiece(EasyMock.anyObject(Location.class)))
                .andReturn(null).anyTimes();

        EasyMock.replay(board);

        
        assertFalse(queen.canMove(board, new Location(fromRow, fromCol), new Location(toRow, toCol)));

        EasyMock.verify(board);
    }

    private static Stream<Arguments> provideFriendObstructedPathQueenCases() {
        return Stream.of(
                
                Arguments.of("QTC49: forward path blocked",  0, 3,  1, 3,  7, 3),
                Arguments.of("QTC50: backward path blocked", 7, 3,  6, 3,  0, 3),
                Arguments.of("QTC51: left path blocked",     3, 7,  3, 6,  3, 0),
                Arguments.of("QTC52: right path blocked",    3, 0,  3, 1,  3, 7),

                
                Arguments.of("QTC53: forward-left path blocked",   0, 7,  1, 6,  7, 0),
                Arguments.of("QTC54: forward-right path blocked",  0, 0,  1, 1,  7, 7),
                Arguments.of("QTC55: backward-left path blocked",  7, 7,  6, 6,  0, 0),
                Arguments.of("QTC56: backward-right path blocked", 7, 0,  6, 1,  0, 7)
        );
    }

    @ParameterizedTest(name = "Invalid Queen path foe blocked: {0}")
    @MethodSource("provideFoeObstructedPathQueenCases")
    void queenMaxMove_foeObstructedPath_invalid(
            String testName,
            int fromRow, int fromCol,
            int blockRow, int blockCol,
            int toRow, int toCol
    ) {
        Piece queen = new Piece(PieceType.QUEEN, PieceColor.WHITE);
        Piece foeBlocker = new Piece(PieceType.PAWN, PieceColor.BLACK);

        
        EasyMock.expect(board.getPiece(matchesLoc(blockRow, blockCol)))
                .andReturn(foeBlocker).anyTimes();

        
        EasyMock.expect(board.getPiece(EasyMock.anyObject(Location.class)))
                .andReturn(null).anyTimes();

        EasyMock.replay(board);

        
        assertFalse(queen.canMove(board, new Location(fromRow, fromCol), new Location(toRow, toCol)));

        EasyMock.verify(board);
    }

    private static Stream<Arguments> provideFoeObstructedPathQueenCases() {
        return Stream.of(
                
                Arguments.of("QTC57: forward path enemy-blocked",  0, 3,  1, 3,  7, 3),
                Arguments.of("QTC58: backward path enemy-blocked", 7, 3,  6, 3,  0, 3),
                Arguments.of("QTC59: left path enemy-blocked",     3, 7,  3, 6,  3, 0),
                Arguments.of("QTC60: right path enemy-blocked",    3, 0,  3, 1,  3, 7),

                
                Arguments.of("QTC61: forward-left path enemy-blocked",   0, 7,  1, 6,  7, 0),
                Arguments.of("QTC62: forward-right path enemy-blocked",  0, 0,  1, 1,  7, 7),
                Arguments.of("QTC63: backward-left path enemy-blocked",  7, 7,  6, 6,  0, 0),
                Arguments.of("QTC64: backward-right path enemy-blocked", 7, 0,  6, 1,  0, 7)
        );
    }

    @ParameterizedTest(name = "Invalid Queen friend-blocked path to foe target: {0}")
    @MethodSource("provideFriendObstructedPathFoeDestQueenCases")
    void queenMaxMove_friendObstructedPath_foeDest_invalid(
            String testName,
            int fromRow, int fromCol,
            int blockRow, int blockCol,
            int toRow, int toCol
    ) {
        Piece queen = new Piece(PieceType.QUEEN, PieceColor.WHITE);
        Piece friendBlocker = new Piece(PieceType.PAWN, PieceColor.WHITE);
        Piece foeDest = new Piece(PieceType.PAWN, PieceColor.BLACK);

        
        EasyMock.expect(board.getPiece(matchesLoc(blockRow, blockCol)))
                .andReturn(friendBlocker).anyTimes();

        
        EasyMock.expect(board.getPiece(matchesLoc(toRow, toCol)))
                .andReturn(foeDest).anyTimes();

        
        EasyMock.expect(board.getPiece(EasyMock.anyObject(Location.class)))
                .andReturn(null).anyTimes();

        EasyMock.replay(board);

        
        assertFalse(queen.canMove(board, new Location(fromRow, fromCol), new Location(toRow, toCol)));

        EasyMock.verify(board);
    }

    private static Stream<Arguments> provideFriendObstructedPathFoeDestQueenCases() {
        return Stream.of(
                
                Arguments.of("QTC65: forward, friend path blocker, foe target",  0, 3,  1, 3,  7, 3),
                Arguments.of("QTC66: backward, friend path blocker, foe target", 7, 3,  6, 3,  0, 3),
                Arguments.of("QTC67: left, friend path blocker, foe target",     3, 7,  3, 6,  3, 0),
                Arguments.of("QTC68: right, friend path blocker, foe target",    3, 0,  3, 1,  3, 7),

                
                Arguments.of("QTC69: forward-left, friend path blocker, foe target",   0, 7,  1, 6,  7, 0),
                Arguments.of("QTC70: forward-right, friend path blocker, foe target",  0, 0,  1, 1,  7, 7),
                Arguments.of("QTC71: backward-left, friend path blocker, foe target",  7, 7,  6, 6,  0, 0),
                Arguments.of("QTC72: backward-right, friend path blocker, foe target", 7, 0,  6, 1,  0, 7)
        );
    }

    @ParameterizedTest(name = "Invalid Queen foe-blocked path to foe target: {0}")
    @MethodSource("provideFoeObstructedPathFoeDestQueenCases")
    void queenMaxMove_foeObstructedPath_foeDest_invalid(
            String testName,
            int fromRow, int fromCol,
            int blockRow, int blockCol,
            int toRow, int toCol
    ) {
        Piece queen = new Piece(PieceType.QUEEN, PieceColor.WHITE);
        Piece foeBlocker = new Piece(PieceType.PAWN, PieceColor.BLACK);
        Piece foeDest = new Piece(PieceType.PAWN, PieceColor.BLACK);

        
        EasyMock.expect(board.getPiece(matchesLoc(blockRow, blockCol)))
                .andReturn(foeBlocker).anyTimes();

        
        EasyMock.expect(board.getPiece(matchesLoc(toRow, toCol)))
                .andReturn(foeDest).anyTimes();

        
        EasyMock.expect(board.getPiece(EasyMock.anyObject(Location.class)))
                .andReturn(null).anyTimes();

        EasyMock.replay(board);

        
        assertFalse(queen.canMove(board, new Location(fromRow, fromCol), new Location(toRow, toCol)));

        EasyMock.verify(board);
    }

    private static Stream<Arguments> provideFoeObstructedPathFoeDestQueenCases() {
        return Stream.of(
                
                Arguments.of("QTC73: forward, foe path blocker, foe target",  0, 3,  1, 3,  7, 3),
                Arguments.of("QTC74: backward, foe path blocker, foe target", 7, 3,  6, 3,  0, 3),
                Arguments.of("QTC75: left, foe path blocker, foe target",     3, 7,  3, 6,  3, 0),
                Arguments.of("QTC76: right, foe path blocker, foe target",    3, 0,  3, 1,  3, 7),

                
                Arguments.of("QTC77: forward-left, foe path blocker, foe target",   0, 7,  1, 6,  7, 0),
                Arguments.of("QTC78: forward-right, foe path blocker, foe target",  0, 0,  1, 1,  7, 7),
                Arguments.of("QTC79: backward-left, foe path blocker, foe target",  7, 7,  6, 6,  0, 0),
                Arguments.of("QTC80: backward-right, foe path blocker, foe target", 7, 0,  6, 1,  0, 7)
        );
    }

    @ParameterizedTest(name = "Invalid Queen fully friend-blocked move: {0}")
    @MethodSource("provideFriendObstructedPathFriendDestQueenCases")
    void queenMaxMove_friendObstructedPath_friendDest_invalid(
            String testName,
            int fromRow, int fromCol,
            int blockRow, int blockCol,
            int toRow, int toCol
    ) {
        Piece queen = new Piece(PieceType.QUEEN, PieceColor.WHITE);
        Piece friendPiece = new Piece(PieceType.PAWN, PieceColor.WHITE);

        
        EasyMock.expect(board.getPiece(matchesLoc(blockRow, blockCol)))
                .andReturn(friendPiece).anyTimes();

        
        EasyMock.expect(board.getPiece(matchesLoc(toRow, toCol)))
                .andReturn(friendPiece).anyTimes();

        
        EasyMock.expect(board.getPiece(EasyMock.anyObject(Location.class)))
                .andReturn(null).anyTimes();

        EasyMock.replay(board);

        
        assertFalse(queen.canMove(board, new Location(fromRow, fromCol), new Location(toRow, toCol)));

        EasyMock.verify(board);
    }

    private static Stream<Arguments> provideFriendObstructedPathFriendDestQueenCases() {
        return Stream.of(
                
                Arguments.of("QTC81: forward, path friend-blocked, dest friend",  0, 3,  1, 3,  7, 3),
                Arguments.of("QTC82: backward, path friend-blocked, dest friend", 7, 3,  6, 3,  0, 3),
                Arguments.of("QTC83: left, path friend-blocked, dest friend",     3, 7,  3, 6,  3, 0),
                Arguments.of("QTC84: right, path friend-blocked, dest friend",    3, 0,  3, 1,  3, 7),

                
                Arguments.of("QTC85: forward-left, path friend-blocked, dest friend",   0, 7,  1, 6,  7, 0),
                Arguments.of("QTC86: forward-right, path friend-blocked, dest friend",  0, 0,  1, 1,  7, 7),
                Arguments.of("QTC87: backward-left, path friend-blocked, dest friend",  7, 7,  6, 6,  0, 0),
                Arguments.of("QTC88: backward-right, path friend-blocked, dest friend", 7, 0,  6, 1,  0, 7)
        );
    }

    @ParameterizedTest(name = "Invalid Queen foe-blocked path to friend landing: {0}")
    @MethodSource("provideFoeObstructedPathFriendDestQueenCases")
    void queenMaxMove_foeObstructedPath_friendDest_invalid(
            String testName,
            int fromRow, int fromCol,
            int blockRow, int blockCol,
            int toRow, int toCol
    ) {
        Piece queen = new Piece(PieceType.QUEEN, PieceColor.WHITE);
        Piece foeBlocker = new Piece(PieceType.PAWN, PieceColor.BLACK);
        Piece friendDest = new Piece(PieceType.PAWN, PieceColor.WHITE);

        
        EasyMock.expect(board.getPiece(matchesLoc(blockRow, blockCol)))
                .andReturn(foeBlocker).anyTimes();

        
        EasyMock.expect(board.getPiece(matchesLoc(toRow, toCol)))
                .andReturn(friendDest).anyTimes();

        
        EasyMock.expect(board.getPiece(EasyMock.anyObject(Location.class)))
                .andReturn(null).anyTimes();

        EasyMock.replay(board);

        
        assertFalse(queen.canMove(board, new Location(fromRow, fromCol), new Location(toRow, toCol)));

        EasyMock.verify(board);
    }

    private static Stream<Arguments> provideFoeObstructedPathFriendDestQueenCases() {
        return Stream.of(
                
                Arguments.of("QTC89: forward, foe path blocker, friend target",  0, 3,  1, 3,  7, 3),
                Arguments.of("QTC90: backward, foe path blocker, friend target", 7, 3,  6, 3,  0, 3),
                Arguments.of("QTC91: left, foe path blocker, friend target",     3, 7,  3, 6,  3, 0),
                Arguments.of("QTC92: right, foe path blocker, friend target",    3, 0,  3, 1,  3, 7),

                
                Arguments.of("QTC93: forward-left, foe path blocker, friend target",   0, 7,  1, 6,  7, 0),
                Arguments.of("QTC94: forward-right, foe path blocker, friend target",  0, 0,  1, 1,  7, 7),
                Arguments.of("QTC95: backward-left, foe path blocker, friend target",  7, 7,  6, 6,  0, 0),
                Arguments.of("QTC96: backward-right, foe path blocker, friend target", 7, 0,  6, 1,  0, 7)
        );
    }

    @ParameterizedTest(name = "Invalid Queen Knight L-shape leap: {0}")
    @MethodSource("provideInvalidQueenKnightShapes")
    void queenMove_knightShape_invalid(
            String testName,
            int fromRow, int fromCol,
            int toRow, int toCol
    ) {
        Piece queen = new Piece(PieceType.QUEEN, PieceColor.WHITE);

        
        EasyMock.expect(board.getPiece(matchesLoc(toRow, toCol))).andReturn(null).anyTimes();
        EasyMock.replay(board);

        
        assertFalse(queen.canMove(board, new Location(fromRow, fromCol), new Location(toRow, toCol)));

        EasyMock.verify(board);
    }

    private static Stream<Arguments> provideInvalidQueenKnightShapes() {
        return Stream.of(
                Arguments.of("QTC97: forward-left L-shape",   3, 3, 5, 2),
                Arguments.of("QTC98: forward-right L-shape",  3, 3, 5, 4),
                Arguments.of("QTC99: right-forward L-shape",  3, 3, 4, 5),
                Arguments.of("QTC100: right-backward L-shape",3, 3, 2, 5),
                Arguments.of("QTC101: backward-right L-shape",3, 3, 1, 4),
                Arguments.of("QTC102: backward-left L-shape", 3, 3, 1, 2),
                Arguments.of("QTC103: left-backward L-shape", 3, 3, 2, 1),
                Arguments.of("QTC104: left-forward L-shape",  3, 3, 4, 1)
        );
    }

    @ParameterizedTest(name = "Invalid Queen Knight L-shape friend landing: {0}")
    @MethodSource("provideInvalidQueenKnightFriendMoves")
    void queenMove_knightShapeFriendOccupied_invalid(
            String testName,
            int fromRow, int fromCol,
            int toRow, int toCol
    ) {
        Piece queen = new Piece(PieceType.QUEEN, PieceColor.WHITE);
        Piece friend = new Piece(PieceType.PAWN, PieceColor.WHITE);

        
        EasyMock.expect(board.getPiece(matchesLoc(toRow, toCol))).andReturn(friend).anyTimes();
        EasyMock.replay(board);

        
        assertFalse(queen.canMove(board, new Location(fromRow, fromCol), new Location(toRow, toCol)));

        EasyMock.verify(board);
    }

    private static Stream<Arguments> provideInvalidQueenKnightFriendMoves() {
        return Stream.of(
                Arguments.of("QTC105: forward-left L-shape, friend block",   3, 3, 5, 2),
                Arguments.of("QTC106: forward-right L-shape, friend block",  3, 3, 5, 4),
                Arguments.of("QTC107: right-forward L-shape, friend block",  3, 3, 4, 5),
                Arguments.of("QTC108: right-backward L-shape, friend block", 3, 3, 2, 5),
                Arguments.of("QTC109: backward-right L-shape, friend block", 3, 3, 1, 4),
                Arguments.of("QTC110: backward-left L-shape, friend block",  3, 3, 1, 2),
                Arguments.of("QTC111: left-backward L-shape, friend block",  3, 3, 2, 1),
                Arguments.of("QTC112: left-forward L-shape, friend block",   3, 3, 4, 1)
        );
    }

    @ParameterizedTest(name = "Invalid Queen Knight L-shape foe capture: {0}")
    @MethodSource("provideInvalidQueenKnightFoeMoves")
    void queenMove_knightShapeFoeOccupied_invalid(
            String testName,
            int fromRow, int fromCol,
            int toRow, int toCol
    ) {
        Piece queen = new Piece(PieceType.QUEEN, PieceColor.WHITE);
        Piece foe = new Piece(PieceType.PAWN, PieceColor.BLACK);

        
        EasyMock.expect(board.getPiece(matchesLoc(toRow, toCol))).andReturn(foe).anyTimes();
        EasyMock.replay(board);

        
        assertFalse(queen.canMove(board, new Location(fromRow, fromCol), new Location(toRow, toCol)));

        EasyMock.verify(board);
    }

    private static Stream<Arguments> provideInvalidQueenKnightFoeMoves() {
        return Stream.of(
                Arguments.of("QTC113: forward-left L-shape, foe target",   3, 3, 5, 2),
                Arguments.of("QTC114: forward-right L-shape, foe target",  3, 3, 5, 4),
                Arguments.of("QTC115: right-forward L-shape, foe target",  3, 3, 4, 5),
                Arguments.of("QTC116: right-backward L-shape, foe target", 3, 3, 2, 5),
                Arguments.of("QTC117: backward-right L-shape, foe target", 3, 3, 1, 4),
                Arguments.of("QTC118: backward-left L-shape, foe target",  3, 3, 1, 2),
                Arguments.of("QTC119: left-backward L-shape, foe target",  3, 3, 2, 1),
                Arguments.of("QTC120: left-forward L-shape, foe target",   3, 3, 4, 1)
        );
    }

    @ParameterizedTest(name = "Invalid Queen out-of-bounds: {0}")
    @MethodSource("provideOutOfBoundsQueenCases")
    void queenOutOfBoundsMove_invalid(
            String testName,
            int fromRow, int fromCol,
            int toRow, int toCol
    ) {
        Piece queen = new Piece(PieceType.QUEEN, PieceColor.WHITE);

        
        EasyMock.expect(board.getPiece(EasyMock.anyObject(Location.class))).andReturn(null).anyTimes();
        EasyMock.replay(board);

        
        assertFalse(queen.canMove(board, new Location(fromRow, fromCol), new Location(toRow, toCol)));

        EasyMock.verify(board);
    }

    private static Stream<Arguments> provideOutOfBoundsQueenCases() {
        return Stream.of(
                
                Arguments.of("QTC121: top boundary, straight forward",     7, 3,  8, 3),
                Arguments.of("QTC122: top boundary, diagonal forward-left", 7, 3,  8, 2),
                Arguments.of("QTC123: top boundary, diagonal forward-right",7, 3,  8, 4),

                
                Arguments.of("QTC124: bottom boundary, straight backward",     0, 3, -1, 3),
                Arguments.of("QTC125: bottom boundary, diagonal backward-left", 0, 3, -1, 2),
                Arguments.of("QTC126: bottom boundary, diagonal backward-right",0, 3, -1, 4),

                
                Arguments.of("QTC127: left boundary, straight left",      3, 0,  3, -1),
                Arguments.of("QTC128: left boundary, diagonal forward-left",  3, 0,  4, -1),
                Arguments.of("QTC129: left boundary, diagonal backward-left", 3, 0,  2, -1),

                
                Arguments.of("QTC132: right boundary, straight right",       3, 7,  3, 8),
                Arguments.of("QTC131: right boundary, diagonal forward-right", 3, 7,  4, 8),
                Arguments.of("QTC132: right boundary, diagonal backward-right",3, 7,  2, 8)
        );
    }

    @Test
    void queenZeroDistanceMove_invalid() {
        Piece queen = new Piece(PieceType.QUEEN, PieceColor.WHITE);

        
        EasyMock.expect(board.getPiece(matchesLoc(3, 3))).andReturn(queen).anyTimes();
        EasyMock.replay(board);

        
        assertFalse(queen.canMove(board, new Location(3, 3), new Location(3, 3)));

        EasyMock.verify(board);
    }

    @Test
    void castling_pathObstructed_returnsFalse() {
        Piece king = new Piece(PieceType.KING, PieceColor.WHITE);
        Piece blocker = new Piece(PieceType.PAWN, PieceColor.WHITE);

        Location from = new Location(0, 4);
        Location to = new Location(0, 6);

        
        EasyMock.expect(board.getPiece(EasyMock.anyObject(Location.class))).andAnswer(() -> {
            Location loc = (Location) EasyMock.getCurrentArguments()[0];
            
            if (loc.getRow() == 0 && loc.getCol() == 5) {
                return blocker;
            }
            
            if (loc.getRow() == 0 && loc.getCol() == 7) {
                return new Piece(PieceType.ROOK, PieceColor.WHITE);
            }
            return null; 
        }).anyTimes();

        EasyMock.replay(board);

        assertFalse(king.canMove(board, from, to));
        EasyMock.verify(board);
    }

    @Test
    void KiTC57_castling_enemyRook_invalid() {
        Piece king = new Piece(PieceType.KING, PieceColor.WHITE);
        Piece foeRook = new Piece(PieceType.ROOK, PieceColor.BLACK);

        EasyMock.expect(board.getPiece(EasyMock.anyObject(Location.class))).andReturn(foeRook);
        EasyMock.replay(board);

        assertFalse(king.canMove(board, new Location(0, 4), new Location(0, 6)));
    }


    @Test
    void KiTC58_castling_rookHasMoved_invalid() {
        Piece king = new Piece(PieceType.KING, PieceColor.WHITE);
        Piece movedRook = new Piece(PieceType.ROOK, PieceColor.WHITE);
        movedRook.setMoved(true);

        EasyMock.expect(board.getPiece(EasyMock.anyObject(Location.class))).andReturn(movedRook);
        EasyMock.replay(board);

        assertFalse(king.canMove(board, new Location(0, 4), new Location(0, 6)));
    }

    @Test
    void KiTC59_castling_rookNull_returnsFalse() {
        Piece king = new Piece(PieceType.KING, PieceColor.WHITE);

        Location from = new Location(0, 4);
        Location to = new Location(0, 6);

        
        EasyMock.expect(board.getPiece(new Location(0, 7))).andReturn(null).anyTimes();
        EasyMock.replay(board);

        assertFalse(king.canMove(board, from, to));
        EasyMock.verify(board);
    }

    @Test
    void KiTC60_castling_wrongPieceAtRookSquare_invalid() {
        Piece king = new Piece(PieceType.KING, PieceColor.WHITE);
        Piece notARook = new Piece(PieceType.BISHOP, PieceColor.WHITE);

        EasyMock.expect(board.getPiece(EasyMock.anyObject(Location.class))).andReturn(notARook);
        EasyMock.replay(board);

        assertFalse(king.canMove(board, new Location(0, 4), new Location(0, 6)));
    }


    @Test
    void KiTC61_castling_queensideRookNull_returnsFalse() {
        Piece king = new Piece(PieceType.KING, PieceColor.WHITE);

        Location from = new Location(0, 4);
        Location to = new Location(0, 2); 

        
        EasyMock.expect(board.getPiece(new Location(0, 0))).andReturn(null).anyTimes();
        EasyMock.replay(board);

        assertFalse(king.canMove(board, from, to));
        EasyMock.verify(board);
    }

    @Test
    void KiTC62_king_hasMoved_castlingAttempt_invalid() {
        Piece king = new Piece(PieceType.KING, PieceColor.WHITE);
        king.setMoved(true);
        EasyMock.replay(board);
        assertFalse(king.canMove(board, new Location(0, 4), new Location(0, 6)));
    }

	@Test
	void equals_samePiece_returnsTrue() {
		Piece p1 = new Piece(PieceType.QUEEN, PieceColor.WHITE);
		Piece p2 = new Piece(PieceType.QUEEN, PieceColor.WHITE);
		assertEquals(p1, p2);
	}

	@Test
	void equals_differentType_returnsFalse() {
		Piece p1 = new Piece(PieceType.QUEEN, PieceColor.WHITE);
		Piece p2 = new Piece(PieceType.ROOK, PieceColor.WHITE);
		assertNotEquals(p1, p2);
	}

	@Test
	void equals_null_returnsFalse() {
		Piece p1 = new Piece(PieceType.QUEEN, PieceColor.WHITE);
		assertNotEquals(p1, null);
	}

	@Test
	void equals_sameReference_returnsTrue() {
		Piece p1 = new Piece(PieceType.QUEEN, PieceColor.WHITE);
		assertEquals(p1, p1);
	}

	@Test
	void hashCode_equalPieces_sameHash() {
		Piece p1 = new Piece(PieceType.QUEEN, PieceColor.WHITE);
		Piece p2 = new Piece(PieceType.QUEEN, PieceColor.WHITE);
		assertEquals(p1.hashCode(), p2.hashCode());
	}

	@Test
	void equals_differentColor_returnsFalse() {
		Piece p1 = new Piece(PieceType.QUEEN, PieceColor.WHITE);
		Piece p2 = new Piece(PieceType.QUEEN, PieceColor.BLACK);
		assertNotEquals(p1, p2);
	}

	@Test
	void equals_differentClass_returnsFalse() {
		Piece p1 = new Piece(PieceType.QUEEN, PieceColor.WHITE);
		assertNotEquals(p1, "notAPiece");
	}

	@Test
	void hashCode_differentPieces_differentHash() {
		Piece p1 = new Piece(PieceType.PAWN, PieceColor.WHITE);
		Piece p2 = new Piece(PieceType.ROOK, PieceColor.BLACK);
		assertNotEquals(p1.hashCode(), p2.hashCode());
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
        return null; 
    }

}

