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
package net.openchrom.installer.ui.wizards;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.io.File;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.regex.Pattern;

import org.eclipse.chemclipse.logging.core.Logger;
import org.eclipse.chemclipse.rcp.ui.icons.core.ApplicationImageFactory;
import org.eclipse.chemclipse.rcp.ui.icons.core.IApplicationImage;
import org.eclipse.chemclipse.rcp.ui.icons.core.IApplicationImageProvider;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IBundleGroup;
import org.eclipse.core.runtime.IBundleGroupProvider;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Platform;
import org.eclipse.core.runtime.Status;
import org.eclipse.equinox.p2.engine.IProfile;
import org.eclipse.equinox.p2.engine.IProfileRegistry;
import org.eclipse.equinox.p2.operations.ProvisioningSession;
import org.eclipse.equinox.p2.query.IQueryResult;
import org.eclipse.equinox.p2.query.QueryUtil;
import org.eclipse.equinox.p2.ui.ProvisioningUI;
import org.eclipse.jface.dialogs.Dialog;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.jface.layout.GridDataFactory;
import org.eclipse.jface.layout.GridLayoutFactory;
import org.eclipse.jface.resource.ImageDescriptor;
import org.eclipse.jface.resource.JFaceResources;
import org.eclipse.jface.wizard.WizardPage;
import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.ScrolledComposite;
import org.eclipse.swt.events.ControlAdapter;
import org.eclipse.swt.events.ControlEvent;
import org.eclipse.swt.events.FocusAdapter;
import org.eclipse.swt.events.FocusEvent;
import org.eclipse.swt.events.MouseAdapter;
import org.eclipse.swt.events.MouseEvent;
import org.eclipse.swt.events.MouseListener;
import org.eclipse.swt.events.PaintEvent;
import org.eclipse.swt.events.PaintListener;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.graphics.Font;
import org.eclipse.swt.graphics.FontData;
import org.eclipse.swt.graphics.GC;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.graphics.Rectangle;
import org.eclipse.swt.graphics.Resource;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Canvas;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.FileDialog;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Link;
import org.eclipse.swt.widgets.Listener;
import org.eclipse.swt.widgets.Text;
import org.eclipse.swt.widgets.ToolBar;
import org.eclipse.swt.widgets.ToolItem;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.progress.WorkbenchJob;

import net.openchrom.installer.model.BundleDiscoveryStrategy;
import net.openchrom.installer.model.DiscoveryCategory;
import net.openchrom.installer.model.DiscoveryPlugin;
import net.openchrom.installer.model.IDiscoverySource;
import net.openchrom.installer.model.Overview;
import net.openchrom.installer.model.PluginDescriptor;
import net.openchrom.installer.model.PluginDescriptorKind;
import net.openchrom.installer.model.PluginDiscovery;
import net.openchrom.installer.preferences.PreferenceSupplier;
import net.openchrom.installer.ui.model.SetupDefinition;
import net.openchrom.installer.ui.swt.OverviewToolTip;
import net.openchrom.installer.util.DiscoveryCategoryComparator;
import net.openchrom.installer.util.DiscoveryConnectorComparator;

/**
 * The main wizard page that allows users to select plugins that they wish to install.
 * 
 * @author David Green
 * @author Igor Burilo
 */
public class PluginDiscoveryWizardMainPage extends WizardPage {

	private static final Logger logger = Logger.getLogger(PluginDiscoveryWizardMainPage.class);
	private final Set<PluginDescriptor> installableConnectors = new HashSet<>();
	private PluginDiscovery discovery;
	private Composite body;
	private final List<Resource> disposables = new ArrayList<>();
	private Font h2Font;
	private Font h1Font;
	private Color colorWhite;
	private Text filterText;
	private WorkbenchJob refreshJob;
	private String previousFilterText = ""; //$NON-NLS-1$
	private Pattern filterPattern;
	private Set<String> installedFeatures;
	private Image infoImage;
	private Color colorDisabled;
	private ScrolledComposite bodyScrolledComposite;
	private IProfile profile;
	private List<String> importedFeatures;

	public PluginDiscoveryWizardMainPage() {

		super(PluginDiscoveryWizardMainPage.class.getSimpleName());
		setTitle("Plug-in Installation");
		// setImageDescriptor(image);
		setDescription("Install plug-ins to handle vendor file formats or extend the platform with additional features.");
		setPageComplete(false);
	}

