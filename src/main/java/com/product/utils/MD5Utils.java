package com.product.utils;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class MD5Utils
{
  private static final Pattern pattern = Pattern.compile("\\d+");
  private static final String charset = "utf-8";

  public static String encode(String src, String key)
  {
    byte[] data;
    try
    {
      data = src.getBytes("utf-8");
      byte[] keys = key.getBytes();
      StringBuilder sb = new StringBuilder();
      for (int i = 0; i < data.length; ++i)
      {
        int n = (0xFF & data[i]) + (0xFF & keys[(i % keys.length)]);
        sb.append("@" + n);
      }
      return sb.toString();
    } catch (UnsupportedEncodingException e) {
      e.printStackTrace();

      return src;
    }
  }

  public static String decode(String src, String key) {
    if ((src == null) || (src.length() == 0)) {
      return src;
    }

    Matcher m = pattern.matcher(src);

    List list = new ArrayList();

    for (; m.find(); );
    if (list.size() > 0);
    try
    {
      byte[] data = new byte[list.size()];
      byte[] keys = key.getBytes();

      for (int i = 0; i < data.length; ++i)
        data[i] = (byte)(((Integer)list.get(i)).intValue() - (0xFF & keys[(i % keys.length)]));

      return new String(data, "utf-8");
    } catch (UnsupportedEncodingException e) {
      e.printStackTrace();

      return src;
    }
  }
}