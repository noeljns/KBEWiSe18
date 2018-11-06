package de.htw.ai.kbe.runmerunner;

import java.util.ArrayList;
import java.util.List;

/**
 * class represents the sorted methods of an inspected class
 * @author jns
 *
 */
public class ClassFinderResult {

	private List<String> annotated = new ArrayList<String>();
	private List<String> notAnnotated = new ArrayList<String>();
	private List<String> privateMethods = new ArrayList<String>();
	private List<String> methodsWithParams = new ArrayList<String>();
	
	public List<String> getAnnotated() {
		return annotated;
	}

	public void addAnnotated(String methodName) {
		annotated.add(methodName);
	}

	public List<String> getNotAnnotated() {
		return notAnnotated;
	}

	public void addNotAnnotated(String methodName) {
		notAnnotated.add(methodName);
	}

	public List<String> getPrivateMethods() {
		return privateMethods;
	}

	public void addPrivateMethod(String methodName) {
		privateMethods.add(methodName);
	}

	public List<String> getMethodsWithParams() {
		return methodsWithParams;
	}

	public void addMethodWithParams(String methodName) {
		methodsWithParams.add(methodName);
	}
}
