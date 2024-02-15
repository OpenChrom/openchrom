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
package net.openchrom.xxd.converter.supplier.animl.model.w3;

import java.math.BigInteger;

import javax.xml.namespace.QName;

import jakarta.xml.bind.JAXBElement;
import jakarta.xml.bind.annotation.XmlElementDecl;
import jakarta.xml.bind.annotation.XmlRegistry;

@XmlRegistry
public class ObjectFactory {

	private static final String NAMESPACE_URI = "http://www.w3.org/2000/09/xmldsig#";
	private static final QName _KeyInfo_QNAME = new QName(NAMESPACE_URI, "KeyInfo");
	private static final QName _SignatureProperty_QNAME = new QName(NAMESPACE_URI, "SignatureProperty");
	private static final QName _RSAKeyValue_QNAME = new QName(NAMESPACE_URI, "RSAKeyValue");
	private static final QName _SignatureMethod_QNAME = new QName(NAMESPACE_URI, "SignatureMethod");
	private static final QName _Object_QNAME = new QName(NAMESPACE_URI, "Object");
	private static final QName _PGPData_QNAME = new QName(NAMESPACE_URI, "PGPData");
	private static final QName _RetrievalMethod_QNAME = new QName(NAMESPACE_URI, "RetrievalMethod");
	private static final QName _DSAKeyValue_QNAME = new QName(NAMESPACE_URI, "DSAKeyValue");
	private static final QName _SPKIData_QNAME = new QName(NAMESPACE_URI, "SPKIData");
	private static final QName _SignatureValue_QNAME = new QName(NAMESPACE_URI, "SignatureValue");
	private static final QName _KeyValue_QNAME = new QName(NAMESPACE_URI, "KeyValue");
	private static final QName _Transforms_QNAME = new QName(NAMESPACE_URI, "Transforms");
	private static final QName _DigestMethod_QNAME = new QName(NAMESPACE_URI, "DigestMethod");
	private static final QName _X509Data_QNAME = new QName(NAMESPACE_URI, "X509Data");
	private static final QName _KeyName_QNAME = new QName(NAMESPACE_URI, "KeyName");
	private static final QName _Signature_QNAME = new QName(NAMESPACE_URI, "Signature");
	private static final QName _MgmtData_QNAME = new QName(NAMESPACE_URI, "MgmtData");
	private static final QName _SignatureProperties_QNAME = new QName(NAMESPACE_URI, "SignatureProperties");
	private static final QName _Transform_QNAME = new QName(NAMESPACE_URI, "Transform");
	private static final QName _Reference_QNAME = new QName(NAMESPACE_URI, "Reference");
	private static final QName _DigestValue_QNAME = new QName(NAMESPACE_URI, "DigestValue");
	private static final QName _CanonicalizationMethod_QNAME = new QName(NAMESPACE_URI, "CanonicalizationMethod");
	private static final QName _SignedInfo_QNAME = new QName(NAMESPACE_URI, "SignedInfo");
	private static final QName _Manifest_QNAME = new QName(NAMESPACE_URI, "Manifest");
	private static final QName _SignatureMethodTypeHMACOutputLength_QNAME = new QName(NAMESPACE_URI, "HMACOutputLength");
	private static final QName _X509DataTypeX509IssuerSerial_QNAME = new QName(NAMESPACE_URI, "X509IssuerSerial");
	private static final QName _X509DataTypeX509CRL_QNAME = new QName(NAMESPACE_URI, "X509CRL");
	private static final QName _X509DataTypeX509SubjectName_QNAME = new QName(NAMESPACE_URI, "X509SubjectName");
	private static final QName _X509DataTypeX509SKI_QNAME = new QName(NAMESPACE_URI, "X509SKI");
	private static final QName _X509DataTypeX509Certificate_QNAME = new QName(NAMESPACE_URI, "X509Certificate");
	private static final QName _TransformTypeXPath_QNAME = new QName(NAMESPACE_URI, "XPath");
	private static final QName _PGPDataTypePGPKeyID_QNAME = new QName(NAMESPACE_URI, "PGPKeyID");
	private static final QName _PGPDataTypePGPKeyPacket_QNAME = new QName(NAMESPACE_URI, "PGPKeyPacket");
	private static final QName _SPKIDataTypeSPKISexp_QNAME = new QName(NAMESPACE_URI, "SPKISexp");

