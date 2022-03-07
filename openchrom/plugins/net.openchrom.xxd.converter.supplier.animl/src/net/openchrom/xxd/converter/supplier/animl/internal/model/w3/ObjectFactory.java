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
package net.openchrom.xxd.converter.supplier.animl.internal.model.w3;

import java.math.BigInteger;

import jakarta.xml.bind.JAXBElement;
import jakarta.xml.bind.annotation.XmlElementDecl;
import jakarta.xml.bind.annotation.XmlRegistry;
import javax.xml.namespace.QName;

@XmlRegistry
public class ObjectFactory {

	private final static QName _KeyInfo_QNAME = new QName("http://www.w3.org/2000/09/xmldsig#", "KeyInfo");
	private final static QName _SignatureProperty_QNAME = new QName("http://www.w3.org/2000/09/xmldsig#", "SignatureProperty");
	private final static QName _RSAKeyValue_QNAME = new QName("http://www.w3.org/2000/09/xmldsig#", "RSAKeyValue");
	private final static QName _SignatureMethod_QNAME = new QName("http://www.w3.org/2000/09/xmldsig#", "SignatureMethod");
	private final static QName _Object_QNAME = new QName("http://www.w3.org/2000/09/xmldsig#", "Object");
	private final static QName _PGPData_QNAME = new QName("http://www.w3.org/2000/09/xmldsig#", "PGPData");
	private final static QName _RetrievalMethod_QNAME = new QName("http://www.w3.org/2000/09/xmldsig#", "RetrievalMethod");
	private final static QName _DSAKeyValue_QNAME = new QName("http://www.w3.org/2000/09/xmldsig#", "DSAKeyValue");
	private final static QName _SPKIData_QNAME = new QName("http://www.w3.org/2000/09/xmldsig#", "SPKIData");
	private final static QName _SignatureValue_QNAME = new QName("http://www.w3.org/2000/09/xmldsig#", "SignatureValue");
	private final static QName _KeyValue_QNAME = new QName("http://www.w3.org/2000/09/xmldsig#", "KeyValue");
	private final static QName _Transforms_QNAME = new QName("http://www.w3.org/2000/09/xmldsig#", "Transforms");
	private final static QName _DigestMethod_QNAME = new QName("http://www.w3.org/2000/09/xmldsig#", "DigestMethod");
	private final static QName _X509Data_QNAME = new QName("http://www.w3.org/2000/09/xmldsig#", "X509Data");
	private final static QName _KeyName_QNAME = new QName("http://www.w3.org/2000/09/xmldsig#", "KeyName");
	private final static QName _Signature_QNAME = new QName("http://www.w3.org/2000/09/xmldsig#", "Signature");
	private final static QName _MgmtData_QNAME = new QName("http://www.w3.org/2000/09/xmldsig#", "MgmtData");
	private final static QName _SignatureProperties_QNAME = new QName("http://www.w3.org/2000/09/xmldsig#", "SignatureProperties");
	private final static QName _Transform_QNAME = new QName("http://www.w3.org/2000/09/xmldsig#", "Transform");
	private final static QName _Reference_QNAME = new QName("http://www.w3.org/2000/09/xmldsig#", "Reference");
	private final static QName _DigestValue_QNAME = new QName("http://www.w3.org/2000/09/xmldsig#", "DigestValue");
	private final static QName _CanonicalizationMethod_QNAME = new QName("http://www.w3.org/2000/09/xmldsig#", "CanonicalizationMethod");
	private final static QName _SignedInfo_QNAME = new QName("http://www.w3.org/2000/09/xmldsig#", "SignedInfo");
	private final static QName _Manifest_QNAME = new QName("http://www.w3.org/2000/09/xmldsig#", "Manifest");
	private final static QName _SignatureMethodTypeHMACOutputLength_QNAME = new QName("http://www.w3.org/2000/09/xmldsig#", "HMACOutputLength");
	private final static QName _X509DataTypeX509IssuerSerial_QNAME = new QName("http://www.w3.org/2000/09/xmldsig#", "X509IssuerSerial");
	private final static QName _X509DataTypeX509CRL_QNAME = new QName("http://www.w3.org/2000/09/xmldsig#", "X509CRL");
	private final static QName _X509DataTypeX509SubjectName_QNAME = new QName("http://www.w3.org/2000/09/xmldsig#", "X509SubjectName");
	private final static QName _X509DataTypeX509SKI_QNAME = new QName("http://www.w3.org/2000/09/xmldsig#", "X509SKI");
	private final static QName _X509DataTypeX509Certificate_QNAME = new QName("http://www.w3.org/2000/09/xmldsig#", "X509Certificate");
	private final static QName _TransformTypeXPath_QNAME = new QName("http://www.w3.org/2000/09/xmldsig#", "XPath");
	private final static QName _PGPDataTypePGPKeyID_QNAME = new QName("http://www.w3.org/2000/09/xmldsig#", "PGPKeyID");
	private final static QName _PGPDataTypePGPKeyPacket_QNAME = new QName("http://www.w3.org/2000/09/xmldsig#", "PGPKeyPacket");
	private final static QName _SPKIDataTypeSPKISexp_QNAME = new QName("http://www.w3.org/2000/09/xmldsig#", "SPKISexp");

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

