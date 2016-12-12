/*******************************************************************************
 * Copyright (c) 2016 Walter Whitlock, Philip Wenig.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors:
 * Walter Whitlock - initial API and implementation
 * Philip Wenig - initial API and implementation
 *******************************************************************************/
package net.openchrom.msd.process.supplier.cms.core;

import java.util.Arrays;

import org.eclipse.chemclipse.logging.core.Logger;
import org.eclipse.chemclipse.msd.model.core.IIon;
import org.eclipse.chemclipse.msd.model.core.IMassSpectra;
import org.eclipse.chemclipse.msd.model.core.IRegularLibraryMassSpectrum;
import org.eclipse.chemclipse.msd.model.core.IScanMSD;
import org.eclipse.chemclipse.msd.model.implementation.MassSpectra;
import org.eclipse.core.runtime.IProgressMonitor;
import org.ejml.data.DenseMatrix64F;
import org.ejml.factory.LinearSolverFactory;
import org.ejml.interfaces.decomposition.DecompositionInterface;
import org.ejml.interfaces.decomposition.QRDecomposition;
import org.ejml.interfaces.decomposition.QRPDecomposition;
import org.ejml.interfaces.linsol.LinearSolver;
import org.ejml.ops.CommonOps;
import org.ejml.ops.SpecializedOps;

import net.openchrom.msd.converter.supplier.cms.model.CalibratedVendorMassSpectrum;
import net.openchrom.msd.converter.supplier.cms.model.ICalibratedVendorMassSpectrum;
import net.openchrom.msd.converter.supplier.cms.model.IMsdPeakMeasurement;
import net.openchrom.msd.converter.supplier.cms.model.MsdPeakMeasurement;

public class MassSpectraDecomposition {
	private static final Logger logger = Logger.getLogger(MassSpectraDecomposition.class);
	private DenseMatrix64F y = new DenseMatrix64F(1,1); //unknown mass spectrum measurements matrix
	private DenseMatrix64F x = new DenseMatrix64F(1,1); //matrix containing computed component fractions
	private DenseMatrix64F A = new DenseMatrix64F(1,1); //mass spectrum library components matrix
	private DenseMatrix64F P = new DenseMatrix64F(1,1); //error weight matrix
	private DenseMatrix64F wtA = new DenseMatrix64F(1,1); //error weighted A matrix used and possibly modified by linear solver
	private DenseMatrix64F wty = new DenseMatrix64F(1,1); //error weighted y matrix used by linear solver and to compute weighted sum of squares error
	private DenseMatrix64F ynew = new DenseMatrix64F(1,1); //calculated y after solving for x
	private DenseMatrix64F yResid = new DenseMatrix64F(1,1); //(ynew-y)
	private DenseMatrix64F wtyerr = new DenseMatrix64F(1,1); //P*yerr
	private double ssError; //sum of squares error
	private double wtssError; //weighted sum of squares error
	private boolean solverRetVal;
	ICalibratedVendorMassSpectrum scanResidual; //residual mass spectrum after subtracting calculated ion signals	
    
	private static final String MS_IDENTIFIER_ID = "org.eclipse.chemclipse.chromatogram.msd.identifier.supplier.file.massSpectrum";

