/*******************************************************************************
 * Copyright (c) 2023 Lablicate GmbH.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors:
 * Philip Wenig - initial API and implementation
 *******************************************************************************/
package net.openchrom.swtchart.extension.export.vectorgraphics.support;

import java.awt.geom.AffineTransform;

import org.eclipse.swt.graphics.Transform;

public class AWTUtils {

	public static java.awt.Color convertColor(org.eclipse.swt.graphics.Color color) {

		return new java.awt.Color(color.getRed(), color.getGreen(), color.getBlue());
	}

	public static AffineTransform convertTransform(Transform transform) {

		float[] elements = new float[6];
		transform.getElements(elements);
		AffineTransform affineTransform = new AffineTransform(elements);
		//
		return affineTransform;
	}
}