	@XmlElementDecl(namespace = "http://www.w3.org/2000/09/xmldsig#", name = "KeyInfo")
	public JAXBElement<KeyInfoType> createKeyInfo(KeyInfoType value) {

		return new JAXBElement<KeyInfoType>(_KeyInfo_QNAME, KeyInfoType.class, null, value);
	}

	@XmlElementDecl(namespace = "http://www.w3.org/2000/09/xmldsig#", name = "SignatureProperty")
	public JAXBElement<SignaturePropertyType> createSignatureProperty(SignaturePropertyType value) {

		return new JAXBElement<SignaturePropertyType>(_SignatureProperty_QNAME, SignaturePropertyType.class, null, value);
	}

	@XmlElementDecl(namespace = "http://www.w3.org/2000/09/xmldsig#", name = "RSAKeyValue")
	public JAXBElement<RSAKeyValueType> createRSAKeyValue(RSAKeyValueType value) {

		return new JAXBElement<RSAKeyValueType>(_RSAKeyValue_QNAME, RSAKeyValueType.class, null, value);
	}

	@XmlElementDecl(namespace = "http://www.w3.org/2000/09/xmldsig#", name = "SignatureMethod")
	public JAXBElement<SignatureMethodType> createSignatureMethod(SignatureMethodType value) {

		return new JAXBElement<SignatureMethodType>(_SignatureMethod_QNAME, SignatureMethodType.class, null, value);
	}

	@XmlElementDecl(namespace = "http://www.w3.org/2000/09/xmldsig#", name = "Object")
	public JAXBElement<ObjectType> createObject(ObjectType value) {

		return new JAXBElement<ObjectType>(_Object_QNAME, ObjectType.class, null, value);
	}

	@XmlElementDecl(namespace = "http://www.w3.org/2000/09/xmldsig#", name = "PGPData")
	public JAXBElement<PGPDataType> createPGPData(PGPDataType value) {

		return new JAXBElement<PGPDataType>(_PGPData_QNAME, PGPDataType.class, null, value);
	}

	@XmlElementDecl(namespace = "http://www.w3.org/2000/09/xmldsig#", name = "RetrievalMethod")
	public JAXBElement<RetrievalMethodType> createRetrievalMethod(RetrievalMethodType value) {

		return new JAXBElement<RetrievalMethodType>(_RetrievalMethod_QNAME, RetrievalMethodType.class, null, value);
	}

	@XmlElementDecl(namespace = "http://www.w3.org/2000/09/xmldsig#", name = "DSAKeyValue")
	public JAXBElement<DSAKeyValueType> createDSAKeyValue(DSAKeyValueType value) {

		return new JAXBElement<DSAKeyValueType>(_DSAKeyValue_QNAME, DSAKeyValueType.class, null, value);
	}

	@XmlElementDecl(namespace = "http://www.w3.org/2000/09/xmldsig#", name = "SPKIData")
	public JAXBElement<SPKIDataType> createSPKIData(SPKIDataType value) {

		return new JAXBElement<SPKIDataType>(_SPKIData_QNAME, SPKIDataType.class, null, value);
	}

