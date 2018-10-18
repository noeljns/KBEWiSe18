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
		
		options.addOption("c", "class", true, "class name");
		options .addOption("o", "output", false, "output of report");
	}
	
	public void parse() {
		CommandLineParser parser = new DefaultParser();
		
		CommandLine cmd = null;
		try {
			cmd = parser.parse( options, args);
		} catch (ParseException e) {
			
		}
	}

}









