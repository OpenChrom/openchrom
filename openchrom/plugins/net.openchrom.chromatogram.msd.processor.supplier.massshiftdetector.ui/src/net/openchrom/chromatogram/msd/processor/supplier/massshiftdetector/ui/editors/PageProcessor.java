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
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Spinner;
import org.eclipse.swt.widgets.Text;
import org.eclipse.ui.forms.events.HyperlinkAdapter;
import org.eclipse.ui.forms.events.HyperlinkEvent;
import org.eclipse.ui.forms.widgets.ImageHyperlink;
import org.eclipse.ui.forms.widgets.ScrolledForm;
import org.eclipse.ui.forms.widgets.Section;
import org.eclipse.ui.forms.widgets.TableWrapLayout;

public class PageProcessor extends AbstractExtendedEditorPage implements IExtendedEditorPage {

	private Text c12ChromatogramText;
	private Text c13ChromatogramText;
	private Spinner shiftsSpinner;
	private Label labelNotes;
	//
	private ImageHyperlink hyperlinkProcess;

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
		createPropertiesSection(body);
		createProcessSection(body);
	}

	private void createPropertiesSection(Composite parent) {

		Section section = createSection(parent, 3, "Settings", "The selected settings will be used for the current analysis.");
		Composite client = createClient(section, 3);
		//
		createC12ChromatogramText(client);
		createC13ChromatogramText(client);
		createShiftsSpinner(client);
		createNotesLabel(client);
		/*
		 * Add the client to the section.
		 */
		section.setClient(client);
	}

	private void createC12ChromatogramText(Composite client) {

		createLabel(client, "C12 - Chromatogram");
		//
		c12ChromatogramText = createText(client, SWT.BORDER, "");
		c12ChromatogramText.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));
		//
		Button button = new Button(client, SWT.PUSH);
		button.setText("Select Chromatogram");
	}

	private void createC13ChromatogramText(Composite client) {

		createLabel(client, "C13 - Chromatogram");
		//
		c13ChromatogramText = createText(client, SWT.BORDER, "");
		c13ChromatogramText.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));
		//
		Button button = new Button(client, SWT.PUSH);
		button.setText("Select Chromatogram");
	}

	private void createShiftsSpinner(Composite client) {

		createLabel(client, "Number of shifts");
		//
		shiftsSpinner = new Spinner(client, SWT.NONE);
		shiftsSpinner.setMinimum(1);
		shiftsSpinner.setMaximum(5);
		shiftsSpinner.setIncrement(1);
		//
		GridData gridData = new GridData();
		gridData.horizontalSpan = 2;
		gridData.widthHint = 50;
		gridData.heightHint = 20;
		shiftsSpinner.setLayoutData(gridData);
	}

	private void createNotesLabel(Composite client) {

		labelNotes = createLabel(client, "Please have a look at the retention time range 5 - 10 minutes.");
		labelNotes.setBackground(Colors.YELLOW);
		GridData gridData = new GridData(GridData.FILL_HORIZONTAL);
		gridData.horizontalSpan = 3;
		labelNotes.setLayoutData(gridData);
	}

	private void createProcessSection(Composite parent) {

		Section section = createSection(parent, 3, "Process", "The selected chromatograms are processed with the given settings to detect mass shifts.");
		Composite client = createClient(section);
		/*
		 * Edit
		 */
		hyperlinkProcess = createProcessHyperlink(client);
		hyperlinkProcess.setText("Process Chromatograms");
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
