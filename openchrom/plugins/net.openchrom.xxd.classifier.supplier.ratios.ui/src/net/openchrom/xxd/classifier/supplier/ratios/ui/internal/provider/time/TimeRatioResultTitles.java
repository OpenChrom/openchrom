/*******************************************************************************
 * Copyright (c) 2019, 2022 Lablicate GmbH.
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
package net.openchrom.xxd.classifier.supplier.ratios.ui.internal.provider.time;

import static org.eclipse.chemclipse.support.ui.swt.columns.ColumnBuilder.defaultSortableColumn;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.function.Function;

import org.eclipse.chemclipse.model.core.IChromatogram;
import org.eclipse.chemclipse.model.core.IPeak;
import org.eclipse.chemclipse.support.ui.swt.columns.ColumnDefinition;
import org.eclipse.chemclipse.support.ui.swt.columns.ColumnDefinitionProvider;

import net.openchrom.xxd.classifier.supplier.ratios.model.time.TimeRatio;
import net.openchrom.xxd.classifier.supplier.ratios.ui.internal.provider.AbstractPeakRatioTitles;

public class TimeRatioResultTitles extends AbstractPeakRatioTitles implements ColumnDefinitionProvider {

	public static final String EXPECTED_RETENTION_TIME = "Expected Retention Time [min]";
	//
	public static final String[] TITLES_SETTINGS = { //
			NAME, //
			EXPECTED_RETENTION_TIME, //
			DEVIATION_WARN, //
			DEVIATION_ERROR //
	};
	//
	public static final int[] BOUNDS_SETTINGS = { //
			150, //
			120, //
			80, //
			80 //
	};
	//
	public static final String[] TITLES_RESULTS = { //
			RETENTION_TIME, //
			NAME, //
			EXPECTED_RETENTION_TIME, //
			DEVIATION //
	};
	//
	public static final int[] BOUNDS_RESULTS = { //
			80, //
			150, //
			80, //
			80 //
	};

	@Override
	public Collection<? extends ColumnDefinition<?, ?>> getColumnDefinitions() {

		List<ColumnDefinition<?, ?>> list = new ArrayList<>();
		//
		list.add(defaultSortableColumn(RETENTION_TIME, 80, new Function<TimeRatio, Double>() {

			@Override
			public Double apply(TimeRatio ratio) {

				IPeak peak = ratio.getPeak();
				if(peak != null) {
					return peak.getPeakModel().getRetentionTimeAtPeakMaximum() / IChromatogram.MINUTE_CORRELATION_FACTOR;
				} else {
					return null;
				}
			}
		}).create());
		//
		list.add(defaultSortableColumn(NAME, 150, new Function<TimeRatio, String>() {

			@Override
			public String apply(TimeRatio ratio) {

				return ratio.getName();
			}
		}).create());
		//
		list.add(defaultSortableColumn(EXPECTED_RETENTION_TIME, 80, new Function<TimeRatio, Integer>() {

			@Override
			public Integer apply(TimeRatio ratio) {

				return ratio.getExpectedRetentionTime();
			}
		}).create());
		//
		list.add(defaultSortableColumn(DEVIATION, 80, new Function<TimeRatio, Double>() {

			@Override
			public Double apply(TimeRatio ratio) {

				return ratio.getDeviation();
			}
		}).create());
		//
		return list;
	}
}
