/*******************************************************************************
 * Copyright (c) 2010-2014 Alexander Kerner. All rights reserved.
 * 
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * 
 *   http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 ******************************************************************************/
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

    public static JComponent buildInfoPanelEmptyBorderScroll(final JComponent content,
            final int top, final int left, final int bottom, final int right) {
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
        return buildInfoPanelTextBorder((JComponent) content, title);
    }

    public static JComponent buildInfoPanelTextBorderScroll(final JComponent content,
            final String title) {
        final JPanel result = new JPanel(new BorderLayout());
        result.setBorder(BorderFactory.createTitledBorder(title));
        final JScrollPane scroll = new JScrollPane(content);
        result.add(scroll, BorderLayout.CENTER);
        return result;
    }

    public static JComponent buildPanelEmptyBorder(final JComponent content, final int top,
            final int left, final int bottom, final int right) {
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
