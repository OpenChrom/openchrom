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
package net.openchrom.xxd.converter.supplier.animl.internal.model.astm.technique;

import javax.xml.bind.JAXBElement;
import javax.xml.bind.annotation.XmlElementDecl;
import javax.xml.bind.annotation.XmlRegistry;
import javax.xml.datatype.XMLGregorianCalendar;
import javax.xml.namespace.QName;

@XmlRegistry
public class ObjectFactory {

	private final static QName _ExperimentDataRoleBlueprint_QNAME = new QName("urn:org:astm:animl:schema:technique:draft:0.90", "ExperimentDataRoleBlueprint");
	private final static QName _AllowedRange_QNAME = new QName("urn:org:astm:animl:schema:technique:draft:0.90", "AllowedRange");
	private final static QName _ExtendedExtension_QNAME = new QName("urn:org:astm:animl:schema:technique:draft:0.90", "ExtendedExtension");
	private final static QName _LiteratureReference_QNAME = new QName("urn:org:astm:animl:schema:technique:draft:0.90", "LiteratureReference");
	private final static QName _F_QNAME = new QName("urn:org:astm:animl:schema:technique:draft:0.90", "F");
	private final static QName _Max_QNAME = new QName("urn:org:astm:animl:schema:technique:draft:0.90", "Max");
	private final static QName _SVG_QNAME = new QName("urn:org:astm:animl:schema:technique:draft:0.90", "SVG");
	private final static QName _AllowedValue_QNAME = new QName("urn:org:astm:animl:schema:technique:draft:0.90", "AllowedValue");
	private final static QName _D_QNAME = new QName("urn:org:astm:animl:schema:technique:draft:0.90", "D");
	private final static QName _SeriesBlueprintChoice_QNAME = new QName("urn:org:astm:animl:schema:technique:draft:0.90", "SeriesBlueprintChoice");
	private final static QName _ExtendedTechnique_QNAME = new QName("urn:org:astm:animl:schema:technique:draft:0.90", "ExtendedTechnique");
	private final static QName _I_QNAME = new QName("urn:org:astm:animl:schema:technique:draft:0.90", "I");
	private final static QName _EmbeddedXML_QNAME = new QName("urn:org:astm:animl:schema:technique:draft:0.90", "EmbeddedXML");
	private final static QName _PNG_QNAME = new QName("urn:org:astm:animl:schema:technique:draft:0.90", "PNG");
	private final static QName _DateTime_QNAME = new QName("urn:org:astm:animl:schema:technique:draft:0.90", "DateTime");
	private final static QName _Unit_QNAME = new QName("urn:org:astm:animl:schema:technique:draft:0.90", "Unit");
	private final static QName _L_QNAME = new QName("urn:org:astm:animl:schema:technique:draft:0.90", "L");
	private final static QName _S_QNAME = new QName("urn:org:astm:animl:schema:technique:draft:0.90", "S");
	private final static QName _SampleRoleBlueprint_QNAME = new QName("urn:org:astm:animl:schema:technique:draft:0.90", "SampleRoleBlueprint");
	private final static QName _SeriesBlueprint_QNAME = new QName("urn:org:astm:animl:schema:technique:draft:0.90", "SeriesBlueprint");
	private final static QName _SeriesSetBlueprint_QNAME = new QName("urn:org:astm:animl:schema:technique:draft:0.90", "SeriesSetBlueprint");
	private final static QName _CategoryBlueprint_QNAME = new QName("urn:org:astm:animl:schema:technique:draft:0.90", "CategoryBlueprint");
	private final static QName _Boolean_QNAME = new QName("urn:org:astm:animl:schema:technique:draft:0.90", "Boolean");
	private final static QName _Documentation_QNAME = new QName("urn:org:astm:animl:schema:technique:draft:0.90", "Documentation");
	private final static QName _ParameterBlueprint_QNAME = new QName("urn:org:astm:animl:schema:technique:draft:0.90", "ParameterBlueprint");
	private final static QName _MethodBlueprint_QNAME = new QName("urn:org:astm:animl:schema:technique:draft:0.90", "MethodBlueprint");
	private final static QName _ExtensionScope_QNAME = new QName("urn:org:astm:animl:schema:technique:draft:0.90", "ExtensionScope");
	private final static QName _Min_QNAME = new QName("urn:org:astm:animl:schema:technique:draft:0.90", "Min");
	private final static QName _SIUnit_QNAME = new QName("urn:org:astm:animl:schema:technique:draft:0.90", "SIUnit");
	private final static QName _Technique_QNAME = new QName("urn:org:astm:animl:schema:technique:draft:0.90", "Technique");
	private final static QName _ResultBlueprint_QNAME = new QName("urn:org:astm:animl:schema:technique:draft:0.90", "ResultBlueprint");
	private final static QName _Bibliography_QNAME = new QName("urn:org:astm:animl:schema:technique:draft:0.90", "Bibliography");

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

