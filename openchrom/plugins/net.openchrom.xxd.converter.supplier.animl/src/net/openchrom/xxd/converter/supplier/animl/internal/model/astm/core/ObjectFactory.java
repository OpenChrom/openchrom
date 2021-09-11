/*******************************************************************************
 * Copyright (c) 2021 Lablicate GmbH.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 * Matthias Mailänder - initial API and implementation
 *******************************************************************************/
package net.openchrom.xxd.converter.supplier.animl.internal.model.astm.core;

import javax.xml.bind.JAXBElement;
import javax.xml.bind.annotation.XmlElementDecl;
import javax.xml.bind.annotation.XmlIDREF;
import javax.xml.bind.annotation.XmlRegistry;
import javax.xml.bind.annotation.adapters.CollapsedStringAdapter;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;
import javax.xml.datatype.XMLGregorianCalendar;
import javax.xml.namespace.QName;

import net.openchrom.xxd.converter.supplier.animl.internal.model.w3.SignatureType;

@XmlRegistry
public class ObjectFactory {

	private final static QName _DateTime_QNAME = new QName("urn:org:astm:animl:schema:core:draft:0.90", "DateTime");
	private final static QName _Template_QNAME = new QName("urn:org:astm:animl:schema:core:draft:0.90", "Template");
	private final static QName _Result_QNAME = new QName("urn:org:astm:animl:schema:core:draft:0.90", "Result");
	private final static QName _Name_QNAME = new QName("urn:org:astm:animl:schema:core:draft:0.90", "Name");
	private final static QName _Unit_QNAME = new QName("urn:org:astm:animl:schema:core:draft:0.90", "Unit");
	private final static QName _PNG_QNAME = new QName("urn:org:astm:animl:schema:core:draft:0.90", "PNG");
	private final static QName _OldValue_QNAME = new QName("urn:org:astm:animl:schema:core:draft:0.90", "OldValue");
	private final static QName _Diff_QNAME = new QName("urn:org:astm:animl:schema:core:draft:0.90", "Diff");
	private final static QName _Email_QNAME = new QName("urn:org:astm:animl:schema:core:draft:0.90", "Email");
	private final static QName _Affiliation_QNAME = new QName("urn:org:astm:animl:schema:core:draft:0.90", "Affiliation");
	private final static QName _Boolean_QNAME = new QName("urn:org:astm:animl:schema:core:draft:0.90", "Boolean");
	private final static QName _ExperimentStepSet_QNAME = new QName("urn:org:astm:animl:schema:core:draft:0.90", "ExperimentStepSet");
	private final static QName _Manufacturer_QNAME = new QName("urn:org:astm:animl:schema:core:draft:0.90", "Manufacturer");
	private final static QName _AutoIncrementedValueSet_QNAME = new QName("urn:org:astm:animl:schema:core:draft:0.90", "AutoIncrementedValueSet");
	private final static QName _Version_QNAME = new QName("urn:org:astm:animl:schema:core:draft:0.90", "Version");
	private final static QName _DeviceIdentifier_QNAME = new QName("urn:org:astm:animl:schema:core:draft:0.90", "DeviceIdentifier");
	private final static QName _ExperimentDataReference_QNAME = new QName("urn:org:astm:animl:schema:core:draft:0.90", "ExperimentDataReference");
	private final static QName _Method_QNAME = new QName("urn:org:astm:animl:schema:core:draft:0.90", "Method");
	private final static QName _Device_QNAME = new QName("urn:org:astm:animl:schema:core:draft:0.90", "Device");
	private final static QName _NewValue_QNAME = new QName("urn:org:astm:animl:schema:core:draft:0.90", "NewValue");
	private final static QName _Software_QNAME = new QName("urn:org:astm:animl:schema:core:draft:0.90", "Software");
	private final static QName _Technique_QNAME = new QName("urn:org:astm:animl:schema:core:draft:0.90", "Technique");
	private final static QName _Author_QNAME = new QName("urn:org:astm:animl:schema:core:draft:0.90", "Author");
	private final static QName _SerialNumber_QNAME = new QName("urn:org:astm:animl:schema:core:draft:0.90", "SerialNumber");
	private final static QName _SeriesSet_QNAME = new QName("urn:org:astm:animl:schema:core:draft:0.90", "SeriesSet");
	private final static QName _SIUnit_QNAME = new QName("urn:org:astm:animl:schema:core:draft:0.90", "SIUnit");
	private final static QName _Role_QNAME = new QName("urn:org:astm:animl:schema:core:draft:0.90", "Role");
	private final static QName _SignatureSet_QNAME = new QName("urn:org:astm:animl:schema:core:draft:0.90", "SignatureSet");
	private final static QName _Reason_QNAME = new QName("urn:org:astm:animl:schema:core:draft:0.90", "Reason");
	private final static QName _AuditTrailEntrySet_QNAME = new QName("urn:org:astm:animl:schema:core:draft:0.90", "AuditTrailEntrySet");
	private final static QName _ExperimentDataBulkReference_QNAME = new QName("urn:org:astm:animl:schema:core:draft:0.90", "ExperimentDataBulkReference");
	private final static QName _L_QNAME = new QName("urn:org:astm:animl:schema:core:draft:0.90", "L");
	private final static QName _Timestamp_QNAME = new QName("urn:org:astm:animl:schema:core:draft:0.90", "Timestamp");
	private final static QName _StartValue_QNAME = new QName("urn:org:astm:animl:schema:core:draft:0.90", "StartValue");
	private final static QName _I_QNAME = new QName("urn:org:astm:animl:schema:core:draft:0.90", "I");
	private final static QName _EmbeddedXML_QNAME = new QName("urn:org:astm:animl:schema:core:draft:0.90", "EmbeddedXML");
	private final static QName _F_QNAME = new QName("urn:org:astm:animl:schema:core:draft:0.90", "F");
	private final static QName _D_QNAME = new QName("urn:org:astm:animl:schema:core:draft:0.90", "D");
	private final static QName _EncodedValueSet_QNAME = new QName("urn:org:astm:animl:schema:core:draft:0.90", "EncodedValueSet");
	private final static QName _SVG_QNAME = new QName("urn:org:astm:animl:schema:core:draft:0.90", "SVG");
	private final static QName _Action_QNAME = new QName("urn:org:astm:animl:schema:core:draft:0.90", "Action");
	private final static QName _Category_QNAME = new QName("urn:org:astm:animl:schema:core:draft:0.90", "Category");
	private final static QName _OperatingSystem_QNAME = new QName("urn:org:astm:animl:schema:core:draft:0.90", "OperatingSystem");
	private final static QName _SampleReferenceSet_QNAME = new QName("urn:org:astm:animl:schema:core:draft:0.90", "SampleReferenceSet");
	private final static QName _EndValue_QNAME = new QName("urn:org:astm:animl:schema:core:draft:0.90", "EndValue");
	private final static QName _Infrastructure_QNAME = new QName("urn:org:astm:animl:schema:core:draft:0.90", "Infrastructure");
	private final static QName _ExperimentStep_QNAME = new QName("urn:org:astm:animl:schema:core:draft:0.90", "ExperimentStep");
	private final static QName _TagSet_QNAME = new QName("urn:org:astm:animl:schema:core:draft:0.90", "TagSet");
	private final static QName _Phone_QNAME = new QName("urn:org:astm:animl:schema:core:draft:0.90", "Phone");
	private final static QName _Increment_QNAME = new QName("urn:org:astm:animl:schema:core:draft:0.90", "Increment");
	private final static QName _ParentDataPointReference_QNAME = new QName("urn:org:astm:animl:schema:core:draft:0.90", "ParentDataPointReference");
	private final static QName _S_QNAME = new QName("urn:org:astm:animl:schema:core:draft:0.90", "S");
	private final static QName _SampleReference_QNAME = new QName("urn:org:astm:animl:schema:core:draft:0.90", "SampleReference");
	private final static QName _FirmwareVersion_QNAME = new QName("urn:org:astm:animl:schema:core:draft:0.90", "FirmwareVersion");
	private final static QName _ParentDataPointReferenceSet_QNAME = new QName("urn:org:astm:animl:schema:core:draft:0.90", "ParentDataPointReferenceSet");
	private final static QName _Sample_QNAME = new QName("urn:org:astm:animl:schema:core:draft:0.90", "Sample");
	private final static QName _Reference_QNAME = new QName("urn:org:astm:animl:schema:core:draft:0.90", "Reference");
	private final static QName _Comment_QNAME = new QName("urn:org:astm:animl:schema:core:draft:0.90", "Comment");
	private final static QName _Location_QNAME = new QName("urn:org:astm:animl:schema:core:draft:0.90", "Location");
	private final static QName _AnIML_QNAME = new QName("urn:org:astm:animl:schema:core:draft:0.90", "AnIML");
	private final static QName _AuditTrailEntry_QNAME = new QName("urn:org:astm:animl:schema:core:draft:0.90", "AuditTrailEntry");
	private final static QName _Tag_QNAME = new QName("urn:org:astm:animl:schema:core:draft:0.90", "Tag");
	private final static QName _IndividualValueSet_QNAME = new QName("urn:org:astm:animl:schema:core:draft:0.90", "IndividualValueSet");
	private final static QName _ExperimentDataReferenceSet_QNAME = new QName("urn:org:astm:animl:schema:core:draft:0.90", "ExperimentDataReferenceSet");
	private final static QName _Parameter_QNAME = new QName("urn:org:astm:animl:schema:core:draft:0.90", "Parameter");
	private final static QName _SampleInheritance_QNAME = new QName("urn:org:astm:animl:schema:core:draft:0.90", "SampleInheritance");
	private final static QName _Signature_QNAME = new QName("urn:org:astm:animl:schema:core:draft:0.90", "Signature");
	private final static QName _Extension_QNAME = new QName("urn:org:astm:animl:schema:core:draft:0.90", "Extension");
	private final static QName _SampleSet_QNAME = new QName("urn:org:astm:animl:schema:core:draft:0.90", "SampleSet");
	private final static QName _Series_QNAME = new QName("urn:org:astm:animl:schema:core:draft:0.90", "Series");