	public ObjectFactory() {

	}

	public SignatureType createSignatureType() {

		return new SignatureType();
	}

	public PGPDataType createPGPDataType() {

		return new PGPDataType();
	}

	public KeyValueType createKeyValueType() {

		return new KeyValueType();
	}

	public DSAKeyValueType createDSAKeyValueType() {

		return new DSAKeyValueType();
	}

	public ReferenceType createReferenceType() {

		return new ReferenceType();
	}

	public RetrievalMethodType createRetrievalMethodType() {

		return new RetrievalMethodType();
	}

	public TransformsType createTransformsType() {

		return new TransformsType();
	}

	public CanonicalizationMethodType createCanonicalizationMethodType() {

		return new CanonicalizationMethodType();
	}

	public DigestMethodType createDigestMethodType() {

		return new DigestMethodType();
	}

	public ManifestType createManifestType() {

		return new ManifestType();
	}

	public SignaturePropertyType createSignaturePropertyType() {

		return new SignaturePropertyType();
	}

	public X509DataType createX509DataType() {

		return new X509DataType();
	}

	public SignedInfoType createSignedInfoType() {

		return new SignedInfoType();
	}

	public RSAKeyValueType createRSAKeyValueType() {

		return new RSAKeyValueType();
	}

	public SPKIDataType createSPKIDataType() {

		return new SPKIDataType();
	}

	public SignatureValueType createSignatureValueType() {

		return new SignatureValueType();
	}

	public KeyInfoType createKeyInfoType() {

		return new KeyInfoType();
	}

	public SignaturePropertiesType createSignaturePropertiesType() {

		return new SignaturePropertiesType();
	}

	public SignatureMethodType createSignatureMethodType() {

		return new SignatureMethodType();
	}

	public ObjectType createObjectType() {

		return new ObjectType();
	}

	public TransformType createTransformType() {

		return new TransformType();
	}

	public X509IssuerSerialType createX509IssuerSerialType() {

		return new X509IssuerSerialType();
	}

	@XmlElementDecl(namespace = NAMESPACE_URI, name = "KeyInfo")
	public JAXBElement<KeyInfoType> createKeyInfo(KeyInfoType value) {

		return new JAXBElement<>(_KeyInfo_QNAME, KeyInfoType.class, null, value);
	}

	@XmlElementDecl(namespace = NAMESPACE_URI, name = "SignatureProperty")
	public JAXBElement<SignaturePropertyType> createSignatureProperty(SignaturePropertyType value) {

		return new JAXBElement<>(_SignatureProperty_QNAME, SignaturePropertyType.class, null, value);
	}

	@XmlElementDecl(namespace = NAMESPACE_URI, name = "RSAKeyValue")
	public JAXBElement<RSAKeyValueType> createRSAKeyValue(RSAKeyValueType value) {

		return new JAXBElement<>(_RSAKeyValue_QNAME, RSAKeyValueType.class, null, value);
	}

	@XmlElementDecl(namespace = NAMESPACE_URI, name = "SignatureMethod")
	public JAXBElement<SignatureMethodType> createSignatureMethod(SignatureMethodType value) {

		return new JAXBElement<>(_SignatureMethod_QNAME, SignatureMethodType.class, null, value);
	}

	@XmlElementDecl(namespace = NAMESPACE_URI, name = "Object")
	public JAXBElement<ObjectType> createObject(ObjectType value) {

		return new JAXBElement<>(_Object_QNAME, ObjectType.class, null, value);
	}

	@XmlElementDecl(namespace = NAMESPACE_URI, name = "PGPData")
	public JAXBElement<PGPDataType> createPGPData(PGPDataType value) {

		return new JAXBElement<>(_PGPData_QNAME, PGPDataType.class, null, value);
	}

	@XmlElementDecl(namespace = NAMESPACE_URI, name = "RetrievalMethod")
	public JAXBElement<RetrievalMethodType> createRetrievalMethod(RetrievalMethodType value) {

		return new JAXBElement<>(_RetrievalMethod_QNAME, RetrievalMethodType.class, null, value);
	}