	@Override
	public void createControl(Composite parent) {

		createRefreshJob();
		initializeColors();
		initializeImages();
		initializeFonts();
		Composite container = new Composite(parent, SWT.NULL);
		container.addDisposeListener(e -> refreshJob.cancel());
		container.setLayout(new GridLayout(1, false));

		{ // header
			Composite header = new Composite(container, SWT.NULL);
			GridLayoutFactory.fillDefaults().applyTo(header);
			GridDataFactory.fillDefaults().grab(true, false).applyTo(header);
			// TODO: refresh button?
			if(getWizard().isShowConnectorDescriptorKindFilter() || getWizard().isShowConnectorDescriptorTextFilter()) {
				Composite filterContainer = new Composite(header, SWT.NULL);
				GridDataFactory.fillDefaults().grab(true, false).applyTo(filterContainer);
				int numColumns = 5;
				GridLayoutFactory.fillDefaults().numColumns(numColumns).applyTo(filterContainer);
				Label label = new Label(filterContainer, SWT.NULL);
				label.setText("Filter");
				Composite textFilterContainer = new Composite(filterContainer, SWT.NULL);
				GridDataFactory.fillDefaults().grab(true, false).applyTo(textFilterContainer);
				GridLayoutFactory.fillDefaults().numColumns(2).applyTo(textFilterContainer);
				filterText = new Text(textFilterContainer, SWT.SINGLE | SWT.BORDER | SWT.SEARCH | SWT.ICON_SEARCH | SWT.ICON_CANCEL);
				filterText.addModifyListener(e -> refreshDisplayedIUs());
				GridDataFactory.fillDefaults().grab(true, false).span(2, 1).applyTo(filterText);
				createButtonImportSetupDefinition(filterContainer);
				Button refresh = new Button(filterContainer, SWT.PUSH);
				refresh.setImage(ApplicationImageFactory.getInstance().getImage(IApplicationImage.IMAGE_REFRESH, IApplicationImageProvider.SIZE_16x16));
				refresh.addSelectionListener(SelectionListener.widgetSelectedAdapter(e -> {
					importedFeatures = null;
					installableConnectors.clear();
					filterText.setText("");
					setPageComplete(false);
					refreshDisplayedIUs();
				}));
				/*
				 * Close this wizard and open the "Available Software" dialog.
				 */
				Button availableSoftware = new Button(filterContainer, SWT.PUSH);
				availableSoftware.setImage(ApplicationImageFactory.getInstance().getImage(IApplicationImage.IMAGE_MARKETPLACE, IApplicationImageProvider.SIZE_16x16));
				availableSoftware.addSelectionListener(SelectionListener.widgetSelectedAdapter(e -> {

					getShell().close();
					ProvisioningUI provisioningUI = ProvisioningUI.getDefaultUI();
					provisioningUI.openInstallWizard(null, null, null);
				}));
			}
		}
		{ // container
			body = new Composite(container, SWT.NULL);
			GridDataFactory.fillDefaults().grab(true, true).hint(SWT.DEFAULT, 480).applyTo(body);
		}
		Dialog.applyDialogFont(container);
		setControl(container);
		PlatformUI.getWorkbench().getHelpSystem().setHelp(parent, "net.openchrom.installer.ui.pluginDiscovery"); // TODO: does not work
	}

	private void createButtonImportSetupDefinition(Composite parent) {

		Button button = new Button(parent, SWT.PUSH);
		button.setText("");
		button.setToolTipText("Select a list of add-ons to be installed.");
		button.setImage(ApplicationImageFactory.getInstance().getImage(IApplicationImage.IMAGE_IMPORT, IApplicationImageProvider.SIZE_16x16));
		button.addSelectionListener(SelectionListener.widgetSelectedAdapter(e -> {

			FileDialog fileDialog = new FileDialog(e.display.getActiveShell(), SWT.READ_ONLY);
			fileDialog.setText(SetupDefinition.DESCRIPTION);
			fileDialog.setFilterExtensions(SetupDefinition.FILTER_EXTENSION);
			fileDialog.setFilterNames(SetupDefinition.FILTER_NAME);
			fileDialog.setFilterPath(PreferenceSupplier.getFilterPathImport());
			String fileName = fileDialog.open();
			if(fileName != null) {
				File file = new File(fileName);
				if(file.exists()) {
					PreferenceSupplier.setFilterPathImport(file.getParent());
					try {
						SetupDefinition setupDefinition = new SetupDefinition();
						List<String> features = setupDefinition.getFeatures(file);
						if(features != null && !features.isEmpty()) {
							filterText.setText("");
							importedFeatures = features;
						} else {
							MessageDialog.openWarning(getShell(), "Invalid file content", "Content of the file is not in the expected format.");
						}
					} catch(IOException ex) {
						MessageDialog.openWarning(getShell(), "Problem reading file", "Failed reading file.  Please verify the file exists and it's readable.");
					}
					refreshDisplayedIUs();
				}
			}
		}));
	}

