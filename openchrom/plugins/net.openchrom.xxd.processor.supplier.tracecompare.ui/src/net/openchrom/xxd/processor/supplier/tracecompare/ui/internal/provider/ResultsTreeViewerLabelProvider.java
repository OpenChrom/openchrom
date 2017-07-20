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

import net.openchrom.xxd.processor.supplier.tracecompare.model.ReferenceModel;
import net.openchrom.xxd.processor.supplier.tracecompare.model.SampleLaneModel;

public class ResultsTreeViewerLabelProvider extends LabelProvider {

	@Override
	public Image getImage(Object element) {

		return null;
	}

	@Override
	public String getText(Object element) {

		String text;
		if(element instanceof ReferenceModel) {
			ReferenceModel referenceModel = (ReferenceModel)element;
			text = referenceModel.toString();
		} else if(element instanceof SampleLaneModel) {
			SampleLaneModel sampleLaneModel = (SampleLaneModel)element;
			text = sampleLaneModel.toString();
		} else {
			text = "n.a.";
		}
		return text;
	}
}
