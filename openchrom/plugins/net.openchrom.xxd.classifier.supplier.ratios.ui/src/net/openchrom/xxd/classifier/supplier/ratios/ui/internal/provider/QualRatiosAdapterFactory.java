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

import net.openchrom.xxd.classifier.supplier.ratios.model.qual.QualRatios;
import net.openchrom.xxd.classifier.supplier.ratios.ui.internal.provider.qual.QualRatioContentProvider;
import net.openchrom.xxd.classifier.supplier.ratios.ui.internal.provider.qual.QualRatioLabelProvider;
import net.openchrom.xxd.classifier.supplier.ratios.ui.internal.provider.qual.QualRatioSelectionListener;
import net.openchrom.xxd.classifier.supplier.ratios.ui.internal.provider.qual.QualRatioTitles;

public class QualRatiosAdapterFactory implements IAdapterFactory {

	private static final QualRatioTitles TITLES_QUAL = new QualRatioTitles();
	private static final QualRatioSelectionListener SELECTION_LISTENER = new QualRatioSelectionListener();
	private static final QualRatioContentProvider CONTENT_PROVIDER = new QualRatioContentProvider();
	private static final QualRatioLabelProvider LABEL_PROVIDER_QUAL = new QualRatioLabelProvider();

	@Override
	public <T> T getAdapter(Object adaptableObject, Class<T> adapterType) {

		if(adapterType == IStructuredContentProvider.class) {
			return adapterType.cast(CONTENT_PROVIDER);
		}
		if(adapterType == ITableLabelProvider.class) {
			if(adaptableObject instanceof QualRatios) {
				return adapterType.cast(LABEL_PROVIDER_QUAL);
			}
		}
		if(adapterType == ColumnDefinitionProvider.class) {
			if(adaptableObject instanceof QualRatios) {
				return adapterType.cast(TITLES_QUAL);
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
