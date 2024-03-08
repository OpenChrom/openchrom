/*******************************************************************************
 * Copyright (c) 2014, 2024 Lablicate GmbH.
 * 
 * All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0 which accompanies this distribution,
 * and is available at http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors:
 * Dr. Philip Wenig - initial API and implementation
 *******************************************************************************/
package net.openchrom.chromatogram.msd.peak.detector.supplier.amdis.internal.identifier;

import java.io.File;

import org.eclipse.chemclipse.logging.core.Logger;
import org.eclipse.chemclipse.support.settings.ApplicationSettings;

public class PathHelper {

	private static final Logger logger = Logger.getLogger(PathHelper.class);
	public static final String AMDIS_DETECTOR = "org.eclipse.chemclipse.chromatogram.msd.peak.detector.supplier.amdis"; // backwards compatibility

	/**
	 * Returns the file object (directory) where the chromatogram instances can
	 * temporarily be stored.
	 * 
	 * @return File
	 */
	public static File getStoragePath() {

		File file = new File(ApplicationSettings.getSettingsDirectory().getAbsolutePath() + File.separator + AMDIS_DETECTOR);
		/*
		 * Create the directory if it not exists.
		 */
		if(!file.exists()) {
			if(!file.mkdirs()) {
				logger.warn("The temporarily nist identifier directory could not be created: " + file.getAbsolutePath());
			}
		}
		return file;
	}

	/**
	 * DO NOT CALL THIS METHOD IF YOU NOT REALLY KNOW WHAT YOU ARE DOING.<br/>
	 * Cleans all temporarily stored files in the storage directory.<br/>
	 * This method will be called on bundle start and stop.
	 */
	public static void cleanStoragePath() {

		File directory = getStoragePath();
		deleteFiles(directory);
	}

	/**
	 * Deletes the given directory recursively.
	 * 
	 * @param directory
	 */
	private static void deleteFiles(File directory) {

		/*
		 * Delete all files in all directories.
		 */
		for(File file : directory.listFiles()) {
			if(file.isDirectory()) {
				deleteFiles(file);
			} else {
				if(!file.delete()) {
					logger.warn("The file " + file + "could not be deleted.");
				}
			}
		}
		/*
		 * Delete the directory if all files have been removed.
		 */
		if(!directory.delete()) {
			logger.warn("The directory " + directory + "could not be deleted.");
		}
	}
}
