package de.htw.ai.kbe.runmerunner;

import org.junit.Assert;
import org.junit.Test;

public class ClassFinderTest {

	@Test
	public void testNullParameter() {
		ClassFinder classFinder = new ClassFinder();
		
		boolean success = classFinder.findClassAndRunMethods(null);
		Assert.assertFalse(success);
	}

	@Test
	public void testArrayWithSingleValue() {
		ClassFinder classFinder = new ClassFinder();
		
		boolean success = classFinder.findClassAndRunMethods(new String[] { "EinWert" });
		Assert.assertFalse(success);
	}

	@Test
	public void testNotExistingClassName() {
		ClassFinder classFinder = new ClassFinder();
		
		boolean success = classFinder.findClassAndRunMethods(new String[] { "KlasseGibtEsNicht", "report.txt" });
		Assert.assertFalse(success);
	}
	
	@Test
	public void testExistingClassName() {
		ClassFinder classFinder = new ClassFinder();
		
		boolean success = classFinder.findClassAndRunMethods(new String[] { "TestClass", "report.txt" });
		Assert.assertTrue(success);
	}
}
