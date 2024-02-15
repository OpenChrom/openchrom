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
package net.openchrom.xxd.converter.supplier.animl.model.astm.technique;

import javax.xml.datatype.XMLGregorianCalendar;
import javax.xml.namespace.QName;

import jakarta.xml.bind.JAXBElement;
import jakarta.xml.bind.annotation.XmlElementDecl;
import jakarta.xml.bind.annotation.XmlRegistry;

@XmlRegistry
public class ObjectFactory {

	private static final String NAMESPACE_URI = "urn:org:astm:animl:schema:technique:draft:0.90";
	private static final QName _ExperimentDataRoleBlueprint_QNAME = new QName(NAMESPACE_URI, "ExperimentDataRoleBlueprint");
	private static final QName _AllowedRange_QNAME = new QName(NAMESPACE_URI, "AllowedRange");
	private static final QName _ExtendedExtension_QNAME = new QName(NAMESPACE_URI, "ExtendedExtension");
	private static final QName _LiteratureReference_QNAME = new QName(NAMESPACE_URI, "LiteratureReference");
	private static final QName _F_QNAME = new QName(NAMESPACE_URI, "F");
	private static final QName _Max_QNAME = new QName(NAMESPACE_URI, "Max");
	private static final QName _SVG_QNAME = new QName(NAMESPACE_URI, "SVG");
	private static final QName _AllowedValue_QNAME = new QName(NAMESPACE_URI, "AllowedValue");
	private static final QName _D_QNAME = new QName(NAMESPACE_URI, "D");
	private static final QName _SeriesBlueprintChoice_QNAME = new QName(NAMESPACE_URI, "SeriesBlueprintChoice");
	private static final QName _ExtendedTechnique_QNAME = new QName(NAMESPACE_URI, "ExtendedTechnique");
	private static final QName _I_QNAME = new QName(NAMESPACE_URI, "I");
	private static final QName _EmbeddedXML_QNAME = new QName(NAMESPACE_URI, "EmbeddedXML");
	private static final QName _PNG_QNAME = new QName(NAMESPACE_URI, "PNG");
	private static final QName _DateTime_QNAME = new QName(NAMESPACE_URI, "DateTime");
	private static final QName _Unit_QNAME = new QName(NAMESPACE_URI, "Unit");
	private static final QName _L_QNAME = new QName(NAMESPACE_URI, "L");
	private static final QName _S_QNAME = new QName(NAMESPACE_URI, "S");
	private static final QName _SampleRoleBlueprint_QNAME = new QName(NAMESPACE_URI, "SampleRoleBlueprint");
	private static final QName _SeriesBlueprint_QNAME = new QName(NAMESPACE_URI, "SeriesBlueprint");
	private static final QName _SeriesSetBlueprint_QNAME = new QName(NAMESPACE_URI, "SeriesSetBlueprint");
	private static final QName _CategoryBlueprint_QNAME = new QName(NAMESPACE_URI, "CategoryBlueprint");
	private static final QName _Boolean_QNAME = new QName(NAMESPACE_URI, "Boolean");
	private static final QName _Documentation_QNAME = new QName(NAMESPACE_URI, "Documentation");
	private static final QName _ParameterBlueprint_QNAME = new QName(NAMESPACE_URI, "ParameterBlueprint");
	private static final QName _MethodBlueprint_QNAME = new QName(NAMESPACE_URI, "MethodBlueprint");
	private static final QName _ExtensionScope_QNAME = new QName(NAMESPACE_URI, "ExtensionScope");
	private static final QName _Min_QNAME = new QName(NAMESPACE_URI, "Min");
	private static final QName _SIUnit_QNAME = new QName(NAMESPACE_URI, "SIUnit");
	private static final QName _Technique_QNAME = new QName(NAMESPACE_URI, "Technique");
	private static final QName _ResultBlueprint_QNAME = new QName(NAMESPACE_URI, "ResultBlueprint");
	private static final QName _Bibliography_QNAME = new QName(NAMESPACE_URI, "Bibliography");

