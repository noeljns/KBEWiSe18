package de.htwBerlin.runMeRunner;

import org.apache.commons.cli.CommandLine;
import org.apache.commons.cli.CommandLineParser;
import org.apache.commons.cli.DefaultParser;
import org.apache.commons.cli.Options;
import org.apache.commons.cli.ParseException;

public class SelfmadeParser {
	
	private String[] args = null;
	private Options options = new Options();

	/**
	 * Constructor
	 */
	public SelfmadeParser(String[] args) {
		this.args  = args;
		
		// addOption() returns the resulting Options instance
		// c is the single-character name of the option
		// class is the multi-character name of the option
		// true means that the argument is required after this option
		// class name is the self-documenting description
		options.addOption("c", "class", true, "class name");
		options.addOption("o", "output", false, "output of report");
	}
	
	public void parse() {
		// create the parser
		CommandLineParser parser = new DefaultParser();
		
		CommandLine line = null;
		try {
			// parse the command line arguments
			// options = the specified options
			// args = the command line arguments
			line = parser.parse(options, args);
			
			if(line.hasOption("c")) {
				String clazz = line.getOptionValue("c");
				// else meldung , dass -c fehlt und allgemeine usage meldung
			}
			if(line.hasOption("o")) {
				String output = line.getOptionValue("o");
				// else meldung , dass -c fehlt und allgemeine usage meldung
			}

		} catch (ParseException e) {
	        System.err.println( "Parsing failed.  Reason: " + e.getMessage() );
		}
	}

}









