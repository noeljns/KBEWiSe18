/**
 * 
 */
package de.htw.ai.kbe.runmerunner;

/**
 * @author camiloocampo
 *
 */
public class TestClassForJUnitTesting {
	
	@RunMe
	public void publicMethodWithRunMe() {	
		System.out.println("I'm publicMethodWithRunMe and I'm annotated. \n");
	}
	
	@RunMe
	public void publicMethodWithRunMeWithParam(String string) {	
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
	private void privateMethodWithRunMeWithParam(String string) {	
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
	protected void protectedMethodWithRunMeWithParam(String string) {	
		System.out.println("I'm the protectedMethodWithRunMeWithParam and I'm annotated. \n");
	}
	
	protected void protectedMethodWithoutRunMe1() {	
		System.out.println("I'm the protectedMethodWithoutRunMe1 and I'm not annotated. \n");
	}
	
	protected int protectedMethodWithoutRunMe2() {	
		System.out.println("I'm the protectedMethodWithoutRunMe2 and I'm not annotated. \n");
		return 1;
	}

	
	// @RunMe
	// public void publicMethodWithRunMeWithException() {	
	//	System.out.println("I'm the public method publicMethodWithRunMeWithException and I'm annotated. \n");
	//	throw new RuntimeException();
	// }
	
}

