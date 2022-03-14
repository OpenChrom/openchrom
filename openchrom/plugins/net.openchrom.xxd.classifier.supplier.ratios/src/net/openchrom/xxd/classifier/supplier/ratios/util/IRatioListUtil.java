/*******************************************************************************
 * Copyright (c) 2019, 2022 Lablicate GmbH.
 * 
 * All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0 which accompanies this distribution,
 * and is available at http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors:
 * Dr. Philip Wenig - initial API and implementation
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