	public ObjectFactory() {

	}

	public AllowedRangeType createAllowedRangeType() {

		return new AllowedRangeType();
	}

	public ExperimentDataRoleBlueprintType createExperimentDataRoleBlueprintType() {

		return new ExperimentDataRoleBlueprintType();
	}

	public AllowedValueType createAllowedValueType() {

		return new AllowedValueType();
	}

	public MaxType createMaxType() {

		return new MaxType();
	}

	public ExtendedExtensionType createExtendedExtensionType() {

		return new ExtendedExtensionType();
	}

	public LiteratureReferenceType createLiteratureReferenceType() {

		return new LiteratureReferenceType();
	}

	public ExtendedTechniqueType createExtendedTechniqueType() {

		return new ExtendedTechniqueType();
	}

	public SeriesBlueprintChoiceType createSeriesBlueprintChoiceType() {

		return new SeriesBlueprintChoiceType();
	}

	public UnitType createUnitType() {

		return new UnitType();
	}

	public SampleRoleBlueprintType createSampleRoleBlueprintType() {

		return new SampleRoleBlueprintType();
	}

	public SeriesSetBlueprintType createSeriesSetBlueprintType() {

		return new SeriesSetBlueprintType();
	}

	public SeriesBlueprintType createSeriesBlueprintType() {

		return new SeriesBlueprintType();
	}

	public CategoryBlueprintType createCategoryBlueprintType() {

		return new CategoryBlueprintType();
	}

	public ParameterBlueprintType createParameterBlueprintType() {

		return new ParameterBlueprintType();
	}

	public DocumentationType createDocumentationType() {

		return new DocumentationType();
	}

	public ExtensionScopeType createExtensionScopeType() {

		return new ExtensionScopeType();
	}

	public Quantity createQuantity() {

		return new Quantity();
	}

	public QuantityType createQuantityType() {

		return new QuantityType();
	}

	public MethodBlueprintType createMethodBlueprintType() {

		return new MethodBlueprintType();
	}

	public MinType createMinType() {

		return new MinType();
	}

	public SIUnitType createSIUnitType() {

		return new SIUnitType();
	}

	public TechniqueType createTechniqueType() {

		return new TechniqueType();
	}

	public ResultBlueprintType createResultBlueprintType() {

		return new ResultBlueprintType();
	}

	public BibliographyType createBibliographyType() {

		return new BibliographyType();
	}

	public NumericValueType createNumericValueType() {

		return new NumericValueType();
	}

	public ParameterValueType createParameterValueType() {

		return new ParameterValueType();
	}

	@XmlElementDecl(namespace = NAMESPACE_URI, name = "ExperimentDataRoleBlueprint")
	public JAXBElement<ExperimentDataRoleBlueprintType> createExperimentDataRoleBlueprint(ExperimentDataRoleBlueprintType value) {

		return new JAXBElement<>(_ExperimentDataRoleBlueprint_QNAME, ExperimentDataRoleBlueprintType.class, null, value);
	}

	@XmlElementDecl(namespace = NAMESPACE_URI, name = "AllowedRange")
	public JAXBElement<AllowedRangeType> createAllowedRange(AllowedRangeType value) {

		return new JAXBElement<>(_AllowedRange_QNAME, AllowedRangeType.class, null, value);
	}

	@XmlElementDecl(namespace = NAMESPACE_URI, name = "ExtendedExtension")
	public JAXBElement<ExtendedExtensionType> createExtendedExtension(ExtendedExtensionType value) {

		return new JAXBElement<>(_ExtendedExtension_QNAME, ExtendedExtensionType.class, null, value);
	}

	@XmlElementDecl(namespace = NAMESPACE_URI, name = "LiteratureReference")
	public JAXBElement<LiteratureReferenceType> createLiteratureReference(LiteratureReferenceType value) {

		return new JAXBElement<>(_LiteratureReference_QNAME, LiteratureReferenceType.class, null, value);
	}

