/*******************************************************************************
 * Copyright (c) 2008, 2011 Philip (eselmeister) Wenig.
 * 
 * This library is free
 * software; you can redistribute it and/or modify it under the terms of the GNU
 * Lesser General Public License as published by the Free Software Foundation;
 * either version 2.1 of the License, or (at your option) any later version.
 * This library is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS
 * FOR A PARTICULAR PURPOSE. See the GNU Lesser General Public License for more
 * details. You should have received a copy of the GNU Lesser General Public
 * License along with this library; if not, write to the Free Software
 * Foundation, Inc., 59 Temple Place, Suite 330, Boston MA 02111-1307, USA
 * 
 * 
 * Contributors: Philip (eselmeister) Wenig - initial API and implementation
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
