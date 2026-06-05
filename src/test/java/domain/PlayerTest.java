package domain;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

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

	@Test
	public void playerColorIsNullColor() {
		assertThrows(IllegalArgumentException.class,
				() -> new Player("Alan", null));
	}

	@Test
	public void playerGivenNoName() {
		assertThrows(IllegalArgumentException.class,
				() -> new Player("", PieceColor.BLACK));
	}

	@Test
	public void playerGivenNullName() {
		assertThrows(IllegalArgumentException.class,
				() -> new Player(null, PieceColor.BLACK));
	}

	@Test
	public void setPlayerColorToWhite_previouslyBlack() {
		Player testPlayer = new Player("Alan", PieceColor.BLACK);
		PieceColor testPlayerColor = testPlayer.getColor();
		assertEquals(PieceColor.BLACK, testPlayerColor);

		testPlayer.setColor(PieceColor.WHITE);
		PieceColor testPlayerNewColor = testPlayer.getColor();
		assertEquals(PieceColor.WHITE, testPlayerNewColor);
	}

	@Test
	public void setPlayerColorToWhite_previouslyWhite() {
		Player testPlayer = new Player("Alan", PieceColor.WHITE);
		PieceColor testPlayerColor = testPlayer.getColor();
		assertEquals(PieceColor.WHITE, testPlayerColor);

		testPlayer.setColor(PieceColor.WHITE);
		PieceColor testPlayerNewColor = testPlayer.getColor();
		assertEquals(PieceColor.WHITE, testPlayerNewColor);
	}
}
