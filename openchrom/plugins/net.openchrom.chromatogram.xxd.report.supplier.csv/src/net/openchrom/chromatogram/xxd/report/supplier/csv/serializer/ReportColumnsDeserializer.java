/*******************************************************************************
 * Copyright (c) 2021, 2026 Lablicate GmbH.
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
package net.openchrom.chromatogram.xxd.report.supplier.csv.serializer;

import java.io.IOException;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;

import net.openchrom.chromatogram.xxd.report.supplier.csv.model.ReportColumns;

public class ReportColumnsDeserializer extends JsonDeserializer<ReportColumns> {

	@Override
	public ReportColumns deserialize(JsonParser jsonParser, DeserializationContext deserializationContext) throws IOException {

		ReportColumns reportColumns = new ReportColumns();
		reportColumns.load(jsonParser.getText());
		return reportColumns;
	}
}