	private void createRefreshJob() {

		refreshJob = new WorkbenchJob("filter") { //$NON-NLS-1$

			@Override
			public IStatus runInUIThread(IProgressMonitor monitor) {

				if(filterText.isDisposed()) {
					return Status.CANCEL_STATUS;
				}
				String text = filterText.getText().trim();
				if(!previousFilterText.equals(text)) {
					previousFilterText = text;
					filterPattern = createPattern(previousFilterText);
					createBodyContents();
				} else if(importedFeatures == null || !importedFeatures.isEmpty()) {
					createBodyContents();
				}
				return Status.OK_STATUS;
			}
		};
		refreshJob.setSystem(true);
	}

	protected Pattern createPattern(String filterText) {

		if(filterText == null || filterText.isEmpty()) {
			return null;
		}
		String regex = filterText;
		regex.replace("\\", "\\\\").replace("?", ".").replace("*", ".*?"); //$NON-NLS-1$//$NON-NLS-2$ //$NON-NLS-3$ //$NON-NLS-4$ //$NON-NLS-5$ //$NON-NLS-6$
		return Pattern.compile(regex, Pattern.CASE_INSENSITIVE | Pattern.DOTALL);
	}

	@Override
	public PluginDiscoveryWizard getWizard() {

		return (PluginDiscoveryWizard)super.getWizard();
	}

	private void clearFilterText() {

		filterText.setText(""); //$NON-NLS-1$
		refreshDisplayedIUs();
	}

	private void refreshDisplayedIUs() {

		refreshJob.cancel();
		refreshJob.schedule(200L);
	}

	/**
	 * cause the UI to respond to a change in visibility filters
	 * 
	 * @see #setVisibility(PluginDescriptorKind, boolean)
	 */
	public void pluginDescriptorKindVisibilityUpdated() {

		createBodyContents();
	}

	@Override
	public void dispose() {

		super.dispose();
		clearDisposables();
		h1Font.dispose();
		h2Font.dispose();
	}

	private void clearDisposables() {

		for(Resource resource : disposables) {
			resource.dispose();
		}
		disposables.clear();
	}

	public void createBodyContents() {

		// remove any existing contents
		for(Control child : body.getChildren()) {
			child.dispose();
		}
		clearDisposables();
		GridLayoutFactory.fillDefaults().applyTo(body);
		bodyScrolledComposite = new ScrolledComposite(body, SWT.H_SCROLL | SWT.V_SCROLL | SWT.BORDER);
		configureLook(bodyScrolledComposite, colorWhite);
		GridDataFactory.fillDefaults().grab(true, true).applyTo(bodyScrolledComposite);
		// FIXME 3.2 does white work for any desktop theme, e.g. an inverse theme?
		final Composite scrolledContents = new Composite(bodyScrolledComposite, SWT.NONE);
		configureLook(scrolledContents, colorWhite);
		scrolledContents.setRedraw(false);
		try {
			createDiscoveryContents(scrolledContents);
		} finally {
			scrolledContents.layout(true);
			scrolledContents.setRedraw(true);
		}
		Point size = scrolledContents.computeSize(body.getSize().x, SWT.DEFAULT, true);
		scrolledContents.setSize(size);
		bodyScrolledComposite.setExpandHorizontal(true);
		bodyScrolledComposite.setMinWidth(100);
		bodyScrolledComposite.setExpandVertical(true);
		bodyScrolledComposite.setMinHeight(1);
		bodyScrolledComposite.addControlListener(new ControlAdapter() {

			@Override
			public void controlResized(ControlEvent e) {

				// XXX small offset in case list has a scroll bar
				Point size = scrolledContents.computeSize(body.getSize().x - 20, SWT.DEFAULT, true);
				scrolledContents.setSize(size);
				bodyScrolledComposite.setMinHeight(size.y);
			}
		});
		bodyScrolledComposite.setContent(scrolledContents);
		Dialog.applyDialogFont(body);
		// we've changed it so it needs to know
		body.layout(true);
	}

	private void initializeImages() {

		infoImage = ApplicationImageFactory.getInstance().getImage(IApplicationImage.IMAGE_INFO, IApplicationImageProvider.SIZE_16x16);
	}

	private void initializeColors() {

		colorWhite = getShell().getDisplay().getSystemColor(SWT.COLOR_WHITE);
		colorDisabled = getShell().getDisplay().getSystemColor(SWT.COLOR_DARK_GRAY);
	}