	public ObjectFactory() {

	}

	public DiffType createDiffType() {

		return new DiffType();
	}

	public UnitType createUnitType() {

		return new UnitType();
	}

	public TemplateType createTemplateType() {

		return new TemplateType();
	}

	public ResultType createResultType() {

		return new ResultType();
	}

	public AutoIncrementedValueSetType createAutoIncrementedValueSetType() {

		return new AutoIncrementedValueSetType();
	}

	public ExperimentStepSetType createExperimentStepSetType() {

		return new ExperimentStepSetType();
	}

	public DeviceType createDeviceType() {

		return new DeviceType();
	}

	public ExperimentDataReferenceType createExperimentDataReferenceType() {

		return new ExperimentDataReferenceType();
	}

	public MethodType createMethodType() {

		return new MethodType();
	}

	public SignatureSetType createSignatureSetType() {

		return new SignatureSetType();
	}

	public SeriesSetType createSeriesSetType() {

		return new SeriesSetType();
	}

	public SIUnitType createSIUnitType() {

		return new SIUnitType();
	}

	public AuthorType createAuthorType() {

		return new AuthorType();
	}

	public SoftwareType createSoftwareType() {

		return new SoftwareType();
	}

	public TechniqueType createTechniqueType() {

		return new TechniqueType();
	}

