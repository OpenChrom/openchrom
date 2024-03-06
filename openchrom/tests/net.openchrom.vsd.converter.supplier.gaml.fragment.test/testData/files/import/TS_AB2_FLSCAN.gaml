<?xml version="1.0" encoding="UTF-8"?>
<GAML version="1.20" name="QUININE">
  <integrity algorithm="SHA1">b924729dc832961d2a0f226cfec4220da67cf19d</integrity>
  <parameter name="component_name" label="Component name" group="GAML Generation">GAMLIO</parameter>
  <parameter name="component_version" label="Component version" group="GAML Generation">9.1.3.6</parameter>
  <parameter name="converter_name" label="Converter name" group="Data Conversion">SLM_BWM2</parameter>
  <parameter name="converter_description" label="Converter description" group="Data Conversion">Thermo Spectronic SLM-Aminco Bowman Series 2</parameter>
  <parameter name="converter_version" label="Converter version" group="Data Conversion">6.1.0.1</parameter>
  <parameter name="conversion_date" label="Conversion date" group="Data Conversion">2010-09-16 11:12:28</parameter>
  <parameter name="converter_input_source" label="Converter input source" group="Data Conversion">D:\temp\GAMLORG\Thermo Spectronic AB2 Fluorometer\rawdata\QUININE.DAT</parameter>
  <parameter name="converter_output_file" label="Converter output file name" group="Data Conversion">C:\Temp\GCnvtstep1-1738177c1\TS_AB2_FLSCAN.gaml</parameter>
  <experiment name="Em scan">
    <collectdate>1992-08-13T18:43:10Z</collectdate>
    <parameter name="Title" label="Data record title" group="Header">Em scan</parameter>
    <parameter name="DataFileSignature" label="Data file signature" group="Header">SLM Data File, Format 1.3
</parameter>
    <parameter name="Format" label="Data record format" group="Header">4</parameter>
    <parameter name="Axes" label="Number of X-axes in this data record" group="Header">1</parameter>
    <parameter name="Comment" label="Data record comments" group="Header">NULL</parameter>
    <trace name="Em scan" technique="FLUOR">
      <parameter name="ZaxisType" label="Z-axis type" group="ZAxis">0x3 = Excitation Monochromator</parameter>
      <parameter name="ZaxisUnits" label="Z-axis units" group="ZAxis">0x2 = Nanometers (nm)</parameter>
      <parameter name="ZaxisLL" label="Z-axis lower limit" group="ZAxis">351.000000</parameter>
      <parameter name="ZaxisUL" label="Z-axis upper limit" group="ZAxis">351.000000</parameter>
      <parameter name="LabelZ" label="Z-axis Label" group="ZAxis">Excitation (nm)</parameter>
      <parameter name="PlotInfo" label="Z-axis plotting annotation" group="ZAxis">NULL</parameter>
      <coordinates label="Excitation (nm)" units="NANOMETERS" valueorder="EVEN">
        <values byteorder="INTEL" format="FLOAT32" numvalues="1">AICvQw==
        </values>
      </coordinates>
      <Xdata label="Emission (nm)" units="NANOMETERS" valueorder="EVEN">
        <parameter name="DataType" label="Type of data stored" group="XAxis">0x01 = Normal Data</parameter>
        <parameter name="Incs" label="# of different X-axis increments" group="XAxis">1</parameter>
        <parameter name="Channels" label="# of Y-axis associated with this X-axis" group="XAxis">1</parameter>
        <parameter name="Points" label="# of data points along the X-axis" group="XAxis">301</parameter>
        <parameter name="XaxisType" label="X-axis type" group="XAxis">0x2 = Time-Dependent Emission Monochromator</parameter>
        <parameter name="XaxisUnits" label="X-axis units" group="XAxis">0x2 = Nanometers (nm)</parameter>
        <parameter name="XaxisLL" label="X-axis lower limit" group="XAxis">375.000000</parameter>
        <parameter name="XaxisUL" label="X-axis upper limit" group="XAxis">675.000000</parameter>
        <parameter name="XFlag" label="X-axis values included flag" group="XAxis">0x0</parameter>
        <parameter name="Zaxis" label="Z-axis values for this X-axis" group="XAxis">351.000000</parameter>
        <parameter name="LabelX" label="X-axis Label" group="XAxis">Emission (nm)</parameter>
        <parameter name="Inc" label="Data point increment" group="XAxis">1.000000</parameter>
        <parameter name="Header" label="Data type specific header info" group="XAxis">0</parameter>
        <parameter name="PlotInfo" label="X-axis plotting annotation" group="XAxis">NULL</parameter>
        <values byteorder="INTEL" format="FLOAT32" numvalues="301">AIC7QwAAvEMAgLxDAAC9QwCAvUMAAL5DAIC+QwAAv0MAgL9DAADAQwCAwEMAAMFDAIDBQwAAwkMA
