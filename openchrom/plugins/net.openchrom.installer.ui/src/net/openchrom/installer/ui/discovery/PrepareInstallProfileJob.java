/*******************************************************************************
 * Copyright (c) 2009, 2026 Tasktop Technologies, Polarion Software and others.
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

import java.lang.reflect.InvocationTargetException;
import java.net.URI;
import java.net.URISyntaxException;
import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.eclipse.chemclipse.logging.core.Logger;
import org.eclipse.chemclipse.support.ui.workbench.DisplayUtils;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.OperationCanceledException;
import org.eclipse.core.runtime.SubMonitor;
import org.eclipse.equinox.p2.core.ProvisionException;
import org.eclipse.equinox.p2.engine.IProfile;
import org.eclipse.equinox.p2.engine.IProfileRegistry;
import org.eclipse.equinox.p2.metadata.IInstallableUnit;
import org.eclipse.equinox.p2.metadata.Version;
import org.eclipse.equinox.p2.operations.InstallOperation;
import org.eclipse.equinox.p2.operations.ProvisioningSession;
import org.eclipse.equinox.p2.query.IQuery;
import org.eclipse.equinox.p2.query.IQueryResult;
import org.eclipse.equinox.p2.query.QueryUtil;
import org.eclipse.equinox.p2.repository.metadata.IMetadataRepository;
import org.eclipse.equinox.p2.repository.metadata.IMetadataRepositoryManager;
import org.eclipse.equinox.p2.ui.ProvisioningUI;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.swt.widgets.Display;

import net.openchrom.installer.model.IPluginDescriptor;
import net.openchrom.installer.model.PluginDescriptor;
import net.openchrom.installer.ui.swt.InstallErrorDialog;

/**
 * A job that configures a p2 {@link #getInstallAction() install action} for installing one or more
 * {@link PluginDescriptor plugins}. The bulk of the installation work is done by p2; this class just sets up the
 * p2 repository meta-data and selects the appropriate features to install. After running the job the
 * {@link #getInstallAction() install action} must be run to perform the installation.
 * 
 * @author David Green
 * @author Steffen Pingel
 * @author Igor Burilo
 */
public class PrepareInstallProfileJob implements IPluginInstallJob {

	private static final Logger logger = Logger.getLogger(PrepareInstallProfileJob.class);
	private static final String P2_FEATURE_GROUP_SUFFIX = ".feature.group"; //$NON-NLS-1$
	private Set<IPluginDescriptor> installableConnectors;
	private final ProvisioningUI provisioningUI;
	private URI[] repositories;

	public PrepareInstallProfileJob() {

		this.provisioningUI = ProvisioningUI.getDefaultUI();
		IMetadataRepositoryManager manager = provisioningUI.getSession().getProvisioningAgent().getService(IMetadataRepositoryManager.class);
		repositories = manager.getKnownRepositories(IMetadataRepositoryManager.REPOSITORIES_ALL);
	}

	@Override
	public void setInstallableConnectors(Set<IPluginDescriptor> installableConnectors) {

		if(installableConnectors == null || installableConnectors.isEmpty()) {
			throw new IllegalArgumentException();
		}
		this.installableConnectors = new HashSet<>(installableConnectors);
	}

	@Override
	public void run(IProgressMonitor progressMonitor) throws InvocationTargetException, InterruptedException {

		try {
			SubMonitor monitor = SubMonitor.convert(progressMonitor, "configuring", 100);
			try {
				Set<IInstallableUnit> installableUnits = computeInstallableUnits(monitor.newChild(50));
				checkCancelled(monitor);
				if(!installableUnits.isEmpty()) {
					final InstallOperation installOperation = resolve(monitor.newChild(50), installableUnits);
					checkCancelled(monitor);
					Display.getDefault().asyncExec(() -> provisioningUI.openInstallWizard(installableUnits, installOperation, null));
				}
			} catch(URISyntaxException | CoreException e) {
				Display.getDefault().asyncExec(() -> InstallErrorDialog.notifyError(DisplayUtils.getShell(), "Failed to install plugins.", e));
			} finally {
				monitor.done();
			}
		} catch(OperationCanceledException e) {
			throw new InterruptedException();
		} catch(Exception e) {
			throw new InvocationTargetException(e);
		}
	}