	public CategoryType createCategoryType() {

		return new CategoryType();
	}

	public EncodedValueSetType createEncodedValueSetType() {

		return new EncodedValueSetType();
	}

	public StartValueType createStartValueType() {

		return new StartValueType();
	}

	public ExperimentDataBulkReferenceType createExperimentDataBulkReferenceType() {

		return new ExperimentDataBulkReferenceType();
	}

	public AuditTrailEntrySetType createAuditTrailEntrySetType() {

		return new AuditTrailEntrySetType();
	}

	public SampleReferenceType createSampleReferenceType() {

		return new SampleReferenceType();
	}

	public ParentDataPointReferenceType createParentDataPointReferenceType() {

		return new ParentDataPointReferenceType();
	}

	public ExperimentStepType createExperimentStepType() {

		return new ExperimentStepType();
	}

	public TagSetType createTagSetType() {

		return new TagSetType();
	}

	public IncrementType createIncrementType() {

		return new IncrementType();
	}

	public InfrastructureType createInfrastructureType() {

		return new InfrastructureType();
	}

	public EndValueType createEndValueType() {

		return new EndValueType();
	}

	public SampleReferenceSetType createSampleReferenceSetType() {

		return new SampleReferenceSetType();
	}

	public SampleType createSampleType() {

		return new SampleType();
	}

	public ParentDataPointReferenceSetType createParentDataPointReferenceSetType() {

		return new ParentDataPointReferenceSetType();
	}

	public ExtensionType createExtensionType() {

		return new ExtensionType();
	}

	public SampleSetType createSampleSetType() {

		return new SampleSetType();
	}

	public SeriesType createSeriesType() {

		return new SeriesType();
	}

	public SampleInheritanceType createSampleInheritanceType() {

		return new SampleInheritanceType();
	}

	public IndividualValueSetType createIndividualValueSetType() {

		return new IndividualValueSetType();
	}

	public ExperimentDataReferenceSetType createExperimentDataReferenceSetType() {

		return new ExperimentDataReferenceSetType();
	}

	public ParameterType createParameterType() {

		return new ParameterType();
	}

	public AuditTrailEntryType createAuditTrailEntryType() {

		return new AuditTrailEntryType();
	}

