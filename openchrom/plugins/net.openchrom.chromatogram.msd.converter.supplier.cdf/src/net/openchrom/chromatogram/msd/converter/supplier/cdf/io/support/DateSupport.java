/*******************************************************************************
 * Copyright (c) 2013 Dr. Philip Wenig.
 * 
 * All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0 which accompanies this distribution,
 * and is available at http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors:
 * Dr. Philip Wenig - initial API and implementation
 *******************************************************************************/
package net.openchrom.chromatogram.msd.converter.supplier.cdf.io.support;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

/**
 * Converts string and date to a netCDF specific format.<br/>
 * 20080630140216+0200<br/>
 * 20080312102600+0200<br/>
 * 20081007124044+0200<br/>
 * 20080903163600+0200<br/>
 * 20080813135549+0200<br/>
 * 20080312222400+0200<br/>
 * 20080813135552+0200<br/>
 * 20080313003000+0200<br/>
 * 20080908131858+0200<br/>
 * 20080329065100+0200<br/>
 * 
 * @author eselmeister
 */
public class DateSupport {

	private static final String pattern = "yyyyMMddHHmmssZ";

	/**
	 * Returns an netCDF specific string representation of the actual date.<br/>
	 * e.g: 20080630140216+0200
	 * 
	 * @return String
	 */
	public static String getActualDate() {

		Date now = new Date();
		return getDate(now);
	}

	/**
	 * Returns an netCDF specific string representation of the given date.
	 * 
	 * @param date
	 * @return String
	 */
	public static String getDate(Date date) {

		SimpleDateFormat dateFormat = new SimpleDateFormat(pattern, Locale.ENGLISH);
		return dateFormat.format(date);
	}

	/**
	 * Parses a cdf chromatogram date string an gives back it's date
	 * representation.
	 * 
	 * @param date
	 * @return Date
	 * @throws ParseException
	 */
	public static Date getDate(String date) throws ParseException {

		SimpleDateFormat dateFormat = new SimpleDateFormat(pattern, Locale.ENGLISH);
		return dateFormat.parse(date);
	}
}
