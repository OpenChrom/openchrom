/*******************************************************************************
 * Copyright (c) 2009, 2024 Tasktop Technologies, Polarion Software and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors:
 * Tasktop Technologies - initial API and implementation
 *******************************************************************************/
package net.openchrom.installer.ui.discovery;

import java.util.List;

import org.eclipse.jface.operation.IRunnableWithProgress;

import net.openchrom.installer.model.PluginDescriptor;

/**
 * Interface for install jobs
 * 
 * @author Igor Burilo
 */
public interface IPluginInstallJob extends IRunnableWithProgress {

	void setInstallableConnectors(List<PluginDescriptor> installableConnectors);
}