	@XmlElementDecl(namespace = NAMESPACE_URI, name = "F")
	public JAXBElement<Float> createF(Float value) {

		return new JAXBElement<>(_F_QNAME, Float.class, null, value);
	}

	@XmlElementDecl(namespace = NAMESPACE_URI, name = "Max")
	public JAXBElement<MaxType> createMax(MaxType value) {

		return new JAXBElement<>(_Max_QNAME, MaxType.class, null, value);
	}

	@XmlElementDecl(namespace = NAMESPACE_URI, name = "SVG")
	public JAXBElement<String> createSVG(String value) {

		return new JAXBElement<>(_SVG_QNAME, String.class, null, value);
	}

	@XmlElementDecl(namespace = NAMESPACE_URI, name = "AllowedValue")
	public JAXBElement<AllowedValueType> createAllowedValue(AllowedValueType value) {

		return new JAXBElement<>(_AllowedValue_QNAME, AllowedValueType.class, null, value);
	}

	@XmlElementDecl(namespace = NAMESPACE_URI, name = "D")
	public JAXBElement<Double> createD(Double value) {

		return new JAXBElement<>(_D_QNAME, Double.class, null, value);
	}

	@XmlElementDecl(namespace = NAMESPACE_URI, name = "SeriesBlueprintChoice")
	public JAXBElement<SeriesBlueprintChoiceType> createSeriesBlueprintChoice(SeriesBlueprintChoiceType value) {

		return new JAXBElement<>(_SeriesBlueprintChoice_QNAME, SeriesBlueprintChoiceType.class, null, value);
	}

	@XmlElementDecl(namespace = NAMESPACE_URI, name = "ExtendedTechnique")
	public JAXBElement<ExtendedTechniqueType> createExtendedTechnique(ExtendedTechniqueType value) {

		return new JAXBElement<>(_ExtendedTechnique_QNAME, ExtendedTechniqueType.class, null, value);
	}

	@XmlElementDecl(namespace = NAMESPACE_URI, name = "I")
	public JAXBElement<Integer> createI(Integer value) {

		return new JAXBElement<>(_I_QNAME, Integer.class, null, value);
	}

	@XmlElementDecl(namespace = NAMESPACE_URI, name = "EmbeddedXML")
	public JAXBElement<String> createEmbeddedXML(String value) {

		return new JAXBElement<>(_EmbeddedXML_QNAME, String.class, null, value);
	}

	@XmlElementDecl(namespace = NAMESPACE_URI, name = "PNG")
	public JAXBElement<byte[]> createPNG(byte[] value) {

		return new JAXBElement<>(_PNG_QNAME, byte[].class, null, (value));
	}

	@XmlElementDecl(namespace = NAMESPACE_URI, name = "DateTime")
	public JAXBElement<XMLGregorianCalendar> createDateTime(XMLGregorianCalendar value) {

		return new JAXBElement<>(_DateTime_QNAME, XMLGregorianCalendar.class, null, value);
	}

	@XmlElementDecl(namespace = NAMESPACE_URI, name = "Unit")
	public JAXBElement<UnitType> createUnit(UnitType value) {

		return new JAXBElement<>(_Unit_QNAME, UnitType.class, null, value);
	}

	@XmlElementDecl(namespace = NAMESPACE_URI, name = "L")
	public JAXBElement<Long> createL(Long value) {

		return new JAXBElement<>(_L_QNAME, Long.class, null, value);
	}

	@XmlElementDecl(namespace = NAMESPACE_URI, name = "S")
	public JAXBElement<String> createS(String value) {

		return new JAXBElement<>(_S_QNAME, String.class, null, value);
	}

	@XmlElementDecl(namespace = NAMESPACE_URI, name = "SampleRoleBlueprint")
	public JAXBElement<SampleRoleBlueprintType> createSampleRoleBlueprint(SampleRoleBlueprintType value) {

		return new JAXBElement<>(_SampleRoleBlueprint_QNAME, SampleRoleBlueprintType.class, null, value);
	}

