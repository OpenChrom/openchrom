/*******************************************************************************
 * Copyright (c) 2015 Lablicate UG (haftungsbeschränkt).
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
package net.sf.kerner.utils.async;

import static org.junit.Assert.assertEquals;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

/**
 * @author <a href="mailto:alex.kerner.24@googlemail.com">Alexander Kerner</a>
 * @version 2011-05-18
 */
public class TestAbstractAsyncTask {

	private AbstractAsyncTask<String, String> call;
	@SuppressWarnings("unused")
	private volatile String res;
	private volatile Exception failure;

	@BeforeClass
	public static void setUpBeforeClass() throws Exception {

	}

	@AfterClass
	public static void tearDownAfterClass() throws Exception {

	}

	@Before
	public void setUp() throws Exception {

		// success = false;
		failure = null;
		res = null;
		call = new AbstractAsyncTask<String, String>() {

			public String run(String value) throws Exception {

				return "gut" + value;
			}

			public void doOnSucess(String result) {

				res = result;
			}

			public void doOnFailure(Exception t) {

				failure = t;
			}

			public void doBefore() {

				// TODO Auto-generated method stub
			}
		};
	}

	@After
	public void tearDown() throws Exception {

	}

	/**
	 * Test method for {@link net.sf.kerner.utils.async.AbstractAsyncTask#AbstractAsyncCallBack()} .
	 */
	@Test
	public final void testAbstractAsyncCallBack() {

		new AbstractAsyncTask<String, String>() {

			public String run(String value) throws Exception {

				// TODO Auto-generated method stub
				return null;
			}

			public void doOnSucess(String result) {

				// TODO Auto-generated method stub
			}

			public void doOnFailure(Exception t) {

				// TODO Auto-generated method stub
			}

			public void doBefore() {

				// TODO Auto-generated method stub
			}
		};
	}

	/**
	 * Test method for {@link net.sf.kerner.utils.async.AbstractAsyncTask#AbstractAsyncCallBack(java.util.concurrent.ExecutorService)} .
	 */
	@Test
	public final void testAbstractAsyncCallBackExecutorService() {

		new AbstractAsyncTask<String, String>() {

			public String run(String value) throws Exception {

				// TODO Auto-generated method stub
				return null;
			}

			public void doOnSucess(String result) {

				// TODO Auto-generated method stub
			}

			public void doOnFailure(Exception t) {

				// TODO Auto-generated method stub
			}

			public void doBefore() {

				// TODO Auto-generated method stub
			}
		};
	}

	/**
	 * Test method for {@link net.sf.kerner.utils.async.AbstractAsyncTask#execute(java.lang.Object)} .
	 * 
	 * @throws InterruptedException
	 */
	@Test
	public final void testExecute01() throws InterruptedException {

		call = new AbstractAsyncTask<String, String>() {

			public String run(String value) throws Exception {

				throw new IllegalAccessException();
			}

			public void doOnSucess(String result) {

				// TODO Auto-generated method stub
			}

			public void doOnFailure(Exception t) {

				failure = t;
			}

			public void doBefore() {

				// TODO Auto-generated method stub
			}
		};
		call.execute("hallo");
		assertEquals(IllegalAccessException.class, failure.getClass());
	}
}
