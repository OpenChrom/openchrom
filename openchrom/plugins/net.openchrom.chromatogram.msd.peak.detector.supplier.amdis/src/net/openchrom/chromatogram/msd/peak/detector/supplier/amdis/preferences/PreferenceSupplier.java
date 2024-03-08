/*******************************************************************************
 * Copyright (c) 2010, 2024 Lablicate GmbH.
 * 
 * All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0 which accompanies this distribution,
 * and is available at http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors:
 * Philip Wenig - initial API and implementation
 *******************************************************************************/
package net.openchrom.chromatogram.msd.peak.detector.supplier.amdis.preferences;

import java.io.File;

import org.eclipse.chemclipse.logging.core.Logger;
import org.eclipse.chemclipse.support.preferences.AbstractPreferenceSupplier;
import org.eclipse.chemclipse.support.preferences.IPreferenceSupplier;

import net.openchrom.chromatogram.msd.peak.detector.supplier.amdis.Activator;
import net.openchrom.chromatogram.msd.peak.detector.supplier.amdis.model.AdjacentPeakSubtraction;
import net.openchrom.chromatogram.msd.peak.detector.supplier.amdis.model.Option;
import net.openchrom.chromatogram.msd.peak.detector.supplier.amdis.model.Resolution;
import net.openchrom.chromatogram.msd.peak.detector.supplier.amdis.model.Sensitivity;
import net.openchrom.chromatogram.msd.peak.detector.supplier.amdis.model.ShapeRequirements;
import net.openchrom.chromatogram.msd.peak.detector.supplier.amdis.model.Threshold;
import net.openchrom.chromatogram.msd.peak.detector.supplier.amdis.settings.IOnsiteSettings;
import net.openchrom.chromatogram.msd.peak.detector.supplier.amdis.settings.IProcessSettings;
import net.openchrom.chromatogram.msd.peak.detector.supplier.amdis.settings.ModelPeakOption;
import net.openchrom.chromatogram.msd.peak.detector.supplier.amdis.settings.SettingsAMDIS;
import net.openchrom.chromatogram.msd.peak.detector.supplier.amdis.settings.SettingsELU;

public class PreferenceSupplier extends AbstractPreferenceSupplier implements IPreferenceSupplier {