	private void initializeFonts() {

		// create a level-2 heading font
		{
			Font baseFont = JFaceResources.getDialogFont();
			FontData[] fontData = baseFont.getFontData();
			for(FontData data : fontData) {
				data.setStyle(data.getStyle() | SWT.BOLD);
				data.height = data.height * 1.25f;
			}
			h2Font = new Font(Display.getCurrent(), fontData);
		}
		// create a level-1 heading font
		{
			Font baseFont = JFaceResources.getDialogFont();
			FontData[] fontData = baseFont.getFontData();
			for(FontData data : fontData) {
				data.setStyle(data.getStyle() | SWT.BOLD);
				data.height = data.height * 1.35f;
			}
			h1Font = new Font(Display.getCurrent(), fontData);
		}
	}

	private class PluginDescriptorItemUI implements PropertyChangeListener, Runnable {

		private final DiscoveryPlugin plugin;
		private final Button checkbox;
		private final Label iconLabel;
		private final Label nameLabel;
		private ToolItem infoButton;
		private final Label providerLabel;
		private final Label description;
		private final Composite checkboxContainer;
		private final Composite pluginContainer;
		private final Display display;
		private Image iconImage;

		public PluginDescriptorItemUI(DiscoveryPlugin plugin, Composite categoryChildrenContainer, Color background) {

			display = categoryChildrenContainer.getDisplay();
			this.plugin = plugin;
			plugin.addPropertyChangeListener(this);
			pluginContainer = new Composite(categoryChildrenContainer, SWT.NULL);
			configureLook(pluginContainer, background);
			GridDataFactory.fillDefaults().grab(true, false).applyTo(pluginContainer);
			GridLayout layout = new GridLayout(4, false);
			layout.marginLeft = 7;
			layout.marginTop = 2;
			layout.marginBottom = 2;
			pluginContainer.setLayout(layout);
			checkboxContainer = new Composite(pluginContainer, SWT.NULL);
			configureLook(checkboxContainer, background);
			GridDataFactory.swtDefaults().align(SWT.CENTER, SWT.BEGINNING).span(1, 2).applyTo(checkboxContainer);
			GridLayoutFactory.fillDefaults().spacing(1, 1).numColumns(2).applyTo(checkboxContainer);
			checkbox = new Button(checkboxContainer, SWT.CHECK);
			checkbox.setText(" "); //$NON-NLS-1$
			// help UI tests
			checkbox.setData("pluginId", plugin.getInstallableUnit()); //$NON-NLS-1$
			checkbox.setSelection(installableConnectors.contains(plugin));
			checkbox.addFocusListener(new FocusAdapter() {

				@Override
				public void focusGained(FocusEvent e) {

					bodyScrolledComposite.showControl(pluginContainer);
				}
			});
			GridDataFactory.swtDefaults().align(SWT.CENTER, SWT.CENTER).applyTo(checkbox);
			iconLabel = new Label(checkboxContainer, SWT.NULL);
			configureLook(iconLabel, background);
			GridDataFactory.swtDefaults().align(SWT.CENTER, SWT.CENTER).applyTo(iconLabel);
			if(plugin.getIcon() != null) {
				iconImage = computeIconImage(plugin.getSource(), plugin.getIcon(), false);
				if(iconImage != null) {
					iconLabel.setImage(iconImage);
				}
			}
			nameLabel = new Label(pluginContainer, SWT.NULL);
			configureLook(nameLabel, background);
			GridDataFactory.fillDefaults().grab(true, false).applyTo(nameLabel);
			nameLabel.setFont(h2Font);
			nameLabel.setText(plugin.getName());
			providerLabel = new Label(pluginContainer, SWT.NULL);
			configureLook(providerLabel, background);
			GridDataFactory.fillDefaults().align(SWT.END, SWT.CENTER).applyTo(providerLabel);
			providerLabel.setText(plugin.getProvider() + " " + plugin.getLicense());
			if(hasTooltip(plugin)) {
				ToolBar toolBar = new ToolBar(pluginContainer, SWT.FLAT);
				toolBar.setBackground(background);
				infoButton = new ToolItem(toolBar, SWT.PUSH);
				infoButton.setImage(infoImage);
				infoButton.setToolTipText("show overview");
				hookTooltip(toolBar, infoButton, pluginContainer, nameLabel, plugin.getSource(), plugin.getOverview());
				GridDataFactory.fillDefaults().align(SWT.END, SWT.CENTER).applyTo(toolBar);
			} else {
				new Label(pluginContainer, SWT.NULL).setText(" "); //$NON-NLS-1$
			}
			description = new Label(pluginContainer, SWT.NULL | SWT.WRAP);
			configureLook(description, background);
			GridDataFactory.fillDefaults().grab(true, false).span(3, 1).hint(100, SWT.DEFAULT).applyTo(description);
			String descriptionText = plugin.getDescription();
			int maxDescriptionLength = 162;
			if(descriptionText.length() > maxDescriptionLength) {
				descriptionText = descriptionText.substring(0, maxDescriptionLength);
			}
			description.setText(descriptionText.replaceAll("(\\r\\n)|\\n|\\r", " ")); //$NON-NLS-1$ //$NON-NLS-2$
			// always disabled color to make it less prominent
			providerLabel.setForeground(colorDisabled);
			checkbox.addSelectionListener(new SelectionListener() {

				@Override
				public void widgetDefaultSelected(SelectionEvent e) {

					widgetSelected(e);
				}

				@Override
				public void widgetSelected(SelectionEvent e) {

					boolean selected = checkbox.getSelection();
					maybeModifySelection(selected);
				}
			});
			MouseListener pluginItemMouseListener = new MouseAdapter() {

				@Override
				public void mouseUp(MouseEvent e) {

					if(checkbox.getEnabled()) {
						boolean selected = !checkbox.getSelection();
						if(maybeModifySelection(selected)) {
							checkbox.setSelection(selected);
						}
					}
				}
			};
			checkboxContainer.addMouseListener(pluginItemMouseListener);
			pluginContainer.addMouseListener(pluginItemMouseListener);
			iconLabel.addMouseListener(pluginItemMouseListener);
			nameLabel.addMouseListener(pluginItemMouseListener);
			providerLabel.addMouseListener(pluginItemMouseListener);
			description.addMouseListener(pluginItemMouseListener);
		}

