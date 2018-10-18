package de.htwBerlin.runMeRunner;

import org.apache.commons.cli.CommandLine;
import org.apache.commons.cli.CommandLineParser;
import org.apache.commons.cli.DefaultParser;
import org.apache.commons.cli.HelpFormatter;
import org.apache.commons.cli.Option;
import org.apache.commons.cli.Options;
import org.apache.commons.cli.ParseException;

/**
 * class to parse arguments of the command line
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
				
		// addRequiredOption() returns the resulting Options instance which is required
		// c is the single-character name of the option
		// class is the multi-character name of the option
		// true means that the argument is required after this option
		// class name is the self-documenting description
		options.addRequiredOption("c", "class", true, "class name");
		// build own option as the specification (optional option and optional argument) is not offered by the Option class
		options.addOption(Option.builder("o").longOpt("output").hasArg().optionalArg(true).desc("output of report").build());
	}
	
	public void parse(String[] args) {
		try {
			// parse the command line arguments
			final CommandLine line = parser.parse(options, args);
			
			if (line.hasOption("c")) {
				final String className = line.getOptionValue("c");
				final String output = line.getOptionValue("o", "<command line>");
				
				if (className != null) {
					System.out.println("Input class: " + className);
					System.out.println("Report: " + output);
				}
			}

		} catch (ParseException e) {
	        System.out.println( "An error has occured: " + e.getMessage());
	        System.out.println( "");
	        // create usage message
	        final HelpFormatter formatter = new HelpFormatter();
	        formatter.printHelp("java -jar .... -c CLASS NAME [-o REPORT]", options);
		}
	}

}









