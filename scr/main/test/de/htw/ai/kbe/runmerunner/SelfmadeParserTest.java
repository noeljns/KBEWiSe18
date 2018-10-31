package de.htw.ai.kbe.runmerunner;



import static org.junit.Assert.assertEquals;


import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;




/**
 * 
 */

/**
 * @author camiloocampo
 *
 */
public class SelfmadeParserTest {

	/**
	 * Test method for {@link de.htw.ai.kbe.runmerunner.SelfmadeParser#parse(java.lang.String[])}.
	 */
	
	
	@Before
	public void initParser() {
		
		
		
	}
	
	
	
	
	@Test
	public void testCorrectCOptionAndDefaultReportName() {
		SelfmadeParser parser= new SelfmadeParser();
		String[] input= {"-c", "TestClass" };
		String[] parsedOutput= (parser.parse(input));
		Assert.assertNotNull(parsedOutput);	
		Assert.assertEquals("TestClass", parsedOutput[0]);
		Assert.assertEquals("report.txt", parsedOutput[1]);
	}


	@Test
	public void testCorrectCOptionAndOOption() {
		SelfmadeParser parser= new SelfmadeParser();
		String[] input= {"-c", "TestClass", "-o", "alternativeName.txt" };
		String[] parsedOutput= (parser.parse(input));
		Assert.assertNotNull(parsedOutput);	
		Assert.assertEquals("TestClass", parsedOutput[0]);
		Assert.assertEquals("alternativeName.txt", parsedOutput[1]);
		
	}
	
	@Test
	public void testMissingCArgument() {
		SelfmadeParser parser= new SelfmadeParser();
		String[] input= {"-c"};
		String[] parsedOutput= (parser.parse(input));
		Assert.assertNull(parsedOutput);
		
	}
	
// -c className xyz  ok, xyz soll ignoriert werden
	@Test
	public void testMissingOOption() {
		SelfmadeParser parser= new SelfmadeParser();
		String[] input= {"-c", "TestClass", "xyz"};
		String[] parsedOutput= (parser.parse(input));
		Assert.assertNotNull(parsedOutput);
		Assert.assertEquals("report.txt", parsedOutput[1]);
	}
	
	// _____xyz        nicht ok
	@Test
	public void missingCOption() {
		SelfmadeParser parser= new SelfmadeParser();
		String[] input= {"TestClass"};
		String[] parsedOutput= (parser.parse(input));
		Assert.assertNull(parsedOutput);
		
	}
	
	
	
	
	
}
