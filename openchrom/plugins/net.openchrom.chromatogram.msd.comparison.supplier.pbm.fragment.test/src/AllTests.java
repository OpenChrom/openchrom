/*******************************************************************************
 * Copyright (c) 2008, 2012 Philip (eselmeister) Wenig.
 * 
 * All rights reserved.
 *******************************************************************************/
import junit.framework.Test;
import junit.framework.TestSuite;
import junitx.util.DirectorySuiteBuilder;
import junitx.util.SimpleTestFilter;

/**
 * This class collects all junit tests this plugin and runs them.<br/>
 * It is necessary that the tests end with *Test.
 * 
 * @author eselmeister
 */
public class AllTests extends TestSuite {

	public static Test suite() throws Exception {

		DirectorySuiteBuilder builder = new DirectorySuiteBuilder();
		builder.setFilter(new SimpleTestFilter());
		return builder.suite("bin/");
	}
}
