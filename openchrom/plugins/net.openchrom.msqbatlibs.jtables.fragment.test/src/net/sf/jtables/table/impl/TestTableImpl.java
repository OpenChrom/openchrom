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
import static org.junit.Assert.assertNotSame;
import static org.junit.Assert.fail;

import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;

import net.sf.jtables.table.Column;
import net.sf.jtables.table.Row;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Ignore;
import org.junit.Test;

/**
 * 
 * 
 * @author <a href="mailto:alex.kerner.24@googlemail.com">Alexander Kerner</a>
 * @version 2010-11-19
 * 
 */
public class TestTableImpl {

    private TableImpl<String> ma;

    private TableImpl<String> ma2;

    private List<Row<String>> rows;

    private List<Column<String>> cols;

    @BeforeClass
    public static void setUpBeforeClass() throws Exception {
    }

    @AfterClass
    public static void tearDownAfterClass() throws Exception {
    }

    @SuppressWarnings("serial")
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
        ma = new TableImpl<String>(rows);
        ma2 = new TableImpl<String>(rows);
    }

    @After
    public void tearDown() throws Exception {
        cols = null;
        rows = null;
        ma = null;
        ma2 = null;
    }

    /**
     * Test method for {@link net.sf.jtables.table.impl.TableImpl#hashCode()}.
     */
    @Test
    @Ignore
    public final void testHashCode() {
        assertEquals(ma.hashCode(), ma2.hashCode());
    }

    /**
     * Test method for {@link net.sf.jtables.table.impl.TableImpl#hashCode()}.
     */
    @Test
    @Ignore
    public final void testHashCode01() {
        ma2 = new TableImpl<String>();
        assertNotSame(ma.hashCode(), ma2.hashCode());
    }

    /**
     * Test method for {@link net.sf.jtables.table.impl.TableImpl#TableImpl()}.
     */
    @Test
    @Ignore
    public final void testTableImpl() {
        fail("Not yet implemented"); // TODO
    }

    /**
     * Test method for
     * {@link net.sf.jtables.table.impl.TableImpl#TableImpl(java.util.List)}.
     */
    @Test
    @Ignore
    public final void testTableImplListOfQextendsListOfQextendsT() {
        fail("Not yet implemented"); // TODO
    }

    /**
     * Test method for
     * {@link net.sf.jtables.table.impl.TableImpl#checkRowIndex(int)}.
     */
    @Test
    @Ignore
    public final void testCheckRowIndex() {
        fail("Not yet implemented"); // TODO
    }

    /**
     * Test method for
     * {@link net.sf.jtables.table.impl.TableImpl#checkColumnIndex(int)}.
     */
    @Test
    @Ignore
    public final void testCheckColumnIndex() {
        fail("Not yet implemented"); // TODO
    }

    /**
     * Test method for {@link net.sf.jtables.table.impl.TableImpl#toString()}.
     */
    @Test
    @Ignore
    public final void testToString() {
        fail("Not yet implemented"); // TODO
    }

    /**
     * Test method for
     * {@link net.sf.jtables.table.impl.TableImpl#equals(java.lang.Object)}.
     */
    @Test
    @Ignore
    public final void testEqualsObject() {
        fail("Not yet implemented"); // TODO
    }

    /**
     * Test method for {@link net.sf.jtables.table.impl.TableImpl#iterator()}.
     */
    @Test
    @Ignore
    public final void testIterator() {
        fail("Not yet implemented"); // TODO
    }

    /**
     * Test method for
     * {@link net.sf.kerner.commons.collection.table.impl.TableImpl#getRow(int)}
     * .
     */
    @Test
    public final void testGetRow() {
        assertEquals(rows.get(0), ma.getRow(0));
    }

    /**
     * Test method for
     * {@link net.sf.kerner.commons.collection.table.impl.TableImpl#getRow(int)}
     * .
     */
    @Test(expected = NoSuchElementException.class)
    public final void testGetRow02() {
        ma.getRow(2);
    }

    /**
     * Test method for
     * {@link net.sf.kerner.commons.collection.table.impl.TableImpl#getRow(int)}
     * .
     */
    @Test(expected = IllegalArgumentException.class)
    public final void testGetRow03() {
        ma.getRow(-1);
    }

    /**
     * Test method for
     * {@link net.sf.kerner.commons.collection.table.impl.TableImpl#getRow(int)}
     * .
     */
    @Test
    public final void testGetRow01() {
        assertEquals(rows.get(1), ma.getRow(1));
    }

    /**
     * Test method for
     * {@link net.sf.kerner.commons.collection.table.impl.TableImpl#getRows()}.
     */
    @Test
    public final void testGetRows() {
        assertArrayEquals(rows.toArray(), ma.getRows().toArray());
    }

    /**
     * Test method for
     * {@link net.sf.kerner.commons.collection.table.impl.TableImpl#getColumn(int)}
     * .
     */
    @Test
    public final void testGetColumn() {
        assertEquals(cols.get(0), ma.getColumn(0));
    }

    /**
     * Test method for
     * {@link net.sf.kerner.commons.collection.table.impl.TableImpl#getColumn(int)}
     * .
     */
    @Test(expected = IllegalArgumentException.class)
    public final void testGetColumn01() {
        ma.getColumn(-1);
    }

    /**
     * Test method for
     * {@link net.sf.kerner.commons.collection.table.impl.TableImpl#getColumn(int)}
     * .
     */
    @Test(expected = NoSuchElementException.class)
    public final void testGetColumn02() {
        ma.getColumn(4);
    }

    /**
     * Test method for
     * {@link net.sf.kerner.commons.collection.table.impl.TableImpl#getColumn(int)}
     * .
     */
    @Test
    public final void testGetColumn03() {
        assertEquals(cols.get(2), ma.getColumn(2));
    }

    /**
     * Test method for
     * {@link net.sf.kerner.commons.collection.table.impl.TableImpl#getColumns()}
     * .
     */
    @Test
    public final void testGetColumns() {
        assertArrayEquals(cols.toArray(), ma.getColumns().toArray());
    }

    /**
     * Test method for
     * {@link net.sf.kerner.commons.collection.table.impl.TableImpl#get(int, int)}
     * .
     */
    @Test(expected = IllegalArgumentException.class)
    public final void testGet() {
        ma.get(-1, 0);
    }

    /**
     * Test method for
     * {@link net.sf.kerner.commons.collection.table.impl.TableImpl#get(int, int)}
     * .
     */
    @Test(expected = IllegalArgumentException.class)
    public final void testGet01() {
        ma.get(0, -1);
    }

    /**
     * Test method for
     * {@link net.sf.kerner.commons.collection.table.impl.TableImpl#get(int, int)}
     * .
     */
    @Test(expected = IllegalArgumentException.class)
    public final void testGet02() {
        ma.get(-1, -1);
    }

    /**
     * Test method for
     * {@link net.sf.kerner.commons.collection.table.impl.TableImpl#get(int, int)}
     * .
     */
    @Test
    public final void testGet03() {
        for (int i = 0; i < rows.size(); i++) {
            for (int j = 0; j < rows.get(i).size(); j++) {
                assertEquals(rows.get(i).get(j), ma.get(i, j));
            }
        }
    }

    /**
     * Test method for
     * {@link net.sf.kerner.commons.collection.table.impl.TableImpl#getRowSize(int)}
     * .
     */
    @Test
    public final void testGetRowSize() {
        for (int i = 0; i < rows.size(); i++) {
            assertEquals(rows.get(i).size(), ma.getRowSize(i));
        }
    }

    /**
     * Test method for
     * {@link net.sf.kerner.commons.collection.table.impl.TableImpl#getRowSize(int)}
     * .
     */
    @Test(expected = IllegalArgumentException.class)
    public final void testGetRowSize01() {
        ma.getRowSize(-1);
    }

    /**
     * Test method for
     * {@link net.sf.kerner.commons.collection.table.impl.TableImpl#getColumnSize(int)}
     * .
     */
    @Test
    public final void testGetColumnSize() {
        for (int i = 0; i < cols.size(); i++) {
            assertEquals(cols.get(i).size(), ma.getColumnSize(i));
        }
    }

    /**
     * Test method for
     * {@link net.sf.kerner.commons.collection.table.impl.TableImpl#getColumnSize(int)}
     * .
     */
    @Test(expected = IllegalArgumentException.class)
    public final void testGetColumnSize01() {
        ma.getColumnSize(-1);
    }

    /**
     * Test method for
     * {@link net.sf.kerner.commons.collection.table.impl.TableImpl#getMaxRowSize()}
     * .
     */
    @SuppressWarnings("serial")
    @Test
    public final void testGetMaxRowSize() {
        rows.add(new RowImpl<String>() {
            {
                add("1");
                add("2");
                add("3");
                add("4");
            }
        });
        ma = new TableImpl<String>(rows);
        assertEquals(4, ma.getMaxRowSize());
    }

    /**
     * Test method for
     * {@link net.sf.kerner.commons.collection.table.impl.TableImpl#getMaxRowSize()}
     * .
     */
    @SuppressWarnings("serial")
    @Test
    public final void testGetMaxRowSize01() {
        rows.clear();
        rows.add(new RowImpl<String>() {
            {

            }
        });
        ma = new TableImpl<String>(rows);
        assertEquals(0, ma.getMaxRowSize());
    }

    /**
     * Test method for
     * {@link net.sf.kerner.commons.collection.table.impl.TableImpl#getMaxRowSize()}
     * .
     */
    @SuppressWarnings("serial")
    @Test
    public final void testGetMaxRowSize02() {
        rows.clear();
        rows.add(new RowImpl<String>() {
            {
                add("1");
            }
        });
        ma = new TableImpl<String>(rows);
        assertEquals(1, ma.getMaxRowSize());
    }

    /**
     * Test method for
     * {@link net.sf.kerner.commons.collection.table.impl.TableImpl#getMaxColumnSize()}
     * .
     */
    @SuppressWarnings("serial")
    @Test
    public final void testGetMaxColumnSize() {
        rows.add(new RowImpl<String>() {
            {
                add("1");

            }
        });
        rows.add(new RowImpl<String>() {
            {
                add("1");

            }
        });
        ma = new TableImpl<String>(rows);
        assertEquals(4, ma.getMaxColumnSize());
    }

    /**
     * Test method for
     * {@link net.sf.kerner.commons.collection.table.impl.TableImpl#getNumberOfRows()}
     * .
     */
    @Test
    public final void testGetNumberOfRows() {
        assertEquals(rows.size(), ma.getNumberOfRows());
    }

    /**
     * Test method for
     * {@link net.sf.kerner.commons.collection.table.impl.TableImpl#getNumberOfColumns()}
     * .
     */
    @Test
    public final void testGetNumberOfColumns() {
        assertEquals(cols.size(), ma.getNumberOfColumns());
    }

    /**
     * Test method for
     * {@link net.sf.jtables.table.impl.TableImpl#contains(java.lang.Object)}.
     */
    @Test
    @Ignore
    public final void testContains() {
        fail("Not yet implemented"); // TODO
    }

    /**
     * Test method for
     * {@link net.sf.jtables.table.impl.TableImpl#getAllElements()}.
     */
    @Test
    @Ignore
    public final void testGetAllElements() {
        fail("Not yet implemented"); // TODO
    }

    /**
     * Test method for
     * {@link net.sf.jtables.table.impl.TableImpl#getRowIterator()}.
     */
    @Test
    @Ignore
    public final void testGetRowIterator() {
        fail("Not yet implemented"); // TODO
    }

    /**
     * Test method for
     * {@link net.sf.jtables.table.impl.TableImpl#getColumnIterator()}.
     */
    @Test
    @Ignore
    public final void testGetColumnIterator() {
        fail("Not yet implemented"); // TODO
    }

}
