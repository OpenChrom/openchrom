/*******************************************************************************
 * Copyright (c) 2008, 2010 Philip (eselmeister) Wenig.
 * 
 * This library is free
 * software; you can redistribute it and/or modify it under the terms of the GNU
 * Lesser General Public License as published by the Free Software Foundation;
 * either version 2.1 of the License, or (at your option) any later version.
 * This library is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS
 * FOR A PARTICULAR PURPOSE. See the GNU Lesser General Public License for more
 * details. You should have received a copy of the GNU Lesser General Public
 * License along with this library; if not, write to the Free Software
 * Foundation, Inc., 59 Temple Place, Suite 330, Boston MA 02111-1307, USA
 * 
 * 
 * Contributors: Philip (eselmeister) Wenig - initial API and implementation
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
