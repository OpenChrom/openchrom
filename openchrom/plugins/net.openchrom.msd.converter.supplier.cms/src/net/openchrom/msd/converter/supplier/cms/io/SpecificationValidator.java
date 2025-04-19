/*******************************************************************************
 * Copyright (c) 2016, 2025 Walter Whitlock, Philip Wenig.
 *
 * This program and the accompanying materials are made
 * available under the terms of the Eclipse Public License 2.0
 * which is available at https://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
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
