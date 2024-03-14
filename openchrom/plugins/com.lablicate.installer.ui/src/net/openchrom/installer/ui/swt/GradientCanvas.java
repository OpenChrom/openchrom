/*******************************************************************************
 * Copyright (c) 2000, 2024 IBM Corporation, Polarion Software and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 * IBM Corporation - initial API and implementation
 * Tasktop Technologies - extracted FormHeading implementation for Mylyn
 *******************************************************************************/
package net.openchrom.installer.ui.swt;

import java.util.Hashtable;

import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.graphics.GC;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.graphics.Rectangle;
import org.eclipse.swt.widgets.Canvas;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.Listener;

public class GradientCanvas extends Canvas {

	private static final int SEPARATOR = 1 << 1;
	private static final int BOTTOM_SEPARATOR = 1 << 2;
	private static final int BACKGROUND_IMAGE_TILED = 1 << 3;
	public static final String COLOR_BASE_BG = "baseBg"; //$NON-NLS-1$
	static final String PREFIX = "org.eclipse.ui.forms."; //$NON-NLS-1$
	static final String H_PREFIX = PREFIX + "H_"; //$NON-NLS-1$
	public static final String H_BOTTOM_KEYLINE1 = H_PREFIX + "BOTTOM_KEYLINE1"; //$NON-NLS-1$
	/**
	 * Key for the form header bottom keyline 2 color.
	 */
	public static String H_BOTTOM_KEYLINE2 = H_PREFIX + "BOTTOM_KEYLINE2"; //$NON-NLS-1$
	private Image backgroundImage;
	private Image gradientImage;
	Hashtable<String, Color> colors = new Hashtable<>();
	private int flags;
	private GradientInfo gradientInfo;

	private class GradientInfo {

		Color[] gradientColors;
		int[] percents;
		boolean vertical;
	}

	@Override
	public boolean forceFocus() {

		return false;
	}

	/**
	 * Creates the form content control as a child of the provided parent.
	 * 
	 * @param parent
	 *            the parent widget
	 */
	public GradientCanvas(Composite parent, int style) {

		super(parent, style);
		setBackgroundMode(SWT.INHERIT_DEFAULT);
		setSeparatorAlignment(SWT.BOTTOM);
		addListener(SWT.Paint, new Listener() {

			@Override
			public void handleEvent(Event e) {

				onPaint(e.gc);
			}
		});
		addListener(SWT.Dispose, new Listener() {

			@Override
			public void handleEvent(Event e) {

				if(gradientImage != null) {
					gradientImage.dispose();
					gradientImage = null;
				}
			}
		});
		addListener(SWT.Resize, new Listener() {

			@Override
			public void handleEvent(Event e) {

				if(gradientInfo != null || (backgroundImage != null && !isBackgroundImageTiled())) {
					updateGradientImage();
				}
			}
		});
	}

	/**
	 * Sets the background color of the header.
	 */
	@Override
	public void setBackground(Color bg) {

		super.setBackground(bg);
		internalSetBackground(bg);
	}

	private void internalSetBackground(Color bg) {

		putColor(COLOR_BASE_BG, bg);
	}

	public void setBackgroundGradient(Color[] gradientColors, int[] percents, boolean vertical) {

		if(gradientColors != null) {
			gradientInfo = new GradientInfo();
			gradientInfo.gradientColors = gradientColors;
			gradientInfo.percents = percents;
			gradientInfo.vertical = vertical;
			setBackground(null);
			updateGradientImage();
		} else {
			// reset
			gradientInfo = null;
			if(gradientImage != null) {
				gradientImage.dispose();
				gradientImage = null;
				setBackgroundImage(null);
			}
		}
	}

	public void setHeadingBackgroundImage(Image image) {

		this.backgroundImage = image;
		if(image != null) {
			setBackground(null);
		}
		if(isBackgroundImageTiled()) {
			setBackgroundImage(image);
		} else {
			updateGradientImage();
		}
	}

	public Image getHeadingBackgroundImage() {

		return backgroundImage;
	}

	public void setBackgroundImageTiled(boolean tiled) {

		if(tiled) {
			flags |= BACKGROUND_IMAGE_TILED;
		} else {
			flags &= ~BACKGROUND_IMAGE_TILED;
		}
		setHeadingBackgroundImage(this.backgroundImage);
	}

	public boolean isBackgroundImageTiled() {

		return (flags & BACKGROUND_IMAGE_TILED) != 0;
	}

	@Override
	public void setBackgroundImage(Image image) {

		super.setBackgroundImage(image);
		if(image != null) {
			internalSetBackground(null);
		}
	}

	private void onPaint(GC gc) {

		if(!isSeparatorVisible() && getBackgroundImage() == null) {
			return;
		}
		Rectangle carea = getClientArea();
		Image buffer = new Image(getDisplay(), carea.width, carea.height);
		buffer.setBackground(getBackground());
		GC igc = new GC(buffer);
		igc.setBackground(getBackground());
		igc.fillRectangle(0, 0, carea.width, carea.height);
		if(getBackgroundImage() != null) {
			if(gradientInfo != null) {
				drawBackground(igc, carea.x, carea.y, carea.width, carea.height);
			} else {
				Image bgImage = getBackgroundImage();
				Rectangle ibounds = bgImage.getBounds();
				drawBackground(igc, carea.x, carea.y, ibounds.width, ibounds.height);
			}
		}
		if(isSeparatorVisible()) {
			drawSeparator(carea, igc);
		}
		igc.dispose();
		gc.drawImage(buffer, carea.x, carea.y);
		buffer.dispose();
	}