	@XmlElementDecl(namespace = "urn:org:astm:animl:schema:technique:draft:0.90", name = "ExperimentDataRoleBlueprint")
	public JAXBElement<ExperimentDataRoleBlueprintType> createExperimentDataRoleBlueprint(ExperimentDataRoleBlueprintType value) {

		return new JAXBElement<ExperimentDataRoleBlueprintType>(_ExperimentDataRoleBlueprint_QNAME, ExperimentDataRoleBlueprintType.class, null, value);
	}

	@XmlElementDecl(namespace = "urn:org:astm:animl:schema:technique:draft:0.90", name = "AllowedRange")
	public JAXBElement<AllowedRangeType> createAllowedRange(AllowedRangeType value) {

		return new JAXBElement<AllowedRangeType>(_AllowedRange_QNAME, AllowedRangeType.class, null, value);
	}

	@XmlElementDecl(namespace = "urn:org:astm:animl:schema:technique:draft:0.90", name = "ExtendedExtension")
	public JAXBElement<ExtendedExtensionType> createExtendedExtension(ExtendedExtensionType value) {

		return new JAXBElement<ExtendedExtensionType>(_ExtendedExtension_QNAME, ExtendedExtensionType.class, null, value);
	}

	@XmlElementDecl(namespace = "urn:org:astm:animl:schema:technique:draft:0.90", name = "LiteratureReference")
	public JAXBElement<LiteratureReferenceType> createLiteratureReference(LiteratureReferenceType value) {

		return new JAXBElement<LiteratureReferenceType>(_LiteratureReference_QNAME, LiteratureReferenceType.class, null, value);
	}

	@XmlElementDecl(namespace = "urn:org:astm:animl:schema:technique:draft:0.90", name = "F")
	public JAXBElement<Float> createF(Float value) {

		return new JAXBElement<Float>(_F_QNAME, Float.class, null, value);
	}

	@XmlElementDecl(namespace = "urn:org:astm:animl:schema:technique:draft:0.90", name = "Max")
	public JAXBElement<MaxType> createMax(MaxType value) {

		return new JAXBElement<MaxType>(_Max_QNAME, MaxType.class, null, value);
	}

	@XmlElementDecl(namespace = "urn:org:astm:animl:schema:technique:draft:0.90", name = "SVG")
	public JAXBElement<String> createSVG(String value) {

		return new JAXBElement<String>(_SVG_QNAME, String.class, null, value);
	}

	@XmlElementDecl(namespace = "urn:org:astm:animl:schema:technique:draft:0.90", name = "AllowedValue")
	public JAXBElement<AllowedValueType> createAllowedValue(AllowedValueType value) {

		return new JAXBElement<AllowedValueType>(_AllowedValue_QNAME, AllowedValueType.class, null, value);
	}

	@XmlElementDecl(namespace = "urn:org:astm:animl:schema:technique:draft:0.90", name = "D")
	public JAXBElement<Double> createD(Double value) {

		return new JAXBElement<Double>(_D_QNAME, Double.class, null, value);
	}

