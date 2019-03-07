package net.openchrom.nmr.processing.supplier.base.settings.support;

import org.ejml.simple.SimpleMatrix;

import net.openchrom.nmr.processing.supplier.base.settings.IcoShiftAlignmentSettings;
import net.openchrom.nmr.processing.supplier.base.settings.support.IcoShiftAlignmentUtilities.Interval;

public interface CalculateTargetFunction {

	double[] calculateTarget(SimpleMatrix experimentalDatasetsMatrix, Interval<Integer> interval, IcoShiftAlignmentSettings settings);
}