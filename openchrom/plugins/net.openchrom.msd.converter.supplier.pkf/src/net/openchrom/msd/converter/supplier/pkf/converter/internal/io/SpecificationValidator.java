/*******************************************************************************
 * Copyright (c) 2015, 2025 Lablicate GmbH.
 *
 * This program and the accompanying materials are made
 * available under the terms of the Eclipse Public License 2.0
 * which is available at https://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
 * 
 * Contributors:
 * Philip Wenig - initial API and implementation
 * Matthias Mailänder - initial API and implementation
 *******************************************************************************/
package net.openchrom.msd.converter.supplier.pkf.converter.internal.io;

import java.io.File;

public class SpecificationValidator {

	public static File validateSpecification(File file) {

		if(file == null) {
			return null;
		}
		/*
		 * Check the extension.
		 */
		String path = file.getAbsolutePath();
		if(path.toLowerCase().endsWith(".pkf")) {
			return file;
		}
		return null;
	}

	/**
	 * Use only static methods.
	 */
	private SpecificationValidator() {

	}
}
