/*******************************************************************************
 * Copyright (c) 2019 Lablicate GmbH.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 * Christoph Läubrich - initial API and implementation
 * Alexander Stark - re-factoring
 *******************************************************************************/
package net.openchrom.nmr.processing.supplier.base.ui;

import java.text.NumberFormat;
import java.text.ParseException;
import java.util.Collection;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.function.DoubleConsumer;

import org.eclipse.swt.SWT;
import org.eclipse.swt.events.FocusEvent;
import org.eclipse.swt.events.FocusListener;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.graphics.GC;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Scale;
import org.eclipse.swt.widgets.Text;

public class SliderUI {

	private int scaleFactor;
	private Scale scale;
	private int min;
	private Text textField;
	private NumberFormat numberFormat = NumberFormat.getInstance();
	private Composite composite;
	private Collection<DoubleConsumer> consumers = new CopyOnWriteArrayList<>();

	/**
	 *
	 * @param composite
	 * @param range
	 * @param min
	 * @param fractionDigits
	 */
	public SliderUI(Composite parent, int fractionDigits){
		scaleFactor = (int) Math.pow(10, fractionDigits);
		numberFormat.setMaximumFractionDigits(fractionDigits);
		numberFormat.setMinimumFractionDigits(fractionDigits);
		composite = new Composite(parent, SWT.NONE);
		composite.setLayout(new GridLayout(2, false));
		textField = new Text(composite, SWT.BORDER);
		scale = new Scale(composite, SWT.HORIZONTAL);
		//
		scale.setMinimum(0);
		scale.setIncrement(1);
		scale.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));
		scale.addSelectionListener(new SelectionListener() {

			@Override
			public void widgetSelected(SelectionEvent e) {

				updateText();
				notifyConsumer();
			}

			@Override
			public void widgetDefaultSelected(SelectionEvent e) {

			}
		});
		textField.addFocusListener(new FocusListener() {

			@Override
			public void focusLost(FocusEvent e) {

			}

			@Override
			public void focusGained(FocusEvent e) {

				Display.getDefault().asyncExec(textField::selectAll);
			}
		});
		textField.addSelectionListener(new SelectionListener() {

			@Override
			public void widgetSelected(SelectionEvent e) {

			}

			@Override
			public void widgetDefaultSelected(SelectionEvent se) {

				try {
					setValue(NumberFormat.getInstance().parse(textField.getText()).doubleValue());
				} catch (RuntimeException | ParseException e) {
					// can't parse (yet)
				}
			}
		});
		GridData layoutData = new GridData(SWT.CENTER, SWT.CENTER, false, false);
		textField.setLayoutData(layoutData);
		updateText();
	}

	public Control getControl() {

		return composite;
	}

	private void updateText() {

		textField.setText(numberFormat.format(getValue()));
	}

	public double getValue() {

		int rawValue = scale.getSelection();
		int scaledValue = rawValue + min;
		return (double) scaledValue / (double) scaleFactor;
	}

	public void setValue(double value) {

		int scaled = (int) (value * scaleFactor) - min;
		setValueInternal(scaled);
	}

	private void setValueInternal(int scaled) {

		if(scaled != scale.getSelection()) {
			scale.setSelection(scaled);
			notifyConsumer();
		}
		updateText();
	}

	private void notifyConsumer() {

		double value = getValue();
		for(DoubleConsumer consumer : consumers) {
			consumer.accept(value);
		}
	}

	public void setRange(int min, int max) {

		this.min = min * scaleFactor;
		int maxScaled = (Math.abs(min) + Math.abs(max)) * scaleFactor;
		scale.setMaximum(maxScaled);
		GridData layoutData = (GridData) textField.getLayoutData();
		layoutData.widthHint = Math.max(getTextLength(numberFormat.format(min)), getTextLength(numberFormat.format(max)));
	}

	private int getTextLength(String str) {

		GC gc = new GC(textField);
		try {
			return gc.textExtent(str).x;
		} finally {
			gc.dispose();
		}
	}

	public void setPageIncrement(int increment) {

		scale.setPageIncrement(increment * scaleFactor);
	}

	public void addConsumer(DoubleConsumer consumer) {

		consumers.add(consumer);
	}

	/**
	 * Increments the slider by multiples of the page increment
	 *
	 * @param incrementValue
	 */
	public void increment(int incrementValue) {

		int increment = scale.getPageIncrement() * incrementValue;
		int current = scale.getSelection();
		int modulus = scale.getMaximum();
		current = absWithModulus(current, modulus);
		increment = absWithModulus(increment, modulus);
		if(increment > modulus - current) {
			setValueInternal(increment - (modulus - current));
		} else {
			setValueInternal((current + increment) % modulus);
		}
	}

	private static int absWithModulus(int value, int modulus) {

		value %= modulus;
		if(value < 0) {
			value += modulus;
		}
		return value;
	}
}
