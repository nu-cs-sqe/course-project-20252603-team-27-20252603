package domain;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class LocationTest {

	@Test
	public void testGetRow_minimumBoundary() {
		Location location = new Location(0, 4);
		assertEquals(0, location.getRow());
	}

	@Test
	public void testGetRow_maximumBoundary() {
		Location location = new Location(7, 4);
		assertEquals(7, location.getRow());
	}

	@Test
	public void testGetCol_minimumBoundary() {
		Location location = new Location(4, 0);
		assertEquals(0, location.getCol());
	}

	@Test
	public void testGetCol_maximumBoundary() {
		Location location = new Location(4, 7);
		assertEquals(7, location.getCol());
	}

	@Test
	public void testGetRowAndCol_bothMinimumBoundary() {
		Location location = new Location(0, 0);
		assertEquals(0, location.getRow());
		assertEquals(0, location.getCol());
	}

	@Test
	public void testGetRowAndCol_bothMaximumBoundary() {
		Location location = new Location(7, 7);
		assertEquals(7, location.getRow());
		assertEquals(7, location.getCol());
	}

	@Test
	public void testGetRowAndCol_minRowMaxCol() {
		Location location = new Location(0, 7);
		assertEquals(0, location.getRow());
		assertEquals(7, location.getCol());
	}

	@Test
	public void testGetRowAndCol_maxRowMinCol() {
		Location location = new Location(7, 0);
		assertEquals(7, location.getRow());
		assertEquals(0, location.getCol());
	}

	@Test
	public void testEquals_sameMinimumBoundaryCorner() {
		Location a = new Location(0, 0);
		Location b = new Location(0, 0);
		assertTrue(a.equals(b));
	}

	@Test
	public void testEquals_sameMaximumBoundaryCorner() {
		Location a = new Location(7, 7);
		Location b = new Location(7, 7);
		assertTrue(a.equals(b));
	}

	@Test
	public void testEquals_sameMinRowMaxCol() {
		Location a = new Location(0, 7);
		Location b = new Location(0, 7);
		assertTrue(a.equals(b));
	}

	@Test
	public void testEquals_sameMaxRowMinCol() {
		Location a = new Location(7, 0);
		Location b = new Location(7, 0);
		assertTrue(a.equals(b));
	}

	@Test
	public void testEquals_sameReference_minimumBoundary() {
		Location a = new Location(0, 0);
		assertTrue(a.equals(a));
	}

	@Test
	public void testEquals_sameReference_maximumBoundary() {
		Location a = new Location(7, 7);
		assertTrue(a.equals(a));
	}

	@Test
	public void testEquals_rowDiffers_aAtMinBoundary() {
		Location a = new Location(0, 4);
		Location b = new Location(1, 4);
		assertFalse(a.equals(b));
	}

	@Test
	public void testEquals_rowDiffers_aAtMaxBoundary() {
		Location a = new Location(7, 4);
		Location b = new Location(6, 4);
		assertFalse(a.equals(b));
	}

	@Test
	public void testEquals_colDiffers_aAtMinBoundary() {
		Location a = new Location(4, 0);
		Location b = new Location(4, 1);
		assertFalse(a.equals(b));
	}

	@Test
	public void testEquals_colDiffers_aAtMaxBoundary() {
		Location a = new Location(4, 7);
		Location b = new Location(4, 6);
		assertFalse(a.equals(b));
	}
}
