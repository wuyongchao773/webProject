package com.product.utils;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Arrays;

public class Decript
{
  public static String SHA1(String decript)
  {
    MessageDigest digest;
    try
    {
      digest = 
        MessageDigest.getInstance("SHA-1");
      digest.update(decript.getBytes());
      byte[] messageDigest = digest.digest();

      StringBuffer hexString = new StringBuffer();

      for (int i = 0; i < messageDigest.length; ++i) {
        String shaHex = Integer.toHexString(messageDigest[i] & 0xFF);
        if (shaHex.length() < 2)
          hexString.append(0);

        hexString.append(shaHex);
      }
      return hexString.toString();
    }
    catch (NoSuchAlgorithmException e) {
      e.printStackTrace();

      return "";
    }
  }

  public static String sort(String token, String timestamp, String nonce)
  {
    String[] arrayOfString1;
    String[] strArray = { token, timestamp, nonce };
    Arrays.sort(strArray);

    StringBuilder sbuilder = new StringBuilder();
    int j = (arrayOfString1 = strArray).length; for (int i = 0; i < j; ++i) { String str = arrayOfString1[i];
      sbuilder.append(str);
    }

    return sbuilder.toString();
  }
}