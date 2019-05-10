/*******************************************************************************
 * Copyright (c) 2019 Lablicate GmbH.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors:
 * Dr. Philip Wenig - initial API and implementation
 *******************************************************************************/
package net.openchrom.xxd.process.supplier.templates.settings;

public interface ITemplateSettings {

	String RE_START = "^";
	String RE_NUMBER = "(\\d*\\.)?\\d+";
	String RE_TEXT = "([^;\\|]*)";
	String RE_FLAG = "(true|false)";
	String RE_SEPARATOR = "(\\s*\\|\\s*)";
	//
	String RE_TRACES = "([\\d+,|\\d+-]*)";
	String RE_DETECTOR_TYPE = "(VV|BB)";
	String RE_INTEGRATOR_TYPE = "(Trapezoid|Max)";
}
