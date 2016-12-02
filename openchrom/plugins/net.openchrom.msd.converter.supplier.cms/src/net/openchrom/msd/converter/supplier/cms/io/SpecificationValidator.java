/*******************************************************************************
 * Copyright (c) 2016 Walter Whitlock, Philip Wenig.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors:
 * Walter Whitlock - initial API and implementation
 * Philip Wenig - initial API and implementation
 *******************************************************************************/
package net.openchrom.msd.converter.supplier.cms.io;

import java.io.File;

public class SpecificationValidator {

	private static final String FILE_EXTENSION = "CMS";

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
		 * Check the extension.
		 */
		File validFile;
		String path = file.getAbsolutePath().toUpperCase();
		if(file.isDirectory()) {
			validFile = new File(file.getAbsolutePath() + File.separator + "MASSSPECTRA." + FILE_EXTENSION);
		} else {
			if(path.endsWith(".")) {
				validFile = new File(file.getAbsolutePath() + FILE_EXTENSION);
			} else if(!path.endsWith("." + FILE_EXTENSION)) {
				validFile = new File(file.getAbsolutePath() + "." + FILE_EXTENSION);
			} else {
				validFile = file;
			}
		}
		return validFile;
	}
}
