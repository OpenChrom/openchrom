/*******************************************************************************
 * Copyright (c) 2013, 2014 Dr. Philip Wenig, Marwin Wollschläger.
 * 
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors:
 * Dr. Philip Wenig - initial API and implementation
 * Marwin Wollschläger - initial API and implementation
 *******************************************************************************/
package net.chemclipse.chromatogram.msd.identifier.supplier.cdk.ui.views;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import javax.inject.Inject;

import org.eclipse.e4.core.services.events.IEventBroker;
import org.eclipse.e4.ui.di.Focus;
import org.eclipse.e4.ui.model.application.ui.basic.MPart;
import org.eclipse.e4.ui.workbench.modeling.EPartService;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.ControlAdapter;
import org.eclipse.swt.events.ControlEvent;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Label;
import org.osgi.service.event.Event;
import org.osgi.service.event.EventHandler;

import net.chemclipse.chromatogram.msd.identifier.supplier.cdk.converter.ImageConverter;
import net.chemclipse.logging.core.Logger;
import net.chemclipse.support.events.IChemClipseEvents;

public class MoleculeView {

	private static final Logger logger = Logger.getLogger(MoleculeView.class);
	private Label moleculeInfo;
	private Label moleculeLabel;
	private Composite moleculeComposite;
	/*
	 * SMILES example:
	 * "C(C(CO[N+](=O)[O-])O[N+](=O)[O-])O[N+](=O)[O-]"
	 * IUPAC example:
	 * "hexane"
	 */
	private String iupacName = "Demo";
	private String smilesFormula = "C(C(CO[N+](=O)[O-])O[N+](=O)[O-])O[N+](=O)[O-]";
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
	public MoleculeView(IEventBroker eventBroker, EventHandler eventHandler) {

		this.eventBroker = eventBroker;
		this.eventHandler = eventHandler;
		subscribe();
	}

	private Image convertMoleculeToImage(boolean useSmiles, String converterInput) {

		Image moleculeImage = null;
		try {
			Point point = calculateMoleculeImageSize();
			moleculeImage = ImageConverter.getInstance().moleculeToImage(useSmiles, converterInput, point);
		} catch(Exception e) {
			logger.warn(e);
		}
		return moleculeImage;
	}

	private void makeImage() {

		if(isPartVisible()) {
			/*
			 * First, try to parse the smiles formula. If it's not available,
			 * try to create the molecule by parsing the IUPAC name.
			 */
			Image moleculeImage = null;
			if(smilesFormula != null && !smilesFormula.equals("")) {
				/*
				 * SMILES is the default
				 */
				moleculeImage = convertMoleculeToImage(true, smilesFormula);
				/*
				 * IUPAC
				 */
				moleculeImage = convertMoleculeToImage(false, iupacName);
			}
			/*
			 * Set the molecule image or a notification.
			 */
			moleculeInfo.setText(iupacName);
			if(moleculeImage != null) {
				/*
				 * OK
				 */
				moleculeLabel.setText("");
				moleculeLabel.setImage(moleculeImage);
				moleculeImage.dispose();
			} else {
				/*
				 * Parser error
				 */
				moleculeLabel.setImage(null);
				moleculeLabel.setText("Sorry, it's not possible to parse the molecule.");
			}
		}
	}

	private Point calculateMoleculeImageSize() {

		/*
		 * Calculate the width and height.
		 */
		Point point;
		if(moleculeComposite.getSize().x != 0 && moleculeComposite.getSize().y != 0) {
			/*
			 * Get the image size.
			 */
			int width = moleculeComposite.getSize().x;
			int height = moleculeComposite.getSize().y;
			point = new Point(width, height);
		} else {
			/*
			 * Set the default image size.
			 */
			point = new Point(ImageConverter.DEFAULT_WIDTH, ImageConverter.DEFAULT_HEIGHT);
		}
		return point;
	}

	@PostConstruct
	private void createControl() {

		parent.setLayout(new GridLayout(1, true));
		/*
		 * Info
		 */
		moleculeInfo = new Label(parent, SWT.NONE);
		moleculeInfo.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));
		/*
		 * Molecule
		 */
		moleculeComposite = new Composite(parent, SWT.NONE);
		moleculeComposite.setBackground(Display.getCurrent().getSystemColor(SWT.COLOR_WHITE));
		moleculeComposite.setLayoutData(new GridData(GridData.FILL_BOTH));
		moleculeComposite.setLayout(new GridLayout(2, true));
		moleculeComposite.addControlListener(new ControlAdapter() {

			@Override
			public void controlResized(ControlEvent e) {

				makeImage();
			}
		});
		moleculeLabel = new Label(moleculeComposite, SWT.CENTER);
		moleculeLabel.setBackground(Display.getCurrent().getSystemColor(SWT.COLOR_WHITE));
		moleculeLabel.addControlListener(new ControlAdapter() {

			@Override
			public void controlResized(ControlEvent e) {

				moleculeLabel.setBounds(5, 5, moleculeComposite.getSize().x, moleculeComposite.getSize().y);
			}
		});
		//
		makeImage();
	}

	@PreDestroy
	private void preDestroy() {

		unsubscribe();
	}

	@Focus
	public void setFocus() {

		moleculeLabel.setFocus();
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

					/*
					 * Receive name and formula.
					 */
					iupacName = (String)event.getProperty(IChemClipseEvents.PROPERTY_IDENTIFICATION_ENTRY_NAME);
					smilesFormula = (String)event.getProperty(IChemClipseEvents.PROPERTY_IDENTIFICATION_ENTRY_FORMULA);
					//
					makeImage();
				}
			};
			eventBroker.subscribe(IChemClipseEvents.TOPIC_IDENTIFICATION_ENTRY_UPDATE_CDK, eventHandler);
		}
	}
}
