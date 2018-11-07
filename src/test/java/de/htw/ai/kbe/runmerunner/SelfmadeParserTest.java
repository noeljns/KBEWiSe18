package de.htw.ai.kbe.runmerunner;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.notNullValue;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

/**
 * @author camiloocampo
 *
 */
public class SelfmadeParserTest {

	private SelfmadeParser parser;

	// only works if there is a field called parser in SelfmadeParsterTest Class
	@Before
	public void initParser() {
		parser = new SelfmadeParser();
	}
	
	
	// Testcase: -c className -o filename.txt			OK
	@Test
	public void testGivenRequiredOptionCAndOptionalOptionO() {
		String[] input= {"-c", "TestClassForJUnitTesting", "-o", "alternativerName.txt" };
		String[] parsedOutput= (parser.parse(input));
		Assert.assertNotNull(parsedOutput);	
		Assert.assertEquals("TestClassForJUnitTesting", parsedOutput[0]);
		Assert.assertEquals("alternativerName.txt", parsedOutput[1]);
	}

	
	// Testcase: c- class							OK
	@Test
	public void testGivenRequiredOptionCAndWithoutOptionalOptionO() {
		String[] input= {"-c", "TestClassForJUnitTesting" };
		String[] parsedOutput= (parser.parse(input));
		
		Assert.assertNotNull(parsedOutput);	
		// same assertion but with hamcrest
		Assert.assertThat(parsedOutput, is(notNullValue()));
		
		Assert.assertEquals("TestClassForJUnitTesting", parsedOutput[0]);
		Assert.assertEquals("report.txt", parsedOutput[1]);
	}
	
	
	// Testcase: -c className xyz  					OK (xyz soll ignoriert werden)
	@Test
	public void testWithoutOptionalOptionO() {
		String[] input= {"-c", "TestClassForJUnitTesting", "xyz"};
		String[] parsedOutput= (parser.parse(input));
		Assert.assertNotNull(parsedOutput);
		Assert.assertEquals("report.txt", parsedOutput[1]);
	}
	
	
	// Testcase: -c _____							NICHT OK
	@Test
	public void testMissingRequiredArgumentForRequiredOptionC() {
		String[] input= {"-c"};
		String[] parsedOutput= (parser.parse(input));
		Assert.assertNull(parsedOutput);
	}


	// Testcase: _____ xyz        					NICHT OK
	@Test
	public void testMissingRequiredOptionC() {
		String[] input= {"TestClassForJUnitTesting"};
		String[] parsedOutput= (parser.parse(input));
		Assert.assertNull(parsedOutput);
	}
}
























