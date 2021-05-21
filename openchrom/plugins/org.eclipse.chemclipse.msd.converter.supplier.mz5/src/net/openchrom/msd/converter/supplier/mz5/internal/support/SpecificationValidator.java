/*******************************************************************************
 * Copyright (c) 2014, 2018 Lablicate GmbH.
 * 
 * All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0 which accompanies this distribution,
 * and is available at http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors:
 * Dr. Philip Wenig - initial API and implementation
 *******************************************************************************/
package net.openchrom.msd.converter.supplier.mz5.internal.support;

import java.io.File;

public class SpecificationValidator {

	/**
	 * Use only static methods.
	 */
	private SpecificationValidator() {
	}

	public static File validateSpecification(File file) {

		if(file == null) {
			return null;
		}
		/*
		 * Validate
		 */
		File validFile;
		String path = file.getAbsolutePath().toLowerCase();
		if(file.isDirectory()) {
			validFile = new File(file.getAbsolutePath() + File.separator + "chromatogram.mz5");
		} else {
			if(path.endsWith(".")) {
				validFile = new File(file.getAbsolutePath() + "mz5");
			} else if(!path.endsWith(".mz5")) {
				validFile = new File(file.getAbsolutePath() + ".mz5");
			} else {
				validFile = file;
			}
		}
		return validFile;
	}
}
