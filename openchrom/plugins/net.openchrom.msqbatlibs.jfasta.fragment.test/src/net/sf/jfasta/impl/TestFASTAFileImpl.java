/*******************************************************************************
 * Copyright (c) 2015, 2016 Lablicate GmbH.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 * Dr. Alexander Kerner - initial API and implementation
 *******************************************************************************/
/**
 * 
 */
package net.sf.jfasta.impl;

import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.util.Arrays;

import net.sf.jfasta.FASTAElement;
import net.sf.jfasta.FASTAFile;
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
 * @version 2010-11-06
 * 
 */
public class TestFASTAFileImpl {

	@BeforeClass
	public static void setUpBeforeClass() throws Exception {

	}

	@AfterClass
	public static void tearDownAfterClass() throws Exception {

	}

	private FASTAElement el1, el2;
	private FASTAFileImpl file1;
	private FASTAFileImpl file2;

	@Before
	public void setUp() throws Exception {

	}

	@After
	public void tearDown() throws Exception {

	}

	/**
	 * Test method for {@link net.sf.jfasta.impl.FASTAFileImpl#add(net.sf.jfasta.FASTAElement)}.
	 */
	@Test
	@Ignore("Simple delegate")
	public final void testAdd() {

		fail("Not yet implemented"); // TODO
	}

	/**
	 * Test method for {@link net.sf.jfasta.impl.FASTAFileImpl#addAll(java.util.Collection)}.
	 */
	@Test
	@Ignore("Simple delegate")
	public final void testAddAll() {

		fail("Not yet implemented"); // TODO
	}

	/**
	 * Test method for {@link net.sf.jfasta.impl.FASTAFileImpl#clear()}.
	 */
	@Test
	@Ignore("Simple delegate")
	public final void testClear() {

		el1 = new FASTAElementImpl("header", "seq");
		el2 = new FASTAElementImpl("header2", "seqq");
		file1 = new FASTAFileImpl(Arrays.asList(el2, el2));
		file1.clear();
		assertTrue(file1.isEmpty());
	}

	/**
	 * Test method for {@link net.sf.jfasta.impl.FASTAFileImpl#contains(java.lang.Object)}.
	 */
	@Test
	@Ignore("Simple delegate")
	public final void testContains() {

		fail("Not yet implemented"); // TODO
	}

	/**
	 * Test method for {@link net.sf.jfasta.impl.FASTAFileImpl#containsAll(java.util.Collection)} .
	 */
	@Test
	@Ignore("Simple delegate")
	public final void testContainsAll() {

		fail("Not yet implemented"); // TODO
	}

	/**
	 * Test method for {@link net.sf.jfasta.impl.FASTAFileImpl#equals(java.lang.Object)}.
	 */
	@Test
	public final void testEqualsObject() {

		el1 = new FASTAElementImpl("header", "seq");
		el2 = new FASTAElementImpl("header", "seq");
		file1 = new FASTAFileImpl(el1);
		file2 = new FASTAFileImpl(el2);
		assertEquals(file1, file2);
	}

	/**
	 * Test method for {@link net.sf.jfasta.impl.FASTAFileImpl#FASTAFileImpl()}.
	 */
	@Test
	@Ignore
	public final void testFASTAFileImpl() {

		fail("Not yet implemented"); // TODO
	}

	/**
	 * Test method for {@link net.sf.jfasta.impl.FASTAFileImpl#FASTAFileImpl(java.util.Collection)} .
	 */
	@Test
	@Ignore
	public final void testFASTAFileImplCollectionOfQextendsFASTAElement() {

		fail("Not yet implemented"); // TODO
	}

	/**
	 * Test method for {@link net.sf.jfasta.impl.FASTAFileImpl#getElementByHeader(java.lang.String)} .
	 */
	@Test
	public final void testGetElementByHeader() {

		el1 = new FASTAElementImpl("header", "seq");
		file1 = new FASTAFileImpl(el1);
		assertEquals(el1, file1.getElementByHeader("header"));
	}

	/**
	 * Test method for {@link net.sf.jfasta.impl.FASTAFileImpl#getLargestElement()}.
	 */
	@Test
	public final void testGetLargestElement() {

		el1 = new FASTAElementImpl("header", "seq");
		el2 = new FASTAElementImpl("header2", "seqq");
		file1 = new FASTAFileImpl(Arrays.asList(el2, el2));
		assertEquals(el2, file1.getLargestElement());
	}

	/**
	 * Test method for {@link net.sf.jfasta.impl.FASTAFileImpl#getLineLength()}.
	 */
	@Test
	public final void testGetLineLength() {

		assertEquals(FASTAFile.DEFAULT_LINE_LENGTH, new FASTAFileImpl().getLineLength());
	}

