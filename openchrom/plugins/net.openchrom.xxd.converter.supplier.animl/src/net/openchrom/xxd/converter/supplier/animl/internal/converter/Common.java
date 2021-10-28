/*******************************************************************************
 * Copyright (c) 2021 Lablicate GmbH.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors:
 * Matthias Mailänder - initial API and implementation
 *******************************************************************************/
package net.openchrom.xxd.converter.supplier.animl.internal.converter;

import org.eclipse.core.runtime.IProduct;
import org.eclipse.core.runtime.Platform;
import org.osgi.framework.Version;

import net.openchrom.xxd.converter.supplier.animl.internal.model.astm.core.SoftwareType;

public class Common {

	public static SoftwareType createSoftware() {

		SoftwareType software = new SoftwareType();
		software.setName("OpenChrom");
		IProduct product = Platform.getProduct();
		Version version = product.getDefiningBundle().getVersion();
		software.setVersion(version.toString());
		software.setManufacturer("Lablicate GmbH");
		software.setOperatingSystem(System.getProperty("os.name"));
		return software;
	}
}
