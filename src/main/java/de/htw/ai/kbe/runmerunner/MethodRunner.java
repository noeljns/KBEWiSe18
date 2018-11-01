package de.htw.ai.kbe.runmerunner;

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
