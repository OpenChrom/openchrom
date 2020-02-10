/*******************************************************************************
 * Copyright (c) 2019, 2020 Lablicate GmbH.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors:
 * Dr. Philip Wenig - initial API and implementation
 * Christoph Läubrich - use PeakType instead of plain String
 *******************************************************************************/
package net.openchrom.xxd.process.supplier.templates.settings;

import net.openchrom.xxd.process.supplier.templates.model.IntegratorSetting;

public interface ITemplateSettings {

	String RE_START = "^";
	String RE_NUMBER = "(\\d*\\.)?\\d+";
	String RE_TEXT = "([^;\\|]*)";
	String RE_FLAG = "(true|false)";
	String RE_SEPARATOR = "(\\s*\\|\\s*)";
	//
	String RE_TRACES = "([\\s*\\d+,|\\d+-]*)";
	String RE_INTEGRATOR_TYPE = "(" + IntegratorSetting.INTEGRATOR_NAME_TRAPEZOID + "|" + IntegratorSetting.INTEGRATOR_NAME_MAX + ")"; // (Trapezoid|Max)
}
