/*******************************************************************************
 * Copyright (c) 2019, 2024 Lablicate GmbH.
 * 
 * All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0 which accompanies this distribution,
 * and is available at http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors:
 * Christoph Läubrich - initial API and implementation
 * Philip Wenig - color provider table
 *******************************************************************************/
package net.openchrom.xxd.classifier.supplier.ratios.ui.internal.provider;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.eclipse.chemclipse.support.ui.provider.AbstractChemClipseLabelProvider;
import org.eclipse.chemclipse.swt.ui.support.Colors;
import org.eclipse.jface.viewers.ITableColorProvider;
import org.eclipse.swt.graphics.Color;

import net.openchrom.xxd.classifier.supplier.ratios.model.IPeakRatio;

public abstract class AbstractTraceRatioLabelProvider extends AbstractChemClipseLabelProvider implements ITableColorProvider {

	private Set<Integer> deviationColumns = new HashSet<>();

	public AbstractTraceRatioLabelProvider(List<Integer> deviationColumns) {

		this.deviationColumns.addAll(deviationColumns);
	}

	@Override
	public Color getBackground(Object element, int columnIndex) {

		if(deviationColumns.contains(columnIndex)) {
			if(element instanceof IPeakRatio peakRatio) {
				double deviation = peakRatio.getDeviation();
				if(Double.isNaN(deviation)) {
					return Colors.RED;
				} else {
					double deviationWarn = peakRatio.getDeviationWarn();
					double deviationError = peakRatio.getDeviationError();
					if(deviation < deviationWarn) {
						return Colors.getColor(Colors.LIGHT_GREEN);
					} else if(deviation < deviationError) {
						return Colors.getColor(Colors.LIGHT_YELLOW);
					} else {
						return Colors.getColor(Colors.LIGHT_RED);
					}
				}
			}
		}
		//
		return null;
	}

	@Override
	public Color getForeground(Object element, int columnIndex) {

		if(deviationColumns.contains(columnIndex)) {
			if(element instanceof IPeakRatio peakRatio) {
				double deviation = peakRatio.getDeviation();
				if(Double.isNaN(deviation)) {
					return Colors.WHITE;
				} else {
					return Colors.BLACK;
				}
			}
		}
		//
		return null;
	}
}