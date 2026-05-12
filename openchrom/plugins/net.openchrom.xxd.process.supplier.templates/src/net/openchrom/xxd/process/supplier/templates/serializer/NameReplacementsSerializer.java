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
package net.openchrom.xxd.process.supplier.templates.serializer;

import java.io.IOException;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;

import net.openchrom.xxd.process.supplier.templates.model.NameReplacements;

public class NameReplacementsSerializer extends JsonSerializer<NameReplacements> {

	@Override
	public void serialize(NameReplacements nameReplacements, JsonGenerator jsonGenerator, SerializerProvider serializerProvider) throws IOException {

		if(nameReplacements != null) {
			jsonGenerator.writeString(nameReplacements.save());
		} else {
			jsonGenerator.writeString("");
		}
	}
}