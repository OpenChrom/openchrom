/*******************************************************************************
 * Copyright (c) 2013, 2026 Lablicate GmbH.
 *
 * This program and the accompanying materials are made
 * available under the terms of the Eclipse Public License 2.0
 * which is available at https://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
 * 
 * Contributors:
 * Philip Wenig - initial API and implementation
 *******************************************************************************/
package net.openchrom.msd.converter.supplier.cdf.io.support;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.text.ParseException;
import java.util.Date;
import java.util.TimeZone;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.TestInstance.Lifecycle;
import org.junit.jupiter.api.util.DefaultTimeZone;

@DefaultTimeZone("CET")
@TestInstance(Lifecycle.PER_CLASS)
public class DateSupport_1_Test {

	@Test
	public void testGetActualDate_1() {

		// 12 Nov 2008 7:41 ! CET > +0100
		Date date = new Date(1226472095160l);
		assertEquals("20081112074135+0100", DateSupport.getDate(date), "12 Nov 08 7:41");
	}

	@Test
	public void testGetDate_1() throws ParseException {

		// 12 Jan 2006 18:47 ! CET > +0100
		String agilentDate = "20060112184700+0100";
		Date test = new Date(1137088020000l);
		Date date = DateSupport.getDate(agilentDate);
		assertEquals(test, date, agilentDate);
	}

	@Test
	public void testGetDate_2() throws ParseException {

		// 18 Feb 2006 16:18 ! CET > +0100
		String agilentDate = "20060218161800+0100";
		Date test = new Date(1140275880000l);
		Date date = DateSupport.getDate(agilentDate);
		assertEquals(test, date, agilentDate);
	}

	@Test
	public void testGetDate_3() throws ParseException {

		// 3 Mar 2006 14:26 ! CET > +0100
		String agilentDate = "20060303142600+0100";
		Date test = new Date(1141392360000l);
		Date date = DateSupport.getDate(agilentDate);
		assertEquals(test, date, agilentDate);
	}

	@Test
	public void testGetDate_4() throws ParseException {

		// 17 Apr 2006 13:40 ! CEST > +0200
		String agilentDate = "20060417134000+0200";
		TimeZone.setDefault(TimeZone.getTimeZone("CEST"));
		Date test = new Date(1145274000000l);
		Date date = DateSupport.getDate(agilentDate);
		assertEquals(test, date, agilentDate);
	}

	@Test
	public void testGetDate_5() throws ParseException {

		// 19 May 2006 9:57 ! CEST > +0200
		String agilentDate = "20060519095700+0200";
		TimeZone.setDefault(TimeZone.getTimeZone("CEST"));
		Date test = new Date(1148025420000l);
		Date date = DateSupport.getDate(agilentDate);
		assertEquals(test, date, agilentDate);
	}

	@Test
	public void testGetDate_6() throws ParseException {

		// 16 Jun 2006 21:11 ! CEST > +0200
		String agilentDate = "20060616211100+0200";
		TimeZone.setDefault(TimeZone.getTimeZone("CEST"));
		Date test = new Date(1150485060000l);
		Date date = DateSupport.getDate(agilentDate);
		assertEquals(test, date, agilentDate);
	}

	@Test
	public void testGetDate_7() throws ParseException {

		// 28 Jul 2006 18:43 ! CEST > +0200
		String agilentDate = "20060728184300+0200";
		TimeZone.setDefault(TimeZone.getTimeZone("CEST"));
		Date test = new Date(1154104980000l);
		Date date = DateSupport.getDate(agilentDate);
		assertEquals(test, date, agilentDate);
	}

	@Test
	public void testGetDate_8() throws ParseException {

		// 22 Aug 2006 16:35 ! CEST > +0200
		String agilentDate = "20060822163500+0200";
		TimeZone.setDefault(TimeZone.getTimeZone("CEST"));
		Date test = new Date(1156257300000l);
		Date date = DateSupport.getDate(agilentDate);
		assertEquals(test, date, agilentDate);
	}

	@Test
	public void testGetDate_9() throws ParseException {

		// 1 Sep 2006 13:56 ! CEST > +0200
		String agilentDate = "20060901135600+0200";
		TimeZone.setDefault(TimeZone.getTimeZone("CEST"));
		Date test = new Date(1157111760000l);
		Date date = DateSupport.getDate(agilentDate);
		assertEquals(test, date, agilentDate);
	}

	@Test
	public void testGetDate_10() throws ParseException {

		// 17 Oct 2006 1:42 ! CEST > +0200
		String agilentDate = "20061017014200+0200";
		TimeZone.setDefault(TimeZone.getTimeZone("CEST"));
		Date test = new Date(1161042120000l);
		Date date = DateSupport.getDate(agilentDate);
		assertEquals(test, date, agilentDate);
	}

	@Test
	public void testGetDate_11() throws ParseException {

		// 9 Nov 2008 20:34 ! CET > +0100
		String agilentDate = "20081109203400+0100";
		Date test = new Date(1226259240000l);
		Date date = DateSupport.getDate(agilentDate);
		assertEquals(test, date, agilentDate);
	}

	@Test
	public void testGetDate_12() throws ParseException {

		// 22 Dec 2007 6:51 ! CET > +0100
		String agilentDate = "20071222065100+0100";
		Date test = new Date(1198302660000l);
		Date date = DateSupport.getDate(agilentDate);
		assertEquals(test, date, agilentDate);
	}
}
