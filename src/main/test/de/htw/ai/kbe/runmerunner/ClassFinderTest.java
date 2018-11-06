package de.htw.ai.kbe.runmerunner;

import java.lang.reflect.Method;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class ClassFinderTest {

	private ClassFinder classFinder;
	private Class<?> testClass;
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
		
		Assert.assertTrue(result.getNotAnnotated().contains("publicMethodWithoutRunMe1"));
		Assert.assertTrue(result.getNotAnnotated().contains("publicMethodWithoutRunMe2"));
		Assert.assertTrue(result.getNotAnnotated().contains("privateMethodWithoutRunMe1"));
		Assert.assertTrue(result.getNotAnnotated().contains("privateMethodWithoutRunMe2"));
		Assert.assertTrue(result.getNotAnnotated().contains("protectedMethodWithoutRunMe1"));
		Assert.assertTrue(result.getNotAnnotated().contains("protectedMethodWithoutRunMe2"));
		Assert.assertTrue(result.getNotAnnotated().contains("publicFinalMethodWithoutRunMe"));
		Assert.assertTrue(result.getNotAnnotated().contains("protectedFinalMethodWithoutRunMe"));
		Assert.assertTrue(result.getNotAnnotated().contains("publicStaticMethodWithoutRunMe"));
		Assert.assertTrue(result.getNotAnnotated().contains("privateStaticMethodWithoutRunMe"));
		Assert.assertTrue(result.getNotAnnotated().contains("protectedStaticMethodWithoutRunMe"));
		Assert.assertTrue(result.getNotAnnotated().contains("superTestClassMethodSecondWithoutRunMe"));
	}
	
	
	// Testcase: Finde alle @RunMe-Methoden
	@Test
	public void testFindAllMethodsWithAnnotationRunMe() {
		Assert.assertEquals(14, result.getAnnotated().size());
		
		Assert.assertTrue(result.getAnnotated().contains("publicMethodWithRunMe"));
		Assert.assertTrue(result.getAnnotated().contains("publicMethodWithParamWithRunMe"));
		Assert.assertTrue(result.getAnnotated().contains("privateMethodWithRunMe"));
		Assert.assertTrue(result.getAnnotated().contains("privateMethodWithParamWithRunMe"));
		Assert.assertTrue(result.getAnnotated().contains("protectedMethodWithRunMe"));
		Assert.assertTrue(result.getAnnotated().contains("protectedMethodWithParamWithRunMe"));
		Assert.assertTrue(result.getAnnotated().contains("publicFinalMethodWithRunMe"));
		Assert.assertTrue(result.getAnnotated().contains("protectedFinalMethodWithRunMe"));
		Assert.assertTrue(result.getAnnotated().contains("publicStaticMethodWithRunMe"));
		Assert.assertTrue(result.getAnnotated().contains("privateStaticMethodWithRunMe"));
		Assert.assertTrue(result.getAnnotated().contains("privateStaticMethodWithParamsWithRunMe"));
		Assert.assertTrue(result.getAnnotated().contains("protectedStaticMethodWithRunMe"));
		Assert.assertTrue(result.getAnnotated().contains("superTestClassMethodFirstWithRunMe"));
		Assert.assertTrue(result.getAnnotated().contains("interfaceMethodWithRunMe"));
	}
	
	
	// Testcase: Finde alle @RunMe-Methoden die nicht invokierbar sind, da privat
	@Test
	public void testFindAllMethodsWithAnnotationRunMeAndPrivate() {
		Assert.assertEquals(4, result.getPrivateMethods().size());
		
		Assert.assertTrue(result.getPrivateMethods().contains("privateMethodWithRunMe"));
		Assert.assertTrue(result.getPrivateMethods().contains("privateMethodWithParamWithRunMe"));
		Assert.assertTrue(result.getPrivateMethods().contains("privateStaticMethodWithRunMe"));
		Assert.assertTrue(result.getPrivateMethods().contains("privateStaticMethodWithParamsWithRunMe"));
	}
	
	
	// Testcase: Finde alle @RunMe-Methoden die nicht invokierbar sind, da Parameter
	@Test
	public void testFindAllMethodsWithAnnotationRunMeAndWithParamsAndNotPrivate() {
		Assert.assertEquals(2, result.getMethodsWithParams().size());
		
		Assert.assertTrue(result.getMethodsWithParams().contains("publicMethodWithParamWithRunMe"));
		Assert.assertTrue(result.getMethodsWithParams().contains("protectedMethodWithParamWithRunMe"));
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
		
		boolean success = classFinder.findClassAndRunMethods(new String[] { "AnotherTestClassForJUnitTesting", "report.txt" });
		Assert.assertTrue(success);
	}
}
