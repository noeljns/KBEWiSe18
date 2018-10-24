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
	void method0() {
		
		System.out.println("hello, I'm annotated method0");
	}
	
	
	int method1() {
		System.out.println("hello, I'm method1 and I'm not annotated");
		return 1;
		
	}
	
	@RunMe
	void method2() {
		
		System.out.println("hello, I'm annotated method2");
	}
	
	
	
}
