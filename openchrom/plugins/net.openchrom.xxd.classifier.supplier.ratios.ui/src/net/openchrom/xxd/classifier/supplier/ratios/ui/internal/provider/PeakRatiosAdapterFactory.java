/*******************************************************************************
 * Copyright (c) 2019 Lablicate GmbH.
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

import org.eclipse.chemclipse.support.ui.swt.columns.ColumnDefinitionProvider;
import org.eclipse.core.runtime.IAdapterFactory;
import org.eclipse.jface.viewers.ISelectionChangedListener;
import org.eclipse.jface.viewers.IStructuredContentProvider;
import org.eclipse.jface.viewers.ITableLabelProvider;

import net.openchrom.xxd.classifier.supplier.ratios.model.quant.QuantRatios;
import net.openchrom.xxd.classifier.supplier.ratios.model.time.TimeRatios;
import net.openchrom.xxd.classifier.supplier.ratios.model.trace.TraceRatios;
import net.openchrom.xxd.classifier.supplier.ratios.ui.internal.provider.quant.QuantRatioLabelProvider;
import net.openchrom.xxd.classifier.supplier.ratios.ui.internal.provider.time.TimeRatioLabelProvider;
import net.openchrom.xxd.classifier.supplier.ratios.ui.internal.provider.time.TimeRatioResultTitles;
import net.openchrom.xxd.classifier.supplier.ratios.ui.internal.provider.trace.TraceRatioLabelProvider;
import net.openchrom.xxd.classifier.supplier.ratios.ui.internal.provider.trace.TraceRatioResultTitles;

public class PeakRatiosAdapterFactory implements IAdapterFactory {

	private static final PeakRatioSelectionListener SELECTION_LISTENER = new PeakRatioSelectionListener();
	private static final TimeRatioResultTitles TITLES_TIME = new TimeRatioResultTitles();
	private static final TraceRatioResultTitles TITLES_TRACE = new TraceRatioResultTitles();
	private static final DisplayOption OPTION = DisplayOption.RESULTS;
	private static final TimeRatioLabelProvider LABEL_PROVIDER_TIME = new TimeRatioLabelProvider(OPTION);
	private static final TraceRatioLabelProvider LABEL_PROVIDER_TRACE = new TraceRatioLabelProvider(OPTION);
	private static final PeakRatioContentProvider CONTENT_PROVIDER = new PeakRatioContentProvider(OPTION);
	private static final QuantRatioLabelProvider LABEL_PROVIDER_QUANT = new QuantRatioLabelProvider(OPTION);

	@Override
	public <T> T getAdapter(Object adaptableObject, Class<T> adapterType) {

		if(adapterType == IStructuredContentProvider.class) {
			return adapterType.cast(CONTENT_PROVIDER);
		}
		if(adapterType == ITableLabelProvider.class) {
			if(adaptableObject instanceof TraceRatios) {
				return adapterType.cast(LABEL_PROVIDER_TRACE);
			}
			if(adaptableObject instanceof QuantRatios) {
				return adapterType.cast(LABEL_PROVIDER_QUANT);
			}
			if(adaptableObject instanceof TimeRatios) {
				return adapterType.cast(LABEL_PROVIDER_TIME);
			}
		}
		if(adapterType == ColumnDefinitionProvider.class) {
			if(adaptableObject instanceof TraceRatios) {
				return adapterType.cast(TITLES_TRACE);
			}
			if(adaptableObject instanceof QuantRatios) {
				// return adapterType.cast(LABEL_PROVIDER_QUANT);
			}
			if(adaptableObject instanceof TimeRatios) {
				return adapterType.cast(TITLES_TIME);
			}
		}
		if(adapterType == ISelectionChangedListener.class) {
			return adapterType.cast(SELECTION_LISTENER);
		}
		return null;
	}

	@Override
	public Class<?>[] getAdapterList() {

		return new Class<?>[]{IStructuredContentProvider.class, ColumnDefinitionProvider.class, ITableLabelProvider.class, ISelectionChangedListener.class};
	}
}
