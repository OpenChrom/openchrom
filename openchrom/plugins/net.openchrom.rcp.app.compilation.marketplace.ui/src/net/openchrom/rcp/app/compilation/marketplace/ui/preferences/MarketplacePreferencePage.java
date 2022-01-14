/*******************************************************************************
 * Copyright (c) 2012, 2022 Lablicate GmbH.
 * 
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors:
 * Dr. Philip Wenig - initial API and implementation
 *******************************************************************************/
package net.openchrom.rcp.app.compilation.marketplace.ui.preferences;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;

import org.eclipse.chemclipse.logging.core.Logger;
import org.eclipse.chemclipse.support.ui.p2.UpdateSiteSupport;
import org.eclipse.chemclipse.support.ui.preferences.fieldeditors.LabelFieldEditor;
import org.eclipse.chemclipse.support.ui.preferences.fieldeditors.SpacerFieldEditor;
import org.eclipse.epp.mpc.ui.CatalogDescriptor;
import org.eclipse.epp.mpc.ui.MarketplaceClient;
import org.eclipse.jface.preference.FieldEditorPreferencePage;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.MessageBox;
import org.eclipse.ui.IWorkbench;
import org.eclipse.ui.IWorkbenchPreferencePage;

import net.openchrom.rcp.app.compilation.marketplace.ui.Activator;

public class MarketplacePreferencePage extends FieldEditorPreferencePage implements IWorkbenchPreferencePage {

	private static final Logger logger = Logger.getLogger(MarketplacePreferencePage.class);

	public MarketplacePreferencePage() {

		super(GRID);
		// The preference store is not needed.
		setDescription("Add the marketplace on demand, to fetch additional plug-ins.");
	}

	@Override
	public void init(IWorkbench workbench) {

	}

	@Override
	protected void createFieldEditors() {

		addField(new SpacerFieldEditor(getFieldEditorParent()));
		addField(new LabelFieldEditor("The marketplace offers additional plugins.", getFieldEditorParent()));
		/*
		 * Add additional update sites.
		 */
		Button addMarketplace = new Button(getFieldEditorParent(), SWT.NONE);
		addMarketplace.setText("Add OpenChrom Marketplace");
		addMarketplace.addSelectionListener(new SelectionAdapter() {

			@Override
			public void widgetSelected(SelectionEvent e) {

				/*
				 * Delete all settings, all installed plug-ins, everything.
				 */
				MessageBox messageBox = new MessageBox(Display.getCurrent().getActiveShell(), SWT.ICON_INFORMATION | SWT.YES | SWT.NO | SWT.CANCEL);
				messageBox.setText("Add OpenChrom Marketplace");
				messageBox.setMessage("Do you really want to add the marketplace?");
				if(messageBox.open() == SWT.YES) {
					try {
						/*
						 * Add the marketplace
						 */
						URL url = new URL("http://www.openchrom.net/");
						CatalogDescriptor catalogDescriptor = new CatalogDescriptor(url, "OpenChrom Marketplace");
						catalogDescriptor.setDescription("Install new plug-ins from the marketplace.");
						catalogDescriptor.setIcon(Activator.imageDescriptorFromPlugin(Activator.getDefault().getBundle().getSymbolicName(), "icons/logo_32x32.png"));
						catalogDescriptor.setInstallFromAllRepositories(false);
						MarketplaceClient.addCatalogDescriptor(catalogDescriptor);
						/*
						 * Add needed additional repositories.
						 */
						UpdateSiteSupport updateSiteSupport = new UpdateSiteSupport();
						Map<String, String> updateSites = new HashMap<String, String>();
						updateSites.put("OpenChrom 3rd Party Libraries", "http://update.openchrom.net/repositories/community/1.0.x/plugins/openchrom3rdpl"); // 3rd Party Libraries
						updateSites.put("OpenChrom Keys", "http://update.openchrom.net/repositories/community/1.0.x/plugins/openchromkeys"); // Keys Support
						updateSites.put("OpenChrom Icons", "http://update.openchrom.net/repositories/community/1.0.x/plugins/enterprisesupport"); // Enterprise Icons
						updateSites.put("OpenChrom xIdent", "http://update.openchrom.net/repositories/community/1.0.x/plugins/xident"); // xIdent Support
						updateSiteSupport.addProvisioningRepositories(updateSites);
					} catch(MalformedURLException e1) {
						logger.warn(e1);
					}
				}
			}
		});
	}
}
