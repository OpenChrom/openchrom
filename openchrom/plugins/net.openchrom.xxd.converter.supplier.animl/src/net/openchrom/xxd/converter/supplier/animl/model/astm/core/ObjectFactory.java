/*******************************************************************************
 * Copyright (c) 2021, 2024 Lablicate GmbH.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 * Matthias Mailänder - initial API and implementation
 *******************************************************************************/
package net.openchrom.xxd.converter.supplier.animl.model.astm.core;

import javax.xml.datatype.XMLGregorianCalendar;
import javax.xml.namespace.QName;

import net.openchrom.xxd.converter.supplier.animl.model.w3.SignatureType;

import jakarta.xml.bind.JAXBElement;
import jakarta.xml.bind.annotation.XmlElementDecl;
import jakarta.xml.bind.annotation.XmlIDREF;
import jakarta.xml.bind.annotation.XmlRegistry;
import jakarta.xml.bind.annotation.adapters.CollapsedStringAdapter;
import jakarta.xml.bind.annotation.adapters.XmlJavaTypeAdapter;

@XmlRegistry
public class ObjectFactory {

	private static final String NAMESPACE_URI = "urn:org:astm:animl:schema:core:draft:0.90";
	private static final QName _DateTime_QNAME = new QName(NAMESPACE_URI, "DateTime");
	private static final QName _Template_QNAME = new QName(NAMESPACE_URI, "Template");
	private static final QName _Result_QNAME = new QName(NAMESPACE_URI, "Result");
	private static final QName _Name_QNAME = new QName(NAMESPACE_URI, "Name");
	private static final QName _Unit_QNAME = new QName(NAMESPACE_URI, "Unit");
	private static final QName _PNG_QNAME = new QName(NAMESPACE_URI, "PNG");
	private static final QName _OldValue_QNAME = new QName(NAMESPACE_URI, "OldValue");
	private static final QName _Diff_QNAME = new QName(NAMESPACE_URI, "Diff");
	private static final QName _Email_QNAME = new QName(NAMESPACE_URI, "Email");
	private static final QName _Affiliation_QNAME = new QName(NAMESPACE_URI, "Affiliation");
	private static final QName _Boolean_QNAME = new QName(NAMESPACE_URI, "Boolean");
	private static final QName _ExperimentStepSet_QNAME = new QName(NAMESPACE_URI, "ExperimentStepSet");
	private static final QName _Manufacturer_QNAME = new QName(NAMESPACE_URI, "Manufacturer");
	private static final QName _AutoIncrementedValueSet_QNAME = new QName(NAMESPACE_URI, "AutoIncrementedValueSet");
	private static final QName _Version_QNAME = new QName(NAMESPACE_URI, "Version");
	private static final QName _DeviceIdentifier_QNAME = new QName(NAMESPACE_URI, "DeviceIdentifier");
	private static final QName _ExperimentDataReference_QNAME = new QName(NAMESPACE_URI, "ExperimentDataReference");
	private static final QName _Method_QNAME = new QName(NAMESPACE_URI, "Method");
	private static final QName _Device_QNAME = new QName(NAMESPACE_URI, "Device");
	private static final QName _NewValue_QNAME = new QName(NAMESPACE_URI, "NewValue");
	private static final QName _Software_QNAME = new QName(NAMESPACE_URI, "Software");
	private static final QName _Technique_QNAME = new QName(NAMESPACE_URI, "Technique");
	private static final QName _Author_QNAME = new QName(NAMESPACE_URI, "Author");
	private static final QName _SerialNumber_QNAME = new QName(NAMESPACE_URI, "SerialNumber");
	private static final QName _SeriesSet_QNAME = new QName(NAMESPACE_URI, "SeriesSet");
	private static final QName _SIUnit_QNAME = new QName(NAMESPACE_URI, "SIUnit");
	private static final QName _Role_QNAME = new QName(NAMESPACE_URI, "Role");
	private static final QName _SignatureSet_QNAME = new QName(NAMESPACE_URI, "SignatureSet");
	private static final QName _Reason_QNAME = new QName(NAMESPACE_URI, "Reason");
	private static final QName _AuditTrailEntrySet_QNAME = new QName(NAMESPACE_URI, "AuditTrailEntrySet");
	private static final QName _ExperimentDataBulkReference_QNAME = new QName(NAMESPACE_URI, "ExperimentDataBulkReference");
	private static final QName _L_QNAME = new QName(NAMESPACE_URI, "L");
	private static final QName _Timestamp_QNAME = new QName(NAMESPACE_URI, "Timestamp");
	private static final QName _StartValue_QNAME = new QName(NAMESPACE_URI, "StartValue");
	private static final QName _I_QNAME = new QName(NAMESPACE_URI, "I");
	private static final QName _EmbeddedXML_QNAME = new QName(NAMESPACE_URI, "EmbeddedXML");
	private static final QName _F_QNAME = new QName(NAMESPACE_URI, "F");
	private static final QName _D_QNAME = new QName(NAMESPACE_URI, "D");
	private static final QName _EncodedValueSet_QNAME = new QName(NAMESPACE_URI, "EncodedValueSet");
	private static final QName _SVG_QNAME = new QName(NAMESPACE_URI, "SVG");
	private static final QName _Action_QNAME = new QName(NAMESPACE_URI, "Action");
	private static final QName _Category_QNAME = new QName(NAMESPACE_URI, "Category");
	private static final QName _OperatingSystem_QNAME = new QName(NAMESPACE_URI, "OperatingSystem");
	private static final QName _SampleReferenceSet_QNAME = new QName(NAMESPACE_URI, "SampleReferenceSet");
	private static final QName _EndValue_QNAME = new QName(NAMESPACE_URI, "EndValue");
	private static final QName _Infrastructure_QNAME = new QName(NAMESPACE_URI, "Infrastructure");
	private static final QName _ExperimentStep_QNAME = new QName(NAMESPACE_URI, "ExperimentStep");
	private static final QName _TagSet_QNAME = new QName(NAMESPACE_URI, "TagSet");
	private static final QName _Phone_QNAME = new QName(NAMESPACE_URI, "Phone");
	private static final QName _Increment_QNAME = new QName(NAMESPACE_URI, "Increment");
	private static final QName _ParentDataPointReference_QNAME = new QName(NAMESPACE_URI, "ParentDataPointReference");
	private static final QName _S_QNAME = new QName(NAMESPACE_URI, "S");
	private static final QName _SampleReference_QNAME = new QName(NAMESPACE_URI, "SampleReference");
	private static final QName _FirmwareVersion_QNAME = new QName(NAMESPACE_URI, "FirmwareVersion");
	private static final QName _ParentDataPointReferenceSet_QNAME = new QName(NAMESPACE_URI, "ParentDataPointReferenceSet");
	private static final QName _Sample_QNAME = new QName(NAMESPACE_URI, "Sample");
	private static final QName _Reference_QNAME = new QName(NAMESPACE_URI, "Reference");
	private static final QName _Comment_QNAME = new QName(NAMESPACE_URI, "Comment");
	private static final QName _Location_QNAME = new QName(NAMESPACE_URI, "Location");
	private static final QName _AnIML_QNAME = new QName(NAMESPACE_URI, "AnIML");
	private static final QName _AuditTrailEntry_QNAME = new QName(NAMESPACE_URI, "AuditTrailEntry");
	private static final QName _Tag_QNAME = new QName(NAMESPACE_URI, "Tag");
	private static final QName _IndividualValueSet_QNAME = new QName(NAMESPACE_URI, "IndividualValueSet");
	private static final QName _ExperimentDataReferenceSet_QNAME = new QName(NAMESPACE_URI, "ExperimentDataReferenceSet");
	private static final QName _Parameter_QNAME = new QName(NAMESPACE_URI, "Parameter");
	private static final QName _SampleInheritance_QNAME = new QName(NAMESPACE_URI, "SampleInheritance");
	private static final QName _Signature_QNAME = new QName(NAMESPACE_URI, "Signature");
	private static final QName _Extension_QNAME = new QName(NAMESPACE_URI, "Extension");
	private static final QName _SampleSet_QNAME = new QName(NAMESPACE_URI, "SampleSet");
	private static final QName _Series_QNAME = new QName(NAMESPACE_URI, "Series");

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

