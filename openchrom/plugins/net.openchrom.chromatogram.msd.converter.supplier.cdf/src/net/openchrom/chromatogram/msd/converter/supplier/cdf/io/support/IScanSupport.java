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

public interface IScanSupport {

	/**
	 * Returns the scan index for the given scan.
	 * 
	 * @param scan
	 * @return int
	 */
	int getScanIndex(int scan);

	/**
	 * Returns the point count (number of mass fragments) for the given scan.
	 * 
	 * @param scan
	 * @return int
	 */
	int getPointCount(int scan);

	/**
	 * Returns the min mass fragment of the given scan.
	 * 
	 * @param scan
	 * @return float
	 */
	float getMinMassFragment(int scan);

	/**
	 * Returns the max mass fragment of the given scan.
	 * 
	 * @param scan
	 * @return float
	 */
	float getMaxMassFragment(int scan);
}