	@XmlElementDecl(namespace = NAMESPACE_URI, name = "SeriesBlueprint")
	public JAXBElement<SeriesBlueprintType> createSeriesBlueprint(SeriesBlueprintType value) {

		return new JAXBElement<>(_SeriesBlueprint_QNAME, SeriesBlueprintType.class, null, value);
	}

	@XmlElementDecl(namespace = NAMESPACE_URI, name = "SeriesSetBlueprint")
	public JAXBElement<SeriesSetBlueprintType> createSeriesSetBlueprint(SeriesSetBlueprintType value) {

		return new JAXBElement<>(_SeriesSetBlueprint_QNAME, SeriesSetBlueprintType.class, null, value);
	}

	@XmlElementDecl(namespace = NAMESPACE_URI, name = "CategoryBlueprint")
	public JAXBElement<CategoryBlueprintType> createCategoryBlueprint(CategoryBlueprintType value) {

		return new JAXBElement<>(_CategoryBlueprint_QNAME, CategoryBlueprintType.class, null, value);
	}

	@XmlElementDecl(namespace = NAMESPACE_URI, name = "Boolean")
	public JAXBElement<Boolean> createBoolean(Boolean value) {

		return new JAXBElement<>(_Boolean_QNAME, Boolean.class, null, value);
	}

	@XmlElementDecl(namespace = NAMESPACE_URI, name = "Documentation")
	public JAXBElement<DocumentationType> createDocumentation(DocumentationType value) {

		return new JAXBElement<>(_Documentation_QNAME, DocumentationType.class, null, value);
	}

	@XmlElementDecl(namespace = NAMESPACE_URI, name = "ParameterBlueprint")
	public JAXBElement<ParameterBlueprintType> createParameterBlueprint(ParameterBlueprintType value) {

		return new JAXBElement<>(_ParameterBlueprint_QNAME, ParameterBlueprintType.class, null, value);
	}

	@XmlElementDecl(namespace = NAMESPACE_URI, name = "MethodBlueprint")
	public JAXBElement<MethodBlueprintType> createMethodBlueprint(MethodBlueprintType value) {

		return new JAXBElement<>(_MethodBlueprint_QNAME, MethodBlueprintType.class, null, value);
	}

	@XmlElementDecl(namespace = NAMESPACE_URI, name = "ExtensionScope")
	public JAXBElement<ExtensionScopeType> createExtensionScope(ExtensionScopeType value) {

		return new JAXBElement<>(_ExtensionScope_QNAME, ExtensionScopeType.class, null, value);
	}

	@XmlElementDecl(namespace = NAMESPACE_URI, name = "Min")
	public JAXBElement<MinType> createMin(MinType value) {

		return new JAXBElement<>(_Min_QNAME, MinType.class, null, value);
	}

	@XmlElementDecl(namespace = NAMESPACE_URI, name = "SIUnit")
	public JAXBElement<SIUnitType> createSIUnit(SIUnitType value) {

		return new JAXBElement<>(_SIUnit_QNAME, SIUnitType.class, null, value);
	}

	@XmlElementDecl(namespace = NAMESPACE_URI, name = "Technique")
	public JAXBElement<TechniqueType> createTechnique(TechniqueType value) {

		return new JAXBElement<>(_Technique_QNAME, TechniqueType.class, null, value);
	}

	@XmlElementDecl(namespace = NAMESPACE_URI, name = "ResultBlueprint")
	public JAXBElement<ResultBlueprintType> createResultBlueprint(ResultBlueprintType value) {

		return new JAXBElement<>(_ResultBlueprint_QNAME, ResultBlueprintType.class, null, value);
	}

	@XmlElementDecl(namespace = NAMESPACE_URI, name = "Bibliography")
	public JAXBElement<BibliographyType> createBibliography(BibliographyType value) {

		return new JAXBElement<>(_Bibliography_QNAME, BibliographyType.class, null, value);
	}
}
