<?xml version="1.0" encoding="UTF-8"?>
<GAML version="1.20" name="3DLIVE">
  <integrity algorithm="SHA1">33bca32b653a2dcce6dbb794ecb224ecd6232aa3</integrity>
  <parameter name="component_name" label="Component name" group="GAML Generation">GAMLIO</parameter>
  <parameter name="component_version" label="Component version" group="GAML Generation">9.1.3.6</parameter>
  <parameter name="converter_name" label="Converter name" group="Data Conversion">SLM_BWM2</parameter>
  <parameter name="converter_description" label="Converter description" group="Data Conversion">Thermo Spectronic SLM-Aminco Bowman Series 2</parameter>
  <parameter name="converter_version" label="Converter version" group="Data Conversion">6.1.0.1</parameter>
  <parameter name="conversion_date" label="Conversion date" group="Data Conversion">2010-09-16 11:10:50</parameter>
  <parameter name="converter_input_source" label="Converter input source" group="Data Conversion">D:\temp\GAMLORG\Thermo Spectronic AB2 3D\rawdata\3DLIVE.DAT</parameter>
  <parameter name="converter_output_file" label="Converter output file name" group="Data Conversion">C:\Temp\GCnvtstep1-1738177c1\TS_AB2_3D.gaml</parameter>
  <experiment name="3D Ex/Em Matrix, scan rate 50nm/sec (4sec/trace), 40 traces/200sec ">
    <collectdate>1993-12-16T22:10:18Z</collectdate>
    <parameter name="Title" label="Data record title" group="Header">3D Ex/Em Matrix, scan rate 50nm/sec (4sec/trace), 40 traces/200sec </parameter>
    <parameter name="DataFileSignature" label="Data file signature" group="Header">SLM Data File, Format 1.5
</parameter>
    <parameter name="Format" label="Data record format" group="Header">6</parameter>
    <parameter name="Axes" label="Number of X-axes in this data record" group="Header">41</parameter>
    <parameter name="Comment" label="Data record comments" group="Header">NULL</parameter>
    <trace name="3D Ex/Em Matrix, scan rate 50nm/sec (4sec/trace), 40 traces/200sec " technique="FLUOR">
      <parameter name="ZaxisType" label="Z-axis type" group="ZAxis">0x3 = Excitation Monochromator</parameter>
      <parameter name="ZaxisUnits" label="Z-axis units" group="ZAxis">0x2 = Nanometers (nm)</parameter>
      <parameter name="ZaxisLL" label="Z-axis lower limit" group="ZAxis">200.000000</parameter>
      <parameter name="ZaxisUL" label="Z-axis upper limit" group="ZAxis">400.000000</parameter>
      <parameter name="LabelZ" label="Z-axis Label" group="ZAxis">Excitation wavelength</parameter>
      <parameter name="PlotInfo" label="Z-axis plotting annotation" group="ZAxis">NULL</parameter>
      <coordinates label="Excitation wavelength" units="NANOMETERS" valueorder="EVEN">
        <values byteorder="INTEL" format="FLOAT32" numvalues="41">AABIQwAATUMAAFJDAABXQwAAXEMAAGFDAABmQwAAa0MAAHBDAAB1QwAAekMAAH9DAACCQwCAhEMA
AIdDAICJQwAAjEMAgI5DAACRQwCAk0MAAJZDAICYQwAAm0MAgJ1DAACgQwCAokMAAKVDAICnQwAA
qkMAgKxDAACvQwCAsUMAALRDAIC2QwAAuUMAgLtDAAC+QwCAwEMAAMNDAIDFQwAAyEM=
        </values>
      </coordinates>
      <Xdata label="Emission wavelength" units="NANOMETERS" valueorder="EVEN">
        <parameter name="DataType" label="Type of data stored" group="XAxis">0x01 = Normal Data</parameter>
        <parameter name="Incs" label="# of different X-axis increments" group="XAxis">1</parameter>
        <parameter name="Channels" label="# of Y-axis associated with this X-axis" group="XAxis">1</parameter>
        <parameter name="Points" label="# of data points along the X-axis" group="XAxis">201</parameter>
        <parameter name="XFlag" label="X-axis values included flag" group="XAxis">0x0</parameter>
        <parameter name="Zaxis" label="Z-axis values for this X-axis" group="XAxis">200.000000</parameter>
        <parameter name="LabelX" label="X-axis Label" group="XAxis">Emission wavelength</parameter>
        <parameter name="XaxisType" label="X-axis type" group="XAxis">0x2 = Time-Dependent Emission Monochromator</parameter>
        <parameter name="XaxisUnits" label="X-axis units" group="XAxis">0x2 = Nanometers (nm)</parameter>
        <parameter name="Inc" label="Data point increment" group="XAxis">1.000000</parameter>
        <parameter name="XaxisLL" label="X-axis lower limit" group="XAxis">300.000000</parameter>
        <parameter name="XaxisUL" label="X-axis upper limit" group="XAxis">500.000000</parameter>
        <parameter name="Header" label="Data type specific header info" group="XAxis">0</parameter>
        <parameter name="PlotInfo" label="X-axis plotting annotation" group="XAxis">NULL</parameter>
        <values byteorder="INTEL" format="FLOAT32" numvalues="201">AACWQwCAlkMAAJdDAICXQwAAmEMAgJhDAACZQwCAmUMAAJpDAICaQwAAm0MAgJtDAACcQwCAnEMA
AJ1DAICdQwAAnkMAgJ5DAACfQwCAn0MAAKBDAICgQwAAoUMAgKFDAACiQwCAokMAAKNDAICjQwAA
pEMAgKRDAAClQwCApUMAAKZDAICmQwAAp0MAgKdDAACoQwCAqEMAAKlDAICpQwAAqkMAgKpDAACr
QwCAq0MAAKxDAICsQwAArUMAgK1DAACuQwCArkMAAK9DAICvQwAAsEMAgLBDAACxQwCAsUMAALJD
AICyQwAAs0MAgLNDAAC0QwCAtEMAALVDAIC1QwAAtkMAgLZDAAC3QwCAt0MAALhDAIC4QwAAuUMA
gLlDAAC6QwCAukMAALtDAIC7QwAAvEMAgLxDAAC9QwCAvUMAAL5DAIC+QwAAv0MAgL9DAADAQwCA
wEMAAMFDAIDBQwAAwkMAgMJDAADDQwCAw0MAAMRDAIDEQwAAxUMAgMVDAADGQwCAxkMAAMdDAIDH
QwAAyEMAgMhDAADJQwCAyUMAAMpDAIDKQwAAy0MAgMtDAADMQwCAzEMAAM1DAIDNQwAAzkMAgM5D
AADPQwCAz0MAANBDAIDQQwAA0UMAgNFDAADSQwCA0kMAANNDAIDTQwAA1EMAgNRDAADVQwCA1UMA
ANZDAIDWQwAA10MAgNdDAADYQwCA2EMAANlDAIDZQwAA2kMAgNpDAADbQwCA20MAANxDAIDcQwAA
3UMAgN1DAADeQwCA3kMAAN9DAIDfQwAA4EMAgOBDAADhQwCA4UMAAOJDAIDiQwAA40MAgONDAADk
QwCA5EMAAOVDAIDlQwAA5kMAgOZDAADnQwCA50MAAOhDAIDoQwAA6UMAgOlDAADqQwCA6kMAAOtD
AIDrQwAA7EMAgOxDAADtQwCA7UMAAO5DAIDuQwAA70MAgO9DAADwQwCA8EMAAPFDAIDxQwAA8kMA
gPJDAADzQwCA80MAAPRDAID0QwAA9UMAgPVDAAD2QwCA9kMAAPdDAID3QwAA+EMAgPhDAAD5QwCA
+UMAAPpD
        </values>
        <Ydata label="Fluorescence" units="UNKNOWN">
          <parameter name="DataTags" label="# of data points with tags" group="YAxis">0</parameter>
          <parameter name="Channel" label="Instrument channel stored" group="YAxis">10</parameter>
          <parameter name="MaxX" label="X-axis of the data set maximum" group="YAxis">348.000000</parameter>
          <parameter name="MaxY" label="Data set maximum" group="YAxis">0.029907</parameter>
          <parameter name="MinX" label="X-axis of the data set minimum" group="YAxis">305.000000</parameter>
          <parameter name="MinY" label="Data set minimum" group="YAxis">0.024414</parameter>
          <parameter name="YaxisType" label="Y-axis type" group="YAxis">0x12 = Intensity</parameter>
          <parameter name="YaxisUnit" label="Y-axis unit" group="YAxis">0x0 = Unknown</parameter>
          <parameter name="YAxisLL" label="Y-axis lower limit" group="YAxis">0.024414</parameter>
          <parameter name="YAxisUL" label="Y-axis upper limit" group="YAxis">0.029907</parameter>
          <parameter name="LabelY" label="Y-axis Label" group="YAxis">Fluorescence</parameter>
          <parameter name="AcqStarted" label="Date and Time the acquisition was started" group="Standard">0000-00-00 00:00:00</parameter>
          <parameter name="AcqCompleted" label="Date and Time the acquisition was completed" group="Standard">0000-00-00 00:00:00</parameter>
          <parameter name="ID" label="Acquiring instruments serial (ID) number" group="Standard">0</parameter>
          <parameter name="Instrument" label="Instrument type acquired on" group="Standard">7</parameter>
          <parameter name="Modified" label="Date and Time the acquisition was modified" group="Standard">0000-00-00 00:00:00</parameter>
          <parameter name="OSVersion" label="Operating system version" group="Standard">0.000000</parameter>
          <parameter name="Version" label="Program version (&apos;C&apos;) acquired on" group="Standard">0.000000</parameter>
          <parameter name="Comment" label="Acquisition comment" group="Standard">NULL</parameter>
          <parameter name="AcqTitle" label="Acquisition title" group="Standard">NULL</parameter>
          <parameter name="User" label="User who acquired this data" group="Standard">NULL</parameter>
          <parameter name="Monochromators" label="# of monochromators attached" group="Hardware">2</parameter>
          <parameter name="Pmts" label="# of PMTs attached" group="Hardware">2</parameter>
          <parameter name="Lamp" label="Excitation light source type" group="LAMP">1</parameter>
          <parameter name="LampComment" label="Excitation light source comment" group="LAMP">Default Lamp Comment</parameter>
          <parameter name="MonochromatorAt1" label="Monochromator constant/setup wavelength" group="Monochromator">300.000000</parameter>
          <parameter name="MonochromatorShutter1" label="Monochromator shutter status" group="Monochromator">0</parameter>
          <parameter name="MonochromatorSlitIn1" label="Monochromator input slit width" group="Monochromator">4.000000</parameter>
          <parameter name="MonochromatorSlitOut1" label="Monochromator output slit width" group="Monochromator">4.000000</parameter>
          <parameter name="MonochromatorStepSize1" label="Monochromator step size" group="Monochromator">0.200000</parameter>
          <parameter name="MonochromatorType1" label="Monochromator type (excitation or emission)" group="Monochromator">10</parameter>
          <parameter name="MonochromatorUpperLimit1" label="Monochromator upper limit" group="Monochromator">950.000000</parameter>
          <parameter name="MonochromatorComment1" label="Monochromator comment" group="Monochromator">Default Monochromator Comment</parameter>
          <parameter name="MonochromatorNumFilter1" label="Monochromator # of filters switched" group="Filter">2</parameter>
          <parameter name="MonochromatorFilter1Wavelength1" label="Monochromator constant/setup wavelength" group="Filter">200.000000</parameter>
          <parameter name="MonochromatorFilter1Wavelength2" label="Monochromator constant/setup wavelength" group="Filter">300.000000</parameter>
          <parameter name="MonochromatorFilter1Comment" label="Monochromator filter comment" group="Filter">Default Filter Comment</parameter>
          <parameter name="MonochromatorPolarizerAngle1" label="Monochromator polarization angle" group="Polarizer">0.000000</parameter>
          <parameter name="MonochromatorPolarizerType1" label="Monochromator polarizer type" group="Polarizer">1</parameter>
          <parameter name="MonochromatorPolarizerComment1" label="Monochromator polarizer comment" group="Polarizer">Default Polarizer Comment</parameter>
          <parameter name="MonochromatorAt2" label="Monochromator constant/setup wavelength" group="Monochromator">400.000000</parameter>
          <parameter name="MonochromatorShutter2" label="Monochromator shutter status" group="Monochromator">0</parameter>
          <parameter name="MonochromatorSlitIn2" label="Monochromator input slit width" group="Monochromator">4.000000</parameter>
          <parameter name="MonochromatorSlitOut2" label="Monochromator output slit width" group="Monochromator">4.000000</parameter>
          <parameter name="MonochromatorStepSize2" label="Monochromator step size" group="Monochromator">0.200000</parameter>
          <parameter name="MonochromatorType2" label="Monochromator type (excitation or emission)" group="Monochromator">8</parameter>
          <parameter name="MonochromatorUpperLimit2" label="Monochromator upper limit" group="Monochromator">950.000000</parameter>
          <parameter name="MonochromatorComment2" label="Monochromator comment" group="Monochromator">Default Monochromator Comment</parameter>
          <parameter name="MonochromatorNumFilter2" label="Monochromator # of filters switched" group="Filter">3</parameter>
          <parameter name="MonochromatorFilter2Wavelength1" label="Monochromator constant/setup wavelength" group="Filter">200.000000</parameter>
          <parameter name="MonochromatorFilter2Wavelength2" label="Monochromator constant/setup wavelength" group="Filter">300.000000</parameter>
          <parameter name="MonochromatorFilter2Wavelength3" label="Monochromator constant/setup wavelength" group="Filter">400.000000</parameter>
          <parameter name="MonochromatorFilter2Comment" label="Monochromator filter comment" group="Filter">Default Filter Comment</parameter>
          <parameter name="MonochromatorPolarizerAngle2" label="Monochromator polarization angle" group="Polarizer">90.000000</parameter>
          <parameter name="MonochromatorPolarizerType2" label="Monochromator polarizer type" group="Polarizer">1</parameter>
          <parameter name="MonochromatorPolarizerComment2" label="Monochromator polarizer comment" group="Polarizer">Default Polarizer Comment</parameter>
          <parameter name="PmtConnection1" label="PMT connection" group="PMT">10</parameter>
          <parameter name="PmtGain1" label="PMT gain" group="PMT">1</parameter>
          <parameter name="PmtHighVolt1" label="PMT high voltage" group="PMT">750</parameter>
          <parameter name="PmtOffset1" label="PMT offset status" group="PMT">0</parameter>
          <parameter name="PmtAcquisition1" label="PMT acquisition type" group="PMT">0</parameter>
          <parameter name="PmtComment1" label="PMT comment" group="PMT">Default Pmt Comment</parameter>
          <parameter name="PmtConnection2" label="PMT connection" group="PMT">10</parameter>
          <parameter name="PmtGain2" label="PMT gain" group="PMT">1</parameter>
          <parameter name="PmtHighVolt2" label="PMT high voltage" group="PMT">750</parameter>
          <parameter name="PmtOffset2" label="PMT offset status" group="PMT">0</parameter>
          <parameter name="PmtAcquisition2" label="PMT acquisition type" group="PMT">1</parameter>
          <parameter name="PmtComment2" label="PMT comment" group="PMT">Default Pmt Comment</parameter>
          <parameter name="InstrumentSpecific3" label="Instrument specific information" group="AUX">2</parameter>
          <parameter name="AuxChannelGain1" label="AUX channel gain" group="AUX">0</parameter>
          <parameter name="AuxChannelOffset1" label="AUX channel offset status" group="AUX">0</parameter>
          <parameter name="AuxChannelComment1" label="AUX channel comment" group="AUX">Default Aux Pmt Comment</parameter>
          <parameter name="AuxChannelGain2" label="AUX channel gain" group="AUX">1</parameter>
          <parameter name="AuxChannelOffset2" label="AUX channel offset status" group="AUX">0</parameter>
          <parameter name="AuxChannelComment2" label="AUX channel comment" group="AUX">Default Aux Pmt Comment</parameter>
          <parameter name="PlotInfo" label="Y-axis plotting annotation" group="YAxis">NULL</parameter>
          <parameter name="Corrected" label="Data corrected flag" group="MANIPULATION">0x0</parameter>
          <parameter name="Derivative" label="Degree of derivation" group="MANIPULATION">0</parameter>
          <parameter name="Norm" label="Normalized flag" group="MANIPULATION">0x0</parameter>
          <parameter name="Offset" label="Offset from the original data" group="MANIPULATION">0.000000</parameter>
          <parameter name="Scale" label="Scale from the original data" group="MANIPULATION">1.000000</parameter>
          <parameter name="Smooth" label="# of smoothing passes done" group="MANIPULATION">0</parameter>
          <parameter name="SmoothType" label="Smoothing type" group="MANIPULATION">1.000000</parameter>
          <values byteorder="INTEL" format="FLOAT32" numvalues="201">AIDPPACAyjwAgOM8AIDPPACA4zwAAMg8AIDoPAAAzTwAAOs8AIDPPAAA8DwAgM88AADrPACA1DwA
AOs8AIDPPACA7TwAAM08AADrPAAA0jwAgO08AADXPAAA6zwAANI8AIDtPAAA1zwAAOs8AIDUPACA
7TwAANI8AIDtPACA1DwAAPA8AIDZPAAA8DwAgNk8AADrPAAA3DwAAPA8AADSPAAA8DwAgNQ8AADr
PAAA1zwAAPA8AADcPACA8jwAgN48AAD1PAAA3DwAgPI8AIDePAAA8DwAANw8AADwPACA3jwAgPI8
AADhPAAA9TwAgN48AIDtPAAA3DwAAPA8AADcPACA7TwAgNk8AADwPAAA3DwAAOs8AIDZPACA7TwA
ANw8AADrPAAA3DwAAOs8AADcPACA6DwAgN48AIDtPACA3jwAAOs8AIDZPACA6DwAgN48AIDoPAAA
3DwAAOs8AIDZPACA7TwAgNk8AADmPACA2TwAAOY8AADXPACA7TwAgNk8AIDtPAAA1zwAAPA8AADX
PACA6DwAANc8AADrPACA1DwAAOs8AIDZPACA7TwAANw8AADrPACA1DwAAOs8AADXPACA7TwAANw8
AIDoPAAA1zwAgOg8AADSPAAA6zwAANc8AADrPAAA1zwAgOg8AADcPAAA6zwAANc8AADrPAAA1zwA
APA8AADXPAAA6zwAgNQ8AIDjPAAA3DwAAOs8AADXPACA7TwAANc8AIDoPACA2TwAAOs8AIDZPAAA
5jwAgNk8AIDoPACA1DwAAOs8AIDUPAAA6zwAgNQ8AIDoPACA1DwAAPA8AADcPAAA6zwAANc8AIDj
PAAA0jwAgO08AIDPPAAA8DwAgNQ8AADrPACA2TwAgOg8AADSPACA7TwAANI8AIDyPAAA0jwAgO08
AADSPAAA6zwAANc8AIDtPACA1DwAAOs8AIDUPAAA6zwAgNQ8AADmPAAA0jwAgOg8AADSPACA7TwA
ANI8AADrPACA1DwAAOs8AIDUPACA7TwAANI8AADrPAAA1zwAgO08AADSPAAA6zwAgNQ8AIDtPAAA
0jwAgO08
          </values>
        </Ydata>
        <Ydata label="Y" units="UNKNOWN">
          <parameter name="DataTags" label="# of data points with tags" group="YAxis">0</parameter>
          <parameter name="Channel" label="Instrument channel stored" group="YAxis">10</parameter>
          <parameter name="MaxX" label="X-axis of the data set maximum" group="YAxis">342.000000</parameter>
          <parameter name="MaxY" label="Data set maximum" group="YAxis">0.029907</parameter>
          <parameter name="MinX" label="X-axis of the data set minimum" group="YAxis">307.000000</parameter>
          <parameter name="MinY" label="Data set minimum" group="YAxis">0.025635</parameter>
          <parameter name="YaxisType" label="Y-axis type" group="YAxis">0x12 = Intensity</parameter>
          <parameter name="YaxisUnit" label="Y-axis unit" group="YAxis">0x0 = Unknown</parameter>
          <parameter name="YAxisLL" label="Y-axis lower limit" group="YAxis">0.025635</parameter>
          <parameter name="YAxisUL" label="Y-axis upper limit" group="YAxis">0.029907</parameter>
          <parameter name="LabelY" label="Y-axis Label" group="YAxis">Y</parameter>
          <parameter name="AcqStarted" label="Date and Time the acquisition was started" group="Standard">0000-00-00 00:00:00</parameter>
          <parameter name="AcqCompleted" label="Date and Time the acquisition was completed" group="Standard">0000-00-00 00:00:00</parameter>
          <parameter name="ID" label="Acquiring instruments serial (ID) number" group="Standard">0</parameter>
          <parameter name="Instrument" label="Instrument type acquired on" group="Standard">7</parameter>
          <parameter name="Modified" label="Date and Time the acquisition was modified" group="Standard">0000-00-00 00:00:00</parameter>
          <parameter name="OSVersion" label="Operating system version" group="Standard">0.000000</parameter>
          <parameter name="Version" label="Program version (&apos;C&apos;) acquired on" group="Standard">0.000000</parameter>
          <parameter name="Comment" label="Acquisition comment" group="Standard">NULL</parameter>
          <parameter name="AcqTitle" label="Acquisition title" group="Standard">NULL</parameter>
          <parameter name="User" label="User who acquired this data" group="Standard">NULL</parameter>
          <parameter name="Monochromators" label="# of monochromators attached" group="Hardware">2</parameter>
          <parameter name="Pmts" label="# of PMTs attached" group="Hardware">2</parameter>
          <parameter name="Lamp" label="Excitation light source type" group="LAMP">1</parameter>
          <parameter name="LampComment" label="Excitation light source comment" group="LAMP">Default Lamp Comment</parameter>
          <parameter name="MonochromatorAt1" label="Monochromator constant/setup wavelength" group="Monochromator">300.000000</parameter>
          <parameter name="MonochromatorShutter1" label="Monochromator shutter status" group="Monochromator">0</parameter>
          <parameter name="MonochromatorSlitIn1" label="Monochromator input slit width" group="Monochromator">4.000000</parameter>
          <parameter name="MonochromatorSlitOut1" label="Monochromator output slit width" group="Monochromator">4.000000</parameter>
          <parameter name="MonochromatorStepSize1" label="Monochromator step size" group="Monochromator">0.200000</parameter>
          <parameter name="MonochromatorType1" label="Monochromator type (excitation or emission)" group="Monochromator">10</parameter>
          <parameter name="MonochromatorUpperLimit1" label="Monochromator upper limit" group="Monochromator">950.000000</parameter>
          <parameter name="MonochromatorComment1" label="Monochromator comment" group="Monochromator">Default Monochromator Comment</parameter>
          <parameter name="MonochromatorNumFilter1" label="Monochromator # of filters switched" group="Filter">2</parameter>
          <parameter name="MonochromatorFilter1Wavelength1" label="Monochromator constant/setup wavelength" group="Filter">200.000000</parameter>
          <parameter name="MonochromatorFilter1Wavelength2" label="Monochromator constant/setup wavelength" group="Filter">300.000000</parameter>
          <parameter name="MonochromatorFilter1Comment" label="Monochromator filter comment" group="Filter">Default Filter Comment</parameter>
          <parameter name="MonochromatorPolarizerAngle1" label="Monochromator polarization angle" group="Polarizer">0.000000</parameter>
          <parameter name="MonochromatorPolarizerType1" label="Monochromator polarizer type" group="Polarizer">1</parameter>
          <parameter name="MonochromatorPolarizerComment1" label="Monochromator polarizer comment" group="Polarizer">Default Polarizer Comment</parameter>
          <parameter name="MonochromatorAt2" label="Monochromator constant/setup wavelength" group="Monochromator">400.000000</parameter>
          <parameter name="MonochromatorShutter2" label="Monochromator shutter status" group="Monochromator">0</parameter>
          <parameter name="MonochromatorSlitIn2" label="Monochromator input slit width" group="Monochromator">4.000000</parameter>
          <parameter name="MonochromatorSlitOut2" label="Monochromator output slit width" group="Monochromator">4.000000</parameter>
          <parameter name="MonochromatorStepSize2" label="Monochromator step size" group="Monochromator">0.200000</parameter>
          <parameter name="MonochromatorType2" label="Monochromator type (excitation or emission)" group="Monochromator">8</parameter>
          <parameter name="MonochromatorUpperLimit2" label="Monochromator upper limit" group="Monochromator">950.000000</parameter>
          <parameter name="MonochromatorComment2" label="Monochromator comment" group="Monochromator">Default Monochromator Comment</parameter>
          <parameter name="MonochromatorNumFilter2" label="Monochromator # of filters switched" group="Filter">3</parameter>
          <parameter name="MonochromatorFilter2Wavelength1" label="Monochromator constant/setup wavelength" group="Filter">200.000000</parameter>
          <parameter name="MonochromatorFilter2Wavelength2" label="Monochromator constant/setup wavelength" group="Filter">300.000000</parameter>
          <parameter name="MonochromatorFilter2Wavelength3" label="Monochromator constant/setup wavelength" group="Filter">400.000000</parameter>
          <parameter name="MonochromatorFilter2Comment" label="Monochromator filter comment" group="Filter">Default Filter Comment</parameter>
          <parameter name="MonochromatorPolarizerAngle2" label="Monochromator polarization angle" group="Polarizer">90.000000</parameter>
          <parameter name="MonochromatorPolarizerType2" label="Monochromator polarizer type" group="Polarizer">1</parameter>
          <parameter name="MonochromatorPolarizerComment2" label="Monochromator polarizer comment" group="Polarizer">Default Polarizer Comment</parameter>
          <parameter name="PmtConnection1" label="PMT connection" group="PMT">10</parameter>
          <parameter name="PmtGain1" label="PMT gain" group="PMT">1</parameter>
          <parameter name="PmtHighVolt1" label="PMT high voltage" group="PMT">750</parameter>
          <parameter name="PmtOffset1" label="PMT offset status" group="PMT">0</parameter>
          <parameter name="PmtAcquisition1" label="PMT acquisition type" group="PMT">0</parameter>
          <parameter name="PmtComment1" label="PMT comment" group="PMT">Default Pmt Comment</parameter>
          <parameter name="PmtConnection2" label="PMT connection" group="PMT">10</parameter>
          <parameter name="PmtGain2" label="PMT gain" group="PMT">1</parameter>
          <parameter name="PmtHighVolt2" label="PMT high voltage" group="PMT">750</parameter>
          <parameter name="PmtOffset2" label="PMT offset status" group="PMT">0</parameter>
          <parameter name="PmtAcquisition2" label="PMT acquisition type" group="PMT">1</parameter>
          <parameter name="PmtComment2" label="PMT comment" group="PMT">Default Pmt Comment</parameter>
          <parameter name="InstrumentSpecific3" label="Instrument specific information" group="AUX">2</parameter>
          <parameter name="AuxChannelGain1" label="AUX channel gain" group="AUX">0</parameter>
          <parameter name="AuxChannelOffset1" label="AUX channel offset status" group="AUX">0</parameter>
          <parameter name="AuxChannelComment1" label="AUX channel comment" group="AUX">Default Aux Pmt Comment</parameter>
          <parameter name="AuxChannelGain2" label="AUX channel gain" group="AUX">1</parameter>
          <parameter name="AuxChannelOffset2" label="AUX channel offset status" group="AUX">0</parameter>
          <parameter name="AuxChannelComment2" label="AUX channel comment" group="AUX">Default Aux Pmt Comment</parameter>
          <parameter name="PlotInfo" label="Y-axis plotting annotation" group="YAxis">NULL</parameter>
          <parameter name="Corrected" label="Data corrected flag" group="MANIPULATION">0x0</parameter>
          <parameter name="Derivative" label="Degree of derivation" group="MANIPULATION">0</parameter>
          <parameter name="Norm" label="Normalized flag" group="MANIPULATION">0x0</parameter>
          <parameter name="Offset" label="Offset from the original data" group="MANIPULATION">0.000000</parameter>
          <parameter name="Scale" label="Scale from the original data" group="MANIPULATION">1.000000</parameter>
          <parameter name="Smooth" label="# of smoothing passes done" group="MANIPULATION">0</parameter>
          <parameter name="SmoothType" label="Smoothing type" group="MANIPULATION">1.000000</parameter>
          <values byteorder="INTEL" format="FLOAT32" numvalues="201">AADcPAAA4TwAgO08AADXPAAA8DwAgNk8AADrPAAA0jwAAOs8AADXPAAA6zwAgNQ8AADrPAAA1zwA
gPI8AIDUPAAA6zwAgNk8AADwPAAA1zwAAPA8AADXPAAA6zwAANc8AADrPAAA1zwAAOs8AADXPAAA
8DwAANw8AIDtPACA3jwAAPA8AIDePAAA8DwAgNk8AIDtPACA2TwAgO08AADcPAAA8DwAgN48AAD1
PAAA4TwAgO08AADhPAAA8DwAgN48AIDyPACA4zwAgO08AADhPAAA8DwAgN48AIDtPACA4zwAgPI8
AADhPAAA6zwAANw8AADwPACA4zwAgO08AADhPACA8jwAgN48AIDtPAAA4TwAgO08AADhPAAA6zwA
gN48AIDtPACA3jwAgOg8AIDjPACA6DwAgOM8AIDoPAAA4TwAAPA8AADhPAAA6zwAgOM8AIDoPAAA
4TwAgOg8AIDjPACA7TwAgN48AIDoPACA3jwAgO08AADmPAAA6zwAgN48AADrPACA2TwAgO08AIDe
PAAA8DwAAOE8AADrPAAA3DwAgO08AADcPACA7TwAgN48AADrPACA4zwAgO08AIDZPAAA8DwAANw8
AADrPACA3jwAAOY8AIDePAAA6zwAANw8AADrPAAA3DwAgOg8AIDePAAA6zwAANw8AIDoPAAA3DwA
gO08AADcPACA6DwAAOE8AADrPAAA3DwAgO08AADcPAAA6zwAgNk8AIDtPAAA3DwAAOs8AIDePACA
6DwAAOE8AADrPACA3jwAgOg8AIDePAAA6zwAgN48AADrPAAA3DwAAOs8AIDZPACA7TwAANw8AADr
PAAA3DwAAOs8AIDUPAAA6zwAgNk8AADrPAAA4TwAgO08AADcPAAA6zwAgNQ8AADwPAAA3DwAgOg8
AIDZPACA6DwAANw8AADrPACA1DwAgOg8AIDUPACA6DwAANc8AIDoPAAA1zwAgOg8AADXPAAA6zwA
ANI8AIDjPACA2TwAAOs8AADXPACA6DwAANc8AIDtPACA3jwAAOs8AIDZPACA6DwAANw8AIDtPACA
1DwAAOY8
          </values>
        </Ydata>
        <Ydata label="Y" units="UNKNOWN">
          <parameter name="DataTags" label="# of data points with tags" group="YAxis">0</parameter>
          <parameter name="Channel" label="Instrument channel stored" group="YAxis">10</parameter>
          <parameter name="MaxX" label="X-axis of the data set maximum" group="YAxis">346.000000</parameter>
          <parameter name="MaxY" label="Data set maximum" group="YAxis">0.031128</parameter>
          <parameter name="MinX" label="X-axis of the data set minimum" group="YAxis">307.000000</parameter>
          <parameter name="MinY" label="Data set minimum" group="YAxis">0.025024</parameter>
          <parameter name="YaxisType" label="Y-axis type" group="YAxis">0x12 = Intensity</parameter>
          <parameter name="YaxisUnit" label="Y-axis unit" group="YAxis">0x0 = Unknown</parameter>
          <parameter name="YAxisLL" label="Y-axis lower limit" group="YAxis">0.025024</parameter>
          <parameter name="YAxisUL" label="Y-axis upper limit" group="YAxis">0.031128</parameter>
          <parameter name="LabelY" label="Y-axis Label" group="YAxis">Y</parameter>
          <parameter name="AcqStarted" label="Date and Time the acquisition was started" group="Standard">0000-00-00 00:00:00</parameter>
          <parameter name="AcqCompleted" label="Date and Time the acquisition was completed" group="Standard">0000-00-00 00:00:00</parameter>
          <parameter name="ID" label="Acquiring instruments serial (ID) number" group="Standard">0</parameter>
          <parameter name="Instrument" label="Instrument type acquired on" group="Standard">7</parameter>
          <parameter name="Modified" label="Date and Time the acquisition was modified" group="Standard">0000-00-00 00:00:00</parameter>
          <parameter name="OSVersion" label="Operating system version" group="Standard">0.000000</parameter>
          <parameter name="Version" label="Program version (&apos;C&apos;) acquired on" group="Standard">0.000000</parameter>
          <parameter name="Comment" label="Acquisition comment" group="Standard">NULL</parameter>
          <parameter name="AcqTitle" label="Acquisition title" group="Standard">NULL</parameter>
          <parameter name="User" label="User who acquired this data" group="Standard">NULL</parameter>
          <parameter name="Monochromators" label="# of monochromators attached" group="Hardware">2</parameter>
          <parameter name="Pmts" label="# of PMTs attached" group="Hardware">2</parameter>
          <parameter name="Lamp" label="Excitation light source type" group="LAMP">1</parameter>
          <parameter name="LampComment" label="Excitation light source comment" group="LAMP">Default Lamp Comment</parameter>
          <parameter name="MonochromatorAt1" label="Monochromator constant/setup wavelength" group="Monochromator">300.000000</parameter>
          <parameter name="MonochromatorShutter1" label="Monochromator shutter status" group="Monochromator">0</parameter>
          <parameter name="MonochromatorSlitIn1" label="Monochromator input slit width" group="Monochromator">4.000000</parameter>
          <parameter name="MonochromatorSlitOut1" label="Monochromator output slit width" group="Monochromator">4.000000</parameter>
          <parameter name="MonochromatorStepSize1" label="Monochromator step size" group="Monochromator">0.200000</parameter>
          <parameter name="MonochromatorType1" label="Monochromator type (excitation or emission)" group="Monochromator">10</parameter>
          <parameter name="MonochromatorUpperLimit1" label="Monochromator upper limit" group="Monochromator">950.000000</parameter>
          <parameter name="MonochromatorComment1" label="Monochromator comment" group="Monochromator">Default Monochromator Comment</parameter>
          <parameter name="MonochromatorNumFilter1" label="Monochromator # of filters switched" group="Filter">2</parameter>
          <parameter name="MonochromatorFilter1Wavelength1" label="Monochromator constant/setup wavelength" group="Filter">200.000000</parameter>
          <parameter name="MonochromatorFilter1Wavelength2" label="Monochromator constant/setup wavelength" group="Filter">300.000000</parameter>
          <parameter name="MonochromatorFilter1Comment" label="Monochromator filter comment" group="Filter">Default Filter Comment</parameter>
          <parameter name="MonochromatorPolarizerAngle1" label="Monochromator polarization angle" group="Polarizer">0.000000</parameter>
          <parameter name="MonochromatorPolarizerType1" label="Monochromator polarizer type" group="Polarizer">1</parameter>
          <parameter name="MonochromatorPolarizerComment1" label="Monochromator polarizer comment" group="Polarizer">Default Polarizer Comment</parameter>
          <parameter name="MonochromatorAt2" label="Monochromator constant/setup wavelength" group="Monochromator">400.000000</parameter>
          <parameter name="MonochromatorShutter2" label="Monochromator shutter status" group="Monochromator">0</parameter>
          <parameter name="MonochromatorSlitIn2" label="Monochromator input slit width" group="Monochromator">4.000000</parameter>
          <parameter name="MonochromatorSlitOut2" label="Monochromator output slit width" group="Monochromator">4.000000</parameter>
          <parameter name="MonochromatorStepSize2" label="Monochromator step size" group="Monochromator">0.200000</parameter>
          <parameter name="MonochromatorType2" label="Monochromator type (excitation or emission)" group="Monochromator">8</parameter>
          <parameter name="MonochromatorUpperLimit2" label="Monochromator upper limit" group="Monochromator">950.000000</parameter>
          <parameter name="MonochromatorComment2" label="Monochromator comment" group="Monochromator">Default Monochromator Comment</parameter>
          <parameter name="MonochromatorNumFilter2" label="Monochromator # of filters switched" group="Filter">3</parameter>
          <parameter name="MonochromatorFilter2Wavelength1" label="Monochromator constant/setup wavelength" group="Filter">200.000000</parameter>
          <parameter name="MonochromatorFilter2Wavelength2" label="Monochromator constant/setup wavelength" group="Filter">300.000000</parameter>
          <parameter name="MonochromatorFilter2Wavelength3" label="Monochromator constant/setup wavelength" group="Filter">400.000000</parameter>
          <parameter name="MonochromatorFilter2Comment" label="Monochromator filter comment" group="Filter">Default Filter Comment</parameter>
          <parameter name="MonochromatorPolarizerAngle2" label="Monochromator polarization angle" group="Polarizer">90.000000</parameter>
          <parameter name="MonochromatorPolarizerType2" label="Monochromator polarizer type" group="Polarizer">1</parameter>
          <parameter name="MonochromatorPolarizerComment2" label="Monochromator polarizer comment" group="Polarizer">Default Polarizer Comment</parameter>
          <parameter name="PmtConnection1" label="PMT connection" group="PMT">10</parameter>
          <parameter name="PmtGain1" label="PMT gain" group="PMT">1</parameter>
          <parameter name="PmtHighVolt1" label="PMT high voltage" group="PMT">750</parameter>
          <parameter name="PmtOffset1" label="PMT offset status" group="PMT">0</parameter>
          <parameter name="PmtAcquisition1" label="PMT acquisition type" group="PMT">0</parameter>
          <parameter name="PmtComment1" label="PMT comment" group="PMT">Default Pmt Comment</parameter>
          <parameter name="PmtConnection2" label="PMT connection" group="PMT">10</parameter>
          <parameter name="PmtGain2" label="PMT gain" group="PMT">1</parameter>
          <parameter name="PmtHighVolt2" label="PMT high voltage" group="PMT">750</parameter>
          <parameter name="PmtOffset2" label="PMT offset status" group="PMT">0</parameter>
          <parameter name="PmtAcquisition2" label="PMT acquisition type" group="PMT">1</parameter>
          <parameter name="PmtComment2" label="PMT comment" group="PMT">Default Pmt Comment</parameter>
          <parameter name="InstrumentSpecific3" label="Instrument specific information" group="AUX">2</parameter>
          <parameter name="AuxChannelGain1" label="AUX channel gain" group="AUX">0</parameter>
          <parameter name="AuxChannelOffset1" label="AUX channel offset status" group="AUX">0</parameter>
          <parameter name="AuxChannelComment1" label="AUX channel comment" group="AUX">Default Aux Pmt Comment</parameter>
          <parameter name="AuxChannelGain2" label="AUX channel gain" group="AUX">1</parameter>
          <parameter name="AuxChannelOffset2" label="AUX channel offset status" group="AUX">0</parameter>
          <parameter name="AuxChannelComment2" label="AUX channel comment" group="AUX">Default Aux Pmt Comment</parameter>
          <parameter name="PlotInfo" label="Y-axis plotting annotation" group="YAxis">NULL</parameter>
          <parameter name="Corrected" label="Data corrected flag" group="MANIPULATION">0x0</parameter>
          <parameter name="Derivative" label="Degree of derivation" group="MANIPULATION">0</parameter>
          <parameter name="Norm" label="Normalized flag" group="MANIPULATION">0x0</parameter>
          <parameter name="Offset" label="Offset from the original data" group="MANIPULATION">0.000000</parameter>
          <parameter name="Scale" label="Scale from the original data" group="MANIPULATION">1.000000</parameter>
          <parameter name="Smooth" label="# of smoothing passes done" group="MANIPULATION">0</parameter>
          <parameter name="SmoothType" label="Smoothing type" group="MANIPULATION">1.000000</parameter>
          <values byteorder="INTEL" format="FLOAT32" numvalues="201">AIDUPACA1DwAgOg8AIDUPAAA8DwAANI8AIDtPAAAzTwAgPI8AADNPACA7TwAANI8AADwPACA2TwA
APA8AIDPPACA7TwAANI8AIDtPACA1DwAgO08AIDUPACA7TwAgNQ8AADwPAAA1zwAgO08AADXPACA
8jwAgNk8AADwPAAA1zwAgPI8AADcPAAA8DwAgN48AAD1PACA2TwAAPo8AIDePACA8jwAgOM8AAD1
PAAA4TwAAPA8AADcPAAA/zwAgN48AID8PACA4zwAAPU8AADmPAAA+jwAAOY8AAD1PAAA3DwAgPc8
AIDjPACA8jwAgOM8AAD6PACA3jwAAOs8AIDZPAAA8DwAANw8AAD6PAAA3DwAAPU8AADcPAAA6zwA
gNk8AIDtPAAA4TwAAPA8AIDePAAA8DwAAOE8AADwPACA3jwAgO08AIDePACA7TwAAOE8AADrPAAA
3DwAAOs8AIDZPACA7TwAgN48AIDyPACA3jwAAPA8AIDePACA7TwAANw8AADwPAAA3DwAAPA8AIDe
PACA7TwAgN48AADwPAAA1zwAgO08AADXPAAA6zwAANc8AADwPACA2TwAAOs8AIDUPACA7TwAgNk8
AIDtPACA3jwAAOs8AADcPACA8jwAgNk8AADrPAAA3DwAgO08AADcPACA7TwAANc8AIDtPAAA1zwA
APA8AIDUPACA7TwAgNk8AIDoPAAA3DwAgPI8AIDUPAAA8DwAgNQ8AADwPACA2TwAgOg8AIDZPACA
7TwAgNk8AIDtPAAA3DwAAOs8AIDZPAAA6zwAANw8AADwPACA1DwAgO08AIDZPACA7TwAgNQ8AIDt
PACA2TwAAPA8AADSPAAA8DwAgNQ8AIDtPACA2TwAAOs8AIDUPACA7TwAANI8AADrPACA1DwAAOs8
AADXPAAA6zwAgNQ8AIDtPAAA0jwAAPA8AIDUPACA7TwAgNQ8AIDtPACA1DwAAOs8AADXPAAA6zwA
ANc8AADrPACA1DwAgO08AADXPAAA6zwAgNQ8AIDoPACA1DwAAOs8AADSPACA7TwAgNQ8AADrPAAA
0jwAAOs8
          </values>
        </Ydata>
        <Ydata label="Y" units="UNKNOWN">
          <parameter name="DataTags" label="# of data points with tags" group="YAxis">0</parameter>
          <parameter name="Channel" label="Instrument channel stored" group="YAxis">10</parameter>
          <parameter name="MaxX" label="X-axis of the data set maximum" group="YAxis">354.000000</parameter>
          <parameter name="MaxY" label="Data set maximum" group="YAxis">0.034485</parameter>
          <parameter name="MinX" label="X-axis of the data set minimum" group="YAxis">303.000000</parameter>
          <parameter name="MinY" label="Data set minimum" group="YAxis">0.025635</parameter>
          <parameter name="YaxisType" label="Y-axis type" group="YAxis">0x12 = Intensity</parameter>
          <parameter name="YaxisUnit" label="Y-axis unit" group="YAxis">0x0 = Unknown</parameter>
          <parameter name="YAxisLL" label="Y-axis lower limit" group="YAxis">0.025635</parameter>
          <parameter name="YAxisUL" label="Y-axis upper limit" group="YAxis">0.034485</parameter>
          <parameter name="LabelY" label="Y-axis Label" group="YAxis">Y</parameter>
          <parameter name="AcqStarted" label="Date and Time the acquisition was started" group="Standard">0000-00-00 00:00:00</parameter>
          <parameter name="AcqCompleted" label="Date and Time the acquisition was completed" group="Standard">0000-00-00 00:00:00</parameter>
          <parameter name="ID" label="Acquiring instruments serial (ID) number" group="Standard">0</parameter>
          <parameter name="Instrument" label="Instrument type acquired on" group="Standard">7</parameter>
          <parameter name="Modified" label="Date and Time the acquisition was modified" group="Standard">0000-00-00 00:00:00</parameter>
          <parameter name="OSVersion" label="Operating system version" group="Standard">0.000000</parameter>
          <parameter name="Version" label="Program version (&apos;C&apos;) acquired on" group="Standard">0.000000</parameter>
          <parameter name="Comment" label="Acquisition comment" group="Standard">NULL</parameter>
          <parameter name="AcqTitle" label="Acquisition title" group="Standard">NULL</parameter>
          <parameter name="User" label="User who acquired this data" group="Standard">NULL</parameter>
          <parameter name="Monochromators" label="# of monochromators attached" group="Hardware">2</parameter>
          <parameter name="Pmts" label="# of PMTs attached" group="Hardware">2</parameter>
          <parameter name="Lamp" label="Excitation light source type" group="LAMP">1</parameter>
          <parameter name="LampComment" label="Excitation light source comment" group="LAMP">Default Lamp Comment</parameter>
          <parameter name="MonochromatorAt1" label="Monochromator constant/setup wavelength" group="Monochromator">300.000000</parameter>
          <parameter name="MonochromatorShutter1" label="Monochromator shutter status" group="Monochromator">0</parameter>
          <parameter name="MonochromatorSlitIn1" label="Monochromator input slit width" group="Monochromator">4.000000</parameter>
          <parameter name="MonochromatorSlitOut1" label="Monochromator output slit width" group="Monochromator">4.000000</parameter>
          <parameter name="MonochromatorStepSize1" label="Monochromator step size" group="Monochromator">0.200000</parameter>
          <parameter name="MonochromatorType1" label="Monochromator type (excitation or emission)" group="Monochromator">10</parameter>
          <parameter name="MonochromatorUpperLimit1" label="Monochromator upper limit" group="Monochromator">950.000000</parameter>
          <parameter name="MonochromatorComment1" label="Monochromator comment" group="Monochromator">Default Monochromator Comment</parameter>
          <parameter name="MonochromatorNumFilter1" label="Monochromator # of filters switched" group="Filter">2</parameter>
          <parameter name="MonochromatorFilter1Wavelength1" label="Monochromator constant/setup wavelength" group="Filter">200.000000</parameter>
          <parameter name="MonochromatorFilter1Wavelength2" label="Monochromator constant/setup wavelength" group="Filter">300.000000</parameter>
          <parameter name="MonochromatorFilter1Comment" label="Monochromator filter comment" group="Filter">Default Filter Comment</parameter>
          <parameter name="MonochromatorPolarizerAngle1" label="Monochromator polarization angle" group="Polarizer">0.000000</parameter>
          <parameter name="MonochromatorPolarizerType1" label="Monochromator polarizer type" group="Polarizer">1</parameter>
          <parameter name="MonochromatorPolarizerComment1" label="Monochromator polarizer comment" group="Polarizer">Default Polarizer Comment</parameter>
          <parameter name="MonochromatorAt2" label="Monochromator constant/setup wavelength" group="Monochromator">400.000000</parameter>
          <parameter name="MonochromatorShutter2" label="Monochromator shutter status" group="Monochromator">0</parameter>
          <parameter name="MonochromatorSlitIn2" label="Monochromator input slit width" group="Monochromator">4.000000</parameter>
          <parameter name="MonochromatorSlitOut2" label="Monochromator output slit width" group="Monochromator">4.000000</parameter>
          <parameter name="MonochromatorStepSize2" label="Monochromator step size" group="Monochromator">0.200000</parameter>
          <parameter name="MonochromatorType2" label="Monochromator type (excitation or emission)" group="Monochromator">8</parameter>
          <parameter name="MonochromatorUpperLimit2" label="Monochromator upper limit" group="Monochromator">950.000000</parameter>
          <parameter name="MonochromatorComment2" label="Monochromator comment" group="Monochromator">Default Monochromator Comment</parameter>
          <parameter name="MonochromatorNumFilter2" label="Monochromator # of filters switched" group="Filter">3</parameter>
          <parameter name="MonochromatorFilter2Wavelength1" label="Monochromator constant/setup wavelength" group="Filter">200.000000</parameter>
          <parameter name="MonochromatorFilter2Wavelength2" label="Monochromator constant/setup wavelength" group="Filter">300.000000</parameter>
          <parameter name="MonochromatorFilter2Wavelength3" label="Monochromator constant/setup wavelength" group="Filter">400.000000</parameter>
          <parameter name="MonochromatorFilter2Comment" label="Monochromator filter comment" group="Filter">Default Filter Comment</parameter>
          <parameter name="MonochromatorPolarizerAngle2" label="Monochromator polarization angle" group="Polarizer">90.000000</parameter>
          <parameter name="MonochromatorPolarizerType2" label="Monochromator polarizer type" group="Polarizer">1</parameter>
          <parameter name="MonochromatorPolarizerComment2" label="Monochromator polarizer comment" group="Polarizer">Default Polarizer Comment</parameter>
          <parameter name="PmtConnection1" label="PMT connection" group="PMT">10</parameter>
          <parameter name="PmtGain1" label="PMT gain" group="PMT">1</parameter>
          <parameter name="PmtHighVolt1" label="PMT high voltage" group="PMT">750</parameter>
          <parameter name="PmtOffset1" label="PMT offset status" group="PMT">0</parameter>
          <parameter name="PmtAcquisition1" label="PMT acquisition type" group="PMT">0</parameter>
          <parameter name="PmtComment1" label="PMT comment" group="PMT">Default Pmt Comment</parameter>
          <parameter name="PmtConnection2" label="PMT connection" group="PMT">10</parameter>
          <parameter name="PmtGain2" label="PMT gain" group="PMT">1</parameter>
          <parameter name="PmtHighVolt2" label="PMT high voltage" group="PMT">750</parameter>
          <parameter name="PmtOffset2" label="PMT offset status" group="PMT">0</parameter>
          <parameter name="PmtAcquisition2" label="PMT acquisition type" group="PMT">1</parameter>
          <parameter name="PmtComment2" label="PMT comment" group="PMT">Default Pmt Comment</parameter>
          <parameter name="InstrumentSpecific3" label="Instrument specific information" group="AUX">2</parameter>
          <parameter name="AuxChannelGain1" label="AUX channel gain" group="AUX">0</parameter>
          <parameter name="AuxChannelOffset1" label="AUX channel offset status" group="AUX">0</parameter>
          <parameter name="AuxChannelComment1" label="AUX channel comment" group="AUX">Default Aux Pmt Comment</parameter>
          <parameter name="AuxChannelGain2" label="AUX channel gain" group="AUX">1</parameter>
          <parameter name="AuxChannelOffset2" label="AUX channel offset status" group="AUX">0</parameter>
          <parameter name="AuxChannelComment2" label="AUX channel comment" group="AUX">Default Aux Pmt Comment</parameter>
          <parameter name="PlotInfo" label="Y-axis plotting annotation" group="YAxis">NULL</parameter>
          <parameter name="Corrected" label="Data corrected flag" group="MANIPULATION">0x0</parameter>
          <parameter name="Derivative" label="Degree of derivation" group="MANIPULATION">0</parameter>
          <parameter name="Norm" label="Normalized flag" group="MANIPULATION">0x0</parameter>
          <parameter name="Offset" label="Offset from the original data" group="MANIPULATION">0.000000</parameter>
          <parameter name="Scale" label="Scale from the original data" group="MANIPULATION">1.000000</parameter>
          <parameter name="Smooth" label="# of smoothing passes done" group="MANIPULATION">0</parameter>
          <parameter name="SmoothType" label="Smoothing type" group="MANIPULATION">1.000000</parameter>
          <values byteorder="INTEL" format="FLOAT32" numvalues="201">AADcPAAA1zwAgO08AADSPACA6DwAgNQ8AADwPAAA0jwAgPI8AIDUPACA8jwAANI8AADrPAAA3DwA
gPI8AADXPAAA9TwAgNk8AADwPAAA3DwAAOs8AADhPAAA9TwAgN48AID3PAAA4TwAgPI8AIDtPACA
9zwAgPI8AAAHPQCA6DwAQAM9AID3PAAABz0AgPI8AAACPQCA9zwAQAg9AAD1PADACj0AgPI8AEAD
PQAA9TwAQAg9AID3PADACj0AAPo8AAAHPQCA/DwAgAQ9AAD/PABAAz0AAP88AEANPQAA9TwAgAQ9
AAD6PAAABz0AgPI8AEADPQCA9zwAAAI9AIDyPADAAD0AgPI8AID8PACA8jwAgPI8AIDtPAAA+jwA
gO08AID8PAAA5jwAgPc8AIDoPAAA9TwAAOs8AAD1PAAA4TwAAPo8AIDoPAAA9TwAAOY8AID3PAAA
6zwAAPo8AADmPACA9zwAgOg8AADwPACA4zwAgO08AIDePAAA9TwAAOE8AID3PACA3jwAgPI8AIDj
PACA7TwAgOg8AADwPACA4zwAAPA8AADcPAAA8DwAAOE8AADwPACA4zwAgPI8AADcPACA8jwAANw8
AADwPACA3jwAAOs8AIDePAAA8DwAAOE8AADwPACA3jwAgO08AADcPAAA6zwAAOE8AIDyPAAA3DwA
gPc8AADcPAAA6zwAANw8AIDoPACA4zwAgPI8AADcPAAA8DwAgNk8AIDoPACA3jwAAOs8AADcPACA
7TwAAOE8AADrPACA4zwAgO08AADcPACA6DwAgN48AIDyPACA2TwAAPA8AADcPAAA6zwAgNk8AIDj
PAAA3DwAAPA8AADcPAAA8DwAgNk8AIDoPAAA1zwAAOs8AADXPAAA8DwAANc8AIDyPAAA4TwAAOs8
AIDZPAAA6zwAgNk8AIDyPACA1DwAgO08AIDUPAAA6zwAgNk8AADmPACA2TwAAPA8AIDUPACA8jwA
gN48AADwPACA2TwAAOY8AADcPAAA8DwAgNQ8AADrPACA2TwAgOg8AIDZPAAA6zwAgNk8AIDtPAAA
1zwAgO08
          </values>
        </Ydata>
        <Ydata label="Y" units="UNKNOWN">
          <parameter name="DataTags" label="# of data points with tags" group="YAxis">0</parameter>
          <parameter name="Channel" label="Instrument channel stored" group="YAxis">10</parameter>
          <parameter name="MaxX" label="X-axis of the data set maximum" group="YAxis">346.000000</parameter>
          <parameter name="MaxY" label="Data set maximum" group="YAxis">0.040283</parameter>
          <parameter name="MinX" label="X-axis of the data set minimum" group="YAxis">303.000000</parameter>
          <parameter name="MinY" label="Data set minimum" group="YAxis">0.025330</parameter>
          <parameter name="YaxisType" label="Y-axis type" group="YAxis">0x12 = Intensity</parameter>
          <parameter name="YaxisUnit" label="Y-axis unit" group="YAxis">0x0 = Unknown</parameter>
          <parameter name="YAxisLL" label="Y-axis lower limit" group="YAxis">0.025330</parameter>
          <parameter name="YAxisUL" label="Y-axis upper limit" group="YAxis">0.040283</parameter>
          <parameter name="LabelY" label="Y-axis Label" group="YAxis">Y</parameter>
          <parameter name="AcqStarted" label="Date and Time the acquisition was started" group="Standard">0000-00-00 00:00:00</parameter>
          <parameter name="AcqCompleted" label="Date and Time the acquisition was completed" group="Standard">0000-00-00 00:00:00</parameter>
          <parameter name="ID" label="Acquiring instruments serial (ID) number" group="Standard">0</parameter>
          <parameter name="Instrument" label="Instrument type acquired on" group="Standard">7</parameter>
          <parameter name="Modified" label="Date and Time the acquisition was modified" group="Standard">0000-00-00 00:00:00</parameter>
          <parameter name="OSVersion" label="Operating system version" group="Standard">0.000000</parameter>
          <parameter name="Version" label="Program version (&apos;C&apos;) acquired on" group="Standard">0.000000</parameter>
          <parameter name="Comment" label="Acquisition comment" group="Standard">NULL</parameter>
          <parameter name="AcqTitle" label="Acquisition title" group="Standard">NULL</parameter>
          <parameter name="User" label="User who acquired this data" group="Standard">NULL</parameter>
          <parameter name="Monochromators" label="# of monochromators attached" group="Hardware">2</parameter>
          <parameter name="Pmts" label="# of PMTs attached" group="Hardware">2</parameter>
          <parameter name="Lamp" label="Excitation light source type" group="LAMP">1</parameter>
          <parameter name="LampComment" label="Excitation light source comment" group="LAMP">Default Lamp Comment</parameter>
          <parameter name="MonochromatorAt1" label="Monochromator constant/setup wavelength" group="Monochromator">300.000000</parameter>
          <parameter name="MonochromatorShutter1" label="Monochromator shutter status" group="Monochromator">0</parameter>
          <parameter name="MonochromatorSlitIn1" label="Monochromator input slit width" group="Monochromator">4.000000</parameter>
          <parameter name="MonochromatorSlitOut1" label="Monochromator output slit width" group="Monochromator">4.000000</parameter>
          <parameter name="MonochromatorStepSize1" label="Monochromator step size" group="Monochromator">0.200000</parameter>
          <parameter name="MonochromatorType1" label="Monochromator type (excitation or emission)" group="Monochromator">10</parameter>
          <parameter name="MonochromatorUpperLimit1" label="Monochromator upper limit" group="Monochromator">950.000000</parameter>
          <parameter name="MonochromatorComment1" label="Monochromator comment" group="Monochromator">Default Monochromator Comment</parameter>
          <parameter name="MonochromatorNumFilter1" label="Monochromator # of filters switched" group="Filter">2</parameter>
          <parameter name="MonochromatorFilter1Wavelength1" label="Monochromator constant/setup wavelength" group="Filter">200.000000</parameter>
          <parameter name="MonochromatorFilter1Wavelength2" label="Monochromator constant/setup wavelength" group="Filter">300.000000</parameter>
          <parameter name="MonochromatorFilter1Comment" label="Monochromator filter comment" group="Filter">Default Filter Comment</parameter>
          <parameter name="MonochromatorPolarizerAngle1" label="Monochromator polarization angle" group="Polarizer">0.000000</parameter>
          <parameter name="MonochromatorPolarizerType1" label="Monochromator polarizer type" group="Polarizer">1</parameter>
          <parameter name="MonochromatorPolarizerComment1" label="Monochromator polarizer comment" group="Polarizer">Default Polarizer Comment</parameter>
          <parameter name="MonochromatorAt2" label="Monochromator constant/setup wavelength" group="Monochromator">400.000000</parameter>
          <parameter name="MonochromatorShutter2" label="Monochromator shutter status" group="Monochromator">0</parameter>
          <parameter name="MonochromatorSlitIn2" label="Monochromator input slit width" group="Monochromator">4.000000</parameter>
          <parameter name="MonochromatorSlitOut2" label="Monochromator output slit width" group="Monochromator">4.000000</parameter>
          <parameter name="MonochromatorStepSize2" label="Monochromator step size" group="Monochromator">0.200000</parameter>
          <parameter name="MonochromatorType2" label="Monochromator type (excitation or emission)" group="Monochromator">8</parameter>
          <parameter name="MonochromatorUpperLimit2" label="Monochromator upper limit" group="Monochromator">950.000000</parameter>
          <parameter name="MonochromatorComment2" label="Monochromator comment" group="Monochromator">Default Monochromator Comment</parameter>
          <parameter name="MonochromatorNumFilter2" label="Monochromator # of filters switched" group="Filter">3</parameter>
          <parameter name="MonochromatorFilter2Wavelength1" label="Monochromator constant/setup wavelength" group="Filter">200.000000</parameter>
          <parameter name="MonochromatorFilter2Wavelength2" label="Monochromator constant/setup wavelength" group="Filter">300.000000</parameter>
          <parameter name="MonochromatorFilter2Wavelength3" label="Monochromator constant/setup wavelength" group="Filter">400.000000</parameter>
          <parameter name="MonochromatorFilter2Comment" label="Monochromator filter comment" group="Filter">Default Filter Comment</parameter>
          <parameter name="MonochromatorPolarizerAngle2" label="Monochromator polarization angle" group="Polarizer">90.000000</parameter>
          <parameter name="MonochromatorPolarizerType2" label="Monochromator polarizer type" group="Polarizer">1</parameter>
          <parameter name="MonochromatorPolarizerComment2" label="Monochromator polarizer comment" group="Polarizer">Default Polarizer Comment</parameter>
          <parameter name="PmtConnection1" label="PMT connection" group="PMT">10</parameter>
          <parameter name="PmtGain1" label="PMT gain" group="PMT">1</parameter>
          <parameter name="PmtHighVolt1" label="PMT high voltage" group="PMT">750</parameter>
          <parameter name="PmtOffset1" label="PMT offset status" group="PMT">0</parameter>
          <parameter name="PmtAcquisition1" label="PMT acquisition type" group="PMT">0</parameter>
          <parameter name="PmtComment1" label="PMT comment" group="PMT">Default Pmt Comment</parameter>
          <parameter name="PmtConnection2" label="PMT connection" group="PMT">10</parameter>
          <parameter name="PmtGain2" label="PMT gain" group="PMT">1</parameter>
          <parameter name="PmtHighVolt2" label="PMT high voltage" group="PMT">750</parameter>
          <parameter name="PmtOffset2" label="PMT offset status" group="PMT">0</parameter>
          <parameter name="PmtAcquisition2" label="PMT acquisition type" group="PMT">1</parameter>
          <parameter name="PmtComment2" label="PMT comment" group="PMT">Default Pmt Comment</parameter>
          <parameter name="InstrumentSpecific3" label="Instrument specific information" group="AUX">2</parameter>
          <parameter name="AuxChannelGain1" label="AUX channel gain" group="AUX">0</parameter>
          <parameter name="AuxChannelOffset1" label="AUX channel offset status" group="AUX">0</parameter>
          <parameter name="AuxChannelComment1" label="AUX channel comment" group="AUX">Default Aux Pmt Comment</parameter>
          <parameter name="AuxChannelGain2" label="AUX channel gain" group="AUX">1</parameter>
          <parameter name="AuxChannelOffset2" label="AUX channel offset status" group="AUX">0</parameter>
          <parameter name="AuxChannelComment2" label="AUX channel comment" group="AUX">Default Aux Pmt Comment</parameter>
          <parameter name="PlotInfo" label="Y-axis plotting annotation" group="YAxis">NULL</parameter>
          <parameter name="Corrected" label="Data corrected flag" group="MANIPULATION">0x0</parameter>
          <parameter name="Derivative" label="Degree of derivation" group="MANIPULATION">0</parameter>
          <parameter name="Norm" label="Normalized flag" group="MANIPULATION">0x0</parameter>
          <parameter name="Offset" label="Offset from the original data" group="MANIPULATION">0.000000</parameter>
          <parameter name="Scale" label="Scale from the original data" group="MANIPULATION">1.000000</parameter>
          <parameter name="Smooth" label="# of smoothing passes done" group="MANIPULATION">0</parameter>
          <parameter name="SmoothType" label="Smoothing type" group="MANIPULATION">1.000000</parameter>
          <values byteorder="INTEL" format="FLOAT32" numvalues="201">AADSPACA1DwAAOY8AIDPPACA7TwAgM88AADrPACAzzwAAPA8AIDUPAAA8DwAgNQ8AIDtPAAA1zwA
APU8AADcPACA9zwAANc8AAD1PAAA4TwAgPc8AADmPAAA+jwAgOg8AAD/PAAA8DwAwAU9AMAFPQBA
DT0AQAg9AAARPQAABz0AQBw9AEASPQBAHD0AABE9AIAiPQBAFz0AwB49AMAPPQAAID0AABE9AAAg
PQDAFD0AgB09AEAcPQAAJT0AQBc9AIAiPQBAEj0AgB09AMAPPQCAHT0AwBQ9AAAgPQBAEj0AgBg9
AAAMPQAAET0AgA49AAAWPQCABD0AABY9AEAIPQDAFD0AQAg9AEAIPQDABT0AgAk9AAACPQCACT0A
APU8AAAHPQCA9zwAAAc9AAD6PAAABz0AgPc8AAAHPQCA9zwAgAQ9AIDyPACABD0AAPA8AIAEPQCA
9zwAAP88AIDtPADAAD0AAPA8AID8PACA6DwAgPc8AIDoPAAA9TwAgOg8AAD/PAAA6zwAgPI8AIDo
PAAA9TwAgOg8AID3PACA4zwAgPc8AIDjPACA8jwAgOM8AAD1PACA6DwAAPU8AIDjPACA9zwAgNk8
AID3PACA3jwAgPI8AADcPACA8jwAgN48AAD1PACA3jwAgPI8AIDZPAAA8DwAANw8AIDtPACA2TwA
APU8AADcPACA8jwAgN48AIDoPAAA3DwAAPA8AADcPACA8jwAANw8AIDyPACA3jwAgPI8AIDZPAAA
6zwAANc8AADwPAAA4TwAgO08AIDePACA7TwAANw8AADwPACA3jwAgO08AADXPAAA8DwAgNQ8AIDt
PACA3jwAAPA8AIDUPACA7TwAgNk8AIDtPAAA3DwAgO08AIDePACA6DwAANI8AADwPAAA1zwAAOs8
AIDZPAAA6zwAANc8AIDtPAAA1zwAAPA8AADXPAAA6zwAANc8AIDtPACA1DwAAPA8AIDZPACA7TwA
ANI8AADrPAAA1zwAAOs8AIDZPACA7TwAgM88AADrPAAA0jwAgO08AADXPACA6DwAANc8AADwPACA
1DwAAOs8
          </values>
        </Ydata>
        <Ydata label="Y" units="UNKNOWN">
          <parameter name="DataTags" label="# of data points with tags" group="YAxis">0</parameter>
          <parameter name="Channel" label="Instrument channel stored" group="YAxis">10</parameter>
          <parameter name="MaxX" label="X-axis of the data set maximum" group="YAxis">348.000000</parameter>
          <parameter name="MaxY" label="Data set maximum" group="YAxis">0.053711</parameter>
          <parameter name="MinX" label="X-axis of the data set minimum" group="YAxis">479.000000</parameter>
          <parameter name="MinY" label="Data set minimum" group="YAxis">0.025635</parameter>
          <parameter name="YaxisType" label="Y-axis type" group="YAxis">0x12 = Intensity</parameter>
          <parameter name="YaxisUnit" label="Y-axis unit" group="YAxis">0x0 = Unknown</parameter>
          <parameter name="YAxisLL" label="Y-axis lower limit" group="YAxis">0.025635</parameter>
          <parameter name="YAxisUL" label="Y-axis upper limit" group="YAxis">0.053711</parameter>
          <parameter name="LabelY" label="Y-axis Label" group="YAxis">Y</parameter>
          <parameter name="AcqStarted" label="Date and Time the acquisition was started" group="Standard">0000-00-00 00:00:00</parameter>
          <parameter name="AcqCompleted" label="Date and Time the acquisition was completed" group="Standard">0000-00-00 00:00:00</parameter>
          <parameter name="ID" label="Acquiring instruments serial (ID) number" group="Standard">0</parameter>
          <parameter name="Instrument" label="Instrument type acquired on" group="Standard">7</parameter>
          <parameter name="Modified" label="Date and Time the acquisition was modified" group="Standard">0000-00-00 00:00:00</parameter>
          <parameter name="OSVersion" label="Operating system version" group="Standard">0.000000</parameter>
          <parameter name="Version" label="Program version (&apos;C&apos;) acquired on" group="Standard">0.000000</parameter>
          <parameter name="Comment" label="Acquisition comment" group="Standard">NULL</parameter>
          <parameter name="AcqTitle" label="Acquisition title" group="Standard">NULL</parameter>
          <parameter name="User" label="User who acquired this data" group="Standard">NULL</parameter>
          <parameter name="Monochromators" label="# of monochromators attached" group="Hardware">2</parameter>
          <parameter name="Pmts" label="# of PMTs attached" group="Hardware">2</parameter>
          <parameter name="Lamp" label="Excitation light source type" group="LAMP">1</parameter>
          <parameter name="LampComment" label="Excitation light source comment" group="LAMP">Default Lamp Comment</parameter>
          <parameter name="MonochromatorAt1" label="Monochromator constant/setup wavelength" group="Monochromator">300.000000</parameter>
          <parameter name="MonochromatorShutter1" label="Monochromator shutter status" group="Monochromator">0</parameter>
          <parameter name="MonochromatorSlitIn1" label="Monochromator input slit width" group="Monochromator">4.000000</parameter>
          <parameter name="MonochromatorSlitOut1" label="Monochromator output slit width" group="Monochromator">4.000000</parameter>
          <parameter name="MonochromatorStepSize1" label="Monochromator step size" group="Monochromator">0.200000</parameter>
          <parameter name="MonochromatorType1" label="Monochromator type (excitation or emission)" group="Monochromator">10</parameter>
          <parameter name="MonochromatorUpperLimit1" label="Monochromator upper limit" group="Monochromator">950.000000</parameter>
          <parameter name="MonochromatorComment1" label="Monochromator comment" group="Monochromator">Default Monochromator Comment</parameter>
          <parameter name="MonochromatorNumFilter1" label="Monochromator # of filters switched" group="Filter">2</parameter>
          <parameter name="MonochromatorFilter1Wavelength1" label="Monochromator constant/setup wavelength" group="Filter">200.000000</parameter>
          <parameter name="MonochromatorFilter1Wavelength2" label="Monochromator constant/setup wavelength" group="Filter">300.000000</parameter>
          <parameter name="MonochromatorFilter1Comment" label="Monochromator filter comment" group="Filter">Default Filter Comment</parameter>
          <parameter name="MonochromatorPolarizerAngle1" label="Monochromator polarization angle" group="Polarizer">0.000000</parameter>
          <parameter name="MonochromatorPolarizerType1" label="Monochromator polarizer type" group="Polarizer">1</parameter>
          <parameter name="MonochromatorPolarizerComment1" label="Monochromator polarizer comment" group="Polarizer">Default Polarizer Comment</parameter>
          <parameter name="MonochromatorAt2" label="Monochromator constant/setup wavelength" group="Monochromator">400.000000</parameter>
          <parameter name="MonochromatorShutter2" label="Monochromator shutter status" group="Monochromator">0</parameter>
          <parameter name="MonochromatorSlitIn2" label="Monochromator input slit width" group="Monochromator">4.000000</parameter>
          <parameter name="MonochromatorSlitOut2" label="Monochromator output slit width" group="Monochromator">4.000000</parameter>
          <parameter name="MonochromatorStepSize2" label="Monochromator step size" group="Monochromator">0.200000</parameter>
          <parameter name="MonochromatorType2" label="Monochromator type (excitation or emission)" group="Monochromator">8</parameter>
          <parameter name="MonochromatorUpperLimit2" label="Monochromator upper limit" group="Monochromator">950.000000</parameter>
          <parameter name="MonochromatorComment2" label="Monochromator comment" group="Monochromator">Default Monochromator Comment</parameter>
          <parameter name="MonochromatorNumFilter2" label="Monochromator # of filters switched" group="Filter">3</parameter>
          <parameter name="MonochromatorFilter2Wavelength1" label="Monochromator constant/setup wavelength" group="Filter">200.000000</parameter>
          <parameter name="MonochromatorFilter2Wavelength2" label="Monochromator constant/setup wavelength" group="Filter">300.000000</parameter>
          <parameter name="MonochromatorFilter2Wavelength3" label="Monochromator constant/setup wavelength" group="Filter">400.000000</parameter>
          <parameter name="MonochromatorFilter2Comment" label="Monochromator filter comment" group="Filter">Default Filter Comment</parameter>
          <parameter name="MonochromatorPolarizerAngle2" label="Monochromator polarization angle" group="Polarizer">90.000000</parameter>
          <parameter name="MonochromatorPolarizerType2" label="Monochromator polarizer type" group="Polarizer">1</parameter>
          <parameter name="MonochromatorPolarizerComment2" label="Monochromator polarizer comment" group="Polarizer">Default Polarizer Comment</parameter>
          <parameter name="PmtConnection1" label="PMT connection" group="PMT">10</parameter>
          <parameter name="PmtGain1" label="PMT gain" group="PMT">1</parameter>
          <parameter name="PmtHighVolt1" label="PMT high voltage" group="PMT">750</parameter>
          <parameter name="PmtOffset1" label="PMT offset status" group="PMT">0</parameter>
          <parameter name="PmtAcquisition1" label="PMT acquisition type" group="PMT">0</parameter>
          <parameter name="PmtComment1" label="PMT comment" group="PMT">Default Pmt Comment</parameter>
          <parameter name="PmtConnection2" label="PMT connection" group="PMT">10</parameter>
          <parameter name="PmtGain2" label="PMT gain" group="PMT">1</parameter>
          <parameter name="PmtHighVolt2" label="PMT high voltage" group="PMT">750</parameter>
          <parameter name="PmtOffset2" label="PMT offset status" group="PMT">0</parameter>
          <parameter name="PmtAcquisition2" label="PMT acquisition type" group="PMT">1</parameter>
          <parameter name="PmtComment2" label="PMT comment" group="PMT">Default Pmt Comment</parameter>
          <parameter name="InstrumentSpecific3" label="Instrument specific information" group="AUX">2</parameter>
          <parameter name="AuxChannelGain1" label="AUX channel gain" group="AUX">0</parameter>
          <parameter name="AuxChannelOffset1" label="AUX channel offset status" group="AUX">0</parameter>
          <parameter name="AuxChannelComment1" label="AUX channel comment" group="AUX">Default Aux Pmt Comment</parameter>
          <parameter name="AuxChannelGain2" label="AUX channel gain" group="AUX">1</parameter>
          <parameter name="AuxChannelOffset2" label="AUX channel offset status" group="AUX">0</parameter>
          <parameter name="AuxChannelComment2" label="AUX channel comment" group="AUX">Default Aux Pmt Comment</parameter>
          <parameter name="PlotInfo" label="Y-axis plotting annotation" group="YAxis">NULL</parameter>
          <parameter name="Corrected" label="Data corrected flag" group="MANIPULATION">0x0</parameter>
          <parameter name="Derivative" label="Degree of derivation" group="MANIPULATION">0</parameter>
          <parameter name="Norm" label="Normalized flag" group="MANIPULATION">0x0</parameter>
          <parameter name="Offset" label="Offset from the original data" group="MANIPULATION">0.000000</parameter>
          <parameter name="Scale" label="Scale from the original data" group="MANIPULATION">1.000000</parameter>
          <parameter name="Smooth" label="# of smoothing passes done" group="MANIPULATION">0</parameter>
          <parameter name="SmoothType" label="Smoothing type" group="MANIPULATION">1.000000</parameter>
          <values byteorder="INTEL" format="FLOAT32" numvalues="201">AADhPACA2TwAAPU8AIDUPACA7TwAANc8AID3PACA1DwAAPA8AIDUPAAA8DwAgNk8AAD1PAAA3DwA
AP88AADhPAAA+jwAgN48AID8PAAA4TwAAP88AADwPAAABz0AgPw8AIAOPQBACD0AwBk9AAAWPQDA
LT0AgCI9AEA6PQCAOz0AAEM9AMA8PQAAUj0AgEA9AEBOPQAAQz0AwEs9AIA2PQAAUj0AQEk9AIBU
PQAAQz0AQE49AIBKPQAASD0AQEQ9AABcPQBAST0AgE89AIBAPQCASj0AwDw9AABDPQAAPj0AAEM9
AAAvPQAAND0AQDA9AAAvPQBAKz0AAC89AMAjPQCALD0AABs9AIAsPQBAHD0AQBc9AMAUPQAAGz0A
ABE9AEAXPQCADj0AgBg9AEANPQBAEj0AAAw9AAAWPQAABz0AwA89AMAFPQDADz0AAAc9AAAMPQDA
BT0AgAk9AMAAPQAADD0AQAM9AEANPQAA/zwAwAU9AAD6PAAABz0AgPc8AMAFPQCA8jwAwAU9AAD1
PAAAAj0AAPU8AAD/PAAA9TwAgAQ9AIDtPABAAz0AAOs8AAD6PACA7TwAgPw8AADmPACA9zwAAOY8
AAD1PACA4zwAAPo8AIDoPADAAD0AgOg8AID8PAAA5jwAAPo8AIDoPACA8jwAAOY8AAD6PAAA4TwA
APU8AIDjPAAA9TwAgOM8AIDyPAAA4TwAAPo8AADhPAAA+jwAgN48AADwPACA4zwAgO08AADhPACA
9zwAANw8AIDtPACA3jwAgO08AIDjPACA6DwAANw8AAD1PAAA4TwAgPc8AADcPAAA8DwAgNk8AADw
PAAA4TwAgPc8AIDZPAAA9TwAANw8AADwPACA2TwAgO08AADcPAAA9TwAANc8AIDyPAAA3DwAgO08
AADcPACA7TwAAOE8AAD1PACA2TwAgPI8AADXPACA7TwAANI8AADwPAAA3DwAAPA8AADXPACA8jwA
ANc8AAD1PAAA1zwAgOg8AADXPACA7TwAANI8AADwPACA1DwAAOs8AIDUPACA7TwAgNQ8AADwPACA
2TwAgO08
          </values>
        </Ydata>
        <Ydata label="Y" units="UNKNOWN">
          <parameter name="DataTags" label="# of data points with tags" group="YAxis">0</parameter>
          <parameter name="Channel" label="Instrument channel stored" group="YAxis">10</parameter>
          <parameter name="MaxX" label="X-axis of the data set maximum" group="YAxis">344.000000</parameter>
          <parameter name="MaxY" label="Data set maximum" group="YAxis">0.073547</parameter>
          <parameter name="MinX" label="X-axis of the data set minimum" group="YAxis">303.000000</parameter>
          <parameter name="MinY" label="Data set minimum" group="YAxis">0.025330</parameter>
          <parameter name="YaxisType" label="Y-axis type" group="YAxis">0x12 = Intensity</parameter>
          <parameter name="YaxisUnit" label="Y-axis unit" group="YAxis">0x0 = Unknown</parameter>
          <parameter name="YAxisLL" label="Y-axis lower limit" group="YAxis">0.025330</parameter>
          <parameter name="YAxisUL" label="Y-axis upper limit" group="YAxis">0.073547</parameter>
          <parameter name="LabelY" label="Y-axis Label" group="YAxis">Y</parameter>
          <parameter name="AcqStarted" label="Date and Time the acquisition was started" group="Standard">0000-00-00 00:00:00</parameter>
          <parameter name="AcqCompleted" label="Date and Time the acquisition was completed" group="Standard">0000-00-00 00:00:00</parameter>
          <parameter name="ID" label="Acquiring instruments serial (ID) number" group="Standard">0</parameter>
          <parameter name="Instrument" label="Instrument type acquired on" group="Standard">7</parameter>
          <parameter name="Modified" label="Date and Time the acquisition was modified" group="Standard">0000-00-00 00:00:00</parameter>
          <parameter name="OSVersion" label="Operating system version" group="Standard">0.000000</parameter>
          <parameter name="Version" label="Program version (&apos;C&apos;) acquired on" group="Standard">0.000000</parameter>
          <parameter name="Comment" label="Acquisition comment" group="Standard">NULL</parameter>
          <parameter name="AcqTitle" label="Acquisition title" group="Standard">NULL</parameter>
          <parameter name="User" label="User who acquired this data" group="Standard">NULL</parameter>
          <parameter name="Monochromators" label="# of monochromators attached" group="Hardware">2</parameter>
          <parameter name="Pmts" label="# of PMTs attached" group="Hardware">2</parameter>
          <parameter name="Lamp" label="Excitation light source type" group="LAMP">1</parameter>
          <parameter name="LampComment" label="Excitation light source comment" group="LAMP">Default Lamp Comment</parameter>
          <parameter name="MonochromatorAt1" label="Monochromator constant/setup wavelength" group="Monochromator">300.000000</parameter>
          <parameter name="MonochromatorShutter1" label="Monochromator shutter status" group="Monochromator">0</parameter>
          <parameter name="MonochromatorSlitIn1" label="Monochromator input slit width" group="Monochromator">4.000000</parameter>
          <parameter name="MonochromatorSlitOut1" label="Monochromator output slit width" group="Monochromator">4.000000</parameter>
          <parameter name="MonochromatorStepSize1" label="Monochromator step size" group="Monochromator">0.200000</parameter>
          <parameter name="MonochromatorType1" label="Monochromator type (excitation or emission)" group="Monochromator">10</parameter>
          <parameter name="MonochromatorUpperLimit1" label="Monochromator upper limit" group="Monochromator">950.000000</parameter>
          <parameter name="MonochromatorComment1" label="Monochromator comment" group="Monochromator">Default Monochromator Comment</parameter>
          <parameter name="MonochromatorNumFilter1" label="Monochromator # of filters switched" group="Filter">2</parameter>
          <parameter name="MonochromatorFilter1Wavelength1" label="Monochromator constant/setup wavelength" group="Filter">200.000000</parameter>
          <parameter name="MonochromatorFilter1Wavelength2" label="Monochromator constant/setup wavelength" group="Filter">300.000000</parameter>
          <parameter name="MonochromatorFilter1Comment" label="Monochromator filter comment" group="Filter">Default Filter Comment</parameter>
          <parameter name="MonochromatorPolarizerAngle1" label="Monochromator polarization angle" group="Polarizer">0.000000</parameter>
          <parameter name="MonochromatorPolarizerType1" label="Monochromator polarizer type" group="Polarizer">1</parameter>
          <parameter name="MonochromatorPolarizerComment1" label="Monochromator polarizer comment" group="Polarizer">Default Polarizer Comment</parameter>
          <parameter name="MonochromatorAt2" label="Monochromator constant/setup wavelength" group="Monochromator">400.000000</parameter>
          <parameter name="MonochromatorShutter2" label="Monochromator shutter status" group="Monochromator">0</parameter>
          <parameter name="MonochromatorSlitIn2" label="Monochromator input slit width" group="Monochromator">4.000000</parameter>
          <parameter name="MonochromatorSlitOut2" label="Monochromator output slit width" group="Monochromator">4.000000</parameter>
          <parameter name="MonochromatorStepSize2" label="Monochromator step size" group="Monochromator">0.200000</parameter>
          <parameter name="MonochromatorType2" label="Monochromator type (excitation or emission)" group="Monochromator">8</parameter>
          <parameter name="MonochromatorUpperLimit2" label="Monochromator upper limit" group="Monochromator">950.000000</parameter>
          <parameter name="MonochromatorComment2" label="Monochromator comment" group="Monochromator">Default Monochromator Comment</parameter>
          <parameter name="MonochromatorNumFilter2" label="Monochromator # of filters switched" group="Filter">3</parameter>
          <parameter name="MonochromatorFilter2Wavelength1" label="Monochromator constant/setup wavelength" group="Filter">200.000000</parameter>
          <parameter name="MonochromatorFilter2Wavelength2" label="Monochromator constant/setup wavelength" group="Filter">300.000000</parameter>
          <parameter name="MonochromatorFilter2Wavelength3" label="Monochromator constant/setup wavelength" group="Filter">400.000000</parameter>
          <parameter name="MonochromatorFilter2Comment" label="Monochromator filter comment" group="Filter">Default Filter Comment</parameter>
          <parameter name="MonochromatorPolarizerAngle2" label="Monochromator polarization angle" group="Polarizer">90.000000</parameter>
          <parameter name="MonochromatorPolarizerType2" label="Monochromator polarizer type" group="Polarizer">1</parameter>
          <parameter name="MonochromatorPolarizerComment2" label="Monochromator polarizer comment" group="Polarizer">Default Polarizer Comment</parameter>
          <parameter name="PmtConnection1" label="PMT connection" group="PMT">10</parameter>
          <parameter name="PmtGain1" label="PMT gain" group="PMT">1</parameter>
          <parameter name="PmtHighVolt1" label="PMT high voltage" group="PMT">750</parameter>
          <parameter name="PmtOffset1" label="PMT offset status" group="PMT">0</parameter>
          <parameter name="PmtAcquisition1" label="PMT acquisition type" group="PMT">0</parameter>
          <parameter name="PmtComment1" label="PMT comment" group="PMT">Default Pmt Comment</parameter>
          <parameter name="PmtConnection2" label="PMT connection" group="PMT">10</parameter>
          <parameter name="PmtGain2" label="PMT gain" group="PMT">1</parameter>
          <parameter name="PmtHighVolt2" label="PMT high voltage" group="PMT">750</parameter>
          <parameter name="PmtOffset2" label="PMT offset status" group="PMT">0</parameter>
          <parameter name="PmtAcquisition2" label="PMT acquisition type" group="PMT">1</parameter>
          <parameter name="PmtComment2" label="PMT comment" group="PMT">Default Pmt Comment</parameter>
          <parameter name="InstrumentSpecific3" label="Instrument specific information" group="AUX">2</parameter>
          <parameter name="AuxChannelGain1" label="AUX channel gain" group="AUX">0</parameter>
          <parameter name="AuxChannelOffset1" label="AUX channel offset status" group="AUX">0</parameter>
          <parameter name="AuxChannelComment1" label="AUX channel comment" group="AUX">Default Aux Pmt Comment</parameter>
          <parameter name="AuxChannelGain2" label="AUX channel gain" group="AUX">1</parameter>
          <parameter name="AuxChannelOffset2" label="AUX channel offset status" group="AUX">0</parameter>
          <parameter name="AuxChannelComment2" label="AUX channel comment" group="AUX">Default Aux Pmt Comment</parameter>
          <parameter name="PlotInfo" label="Y-axis plotting annotation" group="YAxis">NULL</parameter>
          <parameter name="Corrected" label="Data corrected flag" group="MANIPULATION">0x0</parameter>
          <parameter name="Derivative" label="Degree of derivation" group="MANIPULATION">0</parameter>
          <parameter name="Norm" label="Normalized flag" group="MANIPULATION">0x0</parameter>
          <parameter name="Offset" label="Offset from the original data" group="MANIPULATION">0.000000</parameter>
          <parameter name="Scale" label="Scale from the original data" group="MANIPULATION">1.000000</parameter>
          <parameter name="Smooth" label="# of smoothing passes done" group="MANIPULATION">0</parameter>
          <parameter name="SmoothType" label="Smoothing type" group="MANIPULATION">1.000000</parameter>
          <values byteorder="INTEL" format="FLOAT32" numvalues="201">AADSPACA2TwAgOg8AIDPPACA6DwAANI8AADrPACA1DwAgOg8AADXPAAA8DwAgNk8AIDyPAAA3DwA
gPw8AADmPACA/DwAgOM8AAD/PACA6DwAAAI9AMAAPQAADD0AQAg9AMAZPQAAGz0AgEA9AIBAPQAA
XD0AAGs9AEB7PQCAdz0AYIs9AICJPQCgkT0AAIw9AOCNPQCAiT0AIIo9AKCMPQCAjj0AgIk9AECS
PQCgjD0AoJY9AOCSPQCglj0AAJE9AACWPQAAkT0AgJM9AACHPQDAij0A4Ig9AICJPQDAcz0AYIE9
AIB3PQCAbT0AwGQ9AEBnPQCAWT0AQFg9AEBTPQCATz0AwEY9AMBGPQCANj0AgDY9AIAxPQBANT0A
ACo9AIAsPQCAIj0AwC09AEAcPQDAKD0AwB49AAAlPQCAHT0AQCY9AEAXPQCAIj0AgBM9AMAZPQDA
GT0AQBc9AAARPQCAEz0AQA09AAARPQBAAz0AAAw9AMAFPQBADT0AQAM9AAAMPQAAAj0AQAg9AEAD
PQCACT0AwAA9AEAIPQAA/zwAQAg9AID8PADABT0AgPw8AAACPQCA9zwAAAc9AIDyPAAA/zwAAPU8
AMAAPQCA9zwAAP88AIDyPAAAAj0AAPA8AMAAPQCA6DwAgPc8AIDtPACA9zwAAOY8AAD/PACA7TwA
gPc8AIDePACA/DwAgOM8AAD1PAAA5jwAgPc8AADhPAAA9TwAgOM8AAD1PAAA4TwAAPU8AADhPAAA
8DwAAOY8AIDyPAAA5jwAAPU8AADhPACA8jwAgOM8AAD1PAAA4TwAAPA8AIDZPACA8jwAgN48AIDy
PACA4zwAgO08AADXPACA9zwAgN48AADwPACA3jwAgO08AADcPAAA8DwAANw8AAD1PACA2TwAgPI8
AADXPACA7TwAgN48AADwPAAA1zwAgPI8AADSPAAA6zwAgNk8AIDtPAAA1zwAgPI8AADXPAAA6zwA
gNk8AADwPAAA1zwAgOg8AIDZPACA8jwAANw8AIDtPAAA1zwAgOg8AIDZPACA7TwAANc8AADrPAAA
1zwAgPI8
          </values>
        </Ydata>
        <Ydata label="Y" units="UNKNOWN">
          <parameter name="DataTags" label="# of data points with tags" group="YAxis">0</parameter>
          <parameter name="Channel" label="Instrument channel stored" group="YAxis">10</parameter>
          <parameter name="MaxX" label="X-axis of the data set maximum" group="YAxis">349.000000</parameter>
          <parameter name="MaxY" label="Data set maximum" group="YAxis">0.109558</parameter>
          <parameter name="MinX" label="X-axis of the data set minimum" group="YAxis">303.000000</parameter>
          <parameter name="MinY" label="Data set minimum" group="YAxis">0.025940</parameter>
          <parameter name="YaxisType" label="Y-axis type" group="YAxis">0x12 = Intensity</parameter>
          <parameter name="YaxisUnit" label="Y-axis unit" group="YAxis">0x0 = Unknown</parameter>
          <parameter name="YAxisLL" label="Y-axis lower limit" group="YAxis">0.025940</parameter>
          <parameter name="YAxisUL" label="Y-axis upper limit" group="YAxis">0.109558</parameter>
          <parameter name="LabelY" label="Y-axis Label" group="YAxis">Y</parameter>
          <parameter name="AcqStarted" label="Date and Time the acquisition was started" group="Standard">0000-00-00 00:00:00</parameter>
          <parameter name="AcqCompleted" label="Date and Time the acquisition was completed" group="Standard">0000-00-00 00:00:00</parameter>
          <parameter name="ID" label="Acquiring instruments serial (ID) number" group="Standard">0</parameter>
          <parameter name="Instrument" label="Instrument type acquired on" group="Standard">7</parameter>
          <parameter name="Modified" label="Date and Time the acquisition was modified" group="Standard">0000-00-00 00:00:00</parameter>
          <parameter name="OSVersion" label="Operating system version" group="Standard">0.000000</parameter>
          <parameter name="Version" label="Program version (&apos;C&apos;) acquired on" group="Standard">0.000000</parameter>
          <parameter name="Comment" label="Acquisition comment" group="Standard">NULL</parameter>
          <parameter name="AcqTitle" label="Acquisition title" group="Standard">NULL</parameter>
          <parameter name="User" label="User who acquired this data" group="Standard">NULL</parameter>
          <parameter name="Monochromators" label="# of monochromators attached" group="Hardware">2</parameter>
          <parameter name="Pmts" label="# of PMTs attached" group="Hardware">2</parameter>
          <parameter name="Lamp" label="Excitation light source type" group="LAMP">1</parameter>
          <parameter name="LampComment" label="Excitation light source comment" group="LAMP">Default Lamp Comment</parameter>
          <parameter name="MonochromatorAt1" label="Monochromator constant/setup wavelength" group="Monochromator">300.000000</parameter>
          <parameter name="MonochromatorShutter1" label="Monochromator shutter status" group="Monochromator">0</parameter>
          <parameter name="MonochromatorSlitIn1" label="Monochromator input slit width" group="Monochromator">4.000000</parameter>
          <parameter name="MonochromatorSlitOut1" label="Monochromator output slit width" group="Monochromator">4.000000</parameter>
          <parameter name="MonochromatorStepSize1" label="Monochromator step size" group="Monochromator">0.200000</parameter>
          <parameter name="MonochromatorType1" label="Monochromator type (excitation or emission)" group="Monochromator">10</parameter>
          <parameter name="MonochromatorUpperLimit1" label="Monochromator upper limit" group="Monochromator">950.000000</parameter>
          <parameter name="MonochromatorComment1" label="Monochromator comment" group="Monochromator">Default Monochromator Comment</parameter>
          <parameter name="MonochromatorNumFilter1" label="Monochromator # of filters switched" group="Filter">2</parameter>
          <parameter name="MonochromatorFilter1Wavelength1" label="Monochromator constant/setup wavelength" group="Filter">200.000000</parameter>
          <parameter name="MonochromatorFilter1Wavelength2" label="Monochromator constant/setup wavelength" group="Filter">300.000000</parameter>
          <parameter name="MonochromatorFilter1Comment" label="Monochromator filter comment" group="Filter">Default Filter Comment</parameter>
          <parameter name="MonochromatorPolarizerAngle1" label="Monochromator polarization angle" group="Polarizer">0.000000</parameter>
          <parameter name="MonochromatorPolarizerType1" label="Monochromator polarizer type" group="Polarizer">1</parameter>
          <parameter name="MonochromatorPolarizerComment1" label="Monochromator polarizer comment" group="Polarizer">Default Polarizer Comment</parameter>
          <parameter name="MonochromatorAt2" label="Monochromator constant/setup wavelength" group="Monochromator">400.000000</parameter>
          <parameter name="MonochromatorShutter2" label="Monochromator shutter status" group="Monochromator">0</parameter>
          <parameter name="MonochromatorSlitIn2" label="Monochromator input slit width" group="Monochromator">4.000000</parameter>
          <parameter name="MonochromatorSlitOut2" label="Monochromator output slit width" group="Monochromator">4.000000</parameter>
          <parameter name="MonochromatorStepSize2" label="Monochromator step size" group="Monochromator">0.200000</parameter>
          <parameter name="MonochromatorType2" label="Monochromator type (excitation or emission)" group="Monochromator">8</parameter>
          <parameter name="MonochromatorUpperLimit2" label="Monochromator upper limit" group="Monochromator">950.000000</parameter>
          <parameter name="MonochromatorComment2" label="Monochromator comment" group="Monochromator">Default Monochromator Comment</parameter>
          <parameter name="MonochromatorNumFilter2" label="Monochromator # of filters switched" group="Filter">3</parameter>
          <parameter name="MonochromatorFilter2Wavelength1" label="Monochromator constant/setup wavelength" group="Filter">200.000000</parameter>
          <parameter name="MonochromatorFilter2Wavelength2" label="Monochromator constant/setup wavelength" group="Filter">300.000000</parameter>
          <parameter name="MonochromatorFilter2Wavelength3" label="Monochromator constant/setup wavelength" group="Filter">400.000000</parameter>
          <parameter name="MonochromatorFilter2Comment" label="Monochromator filter comment" group="Filter">Default Filter Comment</parameter>
          <parameter name="MonochromatorPolarizerAngle2" label="Monochromator polarization angle" group="Polarizer">90.000000</parameter>
          <parameter name="MonochromatorPolarizerType2" label="Monochromator polarizer type" group="Polarizer">1</parameter>
          <parameter name="MonochromatorPolarizerComment2" label="Monochromator polarizer comment" group="Polarizer">Default Polarizer Comment</parameter>
          <parameter name="PmtConnection1" label="PMT connection" group="PMT">10</parameter>
          <parameter name="PmtGain1" label="PMT gain" group="PMT">1</parameter>
          <parameter name="PmtHighVolt1" label="PMT high voltage" group="PMT">750</parameter>
          <parameter name="PmtOffset1" label="PMT offset status" group="PMT">0</parameter>
          <parameter name="PmtAcquisition1" label="PMT acquisition type" group="PMT">0</parameter>
          <parameter name="PmtComment1" label="PMT comment" group="PMT">Default Pmt Comment</parameter>
          <parameter name="PmtConnection2" label="PMT connection" group="PMT">10</parameter>
          <parameter name="PmtGain2" label="PMT gain" group="PMT">1</parameter>
          <parameter name="PmtHighVolt2" label="PMT high voltage" group="PMT">750</parameter>
          <parameter name="PmtOffset2" label="PMT offset status" group="PMT">0</parameter>
          <parameter name="PmtAcquisition2" label="PMT acquisition type" group="PMT">1</parameter>
          <parameter name="PmtComment2" label="PMT comment" group="PMT">Default Pmt Comment</parameter>
          <parameter name="InstrumentSpecific3" label="Instrument specific information" group="AUX">2</parameter>
          <parameter name="AuxChannelGain1" label="AUX channel gain" group="AUX">0</parameter>
          <parameter name="AuxChannelOffset1" label="AUX channel offset status" group="AUX">0</parameter>
          <parameter name="AuxChannelComment1" label="AUX channel comment" group="AUX">Default Aux Pmt Comment</parameter>
          <parameter name="AuxChannelGain2" label="AUX channel gain" group="AUX">1</parameter>
          <parameter name="AuxChannelOffset2" label="AUX channel offset status" group="AUX">0</parameter>
          <parameter name="AuxChannelComment2" label="AUX channel comment" group="AUX">Default Aux Pmt Comment</parameter>
          <parameter name="PlotInfo" label="Y-axis plotting annotation" group="YAxis">NULL</parameter>
          <parameter name="Corrected" label="Data corrected flag" group="MANIPULATION">0x0</parameter>
          <parameter name="Derivative" label="Degree of derivation" group="MANIPULATION">0</parameter>
          <parameter name="Norm" label="Normalized flag" group="MANIPULATION">0x0</parameter>
          <parameter name="Offset" label="Offset from the original data" group="MANIPULATION">0.000000</parameter>
          <parameter name="Scale" label="Scale from the original data" group="MANIPULATION">1.000000</parameter>
          <parameter name="Smooth" label="# of smoothing passes done" group="MANIPULATION">0</parameter>
          <parameter name="SmoothType" label="Smoothing type" group="MANIPULATION">1.000000</parameter>
          <values byteorder="INTEL" format="FLOAT32" numvalues="201">AIDePAAA3DwAgO08AIDUPAAA8DwAANc8AIDtPACA1DwAAPA8AIDZPAAA9TwAANw8AID3PACA4zwA
APo8AADmPAAA/zwAAOY8AMAAPQCA8jwAgAk9AAD6PAAADD0AgA49AIAnPQAAND0AgFk9AABhPQCg
gj0A4JI9ACCoPQBAsD0A4L89AOC/PQAAzT0AwME9AODEPQCAwD0AAM09AODEPQBgzD0AoMg9AADN
PQCAyj0AANc9AGDWPQDA3z0AwNo9AADSPQBg4D0AwNU9AKDNPQCgzT0AQL89AADDPQCgrz0AwLI9
AACqPQCgqj0AoKA9AEChPQBgmj0AQJI9AOCIPQDgjT0A4IM9ACCFPQCAdz0AAHU9AABhPQCAcj0A
QF09AABcPQDASz0AQFg9AMBGPQAATT0AgEA9AIBFPQBAOj0AQD89AMA8PQBAPz0AwDw9AEA6PQDA
LT0AQDA9AMAoPQAAKj0AgCI9AMAyPQBAJj0AwCM9AEAXPQBAJj0AABY9AAAbPQCAGD0AABs9AMAP
PQCAGD0AwA89AAARPQBACD0AwBQ9AEADPQAADD0AAAc9AEAIPQBAAz0AgAk9AAD/PAAABz0AAAI9
AAAHPQAA/zwAwAo9AAD6PADABT0AgPI8AIAEPQAA+jwAwAA9AID3PADAAD0AgPw8AEADPQCA7TwA
wAA9AIDtPAAAAj0AgO08AAD6PAAA6zwAwAA9AADrPACA/DwAgO08AID8PACA7TwAAP88AADrPACA
9zwAgOM8AAD6PAAA8DwAAPU8AIDoPAAA9TwAgOg8AAD6PAAA5jwAgPI8AIDjPACA9zwAAOE8AAD6
PAAA5jwAAPA8AIDjPAAA9TwAAOE8AAD6PAAA5jwAgPI8AIDePACA8jwAgN48AADwPACA4zwAgPI8
AADcPAAA8DwAgN48AADwPAAA3DwAgPI8AIDePACA8jwAgN48AIDtPAAA3DwAgPI8AIDZPACA7TwA
gNk8AIDyPAAA3DwAgPI8AIDePACA7TwAANw8AADrPAAA1zwAgO08AADcPACA7TwAgNk8AIDtPACA
2TwAAPA8
          </values>
        </Ydata>
        <Ydata label="Y" units="UNKNOWN">
          <parameter name="DataTags" label="# of data points with tags" group="YAxis">0</parameter>
          <parameter name="Channel" label="Instrument channel stored" group="YAxis">10</parameter>
          <parameter name="MaxX" label="X-axis of the data set maximum" group="YAxis">348.000000</parameter>
          <parameter name="MaxY" label="Data set maximum" group="YAxis">0.164490</parameter>
          <parameter name="MinX" label="X-axis of the data set minimum" group="YAxis">303.000000</parameter>
          <parameter name="MinY" label="Data set minimum" group="YAxis">0.025635</parameter>
          <parameter name="YaxisType" label="Y-axis type" group="YAxis">0x12 = Intensity</parameter>
          <parameter name="YaxisUnit" label="Y-axis unit" group="YAxis">0x0 = Unknown</parameter>
          <parameter name="YAxisLL" label="Y-axis lower limit" group="YAxis">0.025635</parameter>
          <parameter name="YAxisUL" label="Y-axis upper limit" group="YAxis">0.164490</parameter>
          <parameter name="LabelY" label="Y-axis Label" group="YAxis">Y</parameter>
          <parameter name="AcqStarted" label="Date and Time the acquisition was started" group="Standard">0000-00-00 00:00:00</parameter>
          <parameter name="AcqCompleted" label="Date and Time the acquisition was completed" group="Standard">0000-00-00 00:00:00</parameter>
          <parameter name="ID" label="Acquiring instruments serial (ID) number" group="Standard">0</parameter>
          <parameter name="Instrument" label="Instrument type acquired on" group="Standard">7</parameter>
          <parameter name="Modified" label="Date and Time the acquisition was modified" group="Standard">0000-00-00 00:00:00</parameter>
          <parameter name="OSVersion" label="Operating system version" group="Standard">0.000000</parameter>
          <parameter name="Version" label="Program version (&apos;C&apos;) acquired on" group="Standard">0.000000</parameter>
          <parameter name="Comment" label="Acquisition comment" group="Standard">NULL</parameter>
          <parameter name="AcqTitle" label="Acquisition title" group="Standard">NULL</parameter>
          <parameter name="User" label="User who acquired this data" group="Standard">NULL</parameter>
          <parameter name="Monochromators" label="# of monochromators attached" group="Hardware">2</parameter>
          <parameter name="Pmts" label="# of PMTs attached" group="Hardware">2</parameter>
          <parameter name="Lamp" label="Excitation light source type" group="LAMP">1</parameter>
          <parameter name="LampComment" label="Excitation light source comment" group="LAMP">Default Lamp Comment</parameter>
          <parameter name="MonochromatorAt1" label="Monochromator constant/setup wavelength" group="Monochromator">300.000000</parameter>
          <parameter name="MonochromatorShutter1" label="Monochromator shutter status" group="Monochromator">0</parameter>
          <parameter name="MonochromatorSlitIn1" label="Monochromator input slit width" group="Monochromator">4.000000</parameter>
          <parameter name="MonochromatorSlitOut1" label="Monochromator output slit width" group="Monochromator">4.000000</parameter>
          <parameter name="MonochromatorStepSize1" label="Monochromator step size" group="Monochromator">0.200000</parameter>
          <parameter name="MonochromatorType1" label="Monochromator type (excitation or emission)" group="Monochromator">10</parameter>
          <parameter name="MonochromatorUpperLimit1" label="Monochromator upper limit" group="Monochromator">950.000000</parameter>
          <parameter name="MonochromatorComment1" label="Monochromator comment" group="Monochromator">Default Monochromator Comment</parameter>
          <parameter name="MonochromatorNumFilter1" label="Monochromator # of filters switched" group="Filter">2</parameter>
          <parameter name="MonochromatorFilter1Wavelength1" label="Monochromator constant/setup wavelength" group="Filter">200.000000</parameter>
          <parameter name="MonochromatorFilter1Wavelength2" label="Monochromator constant/setup wavelength" group="Filter">300.000000</parameter>
          <parameter name="MonochromatorFilter1Comment" label="Monochromator filter comment" group="Filter">Default Filter Comment</parameter>
          <parameter name="MonochromatorPolarizerAngle1" label="Monochromator polarization angle" group="Polarizer">0.000000</parameter>
          <parameter name="MonochromatorPolarizerType1" label="Monochromator polarizer type" group="Polarizer">1</parameter>
          <parameter name="MonochromatorPolarizerComment1" label="Monochromator polarizer comment" group="Polarizer">Default Polarizer Comment</parameter>
          <parameter name="MonochromatorAt2" label="Monochromator constant/setup wavelength" group="Monochromator">400.000000</parameter>
          <parameter name="MonochromatorShutter2" label="Monochromator shutter status" group="Monochromator">0</parameter>
          <parameter name="MonochromatorSlitIn2" label="Monochromator input slit width" group="Monochromator">4.000000</parameter>
          <parameter name="MonochromatorSlitOut2" label="Monochromator output slit width" group="Monochromator">4.000000</parameter>
          <parameter name="MonochromatorStepSize2" label="Monochromator step size" group="Monochromator">0.200000</parameter>
          <parameter name="MonochromatorType2" label="Monochromator type (excitation or emission)" group="Monochromator">8</parameter>
          <parameter name="MonochromatorUpperLimit2" label="Monochromator upper limit" group="Monochromator">950.000000</parameter>
          <parameter name="MonochromatorComment2" label="Monochromator comment" group="Monochromator">Default Monochromator Comment</parameter>
          <parameter name="MonochromatorNumFilter2" label="Monochromator # of filters switched" group="Filter">3</parameter>
          <parameter name="MonochromatorFilter2Wavelength1" label="Monochromator constant/setup wavelength" group="Filter">200.000000</parameter>
          <parameter name="MonochromatorFilter2Wavelength2" label="Monochromator constant/setup wavelength" group="Filter">300.000000</parameter>
          <parameter name="MonochromatorFilter2Wavelength3" label="Monochromator constant/setup wavelength" group="Filter">400.000000</parameter>
          <parameter name="MonochromatorFilter2Comment" label="Monochromator filter comment" group="Filter">Default Filter Comment</parameter>
          <parameter name="MonochromatorPolarizerAngle2" label="Monochromator polarization angle" group="Polarizer">90.000000</parameter>
          <parameter name="MonochromatorPolarizerType2" label="Monochromator polarizer type" group="Polarizer">1</parameter>
          <parameter name="MonochromatorPolarizerComment2" label="Monochromator polarizer comment" group="Polarizer">Default Polarizer Comment</parameter>
          <parameter name="PmtConnection1" label="PMT connection" group="PMT">10</parameter>
          <parameter name="PmtGain1" label="PMT gain" group="PMT">1</parameter>
          <parameter name="PmtHighVolt1" label="PMT high voltage" group="PMT">750</parameter>
          <parameter name="PmtOffset1" label="PMT offset status" group="PMT">0</parameter>
          <parameter name="PmtAcquisition1" label="PMT acquisition type" group="PMT">0</parameter>
          <parameter name="PmtComment1" label="PMT comment" group="PMT">Default Pmt Comment</parameter>
          <parameter name="PmtConnection2" label="PMT connection" group="PMT">10</parameter>
          <parameter name="PmtGain2" label="PMT gain" group="PMT">1</parameter>
          <parameter name="PmtHighVolt2" label="PMT high voltage" group="PMT">750</parameter>
          <parameter name="PmtOffset2" label="PMT offset status" group="PMT">0</parameter>
          <parameter name="PmtAcquisition2" label="PMT acquisition type" group="PMT">1</parameter>
          <parameter name="PmtComment2" label="PMT comment" group="PMT">Default Pmt Comment</parameter>
          <parameter name="InstrumentSpecific3" label="Instrument specific information" group="AUX">2</parameter>
          <parameter name="AuxChannelGain1" label="AUX channel gain" group="AUX">0</parameter>
          <parameter name="AuxChannelOffset1" label="AUX channel offset status" group="AUX">0</parameter>
          <parameter name="AuxChannelComment1" label="AUX channel comment" group="AUX">Default Aux Pmt Comment</parameter>
          <parameter name="AuxChannelGain2" label="AUX channel gain" group="AUX">1</parameter>
          <parameter name="AuxChannelOffset2" label="AUX channel offset status" group="AUX">0</parameter>
          <parameter name="AuxChannelComment2" label="AUX channel comment" group="AUX">Default Aux Pmt Comment</parameter>
          <parameter name="PlotInfo" label="Y-axis plotting annotation" group="YAxis">NULL</parameter>
          <parameter name="Corrected" label="Data corrected flag" group="MANIPULATION">0x0</parameter>
          <parameter name="Derivative" label="Degree of derivation" group="MANIPULATION">0</parameter>
          <parameter name="Norm" label="Normalized flag" group="MANIPULATION">0x0</parameter>
          <parameter name="Offset" label="Offset from the original data" group="MANIPULATION">0.000000</parameter>
          <parameter name="Scale" label="Scale from the original data" group="MANIPULATION">1.000000</parameter>
          <parameter name="Smooth" label="# of smoothing passes done" group="MANIPULATION">0</parameter>
          <parameter name="SmoothType" label="Smoothing type" group="MANIPULATION">1.000000</parameter>
          <values byteorder="INTEL" format="FLOAT32" numvalues="201">AIDUPACA1DwAgO08AADSPACA8jwAANI8AADwPACA1DwAgPI8AIDZPAAA8DwAgNk8AID8PAAA4TwA
AAI9AIDoPADABT0AAPA8AEAIPQCA9zwAAAw9AMAFPQDAFD0AQBI9AAA0PQAALz0AgF49ACCAPQAA
mz0A4Ks9AMDVPQCA6D0AoAI+AKAHPgDwET4AMBM+AMAZPgCQFz4AYBo+ANATPgBQFj4AUBY+AMAU
PgBAEj4AEB8+AOAcPgCQHD4AIB4+AHAoPgAAJT4AgCc+AKAlPgBAJj4AkBc+AAAbPgCwCz4AMA4+
AHAFPgCgBz4AoPU9AIDtPQCA6D0AIOQ9AODdPQBA0z0AwNA9AIDPPQCAuz0AoL49AMCyPQDgqz0A
4KY9AAClPQBglT0A4Jc9AACWPQAglD0AYJA9AECNPQCghz0A4Ig9AECDPQDAij0AIIA9AIB3PQBg
gT0AgHw9AEBnPQCAdz0AAGs9AABrPQDAXz0AwFo9AABSPQCAVD0AgEU9AABSPQBAPz0AAE09AEA/
PQDAPD0AQDo9AMBBPQAAND0AwEE9AIAnPQBAMD0AACU9AMAtPQCAHT0AwCg9AEAXPQBAJj0AgBg9
AMAePQAAGz0AwB49AAAWPQAAGz0AwA89AMAUPQBADT0AgBg9AEASPQCADj0AQA09AMAUPQDADz0A
ABE9AMAFPQCADj0AQAM9AMAPPQDABT0AAAw9AID3PABADT0AAAI9AAAMPQCA/DwAgAk9AIAEPQAA
DD0AAAI9AIAJPQCA9zwAwAo9AID3PAAABz0AgPc8AAAHPQCA8jwAQAM9AAD6PAAAAj0AgO08AAD/
PAAA9TwAAAI9AIDtPADAAD0AAOs8AEADPQAA6zwAAP88AIDyPACA/DwAgOg8AMAAPQAA5jwAAAI9
AIDoPACA/DwAAOY8AAD/PACA3jwAAPo8AADmPAAA+jwAgOg8AAD1PAAA5jwAwAA9AIDjPACA/DwA
AOE8AAD/PACA2TwAAPU8AADmPACA/DwAANw8AID8PACA4zwAAPU8AADhPACA9zwAAOE8AID3PAAA
4TwAgPc8
          </values>
        </Ydata>
        <Ydata label="Y" units="UNKNOWN">
          <parameter name="DataTags" label="# of data points with tags" group="YAxis">0</parameter>
          <parameter name="Channel" label="Instrument channel stored" group="YAxis">10</parameter>
          <parameter name="MaxX" label="X-axis of the data set maximum" group="YAxis">354.000000</parameter>
          <parameter name="MaxY" label="Data set maximum" group="YAxis">0.169678</parameter>
          <parameter name="MinX" label="X-axis of the data set minimum" group="YAxis">305.000000</parameter>
          <parameter name="MinY" label="Data set minimum" group="YAxis">0.026245</parameter>
          <parameter name="YaxisType" label="Y-axis type" group="YAxis">0x12 = Intensity</parameter>
          <parameter name="YaxisUnit" label="Y-axis unit" group="YAxis">0x0 = Unknown</parameter>
          <parameter name="YAxisLL" label="Y-axis lower limit" group="YAxis">0.026245</parameter>
          <parameter name="YAxisUL" label="Y-axis upper limit" group="YAxis">0.169678</parameter>
          <parameter name="LabelY" label="Y-axis Label" group="YAxis">Y</parameter>
          <parameter name="AcqStarted" label="Date and Time the acquisition was started" group="Standard">0000-00-00 00:00:00</parameter>
          <parameter name="AcqCompleted" label="Date and Time the acquisition was completed" group="Standard">0000-00-00 00:00:00</parameter>
          <parameter name="ID" label="Acquiring instruments serial (ID) number" group="Standard">0</parameter>
          <parameter name="Instrument" label="Instrument type acquired on" group="Standard">7</parameter>
          <parameter name="Modified" label="Date and Time the acquisition was modified" group="Standard">0000-00-00 00:00:00</parameter>
          <parameter name="OSVersion" label="Operating system version" group="Standard">0.000000</parameter>
          <parameter name="Version" label="Program version (&apos;C&apos;) acquired on" group="Standard">0.000000</parameter>
          <parameter name="Comment" label="Acquisition comment" group="Standard">NULL</parameter>
          <parameter name="AcqTitle" label="Acquisition title" group="Standard">NULL</parameter>
          <parameter name="User" label="User who acquired this data" group="Standard">NULL</parameter>
          <parameter name="Monochromators" label="# of monochromators attached" group="Hardware">2</parameter>
          <parameter name="Pmts" label="# of PMTs attached" group="Hardware">2</parameter>
          <parameter name="Lamp" label="Excitation light source type" group="LAMP">1</parameter>
          <parameter name="LampComment" label="Excitation light source comment" group="LAMP">Default Lamp Comment</parameter>
          <parameter name="MonochromatorAt1" label="Monochromator constant/setup wavelength" group="Monochromator">300.000000</parameter>
          <parameter name="MonochromatorShutter1" label="Monochromator shutter status" group="Monochromator">0</parameter>
          <parameter name="MonochromatorSlitIn1" label="Monochromator input slit width" group="Monochromator">4.000000</parameter>
          <parameter name="MonochromatorSlitOut1" label="Monochromator output slit width" group="Monochromator">4.000000</parameter>
          <parameter name="MonochromatorStepSize1" label="Monochromator step size" group="Monochromator">0.200000</parameter>
          <parameter name="MonochromatorType1" label="Monochromator type (excitation or emission)" group="Monochromator">10</parameter>
          <parameter name="MonochromatorUpperLimit1" label="Monochromator upper limit" group="Monochromator">950.000000</parameter>
          <parameter name="MonochromatorComment1" label="Monochromator comment" group="Monochromator">Default Monochromator Comment</parameter>
          <parameter name="MonochromatorNumFilter1" label="Monochromator # of filters switched" group="Filter">2</parameter>
          <parameter name="MonochromatorFilter1Wavelength1" label="Monochromator constant/setup wavelength" group="Filter">200.000000</parameter>
          <parameter name="MonochromatorFilter1Wavelength2" label="Monochromator constant/setup wavelength" group="Filter">300.000000</parameter>
          <parameter name="MonochromatorFilter1Comment" label="Monochromator filter comment" group="Filter">Default Filter Comment</parameter>
          <parameter name="MonochromatorPolarizerAngle1" label="Monochromator polarization angle" group="Polarizer">0.000000</parameter>
          <parameter name="MonochromatorPolarizerType1" label="Monochromator polarizer type" group="Polarizer">1</parameter>
          <parameter name="MonochromatorPolarizerComment1" label="Monochromator polarizer comment" group="Polarizer">Default Polarizer Comment</parameter>
          <parameter name="MonochromatorAt2" label="Monochromator constant/setup wavelength" group="Monochromator">400.000000</parameter>
          <parameter name="MonochromatorShutter2" label="Monochromator shutter status" group="Monochromator">0</parameter>
          <parameter name="MonochromatorSlitIn2" label="Monochromator input slit width" group="Monochromator">4.000000</parameter>
          <parameter name="MonochromatorSlitOut2" label="Monochromator output slit width" group="Monochromator">4.000000</parameter>
          <parameter name="MonochromatorStepSize2" label="Monochromator step size" group="Monochromator">0.200000</parameter>
          <parameter name="MonochromatorType2" label="Monochromator type (excitation or emission)" group="Monochromator">8</parameter>
          <parameter name="MonochromatorUpperLimit2" label="Monochromator upper limit" group="Monochromator">950.000000</parameter>
          <parameter name="MonochromatorComment2" label="Monochromator comment" group="Monochromator">Default Monochromator Comment</parameter>
          <parameter name="MonochromatorNumFilter2" label="Monochromator # of filters switched" group="Filter">3</parameter>
          <parameter name="MonochromatorFilter2Wavelength1" label="Monochromator constant/setup wavelength" group="Filter">200.000000</parameter>
          <parameter name="MonochromatorFilter2Wavelength2" label="Monochromator constant/setup wavelength" group="Filter">300.000000</parameter>
          <parameter name="MonochromatorFilter2Wavelength3" label="Monochromator constant/setup wavelength" group="Filter">400.000000</parameter>
          <parameter name="MonochromatorFilter2Comment" label="Monochromator filter comment" group="Filter">Default Filter Comment</parameter>
          <parameter name="MonochromatorPolarizerAngle2" label="Monochromator polarization angle" group="Polarizer">90.000000</parameter>
          <parameter name="MonochromatorPolarizerType2" label="Monochromator polarizer type" group="Polarizer">1</parameter>
          <parameter name="MonochromatorPolarizerComment2" label="Monochromator polarizer comment" group="Polarizer">Default Polarizer Comment</parameter>
          <parameter name="PmtConnection1" label="PMT connection" group="PMT">10</parameter>
          <parameter name="PmtGain1" label="PMT gain" group="PMT">1</parameter>
          <parameter name="PmtHighVolt1" label="PMT high voltage" group="PMT">750</parameter>
          <parameter name="PmtOffset1" label="PMT offset status" group="PMT">0</parameter>
          <parameter name="PmtAcquisition1" label="PMT acquisition type" group="PMT">0</parameter>
          <parameter name="PmtComment1" label="PMT comment" group="PMT">Default Pmt Comment</parameter>
          <parameter name="PmtConnection2" label="PMT connection" group="PMT">10</parameter>
          <parameter name="PmtGain2" label="PMT gain" group="PMT">1</parameter>
          <parameter name="PmtHighVolt2" label="PMT high voltage" group="PMT">750</parameter>
          <parameter name="PmtOffset2" label="PMT offset status" group="PMT">0</parameter>
          <parameter name="PmtAcquisition2" label="PMT acquisition type" group="PMT">1</parameter>
          <parameter name="PmtComment2" label="PMT comment" group="PMT">Default Pmt Comment</parameter>
          <parameter name="InstrumentSpecific3" label="Instrument specific information" group="AUX">2</parameter>
          <parameter name="AuxChannelGain1" label="AUX channel gain" group="AUX">0</parameter>
          <parameter name="AuxChannelOffset1" label="AUX channel offset status" group="AUX">0</parameter>
          <parameter name="AuxChannelComment1" label="AUX channel comment" group="AUX">Default Aux Pmt Comment</parameter>
          <parameter name="AuxChannelGain2" label="AUX channel gain" group="AUX">1</parameter>
          <parameter name="AuxChannelOffset2" label="AUX channel offset status" group="AUX">0</parameter>
          <parameter name="AuxChannelComment2" label="AUX channel comment" group="AUX">Default Aux Pmt Comment</parameter>
          <parameter name="PlotInfo" label="Y-axis plotting annotation" group="YAxis">NULL</parameter>
          <parameter name="Corrected" label="Data corrected flag" group="MANIPULATION">0x0</parameter>
          <parameter name="Derivative" label="Degree of derivation" group="MANIPULATION">0</parameter>
          <parameter name="Norm" label="Normalized flag" group="MANIPULATION">0x0</parameter>
          <parameter name="Offset" label="Offset from the original data" group="MANIPULATION">0.000000</parameter>
          <parameter name="Scale" label="Scale from the original data" group="MANIPULATION">1.000000</parameter>
          <parameter name="Smooth" label="# of smoothing passes done" group="MANIPULATION">0</parameter>
          <parameter name="SmoothType" label="Smoothing type" group="MANIPULATION">1.000000</parameter>
          <values byteorder="INTEL" format="FLOAT32" numvalues="201">AIDePACA3jwAgO08AIDZPACA7TwAANc8AADwPACA3jwAAPU8AIDoPACA8jwAAOs8AAACPQCA7TwA
AAI9AID3PADABT0AAP88AAAMPQAABz0AgBM9AMAKPQCAHT0AQBw9AEArPQAALz0AQFM9AIBePQBg
hj0AYJo9AECwPQCAuz0AINo9AMDpPQBQAj4AkAM+ABAQPgDgCD4AIAo+ACAKPgBQET4AQBI+AGAQ
PgBQET4A8BY+AGAVPgBgHz4AoCU+ACAoPgAAJT4A0Cw+ALApPgAwLD4AACo+AMAtPgDQJz4AECQ+
AEAhPgAQGj4AkBc+ALAQPgBADT4A0A4+APACPgCwBj4AgPc9ACDzPQDg5z0A4Oc9AIDZPQCA4z0A
YNE9AGDWPQBgzD0AAM09AMDBPQCAwD0AYL09AMDGPQAgtz0AALQ9AMC3PQDAtz0AAKo9AACvPQAA
oD0AQKY9AGCfPQDgoT0A4Jw9AKCbPQAAlj0AYJU9ACCPPQBAjT0AIIo9AECIPQBggT0AYIY9AIB3
PQAAfz0AAHU9AIB8PQBAYj0AQGw9AABhPQCAaD0AAFw9AEBYPQCAWT0AQE49AIBFPQAATT0AgEU9
AABNPQBARD0AwEE9AIA7PQCART0AQDo9AIA7PQDAMj0AgDs9AIAxPQCANj0AQCs9AAAvPQBAJj0A
gCw9AMAtPQDAKD0AACA9AMAoPQDAHj0AgCc9AEAXPQCAIj0AQBw9AIAdPQBAFz0AACA9AEASPQCA
HT0AQBc9AEAXPQCAEz0AQBc9AMAUPQCAEz0AgAk9AEASPQAADD0AgBM9AIAJPQBAEj0AAAc9AMAU
PQBADT0AABE9AIAEPQBAEj0AQAM9AIAOPQDABT0AwA89AAACPQDACj0AAP88AAAMPQAA/zwAAAw9
AIAEPQCABD0AAPo8AIAJPQDAAD0AwAo9AAD/PADABT0AAPU8AIAEPQAA+jwAwAU9AAD6PABAAz0A
APU8AEADPQCA8jwAwAA9AID3PABAAz0AgO08AIAEPQAA8DwAwAA9AADwPACA9zwAAPA8AMAAPQCA
7TwAAPo8
          </values>
        </Ydata>
        <Ydata label="Y" units="UNKNOWN">
          <parameter name="DataTags" label="# of data points with tags" group="YAxis">0</parameter>
          <parameter name="Channel" label="Instrument channel stored" group="YAxis">10</parameter>
          <parameter name="MaxX" label="X-axis of the data set maximum" group="YAxis">356.000000</parameter>
          <parameter name="MaxY" label="Data set maximum" group="YAxis">0.138245</parameter>
          <parameter name="MinX" label="X-axis of the data set minimum" group="YAxis">300.000000</parameter>
          <parameter name="MinY" label="Data set minimum" group="YAxis">0.025635</parameter>
          <parameter name="YaxisType" label="Y-axis type" group="YAxis">0x12 = Intensity</parameter>
          <parameter name="YaxisUnit" label="Y-axis unit" group="YAxis">0x0 = Unknown</parameter>
          <parameter name="YAxisLL" label="Y-axis lower limit" group="YAxis">0.025635</parameter>
          <parameter name="YAxisUL" label="Y-axis upper limit" group="YAxis">0.138245</parameter>
          <parameter name="LabelY" label="Y-axis Label" group="YAxis">Y</parameter>
          <parameter name="AcqStarted" label="Date and Time the acquisition was started" group="Standard">0000-00-00 00:00:00</parameter>
          <parameter name="AcqCompleted" label="Date and Time the acquisition was completed" group="Standard">0000-00-00 00:00:00</parameter>
          <parameter name="ID" label="Acquiring instruments serial (ID) number" group="Standard">0</parameter>
          <parameter name="Instrument" label="Instrument type acquired on" group="Standard">7</parameter>
          <parameter name="Modified" label="Date and Time the acquisition was modified" group="Standard">0000-00-00 00:00:00</parameter>
          <parameter name="OSVersion" label="Operating system version" group="Standard">0.000000</parameter>
          <parameter name="Version" label="Program version (&apos;C&apos;) acquired on" group="Standard">0.000000</parameter>
          <parameter name="Comment" label="Acquisition comment" group="Standard">NULL</parameter>
          <parameter name="AcqTitle" label="Acquisition title" group="Standard">NULL</parameter>
          <parameter name="User" label="User who acquired this data" group="Standard">NULL</parameter>
          <parameter name="Monochromators" label="# of monochromators attached" group="Hardware">2</parameter>
          <parameter name="Pmts" label="# of PMTs attached" group="Hardware">2</parameter>
          <parameter name="Lamp" label="Excitation light source type" group="LAMP">1</parameter>
          <parameter name="LampComment" label="Excitation light source comment" group="LAMP">Default Lamp Comment</parameter>
          <parameter name="MonochromatorAt1" label="Monochromator constant/setup wavelength" group="Monochromator">300.000000</parameter>
          <parameter name="MonochromatorShutter1" label="Monochromator shutter status" group="Monochromator">0</parameter>
          <parameter name="MonochromatorSlitIn1" label="Monochromator input slit width" group="Monochromator">4.000000</parameter>
          <parameter name="MonochromatorSlitOut1" label="Monochromator output slit width" group="Monochromator">4.000000</parameter>
          <parameter name="MonochromatorStepSize1" label="Monochromator step size" group="Monochromator">0.200000</parameter>
          <parameter name="MonochromatorType1" label="Monochromator type (excitation or emission)" group="Monochromator">10</parameter>
          <parameter name="MonochromatorUpperLimit1" label="Monochromator upper limit" group="Monochromator">950.000000</parameter>
          <parameter name="MonochromatorComment1" label="Monochromator comment" group="Monochromator">Default Monochromator Comment</parameter>
          <parameter name="MonochromatorNumFilter1" label="Monochromator # of filters switched" group="Filter">2</parameter>
          <parameter name="MonochromatorFilter1Wavelength1" label="Monochromator constant/setup wavelength" group="Filter">200.000000</parameter>
          <parameter name="MonochromatorFilter1Wavelength2" label="Monochromator constant/setup wavelength" group="Filter">300.000000</parameter>
          <parameter name="MonochromatorFilter1Comment" label="Monochromator filter comment" group="Filter">Default Filter Comment</parameter>
          <parameter name="MonochromatorPolarizerAngle1" label="Monochromator polarization angle" group="Polarizer">0.000000</parameter>
          <parameter name="MonochromatorPolarizerType1" label="Monochromator polarizer type" group="Polarizer">1</parameter>
          <parameter name="MonochromatorPolarizerComment1" label="Monochromator polarizer comment" group="Polarizer">Default Polarizer Comment</parameter>
          <parameter name="MonochromatorAt2" label="Monochromator constant/setup wavelength" group="Monochromator">400.000000</parameter>
          <parameter name="MonochromatorShutter2" label="Monochromator shutter status" group="Monochromator">0</parameter>
          <parameter name="MonochromatorSlitIn2" label="Monochromator input slit width" group="Monochromator">4.000000</parameter>
          <parameter name="MonochromatorSlitOut2" label="Monochromator output slit width" group="Monochromator">4.000000</parameter>
          <parameter name="MonochromatorStepSize2" label="Monochromator step size" group="Monochromator">0.200000</parameter>
          <parameter name="MonochromatorType2" label="Monochromator type (excitation or emission)" group="Monochromator">8</parameter>
          <parameter name="MonochromatorUpperLimit2" label="Monochromator upper limit" group="Monochromator">950.000000</parameter>
          <parameter name="MonochromatorComment2" label="Monochromator comment" group="Monochromator">Default Monochromator Comment</parameter>
          <parameter name="MonochromatorNumFilter2" label="Monochromator # of filters switched" group="Filter">3</parameter>
          <parameter name="MonochromatorFilter2Wavelength1" label="Monochromator constant/setup wavelength" group="Filter">200.000000</parameter>
          <parameter name="MonochromatorFilter2Wavelength2" label="Monochromator constant/setup wavelength" group="Filter">300.000000</parameter>
          <parameter name="MonochromatorFilter2Wavelength3" label="Monochromator constant/setup wavelength" group="Filter">400.000000</parameter>
          <parameter name="MonochromatorFilter2Comment" label="Monochromator filter comment" group="Filter">Default Filter Comment</parameter>
          <parameter name="MonochromatorPolarizerAngle2" label="Monochromator polarization angle" group="Polarizer">90.000000</parameter>
          <parameter name="MonochromatorPolarizerType2" label="Monochromator polarizer type" group="Polarizer">1</parameter>
          <parameter name="MonochromatorPolarizerComment2" label="Monochromator polarizer comment" group="Polarizer">Default Polarizer Comment</parameter>
          <parameter name="PmtConnection1" label="PMT connection" group="PMT">10</parameter>
          <parameter name="PmtGain1" label="PMT gain" group="PMT">1</parameter>
          <parameter name="PmtHighVolt1" label="PMT high voltage" group="PMT">750</parameter>
          <parameter name="PmtOffset1" label="PMT offset status" group="PMT">0</parameter>
          <parameter name="PmtAcquisition1" label="PMT acquisition type" group="PMT">0</parameter>
          <parameter name="PmtComment1" label="PMT comment" group="PMT">Default Pmt Comment</parameter>
          <parameter name="PmtConnection2" label="PMT connection" group="PMT">10</parameter>
          <parameter name="PmtGain2" label="PMT gain" group="PMT">1</parameter>
          <parameter name="PmtHighVolt2" label="PMT high voltage" group="PMT">750</parameter>
          <parameter name="PmtOffset2" label="PMT offset status" group="PMT">0</parameter>
          <parameter name="PmtAcquisition2" label="PMT acquisition type" group="PMT">1</parameter>
          <parameter name="PmtComment2" label="PMT comment" group="PMT">Default Pmt Comment</parameter>
          <parameter name="InstrumentSpecific3" label="Instrument specific information" group="AUX">2</parameter>
          <parameter name="AuxChannelGain1" label="AUX channel gain" group="AUX">0</parameter>
          <parameter name="AuxChannelOffset1" label="AUX channel offset status" group="AUX">0</parameter>
          <parameter name="AuxChannelComment1" label="AUX channel comment" group="AUX">Default Aux Pmt Comment</parameter>
          <parameter name="AuxChannelGain2" label="AUX channel gain" group="AUX">1</parameter>
          <parameter name="AuxChannelOffset2" label="AUX channel offset status" group="AUX">0</parameter>
          <parameter name="AuxChannelComment2" label="AUX channel comment" group="AUX">Default Aux Pmt Comment</parameter>
          <parameter name="PlotInfo" label="Y-axis plotting annotation" group="YAxis">NULL</parameter>
          <parameter name="Corrected" label="Data corrected flag" group="MANIPULATION">0x0</parameter>
          <parameter name="Derivative" label="Degree of derivation" group="MANIPULATION">0</parameter>
          <parameter name="Norm" label="Normalized flag" group="MANIPULATION">0x0</parameter>
          <parameter name="Offset" label="Offset from the original data" group="MANIPULATION">0.000000</parameter>
          <parameter name="Scale" label="Scale from the original data" group="MANIPULATION">1.000000</parameter>
          <parameter name="Smooth" label="# of smoothing passes done" group="MANIPULATION">0</parameter>
          <parameter name="SmoothType" label="Smoothing type" group="MANIPULATION">1.000000</parameter>
          <values byteorder="INTEL" format="FLOAT32" numvalues="201">AADSPACA1DwAgOg8AADSPACA9zwAANc8AAD6PAAA1zwAgPc8AADcPADAAD0AAOE8AIAEPQCA7TwA
QAg9AID3PABADT0AAP88AIATPQAADD0AABs9AAARPQAAJT0AQBI9AMAtPQBAJj0AAD49AMBLPQAA
Vz0AgGg9AAB/PQDgiD0AAJs9AICdPQBgpD0AQKY9ACC3PQBguD0AQL89AMC8PQDgvz0AoMM9AODO
PQCg0j0AINo9AEDiPQBg6j0AYOo9AAD1PQBg9D0AYP49ALABPgAwBD4AwAo+ADAJPgAQCz4AkA0+
AOADPgDgCD4AoAI+AHAFPgBA+z0AsAE+AKD1PQDA7j0AQOc9AKDcPQDg2D0AoNc9AEDOPQAAyD0A
oMM9AIDPPQDgvz0AAMM9ACC8PQBgwj0AIME9AOC/PQAAvj0A4MQ9ACC8PQCAuz0AYL09ACC8PQAA
uT0AAK89AICsPQAAqj0A4KE9AKCqPQCAnT0AQJw9AMCjPQAAoD0AgI49AGCVPQCgjD0AoJY9AECS
PQBgkD0AgIQ9AGCBPQAAfz0AYIY9AAB1PQDAfT0AAH89AIByPQBAYj0AAHo9AEBdPQDAaT0AAFw9
AIBoPQCAWT0AAGE9AIBPPQDAWj0AgEo9AMBQPQDAQT0AQE49AIBFPQBARD0AAEM9AEA6PQCAMT0A
QEQ9AIAxPQDANz0AwCg9AIA2PQDAMj0AADQ9AEAhPQDAMj0AgCc9AAAvPQCAHT0AACU9AAAgPQDA
KD0AQBw9AAAgPQBAFz0AQCE9AIAYPQCAHT0AQBc9AAAbPQAADD0AQBw9AAARPQDAHj0AAAw9AEAX
PQCACT0AwA89AMAFPQBAFz0AgAk9AIATPQDABT0AwA89AIAEPQCADj0AAAI9AAARPQCABD0AgA49
AID8PACACT0AAAI9AAAMPQDAAD0AgAk9AAD/PABACD0AAPU8AMAFPQCA/DwAwAU9AIDyPABACD0A
APU8AIAEPQAA6zwAAAI9AADwPADABT0AgOg8AAACPQCA6DwAAP88AADwPAAAAj0AgO08AID8PACA
7TwAgPw8
          </values>
        </Ydata>
        <Ydata label="Y" units="UNKNOWN">
          <parameter name="DataTags" label="# of data points with tags" group="YAxis">0</parameter>
          <parameter name="Channel" label="Instrument channel stored" group="YAxis">10</parameter>
          <parameter name="MaxX" label="X-axis of the data set maximum" group="YAxis">356.000000</parameter>
          <parameter name="MaxY" label="Data set maximum" group="YAxis">0.110474</parameter>
          <parameter name="MinX" label="X-axis of the data set minimum" group="YAxis">303.000000</parameter>
          <parameter name="MinY" label="Data set minimum" group="YAxis">0.026245</parameter>
          <parameter name="YaxisType" label="Y-axis type" group="YAxis">0x12 = Intensity</parameter>
          <parameter name="YaxisUnit" label="Y-axis unit" group="YAxis">0x0 = Unknown</parameter>
          <parameter name="YAxisLL" label="Y-axis lower limit" group="YAxis">0.026245</parameter>
          <parameter name="YAxisUL" label="Y-axis upper limit" group="YAxis">0.110474</parameter>
          <parameter name="LabelY" label="Y-axis Label" group="YAxis">Y</parameter>
          <parameter name="AcqStarted" label="Date and Time the acquisition was started" group="Standard">0000-00-00 00:00:00</parameter>
          <parameter name="AcqCompleted" label="Date and Time the acquisition was completed" group="Standard">0000-00-00 00:00:00</parameter>
          <parameter name="ID" label="Acquiring instruments serial (ID) number" group="Standard">0</parameter>
          <parameter name="Instrument" label="Instrument type acquired on" group="Standard">7</parameter>
          <parameter name="Modified" label="Date and Time the acquisition was modified" group="Standard">0000-00-00 00:00:00</parameter>
          <parameter name="OSVersion" label="Operating system version" group="Standard">0.000000</parameter>
          <parameter name="Version" label="Program version (&apos;C&apos;) acquired on" group="Standard">0.000000</parameter>
          <parameter name="Comment" label="Acquisition comment" group="Standard">NULL</parameter>
          <parameter name="AcqTitle" label="Acquisition title" group="Standard">NULL</parameter>
          <parameter name="User" label="User who acquired this data" group="Standard">NULL</parameter>
          <parameter name="Monochromators" label="# of monochromators attached" group="Hardware">2</parameter>
          <parameter name="Pmts" label="# of PMTs attached" group="Hardware">2</parameter>
          <parameter name="Lamp" label="Excitation light source type" group="LAMP">1</parameter>
          <parameter name="LampComment" label="Excitation light source comment" group="LAMP">Default Lamp Comment</parameter>
          <parameter name="MonochromatorAt1" label="Monochromator constant/setup wavelength" group="Monochromator">300.000000</parameter>
          <parameter name="MonochromatorShutter1" label="Monochromator shutter status" group="Monochromator">0</parameter>
          <parameter name="MonochromatorSlitIn1" label="Monochromator input slit width" group="Monochromator">4.000000</parameter>
          <parameter name="MonochromatorSlitOut1" label="Monochromator output slit width" group="Monochromator">4.000000</parameter>
          <parameter name="MonochromatorStepSize1" label="Monochromator step size" group="Monochromator">0.200000</parameter>
          <parameter name="MonochromatorType1" label="Monochromator type (excitation or emission)" group="Monochromator">10</parameter>
          <parameter name="MonochromatorUpperLimit1" label="Monochromator upper limit" group="Monochromator">950.000000</parameter>
          <parameter name="MonochromatorComment1" label="Monochromator comment" group="Monochromator">Default Monochromator Comment</parameter>
          <parameter name="MonochromatorNumFilter1" label="Monochromator # of filters switched" group="Filter">2</parameter>
          <parameter name="MonochromatorFilter1Wavelength1" label="Monochromator constant/setup wavelength" group="Filter">200.000000</parameter>
          <parameter name="MonochromatorFilter1Wavelength2" label="Monochromator constant/setup wavelength" group="Filter">300.000000</parameter>
          <parameter name="MonochromatorFilter1Comment" label="Monochromator filter comment" group="Filter">Default Filter Comment</parameter>
          <parameter name="MonochromatorPolarizerAngle1" label="Monochromator polarization angle" group="Polarizer">0.000000</parameter>
          <parameter name="MonochromatorPolarizerType1" label="Monochromator polarizer type" group="Polarizer">1</parameter>
          <parameter name="MonochromatorPolarizerComment1" label="Monochromator polarizer comment" group="Polarizer">Default Polarizer Comment</parameter>
          <parameter name="MonochromatorAt2" label="Monochromator constant/setup wavelength" group="Monochromator">400.000000</parameter>
          <parameter name="MonochromatorShutter2" label="Monochromator shutter status" group="Monochromator">0</parameter>
          <parameter name="MonochromatorSlitIn2" label="Monochromator input slit width" group="Monochromator">4.000000</parameter>
          <parameter name="MonochromatorSlitOut2" label="Monochromator output slit width" group="Monochromator">4.000000</parameter>
          <parameter name="MonochromatorStepSize2" label="Monochromator step size" group="Monochromator">0.200000</parameter>
          <parameter name="MonochromatorType2" label="Monochromator type (excitation or emission)" group="Monochromator">8</parameter>
          <parameter name="MonochromatorUpperLimit2" label="Monochromator upper limit" group="Monochromator">950.000000</parameter>
          <parameter name="MonochromatorComment2" label="Monochromator comment" group="Monochromator">Default Monochromator Comment</parameter>
          <parameter name="MonochromatorNumFilter2" label="Monochromator # of filters switched" group="Filter">3</parameter>
          <parameter name="MonochromatorFilter2Wavelength1" label="Monochromator constant/setup wavelength" group="Filter">200.000000</parameter>
          <parameter name="MonochromatorFilter2Wavelength2" label="Monochromator constant/setup wavelength" group="Filter">300.000000</parameter>
          <parameter name="MonochromatorFilter2Wavelength3" label="Monochromator constant/setup wavelength" group="Filter">400.000000</parameter>
          <parameter name="MonochromatorFilter2Comment" label="Monochromator filter comment" group="Filter">Default Filter Comment</parameter>
          <parameter name="MonochromatorPolarizerAngle2" label="Monochromator polarization angle" group="Polarizer">90.000000</parameter>
          <parameter name="MonochromatorPolarizerType2" label="Monochromator polarizer type" group="Polarizer">1</parameter>
          <parameter name="MonochromatorPolarizerComment2" label="Monochromator polarizer comment" group="Polarizer">Default Polarizer Comment</parameter>
          <parameter name="PmtConnection1" label="PMT connection" group="PMT">10</parameter>
          <parameter name="PmtGain1" label="PMT gain" group="PMT">1</parameter>
          <parameter name="PmtHighVolt1" label="PMT high voltage" group="PMT">750</parameter>
          <parameter name="PmtOffset1" label="PMT offset status" group="PMT">0</parameter>
          <parameter name="PmtAcquisition1" label="PMT acquisition type" group="PMT">0</parameter>
          <parameter name="PmtComment1" label="PMT comment" group="PMT">Default Pmt Comment</parameter>
          <parameter name="PmtConnection2" label="PMT connection" group="PMT">10</parameter>
          <parameter name="PmtGain2" label="PMT gain" group="PMT">1</parameter>
          <parameter name="PmtHighVolt2" label="PMT high voltage" group="PMT">750</parameter>
          <parameter name="PmtOffset2" label="PMT offset status" group="PMT">0</parameter>
          <parameter name="PmtAcquisition2" label="PMT acquisition type" group="PMT">1</parameter>
          <parameter name="PmtComment2" label="PMT comment" group="PMT">Default Pmt Comment</parameter>
          <parameter name="InstrumentSpecific3" label="Instrument specific information" group="AUX">2</parameter>
          <parameter name="AuxChannelGain1" label="AUX channel gain" group="AUX">0</parameter>
          <parameter name="AuxChannelOffset1" label="AUX channel offset status" group="AUX">0</parameter>
          <parameter name="AuxChannelComment1" label="AUX channel comment" group="AUX">Default Aux Pmt Comment</parameter>
          <parameter name="AuxChannelGain2" label="AUX channel gain" group="AUX">1</parameter>
          <parameter name="AuxChannelOffset2" label="AUX channel offset status" group="AUX">0</parameter>
          <parameter name="AuxChannelComment2" label="AUX channel comment" group="AUX">Default Aux Pmt Comment</parameter>
          <parameter name="PlotInfo" label="Y-axis plotting annotation" group="YAxis">NULL</parameter>
          <parameter name="Corrected" label="Data corrected flag" group="MANIPULATION">0x0</parameter>
          <parameter name="Derivative" label="Degree of derivation" group="MANIPULATION">0</parameter>
          <parameter name="Norm" label="Normalized flag" group="MANIPULATION">0x0</parameter>
          <parameter name="Offset" label="Offset from the original data" group="MANIPULATION">0.000000</parameter>
          <parameter name="Scale" label="Scale from the original data" group="MANIPULATION">1.000000</parameter>
          <parameter name="Smooth" label="# of smoothing passes done" group="MANIPULATION">0</parameter>
          <parameter name="SmoothType" label="Smoothing type" group="MANIPULATION">1.000000</parameter>
          <values byteorder="INTEL" format="FLOAT32" numvalues="201">AADcPACA2TwAgO08AADXPACA8jwAANw8AAD1PAAA3DwAAPo8AIDjPAAA+jwAAOY8AMAAPQAA+jwA
QA09AAD/PADADz0AwAA9AAARPQCACT0AABY9AEASPQAAGz0AABY9AIAnPQAAID0AwDc9AMA3PQAA
TT0AQEQ9AABXPQAAXD0AQHY9AEBxPQAAfz0AwH09AECDPQCAhD0AQJI9AGCLPQAgjz0AYJU9ACCZ
PQAgmT0AIJ49AICdPQCAsT0AwLc9AOC1PQDguj0AQMQ9AIDFPQCAxT0AwNU9AGDbPQBA0z0AQOI9
AADXPQCA3j0AINU9AADXPQDA0D0A4Mk9AGDMPQBAxD0AILw9AAC+PQDAtz0AgLY9AICxPQDgsD0A
wKg9AKCvPQAgrT0AoK89AKClPQDArT0AAKU9ACCtPQCApz0AAKo9ACCePQAApT0AoJs9ACCoPQCA
nT0AgJ09AICYPQBgnz0AYJU9ACCZPQBglT0AAJY9AICJPQCgjD0AgIk9AKCRPQAAhz0AwIA9AICE
PQBggT0AIIo9AEB7PQBAdj0AAH89AEBsPQDAbj0AgGg9AIBtPQBAYj0AgG09AABmPQCAXj0AQFM9
AEBnPQDAUD0AAE09AMBQPQDAWj0AQEk9AABSPQAAQz0AgE89AEBJPQCART0AwEE9AIBAPQAAND0A
QDo9AIA2PQDANz0AwDI9AIAxPQBAKz0AADk9AAAlPQAALz0AwB49AEArPQCAIj0AQCY9AEAhPQDA
KD0AQBw9AAAgPQAAGz0AQBw9AEASPQCAHT0AwBQ9AIAdPQAAET0AQBc9AEASPQDAGT0AAAw9AAAW
PQDACj0AgBM9AEAIPQBAEj0AwAU9AEANPQAABz0AwAo9AAAHPQCAEz0AAAI9AAAMPQCABD0AgA49
AAD/PABACD0AAPo8AIAJPQAA9TwAwAo9AAD1PABAAz0AAPo8AAAHPQAA8DwAAAc9AIDyPAAAAj0A
gO08AAACPQAA8DwAAAI9AADwPADABT0AgOg8AMAAPQAA4TwAwAA9AADrPACA/DwAAOs8AID8PACA
7TwAAP88
          </values>
        </Ydata>
        <Ydata label="Y" units="UNKNOWN">
          <parameter name="DataTags" label="# of data points with tags" group="YAxis">0</parameter>
          <parameter name="Channel" label="Instrument channel stored" group="YAxis">10</parameter>
          <parameter name="MaxX" label="X-axis of the data set maximum" group="YAxis">354.000000</parameter>
          <parameter name="MaxY" label="Data set maximum" group="YAxis">0.088806</parameter>
          <parameter name="MinX" label="X-axis of the data set minimum" group="YAxis">303.000000</parameter>
          <parameter name="MinY" label="Data set minimum" group="YAxis">0.025330</parameter>
          <parameter name="YaxisType" label="Y-axis type" group="YAxis">0x12 = Intensity</parameter>
          <parameter name="YaxisUnit" label="Y-axis unit" group="YAxis">0x0 = Unknown</parameter>
          <parameter name="YAxisLL" label="Y-axis lower limit" group="YAxis">0.025330</parameter>
          <parameter name="YAxisUL" label="Y-axis upper limit" group="YAxis">0.088806</parameter>
          <parameter name="LabelY" label="Y-axis Label" group="YAxis">Y</parameter>
          <parameter name="AcqStarted" label="Date and Time the acquisition was started" group="Standard">0000-00-00 00:00:00</parameter>
          <parameter name="AcqCompleted" label="Date and Time the acquisition was completed" group="Standard">0000-00-00 00:00:00</parameter>
          <parameter name="ID" label="Acquiring instruments serial (ID) number" group="Standard">0</parameter>
          <parameter name="Instrument" label="Instrument type acquired on" group="Standard">7</parameter>
          <parameter name="Modified" label="Date and Time the acquisition was modified" group="Standard">0000-00-00 00:00:00</parameter>
          <parameter name="OSVersion" label="Operating system version" group="Standard">0.000000</parameter>
          <parameter name="Version" label="Program version (&apos;C&apos;) acquired on" group="Standard">0.000000</parameter>
          <parameter name="Comment" label="Acquisition comment" group="Standard">NULL</parameter>
          <parameter name="AcqTitle" label="Acquisition title" group="Standard">NULL</parameter>
          <parameter name="User" label="User who acquired this data" group="Standard">NULL</parameter>
          <parameter name="Monochromators" label="# of monochromators attached" group="Hardware">2</parameter>
          <parameter name="Pmts" label="# of PMTs attached" group="Hardware">2</parameter>
          <parameter name="Lamp" label="Excitation light source type" group="LAMP">1</parameter>
          <parameter name="LampComment" label="Excitation light source comment" group="LAMP">Default Lamp Comment</parameter>
          <parameter name="MonochromatorAt1" label="Monochromator constant/setup wavelength" group="Monochromator">300.000000</parameter>
          <parameter name="MonochromatorShutter1" label="Monochromator shutter status" group="Monochromator">0</parameter>
          <parameter name="MonochromatorSlitIn1" label="Monochromator input slit width" group="Monochromator">4.000000</parameter>
          <parameter name="MonochromatorSlitOut1" label="Monochromator output slit width" group="Monochromator">4.000000</parameter>
          <parameter name="MonochromatorStepSize1" label="Monochromator step size" group="Monochromator">0.200000</parameter>
          <parameter name="MonochromatorType1" label="Monochromator type (excitation or emission)" group="Monochromator">10</parameter>
          <parameter name="MonochromatorUpperLimit1" label="Monochromator upper limit" group="Monochromator">950.000000</parameter>
          <parameter name="MonochromatorComment1" label="Monochromator comment" group="Monochromator">Default Monochromator Comment</parameter>
          <parameter name="MonochromatorNumFilter1" label="Monochromator # of filters switched" group="Filter">2</parameter>
          <parameter name="MonochromatorFilter1Wavelength1" label="Monochromator constant/setup wavelength" group="Filter">200.000000</parameter>
          <parameter name="MonochromatorFilter1Wavelength2" label="Monochromator constant/setup wavelength" group="Filter">300.000000</parameter>
          <parameter name="MonochromatorFilter1Comment" label="Monochromator filter comment" group="Filter">Default Filter Comment</parameter>
          <parameter name="MonochromatorPolarizerAngle1" label="Monochromator polarization angle" group="Polarizer">0.000000</parameter>
          <parameter name="MonochromatorPolarizerType1" label="Monochromator polarizer type" group="Polarizer">1</parameter>
          <parameter name="MonochromatorPolarizerComment1" label="Monochromator polarizer comment" group="Polarizer">Default Polarizer Comment</parameter>
          <parameter name="MonochromatorAt2" label="Monochromator constant/setup wavelength" group="Monochromator">400.000000</parameter>
          <parameter name="MonochromatorShutter2" label="Monochromator shutter status" group="Monochromator">0</parameter>
          <parameter name="MonochromatorSlitIn2" label="Monochromator input slit width" group="Monochromator">4.000000</parameter>
          <parameter name="MonochromatorSlitOut2" label="Monochromator output slit width" group="Monochromator">4.000000</parameter>
          <parameter name="MonochromatorStepSize2" label="Monochromator step size" group="Monochromator">0.200000</parameter>
          <parameter name="MonochromatorType2" label="Monochromator type (excitation or emission)" group="Monochromator">8</parameter>
          <parameter name="MonochromatorUpperLimit2" label="Monochromator upper limit" group="Monochromator">950.000000</parameter>
          <parameter name="MonochromatorComment2" label="Monochromator comment" group="Monochromator">Default Monochromator Comment</parameter>
          <parameter name="MonochromatorNumFilter2" label="Monochromator # of filters switched" group="Filter">3</parameter>
          <parameter name="MonochromatorFilter2Wavelength1" label="Monochromator constant/setup wavelength" group="Filter">200.000000</parameter>
          <parameter name="MonochromatorFilter2Wavelength2" label="Monochromator constant/setup wavelength" group="Filter">300.000000</parameter>
          <parameter name="MonochromatorFilter2Wavelength3" label="Monochromator constant/setup wavelength" group="Filter">400.000000</parameter>
          <parameter name="MonochromatorFilter2Comment" label="Monochromator filter comment" group="Filter">Default Filter Comment</parameter>
          <parameter name="MonochromatorPolarizerAngle2" label="Monochromator polarization angle" group="Polarizer">90.000000</parameter>
          <parameter name="MonochromatorPolarizerType2" label="Monochromator polarizer type" group="Polarizer">1</parameter>
          <parameter name="MonochromatorPolarizerComment2" label="Monochromator polarizer comment" group="Polarizer">Default Polarizer Comment</parameter>
          <parameter name="PmtConnection1" label="PMT connection" group="PMT">10</parameter>
          <parameter name="PmtGain1" label="PMT gain" group="PMT">1</parameter>
          <parameter name="PmtHighVolt1" label="PMT high voltage" group="PMT">750</parameter>
          <parameter name="PmtOffset1" label="PMT offset status" group="PMT">0</parameter>
          <parameter name="PmtAcquisition1" label="PMT acquisition type" group="PMT">0</parameter>
          <parameter name="PmtComment1" label="PMT comment" group="PMT">Default Pmt Comment</parameter>
          <parameter name="PmtConnection2" label="PMT connection" group="PMT">10</parameter>
          <parameter name="PmtGain2" label="PMT gain" group="PMT">1</parameter>
          <parameter name="PmtHighVolt2" label="PMT high voltage" group="PMT">750</parameter>
          <parameter name="PmtOffset2" label="PMT offset status" group="PMT">0</parameter>
          <parameter name="PmtAcquisition2" label="PMT acquisition type" group="PMT">1</parameter>
          <parameter name="PmtComment2" label="PMT comment" group="PMT">Default Pmt Comment</parameter>
          <parameter name="InstrumentSpecific3" label="Instrument specific information" group="AUX">2</parameter>
          <parameter name="AuxChannelGain1" label="AUX channel gain" group="AUX">0</parameter>
          <parameter name="AuxChannelOffset1" label="AUX channel offset status" group="AUX">0</parameter>
          <parameter name="AuxChannelComment1" label="AUX channel comment" group="AUX">Default Aux Pmt Comment</parameter>
          <parameter name="AuxChannelGain2" label="AUX channel gain" group="AUX">1</parameter>
          <parameter name="AuxChannelOffset2" label="AUX channel offset status" group="AUX">0</parameter>
          <parameter name="AuxChannelComment2" label="AUX channel comment" group="AUX">Default Aux Pmt Comment</parameter>
          <parameter name="PlotInfo" label="Y-axis plotting annotation" group="YAxis">NULL</parameter>
          <parameter name="Corrected" label="Data corrected flag" group="MANIPULATION">0x0</parameter>
          <parameter name="Derivative" label="Degree of derivation" group="MANIPULATION">0</parameter>
          <parameter name="Norm" label="Normalized flag" group="MANIPULATION">0x0</parameter>
          <parameter name="Offset" label="Offset from the original data" group="MANIPULATION">0.000000</parameter>
          <parameter name="Scale" label="Scale from the original data" group="MANIPULATION">1.000000</parameter>
          <parameter name="Smooth" label="# of smoothing passes done" group="MANIPULATION">0</parameter>
          <parameter name="SmoothType" label="Smoothing type" group="MANIPULATION">1.000000</parameter>
          <values byteorder="INTEL" format="FLOAT32" numvalues="201">AIDUPAAA0jwAgOg8AIDPPAAA8DwAANc8AIDtPAAA1zwAgPc8AIDZPAAA+jwAAOE8AAACPQCA7TwA
AAc9AIDtPADACj0AgPI8AAAMPQBAAz0AgBM9AEADPQDAFD0AAAw9AAAbPQBAFz0AgDE9AEArPQCA
QD0AQDo9AABIPQCASj0AQFg9AMBVPQBAYj0AgF49AIBjPQDAWj0AwGk9AMBfPQCAcj0AwG49AAB/
PQCAdz0AgIk9AOCDPQAAlj0AQIg9AKCRPQCAkz0AwJk9ACCePQAgoz0AQKY9AOC1PQCgqj0AALQ9
AGCuPQDgsD0AoKo9AOCrPQDgqz0AgKc9AACgPQDAoz0AAJs9AKCgPQDgkj0AYJU9AECSPQCAjj0A
gI49AGCVPQDAhT0AwJQ9AACRPQBglT0AYIs9AACMPQDgiD0AAIw9AKCHPQBgkD0AwIU9AACHPQBg
hj0AoIc9AIB3PQBAgz0AgHw9AGCBPQBAez0AwH09AEB7PQDAgD0AwHM9AKCCPQAAaz0AwG49AABr
PQDAcz0AQGI9AMBpPQBAWD0AAGY9AIBZPQCAXj0AAFc9AIBePQDAUD0AgFQ9AMBGPQBAWD0AwEY9
AABNPQCAQD0AwEs9AEBEPQBAST0AgDs9AIBFPQAAOT0AAD49AAA0PQAAOT0AADQ9AIBFPQBAKz0A
wC09AMAtPQCANj0AgB09AMAyPQBAIT0AQCs9AIAdPQCALD0AQBc9AIAnPQDAHj0AQCY9AEAXPQDA
Hj0AgBg9AMAePQCAEz0AQCE9AAARPQBAFz0AABE9AAAbPQAADD0AwBQ9AIAJPQCAGD0AAAc9AEAX
PQDABT0AABE9AEADPQBAEj0AQAM9AIAOPQAAAj0AAAw9AMAFPQAADD0AAPo8AEANPQCA8jwAwAo9
AAD6PAAABz0AAPo8AAAHPQCA8jwAQAg9AIDyPAAABz0AgO08AAACPQAA6zwAAP88AIDoPADABT0A
gOg8AID8PACA4zwAAAI9AADhPACA/DwAgO08AAD/PAAA4TwAgPc8AADrPAAA+jwAAOE8AID8PAAA
4TwAAPo8
          </values>
        </Ydata>
        <Ydata label="Y" units="UNKNOWN">
          <parameter name="DataTags" label="# of data points with tags" group="YAxis">0</parameter>
          <parameter name="Channel" label="Instrument channel stored" group="YAxis">10</parameter>
          <parameter name="MaxX" label="X-axis of the data set maximum" group="YAxis">358.000000</parameter>
          <parameter name="MaxY" label="Data set maximum" group="YAxis">0.079346</parameter>
          <parameter name="MinX" label="X-axis of the data set minimum" group="YAxis">303.000000</parameter>
          <parameter name="MinY" label="Data set minimum" group="YAxis">0.026245</parameter>
          <parameter name="YaxisType" label="Y-axis type" group="YAxis">0x12 = Intensity</parameter>
          <parameter name="YaxisUnit" label="Y-axis unit" group="YAxis">0x0 = Unknown</parameter>
          <parameter name="YAxisLL" label="Y-axis lower limit" group="YAxis">0.026245</parameter>
          <parameter name="YAxisUL" label="Y-axis upper limit" group="YAxis">0.079346</parameter>
          <parameter name="LabelY" label="Y-axis Label" group="YAxis">Y</parameter>
          <parameter name="AcqStarted" label="Date and Time the acquisition was started" group="Standard">0000-00-00 00:00:00</parameter>
          <parameter name="AcqCompleted" label="Date and Time the acquisition was completed" group="Standard">0000-00-00 00:00:00</parameter>
          <parameter name="ID" label="Acquiring instruments serial (ID) number" group="Standard">0</parameter>
          <parameter name="Instrument" label="Instrument type acquired on" group="Standard">7</parameter>
          <parameter name="Modified" label="Date and Time the acquisition was modified" group="Standard">0000-00-00 00:00:00</parameter>
          <parameter name="OSVersion" label="Operating system version" group="Standard">0.000000</parameter>
          <parameter name="Version" label="Program version (&apos;C&apos;) acquired on" group="Standard">0.000000</parameter>
          <parameter name="Comment" label="Acquisition comment" group="Standard">NULL</parameter>
          <parameter name="AcqTitle" label="Acquisition title" group="Standard">NULL</parameter>
          <parameter name="User" label="User who acquired this data" group="Standard">NULL</parameter>
          <parameter name="Monochromators" label="# of monochromators attached" group="Hardware">2</parameter>
          <parameter name="Pmts" label="# of PMTs attached" group="Hardware">2</parameter>
          <parameter name="Lamp" label="Excitation light source type" group="LAMP">1</parameter>
          <parameter name="LampComment" label="Excitation light source comment" group="LAMP">Default Lamp Comment</parameter>
          <parameter name="MonochromatorAt1" label="Monochromator constant/setup wavelength" group="Monochromator">300.000000</parameter>
          <parameter name="MonochromatorShutter1" label="Monochromator shutter status" group="Monochromator">0</parameter>
          <parameter name="MonochromatorSlitIn1" label="Monochromator input slit width" group="Monochromator">4.000000</parameter>
          <parameter name="MonochromatorSlitOut1" label="Monochromator output slit width" group="Monochromator">4.000000</parameter>
          <parameter name="MonochromatorStepSize1" label="Monochromator step size" group="Monochromator">0.200000</parameter>
          <parameter name="MonochromatorType1" label="Monochromator type (excitation or emission)" group="Monochromator">10</parameter>
          <parameter name="MonochromatorUpperLimit1" label="Monochromator upper limit" group="Monochromator">950.000000</parameter>
          <parameter name="MonochromatorComment1" label="Monochromator comment" group="Monochromator">Default Monochromator Comment</parameter>
          <parameter name="MonochromatorNumFilter1" label="Monochromator # of filters switched" group="Filter">2</parameter>
          <parameter name="MonochromatorFilter1Wavelength1" label="Monochromator constant/setup wavelength" group="Filter">200.000000</parameter>
          <parameter name="MonochromatorFilter1Wavelength2" label="Monochromator constant/setup wavelength" group="Filter">300.000000</parameter>
          <parameter name="MonochromatorFilter1Comment" label="Monochromator filter comment" group="Filter">Default Filter Comment</parameter>
          <parameter name="MonochromatorPolarizerAngle1" label="Monochromator polarization angle" group="Polarizer">0.000000</parameter>
          <parameter name="MonochromatorPolarizerType1" label="Monochromator polarizer type" group="Polarizer">1</parameter>
          <parameter name="MonochromatorPolarizerComment1" label="Monochromator polarizer comment" group="Polarizer">Default Polarizer Comment</parameter>
          <parameter name="MonochromatorAt2" label="Monochromator constant/setup wavelength" group="Monochromator">400.000000</parameter>
          <parameter name="MonochromatorShutter2" label="Monochromator shutter status" group="Monochromator">0</parameter>
          <parameter name="MonochromatorSlitIn2" label="Monochromator input slit width" group="Monochromator">4.000000</parameter>
          <parameter name="MonochromatorSlitOut2" label="Monochromator output slit width" group="Monochromator">4.000000</parameter>
          <parameter name="MonochromatorStepSize2" label="Monochromator step size" group="Monochromator">0.200000</parameter>
          <parameter name="MonochromatorType2" label="Monochromator type (excitation or emission)" group="Monochromator">8</parameter>
          <parameter name="MonochromatorUpperLimit2" label="Monochromator upper limit" group="Monochromator">950.000000</parameter>
          <parameter name="MonochromatorComment2" label="Monochromator comment" group="Monochromator">Default Monochromator Comment</parameter>
          <parameter name="MonochromatorNumFilter2" label="Monochromator # of filters switched" group="Filter">3</parameter>
          <parameter name="MonochromatorFilter2Wavelength1" label="Monochromator constant/setup wavelength" group="Filter">200.000000</parameter>
          <parameter name="MonochromatorFilter2Wavelength2" label="Monochromator constant/setup wavelength" group="Filter">300.000000</parameter>
          <parameter name="MonochromatorFilter2Wavelength3" label="Monochromator constant/setup wavelength" group="Filter">400.000000</parameter>
          <parameter name="MonochromatorFilter2Comment" label="Monochromator filter comment" group="Filter">Default Filter Comment</parameter>
          <parameter name="MonochromatorPolarizerAngle2" label="Monochromator polarization angle" group="Polarizer">90.000000</parameter>
          <parameter name="MonochromatorPolarizerType2" label="Monochromator polarizer type" group="Polarizer">1</parameter>
          <parameter name="MonochromatorPolarizerComment2" label="Monochromator polarizer comment" group="Polarizer">Default Polarizer Comment</parameter>
          <parameter name="PmtConnection1" label="PMT connection" group="PMT">10</parameter>
          <parameter name="PmtGain1" label="PMT gain" group="PMT">1</parameter>
          <parameter name="PmtHighVolt1" label="PMT high voltage" group="PMT">750</parameter>
          <parameter name="PmtOffset1" label="PMT offset status" group="PMT">0</parameter>
          <parameter name="PmtAcquisition1" label="PMT acquisition type" group="PMT">0</parameter>
          <parameter name="PmtComment1" label="PMT comment" group="PMT">Default Pmt Comment</parameter>
          <parameter name="PmtConnection2" label="PMT connection" group="PMT">10</parameter>
          <parameter name="PmtGain2" label="PMT gain" group="PMT">1</parameter>
          <parameter name="PmtHighVolt2" label="PMT high voltage" group="PMT">750</parameter>
          <parameter name="PmtOffset2" label="PMT offset status" group="PMT">0</parameter>
          <parameter name="PmtAcquisition2" label="PMT acquisition type" group="PMT">1</parameter>
          <parameter name="PmtComment2" label="PMT comment" group="PMT">Default Pmt Comment</parameter>
          <parameter name="InstrumentSpecific3" label="Instrument specific information" group="AUX">2</parameter>
          <parameter name="AuxChannelGain1" label="AUX channel gain" group="AUX">0</parameter>
          <parameter name="AuxChannelOffset1" label="AUX channel offset status" group="AUX">0</parameter>
          <parameter name="AuxChannelComment1" label="AUX channel comment" group="AUX">Default Aux Pmt Comment</parameter>
          <parameter name="AuxChannelGain2" label="AUX channel gain" group="AUX">1</parameter>
          <parameter name="AuxChannelOffset2" label="AUX channel offset status" group="AUX">0</parameter>
          <parameter name="AuxChannelComment2" label="AUX channel comment" group="AUX">Default Aux Pmt Comment</parameter>
          <parameter name="PlotInfo" label="Y-axis plotting annotation" group="YAxis">NULL</parameter>
          <parameter name="Corrected" label="Data corrected flag" group="MANIPULATION">0x0</parameter>
          <parameter name="Derivative" label="Degree of derivation" group="MANIPULATION">0</parameter>
          <parameter name="Norm" label="Normalized flag" group="MANIPULATION">0x0</parameter>
          <parameter name="Offset" label="Offset from the original data" group="MANIPULATION">0.000000</parameter>
          <parameter name="Scale" label="Scale from the original data" group="MANIPULATION">1.000000</parameter>
          <parameter name="Smooth" label="# of smoothing passes done" group="MANIPULATION">0</parameter>
          <parameter name="SmoothType" label="Smoothing type" group="MANIPULATION">1.000000</parameter>
          <values byteorder="INTEL" format="FLOAT32" numvalues="201">AADhPACA3jwAAPA8AADXPACA7TwAgNk8AAD1PAAA1zwAAP88AADcPACA/DwAAOY8AID8PAAA5jwA
AAc9AIDyPAAABz0AgPw8AIAEPQCA/DwAAAw9AAACPQBAEj0AwAo9AAAbPQCADj0AACA9AAAgPQBA
NT0AQCY9AAA+PQBAOj0AgEU9AMA8PQBAUz0AgEo9AEBTPQCASj0AAGE9AIBPPQBAZz0AAGE9AEBi
PQCAXj0AIIA9AIB3PQCggj0AwIA9AOCNPQBAgz0AQI09AOCNPQCAkz0AoJE9AICdPQAAlj0AIJk9
ACCZPQCAoj0AgJg9AECcPQDAmT0A4Jw9AACRPQCglj0A4I09AGCQPQDgiD0AwIo9AMB9PQCghz0A
wIU9AKCHPQDAgD0AIIU9AECDPQBAiD0AQHs9AECIPQCAfD0AwIU9AAB6PQCggj0AAHU9AABwPQAA
cD0AwHg9AMBpPQAAaz0AQGI9AEBsPQAAZj0AQGI9AIBoPQDAaT0AgFQ9AIBtPQDAVT0AgGM9AABc
PQBAXT0AQF09AABXPQCAVD0AAFw9AABSPQAAXD0AgEo9AMBGPQBAST0AAE09AMBLPQBATj0AQDo9
AEBEPQDAPD0AgEA9AIA2PQCAQD0AADk9AAA+PQAAPj0AgDs9AIAnPQBAOj0AQDU9AEA1PQBAKz0A
gDE9AEAmPQBAMD0AgCc9AEArPQDAHj0AQCY9AEAhPQAAKj0AABs9AEAhPQDAGT0AACU9AIAYPQCA
HT0AABY9AEAcPQDAFD0AQCE9AIAOPQCAGD0AwAo9AAAWPQBADT0AQBc9AEAIPQDAGT0AgAQ9AEAS
PQAAAj0AQBI9AMAFPQCADj0AwAA9AEANPQCABD0AwAo9AID8PAAADD0AAAI9AEANPQCA8jwAQAg9
AID3PACACT0AgPw8AAAHPQCA9zwAAAc9AAD1PACABD0AgOg8AIAEPQAA6zwAwAU9AIDoPACABD0A
APA8AID8PACA7TwAAPo8AADhPACABD0AgOM8AID8PAAA5jwAgPc8AIDjPAAA9TwAgOg8AID8PACA
4zwAAPo8
          </values>
        </Ydata>
        <Ydata label="Y" units="UNKNOWN">
          <parameter name="DataTags" label="# of data points with tags" group="YAxis">0</parameter>
          <parameter name="Channel" label="Instrument channel stored" group="YAxis">10</parameter>
          <parameter name="MaxX" label="X-axis of the data set maximum" group="YAxis">358.000000</parameter>
          <parameter name="MaxY" label="Data set maximum" group="YAxis">0.074768</parameter>
          <parameter name="MinX" label="X-axis of the data set minimum" group="YAxis">300.000000</parameter>
          <parameter name="MinY" label="Data set minimum" group="YAxis">0.025635</parameter>
          <parameter name="YaxisType" label="Y-axis type" group="YAxis">0x12 = Intensity</parameter>
          <parameter name="YaxisUnit" label="Y-axis unit" group="YAxis">0x0 = Unknown</parameter>
          <parameter name="YAxisLL" label="Y-axis lower limit" group="YAxis">0.025635</parameter>
          <parameter name="YAxisUL" label="Y-axis upper limit" group="YAxis">0.074768</parameter>
          <parameter name="LabelY" label="Y-axis Label" group="YAxis">Y</parameter>
          <parameter name="AcqStarted" label="Date and Time the acquisition was started" group="Standard">0000-00-00 00:00:00</parameter>
          <parameter name="AcqCompleted" label="Date and Time the acquisition was completed" group="Standard">0000-00-00 00:00:00</parameter>
          <parameter name="ID" label="Acquiring instruments serial (ID) number" group="Standard">0</parameter>
          <parameter name="Instrument" label="Instrument type acquired on" group="Standard">7</parameter>
          <parameter name="Modified" label="Date and Time the acquisition was modified" group="Standard">0000-00-00 00:00:00</parameter>
          <parameter name="OSVersion" label="Operating system version" group="Standard">0.000000</parameter>
          <parameter name="Version" label="Program version (&apos;C&apos;) acquired on" group="Standard">0.000000</parameter>
          <parameter name="Comment" label="Acquisition comment" group="Standard">NULL</parameter>
          <parameter name="AcqTitle" label="Acquisition title" group="Standard">NULL</parameter>
          <parameter name="User" label="User who acquired this data" group="Standard">NULL</parameter>
          <parameter name="Monochromators" label="# of monochromators attached" group="Hardware">2</parameter>
          <parameter name="Pmts" label="# of PMTs attached" group="Hardware">2</parameter>
          <parameter name="Lamp" label="Excitation light source type" group="LAMP">1</parameter>
          <parameter name="LampComment" label="Excitation light source comment" group="LAMP">Default Lamp Comment</parameter>
          <parameter name="MonochromatorAt1" label="Monochromator constant/setup wavelength" group="Monochromator">300.000000</parameter>
          <parameter name="MonochromatorShutter1" label="Monochromator shutter status" group="Monochromator">0</parameter>
          <parameter name="MonochromatorSlitIn1" label="Monochromator input slit width" group="Monochromator">4.000000</parameter>
          <parameter name="MonochromatorSlitOut1" label="Monochromator output slit width" group="Monochromator">4.000000</parameter>
          <parameter name="MonochromatorStepSize1" label="Monochromator step size" group="Monochromator">0.200000</parameter>
          <parameter name="MonochromatorType1" label="Monochromator type (excitation or emission)" group="Monochromator">10</parameter>
          <parameter name="MonochromatorUpperLimit1" label="Monochromator upper limit" group="Monochromator">950.000000</parameter>
          <parameter name="MonochromatorComment1" label="Monochromator comment" group="Monochromator">Default Monochromator Comment</parameter>
          <parameter name="MonochromatorNumFilter1" label="Monochromator # of filters switched" group="Filter">2</parameter>
          <parameter name="MonochromatorFilter1Wavelength1" label="Monochromator constant/setup wavelength" group="Filter">200.000000</parameter>
          <parameter name="MonochromatorFilter1Wavelength2" label="Monochromator constant/setup wavelength" group="Filter">300.000000</parameter>
          <parameter name="MonochromatorFilter1Comment" label="Monochromator filter comment" group="Filter">Default Filter Comment</parameter>
          <parameter name="MonochromatorPolarizerAngle1" label="Monochromator polarization angle" group="Polarizer">0.000000</parameter>
          <parameter name="MonochromatorPolarizerType1" label="Monochromator polarizer type" group="Polarizer">1</parameter>
          <parameter name="MonochromatorPolarizerComment1" label="Monochromator polarizer comment" group="Polarizer">Default Polarizer Comment</parameter>
          <parameter name="MonochromatorAt2" label="Monochromator constant/setup wavelength" group="Monochromator">400.000000</parameter>
          <parameter name="MonochromatorShutter2" label="Monochromator shutter status" group="Monochromator">0</parameter>
          <parameter name="MonochromatorSlitIn2" label="Monochromator input slit width" group="Monochromator">4.000000</parameter>
          <parameter name="MonochromatorSlitOut2" label="Monochromator output slit width" group="Monochromator">4.000000</parameter>
          <parameter name="MonochromatorStepSize2" label="Monochromator step size" group="Monochromator">0.200000</parameter>
          <parameter name="MonochromatorType2" label="Monochromator type (excitation or emission)" group="Monochromator">8</parameter>
          <parameter name="MonochromatorUpperLimit2" label="Monochromator upper limit" group="Monochromator">950.000000</parameter>
          <parameter name="MonochromatorComment2" label="Monochromator comment" group="Monochromator">Default Monochromator Comment</parameter>
          <parameter name="MonochromatorNumFilter2" label="Monochromator # of filters switched" group="Filter">3</parameter>
          <parameter name="MonochromatorFilter2Wavelength1" label="Monochromator constant/setup wavelength" group="Filter">200.000000</parameter>
          <parameter name="MonochromatorFilter2Wavelength2" label="Monochromator constant/setup wavelength" group="Filter">300.000000</parameter>
          <parameter name="MonochromatorFilter2Wavelength3" label="Monochromator constant/setup wavelength" group="Filter">400.000000</parameter>
          <parameter name="MonochromatorFilter2Comment" label="Monochromator filter comment" group="Filter">Default Filter Comment</parameter>
          <parameter name="MonochromatorPolarizerAngle2" label="Monochromator polarization angle" group="Polarizer">90.000000</parameter>
          <parameter name="MonochromatorPolarizerType2" label="Monochromator polarizer type" group="Polarizer">1</parameter>
          <parameter name="MonochromatorPolarizerComment2" label="Monochromator polarizer comment" group="Polarizer">Default Polarizer Comment</parameter>
          <parameter name="PmtConnection1" label="PMT connection" group="PMT">10</parameter>
          <parameter name="PmtGain1" label="PMT gain" group="PMT">1</parameter>
          <parameter name="PmtHighVolt1" label="PMT high voltage" group="PMT">750</parameter>
          <parameter name="PmtOffset1" label="PMT offset status" group="PMT">0</parameter>
          <parameter name="PmtAcquisition1" label="PMT acquisition type" group="PMT">0</parameter>
          <parameter name="PmtComment1" label="PMT comment" group="PMT">Default Pmt Comment</parameter>
          <parameter name="PmtConnection2" label="PMT connection" group="PMT">10</parameter>
          <parameter name="PmtGain2" label="PMT gain" group="PMT">1</parameter>
          <parameter name="PmtHighVolt2" label="PMT high voltage" group="PMT">750</parameter>
          <parameter name="PmtOffset2" label="PMT offset status" group="PMT">0</parameter>
          <parameter name="PmtAcquisition2" label="PMT acquisition type" group="PMT">1</parameter>
          <parameter name="PmtComment2" label="PMT comment" group="PMT">Default Pmt Comment</parameter>
          <parameter name="InstrumentSpecific3" label="Instrument specific information" group="AUX">2</parameter>
          <parameter name="AuxChannelGain1" label="AUX channel gain" group="AUX">0</parameter>
          <parameter name="AuxChannelOffset1" label="AUX channel offset status" group="AUX">0</parameter>
          <parameter name="AuxChannelComment1" label="AUX channel comment" group="AUX">Default Aux Pmt Comment</parameter>
          <parameter name="AuxChannelGain2" label="AUX channel gain" group="AUX">1</parameter>
          <parameter name="AuxChannelOffset2" label="AUX channel offset status" group="AUX">0</parameter>
          <parameter name="AuxChannelComment2" label="AUX channel comment" group="AUX">Default Aux Pmt Comment</parameter>
          <parameter name="PlotInfo" label="Y-axis plotting annotation" group="YAxis">NULL</parameter>
          <parameter name="Corrected" label="Data corrected flag" group="MANIPULATION">0x0</parameter>
          <parameter name="Derivative" label="Degree of derivation" group="MANIPULATION">0</parameter>
          <parameter name="Norm" label="Normalized flag" group="MANIPULATION">0x0</parameter>
          <parameter name="Offset" label="Offset from the original data" group="MANIPULATION">0.000000</parameter>
          <parameter name="Scale" label="Scale from the original data" group="MANIPULATION">1.000000</parameter>
          <parameter name="Smooth" label="# of smoothing passes done" group="MANIPULATION">0</parameter>
          <parameter name="SmoothType" label="Smoothing type" group="MANIPULATION">1.000000</parameter>
          <values byteorder="INTEL" format="FLOAT32" numvalues="201">AADSPAAA1zwAAOs8AADSPACA7TwAANc8AADwPAAA1zwAAPU8AIDZPAAA+jwAAOE8AMAAPQCA7TwA
AP88AADrPADACj0AgPc8AMAKPQDAAD0AQA09AIAEPQBAEj0AwAU9AIAdPQBAFz0AACo9AIAiPQCA
Nj0AADQ9AMBBPQAAOT0AQE49AMBLPQBAXT0AAE09AMBaPQAATT0AgGM9AMBLPQBAYj0AwF89AMBp
PQDAaT0AQHY9AEB2PQCggj0AQIM9AECIPQBghj0AAIw9AICOPQBAkj0AII89AACRPQCgkT0AQJI9
AECNPQAgmT0AwI89AOCSPQCAiT0AAIw9AMCFPQBgkD0AQIM9AICOPQAAfz0AwIA9ACCAPQDAfT0A
QHE9ACCAPQAAaz0AAHA9AIBtPQAAfz0AwGQ9AEBxPQDAaT0AwHM9AABwPQCAbT0AAGY9AABrPQBA
Yj0AgG09AABXPQAAYT0AQFg9AMBfPQCAVD0AwFA9AEBJPQAAVz0AwFA9AEBYPQAAQz0AQEk9AEBE
PQAASD0AQEQ9AIBPPQCAQD0AgEU9AMA8PQDARj0AwDc9AIA7PQBAOj0AADk9AIAsPQDAPD0AAC89
AIA2PQDALT0AADk9AMAyPQCAMT0AQCY9AIAxPQDAIz0AgDE9AIAsPQBAKz0AwB49AIAsPQBAHD0A
wCg9AEAXPQBAIT0AQBw9AMAePQBAEj0AACA9AEASPQBAHD0AABY9AAAgPQAAFj0AgB09AIAOPQDA
GT0AwA89AEAXPQAAET0AQBc9AEANPQCAEz0AwA89AEANPQCACT0AgBM9AEADPQDACj0AwAA9AEAI
PQAABz0AgAk9AAD/PADACj0AgPc8AIAEPQCA/DwAwAo9AAD1PADABT0AAP88AEADPQCA9zwAgAk9
AAD6PACABD0AgPI8AIAEPQCA8jwAwAU9AADwPABAAz0AgO08AID8PAAA8DwAAAI9AIDtPACABD0A
gOM8AAD6PAAA6zwAgPw8AADrPACA/DwAAOY8AID8PACA3jwAAPo8AIDjPACA9zwAgOM8AID3PACA
3jwAAPA8
          </values>
        </Ydata>
        <Ydata label="Y" units="UNKNOWN">
          <parameter name="DataTags" label="# of data points with tags" group="YAxis">0</parameter>
          <parameter name="Channel" label="Instrument channel stored" group="YAxis">10</parameter>
          <parameter name="MaxX" label="X-axis of the data set maximum" group="YAxis">352.000000</parameter>
          <parameter name="MaxY" label="Data set maximum" group="YAxis">0.075073</parameter>
          <parameter name="MinX" label="X-axis of the data set minimum" group="YAxis">303.000000</parameter>
          <parameter name="MinY" label="Data set minimum" group="YAxis">0.025940</parameter>
          <parameter name="YaxisType" label="Y-axis type" group="YAxis">0x12 = Intensity</parameter>
          <parameter name="YaxisUnit" label="Y-axis unit" group="YAxis">0x0 = Unknown</parameter>
          <parameter name="YAxisLL" label="Y-axis lower limit" group="YAxis">0.025940</parameter>
          <parameter name="YAxisUL" label="Y-axis upper limit" group="YAxis">0.075073</parameter>
          <parameter name="LabelY" label="Y-axis Label" group="YAxis">Y</parameter>
          <parameter name="AcqStarted" label="Date and Time the acquisition was started" group="Standard">0000-00-00 00:00:00</parameter>
          <parameter name="AcqCompleted" label="Date and Time the acquisition was completed" group="Standard">0000-00-00 00:00:00</parameter>
          <parameter name="ID" label="Acquiring instruments serial (ID) number" group="Standard">0</parameter>
          <parameter name="Instrument" label="Instrument type acquired on" group="Standard">7</parameter>
          <parameter name="Modified" label="Date and Time the acquisition was modified" group="Standard">0000-00-00 00:00:00</parameter>
          <parameter name="OSVersion" label="Operating system version" group="Standard">0.000000</parameter>
          <parameter name="Version" label="Program version (&apos;C&apos;) acquired on" group="Standard">0.000000</parameter>
          <parameter name="Comment" label="Acquisition comment" group="Standard">NULL</parameter>
          <parameter name="AcqTitle" label="Acquisition title" group="Standard">NULL</parameter>
          <parameter name="User" label="User who acquired this data" group="Standard">NULL</parameter>
          <parameter name="Monochromators" label="# of monochromators attached" group="Hardware">2</parameter>
          <parameter name="Pmts" label="# of PMTs attached" group="Hardware">2</parameter>
          <parameter name="Lamp" label="Excitation light source type" group="LAMP">1</parameter>
          <parameter name="LampComment" label="Excitation light source comment" group="LAMP">Default Lamp Comment</parameter>
          <parameter name="MonochromatorAt1" label="Monochromator constant/setup wavelength" group="Monochromator">300.000000</parameter>
          <parameter name="MonochromatorShutter1" label="Monochromator shutter status" group="Monochromator">0</parameter>
          <parameter name="MonochromatorSlitIn1" label="Monochromator input slit width" group="Monochromator">4.000000</parameter>
          <parameter name="MonochromatorSlitOut1" label="Monochromator output slit width" group="Monochromator">4.000000</parameter>
          <parameter name="MonochromatorStepSize1" label="Monochromator step size" group="Monochromator">0.200000</parameter>
          <parameter name="MonochromatorType1" label="Monochromator type (excitation or emission)" group="Monochromator">10</parameter>
          <parameter name="MonochromatorUpperLimit1" label="Monochromator upper limit" group="Monochromator">950.000000</parameter>
          <parameter name="MonochromatorComment1" label="Monochromator comment" group="Monochromator">Default Monochromator Comment</parameter>
          <parameter name="MonochromatorNumFilter1" label="Monochromator # of filters switched" group="Filter">2</parameter>
          <parameter name="MonochromatorFilter1Wavelength1" label="Monochromator constant/setup wavelength" group="Filter">200.000000</parameter>
          <parameter name="MonochromatorFilter1Wavelength2" label="Monochromator constant/setup wavelength" group="Filter">300.000000</parameter>
          <parameter name="MonochromatorFilter1Comment" label="Monochromator filter comment" group="Filter">Default Filter Comment</parameter>
          <parameter name="MonochromatorPolarizerAngle1" label="Monochromator polarization angle" group="Polarizer">0.000000</parameter>
          <parameter name="MonochromatorPolarizerType1" label="Monochromator polarizer type" group="Polarizer">1</parameter>
          <parameter name="MonochromatorPolarizerComment1" label="Monochromator polarizer comment" group="Polarizer">Default Polarizer Comment</parameter>
          <parameter name="MonochromatorAt2" label="Monochromator constant/setup wavelength" group="Monochromator">400.000000</parameter>
          <parameter name="MonochromatorShutter2" label="Monochromator shutter status" group="Monochromator">0</parameter>
          <parameter name="MonochromatorSlitIn2" label="Monochromator input slit width" group="Monochromator">4.000000</parameter>
          <parameter name="MonochromatorSlitOut2" label="Monochromator output slit width" group="Monochromator">4.000000</parameter>
          <parameter name="MonochromatorStepSize2" label="Monochromator step size" group="Monochromator">0.200000</parameter>
          <parameter name="MonochromatorType2" label="Monochromator type (excitation or emission)" group="Monochromator">8</parameter>
          <parameter name="MonochromatorUpperLimit2" label="Monochromator upper limit" group="Monochromator">950.000000</parameter>
          <parameter name="MonochromatorComment2" label="Monochromator comment" group="Monochromator">Default Monochromator Comment</parameter>
          <parameter name="MonochromatorNumFilter2" label="Monochromator # of filters switched" group="Filter">3</parameter>
          <parameter name="MonochromatorFilter2Wavelength1" label="Monochromator constant/setup wavelength" group="Filter">200.000000</parameter>
          <parameter name="MonochromatorFilter2Wavelength2" label="Monochromator constant/setup wavelength" group="Filter">300.000000</parameter>
          <parameter name="MonochromatorFilter2Wavelength3" label="Monochromator constant/setup wavelength" group="Filter">400.000000</parameter>
          <parameter name="MonochromatorFilter2Comment" label="Monochromator filter comment" group="Filter">Default Filter Comment</parameter>
          <parameter name="MonochromatorPolarizerAngle2" label="Monochromator polarization angle" group="Polarizer">90.000000</parameter>
          <parameter name="MonochromatorPolarizerType2" label="Monochromator polarizer type" group="Polarizer">1</parameter>
          <parameter name="MonochromatorPolarizerComment2" label="Monochromator polarizer comment" group="Polarizer">Default Polarizer Comment</parameter>
          <parameter name="PmtConnection1" label="PMT connection" group="PMT">10</parameter>
          <parameter name="PmtGain1" label="PMT gain" group="PMT">1</parameter>
          <parameter name="PmtHighVolt1" label="PMT high voltage" group="PMT">750</parameter>
          <parameter name="PmtOffset1" label="PMT offset status" group="PMT">0</parameter>
          <parameter name="PmtAcquisition1" label="PMT acquisition type" group="PMT">0</parameter>
          <parameter name="PmtComment1" label="PMT comment" group="PMT">Default Pmt Comment</parameter>
          <parameter name="PmtConnection2" label="PMT connection" group="PMT">10</parameter>
          <parameter name="PmtGain2" label="PMT gain" group="PMT">1</parameter>
          <parameter name="PmtHighVolt2" label="PMT high voltage" group="PMT">750</parameter>
          <parameter name="PmtOffset2" label="PMT offset status" group="PMT">0</parameter>
          <parameter name="PmtAcquisition2" label="PMT acquisition type" group="PMT">1</parameter>
          <parameter name="PmtComment2" label="PMT comment" group="PMT">Default Pmt Comment</parameter>
          <parameter name="InstrumentSpecific3" label="Instrument specific information" group="AUX">2</parameter>
          <parameter name="AuxChannelGain1" label="AUX channel gain" group="AUX">0</parameter>
          <parameter name="AuxChannelOffset1" label="AUX channel offset status" group="AUX">0</parameter>
          <parameter name="AuxChannelComment1" label="AUX channel comment" group="AUX">Default Aux Pmt Comment</parameter>
          <parameter name="AuxChannelGain2" label="AUX channel gain" group="AUX">1</parameter>
          <parameter name="AuxChannelOffset2" label="AUX channel offset status" group="AUX">0</parameter>
          <parameter name="AuxChannelComment2" label="AUX channel comment" group="AUX">Default Aux Pmt Comment</parameter>
          <parameter name="PlotInfo" label="Y-axis plotting annotation" group="YAxis">NULL</parameter>
          <parameter name="Corrected" label="Data corrected flag" group="MANIPULATION">0x0</parameter>
          <parameter name="Derivative" label="Degree of derivation" group="MANIPULATION">0</parameter>
          <parameter name="Norm" label="Normalized flag" group="MANIPULATION">0x0</parameter>
          <parameter name="Offset" label="Offset from the original data" group="MANIPULATION">0.000000</parameter>
          <parameter name="Scale" label="Scale from the original data" group="MANIPULATION">1.000000</parameter>
          <parameter name="Smooth" label="# of smoothing passes done" group="MANIPULATION">0</parameter>
          <parameter name="SmoothType" label="Smoothing type" group="MANIPULATION">1.000000</parameter>
          <values byteorder="INTEL" format="FLOAT32" numvalues="201">AIDePACA3jwAgPI8AIDUPAAA9TwAgNk8AID3PAAA3DwAgPw8AADrPAAA+jwAAPA8AIAEPQAA8DwA
wAo9AAD6PAAAET0AQAM9AMAPPQCACT0AQBI9AEANPQAAGz0AgBM9AAAqPQCAJz0AADk9AIA7PQCA
Sj0AAE09AABcPQDAXz0AAGs9AMBuPQDAbj0AgG09AAB1PQCAcj0AoII9AEBxPQCAdz0AQHE9AIB8
PQAggD0AoIc9AACHPQBAjT0AYIs9AKCRPQDAjz0AgJg9ACCPPQDAmT0AoJE9AECXPQCAkz0AQJc9
AGCVPQAAlj0AII89AOCSPQCgkT0AQI09AKCHPQCAjj0A4IM9AICEPQBAgz0AYIY9AIByPQCAhD0A
wHg9AACCPQCAcj0AAH89AAB1PQBAez0AQHE9AEB2PQAAcD0AgHc9AEBxPQAAej0AwHM9AEBsPQCA
Xj0AQGw9AMBkPQCAXj0AQFg9AEBiPQAAVz0AgGM9AIBPPQCAWT0AgEo9AEBTPQBATj0AgE89AIBF
PQCASj0AQEQ9AMBGPQDARj0AAEM9AMA8PQBAPz0AADQ9AEA/PQAAND0AQDo9AAAvPQAAND0AACo9
AMAyPQAAKj0AwDI9AIAiPQCALD0AACU9AEArPQAAJT0AQCY9AIAdPQBAIT0AgBg9AAAlPQCAGD0A
QCE9AAAWPQAAID0AABY9AMAePQBAEj0AgBg9AMAPPQCAHT0AgA49AAAWPQAAET0AwBQ9AIAJPQDA
Dz0AgAk9AMAUPQBADT0AwA89AIAJPQBADT0AQAg9AAAMPQBAAz0AgA49AIAEPQAABz0AAAI9AMAK
PQDABT0AQBI9AAD/PACADj0AAPo8AEAIPQBAAz0AQAg9AID8PAAABz0AAPo8AAACPQCA/DwAAAI9
AID8PABAAz0AgPI8AAACPQCA8jwAgAQ9AIDyPAAA/zwAgPI8AAACPQAA9TwAgAQ9AIDtPAAA/zwA
gOg8AAD6PAAA6zwAgPw8AADmPAAA/zwAgOg8AAD1PACA6DwAgPc8AIDoPAAA+jwAAOY8AIDyPACA
4zwAgPc8
          </values>
        </Ydata>
        <Ydata label="Y" units="UNKNOWN">
          <parameter name="DataTags" label="# of data points with tags" group="YAxis">0</parameter>
          <parameter name="Channel" label="Instrument channel stored" group="YAxis">10</parameter>
          <parameter name="MaxX" label="X-axis of the data set maximum" group="YAxis">350.000000</parameter>
          <parameter name="MaxY" label="Data set maximum" group="YAxis">0.089111</parameter>
          <parameter name="MinX" label="X-axis of the data set minimum" group="YAxis">303.000000</parameter>
          <parameter name="MinY" label="Data set minimum" group="YAxis">0.025330</parameter>
          <parameter name="YaxisType" label="Y-axis type" group="YAxis">0x12 = Intensity</parameter>
          <parameter name="YaxisUnit" label="Y-axis unit" group="YAxis">0x0 = Unknown</parameter>
          <parameter name="YAxisLL" label="Y-axis lower limit" group="YAxis">0.025330</parameter>
          <parameter name="YAxisUL" label="Y-axis upper limit" group="YAxis">0.089111</parameter>
          <parameter name="LabelY" label="Y-axis Label" group="YAxis">Y</parameter>
          <parameter name="AcqStarted" label="Date and Time the acquisition was started" group="Standard">0000-00-00 00:00:00</parameter>
          <parameter name="AcqCompleted" label="Date and Time the acquisition was completed" group="Standard">0000-00-00 00:00:00</parameter>
          <parameter name="ID" label="Acquiring instruments serial (ID) number" group="Standard">0</parameter>
          <parameter name="Instrument" label="Instrument type acquired on" group="Standard">7</parameter>
          <parameter name="Modified" label="Date and Time the acquisition was modified" group="Standard">0000-00-00 00:00:00</parameter>
          <parameter name="OSVersion" label="Operating system version" group="Standard">0.000000</parameter>
          <parameter name="Version" label="Program version (&apos;C&apos;) acquired on" group="Standard">0.000000</parameter>
          <parameter name="Comment" label="Acquisition comment" group="Standard">NULL</parameter>
          <parameter name="AcqTitle" label="Acquisition title" group="Standard">NULL</parameter>
          <parameter name="User" label="User who acquired this data" group="Standard">NULL</parameter>
          <parameter name="Monochromators" label="# of monochromators attached" group="Hardware">2</parameter>
          <parameter name="Pmts" label="# of PMTs attached" group="Hardware">2</parameter>
          <parameter name="Lamp" label="Excitation light source type" group="LAMP">1</parameter>
          <parameter name="LampComment" label="Excitation light source comment" group="LAMP">Default Lamp Comment</parameter>
          <parameter name="MonochromatorAt1" label="Monochromator constant/setup wavelength" group="Monochromator">300.000000</parameter>
          <parameter name="MonochromatorShutter1" label="Monochromator shutter status" group="Monochromator">0</parameter>
          <parameter name="MonochromatorSlitIn1" label="Monochromator input slit width" group="Monochromator">4.000000</parameter>
          <parameter name="MonochromatorSlitOut1" label="Monochromator output slit width" group="Monochromator">4.000000</parameter>
          <parameter name="MonochromatorStepSize1" label="Monochromator step size" group="Monochromator">0.200000</parameter>
          <parameter name="MonochromatorType1" label="Monochromator type (excitation or emission)" group="Monochromator">10</parameter>
          <parameter name="MonochromatorUpperLimit1" label="Monochromator upper limit" group="Monochromator">950.000000</parameter>
          <parameter name="MonochromatorComment1" label="Monochromator comment" group="Monochromator">Default Monochromator Comment</parameter>
          <parameter name="MonochromatorNumFilter1" label="Monochromator # of filters switched" group="Filter">2</parameter>
          <parameter name="MonochromatorFilter1Wavelength1" label="Monochromator constant/setup wavelength" group="Filter">200.000000</parameter>
          <parameter name="MonochromatorFilter1Wavelength2" label="Monochromator constant/setup wavelength" group="Filter">300.000000</parameter>
          <parameter name="MonochromatorFilter1Comment" label="Monochromator filter comment" group="Filter">Default Filter Comment</parameter>
          <parameter name="MonochromatorPolarizerAngle1" label="Monochromator polarization angle" group="Polarizer">0.000000</parameter>
          <parameter name="MonochromatorPolarizerType1" label="Monochromator polarizer type" group="Polarizer">1</parameter>
          <parameter name="MonochromatorPolarizerComment1" label="Monochromator polarizer comment" group="Polarizer">Default Polarizer Comment</parameter>
          <parameter name="MonochromatorAt2" label="Monochromator constant/setup wavelength" group="Monochromator">400.000000</parameter>
          <parameter name="MonochromatorShutter2" label="Monochromator shutter status" group="Monochromator">0</parameter>
          <parameter name="MonochromatorSlitIn2" label="Monochromator input slit width" group="Monochromator">4.000000</parameter>
          <parameter name="MonochromatorSlitOut2" label="Monochromator output slit width" group="Monochromator">4.000000</parameter>
          <parameter name="MonochromatorStepSize2" label="Monochromator step size" group="Monochromator">0.200000</parameter>
          <parameter name="MonochromatorType2" label="Monochromator type (excitation or emission)" group="Monochromator">8</parameter>
          <parameter name="MonochromatorUpperLimit2" label="Monochromator upper limit" group="Monochromator">950.000000</parameter>
          <parameter name="MonochromatorComment2" label="Monochromator comment" group="Monochromator">Default Monochromator Comment</parameter>
          <parameter name="MonochromatorNumFilter2" label="Monochromator # of filters switched" group="Filter">3</parameter>
          <parameter name="MonochromatorFilter2Wavelength1" label="Monochromator constant/setup wavelength" group="Filter">200.000000</parameter>
          <parameter name="MonochromatorFilter2Wavelength2" label="Monochromator constant/setup wavelength" group="Filter">300.000000</parameter>
          <parameter name="MonochromatorFilter2Wavelength3" label="Monochromator constant/setup wavelength" group="Filter">400.000000</parameter>
          <parameter name="MonochromatorFilter2Comment" label="Monochromator filter comment" group="Filter">Default Filter Comment</parameter>
          <parameter name="MonochromatorPolarizerAngle2" label="Monochromator polarization angle" group="Polarizer">90.000000</parameter>
          <parameter name="MonochromatorPolarizerType2" label="Monochromator polarizer type" group="Polarizer">1</parameter>
          <parameter name="MonochromatorPolarizerComment2" label="Monochromator polarizer comment" group="Polarizer">Default Polarizer Comment</parameter>
          <parameter name="PmtConnection1" label="PMT connection" group="PMT">10</parameter>
          <parameter name="PmtGain1" label="PMT gain" group="PMT">1</parameter>
          <parameter name="PmtHighVolt1" label="PMT high voltage" group="PMT">750</parameter>
          <parameter name="PmtOffset1" label="PMT offset status" group="PMT">0</parameter>
          <parameter name="PmtAcquisition1" label="PMT acquisition type" group="PMT">0</parameter>
          <parameter name="PmtComment1" label="PMT comment" group="PMT">Default Pmt Comment</parameter>
          <parameter name="PmtConnection2" label="PMT connection" group="PMT">10</parameter>
          <parameter name="PmtGain2" label="PMT gain" group="PMT">1</parameter>
          <parameter name="PmtHighVolt2" label="PMT high voltage" group="PMT">750</parameter>
          <parameter name="PmtOffset2" label="PMT offset status" group="PMT">0</parameter>
          <parameter name="PmtAcquisition2" label="PMT acquisition type" group="PMT">1</parameter>
          <parameter name="PmtComment2" label="PMT comment" group="PMT">Default Pmt Comment</parameter>
          <parameter name="InstrumentSpecific3" label="Instrument specific information" group="AUX">2</parameter>
          <parameter name="AuxChannelGain1" label="AUX channel gain" group="AUX">0</parameter>
          <parameter name="AuxChannelOffset1" label="AUX channel offset status" group="AUX">0</parameter>
          <parameter name="AuxChannelComment1" label="AUX channel comment" group="AUX">Default Aux Pmt Comment</parameter>
          <parameter name="AuxChannelGain2" label="AUX channel gain" group="AUX">1</parameter>
          <parameter name="AuxChannelOffset2" label="AUX channel offset status" group="AUX">0</parameter>
          <parameter name="AuxChannelComment2" label="AUX channel comment" group="AUX">Default Aux Pmt Comment</parameter>
          <parameter name="PlotInfo" label="Y-axis plotting annotation" group="YAxis">NULL</parameter>
          <parameter name="Corrected" label="Data corrected flag" group="MANIPULATION">0x0</parameter>
          <parameter name="Derivative" label="Degree of derivation" group="MANIPULATION">0</parameter>
          <parameter name="Norm" label="Normalized flag" group="MANIPULATION">0x0</parameter>
          <parameter name="Offset" label="Offset from the original data" group="MANIPULATION">0.000000</parameter>
          <parameter name="Scale" label="Scale from the original data" group="MANIPULATION">1.000000</parameter>
          <parameter name="Smooth" label="# of smoothing passes done" group="MANIPULATION">0</parameter>
          <parameter name="SmoothType" label="Smoothing type" group="MANIPULATION">1.000000</parameter>
          <values byteorder="INTEL" format="FLOAT32" numvalues="201">AADXPAAA1zwAAOs8AIDPPACA7TwAgN48AAD1PAAA1zwAAPo8AIDePAAAAj0AgPc8AAAHPQCA/DwA
AAw9AIAEPQDAFD0AQA09AAAbPQDAFD0AACA9AIAdPQDALT0AgCI9AEA/PQCART0AAGE9AMBfPQAg
gD0AwHg9ACCKPQAgjz0AYJU9AGCQPQDAnj0AAJY9AMCePQDglz0AoKA9AKCbPQDgoT0AYJU9ACCj
PQDAnj0AgKc9AICiPQBAtT0A4KE9AMCyPQBgqT0AgLY9AGCkPQDAsj0AwK09AGCuPQDArT0AYK49
AKCgPQDArT0AgKI9AECrPQAgnj0AAKA9AICYPQCglj0AIJQ9ACCPPQDAjz0AwIo9AKCCPQAAjD0A
4IM9AMCAPQAgij0AYIE9AIB8PQDAgD0AoII9AICEPQAAej0AAIw9AMB9PQCAdz0AYIE9AICEPQAA
ej0AgHw9AIBtPQBAcT0AAGY9AIBtPQCAaD0AAHA9AIBoPQBAZz0AQFM9AIBtPQBAWD0AQFg9AMBL
PQAAXD0AQE49AEBTPQDARj0AgFQ9AEBJPQAATT0AgEA9AMBLPQAAQz0AwEE9AIA2PQBAPz0AQDU9
AAA5PQDAMj0AgDY9AMA3PQCANj0AwC09AIAxPQDAIz0AgCw9AMAePQCALD0AQCE9AIAsPQCAIj0A
gCw9AAAbPQDAKD0AABs9AEAhPQBAHD0AgCI9AEAXPQCAHT0AgBM9AIAdPQBAEj0AgB09AMAPPQDA
FD0AAAw9AEASPQCADj0AgBM9AAARPQCADj0AgAk9AAAMPQDACj0AQBI9AIAJPQBADT0AQAM9AEAN
PQAABz0AgA49AMAAPQCACT0AQAM9AEANPQAA+jwAgA49AAD/PACACT0AgPI8AMAKPQAA9TwAwAU9
AMAAPQCABD0AgPI8AAAHPQCA9zwAgAQ9AAD1PADABT0AgPI8AMAFPQCA7TwAQAM9AIDtPAAABz0A
gO08AAACPQCA7TwAwAA9AADwPABAAz0AgO08AAD/PAAA6zwAQAM9AIDjPACA/DwAgOg8AAD/PAAA
4TwAgPw8
          </values>
        </Ydata>
        <Ydata label="Y" units="UNKNOWN">
          <parameter name="DataTags" label="# of data points with tags" group="YAxis">0</parameter>
          <parameter name="Channel" label="Instrument channel stored" group="YAxis">10</parameter>
          <parameter name="MaxX" label="X-axis of the data set maximum" group="YAxis">352.000000</parameter>
          <parameter name="MaxY" label="Data set maximum" group="YAxis">0.120850</parameter>
          <parameter name="MinX" label="X-axis of the data set minimum" group="YAxis">303.000000</parameter>
          <parameter name="MinY" label="Data set minimum" group="YAxis">0.026550</parameter>
          <parameter name="YaxisType" label="Y-axis type" group="YAxis">0x12 = Intensity</parameter>
          <parameter name="YaxisUnit" label="Y-axis unit" group="YAxis">0x0 = Unknown</parameter>
          <parameter name="YAxisLL" label="Y-axis lower limit" group="YAxis">0.026550</parameter>
          <parameter name="YAxisUL" label="Y-axis upper limit" group="YAxis">0.120850</parameter>
          <parameter name="LabelY" label="Y-axis Label" group="YAxis">Y</parameter>
          <parameter name="AcqStarted" label="Date and Time the acquisition was started" group="Standard">0000-00-00 00:00:00</parameter>
          <parameter name="AcqCompleted" label="Date and Time the acquisition was completed" group="Standard">0000-00-00 00:00:00</parameter>
          <parameter name="ID" label="Acquiring instruments serial (ID) number" group="Standard">0</parameter>
          <parameter name="Instrument" label="Instrument type acquired on" group="Standard">7</parameter>
          <parameter name="Modified" label="Date and Time the acquisition was modified" group="Standard">0000-00-00 00:00:00</parameter>
          <parameter name="OSVersion" label="Operating system version" group="Standard">0.000000</parameter>
          <parameter name="Version" label="Program version (&apos;C&apos;) acquired on" group="Standard">0.000000</parameter>
          <parameter name="Comment" label="Acquisition comment" group="Standard">NULL</parameter>
          <parameter name="AcqTitle" label="Acquisition title" group="Standard">NULL</parameter>
          <parameter name="User" label="User who acquired this data" group="Standard">NULL</parameter>
          <parameter name="Monochromators" label="# of monochromators attached" group="Hardware">2</parameter>
          <parameter name="Pmts" label="# of PMTs attached" group="Hardware">2</parameter>
          <parameter name="Lamp" label="Excitation light source type" group="LAMP">1</parameter>
          <parameter name="LampComment" label="Excitation light source comment" group="LAMP">Default Lamp Comment</parameter>
          <parameter name="MonochromatorAt1" label="Monochromator constant/setup wavelength" group="Monochromator">300.000000</parameter>
          <parameter name="MonochromatorShutter1" label="Monochromator shutter status" group="Monochromator">0</parameter>
          <parameter name="MonochromatorSlitIn1" label="Monochromator input slit width" group="Monochromator">4.000000</parameter>
          <parameter name="MonochromatorSlitOut1" label="Monochromator output slit width" group="Monochromator">4.000000</parameter>
          <parameter name="MonochromatorStepSize1" label="Monochromator step size" group="Monochromator">0.200000</parameter>
          <parameter name="MonochromatorType1" label="Monochromator type (excitation or emission)" group="Monochromator">10</parameter>
          <parameter name="MonochromatorUpperLimit1" label="Monochromator upper limit" group="Monochromator">950.000000</parameter>
          <parameter name="MonochromatorComment1" label="Monochromator comment" group="Monochromator">Default Monochromator Comment</parameter>
          <parameter name="MonochromatorNumFilter1" label="Monochromator # of filters switched" group="Filter">2</parameter>
          <parameter name="MonochromatorFilter1Wavelength1" label="Monochromator constant/setup wavelength" group="Filter">200.000000</parameter>
          <parameter name="MonochromatorFilter1Wavelength2" label="Monochromator constant/setup wavelength" group="Filter">300.000000</parameter>
          <parameter name="MonochromatorFilter1Comment" label="Monochromator filter comment" group="Filter">Default Filter Comment</parameter>
          <parameter name="MonochromatorPolarizerAngle1" label="Monochromator polarization angle" group="Polarizer">0.000000</parameter>
          <parameter name="MonochromatorPolarizerType1" label="Monochromator polarizer type" group="Polarizer">1</parameter>
          <parameter name="MonochromatorPolarizerComment1" label="Monochromator polarizer comment" group="Polarizer">Default Polarizer Comment</parameter>
          <parameter name="MonochromatorAt2" label="Monochromator constant/setup wavelength" group="Monochromator">400.000000</parameter>
          <parameter name="MonochromatorShutter2" label="Monochromator shutter status" group="Monochromator">0</parameter>
          <parameter name="MonochromatorSlitIn2" label="Monochromator input slit width" group="Monochromator">4.000000</parameter>
          <parameter name="MonochromatorSlitOut2" label="Monochromator output slit width" group="Monochromator">4.000000</parameter>
          <parameter name="MonochromatorStepSize2" label="Monochromator step size" group="Monochromator">0.200000</parameter>
          <parameter name="MonochromatorType2" label="Monochromator type (excitation or emission)" group="Monochromator">8</parameter>
          <parameter name="MonochromatorUpperLimit2" label="Monochromator upper limit" group="Monochromator">950.000000</parameter>
          <parameter name="MonochromatorComment2" label="Monochromator comment" group="Monochromator">Default Monochromator Comment</parameter>
          <parameter name="MonochromatorNumFilter2" label="Monochromator # of filters switched" group="Filter">3</parameter>
          <parameter name="MonochromatorFilter2Wavelength1" label="Monochromator constant/setup wavelength" group="Filter">200.000000</parameter>
          <parameter name="MonochromatorFilter2Wavelength2" label="Monochromator constant/setup wavelength" group="Filter">300.000000</parameter>
          <parameter name="MonochromatorFilter2Wavelength3" label="Monochromator constant/setup wavelength" group="Filter">400.000000</parameter>
          <parameter name="MonochromatorFilter2Comment" label="Monochromator filter comment" group="Filter">Default Filter Comment</parameter>
          <parameter name="MonochromatorPolarizerAngle2" label="Monochromator polarization angle" group="Polarizer">90.000000</parameter>
          <parameter name="MonochromatorPolarizerType2" label="Monochromator polarizer type" group="Polarizer">1</parameter>
          <parameter name="MonochromatorPolarizerComment2" label="Monochromator polarizer comment" group="Polarizer">Default Polarizer Comment</parameter>
          <parameter name="PmtConnection1" label="PMT connection" group="PMT">10</parameter>
          <parameter name="PmtGain1" label="PMT gain" group="PMT">1</parameter>
          <parameter name="PmtHighVolt1" label="PMT high voltage" group="PMT">750</parameter>
          <parameter name="PmtOffset1" label="PMT offset status" group="PMT">0</parameter>
          <parameter name="PmtAcquisition1" label="PMT acquisition type" group="PMT">0</parameter>
          <parameter name="PmtComment1" label="PMT comment" group="PMT">Default Pmt Comment</parameter>
          <parameter name="PmtConnection2" label="PMT connection" group="PMT">10</parameter>
          <parameter name="PmtGain2" label="PMT gain" group="PMT">1</parameter>
          <parameter name="PmtHighVolt2" label="PMT high voltage" group="PMT">750</parameter>
          <parameter name="PmtOffset2" label="PMT offset status" group="PMT">0</parameter>
          <parameter name="PmtAcquisition2" label="PMT acquisition type" group="PMT">1</parameter>
          <parameter name="PmtComment2" label="PMT comment" group="PMT">Default Pmt Comment</parameter>
          <parameter name="InstrumentSpecific3" label="Instrument specific information" group="AUX">2</parameter>
          <parameter name="AuxChannelGain1" label="AUX channel gain" group="AUX">0</parameter>
          <parameter name="AuxChannelOffset1" label="AUX channel offset status" group="AUX">0</parameter>
          <parameter name="AuxChannelComment1" label="AUX channel comment" group="AUX">Default Aux Pmt Comment</parameter>
          <parameter name="AuxChannelGain2" label="AUX channel gain" group="AUX">1</parameter>
          <parameter name="AuxChannelOffset2" label="AUX channel offset status" group="AUX">0</parameter>
          <parameter name="AuxChannelComment2" label="AUX channel comment" group="AUX">Default Aux Pmt Comment</parameter>
          <parameter name="PlotInfo" label="Y-axis plotting annotation" group="YAxis">NULL</parameter>
          <parameter name="Corrected" label="Data corrected flag" group="MANIPULATION">0x0</parameter>
          <parameter name="Derivative" label="Degree of derivation" group="MANIPULATION">0</parameter>
          <parameter name="Norm" label="Normalized flag" group="MANIPULATION">0x0</parameter>
          <parameter name="Offset" label="Offset from the original data" group="MANIPULATION">0.000000</parameter>
          <parameter name="Scale" label="Scale from the original data" group="MANIPULATION">1.000000</parameter>
          <parameter name="Smooth" label="# of smoothing passes done" group="MANIPULATION">0</parameter>
          <parameter name="SmoothType" label="Smoothing type" group="MANIPULATION">1.000000</parameter>
          <values byteorder="INTEL" format="FLOAT32" numvalues="201">AIDePACA3jwAgPI8AIDZPACA8jwAAOE8AAD1PAAA5jwAwAA9AIDtPAAAAj0AAAI9AEANPQBACD0A
wBk9AMAPPQDAIz0AgB09AAAvPQAAKj0AgEA9AAA+PQDARj0AgEU9AEBnPQCAdz0A4Ig9ACCUPQBA
oT0A4Ks9AADDPQAAyD0AwNA9AGDWPQBA2D0A4NM9AKDhPQDg2D0AwOQ9AADhPQDg3T0AwN89AIDj
PQBA5z0AYOo9AKDmPQCA7T0AYPQ9AOD2PQAA9T0AoPA9AKD1PQCA9z0AIO49AGDvPQDg7D0AwOk9
AEDiPQAg6T0AQNg9AKDSPQDgzj0AINU9AMDGPQAgwT0AIME9AKC5PQBgrj0A4LA9AACvPQAArz0A
wKM9AOCrPQAAqj0AwKM9AEChPQBgqT0AYJ89AGCfPQCAoj0AoKA9AICdPQAAoD0AgKI9AECcPQCA
mD0A4Jw9AOCSPQAAlj0AYJU9AICTPQAAjD0AAJE9AKCMPQCghz0AQIg9AGCLPQAghT0AAII9AEB7
PQBghj0AQHs9AOCDPQBAcT0AQHs9AMBuPQBAcT0AgGM9AEBnPQDAXz0AAGY9AABcPQAAXD0AgFk9
AMBaPQDAVT0AAFw9AIBUPQDAUD0AwEs9AEBTPQCAQD0AgEU9AIA7PQBAST0AgDY9AEBEPQCAOz0A
QD89AAA5PQCART0AADk9AEA/PQDAMj0AwDc9AIAsPQAAOT0AAC89AIAxPQAAKj0AwDc9AMAjPQAA
JT0AwCM9AIAnPQBAIT0AACo9AEAhPQDAHj0AQBc9AMAjPQAAGz0AwB49AIAYPQDAHj0AQBc9AEAc
PQAAFj0AgB09AIAOPQAAGz0AwA89AIATPQBADT0AgBM9AAAMPQDAFD0AQAg9AAAWPQBAAz0AAAw9
AIAJPQAAET0AAAc9AMAPPQDAAD0AgAk9AIAEPQCACT0AQAM9AMAKPQDABT0AwAo9AMAAPQAABz0A
AP88AAAHPQCABD0AQAg9AID8PABAAz0AgPc8AEADPQCA8jwAQAM9AID3PAAAAj0AAPo8AEADPQCA
8jwAQAM9
          </values>
        </Ydata>
        <Ydata label="Y" units="UNKNOWN">
          <parameter name="DataTags" label="# of data points with tags" group="YAxis">0</parameter>
          <parameter name="Channel" label="Instrument channel stored" group="YAxis">10</parameter>
          <parameter name="MaxX" label="X-axis of the data set maximum" group="YAxis">350.000000</parameter>
          <parameter name="MaxY" label="Data set maximum" group="YAxis">0.184021</parameter>
          <parameter name="MinX" label="X-axis of the data set minimum" group="YAxis">303.000000</parameter>
          <parameter name="MinY" label="Data set minimum" group="YAxis">0.026245</parameter>
          <parameter name="YaxisType" label="Y-axis type" group="YAxis">0x12 = Intensity</parameter>
          <parameter name="YaxisUnit" label="Y-axis unit" group="YAxis">0x0 = Unknown</parameter>
          <parameter name="YAxisLL" label="Y-axis lower limit" group="YAxis">0.026245</parameter>
          <parameter name="YAxisUL" label="Y-axis upper limit" group="YAxis">0.184021</parameter>
          <parameter name="LabelY" label="Y-axis Label" group="YAxis">Y</parameter>
          <parameter name="AcqStarted" label="Date and Time the acquisition was started" group="Standard">0000-00-00 00:00:00</parameter>
          <parameter name="AcqCompleted" label="Date and Time the acquisition was completed" group="Standard">0000-00-00 00:00:00</parameter>
          <parameter name="ID" label="Acquiring instruments serial (ID) number" group="Standard">0</parameter>
          <parameter name="Instrument" label="Instrument type acquired on" group="Standard">7</parameter>
          <parameter name="Modified" label="Date and Time the acquisition was modified" group="Standard">0000-00-00 00:00:00</parameter>
          <parameter name="OSVersion" label="Operating system version" group="Standard">0.000000</parameter>
          <parameter name="Version" label="Program version (&apos;C&apos;) acquired on" group="Standard">0.000000</parameter>
          <parameter name="Comment" label="Acquisition comment" group="Standard">NULL</parameter>
          <parameter name="AcqTitle" label="Acquisition title" group="Standard">NULL</parameter>
          <parameter name="User" label="User who acquired this data" group="Standard">NULL</parameter>
          <parameter name="Monochromators" label="# of monochromators attached" group="Hardware">2</parameter>
          <parameter name="Pmts" label="# of PMTs attached" group="Hardware">2</parameter>
          <parameter name="Lamp" label="Excitation light source type" group="LAMP">1</parameter>
          <parameter name="LampComment" label="Excitation light source comment" group="LAMP">Default Lamp Comment</parameter>
          <parameter name="MonochromatorAt1" label="Monochromator constant/setup wavelength" group="Monochromator">300.000000</parameter>
          <parameter name="MonochromatorShutter1" label="Monochromator shutter status" group="Monochromator">0</parameter>
          <parameter name="MonochromatorSlitIn1" label="Monochromator input slit width" group="Monochromator">4.000000</parameter>
          <parameter name="MonochromatorSlitOut1" label="Monochromator output slit width" group="Monochromator">4.000000</parameter>
          <parameter name="MonochromatorStepSize1" label="Monochromator step size" group="Monochromator">0.200000</parameter>
          <parameter name="MonochromatorType1" label="Monochromator type (excitation or emission)" group="Monochromator">10</parameter>
          <parameter name="MonochromatorUpperLimit1" label="Monochromator upper limit" group="Monochromator">950.000000</parameter>
          <parameter name="MonochromatorComment1" label="Monochromator comment" group="Monochromator">Default Monochromator Comment</parameter>
          <parameter name="MonochromatorNumFilter1" label="Monochromator # of filters switched" group="Filter">2</parameter>
          <parameter name="MonochromatorFilter1Wavelength1" label="Monochromator constant/setup wavelength" group="Filter">200.000000</parameter>
          <parameter name="MonochromatorFilter1Wavelength2" label="Monochromator constant/setup wavelength" group="Filter">300.000000</parameter>
          <parameter name="MonochromatorFilter1Comment" label="Monochromator filter comment" group="Filter">Default Filter Comment</parameter>
          <parameter name="MonochromatorPolarizerAngle1" label="Monochromator polarization angle" group="Polarizer">0.000000</parameter>
          <parameter name="MonochromatorPolarizerType1" label="Monochromator polarizer type" group="Polarizer">1</parameter>
          <parameter name="MonochromatorPolarizerComment1" label="Monochromator polarizer comment" group="Polarizer">Default Polarizer Comment</parameter>
          <parameter name="MonochromatorAt2" label="Monochromator constant/setup wavelength" group="Monochromator">400.000000</parameter>
          <parameter name="MonochromatorShutter2" label="Monochromator shutter status" group="Monochromator">0</parameter>
          <parameter name="MonochromatorSlitIn2" label="Monochromator input slit width" group="Monochromator">4.000000</parameter>
          <parameter name="MonochromatorSlitOut2" label="Monochromator output slit width" group="Monochromator">4.000000</parameter>
          <parameter name="MonochromatorStepSize2" label="Monochromator step size" group="Monochromator">0.200000</parameter>
          <parameter name="MonochromatorType2" label="Monochromator type (excitation or emission)" group="Monochromator">8</parameter>
          <parameter name="MonochromatorUpperLimit2" label="Monochromator upper limit" group="Monochromator">950.000000</parameter>
          <parameter name="MonochromatorComment2" label="Monochromator comment" group="Monochromator">Default Monochromator Comment</parameter>
          <parameter name="MonochromatorNumFilter2" label="Monochromator # of filters switched" group="Filter">3</parameter>
          <parameter name="MonochromatorFilter2Wavelength1" label="Monochromator constant/setup wavelength" group="Filter">200.000000</parameter>
          <parameter name="MonochromatorFilter2Wavelength2" label="Monochromator constant/setup wavelength" group="Filter">300.000000</parameter>
          <parameter name="MonochromatorFilter2Wavelength3" label="Monochromator constant/setup wavelength" group="Filter">400.000000</parameter>
          <parameter name="MonochromatorFilter2Comment" label="Monochromator filter comment" group="Filter">Default Filter Comment</parameter>
          <parameter name="MonochromatorPolarizerAngle2" label="Monochromator polarization angle" group="Polarizer">90.000000</parameter>
          <parameter name="MonochromatorPolarizerType2" label="Monochromator polarizer type" group="Polarizer">1</parameter>
          <parameter name="MonochromatorPolarizerComment2" label="Monochromator polarizer comment" group="Polarizer">Default Polarizer Comment</parameter>
          <parameter name="PmtConnection1" label="PMT connection" group="PMT">10</parameter>
          <parameter name="PmtGain1" label="PMT gain" group="PMT">1</parameter>
          <parameter name="PmtHighVolt1" label="PMT high voltage" group="PMT">750</parameter>
          <parameter name="PmtOffset1" label="PMT offset status" group="PMT">0</parameter>
          <parameter name="PmtAcquisition1" label="PMT acquisition type" group="PMT">0</parameter>
          <parameter name="PmtComment1" label="PMT comment" group="PMT">Default Pmt Comment</parameter>
          <parameter name="PmtConnection2" label="PMT connection" group="PMT">10</parameter>
          <parameter name="PmtGain2" label="PMT gain" group="PMT">1</parameter>
          <parameter name="PmtHighVolt2" label="PMT high voltage" group="PMT">750</parameter>
          <parameter name="PmtOffset2" label="PMT offset status" group="PMT">0</parameter>
          <parameter name="PmtAcquisition2" label="PMT acquisition type" group="PMT">1</parameter>
          <parameter name="PmtComment2" label="PMT comment" group="PMT">Default Pmt Comment</parameter>
          <parameter name="InstrumentSpecific3" label="Instrument specific information" group="AUX">2</parameter>
          <parameter name="AuxChannelGain1" label="AUX channel gain" group="AUX">0</parameter>
          <parameter name="AuxChannelOffset1" label="AUX channel offset status" group="AUX">0</parameter>
          <parameter name="AuxChannelComment1" label="AUX channel comment" group="AUX">Default Aux Pmt Comment</parameter>
          <parameter name="AuxChannelGain2" label="AUX channel gain" group="AUX">1</parameter>
          <parameter name="AuxChannelOffset2" label="AUX channel offset status" group="AUX">0</parameter>
          <parameter name="AuxChannelComment2" label="AUX channel comment" group="AUX">Default Aux Pmt Comment</parameter>
          <parameter name="PlotInfo" label="Y-axis plotting annotation" group="YAxis">NULL</parameter>
          <parameter name="Corrected" label="Data corrected flag" group="MANIPULATION">0x0</parameter>
          <parameter name="Derivative" label="Degree of derivation" group="MANIPULATION">0</parameter>
          <parameter name="Norm" label="Normalized flag" group="MANIPULATION">0x0</parameter>
          <parameter name="Offset" label="Offset from the original data" group="MANIPULATION">0.000000</parameter>
          <parameter name="Scale" label="Scale from the original data" group="MANIPULATION">1.000000</parameter>
          <parameter name="Smooth" label="# of smoothing passes done" group="MANIPULATION">0</parameter>
          <parameter name="SmoothType" label="Smoothing type" group="MANIPULATION">1.000000</parameter>
          <values byteorder="INTEL" format="FLOAT32" numvalues="201">AADcPAAA3DwAgO08AADXPACA7TwAAOE8AAD6PACA3jwAgAQ9AAD6PACADj0AwAU9AEAXPQBAEj0A
ACU9AMAoPQDAQT0AgDY9AABSPQDAQT0AwF89AABcPQBAdj0AAH89AMCUPQCglj0AoLk9AEDEPQAg
3z0A4PY9AGALPgCwCz4AgBg+AIAiPgCwHz4AUCA+ACAoPgAQHz4AACU+AEAhPgBAKz4AYB8+ANAs
PgDgMD4AEDM+AOAwPgCwOD4AMDY+ACAyPgAALz4AcDw+AAAvPgDgNT4AIDI+ADA2PgCwKT4AUC8+
AJArPgCQIT4AoCA+AIAiPgDwFj4AkBI+AKARPgBAEj4AoAI+AJANPgAg/T0AIAA+AEDsPQAg7j0A
gOM9AMDfPQCA3j0A4N09AMDaPQDA3z0AIN89AMDaPQAAyD0A4Ng9AIDKPQCg1z0AAMg9AGDMPQBg
wj0A4L89AOC/PQCgwz0A4Lo9AMC8PQDAtz0AALk9AMCyPQAAuT0AgKw9AOCwPQAAqj0A4LA9AACg
PQCgqj0AoKU9AICnPQBAlz0AoKU9AOCSPQAglD0AAJE9ACCUPQDAjz0AIJQ9AOCNPQCgkT0AYIs9
AECIPQAggD0A4Ig9AMCAPQDggz0AQHY9AMCAPQCAdz0AwHM9AIBtPQAAej0AgGg9AEBsPQDAXz0A
QGw9AABcPQAAZj0AgFk9AEBiPQAAUj0AQFM9AABXPQDAWj0AwFA9AEBTPQCART0AAFI9AAA+PQBA
Uz0AwEE9AEBOPQCASj0AAEM9AEA1PQDAPD0AQDo9AEA6PQCANj0AQDo9AAA0PQAAND0AAC89AEAw
PQBAJj0AwC09AEAmPQCAJz0AQCE9AMAoPQAAID0AgCw9AMAePQCAJz0AQBc9AIAnPQCAEz0AQCE9
AIAYPQBAIT0AwA89AEAhPQCAEz0AQBw9AMAPPQDAGT0AgAk9AIAYPQBADT0AABY9AAAHPQAAET0A
gAk9AMAUPQAADD0AwA89AMAFPQDADz0AwAU9AEASPQDAAD0AQBI9AIAEPQCADj0AAAI9AEANPQAA
+jwAAAw9
          </values>
        </Ydata>
        <Ydata label="Y" units="UNKNOWN">
          <parameter name="DataTags" label="# of data points with tags" group="YAxis">0</parameter>
          <parameter name="Channel" label="Instrument channel stored" group="YAxis">10</parameter>
          <parameter name="MaxX" label="X-axis of the data set maximum" group="YAxis">349.000000</parameter>
          <parameter name="MaxY" label="Data set maximum" group="YAxis">0.293579</parameter>
          <parameter name="MinX" label="X-axis of the data set minimum" group="YAxis">303.000000</parameter>
          <parameter name="MinY" label="Data set minimum" group="YAxis">0.027466</parameter>
          <parameter name="YaxisType" label="Y-axis type" group="YAxis">0x12 = Intensity</parameter>
          <parameter name="YaxisUnit" label="Y-axis unit" group="YAxis">0x0 = Unknown</parameter>
          <parameter name="YAxisLL" label="Y-axis lower limit" group="YAxis">0.027466</parameter>
          <parameter name="YAxisUL" label="Y-axis upper limit" group="YAxis">0.293579</parameter>
          <parameter name="LabelY" label="Y-axis Label" group="YAxis">Y</parameter>
          <parameter name="AcqStarted" label="Date and Time the acquisition was started" group="Standard">0000-00-00 00:00:00</parameter>
          <parameter name="AcqCompleted" label="Date and Time the acquisition was completed" group="Standard">0000-00-00 00:00:00</parameter>
          <parameter name="ID" label="Acquiring instruments serial (ID) number" group="Standard">0</parameter>
          <parameter name="Instrument" label="Instrument type acquired on" group="Standard">7</parameter>
          <parameter name="Modified" label="Date and Time the acquisition was modified" group="Standard">0000-00-00 00:00:00</parameter>
          <parameter name="OSVersion" label="Operating system version" group="Standard">0.000000</parameter>
          <parameter name="Version" label="Program version (&apos;C&apos;) acquired on" group="Standard">0.000000</parameter>
          <parameter name="Comment" label="Acquisition comment" group="Standard">NULL</parameter>
          <parameter name="AcqTitle" label="Acquisition title" group="Standard">NULL</parameter>
          <parameter name="User" label="User who acquired this data" group="Standard">NULL</parameter>
          <parameter name="Monochromators" label="# of monochromators attached" group="Hardware">2</parameter>
          <parameter name="Pmts" label="# of PMTs attached" group="Hardware">2</parameter>
          <parameter name="Lamp" label="Excitation light source type" group="LAMP">1</parameter>
          <parameter name="LampComment" label="Excitation light source comment" group="LAMP">Default Lamp Comment</parameter>
          <parameter name="MonochromatorAt1" label="Monochromator constant/setup wavelength" group="Monochromator">300.000000</parameter>
          <parameter name="MonochromatorShutter1" label="Monochromator shutter status" group="Monochromator">0</parameter>
          <parameter name="MonochromatorSlitIn1" label="Monochromator input slit width" group="Monochromator">4.000000</parameter>
          <parameter name="MonochromatorSlitOut1" label="Monochromator output slit width" group="Monochromator">4.000000</parameter>
          <parameter name="MonochromatorStepSize1" label="Monochromator step size" group="Monochromator">0.200000</parameter>
          <parameter name="MonochromatorType1" label="Monochromator type (excitation or emission)" group="Monochromator">10</parameter>
          <parameter name="MonochromatorUpperLimit1" label="Monochromator upper limit" group="Monochromator">950.000000</parameter>
          <parameter name="MonochromatorComment1" label="Monochromator comment" group="Monochromator">Default Monochromator Comment</parameter>
          <parameter name="MonochromatorNumFilter1" label="Monochromator # of filters switched" group="Filter">2</parameter>
          <parameter name="MonochromatorFilter1Wavelength1" label="Monochromator constant/setup wavelength" group="Filter">200.000000</parameter>
          <parameter name="MonochromatorFilter1Wavelength2" label="Monochromator constant/setup wavelength" group="Filter">300.000000</parameter>
          <parameter name="MonochromatorFilter1Comment" label="Monochromator filter comment" group="Filter">Default Filter Comment</parameter>
          <parameter name="MonochromatorPolarizerAngle1" label="Monochromator polarization angle" group="Polarizer">0.000000</parameter>
          <parameter name="MonochromatorPolarizerType1" label="Monochromator polarizer type" group="Polarizer">1</parameter>
          <parameter name="MonochromatorPolarizerComment1" label="Monochromator polarizer comment" group="Polarizer">Default Polarizer Comment</parameter>
          <parameter name="MonochromatorAt2" label="Monochromator constant/setup wavelength" group="Monochromator">400.000000</parameter>
          <parameter name="MonochromatorShutter2" label="Monochromator shutter status" group="Monochromator">0</parameter>
          <parameter name="MonochromatorSlitIn2" label="Monochromator input slit width" group="Monochromator">4.000000</parameter>
          <parameter name="MonochromatorSlitOut2" label="Monochromator output slit width" group="Monochromator">4.000000</parameter>
          <parameter name="MonochromatorStepSize2" label="Monochromator step size" group="Monochromator">0.200000</parameter>
          <parameter name="MonochromatorType2" label="Monochromator type (excitation or emission)" group="Monochromator">8</parameter>
          <parameter name="MonochromatorUpperLimit2" label="Monochromator upper limit" group="Monochromator">950.000000</parameter>
          <parameter name="MonochromatorComment2" label="Monochromator comment" group="Monochromator">Default Monochromator Comment</parameter>
          <parameter name="MonochromatorNumFilter2" label="Monochromator # of filters switched" group="Filter">3</parameter>
          <parameter name="MonochromatorFilter2Wavelength1" label="Monochromator constant/setup wavelength" group="Filter">200.000000</parameter>
          <parameter name="MonochromatorFilter2Wavelength2" label="Monochromator constant/setup wavelength" group="Filter">300.000000</parameter>
          <parameter name="MonochromatorFilter2Wavelength3" label="Monochromator constant/setup wavelength" group="Filter">400.000000</parameter>
          <parameter name="MonochromatorFilter2Comment" label="Monochromator filter comment" group="Filter">Default Filter Comment</parameter>
          <parameter name="MonochromatorPolarizerAngle2" label="Monochromator polarization angle" group="Polarizer">90.000000</parameter>
          <parameter name="MonochromatorPolarizerType2" label="Monochromator polarizer type" group="Polarizer">1</parameter>
          <parameter name="MonochromatorPolarizerComment2" label="Monochromator polarizer comment" group="Polarizer">Default Polarizer Comment</parameter>
          <parameter name="PmtConnection1" label="PMT connection" group="PMT">10</parameter>
          <parameter name="PmtGain1" label="PMT gain" group="PMT">1</parameter>
          <parameter name="PmtHighVolt1" label="PMT high voltage" group="PMT">750</parameter>
          <parameter name="PmtOffset1" label="PMT offset status" group="PMT">0</parameter>
          <parameter name="PmtAcquisition1" label="PMT acquisition type" group="PMT">0</parameter>
          <parameter name="PmtComment1" label="PMT comment" group="PMT">Default Pmt Comment</parameter>
          <parameter name="PmtConnection2" label="PMT connection" group="PMT">10</parameter>
          <parameter name="PmtGain2" label="PMT gain" group="PMT">1</parameter>
          <parameter name="PmtHighVolt2" label="PMT high voltage" group="PMT">750</parameter>
          <parameter name="PmtOffset2" label="PMT offset status" group="PMT">0</parameter>
          <parameter name="PmtAcquisition2" label="PMT acquisition type" group="PMT">1</parameter>
          <parameter name="PmtComment2" label="PMT comment" group="PMT">Default Pmt Comment</parameter>
          <parameter name="InstrumentSpecific3" label="Instrument specific information" group="AUX">2</parameter>
          <parameter name="AuxChannelGain1" label="AUX channel gain" group="AUX">0</parameter>
          <parameter name="AuxChannelOffset1" label="AUX channel offset status" group="AUX">0</parameter>
          <parameter name="AuxChannelComment1" label="AUX channel comment" group="AUX">Default Aux Pmt Comment</parameter>
          <parameter name="AuxChannelGain2" label="AUX channel gain" group="AUX">1</parameter>
          <parameter name="AuxChannelOffset2" label="AUX channel offset status" group="AUX">0</parameter>
          <parameter name="AuxChannelComment2" label="AUX channel comment" group="AUX">Default Aux Pmt Comment</parameter>
          <parameter name="PlotInfo" label="Y-axis plotting annotation" group="YAxis">NULL</parameter>
          <parameter name="Corrected" label="Data corrected flag" group="MANIPULATION">0x0</parameter>
          <parameter name="Derivative" label="Degree of derivation" group="MANIPULATION">0</parameter>
          <parameter name="Norm" label="Normalized flag" group="MANIPULATION">0x0</parameter>
          <parameter name="Offset" label="Offset from the original data" group="MANIPULATION">0.000000</parameter>
          <parameter name="Scale" label="Scale from the original data" group="MANIPULATION">1.000000</parameter>
          <parameter name="Smooth" label="# of smoothing passes done" group="MANIPULATION">0</parameter>
          <parameter name="SmoothType" label="Smoothing type" group="MANIPULATION">1.000000</parameter>
          <values byteorder="INTEL" format="FLOAT32" numvalues="201">AADmPAAA5jwAAPU8AADhPAAA+jwAAOE8AAACPQCA9zwAAAw9AEAIPQDAFD0AQBI9AEAhPQCAMT0A
gDY9AIBAPQBAUz0AgFk9AABrPQDAbj0AgIQ9AOCSPQBAnD0AYKQ9AGC9PQBg0T0AIP09APAHPgCw
Hz4AEDM+AGBCPgDgTj4AUGY+AMBkPgBAcT4AAHU+AHB4PgAQdD4AQHY+AIB8PgBgeT4AwIA+APCC
PgCwhj4ASIo+ANiLPgBYkz4AeJE+ANiVPgBQlj4A2JU+AHCPPgAYkj4AmI8+AJCNPgBQhz4AyIw+
ACiHPgCYhT4ACIQ+ANB8PgCwdD4AsHQ+APBrPgDQXj4AoFc+AEBTPgBAST4AYEI+AAA+PgDwPj4A
sC4+AAAvPgBwLT4AACo+APAlPgAgLT4AsCQ+ALApPgAgIz4A0CI+AIAiPgAwHT4A4Bc+AHAZPgDA
Dz4AMBM+AFAMPgCQDT4AgA4+ANAJPgBgBj4AcAU+AAACPgCgAj4AwPg9AGABPgCA9z0AQPY9AGDq
PQAg8z0AwOQ9AIDjPQAA5j0AAOE9AODYPQCg3D0AQNM9ACDVPQCgyD0AgMo9AKDDPQDAvD0AQLo9
AKDDPQCAtj0AgLY9AICsPQBAuj0AgKc9AMCtPQDAqD0AIKg9AKCgPQCApz0AwKg9AICiPQDAnj0A
oKA9AGCVPQAglD0AII89ACCPPQCAiT0AAJE9AECIPQAAjD0A4IM9AECIPQCggj0AoII9ACCAPQBA
iD0AAHU9AGCBPQBAdj0AwH09AIBtPQCAbT0AwGQ9AAB1PQDAaT0AAHA9AABcPQDAZD0AgF49AEBY
PQBAWD0AAFw9AEBOPQBAXT0AgEU9AMBQPQBARD0AQFM9AIBFPQAAUj0AAEM9AEBEPQAAOT0AgEU9
AEA6PQAAQz0AQDA9AAA5PQBAMD0AwDw9AMAtPQDAMj0AwDI9AMAoPQBAJj0AwC09AIAiPQAAKj0A
gCI9AAAvPQDAGT0AwCM9AMAePQCAJz0AQBc9AAAgPQAAFj0AwCM9AEAcPQDAHj0AQBc9AAAgPQBA
Ej0AwBk9
          </values>
        </Ydata>
        <Ydata label="Y" units="UNKNOWN">
          <parameter name="DataTags" label="# of data points with tags" group="YAxis">0</parameter>
          <parameter name="Channel" label="Instrument channel stored" group="YAxis">10</parameter>
          <parameter name="MaxX" label="X-axis of the data set maximum" group="YAxis">351.000000</parameter>
          <parameter name="MaxY" label="Data set maximum" group="YAxis">0.492249</parameter>
          <parameter name="MinX" label="X-axis of the data set minimum" group="YAxis">303.000000</parameter>
          <parameter name="MinY" label="Data set minimum" group="YAxis">0.030518</parameter>
          <parameter name="YaxisType" label="Y-axis type" group="YAxis">0x12 = Intensity</parameter>
          <parameter name="YaxisUnit" label="Y-axis unit" group="YAxis">0x0 = Unknown</parameter>
          <parameter name="YAxisLL" label="Y-axis lower limit" group="YAxis">0.030518</parameter>
          <parameter name="YAxisUL" label="Y-axis upper limit" group="YAxis">0.492249</parameter>
          <parameter name="LabelY" label="Y-axis Label" group="YAxis">Y</parameter>
          <parameter name="AcqStarted" label="Date and Time the acquisition was started" group="Standard">0000-00-00 00:00:00</parameter>
          <parameter name="AcqCompleted" label="Date and Time the acquisition was completed" group="Standard">0000-00-00 00:00:00</parameter>
          <parameter name="ID" label="Acquiring instruments serial (ID) number" group="Standard">0</parameter>
          <parameter name="Instrument" label="Instrument type acquired on" group="Standard">7</parameter>
          <parameter name="Modified" label="Date and Time the acquisition was modified" group="Standard">0000-00-00 00:00:00</parameter>
          <parameter name="OSVersion" label="Operating system version" group="Standard">0.000000</parameter>
          <parameter name="Version" label="Program version (&apos;C&apos;) acquired on" group="Standard">0.000000</parameter>
          <parameter name="Comment" label="Acquisition comment" group="Standard">NULL</parameter>
          <parameter name="AcqTitle" label="Acquisition title" group="Standard">NULL</parameter>
          <parameter name="User" label="User who acquired this data" group="Standard">NULL</parameter>
          <parameter name="Monochromators" label="# of monochromators attached" group="Hardware">2</parameter>
          <parameter name="Pmts" label="# of PMTs attached" group="Hardware">2</parameter>
          <parameter name="Lamp" label="Excitation light source type" group="LAMP">1</parameter>
          <parameter name="LampComment" label="Excitation light source comment" group="LAMP">Default Lamp Comment</parameter>
          <parameter name="MonochromatorAt1" label="Monochromator constant/setup wavelength" group="Monochromator">300.000000</parameter>
          <parameter name="MonochromatorShutter1" label="Monochromator shutter status" group="Monochromator">0</parameter>
          <parameter name="MonochromatorSlitIn1" label="Monochromator input slit width" group="Monochromator">4.000000</parameter>
          <parameter name="MonochromatorSlitOut1" label="Monochromator output slit width" group="Monochromator">4.000000</parameter>
          <parameter name="MonochromatorStepSize1" label="Monochromator step size" group="Monochromator">0.200000</parameter>
          <parameter name="MonochromatorType1" label="Monochromator type (excitation or emission)" group="Monochromator">10</parameter>
          <parameter name="MonochromatorUpperLimit1" label="Monochromator upper limit" group="Monochromator">950.000000</parameter>
          <parameter name="MonochromatorComment1" label="Monochromator comment" group="Monochromator">Default Monochromator Comment</parameter>
          <parameter name="MonochromatorNumFilter1" label="Monochromator # of filters switched" group="Filter">2</parameter>
          <parameter name="MonochromatorFilter1Wavelength1" label="Monochromator constant/setup wavelength" group="Filter">200.000000</parameter>
          <parameter name="MonochromatorFilter1Wavelength2" label="Monochromator constant/setup wavelength" group="Filter">300.000000</parameter>
          <parameter name="MonochromatorFilter1Comment" label="Monochromator filter comment" group="Filter">Default Filter Comment</parameter>
          <parameter name="MonochromatorPolarizerAngle1" label="Monochromator polarization angle" group="Polarizer">0.000000</parameter>
          <parameter name="MonochromatorPolarizerType1" label="Monochromator polarizer type" group="Polarizer">1</parameter>
          <parameter name="MonochromatorPolarizerComment1" label="Monochromator polarizer comment" group="Polarizer">Default Polarizer Comment</parameter>
          <parameter name="MonochromatorAt2" label="Monochromator constant/setup wavelength" group="Monochromator">400.000000</parameter>
          <parameter name="MonochromatorShutter2" label="Monochromator shutter status" group="Monochromator">0</parameter>
          <parameter name="MonochromatorSlitIn2" label="Monochromator input slit width" group="Monochromator">4.000000</parameter>
          <parameter name="MonochromatorSlitOut2" label="Monochromator output slit width" group="Monochromator">4.000000</parameter>
          <parameter name="MonochromatorStepSize2" label="Monochromator step size" group="Monochromator">0.200000</parameter>
          <parameter name="MonochromatorType2" label="Monochromator type (excitation or emission)" group="Monochromator">8</parameter>
          <parameter name="MonochromatorUpperLimit2" label="Monochromator upper limit" group="Monochromator">950.000000</parameter>
          <parameter name="MonochromatorComment2" label="Monochromator comment" group="Monochromator">Default Monochromator Comment</parameter>
          <parameter name="MonochromatorNumFilter2" label="Monochromator # of filters switched" group="Filter">3</parameter>
          <parameter name="MonochromatorFilter2Wavelength1" label="Monochromator constant/setup wavelength" group="Filter">200.000000</parameter>
          <parameter name="MonochromatorFilter2Wavelength2" label="Monochromator constant/setup wavelength" group="Filter">300.000000</parameter>
          <parameter name="MonochromatorFilter2Wavelength3" label="Monochromator constant/setup wavelength" group="Filter">400.000000</parameter>
          <parameter name="MonochromatorFilter2Comment" label="Monochromator filter comment" group="Filter">Default Filter Comment</parameter>
          <parameter name="MonochromatorPolarizerAngle2" label="Monochromator polarization angle" group="Polarizer">90.000000</parameter>
          <parameter name="MonochromatorPolarizerType2" label="Monochromator polarizer type" group="Polarizer">1</parameter>
          <parameter name="MonochromatorPolarizerComment2" label="Monochromator polarizer comment" group="Polarizer">Default Polarizer Comment</parameter>
          <parameter name="PmtConnection1" label="PMT connection" group="PMT">10</parameter>
          <parameter name="PmtGain1" label="PMT gain" group="PMT">1</parameter>
          <parameter name="PmtHighVolt1" label="PMT high voltage" group="PMT">750</parameter>
          <parameter name="PmtOffset1" label="PMT offset status" group="PMT">0</parameter>
          <parameter name="PmtAcquisition1" label="PMT acquisition type" group="PMT">0</parameter>
          <parameter name="PmtComment1" label="PMT comment" group="PMT">Default Pmt Comment</parameter>
          <parameter name="PmtConnection2" label="PMT connection" group="PMT">10</parameter>
          <parameter name="PmtGain2" label="PMT gain" group="PMT">1</parameter>
          <parameter name="PmtHighVolt2" label="PMT high voltage" group="PMT">750</parameter>
          <parameter name="PmtOffset2" label="PMT offset status" group="PMT">0</parameter>
          <parameter name="PmtAcquisition2" label="PMT acquisition type" group="PMT">1</parameter>
          <parameter name="PmtComment2" label="PMT comment" group="PMT">Default Pmt Comment</parameter>
          <parameter name="InstrumentSpecific3" label="Instrument specific information" group="AUX">2</parameter>
          <parameter name="AuxChannelGain1" label="AUX channel gain" group="AUX">0</parameter>
          <parameter name="AuxChannelOffset1" label="AUX channel offset status" group="AUX">0</parameter>
          <parameter name="AuxChannelComment1" label="AUX channel comment" group="AUX">Default Aux Pmt Comment</parameter>
          <parameter name="AuxChannelGain2" label="AUX channel gain" group="AUX">1</parameter>
          <parameter name="AuxChannelOffset2" label="AUX channel offset status" group="AUX">0</parameter>
          <parameter name="AuxChannelComment2" label="AUX channel comment" group="AUX">Default Aux Pmt Comment</parameter>
          <parameter name="PlotInfo" label="Y-axis plotting annotation" group="YAxis">NULL</parameter>
          <parameter name="Corrected" label="Data corrected flag" group="MANIPULATION">0x0</parameter>
          <parameter name="Derivative" label="Degree of derivation" group="MANIPULATION">0</parameter>
          <parameter name="Norm" label="Normalized flag" group="MANIPULATION">0x0</parameter>
          <parameter name="Offset" label="Offset from the original data" group="MANIPULATION">0.000000</parameter>
          <parameter name="Scale" label="Scale from the original data" group="MANIPULATION">1.000000</parameter>
          <parameter name="Smooth" label="# of smoothing passes done" group="MANIPULATION">0</parameter>
          <parameter name="SmoothType" label="Smoothing type" group="MANIPULATION">1.000000</parameter>
          <values byteorder="INTEL" format="FLOAT32" numvalues="201">AAAHPQDABT0AgBM9AAD6PACACT0AwAA9AMAFPQCA/DwAABE9AMAPPQAAKj0AACo9AEBEPQCAQD0A
wGk9AIBtPQAgij0AYJA9AGCaPQDAnj0AwLI9AKC5PQCAzz0AoOE9AMAFPgBgED4AYCk+AKBIPgCg
cD4A+IQ+AEiUPgCgoD4AeK8+AAC5PgBAvz4AeMM+AOjBPgAAwz4AIMY+AGjJPgAIwD4AUMg+AKDS
PgCI1j4AaN0+ABDlPgDo8z4AKOs+AHj1PgCg+j4AwPM+AAj8PgDo8z4ASPg+ABj2PgCg8D4AuPE+
ADjlPgCQ4j4AkN0+AEDYPgAQ0T4A4M4+AAjKPgA4wj4A2Lg+ADC2PgCorD4AgKw+AKidPgDAnj4A
aJc+AEiZPgCQkj4AgI4+AHCPPgCwkD4ASIo+AAiJPgBQhz4A6IU+ANiBPgA4gT4AsH4+AKB/PgCw
dD4AUHA+ACBuPgCQYj4AQGc+AABmPgCAWT4AEFY+AABSPgBQUj4AkEQ+AFBIPgBARD4A4EQ+AOA6
PgDwOT4AwDc+AHAyPgBwLT4AwC0+ALApPgBAJj4AQCE+AKAgPgBgGj4AQBw+AFARPgCQEj4A4A0+
AKAMPgAQCz4AQAg+ANAEPgAQBj4AcAA+AGD+PQCg+j0AYPQ9AKDwPQAg+D0AwO49AODsPQBg4D0A
IO49AMDfPQBg2z0AgNk9ACDVPQBgzD0A4NM9AOC/PQDgyT0AAL49AKDDPQCguT0AwLw9AGCzPQBg
sz0AgKw9AKCvPQCAoj0AQKs9AICnPQDAoz0AYJ89AKCWPQCAnT0AoJs9ACCPPQCglj0AAJY9AMCP
PQCAiT0AII89ACCKPQCgjD0AQIM9AICJPQCggj0AoII9ACCAPQDAfT0AwHM9AEB7PQAAaz0AQHE9
AMBkPQBAbD0AAGE9AMBzPQDAVT0AQGc9AABNPQBAYj0AgE89AMBVPQAAUj0AgE89AIBFPQAAUj0A
wEY9AABSPQCAOz0AQE49AEA6PQCART0AgDs9AEA/PQCAMT0AQDU9AAA0PQBAOj0AQCs9AAAqPQCA
Ij0AgDY9
          </values>
        </Ydata>
        <Ydata label="Y" units="UNKNOWN">
          <parameter name="DataTags" label="# of data points with tags" group="YAxis">0</parameter>
          <parameter name="Channel" label="Instrument channel stored" group="YAxis">10</parameter>
          <parameter name="MaxX" label="X-axis of the data set maximum" group="YAxis">352.000000</parameter>
          <parameter name="MaxY" label="Data set maximum" group="YAxis">0.781250</parameter>
          <parameter name="MinX" label="X-axis of the data set minimum" group="YAxis">300.000000</parameter>
          <parameter name="MinY" label="Data set minimum" group="YAxis">0.028992</parameter>
          <parameter name="YaxisType" label="Y-axis type" group="YAxis">0x12 = Intensity</parameter>
          <parameter name="YaxisUnit" label="Y-axis unit" group="YAxis">0x0 = Unknown</parameter>
          <parameter name="YAxisLL" label="Y-axis lower limit" group="YAxis">0.028992</parameter>
          <parameter name="YAxisUL" label="Y-axis upper limit" group="YAxis">0.781250</parameter>
          <parameter name="LabelY" label="Y-axis Label" group="YAxis">Y</parameter>
          <parameter name="AcqStarted" label="Date and Time the acquisition was started" group="Standard">0000-00-00 00:00:00</parameter>
          <parameter name="AcqCompleted" label="Date and Time the acquisition was completed" group="Standard">0000-00-00 00:00:00</parameter>
          <parameter name="ID" label="Acquiring instruments serial (ID) number" group="Standard">0</parameter>
          <parameter name="Instrument" label="Instrument type acquired on" group="Standard">7</parameter>
          <parameter name="Modified" label="Date and Time the acquisition was modified" group="Standard">0000-00-00 00:00:00</parameter>
          <parameter name="OSVersion" label="Operating system version" group="Standard">0.000000</parameter>
          <parameter name="Version" label="Program version (&apos;C&apos;) acquired on" group="Standard">0.000000</parameter>
          <parameter name="Comment" label="Acquisition comment" group="Standard">NULL</parameter>
          <parameter name="AcqTitle" label="Acquisition title" group="Standard">NULL</parameter>
          <parameter name="User" label="User who acquired this data" group="Standard">NULL</parameter>
          <parameter name="Monochromators" label="# of monochromators attached" group="Hardware">2</parameter>
          <parameter name="Pmts" label="# of PMTs attached" group="Hardware">2</parameter>
          <parameter name="Lamp" label="Excitation light source type" group="LAMP">1</parameter>
          <parameter name="LampComment" label="Excitation light source comment" group="LAMP">Default Lamp Comment</parameter>
          <parameter name="MonochromatorAt1" label="Monochromator constant/setup wavelength" group="Monochromator">300.000000</parameter>
          <parameter name="MonochromatorShutter1" label="Monochromator shutter status" group="Monochromator">0</parameter>
          <parameter name="MonochromatorSlitIn1" label="Monochromator input slit width" group="Monochromator">4.000000</parameter>
          <parameter name="MonochromatorSlitOut1" label="Monochromator output slit width" group="Monochromator">4.000000</parameter>
          <parameter name="MonochromatorStepSize1" label="Monochromator step size" group="Monochromator">0.200000</parameter>
          <parameter name="MonochromatorType1" label="Monochromator type (excitation or emission)" group="Monochromator">10</parameter>
          <parameter name="MonochromatorUpperLimit1" label="Monochromator upper limit" group="Monochromator">950.000000</parameter>
          <parameter name="MonochromatorComment1" label="Monochromator comment" group="Monochromator">Default Monochromator Comment</parameter>
          <parameter name="MonochromatorNumFilter1" label="Monochromator # of filters switched" group="Filter">2</parameter>
          <parameter name="MonochromatorFilter1Wavelength1" label="Monochromator constant/setup wavelength" group="Filter">200.000000</parameter>
          <parameter name="MonochromatorFilter1Wavelength2" label="Monochromator constant/setup wavelength" group="Filter">300.000000</parameter>
          <parameter name="MonochromatorFilter1Comment" label="Monochromator filter comment" group="Filter">Default Filter Comment</parameter>
          <parameter name="MonochromatorPolarizerAngle1" label="Monochromator polarization angle" group="Polarizer">0.000000</parameter>
          <parameter name="MonochromatorPolarizerType1" label="Monochromator polarizer type" group="Polarizer">1</parameter>
          <parameter name="MonochromatorPolarizerComment1" label="Monochromator polarizer comment" group="Polarizer">Default Polarizer Comment</parameter>
          <parameter name="MonochromatorAt2" label="Monochromator constant/setup wavelength" group="Monochromator">400.000000</parameter>
          <parameter name="MonochromatorShutter2" label="Monochromator shutter status" group="Monochromator">0</parameter>
          <parameter name="MonochromatorSlitIn2" label="Monochromator input slit width" group="Monochromator">4.000000</parameter>
          <parameter name="MonochromatorSlitOut2" label="Monochromator output slit width" group="Monochromator">4.000000</parameter>
          <parameter name="MonochromatorStepSize2" label="Monochromator step size" group="Monochromator">0.200000</parameter>
          <parameter name="MonochromatorType2" label="Monochromator type (excitation or emission)" group="Monochromator">8</parameter>
          <parameter name="MonochromatorUpperLimit2" label="Monochromator upper limit" group="Monochromator">950.000000</parameter>
          <parameter name="MonochromatorComment2" label="Monochromator comment" group="Monochromator">Default Monochromator Comment</parameter>
          <parameter name="MonochromatorNumFilter2" label="Monochromator # of filters switched" group="Filter">3</parameter>
          <parameter name="MonochromatorFilter2Wavelength1" label="Monochromator constant/setup wavelength" group="Filter">200.000000</parameter>
          <parameter name="MonochromatorFilter2Wavelength2" label="Monochromator constant/setup wavelength" group="Filter">300.000000</parameter>
          <parameter name="MonochromatorFilter2Wavelength3" label="Monochromator constant/setup wavelength" group="Filter">400.000000</parameter>
          <parameter name="MonochromatorFilter2Comment" label="Monochromator filter comment" group="Filter">Default Filter Comment</parameter>
          <parameter name="MonochromatorPolarizerAngle2" label="Monochromator polarization angle" group="Polarizer">90.000000</parameter>
          <parameter name="MonochromatorPolarizerType2" label="Monochromator polarizer type" group="Polarizer">1</parameter>
          <parameter name="MonochromatorPolarizerComment2" label="Monochromator polarizer comment" group="Polarizer">Default Polarizer Comment</parameter>
          <parameter name="PmtConnection1" label="PMT connection" group="PMT">10</parameter>
          <parameter name="PmtGain1" label="PMT gain" group="PMT">1</parameter>
          <parameter name="PmtHighVolt1" label="PMT high voltage" group="PMT">750</parameter>
          <parameter name="PmtOffset1" label="PMT offset status" group="PMT">0</parameter>
          <parameter name="PmtAcquisition1" label="PMT acquisition type" group="PMT">0</parameter>
          <parameter name="PmtComment1" label="PMT comment" group="PMT">Default Pmt Comment</parameter>
          <parameter name="PmtConnection2" label="PMT connection" group="PMT">10</parameter>
          <parameter name="PmtGain2" label="PMT gain" group="PMT">1</parameter>
          <parameter name="PmtHighVolt2" label="PMT high voltage" group="PMT">750</parameter>
          <parameter name="PmtOffset2" label="PMT offset status" group="PMT">0</parameter>
          <parameter name="PmtAcquisition2" label="PMT acquisition type" group="PMT">1</parameter>
          <parameter name="PmtComment2" label="PMT comment" group="PMT">Default Pmt Comment</parameter>
          <parameter name="InstrumentSpecific3" label="Instrument specific information" group="AUX">2</parameter>
          <parameter name="AuxChannelGain1" label="AUX channel gain" group="AUX">0</parameter>
          <parameter name="AuxChannelOffset1" label="AUX channel offset status" group="AUX">0</parameter>
          <parameter name="AuxChannelComment1" label="AUX channel comment" group="AUX">Default Aux Pmt Comment</parameter>
          <parameter name="AuxChannelGain2" label="AUX channel gain" group="AUX">1</parameter>
          <parameter name="AuxChannelOffset2" label="AUX channel offset status" group="AUX">0</parameter>
          <parameter name="AuxChannelComment2" label="AUX channel comment" group="AUX">Default Aux Pmt Comment</parameter>
          <parameter name="PlotInfo" label="Y-axis plotting annotation" group="YAxis">NULL</parameter>
          <parameter name="Corrected" label="Data corrected flag" group="MANIPULATION">0x0</parameter>
          <parameter name="Derivative" label="Degree of derivation" group="MANIPULATION">0</parameter>
          <parameter name="Norm" label="Normalized flag" group="MANIPULATION">0x0</parameter>
          <parameter name="Offset" label="Offset from the original data" group="MANIPULATION">0.000000</parameter>
          <parameter name="Scale" label="Scale from the original data" group="MANIPULATION">1.000000</parameter>
          <parameter name="Smooth" label="# of smoothing passes done" group="MANIPULATION">0</parameter>
          <parameter name="SmoothType" label="Smoothing type" group="MANIPULATION">1.000000</parameter>
          <values byteorder="INTEL" format="FLOAT32" numvalues="201">AIDtPACA9zwAgBM9AMAUPQDAPD0AQD89AEBiPQBAWD0AAGE9AIBPPQBAZz0AAFI9AMB4PQBAiD0A
YJo9AICiPQBAuj0AwME9AIDePQCA3j0AgPc9AJAIPgBwFD4A8CA+AJA6PgCQUz4AwHg+ACiRPgBQ
pT4AgLs+ANjRPgAY7D4AYP4+ABAGPwCICz8AnAs/AGAQPwD8Dz8AWBM/AAwUPwDoFD8ATBo/AAgi
PwD4Ij8AWCw/AAw3PwCYNz8ASDw/APBDPwCEQT8A0EA/ADBFPwAASD8AIEE/ACRCPwAAQz8ASEE/
AMw6PwDwOT8AQDU/ANQtPwBsLD8AaCs/AAwjPwA4Hz8AdBo/ABwYPwBkDD8AAAw/ANQFPwCYAD8A
+Pc+AJj4PgBY8j4AwO4+AFDwPgDA7j4AoOs+AHjrPgDQ6D4AsOU+AEjfPgCA3j4AkNg+AOjQPgCY
yz4A0Mo+ADDAPgCgvj4A8Lk+AMi0PgAQrj4AKK8+AEioPgAQqT4AqKI+AKiiPgDAmT4AaJw+ANia
PgDIlj4A6I8+AFiOPgDoij4AOIY+AMiCPgD4hD4AQHY+AGB5PgAwcj4AMGg+ALBgPgAAYT4A4F0+
AIBePgAwVD4AAFI+ABBRPgDgST4AgEU+AABNPgDANz4AIDw+AKA5PgBANT4AQDU+AEAwPgBQKj4A
UCo+ALAkPgCwJD4A0B0+AHAePgBgGj4AUBs+ADAOPgCQFz4AIA8+AHAPPgCgBz4AoAc+AOADPgBQ
Aj4AsAE+AMD9PQCg9T0AoPU9AKD6PQDA8z0AIN89AADrPQBA3T0AgN49AADSPQBg1j0AYNY9AADS
PQCgzT0AQM49AGC9PQDgvz0AwLw9AEC/PQDArT0AALk9AMCtPQCgrz0A4Ks9AACvPQCArD0AQKY9
AKCWPQDAqD0AQJw9ACCePQAglD0AgJg9AOCNPQAAkT0AAIw9ACCKPQCAiT0AII89AACCPQCggj0A
YIE9AOCIPQAAdT0AQHs9AMB4PQCAfD0AwF89AAB1PQBAZz0AQHE9AABhPQDAZD0AQFg9AIBjPQAA
Uj0AgF49
          </values>
        </Ydata>
        <Ydata label="Y" units="UNKNOWN">
          <parameter name="DataTags" label="# of data points with tags" group="YAxis">0</parameter>
          <parameter name="Channel" label="Instrument channel stored" group="YAxis">10</parameter>
          <parameter name="MaxX" label="X-axis of the data set maximum" group="YAxis">352.000000</parameter>
          <parameter name="MaxY" label="Data set maximum" group="YAxis">1.267395</parameter>
          <parameter name="MinX" label="X-axis of the data set minimum" group="YAxis">300.000000</parameter>
          <parameter name="MinY" label="Data set minimum" group="YAxis">0.026245</parameter>
          <parameter name="YaxisType" label="Y-axis type" group="YAxis">0x12 = Intensity</parameter>
          <parameter name="YaxisUnit" label="Y-axis unit" group="YAxis">0x0 = Unknown</parameter>
          <parameter name="YAxisLL" label="Y-axis lower limit" group="YAxis">0.026245</parameter>
          <parameter name="YAxisUL" label="Y-axis upper limit" group="YAxis">1.267395</parameter>
          <parameter name="LabelY" label="Y-axis Label" group="YAxis">Y</parameter>
          <parameter name="AcqStarted" label="Date and Time the acquisition was started" group="Standard">0000-00-00 00:00:00</parameter>
          <parameter name="AcqCompleted" label="Date and Time the acquisition was completed" group="Standard">0000-00-00 00:00:00</parameter>
          <parameter name="ID" label="Acquiring instruments serial (ID) number" group="Standard">0</parameter>
          <parameter name="Instrument" label="Instrument type acquired on" group="Standard">7</parameter>
          <parameter name="Modified" label="Date and Time the acquisition was modified" group="Standard">0000-00-00 00:00:00</parameter>
          <parameter name="OSVersion" label="Operating system version" group="Standard">0.000000</parameter>
          <parameter name="Version" label="Program version (&apos;C&apos;) acquired on" group="Standard">0.000000</parameter>
          <parameter name="Comment" label="Acquisition comment" group="Standard">NULL</parameter>
          <parameter name="AcqTitle" label="Acquisition title" group="Standard">NULL</parameter>
          <parameter name="User" label="User who acquired this data" group="Standard">NULL</parameter>
          <parameter name="Monochromators" label="# of monochromators attached" group="Hardware">2</parameter>
          <parameter name="Pmts" label="# of PMTs attached" group="Hardware">2</parameter>
          <parameter name="Lamp" label="Excitation light source type" group="LAMP">1</parameter>
          <parameter name="LampComment" label="Excitation light source comment" group="LAMP">Default Lamp Comment</parameter>
          <parameter name="MonochromatorAt1" label="Monochromator constant/setup wavelength" group="Monochromator">300.000000</parameter>
          <parameter name="MonochromatorShutter1" label="Monochromator shutter status" group="Monochromator">0</parameter>
          <parameter name="MonochromatorSlitIn1" label="Monochromator input slit width" group="Monochromator">4.000000</parameter>
          <parameter name="MonochromatorSlitOut1" label="Monochromator output slit width" group="Monochromator">4.000000</parameter>
          <parameter name="MonochromatorStepSize1" label="Monochromator step size" group="Monochromator">0.200000</parameter>
          <parameter name="MonochromatorType1" label="Monochromator type (excitation or emission)" group="Monochromator">10</parameter>
          <parameter name="MonochromatorUpperLimit1" label="Monochromator upper limit" group="Monochromator">950.000000</parameter>
          <parameter name="MonochromatorComment1" label="Monochromator comment" group="Monochromator">Default Monochromator Comment</parameter>
          <parameter name="MonochromatorNumFilter1" label="Monochromator # of filters switched" group="Filter">2</parameter>
          <parameter name="MonochromatorFilter1Wavelength1" label="Monochromator constant/setup wavelength" group="Filter">200.000000</parameter>
          <parameter name="MonochromatorFilter1Wavelength2" label="Monochromator constant/setup wavelength" group="Filter">300.000000</parameter>
          <parameter name="MonochromatorFilter1Comment" label="Monochromator filter comment" group="Filter">Default Filter Comment</parameter>
          <parameter name="MonochromatorPolarizerAngle1" label="Monochromator polarization angle" group="Polarizer">0.000000</parameter>
          <parameter name="MonochromatorPolarizerType1" label="Monochromator polarizer type" group="Polarizer">1</parameter>
          <parameter name="MonochromatorPolarizerComment1" label="Monochromator polarizer comment" group="Polarizer">Default Polarizer Comment</parameter>
          <parameter name="MonochromatorAt2" label="Monochromator constant/setup wavelength" group="Monochromator">400.000000</parameter>
          <parameter name="MonochromatorShutter2" label="Monochromator shutter status" group="Monochromator">0</parameter>
          <parameter name="MonochromatorSlitIn2" label="Monochromator input slit width" group="Monochromator">4.000000</parameter>
          <parameter name="MonochromatorSlitOut2" label="Monochromator output slit width" group="Monochromator">4.000000</parameter>
          <parameter name="MonochromatorStepSize2" label="Monochromator step size" group="Monochromator">0.200000</parameter>
          <parameter name="MonochromatorType2" label="Monochromator type (excitation or emission)" group="Monochromator">8</parameter>
          <parameter name="MonochromatorUpperLimit2" label="Monochromator upper limit" group="Monochromator">950.000000</parameter>
          <parameter name="MonochromatorComment2" label="Monochromator comment" group="Monochromator">Default Monochromator Comment</parameter>
          <parameter name="MonochromatorNumFilter2" label="Monochromator # of filters switched" group="Filter">3</parameter>
          <parameter name="MonochromatorFilter2Wavelength1" label="Monochromator constant/setup wavelength" group="Filter">200.000000</parameter>
          <parameter name="MonochromatorFilter2Wavelength2" label="Monochromator constant/setup wavelength" group="Filter">300.000000</parameter>
          <parameter name="MonochromatorFilter2Wavelength3" label="Monochromator constant/setup wavelength" group="Filter">400.000000</parameter>
          <parameter name="MonochromatorFilter2Comment" label="Monochromator filter comment" group="Filter">Default Filter Comment</parameter>
          <parameter name="MonochromatorPolarizerAngle2" label="Monochromator polarization angle" group="Polarizer">90.000000</parameter>
          <parameter name="MonochromatorPolarizerType2" label="Monochromator polarizer type" group="Polarizer">1</parameter>
          <parameter name="MonochromatorPolarizerComment2" label="Monochromator polarizer comment" group="Polarizer">Default Polarizer Comment</parameter>
          <parameter name="PmtConnection1" label="PMT connection" group="PMT">10</parameter>
          <parameter name="PmtGain1" label="PMT gain" group="PMT">1</parameter>
          <parameter name="PmtHighVolt1" label="PMT high voltage" group="PMT">750</parameter>
          <parameter name="PmtOffset1" label="PMT offset status" group="PMT">0</parameter>
          <parameter name="PmtAcquisition1" label="PMT acquisition type" group="PMT">0</parameter>
          <parameter name="PmtComment1" label="PMT comment" group="PMT">Default Pmt Comment</parameter>
          <parameter name="PmtConnection2" label="PMT connection" group="PMT">10</parameter>
          <parameter name="PmtGain2" label="PMT gain" group="PMT">1</parameter>
          <parameter name="PmtHighVolt2" label="PMT high voltage" group="PMT">750</parameter>
          <parameter name="PmtOffset2" label="PMT offset status" group="PMT">0</parameter>
          <parameter name="PmtAcquisition2" label="PMT acquisition type" group="PMT">1</parameter>
          <parameter name="PmtComment2" label="PMT comment" group="PMT">Default Pmt Comment</parameter>
          <parameter name="InstrumentSpecific3" label="Instrument specific information" group="AUX">2</parameter>
          <parameter name="AuxChannelGain1" label="AUX channel gain" group="AUX">0</parameter>
          <parameter name="AuxChannelOffset1" label="AUX channel offset status" group="AUX">0</parameter>
          <parameter name="AuxChannelComment1" label="AUX channel comment" group="AUX">Default Aux Pmt Comment</parameter>
          <parameter name="AuxChannelGain2" label="AUX channel gain" group="AUX">1</parameter>
          <parameter name="AuxChannelOffset2" label="AUX channel offset status" group="AUX">0</parameter>
          <parameter name="AuxChannelComment2" label="AUX channel comment" group="AUX">Default Aux Pmt Comment</parameter>
          <parameter name="PlotInfo" label="Y-axis plotting annotation" group="YAxis">NULL</parameter>
          <parameter name="Corrected" label="Data corrected flag" group="MANIPULATION">0x0</parameter>
          <parameter name="Derivative" label="Degree of derivation" group="MANIPULATION">0</parameter>
          <parameter name="Norm" label="Normalized flag" group="MANIPULATION">0x0</parameter>
          <parameter name="Offset" label="Offset from the original data" group="MANIPULATION">0.000000</parameter>
          <parameter name="Scale" label="Scale from the original data" group="MANIPULATION">1.000000</parameter>
          <parameter name="Smooth" label="# of smoothing passes done" group="MANIPULATION">0</parameter>
          <parameter name="SmoothType" label="Smoothing type" group="MANIPULATION">1.000000</parameter>
          <values byteorder="INTEL" format="FLOAT32" numvalues="201">AADXPACA3jwAgO08AADcPAAAAj0AAAI9AMAoPQDAXz0AYJ89AADIPQAA/z0AoAw+AGAVPgCgDD4A
wAU+AKACPgCgBz4AYAY+AMAUPgDgHD4AkDA+AFA+PgAQUT4AEGo+ABCLPgAQnz4AgLE+AADXPgDo
+D4AZAw/ALQbPwBILT8AqDs/APBDPwD0Uz8AeFw/AEBiPwAAYT8AfGc/AGxtPwDEbz8AaHs/AOSE
PwCoiT8AFo8/AEaWPwCImj8AKJs/AGqfPwCSnz8AxJ8/AEChPwA6oj8AMqA/AHCePwD2nz8AYp0/
APqbPwAymz8AXJk/AOKVPwBgkD8AppA/AMKNPwAIiT8ALoY/ACSBPwCUcj8AZGY/APxfPwBMWz8A
vE8/ANhRPwD8Sz8AXEY/AERFPwBUST8AgEU/AEhGPwCoRT8AuEQ/AMg+PwBMPT8AHDs/AGAzPwCA
MT8AdC4/AAwoPwCoIj8AJB8/AFAbPwBIFD8AzA0/AOQOPwCACT8AvAQ/ALQCPwB4+j4A6Pg+ACj1
PgA47z4AKOs+AAjePgD43j4AMNk+ANDPPgCY0D4ASNA+AMjIPgDQuz4AgLs+AMi0PgCgrz4A6K0+
AJitPgDgoT4AQKE+ADCdPgCAnT4AoJY+AEiUPgAolj4AwI8+APCMPgBwij4AyIc+AAiEPgDQfD4A
UH8+AHB4PgCgcD4AwG4+ADByPgDgYj4A0GM+ANBZPgDQWT4A0FQ+AJBTPgAQTD4AcEs+ABBHPgBg
Qj4A0Ds+AABDPgCwOD4AEDM+AHAyPgBQLz4AUCo+AFAqPgBwIz4AICM+ALAfPgAwHT4AIBk+AMAU
PgAgGT4AgBM+AHAPPgCwED4AcA8+ANAJPgAgBT4AkAM+ADAEPgAA+j0AgPI9AED2PQDA6T0AIO49
AADhPQCA4z0AwN89AKDXPQDg3T0AANc9AEDOPQBg0T0AgMo9AIDKPQBAvz0AoM09AKC0PQAgtz0A
oK89AOC1PQCArD0AQLA9AOCrPQDgpj0AwJ49AECmPQBAnD0AwKM9AECXPQBgnz0AwJk9AMCUPQDA
jz0AwIo9
          </values>
        </Ydata>
        <Ydata label="Y" units="UNKNOWN">
          <parameter name="DataTags" label="# of data points with tags" group="YAxis">0</parameter>
          <parameter name="Channel" label="Instrument channel stored" group="YAxis">10</parameter>
          <parameter name="MaxX" label="X-axis of the data set maximum" group="YAxis">349.000000</parameter>
          <parameter name="MaxY" label="Data set maximum" group="YAxis">1.936035</parameter>
          <parameter name="MinX" label="X-axis of the data set minimum" group="YAxis">303.000000</parameter>
          <parameter name="MinY" label="Data set minimum" group="YAxis">0.027466</parameter>
          <parameter name="YaxisType" label="Y-axis type" group="YAxis">0x12 = Intensity</parameter>
          <parameter name="YaxisUnit" label="Y-axis unit" group="YAxis">0x0 = Unknown</parameter>
          <parameter name="YAxisLL" label="Y-axis lower limit" group="YAxis">0.027466</parameter>
          <parameter name="YAxisUL" label="Y-axis upper limit" group="YAxis">1.936035</parameter>
          <parameter name="LabelY" label="Y-axis Label" group="YAxis">Y</parameter>
          <parameter name="AcqStarted" label="Date and Time the acquisition was started" group="Standard">0000-00-00 00:00:00</parameter>
          <parameter name="AcqCompleted" label="Date and Time the acquisition was completed" group="Standard">0000-00-00 00:00:00</parameter>
          <parameter name="ID" label="Acquiring instruments serial (ID) number" group="Standard">0</parameter>
          <parameter name="Instrument" label="Instrument type acquired on" group="Standard">7</parameter>
          <parameter name="Modified" label="Date and Time the acquisition was modified" group="Standard">0000-00-00 00:00:00</parameter>
          <parameter name="OSVersion" label="Operating system version" group="Standard">0.000000</parameter>
          <parameter name="Version" label="Program version (&apos;C&apos;) acquired on" group="Standard">0.000000</parameter>
          <parameter name="Comment" label="Acquisition comment" group="Standard">NULL</parameter>
          <parameter name="AcqTitle" label="Acquisition title" group="Standard">NULL</parameter>
          <parameter name="User" label="User who acquired this data" group="Standard">NULL</parameter>
          <parameter name="Monochromators" label="# of monochromators attached" group="Hardware">2</parameter>
          <parameter name="Pmts" label="# of PMTs attached" group="Hardware">2</parameter>
          <parameter name="Lamp" label="Excitation light source type" group="LAMP">1</parameter>
          <parameter name="LampComment" label="Excitation light source comment" group="LAMP">Default Lamp Comment</parameter>
          <parameter name="MonochromatorAt1" label="Monochromator constant/setup wavelength" group="Monochromator">300.000000</parameter>
          <parameter name="MonochromatorShutter1" label="Monochromator shutter status" group="Monochromator">0</parameter>
          <parameter name="MonochromatorSlitIn1" label="Monochromator input slit width" group="Monochromator">4.000000</parameter>
          <parameter name="MonochromatorSlitOut1" label="Monochromator output slit width" group="Monochromator">4.000000</parameter>
          <parameter name="MonochromatorStepSize1" label="Monochromator step size" group="Monochromator">0.200000</parameter>
          <parameter name="MonochromatorType1" label="Monochromator type (excitation or emission)" group="Monochromator">10</parameter>
          <parameter name="MonochromatorUpperLimit1" label="Monochromator upper limit" group="Monochromator">950.000000</parameter>
          <parameter name="MonochromatorComment1" label="Monochromator comment" group="Monochromator">Default Monochromator Comment</parameter>
          <parameter name="MonochromatorNumFilter1" label="Monochromator # of filters switched" group="Filter">2</parameter>
          <parameter name="MonochromatorFilter1Wavelength1" label="Monochromator constant/setup wavelength" group="Filter">200.000000</parameter>
          <parameter name="MonochromatorFilter1Wavelength2" label="Monochromator constant/setup wavelength" group="Filter">300.000000</parameter>
          <parameter name="MonochromatorFilter1Comment" label="Monochromator filter comment" group="Filter">Default Filter Comment</parameter>
          <parameter name="MonochromatorPolarizerAngle1" label="Monochromator polarization angle" group="Polarizer">0.000000</parameter>
          <parameter name="MonochromatorPolarizerType1" label="Monochromator polarizer type" group="Polarizer">1</parameter>
          <parameter name="MonochromatorPolarizerComment1" label="Monochromator polarizer comment" group="Polarizer">Default Polarizer Comment</parameter>
          <parameter name="MonochromatorAt2" label="Monochromator constant/setup wavelength" group="Monochromator">400.000000</parameter>
          <parameter name="MonochromatorShutter2" label="Monochromator shutter status" group="Monochromator">0</parameter>
          <parameter name="MonochromatorSlitIn2" label="Monochromator input slit width" group="Monochromator">4.000000</parameter>
          <parameter name="MonochromatorSlitOut2" label="Monochromator output slit width" group="Monochromator">4.000000</parameter>
          <parameter name="MonochromatorStepSize2" label="Monochromator step size" group="Monochromator">0.200000</parameter>
          <parameter name="MonochromatorType2" label="Monochromator type (excitation or emission)" group="Monochromator">8</parameter>
          <parameter name="MonochromatorUpperLimit2" label="Monochromator upper limit" group="Monochromator">950.000000</parameter>
          <parameter name="MonochromatorComment2" label="Monochromator comment" group="Monochromator">Default Monochromator Comment</parameter>
          <parameter name="MonochromatorNumFilter2" label="Monochromator # of filters switched" group="Filter">3</parameter>
          <parameter name="MonochromatorFilter2Wavelength1" label="Monochromator constant/setup wavelength" group="Filter">200.000000</parameter>
          <parameter name="MonochromatorFilter2Wavelength2" label="Monochromator constant/setup wavelength" group="Filter">300.000000</parameter>
          <parameter name="MonochromatorFilter2Wavelength3" label="Monochromator constant/setup wavelength" group="Filter">400.000000</parameter>
          <parameter name="MonochromatorFilter2Comment" label="Monochromator filter comment" group="Filter">Default Filter Comment</parameter>
          <parameter name="MonochromatorPolarizerAngle2" label="Monochromator polarization angle" group="Polarizer">90.000000</parameter>
          <parameter name="MonochromatorPolarizerType2" label="Monochromator polarizer type" group="Polarizer">1</parameter>
          <parameter name="MonochromatorPolarizerComment2" label="Monochromator polarizer comment" group="Polarizer">Default Polarizer Comment</parameter>
          <parameter name="PmtConnection1" label="PMT connection" group="PMT">10</parameter>
          <parameter name="PmtGain1" label="PMT gain" group="PMT">1</parameter>
          <parameter name="PmtHighVolt1" label="PMT high voltage" group="PMT">750</parameter>
          <parameter name="PmtOffset1" label="PMT offset status" group="PMT">0</parameter>
          <parameter name="PmtAcquisition1" label="PMT acquisition type" group="PMT">0</parameter>
          <parameter name="PmtComment1" label="PMT comment" group="PMT">Default Pmt Comment</parameter>
          <parameter name="PmtConnection2" label="PMT connection" group="PMT">10</parameter>
          <parameter name="PmtGain2" label="PMT gain" group="PMT">1</parameter>
          <parameter name="PmtHighVolt2" label="PMT high voltage" group="PMT">750</parameter>
          <parameter name="PmtOffset2" label="PMT offset status" group="PMT">0</parameter>
          <parameter name="PmtAcquisition2" label="PMT acquisition type" group="PMT">1</parameter>
          <parameter name="PmtComment2" label="PMT comment" group="PMT">Default Pmt Comment</parameter>
          <parameter name="InstrumentSpecific3" label="Instrument specific information" group="AUX">2</parameter>
          <parameter name="AuxChannelGain1" label="AUX channel gain" group="AUX">0</parameter>
          <parameter name="AuxChannelOffset1" label="AUX channel offset status" group="AUX">0</parameter>
          <parameter name="AuxChannelComment1" label="AUX channel comment" group="AUX">Default Aux Pmt Comment</parameter>
          <parameter name="AuxChannelGain2" label="AUX channel gain" group="AUX">1</parameter>
          <parameter name="AuxChannelOffset2" label="AUX channel offset status" group="AUX">0</parameter>
          <parameter name="AuxChannelComment2" label="AUX channel comment" group="AUX">Default Aux Pmt Comment</parameter>
          <parameter name="PlotInfo" label="Y-axis plotting annotation" group="YAxis">NULL</parameter>
          <parameter name="Corrected" label="Data corrected flag" group="MANIPULATION">0x0</parameter>
          <parameter name="Derivative" label="Degree of derivation" group="MANIPULATION">0</parameter>
          <parameter name="Norm" label="Normalized flag" group="MANIPULATION">0x0</parameter>
          <parameter name="Offset" label="Offset from the original data" group="MANIPULATION">0.000000</parameter>
          <parameter name="Scale" label="Scale from the original data" group="MANIPULATION">1.000000</parameter>
          <parameter name="Smooth" label="# of smoothing passes done" group="MANIPULATION">0</parameter>
          <parameter name="SmoothType" label="Smoothing type" group="MANIPULATION">1.000000</parameter>
          <values byteorder="INTEL" format="FLOAT32" numvalues="201">AADmPAAA5jwAAPU8AADhPACA9zwAAOY8AMAAPQCA8jwAQA09AIATPQDASz0AIJQ9AEDsPQCQNT4A
oHo+AMibPgD4tj4AoLk+AGi1PgAolj4ASIA+AHBkPgDwZj4AoH8+AOiUPgDQsT4A2NE+APD6PgAQ
Gj8A4DU/AFROPwDIaz8AjII/AHqKPwCCkT8Atpk/AA6cPwBkoD8A2KQ/AJKpPwDYrj8ATrY/ANDA
PwAcxT8Ass8/AE7ePwBI5D8AeOs/AETyPwDQ9z8AlvU/ACL2PwAm9z8ARPI/AGjxPwDQ7T8ALOw/
AOjpPwC05j8AeOY/ADzhPwCi2j8ANNo/AKzVPwAGzD8ASsk/AFrDPwAsuj8ALLU/AIKvPwD0pj8A
LKE/AC6fPwDEmj8AQJc/AMqUPwDWlz8ApJI/ALSRPwCilD8A6pI/ABiNPwBIjz8AuI0/AJCIPwAs
iD8AbIQ/APyAPwA4eT8A0HI/AJxqPwDAXz8AHFk/AERUPwBgRz8A0EA/AGg/PwAAOT8ANDI/AEAw
PwDELj8AoCU/AFQhPwBEIj8ANBk/ABAVPwCcFT8AIA8/ABQMPwAYCD8AVAg/ADj+PgAQ/j4ASPM+
AGDvPgDw5j4AMOM+AEDdPgCQ0z4A4NM+AADNPgDoxj4AwME+AFC+PgAYuj4AsLg+ABi1PgCQqz4A
UKo+AKinPgDooz4AQJw+AHCePgBYmD4AIJk+AMCPPgDAjz4A8Iw+AGCLPgCghz4AiIY+ALCBPgCw
gT4AsHk+AFB1PgDAcz4A4HE+AEBxPgAwbT4AwF8+ANBePgAgWj4AAFc+AABNPgCgUj4AYEc+AOBE
PgDQQD4AoEM+ABA4PgCgOT4AoDQ+AOAwPgDALT4AkDA+ANAiPgBgJD4AMCI+ALAfPgBAHD4A4Bc+
ACAZPgCwFT4AsBA+AHAPPgCgDD4AUAw+AAAHPgBACD4AwAU+AKACPgAgAD4AsAE+AKDwPQAA9T0A
gOg9AMDkPQBA4j0AAOY9ACDkPQAA4T0AANI9AMDfPQBg0T0AwNA9AKDNPQBAyT0AIME9AEDEPQAg
sj0AoLQ9
          </values>
        </Ydata>
        <Ydata label="Y" units="UNKNOWN">
          <parameter name="DataTags" label="# of data points with tags" group="YAxis">0</parameter>
          <parameter name="Channel" label="Instrument channel stored" group="YAxis">10</parameter>
          <parameter name="MaxX" label="X-axis of the data set maximum" group="YAxis">355.000000</parameter>
          <parameter name="MaxY" label="Data set maximum" group="YAxis">2.543640</parameter>
          <parameter name="MinX" label="X-axis of the data set minimum" group="YAxis">305.000000</parameter>
          <parameter name="MinY" label="Data set minimum" group="YAxis">0.026550</parameter>
          <parameter name="YaxisType" label="Y-axis type" group="YAxis">0x12 = Intensity</parameter>
          <parameter name="YaxisUnit" label="Y-axis unit" group="YAxis">0x0 = Unknown</parameter>
          <parameter name="YAxisLL" label="Y-axis lower limit" group="YAxis">0.026550</parameter>
          <parameter name="YAxisUL" label="Y-axis upper limit" group="YAxis">2.543640</parameter>
          <parameter name="LabelY" label="Y-axis Label" group="YAxis">Y</parameter>
          <parameter name="AcqStarted" label="Date and Time the acquisition was started" group="Standard">0000-00-00 00:00:00</parameter>
          <parameter name="AcqCompleted" label="Date and Time the acquisition was completed" group="Standard">0000-00-00 00:00:00</parameter>
          <parameter name="ID" label="Acquiring instruments serial (ID) number" group="Standard">0</parameter>
          <parameter name="Instrument" label="Instrument type acquired on" group="Standard">7</parameter>
          <parameter name="Modified" label="Date and Time the acquisition was modified" group="Standard">0000-00-00 00:00:00</parameter>
          <parameter name="OSVersion" label="Operating system version" group="Standard">0.000000</parameter>
          <parameter name="Version" label="Program version (&apos;C&apos;) acquired on" group="Standard">0.000000</parameter>
          <parameter name="Comment" label="Acquisition comment" group="Standard">NULL</parameter>
          <parameter name="AcqTitle" label="Acquisition title" group="Standard">NULL</parameter>
          <parameter name="User" label="User who acquired this data" group="Standard">NULL</parameter>
          <parameter name="Monochromators" label="# of monochromators attached" group="Hardware">2</parameter>
          <parameter name="Pmts" label="# of PMTs attached" group="Hardware">2</parameter>
          <parameter name="Lamp" label="Excitation light source type" group="LAMP">1</parameter>
          <parameter name="LampComment" label="Excitation light source comment" group="LAMP">Default Lamp Comment</parameter>
          <parameter name="MonochromatorAt1" label="Monochromator constant/setup wavelength" group="Monochromator">300.000000</parameter>
          <parameter name="MonochromatorShutter1" label="Monochromator shutter status" group="Monochromator">0</parameter>
          <parameter name="MonochromatorSlitIn1" label="Monochromator input slit width" group="Monochromator">4.000000</parameter>
          <parameter name="MonochromatorSlitOut1" label="Monochromator output slit width" group="Monochromator">4.000000</parameter>
          <parameter name="MonochromatorStepSize1" label="Monochromator step size" group="Monochromator">0.200000</parameter>
          <parameter name="MonochromatorType1" label="Monochromator type (excitation or emission)" group="Monochromator">10</parameter>
          <parameter name="MonochromatorUpperLimit1" label="Monochromator upper limit" group="Monochromator">950.000000</parameter>
          <parameter name="MonochromatorComment1" label="Monochromator comment" group="Monochromator">Default Monochromator Comment</parameter>
          <parameter name="MonochromatorNumFilter1" label="Monochromator # of filters switched" group="Filter">2</parameter>
          <parameter name="MonochromatorFilter1Wavelength1" label="Monochromator constant/setup wavelength" group="Filter">200.000000</parameter>
          <parameter name="MonochromatorFilter1Wavelength2" label="Monochromator constant/setup wavelength" group="Filter">300.000000</parameter>
          <parameter name="MonochromatorFilter1Comment" label="Monochromator filter comment" group="Filter">Default Filter Comment</parameter>
          <parameter name="MonochromatorPolarizerAngle1" label="Monochromator polarization angle" group="Polarizer">0.000000</parameter>
          <parameter name="MonochromatorPolarizerType1" label="Monochromator polarizer type" group="Polarizer">1</parameter>
          <parameter name="MonochromatorPolarizerComment1" label="Monochromator polarizer comment" group="Polarizer">Default Polarizer Comment</parameter>
          <parameter name="MonochromatorAt2" label="Monochromator constant/setup wavelength" group="Monochromator">400.000000</parameter>
          <parameter name="MonochromatorShutter2" label="Monochromator shutter status" group="Monochromator">0</parameter>
          <parameter name="MonochromatorSlitIn2" label="Monochromator input slit width" group="Monochromator">4.000000</parameter>
          <parameter name="MonochromatorSlitOut2" label="Monochromator output slit width" group="Monochromator">4.000000</parameter>
          <parameter name="MonochromatorStepSize2" label="Monochromator step size" group="Monochromator">0.200000</parameter>
          <parameter name="MonochromatorType2" label="Monochromator type (excitation or emission)" group="Monochromator">8</parameter>
          <parameter name="MonochromatorUpperLimit2" label="Monochromator upper limit" group="Monochromator">950.000000</parameter>
          <parameter name="MonochromatorComment2" label="Monochromator comment" group="Monochromator">Default Monochromator Comment</parameter>
          <parameter name="MonochromatorNumFilter2" label="Monochromator # of filters switched" group="Filter">3</parameter>
          <parameter name="MonochromatorFilter2Wavelength1" label="Monochromator constant/setup wavelength" group="Filter">200.000000</parameter>
          <parameter name="MonochromatorFilter2Wavelength2" label="Monochromator constant/setup wavelength" group="Filter">300.000000</parameter>
          <parameter name="MonochromatorFilter2Wavelength3" label="Monochromator constant/setup wavelength" group="Filter">400.000000</parameter>
          <parameter name="MonochromatorFilter2Comment" label="Monochromator filter comment" group="Filter">Default Filter Comment</parameter>
          <parameter name="MonochromatorPolarizerAngle2" label="Monochromator polarization angle" group="Polarizer">90.000000</parameter>
          <parameter name="MonochromatorPolarizerType2" label="Monochromator polarizer type" group="Polarizer">1</parameter>
          <parameter name="MonochromatorPolarizerComment2" label="Monochromator polarizer comment" group="Polarizer">Default Polarizer Comment</parameter>
          <parameter name="PmtConnection1" label="PMT connection" group="PMT">10</parameter>
          <parameter name="PmtGain1" label="PMT gain" group="PMT">1</parameter>
          <parameter name="PmtHighVolt1" label="PMT high voltage" group="PMT">750</parameter>
          <parameter name="PmtOffset1" label="PMT offset status" group="PMT">0</parameter>
          <parameter name="PmtAcquisition1" label="PMT acquisition type" group="PMT">0</parameter>
          <parameter name="PmtComment1" label="PMT comment" group="PMT">Default Pmt Comment</parameter>
          <parameter name="PmtConnection2" label="PMT connection" group="PMT">10</parameter>
          <parameter name="PmtGain2" label="PMT gain" group="PMT">1</parameter>
          <parameter name="PmtHighVolt2" label="PMT high voltage" group="PMT">750</parameter>
          <parameter name="PmtOffset2" label="PMT offset status" group="PMT">0</parameter>
          <parameter name="PmtAcquisition2" label="PMT acquisition type" group="PMT">1</parameter>
          <parameter name="PmtComment2" label="PMT comment" group="PMT">Default Pmt Comment</parameter>
          <parameter name="InstrumentSpecific3" label="Instrument specific information" group="AUX">2</parameter>
          <parameter name="AuxChannelGain1" label="AUX channel gain" group="AUX">0</parameter>
          <parameter name="AuxChannelOffset1" label="AUX channel offset status" group="AUX">0</parameter>
          <parameter name="AuxChannelComment1" label="AUX channel comment" group="AUX">Default Aux Pmt Comment</parameter>
          <parameter name="AuxChannelGain2" label="AUX channel gain" group="AUX">1</parameter>
          <parameter name="AuxChannelOffset2" label="AUX channel offset status" group="AUX">0</parameter>
          <parameter name="AuxChannelComment2" label="AUX channel comment" group="AUX">Default Aux Pmt Comment</parameter>
          <parameter name="PlotInfo" label="Y-axis plotting annotation" group="YAxis">NULL</parameter>
          <parameter name="Corrected" label="Data corrected flag" group="MANIPULATION">0x0</parameter>
          <parameter name="Derivative" label="Degree of derivation" group="MANIPULATION">0</parameter>
          <parameter name="Norm" label="Normalized flag" group="MANIPULATION">0x0</parameter>
          <parameter name="Offset" label="Offset from the original data" group="MANIPULATION">0.000000</parameter>
          <parameter name="Scale" label="Scale from the original data" group="MANIPULATION">1.000000</parameter>
          <parameter name="Smooth" label="# of smoothing passes done" group="MANIPULATION">0</parameter>
          <parameter name="SmoothType" label="Smoothing type" group="MANIPULATION">1.000000</parameter>
          <values byteorder="INTEL" format="FLOAT32" numvalues="201">AIDePACA4zwAgPI8AADcPAAA+jwAgNk8AID8PAAA4TwAAP88AIDyPABAAz0AgPw8AAARPQDAFD0A
wEY9AGCLPQBA9j0AYFE+AAiiPgAo5j4A0A4/ACQaPwBcHj8AQA0/AFD6PgD41D4AONE+ALDvPgDs
Cz8ARCw/ADRQPwBobD8ATIY/AHiWPwCooj8A/K0/ALa3PwDEwj8AcsQ/AM7MPwDs2z8ANOQ/APTs
PwBG+j8ARARAAK4IQACzDUAAgRdAALoaQABpG0AANyBAACchQADIIEAAaSBAAMYiQADLIkAAFx1A
AJ4dQABaG0AAwxZAAIgVQADGE0AA3RBAAA8MQABkDEAAHQhAAH4BQACS/j8Asvc/AOLqPwBS5D8A
7t4/ADDePwAI1D8AStM/AC7RPwAqyz8A9Mk/AHrLPwCcxz8AEsU/AOjGPwCoxT8AEsA/AIi9PwCs
vD8AdrY/ACywPwCcrj8ATKk/AOagPwC0mz8Aipg/AJKQPwAEjT8Ahog/AJqDPwCsfT8AVHs/AERt
PwD0Zz8ASF8/AJBdPwAIVD8AAE0/AMhIPwCQRD8AED0/AGQ5PwDEMz8AfCs/AGApPwDcJT8AfBw/
ANwWPwA4FT8AaBI/ALQMPwDIBz8ABAg/AFACPwAA+j4A+Pc+ACjwPgAY4j4ASN8+AKjePgAo0j4A
QNM+ANjMPgB4yD4A6Lw+AOjBPgBwvD4AgLY+ALCzPgDwrz4AqKw+AHiqPgAIpz4AQKY+AFidPgAg
nj4AEJo+AKiYPgAglD4AmJQ+ADCOPgDgjT4AIIU+ANiGPgB4gj4A2IE+AGCBPgAwfD4AgG0+AIBt
PgAAZj4AQGc+AOBYPgCwYD4AEFY+AEBOPgAAUj4AoFI+APBDPgAwSj4A4D8+AFA+PgCQNT4AsDg+
ABAuPgBwLT4AEC4+AJArPgAQJD4AMCI+AGAfPgDQHT4AIBk+AJAXPgAQFT4AcBQ+ADAJPgAADD4A
oAc+AAAMPgAwBD4AEAY+ACAAPgAwBD4AIP09AKD6PQAA8D0AwPM9AKDwPQBg6j0AQOI9AKDXPQAA
zT0A4Ng9
          </values>
        </Ydata>
        <Ydata label="Y" units="UNKNOWN">
          <parameter name="DataTags" label="# of data points with tags" group="YAxis">0</parameter>
          <parameter name="Channel" label="Instrument channel stored" group="YAxis">10</parameter>
          <parameter name="MaxX" label="X-axis of the data set maximum" group="YAxis">357.000000</parameter>
          <parameter name="MaxY" label="Data set maximum" group="YAxis">2.991943</parameter>
          <parameter name="MinX" label="X-axis of the data set minimum" group="YAxis">303.000000</parameter>
          <parameter name="MinY" label="Data set minimum" group="YAxis">0.027161</parameter>
          <parameter name="YaxisType" label="Y-axis type" group="YAxis">0x12 = Intensity</parameter>
          <parameter name="YaxisUnit" label="Y-axis unit" group="YAxis">0x0 = Unknown</parameter>
          <parameter name="YAxisLL" label="Y-axis lower limit" group="YAxis">0.027161</parameter>
          <parameter name="YAxisUL" label="Y-axis upper limit" group="YAxis">2.991943</parameter>
          <parameter name="LabelY" label="Y-axis Label" group="YAxis">Y</parameter>
          <parameter name="AcqStarted" label="Date and Time the acquisition was started" group="Standard">0000-00-00 00:00:00</parameter>
          <parameter name="AcqCompleted" label="Date and Time the acquisition was completed" group="Standard">0000-00-00 00:00:00</parameter>
          <parameter name="ID" label="Acquiring instruments serial (ID) number" group="Standard">0</parameter>
          <parameter name="Instrument" label="Instrument type acquired on" group="Standard">7</parameter>
          <parameter name="Modified" label="Date and Time the acquisition was modified" group="Standard">0000-00-00 00:00:00</parameter>
          <parameter name="OSVersion" label="Operating system version" group="Standard">0.000000</parameter>
          <parameter name="Version" label="Program version (&apos;C&apos;) acquired on" group="Standard">0.000000</parameter>
          <parameter name="Comment" label="Acquisition comment" group="Standard">NULL</parameter>
          <parameter name="AcqTitle" label="Acquisition title" group="Standard">NULL</parameter>
          <parameter name="User" label="User who acquired this data" group="Standard">NULL</parameter>
          <parameter name="Monochromators" label="# of monochromators attached" group="Hardware">2</parameter>
          <parameter name="Pmts" label="# of PMTs attached" group="Hardware">2</parameter>
          <parameter name="Lamp" label="Excitation light source type" group="LAMP">1</parameter>
          <parameter name="LampComment" label="Excitation light source comment" group="LAMP">Default Lamp Comment</parameter>
          <parameter name="MonochromatorAt1" label="Monochromator constant/setup wavelength" group="Monochromator">300.000000</parameter>
          <parameter name="MonochromatorShutter1" label="Monochromator shutter status" group="Monochromator">0</parameter>
          <parameter name="MonochromatorSlitIn1" label="Monochromator input slit width" group="Monochromator">4.000000</parameter>
          <parameter name="MonochromatorSlitOut1" label="Monochromator output slit width" group="Monochromator">4.000000</parameter>
          <parameter name="MonochromatorStepSize1" label="Monochromator step size" group="Monochromator">0.200000</parameter>
          <parameter name="MonochromatorType1" label="Monochromator type (excitation or emission)" group="Monochromator">10</parameter>
          <parameter name="MonochromatorUpperLimit1" label="Monochromator upper limit" group="Monochromator">950.000000</parameter>
          <parameter name="MonochromatorComment1" label="Monochromator comment" group="Monochromator">Default Monochromator Comment</parameter>
          <parameter name="MonochromatorNumFilter1" label="Monochromator # of filters switched" group="Filter">2</parameter>
          <parameter name="MonochromatorFilter1Wavelength1" label="Monochromator constant/setup wavelength" group="Filter">200.000000</parameter>
          <parameter name="MonochromatorFilter1Wavelength2" label="Monochromator constant/setup wavelength" group="Filter">300.000000</parameter>
          <parameter name="MonochromatorFilter1Comment" label="Monochromator filter comment" group="Filter">Default Filter Comment</parameter>
          <parameter name="MonochromatorPolarizerAngle1" label="Monochromator polarization angle" group="Polarizer">0.000000</parameter>
          <parameter name="MonochromatorPolarizerType1" label="Monochromator polarizer type" group="Polarizer">1</parameter>
          <parameter name="MonochromatorPolarizerComment1" label="Monochromator polarizer comment" group="Polarizer">Default Polarizer Comment</parameter>
          <parameter name="MonochromatorAt2" label="Monochromator constant/setup wavelength" group="Monochromator">400.000000</parameter>
          <parameter name="MonochromatorShutter2" label="Monochromator shutter status" group="Monochromator">0</parameter>
          <parameter name="MonochromatorSlitIn2" label="Monochromator input slit width" group="Monochromator">4.000000</parameter>
          <parameter name="MonochromatorSlitOut2" label="Monochromator output slit width" group="Monochromator">4.000000</parameter>
          <parameter name="MonochromatorStepSize2" label="Monochromator step size" group="Monochromator">0.200000</parameter>
          <parameter name="MonochromatorType2" label="Monochromator type (excitation or emission)" group="Monochromator">8</parameter>
          <parameter name="MonochromatorUpperLimit2" label="Monochromator upper limit" group="Monochromator">950.000000</parameter>
          <parameter name="MonochromatorComment2" label="Monochromator comment" group="Monochromator">Default Monochromator Comment</parameter>
          <parameter name="MonochromatorNumFilter2" label="Monochromator # of filters switched" group="Filter">3</parameter>
          <parameter name="MonochromatorFilter2Wavelength1" label="Monochromator constant/setup wavelength" group="Filter">200.000000</parameter>
          <parameter name="MonochromatorFilter2Wavelength2" label="Monochromator constant/setup wavelength" group="Filter">300.000000</parameter>
          <parameter name="MonochromatorFilter2Wavelength3" label="Monochromator constant/setup wavelength" group="Filter">400.000000</parameter>
          <parameter name="MonochromatorFilter2Comment" label="Monochromator filter comment" group="Filter">Default Filter Comment</parameter>
          <parameter name="MonochromatorPolarizerAngle2" label="Monochromator polarization angle" group="Polarizer">90.000000</parameter>
          <parameter name="MonochromatorPolarizerType2" label="Monochromator polarizer type" group="Polarizer">1</parameter>
          <parameter name="MonochromatorPolarizerComment2" label="Monochromator polarizer comment" group="Polarizer">Default Polarizer Comment</parameter>
          <parameter name="PmtConnection1" label="PMT connection" group="PMT">10</parameter>
          <parameter name="PmtGain1" label="PMT gain" group="PMT">1</parameter>
          <parameter name="PmtHighVolt1" label="PMT high voltage" group="PMT">750</parameter>
          <parameter name="PmtOffset1" label="PMT offset status" group="PMT">0</parameter>
          <parameter name="PmtAcquisition1" label="PMT acquisition type" group="PMT">0</parameter>
          <parameter name="PmtComment1" label="PMT comment" group="PMT">Default Pmt Comment</parameter>
          <parameter name="PmtConnection2" label="PMT connection" group="PMT">10</parameter>
          <parameter name="PmtGain2" label="PMT gain" group="PMT">1</parameter>
          <parameter name="PmtHighVolt2" label="PMT high voltage" group="PMT">750</parameter>
          <parameter name="PmtOffset2" label="PMT offset status" group="PMT">0</parameter>
          <parameter name="PmtAcquisition2" label="PMT acquisition type" group="PMT">1</parameter>
          <parameter name="PmtComment2" label="PMT comment" group="PMT">Default Pmt Comment</parameter>
          <parameter name="InstrumentSpecific3" label="Instrument specific information" group="AUX">2</parameter>
          <parameter name="AuxChannelGain1" label="AUX channel gain" group="AUX">0</parameter>
          <parameter name="AuxChannelOffset1" label="AUX channel offset status" group="AUX">0</parameter>
          <parameter name="AuxChannelComment1" label="AUX channel comment" group="AUX">Default Aux Pmt Comment</parameter>
          <parameter name="AuxChannelGain2" label="AUX channel gain" group="AUX">1</parameter>
          <parameter name="AuxChannelOffset2" label="AUX channel offset status" group="AUX">0</parameter>
          <parameter name="AuxChannelComment2" label="AUX channel comment" group="AUX">Default Aux Pmt Comment</parameter>
          <parameter name="PlotInfo" label="Y-axis plotting annotation" group="YAxis">NULL</parameter>
          <parameter name="Corrected" label="Data corrected flag" group="MANIPULATION">0x0</parameter>
          <parameter name="Derivative" label="Degree of derivation" group="MANIPULATION">0</parameter>
          <parameter name="Norm" label="Normalized flag" group="MANIPULATION">0x0</parameter>
          <parameter name="Offset" label="Offset from the original data" group="MANIPULATION">0.000000</parameter>
          <parameter name="Scale" label="Scale from the original data" group="MANIPULATION">1.000000</parameter>
          <parameter name="Smooth" label="# of smoothing passes done" group="MANIPULATION">0</parameter>
          <parameter name="SmoothType" label="Smoothing type" group="MANIPULATION">1.000000</parameter>
          <values byteorder="INTEL" format="FLOAT32" numvalues="201">AIDoPACA6DwAgPI8AIDePACA/DwAgOM8AID8PAAA5jwAgPw8AIDtPAAA+jwAgO08AMAFPQCA9zwA
AAw9AMAKPQAAGz0AgCI9AEBJPQCggj0AoOY9AJBdPgCYyz4A5CI/AEhfPwBAiD8A+Jg/AJCcPwA+
lD8A6o0/ANaNPwCAjj8AKpk/AAapPwCqtD8AcME/AObNPwBG3D8A2uM/ABzyPwBWAUAAAgVAAF0O
QACVF0AACiBAAKwjQAAEK0AAuzJAAH00QAC6OEAAnD1AAN87QACoO0AAqj5AAIw+QAC1PUAAbT9A
AHw/QADHOkAAZDlAAOI4QAD6NEAAdC5AAEksQAA7K0AAniJAAN8dQAAiHEAAwBRAAHEOQACrC0AA
jQZAAFkDQABaAkAAIABAACT+PwAg/T8ANvs/AFr6PwB68z8ALvk/AP72PwAi8T8AjPA/AObwPwD4
6D8ApuA/AJbhPwD61z8ADs4/AKrIPwDuxT8A4r0/ANizPwDErj8AaKs/AK6hPwBcnj8ALpo/AFyU
PwD4jj8Aeoo/AHKIPwCugz8AGoE/AOB7PwBMdD8ATGo/AJhkPwAsXT8ApFM/ABRNPwBMRz8AjD4/
ABw7PwBwMj8AyC8/AIAnPwAAJT8ADB4/AAAWPwDcET8AXA8/AIgLPwDIBz8AMAQ/AMQBPwAo9T4A
yPA+AKjtPgAY4j4AsNs+AJjaPgD41D4A4M4+AODOPgCYxj4AiMI+AHi5PgC4uj4AqLE+AECrPgAY
sD4AOKk+AEChPgBQoD4AaKE+AAiYPgD4mD4AkJI+ACCPPgDAhT4AAIc+AACHPgB4gj4A4Hs+AJiA
PgAAdT4A0G0+AABwPgDQaD4AwFo+AIBePgAgVT4AMFQ+AIBUPgAgVT4AYEI+ABBHPgAwQD4AYD0+
AEA1PgCwMz4AQDU+ALAuPgBAKz4AECk+AEAmPgBwIz4AwCM+AIAdPgCgET4AYBU+ACAUPgAQFT4A
YAs+ABALPgCwBj4AAAc+AGAGPgBgBj4AcAA+AGD5PQBg+T0AoPo9AGD0PQDA6T0AgOg9AODiPQDg
2D0AANw9
          </values>
        </Ydata>
        <Ydata label="Y" units="UNKNOWN">
          <parameter name="DataTags" label="# of data points with tags" group="YAxis">0</parameter>
          <parameter name="Channel" label="Instrument channel stored" group="YAxis">10</parameter>
          <parameter name="MaxX" label="X-axis of the data set maximum" group="YAxis">350.000000</parameter>
          <parameter name="MaxY" label="Data set maximum" group="YAxis">3.321533</parameter>
          <parameter name="MinX" label="X-axis of the data set minimum" group="YAxis">309.000000</parameter>
          <parameter name="MinY" label="Data set minimum" group="YAxis">0.026550</parameter>
          <parameter name="YaxisType" label="Y-axis type" group="YAxis">0x12 = Intensity</parameter>
          <parameter name="YaxisUnit" label="Y-axis unit" group="YAxis">0x0 = Unknown</parameter>
          <parameter name="YAxisLL" label="Y-axis lower limit" group="YAxis">0.026550</parameter>
          <parameter name="YAxisUL" label="Y-axis upper limit" group="YAxis">3.321533</parameter>
          <parameter name="LabelY" label="Y-axis Label" group="YAxis">Y</parameter>
          <parameter name="AcqStarted" label="Date and Time the acquisition was started" group="Standard">0000-00-00 00:00:00</parameter>
          <parameter name="AcqCompleted" label="Date and Time the acquisition was completed" group="Standard">0000-00-00 00:00:00</parameter>
          <parameter name="ID" label="Acquiring instruments serial (ID) number" group="Standard">0</parameter>
          <parameter name="Instrument" label="Instrument type acquired on" group="Standard">7</parameter>
          <parameter name="Modified" label="Date and Time the acquisition was modified" group="Standard">0000-00-00 00:00:00</parameter>
          <parameter name="OSVersion" label="Operating system version" group="Standard">0.000000</parameter>
          <parameter name="Version" label="Program version (&apos;C&apos;) acquired on" group="Standard">0.000000</parameter>
          <parameter name="Comment" label="Acquisition comment" group="Standard">NULL</parameter>
          <parameter name="AcqTitle" label="Acquisition title" group="Standard">NULL</parameter>
          <parameter name="User" label="User who acquired this data" group="Standard">NULL</parameter>
          <parameter name="Monochromators" label="# of monochromators attached" group="Hardware">2</parameter>
          <parameter name="Pmts" label="# of PMTs attached" group="Hardware">2</parameter>
          <parameter name="Lamp" label="Excitation light source type" group="LAMP">1</parameter>
          <parameter name="LampComment" label="Excitation light source comment" group="LAMP">Default Lamp Comment</parameter>
          <parameter name="MonochromatorAt1" label="Monochromator constant/setup wavelength" group="Monochromator">300.000000</parameter>
          <parameter name="MonochromatorShutter1" label="Monochromator shutter status" group="Monochromator">0</parameter>
          <parameter name="MonochromatorSlitIn1" label="Monochromator input slit width" group="Monochromator">4.000000</parameter>
          <parameter name="MonochromatorSlitOut1" label="Monochromator output slit width" group="Monochromator">4.000000</parameter>
          <parameter name="MonochromatorStepSize1" label="Monochromator step size" group="Monochromator">0.200000</parameter>
          <parameter name="MonochromatorType1" label="Monochromator type (excitation or emission)" group="Monochromator">10</parameter>
          <parameter name="MonochromatorUpperLimit1" label="Monochromator upper limit" group="Monochromator">950.000000</parameter>
          <parameter name="MonochromatorComment1" label="Monochromator comment" group="Monochromator">Default Monochromator Comment</parameter>
          <parameter name="MonochromatorNumFilter1" label="Monochromator # of filters switched" group="Filter">2</parameter>
          <parameter name="MonochromatorFilter1Wavelength1" label="Monochromator constant/setup wavelength" group="Filter">200.000000</parameter>
          <parameter name="MonochromatorFilter1Wavelength2" label="Monochromator constant/setup wavelength" group="Filter">300.000000</parameter>
          <parameter name="MonochromatorFilter1Comment" label="Monochromator filter comment" group="Filter">Default Filter Comment</parameter>
          <parameter name="MonochromatorPolarizerAngle1" label="Monochromator polarization angle" group="Polarizer">0.000000</parameter>
          <parameter name="MonochromatorPolarizerType1" label="Monochromator polarizer type" group="Polarizer">1</parameter>
          <parameter name="MonochromatorPolarizerComment1" label="Monochromator polarizer comment" group="Polarizer">Default Polarizer Comment</parameter>
          <parameter name="MonochromatorAt2" label="Monochromator constant/setup wavelength" group="Monochromator">400.000000</parameter>
          <parameter name="MonochromatorShutter2" label="Monochromator shutter status" group="Monochromator">0</parameter>
          <parameter name="MonochromatorSlitIn2" label="Monochromator input slit width" group="Monochromator">4.000000</parameter>
          <parameter name="MonochromatorSlitOut2" label="Monochromator output slit width" group="Monochromator">4.000000</parameter>
          <parameter name="MonochromatorStepSize2" label="Monochromator step size" group="Monochromator">0.200000</parameter>
          <parameter name="MonochromatorType2" label="Monochromator type (excitation or emission)" group="Monochromator">8</parameter>
          <parameter name="MonochromatorUpperLimit2" label="Monochromator upper limit" group="Monochromator">950.000000</parameter>
          <parameter name="MonochromatorComment2" label="Monochromator comment" group="Monochromator">Default Monochromator Comment</parameter>
          <parameter name="MonochromatorNumFilter2" label="Monochromator # of filters switched" group="Filter">3</parameter>
          <parameter name="MonochromatorFilter2Wavelength1" label="Monochromator constant/setup wavelength" group="Filter">200.000000</parameter>
          <parameter name="MonochromatorFilter2Wavelength2" label="Monochromator constant/setup wavelength" group="Filter">300.000000</parameter>
          <parameter name="MonochromatorFilter2Wavelength3" label="Monochromator constant/setup wavelength" group="Filter">400.000000</parameter>
          <parameter name="MonochromatorFilter2Comment" label="Monochromator filter comment" group="Filter">Default Filter Comment</parameter>
          <parameter name="MonochromatorPolarizerAngle2" label="Monochromator polarization angle" group="Polarizer">90.000000</parameter>
          <parameter name="MonochromatorPolarizerType2" label="Monochromator polarizer type" group="Polarizer">1</parameter>
          <parameter name="MonochromatorPolarizerComment2" label="Monochromator polarizer comment" group="Polarizer">Default Polarizer Comment</parameter>
          <parameter name="PmtConnection1" label="PMT connection" group="PMT">10</parameter>
          <parameter name="PmtGain1" label="PMT gain" group="PMT">1</parameter>
          <parameter name="PmtHighVolt1" label="PMT high voltage" group="PMT">750</parameter>
          <parameter name="PmtOffset1" label="PMT offset status" group="PMT">0</parameter>
          <parameter name="PmtAcquisition1" label="PMT acquisition type" group="PMT">0</parameter>
          <parameter name="PmtComment1" label="PMT comment" group="PMT">Default Pmt Comment</parameter>
          <parameter name="PmtConnection2" label="PMT connection" group="PMT">10</parameter>
          <parameter name="PmtGain2" label="PMT gain" group="PMT">1</parameter>
          <parameter name="PmtHighVolt2" label="PMT high voltage" group="PMT">750</parameter>
          <parameter name="PmtOffset2" label="PMT offset status" group="PMT">0</parameter>
          <parameter name="PmtAcquisition2" label="PMT acquisition type" group="PMT">1</parameter>
          <parameter name="PmtComment2" label="PMT comment" group="PMT">Default Pmt Comment</parameter>
          <parameter name="InstrumentSpecific3" label="Instrument specific information" group="AUX">2</parameter>
          <parameter name="AuxChannelGain1" label="AUX channel gain" group="AUX">0</parameter>
          <parameter name="AuxChannelOffset1" label="AUX channel offset status" group="AUX">0</parameter>
          <parameter name="AuxChannelComment1" label="AUX channel comment" group="AUX">Default Aux Pmt Comment</parameter>
          <parameter name="AuxChannelGain2" label="AUX channel gain" group="AUX">1</parameter>
          <parameter name="AuxChannelOffset2" label="AUX channel offset status" group="AUX">0</parameter>
          <parameter name="AuxChannelComment2" label="AUX channel comment" group="AUX">Default Aux Pmt Comment</parameter>
          <parameter name="PlotInfo" label="Y-axis plotting annotation" group="YAxis">NULL</parameter>
          <parameter name="Corrected" label="Data corrected flag" group="MANIPULATION">0x0</parameter>
          <parameter name="Derivative" label="Degree of derivation" group="MANIPULATION">0</parameter>
          <parameter name="Norm" label="Normalized flag" group="MANIPULATION">0x0</parameter>
          <parameter name="Offset" label="Offset from the original data" group="MANIPULATION">0.000000</parameter>
          <parameter name="Scale" label="Scale from the original data" group="MANIPULATION">1.000000</parameter>
          <parameter name="Smooth" label="# of smoothing passes done" group="MANIPULATION">0</parameter>
          <parameter name="SmoothType" label="Smoothing type" group="MANIPULATION">1.000000</parameter>
          <values byteorder="INTEL" format="FLOAT32" numvalues="201">AIDePACA3jwAgPc8AIDePAAA+jwAAOE8AAD6PACA4zwAgPw8AIDZPABAAz0AgOM8AEADPQCA7TwA
gAQ9AAD6PABACD0AAP88AAAWPQBAFz0AQDo9AMBVPQDggz0AAK89AEADPgCwbz4AWN4+AMRCPwCu
kj8AoM0/ABz8PwCeCUAAZRBAAIENQADMA0AA9vk/AK72PwB0+T8AuPs/AMwDQADpCUAACQ1AAOMU
QADeHkAAtCVAAFAvQADqOkAAvURAAIhHQACzTkAAlFRAAJtSQAC7UEAAO1NAAPxQQABVTUAAaU1A
AD9PQABbR0AAS0NAAEVEQACgPkAA1TtAAJk7QABLOUAABzJAAEQsQABJLEAAzyNAACccQAB/GUAA
9xRAAPEQQACkDUAAfQxAAGYKQACWB0AARQhAAMcIQAAYCEAAGQdAAKAHQABCBkAABwVAAD0GQADP
AEAAvvo/ANT4PwBQ8D8A3OY/AHTgPwCK2T8A7NE/AJbIPwASxT8Aerw/AFC0PwBUsD8ApKs/AG6l
PwCWoD8Aip0/AHibPwAElz8AhpI/AFiOPwBYiT8A8II/AHSBPwBQdT8AUGs/AEhkPwCMYT8AKFI/
AGhOPwCcRz8ACEA/AFw3PwA8ND8AmC0/ALgmPwDcID8A6B4/AIQZPwBoEj8AvA4/ANQKPwAkBj8A
2AE/ANQAPwAI/D4A8Os+AAjtPgBw5D4AoNc+AKDSPgDo0D4AiMc+APDDPgDIuT4AeLk+AGiwPgDo
rT4AqKw+AJioPgAQnz4AgJ0+AMiWPgDIkT4AmI8+AICOPgAIiT4AWIQ+AEiAPgDAgD4AoH8+ABB0
PgAgbj4AEGo+AKBcPgCQYj4A4Fg+AEBYPgBwUD4A4FM+AOBJPgDAQT4AgEU+ALBHPgCQNT4AADQ+
ALAuPgDwKj4AkCE+AKAlPgCwGj4AsB8+AHAePgCQHD4AUBE+AJASPgDQDj4AQBI+APAHPgCgDD4A
4AM+AEADPgAA+j0AoP89AOD2PQDg8T0A4PE9AMDzPQDA5D0A4Ow9AEDiPQDA2j0AINA9AADcPQBA
yT0AQMk9
          </values>
        </Ydata>
        <Ydata label="Y" units="UNKNOWN">
          <parameter name="DataTags" label="# of data points with tags" group="YAxis">0</parameter>
          <parameter name="Channel" label="Instrument channel stored" group="YAxis">10</parameter>
          <parameter name="MaxX" label="X-axis of the data set maximum" group="YAxis">356.000000</parameter>
          <parameter name="MaxY" label="Data set maximum" group="YAxis">2.794189</parameter>
          <parameter name="MinX" label="X-axis of the data set minimum" group="YAxis">303.000000</parameter>
          <parameter name="MinY" label="Data set minimum" group="YAxis">0.026550</parameter>
          <parameter name="YaxisType" label="Y-axis type" group="YAxis">0x12 = Intensity</parameter>
          <parameter name="YaxisUnit" label="Y-axis unit" group="YAxis">0x0 = Unknown</parameter>
          <parameter name="YAxisLL" label="Y-axis lower limit" group="YAxis">0.026550</parameter>
          <parameter name="YAxisUL" label="Y-axis upper limit" group="YAxis">2.794189</parameter>
          <parameter name="LabelY" label="Y-axis Label" group="YAxis">Y</parameter>
          <parameter name="AcqStarted" label="Date and Time the acquisition was started" group="Standard">0000-00-00 00:00:00</parameter>
          <parameter name="AcqCompleted" label="Date and Time the acquisition was completed" group="Standard">0000-00-00 00:00:00</parameter>
          <parameter name="ID" label="Acquiring instruments serial (ID) number" group="Standard">0</parameter>
          <parameter name="Instrument" label="Instrument type acquired on" group="Standard">7</parameter>
          <parameter name="Modified" label="Date and Time the acquisition was modified" group="Standard">0000-00-00 00:00:00</parameter>
          <parameter name="OSVersion" label="Operating system version" group="Standard">0.000000</parameter>
          <parameter name="Version" label="Program version (&apos;C&apos;) acquired on" group="Standard">0.000000</parameter>
          <parameter name="Comment" label="Acquisition comment" group="Standard">NULL</parameter>
          <parameter name="AcqTitle" label="Acquisition title" group="Standard">NULL</parameter>
          <parameter name="User" label="User who acquired this data" group="Standard">NULL</parameter>
          <parameter name="Monochromators" label="# of monochromators attached" group="Hardware">2</parameter>
          <parameter name="Pmts" label="# of PMTs attached" group="Hardware">2</parameter>
          <parameter name="Lamp" label="Excitation light source type" group="LAMP">1</parameter>
          <parameter name="LampComment" label="Excitation light source comment" group="LAMP">Default Lamp Comment</parameter>
          <parameter name="MonochromatorAt1" label="Monochromator constant/setup wavelength" group="Monochromator">300.000000</parameter>
          <parameter name="MonochromatorShutter1" label="Monochromator shutter status" group="Monochromator">0</parameter>
          <parameter name="MonochromatorSlitIn1" label="Monochromator input slit width" group="Monochromator">4.000000</parameter>
          <parameter name="MonochromatorSlitOut1" label="Monochromator output slit width" group="Monochromator">4.000000</parameter>
          <parameter name="MonochromatorStepSize1" label="Monochromator step size" group="Monochromator">0.200000</parameter>
          <parameter name="MonochromatorType1" label="Monochromator type (excitation or emission)" group="Monochromator">10</parameter>
          <parameter name="MonochromatorUpperLimit1" label="Monochromator upper limit" group="Monochromator">950.000000</parameter>
          <parameter name="MonochromatorComment1" label="Monochromator comment" group="Monochromator">Default Monochromator Comment</parameter>
          <parameter name="MonochromatorNumFilter1" label="Monochromator # of filters switched" group="Filter">2</parameter>
          <parameter name="MonochromatorFilter1Wavelength1" label="Monochromator constant/setup wavelength" group="Filter">200.000000</parameter>
          <parameter name="MonochromatorFilter1Wavelength2" label="Monochromator constant/setup wavelength" group="Filter">300.000000</parameter>
          <parameter name="MonochromatorFilter1Comment" label="Monochromator filter comment" group="Filter">Default Filter Comment</parameter>
          <parameter name="MonochromatorPolarizerAngle1" label="Monochromator polarization angle" group="Polarizer">0.000000</parameter>
          <parameter name="MonochromatorPolarizerType1" label="Monochromator polarizer type" group="Polarizer">1</parameter>
          <parameter name="MonochromatorPolarizerComment1" label="Monochromator polarizer comment" group="Polarizer">Default Polarizer Comment</parameter>
          <parameter name="MonochromatorAt2" label="Monochromator constant/setup wavelength" group="Monochromator">400.000000</parameter>
          <parameter name="MonochromatorShutter2" label="Monochromator shutter status" group="Monochromator">0</parameter>
          <parameter name="MonochromatorSlitIn2" label="Monochromator input slit width" group="Monochromator">4.000000</parameter>
          <parameter name="MonochromatorSlitOut2" label="Monochromator output slit width" group="Monochromator">4.000000</parameter>
          <parameter name="MonochromatorStepSize2" label="Monochromator step size" group="Monochromator">0.200000</parameter>
          <parameter name="MonochromatorType2" label="Monochromator type (excitation or emission)" group="Monochromator">8</parameter>
          <parameter name="MonochromatorUpperLimit2" label="Monochromator upper limit" group="Monochromator">950.000000</parameter>
          <parameter name="MonochromatorComment2" label="Monochromator comment" group="Monochromator">Default Monochromator Comment</parameter>
          <parameter name="MonochromatorNumFilter2" label="Monochromator # of filters switched" group="Filter">3</parameter>
          <parameter name="MonochromatorFilter2Wavelength1" label="Monochromator constant/setup wavelength" group="Filter">200.000000</parameter>
          <parameter name="MonochromatorFilter2Wavelength2" label="Monochromator constant/setup wavelength" group="Filter">300.000000</parameter>
          <parameter name="MonochromatorFilter2Wavelength3" label="Monochromator constant/setup wavelength" group="Filter">400.000000</parameter>
          <parameter name="MonochromatorFilter2Comment" label="Monochromator filter comment" group="Filter">Default Filter Comment</parameter>
          <parameter name="MonochromatorPolarizerAngle2" label="Monochromator polarization angle" group="Polarizer">90.000000</parameter>
          <parameter name="MonochromatorPolarizerType2" label="Monochromator polarizer type" group="Polarizer">1</parameter>
          <parameter name="MonochromatorPolarizerComment2" label="Monochromator polarizer comment" group="Polarizer">Default Polarizer Comment</parameter>
          <parameter name="PmtConnection1" label="PMT connection" group="PMT">10</parameter>
          <parameter name="PmtGain1" label="PMT gain" group="PMT">1</parameter>
          <parameter name="PmtHighVolt1" label="PMT high voltage" group="PMT">750</parameter>
          <parameter name="PmtOffset1" label="PMT offset status" group="PMT">0</parameter>
          <parameter name="PmtAcquisition1" label="PMT acquisition type" group="PMT">0</parameter>
          <parameter name="PmtComment1" label="PMT comment" group="PMT">Default Pmt Comment</parameter>
          <parameter name="PmtConnection2" label="PMT connection" group="PMT">10</parameter>
          <parameter name="PmtGain2" label="PMT gain" group="PMT">1</parameter>
          <parameter name="PmtHighVolt2" label="PMT high voltage" group="PMT">750</parameter>
          <parameter name="PmtOffset2" label="PMT offset status" group="PMT">0</parameter>
          <parameter name="PmtAcquisition2" label="PMT acquisition type" group="PMT">1</parameter>
          <parameter name="PmtComment2" label="PMT comment" group="PMT">Default Pmt Comment</parameter>
          <parameter name="InstrumentSpecific3" label="Instrument specific information" group="AUX">2</parameter>
          <parameter name="AuxChannelGain1" label="AUX channel gain" group="AUX">0</parameter>
          <parameter name="AuxChannelOffset1" label="AUX channel offset status" group="AUX">0</parameter>
          <parameter name="AuxChannelComment1" label="AUX channel comment" group="AUX">Default Aux Pmt Comment</parameter>
          <parameter name="AuxChannelGain2" label="AUX channel gain" group="AUX">1</parameter>
          <parameter name="AuxChannelOffset2" label="AUX channel offset status" group="AUX">0</parameter>
          <parameter name="AuxChannelComment2" label="AUX channel comment" group="AUX">Default Aux Pmt Comment</parameter>
          <parameter name="PlotInfo" label="Y-axis plotting annotation" group="YAxis">NULL</parameter>
          <parameter name="Corrected" label="Data corrected flag" group="MANIPULATION">0x0</parameter>
          <parameter name="Derivative" label="Degree of derivation" group="MANIPULATION">0</parameter>
          <parameter name="Norm" label="Normalized flag" group="MANIPULATION">0x0</parameter>
          <parameter name="Offset" label="Offset from the original data" group="MANIPULATION">0.000000</parameter>
          <parameter name="Scale" label="Scale from the original data" group="MANIPULATION">1.000000</parameter>
          <parameter name="Smooth" label="# of smoothing passes done" group="MANIPULATION">0</parameter>
          <parameter name="SmoothType" label="Smoothing type" group="MANIPULATION">1.000000</parameter>
          <values byteorder="INTEL" format="FLOAT32" numvalues="201">AADmPACA4zwAgPc8AIDZPAAA9TwAAOE8AAD6PAAA4TwAgPw8AADmPACA/DwAAOE8AID3PAAA6zwA
AAI9AIDoPACA/DwAgOg8AMAFPQCA/DwAgBM9AAAMPQCAIj0AwC09AABSPQCAdz0AwK09AKDcPQBg
Hz4A8H8+AFDhPgCEQT8AZpk/ALDgPwD8CkAAFyJAAN4oQABYHUAA+xBAAGcEQACG8T8AAOY/AMDp
PwDI8D8AIPg/AAQDQADGCUAAAw5AAC8UQADFHkAAYiJAAMwmQAD4LEAAaS9AAEYvQAAwMUAA1DJA
APQwQACNLkAAFC9AAMssQAA/J0AAGCZAAOUhQAABH0AAxBpAAKIZQAC7FEAAFRBAAM4LQABeDUAA
WgdAANEDQACeBEAAlwFAANz/PwDUAEAAIwJAAKcAQAD8AEAA2wNAAK8CQAAPAkAAgQNAALMDQAAa
/j8Awvs/AHL2PwDy7j8ASOQ/AKzfPwCo2T8A3tA/AHLJPwB4yD8A9r0/AG65PwCatT8A3K8/AFap
PwDAqD8AmKM/ABaePwASnT8Axpg/ACySPwAokT8AXo0/ANyHPwAUgj8A1Hg/AFRxPwCQZz8AgF4/
ANBZPwBsTz8AAEg/APBDPwC8Oz8APDQ/ABgwPwAsKz8ALCY/AGQgPwBUIT8AWBg/ALgSPwAgDz8A
hA8/ANQFPwCwAT8ANAA/APj3PgAg8z4A0Og+ACDfPgCA2T4AaNM+ABjOPgAAwz4A6Lw+AKC5PgAw
sT4AgKw+AMCtPgDIpT4ASJ4+AOCXPgBwmT4AKJY+AHiRPgC4jT4AOIs+AMiHPgCQgz4AAII+ADB3
PgAwbT4AUHA+ALBlPgCgXD4AoFc+AMBaPgCQTj4AUE0+AFBDPgBQQz4AgDY+AGA4PgCAMT4AkDA+
AOArPgAQKT4AkCE+AMAjPgBAIT4AACA+ABAVPgCQFz4A8BE+AIAOPgCgDD4AAAw+APAHPgCABD4A
cAA+AMD9PQBg+T0AYP49AKDrPQAg8z0AoOs9ACDuPQCA4z0AgOM9AMDVPQBA3T0AwNA9AEDOPQAg
xj0AAMM9
          </values>
        </Ydata>
        <Ydata label="Y" units="UNKNOWN">
          <parameter name="DataTags" label="# of data points with tags" group="YAxis">0</parameter>
          <parameter name="Channel" label="Instrument channel stored" group="YAxis">10</parameter>
          <parameter name="MaxX" label="X-axis of the data set maximum" group="YAxis">341.000000</parameter>
          <parameter name="MaxY" label="Data set maximum" group="YAxis">2.351990</parameter>
          <parameter name="MinX" label="X-axis of the data set minimum" group="YAxis">305.000000</parameter>
          <parameter name="MinY" label="Data set minimum" group="YAxis">0.025940</parameter>
          <parameter name="YaxisType" label="Y-axis type" group="YAxis">0x12 = Intensity</parameter>
          <parameter name="YaxisUnit" label="Y-axis unit" group="YAxis">0x0 = Unknown</parameter>
          <parameter name="YAxisLL" label="Y-axis lower limit" group="YAxis">0.025940</parameter>
          <parameter name="YAxisUL" label="Y-axis upper limit" group="YAxis">2.351990</parameter>
          <parameter name="LabelY" label="Y-axis Label" group="YAxis">Y</parameter>
          <parameter name="AcqStarted" label="Date and Time the acquisition was started" group="Standard">0000-00-00 00:00:00</parameter>
          <parameter name="AcqCompleted" label="Date and Time the acquisition was completed" group="Standard">0000-00-00 00:00:00</parameter>
          <parameter name="ID" label="Acquiring instruments serial (ID) number" group="Standard">0</parameter>
          <parameter name="Instrument" label="Instrument type acquired on" group="Standard">7</parameter>
          <parameter name="Modified" label="Date and Time the acquisition was modified" group="Standard">0000-00-00 00:00:00</parameter>
          <parameter name="OSVersion" label="Operating system version" group="Standard">0.000000</parameter>
          <parameter name="Version" label="Program version (&apos;C&apos;) acquired on" group="Standard">0.000000</parameter>
          <parameter name="Comment" label="Acquisition comment" group="Standard">NULL</parameter>
          <parameter name="AcqTitle" label="Acquisition title" group="Standard">NULL</parameter>
          <parameter name="User" label="User who acquired this data" group="Standard">NULL</parameter>
          <parameter name="Monochromators" label="# of monochromators attached" group="Hardware">2</parameter>
          <parameter name="Pmts" label="# of PMTs attached" group="Hardware">2</parameter>
          <parameter name="Lamp" label="Excitation light source type" group="LAMP">1</parameter>
          <parameter name="LampComment" label="Excitation light source comment" group="LAMP">Default Lamp Comment</parameter>
          <parameter name="MonochromatorAt1" label="Monochromator constant/setup wavelength" group="Monochromator">300.000000</parameter>
          <parameter name="MonochromatorShutter1" label="Monochromator shutter status" group="Monochromator">0</parameter>
          <parameter name="MonochromatorSlitIn1" label="Monochromator input slit width" group="Monochromator">4.000000</parameter>
          <parameter name="MonochromatorSlitOut1" label="Monochromator output slit width" group="Monochromator">4.000000</parameter>
          <parameter name="MonochromatorStepSize1" label="Monochromator step size" group="Monochromator">0.200000</parameter>
          <parameter name="MonochromatorType1" label="Monochromator type (excitation or emission)" group="Monochromator">10</parameter>
          <parameter name="MonochromatorUpperLimit1" label="Monochromator upper limit" group="Monochromator">950.000000</parameter>
          <parameter name="MonochromatorComment1" label="Monochromator comment" group="Monochromator">Default Monochromator Comment</parameter>
          <parameter name="MonochromatorNumFilter1" label="Monochromator # of filters switched" group="Filter">2</parameter>
          <parameter name="MonochromatorFilter1Wavelength1" label="Monochromator constant/setup wavelength" group="Filter">200.000000</parameter>
          <parameter name="MonochromatorFilter1Wavelength2" label="Monochromator constant/setup wavelength" group="Filter">300.000000</parameter>
          <parameter name="MonochromatorFilter1Comment" label="Monochromator filter comment" group="Filter">Default Filter Comment</parameter>
          <parameter name="MonochromatorPolarizerAngle1" label="Monochromator polarization angle" group="Polarizer">0.000000</parameter>
          <parameter name="MonochromatorPolarizerType1" label="Monochromator polarizer type" group="Polarizer">1</parameter>
          <parameter name="MonochromatorPolarizerComment1" label="Monochromator polarizer comment" group="Polarizer">Default Polarizer Comment</parameter>
          <parameter name="MonochromatorAt2" label="Monochromator constant/setup wavelength" group="Monochromator">400.000000</parameter>
          <parameter name="MonochromatorShutter2" label="Monochromator shutter status" group="Monochromator">0</parameter>
          <parameter name="MonochromatorSlitIn2" label="Monochromator input slit width" group="Monochromator">4.000000</parameter>
          <parameter name="MonochromatorSlitOut2" label="Monochromator output slit width" group="Monochromator">4.000000</parameter>
          <parameter name="MonochromatorStepSize2" label="Monochromator step size" group="Monochromator">0.200000</parameter>
          <parameter name="MonochromatorType2" label="Monochromator type (excitation or emission)" group="Monochromator">8</parameter>
          <parameter name="MonochromatorUpperLimit2" label="Monochromator upper limit" group="Monochromator">950.000000</parameter>
          <parameter name="MonochromatorComment2" label="Monochromator comment" group="Monochromator">Default Monochromator Comment</parameter>
          <parameter name="MonochromatorNumFilter2" label="Monochromator # of filters switched" group="Filter">3</parameter>
          <parameter name="MonochromatorFilter2Wavelength1" label="Monochromator constant/setup wavelength" group="Filter">200.000000</parameter>
          <parameter name="MonochromatorFilter2Wavelength2" label="Monochromator constant/setup wavelength" group="Filter">300.000000</parameter>
          <parameter name="MonochromatorFilter2Wavelength3" label="Monochromator constant/setup wavelength" group="Filter">400.000000</parameter>
          <parameter name="MonochromatorFilter2Comment" label="Monochromator filter comment" group="Filter">Default Filter Comment</parameter>
          <parameter name="MonochromatorPolarizerAngle2" label="Monochromator polarization angle" group="Polarizer">90.000000</parameter>
          <parameter name="MonochromatorPolarizerType2" label="Monochromator polarizer type" group="Polarizer">1</parameter>
          <parameter name="MonochromatorPolarizerComment2" label="Monochromator polarizer comment" group="Polarizer">Default Polarizer Comment</parameter>
          <parameter name="PmtConnection1" label="PMT connection" group="PMT">10</parameter>
          <parameter name="PmtGain1" label="PMT gain" group="PMT">1</parameter>
          <parameter name="PmtHighVolt1" label="PMT high voltage" group="PMT">750</parameter>
          <parameter name="PmtOffset1" label="PMT offset status" group="PMT">0</parameter>
          <parameter name="PmtAcquisition1" label="PMT acquisition type" group="PMT">0</parameter>
          <parameter name="PmtComment1" label="PMT comment" group="PMT">Default Pmt Comment</parameter>
          <parameter name="PmtConnection2" label="PMT connection" group="PMT">10</parameter>
          <parameter name="PmtGain2" label="PMT gain" group="PMT">1</parameter>
          <parameter name="PmtHighVolt2" label="PMT high voltage" group="PMT">750</parameter>
          <parameter name="PmtOffset2" label="PMT offset status" group="PMT">0</parameter>
          <parameter name="PmtAcquisition2" label="PMT acquisition type" group="PMT">1</parameter>
          <parameter name="PmtComment2" label="PMT comment" group="PMT">Default Pmt Comment</parameter>
          <parameter name="InstrumentSpecific3" label="Instrument specific information" group="AUX">2</parameter>
          <parameter name="AuxChannelGain1" label="AUX channel gain" group="AUX">0</parameter>
          <parameter name="AuxChannelOffset1" label="AUX channel offset status" group="AUX">0</parameter>
          <parameter name="AuxChannelComment1" label="AUX channel comment" group="AUX">Default Aux Pmt Comment</parameter>
          <parameter name="AuxChannelGain2" label="AUX channel gain" group="AUX">1</parameter>
          <parameter name="AuxChannelOffset2" label="AUX channel offset status" group="AUX">0</parameter>
          <parameter name="AuxChannelComment2" label="AUX channel comment" group="AUX">Default Aux Pmt Comment</parameter>
          <parameter name="PlotInfo" label="Y-axis plotting annotation" group="YAxis">NULL</parameter>
          <parameter name="Corrected" label="Data corrected flag" group="MANIPULATION">0x0</parameter>
          <parameter name="Derivative" label="Degree of derivation" group="MANIPULATION">0</parameter>
          <parameter name="Norm" label="Normalized flag" group="MANIPULATION">0x0</parameter>
          <parameter name="Offset" label="Offset from the original data" group="MANIPULATION">0.000000</parameter>
          <parameter name="Scale" label="Scale from the original data" group="MANIPULATION">1.000000</parameter>
          <parameter name="Smooth" label="# of smoothing passes done" group="MANIPULATION">0</parameter>
          <parameter name="SmoothType" label="Smoothing type" group="MANIPULATION">1.000000</parameter>
          <values byteorder="INTEL" format="FLOAT32" numvalues="201">AIDZPACA3jwAAOs8AIDZPACA7TwAgNQ8AIDyPACA2TwAAPU8AADXPAAA9TwAgNk8AIDyPACA2TwA
gPc8AIDUPADAAD0AgN48AID3PACA3jwAAPo8AADrPADAAD0AAPU8AAAMPQCACT0AABs9AMAoPQCA
Tz0AwHM9ACCePQBAvz0AoP89ANAnPgBwcz4A+Mo+ACAjPwDcgj8ALsI/AO7yPwBVDEAAhxZAAH0M
QAB6/T8AWuY/ANjMPwBOwD8ASME/AMLEPwCIxz8Aqs0/AJ7ZPwDw3D8A1t0/ALzoPwCw7z8ACPc/
AIT9PwDYAUAAHQNAAJYCQADUBUAArAVAAPsBQAD6/z8AGv4/AKLzPwDg7D8Alus/AJLgPwAU3D8A
Kto/AIzXPwDG1D8AOtQ/ANDZPwBW2z8AHtw/ANzhPwBc5D8AxuM/ANzmPwDY6j8AtOs/AG7rPwBA
7D8A0Og/ACziPwCo3j8AQNg/ANrPPwCeyj8AEsU/AHK/PwDUtz8A7rY/AEyzPwDcqj8A3qg/AGCk
PwDkoj8Ayp4/AOiZPwBMmj8AIpc/ABaPPwBwjz8Aaos/AOKGPwAShD8AxH4/AIRzPwCUbT8AOGU/
AFhePwAEUz8A7Ew/AFRJPwAQQj8AODg/AHQ4PwD8LT8AbCw/APwoPwAYIT8AeBs/ADgaPwC8Ez8A
wA8/ACwIPwD0CD8AJAY/AKwAPwAY9j4AePU+AODiPgCA3j4AKNc+AMjSPgBoyT4AaMk+ABi/PgBo
uj4AILI+AFixPgAwrD4A0Kc+APCgPgBInj4AmJk+AJiUPgAYkj4AMI4+AJCIPgDYhj4AMHw+AKB6
PgCwbz4A0HI+AEBnPgCAXj4A8Fw+AKBcPgCgTT4AME8+AJBJPgAgRj4AYD0+ANA7PgCAOz4A0DE+
AGAuPgAgLT4AACU+AEAmPgAgGT4AcB4+APAWPgCgFj4AkBI+AMAPPgCQCD4AoAc+AKACPgCACT4A
IAU+AGD+PQDg7D0AgO09AIDyPQAA5j0AwOQ9AEDnPQDg4j0AYOU9AADcPQDA2j0AgM89AIDKPQBA
vz0AoMM9
          </values>
        </Ydata>
        <Ydata label="Y" units="UNKNOWN">
          <parameter name="DataTags" label="# of data points with tags" group="YAxis">0</parameter>
          <parameter name="Channel" label="Instrument channel stored" group="YAxis">10</parameter>
          <parameter name="MaxX" label="X-axis of the data set maximum" group="YAxis">345.000000</parameter>
          <parameter name="MaxY" label="Data set maximum" group="YAxis">2.455139</parameter>
          <parameter name="MinX" label="X-axis of the data set minimum" group="YAxis">305.000000</parameter>
          <parameter name="MinY" label="Data set minimum" group="YAxis">0.025940</parameter>
          <parameter name="YaxisType" label="Y-axis type" group="YAxis">0x12 = Intensity</parameter>
          <parameter name="YaxisUnit" label="Y-axis unit" group="YAxis">0x0 = Unknown</parameter>
          <parameter name="YAxisLL" label="Y-axis lower limit" group="YAxis">0.025940</parameter>
          <parameter name="YAxisUL" label="Y-axis upper limit" group="YAxis">2.455139</parameter>
          <parameter name="LabelY" label="Y-axis Label" group="YAxis">Y</parameter>
          <parameter name="AcqStarted" label="Date and Time the acquisition was started" group="Standard">0000-00-00 00:00:00</parameter>
          <parameter name="AcqCompleted" label="Date and Time the acquisition was completed" group="Standard">0000-00-00 00:00:00</parameter>
          <parameter name="ID" label="Acquiring instruments serial (ID) number" group="Standard">0</parameter>
          <parameter name="Instrument" label="Instrument type acquired on" group="Standard">7</parameter>
          <parameter name="Modified" label="Date and Time the acquisition was modified" group="Standard">0000-00-00 00:00:00</parameter>
          <parameter name="OSVersion" label="Operating system version" group="Standard">0.000000</parameter>
          <parameter name="Version" label="Program version (&apos;C&apos;) acquired on" group="Standard">0.000000</parameter>
          <parameter name="Comment" label="Acquisition comment" group="Standard">NULL</parameter>
          <parameter name="AcqTitle" label="Acquisition title" group="Standard">NULL</parameter>
          <parameter name="User" label="User who acquired this data" group="Standard">NULL</parameter>
          <parameter name="Monochromators" label="# of monochromators attached" group="Hardware">2</parameter>
          <parameter name="Pmts" label="# of PMTs attached" group="Hardware">2</parameter>
          <parameter name="Lamp" label="Excitation light source type" group="LAMP">1</parameter>
          <parameter name="LampComment" label="Excitation light source comment" group="LAMP">Default Lamp Comment</parameter>
          <parameter name="MonochromatorAt1" label="Monochromator constant/setup wavelength" group="Monochromator">300.000000</parameter>
          <parameter name="MonochromatorShutter1" label="Monochromator shutter status" group="Monochromator">0</parameter>
          <parameter name="MonochromatorSlitIn1" label="Monochromator input slit width" group="Monochromator">4.000000</parameter>
          <parameter name="MonochromatorSlitOut1" label="Monochromator output slit width" group="Monochromator">4.000000</parameter>
          <parameter name="MonochromatorStepSize1" label="Monochromator step size" group="Monochromator">0.200000</parameter>
          <parameter name="MonochromatorType1" label="Monochromator type (excitation or emission)" group="Monochromator">10</parameter>
          <parameter name="MonochromatorUpperLimit1" label="Monochromator upper limit" group="Monochromator">950.000000</parameter>
          <parameter name="MonochromatorComment1" label="Monochromator comment" group="Monochromator">Default Monochromator Comment</parameter>
          <parameter name="MonochromatorNumFilter1" label="Monochromator # of filters switched" group="Filter">2</parameter>
          <parameter name="MonochromatorFilter1Wavelength1" label="Monochromator constant/setup wavelength" group="Filter">200.000000</parameter>
          <parameter name="MonochromatorFilter1Wavelength2" label="Monochromator constant/setup wavelength" group="Filter">300.000000</parameter>
          <parameter name="MonochromatorFilter1Comment" label="Monochromator filter comment" group="Filter">Default Filter Comment</parameter>
          <parameter name="MonochromatorPolarizerAngle1" label="Monochromator polarization angle" group="Polarizer">0.000000</parameter>
          <parameter name="MonochromatorPolarizerType1" label="Monochromator polarizer type" group="Polarizer">1</parameter>
          <parameter name="MonochromatorPolarizerComment1" label="Monochromator polarizer comment" group="Polarizer">Default Polarizer Comment</parameter>
          <parameter name="MonochromatorAt2" label="Monochromator constant/setup wavelength" group="Monochromator">400.000000</parameter>
          <parameter name="MonochromatorShutter2" label="Monochromator shutter status" group="Monochromator">0</parameter>
          <parameter name="MonochromatorSlitIn2" label="Monochromator input slit width" group="Monochromator">4.000000</parameter>
          <parameter name="MonochromatorSlitOut2" label="Monochromator output slit width" group="Monochromator">4.000000</parameter>
          <parameter name="MonochromatorStepSize2" label="Monochromator step size" group="Monochromator">0.200000</parameter>
          <parameter name="MonochromatorType2" label="Monochromator type (excitation or emission)" group="Monochromator">8</parameter>
          <parameter name="MonochromatorUpperLimit2" label="Monochromator upper limit" group="Monochromator">950.000000</parameter>
          <parameter name="MonochromatorComment2" label="Monochromator comment" group="Monochromator">Default Monochromator Comment</parameter>
          <parameter name="MonochromatorNumFilter2" label="Monochromator # of filters switched" group="Filter">3</parameter>
          <parameter name="MonochromatorFilter2Wavelength1" label="Monochromator constant/setup wavelength" group="Filter">200.000000</parameter>
          <parameter name="MonochromatorFilter2Wavelength2" label="Monochromator constant/setup wavelength" group="Filter">300.000000</parameter>
          <parameter name="MonochromatorFilter2Wavelength3" label="Monochromator constant/setup wavelength" group="Filter">400.000000</parameter>
          <parameter name="MonochromatorFilter2Comment" label="Monochromator filter comment" group="Filter">Default Filter Comment</parameter>
          <parameter name="MonochromatorPolarizerAngle2" label="Monochromator polarization angle" group="Polarizer">90.000000</parameter>
          <parameter name="MonochromatorPolarizerType2" label="Monochromator polarizer type" group="Polarizer">1</parameter>
          <parameter name="MonochromatorPolarizerComment2" label="Monochromator polarizer comment" group="Polarizer">Default Polarizer Comment</parameter>
          <parameter name="PmtConnection1" label="PMT connection" group="PMT">10</parameter>
          <parameter name="PmtGain1" label="PMT gain" group="PMT">1</parameter>
          <parameter name="PmtHighVolt1" label="PMT high voltage" group="PMT">750</parameter>
          <parameter name="PmtOffset1" label="PMT offset status" group="PMT">0</parameter>
          <parameter name="PmtAcquisition1" label="PMT acquisition type" group="PMT">0</parameter>
          <parameter name="PmtComment1" label="PMT comment" group="PMT">Default Pmt Comment</parameter>
          <parameter name="PmtConnection2" label="PMT connection" group="PMT">10</parameter>
          <parameter name="PmtGain2" label="PMT gain" group="PMT">1</parameter>
          <parameter name="PmtHighVolt2" label="PMT high voltage" group="PMT">750</parameter>
          <parameter name="PmtOffset2" label="PMT offset status" group="PMT">0</parameter>
          <parameter name="PmtAcquisition2" label="PMT acquisition type" group="PMT">1</parameter>
          <parameter name="PmtComment2" label="PMT comment" group="PMT">Default Pmt Comment</parameter>
          <parameter name="InstrumentSpecific3" label="Instrument specific information" group="AUX">2</parameter>
          <parameter name="AuxChannelGain1" label="AUX channel gain" group="AUX">0</parameter>
          <parameter name="AuxChannelOffset1" label="AUX channel offset status" group="AUX">0</parameter>
          <parameter name="AuxChannelComment1" label="AUX channel comment" group="AUX">Default Aux Pmt Comment</parameter>
          <parameter name="AuxChannelGain2" label="AUX channel gain" group="AUX">1</parameter>
          <parameter name="AuxChannelOffset2" label="AUX channel offset status" group="AUX">0</parameter>
          <parameter name="AuxChannelComment2" label="AUX channel comment" group="AUX">Default Aux Pmt Comment</parameter>
          <parameter name="PlotInfo" label="Y-axis plotting annotation" group="YAxis">NULL</parameter>
          <parameter name="Corrected" label="Data corrected flag" group="MANIPULATION">0x0</parameter>
          <parameter name="Derivative" label="Degree of derivation" group="MANIPULATION">0</parameter>
          <parameter name="Norm" label="Normalized flag" group="MANIPULATION">0x0</parameter>
          <parameter name="Offset" label="Offset from the original data" group="MANIPULATION">0.000000</parameter>
          <parameter name="Scale" label="Scale from the original data" group="MANIPULATION">1.000000</parameter>
          <parameter name="Smooth" label="# of smoothing passes done" group="MANIPULATION">0</parameter>
          <parameter name="SmoothType" label="Smoothing type" group="MANIPULATION">1.000000</parameter>
          <values byteorder="INTEL" format="FLOAT32" numvalues="201">AIDjPACA3jwAgPI8AIDZPAAA8DwAgNQ8AAD1PACA3jwAAP88AADcPAAA9TwAgNk8AIDyPAAA3DwA
APo8AIDZPACA9zwAANc8AAD1PAAA3DwAAPU8AADhPAAA+jwAgOM8AMAAPQCA6DwAQAM9AID3PAAA
DD0AQA09AMAjPQDAIz0AAEg9AABcPQCghz0AwJk9AIC7PQDg7D0AoCA+AMBfPgDQyj4ARDs/ABSW
PwDI3D8ADg1AACEdQAB8HEAARRJAAPcAQAAK1z8ADro/AMyrPwAWoz8AHqU/ALynPwCSqT8AJK4/
ACCyPwDQtj8A1Lc/AKS6PwBIwT8A/sQ/AKDIPwAAyD8Ahsk/AMjIPwCYwT8AfL8/AMC8PwAusz8A
7rE/ADqxPwDmrz8A0Kw/APqvPwCysT8AHrQ/AJa5PwCQvz8AZMM/AGLKPwAIzz8AoNI/ACTRPwBu
0j8AVNM/AKbMPwASyj8AXsQ/AN68PwDiuD8AOLM/AF6wPwAaqT8AjKU/AGSlPwB2oj8Ahpw/AEab
PwB8nD8AvJg/AHKXPwBalj8AxJA/ANaNPwD+jT8AjIc/AHKDPwBMgT8AtHo/ADxwPwAYZz8AWGM/
AGBbPwC0Uj8AHE8/AFBIPwA4Qj8AdD0/AGw7PwB4ND8ABDA/ACgvPwCwJD8A7B8/ACAePwAcGD8A
1BQ/ABAQPwB4DD8AyAc/AAwAPwDg+z4AAPo+ANDoPgBI5D4AEOA+AJjaPgCg0j4AwNA+ADDKPgDg
xD4AgLs+ACC8PgCosT4A4Ks+AFCqPgBYoj4A2J8+AIifPgDQmD4AYJU+ABCQPgDIjD4AsIY+AOiF
PgBAez4AcH0+AJBxPgAQbz4AoGY+ADBjPgCwYD4A4F0+AFBSPgBgTD4AIEs+ADBFPgDwPj4A0Ds+
ANA2PgCQNT4AACo+ANAnPgCgJT4A4CY+AMAePgDgIT4AMBg+AIAYPgBAEj4AcBQ+AFAHPgAQCz4A
sAY+AMAFPgCA/D0AoAI+AGD0PQCg+j0AoOs9AIDyPQBA3T0AQOw9ACDaPQAA4T0AwNo9AIDUPQAg
yz0AAMM9
          </values>
        </Ydata>
        <Ydata label="Y" units="UNKNOWN">
          <parameter name="DataTags" label="# of data points with tags" group="YAxis">0</parameter>
          <parameter name="Channel" label="Instrument channel stored" group="YAxis">10</parameter>
          <parameter name="MaxX" label="X-axis of the data set maximum" group="YAxis">351.000000</parameter>
          <parameter name="MaxY" label="Data set maximum" group="YAxis">2.272949</parameter>
          <parameter name="MinX" label="X-axis of the data set minimum" group="YAxis">307.000000</parameter>
          <parameter name="MinY" label="Data set minimum" group="YAxis">0.025635</parameter>
          <parameter name="YaxisType" label="Y-axis type" group="YAxis">0x12 = Intensity</parameter>
          <parameter name="YaxisUnit" label="Y-axis unit" group="YAxis">0x0 = Unknown</parameter>
          <parameter name="YAxisLL" label="Y-axis lower limit" group="YAxis">0.025635</parameter>
          <parameter name="YAxisUL" label="Y-axis upper limit" group="YAxis">2.272949</parameter>
          <parameter name="LabelY" label="Y-axis Label" group="YAxis">Y</parameter>
          <parameter name="AcqStarted" label="Date and Time the acquisition was started" group="Standard">0000-00-00 00:00:00</parameter>
          <parameter name="AcqCompleted" label="Date and Time the acquisition was completed" group="Standard">0000-00-00 00:00:00</parameter>
          <parameter name="ID" label="Acquiring instruments serial (ID) number" group="Standard">0</parameter>
          <parameter name="Instrument" label="Instrument type acquired on" group="Standard">7</parameter>
          <parameter name="Modified" label="Date and Time the acquisition was modified" group="Standard">0000-00-00 00:00:00</parameter>
          <parameter name="OSVersion" label="Operating system version" group="Standard">0.000000</parameter>
          <parameter name="Version" label="Program version (&apos;C&apos;) acquired on" group="Standard">0.000000</parameter>
          <parameter name="Comment" label="Acquisition comment" group="Standard">NULL</parameter>
          <parameter name="AcqTitle" label="Acquisition title" group="Standard">NULL</parameter>
          <parameter name="User" label="User who acquired this data" group="Standard">NULL</parameter>
          <parameter name="Monochromators" label="# of monochromators attached" group="Hardware">2</parameter>
          <parameter name="Pmts" label="# of PMTs attached" group="Hardware">2</parameter>
          <parameter name="Lamp" label="Excitation light source type" group="LAMP">1</parameter>
          <parameter name="LampComment" label="Excitation light source comment" group="LAMP">Default Lamp Comment</parameter>
          <parameter name="MonochromatorAt1" label="Monochromator constant/setup wavelength" group="Monochromator">300.000000</parameter>
          <parameter name="MonochromatorShutter1" label="Monochromator shutter status" group="Monochromator">0</parameter>
          <parameter name="MonochromatorSlitIn1" label="Monochromator input slit width" group="Monochromator">4.000000</parameter>
          <parameter name="MonochromatorSlitOut1" label="Monochromator output slit width" group="Monochromator">4.000000</parameter>
          <parameter name="MonochromatorStepSize1" label="Monochromator step size" group="Monochromator">0.200000</parameter>
          <parameter name="MonochromatorType1" label="Monochromator type (excitation or emission)" group="Monochromator">10</parameter>
          <parameter name="MonochromatorUpperLimit1" label="Monochromator upper limit" group="Monochromator">950.000000</parameter>
          <parameter name="MonochromatorComment1" label="Monochromator comment" group="Monochromator">Default Monochromator Comment</parameter>
          <parameter name="MonochromatorNumFilter1" label="Monochromator # of filters switched" group="Filter">2</parameter>
          <parameter name="MonochromatorFilter1Wavelength1" label="Monochromator constant/setup wavelength" group="Filter">200.000000</parameter>
          <parameter name="MonochromatorFilter1Wavelength2" label="Monochromator constant/setup wavelength" group="Filter">300.000000</parameter>
          <parameter name="MonochromatorFilter1Comment" label="Monochromator filter comment" group="Filter">Default Filter Comment</parameter>
          <parameter name="MonochromatorPolarizerAngle1" label="Monochromator polarization angle" group="Polarizer">0.000000</parameter>
          <parameter name="MonochromatorPolarizerType1" label="Monochromator polarizer type" group="Polarizer">1</parameter>
          <parameter name="MonochromatorPolarizerComment1" label="Monochromator polarizer comment" group="Polarizer">Default Polarizer Comment</parameter>
          <parameter name="MonochromatorAt2" label="Monochromator constant/setup wavelength" group="Monochromator">400.000000</parameter>
          <parameter name="MonochromatorShutter2" label="Monochromator shutter status" group="Monochromator">0</parameter>
          <parameter name="MonochromatorSlitIn2" label="Monochromator input slit width" group="Monochromator">4.000000</parameter>
          <parameter name="MonochromatorSlitOut2" label="Monochromator output slit width" group="Monochromator">4.000000</parameter>
          <parameter name="MonochromatorStepSize2" label="Monochromator step size" group="Monochromator">0.200000</parameter>
          <parameter name="MonochromatorType2" label="Monochromator type (excitation or emission)" group="Monochromator">8</parameter>
          <parameter name="MonochromatorUpperLimit2" label="Monochromator upper limit" group="Monochromator">950.000000</parameter>
          <parameter name="MonochromatorComment2" label="Monochromator comment" group="Monochromator">Default Monochromator Comment</parameter>
          <parameter name="MonochromatorNumFilter2" label="Monochromator # of filters switched" group="Filter">3</parameter>
          <parameter name="MonochromatorFilter2Wavelength1" label="Monochromator constant/setup wavelength" group="Filter">200.000000</parameter>
          <parameter name="MonochromatorFilter2Wavelength2" label="Monochromator constant/setup wavelength" group="Filter">300.000000</parameter>
          <parameter name="MonochromatorFilter2Wavelength3" label="Monochromator constant/setup wavelength" group="Filter">400.000000</parameter>
          <parameter name="MonochromatorFilter2Comment" label="Monochromator filter comment" group="Filter">Default Filter Comment</parameter>
          <parameter name="MonochromatorPolarizerAngle2" label="Monochromator polarization angle" group="Polarizer">90.000000</parameter>
          <parameter name="MonochromatorPolarizerType2" label="Monochromator polarizer type" group="Polarizer">1</parameter>
          <parameter name="MonochromatorPolarizerComment2" label="Monochromator polarizer comment" group="Polarizer">Default Polarizer Comment</parameter>
          <parameter name="PmtConnection1" label="PMT connection" group="PMT">10</parameter>
          <parameter name="PmtGain1" label="PMT gain" group="PMT">1</parameter>
          <parameter name="PmtHighVolt1" label="PMT high voltage" group="PMT">750</parameter>
          <parameter name="PmtOffset1" label="PMT offset status" group="PMT">0</parameter>
          <parameter name="PmtAcquisition1" label="PMT acquisition type" group="PMT">0</parameter>
          <parameter name="PmtComment1" label="PMT comment" group="PMT">Default Pmt Comment</parameter>
          <parameter name="PmtConnection2" label="PMT connection" group="PMT">10</parameter>
          <parameter name="PmtGain2" label="PMT gain" group="PMT">1</parameter>
          <parameter name="PmtHighVolt2" label="PMT high voltage" group="PMT">750</parameter>
          <parameter name="PmtOffset2" label="PMT offset status" group="PMT">0</parameter>
          <parameter name="PmtAcquisition2" label="PMT acquisition type" group="PMT">1</parameter>
          <parameter name="PmtComment2" label="PMT comment" group="PMT">Default Pmt Comment</parameter>
          <parameter name="InstrumentSpecific3" label="Instrument specific information" group="AUX">2</parameter>
          <parameter name="AuxChannelGain1" label="AUX channel gain" group="AUX">0</parameter>
          <parameter name="AuxChannelOffset1" label="AUX channel offset status" group="AUX">0</parameter>
          <parameter name="AuxChannelComment1" label="AUX channel comment" group="AUX">Default Aux Pmt Comment</parameter>
          <parameter name="AuxChannelGain2" label="AUX channel gain" group="AUX">1</parameter>
          <parameter name="AuxChannelOffset2" label="AUX channel offset status" group="AUX">0</parameter>
          <parameter name="AuxChannelComment2" label="AUX channel comment" group="AUX">Default Aux Pmt Comment</parameter>
          <parameter name="PlotInfo" label="Y-axis plotting annotation" group="YAxis">NULL</parameter>
          <parameter name="Corrected" label="Data corrected flag" group="MANIPULATION">0x0</parameter>
          <parameter name="Derivative" label="Degree of derivation" group="MANIPULATION">0</parameter>
          <parameter name="Norm" label="Normalized flag" group="MANIPULATION">0x0</parameter>
          <parameter name="Offset" label="Offset from the original data" group="MANIPULATION">0.000000</parameter>
          <parameter name="Scale" label="Scale from the original data" group="MANIPULATION">1.000000</parameter>
          <parameter name="Smooth" label="# of smoothing passes done" group="MANIPULATION">0</parameter>
          <parameter name="SmoothType" label="Smoothing type" group="MANIPULATION">1.000000</parameter>
          <values byteorder="INTEL" format="FLOAT32" numvalues="201">AIDZPAAA1zwAAPA8AIDUPACA8jwAgNQ8AIDtPAAA0jwAgO08AIDUPAAA8DwAANI8AADwPACA2TwA
gPI8AADcPACA8jwAgNk8AADwPAAA1zwAgPI8AADcPACA8jwAgNk8AID3PAAA3DwAAPo8AADmPAAA
+jwAAOs8AIAEPQCA8jwAQAg9AAAHPQAAGz0AgBM9AMAtPQCAMT0AQFM9AABmPQBglT0AQKs9AGDg
PQCwED4A8FI+APisPgB0FT8AIH0/ADq2PwAQ7z8AUgpAAHgRQACjBEAAguY/AL6+PwCimT8AkIg/
APB6PwBweD8AEHQ/ANByPwDMdj8ASHg/APh3PwD4fD8AoH8/AI6APwDIgj8AeoU/AGyEPwBghj8A
BoY/AJqIPwCchj8AGoY/AJ6JPwBuhz8ACIk/ANaNPwCKkz8AGJc/AISePwC0pT8APKo/AFCvPwDe
sj8AkLA/ACqtPwAorz8A4Ks/AGimPwC6pD8AtqM/ALydPwCymD8AEpg/ACKXPwBokj8A6pI/AJqS
PwCojj8AppA/AFyPPwDoij8A2ok/AGCGPwBGhz8AdH4/ABB+PwBkej8AsG8/AKxkPwBcZD8AyFw/
AHhSPwA8Uj8AVE4/AGRDPwDgPz8A6Dw/AGA4PwBwMj8A+DE/AFAqPwDYJD8AeCU/AHAjPwDsGj8A
pBc/ADgVPwAkED8ARAk/ABgIPwCwAT8AUP8+AJD2PgAw8j4AeOY+AADhPgBI3z4A+Nk+AOjLPgBg
zD4AOMI+AMjDPgDwuT4AgLs+AHCyPgAorz4A0Kw+ADipPgBooT4AMJ0+AACbPgCwlT4AsJA+ACiM
PgBQhz4AmIU+AKiEPgAAfz4AsG8+AABwPgCgcD4AIGk+AMBaPgBQVz4AkE4+AKBSPgBwRj4A8Eg+
AAA+PgAAOT4AoDk+AIA2PgDwLz4AUDQ+AOArPgDwKj4AECQ+AKAgPgCQHD4AIBk+ALAQPgCQFz4A
0A4+AFARPgDwET4A4A0+AMAFPgDQBD4AgPw9AGABPgAA9T0AIPg9AODxPQCA6D0AIOk9AGDgPQAg
0D0A4NM9
          </values>
        </Ydata>
        <Ydata label="Y" units="UNKNOWN">
          <parameter name="DataTags" label="# of data points with tags" group="YAxis">0</parameter>
          <parameter name="Channel" label="Instrument channel stored" group="YAxis">10</parameter>
          <parameter name="MaxX" label="X-axis of the data set maximum" group="YAxis">356.000000</parameter>
          <parameter name="MaxY" label="Data set maximum" group="YAxis">2.053223</parameter>
          <parameter name="MinX" label="X-axis of the data set minimum" group="YAxis">303.000000</parameter>
          <parameter name="MinY" label="Data set minimum" group="YAxis">0.026245</parameter>
          <parameter name="YaxisType" label="Y-axis type" group="YAxis">0x12 = Intensity</parameter>
          <parameter name="YaxisUnit" label="Y-axis unit" group="YAxis">0x0 = Unknown</parameter>
          <parameter name="YAxisLL" label="Y-axis lower limit" group="YAxis">0.026245</parameter>
          <parameter name="YAxisUL" label="Y-axis upper limit" group="YAxis">2.053223</parameter>
          <parameter name="LabelY" label="Y-axis Label" group="YAxis">Y</parameter>
          <parameter name="AcqStarted" label="Date and Time the acquisition was started" group="Standard">0000-00-00 00:00:00</parameter>
          <parameter name="AcqCompleted" label="Date and Time the acquisition was completed" group="Standard">0000-00-00 00:00:00</parameter>
          <parameter name="ID" label="Acquiring instruments serial (ID) number" group="Standard">0</parameter>
          <parameter name="Instrument" label="Instrument type acquired on" group="Standard">7</parameter>
          <parameter name="Modified" label="Date and Time the acquisition was modified" group="Standard">0000-00-00 00:00:00</parameter>
          <parameter name="OSVersion" label="Operating system version" group="Standard">0.000000</parameter>
          <parameter name="Version" label="Program version (&apos;C&apos;) acquired on" group="Standard">0.000000</parameter>
          <parameter name="Comment" label="Acquisition comment" group="Standard">NULL</parameter>
          <parameter name="AcqTitle" label="Acquisition title" group="Standard">NULL</parameter>
          <parameter name="User" label="User who acquired this data" group="Standard">NULL</parameter>
          <parameter name="Monochromators" label="# of monochromators attached" group="Hardware">2</parameter>
          <parameter name="Pmts" label="# of PMTs attached" group="Hardware">2</parameter>
          <parameter name="Lamp" label="Excitation light source type" group="LAMP">1</parameter>
          <parameter name="LampComment" label="Excitation light source comment" group="LAMP">Default Lamp Comment</parameter>
          <parameter name="MonochromatorAt1" label="Monochromator constant/setup wavelength" group="Monochromator">300.000000</parameter>
          <parameter name="MonochromatorShutter1" label="Monochromator shutter status" group="Monochromator">0</parameter>
          <parameter name="MonochromatorSlitIn1" label="Monochromator input slit width" group="Monochromator">4.000000</parameter>
          <parameter name="MonochromatorSlitOut1" label="Monochromator output slit width" group="Monochromator">4.000000</parameter>
          <parameter name="MonochromatorStepSize1" label="Monochromator step size" group="Monochromator">0.200000</parameter>
          <parameter name="MonochromatorType1" label="Monochromator type (excitation or emission)" group="Monochromator">10</parameter>
          <parameter name="MonochromatorUpperLimit1" label="Monochromator upper limit" group="Monochromator">950.000000</parameter>
          <parameter name="MonochromatorComment1" label="Monochromator comment" group="Monochromator">Default Monochromator Comment</parameter>
          <parameter name="MonochromatorNumFilter1" label="Monochromator # of filters switched" group="Filter">2</parameter>
          <parameter name="MonochromatorFilter1Wavelength1" label="Monochromator constant/setup wavelength" group="Filter">200.000000</parameter>
          <parameter name="MonochromatorFilter1Wavelength2" label="Monochromator constant/setup wavelength" group="Filter">300.000000</parameter>
          <parameter name="MonochromatorFilter1Comment" label="Monochromator filter comment" group="Filter">Default Filter Comment</parameter>
          <parameter name="MonochromatorPolarizerAngle1" label="Monochromator polarization angle" group="Polarizer">0.000000</parameter>
          <parameter name="MonochromatorPolarizerType1" label="Monochromator polarizer type" group="Polarizer">1</parameter>
          <parameter name="MonochromatorPolarizerComment1" label="Monochromator polarizer comment" group="Polarizer">Default Polarizer Comment</parameter>
          <parameter name="MonochromatorAt2" label="Monochromator constant/setup wavelength" group="Monochromator">400.000000</parameter>
          <parameter name="MonochromatorShutter2" label="Monochromator shutter status" group="Monochromator">0</parameter>
          <parameter name="MonochromatorSlitIn2" label="Monochromator input slit width" group="Monochromator">4.000000</parameter>
          <parameter name="MonochromatorSlitOut2" label="Monochromator output slit width" group="Monochromator">4.000000</parameter>
          <parameter name="MonochromatorStepSize2" label="Monochromator step size" group="Monochromator">0.200000</parameter>
          <parameter name="MonochromatorType2" label="Monochromator type (excitation or emission)" group="Monochromator">8</parameter>
          <parameter name="MonochromatorUpperLimit2" label="Monochromator upper limit" group="Monochromator">950.000000</parameter>
          <parameter name="MonochromatorComment2" label="Monochromator comment" group="Monochromator">Default Monochromator Comment</parameter>
          <parameter name="MonochromatorNumFilter2" label="Monochromator # of filters switched" group="Filter">3</parameter>
          <parameter name="MonochromatorFilter2Wavelength1" label="Monochromator constant/setup wavelength" group="Filter">200.000000</parameter>
          <parameter name="MonochromatorFilter2Wavelength2" label="Monochromator constant/setup wavelength" group="Filter">300.000000</parameter>
          <parameter name="MonochromatorFilter2Wavelength3" label="Monochromator constant/setup wavelength" group="Filter">400.000000</parameter>
          <parameter name="MonochromatorFilter2Comment" label="Monochromator filter comment" group="Filter">Default Filter Comment</parameter>
          <parameter name="MonochromatorPolarizerAngle2" label="Monochromator polarization angle" group="Polarizer">90.000000</parameter>
          <parameter name="MonochromatorPolarizerType2" label="Monochromator polarizer type" group="Polarizer">1</parameter>
          <parameter name="MonochromatorPolarizerComment2" label="Monochromator polarizer comment" group="Polarizer">Default Polarizer Comment</parameter>
          <parameter name="PmtConnection1" label="PMT connection" group="PMT">10</parameter>
          <parameter name="PmtGain1" label="PMT gain" group="PMT">1</parameter>
          <parameter name="PmtHighVolt1" label="PMT high voltage" group="PMT">750</parameter>
          <parameter name="PmtOffset1" label="PMT offset status" group="PMT">0</parameter>
          <parameter name="PmtAcquisition1" label="PMT acquisition type" group="PMT">0</parameter>
          <parameter name="PmtComment1" label="PMT comment" group="PMT">Default Pmt Comment</parameter>
          <parameter name="PmtConnection2" label="PMT connection" group="PMT">10</parameter>
          <parameter name="PmtGain2" label="PMT gain" group="PMT">1</parameter>
          <parameter name="PmtHighVolt2" label="PMT high voltage" group="PMT">750</parameter>
          <parameter name="PmtOffset2" label="PMT offset status" group="PMT">0</parameter>
          <parameter name="PmtAcquisition2" label="PMT acquisition type" group="PMT">1</parameter>
          <parameter name="PmtComment2" label="PMT comment" group="PMT">Default Pmt Comment</parameter>
          <parameter name="InstrumentSpecific3" label="Instrument specific information" group="AUX">2</parameter>
          <parameter name="AuxChannelGain1" label="AUX channel gain" group="AUX">0</parameter>
          <parameter name="AuxChannelOffset1" label="AUX channel offset status" group="AUX">0</parameter>
          <parameter name="AuxChannelComment1" label="AUX channel comment" group="AUX">Default Aux Pmt Comment</parameter>
          <parameter name="AuxChannelGain2" label="AUX channel gain" group="AUX">1</parameter>
          <parameter name="AuxChannelOffset2" label="AUX channel offset status" group="AUX">0</parameter>
          <parameter name="AuxChannelComment2" label="AUX channel comment" group="AUX">Default Aux Pmt Comment</parameter>
          <parameter name="PlotInfo" label="Y-axis plotting annotation" group="YAxis">NULL</parameter>
          <parameter name="Corrected" label="Data corrected flag" group="MANIPULATION">0x0</parameter>
          <parameter name="Derivative" label="Degree of derivation" group="MANIPULATION">0</parameter>
          <parameter name="Norm" label="Normalized flag" group="MANIPULATION">0x0</parameter>
          <parameter name="Offset" label="Offset from the original data" group="MANIPULATION">0.000000</parameter>
          <parameter name="Scale" label="Scale from the original data" group="MANIPULATION">1.000000</parameter>
          <parameter name="Smooth" label="# of smoothing passes done" group="MANIPULATION">0</parameter>
          <parameter name="SmoothType" label="Smoothing type" group="MANIPULATION">1.000000</parameter>
          <values byteorder="INTEL" format="FLOAT32" numvalues="201">AIDjPACA4zwAgPI8AADXPACA8jwAANc8AIDtPACA2TwAAPA8AADcPACA8jwAgNk8AAD1PAAA3DwA
gPI8AADXPACA7TwAANc8AADwPAAA3DwAAPo8AIDePAAA8DwAANc8AADwPACA2TwAAPU8AADcPAAA
+jwAgOM8AID8PAAA3DwAwAA9AIDoPADAAD0AgO08AEADPQAA+jwAAAw9AMAPPQAAID0AQCE9AEA1
PQAATT0AgGM9AOCIPQAAoD0A4MQ9AFACPgCwOD4AsKQ+AEgPPwBscj8AvrQ/APDmPwBNAEAAaANA
AMznPwDEwj8AUJY/AJRtPwCcPT8A1C0/AGQlPwCIJD8AgB0/AKwePwAIHT8AUBs/AOwfPwCwJD8A
OCQ/ANAsPwAkOD8AhDw/ADBAPwCMSD8ARE8/AHxTPwC0Vz8ADGQ/APRsPwB4dT8ACoI/APCHPwAM
ij8ARow/AGqLPwB2iT8AJoQ/AP6DPwDIgj8AsIE/AKCCPwDkhD8A2IE/ADCEPwDggz8A3II/AHSB
PwBqgT8AeII/AIx/PwDYfj8AgHw/AIx1PwB0bz8ArG4/AIRuPwCsZD8A7GA/APRdPwA0VT8ACEo/
ADhMPwDQRT8AWEA/AKQ/PwDAPD8AlDY/APwyPwAgMj8AJC4/AMAoPwBYJz8AbCI/AMQfPwDoGT8A
+Bg/ALgXPwC8Dj8ADA8/ADQKPwCQAz8AKAI/AFj8PgAw9z4A+O0+AFDrPgCQ4j4AMN4+AKjZPgDY
1j4AAM0+AJjGPgAYyT4AaL8+AGC4PgBItz4AcLI+APisPgCQpj4AuKY+ADCiPgBImT4A4Jc+ABiX
PgCQjT4AoIw+ADCJPgBIhT4AAII+AEiAPgCwdD4AsG8+ADBtPgBQaz4AkGI+ACBaPgDAUD4AYFY+
ANBKPgDQRT4AAEM+AIBAPgCAOz4AQDo+AIAxPgCgLz4A8Co+ADAnPgAQHz4A4CE+AFAbPgBAFz4A
EBU+ALAVPgDwET4AMA4+ABAGPgAQCz4A8Ac+AMAFPgBg+T0AQAM+AIDtPQAA8D0AQOw9AEDiPQCA
1D0AgNk9
          </values>
        </Ydata>
        <Ydata label="Y" units="UNKNOWN">
          <parameter name="DataTags" label="# of data points with tags" group="YAxis">0</parameter>
          <parameter name="Channel" label="Instrument channel stored" group="YAxis">10</parameter>
          <parameter name="MaxX" label="X-axis of the data set maximum" group="YAxis">361.000000</parameter>
          <parameter name="MaxY" label="Data set maximum" group="YAxis">1.529236</parameter>
          <parameter name="MinX" label="X-axis of the data set minimum" group="YAxis">303.000000</parameter>
          <parameter name="MinY" label="Data set minimum" group="YAxis">0.025635</parameter>
          <parameter name="YaxisType" label="Y-axis type" group="YAxis">0x12 = Intensity</parameter>
          <parameter name="YaxisUnit" label="Y-axis unit" group="YAxis">0x0 = Unknown</parameter>
          <parameter name="YAxisLL" label="Y-axis lower limit" group="YAxis">0.025635</parameter>
          <parameter name="YAxisUL" label="Y-axis upper limit" group="YAxis">1.529236</parameter>
          <parameter name="LabelY" label="Y-axis Label" group="YAxis">Y</parameter>
          <parameter name="AcqStarted" label="Date and Time the acquisition was started" group="Standard">0000-00-00 00:00:00</parameter>
          <parameter name="AcqCompleted" label="Date and Time the acquisition was completed" group="Standard">0000-00-00 00:00:00</parameter>
          <parameter name="ID" label="Acquiring instruments serial (ID) number" group="Standard">0</parameter>
          <parameter name="Instrument" label="Instrument type acquired on" group="Standard">7</parameter>
          <parameter name="Modified" label="Date and Time the acquisition was modified" group="Standard">0000-00-00 00:00:00</parameter>
          <parameter name="OSVersion" label="Operating system version" group="Standard">0.000000</parameter>
          <parameter name="Version" label="Program version (&apos;C&apos;) acquired on" group="Standard">0.000000</parameter>
          <parameter name="Comment" label="Acquisition comment" group="Standard">NULL</parameter>
          <parameter name="AcqTitle" label="Acquisition title" group="Standard">NULL</parameter>
          <parameter name="User" label="User who acquired this data" group="Standard">NULL</parameter>
          <parameter name="Monochromators" label="# of monochromators attached" group="Hardware">2</parameter>
          <parameter name="Pmts" label="# of PMTs attached" group="Hardware">2</parameter>
          <parameter name="Lamp" label="Excitation light source type" group="LAMP">1</parameter>
          <parameter name="LampComment" label="Excitation light source comment" group="LAMP">Default Lamp Comment</parameter>
          <parameter name="MonochromatorAt1" label="Monochromator constant/setup wavelength" group="Monochromator">300.000000</parameter>
          <parameter name="MonochromatorShutter1" label="Monochromator shutter status" group="Monochromator">0</parameter>
          <parameter name="MonochromatorSlitIn1" label="Monochromator input slit width" group="Monochromator">4.000000</parameter>
          <parameter name="MonochromatorSlitOut1" label="Monochromator output slit width" group="Monochromator">4.000000</parameter>
          <parameter name="MonochromatorStepSize1" label="Monochromator step size" group="Monochromator">0.200000</parameter>
          <parameter name="MonochromatorType1" label="Monochromator type (excitation or emission)" group="Monochromator">10</parameter>
          <parameter name="MonochromatorUpperLimit1" label="Monochromator upper limit" group="Monochromator">950.000000</parameter>
          <parameter name="MonochromatorComment1" label="Monochromator comment" group="Monochromator">Default Monochromator Comment</parameter>
          <parameter name="MonochromatorNumFilter1" label="Monochromator # of filters switched" group="Filter">2</parameter>
          <parameter name="MonochromatorFilter1Wavelength1" label="Monochromator constant/setup wavelength" group="Filter">200.000000</parameter>
          <parameter name="MonochromatorFilter1Wavelength2" label="Monochromator constant/setup wavelength" group="Filter">300.000000</parameter>
          <parameter name="MonochromatorFilter1Comment" label="Monochromator filter comment" group="Filter">Default Filter Comment</parameter>
          <parameter name="MonochromatorPolarizerAngle1" label="Monochromator polarization angle" group="Polarizer">0.000000</parameter>
          <parameter name="MonochromatorPolarizerType1" label="Monochromator polarizer type" group="Polarizer">1</parameter>
          <parameter name="MonochromatorPolarizerComment1" label="Monochromator polarizer comment" group="Polarizer">Default Polarizer Comment</parameter>
          <parameter name="MonochromatorAt2" label="Monochromator constant/setup wavelength" group="Monochromator">400.000000</parameter>
          <parameter name="MonochromatorShutter2" label="Monochromator shutter status" group="Monochromator">0</parameter>
          <parameter name="MonochromatorSlitIn2" label="Monochromator input slit width" group="Monochromator">4.000000</parameter>
          <parameter name="MonochromatorSlitOut2" label="Monochromator output slit width" group="Monochromator">4.000000</parameter>
          <parameter name="MonochromatorStepSize2" label="Monochromator step size" group="Monochromator">0.200000</parameter>
          <parameter name="MonochromatorType2" label="Monochromator type (excitation or emission)" group="Monochromator">8</parameter>
          <parameter name="MonochromatorUpperLimit2" label="Monochromator upper limit" group="Monochromator">950.000000</parameter>
          <parameter name="MonochromatorComment2" label="Monochromator comment" group="Monochromator">Default Monochromator Comment</parameter>
          <parameter name="MonochromatorNumFilter2" label="Monochromator # of filters switched" group="Filter">3</parameter>
          <parameter name="MonochromatorFilter2Wavelength1" label="Monochromator constant/setup wavelength" group="Filter">200.000000</parameter>
          <parameter name="MonochromatorFilter2Wavelength2" label="Monochromator constant/setup wavelength" group="Filter">300.000000</parameter>
          <parameter name="MonochromatorFilter2Wavelength3" label="Monochromator constant/setup wavelength" group="Filter">400.000000</parameter>
          <parameter name="MonochromatorFilter2Comment" label="Monochromator filter comment" group="Filter">Default Filter Comment</parameter>
          <parameter name="MonochromatorPolarizerAngle2" label="Monochromator polarization angle" group="Polarizer">90.000000</parameter>
          <parameter name="MonochromatorPolarizerType2" label="Monochromator polarizer type" group="Polarizer">1</parameter>
          <parameter name="MonochromatorPolarizerComment2" label="Monochromator polarizer comment" group="Polarizer">Default Polarizer Comment</parameter>
          <parameter name="PmtConnection1" label="PMT connection" group="PMT">10</parameter>
          <parameter name="PmtGain1" label="PMT gain" group="PMT">1</parameter>
          <parameter name="PmtHighVolt1" label="PMT high voltage" group="PMT">750</parameter>
          <parameter name="PmtOffset1" label="PMT offset status" group="PMT">0</parameter>
          <parameter name="PmtAcquisition1" label="PMT acquisition type" group="PMT">0</parameter>
          <parameter name="PmtComment1" label="PMT comment" group="PMT">Default Pmt Comment</parameter>
          <parameter name="PmtConnection2" label="PMT connection" group="PMT">10</parameter>
          <parameter name="PmtGain2" label="PMT gain" group="PMT">1</parameter>
          <parameter name="PmtHighVolt2" label="PMT high voltage" group="PMT">750</parameter>
          <parameter name="PmtOffset2" label="PMT offset status" group="PMT">0</parameter>
          <parameter name="PmtAcquisition2" label="PMT acquisition type" group="PMT">1</parameter>
          <parameter name="PmtComment2" label="PMT comment" group="PMT">Default Pmt Comment</parameter>
          <parameter name="InstrumentSpecific3" label="Instrument specific information" group="AUX">2</parameter>
          <parameter name="AuxChannelGain1" label="AUX channel gain" group="AUX">0</parameter>
          <parameter name="AuxChannelOffset1" label="AUX channel offset status" group="AUX">0</parameter>
          <parameter name="AuxChannelComment1" label="AUX channel comment" group="AUX">Default Aux Pmt Comment</parameter>
          <parameter name="AuxChannelGain2" label="AUX channel gain" group="AUX">1</parameter>
          <parameter name="AuxChannelOffset2" label="AUX channel offset status" group="AUX">0</parameter>
          <parameter name="AuxChannelComment2" label="AUX channel comment" group="AUX">Default Aux Pmt Comment</parameter>
          <parameter name="PlotInfo" label="Y-axis plotting annotation" group="YAxis">NULL</parameter>
          <parameter name="Corrected" label="Data corrected flag" group="MANIPULATION">0x0</parameter>
          <parameter name="Derivative" label="Degree of derivation" group="MANIPULATION">0</parameter>
          <parameter name="Norm" label="Normalized flag" group="MANIPULATION">0x0</parameter>
          <parameter name="Offset" label="Offset from the original data" group="MANIPULATION">0.000000</parameter>
          <parameter name="Scale" label="Scale from the original data" group="MANIPULATION">1.000000</parameter>
          <parameter name="Smooth" label="# of smoothing passes done" group="MANIPULATION">0</parameter>
          <parameter name="SmoothType" label="Smoothing type" group="MANIPULATION">1.000000</parameter>
          <values byteorder="INTEL" format="FLOAT32" numvalues="201">AADXPACA2TwAgO08AADSPAAA8DwAANI8AADwPACA1DwAAPA8AIDUPAAA8DwAANI8AIDtPACA1DwA
APA8AIDUPACA7TwAgNQ8AAD1PAAA1zwAgO08AIDZPACA8jwAgNQ8AAD1PAAA1zwAgPI8AADcPACA
8jwAANw8AIDyPAAA3DwAAPU8AADhPAAA+jwAgN48AAD1PACA4zwAwAA9AIDtPADABT0AAPU8AMAK
PQAA+jwAQBI9AEANPQCAIj0AwCM9AABDPQBAST0AQHs9AKCMPQAArz0AgMo9AOASPgAQgT4A0OM+
AAA+PwDuiT8Aqq8/ADLDPwC+wz8Aaq4/AI6KPwCIUT8AgBg/AHj1PgA40T4AeMg+AODJPgBYwD4A
OMc+AMDGPgAAzT4AuNM+AGjdPgAA6z4AePA+AJwBPwBcCj8AkBI/AEAcPwAEJj8AIDI/AMw6PwBA
RD8A3E0/AGRNPwCoTz8ADFA/AJBOPwBAST8AfEk/ANRGPwA8Qz8AVEQ/AMRMPwAcTz8A+Eo/AHhN
PwBoUz8AFFI/ADBUPwDsVj8A6FU/AOhVPwBUUz8ArFU/AERPPwDkSj8A7Ew/AJBEPwAIQD8A4D8/
AAw8PwAAND8AWDY/AHQuPwD4LD8AdCQ/AKQmPwCMJT8AzCE/AHwcPwDkHT8AbBg/AGAVPwDEED8A
sBA/ANQKPwC0Bz8AFAc/APgEPwAo/z4AKP8+ABDvPgBQ8D4AsOU+AKjjPgBg2z4AaNg+ANjRPgDA
0D4AiMc+AHjIPgAowz4AQLo+AKC5PgCYtz4A2K4+AJCmPgCwpD4AYJ8+ALicPgDwmz4A4Jc+AICO
PgDwkT4AwI8+ANCEPgAwhD4A4IM+AJB7PgBgbz4AsHQ+AEBsPgAAZj4AMF4+AFBhPgDgWD4AUE0+
ACBLPgCART4AAD4+ABA9PgBwNz4AUDk+ABApPgDALT4AECQ+APAgPgCQHD4AgCI+ALAVPgDQGD4A
sBU+ALAVPgCQAz4AsAs+AGAGPgBgBj4AsAE+AIAEPgDA+D0AIPg9AMDzPQBA9j0AgOg9AGDlPQAg
3z0AgNQ9
          </values>
        </Ydata>
        <Ydata label="Y" units="UNKNOWN">
          <parameter name="DataTags" label="# of data points with tags" group="YAxis">0</parameter>
          <parameter name="Channel" label="Instrument channel stored" group="YAxis">10</parameter>
          <parameter name="MaxX" label="X-axis of the data set maximum" group="YAxis">365.000000</parameter>
          <parameter name="MaxY" label="Data set maximum" group="YAxis">1.303711</parameter>
          <parameter name="MinX" label="X-axis of the data set minimum" group="YAxis">303.000000</parameter>
          <parameter name="MinY" label="Data set minimum" group="YAxis">0.026245</parameter>
          <parameter name="YaxisType" label="Y-axis type" group="YAxis">0x12 = Intensity</parameter>
          <parameter name="YaxisUnit" label="Y-axis unit" group="YAxis">0x0 = Unknown</parameter>
          <parameter name="YAxisLL" label="Y-axis lower limit" group="YAxis">0.026245</parameter>
          <parameter name="YAxisUL" label="Y-axis upper limit" group="YAxis">1.303711</parameter>
          <parameter name="LabelY" label="Y-axis Label" group="YAxis">Y</parameter>
          <parameter name="AcqStarted" label="Date and Time the acquisition was started" group="Standard">0000-00-00 00:00:00</parameter>
          <parameter name="AcqCompleted" label="Date and Time the acquisition was completed" group="Standard">0000-00-00 00:00:00</parameter>
          <parameter name="ID" label="Acquiring instruments serial (ID) number" group="Standard">0</parameter>
          <parameter name="Instrument" label="Instrument type acquired on" group="Standard">7</parameter>
          <parameter name="Modified" label="Date and Time the acquisition was modified" group="Standard">0000-00-00 00:00:00</parameter>
          <parameter name="OSVersion" label="Operating system version" group="Standard">0.000000</parameter>
          <parameter name="Version" label="Program version (&apos;C&apos;) acquired on" group="Standard">0.000000</parameter>
          <parameter name="Comment" label="Acquisition comment" group="Standard">NULL</parameter>
          <parameter name="AcqTitle" label="Acquisition title" group="Standard">NULL</parameter>
          <parameter name="User" label="User who acquired this data" group="Standard">NULL</parameter>
          <parameter name="Monochromators" label="# of monochromators attached" group="Hardware">2</parameter>
          <parameter name="Pmts" label="# of PMTs attached" group="Hardware">2</parameter>
          <parameter name="Lamp" label="Excitation light source type" group="LAMP">1</parameter>
          <parameter name="LampComment" label="Excitation light source comment" group="LAMP">Default Lamp Comment</parameter>
          <parameter name="MonochromatorAt1" label="Monochromator constant/setup wavelength" group="Monochromator">300.000000</parameter>
          <parameter name="MonochromatorShutter1" label="Monochromator shutter status" group="Monochromator">0</parameter>
          <parameter name="MonochromatorSlitIn1" label="Monochromator input slit width" group="Monochromator">4.000000</parameter>
          <parameter name="MonochromatorSlitOut1" label="Monochromator output slit width" group="Monochromator">4.000000</parameter>
          <parameter name="MonochromatorStepSize1" label="Monochromator step size" group="Monochromator">0.200000</parameter>
          <parameter name="MonochromatorType1" label="Monochromator type (excitation or emission)" group="Monochromator">10</parameter>
          <parameter name="MonochromatorUpperLimit1" label="Monochromator upper limit" group="Monochromator">950.000000</parameter>
          <parameter name="MonochromatorComment1" label="Monochromator comment" group="Monochromator">Default Monochromator Comment</parameter>
          <parameter name="MonochromatorNumFilter1" label="Monochromator # of filters switched" group="Filter">2</parameter>
          <parameter name="MonochromatorFilter1Wavelength1" label="Monochromator constant/setup wavelength" group="Filter">200.000000</parameter>
          <parameter name="MonochromatorFilter1Wavelength2" label="Monochromator constant/setup wavelength" group="Filter">300.000000</parameter>
          <parameter name="MonochromatorFilter1Comment" label="Monochromator filter comment" group="Filter">Default Filter Comment</parameter>
          <parameter name="MonochromatorPolarizerAngle1" label="Monochromator polarization angle" group="Polarizer">0.000000</parameter>
          <parameter name="MonochromatorPolarizerType1" label="Monochromator polarizer type" group="Polarizer">1</parameter>
          <parameter name="MonochromatorPolarizerComment1" label="Monochromator polarizer comment" group="Polarizer">Default Polarizer Comment</parameter>
          <parameter name="MonochromatorAt2" label="Monochromator constant/setup wavelength" group="Monochromator">400.000000</parameter>
          <parameter name="MonochromatorShutter2" label="Monochromator shutter status" group="Monochromator">0</parameter>
          <parameter name="MonochromatorSlitIn2" label="Monochromator input slit width" group="Monochromator">4.000000</parameter>
          <parameter name="MonochromatorSlitOut2" label="Monochromator output slit width" group="Monochromator">4.000000</parameter>
          <parameter name="MonochromatorStepSize2" label="Monochromator step size" group="Monochromator">0.200000</parameter>
          <parameter name="MonochromatorType2" label="Monochromator type (excitation or emission)" group="Monochromator">8</parameter>
          <parameter name="MonochromatorUpperLimit2" label="Monochromator upper limit" group="Monochromator">950.000000</parameter>
          <parameter name="MonochromatorComment2" label="Monochromator comment" group="Monochromator">Default Monochromator Comment</parameter>
          <parameter name="MonochromatorNumFilter2" label="Monochromator # of filters switched" group="Filter">3</parameter>
          <parameter name="MonochromatorFilter2Wavelength1" label="Monochromator constant/setup wavelength" group="Filter">200.000000</parameter>
          <parameter name="MonochromatorFilter2Wavelength2" label="Monochromator constant/setup wavelength" group="Filter">300.000000</parameter>
          <parameter name="MonochromatorFilter2Wavelength3" label="Monochromator constant/setup wavelength" group="Filter">400.000000</parameter>
          <parameter name="MonochromatorFilter2Comment" label="Monochromator filter comment" group="Filter">Default Filter Comment</parameter>
          <parameter name="MonochromatorPolarizerAngle2" label="Monochromator polarization angle" group="Polarizer">90.000000</parameter>
          <parameter name="MonochromatorPolarizerType2" label="Monochromator polarizer type" group="Polarizer">1</parameter>
          <parameter name="MonochromatorPolarizerComment2" label="Monochromator polarizer comment" group="Polarizer">Default Polarizer Comment</parameter>
          <parameter name="PmtConnection1" label="PMT connection" group="PMT">10</parameter>
          <parameter name="PmtGain1" label="PMT gain" group="PMT">1</parameter>
          <parameter name="PmtHighVolt1" label="PMT high voltage" group="PMT">750</parameter>
          <parameter name="PmtOffset1" label="PMT offset status" group="PMT">0</parameter>
          <parameter name="PmtAcquisition1" label="PMT acquisition type" group="PMT">0</parameter>
          <parameter name="PmtComment1" label="PMT comment" group="PMT">Default Pmt Comment</parameter>
          <parameter name="PmtConnection2" label="PMT connection" group="PMT">10</parameter>
          <parameter name="PmtGain2" label="PMT gain" group="PMT">1</parameter>
          <parameter name="PmtHighVolt2" label="PMT high voltage" group="PMT">750</parameter>
          <parameter name="PmtOffset2" label="PMT offset status" group="PMT">0</parameter>
          <parameter name="PmtAcquisition2" label="PMT acquisition type" group="PMT">1</parameter>
          <parameter name="PmtComment2" label="PMT comment" group="PMT">Default Pmt Comment</parameter>
          <parameter name="InstrumentSpecific3" label="Instrument specific information" group="AUX">2</parameter>
          <parameter name="AuxChannelGain1" label="AUX channel gain" group="AUX">0</parameter>
          <parameter name="AuxChannelOffset1" label="AUX channel offset status" group="AUX">0</parameter>
          <parameter name="AuxChannelComment1" label="AUX channel comment" group="AUX">Default Aux Pmt Comment</parameter>
          <parameter name="AuxChannelGain2" label="AUX channel gain" group="AUX">1</parameter>
          <parameter name="AuxChannelOffset2" label="AUX channel offset status" group="AUX">0</parameter>
          <parameter name="AuxChannelComment2" label="AUX channel comment" group="AUX">Default Aux Pmt Comment</parameter>
          <parameter name="PlotInfo" label="Y-axis plotting annotation" group="YAxis">NULL</parameter>
          <parameter name="Corrected" label="Data corrected flag" group="MANIPULATION">0x0</parameter>
          <parameter name="Derivative" label="Degree of derivation" group="MANIPULATION">0</parameter>
          <parameter name="Norm" label="Normalized flag" group="MANIPULATION">0x0</parameter>
          <parameter name="Offset" label="Offset from the original data" group="MANIPULATION">0.000000</parameter>
          <parameter name="Scale" label="Scale from the original data" group="MANIPULATION">1.000000</parameter>
          <parameter name="Smooth" label="# of smoothing passes done" group="MANIPULATION">0</parameter>
          <parameter name="SmoothType" label="Smoothing type" group="MANIPULATION">1.000000</parameter>
          <values byteorder="INTEL" format="FLOAT32" numvalues="201">AIDjPAAA3DwAgPI8AADXPAAA9TwAgNk8AADrPAAA1zwAgO08AADXPAAA8DwAANc8AIDyPACA3jwA
gO08AADcPACA7TwAgN48AIDtPAAA3DwAgPI8AADcPACA7TwAgNk8AIDtPAAA1zwAgPI8AADcPACA
8jwAgN48AIDyPACA3jwAAPA8AADhPACA9zwAANw8AAD1PACA4zwAgPc8AADhPACA9zwAgOg8AID3
PAAA6zwAwAA9AAD1PACABD0AwAA9AEAIPQAAAj0AABE9AEASPQDAIz0AQCY9AIBAPQCATz0AQHE9
AKCHPQBApj0AoOY9APBIPgAovj4ACB0/AKhjPwDyjz8A4KY/ACilPwAkiz8A4F0/AHggPwDg5z4A
qKI+ALCLPgAQfj4AoII+AOCDPgAYiD4A4I0+AHCUPgCQnD4AuKY+AIizPgCAxT4AoNc+AJjuPgDw
/z4A9Ag/APwPPwD8FD8AVBc/AIgaPwCUGD8AhB4/AIQePwCUHT8ABBw/AKgnPwCIJD8AYCQ/ADgk
PwBAJj8ALCY/AAgnPwBMKT8AxCk/ALApPwAIMT8AiC4/AKAqPwCsLT8ADC0/AMwrPwDoKD8AHCc/
ACwmPwAoID8AdB8/AOgePwAwHT8AdBo/ACAZPwDsFT8AEBA/ADgQPwDIDD8AMAk/AFAHPwAYCD8A
2AE/AOj9PgAY+z4AQPY+APDrPgAQ6j4A0OM+AIjgPgA42z4AiNY+AMjXPgC4zj4ASNA+ACjIPgCA
xT4AKL4+AOi8PgBAuj4A2LM+ACivPgBIrT4AgKI+AKCgPgBQoD4AyJs+AECXPgDIkT4A0I4+ABiN
PgCAhD4AkIM+ALB+PgAgeD4AIHM+AHBuPgCgZj4AgGM+AJBiPgBAYj4AoFI+AMBVPgBwSz4AIEE+
ACBBPgAQQj4AMDY+ACA3PgBwLT4AsDM+ANAiPgCAJz4AwB4+AKAgPgCgGz4AkBc+ANAYPgBgED4A
0A4+AGALPgAQBj4AoAc+ACAAPgCwAT4A4Ps9AAD6PQAA8D0AoPA9AADrPQDA6T0AoOE9AKDcPQBA
0z0AAM09
          </values>
        </Ydata>
        <Ydata label="Y" units="UNKNOWN">
          <parameter name="DataTags" label="# of data points with tags" group="YAxis">0</parameter>
          <parameter name="Channel" label="Instrument channel stored" group="YAxis">10</parameter>
          <parameter name="MaxX" label="X-axis of the data set maximum" group="YAxis">371.000000</parameter>
          <parameter name="MaxY" label="Data set maximum" group="YAxis">0.816956</parameter>
          <parameter name="MinX" label="X-axis of the data set minimum" group="YAxis">305.000000</parameter>
          <parameter name="MinY" label="Data set minimum" group="YAxis">0.025024</parameter>
          <parameter name="YaxisType" label="Y-axis type" group="YAxis">0x12 = Intensity</parameter>
          <parameter name="YaxisUnit" label="Y-axis unit" group="YAxis">0x0 = Unknown</parameter>
          <parameter name="YAxisLL" label="Y-axis lower limit" group="YAxis">0.025024</parameter>
          <parameter name="YAxisUL" label="Y-axis upper limit" group="YAxis">0.816956</parameter>
          <parameter name="LabelY" label="Y-axis Label" group="YAxis">Y</parameter>
          <parameter name="AcqStarted" label="Date and Time the acquisition was started" group="Standard">0000-00-00 00:00:00</parameter>
          <parameter name="AcqCompleted" label="Date and Time the acquisition was completed" group="Standard">0000-00-00 00:00:00</parameter>
          <parameter name="ID" label="Acquiring instruments serial (ID) number" group="Standard">0</parameter>
          <parameter name="Instrument" label="Instrument type acquired on" group="Standard">7</parameter>
          <parameter name="Modified" label="Date and Time the acquisition was modified" group="Standard">0000-00-00 00:00:00</parameter>
          <parameter name="OSVersion" label="Operating system version" group="Standard">0.000000</parameter>
          <parameter name="Version" label="Program version (&apos;C&apos;) acquired on" group="Standard">0.000000</parameter>
          <parameter name="Comment" label="Acquisition comment" group="Standard">NULL</parameter>
          <parameter name="AcqTitle" label="Acquisition title" group="Standard">NULL</parameter>
          <parameter name="User" label="User who acquired this data" group="Standard">NULL</parameter>
          <parameter name="Monochromators" label="# of monochromators attached" group="Hardware">2</parameter>
          <parameter name="Pmts" label="# of PMTs attached" group="Hardware">2</parameter>
          <parameter name="Lamp" label="Excitation light source type" group="LAMP">1</parameter>
          <parameter name="LampComment" label="Excitation light source comment" group="LAMP">Default Lamp Comment</parameter>
          <parameter name="MonochromatorAt1" label="Monochromator constant/setup wavelength" group="Monochromator">300.000000</parameter>
          <parameter name="MonochromatorShutter1" label="Monochromator shutter status" group="Monochromator">0</parameter>
          <parameter name="MonochromatorSlitIn1" label="Monochromator input slit width" group="Monochromator">4.000000</parameter>
          <parameter name="MonochromatorSlitOut1" label="Monochromator output slit width" group="Monochromator">4.000000</parameter>
          <parameter name="MonochromatorStepSize1" label="Monochromator step size" group="Monochromator">0.200000</parameter>
          <parameter name="MonochromatorType1" label="Monochromator type (excitation or emission)" group="Monochromator">10</parameter>
          <parameter name="MonochromatorUpperLimit1" label="Monochromator upper limit" group="Monochromator">950.000000</parameter>
          <parameter name="MonochromatorComment1" label="Monochromator comment" group="Monochromator">Default Monochromator Comment</parameter>
          <parameter name="MonochromatorNumFilter1" label="Monochromator # of filters switched" group="Filter">2</parameter>
          <parameter name="MonochromatorFilter1Wavelength1" label="Monochromator constant/setup wavelength" group="Filter">200.000000</parameter>
          <parameter name="MonochromatorFilter1Wavelength2" label="Monochromator constant/setup wavelength" group="Filter">300.000000</parameter>
          <parameter name="MonochromatorFilter1Comment" label="Monochromator filter comment" group="Filter">Default Filter Comment</parameter>
          <parameter name="MonochromatorPolarizerAngle1" label="Monochromator polarization angle" group="Polarizer">0.000000</parameter>
          <parameter name="MonochromatorPolarizerType1" label="Monochromator polarizer type" group="Polarizer">1</parameter>
          <parameter name="MonochromatorPolarizerComment1" label="Monochromator polarizer comment" group="Polarizer">Default Polarizer Comment</parameter>
          <parameter name="MonochromatorAt2" label="Monochromator constant/setup wavelength" group="Monochromator">400.000000</parameter>
          <parameter name="MonochromatorShutter2" label="Monochromator shutter status" group="Monochromator">0</parameter>
          <parameter name="MonochromatorSlitIn2" label="Monochromator input slit width" group="Monochromator">4.000000</parameter>
          <parameter name="MonochromatorSlitOut2" label="Monochromator output slit width" group="Monochromator">4.000000</parameter>
          <parameter name="MonochromatorStepSize2" label="Monochromator step size" group="Monochromator">0.200000</parameter>
          <parameter name="MonochromatorType2" label="Monochromator type (excitation or emission)" group="Monochromator">8</parameter>
          <parameter name="MonochromatorUpperLimit2" label="Monochromator upper limit" group="Monochromator">950.000000</parameter>
          <parameter name="MonochromatorComment2" label="Monochromator comment" group="Monochromator">Default Monochromator Comment</parameter>
          <parameter name="MonochromatorNumFilter2" label="Monochromator # of filters switched" group="Filter">3</parameter>
          <parameter name="MonochromatorFilter2Wavelength1" label="Monochromator constant/setup wavelength" group="Filter">200.000000</parameter>
          <parameter name="MonochromatorFilter2Wavelength2" label="Monochromator constant/setup wavelength" group="Filter">300.000000</parameter>
          <parameter name="MonochromatorFilter2Wavelength3" label="Monochromator constant/setup wavelength" group="Filter">400.000000</parameter>
          <parameter name="MonochromatorFilter2Comment" label="Monochromator filter comment" group="Filter">Default Filter Comment</parameter>
          <parameter name="MonochromatorPolarizerAngle2" label="Monochromator polarization angle" group="Polarizer">90.000000</parameter>
          <parameter name="MonochromatorPolarizerType2" label="Monochromator polarizer type" group="Polarizer">1</parameter>
          <parameter name="MonochromatorPolarizerComment2" label="Monochromator polarizer comment" group="Polarizer">Default Polarizer Comment</parameter>
          <parameter name="PmtConnection1" label="PMT connection" group="PMT">10</parameter>
          <parameter name="PmtGain1" label="PMT gain" group="PMT">1</parameter>
          <parameter name="PmtHighVolt1" label="PMT high voltage" group="PMT">750</parameter>
          <parameter name="PmtOffset1" label="PMT offset status" group="PMT">0</parameter>
          <parameter name="PmtAcquisition1" label="PMT acquisition type" group="PMT">0</parameter>
          <parameter name="PmtComment1" label="PMT comment" group="PMT">Default Pmt Comment</parameter>
          <parameter name="PmtConnection2" label="PMT connection" group="PMT">10</parameter>
          <parameter name="PmtGain2" label="PMT gain" group="PMT">1</parameter>
          <parameter name="PmtHighVolt2" label="PMT high voltage" group="PMT">750</parameter>
          <parameter name="PmtOffset2" label="PMT offset status" group="PMT">0</parameter>
          <parameter name="PmtAcquisition2" label="PMT acquisition type" group="PMT">1</parameter>
          <parameter name="PmtComment2" label="PMT comment" group="PMT">Default Pmt Comment</parameter>
          <parameter name="InstrumentSpecific3" label="Instrument specific information" group="AUX">2</parameter>
          <parameter name="AuxChannelGain1" label="AUX channel gain" group="AUX">0</parameter>
          <parameter name="AuxChannelOffset1" label="AUX channel offset status" group="AUX">0</parameter>
          <parameter name="AuxChannelComment1" label="AUX channel comment" group="AUX">Default Aux Pmt Comment</parameter>
          <parameter name="AuxChannelGain2" label="AUX channel gain" group="AUX">1</parameter>
          <parameter name="AuxChannelOffset2" label="AUX channel offset status" group="AUX">0</parameter>
          <parameter name="AuxChannelComment2" label="AUX channel comment" group="AUX">Default Aux Pmt Comment</parameter>
          <parameter name="PlotInfo" label="Y-axis plotting annotation" group="YAxis">NULL</parameter>
          <parameter name="Corrected" label="Data corrected flag" group="MANIPULATION">0x0</parameter>
          <parameter name="Derivative" label="Degree of derivation" group="MANIPULATION">0</parameter>
          <parameter name="Norm" label="Normalized flag" group="MANIPULATION">0x0</parameter>
          <parameter name="Offset" label="Offset from the original data" group="MANIPULATION">0.000000</parameter>
          <parameter name="Scale" label="Scale from the original data" group="MANIPULATION">1.000000</parameter>
          <parameter name="Smooth" label="# of smoothing passes done" group="MANIPULATION">0</parameter>
          <parameter name="SmoothType" label="Smoothing type" group="MANIPULATION">1.000000</parameter>
          <values byteorder="INTEL" format="FLOAT32" numvalues="201">AIDZPAAA1zwAAOs8AIDUPAAA8DwAAM08AIDtPAAA0jwAAPA8AADSPACA7TwAgNQ8AADwPACA1DwA
APU8AIDUPACA8jwAANI8AIDtPAAA0jwAAPU8AIDUPAAA8DwAANI8AIDyPAAA0jwAgPI8AIDZPAAA
8DwAgNk8AIDyPAAA1zwAAPA8AIDePAAA9TwAANw8AADwPAAA3DwAgPc8AIDePAAA9TwAgNk8AADw
PAAA3DwAAPo8AIDjPACA/DwAgN48AAD6PAAA5jwAwAA9AIDtPAAAAj0AgPc8AAAMPQCA/DwAgBM9
AMAKPQAAGz0AACU9AEA6PQBAOj0AAFw9AIByPQDgoT0AkAM+ALBlPgDAvD4AvAk/AIg4PwAsTj8A
JFE/AKQ/PwDIFj8AYOA+ALicPgDgbD4AkD8+AIBAPgAwQD4A8Eg+ACBVPgAQZT4A0HI+ACiCPgAQ
kD4A0J0+AFinPgDotz4AgMU+ABjTPgCQ4j4A+O0+AKjyPgBI+D4AiAE/ADwHPwAUBz8A6Ao/APwP
PwCkDT8AHA4/AHwNPwDwDD8AhAo/ACwNPwAYDT8AkA0/ALQMPwAsDT8AqA4/AAwPPwB4ET8A1BQ/
ALQRPwAkED8AEBU/ALAQPwAMDz8AOAs/ABwOPwDIDD8AZAc/ANAJPwBsBD8A6P0+ACD9PgAI/D4A
MPI+ANDoPgDA5D4AsOU+ACDfPgCY2j4AgNk+ABjOPgCwzD4AkM4+ACDGPgBYxT4AgMU+ABC9PgDA
tz4AwLc+AJiyPgD4rD4AuLA+AEioPgAQpD4AOJ8+ALCfPgCYmT4AAJY+AMCPPgBYjj4AiIs+APCH
PgCggj4AAII+AOB2PgAAdT4A8HA+AHBuPgBAXT4AYFs+AIBUPgCQUz4A0EU+ADBKPgCAQD4AAEM+
AAA+PgBAOj4AIC0+ALA4PgCwKT4AUCo+AKAqPgDAIz4AkBw+AMAZPgDQGD4AIBQ+ADAOPgDAFD4A
AAc+AHAFPgCgAj4AUAI+ACD9PQDg9j0AAPA9ACDzPQCg6z0AgOg9AODiPQCA6D0AwNA9AADXPQDA
yz0AQMk9
          </values>
        </Ydata>
        <Ydata label="Y" units="UNKNOWN">
          <parameter name="DataTags" label="# of data points with tags" group="YAxis">0</parameter>
          <parameter name="Channel" label="Instrument channel stored" group="YAxis">10</parameter>
          <parameter name="MaxX" label="X-axis of the data set maximum" group="YAxis">376.000000</parameter>
          <parameter name="MaxY" label="Data set maximum" group="YAxis">1.158142</parameter>
          <parameter name="MinX" label="X-axis of the data set minimum" group="YAxis">303.000000</parameter>
          <parameter name="MinY" label="Data set minimum" group="YAxis">0.025635</parameter>
          <parameter name="YaxisType" label="Y-axis type" group="YAxis">0x12 = Intensity</parameter>
          <parameter name="YaxisUnit" label="Y-axis unit" group="YAxis">0x0 = Unknown</parameter>
          <parameter name="YAxisLL" label="Y-axis lower limit" group="YAxis">0.025635</parameter>
          <parameter name="YAxisUL" label="Y-axis upper limit" group="YAxis">1.158142</parameter>
          <parameter name="LabelY" label="Y-axis Label" group="YAxis">Y</parameter>
          <parameter name="AcqStarted" label="Date and Time the acquisition was started" group="Standard">0000-00-00 00:00:00</parameter>
          <parameter name="AcqCompleted" label="Date and Time the acquisition was completed" group="Standard">0000-00-00 00:00:00</parameter>
          <parameter name="ID" label="Acquiring instruments serial (ID) number" group="Standard">0</parameter>
          <parameter name="Instrument" label="Instrument type acquired on" group="Standard">7</parameter>
          <parameter name="Modified" label="Date and Time the acquisition was modified" group="Standard">0000-00-00 00:00:00</parameter>
          <parameter name="OSVersion" label="Operating system version" group="Standard">0.000000</parameter>
          <parameter name="Version" label="Program version (&apos;C&apos;) acquired on" group="Standard">0.000000</parameter>
          <parameter name="Comment" label="Acquisition comment" group="Standard">NULL</parameter>
          <parameter name="AcqTitle" label="Acquisition title" group="Standard">NULL</parameter>
          <parameter name="User" label="User who acquired this data" group="Standard">NULL</parameter>
          <parameter name="Monochromators" label="# of monochromators attached" group="Hardware">2</parameter>
          <parameter name="Pmts" label="# of PMTs attached" group="Hardware">2</parameter>
          <parameter name="Lamp" label="Excitation light source type" group="LAMP">1</parameter>
          <parameter name="LampComment" label="Excitation light source comment" group="LAMP">Default Lamp Comment</parameter>
          <parameter name="MonochromatorAt1" label="Monochromator constant/setup wavelength" group="Monochromator">300.000000</parameter>
          <parameter name="MonochromatorShutter1" label="Monochromator shutter status" group="Monochromator">0</parameter>
          <parameter name="MonochromatorSlitIn1" label="Monochromator input slit width" group="Monochromator">4.000000</parameter>
          <parameter name="MonochromatorSlitOut1" label="Monochromator output slit width" group="Monochromator">4.000000</parameter>
          <parameter name="MonochromatorStepSize1" label="Monochromator step size" group="Monochromator">0.200000</parameter>
          <parameter name="MonochromatorType1" label="Monochromator type (excitation or emission)" group="Monochromator">10</parameter>
          <parameter name="MonochromatorUpperLimit1" label="Monochromator upper limit" group="Monochromator">950.000000</parameter>
          <parameter name="MonochromatorComment1" label="Monochromator comment" group="Monochromator">Default Monochromator Comment</parameter>
          <parameter name="MonochromatorNumFilter1" label="Monochromator # of filters switched" group="Filter">2</parameter>
          <parameter name="MonochromatorFilter1Wavelength1" label="Monochromator constant/setup wavelength" group="Filter">200.000000</parameter>
          <parameter name="MonochromatorFilter1Wavelength2" label="Monochromator constant/setup wavelength" group="Filter">300.000000</parameter>
          <parameter name="MonochromatorFilter1Comment" label="Monochromator filter comment" group="Filter">Default Filter Comment</parameter>
          <parameter name="MonochromatorPolarizerAngle1" label="Monochromator polarization angle" group="Polarizer">0.000000</parameter>
          <parameter name="MonochromatorPolarizerType1" label="Monochromator polarizer type" group="Polarizer">1</parameter>
          <parameter name="MonochromatorPolarizerComment1" label="Monochromator polarizer comment" group="Polarizer">Default Polarizer Comment</parameter>
          <parameter name="MonochromatorAt2" label="Monochromator constant/setup wavelength" group="Monochromator">400.000000</parameter>
          <parameter name="MonochromatorShutter2" label="Monochromator shutter status" group="Monochromator">0</parameter>
          <parameter name="MonochromatorSlitIn2" label="Monochromator input slit width" group="Monochromator">4.000000</parameter>
          <parameter name="MonochromatorSlitOut2" label="Monochromator output slit width" group="Monochromator">4.000000</parameter>
          <parameter name="MonochromatorStepSize2" label="Monochromator step size" group="Monochromator">0.200000</parameter>
          <parameter name="MonochromatorType2" label="Monochromator type (excitation or emission)" group="Monochromator">8</parameter>
          <parameter name="MonochromatorUpperLimit2" label="Monochromator upper limit" group="Monochromator">950.000000</parameter>
          <parameter name="MonochromatorComment2" label="Monochromator comment" group="Monochromator">Default Monochromator Comment</parameter>
          <parameter name="MonochromatorNumFilter2" label="Monochromator # of filters switched" group="Filter">3</parameter>
          <parameter name="MonochromatorFilter2Wavelength1" label="Monochromator constant/setup wavelength" group="Filter">200.000000</parameter>
          <parameter name="MonochromatorFilter2Wavelength2" label="Monochromator constant/setup wavelength" group="Filter">300.000000</parameter>
          <parameter name="MonochromatorFilter2Wavelength3" label="Monochromator constant/setup wavelength" group="Filter">400.000000</parameter>
          <parameter name="MonochromatorFilter2Comment" label="Monochromator filter comment" group="Filter">Default Filter Comment</parameter>
          <parameter name="MonochromatorPolarizerAngle2" label="Monochromator polarization angle" group="Polarizer">90.000000</parameter>
          <parameter name="MonochromatorPolarizerType2" label="Monochromator polarizer type" group="Polarizer">1</parameter>
          <parameter name="MonochromatorPolarizerComment2" label="Monochromator polarizer comment" group="Polarizer">Default Polarizer Comment</parameter>
          <parameter name="PmtConnection1" label="PMT connection" group="PMT">10</parameter>
          <parameter name="PmtGain1" label="PMT gain" group="PMT">1</parameter>
          <parameter name="PmtHighVolt1" label="PMT high voltage" group="PMT">750</parameter>
          <parameter name="PmtOffset1" label="PMT offset status" group="PMT">0</parameter>
          <parameter name="PmtAcquisition1" label="PMT acquisition type" group="PMT">0</parameter>
          <parameter name="PmtComment1" label="PMT comment" group="PMT">Default Pmt Comment</parameter>
          <parameter name="PmtConnection2" label="PMT connection" group="PMT">10</parameter>
          <parameter name="PmtGain2" label="PMT gain" group="PMT">1</parameter>
          <parameter name="PmtHighVolt2" label="PMT high voltage" group="PMT">750</parameter>
          <parameter name="PmtOffset2" label="PMT offset status" group="PMT">0</parameter>
          <parameter name="PmtAcquisition2" label="PMT acquisition type" group="PMT">1</parameter>
          <parameter name="PmtComment2" label="PMT comment" group="PMT">Default Pmt Comment</parameter>
          <parameter name="InstrumentSpecific3" label="Instrument specific information" group="AUX">2</parameter>
          <parameter name="AuxChannelGain1" label="AUX channel gain" group="AUX">0</parameter>
          <parameter name="AuxChannelOffset1" label="AUX channel offset status" group="AUX">0</parameter>
          <parameter name="AuxChannelComment1" label="AUX channel comment" group="AUX">Default Aux Pmt Comment</parameter>
          <parameter name="AuxChannelGain2" label="AUX channel gain" group="AUX">1</parameter>
          <parameter name="AuxChannelOffset2" label="AUX channel offset status" group="AUX">0</parameter>
          <parameter name="AuxChannelComment2" label="AUX channel comment" group="AUX">Default Aux Pmt Comment</parameter>
          <parameter name="PlotInfo" label="Y-axis plotting annotation" group="YAxis">NULL</parameter>
          <parameter name="Corrected" label="Data corrected flag" group="MANIPULATION">0x0</parameter>
          <parameter name="Derivative" label="Degree of derivation" group="MANIPULATION">0</parameter>
          <parameter name="Norm" label="Normalized flag" group="MANIPULATION">0x0</parameter>
          <parameter name="Offset" label="Offset from the original data" group="MANIPULATION">0.000000</parameter>
          <parameter name="Scale" label="Scale from the original data" group="MANIPULATION">1.000000</parameter>
          <parameter name="Smooth" label="# of smoothing passes done" group="MANIPULATION">0</parameter>
          <parameter name="SmoothType" label="Smoothing type" group="MANIPULATION">1.000000</parameter>
          <values byteorder="INTEL" format="FLOAT32" numvalues="201">AIDePAAA3DwAgO08AADSPAAA8DwAgNQ8AIDyPACA1DwAAPA8AADXPAAA6zwAgNQ8AADrPAAA1zwA
gPI8AADXPACA7TwAgNQ8AIDtPAAA1zwAgO08AIDZPAAA8DwAANc8AIDyPACA1DwAAPA8AADXPACA
7TwAgNk8AIDyPAAA3DwAgPI8AADcPACA8jwAANw8AADwPAAA3DwAgPI8AADcPAAA9TwAgN48AADw
PACA3jwAgPI8AADmPAAA+jwAAOY8AID3PACA4zwAAPU8AIDjPACA/DwAgO08AID8PACA6DwAAAI9
AIDyPADABT0AgPw8AMAFPQAAAj0AQA09AAAMPQAAGz0AQBw9AAAvPQBAMD0AgFQ9AECDPQDA6T0A
sGo+ADjRPgDgKz8A6G4/AByOPwA+lD8A6IU/AJRUPwAoET8AiMI+ANB3PgCQRD4AkDo+ANBKPgDg
Uz4AMF4+ACBuPgDwgj4AEIs+AGiXPgCAoj4AGLA+AGi6PgD4zz4AsNY+ACjmPgA47z4AcAA/AOwB
PwCIAT8AjAc/ALgIPwDwBz8AaAg/AFwKPwAsCD8AfAg/AJAIPwBoCD8AVAg/AEgKPwAABz8AbAQ/
AEgKPwDoCj8AOAs/AJQJPwAsDT8A4A0/AIgLPwA8DD8ATBA/AIAJPwBECT8AaAg/AHAFPwCsAD8A
BAM/ADD8PgA49D4AOO8+AAjtPgAA3D4AAOE+AEDYPgCA2T4AcNA+AHDQPgCQyT4AcMY+AOjBPgCY
xj4AwLw+APC5PgDItD4AOLM+ALCuPgBYrD4AsKk+AMigPgBInj4AmJ4+APiYPgDglz4AEJU+AJCN
PgCQiD4A2Is+ALiDPgC4gz4AsHk+ABB5PgCgdT4AoHA+AIBjPgBAYj4A0FQ+AHBQPgDgST4AYEw+
AJA/PgBAPz4AwDc+ADA2PgDwND4AMDY+ANAnPgBgKT4AsCQ+ADAiPgCwGj4AsBo+AJASPgDQGD4A
sBA+AEASPgDQCT4AUAw+ALABPgDQBD4AYPk9AMD9PQCA8j0AoPo9AKDhPQAg6T0AQOI9AODTPQDA
xj0AwNA9
          </values>
        </Ydata>
        <Ydata label="Y" units="UNKNOWN">
          <parameter name="DataTags" label="# of data points with tags" group="YAxis">0</parameter>
          <parameter name="Channel" label="Instrument channel stored" group="YAxis">10</parameter>
          <parameter name="MaxX" label="X-axis of the data set maximum" group="YAxis">381.000000</parameter>
          <parameter name="MaxY" label="Data set maximum" group="YAxis">1.287537</parameter>
          <parameter name="MinX" label="X-axis of the data set minimum" group="YAxis">300.000000</parameter>
          <parameter name="MinY" label="Data set minimum" group="YAxis">0.025330</parameter>
          <parameter name="YaxisType" label="Y-axis type" group="YAxis">0x12 = Intensity</parameter>
          <parameter name="YaxisUnit" label="Y-axis unit" group="YAxis">0x0 = Unknown</parameter>
          <parameter name="YAxisLL" label="Y-axis lower limit" group="YAxis">0.025330</parameter>
          <parameter name="YAxisUL" label="Y-axis upper limit" group="YAxis">1.287537</parameter>
          <parameter name="LabelY" label="Y-axis Label" group="YAxis">Y</parameter>
          <parameter name="AcqStarted" label="Date and Time the acquisition was started" group="Standard">0000-00-00 00:00:00</parameter>
          <parameter name="AcqCompleted" label="Date and Time the acquisition was completed" group="Standard">0000-00-00 00:00:00</parameter>
          <parameter name="ID" label="Acquiring instruments serial (ID) number" group="Standard">0</parameter>
          <parameter name="Instrument" label="Instrument type acquired on" group="Standard">7</parameter>
          <parameter name="Modified" label="Date and Time the acquisition was modified" group="Standard">0000-00-00 00:00:00</parameter>
          <parameter name="OSVersion" label="Operating system version" group="Standard">0.000000</parameter>
          <parameter name="Version" label="Program version (&apos;C&apos;) acquired on" group="Standard">0.000000</parameter>
          <parameter name="Comment" label="Acquisition comment" group="Standard">NULL</parameter>
          <parameter name="AcqTitle" label="Acquisition title" group="Standard">NULL</parameter>
          <parameter name="User" label="User who acquired this data" group="Standard">NULL</parameter>
          <parameter name="Monochromators" label="# of monochromators attached" group="Hardware">2</parameter>
          <parameter name="Pmts" label="# of PMTs attached" group="Hardware">2</parameter>
          <parameter name="Lamp" label="Excitation light source type" group="LAMP">1</parameter>
          <parameter name="LampComment" label="Excitation light source comment" group="LAMP">Default Lamp Comment</parameter>
          <parameter name="MonochromatorAt1" label="Monochromator constant/setup wavelength" group="Monochromator">300.000000</parameter>
          <parameter name="MonochromatorShutter1" label="Monochromator shutter status" group="Monochromator">0</parameter>
          <parameter name="MonochromatorSlitIn1" label="Monochromator input slit width" group="Monochromator">4.000000</parameter>
          <parameter name="MonochromatorSlitOut1" label="Monochromator output slit width" group="Monochromator">4.000000</parameter>
          <parameter name="MonochromatorStepSize1" label="Monochromator step size" group="Monochromator">0.200000</parameter>
          <parameter name="MonochromatorType1" label="Monochromator type (excitation or emission)" group="Monochromator">10</parameter>
          <parameter name="MonochromatorUpperLimit1" label="Monochromator upper limit" group="Monochromator">950.000000</parameter>
          <parameter name="MonochromatorComment1" label="Monochromator comment" group="Monochromator">Default Monochromator Comment</parameter>
          <parameter name="MonochromatorNumFilter1" label="Monochromator # of filters switched" group="Filter">2</parameter>
          <parameter name="MonochromatorFilter1Wavelength1" label="Monochromator constant/setup wavelength" group="Filter">200.000000</parameter>
          <parameter name="MonochromatorFilter1Wavelength2" label="Monochromator constant/setup wavelength" group="Filter">300.000000</parameter>
          <parameter name="MonochromatorFilter1Comment" label="Monochromator filter comment" group="Filter">Default Filter Comment</parameter>
          <parameter name="MonochromatorPolarizerAngle1" label="Monochromator polarization angle" group="Polarizer">0.000000</parameter>
          <parameter name="MonochromatorPolarizerType1" label="Monochromator polarizer type" group="Polarizer">1</parameter>
          <parameter name="MonochromatorPolarizerComment1" label="Monochromator polarizer comment" group="Polarizer">Default Polarizer Comment</parameter>
          <parameter name="MonochromatorAt2" label="Monochromator constant/setup wavelength" group="Monochromator">400.000000</parameter>
          <parameter name="MonochromatorShutter2" label="Monochromator shutter status" group="Monochromator">0</parameter>
          <parameter name="MonochromatorSlitIn2" label="Monochromator input slit width" group="Monochromator">4.000000</parameter>
          <parameter name="MonochromatorSlitOut2" label="Monochromator output slit width" group="Monochromator">4.000000</parameter>
          <parameter name="MonochromatorStepSize2" label="Monochromator step size" group="Monochromator">0.200000</parameter>
          <parameter name="MonochromatorType2" label="Monochromator type (excitation or emission)" group="Monochromator">8</parameter>
          <parameter name="MonochromatorUpperLimit2" label="Monochromator upper limit" group="Monochromator">950.000000</parameter>
          <parameter name="MonochromatorComment2" label="Monochromator comment" group="Monochromator">Default Monochromator Comment</parameter>
          <parameter name="MonochromatorNumFilter2" label="Monochromator # of filters switched" group="Filter">3</parameter>
          <parameter name="MonochromatorFilter2Wavelength1" label="Monochromator constant/setup wavelength" group="Filter">200.000000</parameter>
          <parameter name="MonochromatorFilter2Wavelength2" label="Monochromator constant/setup wavelength" group="Filter">300.000000</parameter>
          <parameter name="MonochromatorFilter2Wavelength3" label="Monochromator constant/setup wavelength" group="Filter">400.000000</parameter>
          <parameter name="MonochromatorFilter2Comment" label="Monochromator filter comment" group="Filter">Default Filter Comment</parameter>
          <parameter name="MonochromatorPolarizerAngle2" label="Monochromator polarization angle" group="Polarizer">90.000000</parameter>
          <parameter name="MonochromatorPolarizerType2" label="Monochromator polarizer type" group="Polarizer">1</parameter>
          <parameter name="MonochromatorPolarizerComment2" label="Monochromator polarizer comment" group="Polarizer">Default Polarizer Comment</parameter>
          <parameter name="PmtConnection1" label="PMT connection" group="PMT">10</parameter>
          <parameter name="PmtGain1" label="PMT gain" group="PMT">1</parameter>
          <parameter name="PmtHighVolt1" label="PMT high voltage" group="PMT">750</parameter>
          <parameter name="PmtOffset1" label="PMT offset status" group="PMT">0</parameter>
          <parameter name="PmtAcquisition1" label="PMT acquisition type" group="PMT">0</parameter>
          <parameter name="PmtComment1" label="PMT comment" group="PMT">Default Pmt Comment</parameter>
          <parameter name="PmtConnection2" label="PMT connection" group="PMT">10</parameter>
          <parameter name="PmtGain2" label="PMT gain" group="PMT">1</parameter>
          <parameter name="PmtHighVolt2" label="PMT high voltage" group="PMT">750</parameter>
          <parameter name="PmtOffset2" label="PMT offset status" group="PMT">0</parameter>
          <parameter name="PmtAcquisition2" label="PMT acquisition type" group="PMT">1</parameter>
          <parameter name="PmtComment2" label="PMT comment" group="PMT">Default Pmt Comment</parameter>
          <parameter name="InstrumentSpecific3" label="Instrument specific information" group="AUX">2</parameter>
          <parameter name="AuxChannelGain1" label="AUX channel gain" group="AUX">0</parameter>
          <parameter name="AuxChannelOffset1" label="AUX channel offset status" group="AUX">0</parameter>
          <parameter name="AuxChannelComment1" label="AUX channel comment" group="AUX">Default Aux Pmt Comment</parameter>
          <parameter name="AuxChannelGain2" label="AUX channel gain" group="AUX">1</parameter>
          <parameter name="AuxChannelOffset2" label="AUX channel offset status" group="AUX">0</parameter>
          <parameter name="AuxChannelComment2" label="AUX channel comment" group="AUX">Default Aux Pmt Comment</parameter>
          <parameter name="PlotInfo" label="Y-axis plotting annotation" group="YAxis">NULL</parameter>
          <parameter name="Corrected" label="Data corrected flag" group="MANIPULATION">0x0</parameter>
          <parameter name="Derivative" label="Degree of derivation" group="MANIPULATION">0</parameter>
          <parameter name="Norm" label="Normalized flag" group="MANIPULATION">0x0</parameter>
          <parameter name="Offset" label="Offset from the original data" group="MANIPULATION">0.000000</parameter>
          <parameter name="Scale" label="Scale from the original data" group="MANIPULATION">1.000000</parameter>
          <parameter name="Smooth" label="# of smoothing passes done" group="MANIPULATION">0</parameter>
          <parameter name="SmoothType" label="Smoothing type" group="MANIPULATION">1.000000</parameter>
          <values byteorder="INTEL" format="FLOAT32" numvalues="201">AIDPPAAA0jwAAOY8AIDPPACA6DwAgM88AADwPACAzzwAgO08AADSPAAA6zwAgM88AIDtPACA1DwA
APA8AADSPAAA8DwAgNQ8AIDtPACA1DwAAOs8AADXPACA6DwAANI8AIDyPACA1DwAAPA8AADXPACA
7TwAANc8AADwPACA1DwAgPI8AIDZPAAA9TwAgNk8AAD1PACA2TwAAPU8AIDZPAAA8DwAgNk8AIDy
PAAA1zwAgPI8AIDePAAA+jwAgNk8AIDyPACA2TwAgPc8AIDePACA8jwAAOE8AAD1PACA3jwAAPU8
AIDePAAA9TwAgOM8AAD6PAAA6zwAgPw8AIDoPADAAD0AAPo8AIAEPQAAAj0AQAg9AIAJPQCAEz0A
gB09AMAtPQAAOT0AYIE9AHAAPgAQiz4AXAU/AChSPwDIjD8A+KI/AM6kPwBMkD8AyGE/ADgkPwCY
0D4AwI8+APBrPgAgaT4AkHE+AKiEPgBYjj4AOJU+AOCcPgDgqz4AWLY+AJjBPgCw0T4AYNs+AKDh
PgAY7D4AsPQ+ACAAPwBIAD8ADAU/AJwGPwB0Bj8A1AU/AJQJPwAcCT8AJAs/AFAMPwAkCz8AIAo/
AMgMPwDYCz8AvAk/ABALPwAsDT8AqAk/AHwIPwCgDD8AxAY/ADwHPwAsCD8AHAk/ACQGPwAMBT8A
5AQ/AIAEPwCw/j4AqPc+AMD4PgAw7T4AWOg+ANDjPgAg5D4AoNc+AFjZPgD4zz4AKNI+AIDKPgA4
xz4AAMM+ALi/PgBwvD4AYL0+AJiyPgC4qz4AoKo+AMCjPgBooT4AoKA+AMCZPgD4mD4A8JE+ANiQ
PgCIiz4AYIs+ACCKPgAIhD4AAII+AGB+PgAgeD4AoHA+ALBlPgCQZz4AAFw+AEBYPgDQWT4AYFE+
ABBMPgAQQj4AoD4+AAA+PgAwMT4AQDo+ANAsPgAAJT4AsCk+AIAdPgBAFz4A8Bs+ALAQPgAwGD4A
YAs+ALAQPgBwCj4AEAY+AFACPgCQAz4A4PY9AID8PQDg7D0AAPA9ACDfPQAA8D0AINo9AEDTPQDg
zj0A4Mk9
          </values>
        </Ydata>
        <Ydata label="Y" units="UNKNOWN">
          <parameter name="DataTags" label="# of data points with tags" group="YAxis">0</parameter>
          <parameter name="Channel" label="Instrument channel stored" group="YAxis">10</parameter>
          <parameter name="MaxX" label="X-axis of the data set maximum" group="YAxis">386.000000</parameter>
          <parameter name="MaxY" label="Data set maximum" group="YAxis">1.709290</parameter>
          <parameter name="MinX" label="X-axis of the data set minimum" group="YAxis">307.000000</parameter>
          <parameter name="MinY" label="Data set minimum" group="YAxis">0.025635</parameter>
          <parameter name="YaxisType" label="Y-axis type" group="YAxis">0x12 = Intensity</parameter>
          <parameter name="YaxisUnit" label="Y-axis unit" group="YAxis">0x0 = Unknown</parameter>
          <parameter name="YAxisLL" label="Y-axis lower limit" group="YAxis">0.025635</parameter>
          <parameter name="YAxisUL" label="Y-axis upper limit" group="YAxis">1.709290</parameter>
          <parameter name="LabelY" label="Y-axis Label" group="YAxis">Y</parameter>
          <parameter name="AcqStarted" label="Date and Time the acquisition was started" group="Standard">0000-00-00 00:00:00</parameter>
          <parameter name="AcqCompleted" label="Date and Time the acquisition was completed" group="Standard">0000-00-00 00:00:00</parameter>
          <parameter name="ID" label="Acquiring instruments serial (ID) number" group="Standard">0</parameter>
          <parameter name="Instrument" label="Instrument type acquired on" group="Standard">7</parameter>
          <parameter name="Modified" label="Date and Time the acquisition was modified" group="Standard">0000-00-00 00:00:00</parameter>
          <parameter name="OSVersion" label="Operating system version" group="Standard">0.000000</parameter>
          <parameter name="Version" label="Program version (&apos;C&apos;) acquired on" group="Standard">0.000000</parameter>
          <parameter name="Comment" label="Acquisition comment" group="Standard">NULL</parameter>
          <parameter name="AcqTitle" label="Acquisition title" group="Standard">NULL</parameter>
          <parameter name="User" label="User who acquired this data" group="Standard">NULL</parameter>
          <parameter name="Monochromators" label="# of monochromators attached" group="Hardware">2</parameter>
          <parameter name="Pmts" label="# of PMTs attached" group="Hardware">2</parameter>
          <parameter name="Lamp" label="Excitation light source type" group="LAMP">1</parameter>
          <parameter name="LampComment" label="Excitation light source comment" group="LAMP">Default Lamp Comment</parameter>
          <parameter name="MonochromatorAt1" label="Monochromator constant/setup wavelength" group="Monochromator">300.000000</parameter>
          <parameter name="MonochromatorShutter1" label="Monochromator shutter status" group="Monochromator">0</parameter>
          <parameter name="MonochromatorSlitIn1" label="Monochromator input slit width" group="Monochromator">4.000000</parameter>
          <parameter name="MonochromatorSlitOut1" label="Monochromator output slit width" group="Monochromator">4.000000</parameter>
          <parameter name="MonochromatorStepSize1" label="Monochromator step size" group="Monochromator">0.200000</parameter>
          <parameter name="MonochromatorType1" label="Monochromator type (excitation or emission)" group="Monochromator">10</parameter>
          <parameter name="MonochromatorUpperLimit1" label="Monochromator upper limit" group="Monochromator">950.000000</parameter>
          <parameter name="MonochromatorComment1" label="Monochromator comment" group="Monochromator">Default Monochromator Comment</parameter>
          <parameter name="MonochromatorNumFilter1" label="Monochromator # of filters switched" group="Filter">2</parameter>
          <parameter name="MonochromatorFilter1Wavelength1" label="Monochromator constant/setup wavelength" group="Filter">200.000000</parameter>
          <parameter name="MonochromatorFilter1Wavelength2" label="Monochromator constant/setup wavelength" group="Filter">300.000000</parameter>
          <parameter name="MonochromatorFilter1Comment" label="Monochromator filter comment" group="Filter">Default Filter Comment</parameter>
          <parameter name="MonochromatorPolarizerAngle1" label="Monochromator polarization angle" group="Polarizer">0.000000</parameter>
          <parameter name="MonochromatorPolarizerType1" label="Monochromator polarizer type" group="Polarizer">1</parameter>
          <parameter name="MonochromatorPolarizerComment1" label="Monochromator polarizer comment" group="Polarizer">Default Polarizer Comment</parameter>
          <parameter name="MonochromatorAt2" label="Monochromator constant/setup wavelength" group="Monochromator">400.000000</parameter>
          <parameter name="MonochromatorShutter2" label="Monochromator shutter status" group="Monochromator">0</parameter>
          <parameter name="MonochromatorSlitIn2" label="Monochromator input slit width" group="Monochromator">4.000000</parameter>
          <parameter name="MonochromatorSlitOut2" label="Monochromator output slit width" group="Monochromator">4.000000</parameter>
          <parameter name="MonochromatorStepSize2" label="Monochromator step size" group="Monochromator">0.200000</parameter>
          <parameter name="MonochromatorType2" label="Monochromator type (excitation or emission)" group="Monochromator">8</parameter>
          <parameter name="MonochromatorUpperLimit2" label="Monochromator upper limit" group="Monochromator">950.000000</parameter>
          <parameter name="MonochromatorComment2" label="Monochromator comment" group="Monochromator">Default Monochromator Comment</parameter>
          <parameter name="MonochromatorNumFilter2" label="Monochromator # of filters switched" group="Filter">3</parameter>
          <parameter name="MonochromatorFilter2Wavelength1" label="Monochromator constant/setup wavelength" group="Filter">200.000000</parameter>
          <parameter name="MonochromatorFilter2Wavelength2" label="Monochromator constant/setup wavelength" group="Filter">300.000000</parameter>
          <parameter name="MonochromatorFilter2Wavelength3" label="Monochromator constant/setup wavelength" group="Filter">400.000000</parameter>
          <parameter name="MonochromatorFilter2Comment" label="Monochromator filter comment" group="Filter">Default Filter Comment</parameter>
          <parameter name="MonochromatorPolarizerAngle2" label="Monochromator polarization angle" group="Polarizer">90.000000</parameter>
          <parameter name="MonochromatorPolarizerType2" label="Monochromator polarizer type" group="Polarizer">1</parameter>
          <parameter name="MonochromatorPolarizerComment2" label="Monochromator polarizer comment" group="Polarizer">Default Polarizer Comment</parameter>
          <parameter name="PmtConnection1" label="PMT connection" group="PMT">10</parameter>
          <parameter name="PmtGain1" label="PMT gain" group="PMT">1</parameter>
          <parameter name="PmtHighVolt1" label="PMT high voltage" group="PMT">750</parameter>
          <parameter name="PmtOffset1" label="PMT offset status" group="PMT">0</parameter>
          <parameter name="PmtAcquisition1" label="PMT acquisition type" group="PMT">0</parameter>
          <parameter name="PmtComment1" label="PMT comment" group="PMT">Default Pmt Comment</parameter>
          <parameter name="PmtConnection2" label="PMT connection" group="PMT">10</parameter>
          <parameter name="PmtGain2" label="PMT gain" group="PMT">1</parameter>
          <parameter name="PmtHighVolt2" label="PMT high voltage" group="PMT">750</parameter>
          <parameter name="PmtOffset2" label="PMT offset status" group="PMT">0</parameter>
          <parameter name="PmtAcquisition2" label="PMT acquisition type" group="PMT">1</parameter>
          <parameter name="PmtComment2" label="PMT comment" group="PMT">Default Pmt Comment</parameter>
          <parameter name="InstrumentSpecific3" label="Instrument specific information" group="AUX">2</parameter>
          <parameter name="AuxChannelGain1" label="AUX channel gain" group="AUX">0</parameter>
          <parameter name="AuxChannelOffset1" label="AUX channel offset status" group="AUX">0</parameter>
          <parameter name="AuxChannelComment1" label="AUX channel comment" group="AUX">Default Aux Pmt Comment</parameter>
          <parameter name="AuxChannelGain2" label="AUX channel gain" group="AUX">1</parameter>
          <parameter name="AuxChannelOffset2" label="AUX channel offset status" group="AUX">0</parameter>
          <parameter name="AuxChannelComment2" label="AUX channel comment" group="AUX">Default Aux Pmt Comment</parameter>
          <parameter name="PlotInfo" label="Y-axis plotting annotation" group="YAxis">NULL</parameter>
          <parameter name="Corrected" label="Data corrected flag" group="MANIPULATION">0x0</parameter>
          <parameter name="Derivative" label="Degree of derivation" group="MANIPULATION">0</parameter>
          <parameter name="Norm" label="Normalized flag" group="MANIPULATION">0x0</parameter>
          <parameter name="Offset" label="Offset from the original data" group="MANIPULATION">0.000000</parameter>
          <parameter name="Scale" label="Scale from the original data" group="MANIPULATION">1.000000</parameter>
          <parameter name="Smooth" label="# of smoothing passes done" group="MANIPULATION">0</parameter>
          <parameter name="SmoothType" label="Smoothing type" group="MANIPULATION">1.000000</parameter>
          <values byteorder="INTEL" format="FLOAT32" numvalues="201">AIDjPAAA3DwAgPI8AADXPACA7TwAgNQ8AIDyPAAA0jwAAPU8AADSPAAA8DwAgNQ8AADrPAAA1zwA
gPI8AIDUPAAA8DwAANc8AIDtPAAA1zwAgO08AADcPAAA8DwAANc8AIDtPACA2TwAgO08AADXPAAA
6zwAgNk8AAD1PAAA1zwAAPA8AADcPACA8jwAgNk8AADrPACA3jwAAPU8AADXPACA9zwAANw8AIDy
PACA3jwAAPA8AADhPACA9zwAAOE8AIDyPAAA4TwAgPc8AADmPACA7TwAAOE8AAD1PACA3jwAAPU8
AADcPACA9zwAAOY8AIDtPAAA4TwAAPo8AADrPAAA+jwAgOM8AID3PAAA8DwAAPo8AIDtPABAAz0A
gPI8AAAHPQAAAj0AQBI9AMAPPQCALD0AgDs9AMBVPQCApz0AcDI+AHDLPgAsNT8AZJY/ANTBPwA6
2T8Ayto/AMy6PwBgkD8A2EI/ABABPwDgtT4AgKI+ABihPgAoqj4AULQ+APi7PgCYxj4AqM8+AMDV
PgBg2z4AcN8+AFDhPgBA5z4A4PE+AIjvPgAI8j4A8Po+AHgCPwBQAj8A5AQ/APwFPwB0Bj8AtAc/
AKwKPwCsCj8AaAg/ANgLPwCkDT8AiAY/AFgJPwA8Bz8AIAU/ALQCPwCwAT8AgPw+ABD+PgCQ9j4A
MPc+ACj1PgC47D4AkPE+AEDsPgB45j4A0O0+AMDkPgAo5j4AENs+APjUPgB40j4AINA+ABDHPgCQ
yT4A4L8+AFC+PgDQuz4AgLs+AICxPgAYsD4AoKo+ANCiPgBgnz4AcJ4+AECXPgCYlD4ACJM+ADCO
PgBghj4AoIc+AEiAPgBwfT4A8HU+AAB6PgCAaD4A4Gc+AFBcPgDAXz4AMFQ+AHBVPgDQTz4AcEY+
ABBCPgDgPz4AwDw+APA5PgCQNT4AgDE+APAqPgDAIz4AUCU+AOAmPgAgHj4AYBo+AOASPgAgDz4A
YAY+AJANPgBwBT4AsAE+AAD6PQDg+z0A4Ow9AAD1PQAA6z0AQOc9AEDdPQDg3T0AoNI9AGDMPQAg
yz0AAMM9
          </values>
        </Ydata>
        <Ydata label="Y" units="UNKNOWN">
          <parameter name="DataTags" label="# of data points with tags" group="YAxis">0</parameter>
          <parameter name="Channel" label="Instrument channel stored" group="YAxis">10</parameter>
          <parameter name="MaxX" label="X-axis of the data set maximum" group="YAxis">391.000000</parameter>
          <parameter name="MaxY" label="Data set maximum" group="YAxis">1.555481</parameter>
          <parameter name="MinX" label="X-axis of the data set minimum" group="YAxis">305.000000</parameter>
          <parameter name="MinY" label="Data set minimum" group="YAxis">0.025024</parameter>
          <parameter name="YaxisType" label="Y-axis type" group="YAxis">0x12 = Intensity</parameter>
          <parameter name="YaxisUnit" label="Y-axis unit" group="YAxis">0x0 = Unknown</parameter>
          <parameter name="YAxisLL" label="Y-axis lower limit" group="YAxis">0.025024</parameter>
          <parameter name="YAxisUL" label="Y-axis upper limit" group="YAxis">1.555481</parameter>
          <parameter name="LabelY" label="Y-axis Label" group="YAxis">Y</parameter>
          <parameter name="AcqStarted" label="Date and Time the acquisition was started" group="Standard">0000-00-00 00:00:00</parameter>
          <parameter name="AcqCompleted" label="Date and Time the acquisition was completed" group="Standard">0000-00-00 00:00:00</parameter>
          <parameter name="ID" label="Acquiring instruments serial (ID) number" group="Standard">0</parameter>
          <parameter name="Instrument" label="Instrument type acquired on" group="Standard">7</parameter>
          <parameter name="Modified" label="Date and Time the acquisition was modified" group="Standard">0000-00-00 00:00:00</parameter>
          <parameter name="OSVersion" label="Operating system version" group="Standard">0.000000</parameter>
          <parameter name="Version" label="Program version (&apos;C&apos;) acquired on" group="Standard">0.000000</parameter>
          <parameter name="Comment" label="Acquisition comment" group="Standard">NULL</parameter>
          <parameter name="AcqTitle" label="Acquisition title" group="Standard">NULL</parameter>
          <parameter name="User" label="User who acquired this data" group="Standard">NULL</parameter>
          <parameter name="Monochromators" label="# of monochromators attached" group="Hardware">2</parameter>
          <parameter name="Pmts" label="# of PMTs attached" group="Hardware">2</parameter>
          <parameter name="Lamp" label="Excitation light source type" group="LAMP">1</parameter>
          <parameter name="LampComment" label="Excitation light source comment" group="LAMP">Default Lamp Comment</parameter>
          <parameter name="MonochromatorAt1" label="Monochromator constant/setup wavelength" group="Monochromator">300.000000</parameter>
          <parameter name="MonochromatorShutter1" label="Monochromator shutter status" group="Monochromator">0</parameter>
          <parameter name="MonochromatorSlitIn1" label="Monochromator input slit width" group="Monochromator">4.000000</parameter>
          <parameter name="MonochromatorSlitOut1" label="Monochromator output slit width" group="Monochromator">4.000000</parameter>
          <parameter name="MonochromatorStepSize1" label="Monochromator step size" group="Monochromator">0.200000</parameter>
          <parameter name="MonochromatorType1" label="Monochromator type (excitation or emission)" group="Monochromator">10</parameter>
          <parameter name="MonochromatorUpperLimit1" label="Monochromator upper limit" group="Monochromator">950.000000</parameter>
          <parameter name="MonochromatorComment1" label="Monochromator comment" group="Monochromator">Default Monochromator Comment</parameter>
          <parameter name="MonochromatorNumFilter1" label="Monochromator # of filters switched" group="Filter">2</parameter>
          <parameter name="MonochromatorFilter1Wavelength1" label="Monochromator constant/setup wavelength" group="Filter">200.000000</parameter>
          <parameter name="MonochromatorFilter1Wavelength2" label="Monochromator constant/setup wavelength" group="Filter">300.000000</parameter>
          <parameter name="MonochromatorFilter1Comment" label="Monochromator filter comment" group="Filter">Default Filter Comment</parameter>
          <parameter name="MonochromatorPolarizerAngle1" label="Monochromator polarization angle" group="Polarizer">0.000000</parameter>
          <parameter name="MonochromatorPolarizerType1" label="Monochromator polarizer type" group="Polarizer">1</parameter>
          <parameter name="MonochromatorPolarizerComment1" label="Monochromator polarizer comment" group="Polarizer">Default Polarizer Comment</parameter>
          <parameter name="MonochromatorAt2" label="Monochromator constant/setup wavelength" group="Monochromator">400.000000</parameter>
          <parameter name="MonochromatorShutter2" label="Monochromator shutter status" group="Monochromator">0</parameter>
          <parameter name="MonochromatorSlitIn2" label="Monochromator input slit width" group="Monochromator">4.000000</parameter>
          <parameter name="MonochromatorSlitOut2" label="Monochromator output slit width" group="Monochromator">4.000000</parameter>
          <parameter name="MonochromatorStepSize2" label="Monochromator step size" group="Monochromator">0.200000</parameter>
          <parameter name="MonochromatorType2" label="Monochromator type (excitation or emission)" group="Monochromator">8</parameter>
          <parameter name="MonochromatorUpperLimit2" label="Monochromator upper limit" group="Monochromator">950.000000</parameter>
          <parameter name="MonochromatorComment2" label="Monochromator comment" group="Monochromator">Default Monochromator Comment</parameter>
          <parameter name="MonochromatorNumFilter2" label="Monochromator # of filters switched" group="Filter">3</parameter>
          <parameter name="MonochromatorFilter2Wavelength1" label="Monochromator constant/setup wavelength" group="Filter">200.000000</parameter>
          <parameter name="MonochromatorFilter2Wavelength2" label="Monochromator constant/setup wavelength" group="Filter">300.000000</parameter>
          <parameter name="MonochromatorFilter2Wavelength3" label="Monochromator constant/setup wavelength" group="Filter">400.000000</parameter>
          <parameter name="MonochromatorFilter2Comment" label="Monochromator filter comment" group="Filter">Default Filter Comment</parameter>
          <parameter name="MonochromatorPolarizerAngle2" label="Monochromator polarization angle" group="Polarizer">90.000000</parameter>
          <parameter name="MonochromatorPolarizerType2" label="Monochromator polarizer type" group="Polarizer">1</parameter>
          <parameter name="MonochromatorPolarizerComment2" label="Monochromator polarizer comment" group="Polarizer">Default Polarizer Comment</parameter>
          <parameter name="PmtConnection1" label="PMT connection" group="PMT">10</parameter>
          <parameter name="PmtGain1" label="PMT gain" group="PMT">1</parameter>
          <parameter name="PmtHighVolt1" label="PMT high voltage" group="PMT">750</parameter>
          <parameter name="PmtOffset1" label="PMT offset status" group="PMT">0</parameter>
          <parameter name="PmtAcquisition1" label="PMT acquisition type" group="PMT">0</parameter>
          <parameter name="PmtComment1" label="PMT comment" group="PMT">Default Pmt Comment</parameter>
          <parameter name="PmtConnection2" label="PMT connection" group="PMT">10</parameter>
          <parameter name="PmtGain2" label="PMT gain" group="PMT">1</parameter>
          <parameter name="PmtHighVolt2" label="PMT high voltage" group="PMT">750</parameter>
          <parameter name="PmtOffset2" label="PMT offset status" group="PMT">0</parameter>
          <parameter name="PmtAcquisition2" label="PMT acquisition type" group="PMT">1</parameter>
          <parameter name="PmtComment2" label="PMT comment" group="PMT">Default Pmt Comment</parameter>
          <parameter name="InstrumentSpecific3" label="Instrument specific information" group="AUX">2</parameter>
          <parameter name="AuxChannelGain1" label="AUX channel gain" group="AUX">0</parameter>
          <parameter name="AuxChannelOffset1" label="AUX channel offset status" group="AUX">0</parameter>
          <parameter name="AuxChannelComment1" label="AUX channel comment" group="AUX">Default Aux Pmt Comment</parameter>
          <parameter name="AuxChannelGain2" label="AUX channel gain" group="AUX">1</parameter>
          <parameter name="AuxChannelOffset2" label="AUX channel offset status" group="AUX">0</parameter>
          <parameter name="AuxChannelComment2" label="AUX channel comment" group="AUX">Default Aux Pmt Comment</parameter>
          <parameter name="PlotInfo" label="Y-axis plotting annotation" group="YAxis">NULL</parameter>
          <parameter name="Corrected" label="Data corrected flag" group="MANIPULATION">0x0</parameter>
          <parameter name="Derivative" label="Degree of derivation" group="MANIPULATION">0</parameter>
          <parameter name="Norm" label="Normalized flag" group="MANIPULATION">0x0</parameter>
          <parameter name="Offset" label="Offset from the original data" group="MANIPULATION">0.000000</parameter>
          <parameter name="Scale" label="Scale from the original data" group="MANIPULATION">1.000000</parameter>
          <parameter name="Smooth" label="# of smoothing passes done" group="MANIPULATION">0</parameter>
          <parameter name="SmoothType" label="Smoothing type" group="MANIPULATION">1.000000</parameter>
          <values byteorder="INTEL" format="FLOAT32" numvalues="201">AIDUPACA1DwAgOg8AIDPPAAA6zwAAM08AADrPAAA0jwAAPA8AADSPACA7TwAgNQ8AIDtPAAA0jwA
gO08AIDUPACA7TwAANI8AIDtPACAzzwAgO08AIDZPACA7TwAANI8AADwPACA1DwAgO08AADXPACA
7TwAgNk8AIDtPACA1DwAgO08AADXPACA8jwAgNk8AIDtPAAA3DwAgO08AIDZPACA8jwAgNk8AIDt
PAAA1zwAAPU8AIDePACA8jwAgNk8AIDyPAAA3DwAAPA8AADhPAAA8DwAAOE8AADwPACA2TwAgPI8
AADhPACA8jwAgN48AIDyPAAA3DwAAPA8AIDePAAA9TwAAOE8AADwPAAA4TwAAPA8AADmPAAA+jwA
gOg8AID8PAAA6zwAgPw8AAD1PACABD0AAAI9AIAYPQCAEz0AgCc9AMA3PQDAUD0AwHg9AMCyPQBw
Mj4AaL8+AMgqPwCggj8AIrA/APTEPwAaxz8AJLM/AF6NPwCwTD8A1BQ/ADjgPgCwwj4AcME+AGjJ
PgAQzD4AYNE+ADjRPgAI1D4AoNI+AMDQPgBY2T4AiNE+AHjcPgAA3D4AyOE+ANjlPgCw6j4ACPI+
AKjyPgBI+D4AXAA/AOgAPwBMAT8AxAE/ABQCPwCA/D4AwAA/AKwAPwB4+j4AWPc+APj3PgCg6z4A
IOQ+AKDhPgDg3T4AgNQ+AGjYPgDwzT4AGM4+AAjKPgCwzD4ASMs+ALDHPgDoxj4AOMw+ANDAPgCQ
xD4AyL4+AJC6PgAQsz4ASLI+AFCqPgBopj4AAKU+APiiPgCgmz4AIJk+AOCXPgCYjz4AuIg+AFCH
PgCQgz4A0Hw+AMB4PgCgdT4AcGk+APBhPgCQYj4AoFw+APBNPgBgTD4A4Ek+AMBGPgDgOj4AoDk+
AFA5PgBgMz4AoC8+AOAwPgAQKT4AsCQ+AEAhPgCAIj4AwBk+ABAVPgAQFT4AUBE+ABAGPgCACT4A
sAE+ANAEPgBA9j0AQPs9AMDpPQAA6z0AwOQ9AIDjPQBA4j0AoNI9AEDOPQCg0j0AQMQ9AADDPQCg
uT0AYLg9
          </values>
        </Ydata>
        <Ydata label="Y" units="UNKNOWN">
          <parameter name="DataTags" label="# of data points with tags" group="YAxis">0</parameter>
          <parameter name="Channel" label="Instrument channel stored" group="YAxis">10</parameter>
          <parameter name="MaxX" label="X-axis of the data set maximum" group="YAxis">396.000000</parameter>
          <parameter name="MaxY" label="Data set maximum" group="YAxis">1.912231</parameter>
          <parameter name="MinX" label="X-axis of the data set minimum" group="YAxis">323.000000</parameter>
          <parameter name="MinY" label="Data set minimum" group="YAxis">0.025635</parameter>
          <parameter name="YaxisType" label="Y-axis type" group="YAxis">0x12 = Intensity</parameter>
          <parameter name="YaxisUnit" label="Y-axis unit" group="YAxis">0x0 = Unknown</parameter>
          <parameter name="YAxisLL" label="Y-axis lower limit" group="YAxis">0.025635</parameter>
          <parameter name="YAxisUL" label="Y-axis upper limit" group="YAxis">1.912231</parameter>
          <parameter name="LabelY" label="Y-axis Label" group="YAxis">Y</parameter>
          <parameter name="AcqStarted" label="Date and Time the acquisition was started" group="Standard">0000-00-00 00:00:00</parameter>
          <parameter name="AcqCompleted" label="Date and Time the acquisition was completed" group="Standard">0000-00-00 00:00:00</parameter>
          <parameter name="ID" label="Acquiring instruments serial (ID) number" group="Standard">0</parameter>
          <parameter name="Instrument" label="Instrument type acquired on" group="Standard">7</parameter>
          <parameter name="Modified" label="Date and Time the acquisition was modified" group="Standard">0000-00-00 00:00:00</parameter>
          <parameter name="OSVersion" label="Operating system version" group="Standard">0.000000</parameter>
          <parameter name="Version" label="Program version (&apos;C&apos;) acquired on" group="Standard">0.000000</parameter>
          <parameter name="Comment" label="Acquisition comment" group="Standard">NULL</parameter>
          <parameter name="AcqTitle" label="Acquisition title" group="Standard">NULL</parameter>
          <parameter name="User" label="User who acquired this data" group="Standard">NULL</parameter>
          <parameter name="Monochromators" label="# of monochromators attached" group="Hardware">2</parameter>
          <parameter name="Pmts" label="# of PMTs attached" group="Hardware">2</parameter>
          <parameter name="Lamp" label="Excitation light source type" group="LAMP">1</parameter>
          <parameter name="LampComment" label="Excitation light source comment" group="LAMP">Default Lamp Comment</parameter>
          <parameter name="MonochromatorAt1" label="Monochromator constant/setup wavelength" group="Monochromator">300.000000</parameter>
          <parameter name="MonochromatorShutter1" label="Monochromator shutter status" group="Monochromator">0</parameter>
          <parameter name="MonochromatorSlitIn1" label="Monochromator input slit width" group="Monochromator">4.000000</parameter>
          <parameter name="MonochromatorSlitOut1" label="Monochromator output slit width" group="Monochromator">4.000000</parameter>
          <parameter name="MonochromatorStepSize1" label="Monochromator step size" group="Monochromator">0.200000</parameter>
          <parameter name="MonochromatorType1" label="Monochromator type (excitation or emission)" group="Monochromator">10</parameter>
          <parameter name="MonochromatorUpperLimit1" label="Monochromator upper limit" group="Monochromator">950.000000</parameter>
          <parameter name="MonochromatorComment1" label="Monochromator comment" group="Monochromator">Default Monochromator Comment</parameter>
          <parameter name="MonochromatorNumFilter1" label="Monochromator # of filters switched" group="Filter">2</parameter>
          <parameter name="MonochromatorFilter1Wavelength1" label="Monochromator constant/setup wavelength" group="Filter">200.000000</parameter>
          <parameter name="MonochromatorFilter1Wavelength2" label="Monochromator constant/setup wavelength" group="Filter">300.000000</parameter>
          <parameter name="MonochromatorFilter1Comment" label="Monochromator filter comment" group="Filter">Default Filter Comment</parameter>
          <parameter name="MonochromatorPolarizerAngle1" label="Monochromator polarization angle" group="Polarizer">0.000000</parameter>
          <parameter name="MonochromatorPolarizerType1" label="Monochromator polarizer type" group="Polarizer">1</parameter>
          <parameter name="MonochromatorPolarizerComment1" label="Monochromator polarizer comment" group="Polarizer">Default Polarizer Comment</parameter>
          <parameter name="MonochromatorAt2" label="Monochromator constant/setup wavelength" group="Monochromator">400.000000</parameter>
          <parameter name="MonochromatorShutter2" label="Monochromator shutter status" group="Monochromator">0</parameter>
          <parameter name="MonochromatorSlitIn2" label="Monochromator input slit width" group="Monochromator">4.000000</parameter>
          <parameter name="MonochromatorSlitOut2" label="Monochromator output slit width" group="Monochromator">4.000000</parameter>
          <parameter name="MonochromatorStepSize2" label="Monochromator step size" group="Monochromator">0.200000</parameter>
          <parameter name="MonochromatorType2" label="Monochromator type (excitation or emission)" group="Monochromator">8</parameter>
          <parameter name="MonochromatorUpperLimit2" label="Monochromator upper limit" group="Monochromator">950.000000</parameter>
          <parameter name="MonochromatorComment2" label="Monochromator comment" group="Monochromator">Default Monochromator Comment</parameter>
          <parameter name="MonochromatorNumFilter2" label="Monochromator # of filters switched" group="Filter">3</parameter>
          <parameter name="MonochromatorFilter2Wavelength1" label="Monochromator constant/setup wavelength" group="Filter">200.000000</parameter>
          <parameter name="MonochromatorFilter2Wavelength2" label="Monochromator constant/setup wavelength" group="Filter">300.000000</parameter>
          <parameter name="MonochromatorFilter2Wavelength3" label="Monochromator constant/setup wavelength" group="Filter">400.000000</parameter>
          <parameter name="MonochromatorFilter2Comment" label="Monochromator filter comment" group="Filter">Default Filter Comment</parameter>
          <parameter name="MonochromatorPolarizerAngle2" label="Monochromator polarization angle" group="Polarizer">90.000000</parameter>
          <parameter name="MonochromatorPolarizerType2" label="Monochromator polarizer type" group="Polarizer">1</parameter>
          <parameter name="MonochromatorPolarizerComment2" label="Monochromator polarizer comment" group="Polarizer">Default Polarizer Comment</parameter>
          <parameter name="PmtConnection1" label="PMT connection" group="PMT">10</parameter>
          <parameter name="PmtGain1" label="PMT gain" group="PMT">1</parameter>
          <parameter name="PmtHighVolt1" label="PMT high voltage" group="PMT">750</parameter>
          <parameter name="PmtOffset1" label="PMT offset status" group="PMT">0</parameter>
          <parameter name="PmtAcquisition1" label="PMT acquisition type" group="PMT">0</parameter>
          <parameter name="PmtComment1" label="PMT comment" group="PMT">Default Pmt Comment</parameter>
          <parameter name="PmtConnection2" label="PMT connection" group="PMT">10</parameter>
          <parameter name="PmtGain2" label="PMT gain" group="PMT">1</parameter>
          <parameter name="PmtHighVolt2" label="PMT high voltage" group="PMT">750</parameter>
          <parameter name="PmtOffset2" label="PMT offset status" group="PMT">0</parameter>
          <parameter name="PmtAcquisition2" label="PMT acquisition type" group="PMT">1</parameter>
          <parameter name="PmtComment2" label="PMT comment" group="PMT">Default Pmt Comment</parameter>
          <parameter name="InstrumentSpecific3" label="Instrument specific information" group="AUX">2</parameter>
          <parameter name="AuxChannelGain1" label="AUX channel gain" group="AUX">0</parameter>
          <parameter name="AuxChannelOffset1" label="AUX channel offset status" group="AUX">0</parameter>
          <parameter name="AuxChannelComment1" label="AUX channel comment" group="AUX">Default Aux Pmt Comment</parameter>
          <parameter name="AuxChannelGain2" label="AUX channel gain" group="AUX">1</parameter>
          <parameter name="AuxChannelOffset2" label="AUX channel offset status" group="AUX">0</parameter>
          <parameter name="AuxChannelComment2" label="AUX channel comment" group="AUX">Default Aux Pmt Comment</parameter>
          <parameter name="PlotInfo" label="Y-axis plotting annotation" group="YAxis">NULL</parameter>
          <parameter name="Corrected" label="Data corrected flag" group="MANIPULATION">0x0</parameter>
          <parameter name="Derivative" label="Degree of derivation" group="MANIPULATION">0</parameter>
          <parameter name="Norm" label="Normalized flag" group="MANIPULATION">0x0</parameter>
          <parameter name="Offset" label="Offset from the original data" group="MANIPULATION">0.000000</parameter>
          <parameter name="Scale" label="Scale from the original data" group="MANIPULATION">1.000000</parameter>
          <parameter name="Smooth" label="# of smoothing passes done" group="MANIPULATION">0</parameter>
          <parameter name="SmoothType" label="Smoothing type" group="MANIPULATION">1.000000</parameter>
          <values byteorder="INTEL" format="FLOAT32" numvalues="201">AADhPAAA4TwAgO08AADXPAAA8DwAANc8AADwPACA1DwAgO08AADcPACA7TwAANc8AADrPAAA3DwA
gPI8AADcPACA7TwAgNk8AADwPAAA1zwAAPA8AIDZPAAA6zwAANI8AADrPACA2TwAAPA8AADcPAAA
8DwAANw8AIDtPAAA3DwAAPA8AADcPACA8jwAANw8AADwPAAA1zwAAPU8AADcPACA8jwAgNk8AIDt
PAAA1zwAgO08AIDjPAAA9TwAANw8AIDyPAAA3DwAgPI8AIDePAAA9TwAAOE8AADwPAAA4TwAgO08
AIDePACA8jwAAOY8AIDyPAAA4TwAAPA8AADhPACA7TwAAOY8AIDtPACA4zwAgO08AADrPACA8jwA
AOE8AAD1PACA3jwAgPI8AIDtPAAA+jwAgPI8AIAEPQCA9zwAwAU9AIAEPQCAEz0AQBc9AAAvPQDA
PD0AAFw9AMBzPQAAlj0AoM09ABA9PgBQwz4ACCw/AMaJPwB0vT8Aiug/AMT0PwAE4j8ARMU/AKCR
PwA8SD8AtAc/AADSPgBIvD4AOMI+ABi/PgDIvj4A+Ls+APC+PgBovz4AkL8+ALi/PgBYxT4AaMQ+
ALDHPgCQzj4AeNI+ACDVPgCI2z4AwN8+ADjgPgDA3z4AmOk+AMjhPgCo4z4AeOE+AHjmPgD42T4A
qNk+AEDdPgCI1j4AiMc+AFDNPgCQxD4AgMA+AJC/PgB4vj4A4LA+ALiwPgBwsj4AQKs+APCqPgBA
qz4AWKc+AKClPgDgpj4AUKo+AHilPgCwpD4AcJ4+AJiZPgCIlT4A2JA+AMCKPgCAiT4AkIM+ACiH
PgDAeD4AYHQ+AFBwPgAAcD4AkF0+AEBdPgBAWD4AUFI+ADBKPgBwRj4AMEA+AGA9PgCAMT4A4DU+
AIAxPgBgKT4AICM+ACAoPgCgGz4AACA+AEAXPgDgFz4AUAw+ALAVPgBgCz4AMAk+AGABPgCABD4A
IAA+AEDxPQAA8D0AgO09AIDjPQBg6j0AwNo9ACDfPQAA0j0AINU9ACDGPQDgyT0AgLs9AOC6PQBg
sz0AYLM9
          </values>
        </Ydata>
        <Ydata label="Y" units="UNKNOWN">
          <parameter name="DataTags" label="# of data points with tags" group="YAxis">0</parameter>
          <parameter name="Channel" label="Instrument channel stored" group="YAxis">10</parameter>
          <parameter name="MaxX" label="X-axis of the data set maximum" group="YAxis">401.000000</parameter>
          <parameter name="MaxY" label="Data set maximum" group="YAxis">1.804810</parameter>
          <parameter name="MinX" label="X-axis of the data set minimum" group="YAxis">307.000000</parameter>
          <parameter name="MinY" label="Data set minimum" group="YAxis">0.025024</parameter>
          <parameter name="YaxisType" label="Y-axis type" group="YAxis">0x12 = Intensity</parameter>
          <parameter name="YaxisUnit" label="Y-axis unit" group="YAxis">0x0 = Unknown</parameter>
          <parameter name="YAxisLL" label="Y-axis lower limit" group="YAxis">0.025024</parameter>
          <parameter name="YAxisUL" label="Y-axis upper limit" group="YAxis">1.804810</parameter>
          <parameter name="LabelY" label="Y-axis Label" group="YAxis">Y</parameter>
          <parameter name="AcqStarted" label="Date and Time the acquisition was started" group="Standard">0000-00-00 00:00:00</parameter>
          <parameter name="AcqCompleted" label="Date and Time the acquisition was completed" group="Standard">0000-00-00 00:00:00</parameter>
          <parameter name="ID" label="Acquiring instruments serial (ID) number" group="Standard">0</parameter>
          <parameter name="Instrument" label="Instrument type acquired on" group="Standard">7</parameter>
          <parameter name="Modified" label="Date and Time the acquisition was modified" group="Standard">0000-00-00 00:00:00</parameter>
          <parameter name="OSVersion" label="Operating system version" group="Standard">0.000000</parameter>
          <parameter name="Version" label="Program version (&apos;C&apos;) acquired on" group="Standard">0.000000</parameter>
          <parameter name="Comment" label="Acquisition comment" group="Standard">NULL</parameter>
          <parameter name="AcqTitle" label="Acquisition title" group="Standard">NULL</parameter>
          <parameter name="User" label="User who acquired this data" group="Standard">NULL</parameter>
          <parameter name="Monochromators" label="# of monochromators attached" group="Hardware">2</parameter>
          <parameter name="Pmts" label="# of PMTs attached" group="Hardware">2</parameter>
          <parameter name="Lamp" label="Excitation light source type" group="LAMP">1</parameter>
          <parameter name="LampComment" label="Excitation light source comment" group="LAMP">Default Lamp Comment</parameter>
          <parameter name="MonochromatorAt1" label="Monochromator constant/setup wavelength" group="Monochromator">300.000000</parameter>
          <parameter name="MonochromatorShutter1" label="Monochromator shutter status" group="Monochromator">0</parameter>
          <parameter name="MonochromatorSlitIn1" label="Monochromator input slit width" group="Monochromator">4.000000</parameter>
          <parameter name="MonochromatorSlitOut1" label="Monochromator output slit width" group="Monochromator">4.000000</parameter>
          <parameter name="MonochromatorStepSize1" label="Monochromator step size" group="Monochromator">0.200000</parameter>
          <parameter name="MonochromatorType1" label="Monochromator type (excitation or emission)" group="Monochromator">10</parameter>
          <parameter name="MonochromatorUpperLimit1" label="Monochromator upper limit" group="Monochromator">950.000000</parameter>
          <parameter name="MonochromatorComment1" label="Monochromator comment" group="Monochromator">Default Monochromator Comment</parameter>
          <parameter name="MonochromatorNumFilter1" label="Monochromator # of filters switched" group="Filter">2</parameter>
          <parameter name="MonochromatorFilter1Wavelength1" label="Monochromator constant/setup wavelength" group="Filter">200.000000</parameter>
          <parameter name="MonochromatorFilter1Wavelength2" label="Monochromator constant/setup wavelength" group="Filter">300.000000</parameter>
          <parameter name="MonochromatorFilter1Comment" label="Monochromator filter comment" group="Filter">Default Filter Comment</parameter>
          <parameter name="MonochromatorPolarizerAngle1" label="Monochromator polarization angle" group="Polarizer">0.000000</parameter>
          <parameter name="MonochromatorPolarizerType1" label="Monochromator polarizer type" group="Polarizer">1</parameter>
          <parameter name="MonochromatorPolarizerComment1" label="Monochromator polarizer comment" group="Polarizer">Default Polarizer Comment</parameter>
          <parameter name="MonochromatorAt2" label="Monochromator constant/setup wavelength" group="Monochromator">400.000000</parameter>
          <parameter name="MonochromatorShutter2" label="Monochromator shutter status" group="Monochromator">0</parameter>
          <parameter name="MonochromatorSlitIn2" label="Monochromator input slit width" group="Monochromator">4.000000</parameter>
          <parameter name="MonochromatorSlitOut2" label="Monochromator output slit width" group="Monochromator">4.000000</parameter>
          <parameter name="MonochromatorStepSize2" label="Monochromator step size" group="Monochromator">0.200000</parameter>
          <parameter name="MonochromatorType2" label="Monochromator type (excitation or emission)" group="Monochromator">8</parameter>
          <parameter name="MonochromatorUpperLimit2" label="Monochromator upper limit" group="Monochromator">950.000000</parameter>
          <parameter name="MonochromatorComment2" label="Monochromator comment" group="Monochromator">Default Monochromator Comment</parameter>
          <parameter name="MonochromatorNumFilter2" label="Monochromator # of filters switched" group="Filter">3</parameter>
          <parameter name="MonochromatorFilter2Wavelength1" label="Monochromator constant/setup wavelength" group="Filter">200.000000</parameter>
          <parameter name="MonochromatorFilter2Wavelength2" label="Monochromator constant/setup wavelength" group="Filter">300.000000</parameter>
          <parameter name="MonochromatorFilter2Wavelength3" label="Monochromator constant/setup wavelength" group="Filter">400.000000</parameter>
          <parameter name="MonochromatorFilter2Comment" label="Monochromator filter comment" group="Filter">Default Filter Comment</parameter>
          <parameter name="MonochromatorPolarizerAngle2" label="Monochromator polarization angle" group="Polarizer">90.000000</parameter>
          <parameter name="MonochromatorPolarizerType2" label="Monochromator polarizer type" group="Polarizer">1</parameter>
          <parameter name="MonochromatorPolarizerComment2" label="Monochromator polarizer comment" group="Polarizer">Default Polarizer Comment</parameter>
          <parameter name="PmtConnection1" label="PMT connection" group="PMT">10</parameter>
          <parameter name="PmtGain1" label="PMT gain" group="PMT">1</parameter>
          <parameter name="PmtHighVolt1" label="PMT high voltage" group="PMT">750</parameter>
          <parameter name="PmtOffset1" label="PMT offset status" group="PMT">0</parameter>
          <parameter name="PmtAcquisition1" label="PMT acquisition type" group="PMT">0</parameter>
          <parameter name="PmtComment1" label="PMT comment" group="PMT">Default Pmt Comment</parameter>
          <parameter name="PmtConnection2" label="PMT connection" group="PMT">10</parameter>
          <parameter name="PmtGain2" label="PMT gain" group="PMT">1</parameter>
          <parameter name="PmtHighVolt2" label="PMT high voltage" group="PMT">750</parameter>
          <parameter name="PmtOffset2" label="PMT offset status" group="PMT">0</parameter>
          <parameter name="PmtAcquisition2" label="PMT acquisition type" group="PMT">1</parameter>
          <parameter name="PmtComment2" label="PMT comment" group="PMT">Default Pmt Comment</parameter>
          <parameter name="InstrumentSpecific3" label="Instrument specific information" group="AUX">2</parameter>
          <parameter name="AuxChannelGain1" label="AUX channel gain" group="AUX">0</parameter>
          <parameter name="AuxChannelOffset1" label="AUX channel offset status" group="AUX">0</parameter>
          <parameter name="AuxChannelComment1" label="AUX channel comment" group="AUX">Default Aux Pmt Comment</parameter>
          <parameter name="AuxChannelGain2" label="AUX channel gain" group="AUX">1</parameter>
          <parameter name="AuxChannelOffset2" label="AUX channel offset status" group="AUX">0</parameter>
          <parameter name="AuxChannelComment2" label="AUX channel comment" group="AUX">Default Aux Pmt Comment</parameter>
          <parameter name="PlotInfo" label="Y-axis plotting annotation" group="YAxis">NULL</parameter>
          <parameter name="Corrected" label="Data corrected flag" group="MANIPULATION">0x0</parameter>
          <parameter name="Derivative" label="Degree of derivation" group="MANIPULATION">0</parameter>
          <parameter name="Norm" label="Normalized flag" group="MANIPULATION">0x0</parameter>
          <parameter name="Offset" label="Offset from the original data" group="MANIPULATION">0.000000</parameter>
          <parameter name="Scale" label="Scale from the original data" group="MANIPULATION">1.000000</parameter>
          <parameter name="Smooth" label="# of smoothing passes done" group="MANIPULATION">0</parameter>
          <parameter name="SmoothType" label="Smoothing type" group="MANIPULATION">1.000000</parameter>
          <values byteorder="INTEL" format="FLOAT32" numvalues="201">AIDZPACA1DwAAOs8AIDPPACA7TwAgM88AADrPAAAzTwAAPA8AIDUPACA8jwAANI8AIDtPACA1DwA
gO08AADSPAAA8DwAgNQ8AADrPAAA0jwAAPA8AADXPAAA8DwAgNQ8AADwPACA1DwAAPA8AIDZPACA
7TwAANI8AIDtPAAA0jwAgO08AADXPACA8jwAgNk8AADrPAAA0jwAgPI8AADXPAAA8DwAANc8AIDt
PAAA0jwAAPA8AADcPAAA9TwAgNk8AADwPAAA3DwAgPI8AIDePAAA9TwAgN48AIDyPAAA3DwAgPI8
AADcPAAA+jwAgN48AIDtPACA3jwAgO08AIDePACA8jwAgN48AIDtPACA2TwAgOg8AIDjPAAA6zwA
gNk8AADwPACA2TwAgPI8AIDePACA7TwAgOM8AIDyPAAA4TwAgPw8AIDtPACA/DwAgPw8AIAJPQCA
BD0AABY9AAAWPQBAKz0AADQ9AEBTPQCAcj0AYIs9AGCVPQBg4D0AYEc+AIDFPgCcOD8ANI8/AErE
PwA63j8ABOc/ADbJPwDwlj8ArFo/AFwPPwDA0D4AEK4+AAClPgDAoz4AEKk+AKClPgDAqD4AWKI+
AOijPgCgpT4AEKQ+AEijPgDIpT4AkKY+AKCqPgCwqT4AqKw+ALCuPgDYsz4AqLE+ANC2PgAgsj4A
sLM+AOC1PgBAtT4AyK8+AFC0PgBAsD4AwKg+ABimPgCArD4AsJ8+APidPgConT4AYJ8+AHiWPgDI
lj4AUIw+ALCQPgBgiz4A+Ik+AICEPgCohD4AYIY+ALiDPgCAhD4AgIQ+AMCAPgBgfj4AcHg+ALB0
PgBQYT4AkF0+ABBgPgCgUj4A8Eg+APBNPgAwSj4AUD4+ALA4PgBwNz4AMDE+APAqPgDgIT4AUCU+
ALAaPgAQGj4AcBQ+ACAUPgDQDj4AYBA+ALAGPgCwBj4AEAE+AGD+PQBA8T0AQPY9AKDrPQAg8z0A
gOM9AODiPQDA2j0AYNs9AODTPQBA2D0AIMY9AODOPQCgwz0AgMU9AAC5PQBgvT0A4LA9AOC1PQAg
oz0AYKk9
          </values>
        </Ydata>
      </Xdata>
    </trace>
  </experiment>
</GAML>