	private void checkCancelled(IProgressMonitor monitor) {

		if(monitor.isCanceled()) {
			throw new OperationCanceledException();
		}
	}

	private InstallOperation resolve(IProgressMonitor monitor, Set<IInstallableUnit> ius) throws CoreException {

		final InstallOperation installOperation = provisioningUI.getInstallOperation(ius, repositories);
		IStatus operationStatus = installOperation.resolveModal(SubMonitor.convert(monitor, installableConnectors.size()));
		if(operationStatus.getSeverity() > IStatus.WARNING) {
			throw new CoreException(operationStatus);
		}
		return installOperation;
	}

	private Set<IInstallableUnit> computeInstallableUnits(SubMonitor monitor) throws ProvisionException, URISyntaxException {

		monitor.setWorkRemaining(100);
		// add repository urls and load meta data
		List<IMetadataRepository> metadataRepositories = getRepositories(monitor.newChild(50));
		final Set<IInstallableUnit> installableUnits = queryInstallableUnits(monitor.newChild(50), metadataRepositories);
		removeOldVersions(installableUnits);
		checkForUnavailable(installableUnits);
		removeInstalled(installableUnits);
		return installableUnits;
	}

	/**
	 * Verifies that we found what we were looking for: it's possible that we have plugin descriptors that are no
	 * longer available on their respective sites. In that case we must inform the user. Unfortunately this is the
	 * earliest point at which we can know.
	 */
	private void checkForUnavailable(final Set<IInstallableUnit> installableUnits) {

		// at least one selected plugin could not be found in a repository
		Set<String> foundIds = new HashSet<>();
		for(IInstallableUnit unit : installableUnits) {
			foundIds.add(unit.getId());
		}
		String message = ""; //$NON-NLS-1$
		String detailedMessage = ""; //$NON-NLS-1$
		for(IPluginDescriptor descriptor : installableConnectors) {
			StringBuilder unavailableIds = null;
			String id = descriptor.getInstallableUnit() + P2_FEATURE_GROUP_SUFFIX;
			if(!foundIds.contains(id)) {
				if(unavailableIds == null) {
					unavailableIds = new StringBuilder();
				}
				unavailableIds.append(id);
			}
			if(unavailableIds != null) {
				if(!message.isEmpty()) {
					message += ", ";
				}
				message += descriptor.getName();
				if(!detailedMessage.isEmpty()) {
					detailedMessage += ", ";
				}
				detailedMessage += MessageFormat.format("{0} (id={1})", descriptor.getName(), unavailableIds);
			}
		}
		if(!message.isEmpty()) {
			// instead of aborting here we ask the user if they wish to proceed anyways
			final boolean[] okayToProceed = new boolean[1];
			final String finalMessage = message;
			DisplayUtils.getDisplay().syncExec(() -> okayToProceed[0] = MessageDialog.openQuestion(DisplayUtils.getShell(), "questionProceed", MessageFormat.format("The following connectors are not available: {0}\nProceed with the installation anyways?", finalMessage)));
			if(!okayToProceed[0]) {
				logger.error("Connectors not available: " + detailedMessage);
			}
		}
	}

