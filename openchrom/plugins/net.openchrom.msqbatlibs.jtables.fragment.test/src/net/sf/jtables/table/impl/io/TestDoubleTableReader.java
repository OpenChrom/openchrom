/**********************************************************************
Copyright (c) 2009-2012 Alexander Kerner. All rights reserved.
Licensed under the Apache License, Version 2.0 (the "License");
you may not use this file except in compliance with the License.
You may obtain a copy of the License at

    http://www.apache.org/licenses/LICENSE-2.0

Unless required by applicable law or agreed to in writing, software
distributed under the License is distributed on an "AS IS" BASIS,
WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
See the License for the specific language governing permissions and
limitations under the License.
 ***********************************************************************/

package net.sf.jtables.table.impl.io;

import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertEquals;

import java.io.IOException;
import java.io.StringReader;

import net.sf.jtables.io.reader.ReaderTableDouble;
import net.sf.jtables.table.impl.TableDouble;
import net.sf.kerner.utils.io.UtilIO;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

public class TestDoubleTableReader {

    @BeforeClass
    public static void setUpBeforeClass() throws Exception {
    }

    @AfterClass
    public static void tearDownAfterClass() throws Exception {
    }

    @Before
    public void setUp() throws Exception {
    }

    @After
    public void tearDown() throws Exception {
    }

    // START SNIPPET: example2

    @Test
    public final void testReadAll01() throws IOException {

        /**
         * colA colB colC rowA 0.0 0.1 0.2 rowB 1.0 1.1 1.2
         */

        // A string that contains a table (tab delimited)
        final String tableString = "rowIDs\tcolA\tcolB\tcolC" + UtilIO.NEW_LINE_STRING
                + "rowA\t0.0\t0.1\t0.2" + UtilIO.NEW_LINE_STRING + "rowB\t1.0\t1.1\t1.2";

        // A Reader to read the table
        final StringReader stringReader = new StringReader(tableString);

        // A TableReader to parse the file
        // First argument is the Reader (File or Stream would also work)
        // Second argument is if column headers are present
        // Third argument is if row headers are present
        // Forth argument is column-delimiter (in this case tab)
        final ReaderTableDouble tableReader = new ReaderTableDouble(stringReader, true, true, "\t");

        // Read the table at once
        final TableDouble table = tableReader.readTableAtOnce();

        // Close the reader
        tableReader.close();

        // table does have row headers
        assertEquals(2, table.getRowIdentifier().size());

        // table does have column headers
        assertEquals(3, table.getColumnIdentifier().size());

        assertArrayEquals(new Double[] { 0.0, 1.0 }, table.getColumn("colA").toArray());
        assertArrayEquals(new Double[] { 0.1, 1.1 }, table.getColumn("colB").toArray());
        assertArrayEquals(new Double[] { 0.2, 1.2 }, table.getColumn("colC").toArray());

        assertArrayEquals(new Double[] { 0.0, 0.1, 0.2 }, table.getRow("rowA").toArray());
        assertArrayEquals(new Double[] { 1.0, 1.1, 1.2 }, table.getRow("rowB").toArray());

    }

    // END SNIPPET: example2

}