	private static final Logger logger = Logger.getLogger(PreferenceSupplier.class);
	//
	public static final String IDENTIFIER = "AMDIS Identifier";
	//
	public static final String AMDIS_EXECUTABLE = "AMDIS32$.exe";
	//
	public static final String P_MAC_WINE_BINARY = "macWineBinary";
	public static final String DEF_MAC_WINE_BINARY = "/Applications/Wine.app";
	//
	public static final String P_AMDIS_APPLICATION_PATH = "amdisApplication";
	public static final String DEF_AMDIS_APPLICATION_PATH = "";
	public static final String P_AMDIS_TMP_PATH = "amdisTmpPath";
	public static final String DEF_AMDIS_TMP_PATH = "";
	/*
	 * AMDIS settings
	 */
	public static final String P_LOW_MZ_AUTO = "lowMzAuto";
	public static final boolean DEF_LOW_MZ_AUTO = true;
	public static final String P_START_MZ = "startMz";
	public static final int DEF_START_MZ = 35;
	public static final String P_HIGH_MZ_AUTO = "highMzAuto";
	public static final boolean DEF_HIGH_MZ_AUTO = true;
	public static final String P_STOP_MZ = "stopMz";
	public static final int DEF_STOP_MZ = 600;
	public static final String P_OMIT_MZ = "omitMz";
	public static final boolean DEF_OMIT_MZ = false;
	public static final String P_OMITED_MZ = "omitedMz";
	public static final String DEF_OMITED_MZ = "0 18 28";
	//
	public static final String P_USE_SOLVENT_TAILING = "useSolventTailing";
	public static final boolean DEF_USE_SOLVENT_TAILING = true;
	public static final String P_SOLVENT_TAILING_MZ = "solventTailingMz";
	public static final int DEF_SOLVENT_TAILING_MZ = IOnsiteSettings.VALUE_SOLVENT_TAILING_MZ;
	public static final String P_USE_COLUMN_BLEED = "useColumnBleed";
	public static final boolean DEF_USE_COLUMN_BLEED = true;
	public static final String P_COLUMN_BLEED_MZ = "columnBleedMz";
	public static final int DEF_COLUMN_BLEED_MZ = IOnsiteSettings.VALUE_COLUMN_BLEED_MZ;
	//
	public static final String P_THRESHOLD = "threshold";
	public static final String DEF_THRESHOLD = Threshold.MEDIUM.value();
	public static final String P_PEAK_WIDTH = "peakWidth"; // Component Width
	public static final int DEF_PEAK_WIDTH = 12;
	//
	public static final String P_ADJACENT_PEAK_SUBTRACTION = "adjacentPeakSubtraction";
	public static final String DEF_ADJACENT_PEAK_SUBTRACTION = AdjacentPeakSubtraction.NONE.value();
	public static final String P_RESOLUTION = "resolution";
	public static final String DEF_RESOLUTION = Resolution.MEDIUM.value();
	public static final String P_SENSITIVITY = "sensitivity";
	public static final String DEF_SENSITIVITY = Sensitivity.MEDIUM.value();
	public static final String P_SHAPE_REQUIREMENTS = "shapeRequirements";
	public static final String DEF_SHAPE_REQUIREMENTS = ShapeRequirements.HIGH.value();
	/*
	 * Extra settings
	 */
	public static final String P_MIN_SN_RATIO = "minSignalToNoiseRatio";
	public static final float DEF_MIN_SN_RATIO = 0.0f; // 0 = all peaks will be added
	public static final String P_MIN_LEADING = "minLeading";
	public static final float DEF_MIN_LEADING = 0.1f;
	public static final String P_MAX_LEADING = "maxLeading";
	public static final float DEF_MAX_LEADING = 2.0f;
	public static final String P_MIN_TAILING = "minTailing";
	public static final float DEF_MIN_TAILING = 0.1f;
	public static final String P_MAX_TAILING = "maxTailing";
	public static final float DEF_MAX_TAILING = 2.0f;
	public static final String P_MODEL_PEAK_OPTION = "modelPeakOption";
	public static final String DEF_MODEL_PEAK_OPTION = ModelPeakOption.MP1.name();
	//
	public static final String P_PATH_ELU_FILE = "pathELUFile";
	public static final String DEF_PATH_ELU_FILE = "";

	public static IPreferenceSupplier INSTANCE() {

		return INSTANCE(PreferenceSupplier.class);
	}

	@Override
	public String getPreferenceNode() {

		return Activator.getContext().getBundle().getSymbolicName();
	}

