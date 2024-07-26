/*******************************************************************************
 * Copyright (c) 2021, 2024 Lablicate GmbH.
 *
 * All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0 which accompanies this distribution,
 * and is available at http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors:
 * Philip Wenig - initial API and implementation
 *******************************************************************************/
package net.openchrom.msd.converter.supplier.cdf.converter;

import java.io.File;

import org.eclipse.chemclipse.converter.core.IFileContentMatcher;
import org.eclipse.chemclipse.converter.core.IMagicNumberMatcher;
import org.eclipse.chemclipse.model.settings.IProcessSettings;
import org.eclipse.chemclipse.tsd.converter.core.IExportConverterTSD;
import org.eclipse.chemclipse.tsd.converter.core.IImportConverterTSD;
import org.eclipse.chemclipse.tsd.converter.service.IConverterServiceTSD;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.ConfigurationPolicy;

import net.openchrom.msd.converter.supplier.cdf.io.ChromatogramReaderTSD;

@Component(service = {IConverterServiceTSD.class}, configurationPolicy = ConfigurationPolicy.OPTIONAL)
public class ChromatogramImportConverterTSD implements IConverterServiceTSD {

	@Override
	public String getId() {

		return "net.openchrom.msd.converter.supplier.cdf";
	}

	@Override
	public String getDescription() {

		return "GCxGC-MS (CDF)";
	}

	@Override
	public String getFilterName() {

		return "CDF Import Converter (*.cdf)";
	}

	@Override
	public String getFileExtension() {

		return ".cdfy";
	}

	@Override
	public String getFileName() {

		return "";
	}

	@Override
	public String getDirectoryExtension() {

		return "";
	}

	@Override
	public IImportConverterTSD getImportConverter() {

		return new ChromatogramReaderTSD();
	}

	@Override
	public IExportConverterTSD getExportConverter() {

		return null;
	}

	@Override
	public IMagicNumberMatcher getMagicNumberMatcher() {

		return new MagicNumberMatcher();
	}

	@Override
	public IFileContentMatcher getFileContentMatcher() {

		return new IFileContentMatcher() {

			@Override
			public boolean checkFileFormat(File file) {

				return true;
			}
		};
	}

	@Override
	public IProcessSettings getProcessSettings() {

		return null;
	}
}