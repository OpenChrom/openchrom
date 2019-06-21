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
package net.openchrom.xxd.classifier.supplier.ratios.ui.internal.provider.trace;

import static org.eclipse.chemclipse.support.ui.swt.columns.ColumnBuilder.defaultSortableColumn;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.function.Function;

import org.eclipse.chemclipse.model.core.IChromatogram;
import org.eclipse.chemclipse.model.core.IPeak;
import org.eclipse.chemclipse.support.ui.swt.columns.ColumnDefinition;
import org.eclipse.chemclipse.support.ui.swt.columns.ColumnDefinitionProvider;

import net.openchrom.xxd.classifier.supplier.ratios.model.trace.TraceRatio;
import net.openchrom.xxd.classifier.supplier.ratios.ui.internal.provider.AbstractPeakRatioTitles;

public class TraceRatioResultTitles extends AbstractPeakRatioTitles implements ColumnDefinitionProvider {

	public static final String TEST_CASE = "Test Case";
	public static final String EXPECTED_RATIO = "Expected Ratio [%]";
	public static final String RATIO = "Ratio [%]";
	//
	public static final String[] TITLES_SETTINGS = { //
			NAME, //
			TEST_CASE, //
			EXPECTED_RATIO, //
			DEVIATION_WARN, //
			DEVIATION_ERROR //
	};
	//
	public static final int[] BOUNDS_SETTINGS = { //
			150, //
			120, //
			80, //
			80, //
			80 //
	};
	//
	public static final String[] TITLES_RESULTS = { //
			RETENTION_TIME, //
			NAME, //
			TEST_CASE, //
			EXPECTED_RATIO, //
			RATIO, //
			DEVIATION //
	};
	//
	public static final int[] BOUNDS_RESULTS = { //
			80, //
			130, //
			110, //
			80, //
			80, //
			80 //
	};

	@Override
	public Collection<? extends ColumnDefinition<?, ?>> getColumnDefinitions() {

		List<ColumnDefinition<?, ?>> list = new ArrayList<>();
		list.add(defaultSortableColumn(RETENTION_TIME, 80, new Function<TraceRatio, Double>() {

			@Override
			public Double apply(TraceRatio ratio) {

				IPeak peak = ratio.getPeak();
				if(peak != null) {
					return peak.getPeakModel().getRetentionTimeAtPeakMaximum() / IChromatogram.MINUTE_CORRELATION_FACTOR;
				} else {
					return null;
				}
			}
		}).create());
		list.add(defaultSortableColumn(NAME, 130, new Function<TraceRatio, String>() {

			@Override
			public String apply(TraceRatio ratio) {

				return ratio.getName();
			}
		}).create());
		list.add(defaultSortableColumn(TEST_CASE, 110, new Function<TraceRatio, String>() {

			@Override
			public String apply(TraceRatio ratio) {

				return ratio.getTestCase();
			}
		}).create());
		list.add(defaultSortableColumn(EXPECTED_RATIO, 80, new Function<TraceRatio, Double>() {

			@Override
			public Double apply(TraceRatio ratio) {

				return ratio.getExpectedRatio();
			}
		}).create());
		list.add(defaultSortableColumn(RATIO, 80, new Function<TraceRatio, Double>() {

			@Override
			public Double apply(TraceRatio ratio) {

				return ratio.getRatio();
			}
		}).create());
		list.add(defaultSortableColumn(DEVIATION, 80, new Function<TraceRatio, Double>() {

			@Override
			public Double apply(TraceRatio ratio) {

				return ratio.getDeviation();
			}
		}).create());
		return list;
	}
}
