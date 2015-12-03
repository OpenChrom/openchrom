/**********************************************************************
Copyright (c) 2009-2010 Alexander Kerner. All rights reserved.
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
import static org.junit.Assert.fail;

import java.io.IOException;
import java.io.StringReader;
import java.util.ArrayList;
import java.util.List;

import net.sf.jtables.io.reader.ReaderTableString;
import net.sf.jtables.table.Column;
import net.sf.jtables.table.Row;
import net.sf.jtables.table.TableAnnotated;
import net.sf.jtables.table.impl.ColumnImpl;
import net.sf.jtables.table.impl.RowImpl;
import net.sf.jtables.table.impl.TableString;
import net.sf.kerner.utils.io.UtilIO;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Ignore;
import org.junit.Test;

/**
 *
 * @author <a href="mailto:alex.kerner.24@googlemail.com">Alexander Kerner</a>
 * @version 2011-06-06
 *
 */
public class TestStringTableReader {

    @BeforeClass
    public static void setUpBeforeClass() throws Exception {
    }

    // private StringReader stringReader;

    // private TableReaderString tableReader;

    @AfterClass
    public static void tearDownAfterClass() throws Exception {
    }

    private TableString table;

    private List<Row<String>> rows;

    private List<Column<String>> cols;

    @Before
    public void setUp() throws Exception {
        rows = new ArrayList<Row<String>>();
        rows.add(new RowImpl<String>() {
            {
                add("eins00");
                add("eins01");
                add("eins02");
            }
        });
        rows.add(new RowImpl<String>() {
            {
                add("zwei00");
                add("zwei01");
                add("zwei02");
            }
        });
        cols = new ArrayList<Column<String>>();
        cols.add(new ColumnImpl<String>() {
            {
                add("eins00");
                add("zwei00");
            }
        });
        cols.add(new ColumnImpl<String>() {
            {
                add("eins01");
                add("zwei01");
            }
        });
        cols.add(new ColumnImpl<String>() {
            {
                add("eins02");
                add("zwei02");
            }
        });
        table = new TableString(rows);
        // stringReader = new StringReader(table.toString());
    }

    @After
    public void tearDown() throws Exception {
    }

    /**
     * Test method for
     * {@link net.sf.jtables.io.reader.ReaderTableAbstract#readTableAtOnce()} .
     *
     * @throws IOException
     */
    @Test
    public final void testReadAll() throws IOException {
        // A string that contains a table
        final String tableString = "1 2 3" + UtilIO.NEW_LINE_STRING + "a b c";
        // A Reader to read the table
        final StringReader stringReader = new StringReader(tableString);

        // First argument is the reader (File or Stream would also work)
        // Second and third argument is column/ row headers
        // Forth argument is column-delimiter
        final ReaderTableString tableReader = new ReaderTableString(stringReader, false, false, " ");

        // Read the table (is also of type StringTable, may be casted)
        final TableAnnotated<String> table = tableReader.readTableAtOnce();

        // Close the reader
        tableReader.close();

        assertArrayEquals(new String[] { "1", "2", "3" }, table.getRow(0).toArray());
    }

