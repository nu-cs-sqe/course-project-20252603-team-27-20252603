package domain;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class PlayerTest {
	@Test
	public void playerColorIsWhite() {
		Player testPlayer = new Player("Alan", PieceColor.WHITE);
		PieceColor testPlayerColor = testPlayer.getColor();
		assertEquals(PieceColor.WHITE, testPlayerColor);
	}
}
