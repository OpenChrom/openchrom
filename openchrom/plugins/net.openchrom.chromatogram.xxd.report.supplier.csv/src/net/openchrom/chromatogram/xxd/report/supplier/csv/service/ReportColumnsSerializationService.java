/*******************************************************************************
 * Copyright (c) 2021, 2022 Lablicate GmbH.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors:
 * Philip Wenig - initial API and implementation
 *******************************************************************************/
package net.openchrom.chromatogram.xxd.report.supplier.csv.service;

import org.eclipse.chemclipse.support.settings.serialization.ISerializationService;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.ConfigurationPolicy;

import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.JsonSerializer;

import net.openchrom.chromatogram.xxd.report.supplier.csv.model.ReportColumns;
import net.openchrom.chromatogram.xxd.report.supplier.csv.serializer.ReportColumnsDeserializer;
import net.openchrom.chromatogram.xxd.report.supplier.csv.serializer.ReportColumnsSerializer;

@Component(service = {ISerializationService.class}, configurationPolicy = ConfigurationPolicy.OPTIONAL)
public class ReportColumnsSerializationService implements ISerializationService {

	@Override
	public Class<?> getSupportedClass() {

		return ReportColumns.class;
	}

	@Override
	public JsonSerializer<ReportColumns> getSerializer() {

		return new ReportColumnsSerializer();
	}

	@Override
	public JsonDeserializer<ReportColumns> getDeserializer() {

		return new ReportColumnsDeserializer();
	}
}
