/*******************************************************************************
 * Copyright (c) 2013, 2020 Dr. Philip Wenig, Marwin Wollschläger.
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
import org.eclipse.chemclipse.rcp.ui.icons.core.ApplicationImageFactory;
import org.eclipse.chemclipse.rcp.ui.icons.core.IApplicationImage;
import org.eclipse.chemclipse.support.events.IChemClipseEvents;
import org.eclipse.chemclipse.swt.ui.support.Colors;
import org.eclipse.chemclipse.ux.extension.ui.support.PartSupport;
import org.eclipse.e4.core.services.events.IEventBroker;
import org.eclipse.e4.ui.di.Focus;
import org.eclipse.e4.ui.model.application.ui.basic.MPart;
import org.eclipse.e4.ui.workbench.modeling.EPartService;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.jface.preference.PreferenceDialog;
import org.eclipse.jface.preference.PreferenceManager;
import org.eclipse.jface.preference.PreferenceNode;
import org.eclipse.jface.window.Window;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.ControlAdapter;
import org.eclipse.swt.events.ControlEvent;
import org.eclipse.swt.events.KeyAdapter;
import org.eclipse.swt.events.KeyEvent;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Text;
import org.osgi.service.event.Event;
import org.osgi.service.event.EventHandler;

import net.openchrom.chromatogram.msd.identifier.supplier.cdk.ui.converter.ImageConverter;
import net.openchrom.chromatogram.msd.identifier.supplier.cdk.ui.preferences.PreferencePage;

public class MoleculeView {

	private static final Logger logger = Logger.getLogger(MoleculeView.class);
	//
	private Label moleculeInfo;
	private Label moleculeLabel;
	private Composite toolbarInfo;
	private Composite toolbarModify;
	private Composite moleculeComposite;
	//
	private Text textName;
	private Text textCas;
	private Text textSmiles;
	/*
	 * SMILES example:
	 * "C(C(CO[N+](=O)[O-])O[N+](=O)[O-])O[N+](=O)[O-]"
	 * IUPAC example:
	 * "hexane"
	 */
	private String iupacName = "Thiamin";
	private String casNumber = "59-43-8";
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
			/*
			 * DepictionGenerator depictionGenerator = new DepictionGenerator();
			 * depictionGenerator.depict(molecule).writeTo(path);
			 */
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
			//
			StringBuilder builder = new StringBuilder();
			builder.append(iupacName);
			builder.append(" | ");
			builder.append(casNumber);
			builder.append(" | ");
			builder.append(smilesFormula);
			moleculeInfo.setText(builder.toString());
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
		//
		createToolbarMain(parent);
		toolbarInfo = createToolbarInfo(parent);
		toolbarModify = createToolbarModify(parent);
		createMoleculeSection(parent);
		//
		PartSupport.setCompositeVisibility(toolbarInfo, true);
		PartSupport.setCompositeVisibility(toolbarModify, false);
	}

	@Focus
	public void setFocus() {

		moleculeLabel.setFocus();
		createMoleculeImage();
	}

	@PreDestroy
	private void preDestroy() {

		unsubscribe();
	}

	private void createToolbarMain(Composite parent) {

		Composite composite = new Composite(parent, SWT.NONE);
		GridData gridData = new GridData(GridData.FILL_HORIZONTAL);
		gridData.horizontalAlignment = SWT.END;
		composite.setLayoutData(gridData);
		composite.setLayout(new GridLayout(3, false));
		//
		createButtonToggleToolbarInfo(composite);
		createButtonToggleToolbarModify(composite);
		createSettingsButton(composite);
	}

	private Button createButtonToggleToolbarInfo(Composite parent) {

		Button button = new Button(parent, SWT.PUSH);
		button.setToolTipText("Toggle info toolbar.");
		button.setText("");
		button.setImage(ApplicationImageFactory.getInstance().getImage(IApplicationImage.IMAGE_INFO, IApplicationImage.SIZE_16x16));
		button.addSelectionListener(new SelectionAdapter() {

			@Override
			public void widgetSelected(SelectionEvent e) {

				boolean visible = PartSupport.toggleCompositeVisibility(toolbarInfo);
				if(visible) {
					button.setImage(ApplicationImageFactory.getInstance().getImage(IApplicationImage.IMAGE_INFO, IApplicationImage.SIZE_16x16));
				} else {
					button.setImage(ApplicationImageFactory.getInstance().getImage(IApplicationImage.IMAGE_INFO, IApplicationImage.SIZE_16x16));
				}
			}
		});
		//
		return button;
	}

	private Button createButtonToggleToolbarModify(Composite parent) {

		Button button = new Button(parent, SWT.PUSH);
		button.setToolTipText("Toggle modify toolbar.");
		button.setText("");
		button.setImage(ApplicationImageFactory.getInstance().getImage(IApplicationImage.IMAGE_EDIT_DEFAULT, IApplicationImage.SIZE_16x16));
		button.addSelectionListener(new SelectionAdapter() {

			@Override
			public void widgetSelected(SelectionEvent e) {

				boolean visible = PartSupport.toggleCompositeVisibility(toolbarModify);
				if(visible) {
					button.setImage(ApplicationImageFactory.getInstance().getImage(IApplicationImage.IMAGE_EDIT_ACTIVE, IApplicationImage.SIZE_16x16));
				} else {
					button.setImage(ApplicationImageFactory.getInstance().getImage(IApplicationImage.IMAGE_EDIT_DEFAULT, IApplicationImage.SIZE_16x16));
				}
			}
		});
		//
		return button;
	}

	private Composite createToolbarInfo(Composite parent) {

		Composite composite = new Composite(parent, SWT.NONE);
		GridData gridData = new GridData(GridData.FILL_HORIZONTAL);
		composite.setLayoutData(gridData);
		composite.setLayout(new GridLayout(1, false));
		//
		moleculeInfo = new Label(composite, SWT.NONE);
		moleculeInfo.setText("");
		moleculeInfo.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));
		//
		return composite;
	}

	private Composite createToolbarModify(Composite parent) {

		Composite composite = new Composite(parent, SWT.NONE);
		GridData gridData = new GridData(GridData.FILL_HORIZONTAL);
		composite.setLayoutData(gridData);
		composite.setLayout(new GridLayout(4, false));
		//
		textName = createTextName(composite);
		textCas = createTextCas(composite);
		textSmiles = createTextSmiles(composite);
		createButtonCalculate(composite);
		//
		return composite;
	}

	private Text createTextName(Composite parent) {

		Text text = new Text(parent, SWT.BORDER);
		text.setText(iupacName);
		text.setToolTipText("IUPAC Name");
		text.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));
		text.addKeyListener(new KeyAdapter() {

			@Override
			public void keyReleased(KeyEvent e) {

				if(isEnterPressed(e)) {
					iupacName = text.getText().trim();
					updateWidgets();
					createMoleculeImage();
				}
			}
		});
		//
		return text;
	}

	private Text createTextCas(Composite parent) {

		Text text = new Text(parent, SWT.BORDER);
		text.setText(casNumber);
		text.setToolTipText("CAS Number");
		text.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));
		text.addKeyListener(new KeyAdapter() {

			@Override
			public void keyReleased(KeyEvent e) {

				if(isEnterPressed(e)) {
					casNumber = text.getText().trim();
					updateWidgets();
					createMoleculeImage();
				}
			}
		});
		//
		return text;
	}

	private Text createTextSmiles(Composite parent) {

		Text text = new Text(parent, SWT.BORDER);
		text.setText(smilesFormula);
		text.setToolTipText("SMILES");
		text.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));
		text.addKeyListener(new KeyAdapter() {

			@Override
			public void keyReleased(KeyEvent e) {

				if(isEnterPressed(e)) {
					smilesFormula = text.getText().trim();
					updateWidgets();
					createMoleculeImage();
				}
			}
		});
		//
		return text;
	}

	private Button createButtonCalculate(Composite parent) {

		Button button = new Button(parent, SWT.PUSH);
		button.setText("");
		button.setToolTipText("Calculate the molecule image");
		button.setImage(ApplicationImageFactory.getInstance().getImage(IApplicationImage.IMAGE_CALCULATE, IApplicationImage.SIZE_16x16));
		button.addSelectionListener(new SelectionAdapter() {

			@Override
			public void widgetSelected(SelectionEvent e) {

				super.widgetSelected(e);
				iupacName = textName.getText().trim();
				casNumber = textCas.getText().trim();
				smilesFormula = textSmiles.getText().trim();
				updateWidgets();
				createMoleculeImage();
			}
		});
		//
		return button;
	}

	private void updateWidgets() {

		textName.setText(iupacName);
		textCas.setText(casNumber);
		textSmiles.setText(smilesFormula);
	}

	private boolean isEnterPressed(KeyEvent e) {

		return (e.keyCode == SWT.LF || e.keyCode == SWT.CR || e.keyCode == SWT.KEYPAD_CR);
	}

	private void createMoleculeSection(Composite parent) {

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
		//
		moleculeLabel = new Label(moleculeComposite, SWT.CENTER);
		moleculeLabel.setBackground(Colors.WHITE);
		moleculeLabel.addControlListener(new ControlAdapter() {

			@Override
			public void controlResized(ControlEvent e) {

				moleculeLabel.setBounds(5, 5, moleculeComposite.getSize().x, moleculeComposite.getSize().y);
			}
		});
		//
		createMoleculeImage();
	}

	private void createSettingsButton(Composite parent) {

		Button button = new Button(parent, SWT.PUSH);
		button.setToolTipText("Open the Settings");
		button.setText("");
		button.setImage(ApplicationImageFactory.getInstance().getImage(IApplicationImage.IMAGE_CONFIGURE, IApplicationImage.SIZE_16x16));
		button.addSelectionListener(new SelectionAdapter() {

			@Override
			public void widgetSelected(SelectionEvent e) {

				PreferenceManager preferenceManager = new PreferenceManager();
				preferenceManager.addToRoot(new PreferenceNode("1", new PreferencePage()));
				//
				PreferenceDialog preferenceDialog = new PreferenceDialog(e.display.getActiveShell(), preferenceManager);
				preferenceDialog.create();
				preferenceDialog.setMessage("Settings");
				if(preferenceDialog.open() == Window.OK) {
					try {
						applySettings();
					} catch(Exception e1) {
						MessageDialog.openError(e.display.getActiveShell(), "Settings", "Something has gone wrong to apply the settings.");
					}
				}
			}
		});
	}

	private void applySettings() {

		createMoleculeImage();
	}

	private void createMoleculeImage() {

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
						casNumber = libraryInformation.getCasNumber();
						smilesFormula = libraryInformation.getSmiles();
						createMoleculeImage();
					}
				}
			};
			eventBroker.subscribe(IChemClipseEvents.TOPIC_IDENTIFICATION_TARGET_UPDATE, eventHandler);
		}
	}
}
