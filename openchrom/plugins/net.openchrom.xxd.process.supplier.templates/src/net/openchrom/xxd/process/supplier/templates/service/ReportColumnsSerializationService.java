/*******************************************************************************
 * Copyright (c) 2021, 2025 Lablicate GmbH.
 *
 * This program and the accompanying materials are made
 * available under the terms of the Eclipse Public License 2.0
 * which is available at https://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
 * 
 * Contributors:
 * Philip Wenig - initial API and implementation
 *******************************************************************************/
package net.openchrom.xxd.process.supplier.templates.service;

import org.eclipse.chemclipse.support.settings.serialization.ISerializationService;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.ConfigurationPolicy;

import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.JsonSerializer;

import net.openchrom.xxd.process.supplier.templates.model.ReportColumns;
import net.openchrom.xxd.process.supplier.templates.serializer.ReportColumnsDeserializer;
import net.openchrom.xxd.process.supplier.templates.serializer.ReportColumnsSerializer;

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