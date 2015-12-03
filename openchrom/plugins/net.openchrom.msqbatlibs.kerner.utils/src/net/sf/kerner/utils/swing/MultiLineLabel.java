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

import java.awt.Rectangle;

import javax.swing.JLabel;

/**
 * A {@link JLabel} with support for multi-line text that wraps when the line
 * doesn't fit in the available width. Multi-line text support is handled by the
 * {@link MultiLineLabelUI}, the default UI delegate of this component. The text
 * in the label can be horizontally and vertically aligned, relative to the
 * bounds of the component.
 * 
 * @author Samuel Sjoberg, http://samuelsjoberg.com
 * @version 1.0.0
 */
public class MultiLineLabel extends JLabel {

    /** Default serial version UID. */
    private static final long serialVersionUID = 1L;

    /** Cache to save heap allocations. */
    private Rectangle bounds;

    /** Horizontal text alignment. */
    private int halign = LEFT;

    /** Vertical text alignment. */
    private int valign = CENTER;

    /**
     * Creates a new empty label.
     */
    public MultiLineLabel() {
        super();
        setUI(MultiLineLabelUI.labelUI);
    }

    /**
     * Creates a new label with <code>text</code> value.
     * 
     * @param text
     *            the value of the label
     */
    public MultiLineLabel(final String text) {
        this();
        setText(text);
    }

    /** {@inheritDoc} */
    @Override
    public Rectangle getBounds() {
        if (bounds == null) {
            bounds = new Rectangle();
        }
        return super.getBounds(bounds);
    }

    /**
     * Get the horizontal text alignment.
     * 
     * @return horizontal text alignment
     */
    public int getHorizontalTextAlignment() {
        return halign;
    }

    /**
     * Get the vertical text alignment.
     * 
     * @return vertical text alignment
     */
    public int getVerticalTextAlignment() {
        return valign;
    }

    /**
     * Set the horizontal text alignment.
     * 
     * @param alignment
     *            horizontal alignment
     */
    public void setHorizontalTextAlignment(final int alignment) {
        firePropertyChange("horizontalTextAlignment", halign, alignment);
        halign = alignment;
    }

    /**
     * Set the vertical text alignment.
     * 
     * @param alignment
     *            vertical alignment
     */
    public void setVerticalTextAlignment(final int alignment) {
        firePropertyChange("verticalTextAlignment", valign, alignment);
        valign = alignment;
    }
}
