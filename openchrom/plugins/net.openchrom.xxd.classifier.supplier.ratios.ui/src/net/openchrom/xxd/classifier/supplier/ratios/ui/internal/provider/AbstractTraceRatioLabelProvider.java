/*******************************************************************************
 * Copyright (c) 2019, 2023 Lablicate GmbH.
 * 
 * All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0 which accompanies this distribution,
 * and is available at http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors:
 * Christoph Läubrich - initial API and implementation
 *******************************************************************************/
package net.openchrom.xxd.classifier.supplier.ratios.ui.internal.provider;

import org.eclipse.chemclipse.support.ui.provider.AbstractChemClipseLabelProvider;
import org.eclipse.chemclipse.swt.ui.support.Colors;
import org.eclipse.jface.viewers.ITableColorProvider;
import org.eclipse.swt.graphics.Color;

import net.openchrom.xxd.classifier.supplier.ratios.model.IPeakRatio;

public abstract class AbstractTraceRatioLabelProvider extends AbstractChemClipseLabelProvider implements ITableColorProvider {

	private int deviationColumn;

	public AbstractTraceRatioLabelProvider(int deviationColumn) {

		this.deviationColumn = deviationColumn;
	}

	@Override
	public Color getBackground(Object element, int columnIndex) {

		if(columnIndex == deviationColumn) {
			if(element instanceof IPeakRatio peakRatio) {
				double deviation = peakRatio.getDeviation();
				double deviationWarn = peakRatio.getDeviationWarn();
				double deviationError = peakRatio.getDeviationError();
				//
				if(deviation < deviationWarn) {
					return Colors.getColor(Colors.LIGHT_GREEN);
				} else if(deviation < deviationError) {
					return Colors.getColor(Colors.LIGHT_YELLOW);
				} else {
					return Colors.getColor(Colors.LIGHT_RED);
				}
			}
		}
		return null;
	}

	@Override
	public Color getForeground(Object element, int columnIndex) {

		if(columnIndex == deviationColumn) {
			if(element instanceof IPeakRatio peakRatio) {
				double deviation = peakRatio.getDeviation();
				double deviationWarn = peakRatio.getDeviationWarn();
				double deviationError = peakRatio.getDeviationError();
				//
				if(deviation < deviationWarn) {
					return Colors.BLACK;
				} else if(deviation < deviationError) {
					return Colors.BLACK;
				} else {
					return Colors.BLACK;
				}
			}
		}
		return null;
	}
}