	@XmlElementDecl(namespace = NAMESPACE_URI, name = "DateTime")
	public JAXBElement<XMLGregorianCalendar> createDateTime(XMLGregorianCalendar value) {

		return new JAXBElement<>(_DateTime_QNAME, XMLGregorianCalendar.class, null, value);
	}

	@XmlElementDecl(namespace = NAMESPACE_URI, name = "Template")
	public JAXBElement<TemplateType> createTemplate(TemplateType value) {

		return new JAXBElement<>(_Template_QNAME, TemplateType.class, null, value);
	}

	@XmlElementDecl(namespace = NAMESPACE_URI, name = "Result")
	public JAXBElement<ResultType> createResult(ResultType value) {

		return new JAXBElement<>(_Result_QNAME, ResultType.class, null, value);
	}

	@XmlElementDecl(namespace = NAMESPACE_URI, name = "Name")
	public JAXBElement<String> createName(String value) {

		return new JAXBElement<>(_Name_QNAME, String.class, null, value);
	}

	@XmlElementDecl(namespace = NAMESPACE_URI, name = "Unit")
	public JAXBElement<UnitType> createUnit(UnitType value) {

		return new JAXBElement<>(_Unit_QNAME, UnitType.class, null, value);
	}

	@XmlElementDecl(namespace = NAMESPACE_URI, name = "PNG")
	public JAXBElement<byte[]> createPNG(byte[] value) {

		return new JAXBElement<>(_PNG_QNAME, byte[].class, null, (value));
	}

