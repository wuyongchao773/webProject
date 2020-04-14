package com.product.controller;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import javax.servlet.ServletInputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.fasterxml.jackson.annotation.ObjectIdGenerators.UUIDGenerator;
import com.product.config.WechatConfig;
import com.product.http.HttpUtils;
import com.product.model.TUser;
import com.product.redis.RadisPoolUtils;
import com.product.service.UserService;
import com.product.utils.Base64Utils;
import com.product.utils.Decript;
import com.product.utils.IdGenerator;

import net.sf.json.JSONObject;

@Controller
public class AccessTokenController {
	
	private Logger logger = LoggerFactory.getLogger(AccessTokenController.class); 
	
	@Autowired
	private UserService userService;

	/***
	 * @Title 校验access_token方法
	 * @author wuyongchao
	 * @date 2018-11-04
	 * @return echostr 随机字符串
	 * @throws IOException 
	 */
	@SuppressWarnings({ "rawtypes", "unchecked" })
	@RequestMapping(value = "/checkToken",method = RequestMethod.GET)
	public void checkAccessToken(HttpServletRequest request,HttpServletResponse response) throws IOException{
		logger.info("开始签名校验");
	    String signature = request.getParameter("signature");
	    String timestamp = request.getParameter("timestamp");
	    String nonce = request.getParameter("nonce");
	    String echostr = request.getParameter("echostr");

	    ArrayList array = new ArrayList();
	    array.add(signature);
	    array.add(timestamp);
	    array.add(nonce);

	    String sortString = Decript.sort(WechatConfig.token, timestamp, nonce);

	    String mytoken = Decript.SHA1(sortString);

	    if ((mytoken != null) && (mytoken != "") && (mytoken.equals(signature))) {
	    	logger.info("签名校验通过。");
			response.getWriter().println(echostr);
	    } else {
	    	logger.info("签名校验失败。");
	    }
	}
	
	/***
	 * @Title 校验数据的
	 * @author wuyongchao
	 * @throws IOException 
	 * @throws DocumentException 
	 * @date 2018-11-04
	 */
	@RequestMapping(value = "/checkToken",method = RequestMethod.POST)
	public void getAccessToken(HttpServletRequest request,HttpServletResponse response) throws IOException, DocumentException{
		request.setCharacterEncoding("UTF-8");
		response.setCharacterEncoding("UTF-8");
		PrintWriter responseXml = response.getWriter();
		ServletInputStream inputStream = request.getInputStream();
		SAXReader reader = new SAXReader();
		Document document = reader.read(inputStream);
		Element element = document.getRootElement();
		String responseInfo = this.checkWechatType(element);
		responseXml.println(responseInfo);
	}
	
	/***
	 * @Title 检查微信请求类型
	 * @author wuyongchao
	 * @date 2018-11-04
	 * @return
	 */
	@SuppressWarnings("unchecked")
	private String checkWechatType(Element element){
		Map<String,Object> map = new HashMap<String,Object>();
		List<Element> elements = element.elements();
		for(Element el : elements){
			map.put(el.getName(),el.getText());
		}
		return this.buildXMLResponse(map);
	}
	
	/***
	 * @Title 返回消息类型
	 * @author wuyongchao
	 * @date 2018-11-04
	 * @param msgType
	 * @return
	 */
	private String buildXMLResponse(Map<String,Object> map){
		Document document = DocumentHelper.createDocument();
		Element xml = DocumentHelper.createElement("xml");
		Element toUserName = DocumentHelper.createElement("ToUserName");
		toUserName.addCDATA(String.valueOf(map.get("FromUserName")));
		Element fromUserName = DocumentHelper.createElement("FromUserName");
		fromUserName.addCDATA(String.valueOf(map.get("ToUserName")));
		Element createTime = DocumentHelper.createElement("CreateTime");
		createTime.addCDATA(String.valueOf(map.get("CreateTime")));
		Element msgType = DocumentHelper.createElement("MsgType");
		Element content = DocumentHelper.createElement("Content");
		msgType.addCDATA("text");
		if(String.valueOf(map.get("MsgType")).equals(WechatConfig.text)){
			content.addCDATA("文本消息")	;
		}else if(String.valueOf(map.get("MsgType")).equals(WechatConfig.image)){
			content.addCDATA("图片消息");
		}else if(String.valueOf(map.get("MsgType")).equals(WechatConfig.voice)){
			content.addCDATA("语音消息");
		}else if(String.valueOf(map.get("MsgType")).equals(WechatConfig.video)){
			content.addCDATA("视频消息");
		}else if(String.valueOf(map.get("MsgType")).equals(WechatConfig.music)){
			content.addCDATA("音乐消息");
		}else if(String.valueOf(map.get("MsgType")).equals(WechatConfig.news)){
			content.addCDATA("图文消息");
		}else if(String.valueOf(map.get("MsgType")).equals(WechatConfig.event)){
			String eventType = String.valueOf(map.get("Event"));
			if(eventType.equals("subscribe")){
				String userOpenId = String.valueOf(map.get("FromUserName"));
				//查询用户是否存在
				String responseOpenId = userService.queryUser(userOpenId);
				if(null == responseOpenId){
					//获取用户详情信息的url
					String getUserInfoUrl = "https://api.weixin.qq.com/cgi-bin/user/info?access_token="+RadisPoolUtils.get("access_token")+"&openid="+userOpenId+"&lang=zh_CN";
					JSONObject obj =JSONObject.fromObject(HttpUtils.HttpsGet(getUserInfoUrl,"UTF-8"));
					TUser tUser = new TUser();
					tUser.setId(IdGenerator.makeUUidGenerator());
					tUser.setOpenId(userOpenId);
					tUser.setRealName(obj.getString("nickname"));
					tUser.setSex(obj.getString("sex"));
					tUser.setLanguage(obj.getString("language"));
					tUser.setCity(obj.getString("city"));
					tUser.setProvince(obj.getString("province"));
					tUser.setCounty("county");
					tUser.setHeadUrl(obj.getString("headimgurl"));
					tUser.setUserType("2");
					tUser.setRoleType("4");
					tUser.setIsVip("0");
					tUser.setSubscribe("1");
					tUser.setDelete_status("0");
					int len = userService.addUser(tUser);
					System.out.println(len>0?"添加成功":"添加失败");
					content.addCDATA("文本消息");
				}
			}else{
				//用户取消了订阅
				content.addCDATA("文本消息");
			}
		}
		xml.add(toUserName);
		xml.add(fromUserName);
		xml.add(createTime);
		xml.add(msgType);
		xml.add(content);
		document.add(xml);
		return document.asXML();
	}
}
