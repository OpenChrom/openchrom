/*******************************************************************************
 * Copyright (c) 2017 Lablicate GmbH.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors:
 * Dr. Philip Wenig - initial API and implementation
 *******************************************************************************/
package net.openchrom.xxd.processor.supplier.tracecompare.ui.internal.provider;

import org.eclipse.jface.viewers.LabelProvider;
import org.eclipse.swt.graphics.Image;

public class ResultsTreeViewerLabelProvider extends LabelProvider {

	@Override
	public Image getImage(Object element) {

		// TODO
		return null;
	}

	@Override
	public String getText(Object element) {

		// TODO
		if(element instanceof String) {
			return (String)element;
		} else {
			return "n.v.";
		}
	}
}
