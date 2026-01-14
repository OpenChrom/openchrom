/*******************************************************************************
 * Copyright (c) 2023, 2026 Lablicate GmbH.
 *
 * This program and the accompanying materials are made
 * available under the terms of the Eclipse Public License 2.0
 * which is available at https://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
 * 
 * Contributors:
 * Philip Wenig - initial API and implementation
 *******************************************************************************/
package net.openchrom.swtchart.extension.export.vectorgraphics.support;

import java.awt.geom.AffineTransform;

import org.eclipse.swt.graphics.Transform;

public class AWTUtils {

	public static java.awt.Color convertColor(org.eclipse.swt.graphics.Color color) {

		return convertColor(color, 255);
	}

	public static java.awt.Color convertColor(org.eclipse.swt.graphics.Color color, int alpha) {

		return new java.awt.Color(color.getRed(), color.getGreen(), color.getBlue(), alpha);
	}

	public static AffineTransform convertTransform(Transform transform) {

		float[] elements = new float[6];
		transform.getElements(elements);
		return new AffineTransform(elements);
	}

	/**
	 * 100% = 255
	 * 20% = 50
	 * 0% = 0
	 * 
	 * @param percent
	 * @return
	 */
	public static int getAlpha(int percent) {

		if(percent < 0) {
			return 0;
		} else if(percent > 100) {
			return 255;
		} else {
			return Math.round(255.0f / 100.0f * percent);
		}
	}
}
