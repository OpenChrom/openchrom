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

package net.sf.jtables.table.impl;

import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

import java.io.File;
import java.io.IOException;
import java.io.StringReader;
import java.util.Arrays;

import net.sf.jtables.io.reader.ReaderTableString;
import net.sf.kerner.utils.io.UtilIO;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Ignore;
import org.junit.Test;

public class TestStringTable {

    @BeforeClass
    public static void setUpBeforeClass() throws Exception {
    }

    @AfterClass
    public static void tearDownAfterClass() throws Exception {
    }

    private TableString table1;

    private TableString table2;

    @Before
    public void setUp() throws Exception {
    }

    @After
    public void tearDown() throws Exception {
        table1 = table2 = null;
    }

    @Test
    public final void testAddColumn01() throws IOException {

        // read a table from a string
        // table has row and column headers

        table1 = new ReaderTableString(new StringReader("rowIDs\tcolA\tcolB"
                + UtilIO.NEW_LINE_STRING + "rowA\ta.a\ta.b"), true, true).readTableAtOnce();

        // add another column to table

        table1.addColumn("colC", new ColumnImpl<String>(Arrays.asList("a.c")));

        // System.out.println("col-ident: " + table1.getColumnIdentifier());
        // System.out.println("row-ident: " + table1.getRowIdentifier());
        // System.out.println("rows: " + table1.getRows());
        // System.out.println("cols: " + table1.getColumns());

        assertEquals(1, table1.getRows().size());
        assertEquals(3, table1.getColumns().size());
        assertArrayEquals(new String[] { "a.c" }, table1.getColumn("colC").toArray());
    }

    @Test
    public final void testAddColumn02() throws IOException {

        // read a table from a string
        // table has only column headers

        table1 = new ReaderTableString(new StringReader("\tcolA\tcolB" + UtilIO.NEW_LINE_STRING
                + "a.a\ta.b"), true, false).readTableAtOnce();

        // add another column to table

        table1.addColumn(new ColumnImpl<String>(Arrays.asList("a.c")));

        System.out.println("col-ident: " + table1.getColumnIdentifier());
        System.out.println("row-ident: " + table1.getRowIdentifier());
        System.out.println("rows: " + table1.getRows());
        System.out.println("cols: " + table1.getColumns());

        assertEquals(3, table1.getColumns().size());
        assertArrayEquals(new String[] { "a.a" }, table1.getColumn("colA").toArray());
        assertArrayEquals(new String[] { "a.b" }, table1.getColumn("colB").toArray());
        assertArrayEquals(new String[] { "a.c" }, table1.getColumn(2).toArray());
    }

    @Ignore
    @Test
    public final void testAddColumnColumnOfT() {
        fail("Not yet implemented"); // TODO
    }

    @Ignore
    @Test
    public final void testAddColumnIntColumnOfT() {
        fail("Not yet implemented"); // TODO
    }

    @Test
    public final void testAddRow01() throws IOException {

        // read a table from a string
        // table has row and column headers

        table1 = new ReaderTableString(new StringReader("\tcolA\tcolB" + UtilIO.NEW_LINE_STRING
                + "rowA\ta.a\ta.b"), true, true).readTableAtOnce();

        System.out.println("col-ident: " + table1.getColumnIdentifier());
        System.out.println("row-ident: " + table1.getRowIdentifier());
        System.out.println("rows: " + table1.getRows());
        System.out.println("cols: " + table1.getColumns());

        // add another row to table

        table1.addRow("rowB", new RowImpl<String>(Arrays.asList("b.a", "b.b")));

        assertEquals(2, table1.getRows().size());
        assertArrayEquals(new String[] { "b.a", "b.b" }, table1.getRow("rowB").toArray());
    }

    @Test
    public final void testAddRow02() throws IOException {

        // read a table from a string
        // table has only column headers

        table1 = new ReaderTableString(new StringReader("\tcolA\tcolB" + UtilIO.NEW_LINE_STRING
                + "a.a\ta.b"), true, false).readTableAtOnce();

        // add another row to table

        table1.addRow(new RowImpl<String>(Arrays.asList("b.a", "b.b")));

        assertEquals(2, table1.getRows().size());
        assertArrayEquals(new String[] { "a.a", "b.a" }, table1.getColumn("colA").toArray());
        assertArrayEquals(new String[] { "a.b", "b.b" }, table1.getColumn("colB").toArray());
    }