	@XmlElementDecl(namespace = NAMESPACE_URI, name = "DSAKeyValue")
	public JAXBElement<DSAKeyValueType> createDSAKeyValue(DSAKeyValueType value) {

		return new JAXBElement<>(_DSAKeyValue_QNAME, DSAKeyValueType.class, null, value);
	}

	@XmlElementDecl(namespace = NAMESPACE_URI, name = "SPKIData")
	public JAXBElement<SPKIDataType> createSPKIData(SPKIDataType value) {

		return new JAXBElement<>(_SPKIData_QNAME, SPKIDataType.class, null, value);
	}

	@XmlElementDecl(namespace = NAMESPACE_URI, name = "SignatureValue")
	public JAXBElement<SignatureValueType> createSignatureValue(SignatureValueType value) {

		return new JAXBElement<>(_SignatureValue_QNAME, SignatureValueType.class, null, value);
	}

	@XmlElementDecl(namespace = NAMESPACE_URI, name = "KeyValue")
	public JAXBElement<KeyValueType> createKeyValue(KeyValueType value) {

		return new JAXBElement<>(_KeyValue_QNAME, KeyValueType.class, null, value);
	}

	@XmlElementDecl(namespace = NAMESPACE_URI, name = "Transforms")
	public JAXBElement<TransformsType> createTransforms(TransformsType value) {

		return new JAXBElement<>(_Transforms_QNAME, TransformsType.class, null, value);
	}

	@XmlElementDecl(namespace = NAMESPACE_URI, name = "DigestMethod")
	public JAXBElement<DigestMethodType> createDigestMethod(DigestMethodType value) {

		return new JAXBElement<>(_DigestMethod_QNAME, DigestMethodType.class, null, value);
	}

	@XmlElementDecl(namespace = NAMESPACE_URI, name = "X509Data")
	public JAXBElement<X509DataType> createX509Data(X509DataType value) {

		return new JAXBElement<>(_X509Data_QNAME, X509DataType.class, null, value);
	}

	@XmlElementDecl(namespace = NAMESPACE_URI, name = "KeyName")
	public JAXBElement<String> createKeyName(String value) {

		return new JAXBElement<>(_KeyName_QNAME, String.class, null, value);
	}

	@XmlElementDecl(namespace = NAMESPACE_URI, name = "Signature")
	public JAXBElement<SignatureType> createSignature(SignatureType value) {

		return new JAXBElement<>(_Signature_QNAME, SignatureType.class, null, value);
	}

	@XmlElementDecl(namespace = NAMESPACE_URI, name = "MgmtData")
	public JAXBElement<String> createMgmtData(String value) {

		return new JAXBElement<>(_MgmtData_QNAME, String.class, null, value);
	}

	@XmlElementDecl(namespace = NAMESPACE_URI, name = "SignatureProperties")
	public JAXBElement<SignaturePropertiesType> createSignatureProperties(SignaturePropertiesType value) {

		return new JAXBElement<>(_SignatureProperties_QNAME, SignaturePropertiesType.class, null, value);
	}

	@XmlElementDecl(namespace = NAMESPACE_URI, name = "Transform")
	public JAXBElement<TransformType> createTransform(TransformType value) {

		return new JAXBElement<>(_Transform_QNAME, TransformType.class, null, value);
	}

	@XmlElementDecl(namespace = NAMESPACE_URI, name = "Reference")
	public JAXBElement<ReferenceType> createReference(ReferenceType value) {

		return new JAXBElement<>(_Reference_QNAME, ReferenceType.class, null, value);
	}

	@XmlElementDecl(namespace = NAMESPACE_URI, name = "DigestValue")
	public JAXBElement<byte[]> createDigestValue(byte[] value) {

		return new JAXBElement<>(_DigestValue_QNAME, byte[].class, null, (value));
	}

	@XmlElementDecl(namespace = NAMESPACE_URI, name = "CanonicalizationMethod")
	public JAXBElement<CanonicalizationMethodType> createCanonicalizationMethod(CanonicalizationMethodType value) {

		return new JAXBElement<>(_CanonicalizationMethod_QNAME, CanonicalizationMethodType.class, null, value);
	}

