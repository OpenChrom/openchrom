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

import java.util.NoSuchElementException;

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
 * @version 2010-09-20
 * @SuppressWarnings("serial")
 * 
 */
public class TestTableMutableImpl {

    private MutableTableImpl<String> ma;

    @BeforeClass
    public static void setUpBeforeClass() throws Exception {
    }

    @AfterClass
    public static void tearDownAfterClass() throws Exception {
    }

    @SuppressWarnings("serial")
    @Before
    public void setUp() throws Exception {
        ma = new MutableTableImpl<String>();
        ma.addRow(new RowImpl<String>() {
            {
                add("eins");
            }
        });
    }

    @After
    public void tearDown() throws Exception {
        ma = null;
    }

    /**
     * Test method for
     * {@link net.sf.kerner.commons.collection.table.impl.MutableTableImpl#MutableTableImpl(java.util.List)}
     * .
     */
    @Test
    @Ignore
    public final void testTableMutableImplListOfListOfQextendsT() {
        fail("Not yet implemented"); // TODO
    }

    /**
     * Test method for
     * {@link net.sf.kerner.commons.collection.table.impl.MutableTableImpl#MutableTableImpl()}
     * .
     */
    @Test
    @Ignore
    public final void testTableMutableImpl() {
        fail("Not yet implemented"); // TODO
    }

    /**
     * Test method for
     * {@link net.sf.kerner.commons.collection.table.impl.MutableTableImpl#setRow(int, java.util.List)}
     * .
     */
    @SuppressWarnings("serial")
    @Test
    public final void testSetRow() {
        ma.setRow(0, new RowImpl<String>() {
            {
                add("zwei");
            }
        });
        assertArrayEquals(new String[] { "zwei" }, ma.getRow(0).toArray());
    }

    /**
     * Test method for
     * {@link net.sf.kerner.commons.collection.table.impl.MutableTableImpl#setColumn(int, java.util.List)}
     * .
     */
    @SuppressWarnings("serial")
    @Test
    public final void testSetColumn() {
        ma.setColumn(0, new ColumnImpl<String>() {
            {
                add("zwei");
            }
        });
        assertArrayEquals(new String[] { "zwei" }, ma.getColumn(0).toArray());
    }

    /**
     * Test method for
     * {@link net.sf.kerner.commons.collection.table.impl.MutableTableImpl#setColumn(int, java.util.List)}
     * .
     */
    @SuppressWarnings("serial")
    @Test
    public final void testSetColumn01() {
        ma.setColumn(0, new ColumnImpl<String>() {
            {
                add("zwei");
                add("drei");
            }
        });
        assertArrayEquals(new String[] { "zwei", "drei" }, ma.getColumn(0).toArray());
    }

    /**
     * Test method for
     * {@link net.sf.kerner.commons.collection.table.impl.MutableTableImpl#setColumn(int, java.util.List)}
     * .
     */
    @SuppressWarnings("serial")
    @Test(expected = NoSuchElementException.class)
    public final void testSetColumn02() {
        ma.setColumn(0, new ColumnImpl<String>() {
            {
                add("zwei");
                add("drei");
            }
        });
        // we set, so column is replaced
        ma.getColumn(1);
    }

    /**
     * Test method for
     * {@link net.sf.kerner.commons.collection.table.impl.MutableTableImpl#setColumn(int, java.util.List)}
     * .
     */
    @SuppressWarnings("serial")
    @Test
    public final void testSetColumn03() {
        ma.setColumn(0, new ColumnImpl<String>() {
            {
                add("zwei");
                add("drei");
            }
        });
        // we set, so column is replaced
        assertEquals(1, ma.getNumberOfColumns());
    }

    /**
     * Test method for
     * {@link net.sf.kerner.commons.collection.table.impl.MutableTableImpl#set(int, int, java.lang.Object)}
     * .
     */
    @Test
    public final void testSet() {
        // log.debug(ma);
        ma.set(0, 0, "john");
        assertEquals("john", ma.getRow(0).get(0));
        assertEquals("john", ma.getColumn(0).get(0));
    }

    /**
     * Test method for
     * {@link net.sf.kerner.commons.collection.table.impl.MutableTableImpl#clear()}
     * .
     */
    @Test
    public final void testClear() {
        ma.clear();
        assertEquals(0, ma.getNumberOfRows());
        assertEquals(0, ma.getNumberOfColumns());
    }

    /**
     * Test method for
     * {@link net.sf.kerner.commons.collection.table.impl.MutableTableImpl#addRow(java.util.List)}
     * .
     */
    @SuppressWarnings("serial")
    @Test
    public final void testAddRowListOfQextendsT() {
        ma.addRow(new RowImpl<String>() {
            {
                add("zwei");
                add("drei");
            }
        });
        // log.debug(ma);
        assertArrayEquals(new String[] { "zwei", "drei" }, ma.getRow(1).toArray());
    }