    @Test
    public final void testAddRowInt01() throws IOException {

        // read a table from a string
        // table has only column headers

        table1 = new ReaderTableString(new StringReader("\tcolA\tcolB" + UtilIO.NEW_LINE_STRING
                + "a.a\ta.b"), true, false).readTableAtOnce();

        // add another row to table at index 0 (before current line)

        table1.addRow(0, new RowImpl<String>(Arrays.asList("b.a", "b.b")));

        assertEquals(2, table1.getRows().size());

        assertArrayEquals(new String[] { "b.a", "b.b" }, table1.getRow(0).toArray());
        assertArrayEquals(new String[] { "a.a", "a.b" }, table1.getRow(1).toArray());
    }

    @Test
    public final void testAddRowInt02() throws IOException {

        // read a table from a string
        // table has only column headers

        table1 = new ReaderTableString(new StringReader("\tcolA\tcolB" + UtilIO.NEW_LINE_STRING
                + "a.a\ta.b"), true, false).readTableAtOnce();

        // add another row to table

        table1.addRow(1, new RowImpl<String>(Arrays.asList("b.a", "b.b")));

        assertEquals(2, table1.getRows().size());

        assertArrayEquals(new String[] { "a.a", "a.b" }, table1.getRow(0).toArray());
        assertArrayEquals(new String[] { "b.a", "b.b" }, table1.getRow(1).toArray());
    }

    @Test(expected = IllegalArgumentException.class)
    public final void testAddRowInt03() throws IOException {

        // read a table from a string
        // table has only column headers

        table1 = new ReaderTableString(new StringReader("\tcolA\tcolB" + UtilIO.NEW_LINE_STRING
                + "a.a\ta.b"), true, false).readTableAtOnce();

        // add another row to table

        table1.addRow(3, new RowImpl<String>(Arrays.asList("b.a", "b.b")));

    }

    @Test(expected = IllegalArgumentException.class)
    public final void testAddRowInt04() throws IOException {

        // read a table from a string
        // table has only column headers

        table1 = new ReaderTableString(new StringReader("\tcolA\tcolB" + UtilIO.NEW_LINE_STRING
                + "a.a\ta.b"), true, false).readTableAtOnce();

        // add another row to table

        table1.addRow(-1, new RowImpl<String>(Arrays.asList("b.a", "b.b")));

    }