	@XmlElementDecl(namespace = NAMESPACE_URI, name = "SignedInfo")
	public JAXBElement<SignedInfoType> createSignedInfo(SignedInfoType value) {

		return new JAXBElement<>(_SignedInfo_QNAME, SignedInfoType.class, null, value);
	}

	@XmlElementDecl(namespace = NAMESPACE_URI, name = "Manifest")
	public JAXBElement<ManifestType> createManifest(ManifestType value) {

		return new JAXBElement<>(_Manifest_QNAME, ManifestType.class, null, value);
	}

	@XmlElementDecl(namespace = NAMESPACE_URI, name = "HMACOutputLength", scope = SignatureMethodType.class)
	public JAXBElement<BigInteger> createSignatureMethodTypeHMACOutputLength(BigInteger value) {

		return new JAXBElement<>(_SignatureMethodTypeHMACOutputLength_QNAME, BigInteger.class, SignatureMethodType.class, value);
	}

	@XmlElementDecl(namespace = NAMESPACE_URI, name = "X509IssuerSerial", scope = X509DataType.class)
	public JAXBElement<X509IssuerSerialType> createX509DataTypeX509IssuerSerial(X509IssuerSerialType value) {

		return new JAXBElement<>(_X509DataTypeX509IssuerSerial_QNAME, X509IssuerSerialType.class, X509DataType.class, value);
	}

	@XmlElementDecl(namespace = NAMESPACE_URI, name = "X509CRL", scope = X509DataType.class)
	public JAXBElement<byte[]> createX509DataTypeX509CRL(byte[] value) {

		return new JAXBElement<>(_X509DataTypeX509CRL_QNAME, byte[].class, X509DataType.class, (value));
	}

	@XmlElementDecl(namespace = NAMESPACE_URI, name = "X509SubjectName", scope = X509DataType.class)
	public JAXBElement<String> createX509DataTypeX509SubjectName(String value) {

		return new JAXBElement<>(_X509DataTypeX509SubjectName_QNAME, String.class, X509DataType.class, value);
	}

	@XmlElementDecl(namespace = NAMESPACE_URI, name = "X509SKI", scope = X509DataType.class)
	public JAXBElement<byte[]> createX509DataTypeX509SKI(byte[] value) {

		return new JAXBElement<>(_X509DataTypeX509SKI_QNAME, byte[].class, X509DataType.class, (value));
	}

	@XmlElementDecl(namespace = NAMESPACE_URI, name = "X509Certificate", scope = X509DataType.class)
	public JAXBElement<byte[]> createX509DataTypeX509Certificate(byte[] value) {

		return new JAXBElement<>(_X509DataTypeX509Certificate_QNAME, byte[].class, X509DataType.class, (value));
	}

	@XmlElementDecl(namespace = NAMESPACE_URI, name = "XPath", scope = TransformType.class)
	public JAXBElement<String> createTransformTypeXPath(String value) {

		return new JAXBElement<>(_TransformTypeXPath_QNAME, String.class, TransformType.class, value);
	}

	@XmlElementDecl(namespace = NAMESPACE_URI, name = "PGPKeyID", scope = PGPDataType.class)
	public JAXBElement<byte[]> createPGPDataTypePGPKeyID(byte[] value) {

		return new JAXBElement<>(_PGPDataTypePGPKeyID_QNAME, byte[].class, PGPDataType.class, (value));
	}

	@XmlElementDecl(namespace = NAMESPACE_URI, name = "PGPKeyPacket", scope = PGPDataType.class)
	public JAXBElement<byte[]> createPGPDataTypePGPKeyPacket(byte[] value) {

		return new JAXBElement<>(_PGPDataTypePGPKeyPacket_QNAME, byte[].class, PGPDataType.class, (value));
	}

	@XmlElementDecl(namespace = NAMESPACE_URI, name = "SPKISexp", scope = SPKIDataType.class)
	public JAXBElement<byte[]> createSPKIDataTypeSPKISexp(byte[] value) {

		return new JAXBElement<>(_SPKIDataTypeSPKISexp_QNAME, byte[].class, SPKIDataType.class, (value));
	}
}
