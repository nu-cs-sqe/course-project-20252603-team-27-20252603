package domain;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

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
}
