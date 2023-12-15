/*******************************************************************************
 * Copyright (c) 2017, 2023 Lablicate GmbH.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors:
 * Dr. Philip Wenig - initial API and implementation
 *******************************************************************************/
package net.openchrom.xxd.processor.supplier.tracecompare.ui.internal.provider;

import java.util.Collection;
import java.util.List;

import org.eclipse.jface.viewers.ITreeContentProvider;
import org.eclipse.jface.viewers.Viewer;

import net.openchrom.xxd.processor.supplier.tracecompare.model.IReferenceModel;
import net.openchrom.xxd.processor.supplier.tracecompare.model.ISampleModel;

public class ResultsTreeViewerContentProvider implements ITreeContentProvider {

	@Override
	public void dispose() {

	}

	@Override
	public void inputChanged(Viewer viewer, Object oldInput, Object newInput) {

	}

	@Override
	public Object[] getElements(Object inputElement) {

		if(inputElement instanceof List<?> list) {
			return list.toArray();
		} else if(inputElement instanceof Collection<?> collection) {
			return collection.toArray();
		} else if(inputElement instanceof IReferenceModel referenceModel) {
			return referenceModel.getSampleModels().values().toArray();
		} else if(inputElement instanceof ISampleModel sampleModel) {
			return sampleModel.getTrackModels().values().toArray();
		}
		//
		return null;
	}

	@Override
	public Object[] getChildren(Object parentElement) {

		if(parentElement instanceof List<?> list) {
			return list.toArray();
		} else if(parentElement instanceof Collection<?> collection) {
			return collection.toArray();
		} else if(parentElement instanceof IReferenceModel referenceModel) {
			return referenceModel.getSampleModels().values().toArray();
		} else if(parentElement instanceof ISampleModel sampleModel) {
			return sampleModel.getTrackModels().values().toArray();
		}
		//
		return null;
	}

	@Override
	public Object getParent(Object element) {

		return null;
	}

	@Override
	public boolean hasChildren(Object element) {

		if(element instanceof List) {
			return true;
		} else if(element instanceof Collection) {
			return true;
		} else if(element instanceof IReferenceModel) {
			return true;
		} else if(element instanceof ISampleModel) {
			return true;
		}
		return false;
	}
}
