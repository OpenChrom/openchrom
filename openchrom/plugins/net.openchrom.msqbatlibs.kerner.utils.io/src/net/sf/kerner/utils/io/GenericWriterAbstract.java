/*******************************************************************************
 * Copyright (c) 2015, 2017 Lablicate GmbH.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 * Dr. Alexander Kerner - initial API and implementation
 *******************************************************************************/
package net.sf.kerner.utils.io;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;

public abstract class GenericWriterAbstract implements GenericWriter {

	public void write(final File file) throws IOException {

		write(new FileOutputStream(file));
	}

	public void write(final OutputStream stream) throws IOException {

		write(UtilIO.outputStreamToWriter(stream));
	}
}