	@XmlElementDecl(namespace = "urn:org:astm:animl:schema:technique:draft:0.90", name = "SeriesBlueprintChoice")
	public JAXBElement<SeriesBlueprintChoiceType> createSeriesBlueprintChoice(SeriesBlueprintChoiceType value) {

		return new JAXBElement<SeriesBlueprintChoiceType>(_SeriesBlueprintChoice_QNAME, SeriesBlueprintChoiceType.class, null, value);
	}

	@XmlElementDecl(namespace = "urn:org:astm:animl:schema:technique:draft:0.90", name = "ExtendedTechnique")
	public JAXBElement<ExtendedTechniqueType> createExtendedTechnique(ExtendedTechniqueType value) {

		return new JAXBElement<ExtendedTechniqueType>(_ExtendedTechnique_QNAME, ExtendedTechniqueType.class, null, value);
	}

	@XmlElementDecl(namespace = "urn:org:astm:animl:schema:technique:draft:0.90", name = "I")
	public JAXBElement<Integer> createI(Integer value) {

		return new JAXBElement<Integer>(_I_QNAME, Integer.class, null, value);
	}

	@XmlElementDecl(namespace = "urn:org:astm:animl:schema:technique:draft:0.90", name = "EmbeddedXML")
	public JAXBElement<String> createEmbeddedXML(String value) {

		return new JAXBElement<String>(_EmbeddedXML_QNAME, String.class, null, value);
	}

	@XmlElementDecl(namespace = "urn:org:astm:animl:schema:technique:draft:0.90", name = "PNG")
	public JAXBElement<byte[]> createPNG(byte[] value) {

		return new JAXBElement<byte[]>(_PNG_QNAME, byte[].class, null, (value));
	}

	@XmlElementDecl(namespace = "urn:org:astm:animl:schema:technique:draft:0.90", name = "DateTime")
	public JAXBElement<XMLGregorianCalendar> createDateTime(XMLGregorianCalendar value) {

		return new JAXBElement<XMLGregorianCalendar>(_DateTime_QNAME, XMLGregorianCalendar.class, null, value);
	}

	@XmlElementDecl(namespace = "urn:org:astm:animl:schema:technique:draft:0.90", name = "Unit")
	public JAXBElement<UnitType> createUnit(UnitType value) {

		return new JAXBElement<UnitType>(_Unit_QNAME, UnitType.class, null, value);
	}

	@XmlElementDecl(namespace = "urn:org:astm:animl:schema:technique:draft:0.90", name = "L")
	public JAXBElement<Long> createL(Long value) {

		return new JAXBElement<Long>(_L_QNAME, Long.class, null, value);
	}

	@XmlElementDecl(namespace = "urn:org:astm:animl:schema:technique:draft:0.90", name = "S")
	public JAXBElement<String> createS(String value) {

		return new JAXBElement<String>(_S_QNAME, String.class, null, value);
	}

	@XmlElementDecl(namespace = "urn:org:astm:animl:schema:technique:draft:0.90", name = "SampleRoleBlueprint")
	public JAXBElement<SampleRoleBlueprintType> createSampleRoleBlueprint(SampleRoleBlueprintType value) {

		return new JAXBElement<SampleRoleBlueprintType>(_SampleRoleBlueprint_QNAME, SampleRoleBlueprintType.class, null, value);
	}

	@XmlElementDecl(namespace = "urn:org:astm:animl:schema:technique:draft:0.90", name = "SeriesBlueprint")
	public JAXBElement<SeriesBlueprintType> createSeriesBlueprint(SeriesBlueprintType value) {

		return new JAXBElement<SeriesBlueprintType>(_SeriesBlueprint_QNAME, SeriesBlueprintType.class, null, value);
	}

	@XmlElementDecl(namespace = "urn:org:astm:animl:schema:technique:draft:0.90", name = "SeriesSetBlueprint")
	public JAXBElement<SeriesSetBlueprintType> createSeriesSetBlueprint(SeriesSetBlueprintType value) {

		return new JAXBElement<SeriesSetBlueprintType>(_SeriesSetBlueprint_QNAME, SeriesSetBlueprintType.class, null, value);
	}

