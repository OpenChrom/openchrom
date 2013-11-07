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

import java.awt.image.BufferedImage;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import javax.inject.Inject;

import org.eclipse.e4.ui.di.Focus;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.ControlEvent;
import org.eclipse.swt.events.ControlListener;

import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Label;
import org.openscience.cdk.interfaces.IMolecule;

import net.openchrom.supplier.cdk.core.AwtToSwtImageBridge;
import net.openchrom.supplier.cdk.core.IUPACtoIMoleculeConverter;
import net.openchrom.supplier.cdk.core.MoleculeToImageConverter;

public class MoleculeView {

	private Label label;
	private Composite composite;
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
		if(composite.getSize().x != 0)
			moleculeToImageConverter.setWidth(composite.getSize().x);
		if(composite.getSize().y != 0)
			moleculeToImageConverter.setHeight(composite.getSize().y);
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
	private void createControl() {

		parent.setLayout(new FillLayout());
		//
		composite = new Composite(parent, SWT.NONE);
		//
		composite.setBackground(Display.getCurrent().getSystemColor(SWT.COLOR_BLACK));
		composite.setLayout(new GridLayout(1, true));
		composite.setLayoutData(new GridData(GridData.FILL_BOTH));
		//
		// use ControlListener instead of FocusListener ...
		composite.addControlListener(new ControlListener() {

			@Override
			public void controlResized(ControlEvent e) {

				makeImage();
			}

			@Override
			public void controlMoved(ControlEvent e) {

			}
		});
		//
		label = new Label(composite, SWT.CENTER);
		label.setBackground(Display.getCurrent().getSystemColor(SWT.COLOR_BLACK));
		//
		makeImage();
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