		protected boolean maybeModifySelection(boolean selected) {

			PluginDiscoveryWizardMainPage.this.modifySelection(plugin, selected);
			return true;
		}

		public void updateAvailability() {

			boolean isInstalled = isInstalled(plugin.getInstallableUnit());
			checkbox.setSelection(isInstalled);
			if(importedFeatures != null && importedFeatures.contains(plugin.getInstallableUnit() + SetupDefinition.P2_FEATURE_GROUP_SUFFIX)) {
				checkbox.setSelection(true);
				modifySelection(plugin, true);
			}
			checkbox.setEnabled(!isInstalled);
			if(iconImage != null) {
				iconLabel.setImage(iconImage);
			}
		}

		/**
		 * Checks whether the given uid is already installed.
		 */
		private boolean isInstalled(String uid) {

			if(!uid.endsWith(SetupDefinition.P2_FEATURE_GROUP_SUFFIX)) {
				uid += SetupDefinition.P2_FEATURE_GROUP_SUFFIX;
			}

			IProfile profile = getP2Profile();
			if(profile == null) {
				return false;
			}

			IQueryResult<?> result = profile.query(QueryUtil.createIUQuery(uid), null);
			return !result.isEmpty();

		}

		@Override
		public void propertyChange(PropertyChangeEvent evt) {

			display.asyncExec(this);
		}

		@Override
		public void run() {

			if(!pluginContainer.isDisposed()) {
				updateAvailability();
			}
		}
	}

	private IProfile getP2Profile() {

		if(profile == null) {
			ProvisioningUI ui = ProvisioningUI.getDefaultUI();

			ProvisioningSession session = ui.getSession();

			IProfileRegistry registry = session.getProvisioningAgent().getService(IProfileRegistry.class);

			String profileId = ui.getProfileId();
			profile = registry.getProfile(profileId);
		}
		return profile;
	}

