/*******************************************************************************
 * Copyright (c) 2026 Lablicate GmbH.
 *
 * This program and the accompanying materials are made
 * available under the terms of the Eclipse Public License 2.0
 * which is available at https://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
 * 
 * Contributors:
 * Aleksandar Kurtakov - initial API and implementation
 *******************************************************************************/
package net.openchrom.installer.ui.handlers;

import java.lang.reflect.InvocationTargetException;
import java.net.URI;
import java.util.HashSet;
import java.util.Set;

import org.eclipse.core.runtime.IStatus;
import org.eclipse.e4.core.contexts.Active;
import org.eclipse.e4.core.di.annotations.CanExecute;
import org.eclipse.e4.core.di.annotations.Execute;
import org.eclipse.equinox.p2.core.IProvisioningAgent;
import org.eclipse.equinox.p2.engine.ProvisioningContext;
import org.eclipse.equinox.p2.operations.ProvisioningSession;
import org.eclipse.equinox.p2.operations.UpdateOperation;
import org.eclipse.equinox.p2.repository.IRepositoryManager;
import org.eclipse.equinox.p2.repository.metadata.IMetadataRepositoryManager;
import org.eclipse.equinox.p2.ui.ProvisioningUI;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.jface.dialogs.ProgressMonitorDialog;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.program.Program;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Link;
import org.eclipse.swt.widgets.MessageBox;
import org.eclipse.swt.widgets.Shell;
import org.osgi.framework.BundleContext;
import org.osgi.framework.FrameworkUtil;
import org.osgi.framework.ServiceReference;
import org.osgi.framework.Version;

import net.openchrom.installer.model.CurrentVersion;

public class CheckForUpdatesHander {

	@Execute
	public void execute(@Active Shell shell) {

		Version bundleVersion = FrameworkUtil.getBundle(CheckForUpdatesHander.class).getVersion();
		boolean updateAvailable = false;
		Version availableVersion = null;

		CurrentVersion latestVersion = CurrentVersion.getLatestVersion();
		if(latestVersion != null) {
			availableVersion = new Version(latestVersion.getVersion());
			if(availableVersion.compareTo(bundleVersion) > 0) {
				updateAvailable = true;
			}
		}

		if(updateAvailable) {
			Version newVersion = availableVersion;
			MessageDialog dialog = new MessageDialog(shell, "Check for Updates", null, "New version available -" + newVersion.toString(), MessageDialog.INFORMATION, new String[]{"OK"}, 0) {

				@Override
				protected Control createCustomArea(Composite parent) {

					boolean performUpdate = Boolean.getBoolean("openchrom.update");
					UpdateOperation updateOperation = getUpdateOperation(newVersion);
					if(performUpdate && canPerformUpdate(updateOperation, parent.getShell())) {
						Link link = new Link(parent, SWT.NONE);
						link.setText("Experimental (may require license updates): <a href=\"https://openchrom.net/download\">Perform Update</a>");
						link.addSelectionListener(SelectionListener.widgetSelectedAdapter(e -> {
							this.close();
							ProvisioningUI.getDefaultUI().openUpdateWizard(false, updateOperation, null);
						}));
						return link;
					} else {
						Link link = new Link(parent, SWT.NONE);
						link.setText("Download from <a href=\"https://openchrom.net/download\">https://openchrom.net/download</a>");
						link.addSelectionListener(SelectionListener.widgetSelectedAdapter(e -> Program.launch(e.text)));
						return link;
					}
				}
			};
			dialog.open();
		} else {
			MessageBox box = new MessageBox(shell);
			box.setText("Check for Updates");
			box.setMessage("No new version available!");
			box.open();
		}

	}

	private boolean canPerformUpdate(UpdateOperation op, Shell shell) {

		try {
			new ProgressMonitorDialog(shell).run(true, true, monitor -> op.resolveModal(monitor));
		} catch(InvocationTargetException e) {
			return false;
		} catch(InterruptedException e) {
			Thread.currentThread().interrupt();
			return false;
		}
		IStatus status = op.getResolutionResult();
		if(status.isOK() && op.getPossibleUpdates().length > 0) {
			return true;
		}
		return false;
	}

	private UpdateOperation getUpdateOperation(Version toVersion) {

		IProvisioningAgent agent = getProvisioningAgent();

		URI[] newUris = updateP2Repos(agent, toVersion);

		ProvisioningSession session = new ProvisioningSession(agent);

		// Update everything in the running profile
		UpdateOperation op = new UpdateOperation(session);

		ProvisioningContext ctx = op.getProvisioningContext();

		// Force p2 to use ONLY the rewritten repositories
		ctx.setMetadataRepositories(newUris);
		ctx.setArtifactRepositories(newUris);
		return op;
	}

	private URI[] updateP2Repos(IProvisioningAgent agent, Version toVersion) {

		IMetadataRepositoryManager metaManager = agent.getService(IMetadataRepositoryManager.class);

		URI[] known = metaManager.getKnownRepositories(IRepositoryManager.REPOSITORIES_ALL);
		Set<URI> newUris = new HashSet<>();
		for(URI uri : known) {
			URI newUri = replaceVersion(uri, toVersion.toString());
			newUris.add(newUri);
		}
		return newUris.toArray(URI[]::new);
	}

	private static URI replaceVersion(URI uri, String newVersion) {

		String path = uri.getPath();
		if(path.endsWith("/")) {
			path = path.substring(0, path.length() - 1);
		}

		int i = path.lastIndexOf('/');
		String newPath = path.substring(0, i + 1) + newVersion;

		return uri.resolve(newPath);
	}

	private IProvisioningAgent getProvisioningAgent() {

		// Get from service registry
		BundleContext context = FrameworkUtil.getBundle(this.getClass()).getBundleContext();
		ServiceReference<IProvisioningAgent> sr = context.getServiceReference(IProvisioningAgent.class);
		return context.getService(sr);
	}

	@CanExecute
	public boolean canExecute() {

		return true;
	}

}