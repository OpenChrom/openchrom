/*******************************************************************************
 * Copyright (c) 2011, 2015 Philip (eselmeister) Wenig.
 * 
 * All rights reserved.
 *******************************************************************************/
package net.openchrom.msd.converter.supplier.pdf.internal.support;

import java.io.File;
import net.chemclipse.logging.core.Logger;
import net.chemclipse.support.settings.ApplicationSettings;

public class PathHelper {

	private static final Logger logger = Logger.getLogger(PathHelper.class);
	public static final String PDF_CONVERTER = "net.chemclipse.msd.converter.supplier.pdf";

	/**
	 * Returns the file object (directory) where the chromatogram instances can
	 * temporarily be stored.
	 * 
	 * @return File
	 */
	public static File getStoragePath() {

		File file = new File(ApplicationSettings.getSettingsDirectory().getAbsolutePath() + File.separator + PDF_CONVERTER);
		/*
		 * Create the directory if it not exists.
		 */
		if(!file.exists()) {
			if(!file.mkdirs()) {
				logger.warn("The temporarily pdf converter directory could not be created: " + file.getAbsolutePath());
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