    @Test
    public final void testReadAll02() throws IOException {

        /**
         * <pre>
         *        colA    colB    colC
         * rowA   a.a     a.b     a.c
         * rowB   b.a     b.b     b.c
         * </pre>
         */

        // A string that contains a table (tab delimited)
        final String tableString = "   \tcolA\tcolB\tcolC" + UtilIO.NEW_LINE_STRING
                + "rowA\ta.a\ta.b\ta.c" + UtilIO.NEW_LINE_STRING + "rowB\tb.a\tb.b\tb.c";

        // A Reader to read the table
        final StringReader stringReader = new StringReader(tableString);

        // A TableReader to parse the file
        // First argument is the Reader (File or Stream would also work)
        // Second argument is if column headers are present
        // Third argument is if row headers are present
        // Forth argument is column-delimiter (in this case tab)
        final ReaderTableString tableReader = new ReaderTableString(stringReader, true, true, "\t");

        // Read the table at once
        final TableString table = tableReader.readTableAtOnce();

        // Close the reader
        tableReader.close();

        // table does have row headers
        assertEquals(2, table.getRowIdentifier().size());

        // table does have column headers
        assertEquals(3, table.getColumnIdentifier().size());

        assertArrayEquals(new String[] { "a.a", "b.a" }, table.getColumn("colA").toArray());
        assertArrayEquals(new String[] { "a.b", "b.b" }, table.getColumn("colB").toArray());
        assertArrayEquals(new String[] { "a.c", "b.c" }, table.getColumn("colC").toArray());

        assertArrayEquals(new String[] { "a.a", "a.b", "a.c" }, table.getRow("rowA").toArray());
        assertArrayEquals(new String[] { "b.a", "b.b", "b.c" }, table.getRow("rowB").toArray());
    }

    @Test
    public final void testReadAll03() throws IOException {

        /**
         * colA colB colC rowA a.a a.c rowB b.a b.b
         */

        // A string that contains a table (tab delimited)
        // note that table has empty cells

        final String tableString = "rowIDs\tcolA\tcolB\tcolC" + UtilIO.NEW_LINE_STRING
                + "rowA\ta.a\t\ta.c" + UtilIO.NEW_LINE_STRING + "rowB\tb.a\tb.b\t";

        // if empty cell is at the end of a row/ column (b.c), row/ column size
        // is less by one!

        // A Reader to read the table
        final StringReader stringReader = new StringReader(tableString);

        // A TableReader to parse the file
        // First argument is the Reader (File or Stream would also work)
        // Second argument is if column headers are present
        // Third argument is if row headers are present
        // Forth argument is column-delimiter (in this case tab)
        final ReaderTableString tableReader = new ReaderTableString(stringReader, true, true, "\t");

        // Read the table at once
        final TableString table = tableReader.readTableAtOnce();

        // Close the reader
        tableReader.close();

        // table does have row headers
        assertEquals(2, table.getRowIdentifier().size());

        // table does have column headers
        assertEquals(3, table.getColumnIdentifier().size());

        // max row size is 3 (including empty element)
        assertEquals(3, table.getMaxRowSize());

        // max column size is 2 (excluding empty element, since it is at the
        // end)
        assertEquals(2, table.getMaxColumnSize());

        assertArrayEquals(new String[] { "a.a", "b.a" }, table.getColumn("colA").toArray());
        assertArrayEquals(new String[] { "", "b.b" }, table.getColumn("colB").toArray());
        assertArrayEquals(new String[] { "a.c" }, table.getColumn("colC").toArray());

        assertArrayEquals(new String[] { "a.a", "", "a.c" }, table.getRow("rowA").toArray());
        assertArrayEquals(new String[] { "b.a", "b.b" }, table.getRow("rowB").toArray());
    }

