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

import net.openchrom.wsd.converter.supplier.abif.internal.support.ChromatogramArrayReader;
import net.openchrom.wsd.converter.supplier.abif.internal.support.IChromatogramArrayReader;
import net.openchrom.wsd.converter.supplier.abif.internal.support.SpecificationValidator;

public class MagicNumberMatcher extends AbstractMagicNumberMatcher implements IMagicNumberMatcher {

	@Override
	public boolean checkFileFormat(File file) {

		boolean isValidFormat = false;
		try {
			file = SpecificationValidator.validateSpecification(file);
			IChromatogramArrayReader in = new ChromatogramArrayReader(file);
			in.resetPosition();
			String fileSignature = in.readBytesAsString(4);
			// Magic byte abbreviation stands for Applied Biosystems, Inc. Format.
			if(fileSignature.equals("ABIF")) {
				isValidFormat = true;
			}
		} catch(Exception e) {
			// Print no exception.
		}
		return isValidFormat;
	}
}
