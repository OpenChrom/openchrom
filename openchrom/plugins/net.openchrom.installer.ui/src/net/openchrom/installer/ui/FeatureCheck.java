/*******************************************************************************
 * Copyright (c) 2023, 2024 Lablicate GmbH.
 * 
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors:
 * Matthias Mailänder - initial API and implementation
 * Philip Wenig - refactoring vibrational spectroscopy
 *******************************************************************************/
package net.openchrom.installer.ui;

import java.util.concurrent.ExecutionException;

import org.eclipse.chemclipse.logging.core.Logger;
import org.eclipse.chemclipse.support.ui.workbench.DisplayUtils;
import org.eclipse.core.runtime.Platform;
import org.eclipse.jface.dialogs.IDialogConstants;
import org.eclipse.jface.dialogs.MessageDialogWithToggle;
import org.eclipse.jface.wizard.WizardDialog;
import org.eclipse.ui.IStartup;
import org.osgi.framework.Bundle;

import net.openchrom.installer.preferences.PreferenceSupplier;
import net.openchrom.installer.ui.discovery.IPluginInstallJob;
import net.openchrom.installer.ui.discovery.PrepareInstallProfileJob;
import net.openchrom.installer.ui.wizards.PluginDiscoveryWizard;

public class FeatureCheck implements IStartup {

	private static final Logger logger = Logger.getLogger(FeatureCheck.class);
	private static final String[] features = new String[]{ // without .feature suffix
			"net.openchrom.msd.converter.supplier.finnigan.itds", //
			"net.openchrom.msd.converter.supplier.finnigan.its40", //
			"net.openchrom.msd.converter.supplier.shimadzu.qgd", //
			"net.openchrom.msd.converter.supplier.shimadzu.spc", //
			"net.openchrom.msd.converter.supplier.varian.sms", //
			"net.openchrom.msd.converter.supplier.varian.xms", //
			"net.openchrom.msd.converter.supplier.finnigan.raw", //
			"net.openchrom.csd.converter.supplier.agilent", //
			"net.openchrom.csd.converter.supplier.openlab", //
			"net.openchrom.msd.converter.supplier.vg", //
			"net.openchrom.msd.converter.supplier.bruker.flex", //
			"net.openchrom.msd.converter.supplier.finnigan.mat", //
			"net.openchrom.csd.converter.supplier.masshunter", //
			"net.openchrom.msd.converter.supplier.masshunter", //
			"net.openchrom.wsd.converter.supplier.masshunter", //
			"net.openchrom.msd.converter.supplier.waters", //
			"net.openchrom.msd.converter.supplier.chromtech", //
			"net.openchrom.msd.converter.supplier.agilent.hp", //
			"net.openchrom.msd.converter.supplier.agilent.icp", //
			"net.openchrom.wsd.converter.supplier.agilent", //
			"net.openchrom.msd.converter.supplier.absciex", //
			"net.openchrom.wsd.converter.supplier.absciex", //
			"net.openchrom.csd.converter.supplier.shimadzu.gcd", //
			"net.openchrom.msd.converter.supplier.finnigan.icis", //
			"net.openchrom.csd.converter.supplier.finnigan.dat", //
			"net.openchrom.csd.converter.supplier.varian", //
			"net.openchrom.csd.converter.supplier.perkinelmer", //
			"net.openchrom.csd.converter.supplier.shimadzu.gc10", //
			"net.openchrom.msd.converter.supplier.finnigan.cgm", //
			"net.openchrom.csd.converter.supplier.finnigan.raw", //
			"net.openchrom.msd.converter.supplier.finnigan.element", //
			"net.openchrom.xxd.converter.supplier.massfinder", //
			"net.openchrom.msd.converter.supplier.shimadzu.qp5000", //
			"net.openchrom.msd.converter.supplier.shimadzu.lcd", //
			"net.openchrom.xxd.converter.supplier.dataapex", //
			"net.openchrom.msd.converter.supplier.leco", //
			"net.openchrom.csd.converter.supplier.ezchrom", //
			"net.openchrom.wsd.converter.supplier.camag", //
			"net.openchrom.csd.converter.supplier.moduvision", //
			"net.openchrom.csd.converter.supplier.labjack", //
			"net.openchrom.vsd.converter.supplier.nicolet", //
			"net.openchrom.nmr.converter.supplier.bruker", //
			"net.openchrom.pcr.converter.supplier.ixo", //
			"net.openchrom.pcr.converter.supplier.egu", //
			"net.openchrom.msd.converter.supplier.masslib", //
			"net.openchrom.msd.converter.supplier.metalign", //
			"net.openchrom.csd.converter.supplier.waters.empower", //
			"net.openchrom.msd.converter.supplier.waters.empower", //
			"net.openchrom.wsd.converter.supplier.waters.empower", //
			"net.openchrom.wsd.converter.supplier.chromulan", //
			"net.openchrom.tsd.converter.supplier.gas", //
			"net.openchrom.wsd.converter.supplier.hsa", //
			"net.openchrom.csd.converter.supplier.thermo.atlas", //
			"net.openchrom.csd.converter.supplier.thermo.raw", //
			"net.openchrom.csd.converter.supplier.peaksimple", //
			"net.openchrom.csd.converter.supplier.igraphx", //
			"net.openchrom.msd.converter.supplier.markes.lsc", //
			"net.openchrom.xxd.converter.supplier.tetrascience", //
			"net.openchrom.vsd.converter.supplier.andor", //
	};

	@Override
	public void earlyStartup() {

		for(String feature : features) {
			Bundle bundle = Platform.getBundle(feature);
			if(bundle != null) {
				return;
			}
		}
		if(PreferenceSupplier.getProprietaryConverters().equals(MessageDialogWithToggle.NEVER)) {
			return;
		}
		try {
			DisplayUtils.executeInUserInterfaceThread(new Runnable() {

				@Override
				public void run() {

					MessageDialogWithToggle dialog = MessageDialogWithToggle.openYesNoQuestion(DisplayUtils.getShell(), "Vendor plugins missing", //
							"You currently have no proprietary converters installed. These are required to open instrument vendor files. Do you want to install converter plug-ins now?", //
							"Don't ask again.", false, net.openchrom.installer.ui.Activator.getDefault().getPreferenceStore(), PreferenceSupplier.P_PROPRIETARY_CONVERTERS);
					if(dialog.getReturnCode() == IDialogConstants.YES_ID) {
						try {
							IPluginInstallJob installJob = new PrepareInstallProfileJob();
							PluginDiscoveryWizard wizard = new PluginDiscoveryWizard(installJob);
							WizardDialog wizardDialog = new WizardDialog(DisplayUtils.getShell(), wizard);
							wizardDialog.open();
						} catch(IllegalArgumentException e) {
							logger.warn(e);
						}
					}
				}
			});
		} catch(InterruptedException e) {
			Thread.currentThread().interrupt();
			logger.warn(e);
		} catch(ExecutionException e) {
			logger.warn(e);
		}
	}
}