/*******************************************************************************
 * Copyright (c) 2015, 2016 Lablicate GmbH.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 * Dr. Philip Wenig - initial API and implementation
 *******************************************************************************/
package net.openchrom.msd.converter.supplier.mgf.converter.internal.io;

import java.io.File;

public class SpecificationValidator {

	public static File validateSpecification(File file) {

		if(file == null) {
			return null;
		}
		/*
		 * Check the extension.
		 */
		File validFile;
		String path = file.getAbsolutePath().toUpperCase();
		if(file.isDirectory()) {
			validFile = new File(file.getAbsolutePath() + File.separator + "MASSSPECTRA.MGF");
		} else {
			if(path.endsWith(".")) {
				validFile = new File(file.getAbsolutePath() + "mgf");
			} else if(!path.endsWith(".mgf")) {
				if(!path.endsWith(".MGF")) {
					validFile = new File(file.getAbsolutePath() + ".mgf");
				} else {
					validFile = file;
				}
			} else {
				validFile = file;
			}
		}
		return validFile;
	}

	/**
	 * Use only static methods.
	 */
	private SpecificationValidator() {
	}
}
