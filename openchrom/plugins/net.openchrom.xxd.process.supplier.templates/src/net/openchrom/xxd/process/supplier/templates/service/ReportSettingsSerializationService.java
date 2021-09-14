/*******************************************************************************
 * Copyright (c) 2021 Lablicate GmbH.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
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

import net.openchrom.xxd.process.supplier.templates.model.ReportSettings;
import net.openchrom.xxd.process.supplier.templates.serializer.ReportSettingsDeserializer;
import net.openchrom.xxd.process.supplier.templates.serializer.ReportSettingsSerializer;

@Component(service = {ISerializationService.class}, configurationPolicy = ConfigurationPolicy.OPTIONAL)
public class ReportSettingsSerializationService implements ISerializationService {

	@Override
	public Class<?> getSupportedClass() {

		return ReportSettings.class;
	}

	@Override
	public JsonSerializer<ReportSettings> getSerializer() {

		return new ReportSettingsSerializer();
	}

	@Override
	public JsonDeserializer<ReportSettings> getDeserializer() {

		return new ReportSettingsDeserializer();
	}
}