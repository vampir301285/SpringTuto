package com.minhduc.security.openpgp;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.security.InvalidKeyException;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;
import java.security.Security;
import java.security.SignatureException;

import org.bouncycastle.jce.provider.BouncyCastleProvider;
import org.bouncycastle.openpgp.PGPException;
import org.junit.Test;

public class TestOpenPGP {
	private boolean isArmored = false;
	private String id = "minhduc";
	private String passwd = "*******";
	private boolean integrityCheck = true;
	private String pubKeyFile = "C:/tmp/pub.dat";
	private String privKeyFile = "C:/tmp/secret.dat";

	// create a plain text file
	private String plainTextFile = "C:/tmp/plain-text.txt";
	private String cipherTextFile = "C:/tmp/cypher-text.dat";
	private String decPlainTextFile = "C:/tmp/dec-plain-text.txt";
	private String signatureFile = "C:/tmp/signature.txt";

	@Test
	public void genKeyPair()
			throws InvalidKeyException, NoSuchProviderException, SignatureException, IOException, PGPException, NoSuchAlgorithmException {
		RSAKeyPairGenerator rkpg = new RSAKeyPairGenerator();
		Security.addProvider(new BouncyCastleProvider());
		KeyPairGenerator kpg = KeyPairGenerator.getInstance("RSA", "BC");
		kpg.initialize(1024);
		KeyPair kp = kpg.generateKeyPair();
		FileOutputStream out1 = new FileOutputStream(privKeyFile);
		FileOutputStream out2 = new FileOutputStream(pubKeyFile);
		rkpg.exportKeyPair(out1, out2, kp.getPublic(), kp.getPrivate(), id, passwd.toCharArray(), isArmored);
	}

	@Test
	public void encrypt() throws NoSuchProviderException, IOException, PGPException {
		FileInputStream pubKeyIs = new FileInputStream(pubKeyFile);
		FileOutputStream cipheredFileIs = new FileOutputStream(cipherTextFile);
		OpenPgpHelper.encryptFile(cipheredFileIs, plainTextFile, OpenPgpHelper.readPublicKey(pubKeyIs), isArmored, integrityCheck);
		cipheredFileIs.close();
		pubKeyIs.close();
	}

	@Test
	public void decrypt() throws Exception {
		FileInputStream cipheredFileIs = new FileInputStream(cipherTextFile);
		FileInputStream privKeyIn = new FileInputStream(privKeyFile);
		FileOutputStream plainTextFileIs = new FileOutputStream(decPlainTextFile);
		OpenPgpHelper.decryptFile(cipheredFileIs, plainTextFileIs, privKeyIn, passwd.toCharArray());
		cipheredFileIs.close();
		plainTextFileIs.close();
		privKeyIn.close();
	}

	@Test
	public void signAndVerify() throws Exception {
		FileInputStream privKeyIn = new FileInputStream(privKeyFile);
		FileInputStream pubKeyIs = new FileInputStream(pubKeyFile);
		// FileInputStream plainTextInput = new FileInputStream(plainTextFile);
		FileOutputStream signatureOut = new FileOutputStream(signatureFile);

		// byte[] bIn =
		// PgpHelper.getInstance().inputStreamToByteArray(plainTextInput);
		byte[] sig = OpenPgpHelper.createSignature(plainTextFile, privKeyIn, signatureOut, passwd.toCharArray(), true);
		OpenPgpHelper.verifySignature(plainTextFile, sig, pubKeyIs);
	}

}