	@XmlElementDecl(namespace = "http://www.w3.org/2000/09/xmldsig#", name = "SignatureValue")
	public JAXBElement<SignatureValueType> createSignatureValue(SignatureValueType value) {

		return new JAXBElement<SignatureValueType>(_SignatureValue_QNAME, SignatureValueType.class, null, value);
	}

	@XmlElementDecl(namespace = "http://www.w3.org/2000/09/xmldsig#", name = "KeyValue")
	public JAXBElement<KeyValueType> createKeyValue(KeyValueType value) {

		return new JAXBElement<KeyValueType>(_KeyValue_QNAME, KeyValueType.class, null, value);
	}

	@XmlElementDecl(namespace = "http://www.w3.org/2000/09/xmldsig#", name = "Transforms")
	public JAXBElement<TransformsType> createTransforms(TransformsType value) {

		return new JAXBElement<TransformsType>(_Transforms_QNAME, TransformsType.class, null, value);
	}

	@XmlElementDecl(namespace = "http://www.w3.org/2000/09/xmldsig#", name = "DigestMethod")
	public JAXBElement<DigestMethodType> createDigestMethod(DigestMethodType value) {

		return new JAXBElement<DigestMethodType>(_DigestMethod_QNAME, DigestMethodType.class, null, value);
	}

	@XmlElementDecl(namespace = "http://www.w3.org/2000/09/xmldsig#", name = "X509Data")
	public JAXBElement<X509DataType> createX509Data(X509DataType value) {

		return new JAXBElement<X509DataType>(_X509Data_QNAME, X509DataType.class, null, value);
	}

	@XmlElementDecl(namespace = "http://www.w3.org/2000/09/xmldsig#", name = "KeyName")
	public JAXBElement<String> createKeyName(String value) {

		return new JAXBElement<String>(_KeyName_QNAME, String.class, null, value);
	}

	@XmlElementDecl(namespace = "http://www.w3.org/2000/09/xmldsig#", name = "Signature")
	public JAXBElement<SignatureType> createSignature(SignatureType value) {

		return new JAXBElement<SignatureType>(_Signature_QNAME, SignatureType.class, null, value);
	}

	@XmlElementDecl(namespace = "http://www.w3.org/2000/09/xmldsig#", name = "MgmtData")
	public JAXBElement<String> createMgmtData(String value) {

		return new JAXBElement<String>(_MgmtData_QNAME, String.class, null, value);
	}

	@XmlElementDecl(namespace = "http://www.w3.org/2000/09/xmldsig#", name = "SignatureProperties")
	public JAXBElement<SignaturePropertiesType> createSignatureProperties(SignaturePropertiesType value) {

		return new JAXBElement<SignaturePropertiesType>(_SignatureProperties_QNAME, SignaturePropertiesType.class, null, value);
	}

	@XmlElementDecl(namespace = "http://www.w3.org/2000/09/xmldsig#", name = "Transform")
	public JAXBElement<TransformType> createTransform(TransformType value) {

		return new JAXBElement<TransformType>(_Transform_QNAME, TransformType.class, null, value);
	}

	@XmlElementDecl(namespace = "http://www.w3.org/2000/09/xmldsig#", name = "Reference")
	public JAXBElement<ReferenceType> createReference(ReferenceType value) {

		return new JAXBElement<ReferenceType>(_Reference_QNAME, ReferenceType.class, null, value);
	}

	@XmlElementDecl(namespace = "http://www.w3.org/2000/09/xmldsig#", name = "DigestValue")
	public JAXBElement<byte[]> createDigestValue(byte[] value) {

		return new JAXBElement<byte[]>(_DigestValue_QNAME, byte[].class, null, (value));
	}

	@XmlElementDecl(namespace = "http://www.w3.org/2000/09/xmldsig#", name = "CanonicalizationMethod")
	public JAXBElement<CanonicalizationMethodType> createCanonicalizationMethod(CanonicalizationMethodType value) {

		return new JAXBElement<CanonicalizationMethodType>(_CanonicalizationMethod_QNAME, CanonicalizationMethodType.class, null, value);
	}

	@XmlElementDecl(namespace = "http://www.w3.org/2000/09/xmldsig#", name = "SignedInfo")
	public JAXBElement<SignedInfoType> createSignedInfo(SignedInfoType value) {

		return new JAXBElement<SignedInfoType>(_SignedInfo_QNAME, SignedInfoType.class, null, value);
	}

