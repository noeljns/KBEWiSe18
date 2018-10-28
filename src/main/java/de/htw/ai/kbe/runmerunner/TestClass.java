/**
 * 
 */
package de.htw.ai.kbe.runmerunner;

/**
 * @author camiloocampo
 *
 */
public class TestClass {
	
	@RunMe
	public void publicMethodWithRunMe() {	
		System.out.println("I'm the public method publicMethodWithRunMe and I'm annotated. \n");
	}
	
	@RunMe
	public void publicMethodWithRunMeWithParam(String string) {	
		System.out.println("I'm the public method publicMethodWithRunMeWithParam and I'm annotated. \n");
	}
	
	public void publicMethodWithoutRunMe1() {	
		System.out.println("I'm the public method publicMethodWithoutRunMe1 and I'm not annotated. \n");
	}
	
	public int publicmethodWithoutRunMe2() {
		System.out.println("I'm the public methodWithoutRunMe2 and I'm not annotated. \n");
		return 1;
	}
	
	
	@RunMe
	private void privateMethodWithRunMe() {	
		System.out.println("I'm the private method privateMethodWithRunMe and I'm annotated. \n");
	}
	
	@RunMe
	private void privateMethodWithRunMeWithParam(String string) {	
		System.out.println("I'm the private method privateMethodWithRunMeWithParam and I'm annotated. \n");
	}
	
	private void privateMethodWithoutRunMe1() {	
		System.out.println("I'm the private method privateMethodWithoutRunMe1 and I'm not annotated. \n");
	}
	
	private int privateMethodWithoutRunMe2() {	
		System.out.println("I'm the private method privateMethodWithoutRunMe2 and I'm not annotated. \n");
		return 1;
	}
	
	
	@RunMe
	protected void protectedMethodWithRunMe() {	
		System.out.println("I'm the protected method protectedMethodWithRunMe and I'm annotated. \n");
	}
	
	@RunMe
	protected void protectedMethodWithRunMeWithParam(String string) {	
		System.out.println("I'm the protected method protectedMethodWithRunMeWithParam and I'm annotated. \n");
	}
	
	protected void protectedMethodWithoutRunMe1() {	
		System.out.println("I'm the protected method protectedMethodWithoutRunMe1 and I'm not annotated. \n");
	}
	
	protected int protectedMethodWithoutRunMe2() {	
		System.out.println("I'm the protected method protectedMethodWithoutRunMe2 and I'm not annotated. \n");
		return 1;
	}

	
	@RunMe
	public void publicMethodWithRunMeWithException() {	
		System.out.println("I'm the public method publicMethodWithRunMeWithException and I'm annotated. \n");
		throw new RuntimeException();
	}
	
}

