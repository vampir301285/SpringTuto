package com.minhduc.security.openpgp;

import java.io.ByteArrayInputStream;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.security.InvalidKeyException;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;
import java.security.Security;
import java.security.SignatureException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.bouncycastle.jce.provider.BouncyCastleProvider;
import org.bouncycastle.openpgp.PGPCompressedData;
import org.bouncycastle.openpgp.PGPEncryptedDataList;
import org.bouncycastle.openpgp.PGPException;
import org.bouncycastle.openpgp.PGPLiteralData;
import org.bouncycastle.openpgp.PGPObjectFactory;
import org.bouncycastle.openpgp.PGPOnePassSignatureList;
import org.bouncycastle.openpgp.PGPPrivateKey;
import org.bouncycastle.openpgp.PGPPublicKeyEncryptedData;
import org.bouncycastle.openpgp.operator.PublicKeyDataDecryptorFactory;
import org.bouncycastle.openpgp.operator.jcajce.JcePublicKeyDataDecryptorFactoryBuilder;

/**
 * The class to decrypt Open PGP encryption
 * 
 * @author minhducngo85@gmail.com
 *
 */
public class OpenPGPDecryptor {
    /** The user's password */
    private static String PASSWORD = "somepasswd";

    /** The public key file */
    public static String PUB_KEY_FILE = "C:/tmp/openpgp/pub.dat";

    /** The private key file */
    private static String PRIV_KEY_FILE = "C:/tmp/openpgp/secret.dat";

    /** The armored flag */
    private boolean isArmored = true;

    private boolean integrityCheck = true;

    /** The cipher text file */
    public static String CIPHER_TEXT_FILE = "C:/tmp/openpgp/cypher-text.dat";

    /** The multi cipher text file */
    private static String MULTI_CIPHER_TEXT_FILE = "C:/tmp/openpgp/multi-cypher-text.dat";

    /** The plain text file */
    public static String PLAIN_TEXT_FILE = "C:/tmp/openpgp/plain-text.txt";

    /** The decrpted text file */
    public static String DECRPYTED_TEXT_FILE = "C:/tmp/openpgp/dec-plain-text.txt";

    /** The decrpted text file */
    private static String DECRPYTED_MULTI_CIPHER_TEXT_FILE = "C:/tmp/openpgp/dec-multi-cipher-plain-text.txt";

    /** The id */
    private String keyId = "someid";

    /**
     * to generate the key pair (public and private key)
     * 
     * @throws InvalidKeyException
     * @throws NoSuchProviderException
     * @throws SignatureException
     * @throws IOException
     * @throws PGPException
     * @throws NoSuchAlgorithmException
     */
    public void genKeyPair(String passwd, String publicKeyOutput, String privateKeyOutput) throws InvalidKeyException, NoSuchProviderException,
            SignatureException, IOException, PGPException, NoSuchAlgorithmException {
        RSAKeyPairGenerator rkpg = new RSAKeyPairGenerator();
        Security.addProvider(new BouncyCastleProvider());
        KeyPairGenerator kpg = KeyPairGenerator.getInstance("RSA", "BC");
        kpg.initialize(1024);
        KeyPair kp = kpg.generateKeyPair();
        FileOutputStream out1 = new FileOutputStream(privateKeyOutput);
        FileOutputStream out2 = new FileOutputStream(publicKeyOutput);
        rkpg.exportKeyPair(out1, out2, kp.getPublic(), kp.getPrivate(), keyId, passwd.toCharArray(), isArmored);
    }

    /**
     * to encrypt a text file
     * 
     * @param plainTextFile
     *            the plain text file
     * @param cipherTextFile
     *            the ecrypted output file
     * @throws NoSuchProviderException
     * @throws IOException
     * @throws PGPException
     */
    public void encrypt(String plainTextFile, String cipherTextFile, String pubKeyFile) throws NoSuchProviderException, IOException, PGPException {
        FileInputStream pubKeyIs = new FileInputStream(pubKeyFile);
        FileOutputStream cipheredFileIs = new FileOutputStream(cipherTextFile);
        OpenPgpHelper.encryptFile(cipheredFileIs, plainTextFile, OpenPgpHelper.readPublicKey(pubKeyIs), isArmored, integrityCheck);
        cipheredFileIs.close();
        pubKeyIs.close();
    }

