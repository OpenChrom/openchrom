/*******************************************************************************
 * Copyright (c) 2008, 2010 Philip (eselmeister) Wenig.
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
package net.openchrom.chromatogram.ms.converter.supplier.cdf.model;

import java.io.File;
import java.util.Date;
import java.util.StringTokenizer;

import net.openchrom.chromatogram.ms.model.core.AbstractChromatogram;

public class CDFChromatogram extends AbstractChromatogram implements ICDFChromatogram {

	private Date dateOfExperiment = new Date();

	public CDFChromatogram() {

		super();
	}

	// ---------------------------------------------ICDFChromatogram
	@Override
	public Date getDateOfExperiment() {

		return dateOfExperiment;
	}

	@Override
	public void setDateOfExperiment(Date dateOfExperiment) {

		this.dateOfExperiment = dateOfExperiment;
	}

	// ---------------------------------------------ICDFChromatogram
	// ---------------------------------------------IChromatogram
	@Override
	public String getName() {

		String name = "CDFChromatogram";
		File file = getFile();
		if(file != null) {
			String fileName = getFile().getName();
			if(fileName != "" && fileName != null) {
				StringTokenizer tokenizer = new StringTokenizer(fileName, ".");
				if(tokenizer.hasMoreTokens()) {
					name = tokenizer.nextToken();
				}
			}
		}
		return name;
		/*
		 * StringTokenizer tokenizer = new
		 * StringTokenizer(getFile().getAbsolutePath(), File.separator); int
		 * element = tokenizer.countTokens() - 1; // Get the CDFChromatogramFile
		 * directory. for(int i = 1; i < element; i++) {
		 * if(tokenizer.hasMoreElements()) { tokenizer.nextToken(); } }
		 * if(tokenizer.hasMoreElements()) { name = tokenizer.nextToken(); } //
		 * Shorten the directory. ".D" is not needed. if(name != null) { name =
		 * name.substring(0, name.length() - 2); } return name;
		 */
	}
	// ---------------------------------------------IChromatogram
}
