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
package net.openchrom.chromatogram.xxd.report.supplier.csv.serializer;

import java.io.IOException;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;

import net.openchrom.chromatogram.xxd.report.supplier.csv.model.ReportColumns;

public class ReportColumnsSerializer extends JsonSerializer<ReportColumns> {

	@Override
	public void serialize(ReportColumns reportColumns, JsonGenerator jsonGenerator, SerializerProvider serializerProvider) throws IOException {

		if(reportColumns != null) {
			jsonGenerator.writeString(reportColumns.save());
		} else {
			jsonGenerator.writeString("");
		}
	}
}
