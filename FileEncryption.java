package com.tkis.qedbot;

import javax.crypto.Cipher; 
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.PBEKeySpec;
import javax.crypto.spec.PBEParameterSpec;

import org.springframework.stereotype.Component;

//import sun.misc.BASE64Decoder;
//import sun.misc.BASE64Encoder;
import java.util.Base64;

@Component
public class FileEncryption 
{
   private PBEKeySpec pbeKeySpec;
   private PBEParameterSpec pbeParamSpec;
   private SecretKeyFactory keyFac;  
   private Cipher pbeCipher;
   private SecretKey pbeKey;
   private String key = "YK";
   private int count = 20;
   private byte[] bSalt = new byte[8]; 
  // Salt
   byte[] salt = {
        (byte)0xc7, (byte)0x73, (byte)0x21, (byte)0x8c,
        (byte)0x7e, (byte)0xc8, (byte)0xee, (byte)0x99 
   };  
  
   public String Encrypt(String strToEncrypt)
   {
       String encryptedStr = "";
       try
       {
            Base64.Encoder encoder = Base64.getMimeEncoder(); 
            encryptedStr = encoder.encodeToString(strToEncrypt.getBytes());
            
       }catch(Exception e)
       {
           e.printStackTrace();
       }
       return encryptedStr;
       
   }
   
   public String Decrypt(String encryptedStr)
   {
       String decryptStr = "";
       try
       {
            Base64.Decoder decoder = Base64.getMimeDecoder();  
            decryptStr = new String(decoder.decode(encryptedStr));  
            
       }catch(Exception e)
       {
           e.printStackTrace();
       }
       return decryptStr;
       
   }
  
  
  public static void main(String[] args) 
  {
  FileEncryption fe = new FileEncryption();
  String encryptedString = null;
  //encryptedString = fe.Encrypt("runBOT@tk1s");
  //String decryptedString = fe.Decrypt("cnVuQk9UQHRrMXM=");//root
  encryptedString = fe.Encrypt("SSzcr^?7d");
  System.out.println("encryptedString:  "+encryptedString); 
  String decryptedString = fe.Decrypt("U1N6Y3JePzdk");//root
  System.out.println("#### decryptedString:  "+decryptedString);

}

  
  
  

} 