	public TagType createTagType() {

		return new TagType();
	}

	public AnIMLType createAnIMLType() {

		return new AnIMLType();
	}

	public NumericValueType createNumericValueType() {

		return new NumericValueType();
	}

	public UnboundedValueType createUnboundedValueType() {

		return new UnboundedValueType();
	}

	public SingleValueType createSingleValueType() {

		return new SingleValueType();
	}

	@XmlElementDecl(namespace = "urn:org:astm:animl:schema:core:draft:0.90", name = "DateTime")
	public JAXBElement<XMLGregorianCalendar> createDateTime(XMLGregorianCalendar value) {

		return new JAXBElement<XMLGregorianCalendar>(_DateTime_QNAME, XMLGregorianCalendar.class, null, value);
	}

	@XmlElementDecl(namespace = "urn:org:astm:animl:schema:core:draft:0.90", name = "Template")
	public JAXBElement<TemplateType> createTemplate(TemplateType value) {

		return new JAXBElement<TemplateType>(_Template_QNAME, TemplateType.class, null, value);
	}

	@XmlElementDecl(namespace = "urn:org:astm:animl:schema:core:draft:0.90", name = "Result")
	public JAXBElement<ResultType> createResult(ResultType value) {

		return new JAXBElement<ResultType>(_Result_QNAME, ResultType.class, null, value);
	}

	@XmlElementDecl(namespace = "urn:org:astm:animl:schema:core:draft:0.90", name = "Name")
	public JAXBElement<String> createName(String value) {

		return new JAXBElement<String>(_Name_QNAME, String.class, null, value);
	}

	@XmlElementDecl(namespace = "urn:org:astm:animl:schema:core:draft:0.90", name = "Unit")
	public JAXBElement<UnitType> createUnit(UnitType value) {

		return new JAXBElement<UnitType>(_Unit_QNAME, UnitType.class, null, value);
	}

	@XmlElementDecl(namespace = "urn:org:astm:animl:schema:core:draft:0.90", name = "PNG")
	public JAXBElement<byte[]> createPNG(byte[] value) {

		return new JAXBElement<byte[]>(_PNG_QNAME, byte[].class, null, (value));
	}

	@XmlElementDecl(namespace = "urn:org:astm:animl:schema:core:draft:0.90", name = "OldValue")
	public JAXBElement<String> createOldValue(String value) {

		return new JAXBElement<String>(_OldValue_QNAME, String.class, null, value);
	}

	@XmlElementDecl(namespace = "urn:org:astm:animl:schema:core:draft:0.90", name = "Diff")
	public JAXBElement<DiffType> createDiff(DiffType value) {

		return new JAXBElement<DiffType>(_Diff_QNAME, DiffType.class, null, value);
	}

	@XmlElementDecl(namespace = "urn:org:astm:animl:schema:core:draft:0.90", name = "Email")
	public JAXBElement<String> createEmail(String value) {

		return new JAXBElement<String>(_Email_QNAME, String.class, null, value);
	}

	@XmlElementDecl(namespace = "urn:org:astm:animl:schema:core:draft:0.90", name = "Affiliation")
	public JAXBElement<String> createAffiliation(String value) {

		return new JAXBElement<String>(_Affiliation_QNAME, String.class, null, value);
	}

	@XmlElementDecl(namespace = "urn:org:astm:animl:schema:core:draft:0.90", name = "Boolean")
	public JAXBElement<Boolean> createBoolean(Boolean value) {

		return new JAXBElement<Boolean>(_Boolean_QNAME, Boolean.class, null, value);
	}

	@XmlElementDecl(namespace = "urn:org:astm:animl:schema:core:draft:0.90", name = "ExperimentStepSet")
	public JAXBElement<ExperimentStepSetType> createExperimentStepSet(ExperimentStepSetType value) {

		return new JAXBElement<ExperimentStepSetType>(_ExperimentStepSet_QNAME, ExperimentStepSetType.class, null, value);
	}

	@XmlElementDecl(namespace = "urn:org:astm:animl:schema:core:draft:0.90", name = "Manufacturer")
	@XmlJavaTypeAdapter(CollapsedStringAdapter.class)
	public JAXBElement<String> createManufacturer(String value) {

		return new JAXBElement<String>(_Manufacturer_QNAME, String.class, null, value);
	}

	@XmlElementDecl(namespace = "urn:org:astm:animl:schema:core:draft:0.90", name = "AutoIncrementedValueSet")
	public JAXBElement<AutoIncrementedValueSetType> createAutoIncrementedValueSet(AutoIncrementedValueSetType value) {

		return new JAXBElement<AutoIncrementedValueSetType>(_AutoIncrementedValueSet_QNAME, AutoIncrementedValueSetType.class, null, value);
	}