	/**
	 * Filters those installable units that have a duplicate in the list with a higher version number. it's possible
	 * that some repositories will host multiple versions of a particular feature. we assume that the user wants the
	 * highest version.
	 */
	private void removeOldVersions(final Set<IInstallableUnit> installableUnits) {

		Map<String, Version> symbolicNameToVersion = new HashMap<>();
		for(IInstallableUnit unit : installableUnits) {
			Version version = symbolicNameToVersion.get(unit.getId());
			if(version == null || version.compareTo(unit.getVersion()) == -1) {
				symbolicNameToVersion.put(unit.getId(), unit.getVersion());
			}
		}
		if(symbolicNameToVersion.size() != installableUnits.size()) {
			for(IInstallableUnit unit : new ArrayList<>(installableUnits)) {
				Version version = symbolicNameToVersion.get(unit.getId());
				if(!version.equals(unit.getVersion())) {
					installableUnits.remove(unit);
				}
			}
		}
	}

	/**
	 * Filters IUs that are already installed
	 */
	private void removeInstalled(final Set<IInstallableUnit> installableUnits) {

		ProvisioningUI ui = ProvisioningUI.getDefaultUI();

		ProvisioningSession session = ui.getSession();

		IProfileRegistry registry = session.getProvisioningAgent().getService(IProfileRegistry.class);

		String profileId = ui.getProfileId();
		IProfile profile = registry.getProfile(profileId);
		Set<IInstallableUnit> toRemove = new HashSet<>();
		for(IInstallableUnit unit : installableUnits) {
			IQueryResult<?> result = profile.query(QueryUtil.createIUQuery(unit.getId()), null);

			if(!result.isEmpty()) {
				toRemove.add(unit);
			}
		}
		installableUnits.removeAll(toRemove);
	}

	/**
	 * Perform a query to get the installable units. This causes p2 to determine what features are available in each
	 * repository. We select installable units by matching both the feature id and the repository; it is possible though
	 * unlikely that the same feature id is available from more than one of the selected repositories, and we must
	 * ensure that the user gets the one that they asked for.
	 */
	private Set<IInstallableUnit> queryInstallableUnits(SubMonitor monitor, List<IMetadataRepository> repositories) throws URISyntaxException {

		final Set<IInstallableUnit> installableUnits = new HashSet<>();
		monitor.setWorkRemaining(repositories.size());
		for(final IMetadataRepository repository : repositories) {
			checkCancelled(monitor);
			final Set<String> installableUnitIdsThisRepository = getDescriptorIds();
			IQuery<IInstallableUnit> query = QueryUtil.createMatchQuery( //
					"id ~= /*.feature.group/ && " + //$NON-NLS-1$
							"properties['org.eclipse.equinox.p2.type.group'] == true ");//$NON-NLS-1$
			IQueryResult<IInstallableUnit> result = repository.query(query, monitor.newChild(1));
			for(Iterator<IInstallableUnit> iter = result.iterator(); iter.hasNext();) {
				IInstallableUnit iu = iter.next();
				String id = iu.getId();
				if(installableUnitIdsThisRepository.contains(id)) {
					installableUnits.add(iu);
				}
			}
		}
		return installableUnits;
	}

	private List<IMetadataRepository> getRepositories(SubMonitor monitor) throws ProvisionException {

		ProvisioningSession session = provisioningUI.getSession();
		List<IMetadataRepository> metaRepositories = new ArrayList<>();
		IMetadataRepositoryManager manager = session.getProvisioningAgent().getService(IMetadataRepositoryManager.class);
		monitor.setWorkRemaining(repositories.length);
		for(URI uri : repositories) {
			checkCancelled(monitor);
			IMetadataRepository repository = manager.loadRepository(uri, monitor.newChild(1));
			metaRepositories.add(repository);
		}
		return metaRepositories;
	}

	private Set<String> getDescriptorIds() throws URISyntaxException {

		final Set<String> installableUnitIdsThisRepository = new HashSet<>();
		// determine all installable units for this repository
		for(IPluginDescriptor descriptor : installableConnectors) {
			installableUnitIdsThisRepository.add(descriptor.getInstallableUnit() + P2_FEATURE_GROUP_SUFFIX);
		}
		return installableUnitIdsThisRepository;
	}

}
