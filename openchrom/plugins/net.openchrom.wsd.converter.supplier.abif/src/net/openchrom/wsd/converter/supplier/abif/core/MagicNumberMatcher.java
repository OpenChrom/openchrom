/*******************************************************************************
 * Copyright (c) 2016 Matthias Mailänder, Dr. Philip Wenig.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors:
 * Matthias Mailänder - initial API and implementation
 * Dr. Philip Wenig - initial API and implementation
 *******************************************************************************/
package net.openchrom.wsd.converter.supplier.abif.core;

import java.io.File;

import org.eclipse.chemclipse.converter.core.AbstractMagicNumberMatcher;
import org.eclipse.chemclipse.converter.core.IMagicNumberMatcher;
import org.eclipse.chemclipse.model.core.IChromatogramOverview;
import org.eclipse.chemclipse.wsd.converter.io.IChromatogramWSDReader;
import org.eclipse.core.runtime.NullProgressMonitor;

import net.openchrom.wsd.converter.supplier.abif.internal.support.SpecificationValidator;
import net.openchrom.wsd.converter.supplier.abif.io.ChromatogramReader;

public class MagicNumberMatcher extends AbstractMagicNumberMatcher implements IMagicNumberMatcher {

	@Override
	public boolean checkFileFormat(File file) {

		boolean isValidFormat = false;
		try {
			file = SpecificationValidator.validateSpecification(file);
			IChromatogramWSDReader reader = new ChromatogramReader();
			IChromatogramOverview chromatogramOverview = reader.readOverview(file, new NullProgressMonitor());
			if(chromatogramOverview != null) {
				isValidFormat = true;
			}
		} catch(Exception e) {
			// Print no exception.
		}
		return isValidFormat;
	}
}
