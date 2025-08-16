package com.ms.ws.utils;

import java.util.Base64;
import java.util.Base64.Decoder;
import javax.crypto.Cipher;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;

public class ApiUtil {
	
	  public static String decrypt(String encryptedString) {
		  
		  String ogString="";
	        try
	        {
	            //String data = "aK7+UX24ttBgfTnAndz9aQ==" ;
	            String key = "1234567812345678";
	            String iv = "1234567812345678";

	            Decoder decoder = Base64.getDecoder();   
	             byte[] encrypted1 = decoder.decode(encryptedString);

	            Cipher cipher = Cipher.getInstance("AES/CBC/NoPadding");
	            SecretKeySpec keyspec = new SecretKeySpec(key.getBytes(), "AES");
	            IvParameterSpec ivspec = new IvParameterSpec(iv.getBytes());

	            cipher.init(Cipher.DECRYPT_MODE, keyspec, ivspec);

	            byte[] original = cipher.doFinal(encrypted1);
	            String originalString = new String(original);
	            ogString=originalString.trim();
	            
	        }
	        catch (Exception e) {
	            e.printStackTrace();
	        }
	        
	        return ogString;
	    }
	  

	  
		public static String encrypt(String input) {

			String key = "1234567812345678";
			String iv = "1234567812345678";
			try {
				 IvParameterSpec ivspec = new IvParameterSpec(iv.getBytes());
				 SecretKeySpec keyspec = new SecretKeySpec(key.getBytes(), "AES");
				 Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
				cipher.init(Cipher.ENCRYPT_MODE, keyspec, ivspec);
				byte[] cipherText = cipher.doFinal(input.getBytes());
				return Base64.getEncoder().encodeToString(cipherText);

			} catch (Exception e) {
				
				e.printStackTrace();
			}
			return "";

		}

}
