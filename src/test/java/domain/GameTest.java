package domain;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class GameTest {
	@Test
	public void pitest_test(){
		Game game=new Game();
		assertEquals(game.pass_pitest(5),0);
	}
}
