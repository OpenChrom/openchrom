/*******************************************************************************
 * Copyright (c) 2022 Lablicate GmbH.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors:
 * Philip Wenig - initial API and implementation
 *******************************************************************************/
package net.openchrom.xxd.process.supplier.templates.ui.swt;

import java.util.List;
import java.util.concurrent.atomic.AtomicReference;

import org.eclipse.chemclipse.model.updates.IUpdateListener;
import org.eclipse.chemclipse.rcp.ui.icons.core.ApplicationImageFactory;
import org.eclipse.chemclipse.rcp.ui.icons.core.IApplicationImage;
import org.eclipse.chemclipse.support.ui.provider.AbstractLabelProvider;
import org.eclipse.jface.viewers.ArrayContentProvider;
import org.eclipse.jface.viewers.ComboViewer;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.KeyAdapter;
import org.eclipse.swt.events.KeyEvent;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Combo;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Text;

import net.openchrom.xxd.process.supplier.templates.model.AbstractSetting;
import net.openchrom.xxd.process.supplier.templates.model.PositionDirective;
import net.openchrom.xxd.process.supplier.templates.model.PositionMarker;

public class PositionAdjusterUI extends Composite {

	private AtomicReference<ComboViewer> comboControl = new AtomicReference<>();
	private AtomicReference<Text> textControl = new AtomicReference<>();
	private AtomicReference<Button> buttonControl = new AtomicReference<>();
	//
	private List<? extends AbstractSetting> settings;
	private IUpdateListener updateListener;

	public PositionAdjusterUI(Composite parent, int style) {

		super(parent, style);
		createControl();
	}

	public void setInput(List<? extends AbstractSetting> settings) {

		this.settings = settings;
	}

	public void setUpdateListener(IUpdateListener updateListener) {

		this.updateListener = updateListener;
	}

	private void createControl() {

		setLayout(new FillLayout());
		//
		Composite composite = new Composite(this, SWT.NONE);
		GridLayout gridLayout = new GridLayout(3, false);
		gridLayout.marginLeft = 0;
		gridLayout.marginRight = 0;
		composite.setLayout(gridLayout);
		//
		createComboViewer(composite);
		createText(composite);
		createButton(composite);
		//
		initialize();
		validate();
	}

	private void initialize() {

		comboControl.get().setInput(PositionMarker.values());
		comboControl.get().getCombo().select(0);
	}

	private void createComboViewer(Composite parent) {

		ComboViewer comboViewer = new ComboViewer(parent, SWT.READ_ONLY);
		Combo combo = comboViewer.getCombo();
		comboViewer.setContentProvider(ArrayContentProvider.getInstance());
		comboViewer.setLabelProvider(new AbstractLabelProvider() {

			@Override
			public String getText(Object element) {

				if(element instanceof PositionMarker) {
					return ((PositionMarker)element).label();
				}
				return null;
			}
		});
		//
		combo.setToolTipText("Select the position field to be updated.");
		GridData gridData = new GridData();
		gridData.widthHint = 250;
		combo.setLayoutData(gridData);
		combo.addSelectionListener(new SelectionAdapter() {

			@Override
			public void widgetSelected(SelectionEvent e) {

				Object object = comboViewer.getStructuredSelection().getFirstElement();
				if(object instanceof PositionMarker) {
					validate();
				}
			}
		});
		//
		comboControl.set(comboViewer);
	}

	private void createText(Composite parent) {

		Text text = new Text(parent, SWT.BORDER);
		text.setText("0");
		text.setToolTipText("Adjust the selected position by the given value.");
		text.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));
		/*
		 * Listen to key event.
		 */
		text.addKeyListener(new KeyAdapter() {

			@Override
			public void keyReleased(KeyEvent e) {

				validate();
				if(e.keyCode == SWT.LF || e.keyCode == SWT.CR || e.keyCode == SWT.KEYPAD_CR) {
					applyShift();
				}
			}
		});
		//
		textControl.set(text);
	}

	private void createButton(Composite parent) {

		Button button = new Button(parent, SWT.PUSH);
		button.setText("");
		button.setToolTipText("Apply the position adjustment.");
		button.setImage(ApplicationImageFactory.getInstance().getImage(IApplicationImage.IMAGE_EXECUTE, IApplicationImage.SIZE_16x16));
		button.addSelectionListener(new SelectionAdapter() {

			@Override
			public void widgetSelected(SelectionEvent e) {

				applyShift();
			}
		});
		//
		buttonControl.set(button);
	}

	private void validate() {

		boolean enabled = settings != null && getPositionMarker() != null && getPositionAdjustment() != 0;
		buttonControl.get().setEnabled(enabled);
	}

	private PositionMarker getPositionMarker() {

		Object object = comboControl.get().getStructuredSelection().getFirstElement();
		if(object instanceof PositionMarker) {
			return (PositionMarker)object;
		}
		//
		return null;
	}

	private double getPositionAdjustment() {

		try {
			return Double.parseDouble(textControl.get().getText().trim());
		} catch(NumberFormatException e) {
			return 0;
		}
	}

	private void applyShift() {

		if(settings != null) {
			double positionAdjustment = getPositionAdjustment();
			if(positionAdjustment != 0) {
				PositionMarker positionMarker = getPositionMarker();
				if(positionMarker != null) {
					for(AbstractSetting setting : settings) {
						if(isValidPositionDirective(setting, positionMarker)) {
							switch(positionMarker) {
								case RT_MS_START:
								case RT_MIN_START:
								case RI_START:
									setting.setPositionStart(validatePosition(setting.getPositionStart() + positionAdjustment));
									break;
								case RT_MS_STOP:
								case RT_MIN_STOP:
								case RI_STOP:
									setting.setPositionStop(validatePosition(setting.getPositionStop() + positionAdjustment));
									break;
								default:
									break;
							}
						}
					}
					fireUpdate();
				}
			}
		}
	}

	private boolean isValidPositionDirective(AbstractSetting setting, PositionMarker positionMarker) {

		PositionDirective positionDirective = setting.getPositionDirective();
		if(PositionDirective.RETENTION_TIME_MS.equals(positionDirective)) {
			return PositionMarker.RT_MS_START.equals(positionMarker) || PositionMarker.RT_MS_STOP.equals(positionMarker);
		} else if(PositionDirective.RETENTION_TIME_MIN.equals(positionDirective)) {
			return PositionMarker.RT_MIN_START.equals(positionMarker) || PositionMarker.RT_MIN_STOP.equals(positionMarker);
		} else if(PositionDirective.RETENTION_INDEX.equals(positionDirective)) {
			return PositionMarker.RI_START.equals(positionMarker) || PositionMarker.RI_STOP.equals(positionMarker);
		} else {
			return false;
		}
	}

	private double validatePosition(double position) {

		return position < 0 ? 0 : position;
	}

	private void fireUpdate() {

		if(updateListener != null) {
			updateListener.update();
		}
	}
}