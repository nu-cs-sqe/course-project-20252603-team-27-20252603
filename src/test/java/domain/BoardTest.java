package domain;

import org.easymock.EasyMock;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNotSame;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.assertNull;

class BoardTest {
	@Test
	void constructorNullPiecesCreatesEmptyBoard() {
		Board board = new Board(null);

		assertTrue(board.isEmpty(new Location(0, 0)));
	}

	@Test
	void constructorCopiesNonNullRowsAndIgnoresNullRows() {
		Piece[][] pieces = new Piece[Board.TOTAL_ROWS][];
		pieces[6] = new Piece[Board.TOTAL_COLS];
		Piece original = EasyMock.createMock(Piece.class);
		pieces[6][0] = original;

		EasyMock.expect(original.makeCopy()).andStubReturn(original);
		EasyMock.replay(original);

		Board board = new Board(pieces);

		assertTrue(board.isEmpty(new Location(0, 0)));
		assertSame(original, board.getPiece(new Location("a2")));
		EasyMock.verify(original);
	}

	@Test
	void setPieceReplacesExistingPieceAndReturnsPrevious() {
		Board board = new Board();
		board.initBoard();

		Location loc = new Location("a2");
		Piece before = board.getPiece(loc);
		assertNotNull(before);
		Piece replacement = EasyMock.createMock(Piece.class);
		EasyMock.replay(replacement);

		Piece returned = board.setPiece(loc, replacement);

		// Expected behavior: returned is the previous piece and board has replacement
		assertSame(before, returned);
		assertSame(replacement, board.getPiece(loc));
		EasyMock.verify(replacement);
	}

	@Test
	void setPieceOnEmptySquareReturnsNullAndPlacesPiece() {
		Board board = new Board();
		board.clearBoard();

		Location loc = new Location("e4");
		assertTrue(board.isEmpty(loc));
		Piece replacement = EasyMock.createMock(Piece.class);
		EasyMock.replay(replacement);

		Piece returned = board.setPiece(loc, replacement);
		assertNull(returned);
		assertSame(replacement, board.getPiece(loc));
		EasyMock.verify(replacement);
	}

	@Test
	void setPieceWithInvalidLocationThrowsException() {
		Board board = new Board();
		board.initBoard();
		Piece replacement = EasyMock.createMock(Piece.class);
		EasyMock.replay(replacement);

		assertThrows(ArrayIndexOutOfBoundsException.class,
				() -> board.setPiece(new Location(-1, 0), replacement));
		EasyMock.verify(replacement);
	}

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
		assertEquals(PieceColor.WHITE, piece.getPieceColor());
		assertEquals(PieceType.PAWN, piece.getPieceType());
	}

	@Test
	void initBoardCheckBlackRookReturnsBlackRook() {
		Board board = new Board();

		board.initBoard();

		Piece piece = board.getPiece(new Location("a8"));
		assertNotNull(piece);
		assertEquals(PieceColor.BLACK, piece.getPieceColor());
		assertEquals(PieceType.ROOK, piece.getPieceType());
	}

	@Test
	void initBoardCheckEmptySquareReturnsEmpty() {
		Board board = new Board();

		board.initBoard();

		assertTrue(board.isEmpty(new Location("e4")));
	}

	@Test
	void initBoardCheckOccupiedSquareReturnsNotEmpty() {
		Board board = new Board();

		board.initBoard();

		assertFalse(board.isEmpty(new Location("a1")));
	}

	@Test
	void initBoardClearsExistingPiecesBeforeSetup() {
		Piece[][] pieces = new Piece[Board.TOTAL_ROWS][Board.TOTAL_COLS];
		pieces[4][4] = new Piece(PieceType.QUEEN, PieceColor.WHITE);
		Board board = new Board(pieces);

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
		EasyMock.expect(original.getPieceColor()).andStubReturn(PieceColor.WHITE);
		EasyMock.expect(original.getPieceType()).andStubReturn(PieceType.PAWN);
		EasyMock.expect(copy.getPieceColor()).andStubReturn(PieceColor.WHITE);
		EasyMock.expect(copy.getPieceType()).andStubReturn(PieceType.PAWN);

		EasyMock.replay(original, copy);

		Piece[][] pieces = new Piece[Board.TOTAL_ROWS][Board.TOTAL_COLS];
		pieces[6][0] = original; // a2

		Board board = new Board(pieces);

		Piece[][] snapshot = board.getSnapshot();

		assertNotSame(original, snapshot[6][0]);
		assertEquals(original.getPieceColor(), snapshot[6][0].getPieceColor());
		assertEquals(original.getPieceType(), snapshot[6][0].getPieceType());
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
		Location foundKing = board.findKing(PieceColor.WHITE);

		assertEquals(kingLocation.getRow(), foundKing.getRow());
		assertEquals(kingLocation.getCol(), foundKing.getCol());
	}

	@Test
	void findKingThrowsWhenMissing() {
		Board board = new Board();
		board.clearBoard();

		assertThrows(IllegalStateException.class,
				() -> board.findKing(PieceColor.WHITE));
	}

	@Test
	void toPositionStringReturnsExpectedBoardLayout() {
		Board board = new Board();
		board.initBoard();

		String expected = String.join(System.lineSeparator(),
				"rnbqkbnr",
				"pppppppp",
				"........",
				"........",
				"........",
				"........",
				"PPPPPPPP",
				"RNBQKBNR");

		assertEquals(expected, board.toPositionString());
	}
}