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
import org.eclipse.e4.ui.workbench.swt.modeling.EMenuService;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.ControlEvent;
import org.eclipse.swt.events.ControlListener;
import org.eclipse.swt.events.ModifyEvent;
import org.eclipse.swt.events.ModifyListener;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Group;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Text;
import org.openscience.cdk.interfaces.IMolecule;
import org.osgi.service.event.Event;
import org.osgi.service.event.EventHandler;

import net.openchrom.supplier.cdk.core.AwtToSwtImageBridge;
import net.openchrom.supplier.cdk.core.CDKIupacToIMoleculeConverter;
import net.openchrom.supplier.cdk.core.CDKSmilesToIMoleculeConverter;
import net.openchrom.supplier.cdk.core.IStructureGenerator;
import net.openchrom.supplier.cdk.core.IUPACtoIMoleculeConverter;
import net.openchrom.supplier.cdk.core.MoleculeToImageConverter;
import net.openchrom.supplier.cdk.core.OPSINIupacToIMoleculeConverter;
import net.openchrom.support.events.IOpenChromEvents;

public class MoleculeView {

	private Text text;
	private Label label;
	private Composite moleculeComposite;
	private Button smilesChoice;
	private Button iupacChoice;
	// private String moleculeString = "C(C(CO[N+](=O)[O-])O[N+](=O)[O-])O[N+](=O)[O-]";// example molecule for SMILES
	private String moleculeString = "hexane"; // example molecule for IUPAC
	private Image moleculeImage = null;
	private IStructureGenerator currentStructureGenerator = null;
	//
	// Macro Definitions
	public static final int MODUS_SMILES = 0;
	public static final int MODUS_IUPAC = 1;
	// How to process Input (e.g. SMILES,IUPAC,...)
	private int convertModus = MODUS_IUPAC;// initially setup to parse IUPAC
	/*
	 * 
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

		MoleculeToImageConverter moleculeToImageConverter = MoleculeToImageConverter.getInstance();
		//
		if(moleculeComposite.getSize().x != 0)
			moleculeToImageConverter.setWidth(moleculeComposite.getSize().x);
		if(moleculeComposite.getSize().y != 0)
			moleculeToImageConverter.setHeight(moleculeComposite.getSize().y);
		if(convertModus == MODUS_SMILES) {
			currentStructureGenerator = new CDKSmilesToIMoleculeConverter();
			convertMoleculeToImage();
		}
		if(convertModus == MODUS_IUPAC) {
			currentStructureGenerator = new OPSINIupacToIMoleculeConverter();
			convertMoleculeToImage();
		}
		if(moleculeImage != null)
			label.setImage(moleculeImage);
		moleculeImage.dispose();
	}

	//
	@Inject
	private Composite parent;

	@PostConstruct
	private void createControl(EMenuService menuService) {

		parent.setLayout(new GridLayout(1, true));
		Composite iupacComposite = new Composite(parent, SWT.NONE);
		moleculeComposite = new Composite(parent, SWT.NONE);
		iupacComposite.setBackground(Display.getCurrent().getSystemColor(SWT.COLOR_WHITE));
		iupacComposite.setLayout(new GridLayout(2, true));
		iupacComposite.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));
		moleculeComposite.setBackground(Display.getCurrent().getSystemColor(SWT.COLOR_WHITE));
		moleculeComposite.setLayoutData(new GridData(GridData.FILL_BOTH));
		// TODO: Put all the stuff here!
		//
		// moleculeComposite.setBackground(Display.getCurrent().getSystemColor(SWT.COLOR_BLACK));
		moleculeComposite.setLayout(new GridLayout(2, true));
		// moleculeComposite.setLayoutData(new GridData(GridData.FILL_BOTH));
		//
		// use ControlListener instead of FocusListener ...
		moleculeComposite.addControlListener(new ControlListener() {

			@Override
			public void controlResized(ControlEvent e) {

				makeImage();
			}

			@Override
			public void controlMoved(ControlEvent e) {

			}
		});
		// moleculeComposite.addMouseListener(new MoleculeViewPopUpMenu());;
		//
		label = new Label(moleculeComposite, SWT.CENTER);
		label.setBackground(Display.getCurrent().getSystemColor(SWT.COLOR_WHITE));
		//
		makeImage();
		//
		// handles to configure MoleculeView behaviour
		Group boxSettings = new Group(iupacComposite, SWT.LEFT);
		boxSettings.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));
		boxSettings.setText("Settings");
		boxSettings.setLayout(new GridLayout());
		//
		iupacChoice = new Button(boxSettings, SWT.RADIO);
		iupacChoice.setText("IUPAC");
		iupacChoice.addSelectionListener(new SelectionListener() {

			@Override
			public void widgetSelected(SelectionEvent e) {

				setModus(MODUS_IUPAC);
			}

			@Override
			public void widgetDefaultSelected(SelectionEvent e) {

			}
		});
		iupacChoice.setSelection(true);
		//
		smilesChoice = new Button(boxSettings, SWT.RADIO);
		smilesChoice.setText("SMILES");
		smilesChoice.addSelectionListener(new SelectionListener() {

			@Override
			public void widgetSelected(SelectionEvent e) {

				setModus(MODUS_SMILES);
			}

			@Override
			public void widgetDefaultSelected(SelectionEvent e) {

			}
		});
		Group boxLookUp = new Group(iupacComposite, SWT.RIGHT);
		boxLookUp.setText("Look-Up");
		boxLookUp.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));
		boxLookUp.setLayout(new GridLayout());
		//
		text = new Text(boxLookUp, SWT.CENTER);
		text.setSize(new org.eclipse.swt.graphics.Point(200, 20));
		text.setText(moleculeString);
		text.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));
		menuService.registerContextMenu(text, "net.openchrom.supplier.cdk.ui.popupmenu.qsar");
		//
		// text.setBackground(Display.getCurrent().getSystemColor(SWT.COLOR_GREEN));
		text.addModifyListener(new ModifyListener() {

			@Override
			public void modifyText(ModifyEvent e) {

				setSmiles(text.getText());
				makeImage();
			}
		});
	}

	@PreDestroy
	private void preDestroy() {

		unsubscribe();
	}

	@Focus
	public void setFocus() {

		label.setFocus();
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
					String cas = (String)event.getProperty(IOpenChromEvents.PROPERTY_IDENTIFICATION_ENTRY_CAS_NUMBER);
					String formula = (String)event.getProperty(IOpenChromEvents.PROPERTY_IDENTIFICATION_ENTRY_FORMULA);
					System.out.println(name + " " + cas + " " + formula);
					//
					if(convertModus == MODUS_SMILES) {
						moleculeString = formula;
						text.setText(moleculeString);
					}
					if(convertModus == MODUS_IUPAC) {
						moleculeString = name;
						text.setText(moleculeString);
					}
					//
					makeImage();
				}
			};
			eventBroker.subscribe(IOpenChromEvents.TOPIC_IDENTIFICATION_ENTRY_UPDATE_CDK, eventHandler);
		}
	}
}
