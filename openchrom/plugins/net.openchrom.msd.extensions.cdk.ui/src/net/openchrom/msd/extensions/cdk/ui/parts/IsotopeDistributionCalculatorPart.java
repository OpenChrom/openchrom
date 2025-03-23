/*******************************************************************************
 * Copyright (c) 2025 Lablicate GmbH.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors:
 * Matthias Mailänder - initial API and implementation
 *******************************************************************************/
package net.openchrom.msd.extensions.cdk.ui.parts;

import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Composite;

import net.openchrom.msd.extensions.cdk.ui.swt.ExtendedIsotopeDistributionCalculatorUI;

import jakarta.inject.Inject;

public class IsotopeDistributionCalculatorPart {

	@Inject
	public IsotopeDistributionCalculatorPart(Composite parent) {

		new ExtendedIsotopeDistributionCalculatorUI(parent, SWT.NONE);
	}
}
