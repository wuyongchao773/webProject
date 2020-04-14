package com.product.utils;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

public class ResponseUtils
{
  public static <T> JSONObject success(T data, String message)
  {
    JSONObject jsonObject = new JSONObject();
    jsonObject.put("status", Integer.valueOf(200));
    jsonObject.put("message", "");
    jsonObject.put("data", JSONObject.fromObject(data));
    return jsonObject;
  }

  public static JSONObject success(String data, String message)
  {
    JSONObject jsonObject = new JSONObject();
    jsonObject.put("status", Integer.valueOf(200));
    jsonObject.put("message", "");
    jsonObject.put("data", JSONObject.fromObject(data));
    return jsonObject;
  }

  public static JSONObject successArray(String data)
  {
    JSONObject jsonObject = new JSONObject();
    jsonObject.put("status", Integer.valueOf(200));
    jsonObject.put("message", "");
    jsonObject.put("data", JSONArray.fromObject(data));
    return jsonObject;
  }

  public static JSONObject success(String message)
  {
    JSONObject jsonObject = new JSONObject();
    jsonObject.put("status", Integer.valueOf(200));
    jsonObject.put("message", message);
    return jsonObject;
  }

  public static JSONObject field(String message)
  {
    JSONObject jsonObject = new JSONObject();
    jsonObject.put("message", message);
    jsonObject.put("status", "201");
    return jsonObject;
  }

  public static JSONObject field()
  {
    JSONObject jsonObject = new JSONObject();
    jsonObject.put("status", "201");
    return jsonObject;
  }
}