    /**
     * Test method for
     * {@link net.sf.kerner.commons.collection.table.impl.MutableTableImpl#addRow(int, java.util.List)}
     * .
     */
    @SuppressWarnings("serial")
    @Test
    public final void testAddRowIntListOfQextendsT() {
        ma.addRow(0, new RowImpl<String>() {
            {
                add("zwei");
                add("drei");
            }
        });
        // log.debug(ma);
        assertArrayEquals(new String[] { "zwei", "drei" }, ma.getRow(0).toArray());
    }

    /**
     * Test method for
     * {@link net.sf.kerner.commons.collection.table.impl.MutableTableImpl#addColumn(java.util.List)}
     * .
     */
    @SuppressWarnings("serial")
    @Test
    public final void testAddColumnListOfQextendsT() {
        ma.addColumn(new ColumnImpl<String>() {
            {
                add("ff");
            }
        });
        assertEquals(2, ma.getNumberOfColumns());
    }

    /**
     * Test method for
     * {@link net.sf.kerner.commons.collection.table.impl.MutableTableImpl#addColumn(java.util.List)}
     * .
     */
    @SuppressWarnings("serial")
    @Test
    public final void testAddColumnListOfQextendsT01() {
        ma.addColumn(new ColumnImpl<String>() {
            {
                add("ff");
            }
        });
        // log.debug(ma);
        assertEquals(1, ma.getNumberOfRows());
    }

    /**
     * Test method for
     * {@link net.sf.kerner.commons.collection.table.impl.MutableTableImpl#addColumn(java.util.List)}
     * .
     */
    @SuppressWarnings("serial")
    @Test
    public final void testAddColumnListOfQextendsT02() {
        ma.addColumn(new ColumnImpl<String>() {
            {
                add("ff");
                add("ee");
            }
        });
        // log.debug(ma);
        assertEquals(2, ma.getNumberOfColumns());
    }

    /**
     * Test method for
     * {@link net.sf.kerner.commons.collection.table.impl.MutableTableImpl#addColumn(java.util.List)}
     * .
     */
    @SuppressWarnings("serial")
    @Test
    public final void testAddColumnListOfQextendsT03() {
        ma.addColumn(new ColumnImpl<String>() {
            {
                add("ff");
                add("ee");
            }
        });
        // log.debug(ma);
        assertEquals(2, ma.getNumberOfRows());
    }

    /**
     * Test method for
     * {@link net.sf.kerner.commons.collection.table.impl.MutableTableImpl#addColumn(int, java.util.List)}
     * .
     */
    @SuppressWarnings("serial")
    @Test
    public final void testAddColumnIntListOfQextendsT() {
        ma.addColumn(0, new ColumnImpl<String>() {
            {
                add("ff");
            }
        });
        assertEquals(2, ma.getNumberOfColumns());
    }

    /**
     * Test method for
     * {@link net.sf.kerner.commons.collection.table.impl.MutableTableImpl#addColumn(int, java.util.List)}
     * .
     */
    @SuppressWarnings("serial")
    @Test
    public final void testAddColumnIntListOfQextendsT01() {
        ma.addColumn(0, new ColumnImpl<String>() {
            {
                add("ff");
            }
        });
        assertEquals(1, ma.getNumberOfRows());
    }

    /**
     * Test method for
     * {@link net.sf.kerner.commons.collection.table.impl.MutableTableImpl#addColumn(int, java.util.List)}
     * .
     */
    @SuppressWarnings("serial")
    @Test
    public final void testAddColumnIntListOfQextendsT02() {
        ma.addColumn(0, new ColumnImpl<String>() {
            {
                add("ff");
                add("ee");
            }
        });
        assertEquals(2, ma.getNumberOfColumns());
    }

    /**
     * Test method for
     * {@link net.sf.kerner.commons.collection.table.impl.MutableTableImpl#addColumn(int, java.util.List)}
     * .
     */
    @SuppressWarnings("serial")
    @Test
    public final void testAddColumnIntListOfQextendsT03() {
        ma.addColumn(0, new ColumnImpl<String>() {
            {
                add("ff");
                add("ee");
            }
        });
        assertEquals(2, ma.getNumberOfRows());
    }

    /**
     * Test method for
     * {@link net.sf.kerner.commons.collection.table.impl.MutableTableImpl#addColumn(int, java.util.List)}
     * .
     */
    @SuppressWarnings("serial")
    @Test
    public final void testAddColumnIntListOfQextendsT04() {
        ma.addColumn(0, new ColumnImpl<String>() {
            {
                add("ff");
                add("ee");
            }
        });
        assertArrayEquals(new String[] { "ff", "ee" }, ma.getColumn(0).toArray());
    }

    /**
     * Test method for
     * {@link net.sf.kerner.commons.collection.table.impl.MutableTableImpl#fillRows(int, java.lang.Object)}
     * .
     */
    @Test
    public final void testFillRows() {
        ma.fillRows(2, "peter");
        assertArrayEquals(new String[] { "eins", "peter" }, ma.getRow(0).toArray());
    }

    /**
     * Test method for
     * {@link net.sf.kerner.commons.collection.table.impl.MutableTableImpl#fillRows(int, java.lang.Object)}
     * .
     */
    @Test
    public final void testFillRows01() {
        ma.fillRows(2, "peter");
        assertEquals(1, ma.getNumberOfRows());
    }

