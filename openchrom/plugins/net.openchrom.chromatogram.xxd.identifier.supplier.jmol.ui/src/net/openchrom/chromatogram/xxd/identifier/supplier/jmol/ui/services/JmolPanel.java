/*******************************************************************************
 * Copyright (c) 2023, 2025 Lablicate GmbH.
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
package net.openchrom.chromatogram.xxd.identifier.supplier.jmol.ui.services;

import javax.swing.JPanel;

import org.jmol.adapter.smarter.SmarterJmolAdapter;
import org.jmol.api.JmolViewer;

public class JmolPanel extends JPanel {

	private static final long serialVersionUID = -8635230667397031184L;

	private JmolViewer viewer;

	public JmolPanel() {

		viewer = JmolViewer.allocateViewer(this, new SmarterJmolAdapter());
	}

	public JmolViewer getViewer() {

		return viewer;
	}
}