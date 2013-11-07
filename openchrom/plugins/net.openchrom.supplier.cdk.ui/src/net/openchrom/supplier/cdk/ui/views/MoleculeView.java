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

import java.awt.Color;
import java.awt.Point;
import java.awt.image.BufferedImage;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import javax.inject.Inject;

import org.eclipse.e4.ui.di.Focus;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.ControlEvent;
import org.eclipse.swt.events.ControlListener;
import org.eclipse.swt.events.ModifyEvent;
import org.eclipse.swt.events.ModifyListener;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Group;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Text;
import org.openscience.cdk.interfaces.IMolecule;

import net.openchrom.supplier.cdk.core.AwtToSwtImageBridge;
import net.openchrom.supplier.cdk.core.IUPACtoIMoleculeConverter;
import net.openchrom.supplier.cdk.core.MoleculeToImageConverter;

public class MoleculeView {

	private Label label;
	private Composite moleculeComposite;
	// private String moleculeString = "C(C(CO[N+](=O)[O-])O[N+](=O)[O-])O[N+](=O)[O-]";// example molecule for SMILES
	private String moleculeString = "hexane"; // example molecule for IUPAC
	private Image moleculeImage = null;
	//
	// Macro Definitions
	public static final int MODUS_SMILES = 0;
	public static final int MODUS_IUPAC = 1;
	// How to process Input (e.g. SMILES,IUPAC,...)
	private int convertModus = MODUS_IUPAC;// initially setup to parse IUPAC

	private void makeImage() {

		MoleculeToImageConverter moleculeToImageConverter = MoleculeToImageConverter.getInstance();
		//
		if(moleculeComposite.getSize().x != 0)
			moleculeToImageConverter.setWidth(moleculeComposite.getSize().x);
		if(moleculeComposite.getSize().y != 0)
			moleculeToImageConverter.setHeight(moleculeComposite.getSize().y);
		if(convertModus == MODUS_SMILES) {
			// process SMILES input
			moleculeImage = //
			new Image(//
			parent.getDisplay(),//
			AwtToSwtImageBridge.convertToSWT(//
			(BufferedImage)moleculeToImageConverter.smilesToImage(moleculeString)));
		}
		if(convertModus == MODUS_IUPAC) {
			// process IUPAC input
			IMolecule molecule = IUPACtoIMoleculeConverter.parse(moleculeString);
			moleculeImage = //
			new Image(//
			parent.getDisplay(),//
			AwtToSwtImageBridge.convertToSWT(//
			(BufferedImage)moleculeToImageConverter.moleculeToImage(molecule)));
		}
		if(moleculeImage != null)
			label.setImage(moleculeImage);
		moleculeImage.dispose();
	}

	//
	@Inject
	private Composite parent;

	@PostConstruct
	private void createControl2() {

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
		Button iupacChoice = new Button(boxSettings, SWT.RADIO);
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
		Button smilesChoice = new Button(boxSettings, SWT.RADIO);
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
		final Text text = new Text(boxLookUp, SWT.CENTER);
		text.setSize(new org.eclipse.swt.graphics.Point(200, 20));
		text.setText(moleculeString);
		text.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));
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
}