    /**
     * Test method for
     * {@link net.sf.kerner.commons.collection.table.impl.MutableTableImpl#fillColumns(int, java.lang.Object)}
     * .
     */
    @Test
    public final void testFillColumns() {
        ma.fillColumns(2, "peter");
        assertArrayEquals(new String[] { "eins", "peter" }, ma.getColumn(0).toArray());
    }

    /**
     * Test method for
     * {@link net.sf.kerner.commons.collection.table.impl.MutableTableImpl#fillColumns(int, java.lang.Object)}
     * .
     */
    @Test
    public final void testFillColumns01() {
        ma.fillColumns(2, "peter");
        assertEquals(1, ma.getNumberOfColumns());
    }

    /**
     * Test method for
     * {@link net.sf.kerner.commons.collection.table.impl.MutableTableImpl#fillColumns(int, java.lang.Object)}
     * .
     */
    @Test
    public final void testFillColumns02() {
        ma.fillColumns(2, "peter");
        assertEquals(2, ma.getNumberOfRows());
    }

    /**
     * Test method for
     * {@link net.sf.kerner.commons.collection.table.impl.MutableTableImpl#fillColumns(int, java.lang.Object)}
     * .
     */
    @Test
    public final void testFillColumns03() {
        ma.clear();
        ma.fillColumns(2, "peter");
        assertEquals(2, ma.getNumberOfRows());
    }

    /**
     * Test method for
     * {@link net.sf.kerner.commons.collection.table.impl.MutableTableImpl#fillColumns(int, java.lang.Object)}
     * .
     */
    @SuppressWarnings("serial")
    @Test
    public final void testFillColumns04() {
        ma = new MutableTableImpl<String>();
        ma.addRow(new RowImpl<String>() {
            {
                add("eins");
                add("zwei");
            }
        });
        ma.fillColumns(3, "peter");
        assertEquals(3, ma.getNumberOfRows());
    }

    /**
     * Test method for
     * {@link net.sf.kerner.commons.collection.table.impl.MutableTableImpl#fillColumns(int, java.lang.Object)}
     * .
     */
    @SuppressWarnings("serial")
    @Test
    public final void testFillColumns05() {
        ma = new MutableTableImpl<String>();
        ma.addRow(new RowImpl<String>() {
            {
                add("eins");
                add("zwei");
            }
        });
        ma.fillColumns(3, "peter");
        assertEquals(2, ma.getNumberOfColumns());
    }

    /**
     * Test method for
     * {@link net.sf.kerner.commons.collection.table.impl.MutableTableImpl#fill(int, java.lang.Object)}
     * .
     */
    @Test
    public final void testFill() {
        ma.fill(3, "peter");
        // log.debug(ma);
    }

    /**
     * Test method for
     * {@link net.sf.kerner.commons.collection.table.impl.MutableTableImpl#fillAndSet(int, int, Object, Object)}
     * .
     */
    @Test
    public final void testFillAndSet() {
        ma = new MutableTableImpl<String>();
        ma.fillAndSet(0, 0, null, "hans");
        assertEquals(1, ma.getNumberOfRows());
    }

    /**
     * Test method for
     * {@link net.sf.kerner.commons.collection.table.impl.MutableTableImpl#fillAndSet(int, int, Object, Object)}
     * .
     */
    @Test
    public final void testFillAndSet01() {
        ma = new MutableTableImpl<String>();
        ma.fillAndSet(0, 0, null, "hans");
        assertEquals(1, ma.getNumberOfColumns());
    }

    /**
     * Test method for
     * {@link net.sf.kerner.commons.collection.table.impl.MutableTableImpl#fillAndSet(int, int, Object, Object)}
     * .
     */
    @Test
    public final void testFillAndSet02() {
        ma = new MutableTableImpl<String>();
        ma.fillAndSet(1, 1, null, "hans");
        assertEquals(2, ma.getNumberOfColumns());
    }

    /**
     * Test method for
     * {@link net.sf.kerner.commons.collection.table.impl.MutableTableImpl#fillAndSet(int, int, Object, Object)}
     * .
     */
    @Test
    public final void testFillAndSet03() {
        ma = new MutableTableImpl<String>();
        ma.fillAndSet(1, 1, null, "hans");
        assertEquals(2, ma.getNumberOfRows());
    }

    /**
     * Test method for
     * {@link net.sf.kerner.commons.collection.table.impl.MutableTableImpl#fillAndSet(int, int, Object, Object)}
     * .
     */
    @Test
    public final void testFillAndSet04() {
        ma = new MutableTableImpl<String>();
        ma.fillAndSet(1, 2, null, "hans");
        assertEquals(2, ma.getNumberOfRows());
    }

    /**
     * Test method for
     * {@link net.sf.kerner.commons.collection.table.impl.MutableTableImpl#fillAndSet(int, int, Object, Object)}
     * .
     */
    @Test
    public final void testFillAndSet05() {
        ma = new MutableTableImpl<String>();
        ma.fillAndSet(1, 2, null, "hans");
        assertEquals(3, ma.getNumberOfColumns());
    }

}
