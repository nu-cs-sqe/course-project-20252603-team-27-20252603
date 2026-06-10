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
				() -> new Move(
						new Location(6, 4),
						new Location(4, 4),
						null,
						null,
						null)
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

	@Test
	void extendedCtor_storesEnPassantFlag() {
		Location from = new Location(4, 4);
		Location to = new Location(3, 3);
		Piece captured = new Piece(PieceType.PAWN, PieceColor.BLACK);
		Move move = new Move(from, to, WHITE_PAWN, captured, null, false, true, "");

		assertFalse(move.isCastle());
		assertTrue(move.isEnPassant());
	}

	@Test
	void extendedCtor_storesNotation() {
		Location from = new Location(6, 4);
		Location to = new Location(4, 4);
		Move move = new Move(from, to, WHITE_PAWN, null, null, false, false, "e4");

		assertEquals("e4", move.getNotation());
	}

	@Test
	void extendedCtor_nullNotationDefaultsToEmptyString() {
		Location from = new Location(6, 4);
		Location to = new Location(4, 4);
		Move move = new Move(from, to, WHITE_PAWN, null, null, false, false, null);

		assertEquals("", move.getNotation());
	}

	@Test
	void equals_sameReference_returnsTrue() {
		Move m = new Move(new Location(0,0), new Location(1,1), new Piece(PieceType.PAWN, PieceColor.WHITE), null, null);
		assertEquals(m, m);
	}

	@Test
	void equals_equalMoves_returnsTrue() {
		Piece p = new Piece(PieceType.PAWN, PieceColor.WHITE);
		Location from = new Location(0,0);
		Location to = new Location(1,1);
		Move m1 = new Move(from, to, p, null, null);
		Move m2 = new Move(from, to, p, null, null);
		assertEquals(m1, m2);
	}

	@Test
	void equals_null_returnsFalse() {
		Move m = new Move(new Location(0,0), new Location(1,1), new Piece(PieceType.PAWN, PieceColor.WHITE), null, null);
		assertNotEquals(m, null);
	}

	@Test
	void equals_differentClass_returnsFalse() {
		Move m = new Move(new Location(0,0), new Location(1,1), new Piece(PieceType.PAWN, PieceColor.WHITE), null, null);
		assertNotEquals(m, "notAMove");
	}

	@Test
	void equals_differentFrom_returnsFalse() {
		Piece p = new Piece(PieceType.PAWN, PieceColor.WHITE);
		Location to = new Location(1,1);
		Move m1 = new Move(new Location(0,0), to, p, null, null);
		Move m2 = new Move(new Location(2,2), to, p, null, null);
		assertNotEquals(m1, m2);
	}

	@Test
	void equals_differentTo_returnsFalse() {
		Piece p = new Piece(PieceType.PAWN, PieceColor.WHITE);
		Location from = new Location(0,0);
		Move m1 = new Move(from, new Location(1,1), p, null, null);
		Move m2 = new Move(from, new Location(2,2), p, null, null);
		assertNotEquals(m1, m2);
	}

	@Test
	void equals_differentMovedPiece_returnsFalse() {
		Location from = new Location(0,0);
		Location to = new Location(1,1);
		Move m1 = new Move(from, to, new Piece(PieceType.PAWN, PieceColor.WHITE), null, null);
		Move m2 = new Move(from, to, new Piece(PieceType.ROOK, PieceColor.WHITE), null, null);
		assertNotEquals(m1, m2);
	}

	@Test
	void hashCode_equalMoves_sameHash() {
		Piece p = new Piece(PieceType.PAWN, PieceColor.WHITE);
		Location from = new Location(0,0);
		Location to = new Location(1,1);
		Move m1 = new Move(from, to, p, null, null);
		Move m2 = new Move(from, to, p, null, null);
		assertEquals(m1.hashCode(), m2.hashCode());
	}

	@Test
	void hashCode_differentMoves_differentHash() {
		Piece p = new Piece(PieceType.PAWN, PieceColor.WHITE);
		Move m1 = new Move(new Location(0,0), new Location(1,1), p, null, null);
		Move m2 = new Move(new Location(2,2), new Location(3,3), p, null, null);
		assertNotEquals(m1.hashCode(), m2.hashCode());
	}
}
