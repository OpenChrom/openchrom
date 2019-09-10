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
package net.openchrom.xxd.process.supplier.templates.util;

import org.eclipse.chemclipse.support.util.ValueParserSupport;
import org.eclipse.core.runtime.IStatus;

public abstract class AbstractTemplateValidator extends ValueParserSupport implements ITemplateValidator {

	private TracesValidator tracesValidator = new TracesValidator();

	@Override
	public String validateTraces(String traces) {

		IStatus status = tracesValidator.validate(traces);
		return (status.isOK()) ? null : status.getMessage();
	}

	@Override
	public int getTrace(String value) {

		return tracesValidator.getTrace(value);
	}
}