    /**
     * to decrypt file
     * 
     * @param cipherTextFile
     * @param decPlainTextFile
     * @param privKeyFile
     * @param passwd
     * @throws Exception
     */
    public void decrypt(String cipherTextFile, String decPlainTextFile, String privKeyFile, String passwd) throws Exception {
        FileInputStream cipheredFileIs = new FileInputStream(cipherTextFile);
        FileInputStream privKeyIn = new FileInputStream(privKeyFile);
        FileOutputStream plainTextFileIs = new FileOutputStream(decPlainTextFile);
        OpenPgpHelper.decryptFile(cipheredFileIs, plainTextFileIs, privKeyIn, passwd.toCharArray());
        cipheredFileIs.close();
        plainTextFileIs.close();
        privKeyIn.close();
    }

    /**
     * to decrypt file
     * 
     * @param cipherTextFile
     * @param decPlainTextFile
     * @param privKeyFile
     * @param passwd
     * @throws Exception
     */
    public List<String> decryptMultipleBlocksFile(String cipherTextFile, String privKeyFile, String passwd) throws Exception {
        String cipherMessage = Utils.readTextFile(cipherTextFile).trim();
        // System.out.println(cipherMessage);
        String beginPGPMessage = "-----BEGIN PGP MESSAGE-----";
        String[] outputArray = cipherMessage.split(beginPGPMessage);
        List<String> ret = new ArrayList<String>();
        int count = 0;
        for (int i = 0; i < outputArray.length; i++) {
            String pgpBlock = outputArray[i];
            if (!pgpBlock.trim().isEmpty() && pgpBlock.contains("-----END PGP MESSAGE-----")) {
                count++;
                pgpBlock = beginPGPMessage + pgpBlock;
                // System.out.println(pgpBlock);
                InputStream cipheredIs = new ByteArrayInputStream(pgpBlock.getBytes("UTF-8"));
                FileInputStream privKeyIn = new FileInputStream(privKeyFile);
                String decryptedMessage = decryptPGPMessage(cipheredIs, privKeyIn, passwd.toCharArray());
                cipheredIs.close();
                privKeyIn.close();
                ret.add(decryptedMessage);
            }
        }
        System.out.println("Number of PGP messages decrypted = " + count);
        return ret;
    }

    /**
     * decrypt the passed in message stream
     */
    @SuppressWarnings("unchecked")
    private String decryptPGPMessage(InputStream in, InputStream keyIn, char[] passwd) throws Exception {
        Security.addProvider(new BouncyCastleProvider());
        in = org.bouncycastle.openpgp.PGPUtil.getDecoderStream(in);
        PGPObjectFactory pgpF = new PGPObjectFactory(in);
        PGPEncryptedDataList enc;
        Object o = pgpF.nextObject();
        //
        // the first object might be a PGP marker packet.
        //
        if (o instanceof PGPEncryptedDataList) {
            enc = (PGPEncryptedDataList) o;
        } else {
            enc = (PGPEncryptedDataList) pgpF.nextObject();
        }

        //
        // find the secret key
        //
        Iterator<PGPPublicKeyEncryptedData> it = enc.getEncryptedDataObjects();
        PGPPrivateKey sKey = null;
        PGPPublicKeyEncryptedData pbe = null;

        while (sKey == null && it.hasNext()) {
            pbe = it.next();
            sKey = OpenPgpHelper.findSecretKey(keyIn, pbe.getKeyID(), passwd);
        }

        if (sKey == null) {
            throw new IllegalArgumentException("Secret key for message not found.");
        }

        PublicKeyDataDecryptorFactory b = new JcePublicKeyDataDecryptorFactoryBuilder().setProvider("BC").setContentProvider("BC").build(sKey);

        InputStream clear = pbe.getDataStream(b);

        PGPObjectFactory plainFact = new PGPObjectFactory(clear);

        Object message = plainFact.nextObject();

        if (message instanceof PGPCompressedData) {
            PGPCompressedData cData = (PGPCompressedData) message;
            PGPObjectFactory pgpFact = new PGPObjectFactory(cData.getDataStream());

            message = pgpFact.nextObject();
        }

        String output = null;
        if (message instanceof PGPLiteralData) {
            PGPLiteralData ld = (PGPLiteralData) message;
            InputStream unc = ld.getInputStream();
            output = Utils.getStringFromInputStream(unc);

        } else if (message instanceof PGPOnePassSignatureList) {
            throw new PGPException("Encrypted message contains a signed message - not literal data.");
        } else {
            throw new PGPException("Message is not a simple encrypted file - type unknown.");
        }

        if (pbe.isIntegrityProtected()) {
            if (!pbe.verify()) {
                throw new PGPException("Message failed integrity check");
            }
        }
        return output;
    }

