/*******************************************************************************
 * Copyright (c) 2018, 2025 Lablicate GmbH.
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
package net.openchrom.xxd.process.supplier.templates.util;

import java.io.File;
import java.util.List;
import java.util.Set;

import org.eclipse.core.databinding.validation.IValidator;

public interface ITemplateListUtil<T extends IValidator<Object>> {

	T getValidator();

	String[] parseString(String stringList);

	String createList(String[] items);

	List<String> importItems(File file);

	void exportItems(File file, String[] items);

	List<String> getList(String preferenceEntry);

	Set<Integer> extractTraces(String traces);
}
