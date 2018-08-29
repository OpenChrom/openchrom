package net.openchrom.xxd.process.supplier.templates.ui;

import org.eclipse.chemclipse.support.ui.activator.AbstractActivatorUI;
import org.osgi.framework.BundleContext;

import net.openchrom.xxd.process.supplier.templates.preferences.PreferenceSupplier;

public class Activator extends AbstractActivatorUI {

	private static Activator plugin;

	public void start(BundleContext context) throws Exception {

		super.start(context);
		plugin = this;
		initializePreferenceStore(PreferenceSupplier.INSTANCE());
	}

	public void stop(BundleContext context) throws Exception {

		plugin = null;
		super.stop(context);
	}

	public static AbstractActivatorUI getDefault() {

		return plugin;
	}
}