    // /**
    // * Test method for
    // * {@link net.sf.jtables.table.impl.AbstractTableReader#readAll()}.
    // *
    // * @throws IOException
    // */
    // @SuppressWarnings("serial")
    // @Test
    // public final void testReadReader01() throws IOException {
    // table.setColumnIdentifier(new LinkedHashSet<String>() {
    // {
    // add("cid00");
    // add("cid01");
    // add("cid02");
    // }
    // });
    // table.setRowIdentifier(new LinkedHashSet<String>(){
    // {
    // add("rid00");
    // add("rid01");
    // }
    // });
    // stringReader = new StringReader(table.toString());
    // tableReader = new StringTableReader(stringReader, true, true);
    // AnnotatedTable<String> result = tableReader.readAll();
    // assertEquals(table.toString(), result.toString());
    // }
    //
    // /**
    // * Test method for
    // * {@link net.sf.jtables.table.impl.AbstractTableReader#readAll()}.
    // *
    // * @throws IOException
    // */
    // @SuppressWarnings("serial")
    // @Test
    // public final void testReadReader02() throws IOException {
    // table.setColumnIdentifier(new LinkedHashSet<String>() {
    // {
    // add("cid00");
    // add("cid01");
    // add("cid02");
    // }
    // });
    // stringReader = new StringReader(table.toString());
    // tableReader = new StringTableReader(stringReader, true, false);
    // AnnotatedTable<String> result = tableReader.readAll();
    // assertEquals(table.toString(), result.toString());
    // }
    //
    // /**
    // * Test method for
    // * {@link net.sf.jtables.table.impl.AbstractTableReader#readAll()}.
    // *
    // * @throws IOException
    // */
    // @SuppressWarnings("serial")
    // @Test
    // public final void testReadReader03() throws IOException {
    // table.setRowIdentifier(new LinkedHashSet<String>(){
    // {
    // add("rid00");
    // add("rid01");
    // }
    // });
    // stringReader = new StringReader(table.toString());
    // tableReader = new StringTableReader(stringReader, false, true);
    // AnnotatedTable<String> result = tableReader.readAll();
    // assertEquals(table.toString(), result.toString());
    // }
    //
    // /**
    // * Test method for
    // * {@link net.sf.jtables.table.impl.AbstractTableReader#readAll()}.
    // *
    // * @throws IOException
    // */
    // @SuppressWarnings("serial")
    // @Test
    // public final void testReadReader04() throws IOException {
    // table.setRowIdentifier(new LinkedHashSet<String>(){
    // {
    // add("rid00");
    // }
    // });
    // stringReader = new StringReader(table.toString());
    // tableReader = new StringTableReader(stringReader, false, true);
    // AnnotatedTable<String> result = tableReader.readAll();
    // assertEquals(table.toString(), result.toString());
    // }
    //
    // /**
    // * Test method for
    // * {@link net.sf.jtables.table.impl.AbstractTableReader#readAll()}.
    // *
    // * @throws IOException
    // */
    // @SuppressWarnings("serial")
    // @Test
    // public final void testReadReader05() throws IOException {
    // table.setColumnIdentifier(new LinkedHashSet<String>() {
    // {
    // add("cid00");
    // }
    // });
    // stringReader = new StringReader(table.toString());
    // tableReader = new StringTableReader(stringReader, true, false);
    // AnnotatedTable<String> result = tableReader.readAll();
    // assertEquals(table.toString(), result.toString());
    // }
    //
    // /**
    // *
    // * Test case for bug fix a01240a88aba5ce9ffb496b52eb53737d68591f9
    // *
    // * @throws IOException
    // */
    // @SuppressWarnings("serial")
    // @Test
    // public final void testReadAll01() throws IOException {
    // table = new StringTable();
    // table.setColumnIdentifier(new LinkedHashSet<String>() {
    // {
    // add("cid00");
    // }
    // });
    // stringReader = new StringReader(table.toString());
    // tableReader = new StringTableReader(stringReader, true, false);
    // AnnotatedTable<String> result = tableReader.readAll();
    // assertTrue(result.getAllElements().isEmpty());
    // }

    // START SNIPPET: example1

    /**
     * Test method for
     * {@link net.sf.jtables.io.reader.ReaderTableString#StringTableReader(boolean, boolean)}
     * .
     */
    @Test
    @Ignore
    public final void testStringTableReaderBooleanBoolean() {
        fail("Not yet implemented"); // TODO
    }

    // END SNIPPET: example1

    // START SNIPPET: example3

    /**
     * Test method for
     * {@link net.sf.jtables.io.reader.ReaderTableString#StringTableReader(boolean, boolean, java.lang.String)}
     * .
     */
    @Test
    @Ignore
    public final void testStringTableReaderBooleanBooleanString() {
        fail("Not yet implemented"); // TODO
    }

    // END SNIPPET: example3

}
