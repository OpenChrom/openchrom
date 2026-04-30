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

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.HashSet;
import java.util.Set;

import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.NullProgressMonitor;
import org.eclipse.e4.core.contexts.Active;
import org.eclipse.e4.core.di.annotations.CanExecute;
import org.eclipse.e4.core.di.annotations.Execute;
import org.eclipse.equinox.p2.core.IProvisioningAgent;
import org.eclipse.equinox.p2.engine.ProvisioningContext;
import org.eclipse.equinox.p2.operations.ProvisioningSession;
import org.eclipse.equinox.p2.operations.UpdateOperation;
import org.eclipse.equinox.p2.repository.metadata.IMetadataRepositoryManager;
import org.eclipse.equinox.p2.ui.ProvisioningUI;
import org.eclipse.jface.dialogs.MessageDialog;
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

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import net.openchrom.installer.ui.model.CurrentVersion;

public class CheckForUpdatesHander {

	private static final String URL_STR = "https://marketplace.lablicate.com/api/download/1/current_element_version";

	@Execute
	public void execute(@Active Shell shell) {

		Version bundleVersion = FrameworkUtil.getBundle(CheckForUpdatesHander.class).getVersion();
		boolean updateAvailable = false;
		Version availableVersion = null;

		CurrentVersion latestVersion = getLatestVersion();
		if(latestVersion != null) {
			availableVersion = new Version(latestVersion.getVersion());
			if(availableVersion.compareTo(bundleVersion) > 0) {
				updateAvailable = true;
			}
		}

		if(updateAvailable) {
			Version toVersion = availableVersion;
			MessageDialog dialog = new MessageDialog(shell, "Check for Updates", null, "New version available -" + toVersion.toString(), MessageDialog.INFORMATION, new String[]{"OK"}, 0) {

				@Override
				protected Control createCustomArea(Composite parent) {

					boolean performUpdate = Boolean.getBoolean("openchrom.update");
					UpdateOperation op = getUpdateOperation(toVersion);
					if(performUpdate && canPerformUpdate(op)) {
						Link link = new Link(parent, SWT.NONE);
						link.setText("Experimental: <a href=\"https://openchrom.net/download\">Perform Update</a>");
						link.addSelectionListener(SelectionListener.widgetSelectedAdapter(e -> {
							this.close();
							ProvisioningUI.getDefaultUI().openUpdateWizard(false, op, null);
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

	private CurrentVersion getLatestVersion() {

		try (HttpClient client = HttpClient.newHttpClient()) {
			HttpRequest request = HttpRequest.newBuilder().uri(URI.create(URL_STR)).GET().build();
			HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
			Gson gson = new Gson();
			JsonObject data = JsonParser.parseString(response.body()).getAsJsonObject().getAsJsonObject("data");
			return gson.fromJson(data, CurrentVersion.class);
		} catch(IOException | InterruptedException e) {
			// can't determine latest version
		}
		return null;
	}

	private boolean canPerformUpdate(UpdateOperation op) {

		IStatus status = op.resolveModal(new NullProgressMonitor());
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

		URI[] known = metaManager.getKnownRepositories(IMetadataRepositoryManager.REPOSITORIES_ALL);
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