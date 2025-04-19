/*******************************************************************************
 * Copyright (c) 2019, 2025 Lablicate GmbH.
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
package net.openchrom.xxd.classifier.supplier.ratios.ui.internal.provider.qual;

import org.eclipse.chemclipse.model.core.IMeasurementResult;
import org.eclipse.jface.viewers.IStructuredContentProvider;
import org.eclipse.jface.viewers.Viewer;

import net.openchrom.xxd.classifier.supplier.ratios.model.qual.QualRatios;

public class QualRatioContentProvider implements IStructuredContentProvider {

	@Override
	public Object[] getElements(Object inputElement) {

		QualRatios qualRatios = null;
		//
		if(inputElement instanceof IMeasurementResult<?> measurementResult) {
			Object object = measurementResult.getResult();
			if(object instanceof QualRatios qualRatiosResult) {
				qualRatios = qualRatiosResult;
			}
		} else if(inputElement instanceof QualRatios inputQualRatios) {
			qualRatios = inputQualRatios;
		}
		//
		if(qualRatios != null) {
			return qualRatios.toArray();
		}
		//
		return null;
	}

	@Override
	public void dispose() {

	}

	@Override
	public void inputChanged(Viewer viewer, Object oldInput, Object newInput) {

	}
}
