/*******************************************************************************
 * Copyright (c) 2016, 2017 Lablicate GmbH.
 *
 * All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0 which accompanies this distribution,
 * and is available at http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors:
 * Dr. Philip Wenig - initial API and implementation
 *******************************************************************************/
package net.openchrom.msd.converter.supplier.cdf.converter;

import java.io.File;

import org.eclipse.chemclipse.converter.core.AbstractMagicNumberMatcher;
import org.eclipse.chemclipse.converter.core.IMagicNumberMatcher;
import org.eclipse.chemclipse.model.core.IChromatogramOverview;
import org.eclipse.chemclipse.msd.converter.io.IChromatogramMSDReader;
import org.eclipse.core.runtime.NullProgressMonitor;

import net.openchrom.msd.converter.supplier.cdf.internal.converter.SpecificationValidator;
import net.openchrom.msd.converter.supplier.cdf.io.ChromatogramReader;

public class MagicNumberMatcher extends AbstractMagicNumberMatcher implements IMagicNumberMatcher {

	@Override
	public boolean checkFileFormat(File file) {

		boolean isValidFormat = false;
		try {
			String fileName = file.getName().toLowerCase();
			if(fileName.endsWith("cdf")) {
				file = SpecificationValidator.validateSpecification(file);
				IChromatogramMSDReader reader = new ChromatogramReader();
				IChromatogramOverview chromatogramOverview = reader.readOverview(file, new NullProgressMonitor());
				if(chromatogramOverview != null) {
					isValidFormat = true;
				}
			}
		} catch(Exception e) {
			// Print no exception.
		}
		return isValidFormat;
	}
}
