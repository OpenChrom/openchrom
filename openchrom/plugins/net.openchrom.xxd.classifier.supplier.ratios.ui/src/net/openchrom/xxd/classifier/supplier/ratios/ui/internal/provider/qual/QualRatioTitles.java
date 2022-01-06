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
package net.openchrom.xxd.classifier.supplier.ratios.ui.internal.provider.qual;

import static org.eclipse.chemclipse.support.ui.swt.columns.ColumnBuilder.defaultSortableColumn;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.function.Function;

import org.eclipse.chemclipse.model.core.IChromatogram;
import org.eclipse.chemclipse.model.core.IPeak;
import org.eclipse.chemclipse.model.targets.TargetSupport;
import org.eclipse.chemclipse.support.ui.swt.columns.ColumnDefinition;
import org.eclipse.chemclipse.support.ui.swt.columns.ColumnDefinitionProvider;

import net.openchrom.xxd.classifier.supplier.ratios.model.qual.PeakQuality;
import net.openchrom.xxd.classifier.supplier.ratios.model.qual.QualRatio;
import net.openchrom.xxd.classifier.supplier.ratios.ui.internal.provider.AbstractPeakRatioTitles;

public class QualRatioTitles extends AbstractPeakRatioTitles implements ColumnDefinitionProvider {

	public static final String BEST_TARGET = "Best Target";
	public static final String LEADING_TAILING = "Leading/Tailing";
	public static final String SIGNAL_TO_NOISE = "S/N";
	public static final String SYMMETRY = "Symmetry";

	@Override
	public Collection<? extends ColumnDefinition<?, ?>> getColumnDefinitions() {

		List<ColumnDefinition<?, ?>> list = new ArrayList<>();
		//
		addColumnRetentionTime(list);
		addColumnBestTarget(list);
		addColumnLeadingTailing(list);
		addColumnSignalToNoise(list);
		addColumnSymmetry(list);
		//
		return list;
	}

	private void addColumnRetentionTime(List<ColumnDefinition<?, ?>> list) {

		list.add(defaultSortableColumn(RETENTION_TIME, 150, new Function<QualRatio, Double>() {

			@Override
			public Double apply(QualRatio ratio) {

				IPeak peak = ratio.getPeak();
				if(peak != null) {
					return peak.getPeakModel().getRetentionTimeAtPeakMaximum() / IChromatogram.MINUTE_CORRELATION_FACTOR;
				} else {
					return null;
				}
			}
		}).create());
	}

	private void addColumnBestTarget(List<ColumnDefinition<?, ?>> list) {

		list.add(defaultSortableColumn(BEST_TARGET, 150, new Function<QualRatio, String>() {

			@Override
			public String apply(QualRatio ratio) {

				return TargetSupport.getBestTargetLibraryField(ratio.getPeak());
			}
		}).create());
	}

	private void addColumnLeadingTailing(List<ColumnDefinition<?, ?>> list) {

		list.add(defaultSortableColumn(LEADING_TAILING, 130, new Function<QualRatio, PeakQuality>() {

			@Override
			public PeakQuality apply(QualRatio ratio) {

				return ratio.getLeadingTailing();
			}
		}).create());
	}

	private void addColumnSignalToNoise(List<ColumnDefinition<?, ?>> list) {

		list.add(defaultSortableColumn(SIGNAL_TO_NOISE, 130, new Function<QualRatio, PeakQuality>() {

			@Override
			public PeakQuality apply(QualRatio ratio) {

				return ratio.getSignalToNoise();
			}
		}).create());
	}

	private void addColumnSymmetry(List<ColumnDefinition<?, ?>> list) {

		list.add(defaultSortableColumn(SYMMETRY, 130, new Function<QualRatio, PeakQuality>() {

			@Override
			public PeakQuality apply(QualRatio ratio) {

				return ratio.getSymmetry();
			}
		}).create());
	}
}