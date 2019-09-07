/*******************************************************************************
 * Copyright (c) 2013, 2019 Dr. Philip Wenig, Marwin Wollschläger.
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
package net.openchrom.chromatogram.msd.identifier.supplier.cdk.ui.views;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import javax.inject.Inject;

import org.eclipse.chemclipse.logging.core.Logger;
import org.eclipse.chemclipse.model.identifier.IIdentificationTarget;
import org.eclipse.chemclipse.model.identifier.ILibraryInformation;
import org.eclipse.chemclipse.support.events.IChemClipseEvents;
import org.eclipse.chemclipse.swt.ui.support.Colors;
import org.eclipse.e4.core.services.events.IEventBroker;
import org.eclipse.e4.ui.di.Focus;
import org.eclipse.e4.ui.model.application.ui.basic.MPart;
import org.eclipse.e4.ui.workbench.modeling.EPartService;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.ControlAdapter;
import org.eclipse.swt.events.ControlEvent;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Label;
import org.osgi.service.event.Event;
import org.osgi.service.event.EventHandler;

import net.openchrom.chromatogram.msd.identifier.supplier.cdk.ui.converter.ImageConverter;

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
	private String iupacName = "Thiamin";
	private String smilesFormula = "OCCc1c(C)[n+](=cs1)Cc2cnc(C)nc(N)2";
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

	private Image convertMoleculeToImage(Display display, boolean useSmiles, String converterInput) {

		Image image = null;
		try {
			Point point = calculateMoleculeImageSize();
			image = ImageConverter.getInstance().moleculeToImage(display, useSmiles, converterInput, point);
		} catch(Exception e) {
			logger.warn(e);
		}
		return image;
	}

	private void makeImage(Display display) {

		if(isPartVisible()) {
			/*
			 * First, try to parse the smiles formula. If it's not available,
			 * try to create the molecule by parsing the IUPAC name.
			 */
			Image moleculeImage = null;
			if(smilesFormula != null && !smilesFormula.equals("")) {
				moleculeImage = convertMoleculeToImage(display, true, smilesFormula);
			} else {
				moleculeImage = convertMoleculeToImage(display, false, iupacName);
			}
			moleculeInfo.setText(iupacName + " | " + smilesFormula);
			/*
			 * Set the molecule image or a notification.
			 */
			if(moleculeImage != null) {
				/*
				 * OK
				 */
				moleculeLabel.setText("");
				moleculeLabel.setImage(moleculeImage);
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
		moleculeComposite.setBackground(Colors.WHITE);
		moleculeComposite.setLayoutData(new GridData(GridData.FILL_BOTH));
		moleculeComposite.setLayout(new FillLayout());
		moleculeComposite.addControlListener(new ControlAdapter() {

			@Override
			public void controlResized(ControlEvent e) {

				makeImage(e.display);
			}
		});
		moleculeLabel = new Label(moleculeComposite, SWT.CENTER);
		moleculeLabel.setBackground(Colors.WHITE);
		moleculeLabel.addControlListener(new ControlAdapter() {

			@Override
			public void controlResized(ControlEvent e) {

				moleculeLabel.setBounds(5, 5, moleculeComposite.getSize().x, moleculeComposite.getSize().y);
			}
		});
		//
		makeImage(moleculeComposite.getDisplay());
	}

	@PreDestroy
	private void preDestroy() {

		unsubscribe();
	}

	@Focus
	public void setFocus() {

		moleculeLabel.setFocus();
		makeImage(moleculeComposite.getDisplay());
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
			eventHandler = new EventHandler() {

				public void handleEvent(Event event) {

					/*
					 * Receive name and formula.
					 */
					Object object = event.getProperty(IChemClipseEvents.PROPERTY_IDENTIFICATION_TARGET);
					if(object instanceof IIdentificationTarget) {
						IIdentificationTarget identificationTarget = (IIdentificationTarget)object;
						ILibraryInformation libraryInformation = identificationTarget.getLibraryInformation();
						iupacName = libraryInformation.getName();
						smilesFormula = libraryInformation.getSmiles();
						makeImage(moleculeComposite.getDisplay());
					}
				}
			};
			eventBroker.subscribe(IChemClipseEvents.TOPIC_IDENTIFICATION_TARGET_UPDATE, eventHandler);
		}
	}
}
