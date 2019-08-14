/*******************************************************************************
 * Copyright (c) 2014, 2018 Dr. Philip Wenig, Matthias Mailänder.
 * 
 * All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0 which accompanies this distribution,
 * and is available at http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors:
 * Dr. Philip Wenig - initial API and implementation
 * Matthias Mailänder - initial API and implementation
 *******************************************************************************/
package net.openchrom.msd.identifier.supplier.opentyper.ui.views;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import javax.inject.Inject;

import org.eclipse.chemclipse.msd.model.core.IScanMSD;
import org.eclipse.chemclipse.msd.model.core.IVendorMassSpectrum;
import org.eclipse.chemclipse.ux.extension.msd.ui.views.AbstractMassSpectrumSelectionView;
import org.eclipse.e4.core.services.events.IEventBroker;
import org.eclipse.e4.ui.di.Focus;
import org.eclipse.e4.ui.model.application.ui.basic.MPart;
import org.eclipse.e4.ui.workbench.modeling.EPartService;
import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Text;

public class MassSpectrumHeaderView extends AbstractMassSpectrumSelectionView {

	@Inject
	private Composite parent;
	@Inject
	private MPart part;
	@Inject
	private EPartService partService;
	private Text text;

	@Inject
	public MassSpectrumHeaderView(EPartService partService, MPart part, IEventBroker eventBroker) {
		super(part, partService, eventBroker);
	}

	private void updateMassSpectrum(IVendorMassSpectrum massSpectrum) {

		if(partService.isPartVisible(part) && massSpectrum != null) {
			StringBuilder builder = new StringBuilder();
			addHeaderLine(builder, "Name", massSpectrum.getName());
			addHeaderLine(builder, "Type Description", massSpectrum.getMassSpectrumTypeDescription());
			addHeaderLine(builder, "File", massSpectrum.getFile().getName());
			addHeaderLine(builder, "Mass Spectrometer", "MS" + massSpectrum.getMassSpectrometer());
			addHeaderLine(builder, "Ions", Integer.toString(massSpectrum.getNumberOfIons()));
			text.setText(builder.toString());
		}
	}

	private void addHeaderLine(StringBuilder builder, String key, String value) {

		builder.append(key);
		builder.append(": ");
		builder.append(value);
		builder.append("\n");
	}

	@PostConstruct
	private void createControl() {

		parent.setLayout(new FillLayout());
		text = new Text(parent, SWT.BORDER | SWT.MULTI | SWT.V_SCROLL | SWT.H_SCROLL);
	}

	@PreDestroy
	private void preDestroy() {

		unsubscribe();
	}

	@Focus
	public void setFocus() {

		text.setFocus();
		update(getMassSpectrum(), false);
	}

	@Override
	public void update(IScanMSD massSpectrum, boolean forceReload) {

		/*
		 * Update the ui only if the actual view part is visible and the
		 * selection is not null.
		 */
		if(massSpectrum != null && isPartVisible()) {
			updateMassSpectrum((IVendorMassSpectrum)massSpectrum);
		}
	}
}
