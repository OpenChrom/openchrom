/*******************************************************************************
 * Copyright (c) 2013, 2017 Lablicate GmbH.
 * 
 * All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0 which accompanies this distribution,
 * and is available at http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors:
 * Dr. Philip Wenig - initial API and implementation
 *******************************************************************************/
package net.openchrom.msd.converter.supplier.cdf.io.support;

import java.text.ParseException;
import java.util.Date;

import net.openchrom.msd.converter.supplier.cdf.io.support.DateSupport;

import junit.framework.TestCase;

public class DateSupportTest_1 extends TestCase {

	@Override
	protected void setUp() throws Exception {

		super.setUp();
	}

	@Override
	protected void tearDown() throws Exception {

		super.tearDown();
	}

	public void testGetActualDate_1() {

		// 12 Nov 2008 7:41 ! CET > +0100
		Date date = new Date(1226472095160l);
		assertEquals("12 Nov 08 7:41", "20081112074135+0100", DateSupport.getDate(date));
	}

	public void testGetDate_1() {

		try {
			// 12 Jan 2006 18:47 ! CET > +0100
			String agilentDate = "20060112184700+0100";
			Date test = new Date(1137088020000l);
			Date date = DateSupport.getDate(agilentDate);
			assertTrue(agilentDate, date.equals(test));
		} catch(ParseException e) {
			assertTrue("ParseException", false);
		}
	}

	public void testGetDate_2() {

		try {
			// 18 Feb 2006 16:18 ! CET > +0100
			String agilentDate = "20060218161800+0100";
			Date test = new Date(1140275880000l);
			Date date = DateSupport.getDate(agilentDate);
			assertTrue(agilentDate, date.equals(test));
		} catch(ParseException e) {
			assertTrue("ParseException", false);
		}
	}

	public void testGetDate_3() {

		try {
			// 3 Mar 2006 14:26 ! CET > +0100
			String agilentDate = "20060303142600+0100";
			Date test = new Date(1141392360000l);
			Date date = DateSupport.getDate(agilentDate);
			assertTrue(agilentDate, date.equals(test));
		} catch(ParseException e) {
			assertTrue("ParseException", false);
		}
	}

	public void testGetDate_4() {

		try {
			// 17 Apr 2006 13:40 ! CEST > +0200
			String agilentDate = "20060417134000+0200";
			Date test = new Date(1145274000000l);
			Date date = DateSupport.getDate(agilentDate);
			assertTrue(agilentDate, date.equals(test));
		} catch(ParseException e) {
			assertTrue("ParseException", false);
		}
	}

	public void testGetDate_5() {

		try {
			// 19 May 2006 9:57 ! CEST > +0200
			String agilentDate = "20060519095700+0200";
			Date test = new Date(1148025420000l);
			Date date = DateSupport.getDate(agilentDate);
			assertTrue(agilentDate, date.equals(test));
		} catch(ParseException e) {
			assertTrue("ParseException", false);
		}
	}

	public void testGetDate_6() {

		try {
			// 16 Jun 2006 21:11 ! CEST > +0200
			String agilentDate = "20060616211100+0200";
			Date test = new Date(1150485060000l);
			Date date = DateSupport.getDate(agilentDate);
			assertTrue(agilentDate, date.equals(test));
		} catch(ParseException e) {
			assertTrue("ParseException", false);
		}
	}

	public void testGetDate_7() {

		try {
			// 28 Jul 2006 18:43 ! CEST > +0200
			String agilentDate = "20060728184300+0200";
			Date test = new Date(1154104980000l);
			Date date = DateSupport.getDate(agilentDate);
			assertTrue(agilentDate, date.equals(test));
		} catch(ParseException e) {
			assertTrue("ParseException", false);
		}
	}

	public void testGetDate_8() {

		try {
			// 22 Aug 2006 16:35 ! CEST > +0200
			String agilentDate = "20060822163500+0200";
			Date test = new Date(1156257300000l);
			Date date = DateSupport.getDate(agilentDate);
			assertTrue(agilentDate, date.equals(test));
		} catch(ParseException e) {
			assertTrue("ParseException", false);
		}
	}

	public void testGetDate_9() {

		try {
			// 1 Sep 2006 13:56 ! CEST > +0200
			String agilentDate = "20060901135600+0200";
			Date test = new Date(1157111760000l);
			Date date = DateSupport.getDate(agilentDate);
			assertTrue(agilentDate, date.equals(test));
		} catch(ParseException e) {
			assertTrue("ParseException", false);
		}
	}

	public void testGetDate_10() {

		try {
			// 17 Oct 2006 1:42 ! CEST > +0200
			String agilentDate = "20061017014200+0200";
			Date test = new Date(1161042120000l);
			Date date = DateSupport.getDate(agilentDate);
			assertTrue(agilentDate, date.equals(test));
		} catch(ParseException e) {
			assertTrue("ParseException", false);
		}
	}

	public void testGetDate_11() {

		try {
			// 9 Nov 2008 20:34 ! CET > +0100
			String agilentDate = "20081109203400+0100";
			Date test = new Date(1226259240000l);
			Date date = DateSupport.getDate(agilentDate);
			assertTrue(agilentDate, date.equals(test));
		} catch(ParseException e) {
			assertTrue("ParseException", false);
		}
	}

	public void testGetDate_12() {

		try {
			// 22 Dec 2007 6:51 ! CET > +0100
			String agilentDate = "20071222065100+0100";
			Date test = new Date(1198302660000l);
			Date date = DateSupport.getDate(agilentDate);
			assertTrue(agilentDate, date.equals(test));
		} catch(ParseException e) {
			assertTrue("ParseException", false);
		}
	}
}