    @Test
    public final void testClone01() throws IOException {
        // read a table from a string
        // table has row and column headers

        table1 = new ReaderTableString(new StringReader("\tcolA\tcolB" + UtilIO.NEW_LINE_STRING
                + "rowA\ta.a\ta.b"), true, true).readTableAtOnce();

        try {
            table2 = (TableString) table1.clone();
        } catch (final CloneNotSupportedException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

        assertEquals(table1, table2);
    }

    @Test
    public final void testEqualsObject01() throws IOException {
        table1 = new ReaderTableString(new StringReader(""), true, true).readTableAtOnce();
        table2 = new ReaderTableString(new StringReader(""), true, true).readTableAtOnce();
        assertEquals(table1, table2);
    }

    @Test
    public final void testEqualsObject02() throws IOException {
        table1 = new ReaderTableString(new StringReader("rowIDs\tcolA\tcolB"
                + UtilIO.NEW_LINE_STRING + "rowA\ta.a\ta.b" + UtilIO.NEW_LINE_STRING
                + "rowB\tb.a\tb.b"), true, true).readTableAtOnce();
        table2 = new ReaderTableString(new StringReader("\tcolA\tcolB" + UtilIO.NEW_LINE_STRING
                + "rowA\ta.a\ta.b" + UtilIO.NEW_LINE_STRING + "rowB\tb.a\tb.b"), true, true)
                .readTableAtOnce();
        assertEquals(table1, table2);
    }

    @Ignore
    @Test
    public final void testFill() {
        fail("Not yet implemented"); // TODO
    }

    @Ignore
    @Test
    public final void testFillAndSet() {
        fail("Not yet implemented"); // TODO
    }

    @Ignore
    @Test
    public final void testFillColumns() {
        fail("Not yet implemented"); // TODO
    }

    @Ignore
    @Test
    public final void testFillRows() {
        fail("Not yet implemented"); // TODO
    }

    @Ignore
    @Test
    public final void testGet() {
        fail("Not yet implemented"); // TODO
    }

    @Ignore
    @Test
    public final void testGetColumnInt() {
        fail("Not yet implemented"); // TODO
    }

    @Ignore
    @Test
    public final void testGetColumnIterator() {
        fail("Not yet implemented"); // TODO
    }

    @Test
    public final void testGetColumnObject01() throws IOException {
        table1 = new ReaderTableString(new File("src/test/resources/anno-orphan-anno.txt"), true,
                false).readTableAtOnce();
        // System.out.println(table1.getColumns());
        // System.out.println(table1.getColumn("mz"));
        // System.out.println(table1.getColumn("frac"));
        assertArrayEquals(new String[] { "1", "4" }, table1.getColumn("mz").toArray());
        assertArrayEquals(new String[] { "2", "5" }, table1.getColumn("frac").toArray());
        assertArrayEquals(new String[] { "3", "6" }, table1.getColumn("conf").toArray());
    }

    @Ignore
    @Test
    public final void testGetColumns() {
        fail("Not yet implemented"); // TODO
    }

    @Test
    public final void testGetColumns01() throws IOException {
        table1 = new ReaderTableString(new StringReader("\tcolA\tcolB" + UtilIO.NEW_LINE_STRING
                + "rowA\ta.a\ta.b"), true, true).readTableAtOnce();

        assertArrayEquals(new ColumnImpl[] { new ColumnImpl<String>(Arrays.asList("a.a")),
                new ColumnImpl<String>(Arrays.asList("a.b")) }, table1.getColumns().toArray());
    }

    @Test
    public final void testGetColumns02() throws IOException {
        table1 = new ReaderTableString(new StringReader("\tcolA\tcolB" + UtilIO.NEW_LINE_STRING
                + "a.a\ta.b"), true, false).readTableAtOnce();

        assertArrayEquals(new ColumnImpl[] { new ColumnImpl<String>(Arrays.asList("a.a")),
                new ColumnImpl<String>(Arrays.asList("a.b")) }, table1.getColumns().toArray());
    }

    @Ignore
    @Test
    public final void testGetColumnSize() {
        fail("Not yet implemented"); // TODO
    }

    @Ignore
    @Test
    public final void testGetMaxColumnSize() {
        fail("Not yet implemented"); // TODO
    }

    @Ignore
    @Test
    public final void testGetMaxRowSize() {
        fail("Not yet implemented"); // TODO
    }

    @Ignore
    @Test
    public final void testGetNumberOfColumns() {
        fail("Not yet implemented"); // TODO
    }

    @Ignore
    @Test
    public final void testGetNumberOfRows() {
        fail("Not yet implemented"); // TODO
    }

    @Ignore
    @Test
    public final void testGetRowInt() {
        fail("Not yet implemented"); // TODO
    }

    @Ignore
    @Test
    public final void testGetRowIterator() {
        fail("Not yet implemented"); // TODO
    }

    @Ignore
    @Test
    public final void testGetRowObject01() throws IOException {

    }

    @Ignore
    @Test
    public final void testGetRows() {
        fail("Not yet implemented"); // TODO
    }

    @Ignore
    @Test
    public final void testGetRowSize() {
        fail("Not yet implemented"); // TODO
    }

    @Ignore
    @Test
    public final void testGetRowsString() {
        fail("Not yet implemented"); // TODO
    }

    @Test
    public final void testHashCode01() throws IOException {
        table1 = new ReaderTableString(new StringReader(""), true, true).readTableAtOnce();
        table2 = new ReaderTableString(new StringReader(""), true, true).readTableAtOnce();
        assertEquals(table1.hashCode(), table2.hashCode());
    }

    @Test
    public final void testHashCode02() throws IOException {
        table1 = new ReaderTableString(new StringReader("\tcolA\tcolB" + UtilIO.NEW_LINE_STRING
                + "rowA\ta.a\ta.b" + UtilIO.NEW_LINE_STRING + "rowB\tb.a\tb.b"), true, true)
                .readTableAtOnce();
        table2 = new ReaderTableString(new StringReader("\tcolA\tcolB" + UtilIO.NEW_LINE_STRING
                + "rowA\ta.a\ta.b" + UtilIO.NEW_LINE_STRING + "rowB\tb.a\tb.b"), true, true)
                .readTableAtOnce();
        assertEquals(table1.hashCode(), table2.hashCode());
    }

    @Test
    public final void testIterator01() throws IOException {
        table1 = new ReaderTableString(new StringReader(""), true, true).readTableAtOnce();
        table1.iterator();
    }

    @Ignore
    @Test
    public final void testRemove() {
        fail("Not yet implemented"); // TODO
    }

    @Ignore
    @Test
    public final void testRemoveColumn() {
        fail("Not yet implemented"); // TODO
    }

    @Ignore
    @Test
    public final void testRemoveRow() {
        fail("Not yet implemented"); // TODO
    }

    @Ignore
    @Test
    public final void testSet() {
        fail("Not yet implemented"); // TODO
    }

    @Ignore
    @Test
    public final void testSetColumn() {
        fail("Not yet implemented"); // TODO
    }

    @Ignore
    @Test
    public final void testSetRow() {
        fail("Not yet implemented"); // TODO
    }

}
