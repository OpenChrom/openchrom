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

import java.util.List;

import org.eclipse.jface.viewers.ITreeContentProvider;
import org.eclipse.jface.viewers.Viewer;

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
		}
		//
		return null;
	}

	@Override
	public Object[] getChildren(Object parentElement) {

		// TODO
		//
		return null;
	}

	@Override
	public Object getParent(Object element) {

		// TODO
		//
		return null;
	}

	@Override
	public boolean hasChildren(Object element) {

		// TODO
		return false;
	}
}
