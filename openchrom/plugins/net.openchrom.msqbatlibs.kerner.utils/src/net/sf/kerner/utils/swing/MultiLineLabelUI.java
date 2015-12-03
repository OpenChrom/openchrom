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
/*
 * The MIT License
 *
 * Copyright (c) 2009 Samuel Sjoberg
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 * 
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
 * THE SOFTWARE.
 */
package net.sf.kerner.utils.swing;

import java.awt.Dimension;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Insets;
import java.awt.Rectangle;
import java.awt.event.ComponentEvent;
import java.awt.event.ComponentListener;
import java.beans.PropertyChangeEvent;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import javax.swing.Icon;
import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.plaf.ComponentUI;
import javax.swing.plaf.LabelUI;
import javax.swing.plaf.basic.BasicLabelUI;
import javax.swing.text.BadLocationException;
import javax.swing.text.Document;
import javax.swing.text.Element;
import javax.swing.text.PlainDocument;
import javax.swing.text.Segment;
import javax.swing.text.Utilities;
import javax.swing.text.View;

/**
 * Label UI delegate that supports multiple lines and line wrapping. Hard line
 * breaks (<code>\n</code>) are preserved. If the dimensions of the label is too
 * small to fit all content, the string will be clipped and "..." appended to
 * the end of the visible text (similar to the default behavior of
 * <code>JLabel</code>). If used in conjunction with a {@link MultiLineLabel},
 * text alignment (horizontal and vertical) is supported. The UI delegate can be
 * used on a regular <code>JLabel</code> if text alignment isn't required. The
 * default alignment, left and vertically centered, will then be used.
 * <p>
 * Example of usage:
 * 
 * <pre>
 * JLabel myLabel = new JLabel();
 * myLabel.setUI(MultiLineLabelUI.labelUI);
 * myLabel.setText(&quot;A long label that will wrap automatically.&quot;);
 * </pre>
 * <p>
 * The line and wrapping support is implemented without using a
 * <code>View</code> to make it easy for subclasses to add custom text effects
 * by overriding {@link #paintEnabledText(JLabel, Graphics, String, int, int)}
 * and {@link #paintDisabledText(JLabel, Graphics, String, int, int)}. This
 * class is designed to be easily extended by subclasses.
 * 
 * @author Samuel Sjoberg, http://samuelsjoberg.com
 * @version 1.3.0
 */
public class MultiLineLabelUI extends BasicLabelUI implements ComponentListener {

    /**
     * Static singleton {@link Segment} cache.
     * 
     * @see javax.swing.text.SegmentCache
     * @author Samuel Sjoberg
     */
    protected static final class SegmentCache {

        /** Singleton instance. */
        private static SegmentCache cache = new SegmentCache();

        /**
         * Returns a <code>Segment</code>. When done, the <code>Segment</code>
         * should be recycled by invoking {@link #releaseSegment(Segment)}.
         * 
         * @return a <code>Segment</code>.
         */
        public static Segment getSegment() {
            final int size = cache.segments.size();
            if (size > 0) {
                return cache.segments.remove(size - 1);
            }
            return new Segment();
        }

        /**
         * Releases a <code>Segment</code>. A segment should not be used after
         * it is released, and a segment should never be released more than
         * once.
         */
        public static void releaseSegment(final Segment segment) {
            segment.array = null;
            segment.count = 0;
            cache.segments.add(segment);
        }

        /** Reused segments. */
        private final ArrayList<Segment> segments = new ArrayList<Segment>(2);

        /** Private constructor. */
        private SegmentCache() {
        }
    }

    /** Default size of the lines list. */
    protected static int defaultSize = 4;

    /** Shared instance of the UI delegate. */
    public static LabelUI labelUI = new MultiLineLabelUI();
    // Static references to avoid heap allocations.
    protected static Rectangle paintIconR = new Rectangle();
    protected static Rectangle paintTextR = new Rectangle();
    protected static Insets paintViewInsets = new Insets(0, 0, 0, 0);

    protected static Rectangle paintViewR = new Rectangle();

    /**
     * Client property key used to store the calculated wrapped lines on the
     * JLabel.
     */
    public static final String PROPERTY_KEY = "WrappedText";

    /**
     * Get the shared UI instance.
     * 
     * @param c
     *            the component about to be installed
     * @return the shared UI delegate instance
     */
    public static ComponentUI createUI(final JComponent c) {
        return labelUI;
    }