	/**
	 * Test method for {@link net.sf.jfasta.impl.FASTAFileImpl#hasElementByHeader(java.lang.String)} .
	 */
	@Test
	public final void testHasElementByHeader() {

		el1 = new FASTAElementImpl("header", "seq");
		el2 = new FASTAElementImpl("header2", "seqq");
		file1 = new FASTAFileImpl(Arrays.asList(el1, el2));
		assertTrue(file1.hasElementByHeader("header"));
		assertTrue(file1.hasElementByHeader("header2"));
	}

	/**
	 * Test method for {@link net.sf.jfasta.impl.FASTAFileImpl#hashCode()}.
	 */
	@Test
	public final void testHashCode() {

		el1 = new FASTAElementImpl("header", "seq");
		el2 = new FASTAElementImpl("header", "seq");
		file1 = new FASTAFileImpl(el1);
		file2 = new FASTAFileImpl(el2);
		assertEquals(file1.hashCode(), file2.hashCode());
	}

	/**
	 * Test method for {@link net.sf.jfasta.impl.FASTAFileImpl#isEmpty()}.
	 */
	@Test
	@Ignore("Simple delegate")
	public final void testIsEmpty() {

		fail("Not yet implemented"); // TODO
	}

	/**
	 * Test method for {@link net.sf.jfasta.impl.FASTAFileImpl#iterator()}.
	 */
	@Test
	@Ignore
	public final void testIterator() {

		fail("Not yet implemented"); // TODO
	}

	/**
	 * Test method for {@link net.sf.jfasta.impl.FASTAFileImpl#remove(java.lang.Object)}.
	 */
	@Test
	@Ignore("Simple delegate")
	public final void testRemove() {

		fail("Not yet implemented"); // TODO
	}

	/**
	 * Test method for {@link net.sf.jfasta.impl.FASTAFileImpl#removeAll(java.util.Collection)}.
	 */
	@Test
	@Ignore("Simple delegate")
	public final void testRemoveAll() {

		fail("Not yet implemented"); // TODO
	}

	/**
	 * Test method for {@link net.sf.jfasta.impl.FASTAFileImpl#retainAll(java.util.Collection)}.
	 */
	@Test
	@Ignore("Simple delegate")
	public final void testRetainAll() {

		fail("Not yet implemented"); // TODO
	}

	/**
	 * Test method for {@link net.sf.jfasta.impl.FASTAFileImpl#setLineLength(int)}.
	 */
	@Test
	public final void testSetLineLength() {

		file1 = new FASTAFileImpl();
		file1.setLineLength(50);
		assertEquals(50, file1.getLineLength());
	}

	/**
	 * Test method for {@link net.sf.jfasta.impl.FASTAFileImpl#size()}.
	 */
	@Test
	public final void testSize() {

		el1 = new FASTAElementImpl("header", "seq");
		file1 = new FASTAFileImpl(el1);
		assertEquals(1, file1.size());
	}

	/**
	 * Test method for {@link net.sf.jfasta.impl.FASTAFileImpl#size()}.
	 */
	@Test
	public final void testSize01() {

		file1 = new FASTAFileImpl();
		assertEquals(0, file1.size());
	}

	/**
	 * Test method for {@link net.sf.jfasta.impl.FASTAFileImpl#toArray()}.
	 */
	@Test
	public final void testToArray() {

		el1 = new FASTAElementImpl("header", "seq");
		el2 = new FASTAElementImpl("header2", "seqq");
		file1 = new FASTAFileImpl(Arrays.asList(el1, el2));
		// System.out.println(file1);
		// System.out.println(Arrays.asList(file1.toArray()));
		assertArrayEquals(new Object[]{el1, el2}, file1.toArray());
	}

	/**
	 * Test method for {@link net.sf.jfasta.impl.FASTAFileImpl#toArray(T[])}.
	 */
	@Test
	@Ignore
	public final void testToArrayTArray() {

	}

	/**
	 * Test method for {@link net.sf.jfasta.impl.FASTAFileImpl#toString()}.
	 */
	@Test
	public final void testToString() {

		el1 = new FASTAElementImpl("header", "seq");
		el2 = new FASTAElementImpl("header2", "seqq");
		file1 = new FASTAFileImpl(Arrays.asList(el1, el2));
		assertEquals(">header" + UtilIO.NEW_LINE_STRING + "seq" + UtilIO.NEW_LINE_STRING + ">header2" + UtilIO.NEW_LINE_STRING + "seqq", file1.toString());
	}

	/**
	 * Test method for {@link net.sf.jfasta.impl.FASTAFileImpl#toString(boolean)}.
	 */
	@Test
	public final void testToStringBoolean() {

		el1 = new FASTAElementImpl("header", "seq");
		el2 = new FASTAElementImpl("header2", "seqq");
		file1 = new FASTAFileImpl(Arrays.asList(el1, el2));
		assertEquals(">header" + UtilIO.NEW_LINE_STRING + "seq" + UtilIO.NEW_LINE_STRING + ">header2" + UtilIO.NEW_LINE_STRING + "seqq", file1.toString());
	}
}
