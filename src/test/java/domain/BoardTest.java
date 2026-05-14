package domain;

import org.easymock.EasyMock;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNotSame;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertTrue;

class BoardTest {
    @Test
    void isInsideBoardBothIndicesMinValidReturnTrue() {
        Board board = new Board();

        assertTrue(board.isInsideBoard(new Location(0, 0)));
    }

    @Test
    void isInsideBoardBothIndicesMaxValidReturnTrue() {
        Board board = new Board();

        assertTrue(board.isInsideBoard(new Location(7, 7)));
    }

    @Test
    void isInsideBoardRowJustBelowValidReturnFalse() {
        Board board = new Board();

        assertFalse(board.isInsideBoard(new Location(-1, 0)));
    }

    @Test
    void isInsideBoardColJustBelowValidReturnFalse() {
        Board board = new Board();

        assertFalse(board.isInsideBoard(new Location(0, -1)));
    }

    @Test
    void isInsideBoardRowJustAboveValidReturnFalse() {
        Board board = new Board();

        assertFalse(board.isInsideBoard(new Location(8, 7)));
    }

    @Test
    void isInsideBoardColJustAboveValidReturnFalse() {
        Board board = new Board();

        assertFalse(board.isInsideBoard(new Location(7, 8)));
    }

    @Test
    void initBoardCheckWhitePawnReturnsWhitePawn() {
        Board board = new Board();

        board.initBoard();

        Piece piece = board.getPiece(new Location("a2"));
        assertNotNull(piece);
        assertEquals(Color.WHITE, piece.getColor());
        assertTrue(piece instanceof Pawn);
    }

    @Test
    void initBoardCheckBlackRookReturnsBlackRook() {
        Board board = new Board();

        board.initBoard();

        Piece piece = board.getPiece(new Location("a8"));
        assertNotNull(piece);
        assertEquals(Color.BLACK, piece.getColor());
        assertTrue(piece instanceof Rook);
    }

    @Test
    void initBoardCheckEmptySquareReturnsEmpty() {
        Board board = new Board();

        board.initBoard();

        assertTrue(board.isEmpty(new Location("e4")));
    }

    @Test
    void clearBoardEmptiesAllSquares() {
        Board board = new Board();

        board.initBoard();

        board.clearBoard();

        assertTrue(board.isEmpty(new Location("a1")));
        assertTrue(board.isEmpty(new Location("h8")));
    }

    @Test
    void getSnapshotReturnsDeepCopyOfPieces() {
        // create a mock piece and mock copy, inject into board via test constructor
        Piece original = EasyMock.createMock(Piece.class);
        Piece copy = EasyMock.createMock(Piece.class);

        EasyMock.expect(original.makeCopy()).andReturn(copy);
        EasyMock.expect(original.getColor()).andStubReturn(Color.WHITE);
        EasyMock.expect(original.getType()).andStubReturn("Pawn");
        EasyMock.expect(copy.getColor()).andStubReturn(Color.WHITE);
        EasyMock.expect(copy.getType()).andStubReturn("Pawn");

        EasyMock.replay(original, copy);

        Piece[][] pieces = new Piece[Board.TOTAL_ROWS][Board.TOTAL_COLS];
        pieces[6][0] = original; // a2

        Board board = new Board(pieces);

        Piece[][] snapshot = board.getSnapshot();

        assertNotSame(original, snapshot[6][0]);
        assertEquals(original.getColor(), snapshot[6][0].getColor());
        assertEquals(original.getType(), snapshot[6][0].getType());
        EasyMock.verify(original, copy);
    }

    @Test
    void movePieceMovesPieceAndEmptiesSource() {
        Board board = new Board();
        board.initBoard();
        Piece pawn = board.getPiece(new Location("a2"));
        Location from = new Location("a2");
        Location to = new Location("a3");

        board.movePiece(from, to);

        assertTrue(board.isEmpty(from));
        assertSame(pawn, board.getPiece(to));
    }

    @Test
    void movePieceCapturesOpponentPiece() {
        Board board = new Board();
        board.initBoard();
        Piece whitePawn = board.getPiece(new Location("a2"));
        Location from = new Location("a2");
        Location to = new Location("a7");

        board.movePiece(from, to);

        assertTrue(board.isEmpty(from));
        assertSame(whitePawn, board.getPiece(to));
    }

    @Test
    void findKingReturnsKingLocation() {
        Board board = new Board();

        board.initBoard();

        Location kingLocation = new Location("e1");
        Location foundKing = board.findKing(Color.WHITE);

        assertEquals(kingLocation.getRow(), foundKing.getRow());
        assertEquals(kingLocation.getCol(), foundKing.getCol());
    }
}