	private void createDiscoveryContents(Composite container) {

		Color background = container.getBackground();
		if(discovery == null || isEmpty(discovery)) {
			GridLayoutFactory.fillDefaults().margins(5, 5).applyTo(container);
			boolean atLeastOneKindFiltered = false;
			for(PluginDescriptorKind kind : PluginDescriptorKind.values()) {
				if(!getWizard().isVisible(kind)) {
					atLeastOneKindFiltered = true;
					break;
				}
			}
			Control helpTextControl;
			if(filterPattern != null) {
				Link link = new Link(container, SWT.WRAP);
				link.setFont(container.getFont());
				link.setText("There are no matching plugins.  Please <a>clear the filter text</a> or try again later.");
				link.addListener(SWT.Selection, event -> {

					clearFilterText();
					filterText.setFocus();
				});
				helpTextControl = link;
			} else {
				Label helpText = new Label(container, SWT.WRAP);
				helpText.setFont(container.getFont());
				if(atLeastOneKindFiltered) {
					helpText.setText("There are no plugins of the selected type.  Please select another plugin type or try again later.");
				} else {
					helpText.setText("Sorry, there are no available plugins.  Please try again later.");
				}
				helpTextControl = helpText;
			}
			configureLook(helpTextControl, background);
			GridDataFactory.fillDefaults().grab(true, false).hint(100, SWT.DEFAULT).applyTo(helpTextControl);
		} else {
			GridLayoutFactory.fillDefaults().numColumns(2).spacing(0, 0).applyTo(container);
			List<DiscoveryCategory> categories = new ArrayList<>(discovery.getCategories());
			Collections.sort(categories, new DiscoveryCategoryComparator());
			Composite categoryChildrenContainer = null;
			for(DiscoveryCategory category : categories) {
				if(isEmpty(category)) {
					// don't add empty categories
					continue;
				}
				{ // category header
					final Canvas categoryHeaderContainer = new Canvas(container, SWT.NONE);
					GridDataFactory.fillDefaults().span(2, 1).applyTo(categoryHeaderContainer);
					GridLayoutFactory.fillDefaults().numColumns(3).margins(5, 5).equalWidth(false).applyTo(categoryHeaderContainer);
					Label iconLabel = new Label(categoryHeaderContainer, SWT.NULL);
					if(category.getIcon() != null) {
						Image image = computeIconImage(category.getSource(), category.getIcon(), true);
						if(image != null) {
							iconLabel.setImage(image);
						}
					}
					GridDataFactory.swtDefaults().align(SWT.CENTER, SWT.BEGINNING).span(1, 2).applyTo(iconLabel);
					Label nameLabel = new Label(categoryHeaderContainer, SWT.NULL);
					nameLabel.setFont(h1Font);
					nameLabel.setText(category.getName());
					GridDataFactory.fillDefaults().grab(true, false).applyTo(nameLabel);
					if(hasTooltip(category)) {
						ToolBar toolBar = new ToolBar(categoryHeaderContainer, SWT.FLAT);
						toolBar.setBackground(null);
						ToolItem infoButton = new ToolItem(toolBar, SWT.PUSH);
						infoButton.setImage(infoImage);
						infoButton.setToolTipText("Show Overview");
						hookTooltip(toolBar, infoButton, categoryHeaderContainer, nameLabel, category.getSource(), category.getOverview());
						GridDataFactory.fillDefaults().align(SWT.END, SWT.CENTER).applyTo(toolBar);
					} else {
						new Label(categoryHeaderContainer, SWT.NULL).setText(" "); //$NON-NLS-1$
					}
					Label description = new Label(categoryHeaderContainer, SWT.WRAP);
					GridDataFactory.fillDefaults().grab(true, false).span(2, 1).hint(100, SWT.DEFAULT).applyTo(description);
					description.setText(category.getDescription());
				}
				categoryChildrenContainer = new Composite(container, SWT.NULL);
				configureLook(categoryChildrenContainer, background);
				GridDataFactory.fillDefaults().span(2, 1).grab(true, false).applyTo(categoryChildrenContainer);
				GridLayoutFactory.fillDefaults().spacing(0, 0).applyTo(categoryChildrenContainer);
				int numChildren = 0;
				List<DiscoveryPlugin> plugins = new ArrayList<>(category.getConnectors());
				Collections.sort(plugins, new DiscoveryConnectorComparator(category));
				for(final DiscoveryPlugin plugin : plugins) {
					if(isFiltered(plugin)) {
						continue;
					}
					if(++numChildren > 1) {
						// a separator between plugin descriptors
						Composite border = new Composite(categoryChildrenContainer, SWT.NULL);
						GridDataFactory.fillDefaults().grab(true, false).hint(SWT.DEFAULT, 1).applyTo(border);
						GridLayoutFactory.fillDefaults().applyTo(border);
						border.addPaintListener(new ConnectorBorderPaintListener());
					}
					PluginDescriptorItemUI itemUi = new PluginDescriptorItemUI(plugin, categoryChildrenContainer, background);
					itemUi.updateAvailability();
				}
			}
			// last one gets a border
			Composite border = new Composite(categoryChildrenContainer, SWT.NULL);
			GridDataFactory.fillDefaults().grab(true, false).hint(SWT.DEFAULT, 1).applyTo(border);
			GridLayoutFactory.fillDefaults().applyTo(border);
			border.addPaintListener(new ConnectorBorderPaintListener());
		}
		container.layout(true);
		container.redraw();
	}