	@XmlElementDecl(namespace = NAMESPACE_URI, name = "OldValue")
	public JAXBElement<String> createOldValue(String value) {

		return new JAXBElement<>(_OldValue_QNAME, String.class, null, value);
	}

	@XmlElementDecl(namespace = NAMESPACE_URI, name = "Diff")
	public JAXBElement<DiffType> createDiff(DiffType value) {

		return new JAXBElement<>(_Diff_QNAME, DiffType.class, null, value);
	}

	@XmlElementDecl(namespace = NAMESPACE_URI, name = "Email")
	public JAXBElement<String> createEmail(String value) {

		return new JAXBElement<>(_Email_QNAME, String.class, null, value);
	}

	@XmlElementDecl(namespace = NAMESPACE_URI, name = "Affiliation")
	public JAXBElement<String> createAffiliation(String value) {

		return new JAXBElement<>(_Affiliation_QNAME, String.class, null, value);
	}

	@XmlElementDecl(namespace = NAMESPACE_URI, name = "Boolean")
	public JAXBElement<Boolean> createBoolean(Boolean value) {

		return new JAXBElement<>(_Boolean_QNAME, Boolean.class, null, value);
	}

	@XmlElementDecl(namespace = NAMESPACE_URI, name = "ExperimentStepSet")
	public JAXBElement<ExperimentStepSetType> createExperimentStepSet(ExperimentStepSetType value) {

		return new JAXBElement<>(_ExperimentStepSet_QNAME, ExperimentStepSetType.class, null, value);
	}

	@XmlElementDecl(namespace = NAMESPACE_URI, name = "Manufacturer")
	@XmlJavaTypeAdapter(CollapsedStringAdapter.class)
	public JAXBElement<String> createManufacturer(String value) {

		return new JAXBElement<>(_Manufacturer_QNAME, String.class, null, value);
	}

