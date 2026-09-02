/*******************************************************************************
 * Copyright (c) 2026 Lablicate GmbH.
 *
 * This program and the accompanying materials are made
 * available under the terms of the Eclipse Public License 2.0
 * which is available at https://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
 * 
 * Contributors:
 * Matthias Mailänder - initial API and implementation
 *******************************************************************************/
package net.openchrom.msd.converter.supplier.muf.converter;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.File;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.TimeZone;

import org.eclipse.chemclipse.msd.model.core.IMassSpectra;
import org.eclipse.chemclipse.msd.model.core.IStandaloneMassSpectrum;
import org.eclipse.chemclipse.processing.core.IProcessingInfo;
import org.eclipse.chemclipse.processing.core.IProcessingMessage;
import org.eclipse.core.runtime.NullProgressMonitor;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.TestInstance.Lifecycle;
import org.junit.jupiter.api.TestMethodOrder;

import net.openchrom.msd.converter.supplier.muf.converter.model.ICultivationConditions;
import net.openchrom.msd.converter.supplier.muf.converter.model.ISpectraMultiFileMassSpectra;
import net.openchrom.msd.converter.supplier.muf.converter.model.ITaxonomicInformation;

@TestInstance(Lifecycle.PER_CLASS)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class MassSpectrumImportConverter_RKI_Ring_Trial_ITest {

	private IMassSpectra massSpectra;
	private File file;

	@Test
	@Order(1)
	public void testImport() {

		file = new File("data/RKI-ring-trial-spectra.muf");
		MassSpectrumImportConverter importConverter = new MassSpectrumImportConverter();
		IProcessingInfo<IMassSpectra> processingInfo = importConverter.convert(file, new NullProgressMonitor());
		for(IProcessingMessage message : processingInfo.getMessages()) {
			System.out.println(message.getMessage());
		}
		massSpectra = processingInfo.getProcessingResult();
		assertNotNull(massSpectra);
	}

	@Test
	public void testMatch() {

		MagicNumberMatcher magicNumberMatcher = new MagicNumberMatcher();
		assertTrue(magicNumberMatcher.checkFileFormat(file));
	}

	@Test
	public void testBasicValidation() {

		assertNotNull(massSpectra);
		assertEquals("2013_06_13_Maren_Stämmler_0001", massSpectra.getName());
	}

	@Test
	public void testOriginalSpectrum() {

		IStandaloneMassSpectrum originalMassSpectrum = (IStandaloneMassSpectrum)massSpectra.getMassSpectrum(1);

		GregorianCalendar calendar = new GregorianCalendar(2013, Calendar.JUNE, 13, 13, 52, 45);
		calendar.set(Calendar.MILLISECOND, 62);
		calendar.setTimeZone(TimeZone.getTimeZone("Europe/Berlin"));
		assertEquals(calendar.getTime(), originalMassSpectrum.getDate());

		assertEquals(33000, originalMassSpectrum.getNumberOfIons());
		assertEquals(20376.65234375, originalMassSpectrum.getHighestIon().getIon());
		assertEquals(15100, originalMassSpectrum.getHighestAbundance().getAbundance());
	}

	@Test
	public void testProcessedSpectrum() {

		IStandaloneMassSpectrum originalMassSpectrum = (IStandaloneMassSpectrum)massSpectra.getMassSpectrum(2);
		assertEquals(33000, originalMassSpectrum.getNumberOfIons());
		assertEquals(20376.65234375, originalMassSpectrum.getHighestIon().getIon());
		assertEquals(26254.029296875, originalMassSpectrum.getHighestAbundance().getAbundance());
	}

	@Test
	public void testTaxonomy() {

		ITaxonomicInformation taxonomicInformation = (ITaxonomicInformation)massSpectra;
		assertEquals("RKI MALDI sample 01,", taxonomicInformation.getGenus());
		assertEquals("QUANDHIP EQAE ring trial,", taxonomicInformation.getSpecies());
		assertEquals("strains provided by RKI | ZBS 2", taxonomicInformation.getStrain());
		assertEquals(28450, taxonomicInformation.getTaxonmicIdentifierNCBI());
		assertEquals(28450, taxonomicInformation.getUnmodifiedTaxonmicIdentifierNCBI());
	}

	@Test
	public void testCultivation() {

		ICultivationConditions cultivationConditions = (ICultivationConditions)massSpectra;
		assertEquals("Optimal growth time between 24 - 72h", cultivationConditions.getGrowthTime());
		assertEquals("37°C", cultivationConditions.getTemperature());
		assertEquals("Optimal aerobic or microaerophilic conditions", cultivationConditions.getAtmosphere());
		assertEquals("Columbia blood agar (Oxoid), 2nd passage on TSA or Caso agar, harvested by the 2nd passage", cultivationConditions.getMedium());
		assertEquals(false, cultivationConditions.isSporeFormer());
	}

	@Test
	public void testMetadata() {

		ISpectraMultiFileMassSpectra spectraMultiFile = (ISpectraMultiFileMassSpectra)massSpectra;
		assertEquals("Measurement 01", spectraMultiFile.getType());
		assertEquals("Pellet produced by centrifugation (1 x 5 Min, 15,000 rpm) of a 500 mkL cell suspension, gamma ray irradiated (30 kGy)", spectraMultiFile.getSampleConcentration());
		assertEquals("Sample mixed with 20 mkL 100§ TFA, final TFA conc. approx. 80 perc.; approx. 30 Min treatment time; diluted 1:10 (vol); mixed with 1:1 HCCA|TA2(A)", spectraMultiFile.getSampleTreatment());
		assertEquals("Burkholderia pseudomallei A101-10: microbial preparation by ZBS 2| preparation for MALDI-ToF MS: M. Stämmler", spectraMultiFile.getExtraInformation());
		assertEquals("Laser power: 23§ 600; laser attenuator offset|range: 52§|20§; Isset: Autoflex20130611.isset; laser defocussed 6x", spectraMultiFile.getLaserParameters());
		assertEquals("linear calibration using Escherichia coli DSM 3871", spectraMultiFile.getCalibrationInformation());
		assertEquals("D:\\Methods\\flexControlMethods\\MaierMethods\\ToM_200ns_20130611.par", spectraMultiFile.getMeasurementMethod());
		assertEquals("RKI ZBS 2| ZBS 6", spectraMultiFile.getCustomerInformation());
		assertEquals("/home/lasch/MATLAB/microbe test spectra/ring trial RKI spectra/Sample_01/0_H3/1/1SLin", spectraMultiFile.getSpectrumPath());
		assertEquals(0, spectraMultiFile.getClassAssignment());
		assertEquals("P- -############################## e-N-                               a-o-123456789111111111122222222223 k-.-,,,,,,,,,012345678901234567890  - -         ,,,,,,,,,,,,,,,,,,,,, t-|-ppppppppp                      a- -:::::::::ppppppppppppppppppppp b-P-         ::::::::::::::::::::: l-o-334444445                      e-s-270488891556666777778888999991  -i-664004589272455135890249566790 g-t-325993644492825602689341718164 e-i-.........224870884252066695398 n-o-007854249....................6 e-n-51188607454764644762460271680. r- -387254035075385475580236553532 a-[-854,67666321873954134882774,71 t-m-,,, ,,,,,5631,98864,,,25361 25 e-/-   h     ,,,, ,,,,,   ,,,,,h,5 d-z-hhh:hhhhh    h     hhh     : ,  -]-::: :::::hhhh:hhhhh:::hhhhh h  f- -   3     :::: :::::   :::::2:h r-|-151812212    1     559     9 : o- -81203658725411423216448112651  m-H-799856479361150796037363008462  -e-349.17546761028442916.59176.33 p-i-...30....9025054276..5.7777138 r-g-9984.7014........0.7194..0.9.5 o-h-10313378801881531.8830942.853. c-t-027848365164710171061959261802 e- -425,57486175498,67281,49665,42 s-[-,,, 2,,,,6433,, ,41,, ,8163 72 s-c-   w,    ,,,,  w ,,  w ,,,,w,1 e-t-www: wwww    ww:w  ww:w    : , d-s-::: w::::wwww:: :ww:: :wwww w   -]-   2:    ::::  1 ::  6 ::::1:w s- -1385 1111    7282  33.5    9 : p-|-24..97728132777.617763.9614.1  e- -..761....687...4.7...58..36911 c-W-6955.9168...46585639873489.0.6 t-e-29770742401751285.8491115.220. r-i-482423660230024899923,,5396900 u-g-88,,1,573738487,,8325  2239,36 m-t-,,  2 ,,,849,,,  6,,,bb,,55 89  -h-  bb,b   ,,,   bb1   ::  1,b,6  -i-bb:: :bbb   bbb::,bbb  bb, : ,  -n-::  b :::bbb:::   :::87:: b b   -g-  11:1   :::   88b   08  b:7:b  - -1111 0111   99998:8885877: 0 :  -[-11311800011955574 321..51 756   -r-641206876008742..8556444270.96  -e-44..8.572510...046...14..16126  -l-..5483...97.95426595632070.7.9  -.-7871.0405..951657.1561882.780.  - -5161468421184958,2593,,3030381  -u-77287,4253029,5, 41,5  2141,03  -n-43,,5 885366, , f4, ,ff,,94 51  -i-,,  4f,,,,6, f f:1 f ::  4,f,2  -t-  ff,:    , f:f: ,f:f  ff, : ,  -s-ff::  ffff f: : 1 : :11:: f f   -]-::  f1::::f: 1 13f 1 55  f:1:f  - -  11:4    : 1213.:151..12: 6 :  -|-9901 .1111 14.3.3 5.48142 1.2   - -....15231112.1.051.8.32..15321  -B-487100....1.09165309494356.6.7  -a-7383.95423.462962.5927157.197.  -s-5139612268447854,21,1,,1913127  -e-,3636,9236425,,, 98 5  ,599,59  -l- ,,,0 457559,   r3,r,rr ,41 ,2  -i-r   5r,,,,2, rrr:8 : ::r 6,r 8  -n-:rrr,:    , r::: ,r r  :r, :r,  -e- :::  rrrr r:   5 :5:55 : r :   - -3   r3::::r: 5454r 0 156 r:5 r  -c-4333:3    : 43947:5259824: 94:  -o-4879 33344 56568. 2.6..12 633   -r-.2554.8766501...2522164.453.85  -r-3...58711000.34636.4.242.994.8  - -861812....6.254166391 9913.269  -[-4578.35324.245856.783 173.758.  -c-4825148511169948 04 3  1497913  -t- 5665 5213063    31 4   277 65  -s-    7 79663      5       46  2  -]-    5     9      3       6   5  - -                                - -                                -|-                                - -                                -F-                                -W-                                -H-                                -M-                                - -                                -[-                                -m-                                -z-                                -]-                                - -                                - -                                - -                                - -                                -|-                                - -                                - -                                -R-                                -e-                                -s-                                -o-                                -l-                                -v-                                -i-                                -n-                                -g-                                - -                                -p-                                -o-                                -w-                                -e-                                -r-                                - -                                - -                                                                                                                                                                                                                                                                                    ", spectraMultiFile.getPeakTableInformation());
	}
}
