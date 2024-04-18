/*******************************************************************************
 * Copyright (c) 2024 Lablicate GmbH.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors:
 * Philip Wenig - initial API and implementation
 *******************************************************************************/
package net.openchrom.xxd.base.model.io;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Map;

import com.google.gson.Gson;

import net.openchrom.xxd.base.model.GroupMarker;
import net.openchrom.xxd.base.model.Suspect;
import net.openchrom.xxd.base.model.SuspectMatrix;

public class SuspectMatricesIO {

	public static final String VERSION_1000 = "1.0.0.0";
	public static final String VERSION_LATEST = VERSION_1000;

	/*
	 * Import
	 */
	@SuppressWarnings("unchecked")
	public static List<SuspectMatrix> fromJson(String items) {

		List<SuspectMatrix> suspectMatrices = new ArrayList<>();
		//
		if(!"".equals(items)) {
			Gson gson = new Gson();
			Object object = gson.fromJson(items, Object.class);
			if(object instanceof List list) {
				for(Object entry : list) {
					if(entry instanceof Map mapMatrix) {
						/*
						 * "name": "Betula",
						 * "version": "1.0.0.0",
						 * "suspects": [...]
						 */
						String version = mapMatrix.getOrDefault("version", "").toString();
						if(version.equals(VERSION_1000)) {
							parseVersion1000(suspectMatrices, mapMatrix);
						}
					}
				}
			}
		}
		//
		return suspectMatrices;
	}

	/*
	 * Export
	 */
	public static String toJson(Collection<SuspectMatrix> settings) {

		/*
		 * Don't export the default entry.
		 */
		List<SuspectMatrix> suspectMatrices = new ArrayList<>(settings);
		for(SuspectMatrix setting : settings) {
			SuspectMatrix suspectMatrix = new SuspectMatrix(setting.getName(), false);
			for(Suspect suspect : setting.getSuspects()) {
				if(!suspect.isDefault()) {
					suspectMatrix.add(suspect);
				}
			}
		}
		/*
		 * Sort the entries on demand.
		 */
		Collections.sort(suspectMatrices, (c1, c2) -> c1.getName().compareTo(c2.getName()));
		/*
		 * JSON
		 */
		Gson gson = new Gson();
		return gson.toJson(suspectMatrices).trim();
	}

	@SuppressWarnings({"rawtypes", "unchecked"})
	private static void parseVersion1000(List<SuspectMatrix> suspectMatrices, Map mapMatrix) {

		String nameMatrix = mapMatrix.getOrDefault("name", "").toString();
		if(!nameMatrix.isBlank()) {
			SuspectMatrix suspectMatrix = new SuspectMatrix(nameMatrix, true);
			Object entrySuspects = mapMatrix.get("suspects");
			if(entrySuspects instanceof List listSuspects) {
				for(Object entrySuspect : listSuspects) {
					if(entrySuspect instanceof Map mapSuspect) {
						/*
						 * "name": "Crotonyl isothiocyanate",
						 * "casNumber": "",
						 * "retentionTimeTarget": 0,
						 * "retentionTimeDelta": 0,
						 * "retentionIndexTarget": 0.0,
						 * "retentionIndexDelta": 0.0,
						 * "groupMarkers": [...]
						 */
						String nameSuspect = mapSuspect.getOrDefault("name", "").toString();
						if(!nameSuspect.isBlank()) {
							/*
							 * Suspect
							 */
							Suspect suspect = new Suspect(nameSuspect);
							suspect.setCasNumber(mapSuspect.getOrDefault("casNumber", "").toString());
							suspect.setRetentionTimeMinutesTarget(getRetentionTime(mapSuspect.getOrDefault("retentionTimeMinutesTarget", "")));
							suspect.setRetentionTimeMinutesDelta(getRetentionTime(mapSuspect.getOrDefault("retentionTimeMinutesDelta", "")));
							suspect.setRetentionIndexTarget(getRetentionIndex(mapSuspect.getOrDefault("retentionIndexTarget", "")));
							suspect.setRetentionIndexDelta(getRetentionIndex(mapSuspect.getOrDefault("retentionIndexDelta", "")));
							suspectMatrix.add(suspect);
							/*
							 * Group Marker
							 */
							Object entryGroups = mapSuspect.get("groupMarkers");
							if(entryGroups instanceof List listGroups) {
								for(Object entryGroup : listGroups) {
									if(entryGroup instanceof Map mapGroupMarker) {
										String nameGroup = mapGroupMarker.getOrDefault("name", "").toString();
										double areaPercent = getValue(mapGroupMarker.getOrDefault("areaPercent", ""));
										if(areaPercent > 0) {
											suspect.getGroupMarkers().add(new GroupMarker(nameGroup, areaPercent));
										}
									}
								}
							}
						}
					}
				}
			}
			suspectMatrices.add(suspectMatrix);
		}
	}

	private static double getRetentionTime(Object input) {

		return getValue(input);
	}

	private static float getRetentionIndex(Object input) {

		return (float)getValue(input);
	}

	private static double getValue(Object input) {

		String in = input.toString();
		if(!in.isBlank()) {
			try {
				return Double.parseDouble(in);
			} catch(NumberFormatException e) {
			}
		}
		//
		return 0;
	}
}