	@XmlElementDecl(namespace = NAMESPACE_URI, name = "AutoIncrementedValueSet")
	public JAXBElement<AutoIncrementedValueSetType> createAutoIncrementedValueSet(AutoIncrementedValueSetType value) {

		return new JAXBElement<>(_AutoIncrementedValueSet_QNAME, AutoIncrementedValueSetType.class, null, value);
	}

	@XmlElementDecl(namespace = NAMESPACE_URI, name = "Version")
	@XmlJavaTypeAdapter(CollapsedStringAdapter.class)
	public JAXBElement<String> createVersion(String value) {

		return new JAXBElement<>(_Version_QNAME, String.class, null, value);
	}

	@XmlElementDecl(namespace = NAMESPACE_URI, name = "DeviceIdentifier")
	@XmlJavaTypeAdapter(CollapsedStringAdapter.class)
	public JAXBElement<String> createDeviceIdentifier(String value) {

		return new JAXBElement<>(_DeviceIdentifier_QNAME, String.class, null, value);
	}

	@XmlElementDecl(namespace = NAMESPACE_URI, name = "ExperimentDataReference")
	public JAXBElement<ExperimentDataReferenceType> createExperimentDataReference(ExperimentDataReferenceType value) {

		return new JAXBElement<>(_ExperimentDataReference_QNAME, ExperimentDataReferenceType.class, null, value);
	}

	@XmlElementDecl(namespace = NAMESPACE_URI, name = "Method")
	public JAXBElement<MethodType> createMethod(MethodType value) {

		return new JAXBElement<>(_Method_QNAME, MethodType.class, null, value);
	}

	@XmlElementDecl(namespace = NAMESPACE_URI, name = "Device")
	public JAXBElement<DeviceType> createDevice(DeviceType value) {

		return new JAXBElement<>(_Device_QNAME, DeviceType.class, null, value);
	}

	@XmlElementDecl(namespace = NAMESPACE_URI, name = "NewValue")
	public JAXBElement<String> createNewValue(String value) {

		return new JAXBElement<>(_NewValue_QNAME, String.class, null, value);
	}

	@XmlElementDecl(namespace = NAMESPACE_URI, name = "Software")
	public JAXBElement<SoftwareType> createSoftware(SoftwareType value) {

		return new JAXBElement<>(_Software_QNAME, SoftwareType.class, null, value);
	}

	@XmlElementDecl(namespace = NAMESPACE_URI, name = "Technique")
	public JAXBElement<TechniqueType> createTechnique(TechniqueType value) {

		return new JAXBElement<>(_Technique_QNAME, TechniqueType.class, null, value);
	}

	@XmlElementDecl(namespace = NAMESPACE_URI, name = "Author")
	public JAXBElement<AuthorType> createAuthor(AuthorType value) {

		return new JAXBElement<>(_Author_QNAME, AuthorType.class, null, value);
	}

	@XmlElementDecl(namespace = NAMESPACE_URI, name = "SerialNumber")
	@XmlJavaTypeAdapter(CollapsedStringAdapter.class)
	public JAXBElement<String> createSerialNumber(String value) {

		return new JAXBElement<>(_SerialNumber_QNAME, String.class, null, value);
	}

	@XmlElementDecl(namespace = NAMESPACE_URI, name = "SeriesSet")
	public JAXBElement<SeriesSetType> createSeriesSet(SeriesSetType value) {

		return new JAXBElement<>(_SeriesSet_QNAME, SeriesSetType.class, null, value);
	}

	@XmlElementDecl(namespace = NAMESPACE_URI, name = "SIUnit")
	public JAXBElement<SIUnitType> createSIUnit(SIUnitType value) {

		return new JAXBElement<>(_SIUnit_QNAME, SIUnitType.class, null, value);
	}

	@XmlElementDecl(namespace = NAMESPACE_URI, name = "Role")
	public JAXBElement<String> createRole(String value) {

		return new JAXBElement<>(_Role_QNAME, String.class, null, value);
	}

