<?xml version="1.0" encoding="UTF-8"?>
<GAML version="1.20" name="absorb">
  <integrity algorithm="SHA1">41d606f75f625457044c36e2eec793c683a518c2</integrity>
  <parameter name="component_name" label="Component name" group="GAML Generation">GAMLIO</parameter>
  <parameter name="component_version" label="Component version" group="GAML Generation">9.1.3.6</parameter>
  <parameter name="converter_name" label="Converter name" group="Data Conversion">NIC_OMNC</parameter>
  <parameter name="converter_description" label="Converter description" group="Data Conversion">Thermo Nicolet Omnic</parameter>
  <parameter name="converter_version" label="Converter version" group="Data Conversion">9.0.0.1</parameter>
  <parameter name="input_file_type" label="Input file type" group="Data Conversion">IRDATA</parameter>
  <parameter name="conversion_date" label="Conversion date" group="Data Conversion">2010-09-16 11:03:06</parameter>
  <parameter name="input_file" label="Input file name" group="Data Conversion">D:\temp\GAMLORG\Thermo Nicolet OMNIC FTIR\rawdata\absorb.spa</parameter>
  <experiment name="Polystyrene measured as a film">
    <parameter name="FileID" label="File ID" group="File Header">Polystyrene measured as a film</parameter>
    <parameter name="time" label="Time Collected" group="File Header">-1222438508</parameter>
    <parameter name="exttime" label="Extended time (.01 sec)" group="File Header">94</parameter>
    <parameter name="BenchID" label="Bench ID" group="Bench Info">44</parameter>
    <parameter name="BenchID_type" label="Bench type" group="Bench Info">Protege 460</parameter>
    <parameter name="DetectorID" label="Detector ID" group="Bench Info">10</parameter>
    <parameter name="DetectorID_type" label="Detector type" group="Bench Info">DTGS KBr</parameter>
    <parameter name="BeamSplitID" label="Beam splitter ID" group="Bench Info">1</parameter>
    <parameter name="BeamSplitID_type" label="Beam splitter type" group="Bench Info">GE KBr</parameter>
    <parameter name="SourceID" label="Source ID" group="Bench Info">2</parameter>
    <parameter name="SourceID_type" label="Source type" group="Bench Info">IR Source</parameter>
    <parameter name="ADBits" label="Number of bits in digitizer" group="Bench Info">20</parameter>
    <parameter name="HPF" label="High pass filter (in Hz)" group="Bench Info">20.000000</parameter>
    <parameter name="LPF" label="Low pass filter (in Hz)" group="Bench Info">11000.000000</parameter>
    <parameter name="SwitchGainHi" label="Switched gain high setting" group="Bench Info">0</parameter>
    <parameter name="SwitchGainLo" label="Switched gain low setting" group="Bench Info">0</parameter>
    <parameter name="SwitchGainP1" label="Switched gain point setting #1" group="Bench Info">0</parameter>
    <parameter name="SwitchGainP2" label="Switched gain point setting #2" group="Bench Info">0</parameter>
    <parameter name="Gain" label="Detector gain setting" group="Bench Info">1.000000</parameter>
    <parameter name="Velocity" label="Moving mirror velocity (in cm/s)" group="Bench Info">0.632900</parameter>
    <parameter name="AccessoryID" label="Accessory ID" group="Bench Info">3473443</parameter>
    <parameter name="HistoryBit1" label="Phase Correction and/or Apodization" group="History Bits">True</parameter>
    <parameter name="HistoryBit18" label="Wavenumber-&gt;Wavelength" group="History Bits">True</parameter>
    <parameter name="HistoryBit19" label="Wavelength-&gt;Wavenumber" group="History Bits">True</parameter>
    <parameter name="History1" label="History entry 1" group="History">Collect Sample</parameter>
    <parameter name="History2" label="History entry 2" group="History"> User name: todd</parameter>
    <parameter name="History3" label="History entry 3" group="History"> Background collected on Mon May 12 09:12:24 1997</parameter>
    <parameter name="History4" label="History entry 4" group="History"> Final format:	Absorbance</parameter>
    <parameter name="History5" label="History entry 5" group="History"> Resolution:	 4.000 from 399.1989 to 3999.7031</parameter>
    <parameter name="History6" label="History entry 6" group="History"> Bench Serial Number:ADT9700090</parameter>
    <parameter name="History7" label="History entry 7" group="History"></parameter>
    <parameter name="History8" label="History entry 8" group="History">Straight Line on Mon May 12 09:13:46 1997</parameter>
    <parameter name="History9" label="History entry 9" group="History"> Data format:	Absorbance</parameter>
    <parameter name="History10" label="History entry 10" group="History"> From 2383.6162 to 2280.6526</parameter>
    <parameter name="History11" label="History entry 11" group="History"></parameter>
    <parameter name="History12" label="History entry 12" group="History"></parameter>
    <trace name="Sample" technique="IR">
      <parameter name="DataType_ID" label="Data type ID" group="Spectrum Header">3</parameter>
      <parameter name="DataType" label="Data type" group="Spectrum Header">Sample</parameter>
      <parameter name="NumPoints" label="Number of data points" group="Spectrum Header">1868</parameter>
      <parameter name="XUnits_ID" label="X axis unit ID" group="Spectrum Header">1</parameter>
      <parameter name="XUnits" label="X axis unit" group="Spectrum Header">Wavenumbers (cm-1)</parameter>
      <parameter name="YUnits_ID" label="Y axis unit ID" group="Spectrum Header">17</parameter>
      <parameter name="YUnits" label="Y axis unit" group="Spectrum Header">Absorbance</parameter>
      <parameter name="FirstX" label="First X" group="Spectrum Header">3999.703125</parameter>
      <parameter name="LastX" label="Last X" group="Spectrum Header">399.198944</parameter>
      <parameter name="Noise" label="Estimated noise level" group="Spectrum Header">0.000663</parameter>
      <parameter name="NumScanPts" label="Number of data points collected" group="Spectrum Header">8480</parameter>
      <parameter name="FinalPeakPos" label="Final location of interferogram centerburst peak" group="Spectrum Header">4096</parameter>
      <parameter name="NumScans" label="Number of scans" group="Spectrum Header">32</parameter>
      <parameter name="InterpPeak" label="Interpolated location of interferogram centerburst" group="Spectrum Header">4096.000000</parameter>
      <parameter name="NumTransformPts" label="Number of FFT points used" group="Spectrum Header">8192</parameter>
      <parameter name="NumPostPeakPts" label="Number of data points after centerburst" group="Spectrum Header">4096</parameter>
      <parameter name="BKNumScans" label="Number of background scans" group="Spectrum Header">64</parameter>
      <parameter name="BKGain" label="Background spectrum gain" group="Spectrum Header">1.000000</parameter>
      <parameter name="ApodID" label="Apodization ID" group="Spectrum Header">0</parameter>
      <parameter name="Apod" label="Apodization function" group="Spectrum Header">Happ-Genzel</parameter>
      <parameter name="ApodValue" label="Apodization value" group="Spectrum Header">0.000000</parameter>
      <parameter name="Duration" label="Length of collection (in hundreths of seconds)" group="Spectrum Header">3503</parameter>
      <parameter name="BurstPeakHeight" label="Height of interferogram centerburst peak (in Volts)" group="Spectrum Header">-4.542373</parameter>
      <parameter name="PhaseCorrID" label="Phase correction type ID" group="Spectrum Header">0</parameter>
      <parameter name="PhaseCorr" label="Phase correction function" group="Spectrum Header">Mertz</parameter>
      <parameter name="LaserFreq" label="Laser frequency" group="Spectrum Header">15798.250000</parameter>
      <parameter name="SSP" label="Sample spacing used by detector and digitizer" group="Spectrum Header">2.000000</parameter>
      <parameter name="ApertureRef" label="Aperture reference factor" group="Spectrum Header">100.000000</parameter>
      <parameter name="Aperture" label="IR beam aperture setting (in percent of max)" group="Spectrum Header">100.000000</parameter>
      <parameter name="StageX" label="X stage position" group="Spectrum Header">0</parameter>
      <parameter name="StageY" label="Y stage position" group="Spectrum Header">0</parameter>
      <parameter name="StageZ" label="Z stage position" group="Spectrum Header">0</parameter>
      <parameter name="Flags" label="Application dependent flags" group="Spectrum Header">32768</parameter>
      <parameter name="OldYUnits" label="Y axis unit before transform" group="Spectrum Header">UNKNOWN</parameter>
      <parameter name="OldType" label="Data type before transform" group="Spectrum Header">0</parameter>
      <parameter name="BadScans" label="Number of bad scans in sample collection" group="Spectrum Header">0</parameter>
      <parameter name="BKBadScans" label="Number of bad scans in background collection" group="Spectrum Header">0</parameter>
      <parameter name="CollectErrorMap" label="Bitmap of errors that occurred during sample collection" group="Spectrum Header">0x0</parameter>
      <parameter name="BKCollectErrorMap" label="Bitmap of errors that occurred during background collection" group="Spectrum Header">0x0</parameter>
      <Xdata label="Wavenumbers (cm-1)" units="WAVENUMBER" valueorder="EVEN">
        <values byteorder="INTEL" format="FLOAT32" numvalues="1868">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
Q32gx0M=
        </values>
        <Ydata label="Absorbance" units="ABSORBANCE">
          <values byteorder="INTEL" format="FLOAT32" numvalues="1868">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
PVWDFT0=
          </values>
        </Ydata>
      </Xdata>
    </trace>
  </experiment>
</GAML>
