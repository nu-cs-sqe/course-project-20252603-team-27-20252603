package domain;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

class LocationTest {
	@Test
	void locationValidMinMinReturnsRow7Col0() {
		Location location = new Location("a1");

		assertEquals(7, location.getRow());
		assertEquals(0, location.getCol());
	}

	@Test
	void locationValidMaxMaxReturnsRow0Col7() {
		Location location = new Location("h8");

		assertEquals(0, location.getRow());
		assertEquals(7, location.getCol());
	}

	@Test
	void locationEmptyStringThrowsIllegalArgumentException() {
		assertThrows(IllegalArgumentException.class, () -> new Location(""));
	}

	@Test
	void locationValidA2ReturnsRow6Col0() {
		Location location = new Location("a2");

		assertEquals(6, location.getRow());
		assertEquals(0, location.getCol());
	}

	@Test
	void locationValidA3ReturnsRow5Col0() {
		Location location = new Location("a3");

		assertEquals(5, location.getRow());
		assertEquals(0, location.getCol());
	}

	@Test
	void locationValidA7ReturnsRow1Col0() {
		Location location = new Location("a7");

		assertEquals(1, location.getRow());
		assertEquals(0, location.getCol());
	}

	@Test
	void locationValidA8ReturnsRow0Col0() {
		Location location = new Location("a8");

		assertEquals(0, location.getRow());
		assertEquals(0, location.getCol());
	}

	@Test
	void locationValidE4ReturnsRow4Col4() {
		Location location = new Location("e4");

		assertEquals(4, location.getRow());
		assertEquals(4, location.getCol());
	}

	@Test
	void locationValidE1ReturnsRow7Col4() {
		Location location = new Location("e1");

		assertEquals(7, location.getRow());
		assertEquals(4, location.getCol());
	}

	@Test
	void locationUnsupportedSquareThrowsUnsupportedOperationException() {
		assertThrows(UnsupportedOperationException.class, () -> new Location("b2"));
	}

	@Test
	void locationStringTooLongThrowsIllegalArgumentException() {
		assertThrows(IllegalArgumentException.class, () -> new Location("a12"));
	}

	@Test
	void locationFileJustBelowValidThrowsIllegalArgumentException() {
		assertThrows(IllegalArgumentException.class, () -> new Location("`1"));
	}

	@Test
	void locationFileJustAboveValidThrowsIllegalArgumentException() {
		assertThrows(IllegalArgumentException.class, () -> new Location("i1"));
	}

	@Test
	void locationRankJustBelowValidThrowsIllegalArgumentException() {
		assertThrows(IllegalArgumentException.class, () -> new Location("a0"));
	}

	@Test
	void locationRankJustAboveValidThrowsIllegalArgumentException() {
		assertThrows(IllegalArgumentException.class, () -> new Location("a9"));
	}

	@Test
	void locationIntConstructorSetsRowAndCol() {
		Location location = new Location(3, 5);

		assertEquals(3, location.getRow());
		assertEquals(5, location.getCol());
	}

	@Test
	void locationIntConstructorStoresMaxMax() {
		Location location = new Location(7, 7);

		assertEquals(7, location.getRow());
		assertEquals(7, location.getCol());
	}

	@Test
	void equalsSameCoordinatesReturnsTrue() {
		Location a = new Location(3, 5);
		Location b = new Location(3, 5);

		assertEquals(true, a.equals(b));
	}

	@Test
	void equalsSameReferenceReturnsTrue() {
		Location a = new Location(3, 5);

		assertTrue(a.equals(a));
	}

	@Test
	void equalsDifferentRowReturnsFalse() {
		Location a = new Location(3, 5);
		Location b = new Location(4, 5);

		assertEquals(false, a.equals(b));
	}

	@Test
	void equalsDifferentColReturnsFalse() {
		Location a = new Location(3, 5);
		Location b = new Location(3, 4);

		assertEquals(false, a.equals(b));
	}

	@Test
	void equalsNullReturnsFalse() {
		Location a = new Location(3, 5);

		assertEquals(false, a.equals(null));
	}

	@Test
	void equalsDifferentTypeReturnsFalse() {
		Location a = new Location(3, 5);

		// use a plain Object to avoid SpotBugs warning about equals(String)
		assertEquals(false, a.equals(new Object()));
	}

	@Test
	void hashCodeEqualLocationsReturnsSameValue() {
		Location a = new Location(3, 5);
		Location b = new Location(3, 5);

		assertEquals(a.hashCode(), b.hashCode());
	}
}
