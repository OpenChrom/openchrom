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
package net.openchrom.installer.ui.wizards;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.InvocationTargetException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Dictionary;
import java.util.HashSet;
import java.util.List;
import java.util.Properties;
import java.util.Set;
import java.util.regex.Pattern;

import org.eclipse.chemclipse.logging.core.Logger;
import org.eclipse.chemclipse.rcp.ui.icons.core.ApplicationImageFactory;
import org.eclipse.chemclipse.rcp.ui.icons.core.IApplicationImage;
import org.eclipse.chemclipse.rcp.ui.icons.core.IApplicationImageProvider;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.FileLocator;
import org.eclipse.core.runtime.IBundleGroup;
import org.eclipse.core.runtime.IBundleGroupProvider;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Path;
import org.eclipse.core.runtime.Platform;
import org.eclipse.core.runtime.Status;
import org.eclipse.jface.dialogs.Dialog;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.jface.layout.GridDataFactory;
import org.eclipse.jface.layout.GridLayoutFactory;
import org.eclipse.jface.operation.IRunnableWithProgress;
import org.eclipse.jface.resource.ColorRegistry;
import org.eclipse.jface.resource.ImageDescriptor;
import org.eclipse.jface.resource.JFaceResources;
import org.eclipse.jface.viewers.DecorationOverlayIcon;
import org.eclipse.jface.viewers.IDecoration;
import org.eclipse.jface.wizard.WizardPage;
import org.eclipse.swt.SWT;
import org.eclipse.swt.accessibility.ACC;
import org.eclipse.swt.accessibility.AccessibleAdapter;
import org.eclipse.swt.accessibility.AccessibleControlAdapter;
import org.eclipse.swt.accessibility.AccessibleControlEvent;
import org.eclipse.swt.accessibility.AccessibleEvent;
import org.eclipse.swt.custom.ScrolledComposite;
import org.eclipse.swt.events.ControlAdapter;
import org.eclipse.swt.events.ControlEvent;
import org.eclipse.swt.events.DisposeEvent;
import org.eclipse.swt.events.DisposeListener;
import org.eclipse.swt.events.FocusAdapter;
import org.eclipse.swt.events.FocusEvent;
import org.eclipse.swt.events.ModifyEvent;
import org.eclipse.swt.events.ModifyListener;
import org.eclipse.swt.events.MouseAdapter;
import org.eclipse.swt.events.MouseEvent;
import org.eclipse.swt.events.MouseListener;
import org.eclipse.swt.events.MouseMoveListener;
import org.eclipse.swt.events.MouseTrackListener;
import org.eclipse.swt.events.PaintEvent;
import org.eclipse.swt.events.PaintListener;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.graphics.Cursor;
import org.eclipse.swt.graphics.Font;
import org.eclipse.swt.graphics.FontData;
import org.eclipse.swt.graphics.GC;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.graphics.RGB;
import org.eclipse.swt.graphics.Rectangle;
import org.eclipse.swt.graphics.Resource;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Link;
import org.eclipse.swt.widgets.Listener;
import org.eclipse.swt.widgets.Text;
import org.eclipse.swt.widgets.ToolBar;
import org.eclipse.swt.widgets.ToolItem;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.forms.IFormColors;
import org.eclipse.ui.progress.WorkbenchJob;

