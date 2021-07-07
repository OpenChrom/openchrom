/*******************************************************************************
 * Copyright (c) 2021 Lablicate GmbH.
 *
 * All rights reserved. This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU Affero General Public License, version 3,
 * as published by the Free Software Foundation.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 * GNU Affero General Public License for more details.
 *
 * You should have received a copy of the GNU Affero General Public License
 * along with this program. If not, see <http://www.gnu.org/licenses/>.
 * 
 * Contributors:
 * Philip Wenig - initial API and implementation
 *******************************************************************************/
package net.openchrom.csd.converter.supplier.cdf.converter;

import org.eclipse.chemclipse.converter.core.IMagicNumberMatcher;
import org.eclipse.chemclipse.model.settings.IProcessSettings;
import org.eclipse.chemclipse.tsd.converter.core.IExportConverterTSD;
import org.eclipse.chemclipse.tsd.converter.core.IImportConverterTSD;
import org.eclipse.chemclipse.tsd.converter.service.IConverterServiceTSD;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.ConfigurationPolicy;

import net.openchrom.csd.converter.supplier.cdf.io.ChromatogramReaderTSD;

@Component(service = {IConverterServiceTSD.class}, configurationPolicy = ConfigurationPolicy.OPTIONAL)
public class ChromatogramImportConverterTSD implements IConverterServiceTSD {

	@Override
	public String getId() {

		return "net.openchrom.csd.converter.supplier.cdf";
	}

	@Override
	public String getDescription() {

		return "CDF GCxGC";
	}

	@Override
	public String getFilterName() {

		return "CDF Import Converter (*.cdf)";
	}

	@Override
	public String getFileExtension() {

		return ".cdfx";
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
	public IProcessSettings getProcessSettings() {

		return null;
	}
}