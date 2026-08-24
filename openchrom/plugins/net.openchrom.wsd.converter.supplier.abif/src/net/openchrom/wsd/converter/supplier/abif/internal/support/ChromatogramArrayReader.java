/*******************************************************************************
 * Copyright (c) 2016, 2026 Lablicate GmbH.
 *
 * This program and the accompanying materials are made
 * available under the terms of the Eclipse Public License 2.0
 * which is available at https://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
 * 
 * Contributors:
 * Matthias Mailänder - initial API and implementation
 * Philip Wenig - initial API and implementation
 *******************************************************************************/
package net.openchrom.wsd.converter.supplier.abif.internal.support;

import java.io.File;
import java.io.IOException;

import org.eclipse.chemclipse.converter.io.support.AbstractArrayReader;

public class ChromatogramArrayReader extends AbstractArrayReader implements IChromatogramArrayReader {

	public ChromatogramArrayReader(File file) throws IOException {

		super(file);
	}
}
