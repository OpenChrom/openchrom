/*******************************************************************************
 * Copyright (c) 2019, 2023 Lablicate GmbH.
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
	public static final String RESPONSE_FACTOR = "Response Factor";
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
			DEVIATION, //
			RESPONSE_FACTOR //
	};
	//
	public static final int[] BOUNDS_RESULTS = { //
			80, //
			80, //
			80, //
			80, //
			80, //
			80, //
			80 //
	};

	@Override
	public Collection<? extends ColumnDefinition<?, ?>> getColumnDefinitions() {

		List<ColumnDefinition<?, ?>> list = new ArrayList<>();
		//
		addColumnRetentionTime(list);
		addColumnQuantitationName(list);
		addColumnExpectedConcentration(list);
		addColumnConcentration(list);
		addColumnConcentrationUnit(list);
		addColumnDeviation(list);
		addColumnResponseFactor(list);
		//
		return list;
	}

	private void addColumnRetentionTime(List<ColumnDefinition<?, ?>> list) {

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
	}

	private void addColumnQuantitationName(List<ColumnDefinition<?, ?>> list) {

		list.add(defaultSortableColumn(QUANTITATION_NAME, 80, new Function<QuantRatio, String>() {

			@Override
			public String apply(QuantRatio ratio) {

				return ratio.getName();
			}
		}).create());
	}

	private void addColumnExpectedConcentration(List<ColumnDefinition<?, ?>> list) {

		list.add(defaultSortableColumn(EXPECTED_CONCENTRATION, 80, new Function<QuantRatio, Double>() {

			@Override
			public Double apply(QuantRatio ratio) {

				return ratio.getExpectedConcentration();
			}
		}).create());
	}

	private void addColumnConcentration(List<ColumnDefinition<?, ?>> list) {

		list.add(defaultSortableColumn(CONCENTRATION, 80, new Function<QuantRatio, Double>() {

			@Override
			public Double apply(QuantRatio ratio) {

				return ratio.getConcentration();
			}
		}).create());
	}

	private void addColumnConcentrationUnit(List<ColumnDefinition<?, ?>> list) {

		list.add(defaultSortableColumn(CONCENTRATION_UNIT, 80, new Function<QuantRatio, String>() {

			@Override
			public String apply(QuantRatio ratio) {

				return ratio.getConcentrationUnit();
			}
		}).create());
	}

	private void addColumnDeviation(List<ColumnDefinition<?, ?>> list) {

		list.add(defaultSortableColumn(DEVIATION, 80, new Function<QuantRatio, Double>() {

			@Override
			public Double apply(QuantRatio ratio) {

				return ratio.getDeviation();
			}
		}).create());
	}

	private void addColumnResponseFactor(List<ColumnDefinition<?, ?>> list) {

		list.add(defaultSortableColumn(RESPONSE_FACTOR, 80, new Function<QuantRatio, Double>() {

			@Override
			public Double apply(QuantRatio ratio) {

				return ratio.getResponseFactor();
			}
		}).create());
	}
}