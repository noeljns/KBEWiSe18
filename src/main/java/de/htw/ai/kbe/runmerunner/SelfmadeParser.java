package de.htw.ai.kbe.runmerunner;

import java.util.List;

import org.apache.commons.cli.CommandLine;
import org.apache.commons.cli.CommandLineParser;
import org.apache.commons.cli.DefaultParser;
import org.apache.commons.cli.HelpFormatter;
import org.apache.commons.cli.Option;
import org.apache.commons.cli.Options;
import org.apache.commons.cli.ParseException;

/**
 * class to parse name of class and name of an output file from the command line
 * @author jns
 *
 */
public class SelfmadeParser {
	private CommandLineParser parser;
	private Options options = new Options();

	/**
	 * Constructor
	 */
	public SelfmadeParser() {
		// create the parser
		parser = new DefaultParser();
				
		// c is the single-character name of the option
		// class is the multi-character name of the option
		// true means that an argument is required after this option
		// class name is the self-documenting description
		options.addRequiredOption("c", "class", true, "class name");
		
		// test: that is not working as false means argument for -o is not ignored
		// options.addOption("o", "output", false, "output of report");
		
		// build own option as the specification (optional option and optional argument) is not offered by the Option class
		options.addOption(Option.builder("o").longOpt("output").hasArg().optionalArg(true).desc("output of report").build());
	}

	/**
	 * method to parse name of class and name of an output file from command line
	 * @param arguments from command line
	 * @throws ParseException
	 * @return a string array containing the parsed name of class and the name of an output file, null if ParseException have been thrown
	 */
	public String[] parse(String[] args) {
		try {
			// parse the command line arguments
			final CommandLine line = parser.parse(options, args);

			// if -cc and if className not necessary as exceptions are already thrown by invocing parse()
			// if there is no -c oder classname, then code will be interrupted before this line
			final String className = line.getOptionValue("c");
			final String output = line.getOptionValue("o", "report.txt");
			System.out.println("Input class: " + className);
			System.out.println("Report: " + output);

			return new String[] { className, output };
			
		} catch (ParseException e) {
	        System.out.println( "An error has occured: " + e.getMessage());
	        System.out.println( "");
	        // create usage message
	        final HelpFormatter formatter = new HelpFormatter();
	        formatter.printHelp("java -jar .... -c CLASSNAME [-o REPORT]", options);

	        return null;
		}
	}
}