	@XmlElementDecl(namespace = "urn:org:astm:animl:schema:core:draft:0.90", name = "Version")
	@XmlJavaTypeAdapter(CollapsedStringAdapter.class)
	public JAXBElement<String> createVersion(String value) {

		return new JAXBElement<String>(_Version_QNAME, String.class, null, value);
	}

	@XmlElementDecl(namespace = "urn:org:astm:animl:schema:core:draft:0.90", name = "DeviceIdentifier")
	@XmlJavaTypeAdapter(CollapsedStringAdapter.class)
	public JAXBElement<String> createDeviceIdentifier(String value) {

		return new JAXBElement<String>(_DeviceIdentifier_QNAME, String.class, null, value);
	}

	@XmlElementDecl(namespace = "urn:org:astm:animl:schema:core:draft:0.90", name = "ExperimentDataReference")
	public JAXBElement<ExperimentDataReferenceType> createExperimentDataReference(ExperimentDataReferenceType value) {

		return new JAXBElement<ExperimentDataReferenceType>(_ExperimentDataReference_QNAME, ExperimentDataReferenceType.class, null, value);
	}

	@XmlElementDecl(namespace = "urn:org:astm:animl:schema:core:draft:0.90", name = "Method")
	public JAXBElement<MethodType> createMethod(MethodType value) {

		return new JAXBElement<MethodType>(_Method_QNAME, MethodType.class, null, value);
	}

	@XmlElementDecl(namespace = "urn:org:astm:animl:schema:core:draft:0.90", name = "Device")
	public JAXBElement<DeviceType> createDevice(DeviceType value) {

		return new JAXBElement<DeviceType>(_Device_QNAME, DeviceType.class, null, value);
	}

	@XmlElementDecl(namespace = "urn:org:astm:animl:schema:core:draft:0.90", name = "NewValue")
	public JAXBElement<String> createNewValue(String value) {

		return new JAXBElement<String>(_NewValue_QNAME, String.class, null, value);
	}

	@XmlElementDecl(namespace = "urn:org:astm:animl:schema:core:draft:0.90", name = "Software")
	public JAXBElement<SoftwareType> createSoftware(SoftwareType value) {

		return new JAXBElement<SoftwareType>(_Software_QNAME, SoftwareType.class, null, value);
	}

	@XmlElementDecl(namespace = "urn:org:astm:animl:schema:core:draft:0.90", name = "Technique")
	public JAXBElement<TechniqueType> createTechnique(TechniqueType value) {

		return new JAXBElement<TechniqueType>(_Technique_QNAME, TechniqueType.class, null, value);
	}

	@XmlElementDecl(namespace = "urn:org:astm:animl:schema:core:draft:0.90", name = "Author")
	public JAXBElement<AuthorType> createAuthor(AuthorType value) {

		return new JAXBElement<AuthorType>(_Author_QNAME, AuthorType.class, null, value);
	}

	@XmlElementDecl(namespace = "urn:org:astm:animl:schema:core:draft:0.90", name = "SerialNumber")
	@XmlJavaTypeAdapter(CollapsedStringAdapter.class)
	public JAXBElement<String> createSerialNumber(String value) {

		return new JAXBElement<String>(_SerialNumber_QNAME, String.class, null, value);
	}

	@XmlElementDecl(namespace = "urn:org:astm:animl:schema:core:draft:0.90", name = "SeriesSet")
	public JAXBElement<SeriesSetType> createSeriesSet(SeriesSetType value) {

		return new JAXBElement<SeriesSetType>(_SeriesSet_QNAME, SeriesSetType.class, null, value);
	}

	@XmlElementDecl(namespace = "urn:org:astm:animl:schema:core:draft:0.90", name = "SIUnit")
	public JAXBElement<SIUnitType> createSIUnit(SIUnitType value) {

		return new JAXBElement<SIUnitType>(_SIUnit_QNAME, SIUnitType.class, null, value);
	}

	@XmlElementDecl(namespace = "urn:org:astm:animl:schema:core:draft:0.90", name = "Role")
	public JAXBElement<String> createRole(String value) {

		return new JAXBElement<String>(_Role_QNAME, String.class, null, value);
	}

	@XmlElementDecl(namespace = "urn:org:astm:animl:schema:core:draft:0.90", name = "SignatureSet")
	public JAXBElement<SignatureSetType> createSignatureSet(SignatureSetType value) {

		return new JAXBElement<SignatureSetType>(_SignatureSet_QNAME, SignatureSetType.class, null, value);
	}

