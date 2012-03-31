/*******************************************************************************
 * Copyright (c) 2012 Philip (eselmeister) Wenig.
 * 
 * All rights reserved.
 *******************************************************************************/
package net.openchrom.chromatogram.msd.converter.supplier.pdf.ui.notifier;

import net.openchrom.chromatogram.msd.model.core.support.IChromatogramSelection;
import net.openchrom.chromatogram.msd.model.notifier.IChromatogramSelectionUpdateNotifier;

/**
 * This class receives the update notifier event if a chromatogram has been loaded.<br/>
 * It enables to show the serial key dialog of the chromatogram converter.<br/>
 * Otherwise, the dialog would be only shown, if the preference of the converter is activated.
 * 
 * @author Philip (eselmeister) Wenig
 * 
 */
public class SelectionUpdateNotifier implements IChromatogramSelectionUpdateNotifier {

	@Override
	public void update(IChromatogramSelection chromatogramSelection, boolean forceReload) {

	}
}