	private void drawSeparator(Rectangle carea, GC igc) {

		// bg separator
		if(hasColor(H_BOTTOM_KEYLINE1)) {
			igc.setForeground(getColor(H_BOTTOM_KEYLINE1));
		} else {
			igc.setForeground(getBackground());
		}
		if(getSeparatorAlignment() == SWT.BOTTOM) {
			igc.drawLine(carea.x, carea.height - 2, carea.x + carea.width - 1, carea.height - 2);
		} else {
			igc.drawLine(carea.x, 1, carea.x + carea.width - 1, 1);
		}
		if(hasColor(H_BOTTOM_KEYLINE2)) {
			igc.setForeground(getColor(H_BOTTOM_KEYLINE2));
		} else {
			igc.setForeground(getForeground());
		}
		if(getSeparatorAlignment() == SWT.BOTTOM) {
			igc.drawLine(carea.x, carea.height - 1, carea.x + carea.width - 1, carea.height - 1);
		} else {
			igc.drawLine(carea.x, 0, carea.x + carea.width - 1, 0);
		}
	}

	private void updateGradientImage() {

		Rectangle rect = getBounds();
		if(gradientImage != null) {
			gradientImage.dispose();
			gradientImage = null;
		}
		if(gradientInfo != null) {
			boolean vertical = gradientInfo.vertical;
			int width = vertical ? 1 : rect.width;
			int height = vertical ? rect.height : 1;
			gradientImage = new Image(getDisplay(), Math.max(width, 1), Math.max(height, 1));
			GC gc = new GC(gradientImage);
			drawTextGradient(gc, width, height);
			gc.dispose();
		} else if(backgroundImage != null && !isBackgroundImageTiled()) {
			gradientImage = new Image(getDisplay(), Math.max(rect.width, 1), Math.max(rect.height, 1));
			gradientImage.setBackground(getBackground());
			GC gc = new GC(gradientImage);
			gc.drawImage(backgroundImage, 0, 0);
			gc.dispose();
		}
		setBackgroundImage(gradientImage);
	}

	private void drawTextGradient(GC gc, int width, int height) {

		final Color oldBackground = gc.getBackground();
		if(gradientInfo.gradientColors.length == 1) {
			if(gradientInfo.gradientColors[0] != null) {
				gc.setBackground(gradientInfo.gradientColors[0]);
			}
			gc.fillRectangle(0, 0, width, height);
		} else {
			final Color oldForeground = gc.getForeground();
			Color lastColor = gradientInfo.gradientColors[0];
			if(lastColor == null) {
				lastColor = oldBackground;
			}
			int pos = 0;
			for(int i = 0; i < gradientInfo.percents.length; ++i) {
				gc.setForeground(lastColor);
				lastColor = gradientInfo.gradientColors[i + 1];
				if(lastColor == null) {
					lastColor = oldBackground;
				}
				gc.setBackground(lastColor);
				if(gradientInfo.vertical) {
					final int gradientHeight = (gradientInfo.percents[i] * height / 100) - pos;
					gc.fillGradientRectangle(0, pos, width, gradientHeight, true);
					pos += gradientHeight;
				} else {
					final int gradientWidth = (gradientInfo.percents[i] * width / 100) - pos;
					gc.fillGradientRectangle(pos, 0, gradientWidth, height, false);
					pos += gradientWidth;
				}
			}
			if(gradientInfo.vertical && pos < height) {
				gc.setBackground(getColor(COLOR_BASE_BG));
				gc.fillRectangle(0, pos, width, height - pos);
			}
			if(!gradientInfo.vertical && pos < width) {
				gc.setBackground(getColor(COLOR_BASE_BG));
				gc.fillRectangle(pos, 0, width - pos, height);
			}
			if(isSeparatorVisible()) {
				drawSeparator(getClientArea(), gc);
			}
			gc.setForeground(oldForeground);
		}
	}

	public boolean isSeparatorVisible() {

		return (flags & SEPARATOR) != 0;
	}

	public void setSeparatorVisible(boolean addSeparator) {

		if(addSeparator) {
			flags |= SEPARATOR;
		} else {
			flags &= ~SEPARATOR;
		}
	}

	public void setSeparatorAlignment(int alignment) {

		if(alignment == SWT.BOTTOM) {
			flags |= BOTTOM_SEPARATOR;
		} else {
			flags &= ~BOTTOM_SEPARATOR;
		}
	}

	public int getSeparatorAlignment() {

		return (flags & BOTTOM_SEPARATOR) != 0 ? SWT.BOTTOM : SWT.TOP;
	}

	public void putColor(String key, Color color) {

		if(color == null) {
			colors.remove(key);
		} else {
			colors.put(key, color);
		}
	}

	public Color getColor(String key) {

		return colors.get(key);
	}

	public boolean hasColor(String key) {

		return colors.containsKey(key);
	}
}
