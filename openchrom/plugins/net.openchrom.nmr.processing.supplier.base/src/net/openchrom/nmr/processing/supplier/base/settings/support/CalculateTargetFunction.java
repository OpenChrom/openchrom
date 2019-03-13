package net.openchrom.nmr.processing.supplier.base.settings.support;

import org.ejml.simple.SimpleMatrix;

import net.openchrom.nmr.processing.supplier.base.settings.IcoShiftAlignmentSettings;

public interface CalculateTargetFunction {

	double[] calculateTarget(SimpleMatrix experimentalDatasetsMatrix, int[] interval, IcoShiftAlignmentSettings settings);
}