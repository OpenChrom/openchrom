/*******************************************************************************
 * Copyright (c) 2023, 2024 Lablicate GmbH.
 * 
 * All rights reserved.
 * 
 * Contributors:
 * Philip Wenig - initial API and implementation
 *******************************************************************************/
package net.openchrom.xxd.base.ui.internal.provider;

import java.text.DecimalFormat;

import org.eclipse.chemclipse.rcp.ui.icons.core.ApplicationImageFactory;
import org.eclipse.chemclipse.rcp.ui.icons.core.IApplicationImage;
import org.eclipse.chemclipse.support.text.ValueFormat;
import org.eclipse.chemclipse.support.ui.provider.AbstractChemClipseLabelProvider;
import org.eclipse.swt.graphics.Image;

import net.openchrom.xxd.base.model.Suspect;
import net.openchrom.xxd.base.model.SuspectEvaluation;

public class SuspectListLabelProvider extends AbstractChemClipseLabelProvider {

	public static final String NAME = "Name";
	public static final String STATUS = "Status";
	public static final String RETENTION_TIME_TARGET = "Retention Time (Target)";
	public static final String RETENTION_TIME_DELTA = "Retention Time (Delta)";
	public static final String RETENTION_INDEX_TARGET = "Retention Index (Target)";
	public static final String RETENTION_INDEX_DELTA = "Retention Index (Delta)";
	public static final String GROUPS = "Groups";
	//
	public static final int INDEX_STATUS = 1;
	//
	private DecimalFormat decimalFormatRT = ValueFormat.getDecimalFormatEnglish("0.000");
	private DecimalFormat decimalFormatRI = ValueFormat.getDecimalFormatEnglish("0");
	//
	public static final String[] TITLES = { //
			NAME, //
			STATUS, //
			RETENTION_TIME_TARGET, //
			RETENTION_TIME_DELTA, //
			RETENTION_INDEX_TARGET, //
			RETENTION_INDEX_DELTA, //
			GROUPS //
	};
	//
	public static final int[] BOUNDS = { //
			150, //
			80, //
			100, //
			100, //
			100, //
			100, //
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
		if(element instanceof SuspectEvaluation suspectEvaluation) {
			Suspect suspect = suspectEvaluation.getSuspect();
			switch(columnIndex) {
				case 0:
					text = suspect.getName();
					break;
				case 1:
					text = suspectEvaluation.getStatus();
					break;
				case 2:
					text = decimalFormatRT.format(suspect.getRetentionTimeMinutesTarget());
					break;
				case 3:
					text = decimalFormatRT.format(suspect.getRetentionTimeMinutesDelta());
					break;
				case 4:
					text = decimalFormatRI.format(suspect.getRetentionIndexTarget());
					break;
				case 5:
					text = decimalFormatRI.format(suspect.getRetentionIndexDelta());
					break;
				case 6:
					text = suspectEvaluation.getGroupsAsString();
					break;
				default:
					text = "n.v.";
			}
		}
		//
		return text;
	}

	@Override
	public Image getImage(Object element) {

		return ApplicationImageFactory.getInstance().getImage(IApplicationImage.IMAGE_PEAK, IApplicationImage.SIZE_16x16);
	}
}