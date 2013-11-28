/*******************************************************************************
 * Copyright (c) 2013 Dr. Philip Wenig.
 * 
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors:
 * Dr. Philip Wenig - initial API and implementation
 *******************************************************************************/
package net.openchrom.supplier.cdk.ui.views;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import javax.inject.Inject;

import org.eclipse.e4.core.services.events.IEventBroker;
import org.eclipse.e4.ui.di.Focus;
import org.eclipse.e4.ui.model.application.ui.basic.MPart;
import org.eclipse.e4.ui.workbench.modeling.EPartService;
import org.eclipse.jface.viewers.TableViewer;
import org.eclipse.jface.viewers.TableViewerColumn;
import org.eclipse.swt.SWT;
import org.eclipse.swt.dnd.Clipboard;
import org.eclipse.swt.dnd.TextTransfer;
import org.eclipse.swt.dnd.Transfer;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Table;
import org.eclipse.swt.widgets.TableColumn;
import org.eclipse.swt.widgets.TableItem;
import org.openscience.cdk.DefaultChemObjectBuilder;
import org.openscience.cdk.interfaces.IMolecularFormulaSet;
import org.osgi.service.event.Event;
import org.osgi.service.event.EventHandler;

import net.openchrom.logging.core.Logger;
import net.openchrom.supplier.cdk.core.CDKMassToFormula;
import net.openchrom.supplier.cdk.core.formula.GenericMassToFormulaBridge;
import net.openchrom.supplier.cdk.core.formula.GenericMassToFormulaTool;
import net.openchrom.supplier.cdk.ui.internal.provider.FormulaListContentProvider;
import net.openchrom.supplier.cdk.ui.internal.provider.FormulaListLabelProvider;
import net.openchrom.supplier.cdk.ui.internal.provider.FormulaListTableSorter;
import net.openchrom.supplier.cdk.ui.internal.provider.NameAndRating;
import net.openchrom.support.events.IOpenChromEvents;

public class FormulaCalculatorView {

	private static final Logger logger = Logger.getLogger(FormulaCalculatorView.class);
	private Label label;
	private TableViewer tableViewer;
	private FormulaListTableSorter formulaListTableSorter;
	private Clipboard clipboard;
	private String[] titles = {"Formula", "Ranking"};
	private int bounds[] = {200, 100};
	/*
	 * Unix: \n Windows: \r\n Mac OSX: \r
	 */
	private static final String DELIMITER = "\t";
	private static final String END_OF_LINE = "\r\n";
	//
	@Inject
	private Composite parent;
	/*
	 * Event Handler
	 */
	@Inject
	private EPartService partService;
	@Inject
	private MPart part;
	private IEventBroker eventBroker;
	private EventHandler eventHandler;

	@Inject
	public FormulaCalculatorView(IEventBroker eventBroker, EventHandler eventHandler) {

		this.eventBroker = eventBroker;
		this.eventHandler = eventHandler;
		subscribe();
	}

