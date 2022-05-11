/*******************************************************************************
 * Copyright (c) 2019, 2022 Lablicate GmbH.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors:
 * Dr. Philip Wenig - initial API and implementation
 *******************************************************************************/
package net.openchrom.xxd.process.supplier.templates.ui.internal.provider;

import net.openchrom.xxd.process.supplier.templates.model.IntegratorSetting;
import net.openchrom.xxd.process.supplier.templates.ui.swt.PeakIntegratorListUI;

public class PeakIntegratorEditingSupport extends AbstractTemplateEditingSupport {

	public PeakIntegratorEditingSupport(PeakIntegratorListUI tableViewer, String column) {

		super(tableViewer, column);
	}

	@Override
	protected Object getValue(Object element) {

		if(element instanceof IntegratorSetting) {
			IntegratorSetting setting = (IntegratorSetting)element;
			Object object = super.getValue(element);
			if(object != null) {
				return object;
			} else {
				switch(getColumn()) {
					case AbstractTemplateLabelProvider.IDENTIFIER:
						return setting.getIdentifier();
					case AbstractTemplateLabelProvider.INTEGRATOR:
						String item = setting.getIntegrator();
						if(item.equals(IntegratorSetting.INTEGRATOR_NAME_TRAPEZOID)) {
							return 0;
						} else if(item.equals(IntegratorSetting.INTEGRATOR_NAME_MAX)) {
							return 1;
						} else {
							return 0;
						}
				}
			}
		}
		//
		return false;
	}

	@Override
	protected void setValue(Object element, Object value) {

		if(element instanceof IntegratorSetting) {
			IntegratorSetting setting = (IntegratorSetting)element;
			super.setValue(element, value);
			switch(getColumn()) {
				case AbstractTemplateLabelProvider.IDENTIFIER:
					setting.setIdentifier((String)value);
					break;
				case AbstractTemplateLabelProvider.INTEGRATOR:
					String integrator = integratorItems[(int)value];
					setting.setIntegrator(integrator);
					break;
			}
			//
			updateTableViewer();
		}
	}
}