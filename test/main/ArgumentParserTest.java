package main;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.Test;

class ArgumentParserTest {
	//TODO
	//Add tests when the option is present but there are no arguments to it

	//Rect parsing tests
	@Test
	void given_ExistingRectArgAndOtherArgs_When_Invoked_Then_SetIt() {
		ArgumentParser ap = new ArgumentParser("-size 10x10 -rect -1.0:1.0:-2.0:2.0 -tasks 1 -output \"bla.png\" -granularity 50 -max-iterations 50".split(" "));
		assertEquals(-1.0,ap.getRealRectangle().getSmallestX());
		assertEquals(1.0,ap.getRealRectangle().getBiggestX());
		assertEquals(-2.0,ap.getRealRectangle().getSmallestY());
		assertEquals(2.0,ap.getRealRectangle().getBiggestY());
	}
	@Test
	void given_ExistingRectButNonExistingOtherArgsWhen_Invoked_Then_ThrowException() {
		assertThrows(IllegalArgumentException.class,() -> new ArgumentParser("-size -rect -1.0:1.0:-1.0:1.0 -tasks -output -granularity 50 -max-iterations 50".split(" ")));
	}
	@Test
	void given_NonExistingRectAndOtherArgs_When_Invoked_Then_ThrowException() {
		assertThrows(IllegalArgumentException.class,
				() -> new ArgumentParser("-size 10x10 -rect -tasks 1 -output \"bla.png\" -granularity 50 -max-iterations 50".split(" ")));
	}
	//Granularity tests
	@Test
	void given_NoGranularityOption_When_Invoked_Then_ThrowException() {
		assertThrows(IllegalArgumentException.class,
				() -> new ArgumentParser("-size 10x10 -rect -1.0:1.0:-2.0:2.0 -tasks 1 -output \"bla.png\" -max-iterations 50".split(" ")));
	}
	@Test
	void given_GranularityArgument_When_Invoked_Then_SetIt() {
			ArgumentParser ap = new ArgumentParser("-size 10x10 -rect -1.0:1.0:-2.0:2.0 -tasks 1 -output \"bla.png\" -granularity 69 -max-iterations 50".split(" "));
			assertEquals(69, ap.getGranularity());
	}

}
