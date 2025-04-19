/*******************************************************************************
 * Copyright (c) 2009, 2025 Tasktop Technologies, Polarion Software and others.
 *
 * This program and the accompanying materials are made
 * available under the terms of the Eclipse Public License 2.0
 * which is available at https://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
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