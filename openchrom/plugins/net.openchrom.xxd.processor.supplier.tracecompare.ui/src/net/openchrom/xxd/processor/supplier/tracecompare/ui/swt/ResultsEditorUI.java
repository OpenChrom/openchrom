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
package net.openchrom.xxd.processor.supplier.tracecompare.ui.swt;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.chemclipse.logging.core.Logger;
import org.eclipse.chemclipse.rcp.ui.icons.core.ApplicationImageFactory;
import org.eclipse.chemclipse.rcp.ui.icons.core.IApplicationImage;
import org.eclipse.core.runtime.preferences.IEclipsePreferences;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.KeyAdapter;
import org.eclipse.swt.events.KeyEvent;
import org.eclipse.swt.events.ModifyEvent;
import org.eclipse.swt.events.ModifyListener;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.graphics.Font;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Text;
import org.osgi.service.prefs.BackingStoreException;

import net.openchrom.xxd.processor.supplier.tracecompare.model.IProcessorModel;
import net.openchrom.xxd.processor.supplier.tracecompare.model.IReferenceModel;
import net.openchrom.xxd.processor.supplier.tracecompare.model.ITrackModel;
import net.openchrom.xxd.processor.supplier.tracecompare.preferences.PreferenceSupplier;
import net.openchrom.xxd.processor.supplier.tracecompare.ui.editors.EditorProcessor;

public class ResultsEditorUI extends Composite {

	private static final Logger logger = Logger.getLogger(ResultsEditorUI.class);
	//
	private Label labelSample;
	private Text textSearch;
	private Text textGeneralNotes;
	private ResultsTreeViewerUI resultsTreeViewerUI;
	private Text textCalculatedResult;
	//
	private IProcessorModel processorModel;

	public ResultsEditorUI(Composite parent, int style) {
		super(parent, style);
		initialize(parent);
	}

	public void update(Object object) {

		if(object instanceof EditorProcessor) {
			EditorProcessor editorProcessor = (EditorProcessor)object;
			processorModel = editorProcessor.getProcessorModel();
			//
			labelSample.setText("Unknown Sample: ..."); // TODO processorModel.getSampleGroup()
			textGeneralNotes.setText(processorModel.getGeneralNotes());
			textCalculatedResult.setText(processorModel.getCalculatedResult());
			resultsTreeViewerUI.setInput(processorModel.getReferenceModels().values());
		}
	}

	private void initialize(Composite parent) {

		setLayout(new FillLayout());
		Composite composite = new Composite(this, SWT.FILL);
		composite.setLayout(new GridLayout(5, false));
		/*
		 * Elements
		 */
		createLabelSample(composite);
		createCalculatedResultField(composite);
		createGeneralNotes(composite);
		createSearchField(composite);
		createResultsList(composite);
	}

	private void createLabelSample(Composite parent) {

		labelSample = new Label(parent, SWT.NONE);
		Display display = Display.getDefault();
		Font font = new Font(display, "Arial", 14, SWT.BOLD);
		labelSample.setFont(font);
		labelSample.setText("Unknown Sample:");
		labelSample.setLayoutData(getGridData(GridData.FILL_HORIZONTAL));
		font.dispose();
	}

