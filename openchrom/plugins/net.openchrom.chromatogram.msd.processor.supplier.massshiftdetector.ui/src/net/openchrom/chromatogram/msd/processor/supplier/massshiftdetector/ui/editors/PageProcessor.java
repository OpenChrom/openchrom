/*******************************************************************************
 * Copyright (c) 2017 Lablicate GmbH.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * Contributors:
 * Dr. Philip Wenig - initial API and implementation
 *******************************************************************************/
package net.openchrom.chromatogram.msd.processor.supplier.massshiftdetector.ui.editors;

import org.eclipse.chemclipse.rcp.ui.icons.core.ApplicationImageFactory;
import org.eclipse.chemclipse.rcp.ui.icons.core.IApplicationImage;
import org.eclipse.chemclipse.support.ui.editors.AbstractExtendedEditorPage;
import org.eclipse.chemclipse.support.ui.editors.IExtendedEditorPage;
import org.eclipse.chemclipse.swt.ui.support.Colors;
import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Label;
import org.eclipse.ui.forms.events.HyperlinkAdapter;
import org.eclipse.ui.forms.events.HyperlinkEvent;
import org.eclipse.ui.forms.widgets.ImageHyperlink;
import org.eclipse.ui.forms.widgets.ScrolledForm;
import org.eclipse.ui.forms.widgets.Section;
import org.eclipse.ui.forms.widgets.TableWrapLayout;

public class PageProcessor extends AbstractExtendedEditorPage implements IExtendedEditorPage {

	private ImageHyperlink hyperlinkProcess;
	//
	private Label labelName;
	private Label labelEvaluationDate;
	private Label labelDescription;
	private Label labelProcessorNames;
	private Label labelNotes;

	public PageProcessor(Composite container) {
		super("Mass Shift Detector", container, true);
	}

	@Override
	public void fillBody(ScrolledForm scrolledForm) {

		Composite body = scrolledForm.getBody();
		body.setLayout(new TableWrapLayout());
		body.setLayout(createFormTableWrapLayout(true, 3));
		/*
		 * 3 column layout
		 */
		createInfoSection(body);
		createProcessSection(body);
	}

	private void createInfoSection(Composite parent) {

		Section section = createSection(parent, 3);
		Composite client = createClientInfo(section);
		//
		labelName = createLabel(client, "");
		labelName.setLayoutData(new GridData(GridData.FILL_BOTH));
		labelEvaluationDate = createLabel(client, "");
		labelEvaluationDate.setLayoutData(new GridData(GridData.FILL_BOTH));
		labelDescription = createLabel(client, "");
		labelDescription.setLayoutData(new GridData(GridData.FILL_BOTH));
		labelProcessorNames = createLabel(client, "");
		labelProcessorNames.setLayoutData(new GridData(GridData.FILL_BOTH));
		labelNotes = createLabel(client, "");
		labelNotes.setBackground(Colors.YELLOW);
		labelNotes.setLayoutData(new GridData(GridData.FILL_BOTH));
		/*
		 * Add the client to the section.
		 */
		section.setClient(client);
	}

	private void createProcessSection(Composite parent) {

		Section section = createSection(parent, 3, "Open Raw File", "Just click on the button to open the evaluated chromatogram.");
		Composite client = createClient(section);
		/*
		 * Edit
		 */
		createLabel(client, "Open Chromatogram");
		hyperlinkProcess = createProcessHyperlink(client);
		hyperlinkProcess.setText("");
		/*
		 * Add the client to the section.
		 */
		section.setClient(client);
	}

	private ImageHyperlink createProcessHyperlink(Composite client) {

		ImageHyperlink imageHyperlink = getFormToolkit().createImageHyperlink(client, SWT.NONE);
		imageHyperlink.setImage(ApplicationImageFactory.getInstance().getImage(IApplicationImage.IMAGE_EXECUTE, IApplicationImage.SIZE_16x16));
		imageHyperlink.setText("");
		GridData gridData = new GridData(GridData.FILL_HORIZONTAL);
		gridData.horizontalIndent = HORIZONTAL_INDENT;
		imageHyperlink.setLayoutData(gridData);
		imageHyperlink.addHyperlinkListener(new HyperlinkAdapter() {

			public void linkActivated(HyperlinkEvent e) {

			}
		});
		return imageHyperlink;
	}
}