	@XmlElementDecl(namespace = NAMESPACE_URI, name = "SignatureSet")
	public JAXBElement<SignatureSetType> createSignatureSet(SignatureSetType value) {

		return new JAXBElement<>(_SignatureSet_QNAME, SignatureSetType.class, null, value);
	}

	@XmlElementDecl(namespace = NAMESPACE_URI, name = "Reason")
	public JAXBElement<String> createReason(String value) {

		return new JAXBElement<>(_Reason_QNAME, String.class, null, value);
	}

	@XmlElementDecl(namespace = NAMESPACE_URI, name = "AuditTrailEntrySet")
	public JAXBElement<AuditTrailEntrySetType> createAuditTrailEntrySet(AuditTrailEntrySetType value) {

		return new JAXBElement<>(_AuditTrailEntrySet_QNAME, AuditTrailEntrySetType.class, null, value);
	}

	@XmlElementDecl(namespace = NAMESPACE_URI, name = "ExperimentDataBulkReference")
	public JAXBElement<ExperimentDataBulkReferenceType> createExperimentDataBulkReference(ExperimentDataBulkReferenceType value) {

		return new JAXBElement<>(_ExperimentDataBulkReference_QNAME, ExperimentDataBulkReferenceType.class, null, value);
	}

	@XmlElementDecl(namespace = NAMESPACE_URI, name = "L")
	public JAXBElement<Long> createL(Long value) {

		return new JAXBElement<>(_L_QNAME, Long.class, null, value);
	}

	@XmlElementDecl(namespace = NAMESPACE_URI, name = "Timestamp")
	public JAXBElement<XMLGregorianCalendar> createTimestamp(XMLGregorianCalendar value) {

		return new JAXBElement<>(_Timestamp_QNAME, XMLGregorianCalendar.class, null, value);
	}

	@XmlElementDecl(namespace = NAMESPACE_URI, name = "StartValue")
	public JAXBElement<StartValueType> createStartValue(StartValueType value) {

		return new JAXBElement<>(_StartValue_QNAME, StartValueType.class, null, value);
	}

	@XmlElementDecl(namespace = NAMESPACE_URI, name = "I")
	public JAXBElement<Integer> createI(Integer value) {

		return new JAXBElement<>(_I_QNAME, Integer.class, null, value);
	}

	@XmlElementDecl(namespace = NAMESPACE_URI, name = "EmbeddedXML")
	public JAXBElement<String> createEmbeddedXML(String value) {

		return new JAXBElement<>(_EmbeddedXML_QNAME, String.class, null, value);
	}

	@XmlElementDecl(namespace = NAMESPACE_URI, name = "F")
	public JAXBElement<Float> createF(Float value) {

		return new JAXBElement<>(_F_QNAME, Float.class, null, value);
	}

	@XmlElementDecl(namespace = NAMESPACE_URI, name = "D")
	public JAXBElement<Double> createD(Double value) {

		return new JAXBElement<>(_D_QNAME, Double.class, null, value);
	}

	@XmlElementDecl(namespace = NAMESPACE_URI, name = "EncodedValueSet")
	public JAXBElement<EncodedValueSetType> createEncodedValueSet(EncodedValueSetType value) {

		return new JAXBElement<>(_EncodedValueSet_QNAME, EncodedValueSetType.class, null, value);
	}

	@XmlElementDecl(namespace = NAMESPACE_URI, name = "SVG")
	public JAXBElement<String> createSVG(String value) {

		return new JAXBElement<>(_SVG_QNAME, String.class, null, value);
	}

	@XmlElementDecl(namespace = NAMESPACE_URI, name = "Action")
	public JAXBElement<ActionType> createAction(ActionType value) {

		return new JAXBElement<>(_Action_QNAME, ActionType.class, null, value);
	}

	@XmlElementDecl(namespace = NAMESPACE_URI, name = "Category")
	public JAXBElement<CategoryType> createCategory(CategoryType value) {

		return new JAXBElement<>(_Category_QNAME, CategoryType.class, null, value);
	}

