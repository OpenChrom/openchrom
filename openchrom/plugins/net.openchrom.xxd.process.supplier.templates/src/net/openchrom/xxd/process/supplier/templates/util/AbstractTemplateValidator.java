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
 *******************************************************************************/
package net.openchrom.xxd.process.supplier.templates.util;

import java.util.HashSet;
import java.util.Set;

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
	public Set<Integer> extractTraces(String traces) {

		IStatus status = tracesValidator.validate(traces);
		Set<Integer> traceSet = status.isOK() ? tracesValidator.getTraces() : new HashSet<>();
		return traceSet;
	}
}
