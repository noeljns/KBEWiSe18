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
	public void method0() {
		
		System.out.println("hello, I'm annotated method0 \n");
	}
	
	
	public int method1() {
		System.out.println("hello, I'm method1 and I'm not annotated\n");
		return 1;
		
	}
	
	@RunMe
	public void method2() {
		
		System.out.println("hello, I'm annotated method2 \n");
	}
	
	@RunMe
	private void privateMethod() {
		
		System.out.println("hello, I'm a private method\n");
	}
	
	@RunMe
	public void methodWithParameters(String param, int counter) {
		
		System.out.println("hello, I'm a private method\n");
	}
}