	@Override
	public void initializeDefaults() {

		/*
		 * AMDIS settings
		 */
		putDefault(P_LOW_MZ_AUTO, Boolean.toString(DEF_LOW_MZ_AUTO));
		putDefault(P_START_MZ, Integer.toString(DEF_START_MZ));
		putDefault(P_HIGH_MZ_AUTO, Boolean.toString(DEF_HIGH_MZ_AUTO));
		putDefault(P_STOP_MZ, Integer.toString(DEF_STOP_MZ));
		putDefault(P_OMIT_MZ, Boolean.toString(DEF_OMIT_MZ));
		putDefault(P_OMITED_MZ, DEF_OMITED_MZ);
		//
		putDefault(P_USE_SOLVENT_TAILING, Boolean.toString(DEF_USE_SOLVENT_TAILING));
		putDefault(P_SOLVENT_TAILING_MZ, Integer.toString(DEF_SOLVENT_TAILING_MZ));
		putDefault(P_USE_COLUMN_BLEED, Boolean.toString(DEF_USE_COLUMN_BLEED));
		putDefault(P_COLUMN_BLEED_MZ, Integer.toString(DEF_COLUMN_BLEED_MZ));
		//
		putDefault(P_THRESHOLD, DEF_THRESHOLD);
		putDefault(P_PEAK_WIDTH, Integer.toString(DEF_PEAK_WIDTH));
		putDefault(P_ADJACENT_PEAK_SUBTRACTION, DEF_ADJACENT_PEAK_SUBTRACTION);
		putDefault(P_RESOLUTION, DEF_RESOLUTION);
		putDefault(P_SENSITIVITY, DEF_SENSITIVITY);
		putDefault(P_SHAPE_REQUIREMENTS, DEF_SHAPE_REQUIREMENTS);
		/*
		 * Extra settings
		 */
		putDefault(P_MIN_SN_RATIO, Float.toString(DEF_MIN_SN_RATIO));
		putDefault(P_MIN_LEADING, Float.toString(DEF_MIN_LEADING));
		putDefault(P_MAX_LEADING, Float.toString(DEF_MAX_LEADING));
		putDefault(P_MIN_TAILING, Float.toString(DEF_MIN_TAILING));
		putDefault(P_MAX_TAILING, Float.toString(DEF_MAX_TAILING));
		putDefault(P_MODEL_PEAK_OPTION, DEF_MODEL_PEAK_OPTION);
		//
		putDefault(P_PATH_ELU_FILE, DEF_PATH_ELU_FILE);
		//
	}

	public static File getInstallationFolder() {

		return getFolder(P_AMDIS_APPLICATION_PATH);
	}

	public static File getDataFolder() {

		return getFolder(P_AMDIS_TMP_PATH);
	}

	public static SettingsAMDIS getSettingsAMDIS() {

		SettingsAMDIS settings = new SettingsAMDIS();
		//
		setApplicationSettings(settings);
		setProcessSettings(settings);
		setOnsiteSettings(settings.getOnsiteSettings());
		//
		return settings;
	}

	public static SettingsELU getSettingsELU() {

		SettingsELU settings = new SettingsELU();
		//
		setProcessSettings(settings);
		setResultSettings(settings);
		//
		return settings;
	}

	public static void setApplicationSettings(SettingsAMDIS settings) {

		settings.setAmdisFolder(getInstallationFolder());
		settings.setTmpFolder(getDataFolder());
	}

	public static void setProcessSettings(IProcessSettings processSettings) {

		processSettings.setMinSignalToNoiseRatio(INSTANCE().getFloat(P_MIN_SN_RATIO, DEF_MIN_SN_RATIO));
		processSettings.setMinLeading(INSTANCE().getFloat(P_MIN_LEADING, DEF_MIN_LEADING));
		processSettings.setMaxLeading(INSTANCE().getFloat(P_MAX_LEADING, DEF_MAX_LEADING));
		processSettings.setMinTailing(INSTANCE().getFloat(P_MIN_TAILING, DEF_MIN_TAILING));
		processSettings.setMaxTailing(INSTANCE().getFloat(P_MAX_TAILING, DEF_MAX_TAILING));
		processSettings.setModelPeakOption(ModelPeakOption.valueOf(INSTANCE().get(P_MODEL_PEAK_OPTION, DEF_MODEL_PEAK_OPTION)));
	}

	public static void setResultSettings(SettingsELU settings) {

		settings.setResultFile(new File(INSTANCE().get(P_PATH_ELU_FILE, DEF_PATH_ELU_FILE)));
	}

