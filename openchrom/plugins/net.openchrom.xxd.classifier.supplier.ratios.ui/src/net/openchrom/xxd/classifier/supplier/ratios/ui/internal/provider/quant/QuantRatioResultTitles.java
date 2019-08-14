/*******************************************************************************
 * Copyright (c) 2019 Lablicate GmbH.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors:
 * Dr. Philip Wenig - initial API and implementation
 * Christoph Läubrich - adjust to new API
 *******************************************************************************/
package net.openchrom.xxd.classifier.supplier.ratios.ui.internal.provider.quant;

import static org.eclipse.chemclipse.support.ui.swt.columns.ColumnBuilder.defaultSortableColumn;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.function.Function;

import org.eclipse.chemclipse.model.core.IChromatogram;
import org.eclipse.chemclipse.model.core.IPeak;
import org.eclipse.chemclipse.support.ui.swt.columns.ColumnDefinition;
import org.eclipse.chemclipse.support.ui.swt.columns.ColumnDefinitionProvider;

import net.openchrom.xxd.classifier.supplier.ratios.model.quant.QuantRatio;
import net.openchrom.xxd.classifier.supplier.ratios.ui.internal.provider.AbstractPeakRatioTitles;

public class QuantRatioResultTitles extends AbstractPeakRatioTitles implements ColumnDefinitionProvider {

	public static final String QUANTITATION_NAME = "Quantitation Name";
	public static final String CONCENTRATION = "Concentration";
	public static final String CONCENTRATION_UNIT = "ConcentrationUnit";
	public static final String EXPECTED_CONCENTRATION = "Expected Concentration";
	//
	public static final String[] TITLES_SETTINGS = { //
			NAME, //
			QUANTITATION_NAME, //
			EXPECTED_CONCENTRATION, //
			CONCENTRATION_UNIT, //
			DEVIATION_WARN, //
			DEVIATION_ERROR //
	};
	//
	public static final int[] BOUNDS_SETTINGS = { //
			150, //
			150, //
			80, //
			120, //
			80, //
			80 //
	};
	//
	public static final String[] TITLES_RESULTS = { //
			RETENTION_TIME, //
			QUANTITATION_NAME, //
			EXPECTED_CONCENTRATION, //
			CONCENTRATION, //
			CONCENTRATION_UNIT, //
			DEVIATION //
	};
	//
	public static final int[] BOUNDS_RESULTS = { //
			80, //
			150, //
			80, //
			80, //
			100, //
			80 //
	};

	@Override
	public Collection<? extends ColumnDefinition<?, ?>> getColumnDefinitions() {

		List<ColumnDefinition<?, ?>> list = new ArrayList<>();
		list.add(defaultSortableColumn(RETENTION_TIME, 80, new Function<QuantRatio, Double>() {

			@Override
			public Double apply(QuantRatio ratio) {

				IPeak peak = ratio.getPeak();
				if(peak != null) {
					return peak.getPeakModel().getRetentionTimeAtPeakMaximum() / IChromatogram.MINUTE_CORRELATION_FACTOR;
				} else {
					return null;
				}
			}
		}).create());
		list.add(defaultSortableColumn(QUANTITATION_NAME, 150, new Function<QuantRatio, String>() {

			@Override
			public String apply(QuantRatio ratio) {

				return ratio.getName();
			}
		}).create());
		list.add(defaultSortableColumn(EXPECTED_CONCENTRATION, 80, new Function<QuantRatio, Double>() {

			@Override
			public Double apply(QuantRatio ratio) {

				return ratio.getExpectedConcentration();
			}
		}).create());
		list.add(defaultSortableColumn(CONCENTRATION, 80, new Function<QuantRatio, Double>() {

			@Override
			public Double apply(QuantRatio ratio) {

				return ratio.getConcentration();
			}
		}).create());
		list.add(defaultSortableColumn(CONCENTRATION_UNIT, 100, new Function<QuantRatio, String>() {

			@Override
			public String apply(QuantRatio ratio) {

				return ratio.getConcentrationUnit();
			}
		}).create());
		list.add(defaultSortableColumn(DEVIATION, 80, new Function<QuantRatio, Double>() {

			@Override
			public Double apply(QuantRatio ratio) {

				return ratio.getDeviation();
			}
		}).create());
		return list;
	}
}
