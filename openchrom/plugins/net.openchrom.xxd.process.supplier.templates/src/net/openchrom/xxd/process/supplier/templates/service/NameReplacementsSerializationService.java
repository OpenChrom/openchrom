/*******************************************************************************
 * Copyright (c) 2026 Lablicate GmbH.
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

import net.openchrom.xxd.process.supplier.templates.model.NameReplacements;
import net.openchrom.xxd.process.supplier.templates.serializer.NameReplacementsDeserializer;
import net.openchrom.xxd.process.supplier.templates.serializer.NameReplacementsSerializer;

@Component(service = {ISerializationService.class}, configurationPolicy = ConfigurationPolicy.OPTIONAL)
public class NameReplacementsSerializationService implements ISerializationService {

	@Override
	public Class<?> getSupportedClass() {

		return NameReplacements.class;
	}

	@Override
	public JsonSerializer<NameReplacements> getSerializer() {

		return new NameReplacementsSerializer();
	}

	@Override
	public JsonDeserializer<NameReplacements> getDeserializer() {

		return new NameReplacementsDeserializer();
	}
}