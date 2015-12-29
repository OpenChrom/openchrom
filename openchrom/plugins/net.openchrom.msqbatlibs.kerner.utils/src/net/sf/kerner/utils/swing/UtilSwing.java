/*******************************************************************************
 * Copyright (c) 2015 Lablicate UG (haftungsbeschränkt).
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 * Dr. Alexander Kerner - initial API and implementation
 *******************************************************************************/
package net.sf.kerner.utils.swing;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Toolkit;

import javax.swing.BorderFactory;
import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.SwingConstants;

public class UtilSwing {

	public static JComponent buildInfoPanelEmptyBorderScroll(final JComponent content) {

		final JPanel result = new JPanel(new BorderLayout());
		result.setBorder(BorderFactory.createEmptyBorder());
		final JScrollPane scroll = new JScrollPane(content);
		result.add(scroll, BorderLayout.CENTER);
		return result;
	}

	public static JComponent buildInfoPanelEmptyBorderScroll(final JComponent content, final int top, final int left, final int bottom, final int right) {

		final JPanel result = new JPanel(new BorderLayout());
		result.setBorder(BorderFactory.createEmptyBorder(top, left, bottom, right));
		final JScrollPane scroll = new JScrollPane(content);
		result.add(scroll, BorderLayout.CENTER);
		return result;
	}

	public static JComponent buildInfoPanelTextBorder(final JComponent content, final String title) {

		final JPanel result = new JPanel(new BorderLayout());
		result.setBorder(BorderFactory.createTitledBorder(title));
		result.add(content, BorderLayout.CENTER);
		return result;
	}

	public static JComponent buildInfoPanelTextBorder(final JLabel content, final String title) {

		content.setHorizontalAlignment(SwingConstants.CENTER);
		content.setVerticalAlignment(SwingConstants.CENTER);
		content.setOpaque(true);
		return buildInfoPanelTextBorder((JComponent)content, title);
	}

	public static JComponent buildInfoPanelTextBorderScroll(final JComponent content, final String title) {

		final JPanel result = new JPanel(new BorderLayout());
		result.setBorder(BorderFactory.createTitledBorder(title));
		final JScrollPane scroll = new JScrollPane(content);
		result.add(scroll, BorderLayout.CENTER);
		return result;
	}

	public static JComponent buildPanelEmptyBorder(final JComponent content, final int top, final int left, final int bottom, final int right) {

		final JPanel result = new JPanel(new BorderLayout());
		result.setBorder(BorderFactory.createEmptyBorder(top, left, bottom, right));
		result.add(content, BorderLayout.CENTER);
		return result;
	}

	public static void center(final Component component) {

		final Toolkit tk = Toolkit.getDefaultToolkit();
		final Dimension screenSize = tk.getScreenSize();
		final int screenHeight = screenSize.height;
		final int screenWidth = screenSize.width;
		component.setSize(screenWidth / 2, screenHeight / 2);
		component.setLocation(screenWidth / 4, screenHeight / 4);
	}

	private UtilSwing() {
	}
}
