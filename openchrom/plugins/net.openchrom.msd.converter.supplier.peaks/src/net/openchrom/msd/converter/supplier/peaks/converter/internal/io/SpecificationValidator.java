/*******************************************************************************
 * Copyright (c) 2015, 2020 Lablicate GmbH.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 * Dr. Philip Wenig - initial API and implementation
 * Matthias Mailänder - initial API and implementation
 *******************************************************************************/
package net.openchrom.msd.converter.supplier.peaks.converter.internal.io;

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
		if(path.toLowerCase().endsWith(".zip")) {
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
