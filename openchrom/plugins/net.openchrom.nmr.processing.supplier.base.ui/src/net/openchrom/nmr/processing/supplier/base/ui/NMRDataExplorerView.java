/*******************************************************************************
 * Copyright (c) 2019, 2020 Lablicate GmbH.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors:
 * Christoph Läubrich - initial API and implementation
 * Philip Wenig - refactoring
 *******************************************************************************/
package net.openchrom.nmr.processing.supplier.base.ui;

import java.util.Collections;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.inject.Named;

import org.eclipse.chemclipse.model.types.DataType;
import org.eclipse.chemclipse.ux.extension.ui.preferences.PreferencePage;
import org.eclipse.chemclipse.ux.extension.ui.provider.ISupplierFileEditorSupport;
import org.eclipse.chemclipse.ux.extension.ui.swt.DataExplorerUI;
import org.eclipse.chemclipse.ux.extension.xxd.ui.editors.EditorSupportFactory;
import org.eclipse.e4.core.di.annotations.Execute;
import org.eclipse.e4.ui.model.application.ui.basic.MPart;
import org.eclipse.e4.ui.services.IServiceConstants;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.jface.preference.PreferenceDialog;
import org.eclipse.jface.preference.PreferenceManager;
import org.eclipse.jface.preference.PreferenceNode;
import org.eclipse.jface.window.Window;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Shell;

public class NMRDataExplorerView {

	private static final List<ISupplierFileEditorSupport> NMR_SUPPLIER = Collections.singletonList(new EditorSupportFactory(DataType.NMR, () -> Activator.getDefault().getEclipseContext()).getInstanceEditorSupport());
	private DataExplorerUI dataExplorerUI;

	@PostConstruct
	public void postConstruct(Composite parent) {

		dataExplorerUI = new DataExplorerUI(parent, Activator.getDefault().getPreferenceStore());
		dataExplorerUI.setSupplierFileIdentifier(NMR_SUPPLIER);
		dataExplorerUI.expandLastDirectoryPath();
	}

	public static final class NMRDataExplorerSettingsHandler {

		@Execute
		public void execute(MPart part, @Named(IServiceConstants.ACTIVE_SHELL) Shell shell) {

			NMRDataExplorerView explorer = (NMRDataExplorerView)part.getObject();
			PreferenceManager preferenceManager = new PreferenceManager();
			preferenceManager.addToRoot(new PreferenceNode("2", new PreferencePage()));
			//
			PreferenceDialog preferenceDialog = new PreferenceDialog(shell, preferenceManager);
			preferenceDialog.create();
			preferenceDialog.setMessage("Settings");
			if(preferenceDialog.open() == Window.OK) {
				try {
					explorer.dataExplorerUI.setSupplierFileIdentifier(NMR_SUPPLIER);
				} catch(Exception e1) {
					MessageDialog.openError(shell, "Settings", "Something has gone wrong to apply the chart settings.");
				}
			}
		}
	}

	public static final class ResetNMRDataExplorerHandler {

		@Execute
		public void execute(MPart part) {

			NMRDataExplorerView explorer = (NMRDataExplorerView)part.getObject();
			explorer.dataExplorerUI.expandLastDirectoryPath();
		}
	}
}