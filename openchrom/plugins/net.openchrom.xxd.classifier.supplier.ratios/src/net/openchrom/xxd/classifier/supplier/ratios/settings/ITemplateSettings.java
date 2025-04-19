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
package net.openchrom.xxd.classifier.supplier.ratios.settings;

public interface ITemplateSettings {

	String RE_START = "^";
	String RE_NUMBER = "(\\d*\\.)?\\d+";
	String RE_TEXT = "([^;\\|]*)";
	String RE_FLAG = "(true|false)";
	String RE_SEPARATOR = "(\\s*\\|\\s*)";
	//
	String RE_TRACE_PATTERN = "(\\d+:\\d+)";
}
