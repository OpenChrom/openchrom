/*******************************************************************************
 * Copyright (c) 2023, 2026 Lablicate GmbH.
 *
 * This program and the accompanying materials are made
 * available under the terms of the Eclipse Public License 2.0
 * which is available at https://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
 * 
 * Contributors:
 * Philip Wenig - initial API and implementation
 *******************************************************************************/
package net.openchrom.msd.converter.supplier.microbenet.converter;

import java.io.File;

import org.eclipse.chemclipse.converter.core.AbstractMagicNumberMatcher;

public class MagicNumberMatcher extends AbstractMagicNumberMatcher {

	@Override
	public boolean checkFileFormat(File file) {

		return checkFileExtension(file, ".xml");
	}
}