    private static int getAscent(final FontMetrics fm) {
        return fm.getAscent() + fm.getLeading();
    }

    /**
     * Check the given string to see if it should be rendered as HTML. Code
     * based on implementation found in
     * <code>BasicHTML.isHTMLString(String)</code> in future JDKs.
     * 
     * @param s
     *            the string
     * @return <code>true</code> if string is HTML, otherwise <code>false</code>
     */
    private static boolean isHTMLString(final String s) {
        if (s != null) {
            if ((s.length() >= 6) && (s.charAt(0) == '<') && (s.charAt(5) == '>')) {
                final String tag = s.substring(1, 5);
                return tag.equalsIgnoreCase("html");
            }
        }
        return false;
    }

    /** Font metrics of the JLabel being rendered. */
    protected FontMetrics metrics;

    /**
     * Establish the horizontal text alignment. The default alignment is left
     * aligned text.
     * 
     * @param label
     *            the label to paint
     * @param fm
     *            font metrics
     * @param s
     *            the string to paint
     * @param bounds
     *            the text bounds rectangle
     * @return the x-coordinate to use when painting for proper alignment
     */
    protected int alignmentX(final JLabel label, final FontMetrics fm, final String s,
            final Rectangle bounds) {
        if (label instanceof MultiLineLabel) {
            final int align = ((MultiLineLabel) label).getHorizontalTextAlignment();
            switch (align) {
                case JLabel.RIGHT:
                    return bounds.x + paintViewR.width - fm.stringWidth(s);
                case JLabel.CENTER:
                    return bounds.x + paintViewR.width / 2 - fm.stringWidth(s) / 2;
                default:
                    return bounds.x;
            }
        }
        return bounds.x;
    }

    /**
     * Establish the vertical text alignment. The default alignment is to center
     * the text in the label.
     * 
     * @param label
     *            the label to paint
     * @param fm
     *            font metrics
     * @param bounds
     *            the text bounds rectangle
     * @return the vertical text alignment, defaults to CENTER.
     */
    protected int alignmentY(final JLabel label, final FontMetrics fm, final Rectangle bounds) {
        final int height = getAvailableHeight(label);
        final int textHeight = bounds.height;

        if (label instanceof MultiLineLabel) {
            final int align = ((MultiLineLabel) label).getVerticalTextAlignment();
            switch (align) {
                case JLabel.TOP:
                    return getAscent(fm) + paintViewInsets.top;
                case JLabel.BOTTOM:
                    return getAscent(fm) + height - paintViewInsets.top + paintViewInsets.bottom
                            - textHeight;
                default:
            }
        }

        // Center alignment
        final int textY = paintViewInsets.top + (height - textHeight) / 2 + getAscent(fm);
        return Math.max(textY, getAscent(fm) + paintViewInsets.top);
    }

    /**
     * Calculate the position on which to break (wrap) the line.
     * 
     * @param doc
     *            the document
     * @param p0
     *            start position
     * @param p1
     *            end position
     * @return the actual end position, will be <code>p1</code> if content does
     *         not need to wrap, otherwise it will be less than <code>p1</code>.
     */
    protected int calculateBreakPosition(final Document doc, final int p0, final int p1) {
        final Segment segment = SegmentCache.getSegment();
        try {
            doc.getText(p0, p1 - p0, segment);
        } catch (final BadLocationException e) {
            throw new Error("Can't get line text");
        }

        final int width = paintTextR.width;
        final int p = p0 + Utilities.getBreakLocation(segment, metrics, 0, width, null, p0);
        SegmentCache.releaseSegment(segment);
        return p;
    }

    /**
     * Clear the wrapped line cache.
     * 
     * @param l
     *            the label containing a cached value
     */
    protected void clearCache(final JLabel l) {
        l.putClientProperty(PROPERTY_KEY, null);
    }

    /**
     * Add a clip indication to the string. It is important that the string
     * length does not exceed the length or the original string.
     * 
     * @param text
     *            the to be painted
     * @param fm
     *            font metrics
     * @param bounds
     *            the text bounds
     * @return the clipped string
     */
    protected String clip(final String text, final FontMetrics fm, final Rectangle bounds) {
        // Fast and lazy way to insert a clip indication is to simply replace
        // the last characters in the string with the clip indication.
        // A better way would be to use metrics and calculate how many (if any)
        // characters that need to be replaced.
        if (text.length() < 3) {
            return "...";
        }
        return text.substring(0, text.length() - 3) + "...";
    }

