/*******************************************************************************
 * Copyright (c) 2023 Lablicate GmbH.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors:
 * Matthias Mailänder - initial API and implementation
 *******************************************************************************/
package net.openchrom.chromatogram.xxd.identifier.supplier.jmol.ui.services;

import javax.swing.JPanel;

import org.jmol.adapter.smarter.SmarterJmolAdapter;
import org.jmol.api.JmolViewer;

public class JmolPanel extends JPanel {

	private static final long serialVersionUID = -8635230667397031184L;
	//
	private JmolViewer viewer;

	public JmolPanel() {

		viewer = JmolViewer.allocateViewer(this, new SmarterJmolAdapter());
	}

	public JmolViewer getViewer() {

		return viewer;
	}
}