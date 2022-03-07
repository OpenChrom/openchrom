/*******************************************************************************
 * Copyright (c) 2021, 2022 Lablicate GmbH.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors:
 * Matthias Mailänder - initial API and implementation
 *******************************************************************************/
package net.openchrom.nmr.converter.supplier.nmrml.internal.v100.model;

import jakarta.xml.bind.JAXBElement;
import jakarta.xml.bind.annotation.XmlElementDecl;
import jakarta.xml.bind.annotation.XmlRegistry;
import javax.xml.namespace.QName;

@XmlRegistry
public class ObjectFactory {

	private final static QName _NmrML_QNAME = new QName("http://nmrml.org/schema", "nmrML");

	public ObjectFactory() {

	}

	public SampleType createSampleType() {

		return new SampleType();
	}

	public SpectrumType createSpectrumType() {

		return new SpectrumType();
	}

	public FirstDimensionProcessingParameterSetType createFirstDimensionProcessingParameterSetType() {

		return new FirstDimensionProcessingParameterSetType();
	}

	public AcquisitionParameterSetMultiDType createAcquisitionParameterSetMultiDType() {

		return new AcquisitionParameterSetMultiDType();
	}

	public NmrMLType createNmrMLType() {

		return new NmrMLType();
	}

	public BondType createBondType() {

		return new BondType();
	}

	public ContactType createContactType() {

		return new ContactType();
	}

	public SpectrumListType createSpectrumListType() {

		return new SpectrumListType();
	}

	public QuantifiedCompoundType createQuantifiedCompoundType() {

		return new QuantifiedCompoundType();
	}

	public SoftwareType createSoftwareType() {

		return new SoftwareType();
	}

	public AcquisitionParameterFileRefListType createAcquisitionParameterFileRefListType() {

		return new AcquisitionParameterFileRefListType();
	}

	public ProcessingParameterFileRefType createProcessingParameterFileRefType() {

		return new ProcessingParameterFileRefType();
	}

	public CVParamWithUnitType createCVParamWithUnitType() {

		return new CVParamWithUnitType();
	}

	public AcquisitionMultiDType createAcquisitionMultiDType() {

		return new AcquisitionMultiDType();
	}

	public UserParamType createUserParamType() {

		return new UserParamType();
	}

	public ContactListType createContactListType() {

		return new ContactListType();
	}

	public SourceFileType createSourceFileType() {

		return new SourceFileType();
	}

	public AtomRefsType createAtomRefsType() {

		return new AtomRefsType();
	}

	public PeakType createPeakType() {

		return new PeakType();
	}

	public ClusterListType createClusterListType() {

		return new ClusterListType();
	}

	public CompoundIdentifierListType createCompoundIdentifierListType() {

		return new CompoundIdentifierListType();
	}

	public InstrumentConfigurationListType createInstrumentConfigurationListType() {

		return new InstrumentConfigurationListType();
	}

	public PulseSequenceType createPulseSequenceType() {

		return new PulseSequenceType();
	}

	public SampleListType createSampleListType() {

		return new SampleListType();
	}

	public PeakListType createPeakListType() {

		return new PeakListType();
	}

	public ValueWithUnitType createValueWithUnitType() {

		return new ValueWithUnitType();
	}

	public SpectrumAnnotationListType createSpectrumAnnotationListType() {

		return new SpectrumAnnotationListType();
	}

	public BinaryDataArrayType createBinaryDataArrayType() {

		return new BinaryDataArrayType();
	}

	public SoftwareRefListType createSoftwareRefListType() {

		return new SoftwareRefListType();
	}

	public QuantificationAnnotationType createQuantificationAnnotationType() {

		return new QuantificationAnnotationType();
	}

	public ProcessingParameterFileRefListType createProcessingParameterFileRefListType() {

		return new ProcessingParameterFileRefListType();
	}

	public AcquisitionType createAcquisitionType() {

		return new AcquisitionType();
	}

	public BondListType createBondListType() {

		return new BondListType();
	}

	public SpectrumRefListType createSpectrumRefListType() {

		return new SpectrumRefListType();
	}

	public ContactRefListType createContactRefListType() {

		return new ContactRefListType();
	}

	public AcquisitionParameterSetType createAcquisitionParameterSetType() {

		return new AcquisitionParameterSetType();
	}

	public AcquisitionParameterFileRefType createAcquisitionParameterFileRefType() {

		return new AcquisitionParameterFileRefType();
	}

	public InstrumentConfigurationType createInstrumentConfigurationType() {

		return new InstrumentConfigurationType();
	}

	public Acquisition1DType createAcquisition1DType() {

		return new Acquisition1DType();
	}

	public SpectralProcessingParameterSet2DType createSpectralProcessingParameterSet2DType() {

		return new SpectralProcessingParameterSet2DType();
	}

	public SpectralProcessingParameterSetType createSpectralProcessingParameterSetType() {

		return new SpectralProcessingParameterSetType();
	}

	public InChiStringType createInChiStringType() {

		return new InChiStringType();
	}

	public AdditionalSoluteListType createAdditionalSoluteListType() {

		return new AdditionalSoluteListType();
	}