gMJDAADDQwCAw0MAAMRDAIDEQwAAxUMAgMVDAADGQwCAxkMAAMdDAIDHQwAAyEMAgMhDAADJQwCA
yUMAAMpDAIDKQwAAy0MAgMtDAADMQwCAzEMAAM1DAIDNQwAAzkMAgM5DAADPQwCAz0MAANBDAIDQ
QwAA0UMAgNFDAADSQwCA0kMAANNDAIDTQwAA1EMAgNRDAADVQwCA1UMAANZDAIDWQwAA10MAgNdD
AADYQwCA2EMAANlDAIDZQwAA2kMAgNpDAADbQwCA20MAANxDAIDcQwAA3UMAgN1DAADeQwCA3kMA
AN9DAIDfQwAA4EMAgOBDAADhQwCA4UMAAOJDAIDiQwAA40MAgONDAADkQwCA5EMAAOVDAIDlQwAA
5kMAgOZDAADnQwCA50MAAOhDAIDoQwAA6UMAgOlDAADqQwCA6kMAAOtDAIDrQwAA7EMAgOxDAADt
QwCA7UMAAO5DAIDuQwAA70MAgO9DAADwQwCA8EMAAPFDAIDxQwAA8kMAgPJDAADzQwCA80MAAPRD
AID0QwAA9UMAgPVDAAD2QwCA9kMAAPdDAID3QwAA+EMAgPhDAAD5QwCA+UMAAPpDAID6QwAA+0MA
gPtDAAD8QwCA/EMAAP1DAID9QwAA/kMAgP5DAAD/QwCA/0MAAABEAEAARACAAEQAwABEAAABRABA
AUQAgAFEAMABRAAAAkQAQAJEAIACRADAAkQAAANEAEADRACAA0QAwANEAAAERABABEQAgAREAMAE
RAAABUQAQAVEAIAFRADABUQAAAZEAEAGRACABkQAwAZEAAAHRABAB0QAgAdEAMAHRAAACEQAQAhE
AIAIRADACEQAAAlEAEAJRACACUQAwAlEAAAKRABACkQAgApEAMAKRAAAC0QAQAtEAIALRADAC0QA
AAxEAEAMRACADEQAwAxEAAANRABADUQAgA1EAMANRAAADkQAQA5EAIAORADADkQAAA9EAEAPRACA
D0QAwA9EAAAQRABAEEQAgBBEAMAQRAAAEUQAQBFEAIARRADAEUQAABJEAEASRACAEkQAwBJEAAAT
RABAE0QAgBNEAMATRAAAFEQAQBREAIAURADAFEQAABVEAEAVRACAFUQAwBVEAAAWRABAFkQAgBZE
AMAWRAAAF0QAQBdEAIAXRADAF0QAABhEAEAYRACAGEQAwBhEAAAZRABAGUQAgBlEAMAZRAAAGkQA
QBpEAIAaRADAGkQAABtEAEAbRACAG0QAwBtEAAAcRABAHEQAgBxEAMAcRAAAHUQAQB1EAIAdRADA
HUQAAB5EAEAeRACAHkQAwB5EAAAfRABAH0QAgB9EAMAfRAAAIEQAQCBEAIAgRADAIEQAACFEAEAh
RACAIUQAwCFEAAAiRABAIkQAgCJEAMAiRAAAI0QAQCNEAIAjRADAI0QAACREAEAkRACAJEQAwCRE
AAAlRABAJUQAgCVEAMAlRAAAJkQAQCZEAIAmRADAJkQAACdEAEAnRACAJ0QAwCdEAAAoRABAKEQA
gChEAMAoRA==
        </values>
        <Ydata label="Fluorescence" units="UNKNOWN">
          <parameter name="DataTags" label="# of data points with tags" group="YAxis">0</parameter>
          <parameter name="Channel" label="Instrument channel stored" group="YAxis">4682</parameter>
          <parameter name="MaxX" label="X-axis of the data set maximum" group="YAxis">447.000000</parameter>
          <parameter name="MaxY" label="Data set maximum" group="YAxis">53.951843</parameter>
          <parameter name="MinX" label="X-axis of the data set minimum" group="YAxis">675.000000</parameter>
          <parameter name="MinY" label="Data set minimum" group="YAxis">0.038076</parameter>
          <parameter name="YaxisType" label="Y-axis type" group="YAxis">0x12 = Intensity</parameter>
          <parameter name="YaxisUnit" label="Y-axis unit" group="YAxis">0x0 = Unknown</parameter>
          <parameter name="YAxisLL" label="Y-axis lower limit" group="YAxis">0.038076</parameter>
          <parameter name="YAxisUL" label="Y-axis upper limit" group="YAxis">53.951843</parameter>
          <parameter name="LabelY" label="Y-axis Label" group="YAxis">Fluorescence</parameter>
          <parameter name="AcqStarted" label="Date and Time the acquisition was started" group="Standard">1992-08-13 14:21:44</parameter>
          <parameter name="AcqCompleted" label="Date and Time the acquisition was completed" group="Standard">1992-08-13 14:41:49</parameter>
          <parameter name="ID" label="Acquiring instruments serial (ID) number" group="Standard">0</parameter>
          <parameter name="Instrument" label="Instrument type acquired on" group="Standard">7</parameter>
          <parameter name="Modified" label="Date and Time the acquisition was modified" group="Standard">0000-00-00 00:00:00</parameter>
          <parameter name="OSVersion" label="Operating system version" group="Standard">1.200000</parameter>
          <parameter name="Version" label="Program version (&apos;C&apos;) acquired on" group="Standard">1.270000</parameter>
          <parameter name="Comment" label="Acquisition comment" group="Standard">NULL</parameter>
          <parameter name="AcqTitle" label="Acquisition title" group="Standard">NULL</parameter>
          <parameter name="User" label="User who acquired this data" group="Standard">None</parameter>
          <parameter name="Monochromators" label="# of monochromators attached" group="Hardware">2</parameter>
          <parameter name="Pmts" label="# of PMTs attached" group="Hardware">1</parameter>
          <parameter name="Lamp" label="Excitation light source type" group="LAMP">1</parameter>
          <parameter name="LampComment" label="Excitation light source comment" group="LAMP">Default Lamp Comment</parameter>
          <parameter name="MonochromatorAt1" label="Monochromator constant/setup wavelength" group="Monochromator">460.000000</parameter>
          <parameter name="MonochromatorShutter1" label="Monochromator shutter status" group="Monochromator">1</parameter>
          <parameter name="MonochromatorSlitIn1" label="Monochromator input slit width" group="Monochromator">4.000000</parameter>
          <parameter name="MonochromatorSlitOut1" label="Monochromator output slit width" group="Monochromator">4.000000</parameter>
          <parameter name="MonochromatorStepSize1" label="Monochromator step size" group="Monochromator">1.000000</parameter>
          <parameter name="MonochromatorType1" label="Monochromator type (excitation or emission)" group="Monochromator">0</parameter>
          <parameter name="MonochromatorUpperLimit1" label="Monochromator upper limit" group="Monochromator">950.000000</parameter>
          <parameter name="MonochromatorComment1" label="Monochromator comment" group="Monochromator">Default Monochromator Comment</parameter>
          <parameter name="MonochromatorNumFilter1" label="Monochromator # of filters switched" group="Filter">2</parameter>
          <parameter name="MonochromatorFilter1Wavelength1" label="Monochromator constant/setup wavelength" group="Filter">200.000000</parameter>
          <parameter name="MonochromatorFilter1Wavelength2" label="Monochromator constant/setup wavelength" group="Filter">300.000000</parameter>
          <parameter name="MonochromatorFilter1Comment" label="Monochromator filter comment" group="Filter">Default Filter Comment</parameter>
          <parameter name="MonochromatorPolarizerAngle1" label="Monochromator polarization angle" group="Polarizer">0.000000</parameter>
          <parameter name="MonochromatorPolarizerType1" label="Monochromator polarizer type" group="Polarizer">1</parameter>
          <parameter name="MonochromatorPolarizerComment1" label="Monochromator polarizer comment" group="Polarizer">Default Polarizer Comment</parameter>
          <parameter name="MonochromatorAt2" label="Monochromator constant/setup wavelength" group="Monochromator">351.000000</parameter>
          <parameter name="MonochromatorShutter2" label="Monochromator shutter status" group="Monochromator">1</parameter>
          <parameter name="MonochromatorSlitIn2" label="Monochromator input slit width" group="Monochromator">4.000000</parameter>
          <parameter name="MonochromatorSlitOut2" label="Monochromator output slit width" group="Monochromator">4.000000</parameter>
          <parameter name="MonochromatorStepSize2" label="Monochromator step size" group="Monochromator">1.000000</parameter>
          <parameter name="MonochromatorType2" label="Monochromator type (excitation or emission)" group="Monochromator">1</parameter>
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
          <parameter name="PmtConnection1" label="PMT connection" group="PMT">0</parameter>
          <parameter name="PmtGain1" label="PMT gain" group="PMT">1</parameter>
          <parameter name="PmtHighVolt1" label="PMT high voltage" group="PMT">665</parameter>
          <parameter name="PmtOffset1" label="PMT offset status" group="PMT">0</parameter>
          <parameter name="PmtAcquisition1" label="PMT acquisition type" group="PMT">0</parameter>
          <parameter name="PmtComment1" label="PMT comment" group="PMT">Default Pmt Comment</parameter>
          <parameter name="InstrumentSpecific2" label="Instrument specific information" group="AUX">0</parameter>
          <parameter name="PlotInfo" label="Y-axis plotting annotation" group="YAxis">NULL</parameter>
          <parameter name="Corrected" label="Data corrected flag" group="MANIPULATION">0x0</parameter>
          <parameter name="Derivative" label="Degree of derivation" group="MANIPULATION">0</parameter>
          <parameter name="Norm" label="Normalized flag" group="MANIPULATION">0x0</parameter>
          <parameter name="Offset" label="Offset from the original data" group="MANIPULATION">0.000000</parameter>
          <parameter name="Scale" label="Scale from the original data" group="MANIPULATION">1.000000</parameter>
          <parameter name="Smooth" label="# of smoothing passes done" group="MANIPULATION">0</parameter>
          <parameter name="SmoothType" label="Smoothing type" group="MANIPULATION">1.000000</parameter>
          <values byteorder="INTEL" format="FLOAT32" numvalues="301">bVSRPsDCrD5Jb84+mwL6PkViFz+nmDY/mpVZP5eZgD/aG5k/GeqzPy6v0z8oC/Y/QPUOQMLbJUCt
