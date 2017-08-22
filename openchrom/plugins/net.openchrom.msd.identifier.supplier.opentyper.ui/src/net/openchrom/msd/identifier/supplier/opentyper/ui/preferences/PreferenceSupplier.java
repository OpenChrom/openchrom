package net.openchrom.msd.identifier.supplier.opentyper.ui.preferences;
/*******************************************************************************
 * Copyright (c) 2017 Lablicate GmbH.
 * 
 * All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0 which accompanies this distribution,
 * and is available at http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors:
 * Dr. Philip Wenig - initial API and implementation
 * Matthias Mailänder - initial API and implementation
 *******************************************************************************/

import net.openchrom.msd.identifier.supplier.opentyper.ui.Activator;

import org.eclipse.jface.preference.IPreferenceStore;

public class PreferenceSupplier {

	public class PreferenceConstants {

		public static final String P_SELECTED_DRIVE_PATH = "selectedDrivePath";
		public static final String P_SELECTED_HOME_PATH = "selectedHomePath";
		public static final String P_SELECTED_USER_LOCATION_PATH = "selectedUserLocationPath";
		//
		public static final String P_USER_LOCATION_PATH = "userLocation";
	}

	/*
	 * Use only static methods.
	 */
	private PreferenceSupplier() {
	}

	public static String getSelectedDrivePath() {

		IPreferenceStore store = Activator.getDefault().getPreferenceStore();
		return store.getString(PreferenceConstants.P_SELECTED_DRIVE_PATH);
	}

	public static void setSelectedDrivePath(String directoryPath) {

		IPreferenceStore store = Activator.getDefault().getPreferenceStore();
		store.setValue(PreferenceConstants.P_SELECTED_DRIVE_PATH, directoryPath);
	}

	public static String getSelectedHomePath() {

		IPreferenceStore store = Activator.getDefault().getPreferenceStore();
		return store.getString(PreferenceConstants.P_SELECTED_HOME_PATH);
	}

	public static void setSelectedHomePath(String directoryPath) {

		IPreferenceStore store = Activator.getDefault().getPreferenceStore();
		store.setValue(PreferenceConstants.P_SELECTED_HOME_PATH, directoryPath);
	}

	public static String getSelectedUserLocationPath() {

		IPreferenceStore store = Activator.getDefault().getPreferenceStore();
		return store.getString(PreferenceConstants.P_SELECTED_USER_LOCATION_PATH);
	}

	public static void setSelectedUserLocationPath(String directoryPath) {

		IPreferenceStore store = Activator.getDefault().getPreferenceStore();
		store.setValue(PreferenceConstants.P_SELECTED_USER_LOCATION_PATH, directoryPath);
	}

	public static String getUserLocationPath() {

		IPreferenceStore store = Activator.getDefault().getPreferenceStore();
		return store.getString(PreferenceConstants.P_USER_LOCATION_PATH);
	}

	public static void setUserLocationPath(String directoryPath) {

		IPreferenceStore store = Activator.getDefault().getPreferenceStore();
		store.setValue(PreferenceConstants.P_USER_LOCATION_PATH, directoryPath);
	}
}
