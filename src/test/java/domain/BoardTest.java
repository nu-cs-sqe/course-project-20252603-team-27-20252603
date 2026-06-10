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
		// FIX: Initialize all 8 rows cleanly so Board doesn't leave null row references
		Piece[][] pieces = new Piece[Board.TOTAL_ROWS][Board.TOTAL_COLS];

		Piece original = EasyMock.createMock(Piece.class);
		pieces[6][0] = original; // Place the mock piece at row 6, col 0

		EasyMock.expect(original.getPieceType()).andStubReturn(PieceType.PAWN);
		EasyMock.expect(original.getColor()).andStubReturn(PieceColor.WHITE);
		EasyMock.replay(original);

		Board board = new Board(pieces);

		// This will now return true safely without throwing a NullPointerException
		assertTrue(board.isEmpty(new Location(0, 0)));

		Piece copiedPiece = board.getPiece(new Location(6, 0));
		assertNotSame(original, copiedPiece);
		assertEquals(PieceType.PAWN, copiedPiece.getPieceType());
		assertEquals(PieceColor.WHITE, copiedPiece.getColor());

		EasyMock.verify(original);
	}

	@Test
	void setPieceReplacesExistingPiece() {
		Board board = new Board();
		board.initBoard();

		Location loc = new Location(6,0);
		Piece before = board.getPiece(loc);
		assertNotNull(before);
		Piece replacement = EasyMock.createMock(Piece.class);
		EasyMock.replay(replacement);

		board.setPiece(loc, replacement);

		assertSame(replacement, board.getPiece(loc));
		EasyMock.verify(replacement);
	}

	@Test
	void setPieceOnEmptySquarePlacesPiece() {
		Board board = new Board();
		board.clearBoard();

		Location loc = new Location(4,4);
		assertTrue(board.isEmpty(loc));
		Piece replacement = EasyMock.createMock(Piece.class);
		EasyMock.replay(replacement);

		// FIX: Just call the void method
		board.setPiece(loc, replacement);

		// Expected behavior: board has the replacement piece
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

		Piece piece = board.getPiece(new Location(6,0));
		assertNotNull(piece);
		assertEquals(PieceColor.WHITE, piece.getColor());
		assertEquals(PieceType.PAWN, piece.getPieceType());
	}

	@Test
	void initBoardCheckBlackRookReturnsBlackRook() {
		Board board = new Board();

		board.initBoard();

		Piece piece = board.getPiece(new Location(0,0));
		assertNotNull(piece);
		assertEquals(PieceColor.BLACK, piece.getColor());
		assertEquals(PieceType.ROOK, piece.getPieceType());
	}

	@Test
	void initBoardCheckEmptySquareReturnsEmpty() {
		Board board = new Board();

		board.initBoard();

		assertTrue(board.isEmpty(new Location(4,4)));
	}

	@Test
	void initBoardCheckOccupiedSquareReturnsNotEmpty() {
		Board board = new Board();

		board.initBoard();

		assertFalse(board.isEmpty(new Location(7,0)));
	}

	@Test
	void initBoardClearsExistingPiecesBeforeSetup() {
		Piece[][] pieces = new Piece[Board.TOTAL_ROWS][Board.TOTAL_COLS];
		pieces[4][4] = new Piece(PieceType.QUEEN, PieceColor.WHITE);
		Board board = new Board(pieces);

		board.initBoard();

		assertTrue(board.isEmpty(new Location(4,4)));
	}

	@Test
	void clearBoardEmptiesAllSquares() {
		Board board = new Board();

		board.initBoard();

		board.clearBoard();

		assertTrue(board.isEmpty(new Location(7,0)));
		assertTrue(board.isEmpty(new Location(0,7)));
	}

	@Test
	void getSnapshotReturnsDeepCopyOfPieces() {
		Piece original = EasyMock.createMock(Piece.class);
		EasyMock.expect(original.getColor()).andStubReturn(PieceColor.WHITE);
		EasyMock.expect(original.getPieceType()).andStubReturn(PieceType.PAWN);
		EasyMock.replay(original);
		
		Piece[][] pieces = new Piece[Board.TOTAL_ROWS][Board.TOTAL_COLS];
		pieces[6][0] = original; 
		Board board = new Board(pieces);
		Piece[][] snapshot = board.getSnapshot();
		
		assertNotSame(original, snapshot[6][0]);
		assertEquals(PieceColor.WHITE, snapshot[6][0].getColor());
		assertEquals(PieceType.PAWN, snapshot[6][0].getPieceType());
		
		EasyMock.verify(original);
	}

	@Test
	void movePieceMovesPieceAndEmptiesSource() {
		Board board = new Board();
		board.initBoard();
		Piece pawn = board.getPiece(new Location(6,0));
		Location from = new Location(6,0);
		Location to = new Location(5,0);

		board.movePiece(from, to);

		assertTrue(board.isEmpty(from));
		assertSame(pawn, board.getPiece(to));
	}

	@Test
	void movePieceCapturesOpponentPiece() {
		Board board = new Board();
		board.initBoard();
		Piece whitePawn = board.getPiece(new Location(6,0));
		Location from = new Location(6,0);
		Location to = new Location(1,0);

		board.movePiece(from, to);

		assertTrue(board.isEmpty(from));
		assertSame(whitePawn, board.getPiece(to));
	}

	@Test
	void findKingReturnsKingLocation() {
		Board board = new Board();

		board.initBoard();

		Location kingLocation = new Location(7,4);
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

	@Test
	void constructorHandlesNullRowsSafely() {
		Piece[][] pieces = new Piece[Board.TOTAL_ROWS][];

		pieces[1] = new Piece[Board.TOTAL_COLS];
		Piece mockPiece = EasyMock.createMock(Piece.class);
		pieces[1][0] = mockPiece;

		EasyMock.expect(mockPiece.getPieceType()).andStubReturn(PieceType.PAWN);
		EasyMock.expect(mockPiece.getColor()).andStubReturn(PieceColor.WHITE);
		EasyMock.replay(mockPiece);

		Board board = new Board(pieces);

		assertTrue(board.isEmpty(new Location(0, 0)));

		assertNotNull(board.getPiece(new Location(1, 0)));
		EasyMock.verify(mockPiece);
	}
}