import net.openchrom.installer.model.BundleDiscoveryStrategy;
import net.openchrom.installer.model.DiscoveryCategory;
import net.openchrom.installer.model.DiscoveryPlugin;
import net.openchrom.installer.model.IDiscoverySource;
import net.openchrom.installer.model.Icon;
import net.openchrom.installer.model.Overview;
import net.openchrom.installer.model.PluginDescriptor;
import net.openchrom.installer.model.PluginDescriptorKind;
import net.openchrom.installer.model.PluginDiscovery;
import net.openchrom.installer.model.RemoteBundleDiscoveryStrategy;
import net.openchrom.installer.ui.Activator;
import net.openchrom.installer.ui.swt.GradientCanvas;
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

	private static final String DISCOVERY_PROPERTIES_FILE = "discovery.properties";
	private static final String URL_DISCOVERY_PROPERTY = "url";
	private static final String COLOR_WHITE = "white"; //$NON-NLS-1$
	private static final String COLOR_DARK_GRAY = "DarkGray"; //$NON-NLS-1$
	private static final String COLOR_CATEGORY_GRADIENT_START = "category.gradient.start"; //$NON-NLS-1$
	private static final String COLOR_CATEGORY_GRADIENT_END = "category.gradient.end"; //$NON-NLS-1$
	private static final Logger logger = Logger.getLogger(PluginDiscoveryWizardMainPage.class);
	private static Boolean useNativeSearchField;
	private final List<PluginDescriptor> installableConnectors = new ArrayList<>();
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
	private Label clearFilterTextControl;
	private Set<String> installedFeatures;
	private Image infoImage;
	private Cursor handCursor;
	private Color colorCategoryGradientStart;
	private Color colorCategoryGradientEnd;
	private Color colorDisabled;
	private ScrolledComposite bodyScrolledComposite;

	public PluginDiscoveryWizardMainPage() {

		super(PluginDiscoveryWizardMainPage.class.getSimpleName());
		setTitle("File Format Support");
		// setImageDescriptor(image);
		setDescription("Install plug-ins to handle vendor file formats.");
		setPageComplete(false);
	}

	@Override
	public void createControl(Composite parent) {

		createRefreshJob();
		Composite container = new Composite(parent, SWT.NULL);
		container.addDisposeListener(new DisposeListener() {

			@Override
			public void widgetDisposed(DisposeEvent e) {

				refreshJob.cancel();
			}
		});
		container.setLayout(new GridLayout(1, false));
		//
		{ // header
			Composite header = new Composite(container, SWT.NULL);
			GridLayoutFactory.fillDefaults().applyTo(header);
			GridDataFactory.fillDefaults().grab(true, false).applyTo(header);
			// TODO: refresh button?
			if(getWizard().isShowConnectorDescriptorKindFilter() || getWizard().isShowConnectorDescriptorTextFilter()) {
				Composite filterContainer = new Composite(header, SWT.NULL);
				GridDataFactory.fillDefaults().grab(true, false).applyTo(filterContainer);
				int numColumns = 1; // 1 for label
				if(getWizard().isShowConnectorDescriptorKindFilter()) {
					numColumns += PluginDescriptorKind.values().length;
				}
				if(getWizard().isShowConnectorDescriptorTextFilter()) {
					++numColumns;
				}
				GridLayoutFactory.fillDefaults().numColumns(numColumns).applyTo(filterContainer);
				Label label = new Label(filterContainer, SWT.NULL);
				label.setText("Filter");
				if(getWizard().isShowConnectorDescriptorTextFilter()) {
					Composite textFilterContainer;
					boolean nativeSearch = useNativeSearchField(header);
					if(nativeSearch) {
						textFilterContainer = new Composite(filterContainer, SWT.NULL);
					} else {
						textFilterContainer = new Composite(filterContainer, SWT.BORDER);
						textFilterContainer.setBackground(header.getDisplay().getSystemColor(SWT.COLOR_LIST_BACKGROUND));
					}
					GridDataFactory.fillDefaults().grab(true, false).applyTo(textFilterContainer);
					GridLayoutFactory.fillDefaults().numColumns(2).applyTo(textFilterContainer);
					if(nativeSearch) {
						filterText = new Text(textFilterContainer, SWT.SINGLE | SWT.BORDER | SWT.SEARCH | SWT.ICON_CANCEL);
					} else {
						filterText = new Text(textFilterContainer, SWT.SINGLE);
					}
					filterText.addModifyListener(new ModifyListener() {

						@Override
						public void modifyText(ModifyEvent e) {

							filterTextChanged();
						}
					});
					if(nativeSearch) {
						filterText.addSelectionListener(new SelectionAdapter() {

							@Override
							public void widgetDefaultSelected(SelectionEvent e) {

								if(e.detail == SWT.ICON_CANCEL) {
									clearFilterText();
								}
							}
						});
						GridDataFactory.fillDefaults().grab(true, false).span(2, 1).applyTo(filterText);
					} else {
						GridDataFactory.fillDefaults().grab(true, false).applyTo(filterText);
						clearFilterTextControl = createClearFilterTextControl(textFilterContainer, filterText);
						clearFilterTextControl.setVisible(false);
					}
				}
				if(getWizard().isShowConnectorDescriptorKindFilter()) { // filter buttons
					for(final PluginDescriptorKind kind : PluginDescriptorKind.values()) {
						final Button checkbox = new Button(filterContainer, SWT.CHECK);
						checkbox.setSelection(getWizard().isVisible(kind));
						checkbox.setText(getFilterLabel(kind));
						checkbox.addSelectionListener(new SelectionListener() {

							@Override
							public void widgetSelected(SelectionEvent e) {

								boolean selection = checkbox.getSelection();
								getWizard().setVisibility(kind, selection);
								pluginDescriptorKindVisibilityUpdated();
							}

							@Override
							public void widgetDefaultSelected(SelectionEvent e) {

								widgetSelected(e);
							}
						});
					}
				}
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

	private static boolean useNativeSearchField(Composite composite) {

		if(useNativeSearchField == null) {
			useNativeSearchField = Boolean.FALSE;
			Text testText = null;
			try {
				testText = new Text(composite, SWT.SEARCH | SWT.ICON_CANCEL);
				useNativeSearchField = Boolean.valueOf((testText.getStyle() & SWT.ICON_CANCEL) != 0);
			} finally {
				if(testText != null) {
					testText.dispose();
				}
			}
		}
		return useNativeSearchField;
	}

	private void createRefreshJob() {

		refreshJob = new WorkbenchJob("filter") { //$NON-NLS-1$

			@Override
			public IStatus runInUIThread(IProgressMonitor monitor) {

				if(filterText.isDisposed()) {
					return Status.CANCEL_STATUS;
				}
				String text = filterText.getText();
				text = text.trim();
				if(!previousFilterText.equals(text)) {
					previousFilterText = text;
					filterPattern = createPattern(previousFilterText);
					if(clearFilterTextControl != null) {
						clearFilterTextControl.setVisible(filterPattern != null);
					}
					createBodyContents();
				}
				return Status.OK_STATUS;
			}
		};
		refreshJob.setSystem(true);
	}

	protected Pattern createPattern(String filterText) {

		if(filterText == null || filterText.length() == 0) {
			return null;
		}
		String regex = filterText;
		regex.replace("\\", "\\\\").replace("?", ".").replace("*", ".*?"); //$NON-NLS-1$//$NON-NLS-2$ //$NON-NLS-3$ //$NON-NLS-4$ //$NON-NLS-5$ //$NON-NLS-6$
		return Pattern.compile(regex, Pattern.CASE_INSENSITIVE | Pattern.DOTALL);
	}

	private Label createClearFilterTextControl(Composite filterContainer, final Text filterText) {

		final Image inactiveImage = ApplicationImageFactory.getInstance().getImage(IApplicationImage.IMAGE_DESELECTED_INACTIVE, IApplicationImageProvider.SIZE_16x16);
		final Image activeImage = ApplicationImageFactory.getInstance().getImage(IApplicationImage.IMAGE_EDIT_ACTIVE, IApplicationImageProvider.SIZE_16x16);
		final Image pressedImage = new Image(filterContainer.getDisplay(), activeImage, SWT.IMAGE_GRAY);
		final Label clearButton = new Label(filterContainer, SWT.NONE);
		clearButton.setLayoutData(new GridData(SWT.BEGINNING, SWT.CENTER, false, false));
		clearButton.setImage(inactiveImage);
		clearButton.setToolTipText("Clear");
		clearButton.setBackground(filterContainer.getDisplay().getSystemColor(SWT.COLOR_LIST_BACKGROUND));
		clearButton.addMouseListener(new MouseAdapter() {

			private MouseMoveListener fMoveListener;

			@Override
			public void mouseDown(MouseEvent e) {

				clearButton.setImage(pressedImage);
				fMoveListener = new MouseMoveListener() {

					private boolean fMouseInButton = true;

					@Override
					public void mouseMove(MouseEvent e) {

						boolean mouseInButton = isMouseInButton(e);
						if(mouseInButton != fMouseInButton) {
							fMouseInButton = mouseInButton;
							clearButton.setImage(mouseInButton ? pressedImage : inactiveImage);
						}
					}
				};
				clearButton.addMouseMoveListener(fMoveListener);
			}

			@Override
			public void mouseUp(MouseEvent e) {

				if(fMoveListener != null) {
					clearButton.removeMouseMoveListener(fMoveListener);
					fMoveListener = null;
					boolean mouseInButton = isMouseInButton(e);
					clearButton.setImage(mouseInButton ? activeImage : inactiveImage);
					if(mouseInButton) {
						clearFilterText();
						filterText.setFocus();
					}
				}
			}

			private boolean isMouseInButton(MouseEvent e) {

				Point buttonSize = clearButton.getSize();
				return 0 <= e.x && e.x < buttonSize.x && 0 <= e.y && e.y < buttonSize.y;
			}
		});
		clearButton.addMouseTrackListener(new MouseTrackListener() {

			@Override
			public void mouseEnter(MouseEvent e) {

				clearButton.setImage(activeImage);
			}

			@Override
			public void mouseExit(MouseEvent e) {

				clearButton.setImage(inactiveImage);
			}

			@Override
			public void mouseHover(MouseEvent e) {

			}
		});
		clearButton.addDisposeListener(new DisposeListener() {

			@Override
			public void widgetDisposed(DisposeEvent e) {

				inactiveImage.dispose();
				activeImage.dispose();
				pressedImage.dispose();
			}
		});
		clearButton.getAccessible().addAccessibleListener(new AccessibleAdapter() {

			@Override
			public void getName(AccessibleEvent e) {

				e.result = "NAME";
			}
		});
		clearButton.getAccessible().addAccessibleControlListener(new AccessibleControlAdapter() {

			@Override
			public void getRole(AccessibleControlEvent e) {

				e.detail = ACC.ROLE_PUSHBUTTON;
			}
		});
		return clearButton;
	}

	@Override
	public PluginDiscoveryWizard getWizard() {

		return (PluginDiscoveryWizard)super.getWizard();
	}

	private void clearFilterText() {

		filterText.setText(""); //$NON-NLS-1$
		filterTextChanged();
	}

	private void filterTextChanged() {

		refreshJob.cancel();
		refreshJob.schedule(200L);
	}

	private String getFilterLabel(PluginDescriptorKind kind) {

		switch(kind) {
			case CONVERTER:
				return "CONVERTER";
			default:
				throw new IllegalStateException(kind.name());
		}
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
		for(Resource resource : disposables) {
			resource.dispose();
		}
		clearDisposables();
		if(discovery != null) {
			discovery.dispose();
		}
	}

	private void clearDisposables() {

		disposables.clear();
		h1Font = null;
		h2Font = null;
		infoImage = null;
		handCursor = null;
		colorCategoryGradientStart = null;
		colorCategoryGradientEnd = null;
	}

	public void createBodyContents() {

		// remove any existing contents
		for(Control child : body.getChildren()) {
			child.dispose();
		}
		clearDisposables();
		initializeCursors();
		initializeImages();
		initializeFonts();
		initializeColors();
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

	private void initializeCursors() {

		if(handCursor == null) {
			handCursor = new Cursor(getShell().getDisplay(), SWT.CURSOR_HAND);
			disposables.add(handCursor);
		}
	}

	private void initializeImages() {

		infoImage = ApplicationImageFactory.getInstance().getImage(IApplicationImage.IMAGE_INFO, IApplicationImageProvider.SIZE_16x16);
	}

	private void initializeColors() {

		if(colorWhite == null) {
			ColorRegistry colorRegistry = JFaceResources.getColorRegistry();
			if(!colorRegistry.hasValueFor(COLOR_WHITE)) {
				colorRegistry.put(COLOR_WHITE, new RGB(255, 255, 255));
			}
			colorWhite = colorRegistry.get(COLOR_WHITE);
		}
		if(colorDisabled == null) {
			ColorRegistry colorRegistry = JFaceResources.getColorRegistry();
			if(!colorRegistry.hasValueFor(COLOR_DARK_GRAY)) {
				colorRegistry.put(COLOR_DARK_GRAY, new RGB(0x69, 0x69, 0x69));
			}
			colorDisabled = colorRegistry.get(COLOR_DARK_GRAY);
		}
		if(colorCategoryGradientStart == null) {
			ColorRegistry colorRegistry = JFaceResources.getColorRegistry();
			if(!colorRegistry.hasValueFor(COLOR_CATEGORY_GRADIENT_START)) {
				colorRegistry.put(COLOR_CATEGORY_GRADIENT_START, new RGB(240, 240, 240));
			}
			colorCategoryGradientStart = colorRegistry.get(COLOR_CATEGORY_GRADIENT_START);
		}
		if(colorCategoryGradientEnd == null) {
			ColorRegistry colorRegistry = JFaceResources.getColorRegistry();
			if(!colorRegistry.hasValueFor(COLOR_CATEGORY_GRADIENT_END)) {
				colorRegistry.put(COLOR_CATEGORY_GRADIENT_END, new RGB(220, 220, 220));
			}
			colorCategoryGradientEnd = colorRegistry.get(COLOR_CATEGORY_GRADIENT_END);
		}
	}

	private void initializeFonts() {

		// create a level-2 heading font
		if(h2Font == null) {
			Font baseFont = JFaceResources.getDialogFont();
			FontData[] fontData = baseFont.getFontData();
			for(FontData data : fontData) {
				data.setStyle(data.getStyle() | SWT.BOLD);
				data.height = data.height * 1.25f;
			}
			h2Font = new Font(Display.getCurrent(), fontData);
			disposables.add(h2Font);
		}
		// create a level-1 heading font
		if(h1Font == null) {
			Font baseFont = JFaceResources.getDialogFont();
			FontData[] fontData = baseFont.getFontData();
			for(FontData data : fontData) {
				data.setStyle(data.getStyle() | SWT.BOLD);
				data.height = data.height * 1.35f;
			}
			h1Font = new Font(Display.getCurrent(), fontData);
			disposables.add(h1Font);
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
		private Image warningIconImage;

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
			checkbox.setData("pluginId", plugin.getInstallableUnits()); //$NON-NLS-1$
			configureLook(checkbox, background);
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
				iconImage = computeIconImage(plugin.getSource(), plugin.getIcon(), 32, false);
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

					boolean selected = !checkbox.getSelection();
					if(maybeModifySelection(selected)) {
						checkbox.setSelection(selected);
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

			boolean enabled = true;
			checkbox.setEnabled(enabled);
			nameLabel.setEnabled(enabled);
			providerLabel.setEnabled(enabled);
			description.setEnabled(enabled);
			Color foreground;
			if(enabled) {
				foreground = pluginContainer.getForeground();
			} else {
				foreground = colorDisabled;
			}
			nameLabel.setForeground(foreground);
			description.setForeground(foreground);
			if(iconImage != null) {
				boolean unavailable = !enabled;
				if(unavailable) {
					if(warningIconImage == null) {
						warningIconImage = new DecorationOverlayIcon(iconImage, ApplicationImageFactory.getInstance().getImageDescriptor(IApplicationImage.IMAGE_WARN, IApplicationImageProvider.SIZE_16x16), IDecoration.TOP_LEFT).createImage();
						disposables.add(warningIconImage);
					}
					iconLabel.setImage(warningIconImage);
				} else if(warningIconImage != null) {
					iconLabel.setImage(iconImage);
				}
			}
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
				link.addListener(SWT.Selection, new Listener() {

					@Override
					public void handleEvent(Event event) {

						clearFilterText();
						filterText.setFocus();
					}
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
					final GradientCanvas categoryHeaderContainer = new GradientCanvas(container, SWT.NONE);
					categoryHeaderContainer.setSeparatorVisible(true);
					categoryHeaderContainer.setSeparatorAlignment(SWT.TOP);
					categoryHeaderContainer.setBackgroundGradient(new Color[]{colorCategoryGradientStart, colorCategoryGradientEnd}, new int[]{100}, true);
					categoryHeaderContainer.putColor(IFormColors.H_BOTTOM_KEYLINE1, colorCategoryGradientStart);
					categoryHeaderContainer.putColor(IFormColors.H_BOTTOM_KEYLINE2, colorCategoryGradientEnd);
					GridDataFactory.fillDefaults().span(2, 1).applyTo(categoryHeaderContainer);
					GridLayoutFactory.fillDefaults().numColumns(3).margins(5, 5).equalWidth(false).applyTo(categoryHeaderContainer);
					Label iconLabel = new Label(categoryHeaderContainer, SWT.NULL);
					if(category.getIcon() != null) {
						Image image = computeIconImage(category.getSource(), category.getIcon(), 48, true);
						if(image != null) {
							iconLabel.setImage(image);
						}
					}
					iconLabel.setBackground(null);
					GridDataFactory.swtDefaults().align(SWT.CENTER, SWT.BEGINNING).span(1, 2).applyTo(iconLabel);
					Label nameLabel = new Label(categoryHeaderContainer, SWT.NULL);
					nameLabel.setFont(h1Font);
					nameLabel.setText(category.getName());
					nameLabel.setBackground(null);
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
					description.setBackground(null);
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
		Listener listener = new Listener() {

			@Override
			public void handleEvent(Event event) {

				switch(event.type) {
					case SWT.Dispose:
					case SWT.MouseWheel:
						toolTip.hide();
						break;
				}
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
		Listener exitListener = new Listener() {

			@Override
			public void handleEvent(Event event) {

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

	@SuppressWarnings("unlikely-arg-type")
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
		if(installedFeatures != null && installedFeatures.contains(descriptor.getInstallableUnits())) {
			// always filter installed features per bug 275777
			return true;
		}
		if(filterPattern != null) {
			if(!(filterMatches(descriptor.getName()) || filterMatches(descriptor.getDescription()) || filterMatches(descriptor.getProvider()) || filterMatches(descriptor.getLicense()))) {
				return true;
			}
		}
		return false;
	}

	private boolean filterMatches(String text) {

		return text != null && filterPattern.matcher(text).find();
	}

	private Image computeIconImage(IDiscoverySource discoverySource, Icon icon, int dimension, boolean fallback) {

		String imagePath;
		switch(dimension) {
			case 64:
				imagePath = icon.getImage64();
				if(imagePath != null || !fallback) {
					break;
				}
			case 48:
				imagePath = icon.getImage48();
				if(imagePath != null || !fallback) {
					break;
				}
			case 32:
				imagePath = icon.getImage32();
				break;
			default:
				throw new IllegalArgumentException();
		}
		if(imagePath != null && imagePath.length() > 0) {
			URL resource = discoverySource.getResource(imagePath);
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
			final Dictionary<Object, Object> environment = getWizard().getEnvironment();
			boolean wasCancelled = false;
			try {
				getContainer().run(true, true, new IRunnableWithProgress() {

					@Override
					public void run(IProgressMonitor monitor) throws InvocationTargetException, InterruptedException {

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
						// retrieve discovery url from properties file and call remote discovery strategy
						URL discoveryFileUrl = FileLocator.find(Activator.getDefault().getBundle(), new Path(PluginDiscoveryWizardMainPage.DISCOVERY_PROPERTIES_FILE), null);
						if(discoveryFileUrl != null) {
							InputStream in = null;
							try {
								Properties props = new Properties();
								in = discoveryFileUrl.openStream();
								props.load(in);
								String discoveryUrl = props.getProperty(PluginDiscoveryWizardMainPage.URL_DISCOVERY_PROPERTY);
								RemoteBundleDiscoveryStrategy remoteDiscoveryStrategy = new RemoteBundleDiscoveryStrategy();
								remoteDiscoveryStrategy.setDiscoveryUrl(discoveryUrl);
								pluginDiscovery.getDiscoveryStrategies().add(remoteDiscoveryStrategy);
							} catch(IOException ie) {
								logger.warn(ie);
							} finally {
								if(in != null) {
									try {
										in.close();
									} catch(IOException e) {
										// ignore
									}
								}
							}
						}
						pluginDiscovery.setEnvironment(environment);
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
			Display.getCurrent().asyncExec(new Runnable() {

				@Override
				public void run() {

					maybeUpdateDiscovery();
				}
			});
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

	public List<PluginDescriptor> getInstallableConnectors() {

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

		return plugin.getOverview() != null && plugin.getOverview().getSummary() != null && plugin.getOverview().getSummary().length() > 0;
	}
}
