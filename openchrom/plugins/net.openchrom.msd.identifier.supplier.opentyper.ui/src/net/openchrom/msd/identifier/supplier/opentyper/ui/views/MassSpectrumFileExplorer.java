/*******************************************************************************
 * Copyright (c) 2017 Lablicate GmbH.
 * 
 * All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0 which accompanies this distribution,
 * and is available at http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors:
 * Dr. Philip Wenig - initial API and implementation
 * Matthias Mailänder - initial API and implementation
 *******************************************************************************/
package net.openchrom.msd.identifier.supplier.opentyper.ui.views;

import javax.inject.Inject;

import org.eclipse.swt.widgets.Composite;

public class MassSpectrumFileExplorer extends AbstractSupplierMassSpectrumFileExplorer {

	@Inject
	public MassSpectrumFileExplorer(Composite parent) {
		super(parent);
	}
}