	public static void setOnsiteSettings(IOnsiteSettings onsiteSettings) {

		onsiteSettings.setValue(IOnsiteSettings.KEY_LOW_MZ_AUTO, INSTANCE().getBoolean(P_LOW_MZ_AUTO, DEF_LOW_MZ_AUTO) ? Option.YES.value() : Option.NO.value());
		onsiteSettings.setValue(IOnsiteSettings.KEY_START_MZ, Integer.toString(INSTANCE().getInteger(P_START_MZ, DEF_START_MZ)));
		onsiteSettings.setValue(IOnsiteSettings.KEY_HIGH_MZ_AUTO, INSTANCE().getBoolean(P_HIGH_MZ_AUTO, DEF_HIGH_MZ_AUTO) ? Option.YES.value() : Option.NO.value());
		onsiteSettings.setValue(IOnsiteSettings.KEY_STOP_MZ, Integer.toString(INSTANCE().getInteger(P_STOP_MZ, DEF_STOP_MZ)));
		onsiteSettings.setValue(IOnsiteSettings.KEY_OMIT_MZ, INSTANCE().getBoolean(P_OMIT_MZ, DEF_OMIT_MZ) ? Option.YES.value() : Option.NO.value());
		onsiteSettings.setValue(IOnsiteSettings.KEY_OMITED_MZ, INSTANCE().get(P_OMITED_MZ, DEF_OMITED_MZ));
		//
		onsiteSettings.setValue(IOnsiteSettings.KEY_USE_SOLVENT_TAILING, INSTANCE().getBoolean(P_USE_SOLVENT_TAILING, DEF_USE_SOLVENT_TAILING) ? Option.YES.value() : Option.NO.value());
		onsiteSettings.setValue(IOnsiteSettings.KEY_SOLVENT_TAILING_MZ, Integer.toString(INSTANCE().getInteger(P_SOLVENT_TAILING_MZ, DEF_SOLVENT_TAILING_MZ)));
		onsiteSettings.setValue(IOnsiteSettings.KEY_USE_COLUMN_BLEED, INSTANCE().getBoolean(P_USE_COLUMN_BLEED, DEF_USE_COLUMN_BLEED) ? Option.YES.value() : Option.NO.value());
		onsiteSettings.setValue(IOnsiteSettings.KEY_COLUMN_BLEED_MZ, Integer.toString(INSTANCE().getInteger(P_COLUMN_BLEED_MZ, DEF_COLUMN_BLEED_MZ)));
		//
		onsiteSettings.setValue(IOnsiteSettings.KEY_THRESHOLD, INSTANCE().get(P_THRESHOLD, DEF_THRESHOLD));
		onsiteSettings.setValue(IOnsiteSettings.KEY_PEAK_WIDTH, Integer.toString(INSTANCE().getInteger(P_PEAK_WIDTH, DEF_PEAK_WIDTH)));
		onsiteSettings.setValue(IOnsiteSettings.KEY_ADJACENT_PEAK_SUBTRACTION, INSTANCE().get(P_ADJACENT_PEAK_SUBTRACTION, DEF_ADJACENT_PEAK_SUBTRACTION));
		onsiteSettings.setValue(IOnsiteSettings.KEY_RESOLUTION, INSTANCE().get(P_RESOLUTION, DEF_RESOLUTION));
		onsiteSettings.setValue(IOnsiteSettings.KEY_SENSITIVITY, INSTANCE().get(P_SENSITIVITY, DEF_SENSITIVITY));
		onsiteSettings.setValue(IOnsiteSettings.KEY_SHAPE_REQUIREMENTS, INSTANCE().get(P_SHAPE_REQUIREMENTS, DEF_SHAPE_REQUIREMENTS));
	}

	public static String getMacWineBinary() {

		return INSTANCE().get(P_MAC_WINE_BINARY, DEF_MAC_WINE_BINARY);
	}

	public static File getFolder(String key) {

		try {
			String path = INSTANCE().get(key, "");
			if(path != null && !path.isEmpty()) {
				//
				File file = new File(path);
				if(file.isFile()) {
					file.getParentFile();
				} else {
					return file;
				}
			}
		} catch(Exception e) {
			logger.warn(e);
		}
		//
		return null;
	}
}