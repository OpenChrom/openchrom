/*******************************************************************************
 * Copyright (c) 2018, 2019 Lablicate GmbH.
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

import java.text.DecimalFormat;

import org.eclipse.chemclipse.rcp.ui.icons.core.ApplicationImageFactory;
import org.eclipse.chemclipse.rcp.ui.icons.core.IApplicationImage;
import org.eclipse.chemclipse.support.text.ValueFormat;
import org.eclipse.chemclipse.support.ui.provider.AbstractChemClipseLabelProvider;
import org.eclipse.swt.graphics.Image;

import net.openchrom.xxd.process.supplier.templates.model.IdentifierSetting;

public class PeakIdentifierLabelProvider extends AbstractChemClipseLabelProvider {

	public static final String NAME = "Name";
	public static final String START_RETENTION_TIME = "Start Retention Time";
	public static final String STOP_RETENTION_TIME = "Stop Retention Time";
	public static final String CAS_NUMBER = "CAS";
	public static final String COMMENTS = "Comments";
	public static final String CONTRIBUTOR = "Contributor";
	public static final String REFERENCE_ID = "Reference Id";
	public static final String TRACES = "Traces";
	//
	private DecimalFormat decimalFormat = ValueFormat.getDecimalFormatEnglish("0.0##");
	//
	public static final String[] TITLES = { //
			NAME, //
			START_RETENTION_TIME, //
			STOP_RETENTION_TIME, //
			CAS_NUMBER, //
			COMMENTS, //
			CONTRIBUTOR, //
			REFERENCE_ID, //
			TRACES //
	};
	public static final int[] BOUNDS = { //
			200, //
			100, //
			100, //
			100, //
			100, //
			100, //
			50, //
			100 //
	};

	@Override
	public Image getColumnImage(Object element, int columnIndex) {

		if(columnIndex == 0) {
			return getImage(element);
		}
		return null;
	}

	@Override
	public String getColumnText(Object element, int columnIndex) {

		String text = "";
		if(element instanceof IdentifierSetting) {
			IdentifierSetting setting = (IdentifierSetting)element;
			switch(columnIndex) {
				case 0:
					text = setting.getName();
					break;
				case 1:
					text = decimalFormat.format(setting.getStartRetentionTimeMinutes());
					break;
				case 2:
					text = decimalFormat.format(setting.getStopRetentionTimeMinutes());
					break;
				case 3:
					text = setting.getCasNumber();
					break;
				case 4:
					text = setting.getComments();
					break;
				case 5:
					text = setting.getContributor();
					break;
				case 6:
					text = setting.getReferenceId();
					break;
				case 7:
					text = setting.getTraces();
					break;
				default:
					text = "n.v.";
			}
		}
		return text;
	}

	@Override
	public Image getImage(Object element) {

		return ApplicationImageFactory.getInstance().getImage(IApplicationImage.IMAGE_IDENTIFY_PEAKS, IApplicationImage.SIZE_16x16);
	}
}