    /**
     * the main method
     * 
     * @param args
     * @throws Exception
     */
    public static void main(String[] args) {
        // OpenPGPDecryptor.run(args);
        OpenPGPDecryptor.test();
    }

    public static void run(String[] args) {
        try {
            if (args != null && args.length == 4) {
                String cipherTextFile = args[0];
                System.out.println("-----Arguments:-----");
                System.out.println("Cipher file: " + cipherTextFile);
                String privKeyFile = args[1];
                System.out.println("Private key file: " + privKeyFile);
                String password = args[2];
                System.out.println("Password phrase:" + password);
                String output = args[3];
                System.out.println("Output Doc:" + output);
                System.out.println();
                System.out.println("-----Decrypting pgp messages, please wait-----");
                OpenPGPDecryptor decryptor = new OpenPGPDecryptor();
                List<String> dectext = decryptor.decryptMultipleBlocksFile(cipherTextFile, privKeyFile, password);
                for (int i = 0; i < dectext.size(); i++) {
                    System.out.println("Decrypted block[" + i + "]: ");
                    System.out.println(dectext.get(i));
                }
                System.out.println();
                System.out.println("-----Writing... to word document: " + output);
                WordDocumentUtils.writeToWordDocument(dectext, output);
                System.out.println("Sucessfully created word document");
                System.out.println("-----End----");

            } else {
                System.err.println("Invalid inputs.");
            }
        } catch (Exception e) {
            System.err.println("Error: " + e.getMessage());
        }
        System.out.println("Press enter to exit!");
        try {
            System.in.read();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void test() {
        System.out.println("----Starting, please wait----");
        try {
            OpenPGPDecryptor decryptor = new OpenPGPDecryptor();
            // decryptor.genKeyPair(PASSWORD, PUB_KEY_FILE, PRIV_KEY_FILE);
            decryptor.encrypt(PLAIN_TEXT_FILE, CIPHER_TEXT_FILE, PUB_KEY_FILE);
            decryptor.decrypt(CIPHER_TEXT_FILE, DECRPYTED_TEXT_FILE, PRIV_KEY_FILE, PASSWORD);
            List<String> dectext = decryptor.decryptMultipleBlocksFile(MULTI_CIPHER_TEXT_FILE, PRIV_KEY_FILE, PASSWORD);
            // System.out.println(dectext.toString());
            Utils.writeToTextFile(dectext.toString(), DECRPYTED_MULTI_CIPHER_TEXT_FILE);
            WordDocumentUtils.writeToWordDocument(dectext, "C:/tmp/openpgp/mydocument.docx");
        } catch (Exception e) {
            System.err.println(e.getMessage());
        }
        System.out.println("----End----");
    }
}
