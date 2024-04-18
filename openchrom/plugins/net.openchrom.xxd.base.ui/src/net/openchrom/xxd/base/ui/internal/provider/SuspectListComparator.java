/*******************************************************************************
 * Copyright (c) 2023, 2024 Lablicate GmbH.
 * 
 * All rights reserved.
 * 
 * Contributors:
 * Philip Wenig - initial API and implementation
 *******************************************************************************/
package net.openchrom.xxd.base.ui.internal.provider;

import org.eclipse.chemclipse.support.ui.swt.AbstractRecordTableComparator;
import org.eclipse.chemclipse.support.ui.swt.IRecordTableComparator;
import org.eclipse.jface.viewers.Viewer;

import net.openchrom.xxd.base.model.Suspect;
import net.openchrom.xxd.base.model.SuspectEvaluation;

public class SuspectListComparator extends AbstractRecordTableComparator implements IRecordTableComparator {

	@Override
	public int compare(Viewer viewer, Object e1, Object e2) {

		int sortOrder = 0;
		if(e1 instanceof SuspectEvaluation suspectEvaluation1 && e2 instanceof SuspectEvaluation suspectEvaluation2) {
			//
			Suspect suspect1 = suspectEvaluation1.getSuspect();
			Suspect suspect2 = suspectEvaluation2.getSuspect();
			//
			switch(getPropertyIndex()) {
				case 0:
					if(suspect1.isDefault()) {
						sortOrder = -1;
					} else if(suspect2.isDefault()) {
						sortOrder = 1;
					} else {
						sortOrder = suspect2.getName().compareTo(suspect1.getName());
					}
					break;
				case 1:
					sortOrder = suspectEvaluation2.getStatus().compareTo(suspectEvaluation1.getStatus());
					break;
				case 2:
					sortOrder = Double.compare(suspect2.getRetentionTimeMinutesTarget(), suspect1.getRetentionTimeMinutesTarget());
					break;
				case 3:
					sortOrder = Double.compare(suspect2.getRetentionTimeMinutesDelta(), suspect1.getRetentionTimeMinutesDelta());
					break;
				case 4:
					sortOrder = Float.compare(suspect2.getRetentionIndexTarget(), suspect1.getRetentionIndexTarget());
					break;
				case 5:
					sortOrder = Float.compare(suspect2.getRetentionIndexDelta(), suspect1.getRetentionIndexDelta());
					break;
				case 6:
					sortOrder = suspectEvaluation2.getGroupsAsString().compareTo(suspectEvaluation1.getGroupsAsString());
					break;
				default:
					sortOrder = 0;
					break;
			}
		}
		//
		if(getDirection() == ASCENDING) {
			sortOrder = -sortOrder;
		}
		//
		return sortOrder;
	}
}