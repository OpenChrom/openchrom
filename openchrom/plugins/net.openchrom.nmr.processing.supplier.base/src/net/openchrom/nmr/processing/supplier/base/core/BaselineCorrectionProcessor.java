package net.openchrom.nmr.processing.supplier.base.core;

import org.apache.commons.math3.complex.Complex;
import org.eclipse.chemclipse.nmr.model.core.IScanNMR;
import org.eclipse.chemclipse.nmr.processor.core.AbstractScanProcessor;
import org.eclipse.chemclipse.nmr.processor.core.IScanProcessor;
import org.eclipse.chemclipse.nmr.processor.settings.IProcessorSettings;
import org.eclipse.chemclipse.processing.core.IProcessingInfo;
import org.eclipse.core.runtime.IProgressMonitor;

import net.openchrom.nmr.processing.supplier.base.settings.BaselineCorrectionSettings;

public class BaselineCorrectionProcessor extends AbstractScanProcessor implements IScanProcessor {

	public BaselineCorrectionProcessor() {

		super();
		// TODO Auto-generated constructor stub
	}

	@Override
	public IProcessingInfo process(final IScanNMR scanNMR, final IProcessorSettings processorSettings, final IProgressMonitor monitor) {

		final IProcessingInfo processingInfo = validate(scanNMR, processorSettings);
		if(!processingInfo.hasErrorMessages()) {
			final BaselineCorrectionSettings settings = (BaselineCorrectionSettings)processorSettings;
			final Complex[] baselineCorrectedData = perform(scanNMR.getPhaseCorrectedData(), settings);
			scanNMR.setBaselineCorrectedData(baselineCorrectedData);
			processingInfo.setProcessingResult(scanNMR);
		}
		return processingInfo;
	}

	private Complex[] perform(final Complex[] modifiedSignals, final BaselineCorrectionSettings settings) {

		// TODO Auto-generated method stub
		return null;
	}
}