	@XmlElementDecl(namespace = "urn:org:astm:animl:schema:technique:draft:0.90", name = "CategoryBlueprint")
	public JAXBElement<CategoryBlueprintType> createCategoryBlueprint(CategoryBlueprintType value) {

		return new JAXBElement<CategoryBlueprintType>(_CategoryBlueprint_QNAME, CategoryBlueprintType.class, null, value);
	}

	@XmlElementDecl(namespace = "urn:org:astm:animl:schema:technique:draft:0.90", name = "Boolean")
	public JAXBElement<Boolean> createBoolean(Boolean value) {

		return new JAXBElement<Boolean>(_Boolean_QNAME, Boolean.class, null, value);
	}

	@XmlElementDecl(namespace = "urn:org:astm:animl:schema:technique:draft:0.90", name = "Documentation")
	public JAXBElement<DocumentationType> createDocumentation(DocumentationType value) {

		return new JAXBElement<DocumentationType>(_Documentation_QNAME, DocumentationType.class, null, value);
	}

	@XmlElementDecl(namespace = "urn:org:astm:animl:schema:technique:draft:0.90", name = "ParameterBlueprint")
	public JAXBElement<ParameterBlueprintType> createParameterBlueprint(ParameterBlueprintType value) {

		return new JAXBElement<ParameterBlueprintType>(_ParameterBlueprint_QNAME, ParameterBlueprintType.class, null, value);
	}

	@XmlElementDecl(namespace = "urn:org:astm:animl:schema:technique:draft:0.90", name = "MethodBlueprint")
	public JAXBElement<MethodBlueprintType> createMethodBlueprint(MethodBlueprintType value) {

		return new JAXBElement<MethodBlueprintType>(_MethodBlueprint_QNAME, MethodBlueprintType.class, null, value);
	}

	@XmlElementDecl(namespace = "urn:org:astm:animl:schema:technique:draft:0.90", name = "ExtensionScope")
	public JAXBElement<ExtensionScopeType> createExtensionScope(ExtensionScopeType value) {

		return new JAXBElement<ExtensionScopeType>(_ExtensionScope_QNAME, ExtensionScopeType.class, null, value);
	}

	@XmlElementDecl(namespace = "urn:org:astm:animl:schema:technique:draft:0.90", name = "Min")
	public JAXBElement<MinType> createMin(MinType value) {

		return new JAXBElement<MinType>(_Min_QNAME, MinType.class, null, value);
	}

	@XmlElementDecl(namespace = "urn:org:astm:animl:schema:technique:draft:0.90", name = "SIUnit")
	public JAXBElement<SIUnitType> createSIUnit(SIUnitType value) {

		return new JAXBElement<SIUnitType>(_SIUnit_QNAME, SIUnitType.class, null, value);
	}

	@XmlElementDecl(namespace = "urn:org:astm:animl:schema:technique:draft:0.90", name = "Technique")
	public JAXBElement<TechniqueType> createTechnique(TechniqueType value) {

		return new JAXBElement<TechniqueType>(_Technique_QNAME, TechniqueType.class, null, value);
	}

	@XmlElementDecl(namespace = "urn:org:astm:animl:schema:technique:draft:0.90", name = "ResultBlueprint")
	public JAXBElement<ResultBlueprintType> createResultBlueprint(ResultBlueprintType value) {

		return new JAXBElement<ResultBlueprintType>(_ResultBlueprint_QNAME, ResultBlueprintType.class, null, value);
	}

	@XmlElementDecl(namespace = "urn:org:astm:animl:schema:technique:draft:0.90", name = "Bibliography")
	public JAXBElement<BibliographyType> createBibliography(BibliographyType value) {

		return new JAXBElement<BibliographyType>(_Bibliography_QNAME, BibliographyType.class, null, value);
	}
}
