package main;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.Test;

class ArgumentParserTest {

	//Rect parsing tests
	@Test
	void givenExistingRectArgAndOtherArgsWhenInvokedThenSetIt() {
		ArgumentParser ap = new ArgumentParser("-size 10x10 -rect -1.0:1.0:-2.0:2.0 -tasks 1 -output \"bla.png\"".split(" "));
		assertEquals(-1.0,ap.getRealRectangle().getSmallestX());
		assertEquals(1.0,ap.getRealRectangle().getBiggestX());
		assertEquals(-2.0,ap.getRealRectangle().getSmallestY());
		assertEquals(2.0,ap.getRealRectangle().getBiggestY());
	}
	@Test
	void givenExistingRectButNonExistingOtherArgsWhenInvokedThenThrowException() {
		assertThrows(IllegalArgumentException.class,() -> new ArgumentParser("-size -rect -1.0:1.0:-1.0:1.0 -tasks -output".split(" ")));
	}
	@Test
	void givenNonExistingRectAndOtherArgsWhenInvokedThenThrowException() {
		assertThrows(IllegalArgumentException.class,
				() -> new ArgumentParser("-size 10x10 -rect -tasks 1 -output \"bla.png\"".split(" ")));
	}

}