	private void configureLook(Control control, Color background) {

		control.setBackground(background);
	}

	private void hookTooltip(final Control tooltipControl, final ToolItem tipActivator, final Control exitControl, final Control titleControl, IDiscoverySource source, Overview overview) {

		final OverviewToolTip toolTip = new OverviewToolTip(tooltipControl, source, overview);
		Listener listener = event -> {

			switch(event.type) {
				case SWT.Dispose:
				case SWT.MouseWheel:
					toolTip.hide();
					break;
			}
		};
		tipActivator.addListener(SWT.Dispose, listener);
		tipActivator.addListener(SWT.MouseWheel, listener);
		tipActivator.addSelectionListener(new SelectionAdapter() {

			@Override
			public void widgetSelected(SelectionEvent e) {

				Point titleAbsLocation = titleControl.getParent().toDisplay(titleControl.getLocation());
				Point containerAbsLocation = tooltipControl.getParent().toDisplay(tooltipControl.getLocation());
				Rectangle bounds = titleControl.getBounds();
				int relativeX = titleAbsLocation.x - containerAbsLocation.x;
				int relativeY = titleAbsLocation.y - containerAbsLocation.y;
				relativeY += bounds.height + 3;
				toolTip.show(new Point(relativeX, relativeY));
			}
		});
		Listener exitListener = event -> {

			switch(event.type) {
				case SWT.MouseWheel:
					toolTip.hide();
					break;
				case SWT.MouseExit:
					/*
					 * Check if the mouse exit happened because we move over the
					 * tooltip
					 */
					Rectangle containerBounds = exitControl.getBounds();
					Point displayLocation = exitControl.getParent().toDisplay(containerBounds.x, containerBounds.y);
					containerBounds.x = displayLocation.x;
					containerBounds.y = displayLocation.y;
					if(containerBounds.contains(Display.getCurrent().getCursorLocation())) {
						break;
					}
					toolTip.hide();
					break;
			}
		};
		hookRecursively(exitControl, exitListener);
	}

	private void hookRecursively(Control control, Listener listener) {

		control.addListener(SWT.Dispose, listener);
		control.addListener(SWT.MouseHover, listener);
		control.addListener(SWT.MouseMove, listener);
		control.addListener(SWT.MouseExit, listener);
		control.addListener(SWT.MouseDown, listener);
		control.addListener(SWT.MouseWheel, listener);
		if(control instanceof Composite composite) {
			for(Control child : composite.getChildren()) {
				hookRecursively(child, listener);
			}
		}
	}

	private boolean hasTooltip(final DiscoveryCategory category) {

		return category.getOverview() != null && category.getOverview().getSummary() != null && category.getOverview().getSummary().length() > 0;
	}

	/**
	 * indicate if there is nothing to display in the UI, given the current state of
	 * {@link PluginDiscoveryWizard#isVisible(PluginDescriptorKind) filters}.
	 */
	private boolean isEmpty(PluginDiscovery discovery) {

		for(DiscoveryCategory category : discovery.getCategories()) {
			if(!isEmpty(category)) {
				return false;
			}
		}
		return true;
	}

	/**
	 * indicate if the category has nothing to display in the UI, given the current state of
	 * {@link PluginDiscoveryWizard#isVisible(PluginDescriptorKind) filters}.
	 */
	private boolean isEmpty(DiscoveryCategory category) {

		if(category.getConnectors().isEmpty()) {
			return true;
		}
		for(PluginDescriptor descriptor : category.getConnectors()) {
			if(!isFiltered(descriptor)) {
				return false;
			}
		}
		return true;
	}

	private boolean isFiltered(PluginDescriptor descriptor) {

		boolean kindFiltered = true;
		for(PluginDescriptorKind kind : descriptor.getKind()) {
			if(getWizard().isVisible(kind)) {
				kindFiltered = false;
				break;
			}
		}
		if(kindFiltered) {
			return true;
		}
		if(installedFeatures != null && installedFeatures.contains(descriptor.getInstallableUnit())) {
			// always filter installed features per bug 275777
			return true;
		}
		if(filterPattern != null) {
			if(!(filterMatches(descriptor.getName()) || filterMatches(descriptor.getDescription()) || filterMatches(descriptor.getProvider()) || filterMatches(descriptor.getLicense()))) {
				return true;
			}
		}
		if(importedFeatures != null && !importedFeatures.isEmpty() && !importedFeatures.contains(descriptor.getInstallableUnit() + SetupDefinition.P2_FEATURE_GROUP_SUFFIX)) {
			return true;
		}
		return false;
	}

