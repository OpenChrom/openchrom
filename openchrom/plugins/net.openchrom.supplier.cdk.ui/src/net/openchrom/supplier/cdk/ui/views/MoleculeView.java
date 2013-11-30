/*******************************************************************************
 * Copyright (c) 2013 Dr. Philip Wenig, Marwin Wollschläger.
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
package net.openchrom.supplier.cdk.ui.views;

import java.awt.image.BufferedImage;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import javax.inject.Inject;

import org.eclipse.e4.core.services.events.IEventBroker;
import org.eclipse.e4.ui.di.Focus;
import org.eclipse.e4.ui.model.application.ui.basic.MPart;
import org.eclipse.e4.ui.workbench.modeling.EPartService;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.ControlEvent;
import org.eclipse.swt.events.ControlListener;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Label;
import org.openscience.cdk.interfaces.IMolecule;
import org.osgi.service.event.Event;
import org.osgi.service.event.EventHandler;

import net.openchrom.supplier.cdk.converter.CDKSmilesToMoleculeConverter;
import net.openchrom.supplier.cdk.converter.IStructureConverter;
import net.openchrom.supplier.cdk.converter.MoleculeToImageConverter;
import net.openchrom.supplier.cdk.converter.OPSINIupacToMoleculeConverter;
import net.openchrom.supplier.cdk.core.AwtToSwtImageBridge;
import net.openchrom.support.events.IOpenChromEvents;

public class MoleculeView {

	private Label moleculeInfo;
	private Label moleculeLabel;
	private Composite moleculeComposite;
	// private String moleculeString = "C(C(CO[N+](=O)[O-])O[N+](=O)[O-])O[N+](=O)[O-]";// example molecule for SMILES
	private String moleculeString = "hexane"; // example molecule for IUPAC
	private Image moleculeImage = null;
	private IStructureConverter currentStructureGenerator = null;
	//
	// Macro Definitions
	public static final int MODUS_SMILES = 0;
	public static final int MODUS_IUPAC = 1;
	// How to process Input (e.g. SMILES,IUPAC,...)
	private int convertModus = MODUS_IUPAC;// initially setup to parse IUPAC
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

	private void convertMoleculeToImage() {

		MoleculeToImageConverter moleculeToImageConverter = MoleculeToImageConverter.getInstance();
		// process SMILES input
		IMolecule molecule = currentStructureGenerator.generate(moleculeString);
		moleculeImage = //
		new Image(//
		parent.getDisplay(),//
		AwtToSwtImageBridge.convertToSWT(//
		(BufferedImage)moleculeToImageConverter.moleculeToImage(molecule)));
	}

	private void makeImage() {

		if(isPartVisible()) {
			/*
			 * Create the molecule image.
			 */
			MoleculeToImageConverter moleculeToImageConverter = MoleculeToImageConverter.getInstance();
			//
			if(moleculeComposite.getSize().x != 0)
				moleculeToImageConverter.setWidth(moleculeComposite.getSize().x);
			if(moleculeComposite.getSize().y != 0)
				moleculeToImageConverter.setHeight(moleculeComposite.getSize().y);
			switch(convertModus) {
				case MODUS_IUPAC:
					/*
					 * Use IUPAC
					 */
					currentStructureGenerator = new OPSINIupacToMoleculeConverter();
					convertMoleculeToImage();
					break;
				default:
					/*
					 * SMILES is the default
					 */
					currentStructureGenerator = new CDKSmilesToMoleculeConverter();
					convertMoleculeToImage();
					break;
			}
			/*
			 * Set the molecule image or a notification.
			 */
			if(moleculeImage != null) {
				/*
				 * OK
				 */
				moleculeInfo.setText(moleculeString);
				moleculeLabel.setImage(moleculeImage);
				moleculeImage.dispose();
			} else {
				/*
				 * Parser error
				 */
				moleculeInfo.setText("There was an error parsing: " + moleculeString);
			}
		}
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
		moleculeComposite.addControlListener(new ControlListener() {

			@Override
			public void controlResized(ControlEvent e) {

				makeImage();
			}

			@Override
			public void controlMoved(ControlEvent e) {

			}
		});
		moleculeLabel = new Label(moleculeComposite, SWT.CENTER);
		moleculeLabel.setBackground(Display.getCurrent().getSystemColor(SWT.COLOR_WHITE));
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

	public void setModus(int modus) {

		if(modus == MODUS_SMILES) {
			convertModus = MODUS_SMILES;
		}
		if(modus == MODUS_IUPAC) {
			convertModus = MODUS_IUPAC;
		}
	}

	public void setSmiles(String str) {

		moleculeString = str;
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

					String name = (String)event.getProperty(IOpenChromEvents.PROPERTY_IDENTIFICATION_ENTRY_NAME);
					String formula = (String)event.getProperty(IOpenChromEvents.PROPERTY_IDENTIFICATION_ENTRY_FORMULA);
					//
					if(convertModus == MODUS_SMILES) {
						moleculeString = formula;
					}
					if(convertModus == MODUS_IUPAC) {
						moleculeString = name;
					}
					//
					makeImage();
				}
			};
			eventBroker.subscribe(IOpenChromEvents.TOPIC_IDENTIFICATION_ENTRY_UPDATE_CDK, eventHandler);
		}
	}
}