	@XmlElementDecl(namespace = "http://www.w3.org/2000/09/xmldsig#", name = "Manifest")
	public JAXBElement<ManifestType> createManifest(ManifestType value) {

		return new JAXBElement<ManifestType>(_Manifest_QNAME, ManifestType.class, null, value);
	}

	@XmlElementDecl(namespace = "http://www.w3.org/2000/09/xmldsig#", name = "HMACOutputLength", scope = SignatureMethodType.class)
	public JAXBElement<BigInteger> createSignatureMethodTypeHMACOutputLength(BigInteger value) {

		return new JAXBElement<BigInteger>(_SignatureMethodTypeHMACOutputLength_QNAME, BigInteger.class, SignatureMethodType.class, value);
	}

	@XmlElementDecl(namespace = "http://www.w3.org/2000/09/xmldsig#", name = "X509IssuerSerial", scope = X509DataType.class)
	public JAXBElement<X509IssuerSerialType> createX509DataTypeX509IssuerSerial(X509IssuerSerialType value) {

		return new JAXBElement<X509IssuerSerialType>(_X509DataTypeX509IssuerSerial_QNAME, X509IssuerSerialType.class, X509DataType.class, value);
	}

	@XmlElementDecl(namespace = "http://www.w3.org/2000/09/xmldsig#", name = "X509CRL", scope = X509DataType.class)
	public JAXBElement<byte[]> createX509DataTypeX509CRL(byte[] value) {

		return new JAXBElement<byte[]>(_X509DataTypeX509CRL_QNAME, byte[].class, X509DataType.class, (value));
	}

	@XmlElementDecl(namespace = "http://www.w3.org/2000/09/xmldsig#", name = "X509SubjectName", scope = X509DataType.class)
	public JAXBElement<String> createX509DataTypeX509SubjectName(String value) {

		return new JAXBElement<String>(_X509DataTypeX509SubjectName_QNAME, String.class, X509DataType.class, value);
	}

	@XmlElementDecl(namespace = "http://www.w3.org/2000/09/xmldsig#", name = "X509SKI", scope = X509DataType.class)
	public JAXBElement<byte[]> createX509DataTypeX509SKI(byte[] value) {

		return new JAXBElement<byte[]>(_X509DataTypeX509SKI_QNAME, byte[].class, X509DataType.class, (value));
	}

	@XmlElementDecl(namespace = "http://www.w3.org/2000/09/xmldsig#", name = "X509Certificate", scope = X509DataType.class)
	public JAXBElement<byte[]> createX509DataTypeX509Certificate(byte[] value) {

		return new JAXBElement<byte[]>(_X509DataTypeX509Certificate_QNAME, byte[].class, X509DataType.class, (value));
	}

	@XmlElementDecl(namespace = "http://www.w3.org/2000/09/xmldsig#", name = "XPath", scope = TransformType.class)
	public JAXBElement<String> createTransformTypeXPath(String value) {

		return new JAXBElement<String>(_TransformTypeXPath_QNAME, String.class, TransformType.class, value);
	}

	@XmlElementDecl(namespace = "http://www.w3.org/2000/09/xmldsig#", name = "PGPKeyID", scope = PGPDataType.class)
	public JAXBElement<byte[]> createPGPDataTypePGPKeyID(byte[] value) {

		return new JAXBElement<byte[]>(_PGPDataTypePGPKeyID_QNAME, byte[].class, PGPDataType.class, (value));
	}

	@XmlElementDecl(namespace = "http://www.w3.org/2000/09/xmldsig#", name = "PGPKeyPacket", scope = PGPDataType.class)
	public JAXBElement<byte[]> createPGPDataTypePGPKeyPacket(byte[] value) {

		return new JAXBElement<byte[]>(_PGPDataTypePGPKeyPacket_QNAME, byte[].class, PGPDataType.class, (value));
	}

	@XmlElementDecl(namespace = "http://www.w3.org/2000/09/xmldsig#", name = "SPKISexp", scope = SPKIDataType.class)
	public JAXBElement<byte[]> createSPKIDataTypeSPKISexp(byte[] value) {

		return new JAXBElement<byte[]>(_SPKIDataTypeSPKISexp_QNAME, byte[].class, SPKIDataType.class, (value));
	}
}