	private boolean filterMatches(String text) {

		return text != null && filterPattern.matcher(text).find();
	}

	private Image computeIconImage(IDiscoverySource discoverySource, String icon, boolean fallback) {

		if(icon != null && !icon.isEmpty()) {
			URL resource = discoverySource.getResource(icon);
			if(resource != null) {
				ImageDescriptor descriptor = ImageDescriptor.createFromURL(resource);
				Image image = descriptor.createImage();
				if(image != null) {
					disposables.add(image);
					return image;
				}
			}
		}
		return null;
	}

	private void maybeUpdateDiscovery() {

		if(!getControl().isDisposed() && isCurrentPage() && discovery == null) {
			boolean wasCancelled = false;
			try {
				getContainer().run(true, true, monitor -> {

					if(PluginDiscoveryWizardMainPage.this.installedFeatures == null) {
						Set<String> installedFeatures = new HashSet<>();
						IBundleGroupProvider[] bundleGroupProviders = Platform.getBundleGroupProviders();
						for(IBundleGroupProvider provider : bundleGroupProviders) {
							if(monitor.isCanceled()) {
								throw new InterruptedException();
							}
							IBundleGroup[] bundleGroups = provider.getBundleGroups();
							for(IBundleGroup group : bundleGroups) {
								installedFeatures.add(group.getIdentifier());
							}
						}
						PluginDiscoveryWizardMainPage.this.installedFeatures = installedFeatures;
					}
					PluginDiscovery pluginDiscovery = new PluginDiscovery();
					pluginDiscovery.getDiscoveryStrategies().add(new BundleDiscoveryStrategy());
					try {
						pluginDiscovery.performDiscovery(monitor);
					} catch(CoreException e) {
						throw new InvocationTargetException(e);
					} finally {
						PluginDiscoveryWizardMainPage.this.discovery = pluginDiscovery;
					}
					if(monitor.isCanceled()) {
						throw new InterruptedException();
					}
				});
			} catch(InvocationTargetException e) {
				logger.warn(e.getCause());
			} catch(InterruptedException e) {
				// cancelled by user so nothing to do here.
				wasCancelled = true;
				Thread.currentThread().interrupt();
			}
			if(discovery != null) {
				discoveryUpdated(wasCancelled);
				if(discovery.getConnectors().isEmpty()) {
					return;
				}
				// createBodyContents() shouldn't be necessary but for some reason checkboxes don't
				// regain their enabled state
				createBodyContents();
			}
			// help UI tests
			body.setData("discoveryComplete", "true"); //$NON-NLS-1$//$NON-NLS-2$
		}
	}

	@Override
	public void setVisible(boolean visible) {

		super.setVisible(visible);
		if(visible && discovery == null) {
			Display.getCurrent().asyncExec(() -> maybeUpdateDiscovery());
		}
	}

	private void discoveryUpdated(boolean wasCancelled) {

		createBodyContents();
		if(discovery != null && !wasCancelled) {
			int categoryWithConnectorCount = 0;
			for(DiscoveryCategory category : discovery.getCategories()) {
				categoryWithConnectorCount += category.getConnectors().size();
			}
			if(categoryWithConnectorCount == 0) {
				// nothing was discovered: notify the user
				MessageDialog.openWarning(getShell(), "No Plugins found", "Plugin discovery completed without finding any plugins.  Please check your Internet connection and try again.");
			}
		}
	}

	public Set<PluginDescriptor> getInstallableConnectors() {

		return installableConnectors;
	}

	private void modifySelection(final DiscoveryPlugin plugin, boolean selected) {

		plugin.setSelected(selected);
		if(selected) {
			installableConnectors.add(plugin);
		} else {
			installableConnectors.remove(plugin);
		}
		setPageComplete(!installableConnectors.isEmpty());
	}

	public class ConnectorBorderPaintListener implements PaintListener {

		@Override
		public void paintControl(PaintEvent e) {

			Composite composite = (Composite)e.widget;
			Rectangle bounds = composite.getBounds();
			GC gc = e.gc;
			gc.setLineStyle(SWT.LINE_DOT);
			gc.drawLine(bounds.x, bounds.y, bounds.x + bounds.width, bounds.y);
		}
	}

	private boolean hasTooltip(final DiscoveryPlugin plugin) {

		return plugin.getOverview() != null && plugin.getOverview().getSummary() != null && !plugin.getOverview().getSummary().isEmpty();
	}
}