	@XmlElementDecl(namespace = "urn:org:astm:animl:schema:core:draft:0.90", name = "Reason")
	public JAXBElement<String> createReason(String value) {

		return new JAXBElement<String>(_Reason_QNAME, String.class, null, value);
	}

	@XmlElementDecl(namespace = "urn:org:astm:animl:schema:core:draft:0.90", name = "AuditTrailEntrySet")
	public JAXBElement<AuditTrailEntrySetType> createAuditTrailEntrySet(AuditTrailEntrySetType value) {

		return new JAXBElement<AuditTrailEntrySetType>(_AuditTrailEntrySet_QNAME, AuditTrailEntrySetType.class, null, value);
	}

	@XmlElementDecl(namespace = "urn:org:astm:animl:schema:core:draft:0.90", name = "ExperimentDataBulkReference")
	public JAXBElement<ExperimentDataBulkReferenceType> createExperimentDataBulkReference(ExperimentDataBulkReferenceType value) {

		return new JAXBElement<ExperimentDataBulkReferenceType>(_ExperimentDataBulkReference_QNAME, ExperimentDataBulkReferenceType.class, null, value);
	}

	@XmlElementDecl(namespace = "urn:org:astm:animl:schema:core:draft:0.90", name = "L")
	public JAXBElement<Long> createL(Long value) {

		return new JAXBElement<Long>(_L_QNAME, Long.class, null, value);
	}

	@XmlElementDecl(namespace = "urn:org:astm:animl:schema:core:draft:0.90", name = "Timestamp")
	public JAXBElement<XMLGregorianCalendar> createTimestamp(XMLGregorianCalendar value) {

		return new JAXBElement<XMLGregorianCalendar>(_Timestamp_QNAME, XMLGregorianCalendar.class, null, value);
	}

	@XmlElementDecl(namespace = "urn:org:astm:animl:schema:core:draft:0.90", name = "StartValue")
	public JAXBElement<StartValueType> createStartValue(StartValueType value) {

		return new JAXBElement<StartValueType>(_StartValue_QNAME, StartValueType.class, null, value);
	}

	@XmlElementDecl(namespace = "urn:org:astm:animl:schema:core:draft:0.90", name = "I")
	public JAXBElement<Integer> createI(Integer value) {

		return new JAXBElement<Integer>(_I_QNAME, Integer.class, null, value);
	}

	@XmlElementDecl(namespace = "urn:org:astm:animl:schema:core:draft:0.90", name = "EmbeddedXML")
	public JAXBElement<String> createEmbeddedXML(String value) {

		return new JAXBElement<String>(_EmbeddedXML_QNAME, String.class, null, value);
	}

	@XmlElementDecl(namespace = "urn:org:astm:animl:schema:core:draft:0.90", name = "F")
	public JAXBElement<Float> createF(Float value) {

		return new JAXBElement<Float>(_F_QNAME, Float.class, null, value);
	}

	@XmlElementDecl(namespace = "urn:org:astm:animl:schema:core:draft:0.90", name = "D")
	public JAXBElement<Double> createD(Double value) {

		return new JAXBElement<Double>(_D_QNAME, Double.class, null, value);
	}

	@XmlElementDecl(namespace = "urn:org:astm:animl:schema:core:draft:0.90", name = "EncodedValueSet")
	public JAXBElement<EncodedValueSetType> createEncodedValueSet(EncodedValueSetType value) {

		return new JAXBElement<EncodedValueSetType>(_EncodedValueSet_QNAME, EncodedValueSetType.class, null, value);
	}

	@XmlElementDecl(namespace = "urn:org:astm:animl:schema:core:draft:0.90", name = "SVG")
	public JAXBElement<String> createSVG(String value) {

		return new JAXBElement<String>(_SVG_QNAME, String.class, null, value);
	}

	@XmlElementDecl(namespace = "urn:org:astm:animl:schema:core:draft:0.90", name = "Action")
	public JAXBElement<ActionType> createAction(ActionType value) {

		return new JAXBElement<ActionType>(_Action_QNAME, ActionType.class, null, value);
	}

	@XmlElementDecl(namespace = "urn:org:astm:animl:schema:core:draft:0.90", name = "Category")
	public JAXBElement<CategoryType> createCategory(CategoryType value) {

		return new JAXBElement<CategoryType>(_Category_QNAME, CategoryType.class, null, value);
	}

