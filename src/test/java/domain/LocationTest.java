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
}