	public void decompose(IMassSpectra scanSpectra, 
			IMassSpectra libMassSpectra, 
			IProgressMonitor monitor) throws InvalidScanException {
		
		// parameter scanSpectra has all the scans we wish to decompose, 1 by 1, into components
		// parameter libMassSpectra has the set of library component cracking patterns we want to fit to

		// generates a list of ions from the unknown mass spectrum (scanIons) having mass ~= at least one library component ion mass
		// and then generates a new, smaller, list of library ions (usedLibIons) having mass ~= at least one ion in the unknown mass spectrum
		double massTol = 0.2;
		IMassSpectra residualSpectra = new MassSpectra();
		residualSpectra.setName(scanSpectra.getName());
		
		for(IScanMSD scan : scanSpectra.getList()) {
			// iterate over the unknown scan spectra

			// replace with a noisy spectrum
			try {
				IScanMSD newscan = ((ICalibratedVendorMassSpectrum)scan).makeNoisyCopy((long)22345, 0.0);
				scan = newscan;
			} catch(CloneNotSupportedException e) {
				logger.warn(e);
			}

			//scan.scale();
			
			Dataset fitDataset = new Dataset();
			double libMatrixQuality;
			
			for(IScanMSD libSpectrum : libMassSpectra.getList()) {
				// first (re)read the library and save a reference to each library component
				if(libSpectrum instanceof IRegularLibraryMassSpectrum) {
					IRegularLibraryMassSpectrum libraryMassSpectrum = (IRegularLibraryMassSpectrum)libSpectrum;
					int componentSequence;
					try {
						componentSequence = fitDataset.addNewComponent(libraryMassSpectrum);
					} catch(DuplicateCompNameException e1) {
						logger.warn(e1);
						continue; //for
					}
					System.out.println("LIB: libName: " + libMassSpectra.getName()
					+ ", Component Name: " + libraryMassSpectrum.getLibraryInformation().getName()
					+ ", Component Index: " + componentSequence
					+ ", #ions: " + libraryMassSpectrum.getNumberOfIons());
					
					System.out.print("\t");
					for (IIon libion: libSpectrum.getIons()) {
						try {
							fitDataset.addLibIon(libion.getIon(), libion.getAbundance(), componentSequence);
						} catch(InvalidComponentIndexException e) {
							logger.warn(e);
						}
						System.out.print("(" + libion.getIon() + ", " + libion.getAbundance() + ")");
					} //for
					System.out.println();
				} //if
			} //for
			
			// then read ions present in the unknown scan
			System.out.println("SCAN: \"" + ((CalibratedVendorMassSpectrum)scan).getScanName() + "\"");
			System.out.print("\t");
			for (IMsdPeakMeasurement sigpeak: ((ICalibratedVendorMassSpectrum)scan).getPeaks()) {
				System.out.print("(" + sigpeak.getMZ() + ", " + sigpeak.getSignal() + ")");
				fitDataset.addScanIon(sigpeak.getMZ(), sigpeak.getSignal(), (ICalibratedVendorMassSpectrum)scan);
			} //for
			System.out.println();
			
			// then match ions found in the unknown scan with ions found in the library components
			// ion mass must be within +/-massTol for ions to match
			// NOTE: should use relative tolerance instead of absolute, but absolute is easier testing
			try {
				fitDataset.matchIons(massTol);
			}
			catch (NoLibIonsException exc) {
				System.out.println(exc);
				throw new InvalidScanException(scan);
			}
			catch (NoScanIonsException exc) {
				System.out.println(exc);
				throw new InvalidScanException(scan);
			}
			
			try {
				System.out.println("\t# total components in library " + fitDataset.getCompCount());
				System.out.println("\t# components in library that match scan ions = " + fitDataset.getUsedCompCount());
				System.out.println("\t# total ions in scan = " + fitDataset.getIonsInScan());
				System.out.println("\t# ions in scan that match library ions = " + fitDataset.getUsedScanIonCount());
				System.out.println("\t# ions in library that match scan ions = " + fitDataset.getUsedLibIonCount());
			
				//if (fitDataset.getCompCount() > fitDataset.getUsedScanIonCount()) {
				//	System.out.println("\tcomponents in library > matching ions in scan, it is not possible to find a solution");
				//	continue; //for
				//} //if
				// set up matrix A, P, stA, and vectors y & wty
				A.reshape(fitDataset.getUsedScanIonCount(), fitDataset.getUsedCompCount()); // holds coefficients from library
				P.reshape(fitDataset.getUsedScanIonCount(), fitDataset.getUsedScanIonCount()); // used to apply weights to sum of squares error
				wtA.reshape(fitDataset.getUsedScanIonCount(), fitDataset.getUsedCompCount()); // weighted library coefficients
				y.reshape(fitDataset.getUsedScanIonCount(), 1); //measurement vector
				wty.reshape(fitDataset.getUsedScanIonCount(), 1); //weighted measurement vector
				x.reshape(fitDataset.getUsedCompCount(), 1); //unknown vector
				ynew.reshape(fitDataset.getUsedScanIonCount(), 1); //unknown vector
				yResid.reshape(fitDataset.getUsedScanIonCount(), 1); //residual vector
				wtyerr.reshape(fitDataset.getUsedScanIonCount(), 1); //weighted error vector
			
				for (LibIon i : fitDataset.getLibIons()) {
					A.set(i.ionRowIndex, i.componentRef.componentIndex, i.ionAbundance);
				}
				for (ScanIon i : fitDataset.getScanIons()) {
					y.set(i.ionRowIndex, 0, i.ionAbundance);
					P.set(i.ionRowIndex, i.ionRowIndex,
						//1); // for testing without error weights
						java.lang.StrictMath.sqrt(1.0/java.lang.StrictMath.abs(i.ionAbundance)));
				}//for
			
					CommonOps.mult(P, A, wtA);
					CommonOps.mult(P, y, wty);
			
				// solve the linear system Ax=y for x using a solver that tolerates a singular A matrix
				LinearSolver<DenseMatrix64F> solver = LinearSolverFactory.leastSquaresQrPivot(false, false);
				QRPDecomposition<DenseMatrix64F> decomp = solver.getDecomposition();
				//decomp.setSingularThreshold(1e-2); // used to test if a higher threshold for detecting a singular condition is useful
				solverRetVal = solver.setA(wtA);

				int pivots[] = decomp.getPivots();
				int rank = decomp.getRank();
				System.out.println("Solver = " + solver);
				System.out.println("Decomposition = " + decomp);
				if (fitDataset.getUsedCompCount() > rank) {
					int ignored[] = Arrays.copyOfRange(pivots, rank, pivots.length);
					Arrays.sort(ignored);
					System.out.println("Final rank = " + rank + ", Initial rank = " + fitDataset.getUsedCompCount());
					System.out.println("\tThe following components were set to 0.0 because they are not sufficiently different from other library components to resolve");
					for (int i=0; i<ignored.length; i++) {
						System.out.println("\t" + fitDataset.getLibCompName(ignored[i]));
					}
				}
				if (!solverRetVal) {
					throw new LibIonsMatrixSingularException("Solver.setA() returned FALSE, library matrix is singular");
				}
				if (true) {
					libMatrixQuality = solver.quality();
					if ( 0 > libMatrixQuality) {
						throw new LibIonsMatrixSingularException("Solver.quality() value of " + 
							libMatrixQuality + " indicates library matrix is nearly singular");
					}
					System.out.print("Solver.quality() = " + libMatrixQuality);
					if (1e-8 > libMatrixQuality) System.out.print(", Library matrix is nearly singular");
					System.out.println("");
				}
				// solve
				solver.solve(wty, x);
			
				// display the result
				System.out.println("SOLVED");
				System.out.print("\t");
				for(int ii = 0; ii < x.numRows; ii++) {
					if (0 != ii) System.out.print(", ");
					System.out.print(fitDataset.getLibCompName(ii) +  ": x[" + ii + "]=" + x.get(ii));
				}
				System.out.println("");
				// compute sum of squares error and residuals vector
				CommonOps.mult(A, x, ynew);
				CommonOps.subtract(ynew, y, yResid);
				ssError = SpecializedOps.elementSumSq(yResid);
				CommonOps.mult(P, yResid, wtyerr);
				wtssError = SpecializedOps.elementSumSq(wtyerr);
				System.out.println("\tSS error = " + ssError + ", weighted SS error = " + wtssError);
			} //try
			
			catch (InvalidGetCompCountException exc) {
				System.out.println(exc);
				throw new InvalidScanException(scan);
			}
			catch (LibIonsMatrixSingularException exc) {
				System.out.println(exc);
				System.out.println("\tTwo or more library components are nearly identical, it is not possible to find a solution");
				continue; //for
			}
			catch (InvalidScanIonCountException exc) {
				System.out.println(exc);
				continue; //for
			}
			catch (InvalidLibIonCountException exc) {
				System.out.println(exc);
				continue; //for
			}
			
			// generate a residuals mass spectrum by replacing abundance values for ions that were fit to library components with residuals
			try {
				scanResidual = ((ICalibratedVendorMassSpectrum)scan).makeDeepCopy();
			} catch(CloneNotSupportedException e) {
				logger.warn(e);
			}
			// Overwrite the peak.signal values for residuals
			float datasetIonSignal, residIonSignal, newSignal;
			double datasetIonMass, residIonMass;
			ScanIon datasetIon;
			IMsdPeakMeasurement scanPeak;
			int residIonIndex;
			try {
				for (int irow=0; irow<yResid.numRows; irow++) {
					newSignal = (float)yResid.get(irow, 0);
					datasetIon = fitDataset.scanions[irow];
					datasetIonMass = datasetIon.ionMass;
					datasetIonSignal = (float)datasetIon.ionAbundance;
					residIonIndex = fitDataset.getScanPeakIndex(irow);
					scanPeak = scanResidual.getPeak(residIonIndex);
					residIonMass = scanPeak.getMZ();
					residIonSignal = scanPeak.getSignal();
					// test to verify that the correct peak measurement will be replaced
					assert((datasetIonMass == residIonMass) && (datasetIonSignal == residIonSignal));
					scanResidual.getPeak(residIonIndex).setSignal(newSignal);
				}
			} catch(InvalidScanIonCountException e) {
				logger.warn(e);
			}
			scanResidual.updateSignalLimits();
			
			residualSpectra.addMassSpectrum(scanResidual);
			
			for(IScanMSD scan1 : residualSpectra.getList()) {
				((ICalibratedVendorMassSpectrum)scan1).updateIons();
			}

			System.out.println();
			
			// allow user to add and/or delete components from selected library components list
			// then repeat decomposition using new library component selection
			
			/*
			 * Use the scan identifier:
			 * org.eclipse.chemclipse.chromatogram.msd.identifier.supplier.file
			 * Works only properly if a library has been set.
			 */
			//MassSpectrumIdentifier.identify(scan, MS_IDENTIFIER_ID, monitor);
			//for(IMassSpectrumTarget massSpectrumTarget : scan.getTargets()) {
			//	System.out.println("\t" + massSpectrumTarget.getLibraryInformation().getName());
			//}
		}
	}
}
