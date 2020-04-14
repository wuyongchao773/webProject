package com.product.http;


import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.Charset;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.util.EntityUtils;

import com.product.utils.SSLClient;

public class HttpUtils
{
  public static String httpPostByString(String url, String param, String charset)
  {
    HttpClient httpClient = null;
    HttpPost httpPost = null;
    String result = null;
    try {
      httpClient = new SSLClient();
      httpPost = new HttpPost(url);
      httpPost.addHeader("Content-Type", "application/json; charset=utf-8");
      httpPost.setHeader("Accept", "application/json");
      HttpEntity httpEntity = new StringEntity(param, Charset.forName(charset));
      httpPost.setEntity(httpEntity);
      HttpResponse httpResponse = httpClient.execute(httpPost);
      result = EntityUtils.toString(httpResponse.getEntity(), charset);
    } catch (Exception e) {
      e.printStackTrace();
    }
    return result;
  }

  public static String HttpsGet(String url, String charset)
  {
    HttpClient httpClient = null;
    HttpGet httpGet = null;
    String result = null;
    try {
      httpClient = new SSLClient();
      httpGet = new HttpGet(url);
      httpGet.addHeader("Content-Type", "application/json; charset=utf-8");
      HttpResponse httpResponse = httpClient.execute(httpGet);
      if (httpResponse != null) {
        HttpEntity entity = httpResponse.getEntity();
        if (entity != null)
          result = EntityUtils.toString(entity, charset);
      }
    }
    catch (Exception e) {
      e.printStackTrace();
    }
    return result;
  }

  public static String connectHttpsByPost(String url, String kk, File file)
    throws IOException
  {
    URL url1 = new URL(url);
    HttpURLConnection connection = (HttpURLConnection)url1.openConnection();
    String result = null;
    connection.setDoOutput(true);
    connection.setDoInput(true);
    connection.setUseCaches(false);
    connection.setRequestProperty("Connection", "Keep-Alive");
    connection.setRequestProperty("Charset", "UTF-8");

    String BOUNDARY = "----------" + System.currentTimeMillis();
    connection.setRequestProperty("Content-Type", "multipart/form-data; boundary=" + BOUNDARY);

    StringBuilder sb = new StringBuilder();
    sb.append("--");
    sb.append(BOUNDARY);
    sb.append("\r\n");
    sb.append("Content-Disposition: form-data;name=\"media\";filelength=\"" + file.length() + "\";filename=\"" + file.getName() + "\"\r\n");
    sb.append("Content-Type:application/octet-stream\r\n\r\n");
    byte[] head = sb.toString().getBytes("utf-8");

    OutputStream out = new DataOutputStream(connection.getOutputStream());

    out.write(head);

    DataInputStream in = new DataInputStream(new FileInputStream(file));
    int bytes = 0;
    byte[] bufferOut = new byte[1024];
    while ((bytes = in.read(bufferOut)) != -1)
      out.write(bufferOut, 0, bytes);

    in.close();

    String foot = "\r\n--" + BOUNDARY + "--\r\n".getBytes("utf-8");
    out.write(foot.getBytes());
    out.flush();
    out.close();
    StringBuffer buffer = new StringBuffer();
    BufferedReader reader = null;
    try
    {
      reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
      String line = null;
      while ((line = reader.readLine()) != null)
        buffer.append(line);

      if (result == null)
        result = buffer.toString();
    }
    catch (IOException e) {
      System.out.println("发送POST请求出现异常！" + e);
      e.printStackTrace();

      if (reader != null)
        reader.close();
    }
    finally
    {
      if (reader != null)
        reader.close();
    }

    return result;
  }
}