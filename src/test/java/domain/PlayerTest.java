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

	@Test
	public void playerColorIsBlack() {
		Player testPlayer = new Player("Alan", PieceColor.BLACK);
		PieceColor testPlayerColor = testPlayer.getColor();
		assertEquals(PieceColor.BLACK, testPlayerColor);
	}
}