	@XmlElementDecl(namespace = "urn:org:astm:animl:schema:core:draft:0.90", name = "OperatingSystem")
	@XmlJavaTypeAdapter(CollapsedStringAdapter.class)
	public JAXBElement<String> createOperatingSystem(String value) {

		return new JAXBElement<String>(_OperatingSystem_QNAME, String.class, null, value);
	}

	@XmlElementDecl(namespace = "urn:org:astm:animl:schema:core:draft:0.90", name = "SampleReferenceSet")
	public JAXBElement<SampleReferenceSetType> createSampleReferenceSet(SampleReferenceSetType value) {

		return new JAXBElement<SampleReferenceSetType>(_SampleReferenceSet_QNAME, SampleReferenceSetType.class, null, value);
	}

	@XmlElementDecl(namespace = "urn:org:astm:animl:schema:core:draft:0.90", name = "EndValue")
	public JAXBElement<EndValueType> createEndValue(EndValueType value) {

		return new JAXBElement<EndValueType>(_EndValue_QNAME, EndValueType.class, null, value);
	}

	@XmlElementDecl(namespace = "urn:org:astm:animl:schema:core:draft:0.90", name = "Infrastructure")
	public JAXBElement<InfrastructureType> createInfrastructure(InfrastructureType value) {

		return new JAXBElement<InfrastructureType>(_Infrastructure_QNAME, InfrastructureType.class, null, value);
	}

	@XmlElementDecl(namespace = "urn:org:astm:animl:schema:core:draft:0.90", name = "ExperimentStep")
	public JAXBElement<ExperimentStepType> createExperimentStep(ExperimentStepType value) {

		return new JAXBElement<ExperimentStepType>(_ExperimentStep_QNAME, ExperimentStepType.class, null, value);
	}

	@XmlElementDecl(namespace = "urn:org:astm:animl:schema:core:draft:0.90", name = "TagSet")
	public JAXBElement<TagSetType> createTagSet(TagSetType value) {

		return new JAXBElement<TagSetType>(_TagSet_QNAME, TagSetType.class, null, value);
	}

	@XmlElementDecl(namespace = "urn:org:astm:animl:schema:core:draft:0.90", name = "Phone")
	public JAXBElement<String> createPhone(String value) {

		return new JAXBElement<String>(_Phone_QNAME, String.class, null, value);
	}

	@XmlElementDecl(namespace = "urn:org:astm:animl:schema:core:draft:0.90", name = "Increment")
	public JAXBElement<IncrementType> createIncrement(IncrementType value) {

		return new JAXBElement<IncrementType>(_Increment_QNAME, IncrementType.class, null, value);
	}

	@XmlElementDecl(namespace = "urn:org:astm:animl:schema:core:draft:0.90", name = "ParentDataPointReference")
	public JAXBElement<ParentDataPointReferenceType> createParentDataPointReference(ParentDataPointReferenceType value) {

		return new JAXBElement<ParentDataPointReferenceType>(_ParentDataPointReference_QNAME, ParentDataPointReferenceType.class, null, value);
	}

	@XmlElementDecl(namespace = "urn:org:astm:animl:schema:core:draft:0.90", name = "S")
	public JAXBElement<String> createS(String value) {

		return new JAXBElement<String>(_S_QNAME, String.class, null, value);
	}

	@XmlElementDecl(namespace = "urn:org:astm:animl:schema:core:draft:0.90", name = "SampleReference")
	public JAXBElement<SampleReferenceType> createSampleReference(SampleReferenceType value) {

		return new JAXBElement<SampleReferenceType>(_SampleReference_QNAME, SampleReferenceType.class, null, value);
	}

	@XmlElementDecl(namespace = "urn:org:astm:animl:schema:core:draft:0.90", name = "FirmwareVersion")
	@XmlJavaTypeAdapter(CollapsedStringAdapter.class)
	public JAXBElement<String> createFirmwareVersion(String value) {

		return new JAXBElement<String>(_FirmwareVersion_QNAME, String.class, null, value);
	}

	@XmlElementDecl(namespace = "urn:org:astm:animl:schema:core:draft:0.90", name = "ParentDataPointReferenceSet")
	public JAXBElement<ParentDataPointReferenceSetType> createParentDataPointReferenceSet(ParentDataPointReferenceSetType value) {

		return new JAXBElement<ParentDataPointReferenceSetType>(_ParentDataPointReferenceSet_QNAME, ParentDataPointReferenceSetType.class, null, value);
	}

	@XmlElementDecl(namespace = "urn:org:astm:animl:schema:core:draft:0.90", name = "Sample")
	public JAXBElement<SampleType> createSample(SampleType value) {

		return new JAXBElement<SampleType>(_Sample_QNAME, SampleType.class, null, value);
	}

