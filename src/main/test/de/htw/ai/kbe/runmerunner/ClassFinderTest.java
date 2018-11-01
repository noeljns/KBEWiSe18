package de.htw.ai.kbe.runmerunner;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class ClassFinderTest {

	private ClassFinder classFinder;
	private Class<?> testClass;
	private TestClass testObject;
	private ClassFinderResult result;

	// only works if there is corresponding fields in ClassFinderTest
	@Before
	public void initResult() {
		classFinder = new ClassFinder();
		testClass = TestClass.class;
		testObject = new TestClass();
		result = classFinder.findAndRunMethods(testClass, testObject);
	}
	
	
	// Testcase: Finde alle ohne @RunMe-Methoden
	@Test
	public void testFindAllMethodsWithoutAnnotationRunMe() {
		Assert.assertEquals(6, result.getNotAnnotated().size());
		// Assert.assertTrue(result.getAnnotated().contains("MyMethodName"));
	}
	
	// Testcase: Finde alle @RunMe-Methoden
	@Test
	public void testFindAllMethodsWithAnnotationRunMe() {
		Assert.assertEquals(6, result.getAnnotated().size());
	}
	
	
	// Testcase: Finde alle @RunMe-Methoden die nicht invokierbar sind, da privat
	@Test
	public void testFindAllMethodsWithAnnotationRunMeAndPrivate() {
		Assert.assertEquals(2, result.getPrivateMethods().size());
	}
	
	
	// Testcase: Finde alle @RunMe-Methoden die nicht invokierbar sind, da Parameter
	@Test
	public void testFindAllMethodsWithAnnotationRunMeAndWithParams() {
		Assert.assertEquals(2, result.getMethodsWithParams().size());
	}
	
	
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
