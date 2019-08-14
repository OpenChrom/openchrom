/*******************************************************************************
 * Copyright (c) 2018, 2019 Lablicate GmbH.
 * 
 * All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0 which accompanies this distribution,
 * and is available at http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors:
 * Dr. Philip Wenig - initial API and implementation
 *******************************************************************************/
package net.openchrom.xxd.process.supplier.templates.util;

import java.io.File;
import java.util.List;
import java.util.Set;

import org.eclipse.core.databinding.validation.IValidator;

public interface ITemplateListUtil<T extends IValidator> {

	T getValidator();

	String[] parseString(String stringList);

	String createList(String[] items);

	List<String> importItems(File file);

	void exportItems(File file, String[] items);

	List<String> getList(String preferenceEntry);

	Set<Integer> extractTraces(String traces);
}
