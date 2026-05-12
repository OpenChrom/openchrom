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

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;

import net.openchrom.xxd.process.supplier.templates.model.NameReplacements;

public class NameReplacementsDeserializer extends JsonDeserializer<NameReplacements> {

	@Override
	public NameReplacements deserialize(JsonParser jsonParser, DeserializationContext deserializationContext) throws IOException {

		NameReplacements nameReplacements = new NameReplacements();
		nameReplacements.load(jsonParser.getText());
		return nameReplacements;
	}
}
