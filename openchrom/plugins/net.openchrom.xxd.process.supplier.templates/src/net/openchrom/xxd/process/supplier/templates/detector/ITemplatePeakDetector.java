/*******************************************************************************
 * Copyright (c) 2020 Lablicate GmbH.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors:
 * Christoph Läubrich - initial API and implementation
 *******************************************************************************/
package net.openchrom.xxd.process.supplier.templates.detector;

import java.util.Collection;
import java.util.Map;

import org.eclipse.chemclipse.model.core.IChromatogram;
import org.eclipse.chemclipse.model.core.IPeakModel;
import org.eclipse.chemclipse.model.core.PeakType;
import org.eclipse.chemclipse.processing.core.MessageConsumer;
import org.eclipse.chemclipse.processing.detector.Detector;
import org.eclipse.chemclipse.processing.detector.DetectorCategory;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.OperationCanceledException;

/**
 * 
 * a {@link ITemplatePeakDetector} detects for a given set of supported {@link PeakType}s possible {@link IPeakModel}s for the given {@link ITemplatePeak}s
 * 
 * @param <ConfigType>
 */
public interface ITemplatePeakDetector<ConfigType> extends Detector<ConfigType> {

	<TPT extends ITemplatePeak> Map<TPT, IPeakModel> detectPeaks(IChromatogram<?> chromatogram, Collection<TPT> templates, ConfigType configuration, MessageConsumer messages, IProgressMonitor progressMonitor) throws OperationCanceledException, InterruptedException;

	@Override
	default DetectorCategory getDetectorCategory() {

		return DetectorCategory.PEAK;
	}

	/**
	 * 
	 * @return the supported {@link PeakType}s of this detector
	 */
	PeakType[] getSupportedPeakTypes();

	/**
	 * 
	 * @param peakType
	 * @return <code>true</code> if this detector is meant to be the default for the given type, that is if nothing is configured, and there are different types available one should prefer this detector over one returning <code>false</code> here
	 */
	default boolean isDefaultFor(PeakType peakType) {

		return false;
	}
}