    /** {@inheritDoc} */
    public void componentHidden(final ComponentEvent e) {
        // Don't care
    }

    /** {@inheritDoc} */
    public void componentMoved(final ComponentEvent e) {
        // Don't care
    }

    /** {@inheritDoc} */
    public void componentResized(final ComponentEvent e) {
        clearCache((JLabel) e.getSource());
    }

    /** {@inheritDoc} */
    public void componentShown(final ComponentEvent e) {
        // Don't care
    }

    /**
     * Returns the available height to paint text on. This is the height of the
     * passed component with insets subtracted.
     * 
     * @param l
     *            a component
     * @return the available height
     */
    protected int getAvailableHeight(final JLabel l) {
        l.getInsets(paintViewInsets);
        return l.getHeight() - paintViewInsets.top - paintViewInsets.bottom;
    }

    /**
     * The preferred height of the label is the height of the lines with added
     * top and bottom insets.
     * 
     * @param label
     *            the label
     * @return the preferred height of the wrapped lines.
     */
    protected int getPreferredHeight(final JLabel label) {
        final int numOfLines = getTextLines(label).size();
        final Insets insets = label.getInsets(paintViewInsets);
        return numOfLines * metrics.getHeight() + insets.top + insets.bottom;
    }

    /** {@inheritDoc} */
    @Override
    public Dimension getPreferredSize(final JComponent c) {
        final Dimension d = super.getPreferredSize(c);
        final JLabel label = (JLabel) c;

        if (isHTMLString(label.getText())) {
            return d; // HTML overrides everything and we don't need to process
        }

        // Width calculated by super is OK. The preferred width is the width of
        // the unwrapped content as long as it does not exceed the width of the
        // parent container.

        if (c.getParent() != null) {
            // Ensure that preferred width never exceeds the available width
            // (including its border insets) of the parent container.
            final Insets insets = c.getParent().getInsets();
            final Dimension size = c.getParent().getSize();
            if (size.width > 0) {
                // If width isn't set component shouldn't adjust.
                d.width = size.width - insets.left - insets.right;
            }
        }

        updateLayout(label, null, d.width, d.height);

        // The preferred height is either the preferred height of the text
        // lines, or the height of the icon.
        d.height = Math.max(d.height, getPreferredHeight(label));

        return d;
    }

    /**
     * Get the lines of text contained in the text label. The prepared lines is
     * cached as a client property, accessible via {@link #PROPERTY_KEY}.
     * 
     * @param l
     *            the label
     * @return the text lines of the label.
     */
    @SuppressWarnings("unchecked")
    protected List<String> getTextLines(final JLabel l) {
        List<String> lines = (List<String>) l.getClientProperty(PROPERTY_KEY);
        if (lines == null) {
            lines = prepareLines(l);
            l.putClientProperty(PROPERTY_KEY, lines);
        }
        return lines;
    }

    /** {@inheritDoc} */
    @Override
    protected void installListeners(final JLabel c) {
        super.installListeners(c);
        c.addComponentListener(this);
    }

    /** {@inheritDoc} */
    @Override
    public void paint(final Graphics g, final JComponent c) {

        // parent's update method fills the background
        prepareGraphics(g);

        final JLabel label = (JLabel) c;
        final String text = label.getText();
        final Icon icon = (label.isEnabled()) ? label.getIcon() : label.getDisabledIcon();

        if ((icon == null) && (text == null)) {
            return;
        }

        final FontMetrics fm = g.getFontMetrics();

        updateLayout(label, fm, c.getWidth(), c.getHeight());

        if (icon != null) {
            icon.paintIcon(c, g, paintIconR.x, paintIconR.y);
        }

        if (text != null) {
            final View v = (View) c.getClientProperty("html");
            if (v != null) {
                // HTML view disables multi-line painting.
                v.paint(g, paintTextR);
            } else {
                // Paint the multi line text
                paintTextLines(g, label, fm);
            }
        }
    }