	private void createSearchField(Composite parent) {

		textSearch = new Text(parent, SWT.BORDER);
		textSearch.setText("Stichworte...");
		textSearch.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));
		textSearch.addKeyListener(new KeyAdapter() {

			@Override
			public void keyReleased(KeyEvent e) {

				/*
				 * Use the enter button.
				 */
				if(e.stateMask == 0) {
					if(e.keyCode == SWT.KEYPAD_CR || e.keyCode == 13) {
						search();
					}
				}
			}
		});
		//
		Button buttonSearch = new Button(parent, SWT.PUSH);
		buttonSearch.setText("Suchen");
		buttonSearch.setImage(ApplicationImageFactory.getInstance().getImage(IApplicationImage.IMAGE_SEARCH, IApplicationImage.SIZE_16x16));
		buttonSearch.addSelectionListener(new SelectionAdapter() {

			@Override
			public void widgetSelected(SelectionEvent e) {

				search();
			}
		});
		//
		Button buttonExpandAll = new Button(parent, SWT.PUSH);
		buttonExpandAll.setText("");
		buttonExpandAll.setImage(ApplicationImageFactory.getInstance().getImage(IApplicationImage.IMAGE_EXPAND_ALL, IApplicationImage.SIZE_16x16));
		buttonExpandAll.addSelectionListener(new SelectionAdapter() {

			@Override
			public void widgetSelected(SelectionEvent e) {

				resultsTreeViewerUI.getTreeViewer().expandAll();
			}
		});
		//
		Button buttonCollapseAll = new Button(parent, SWT.PUSH);
		buttonCollapseAll.setText("");
		buttonCollapseAll.setImage(ApplicationImageFactory.getInstance().getImage(IApplicationImage.IMAGE_COLLAPSE_ALL, IApplicationImage.SIZE_16x16));
		buttonCollapseAll.addSelectionListener(new SelectionAdapter() {

			@Override
			public void widgetSelected(SelectionEvent e) {

				resultsTreeViewerUI.getTreeViewer().collapseAll();
			}
		});
		//
		final IEclipsePreferences preferences = PreferenceSupplier.INSTANCE().getPreferences();
		final Button buttonCaseSensitive = new Button(parent, SWT.CHECK);
		buttonCaseSensitive.setText("Case sensitive");
		buttonCaseSensitive.setSelection(preferences.getBoolean(PreferenceSupplier.P_SEARCH_CASE_SENSITIVE, PreferenceSupplier.DEF_SEARCH_CASE_SENSITIVE));
		buttonCaseSensitive.addSelectionListener(new SelectionAdapter() {

			@Override
			public void widgetSelected(SelectionEvent e) {

				try {
					preferences.put(PreferenceSupplier.P_SEARCH_CASE_SENSITIVE, Boolean.toString(buttonCaseSensitive.getSelection()));
					preferences.flush();
				} catch(BackingStoreException e1) {
					logger.warn(e1);
				}
			}
		});
	}

	private void createGeneralNotes(Composite parent) {

		Label label = new Label(parent, SWT.NONE);
		label.setText("General notes:");
		label.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));
		//
		textGeneralNotes = new Text(parent, SWT.BORDER | SWT.MULTI | SWT.V_SCROLL | SWT.H_SCROLL);
		textGeneralNotes.setText("");
		GridData gridData = getGridData(GridData.FILL_HORIZONTAL);
		gridData.heightHint = 100;
		textGeneralNotes.setLayoutData(gridData);
		textGeneralNotes.addModifyListener(new ModifyListener() {

			@Override
			public void modifyText(ModifyEvent e) {

				processorModel.setGeneralNotes(textGeneralNotes.getText().trim());
			}
		});
	}

	private void createResultsList(Composite parent) {

		resultsTreeViewerUI = new ResultsTreeViewerUI(parent, SWT.NONE);
		resultsTreeViewerUI.setLayoutData(getGridData(GridData.FILL_BOTH));
	}

	private void createCalculatedResultField(Composite parent) {

		Label label = new Label(parent, SWT.NONE);
		label.setText("Calculated result:");
		label.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));
		//
		textCalculatedResult = new Text(parent, SWT.BORDER);
		textCalculatedResult.setText("");
		textCalculatedResult.setLayoutData(getGridData(GridData.FILL_HORIZONTAL));
		textCalculatedResult.addModifyListener(new ModifyListener() {

			@Override
			public void modifyText(ModifyEvent e) {

				processorModel.setCalculatedResult(textCalculatedResult.getText().trim());
			}
		});
	}

	private void search() {

		String searchText = textSearch.getText().trim();
		boolean searchCaseSensitive = PreferenceSupplier.isSearchCaseSensitive();
		if(!searchCaseSensitive) {
			searchText = searchText.toLowerCase();
		}
		//
		if(searchText.equals("")) {
			/*
			 * Default
			 */
			resultsTreeViewerUI.setInput(processorModel.getReferenceModels().values());
		} else {
			/*
			 * Search
			 */
			List<ITrackModel> trackModels = new ArrayList<ITrackModel>();
			for(IReferenceModel referenceModel : processorModel.getReferenceModels().values()) {
				for(ITrackModel trackModel : referenceModel.getTrackModels().values()) {
					/*
					 * Prepare
					 */
					String content = trackModel.toString();
					if(!searchCaseSensitive) {
						content = content.toLowerCase();
					}
					/*
					 * Search
					 */
					if(content.contains(searchText)) {
						trackModels.add(trackModel);
					}
				}
			}
			resultsTreeViewerUI.setInput(trackModels);
		}
	}

	private GridData getGridData(int style) {

		GridData gridData = new GridData(style);
		gridData.horizontalSpan = 5;
		return gridData;
	}
}