fT9A6WteQFN4gEAdBZNA5PeoQKwawEAAk9hAJzHyQG/cBUEmORNB5rUgQcs7LUGucjtBvg5JQffE
V0HaEWhB7Bp4Qe0khUEO4Y5BIUGYQSNwokE9W6xBOZe2QeKAwUE20stBLnXWQcLH4UGFeOtBszP3
QSCGAEIJrAVC1wcLQuH2D0KWHxVCvUkaQpV3HkKKhCNCWaAnQi35K0LFMzBCzTA0QgTCN0LcwTtC
/Fo+QoeDQkI82ERCEpZHQpMnSkLoZ0xCf1FOQnTAUEJGmFFCYtpTQhFnVEICrlVCgXZWQlAJV0LE
FVdCsM5XQtjIVkKwbldC3U1WQnLTVULDO1VCMAxUQjn7UkKbE1JCnOdPQt90T0KSnkxCm25LQlgn
SUIHFEdCQdxEQjonQ0IT9j9C5m8+QtVNO0IeyjhC2hc2QoCpM0KBgzBC8GEuQp2fKkIZwShC+O8k
QtlnIkIJZR9CxKgcQo52GULeBhdCx1ETQmYxEUK1sg1C2P8KQnYjCEIFYQVCvjcCQtzj/0EZJPlB
UW31Qdgb70ExT+tBG7DmQTBq4kGTnt1BqaDYQYZ30UE5cstBwvjCQUviu0H2dbNBjkarQSEwokF7
RppBTA2SQatyi0HGmIRByTt/QckCd0GJtm9BZr9oQbsuZEEg+l1B+N5ZQd0IVEEgBFBBpOdKQUFQ
RkFYbEFBfCI9QdCpN0HRlDNBsDAuQaW7KUFZ8CRBh1ogQTF2G0E4RRdB2T4SQWkCD0GmvglBHe8F
QYbdAUGIH/xApUnzQL737EDoFORAqBjeQKzv1UD5ds9ALwbIQPsFwkBb27tAX5W1QAf3rkB9TKpA
hJGjQGCVnkCrwJhABymUQLyHjkDeXIpAoQiFQOrcgEAQ0XdAA/twQMgVaEBAMWBAc2pYQJe7UUD+
j0hAJWJDQNxeO0BD9jRAsR4vQHWxKECoEiJAVkMdQM45F0DBChJAJ6kMQGcgCEBsnwJAWAH9P/cd
9D9op+s/B2niPw8r3D9ZHtI/urbKPygrxD+IOr4/Jii2Py0SsD9Nbak/4BakPyc3nT+aCJg/JumR
P0e8jT+UQIg/ZFeDP2ndfD9nq3U/L8NrP/cbYz8q21o/KHxTP1DvSz/uW0U/YFk8Pw0vOD+g0i8/
BZkqP1/dIz9fcR0/770XP1IXEz/DKg0/qmoIP5zIAz+O5P0+EZDzPlNK6T4ou+I+7QPbPseJ0j5f
O8s+byDDPuxIvT5nUbQ+hD6vPqVypz5BIaI+UU+aPq6IlT58TY8+/OCKPjSnhD5rxH4+gTt1Podz
bD6wbmQ+wRNYPjVDTz5K10w+t6VEPjVeOj4+jTA+ZR0uPpuTKD7M9x8+JrQXProEFD506A0+dMsJ
Ps4JAj77bv89hADvPeAt5z0Gk9o9T+3aPZYJyj3Y38U9Tsi9PXTOuT3DZ7E9RpyoPcOkoD2Nrpg9
4meUPVhqjD05KYw9KviDPZBmdj0C8nY90FZvPeA8Xz1TflY94zdJPS+zQj0dD0Q9ECc8PQtVND1u
3yM9vfUbPQ==
          </values>
        </Ydata>
      </Xdata>
    </trace>
  </experiment>
</GAML>
