package de.htw.ai.kbe.runmerunner;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.ArrayList;
import java.util.List;

/**
 * class to find class with specific name and run annotated methods
 * 
 * @author jns, camiloocampo
 *
 */
public class ClassFinder {

	/**
	 * Constructor
	 */
	public ClassFinder() {
	}

	/**
	 * method to find specified class, run annotaded methods and write a report
	 * 
	 * @param name
	 *            of class and name of report
	 * @return true if class was found, methods were run and report was written,
	 *         false otherwise
	 */
	public boolean findClassAndRunMethods(String[] parsedArguments) {
		if (parsedArguments == null || parsedArguments.length != 2) {
			System.out.println("Parsed Arguments must be exactly two.");
			return false;
		}

		String className = parsedArguments[0];
		String output = parsedArguments[1];

		Class<?> reflectClass = findClass(className);
		if (reflectClass == null) {
			return false;
		}

		Object reflectObject = getObjectOfClass(reflectClass);
		if (reflectObject == null) {
			return false;
		}

		ClassFinderResult result = findAndRunMethods(reflectClass, reflectObject);
		if (result == null) {
			return false;
		}
		
		boolean success = writeReport(result, output);
		
		return  success;
	}

	
	private Class<?> findClass(String className) {
		Class<?> reflectClass = null;
		try {
			// hart codiert auf unser aktuelles package
			reflectClass = Class.forName("de.htw.ai.kbe.runmerunner." + className);
		} catch (ClassNotFoundException e) {
			System.out.println("Class could not be found.");
			return null;
		}
		return reflectClass;
	}

	
	private Object getObjectOfClass(Class<?> reflectClass) {
		Object reflectObject = null;
		try {
			reflectObject = reflectClass.newInstance();
		} catch (InstantiationException | IllegalAccessException e) {
			System.out.println("Class does not have any default constructor or constructor is not accessible.");
			return null;
		}

		return reflectObject;
	}

	
	// method is package private so that this method is testable in test directory with JUnit
	ClassFinderResult findAndRunMethods(Class<?> reflectClass, Object reflectObject) {
		// init result type
		ClassFinderResult result = new ClassFinderResult();
		
		// returns array of Method objects representing the public methods of this class
		Method declaredMethods[] = reflectClass.getDeclaredMethods();

		for (Method method : declaredMethods) {
			if (method.isAnnotationPresent(RunMe.class)) {
				// add to list of annotated methods
				result.addAnnotated(method.getName());

				int modifiers = method.getModifiers();
				if (Modifier.isPrivate(modifiers)) {
					result.addPrivateMethod(method.getName());
				} else if (method.getParameterCount() != 0) {
					result.addMethodWithParams(method.getName());
				} else {
					try {
						// null as no parameters
						method.invoke(reflectObject, null);
					} catch (IllegalArgumentException e) {
						System.out.println("While trying to invoce the method " + method.getName()
								+ " an IllegalArgumentException has occured: " + e.getMessage());
					} catch (InvocationTargetException e) {
						System.out.println("While trying to invoce the method " + method.getName()
								+ " an InvocationTargetException has occured: " + e.getMessage());
					} catch (IllegalAccessException e) {
						System.out.println("While trying to invoce the method " + method.getName()
								+ " an IllegalAccessException has occured: " + e.getMessage());
					}
				}
			} else {
				result.addNotAnnotated(method.getName());
			}
		}
		return result;
	}
	
	private boolean writeReport(ClassFinderResult result, String output) {
		try {
			FileWriter writer = new FileWriter(new File(output));
			// hier noch abfragen ob methoden vorhanden sind
			// kein Problem: falls keine methoden wird einfach kein loop durchlaufen...
			writer.write("Methoden mit @RunMe: ");

			for (String str : result.getAnnotated()) {
				writer.write(System.lineSeparator());
				writer.write(str);
			}
			writer.write(System.lineSeparator());
			writer.write(System.lineSeparator());

			// hier noch abfragen ob methoden vorhanden sind
			// kein Problem: falls keine methoden wird einfach kein loop durchlaufen...
			writer.write("Methoden ohne @RunMe: ");
			for (String str : result.getNotAnnotated()) {
				writer.write(System.lineSeparator());
				writer.write(str);
			}
			writer.write(System.lineSeparator());
			writer.write(System.lineSeparator());

			writer.write("Nicht-invokierbare Methoden mit @RunMe:");
			for (String str : result.getPrivateMethods()) {
				writer.write(System.lineSeparator());
				writer.write(str +": Private Methode");
			}
			
			for (String str : result.getMethodsWithParams()) {
				writer.write(System.lineSeparator());
				writer.write(str +": Methode mit Parametern");
			}
			
			writer.flush();
			writer.close();
			return true;

		} catch (IOException ex) {
			ex.printStackTrace();
			return false;
		}
	}
}
