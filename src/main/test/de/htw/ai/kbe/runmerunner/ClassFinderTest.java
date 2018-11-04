package de.htw.ai.kbe.runmerunner;

import java.lang.reflect.Method;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class ClassFinderTest {

	private ClassFinder classFinder;
	private Class<?> testClass;
	// private TestClassForJUnitTesting testObject;
	private AnotherTestClassForJUnitTesting testObject;

	private ClassFinderResult result;

	// only works if there is corresponding fields in ClassFinderTest
	@Before
	public void initResult() {
		classFinder = new ClassFinder();
		testClass = AnotherTestClassForJUnitTesting.class;
		testObject = new AnotherTestClassForJUnitTesting();
		result = classFinder.findAndRunMethods(testClass, testObject);
	}
	
	
	// Testcase: Finde alle ohne @RunMe-Methoden
	@Test
	public void testFindAllMethodsWithoutAnnotationRunMe() {
		Assert.assertEquals(12, result.getNotAnnotated().size());
		// Assert.assertTrue(result.getAnnotated().contains("MyMethodName"));
	}
	
	// Testcase: Finde alle @RunMe-Methoden
	@Test
	public void testFindAllMethodsWithAnnotationRunMe() {
		Assert.assertEquals(14, result.getAnnotated().size());
	}
	
	
	// Testcase: Finde alle @RunMe-Methoden die nicht invokierbar sind, da privat
	@Test
	public void testFindAllMethodsWithAnnotationRunMeAndPrivate() {
		Assert.assertEquals(4, result.getPrivateMethods().size());
	}
	
	
	// Testcase: Finde alle @RunMe-Methoden die nicht invokierbar sind, da Parameter
	@Test
	public void testFindAllMethodsWithAnnotationRunMeAndWithParamsAndNotPrivate() {
		Assert.assertEquals(2, result.getMethodsWithParams().size());
	}
	
	
	// check whether all Methods are found
	@Test
	public void testFindAllMethods() {
		Method declaredMethods[] = testClass.getDeclaredMethods();
		int amountOfAllMethods = result.getAnnotated().size() + result.getNotAnnotated().size();
		
		Assert.assertEquals(declaredMethods.length, amountOfAllMethods);
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
		
		boolean success = classFinder.findClassAndRunMethods(new String[] { "TestClassForJUnitTesting", "report.txt" });
		Assert.assertTrue(success);
	}
	
	
}
