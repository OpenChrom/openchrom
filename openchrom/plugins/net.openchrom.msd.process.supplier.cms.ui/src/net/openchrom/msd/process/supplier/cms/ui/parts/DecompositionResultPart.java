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
package net.openchrom.msd.process.supplier.cms.ui.parts;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import javax.inject.Inject;

import org.eclipse.e4.core.services.events.IEventBroker;
import org.eclipse.e4.ui.di.Focus;
import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.widgets.Composite;

import net.openchrom.msd.process.supplier.cms.core.DecompositionResults;
import net.openchrom.msd.process.supplier.cms.ui.EventDataHolder;
import net.openchrom.msd.process.supplier.cms.ui.parts.swt.DecompositionResultUI;
import net.openchrom.msd.process.supplier.cms.ui.parts.swt.IDecompositionResultsListener;

public class DecompositionResultPart {

	@Inject
	private Composite parent;
	@Inject
	private IEventBroker eventBroker;
	//
	private DecompositionResultUI decompositionResultUI;

	@PostConstruct
	private void createControl() {

		parent.setLayout(new FillLayout());
		decompositionResultUI = new DecompositionResultUI(parent, SWT.NONE);
		decompositionResultUI.addResultListener(new IDecompositionResultsListener() {

			@Override
			public void update(DecompositionResults decompositionResults) {

				EventDataHolder.setData(CompositeCompositionsPart.TOPIC_PROCESS_SUPPLIER_CMS_UPDATE_RESULT, decompositionResults);
				eventBroker.send(CompositeCompositionsPart.TOPIC_PROCESS_SUPPLIER_CMS_UPDATE_RESULT, decompositionResults);
			}
		});
	}

	@PreDestroy
	private void preDestroy() {

	}

	@Focus
	public void setFocus() {

		decompositionResultUI.setFocus();
	}
}
