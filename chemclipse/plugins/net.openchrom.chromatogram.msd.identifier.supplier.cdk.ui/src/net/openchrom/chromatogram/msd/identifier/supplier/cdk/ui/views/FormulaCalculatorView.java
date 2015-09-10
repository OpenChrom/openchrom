/*******************************************************************************
 * Copyright (c) 2013, 2015 Dr. Philip Wenig.
 * 
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors:
 * Dr. Philip Wenig - initial API and implementation
 *******************************************************************************/
package net.openchrom.chromatogram.msd.identifier.supplier.cdk.ui.views;

import java.util.List;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import javax.inject.Inject;

import org.eclipse.e4.core.services.events.IEventBroker;
import org.eclipse.e4.ui.di.Focus;
import org.eclipse.e4.ui.model.application.ui.basic.MPart;
import org.eclipse.e4.ui.workbench.modeling.EPartService;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.KeyAdapter;
import org.eclipse.swt.events.KeyEvent;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Label;
import org.osgi.service.event.Event;
import org.osgi.service.event.EventHandler;
import org.eclipse.chemclipse.logging.core.Logger;
import org.eclipse.chemclipse.support.events.IChemClipseEvents;
import org.eclipse.chemclipse.support.ui.swt.viewers.ExtendedTableViewer;

import net.openchrom.chromatogram.msd.identifier.supplier.cdk.formula.NameAndRating;
import net.openchrom.chromatogram.msd.identifier.supplier.cdk.support.FormulaSupport;
import net.openchrom.chromatogram.msd.identifier.supplier.cdk.ui.internal.provider.FormulaListContentProvider;
import net.openchrom.chromatogram.msd.identifier.supplier.cdk.ui.internal.provider.FormulaListLabelProvider;
import net.openchrom.chromatogram.msd.identifier.supplier.cdk.ui.internal.provider.FormulaListTableComparator;

public class FormulaCalculatorView {

	private static final Logger logger = Logger.getLogger(FormulaCalculatorView.class);
	private Label label;
	private ExtendedTableViewer tableViewer;
	private FormulaListTableComparator formulaListTableComparator;
	private String[] titles = {"Formula", "Ranking"};
	private int bounds[] = {200, 100};
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
		tableViewer = new ExtendedTableViewer(composite, SWT.MULTI | SWT.H_SCROLL | SWT.V_SCROLL | SWT.FULL_SELECTION);
		tableViewer.getControl().setLayoutData(new GridData(GridData.FILL_BOTH));
		tableViewer.createColumns(titles, bounds);
		tableViewer.setContentProvider(new FormulaListContentProvider());
		tableViewer.setLabelProvider(new FormulaListLabelProvider());
		/*
		 * Sorting the table.
		 */
		formulaListTableComparator = new FormulaListTableComparator();
		tableViewer.setComparator(formulaListTableComparator);
		/*
		 * Copy and Paste of the table content.
		 */
		tableViewer.getTable().addKeyListener(new KeyAdapter() {

			@Override
			public void keyReleased(KeyEvent e) {

				/*
				 * The selected content will be placed to the clipboard if the
				 * user is using "Function + c". "Function-Key" 262144
				 * (stateMask) + "c" 99 (keyCode)
				 */
				if(e.keyCode == 99 && e.stateMask == 262144) {
					tableViewer.copyToClipboard(titles);
				}
			}
		});
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
			/*
			 * Generate formulas.
			 * The user defined isotope decider will be set dynamically.
			 */
			List<NameAndRating> formulaNamesAndRatings = FormulaSupport.getInstance().getFormulaNamesAndRatings(ion);
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
						Double ion = (Double)event.getProperty(IChemClipseEvents.PROPERTY_SELECTED_ION);
						update(ion);
					} catch(Exception e) {
						logger.warn(e);
					}
				}
			};
			eventBroker.subscribe(IChemClipseEvents.TOPIC_CHROMATOGRAM_MSD_UPDATE_ION_SELECTION, eventHandler);
		}
	}
}
