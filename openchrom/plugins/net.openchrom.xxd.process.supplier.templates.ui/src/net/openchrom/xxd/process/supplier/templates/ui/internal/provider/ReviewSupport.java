/*******************************************************************************
 * Copyright (c) 2020 Lablicate GmbH.
 * 
 * All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0 which accompanies this distribution,
 * and is available at http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors:
 * Philip Wenig - initial API and implementation
 *******************************************************************************/
package net.openchrom.xxd.process.supplier.templates.ui.internal.provider;

import java.util.List;
import java.util.Set;

import org.eclipse.chemclipse.model.core.IPeak;
import org.eclipse.chemclipse.model.identifier.IIdentificationTarget;
import org.eclipse.chemclipse.model.identifier.ILibraryInformation;

import net.openchrom.xxd.process.supplier.templates.model.ReviewSetting;

public class ReviewSupport {

	public static String getName(IPeak peak) {

		/*
		 * Is a peak name set?
		 */
		String name = peak.getName();
		/*
		 * No peak name set.
		 * Then try to get the peak or scan best match.
		 */
		if(name == null) {
			ILibraryInformation libraryInformation = IIdentificationTarget.getBestLibraryInformation(peak.getTargets());
			if(libraryInformation != null) {
				name = libraryInformation.getName();
			}
		}
		/*
		 * No hit at all?
		 * Then return an empty String.
		 */
		return name != null ? name : "";
	}

	public static boolean isPeakReviewed(IPeak peak) {

		boolean status = false;
		if(peak != null) {
			status = peak.getClassifier().contains(ReviewSetting.CLASSIFIER_REVIEW_OK);
		}
		return status;
	}

	public static void setReview(IPeak peak, boolean reviewSuccessful) {

		if(peak != null) {
			if(reviewSuccessful) {
				peak.addClassifier(ReviewSetting.CLASSIFIER_REVIEW_OK);
			} else {
				peak.removeClassifier(ReviewSetting.CLASSIFIER_REVIEW_OK);
			}
		}
	}

	public static boolean isCompoundAvailable(List<IPeak> peaks, ReviewSetting reviewSetting) {

		if(peaks != null && reviewSetting != null) {
			for(IPeak peak : peaks) {
				if(isCompoundAvailable(peak.getTargets(), reviewSetting)) {
					return true;
				}
			}
		}
		return false;
	}

	public static boolean isCompoundAvailable(Set<IIdentificationTarget> targets, ReviewSetting reviewSetting) {

		if(targets != null && reviewSetting != null) {
			for(IIdentificationTarget target : targets) {
				if(isCompoundAvailable(target, reviewSetting)) {
					return true;
				}
			}
		}
		return false;
	}

	public static boolean isCompoundAvailable(IIdentificationTarget target, ReviewSetting reviewSetting) {

		if(target != null) {
			ILibraryInformation libraryInformation = target.getLibraryInformation();
			String name = libraryInformation.getName();
			String cas = libraryInformation.getCasNumber();
			if(name.equals(reviewSetting.getName())) {
				return true;
			} else if(cas.equals(reviewSetting.getCasNumber())) {
				return true;
			}
		}
		return false;
	}
}