	public AxisWithUnitType createAxisWithUnitType() {

		return new AxisWithUnitType();
	}

	public CVTermType createCVTermType() {

		return new CVTermType();
	}

	public AtomAssingmentAnnotationType createAtomAssingmentAnnotationType() {

		return new AtomAssingmentAnnotationType();
	}

	public Spectrum1DType createSpectrum1DType() {

		return new Spectrum1DType();
	}

	public SourceFileRefType createSourceFileRefType() {

		return new SourceFileRefType();
	}

	public AcquisitionDimensionParameterSetType createAcquisitionDimensionParameterSetType() {

		return new AcquisitionDimensionParameterSetType();
	}

	public SoluteType createSoluteType() {

		return new SoluteType();
	}

	public CVListType createCVListType() {

		return new CVListType();
	}

	public ReferenceableParamGroupListType createReferenceableParamGroupListType() {

		return new ReferenceableParamGroupListType();
	}

	public AtomListType createAtomListType() {

		return new AtomListType();
	}

	public ContactRefType createContactRefType() {

		return new ContactRefType();
	}

	public AcquisitionParameterSet1DType createAcquisitionParameterSet1DType() {

		return new AcquisitionParameterSet1DType();
	}

	public ReferenceableParamGroupType createReferenceableParamGroupType() {

		return new ReferenceableParamGroupType();
	}

	public AtomAssignmentListType createAtomAssignmentListType() {

		return new AtomAssignmentListType();
	}

	public SpectrumRefType createSpectrumRefType() {

		return new SpectrumRefType();
	}

	public AtomType createAtomType() {

		return new AtomType();
	}

	public SourceFileListType createSourceFileListType() {

		return new SourceFileListType();
	}

	public CVParamType createCVParamType() {

		return new CVParamType();
	}

	public ChemicalCompoundType createChemicalCompoundType() {

		return new ChemicalCompoundType();
	}

	public FileDescriptionType createFileDescriptionType() {

		return new FileDescriptionType();
	}

	public ParamGroupType createParamGroupType() {

		return new ParamGroupType();
	}

	public MultipletType createMultipletType() {

		return new MultipletType();
	}

	public CompoundDatabaseIdentifierType createCompoundDatabaseIdentifierType() {

		return new CompoundDatabaseIdentifierType();
	}

	public ClusterType createClusterType() {

		return new ClusterType();
	}

	public CompoundStructureType createCompoundStructureType() {

		return new CompoundStructureType();
	}

	public SpectralProjectionParameterSetType createSpectralProjectionParameterSetType() {

		return new SpectralProjectionParameterSetType();
	}

	public SourceFileRefListType createSourceFileRefListType() {

		return new SourceFileRefListType();
	}

	public TemperatureType createTemperatureType() {

		return new TemperatureType();
	}

	public ReferenceableParamGroupRefType createReferenceableParamGroupRefType() {

		return new ReferenceableParamGroupRefType();
	}

	public SoftwareRefType createSoftwareRefType() {

		return new SoftwareRefType();
	}

	public HigherDimensionProcessingParameterSetType createHigherDimensionProcessingParameterSetType() {

		return new HigherDimensionProcessingParameterSetType();
	}

	public CVType createCVType() {

		return new CVType();
	}

	public SoftwareListType createSoftwareListType() {

		return new SoftwareListType();
	}

	public Projected3DProcessingParamaterSetType createProjected3DProcessingParamaterSetType() {

		return new Projected3DProcessingParamaterSetType();
	}

	public SpectrumMultiDType createSpectrumMultiDType() {

		return new SpectrumMultiDType();
	}

	public QuantifiedCompoundListType createQuantifiedCompoundListType() {

		return new QuantifiedCompoundListType();
	}

	public AcquisitionIndirectDimensionParameterSetType createAcquisitionIndirectDimensionParameterSetType() {

		return new AcquisitionIndirectDimensionParameterSetType();
	}

	public SampleType.FieldFrequencyLock createSampleTypeFieldFrequencyLock() {

		return new SampleType.FieldFrequencyLock();
	}

	public SampleType.ConcentrationStandard createSampleTypeConcentrationStandard() {

		return new SampleType.ConcentrationStandard();
	}

	public SpectrumType.ProcessingParameterSet createSpectrumTypeProcessingParameterSet() {

		return new SpectrumType.ProcessingParameterSet();
	}

	public FirstDimensionProcessingParameterSetType.WindowFunction createFirstDimensionProcessingParameterSetTypeWindowFunction() {

		return new FirstDimensionProcessingParameterSetType.WindowFunction();
	}

	public AcquisitionParameterSetMultiDType.HadamardParameterSet createAcquisitionParameterSetMultiDTypeHadamardParameterSet() {

		return new AcquisitionParameterSetMultiDType.HadamardParameterSet();
	}

	@XmlElementDecl(namespace = "http://nmrml.org/schema", name = "nmrML")
	public JAXBElement<NmrMLType> createNmrML(NmrMLType value) {

		return new JAXBElement<NmrMLType>(_NmrML_QNAME, NmrMLType.class, null, value);
	}
}