	@PostConstruct
	private void createControl() {

		clipboard = new Clipboard(Display.getDefault());
		parent.setLayout(new FillLayout());
		Composite composite = new Composite(parent, SWT.NONE);
		composite.setLayout(new GridLayout(1, true));
		/*
		 * Label Ion
		 */
		label = new Label(composite, SWT.NONE);
		label.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));
		/*
		 * Formula table
		 */
		tableViewer = new TableViewer(composite, SWT.MULTI | SWT.H_SCROLL | SWT.V_SCROLL);
		tableViewer.getControl().setLayoutData(new GridData(GridData.FILL_BOTH));
		createColumns(tableViewer);
		tableViewer.setContentProvider(new FormulaListContentProvider());
		tableViewer.setLabelProvider(new FormulaListLabelProvider());
		/*
		 * Sorting the table.
		 */
		formulaListTableSorter = new FormulaListTableSorter();
		tableViewer.setSorter(formulaListTableSorter);
	}

	@PreDestroy
	private void preDestroy() {

		unsubscribe();
	}

	@Focus
	public void setFocus() {

		tableViewer.getControl().setFocus();
	}

	private void update(Double ion) {

		if(isPartVisible()) {
			GenericMassToFormulaBridge massToFormula = new GenericMassToFormulaBridge();
			IMolecularFormulaSet formulas;
			formulas = massToFormula.generate(ion);
			List<String> formulaNames;
			if(formulas != null) {
				formulaNames = massToFormula.getNames(formulas);
			} else {
				formulaNames = new ArrayList<String>();
			}
			List<Double> formulaRatings;
			if(formulas != null) {
				formulaRatings = massToFormula.getRatings(ion, formulas);
			} else {
				formulaRatings = new ArrayList<Double>();
			}
			List<NameAndRating> formulaNamesAndRatings = new ArrayList<NameAndRating>();
			for(int i = 0; i < formulaNames.size() && i < formulaRatings.size(); i++) {
				String formulaName = formulaNames.get(i);
				Double formulaRating = formulaRatings.get(i);
				NameAndRating nameAndRating = new NameAndRating(formulaName, formulaRating);
				formulaNamesAndRatings.add(nameAndRating);
			}
			label.setText("Selected ion: " + ion);
			tableViewer.setInput(formulaNamesAndRatings);
		}
	}

	private boolean isPartVisible() {

		if(partService != null && partService.isPartVisible(part)) {
			return true;
		}
		return false;
	}

	private void unsubscribe() {

		if(eventBroker != null && eventHandler != null) {
			eventBroker.unsubscribe(eventHandler);
		}
	}

	private void subscribe() {

		if(eventBroker != null) {
			/*
			 * Receives and handles chromatogram selection updates.
			 */
			eventHandler = new EventHandler() {

				public void handleEvent(Event event) {

					try {
						Double ion = (Double)event.getProperty(IOpenChromEvents.PROPERTY_SELECTED_ION);
						update(ion);
					} catch(Exception e) {
						logger.warn(e);
					}
				}
			};
			eventBroker.subscribe(IOpenChromEvents.TOPIC_CHROMATOGRAM_MSD_UPDATE_ION_SELECTION, eventHandler);
		}
	}

	private void createColumns(final TableViewer tableViewer) {

		/*
		 * SYNCHRONIZE: PeakListLabelProvider PeakListLabelSorter PeakListView
		 */
		/*
		 * Set the titles and bounds.
		 */
		for(int i = 0; i < titles.length; i++) {
			/*
			 * Column sort.
			 */
			final int index = i;
			final TableViewerColumn tableViewerColumn = new TableViewerColumn(tableViewer, SWT.NONE);
			final TableColumn tableColumn = tableViewerColumn.getColumn();
			tableColumn.setText(titles[i]);
			tableColumn.setWidth(bounds[i]);
			tableColumn.setResizable(true);
			tableColumn.setMoveable(true);
			tableColumn.addSelectionListener(new SelectionAdapter() {

				@Override
				public void widgetSelected(SelectionEvent e) {

					formulaListTableSorter.setColumn(index);
					int direction = tableViewer.getTable().getSortDirection();
					if(tableViewer.getTable().getSortColumn() == tableColumn) {
						/*
						 * Toggle the direction
						 */
						direction = (direction == SWT.UP) ? SWT.DOWN : SWT.UP;
					} else {
						direction = SWT.UP;
					}
					tableViewer.getTable().setSortDirection(direction);
					tableViewer.getTable().setSortColumn(tableColumn);
					tableViewer.refresh();
				}
			});
		}
		/*
		 * Set header and lines visible.
		 */
		Table table = tableViewer.getTable();
		table.setHeaderVisible(true);
		table.setLinesVisible(true);
	}

	/**
	 * Copies the actual selection to the clipboard.
	 */
	public void copyToClipboard() {

		/*
		 * SYNCHRONIZE: PeakListLabelProvider PeakListLabelSorter PeakListView
		 */
		StringBuilder builder = new StringBuilder();
		int size = titles.length;
		/*
		 * Header
		 */
		for(String title : titles) {
			builder.append(title);
			builder.append(DELIMITER);
		}
		builder.append(END_OF_LINE);
		/*
		 * Copy the selected items.
		 */
		TableItem selection;
		Table table = tableViewer.getTable();
		for(int index : table.getSelectionIndices()) {
			/*
			 * Get the nth selected item.
			 */
			selection = table.getItem(index);
			/*
			 * Dump all elements of the item, e.g. RT, Abundance, ... .
			 */
			for(int columnIndex = 0; columnIndex < size; columnIndex++) {
				builder.append(selection.getText(columnIndex));
				builder.append(DELIMITER);
			}
			builder.append(END_OF_LINE);
		}
		/*
		 * If the builder is empty, give a note that items needs to be selected.
		 */
		if(builder.length() == 0) {
			builder.append("Please select one or more entries in the list.");
			builder.append(END_OF_LINE);
		}
		/*
		 * Transfer the selected text (items) to the clipboard.
		 */
		TextTransfer textTransfer = TextTransfer.getInstance();
		Object[] data = new Object[]{builder.toString()};
		Transfer[] dataTypes = new Transfer[]{textTransfer};
		clipboard.setContents(data, dataTypes);
	}
}