	@XmlElementDecl(namespace = "urn:org:astm:animl:schema:core:draft:0.90", name = "Reference")
	@XmlIDREF
	public JAXBElement<Object> createReference(Object value) {

		return new JAXBElement<Object>(_Reference_QNAME, Object.class, null, value);
	}

	@XmlElementDecl(namespace = "urn:org:astm:animl:schema:core:draft:0.90", name = "Comment")
	public JAXBElement<String> createComment(String value) {

		return new JAXBElement<String>(_Comment_QNAME, String.class, null, value);
	}

	@XmlElementDecl(namespace = "urn:org:astm:animl:schema:core:draft:0.90", name = "Location")
	public JAXBElement<String> createLocation(String value) {

		return new JAXBElement<String>(_Location_QNAME, String.class, null, value);
	}

	@XmlElementDecl(namespace = "urn:org:astm:animl:schema:core:draft:0.90", name = "AnIML")
	public JAXBElement<AnIMLType> createAnIML(AnIMLType value) {

		return new JAXBElement<AnIMLType>(_AnIML_QNAME, AnIMLType.class, null, value);
	}

	@XmlElementDecl(namespace = "urn:org:astm:animl:schema:core:draft:0.90", name = "AuditTrailEntry")
	public JAXBElement<AuditTrailEntryType> createAuditTrailEntry(AuditTrailEntryType value) {

		return new JAXBElement<AuditTrailEntryType>(_AuditTrailEntry_QNAME, AuditTrailEntryType.class, null, value);
	}

	@XmlElementDecl(namespace = "urn:org:astm:animl:schema:core:draft:0.90", name = "Tag")
	public JAXBElement<TagType> createTag(TagType value) {

		return new JAXBElement<TagType>(_Tag_QNAME, TagType.class, null, value);
	}

	@XmlElementDecl(namespace = "urn:org:astm:animl:schema:core:draft:0.90", name = "IndividualValueSet")
	public JAXBElement<IndividualValueSetType> createIndividualValueSet(IndividualValueSetType value) {

		return new JAXBElement<IndividualValueSetType>(_IndividualValueSet_QNAME, IndividualValueSetType.class, null, value);
	}

	@XmlElementDecl(namespace = "urn:org:astm:animl:schema:core:draft:0.90", name = "ExperimentDataReferenceSet")
	public JAXBElement<ExperimentDataReferenceSetType> createExperimentDataReferenceSet(ExperimentDataReferenceSetType value) {

		return new JAXBElement<ExperimentDataReferenceSetType>(_ExperimentDataReferenceSet_QNAME, ExperimentDataReferenceSetType.class, null, value);
	}

	@XmlElementDecl(namespace = "urn:org:astm:animl:schema:core:draft:0.90", name = "Parameter")
	public JAXBElement<ParameterType> createParameter(ParameterType value) {

		return new JAXBElement<ParameterType>(_Parameter_QNAME, ParameterType.class, null, value);
	}

	@XmlElementDecl(namespace = "urn:org:astm:animl:schema:core:draft:0.90", name = "SampleInheritance")
	public JAXBElement<SampleInheritanceType> createSampleInheritance(SampleInheritanceType value) {

		return new JAXBElement<SampleInheritanceType>(_SampleInheritance_QNAME, SampleInheritanceType.class, null, value);
	}

	@XmlElementDecl(namespace = "urn:org:astm:animl:schema:core:draft:0.90", name = "Signature")
	public JAXBElement<SignatureType> createSignature(SignatureType value) {

		return new JAXBElement<SignatureType>(_Signature_QNAME, SignatureType.class, null, value);
	}

	@XmlElementDecl(namespace = "urn:org:astm:animl:schema:core:draft:0.90", name = "Extension")
	public JAXBElement<ExtensionType> createExtension(ExtensionType value) {

		return new JAXBElement<ExtensionType>(_Extension_QNAME, ExtensionType.class, null, value);
	}

	@XmlElementDecl(namespace = "urn:org:astm:animl:schema:core:draft:0.90", name = "SampleSet")
	public JAXBElement<SampleSetType> createSampleSet(SampleSetType value) {

		return new JAXBElement<SampleSetType>(_SampleSet_QNAME, SampleSetType.class, null, value);
	}

	@XmlElementDecl(namespace = "urn:org:astm:animl:schema:core:draft:0.90", name = "Series")
	public JAXBElement<SeriesType> createSeries(SeriesType value) {

		return new JAXBElement<SeriesType>(_Series_QNAME, SeriesType.class, null, value);
	}
}
