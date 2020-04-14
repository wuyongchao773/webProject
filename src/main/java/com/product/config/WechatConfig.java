package com.product.config;

/***
 * @Title 微信工具类
 * @author wuyongchao
 * @date 2018-11-04
 */
public class WechatConfig {

	private static final String appID = "wx046e68c71b72b851";
	
	private static final String appSecret = "2a6e1cd3c2aba75afd2a07dafdc43dab";
	
	public static final String token = "wuyongchao773";
	
	/**
	 * 回复文本消息
	 */
	public static final String text = "text";  //
	/** *事件消息 订阅 和取消订阅*/
	public static final String event = "event"; //事件类型  subscribe(订阅)、unsubscribe(取消订阅)
	/***回复图片 消息*/
	public static final String image = "image"; //回复图片消息
	/*** 回复语音消息*/
	public static final String voice = "voice"; //回复语音消息
	/*** 回复视频消息*/
	public static final String video = "video"; //回复视频消息
	/*** 小视频消息*/
	public static final String shortvideo = "shortvideo"; //小视频消息
	/*** 地理位置消息*/
	public static final String location = "loscation"; //地理位置消息
	/*** 链接消息*/
	public static final String link = "link"; //地理位置消息
	/*** 回复音乐消息*/
	public static final String music = "music"; //回复音乐消息
	/*** 回复图文消息消息*/
	public static final String news = "news"; //图文消息
	/*** 获得access_token地址*/
	public static final String accessTokenUrl = "https://api.weixin.qq.com/cgi-bin/token?grant_type=client_credential&appid="+appID+"&secret="+appSecret+"";
}