	@XmlElementDecl(namespace = NAMESPACE_URI, name = "OperatingSystem")
	@XmlJavaTypeAdapter(CollapsedStringAdapter.class)
	public JAXBElement<String> createOperatingSystem(String value) {

		return new JAXBElement<>(_OperatingSystem_QNAME, String.class, null, value);
	}

	@XmlElementDecl(namespace = NAMESPACE_URI, name = "SampleReferenceSet")
	public JAXBElement<SampleReferenceSetType> createSampleReferenceSet(SampleReferenceSetType value) {

		return new JAXBElement<>(_SampleReferenceSet_QNAME, SampleReferenceSetType.class, null, value);
	}

	@XmlElementDecl(namespace = NAMESPACE_URI, name = "EndValue")
	public JAXBElement<EndValueType> createEndValue(EndValueType value) {

		return new JAXBElement<>(_EndValue_QNAME, EndValueType.class, null, value);
	}

	@XmlElementDecl(namespace = NAMESPACE_URI, name = "Infrastructure")
	public JAXBElement<InfrastructureType> createInfrastructure(InfrastructureType value) {

		return new JAXBElement<>(_Infrastructure_QNAME, InfrastructureType.class, null, value);
	}

	@XmlElementDecl(namespace = NAMESPACE_URI, name = "ExperimentStep")
	public JAXBElement<ExperimentStepType> createExperimentStep(ExperimentStepType value) {

		return new JAXBElement<>(_ExperimentStep_QNAME, ExperimentStepType.class, null, value);
	}

	@XmlElementDecl(namespace = NAMESPACE_URI, name = "TagSet")
	public JAXBElement<TagSetType> createTagSet(TagSetType value) {

		return new JAXBElement<>(_TagSet_QNAME, TagSetType.class, null, value);
	}

	@XmlElementDecl(namespace = NAMESPACE_URI, name = "Phone")
	public JAXBElement<String> createPhone(String value) {

		return new JAXBElement<>(_Phone_QNAME, String.class, null, value);
	}

	@XmlElementDecl(namespace = NAMESPACE_URI, name = "Increment")
	public JAXBElement<IncrementType> createIncrement(IncrementType value) {

		return new JAXBElement<>(_Increment_QNAME, IncrementType.class, null, value);
	}

	@XmlElementDecl(namespace = NAMESPACE_URI, name = "ParentDataPointReference")
	public JAXBElement<ParentDataPointReferenceType> createParentDataPointReference(ParentDataPointReferenceType value) {

		return new JAXBElement<>(_ParentDataPointReference_QNAME, ParentDataPointReferenceType.class, null, value);
	}

	@XmlElementDecl(namespace = NAMESPACE_URI, name = "S")
	public JAXBElement<String> createS(String value) {

		return new JAXBElement<>(_S_QNAME, String.class, null, value);
	}

	@XmlElementDecl(namespace = NAMESPACE_URI, name = "SampleReference")
	public JAXBElement<SampleReferenceType> createSampleReference(SampleReferenceType value) {

		return new JAXBElement<>(_SampleReference_QNAME, SampleReferenceType.class, null, value);
	}

	@XmlElementDecl(namespace = NAMESPACE_URI, name = "FirmwareVersion")
	@XmlJavaTypeAdapter(CollapsedStringAdapter.class)
	public JAXBElement<String> createFirmwareVersion(String value) {

		return new JAXBElement<>(_FirmwareVersion_QNAME, String.class, null, value);
	}

	@XmlElementDecl(namespace = NAMESPACE_URI, name = "ParentDataPointReferenceSet")
	public JAXBElement<ParentDataPointReferenceSetType> createParentDataPointReferenceSet(ParentDataPointReferenceSetType value) {

		return new JAXBElement<>(_ParentDataPointReferenceSet_QNAME, ParentDataPointReferenceSetType.class, null, value);
	}

	@XmlElementDecl(namespace = NAMESPACE_URI, name = "Sample")
	public JAXBElement<SampleType> createSample(SampleType value) {

		return new JAXBElement<>(_Sample_QNAME, SampleType.class, null, value);
	}

