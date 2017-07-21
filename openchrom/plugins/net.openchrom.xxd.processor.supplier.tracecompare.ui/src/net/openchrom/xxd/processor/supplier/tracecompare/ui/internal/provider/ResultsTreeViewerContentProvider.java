/*******************************************************************************
 * Copyright (c) 2017 Lablicate GmbH.
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

public class ResultsTreeViewerContentProvider implements ITreeContentProvider {

	@Override
	public void dispose() {

	}

	@Override
	public void inputChanged(Viewer viewer, Object oldInput, Object newInput) {

	}

	@SuppressWarnings("rawtypes")
	@Override
	public Object[] getElements(Object inputElement) {

		if(inputElement instanceof List) {
			return ((List)inputElement).toArray();
		} else if(inputElement instanceof Collection) {
			return ((Collection)inputElement).toArray();
		} else if(inputElement instanceof IReferenceModel) {
			IReferenceModel referenceModel = (IReferenceModel)inputElement;
			return referenceModel.getTrackModels().values().toArray();
		}
		//
		return null;
	}

	@SuppressWarnings("rawtypes")
	@Override
	public Object[] getChildren(Object parentElement) {

		if(parentElement instanceof List) {
			return ((List)parentElement).toArray();
		} else if(parentElement instanceof Collection) {
			return ((Collection)parentElement).toArray();
		} else if(parentElement instanceof IReferenceModel) {
			IReferenceModel referenceModel = (IReferenceModel)parentElement;
			return referenceModel.getTrackModels().values().toArray();
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
		}
		return false;
	}
}
