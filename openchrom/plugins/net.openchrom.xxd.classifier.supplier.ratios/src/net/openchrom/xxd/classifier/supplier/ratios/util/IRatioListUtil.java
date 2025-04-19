/*******************************************************************************
 * Copyright (c) 2019, 2025 Lablicate GmbH.
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
package net.openchrom.xxd.classifier.supplier.ratios.util;

import java.io.File;
import java.util.List;

import org.eclipse.core.databinding.validation.IValidator;

public interface IRatioListUtil<T extends IValidator<Object>> {

	T getValidator();

	String[] parseString(String stringList);

	String createList(String[] items);

	List<String> importItems(File file);

	void exportItems(File file, String[] items);

	List<String> getList(String preferenceEntry);
}