	@XmlElementDecl(namespace = NAMESPACE_URI, name = "Reference")
	@XmlIDREF
	public JAXBElement<Object> createReference(Object value) {

		return new JAXBElement<>(_Reference_QNAME, Object.class, null, value);
	}

	@XmlElementDecl(namespace = NAMESPACE_URI, name = "Comment")
	public JAXBElement<String> createComment(String value) {

		return new JAXBElement<>(_Comment_QNAME, String.class, null, value);
	}

	@XmlElementDecl(namespace = NAMESPACE_URI, name = "Location")
	public JAXBElement<String> createLocation(String value) {

		return new JAXBElement<>(_Location_QNAME, String.class, null, value);
	}

	@XmlElementDecl(namespace = NAMESPACE_URI, name = "AnIML")
	public JAXBElement<AnIMLType> createAnIML(AnIMLType value) {

		return new JAXBElement<>(_AnIML_QNAME, AnIMLType.class, null, value);
	}

	@XmlElementDecl(namespace = NAMESPACE_URI, name = "AuditTrailEntry")
	public JAXBElement<AuditTrailEntryType> createAuditTrailEntry(AuditTrailEntryType value) {

		return new JAXBElement<>(_AuditTrailEntry_QNAME, AuditTrailEntryType.class, null, value);
	}

	@XmlElementDecl(namespace = NAMESPACE_URI, name = "Tag")
	public JAXBElement<TagType> createTag(TagType value) {

		return new JAXBElement<>(_Tag_QNAME, TagType.class, null, value);
	}

	@XmlElementDecl(namespace = NAMESPACE_URI, name = "IndividualValueSet")
	public JAXBElement<IndividualValueSetType> createIndividualValueSet(IndividualValueSetType value) {

		return new JAXBElement<>(_IndividualValueSet_QNAME, IndividualValueSetType.class, null, value);
	}

	@XmlElementDecl(namespace = NAMESPACE_URI, name = "ExperimentDataReferenceSet")
	public JAXBElement<ExperimentDataReferenceSetType> createExperimentDataReferenceSet(ExperimentDataReferenceSetType value) {

		return new JAXBElement<>(_ExperimentDataReferenceSet_QNAME, ExperimentDataReferenceSetType.class, null, value);
	}

	@XmlElementDecl(namespace = NAMESPACE_URI, name = "Parameter")
	public JAXBElement<ParameterType> createParameter(ParameterType value) {

		return new JAXBElement<>(_Parameter_QNAME, ParameterType.class, null, value);
	}

	@XmlElementDecl(namespace = NAMESPACE_URI, name = "SampleInheritance")
	public JAXBElement<SampleInheritanceType> createSampleInheritance(SampleInheritanceType value) {

		return new JAXBElement<>(_SampleInheritance_QNAME, SampleInheritanceType.class, null, value);
	}

	@XmlElementDecl(namespace = NAMESPACE_URI, name = "Signature")
	public JAXBElement<SignatureType> createSignature(SignatureType value) {

		return new JAXBElement<>(_Signature_QNAME, SignatureType.class, null, value);
	}

	@XmlElementDecl(namespace = NAMESPACE_URI, name = "Extension")
	public JAXBElement<ExtensionType> createExtension(ExtensionType value) {

		return new JAXBElement<>(_Extension_QNAME, ExtensionType.class, null, value);
	}

	@XmlElementDecl(namespace = NAMESPACE_URI, name = "SampleSet")
	public JAXBElement<SampleSetType> createSampleSet(SampleSetType value) {

		return new JAXBElement<>(_SampleSet_QNAME, SampleSetType.class, null, value);
	}

	@XmlElementDecl(namespace = NAMESPACE_URI, name = "Series")
	public JAXBElement<SeriesType> createSeries(SeriesType value) {

		return new JAXBElement<>(_Series_QNAME, SeriesType.class, null, value);
	}
}
