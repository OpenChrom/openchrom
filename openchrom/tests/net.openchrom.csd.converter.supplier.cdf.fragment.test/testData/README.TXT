
		  Analytical Instrument Association
           Analytical Data Interchange for Mass Spectrometry
	           ANDI/MS Round-Robin Test Files

			  21 September 1993

Data files for the andi/MS round-robin test are now stored in subdirectories,
by vendor name.  Here is the current structure of these subdirectories:

~/data
      /extrel		(Millipore)
      /finnigan	
      /fisons_a		(Fisons Instruments/VG Analytical)
      /hp		(Hewlett-Packard)
      /kratos		(Shimadzu/Kratos Analytical)
      /pe_sciex
      /teknivnt

In addition, there is a "test" subdirectory, which contains the two test CDF
files (and their CDL counterparts) included with the original source
distribution.

Files submitted by each vendor are listed below; this list is complete as of
the date above:

extrel:
	centscnh.cdf	centsidh.cdf	continum.cdf	readme
	centscnl.cdf	centsidl.cdf	library.cdf

finnigan:
	centscnh.cdf	centscnl.cdf	continum.cdf	library.cdf
	readme

fisons_a:	(Fisons/VG Analytical)
	centscnh.cdf	centscnl.cdf	centsidh.cdf	continum.cdf
	library.cdf	readme

hp:		(Hewlett-Packard)
	centscnh.cdf	centsidh.cdf

kratos:		(Shimadzu)
	centscnh.cdf	centscnl.cdf	readme

pe_sciex:
	centscnh.cdf	centsidh.cdf	continum.cdf
	centscnl.cdf	centsidl.cdf	readme

teknivnt:
	centscnh.cdf	centscnl.cdf	continum.cdf	readme

test:	(original test files)
	nist_50.cdf	nist_50.cdl	qd.cdf		qd.cdl
	readme

The naming convention for these files is as follows; all files could
contain multiple scans:

	centscnh.cdf:	High-resolution (accurate mass) centroided
	centscnl.cdf:	Low-resolution (nominal mass) centroided
	centsidh.cdf:	High-resolution (accurate mass) SID/SIM/SIR
	centsidl.cdf:	Low-resolution (nominal mass) SID/SIM/SIR
	continum.cdf:	Continuum/profile (accurate mass)
	library.cdf:	Spectral library

Only those manufacturers who actively participated in the round-robin
(i.e. submitted files) are represented here.  Because of differing
capabilities of the various data system offerings, not all file types
are included for each manufacturer.  The README files in the
subdirectories give a bit of information regarding the file contents.

Even if a manufacturer's data system does not support data of a given
type, a proper implementation of an ANDI/MS program should fail
gracefully when presented with an unsupported type.  For example,
rather than crash and burn, a program should post a message to the
effect of "this ANDI/MS file type is not supported by this program"
before exiting.
