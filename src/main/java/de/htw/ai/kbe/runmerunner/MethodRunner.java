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
 * class to run annotated methods of specified class
 */
public class MethodRunner {
	public static void main(String[] args) {	
		// nun nicht mehr notwendig, da className im SelfmadeParser ohne .java geholt wird mit getOptionValue()
		// String className= args[1].replaceAll(".java", "");

		// get arguments from command line via SelfmadeParser
		final String[] parsedArguments = new SelfmadeParser().parse(args);
		
		// verify that parsing worked correctly
		if (parsedArguments != null && parsedArguments.length == 2) {
			// find class and run annotaded methods via ClassFinder
			new ClassFinder().findClassAndRunMethods(parsedArguments);		
		}
	}
}
