package myhomepage;

import java.io.File;
import java.io.FileInputStream;
import java.security.KeyStore;
import java.security.PrivateKey;
import java.security.cert.X509Certificate;

public class Certificate {
	public static void main(String[] args) throws Exception{

//      XMLSignatureFactory fac = XMLSignatureFactory.getInstance("DOM");
        KeyStore ks = KeyStore.getInstance("PKCS12");
        String certificatePassword="123456";
       
        String cerficateAlias="0d02cb8ef1e4e4489f2ed658e339a7a3_e2071dad-c4da-42c8-b97c-da34254db0a1";
        ks.load(new FileInputStream(new File("C:/Rajeev/certiWithChain.pfx")),certificatePassword.toCharArray());
        KeyStore.PrivateKeyEntry keyEntry =(KeyStore.PrivateKeyEntry) ks.getEntry(cerficateAlias,new KeyStore.PasswordProtection(certificatePassword.toCharArray()));
         System.out.println(keyEntry);
        X509Certificate cert =(X509Certificate) keyEntry.getCertificate();
        X509Certificate[] chain = (X509Certificate[]) keyEntry.getCertificateChain();
        PrivateKey key = keyEntry.getPrivateKey();
       
        System.out.println(keyEntry);
       
/*              System.out.println("*********************** AVAILABLE PROVIDERS **************************************");
       
        for(Provider provider : Security.getProviders()) {
                System.out.println(provider.getName());
        }

        System.out.println("*********************** AVAILABLE PROVIDERS **************************************");
        System.out.println();
        System.out.println();
        System.out.println();
        System.out.println();
        System.out.println();
        System.out.println("*********************** PRIVATE KEY **************************************");

        System.out.println("Algorithm : " + key.getAlgorithm());
        System.out.println("Format : " + key.getFormat());
        System.out.println("Encoded: Private Key : " + new String(Base64.encodeBase64(key.getEncoded()),"UTF-8"));
        System.out.println("Class : " + key.getClass());

        System.out.println("*********************** PRIVATE KEY **************************************");
        System.out.println();
        System.out.println();
        System.out.println();
        System.out.println();
        System.out.println();
        System.out.println("*********************** CERTIFICATE **************************************");

        System.out.println("Signature : " + new String(Base64.encodeBase64(cert.getSignature()), "UTF-8")); //signature
        System.out.println("Encoded : " + new String(Base64.encodeBase64(cert.getEncoded()), "UTF-8")); //certificate
        System.out.println("Extension value : " + new String(Base64.encodeBase64(cert.getExtensionValue("2.5.29.17")), "UTF-8")); //certificate
        System.out.println("Basic Constraints : " + cert.getBasicConstraints()); //certificate
        System.out.println("Serial No : " + cert.getSerialNumber()); //certificate
        System.out.println("TBS Certificate : " + new String(Base64.encodeBase64(cert.getTBSCertificate()))); //certificate
       
        System.out.println("*********************** CERTIFICATE **************************************");
        System.out.println();
        System.out.println();
        System.out.println();
        System.out.println();
        System.out.println();
        System.out.println("*********************** CERTIFICATE CHAIN**************************************");
       
        for (X509Certificate x509Certificate : chain) {
                System.out.println("PUBLIC KEY: " + x509Certificate.getPublicKey());
                System.out.println("SIGNATURE: " +new String(Base64.encodeBase64(x509Certificate.getSignature())));
                System.out.println("USAGE DIGITAL SIGNATURE: " +x509Certificate.getKeyUsage()[0]);
                System.out.println("USAGE NONREPUDIATION: " +x509Certificate.getKeyUsage()[1]);
                System.out.println("USAGE KEY ENCIPHERMENT: " +x509Certificate.getKeyUsage()[2]);
                System.out.println("USAGE DATA ENCIPHERMENT: " +x509Certificate.getKeyUsage()[3]);
                System.out.println("USAGE KEY AGREEMENT: " +x509Certificate.getKeyUsage()[4]);
                System.out.println("USAGE KEY CERTSIGN: " +x509Certificate.getKeyUsage()[5]);
                System.out.println("USAGE CRL SIGN: " +x509Certificate.getKeyUsage()[6]);
                System.out.println("USAGE ENCIPHER ONLY: " +x509Certificate.getKeyUsage()[7]);
                System.out.println("USAGE DECIPHER ONLY: " +x509Certificate.getKeyUsage()[8]);
                System.out.println("TBS CERTIFICATE: " +new String(Base64.encodeBase64(x509Certificate.getTBSCertificate())));
                System.out.println("SERIAL NUMBER: " +x509Certificate.getSerialNumber());
        }
       
        System.out.println("*********************** CERTIFICATE CHAIN**************************************");
       
       
       
       
       
       
       
       
       
        //Put everything after here in your function.
        KeyStore trustStore  = KeyStore.getInstance(KeyStore.getDefaultType());
        trustStore.load(null);//Make an empty store
        InputStream fis = new FileInputStream(new File("C:/Users/JavaDeamon/Desktop/certi/cer.cer"));
        BufferedInputStream bis = new BufferedInputStream(fis);

        CertificateFactory cf = CertificateFactory.getInstance("X.509");
        Certificate cert2 = null;
        while (bis.available() > 0) {
            cert2 = cf.generateCertificate(bis);
            trustStore.setCertificateEntry("fiddler"+bis.available(), cert2);
        }
        System.out.println();
        System.out.println();
        System.out.println();
        System.out.println();
        System.out.println();
        System.out.println("*********************** CER FILE**************************************");
        System.out.println("Encoded - PK: " + new String(Base64.encodeBase64(cert2.getPublicKey().getEncoded())));
        System.out.println("Encoded - CR: " + new String(Base64.encodeBase64(cert2.getEncoded())));
        String alias2 = trustStore.getCertificateAlias(cert2);
        System.out.println("Alias: " + alias2);
        System.out.println("CERTIFICATE: " + trustStore.getCertificate(alias2));
        for(Certificate certificate : trustStore.getCertificateChain(alias2)) {
                System.out.println("Encoded - CH: " +
                                "" + new String(Base64.encodeBase64(certificate.getEncoded())));
        }
       
        System.out.println("*********************** CER FILE**************************************");
        System.out.println();
        System.out.println();
        System.out.println();
        System.out.println();
        System.out.println();
        System.out.println("*********************** P7B FILE**************************************");
       
        try {
            File file = new File("C:/Users/JavaDeamon/Desktop/certi/p7b.p7b");
            FileInputStream fis2 = new FileInputStream(file);
            CertificateFactory cf2 = CertificateFactory.getInstance("X.509");
            Collection c = cf2.generateCertificates(fis2);
            Iterator i = c.iterator();
            while (i.hasNext()) {
              X509Certificate cert509 = (X509Certificate) i.next();
              System.out.println(cert509);
            }
          }
          catch (Throwable th) {
            th.printStackTrace();
          }
       
       
       
        */
       
       
       
       
       
       
       
       
       
       
       
       
       
        /*System.out.println();
        System.out.println("*************************************************************");
        Enumeration<String> enumeration = ks.aliases();
        String alias = enumeration.nextElement();
        System.out.println("Aliases: " + alias);
       
        java.security.cert.Certificate certificate = ks.getCertificate(alias);
        PublicKey key = certificate.getPublicKey();
        System.out.println("Encoded:" + key.getEncoded());
        System.out.println("Encoded: Private Key : " + new String(Base64.encodeBase64(certificate.getEncoded()),"UTF-8"));
       
        System.out.println("Certificate : " + ks.getCertificate(alias));
        System.out.println();
        System.out.println("*************************************************************");
        System.out.println("Chain");
        for(Certificate certificate2 : ks.getCertificateChain(alias)) {
                System.out.println(new String(Base64.encodeBase64(certificate2.getEncoded()),"UTF-8"));
        }
        for (int i = 0; i < ks.getCertificateChain(alias).length; i++) {
                System.out.println("Chain Element "+i+ " : " +ks.getCertificateChain(alias)[i]);
        }
        System.out.println("*************************************************************");
       
       
       
       
        System.out.println();
        System.out.println("*************************************************************");
        for (int i = 0; i < chain.length; i++) {
                System.out.println("Chain Element "+i+ " : " +chain[i]);
        }*/
/*      

        FileInputStream fileInputStream=null;
         
File file = new File("C:\\Users\\JavaDeamon\\Desktop\\files\\a[signed].zip");

byte[] bFile = new byte[(int) file.length()];
try {
    //convert file into array of bytes
    fileInputStream = new FileInputStream(file);
    fileInputStream.read(bFile);
    fileInputStream.close();

    for (int i = 0; i < bFile.length; i++) {
        System.out.print((char)bFile[i]);
    }

    System.out.println("Done");
}catch(Exception e){
        e.printStackTrace();
}*/
}


}