    /**
     * Paint the wrapped text lines.
     * 
     * @param g
     *            graphics component to paint on
     * @param label
     *            the label being painted
     * @param fm
     *            font metrics for current font
     */
    protected void paintTextLines(final Graphics g, final JLabel label, final FontMetrics fm) {
        final List<String> lines = getTextLines(label);

        // Available component height to paint on.
        final int height = getAvailableHeight(label);

        int textHeight = lines.size() * fm.getHeight();
        while (textHeight > height) {
            // Remove one line until no. of visible lines is found.
            textHeight -= fm.getHeight();
        }
        paintTextR.height = Math.min(textHeight, height);
        paintTextR.y = alignmentY(label, fm, paintTextR);

        final int textX = paintTextR.x;
        int textY = paintTextR.y;

        for (final Iterator<String> it = lines.iterator(); it.hasNext()
                && paintTextR.contains(textX, textY + getAscent(fm)); textY += fm.getHeight()) {

            String text = it.next().trim();

            if (it.hasNext() && !paintTextR.contains(textX, textY + fm.getHeight() + getAscent(fm))) {
                // The last visible row, add a clip indication.
                text = clip(text, fm, paintTextR);
            }

            final int x = alignmentX(label, fm, text, paintTextR);

            if (label.isEnabled()) {
                paintEnabledText(label, g, text, x, textY);
            } else {
                paintDisabledText(label, g, text, x, textY);
            }
        }
    }

    protected void prepareGraphics(final Graphics g) {
    }

    /**
     * Prepare the text lines for rendering. The lines are wrapped to fit in the
     * current available space for text. Explicit line breaks are preserved.
     * 
     * @param l
     *            the label to render
     * @return a list of text lines to render
     */
    protected List<String> prepareLines(final JLabel l) {
        final List<String> lines = new ArrayList<String>(defaultSize);
        final String text = l.getText();
        if (text == null) {
            return null; // Null guard
        }
        final PlainDocument doc = new PlainDocument();
        try {
            doc.insertString(0, text, null);
        } catch (final BadLocationException e) {
            return null;
        }
        final Element root = doc.getDefaultRootElement();
        for (int i = 0, j = root.getElementCount(); i < j; i++) {
            wrap(lines, root.getElement(i));
        }
        return lines;
    }

    /** {@inheritDoc} */
    @Override
    public void propertyChange(final PropertyChangeEvent e) {
        super.propertyChange(e);
        final String name = e.getPropertyName();
        if (name.equals("text") || "font".equals(name)) {
            clearCache((JLabel) e.getSource());
        }
    }

    /** {@inheritDoc} */
    @Override
    protected void uninstallDefaults(final JLabel c) {
        super.uninstallDefaults(c);
        clearCache(c);
    }

    /** {@inheritDoc} */
    @Override
    protected void uninstallListeners(final JLabel c) {
        super.uninstallListeners(c);
        c.removeComponentListener(this);
    }

    /**
     * Calculate the paint rectangles for the icon and text for the passed
     * label.
     * 
     * @param l
     *            a label
     * @param fm
     *            the font metrics to use, or <code>null</code> to get the font
     *            metrics from the label
     * @param width
     *            label width
     * @param height
     *            label height
     */
    protected void updateLayout(final JLabel l, FontMetrics fm, final int width, final int height) {
        if (fm == null) {
            fm = l.getFontMetrics(l.getFont());
        }
        metrics = fm;

        final String text = l.getText();
        final Icon icon = l.getIcon();
        final Insets insets = l.getInsets(paintViewInsets);

        paintViewR.x = insets.left;
        paintViewR.y = insets.top;
        paintViewR.width = width - (insets.left + insets.right);
        paintViewR.height = height - (insets.top + insets.bottom);

        paintIconR.x = paintIconR.y = paintIconR.width = paintIconR.height = 0;
        paintTextR.x = paintTextR.y = paintTextR.width = paintTextR.height = 0;

        layoutCL(l, fm, text, icon, paintViewR, paintIconR, paintTextR);
    }

    /**
     * If necessary, wrap the text into multiple lines.
     * 
     * @param lines
     *            line array in which to store the wrapped lines
     * @param elem
     *            the document element containing the text content
     */
    protected void wrap(final List<String> lines, final Element elem) {
        final int p1 = elem.getEndOffset();
        final Document doc = elem.getDocument();
        for (int p0 = elem.getStartOffset(); p0 < p1;) {
            final int p = calculateBreakPosition(doc, p0, p1);
            try {
                lines.add(doc.getText(p0, p - p0));
            } catch (final BadLocationException e) {
                throw new Error("Can't get line text. p0=" + p0 + " p=" + p);
            }
            p0 = (p == p0) ? p1 : p;
        }
    }
}
