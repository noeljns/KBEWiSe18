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
 * Hello world!
 *
 */

public class MethodRunner {
	public static void main(String[] args) {

		String className= args[1].replaceAll(".java", "");
		
		
		
		new SelfmadeParser().parse(args);

		List<String> annotated = new ArrayList<String>();
		List<String> notAnnotated = new ArrayList<String>();
		List<String> privateMethod = new ArrayList<String>();
		List<String> methodWithParams = new ArrayList<String>();

		try {
			Class reflectClass;
			// wie findet er die klasse im Package?
			reflectClass = Class.forName("de.htw.ai.kbe.runmerunner." + className);
			// was machen wir mit obj?
			Object obj = reflectClass.newInstance();

			System.out.println(reflectClass.getName());
			/*
			 * returns the array of Method objects representing the public methods of this
			 * class
			 */
			Method m[] = reflectClass.getDeclaredMethods();
			for (int i = 0; i < m.length; i++) {

				if (m[i].isAnnotationPresent(RunMe.class)) {

					// add to list of annotated methods
					annotated.add(m[i].getName());
					int modifiers = m[i].getModifiers();

					if (Modifier.isPrivate(modifiers)) {
						privateMethod.add(m[i].getName());

					} 
					else if (m[i].getParameterCount() != 0) {

						methodWithParams.add(m[i].getName());

					}

					else {

						try {
							m[i].invoke(obj, null);
						} catch (IllegalArgumentException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						} catch (InvocationTargetException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
					}
				}

				else {

					notAnnotated.add(m[i].getName());
				}

			}
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (InstantiationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		/// write to file

		try {
			FileWriter writer = new FileWriter(new File("default.txt"));

			// hier noch abfragen ob methoden vorhanden sind
			writer.write("Methoden mit @RunMe: ");

			for (String str : annotated) {
				writer.write(System.lineSeparator());
				writer.write(str);

			}
			writer.write(System.lineSeparator());

			// hier noch abfragen ob methoden vorhanden sind
			writer.write("Methoden ohne @RunMe: ");
			for (String str : notAnnotated) {

				writer.write(System.lineSeparator());
				writer.write(str);

			}
			writer.write(System.lineSeparator());

			
			writer.write("nicht invokierbare Methoden mit @RunMe");
			
			for (String str : privateMethod) {

				writer.write(System.lineSeparator());
				writer.write(str +": Illegal Access Exception ");

			}
			writer.write(System.lineSeparator());
			
			
			for (String str : methodWithParams) {

				writer.write(System.lineSeparator());
				writer.write(str +": Illegal Argument Exception ");

			}
			writer.write(System.lineSeparator());
			
			
			
			
			
			
			writer.flush();
			writer.close();

		} catch (IOException ex) {

			ex.printStackTrace();

		}

	}
}
