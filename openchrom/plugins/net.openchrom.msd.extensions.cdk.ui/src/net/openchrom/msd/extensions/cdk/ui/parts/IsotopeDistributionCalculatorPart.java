/*******************************************************************************
 * Copyright (c) 2025 Lablicate GmbH.
 *
 * This program and the accompanying materials are made
 * available under the terms of the Eclipse Public License 2.0
 * which is available at https://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
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
