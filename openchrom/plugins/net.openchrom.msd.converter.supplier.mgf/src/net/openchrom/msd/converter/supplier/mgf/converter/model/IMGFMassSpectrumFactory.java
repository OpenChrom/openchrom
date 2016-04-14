package net.openchrom.msd.converter.supplier.mgf.converter.model;

public class IMGFMassSpectrumFactory implements IScanMSDFactory {

	@Override
	public MGFMassSpectrum build() {

		return new MGFMassSpectrum();
	}
}
