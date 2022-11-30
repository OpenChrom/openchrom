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
package net.openchrom.xxd.process.supplier.templates.ui.support;

import java.util.Set;

import org.eclipse.chemclipse.model.identifier.IIdentificationTarget;
import org.eclipse.chemclipse.model.identifier.ILibraryInformation;

import net.openchrom.xxd.process.supplier.templates.model.DetectorSetting;

public class DetectorSupport {

	public static boolean isCompoundAvailable(Set<IIdentificationTarget> targets, DetectorSetting detectorSetting) {

		if(targets != null && detectorSetting != null) {
			for(IIdentificationTarget target : targets) {
				if(isCompoundAvailable(target, detectorSetting)) {
					return true;
				}
			}
		}
		return false;
	}

	public static boolean isCompoundAvailable(IIdentificationTarget target, DetectorSetting detectorSetting) {

		if(target != null) {
			ILibraryInformation libraryInformation = target.getLibraryInformation();
			/*
			 * NAME
			 */
			String namePeak = libraryInformation.getName();
			String nameReview = detectorSetting.getName();
			if(!namePeak.isEmpty() && namePeak.equals(nameReview)) {
				return true;
			}
		}
		return false;
	}
}