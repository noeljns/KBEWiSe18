/**
 * 
 */
package de.htw.ai.kbe.runmerunner;

/**
 * @author camiloocampo
 *
 */
public class AnotherTestClassForJUnitTesting extends SuperTestClass implements InterfaceTestClass{
	
	@RunMe
	public void publicMethodWithRunMe() {	
		System.out.println("I'm publicMethodWithRunMe and I'm annotated. \n");
	}
	
	@RunMe
	public void publicMethodWithParamWithRunMe(String string) {	
		System.out.println("I'm publicMethodWithRunMeWithParam and I'm annotated. \n");
	}
	
	public void publicMethodWithoutRunMe1() {	
		System.out.println("I'm publicMethodWithoutRunMe1 and I'm not annotated. \n");
	}
	
	public int publicMethodWithoutRunMe2() {
		System.out.println("I'm publicMethodWithoutRunMe2 and I'm not annotated. \n");
		return 1;
	}
	
	
	@RunMe
	private void privateMethodWithRunMe() {	
		System.out.println("I'm privateMethodWithRunMe and I'm annotated. \n");
	}
	
	@RunMe
	private void privateMethodWithParamWithRunMe(String string) {	
		System.out.println("I'm privateMethodWithRunMeWithParam and I'm annotated. \n");
	}
	
	private void privateMethodWithoutRunMe1() {	
		System.out.println("I'm privateMethodWithoutRunMe1 and I'm not annotated. \n");
	}
	
	private int privateMethodWithoutRunMe2() {	
		System.out.println("I'm privateMethodWithoutRunMe2 and I'm not annotated. \n");
		return 1;
	}
	
	
	@RunMe
	protected void protectedMethodWithRunMe() {	
		System.out.println("I'm the protectedMethodWithRunMe and I'm annotated. \n");
	}
	
	@RunMe
	protected void protectedMethodWithParamWithRunMe(String string) {	
		System.out.println("I'm the protectedMethodWithRunMeWithParam and I'm annotated. \n");
	}
	
	protected void protectedMethodWithoutRunMe1() {	
		System.out.println("I'm the protectedMethodWithoutRunMe1 and I'm not annotated. \n");
	}
	
	protected int protectedMethodWithoutRunMe2() {	
		System.out.println("I'm the protectedMethodWithoutRunMe2 and I'm not annotated. \n");
		return 1;
	}
	

	public final void publicFinalMethodWithoutRunMe() {
		
		System.out.println("I'm the publicFinalMethodWithoutRunme() and I'm not annotated. \n");
	}	
		
	protected final void protectedFinalMethodWithoutRunMe() {
		
		System.out.println("I'm the protectedFinalMethodWithoutRunme and I'm not annotated. \n");
	}
	
	@RunMe
	public final void publicFinalMethodWithRunMe() {
		
		System.out.println("I'm the publicFinalMethodWithRunme() and I'm annotated. \n");
	}
	
	
	@RunMe
	protected final void protectedFinalMethodWithRunMe() {
		
		System.out.println("I'm the protectedFinalMethodWithRunme() and I'm annotated. \n");
	}
	
	
	public static void publicStaticMethodWithoutRunMe() {
		
		System.out.println("I'm the publicStaticMethodWithoutRunme() and I'm not annotated. \n");
	}
	
	private static void privateStaticMethodWithoutRunMe() {
		
		System.out.println("I'm the privateStaticMethodWithoutRunme() and I'm not annotated. \n");
	}
	
	protected static void protectedStaticMethodWithoutRunMe() {
		
		System.out.println("I'm the protectedStaticMethodWithoutRunme() and I'm not annotated. \n");
	}

	@RunMe
	public static void publicStaticMethodWithRunMe() {
		
		System.out.println("I'm the publicStaticMethodWithRunme() and I'm annotated. \n");
	}
	
	@RunMe
	private static void privateStaticMethodWithRunMe() {
		
		System.out.println("I'm the privateStaticMethodWithRunme() and I'm annotated. \n");
	}
	
	@RunMe
	private static void privateStaticMethodWithParamsWithRunme(int param) {
		
		System.out.println("I'm the privateStaticMethodWithParamsWithRunme() and I'm annotated. \n");
	}
	
	@RunMe
	protected static void protectedStaticMethodWithRunMe() {
		
		System.out.println("I'm the protectedStaticMethodWithRunme() and I'm annotated. \n");
	}
	
	@Override @RunMe
	public void superTestClassMethodFirstWithRunMe() {
		
		System.out.println("I overwrite the first superTestClassMethod of SuperTestClass and I'm annotated. \n");
	}
	
	@Override
	public void superTestClassMethodSecondWithoutRunMe() {
		
		System.out.println("I overwrite the second superTestClassMethod of SuperTestClass and I'm not annotated. \n");
	}

	@Override @RunMe
	public void interfaceMethodWithRunMe() {
		System.out.println("I overwrite the interfaceMethod of InterFaceTestClass and I'm annotated. \n");
		
	}

}

